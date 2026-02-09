package net.sam.dcl.form;

import net.sam.dcl.controller.BaseDispatchValidatorForm;

/**
 * @author: DG
 * Date: Aug 6, 2005
 * Time: 5:37:29 PM
 */
public class StuffCategoryForm extends BaseDispatchValidatorForm
{
  String stf_id;
  String stf_name;
  String stf_show_in_montage;

  public String getStf_id()
  {
    return stf_id;
  }

  public void setStf_id(String stf_id)
  {
    this.stf_id = stf_id;
  }

  public String getStf_name()
  {
    return stf_name;
  }

  public void setStf_name(String stf_name)
  {
    this.stf_name = stf_name;
  }

  public String getStf_show_in_montage()
  {
    return stf_show_in_montage;
  }

  public void setStf_show_in_montage(String stf_show_in_montage)
  {
    this.stf_show_in_montage = stf_show_in_montage;
  }
}
