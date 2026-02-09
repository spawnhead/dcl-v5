package net.sam.dcl.beans;

import java.io.Serializable;

public class Setting implements Serializable
{
  final static public String simpleType = "0";
  final static public String listActionType = "1";
  final static public String CheckboxActionType = "2";

  String stn_id;
  String stn_name;
  String stn_description;
  String stn_value;
  String stn_value_extended;
  String stn_type;
  String stn_action;

  public Setting()
  {
  }

  public Setting(String stn_id)
  {
    this.stn_id = stn_id;
  }

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

  public String getStn_action()
  {
    return stn_action;
  }

  public void setStn_action(String stn_action)
  {
    this.stn_action = stn_action;
  }
}