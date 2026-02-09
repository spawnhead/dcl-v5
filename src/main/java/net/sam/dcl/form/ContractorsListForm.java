package net.sam.dcl.form;

import net.sam.dcl.controller.BaseForm;

import java.util.List;
import java.util.ArrayList;

/**
 * @author: DG
 * Date: Aug 6, 2005
 * Time: 5:37:29 PM
 */
public class ContractorsListForm extends BaseForm
{
  String filter;
  boolean have_all = false;
  boolean have_empty = false;
  List list = new ArrayList();

  public List getList()
  {
    return list;
  }

  public void setList(List list)
  {
    this.list = list;
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

  public boolean isHave_empty()
  {
    return have_empty;
  }

  public void setHave_empty(boolean have_empty)
  {
    this.have_empty = have_empty;
  }

  static public class Contractor
  {
    String ctr_id;
    String ctr_name;
    String ctr_email;

    public String getCtr_id()
    {
      return ctr_id;
    }

    public void setCtr_id(String ctr_id)
    {
      this.ctr_id = ctr_id;
    }

    public String getCtr_name()
    {
      return ctr_name;
    }

    public void setCtr_name(String ctr_name)
    {
      this.ctr_name = ctr_name;
    }

    public String getCtr_email()
    {
      return ctr_email;
    }

    public void setCtr_email(String ctr_email)
    {
      this.ctr_email = ctr_email;
    }
  }
}
