package net.sam.dcl.action;

import net.sam.dcl.controller.actions.DBTransactionAction;
import net.sam.dcl.controller.IDispatchable;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.form.StuffCategoryForm;
import org.apache.struts.action.ActionForward;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class StuffCategoryAction extends DBTransactionAction implements IDispatchable
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
    DAOUtils.load(context, "stuff_category-load", null);
    return input(context);
  }

  public ActionForward process(IActionContext context) throws Exception
  {
    StuffCategoryForm form = (StuffCategoryForm) context.getForm();
    if (StringUtil.isEmpty(form.getStf_id()))
    {
      DAOUtils.update(context, "stuff_category-insert", null);
    }
    else
    {
      DAOUtils.update(context, "stuff_category-update", null);
    }
    return context.getMapping().findForward("back");
  }

}
