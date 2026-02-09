package net.sam.dcl.config;

import net.sam.dcl.config.Config;
import net.sam.dcl.config.ConfigImpl;
import net.sam.dcl.db.VDbConnection;
import net.sam.dcl.db.VDbConnectionManager;
import net.sam.dcl.db.VDbException;
import net.sam.dcl.navigation.ControlActions;
import net.sam.dcl.navigation.ControlComments;
import net.sam.dcl.navigation.XMLPermissions;
import net.sam.dcl.util.DbReconnector;
import net.sam.dcl.util.HibernateUtil;
import net.sam.dcl.util.StoreUtil;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.TextResource;
import net.sam.dcl.util.XMLResource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Lightweight startup bootstrap to replace legacy InitApp servlet during migration.
 * Initializes:
 * - DB driver and pool (VDbConnectionManager)
 * - Hibernate (HibernateUtil)
 * - Reloads DB-backed config
 * - Loads XML resources (SQL, Permissions) and Text resources (slogans)
 * - Loads Control comments/actions
 *
 * Intentionally excludes Quartz and Finalizer thread.
 */
public class DbInitListener implements ServletContextListener {

  private static final Log log = LogFactory.getLog(DbInitListener.class);

  @Override
  public void contextInitialized(ServletContextEvent sce) {
    final ServletContext context = sce.getServletContext();
    log.info("DbInitListener: initialization started");

    try {
      // 1) Ensure Config is ready (ConfigInitListener should have run already)
      // If not, do a minimal fallback init to avoid NPEs in dev.
      ensureConfigInitialized(context);

      // 2) Initialize JDBC driver and pool
      initDbPool();

      // 3) Initialize Hibernate
      log.info("DbInitListener: initializing Hibernate...");
      HibernateUtil.init();
      log.info("DbInitListener: Hibernate initialized");

      // 4) Reload DB-backed configuration (after Hibernate/DB ready)
      log.info("DbInitListener: reloading configuration from DB...");
      Config.reload();
      log.info("DbInitListener: configuration reloaded");

      // 5) Load application resources used by actions and JSPs
      loadApplicationResources(context);

      log.info("DbInitListener: initialization finished");
    } catch (Throwable t) {
      log.error("DbInitListener: initialization error", t);
    }
  }

  @Override
  public void contextDestroyed(ServletContextEvent sce) {
    log.info("DbInitListener: shutdown started");
    try {
      // Close DB pool first
      VDbConnectionManager.closeAllRealDbConnection();
    } catch (Throwable t) {
      log.error("DbInitListener: error closing DB connections", t);
    }
    try {
      HibernateUtil.done();
    } catch (Throwable t) {
      log.error("DbInitListener: error closing Hibernate", t);
    }
    log.info("DbInitListener: shutdown finished");
  }

  // Internal helpers

  private void ensureConfigInitialized(ServletContext context) {
    try {
      // Heuristic: if Config.getString doesn't throw, assume already initialized by ConfigInitListener
      Config.getString("global.encoding", "UTF-8");
      log.info("DbInitListener: Config already initialized");
    } catch (Throwable t) {
      log.warn("DbInitListener: Config was not initialized by ConfigInitListener, performing fallback init (dev-only)", t);
      try {
        String basePath = context.getRealPath("/");
        java.io.File path = new java.io.File(basePath, "..");
        basePath = path.getCanonicalPath();
        String configDir = context.getInitParameter("config-dir");
        if (StringUtil.isEmpty(configDir)) {
          configDir = basePath + java.io.File.separator + "conf" + java.io.File.separator;
        }
        String configFile = context.getInitParameter("config-file");
        if (StringUtil.isEmpty(configFile)) {
          configFile = "main.cfg";
        }
        ConfigImpl configImpl = ConfigImpl.getConfigImpl();
        configImpl.init(basePath);
        configImpl.addCfgFile(configDir + configFile);

        String cfgs = configImpl.getString("config.file");
        if (!StringUtil.isEmpty(cfgs)) {
          java.util.StringTokenizer tk = new java.util.StringTokenizer(cfgs, "|");
          while (tk.hasMoreTokens()) {
            String cfgFile = tk.nextToken();
            if (cfgFile.length() > 0) {
              configImpl.addCfgFile(configDir + cfgFile);
            }
          }
        }
        Config.init(configImpl);
        System.setProperty("file.encoding", Config.getString("global.encoding", "UTF-8"));
        log.info("DbInitListener: Config fallback init completed");
      } catch (Exception ex) {
        log.error("DbInitListener: Config fallback init failed", ex);
      }
    }
  }

