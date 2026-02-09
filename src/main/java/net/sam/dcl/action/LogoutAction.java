package net.sam.dcl.action;

import net.sam.dcl.controller.actions.DBTransactionAction;
import net.sam.dcl.controller.IDispatchable;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.navigation.DbLogging;
import org.apache.struts.action.ActionForward;

public class LogoutAction extends DBTransactionAction implements IDispatchable
{
  public ActionForward input(IActionContext context) throws Exception
  {
    context.getRequest().getSession().invalidate();
    DbLogging.logAction(context, DbLogging.logoutId, context.getRequest().getRemoteAddr());
    return new ActionForward("/trusted/Login.do?dispatch=input");
  }
}
