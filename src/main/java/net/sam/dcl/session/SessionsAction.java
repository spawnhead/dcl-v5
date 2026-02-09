package net.sam.dcl.session;

import net.sam.dcl.controller.actions.DBTransactionAction;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.navigation.PermissionChecker;
import org.apache.struts.action.ActionForward;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.ArrayList;

/**
 * @author: DG
 * Date: Jan 26, 2006
 * Time: 2:30:43 PM
 */
public class SessionsAction extends DBTransactionAction
{
  public ActionForward execute(IActionContext context) throws Exception
  {
    final List list = new ArrayList();
    SessionBooking.getSessionBooking().iterate(new SessionBookingProcesor()
    {
      public void process(HttpSession session)
      {
        try
        {
          if (PermissionChecker.isUserLogged(session))
          {
            list.add(new SessionsForm.SessionRecord(session));
          }
        }
        catch (IllegalStateException e)
        {
        }
      }
    });
    SessionsForm form = (SessionsForm) context.getForm();
    form.getGrid().setDataList(list);
    return context.getMapping().getInputForward();
  }
}
