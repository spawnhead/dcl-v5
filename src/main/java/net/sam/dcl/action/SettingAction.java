package net.sam.dcl.action;

import net.sam.dcl.controller.actions.DBAction;
import net.sam.dcl.controller.IDispatchable;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.util.StoreUtil;
import net.sam.dcl.form.SettingForm;
import net.sam.dcl.beans.Setting;
import net.sam.dcl.dao.SettingDAO;
import net.sam.dcl.config.Config;
import org.apache.struts.action.ActionForward;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class SettingAction extends DBAction implements IDispatchable
{
  private void saveCurrentFormToBean(IActionContext context)
  {
    SettingForm form = (SettingForm) context.getForm();

    Setting setting = (Setting) StoreUtil.getSession(context.getRequest(), Setting.class);

    setting.setStn_id(form.getStn_id());
    setting.setStn_name(form.getStn_name());
    setting.setStn_description(form.getStn_description());
    setting.setStn_value(form.getStn_value());
    setting.setStn_value_extended(form.getStn_value_extended());
    setting.setStn_type(form.getStn_type());
    setting.setStn_action(form.getStn_action());

    if ( Setting.listActionType.equals(form.getStn_type()) )
    {
      setting.setStn_value(form.getSettingList().getId());
      setting.setStn_value_extended(form.getSettingList().getName());
    }

    StoreUtil.putSession(context.getRequest(), setting);
  }

  private void getCurrentFormFromBean(IActionContext context, Setting settingIn)
  {
    SettingForm form = (SettingForm) context.getForm();
    Setting setting;
    if (null != settingIn)
    {
      setting = settingIn;
    }
    else
    {
      setting = (Setting) StoreUtil.getSession(context.getRequest(), Setting.class);
    }

    if (null != setting)
    {
      form.setStn_id(setting.getStn_id());
      form.setStn_name(setting.getStn_name());
      form.setStn_description(setting.getStn_description());
      form.setStn_value(setting.getStn_value());
      form.setStn_value_extended(setting.getStn_value_extended());
      form.setStn_type(setting.getStn_type());
      form.setStn_action(setting.getStn_action());

      if ( Setting.listActionType.equals(form.getStn_type()) )
      {
        form.getSettingList().setId(setting.getStn_value());
        form.getSettingList().setName(setting.getStn_value_extended());
      }
    }
  }

  public ActionForward input(IActionContext context) throws Exception
  {
    return context.getMapping().getInputForward();
  }

  public ActionForward edit(IActionContext context) throws Exception
  {
    SettingForm form = (SettingForm) context.getForm();
    Setting setting = SettingDAO.load(context, form.getStn_id());

    StoreUtil.putSession(context.getRequest(), setting);
    getCurrentFormFromBean(context, setting);

    return input(context);
  }

  public ActionForward process(IActionContext context) throws Exception
  {
    saveCurrentFormToBean(context);

    Setting setting = (Setting) StoreUtil.getSession(context.getRequest(), Setting.class);
    SettingDAO.save(context, setting);
    Config.reload();
    return context.getMapping().findForward("back");
  }

}