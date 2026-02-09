package net.sam.dcl.form;

import net.sam.dcl.controller.ActionContext;
import net.sam.dcl.controller.BaseForm;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.taglib.table.model.impl.HolderImplUsingList;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.StrutsUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SettingsForm extends BaseForm
{
  protected static Log log = LogFactory.getLog(SettingsForm.class);

  HolderImplUsingList gridSettings = new HolderImplUsingList();

  public HolderImplUsingList getGridSettings()
  {
    return gridSettings;
  }

  public void setGridSettings(HolderImplUsingList gridSettings)
  {
    this.gridSettings = gridSettings;
  }

  static public class Setting
  {
    String stn_id;
    String stn_name;
    String stn_description;
    String stn_value;
    String stn_value_extended;
    String stn_type;

    public String getStn_id()
    {
      return stn_id;
    }

    public void setStn_id(String stn_id)
    {
      this.stn_id = stn_id;
    }

    public String getStn_name()
    {
      return stn_name;
    }

    public void setStn_name(String stn_name)
    {
      this.stn_name = stn_name;
    }

    public String getStn_description()
    {
      return stn_description;
    }

    public void setStn_description(String stn_description)
    {
      this.stn_description = stn_description;
    }

    public String getStn_value()
    {
      if ( net.sam.dcl.beans.Setting.listActionType.equals(stn_type) )
      {
        return stn_value_extended;
      }

      if (net.sam.dcl.beans.Setting.CheckboxActionType.equals(getStn_type()))
      {
        IActionContext context = ActionContext.threadInstance();
        try
        {
          if (StringUtil.isEmpty(stn_value))
            return StrutsUtil.getMessage(context, "button.no");
          else
            return StrutsUtil.getMessage(context, "button.yes");
        }
        catch (Exception e)
        {
          log.error(e);
        }
      }

      return stn_value;
    }

    public void setStn_value(String stn_value)
    {
      this.stn_value = stn_value;
    }

    public String getStn_value_extended()
    {
      return stn_value_extended;
    }

    public void setStn_value_extended(String stn_value_extended)
    {
      this.stn_value_extended = stn_value_extended;
    }

    public String getStn_type()
    {
      return stn_type;
    }

    public void setStn_type(String stn_type)
    {
      this.stn_type = stn_type;
    }
  }
}