package net.sam.dcl.form;

import net.sam.dcl.controller.BaseDispatchValidatorForm;

/**
 * @author: DG
 * Date: Aug 6, 2005
 * Time: 5:37:29 PM
 */
public class ShippingDocTypeForm extends BaseDispatchValidatorForm
{
  String sdt_id;
  String sdt_name;

  public String getSdt_id()
  {
    return sdt_id;
  }

  public void setSdt_id(String sdt_id)
  {
    this.sdt_id = sdt_id;
  }

  public String getSdt_name()
  {
    return sdt_name;
  }

  public void setSdt_name(String sdt_name)
  {
    this.sdt_name = sdt_name;
  }
}