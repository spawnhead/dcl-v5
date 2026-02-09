package net.sam.dcl.session;

import net.sam.dcl.util.StringUtil;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.ArrayList;
import java.util.Enumeration;

/**
 * @author: DG
 * Date: Jan 26, 2006
 * Time: 2:09:03 PM
 */
public class SessionBooking
{
  protected SessionBooking()
  {

  }

  List sessions = new ArrayList();

  static private SessionBooking sessionBooking = new SessionBooking();

  public static SessionBooking getSessionBooking()
  {
    return sessionBooking;
  }

  void add(HttpSession session)
  {
    synchronized (sessions)
    {
      sessions.add(session);
    }
  }

  void remove(HttpSession session)
  {
    synchronized (sessions)
    {
      sessions.remove(session);
    }
  }

  public HttpSession find(String id)
  {
    synchronized (sessions)
    {
      for (int i = 0; i < sessions.size(); i++)
      {
        HttpSession session = (HttpSession) sessions.get(i);
        if (StringUtil.equal(session.getId(), id))
        {
          return session;
        }
      }
      return null;
    }
  }

  public void iterate(SessionBookingProcesor processor) throws Exception
  {
    synchronized (sessions)
    {
      for (int i = 0; i < sessions.size(); i++)
      {
        processor.process((HttpSession) sessions.get(i));
      }
    }
  }

  public void clear()
  {
    synchronized (sessions)
    {
      for (int i = 0; i < sessions.size(); i++)
      {
        HttpSession session = (HttpSession) sessions.get(i);
        try
        {
          Enumeration<String> names = session.getAttributeNames();
          while (names.hasMoreElements())
          {
            String attr = names.nextElement();
            session.removeAttribute(attr);
          }
          session.invalidate();
        }
        catch (IllegalStateException e)
        {
        }
      }
      sessions.clear();
      sessionBooking = null;
    }

  }
}
