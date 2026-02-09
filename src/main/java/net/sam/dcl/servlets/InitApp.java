package net.sam.dcl.servlets;

import net.sam.dcl.config.Config;
import net.sam.dcl.config.ConfigImpl;
import net.sam.dcl.db.VDbConnection;
import net.sam.dcl.db.VDbConnectionManager;
import net.sam.dcl.db.VDbException;
import net.sam.dcl.tasks.EveryDayTask;
import net.sam.dcl.util.*;
import net.sam.dcl.navigation.XMLPermissions;
import net.sam.dcl.navigation.PermissionChecker;
import net.sam.dcl.navigation.ControlComments;
import net.sam.dcl.navigation.ControlActions;
import net.sam.dcl.session.SessionBooking;
import net.sam.dcl.action.Outline;
import net.sam.dcl.locking.LockedRecords;
import net.sam.dcl.finalizer.FinalizerThread;
import net.sam.dcl.beans.Constants;
import net.sam.dcl.dao.EveryDayTaskDAO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Properties;
import java.util.StringTokenizer;

import com.lowagie.text.FontFactory;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

/**
 * User: Dima
 * Date: Sep 29, 2004
 * Time: 7:03:12 PM
 */
public class InitApp extends HttpServlet
{
  protected static Log log = LogFactory.getLog(InitApp.class);
  public static final String SQL_RESOURCES = "sql-resources";
  public static final String XML_PERMISSIONS = "xml-permisions";
  private Scheduler scheduler = null;

  ServletConfig config = null;

  public void init(ServletConfig config) throws ServletException
  {
    super.init(config);
    this.config = config;
    initApp(config.getServletContext());
  }

  public void destroy()
  {
    shutdownApp(config.getServletContext());
  }

