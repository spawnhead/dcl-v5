package net.sam.dcl.form;

import net.sam.dcl.controller.BaseDispatchValidatorForm;

public class PurchasePurposeForm extends BaseDispatchValidatorForm
{
  String pps_id;
  String pps_name;

  public String getPps_id()
  {
    return pps_id;
  }

  public void setPps_id(String pps_id)
  {
    this.pps_id = pps_id;
  }

  public String getPps_name()
  {
    return pps_name;
  }

  public void setPps_name(String pps_name)
  {
    this.pps_name = pps_name;
  }
}