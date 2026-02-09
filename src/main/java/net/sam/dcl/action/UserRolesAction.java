package net.sam.dcl.action;

import net.sam.dcl.controller.actions.DBAction;
import net.sam.dcl.controller.IDispatchable;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.form.*;
import net.sam.dcl.dao.RoleDAO;
import org.apache.struts.action.ActionForward;

public class UserRolesAction extends DBAction implements IDispatchable
{

  public ActionForward show(IActionContext context) throws Exception
  {
    UserRolesForm form = (UserRolesForm) context.getForm();
    DAOUtils.fillGrid(context, form.getGridOut(), "select-user-roles-out", UserRolesForm.User.class, null, null);
    DAOUtils.fillGrid(context, form.getGridIn(), "select-user-roles-in", UserRolesForm.User.class, null, null);
    form.setRole(RoleDAO.load(context, form.getRol_id()));
    return context.getMapping().findForward("form");
  }

  public ActionForward add(IActionContext context) throws Exception
  {
    UserRolesForm form = (UserRolesForm) context.getForm();
    if (form.getSelected_ids_in().getRecordList().size() != 0)
    {
      DAOUtils.update(context, "add-user-roles", null);
    }
    return show(context);
  }

  public ActionForward delete(IActionContext context) throws Exception
  {
    UserRolesForm form = (UserRolesForm) context.getForm();
    if (form.getSelected_ids_out().getRecordList().size() != 0)
    {
      DAOUtils.update(context, "delete-user-roles", null);
    }
    return show(context);
  }

  public ActionForward addAll(IActionContext context) throws Exception
  {
    DAOUtils.update(context, "add-user-roles-all", null);
    return show(context);
  }

  public ActionForward deleteAll(IActionContext context) throws Exception
  {
    DAOUtils.update(context, "delete-user-roles-all", null);
    return show(context);
  }
}