  private void initDbPool() throws VDbException {
    log.info("DbInitListener: initializing DB pool...");
    String driverClass = Config.getString("dbconnect.driver.class.name");
    String vdbConnClass = Config.getString("dbconnect.connection.class.name");
    String url = Config.getString("dbconnect.connection.url");
    String user = Config.getString("dbconnect.user");
    String pwd = Config.getString("dbconnect.pwd");

    VDbConnectionManager.initAnyDriver(
        driverClass,
        StringUtil.isEmpty(vdbConnClass) ? null : vdbConnClass,
        url,
        user,
        pwd
    );

    int numInitialConnR = Config.getNumber("dbconnect.rConnectNumber", 2);
    int numMaxConnR = Config.getNumber("dbconnect.rConnectNumber", 2);
    int numInitialConnW = Config.getNumber("dbconnect.wConnectNumber", 9);
    int numMaxConnW = Config.getNumber("dbconnect.wConnectNumber", 9);
    int numInitialConnRep = Config.getNumber("dbconnect.repConnectNumber", 2);
    int numMaxConnRep = Config.getNumber("dbconnect.repConnectNumber", 2);

    DbReconnector.initDbPool(
        numInitialConnR, numMaxConnR,
        numInitialConnW, numMaxConnW,
        numInitialConnRep, numMaxConnRep
    );

    // Smoke test a connection so later requests do not trip the "not complete initialization"
    VDbConnection testConn = null;
    try {
      testConn = VDbConnectionManager.getVDbConnection();
      log.info("DbInitListener: DB connection smoke test OK");
    } finally {
      if (testConn != null) testConn.close();
    }
    log.info("DbInitListener: DB pool initialized");
  }

  private void loadApplicationResources(ServletContext context) {
    try {
      log.info("DbInitListener: loading XML resources (SQL)...");
      XMLResource sqlResource = new XMLResource(
          getClass().getClassLoader().getResource(Config.getString("global.sql-resources")).getFile()
      );
      sqlResource.load();
      StoreUtil.putApplication(context, sqlResource);
      log.info("DbInitListener: XML resources (SQL) loaded");
    } catch (Throwable t) {
      log.error("DbInitListener: failed to load SQL XMLResource", t);
    }

    try {
      log.info("DbInitListener: loading XML permissions...");
      XMLPermissions xmlPermissions = new XMLPermissions(
          getClass().getClassLoader().getResource(Config.getString("global.permission-resources")).getFile()
      );
      xmlPermissions.reload();
      StoreUtil.putApplication(context, xmlPermissions);
      log.info("DbInitListener: XML permissions loaded");
    } catch (Throwable t) {
      log.error("DbInitListener: failed to load XMLPermissions", t);
    }

    try {
      log.info("DbInitListener: loading slogans text resource...");
      TextResource slogans = new TextResource(Config.getString("global.slogan-file"));
      slogans.reload();
      StoreUtil.putApplication(context, slogans);
      log.info("DbInitListener: slogans loaded");
    } catch (Throwable t) {
      log.error("DbInitListener: failed to load slogans TextResource", t);
    }

    try {
      log.info("DbInitListener: loading control comments...");
      ControlComments cc = new ControlComments();
      cc.reload();
      StoreUtil.putApplication(context, cc);
      log.info("DbInitListener: control comments loaded");
    } catch (Throwable t) {
      log.error("DbInitListener: failed to load ControlComments", t);
    }

    try {
      log.info("DbInitListener: loading control actions...");
      ControlActions ca = new ControlActions();
      ca.reload();
      StoreUtil.putApplication(context, ca);
      log.info("DbInitListener: control actions loaded");
    } catch (Throwable t) {
      log.error("DbInitListener: failed to load ControlActions", t);
    }
  }
}