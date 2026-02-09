package net.sam.dcl.action;

import net.sam.dcl.controller.actions.DBTransactionAction;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.App;
import net.sam.dcl.navigation.PermissionChecker;
import net.sam.dcl.util.StrutsUtil;
import net.sam.dcl.session.SessionBooking;
import net.sam.dcl.session.SessionBookingProcesor;
import org.apache.struts.action.ActionForward;

import javax.servlet.http.HttpSession;

/**
 * @author: DG
 * Date: 12.05.2006
 * Time: 18:59:30
 */
public class PrepareAppToShutdownAction extends DBTransactionAction {
  public static final String USER_MESSAGE = PrepareAppToShutdownAction.class+"__USER_MESSAGE__";
  public ActionForward execute(final IActionContext context) throws Exception {
    App.loginDisabled = true;
    SessionBooking.getSessionBooking().iterate(new SessionBookingProcesor() {
      public void process(HttpSession session) {
        try {
          if (PermissionChecker.isUserLogged(session)){
            session.setAttribute(USER_MESSAGE, StrutsUtil.getMessage(context,"adm_zone.close-window"));
          }
        } catch (Exception e) {
          log.error(e.getMessage(),e);
        }
      }
    });
    return context.getMapping().getInputForward();
  }
}
