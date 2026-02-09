package net.sam.dcl.session;

import net.sam.dcl.locking.LockedRecords;

import javax.servlet.http.HttpSessionListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author: DG
 * Date: Jan 26, 2006
 * Time: 1:14:42 PM
 */
public class SessionListener implements HttpSessionListener {
  protected static Log log = LogFactory.getLog(SessionListener.class);

  public void sessionCreated(HttpSessionEvent httpSessionEvent) {
    SessionBooking.getSessionBooking().add(httpSessionEvent.getSession());
  }
  public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
    HttpSession session = httpSessionEvent.getSession();
    LockedRecords lockedRecords = LockedRecords.getLockedRecords();
    lockedRecords.unlockWithTheSame(session.getId());
    SessionBooking.getSessionBooking().remove(httpSessionEvent.getSession());
  }
}
