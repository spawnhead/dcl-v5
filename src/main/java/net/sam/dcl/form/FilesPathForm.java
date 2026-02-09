package net.sam.dcl.form;

import net.sam.dcl.controller.BaseDispatchValidatorForm;

public class FilesPathForm extends BaseDispatchValidatorForm
{
  String flp_id;
  String flp_table_name;
  String flp_path;
  String flp_description;

  public String getFlp_id()
  {
    return flp_id;
  }

  public void setFlp_id(String flp_id)
  {
    this.flp_id = flp_id;
  }

  public String getFlp_table_name()
  {
    return flp_table_name;
  }

  public void setFlp_table_name(String flp_table_name)
  {
    this.flp_table_name = flp_table_name;
  }

  public String getFlp_path()
  {
    return flp_path;
  }

  public void setFlp_path(String flp_path)
  {
    this.flp_path = flp_path;
  }

  public String getFlp_description()
  {
    return flp_description;
  }

  public void setFlp_description(String flp_description)
  {
    this.flp_description = flp_description;
  }
}