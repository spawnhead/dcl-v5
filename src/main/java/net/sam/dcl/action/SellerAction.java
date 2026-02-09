package net.sam.dcl.action;

import net.sam.dcl.controller.actions.DBTransactionAction;
import net.sam.dcl.controller.IDispatchable;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.taglib.table.IReadOnlyChecker;
import net.sam.dcl.taglib.table.ReadOnlyCheckerContext;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.form.SellerForm;
import org.apache.struts.action.ActionForward;

public class SellerAction extends DBTransactionAction implements IDispatchable
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
    DAOUtils.load(context, "seller-load", null);
    return input(context);
  }

  public ActionForward process(IActionContext context) throws Exception
  {
    SellerForm form = (SellerForm) context.getForm();
    if (StringUtil.isEmpty(form.getSln_id()))
    {
      DAOUtils.update(context, "seller-insert", null);
    }
    else
    {
      DAOUtils.update(context, "seller-update", null);
    }
    return context.getMapping().findForward("back");
  }

}