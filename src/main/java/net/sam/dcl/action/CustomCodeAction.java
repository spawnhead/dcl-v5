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
public class CustomCodeAction extends DBTransactionAction implements IDispatchable
{

  public ActionForward input(IActionContext context) throws Exception
  {
    return context.getMapping().getInputForward();
  }

  public ActionForward create(IActionContext context) throws Exception
  {
    CustomCodeForm form = (CustomCodeForm) context.getForm();
    form.setCus_instant(StringUtil.date2appDateString(StringUtil.getCurrentDateTime()));
    form.setNewCus(true);
    form.setShowCreateEditUsers(true);
    return input(context);
  }

  public ActionForward edit(IActionContext context) throws Exception
  {
    CustomCodeForm form = (CustomCodeForm) context.getForm();
    form.setHide_percent(true);
    DAOUtils.load(context, "custom_code-load-by-code", null);
    form.setCus_code_old(form.getCus_code());
    form.setNewCus(true);
    form.setShowCreateEditUsers(true);
    return input(context);
  }

  public ActionForward process(IActionContext context) throws Exception
  {
    CustomCodeForm form = (CustomCodeForm) context.getForm();
    User user = UserUtil.getCurrentUser(context.getRequest());
    form.setEditUser(user);
    form.setEditUserMain(user);
    form.setUsr_date_edit_main(StringUtil.date2dbDateString(StringUtil.getCurrentDateTime()));
    if (form.isHide_percent())
    {
      DAOUtils.update(context, "custom_code-update-code-description", null);
    }
    else
    {
      if (CustomCodeDAO.checkByCode(context, new CustomCode(form.getCus_code())))
      {
        StrutsUtil.addError(context, "error.custom_code.duplicate", null);
        return input(context);
      }
      form.setCreateUser(user);
      form.setCreateUserMain(user);
      form.setUsr_date_create_main(StringUtil.date2dbDateString(StringUtil.getCurrentDateTime()));
      DAOUtils.update(context, "custom_code-insert", null);
    }
    return context.getMapping().findForward("back");
  }

}
