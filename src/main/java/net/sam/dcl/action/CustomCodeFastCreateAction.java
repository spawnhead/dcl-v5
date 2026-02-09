package net.sam.dcl.action;

import net.sam.dcl.controller.actions.DBTransactionAction;
import net.sam.dcl.controller.IDispatchable;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.StrutsUtil;
import net.sam.dcl.util.UserUtil;
import net.sam.dcl.form.CustomCodeForm;
import net.sam.dcl.dao.CustomCodeDAO;
import net.sam.dcl.beans.CustomCode;
import net.sam.dcl.beans.User;
import org.apache.struts.action.ActionForward;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class CustomCodeFastCreateAction extends DBTransactionAction implements IDispatchable
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
    DAOUtils.load(context, "custom_code-load-by-code", null);
    return input(context);
  }

  public ActionForward process(IActionContext context) throws Exception
  {
    CustomCodeForm form = (CustomCodeForm) context.getForm();
    User user = UserUtil.getCurrentUser(context.getRequest());
    form.setEditUser(user);
    form.setCreateUser(user);
    if (CustomCodeDAO.checkByCodeInstant(context, new CustomCode(form.getCus_id(), form.getCus_code(), form.getCus_instant_ts())))
    {
      StrutsUtil.addError(context, "error.custom_code.duplicate_instant", null);
      return input(context);
    }
    DAOUtils.update(context, "custom_code-insert", null);
    return context.getMapping().findForward("back");
  }

}
