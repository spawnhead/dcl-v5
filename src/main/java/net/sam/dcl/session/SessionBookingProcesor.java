package net.sam.dcl.session;

import javax.servlet.http.HttpSession;

/**
 * @author: DG
 * Date: Jan 26, 2006
 * Time: 2:22:36 PM
 */
public interface SessionBookingProcesor {
  void process(HttpSession session);
}
