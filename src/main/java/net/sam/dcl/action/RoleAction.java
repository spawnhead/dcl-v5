package net.sam.dcl.action;

import net.sam.dcl.controller.actions.DBTransactionAction;
import net.sam.dcl.controller.IDispatchable;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.form.RoleForm;
import org.apache.struts.action.ActionForward;

public class RoleAction extends DBTransactionAction implements IDispatchable
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
    DAOUtils.load(context, "role-load", null);
    return input(context);
  }

  public ActionForward process(IActionContext context) throws Exception
  {
    RoleForm form = (RoleForm) context.getForm();
    if (StringUtil.isEmpty(form.getRol_id()))
    {
      DAOUtils.update(context, "role-insert", null);
    }
    else
    {
      DAOUtils.update(context, "role-update", null);
    }
    return context.getMapping().findForward("back");
  }

}