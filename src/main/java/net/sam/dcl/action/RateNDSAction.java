package net.sam.dcl.action;

import net.sam.dcl.controller.actions.DBTransactionAction;
import net.sam.dcl.controller.IDispatchable;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.form.RateNDSForm;
import org.apache.struts.action.ActionForward;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class RateNDSAction extends DBTransactionAction implements IDispatchable
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
    DAOUtils.load(context, "rate_nds-load", null);
    return input(context);
  }

  public ActionForward process(IActionContext context) throws Exception
  {
    RateNDSForm form = (RateNDSForm) context.getForm();
    if (StringUtil.isEmpty(form.getRtn_id()))
    {
      DAOUtils.update(context, "rate_nds-insert", null);
    }
    else
    {
      DAOUtils.update(context, "rate_nds-update", null);
    }
    return context.getMapping().findForward("back");
  }

}