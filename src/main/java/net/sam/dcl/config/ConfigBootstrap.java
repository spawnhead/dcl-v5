package net.sam.dcl.config;

import javax.servlet.ServletContext;
import java.io.File;

/**
 * Lightweight bootstrap to initialize Config from file without touching DB.
 * This avoids NPEs when filters/JSP access Config early in app startup.
 *
 * Resolution order:
 * 1) -Ddcl.conf=absolute\path\to\main.cfg (optional override)
 * 2) ${catalina.base}/conf/main.cfg
 * 3) ${user.dir}/conf/main.cfg
 *
 * Base path (for %HOME% in configs):
 * 1) -Ddcl.home=...
 * 2) ${catalina.base}
 * 3) ${user.dir}
 */
public final class ConfigBootstrap {
    private static volatile boolean initialized = false;

    private ConfigBootstrap() {}

    public static synchronized void ensureInitialized(ServletContext ctx) {
        if (initialized) {
            return;
        }

        // If already initialized, exit
        try {
            if (Config.getConfig() != null) {
                initialized = true;
                return;
            }
        } catch (Throwable ignore) {
            // not initialized yet
        }

        try {
            ConfigImpl impl = ConfigImpl.getConfigImpl();

            String home = System.getProperty("dcl.home");
            if (home == null || home.isEmpty()) {
                String catalinaBase = System.getProperty("catalina.base");
                if (catalinaBase != null && !catalinaBase.isEmpty()) {
                    home = catalinaBase;
                } else {
                    home = System.getProperty("user.dir", ".");
                }
            }
            impl.init(home.replace('\\', '/'));

            // Config file path resolution
            String mainCfg = System.getProperty("dcl.conf");
            if (mainCfg == null || mainCfg.isEmpty()) {
                // prefer catalina.base/conf/main.cfg if present
                File byCatalina = new File(home, "conf/main.cfg");
                mainCfg = byCatalina.getAbsolutePath();
            }

            // Load main.cfg if it exists; if not, continue with empty config (filters must handle defaults)
            try {
                File cfgFile = new File(mainCfg);
                if (cfgFile.isFile() && cfgFile.canRead()) {
                    impl.addCfgFile(cfgFile.getAbsolutePath());
                }
            } catch (Exception ignored) {
                // Continue with empty config; callers should use safe fallbacks
            }

            // Bind implementation
            Config.init(impl);
            initialized = true;
        } catch (Throwable t) {
            // As a last resort, bind an empty implementation to avoid NPEs
            try {
                Config.init(ConfigImpl.getConfigImpl());
            } catch (Throwable ignored) {
                // swallow
            }
            initialized = true;
        }
    }
}