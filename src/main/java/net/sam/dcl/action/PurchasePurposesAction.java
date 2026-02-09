package net.sam.dcl.action;

import net.sam.dcl.controller.actions.DBAction;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.controller.IDispatchable;
import net.sam.dcl.form.PurchasePurposesForm;
import net.sam.dcl.util.DAOUtils;
import org.apache.struts.action.ActionForward;

public class PurchasePurposesAction extends DBAction implements IDispatchable
{
  public ActionForward execute(IActionContext context) throws Exception
  {
    PurchasePurposesForm form = (PurchasePurposesForm) context.getForm();
    DAOUtils.fillGrid(context, form.getGrid(), "select-purchase_purposes", PurchasePurposesForm.PurchasePurpose.class, null, null);

    return context.getMapping().getInputForward();
  }

  public ActionForward edit(IActionContext context) throws Exception
  {
    return context.getMapping().findForward("edit");
  }
}