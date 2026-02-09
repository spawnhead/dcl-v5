package net.sam.dcl.beans;

import java.io.Serializable;

public class UserSetting implements Serializable
{
  String ust_id;
  String ust_name;
  String ust_description;
  String ust_value;
  String ust_value_extended;
  String ust_type;
  String ust_action;
  String usr_id;

  public UserSetting()
  {
  }

  public UserSetting(String ust_name, String ust_value)
  {
    this.ust_name = ust_name;
    this.ust_value = ust_value;
  }

  public UserSetting(String ust_id)
  {
    this.ust_id = ust_id;
  }

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

  public String getUsr_id()
  {
    return usr_id;
  }

  public void setUsr_id(String usr_id)
  {
    this.usr_id = usr_id;
  }
}