package net.sam.dcl.config;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Initializes configuration at webapp startup so JSPs/filters can safely call Config.get*.
 */
public class ConfigInitListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ConfigBootstrap.ensureInitialized(sce.getServletContext());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // no-op
    }
}