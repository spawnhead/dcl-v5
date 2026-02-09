package net.sam.dcl.filters;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import net.sam.dcl.beans.User;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.UserUtil;
import net.sam.dcl.config.Config;

/**
 * @author: DG
 * Date: Aug 9, 2005
 * Time: 1:13:07 PM
 */

public class RequestResponseSetupFilter implements Filter {
  protected static Log log = LogFactory.getLog(RequestResponseSetupFilter.class);
  FilterConfig config = null;
  static String requestEncoding = "UTF8";
  static String responseEncoding = "UTF8";

  public void destroy() {
  }
/* todo dfdf	*/
  public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
    long startTime = System.currentTimeMillis();
    HttpServletRequest servletRequest = (HttpServletRequest) req;
    log.info("Start[" + servletRequest.getRequestURL() + ","+servletRequest.getSession().getId()+"]");
		String uri = servletRequest.getRequestURI().substring(servletRequest.getContextPath().length(),servletRequest.getRequestURI().length());
		if (!StringUtil.isEmpty(servletRequest.getQueryString())){
			uri +="?"+servletRequest.getQueryString();
		}
		if (!uri.matches(Config.getString("encodingFilter.exclude"))){
			req.setCharacterEncoding(getRequestEncoding());
			resp.setCharacterEncoding(getResponseEncoding());
		}
		if (uri.matches(Config.getString("encodingFilter.setCache"))){
			HttpServletResponse servletResponse = (HttpServletResponse) resp;
			servletResponse.setHeader("Cache-Control","post-check=3600,pre-check=43200");
		}
    chain.doFilter(req, resp);
		log.info("Stop [" + servletRequest.getRequestURL() + ","+servletRequest.getSession().getId()+"]:" + (System.currentTimeMillis() - startTime));
    User user = UserUtil.getCurrentUser(servletRequest);
    if (user!=null && StringUtil.isEmpty(user.getUsr_id())){
      log.error("Empty user id[" + servletRequest.getRequestURL() + ","+servletRequest.getSession().getId()+ "]:");
    }
	}

  public void init(FilterConfig config) throws ServletException {
    setRequestEncoding(config.getInitParameter("request-encoding"));
    setResponseEncoding(config.getInitParameter("response-encoding"));
  }
  public static String getRequestEncoding() {
    return requestEncoding;
  }
  public static void setRequestEncoding(String requestEncoding) {
    RequestResponseSetupFilter.requestEncoding = requestEncoding;
  }
  public static String getResponseEncoding() {
    return responseEncoding;
  }
  public static void setResponseEncoding(String responseEncoding) {
    RequestResponseSetupFilter.responseEncoding = responseEncoding;
  }
}
