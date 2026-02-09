package net.sam.dcl.form;

import net.sam.dcl.controller.BaseDispatchValidatorForm;

/**
 * @author: DG
 * Date: Aug 6, 2005
 * Time: 5:37:29 PM
 */
public class DepartmentForm extends BaseDispatchValidatorForm
{
  String dep_id;
  String dep_name;

  public String getDep_id()
  {
    return dep_id;
  }

  public void setDep_id(String dep_id)
  {
    this.dep_id = dep_id;
  }

  public String getDep_name()
  {
    return dep_name;
  }

  public void setDep_name(String dep_name)
  {
    this.dep_name = dep_name;
  }
}
