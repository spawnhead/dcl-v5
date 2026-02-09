package net.sam.dcl.test;

import net.sam.dcl.controller.actions.DBAction;
import net.sam.dcl.controller.IDispatchable;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.util.DAOUtils;
import org.apache.struts.action.ActionForward;

public class TestHirGridAction extends DBAction implements IDispatchable
{
  public ActionForward show(IActionContext context) throws Exception
  {
    TestHirGridForm form = (TestHirGridForm) context.getForm();
    form.reset();
    DAOUtils.fillGrid(context, form.getGridOut(), "test-select-user-roles-out", TestHirGridForm.User.class, null, null);
    DAOUtils.fillGrid(context, form.getGridIn(), "test-select-user-roles-in", TestHirGridForm.User.class, null, null);
    return context.getMapping().findForward("form");
  }

  public ActionForward test(IActionContext context) throws Exception
  {
    return context.getMapping().findForward("form");
  }
}
