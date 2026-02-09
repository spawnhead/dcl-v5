package net.sam.dcl.action;

import net.sam.dcl.controller.actions.DBTransactionAction;
import net.sam.dcl.controller.IDispatchable;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.form.LanguageForm;
import org.apache.struts.action.ActionForward;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class LanguageAction extends DBTransactionAction implements IDispatchable
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
    DAOUtils.load(context, "language-load", null);
    return input(context);
  }

  public ActionForward process(IActionContext context) throws Exception
  {
    LanguageForm form = (LanguageForm) context.getForm();
    if (StringUtil.isEmpty(form.getLng_id()))
    {
      DAOUtils.update(context, "language-insert", null);
    }
    else
    {
      DAOUtils.update(context, "language-update", null);
    }
    return context.getMapping().findForward("back");
  }

}
