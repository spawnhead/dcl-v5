package net.sam.dcl.form;

import net.sam.dcl.controller.BaseForm;

/**
 * @author: DG
 * Date: Sep 28, 2005
 * Time: 1:22:06 PM
 */
public class UsersListForm extends BaseForm
{
  String dep_id = "";
  String filter;
  boolean have_all = false;
  boolean respons_person = false;

  public String getDep_id()
  {
    return dep_id;
  }

  public void setDep_id(String dep_id)
  {
    this.dep_id = dep_id;
  }

  public String getFilter()
  {
    return filter;
  }

  public void setFilter(String filter)
  {
    this.filter = filter;
  }

  public boolean isHave_all()
  {
    return have_all;
  }

  public void setHave_all(boolean have_all)
  {
    this.have_all = have_all;
  }

  public boolean isRespons_person()
  {
    return respons_person;
  }

  public int getResponsPersonInt()
  {
    return respons_person ? 1 : 0;
  }

  public void setRespons_person(boolean respons_person)
  {
    this.respons_person = respons_person;
  }
}
