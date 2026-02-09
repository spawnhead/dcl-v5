package net.sam.dcl.util;

import net.sam.dcl.beans.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author: DG
 * Date: 21-Dec-2006
 * Time: 14:29:40
 */
public class UserUtil
{
  static ThreadLocal threadUser = new ThreadLocal();

  public static void setThreadUser(HttpServletRequest request)
  {
    User currentUser = getCurrentUser(request);
    assert threadUser.get() == null;
    threadUser.set(currentUser);
  }

  public static User getThreadUser()
  {
    return (User) threadUser.get();
  }

  public static void clearThreadUser()
  {
    threadUser.set(null);
  }

  static public User getCurrentUser(HttpServletRequest request)
  {
    return getCurrentUser(request.getSession());
  }

  static public User getCurrentUser(HttpSession session)
  {
    User user = (User) StoreUtil.getSession(session, User.class.getName());
    if (user == null)
    {
      return null;
    }
    return new User(user);
  }

  static public User getCurrentUserForModification(HttpServletRequest request)
  {
    return (User) StoreUtil.getSession(request, User.class);
  }
}
