package net.sam.dcl.form;

import net.sam.dcl.controller.BaseForm;

public class DepartmentsListForm extends BaseForm
{
  boolean have_all = false;
  String filter;

  public boolean isHave_all()
  {
    return have_all;
  }

  public void setHave_all(boolean have_all)
  {
    this.have_all = have_all;
  }

  public String getFilter()
  {
    return filter;
  }

  public void setFilter(String filter)
  {
    this.filter = filter;
  }
}
