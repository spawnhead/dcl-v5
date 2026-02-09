package net.sam.dcl.action;

import net.sam.dcl.controller.actions.DBAction;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.util.UserUtil;
import net.sam.dcl.form.UserSettingsForm;
import net.sam.dcl.beans.User;
import org.apache.struts.action.ActionForward;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class UserSettingsAction extends DBAction
{
  public ActionForward execute(IActionContext context) throws Exception
  {
    UserSettingsForm form = (UserSettingsForm) context.getForm();
    User user = UserUtil.getCurrentUser(context.getRequest());
    form.setUser(user);
    DAOUtils.fillGrid(context, form.getGridUserSettings(), "select-user_settings", UserSettingsForm.UserSetting.class, null, null);

    return context.getMapping().getInputForward();
  }

}