  public void initApp(ServletContext context) throws ServletException
  {
    log.info("Application init started...");

    log.info("Config init...");
    String basePath = context.getRealPath("/");
    File path = new File(basePath, "..");
    try
    {
      basePath = path.getCanonicalPath();
    }
    catch (IOException e)
    {
      processError("Can't get base path", e);
    }
    String configDir = context.getInitParameter("config-dir");
    if (StringUtil.isEmpty(configDir))
    {
      configDir = basePath + File.separator + "conf" + File.separator;
    }
    String configFile = context.getInitParameter("config-file");
    if (StringUtil.isEmpty(configFile))
    {
      configFile = "main.cfg";
    }
    ConfigImpl configImpl = ConfigImpl.getConfigImpl();
    configImpl.init(basePath);
    configImpl.addCfgFile(configDir + configFile);


    StringTokenizer cfgFileTokenizer = new StringTokenizer(configImpl.getString("config.file"), "|");
    while (cfgFileTokenizer.hasMoreTokens())
    {
      String cfgFile = cfgFileTokenizer.nextToken();
      if (cfgFile.length() > 0)
        configImpl.addCfgFile(configDir + cfgFile);
    }

    Config.init(configImpl);
    System.setProperty("file.encoding", Config.getString("global.encoding"));
    log.info("Config inited");


    log.info("DB pool init...");
    VDbConnection conn = null;
    try
    {
      String connectionClassName = Config.getString("dbconnect.connection.class.name");
      VDbConnectionManager.initAnyDriver(Config.getString("dbconnect.driver.class.name"),
              StringUtil.isEmpty(connectionClassName) ? null : connectionClassName,
              Config.getString("dbconnect.connection.url"),
              Config.getString("dbconnect.user"),
              Config.getString("dbconnect.pwd"));

      int numInitialConnR = Config.getNumber("dbconnect.rConnectNumber", 2);
      int numMaxConnR = Config.getNumber("dbconnect.rConnectNumber", 2);
      int numInitialConnW = Config.getNumber("dbconnect.wConnectNumber", 9);
      int numMaxConnW = Config.getNumber("dbconnect.rConnectNumber", 9);
      int numInitialConnRep = Config.getNumber("dbconnect.repConnectNumber", 2);
      int numMaxConnRep = Config.getNumber("dbconnect.repConnectNumber", 2);
      DbReconnector.initDbPool(numInitialConnR, numMaxConnR, numInitialConnW, numMaxConnW, numInitialConnRep, numMaxConnRep);

      conn = VDbConnectionManager.getVDbConnection();

      log.info("DB pool inited");
    }
    catch (VDbException e)
    {
      processError("Error occured while initializing connections to DB: ", e);
    }
    finally
    {
      if (conn != null) conn.close();
    }
    log.info("Hibernate init...");

    try
    {
      HibernateUtil.init();
      log.info("Hibernate inited");
    }
    catch (Exception e)
    {
      processError("Error occured while initializing Hibernate: ", e);
    }
    finally
    {
      if (conn != null) conn.close();
    }

    log.info("Have DB connect -> reloading config data from DB...");
    Config.reload();
    log.info("Config data from DB reloaded");

    log.info("XMLResurces for SQL init...");
    XMLResource sqlResource = new XMLResource(getClass().getClassLoader().getResource(Config.getString("global.sql-resources")).getFile());
    sqlResource.load();
    StoreUtil.putApplication(context, sqlResource);
    log.info("XMLResurces for SQL inited");

    log.info("XMLResurces for Permission init...");
    XMLPermissions xmlPermissions = new XMLPermissions(getClass().getClassLoader().getResource(Config.getString("global.permission-resources")).getFile());
    xmlPermissions.reload();
    StoreUtil.putApplication(context, xmlPermissions);
    log.info("XMLResurces for Permission inited");

    log.info("TextResurces for slogans init...");
    TextResource slogans = new TextResource(Config.getString("global.slogan-file"));
    slogans.reload();
    StoreUtil.putApplication(context, slogans);
    log.info("TextResurces for slogans inited");

    log.info("Font registration started...");
    String fontDir = Config.getString("font.dir");
    if (StringUtil.isEmpty(fontDir))
    {
      FontFactory.registerDirectories();
    }
    else
    {
      FontFactory.registerDirectory(fontDir);
    }
    log.info("Font registration done");


    log.info("Context help loading started...");
    ControlComments controlComments = new ControlComments();
    controlComments.reload();
    StoreUtil.putApplication(context, controlComments);
    log.info("Context help loading done");

    
    log.info("Actions list loading started...");
    ControlActions controlActions = new ControlActions();
    controlActions.reload();
    StoreUtil.putApplication(context, controlActions);
    log.info("Actions list loading done");


    FinalizerThread.get().start();
    log.info("Finalizer thread started");

    try
    {
      EveryDayTaskDAO.closeReservedInCommercialProposal();
    }
    catch (Exception e)
    {
      log.error(e);
    }

    try
    {
      Properties schedProperties = new Properties();
      schedProperties.setProperty("org.quartz.jobStore.class", "org.quartz.simpl.RAMJobStore");
      schedProperties.setProperty("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
      schedProperties.setProperty("org.quartz.threadPool.threadCount", "3");
      StdSchedulerFactory schedFact = new StdSchedulerFactory();
      schedFact.initialize(schedProperties);
      scheduler = schedFact.getScheduler();
      scheduler.start();
      //start copy here
      String schedule = Config.getString(Constants.runEveryDayTaskCronScheduler);
      if (schedule != null && schedule.length() != 0)
      {
        JobDetail jobDetail = JobBuilder.newJob(EveryDayTask.class)
                .withIdentity("EveryDayTask", scheduler.DEFAULT_GROUP)
                .build();
        CronTrigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("EveryDayTaskTrigger", scheduler.DEFAULT_GROUP)
                .withSchedule(org.quartz.CronScheduleBuilder.cronSchedule(schedule))
                .build();
        scheduler.scheduleJob(jobDetail, trigger);
        log.info("SCHEDULE: EveryDayTask added to schedule");
      }
      //finish copy here
    }
    catch (SchedulerException e)
    {
      log.error("SCHEDULE:" + e.getMessage());
    }
    catch (ParseException e)
    {
      log.error("SCHEDULE:" + e.getMessage());
    }

    log.info("Application init finished");
  }

  public void shutdownApp(ServletContext context)
  {
    try
    {
      log.info("Application shutdown started");


    if (scheduler != null) {
      try {
        scheduler.shutdown();
      } catch (SchedulerException e) {
        log.error("SCHEDULE:" + e.getMessage(), e);
      }
    }
      log.info("XMLResurces for SQL stop...");
      XMLResource sqlResource = (XMLResource) StoreUtil.getApplication(context, XMLResource.class);
      sqlResource.clear();
      log.info("XMLResurces for SQL stopeded");

      log.info("DB pool stop...");
      VDbConnectionManager.closeAllRealDbConnection();
      log.info("DB pool stoped");

      log.info("Hibernate stop...");
      HibernateUtil.done();
      log.info("Hibernate stoped");

      PermissionChecker.clear();
      SessionBooking.getSessionBooking().clear();
      LockedRecords.getLockedRecords().clear();
      Config.clear();
      Outline.clearOutline();

      FinalizerThread.get().join(100);
      FinalizerThread.get().stopExecution();
      log.info("Finalizer Thread stopped");

      log.info("Application shutdown finished");
    }
    catch (Exception e)
    {
      log.error(e.getMessage(), e);
    }
  }

  void processError(String msg, Exception e) throws ServletException
  {
    log.error(msg, e);
    throw new ServletException(msg, e);
  }

}
