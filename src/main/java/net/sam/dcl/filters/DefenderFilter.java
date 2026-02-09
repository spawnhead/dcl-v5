package net.sam.dcl.filters;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import net.sam.dcl.navigation.PermissionChecker;

/**
 * @author: DG
 * Date: Aug 9, 2005
 * Time: 1:13:07 PM
 */

public class DefenderFilter implements Filter
{
  protected static Log log = LogFactory.getLog(DefenderFilter.class);
  FilterConfig config = null;

  public void doFilter(ServletRequest request, ServletResponse resp, FilterChain chain) throws ServletException, IOException
  {
    String loginPage = config.getInitParameter("login-page");
    if (PermissionChecker.isTrusted(request))
    {
      chain.doFilter(request, resp);
      if (!PermissionChecker.isUserLogged(request))
      {
        ((HttpServletRequest) request).getSession().setMaxInactiveInterval(1);
      }
      return;
    }

    if (!PermissionChecker.isUserLogged(request))
    {
      RequestDispatcher rd = request.getRequestDispatcher(loginPage);
      rd.forward(request, resp);
    }
    else
    {
      HttpServletRequest httpRequest = (HttpServletRequest) request;
      String withParameter = httpRequest.getQueryString();
      withParameter = httpRequest.getServletPath() + "?" + withParameter;
      if (PermissionChecker.checkURL(request, withParameter))
      {
        chain.doFilter(request, resp);
      }
      else
      {
        String noPermissionPage = config.getInitParameter("forward-no-perm-page");
        RequestDispatcher rd = request.getRequestDispatcher(noPermissionPage);
        rd.forward(request, resp);
      }
    }
  }

  public void init(FilterConfig config) throws ServletException
  {
    this.config = config;
    PermissionChecker.init(config.getInitParameter("trusted-dirs"), config.getServletContext());
  }

  public void destroy()
  {

  }

}
