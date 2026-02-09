package net.sam.dcl.form;

import net.sam.dcl.controller.BaseForm;

import java.util.List;
import java.util.ArrayList;

/**
 * @author: DG
 * Date: Aug 6, 2005
 * Time: 5:37:29 PM
 */
public class ContractorsForContractsClosedListForm extends BaseForm
{
  String filter;
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

  static public class Contractor
  {
    String ctr_id;
    String ctr_name;

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
  }
}
