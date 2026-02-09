package net.sam.dcl.navigation;

import net.sam.dcl.beans.User;
import net.sam.dcl.util.StoreUtil;
import net.sam.dcl.util.UserUtil;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author: DG
 * Date: Aug 9, 2005
 * Time: 1:13:07 PM
 */

public class PermissionChecker
{
  static String trustedDirs;
  static ServletContext context;

  static public void init(String trustedDirs, ServletContext context)
  {
    PermissionChecker.trustedDirs = trustedDirs;
    PermissionChecker.context = context;
  }

  static public boolean isTrusted(ServletRequest req)
  {
    HttpServletRequest httpRequest = (HttpServletRequest) req;
    return httpRequest.getServletPath().matches(trustedDirs);
  }

  static public boolean isUserLogged(ServletRequest req)
  {
    return UserUtil.getCurrentUser((HttpServletRequest) req) != null;
  }

  static public boolean isUserLogged(HttpSession session)
  {
    return UserUtil.getCurrentUser(session) != null;
  }

  static public boolean checkURL(ServletRequest req, String url)
  {
    HttpServletRequest httpRequest = (HttpServletRequest) req;

    if (isTrusted(req))
    {
      return true;
    }
    if (!isUserLogged(req))
    {
      return true;
    }
    else
    {
      if (url.startsWith(httpRequest.getContextPath()))
      {
        url = url.substring(httpRequest.getContextPath().length());
      }
      if (url.indexOf(";jsessionid=") != -1)
      {
        int i1 = url.indexOf(";jsessionid=");
        int i2 = url.indexOf("?", i1);
        if (i2 == -1)
        {
          url = url.substring(0, i1);
        }
        else
        {
          url = url.substring(0, i1) + url.substring(i2);
        }
      }

      User user = UserUtil.getCurrentUser((HttpServletRequest) req);
      XMLPermissions xmlPermissions = (XMLPermissions) StoreUtil.getApplication(context, XMLPermissions.class);
      Action action = xmlPermissions.findAction(url);
      return null == action || action.CorrectRole(user.getRoles());

/*
      ControlActions actions = (ControlActions) StoreUtil.getApplication(context, ControlActions.class);
      if ( actions.isCheckAccess(url) )
      {
        User user = UserUtil.getCurrentUser((HttpServletRequest) req);
        return user.isHaveAccess(url);
      }
      else
      {
        return true;
      }
*/
    }
  }

  static public void clear()
  {
    context = null;
  }


}
