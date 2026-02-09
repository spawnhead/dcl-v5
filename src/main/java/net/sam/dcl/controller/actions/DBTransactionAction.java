package net.sam.dcl.controller.actions;

import net.sam.dcl.controller.IProcessAfter;
import net.sam.dcl.controller.IProcessBefore;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.db.VDbException;
import net.sam.dcl.util.StrutsUtil;
import org.apache.struts.action.ActionForward;

/**
 * User: Dima
 * Date: Sep 29, 2004
 * Time: 5:01:29 PM
 */
public class DBTransactionAction extends DBAction implements IProcessBefore, IProcessAfter
{
  public ActionForward processBefore(IActionContext context) throws Exception
  {
    context.getConnection().beginTransaction();
    return null;
  }

  public ActionForward processAfter(IActionContext context, ActionForward forward) throws Exception
  {
    if (context.getConnection().isTransactionStarted())
      context.getConnection().commit();
    return forward;
  }

  public ActionForward processException(IActionContext context, ActionForward forward, Exception e)
  {
    forward = super.processException(context, forward, e);
    if (context.getConnection().isTransactionStarted())
    {
      try
      {
        context.getConnection().rollback();
      }
      catch (VDbException e1)
      {
        log.error(e.getMessage(), e);
        StrutsUtil.addError(context, "common.msg", e.getMessage(), e);
      }
    }
    return forward;
  }
}
