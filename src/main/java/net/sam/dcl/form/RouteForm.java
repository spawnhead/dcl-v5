package net.sam.dcl.form;

import net.sam.dcl.controller.BaseDispatchValidatorForm;

public class RouteForm extends BaseDispatchValidatorForm
{
  String rut_id;
  String rut_name;

  public String getRut_id()
  {
    return rut_id;
  }

  public void setRut_id(String rut_id)
  {
    this.rut_id = rut_id;
  }

  public String getRut_name()
  {
    return rut_name;
  }

  public void setRut_name(String rut_name)
  {
    this.rut_name = rut_name;
  }
}
