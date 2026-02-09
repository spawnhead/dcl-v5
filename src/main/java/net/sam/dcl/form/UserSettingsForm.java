package net.sam.dcl.form;

import net.sam.dcl.beans.Setting;
import net.sam.dcl.beans.User;
import net.sam.dcl.controller.ActionContext;
import net.sam.dcl.controller.BaseForm;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.taglib.table.model.impl.HolderImplUsingList;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.StrutsUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class UserSettingsForm extends BaseForm
{
  protected static Log log = LogFactory.getLog(UserSettingsForm.class);

  User user = new User();
  HolderImplUsingList gridUserSettings = new HolderImplUsingList();

  public User getUser()
  {
    return user;
  }

  public void setUser(User user)
  {
    this.user = user;
  }

  public HolderImplUsingList getGridUserSettings()
  {
    return gridUserSettings;
  }

  public void setGridUserSettings(HolderImplUsingList gridUserSettings)
  {
    this.gridUserSettings = gridUserSettings;
  }

  static public class UserSetting
  {
    String ust_id;
    String ust_name;
    String ust_description;
    String ust_value;
    String ust_value_extended;
    String ust_type;

    public String getUst_id()
    {
      return ust_id;
    }

    public void setUst_id(String ust_id)
    {
      this.ust_id = ust_id;
    }

    public String getUst_name()
    {
      return ust_name;
    }

    public void setUst_name(String ust_name)
    {
      this.ust_name = ust_name;
    }

    public String getUst_description()
    {
      return ust_description;
    }

    public void setUst_description(String ust_description)
    {
      this.ust_description = ust_description;
    }

    public String getUst_value()
    {
      if (net.sam.dcl.beans.Setting.listActionType.equals(getUst_type()))
      {
        return ust_value_extended;
      }

      if (Setting.CheckboxActionType.equals(getUst_type()))
      {
        IActionContext context = ActionContext.threadInstance();
        try
        {
          if (StringUtil.isEmpty(ust_value))
            return StrutsUtil.getMessage(context, "button.no");
          else
            return StrutsUtil.getMessage(context, "button.yes");
        }
        catch (Exception e)
        {
          log.error(e);
        }
      }

      return ust_value;
    }

    public void setUst_value(String ust_value)
    {
      this.ust_value = ust_value;
    }

    public String getUst_value_extended()
    {
      return ust_value_extended;
    }

    public void setUst_value_extended(String ust_value_extended)
    {
      this.ust_value_extended = ust_value_extended;
    }

    public String getUst_type()
    {
      return ust_type;
    }

    public void setUst_type(String ust_type)
    {
      this.ust_type = ust_type;
    }
  }
}