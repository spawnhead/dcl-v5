package net.sam.dcl.action;

import net.sam.dcl.controller.actions.DBAction;
import net.sam.dcl.controller.IDispatchable;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.util.StoreUtil;
import net.sam.dcl.util.UserUtil;
import net.sam.dcl.util.StrutsUtil;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.form.UserSettingForm;
import net.sam.dcl.beans.Setting;
import net.sam.dcl.beans.UserSetting;
import net.sam.dcl.beans.User;
import net.sam.dcl.beans.Constants;
import net.sam.dcl.dao.UserSettingDAO;
import org.apache.struts.action.ActionForward;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class UserSettingAction extends DBAction implements IDispatchable
{
  private void saveCurrentFormToBean(IActionContext context)
  {
    UserSettingForm form = (UserSettingForm) context.getForm();

    UserSetting userSetting = (UserSetting) StoreUtil.getSession(context.getRequest(), UserSetting.class);

    userSetting.setUst_id(form.getUst_id());
    userSetting.setUst_name(form.getUst_name());
    userSetting.setUst_description(form.getUst_description());
    userSetting.setUst_value(form.getUst_value());
    userSetting.setUst_value_extended(form.getUst_value_extended());
    userSetting.setUst_type(form.getUst_type());
    userSetting.setUst_action(form.getUst_action());

    if ( Setting.listActionType.equals(form.getUst_type()) )
    {
      userSetting.setUst_value(form.getUserSettingList().getId());
      userSetting.setUst_value_extended(form.getUserSettingList().getName());
    }

    StoreUtil.putSession(context.getRequest(), userSetting);
  }

  private void getCurrentFormFromBean(IActionContext context, UserSetting userSettingIn)
  {
    UserSettingForm form = (UserSettingForm) context.getForm();
    UserSetting userSetting;
    if (null != userSettingIn)
    {
      userSetting = userSettingIn;
    }
    else
    {
      userSetting = (UserSetting) StoreUtil.getSession(context.getRequest(), UserSetting.class);
    }

    if (null != userSetting)
    {
      form.setUst_id(userSetting.getUst_id());
      form.setUst_name(userSetting.getUst_name());
      form.setUst_description(userSetting.getUst_description());
      form.setUst_value(userSetting.getUst_value());
      form.setUst_value_extended(userSetting.getUst_value_extended());
      form.setUst_type(userSetting.getUst_type());
      form.setUst_action(userSetting.getUst_action());

      if ( Setting.listActionType.equals(form.getUst_type()) )
      {
        form.getUserSettingList().setId(userSetting.getUst_value());
        form.getUserSettingList().setName(userSetting.getUst_value_extended());
      }
    }
  }

  public ActionForward input(IActionContext context) throws Exception
  {
    return context.getMapping().getInputForward();
  }

  public ActionForward edit(IActionContext context) throws Exception
  {
    UserSettingForm form = (UserSettingForm) context.getForm();
    UserSetting userSetting = UserSettingDAO.load(context, form.getUst_id());

    StoreUtil.putSession(context.getRequest(), userSetting);
    getCurrentFormFromBean(context, userSetting);

    return input(context);
  }

  public ActionForward process(IActionContext context) throws Exception
  {
    UserSettingForm form = (UserSettingForm) context.getForm();
    String errMsg = "";
    if (Constants.userRefreshPeriod.equals(form.getUst_name()))
    {
      if ( Integer.valueOf(form.getUst_value()) < Constants.minUserRefreshPeriod )
      {
        errMsg = StrutsUtil.addDelimiter(errMsg);
        errMsg += StrutsUtil.getMessage(context, "error.userSetting.incorrectUserRefreshPeriod", String.valueOf(Constants.minUserRefreshPeriod));
      }
    }

    if ( !StringUtil.isEmpty(errMsg) )
    {
      StrutsUtil.addError(context, "errors.msg", errMsg, null);
      return input(context);
    }

    saveCurrentFormToBean(context);

    UserSetting userSetting = (UserSetting) StoreUtil.getSession(context.getRequest(), UserSetting.class);
    UserSettingDAO.save(context, userSetting);

    User user = UserUtil.getCurrentUserForModification(context.getRequest());
    user.setNewSettingValue(userSetting.getUst_name(), userSetting.getUst_value());

    return context.getMapping().findForward("back");
  }

}