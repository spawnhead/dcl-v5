package net.sam.dcl.form;

import net.sam.dcl.controller.BaseDispatchValidatorForm;

public class PurposeForm extends BaseDispatchValidatorForm
{
  String prs_id;
  String prs_name;

  public String getPrs_id()
  {
    return prs_id;
  }

  public void setPrs_id(String prs_id)
  {
    this.prs_id = prs_id;
  }

  public String getPrs_name()
  {
    return prs_name;
  }

  public void setPrs_name(String prs_name)
  {
    this.prs_name = prs_name;
  }
}
