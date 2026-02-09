package net.sam.dcl.action;

import net.sam.dcl.controller.actions.DBAction;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.form.PurposesForm;
import net.sam.dcl.util.DAOUtils;
import org.apache.struts.action.ActionForward;

public class PurposesAction extends DBAction
{
  public ActionForward execute(IActionContext context) throws Exception
  {
    PurposesForm form = (PurposesForm) context.getForm();
    DAOUtils.fillGrid(context, form.getGrid(), "select-purposes", PurposesForm.Purpose.class, null, null);

    return context.getMapping().getInputForward();
  }
}
