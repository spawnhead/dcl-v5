package net.sam.dcl.form;

import net.sam.dcl.controller.BaseDispatchValidatorForm;
import net.sam.dcl.beans.Setting;

public class UserSettingForm extends BaseDispatchValidatorForm
{
  String ust_id;
  String ust_name;
  String ust_description;
  String ust_value;
  String ust_value_extended;
  String ust_type;
  String ust_action;

  UserSettingList userSettingList = new UserSettingList();

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

  public String getUst_action()
  {
    return ust_action;
  }

  public void setUst_action(String ust_action)
  {
    this.ust_action = ust_action;
  }

  public boolean isShowSimple()
  {
    return Setting.simpleType.equals(getUst_type());
  }

  public boolean isShowListAction()
  {
    return Setting.listActionType.equals(getUst_type());
  }

  public boolean isShowCheckboxAction()
  {
    return Setting.CheckboxActionType.equals(getUst_type());
  }

  public UserSettingList getUserSettingList()
  {
    return userSettingList;
  }

  public void setUserSettingList(UserSettingList userSettingList)
  {
    this.userSettingList = userSettingList;
  }

  static public class UserSettingList
  {
    String id;
    String name;

    public UserSettingList()
    {
    }

    public String getId()
    {
      return id;
    }

    public void setId(String id)
    {
      this.id = id;
    }

    public String getName()
    {
      return name;
    }

    public void setName(String name)
    {
      this.name = name;
    }
  }
}