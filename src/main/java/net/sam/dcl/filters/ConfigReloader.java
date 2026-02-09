package net.sam.dcl.filters;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import net.sam.dcl.config.Config;
import net.sam.dcl.util.StoreUtil;
import net.sam.dcl.util.XMLResource;
import net.sam.dcl.util.TextResource;
import net.sam.dcl.util.classloading.AlwaysLoadClassLoader;
import net.sam.dcl.navigation.XMLPermissions;

/**
 * @author: DG
 * Date: Aug 9, 2005
 * Time: 1:13:07 PM
 */

public class ConfigReloader implements Filter {
  protected static Log log = LogFactory.getLog(ConfigReloader.class);
  FilterConfig config = null;
  public void destroy() {
  }

  public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

		Config.reload();
    XMLResource sqlResource = (XMLResource) StoreUtil.getApplication(config.getServletContext(), XMLResource.class);
    sqlResource.reload();
    XMLPermissions xmlPermissions = (XMLPermissions) StoreUtil.getApplication(config.getServletContext(), XMLPermissions.class);
    xmlPermissions.reload();
		TextResource slogan = (TextResource) StoreUtil.getApplication(config.getServletContext(),TextResource.class);
		slogan.reload();
		chain.doFilter(req,resp);
	}

  public void init(FilterConfig config) throws ServletException {
    this.config = config;
  }

}
