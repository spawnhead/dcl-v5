package net.sam.dcl.action;

import net.sam.dcl.App;
import net.sam.dcl.beans.Action;
import net.sam.dcl.beans.User;
import net.sam.dcl.beans.UserSetting;
import net.sam.dcl.beans.Constants;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.controller.IDispatchable;
import net.sam.dcl.controller.actions.DBTransactionAction;
import net.sam.dcl.db.VResultSet;
import net.sam.dcl.form.LoginForm;
import net.sam.dcl.navigation.DbLogging;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.util.StoreUtil;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.StrutsUtil;
import org.apache.struts.action.ActionForward;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class LoginAction extends DBTransactionAction implements IDispatchable
{
  public ActionForward input(IActionContext context) throws Exception
  {
    if (App.loginDisabled)
    {
      return context.getMapping().findForward("login-disabled");
    }
    else
    {
      return context.getMapping().getInputForward();
    }
  }

  public ActionForward process(IActionContext context) throws Exception
  {
    LoginForm form = (LoginForm) context.getForm();
    boolean ret = DAOUtils.load(context, "user-load-login", null);
    if (ret)
    {
      ret = DAOUtils.load(context, "user-load-login_pwd", null);
      if (ret)
      {
        VResultSet resultSet = DAOUtils.executeQuery(context, "select-user-roles", null);
        List<String> roles = DAOUtils.resultSet2StringList(resultSet);

        String address = context.getRequest().getRemoteAddr();
        if ("1".equals(form.getUsr_block()) || "1".equals(form.getUsr_no_login()))
        {
          StrutsUtil.addError(context, "error.loginpage.block", null);
        }
        else if (StringUtil.isEmpty(form.getUsr_local_entry()) && (address.contains(Constants.localIpPrefix) || address.contains(Constants.localMachinePrefix) ))
        {
          StrutsUtil.addError(context, "error.loginpage.blockLockal", null);
        }
        else if (StringUtil.isEmpty(form.getUsr_internet_entry()) && !(address.contains(Constants.localIpPrefix) || address.contains(Constants.localMachinePrefix) ))
        {
          StrutsUtil.addError(context, "error.loginpage.blockInternet", null);
        }
        else
        {
          resultSet = DAOUtils.executeQuery(context, "select-user-actions", null);
          Map<String, Action> actions = new LinkedHashMap<String, Action>();
          while (resultSet.next())
          {
            Action action = new net.sam.dcl.beans.Action(resultSet.getData("act_id"), resultSet.getData("act_system_name"));
            actions.put(resultSet.getData("act_system_name"), action);
          }

          resultSet = DAOUtils.executeQuery(context, "select-user_settings_login", null);
          Map<String, UserSetting> userSettings = new LinkedHashMap<String, UserSetting>();
          while (resultSet.next())
          {
            UserSetting userSetting = new UserSetting(resultSet.getData("ust_name"), resultSet.getData("ust_value"));
            userSettings.put(resultSet.getData("ust_name"), userSetting);
          }

          User user = new User();
          user.setUsr_id(form.getUsr_id());
          user.setUsr_code(form.getUsr_code());
          user.setUsr_name(form.getUsr_name());
          user.setUsr_login(form.getUsr_login());
          user.setUsr_surname(form.getUsr_surname());
          user.setUsr_chief_dep(form.getUsr_chief_dep());
          user.setDepartment(form.getDepartment());
          user.setRoles(roles);
          user.setActions(actions);
          user.setUserSettings(userSettings);
          user.setIp(address);
          String __usw = form.getUserScreenWith();
          int __userScreenWidth = 1024;
          if (!StringUtil.isEmpty(__usw)) {
            String __uswTrimmed = __usw.trim();
            if (__uswTrimmed.length() > 0) {
              try {
                __userScreenWidth = Integer.parseInt(__uswTrimmed);
              } catch (NumberFormatException ignore) {
                __userScreenWidth = 1024;
              }
            }
          }
          user.setUserScreenWith(Integer.valueOf(__userScreenWidth));
          StoreUtil.putSession(context.getRequest(), user);
          DbLogging.logAction(context, user.getUsr_id(), DbLogging.loginId, context.getRequest().getRemoteAddr());
          return new ActionForward(context.getMapping().findForward("invitation-forward"));
        }
      }
      else
      {
        StrutsUtil.addError(context, "error.loginpage.password", null);
      }
    }
    else
    {
      StrutsUtil.addError(context, "error.loginpage.login", null);
    }
    return context.getMapping().getInputForward();
  }

}
