package net.sam.dcl.action;

import net.sam.dcl.controller.actions.DBAction;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.form.RolesForm;
import org.apache.struts.action.ActionForward;

public class RolesAction extends DBAction
{
  public ActionForward execute(IActionContext context) throws Exception
  {
    RolesForm form = (RolesForm) context.getForm();
    DAOUtils.fillGrid(context, form.getGrid(), "select-roles", RolesForm.Role.class, null, null);
    return context.getMapping().getInputForward();
  }

}
