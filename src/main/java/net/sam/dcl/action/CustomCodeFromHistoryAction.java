package net.sam.dcl.action;

import net.sam.dcl.controller.actions.DBTransactionAction;
import net.sam.dcl.controller.IDispatchable;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.UserUtil;
import net.sam.dcl.form.CustomCodeForm;
import net.sam.dcl.beans.User;
import org.apache.struts.action.ActionForward;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class CustomCodeFromHistoryAction extends DBTransactionAction implements IDispatchable
{
  public ActionForward input(IActionContext context) throws Exception
  {
    return context.getMapping().getInputForward();
  }

  public ActionForward create(IActionContext context) throws Exception
  {
    CustomCodeForm form = (CustomCodeForm) context.getForm();
    form.setCus_instant(StringUtil.date2appDateString(StringUtil.getCurrentDateTime()));
    form.setReadonly_code(true);
    form.setNewCus(true);
    DAOUtils.load(context, "custom_code-load-by-code", null);
    return input(context);
  }

  public ActionForward edit(IActionContext context) throws Exception
  {
    CustomCodeForm form = (CustomCodeForm) context.getForm();
    form.setReadonly_code(true);
    DAOUtils.load(context, "custom_code-load", null);
    form.setCus_instant(StringUtil.dbTimestampString2appDateString(form.getCus_instant()));
    form.setNewCus(false);
    return input(context);
  }

  public ActionForward process(IActionContext context) throws Exception
  {
    CustomCodeForm form = (CustomCodeForm) context.getForm();
    User user = UserUtil.getCurrentUser(context.getRequest());
    form.setEditUser(user);
    if (StringUtil.isEmpty(form.getCus_id()))
    {
      form.setCreateUser(user);
      DAOUtils.update(context, "custom_code-insert", null);
    }
    else
    {
      DAOUtils.update(context, "custom_code-update-percent-instant", null);
    }
    return context.getMapping().findForward("back");
  }

}
