package net.sam.dcl.form;

import net.sam.dcl.controller.BaseForm;
import net.sam.dcl.util.StringUtil;

import java.util.List;
import java.util.ArrayList;

public class IncoTermsListForm extends BaseForm
{
  String forOrder;
  List list = new ArrayList();

  public String getForOrder()
  {
    return forOrder;
  }

  public void setForOrder(String forOrder)
  {
    this.forOrder = forOrder;
  }

  public List getList()
  {
    return list;
  }

  public void setList(List list)
  {
    this.list = list;
  }

  static public class IncoTerm
  {
    String trm_id;
    String trm_name;
    String trm_name_extended;

    public IncoTerm()
    {
    }

    public IncoTerm(String trm_id, String trm_name, String trm_name_extended)
    {
      this.trm_id = trm_id;
      this.trm_name = trm_name;
      this.trm_name_extended = trm_name_extended;
    }

    public String getTrm_id()
    {
      return trm_id;
    }

    public void setTrm_id(String trm_id)
    {
      this.trm_id = trm_id;
    }

    public String getTrm_name()
    {
      return trm_name;
    }

    public void setTrm_name(String trm_name)
    {
      this.trm_name = trm_name;
    }

    public String getTrm_name_extended()
    {
      return trm_name_extended;
    }

    public void setTrm_name_extended(String trm_name_extended)
    {
      this.trm_name_extended = trm_name_extended;
    }
  }
}