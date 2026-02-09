package net.sam.dcl.form;

import net.sam.dcl.controller.BaseDispatchValidatorForm;

public class InstructionTypeForm extends BaseDispatchValidatorForm
{
  String ist_id;
  String ist_name;

  public String getIst_id()
  {
    return ist_id;
  }

  public void setIst_id(String ist_id)
  {
    this.ist_id = ist_id;
  }

  public String getIst_name()
  {
    return ist_name;
  }

  public void setIst_name(String ist_name)
  {
    this.ist_name = ist_name;
  }
}