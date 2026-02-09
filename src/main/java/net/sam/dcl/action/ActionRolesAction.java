package net.sam.dcl.action;

import net.sam.dcl.controller.actions.DBAction;
import net.sam.dcl.controller.IDispatchable;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.form.*;
import net.sam.dcl.dao.ActionDAO;
import org.apache.struts.action.ActionForward;

public class ActionRolesAction extends DBAction implements IDispatchable
{
  public ActionForward show(IActionContext context) throws Exception
  {
    ActionRolesForm form = (ActionRolesForm) context.getForm();
    DAOUtils.fillGrid(context, form.getGridOut(), "select-action-roles-out", ActionRolesForm.Role.class, null, null);
    DAOUtils.fillGrid(context, form.getGridIn(), "select-action-roles-in", ActionRolesForm.Role.class, null, null);
    form.setAction(ActionDAO.load(context, form.getAct_id()));
    return context.getMapping().findForward("form");
  }

  public ActionForward add(IActionContext context) throws Exception
  {
    ActionRolesForm form = (ActionRolesForm) context.getForm();
    if (form.getSelected_ids_in().getRecordList().size() != 0)
    {
      DAOUtils.update(context, "add-action-roles", null);
    }
    return show(context);
  }

  public ActionForward delete(IActionContext context) throws Exception
  {
    ActionRolesForm form = (ActionRolesForm) context.getForm();
    if (form.getSelected_ids_out().getRecordList().size() != 0)
    {
      DAOUtils.update(context, "delete-action-roles", null);
    }
    return show(context);
  }

  public ActionForward addAll(IActionContext context) throws Exception
  {
    DAOUtils.update(context, "add-action-roles-all", null);
    return show(context);
  }

  public ActionForward deleteAll(IActionContext context) throws Exception
  {
    DAOUtils.update(context, "delete-action-roles-all", null);
    return show(context);
  }
}