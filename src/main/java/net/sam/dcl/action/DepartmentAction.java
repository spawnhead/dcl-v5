package net.sam.dcl.action;

import net.sam.dcl.controller.actions.DBTransactionAction;
import net.sam.dcl.controller.IDispatchable;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.form.DepartmentForm;
import org.apache.struts.action.ActionForward;

public class DepartmentAction extends DBTransactionAction implements IDispatchable
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
    DAOUtils.load(context, "department-load", null);
    return input(context);
  }

  public ActionForward process(IActionContext context) throws Exception
  {
    DepartmentForm form = (DepartmentForm) context.getForm();
    if (StringUtil.isEmpty(form.getDep_id()))
    {
      DAOUtils.update(context, "department-insert", null);
    }
    else
    {
      DAOUtils.update(context, "department-update", null);
    }
    return context.getMapping().findForward("back");
  }

}
