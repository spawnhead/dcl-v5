package net.sam.dcl.action;

import net.sam.dcl.controller.actions.DBTransactionAction;
import net.sam.dcl.controller.IDispatchable;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.form.ReputationForm;
import org.apache.struts.action.ActionForward;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class ReputationAction extends DBTransactionAction implements IDispatchable
{
  public ActionForward input(IActionContext context) throws Exception
  {
    return context.getMapping().getInputForward();
  }

  public ActionForward create(IActionContext context) throws Exception
  {
    return input(context);
  }

  public ActionForward edit(IActionContext context) throws Exception
  {
    DAOUtils.load(context, "reputation-load", null);
    ReputationForm form = (ReputationForm) context.getForm();
    form.getNumber().setNumber_str(form.getNumber().getNumber());
    return input(context);
  }

  public ActionForward process(IActionContext context) throws Exception
  {
    ReputationForm form = (ReputationForm) context.getForm();
    if (StringUtil.isEmpty(form.getRpt_id()))
    {
      DAOUtils.update(context, "reputation-insert", null);
    }
    else
    {
      DAOUtils.update(context, "reputation-update", null);
    }
    return context.getMapping().findForward("back");
  }

}
