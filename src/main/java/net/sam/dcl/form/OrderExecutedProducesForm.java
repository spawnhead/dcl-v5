package net.sam.dcl.form;

import net.sam.dcl.controller.BaseDispatchValidatorForm;

public class OrderExecutedProducesForm extends BaseDispatchValidatorForm
{
  String ord_id;
  String ord_number;
  String ord_date;

  String disableEdit = "";
  String colspan;

  public String getOrd_id()
  {
    return ord_id;
  }

  public void setOrd_id(String ord_id)
  {
    this.ord_id = ord_id;
  }

  public String getOrd_number()
  {
    return ord_number;
  }

  public void setOrd_number(String ord_number)
  {
    this.ord_number = ord_number;
  }

  public String getOrd_date()
  {
    return ord_date;
  }

  public void setOrd_date(String ord_date)
  {
    this.ord_date = ord_date;
  }

  public String getDisableEdit()
  {
    return disableEdit;
  }

  public void setDisableEdit(String disableEdit)
  {
    this.disableEdit = disableEdit;
  }

  public String getColspan()
  {
    return colspan;
  }

  public void setColspan(String colspan)
  {
    this.colspan = colspan;
  }
}