package net.sam.dcl.form;

import net.sam.dcl.controller.BaseForm;
import net.sam.dcl.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: DG
 * Date: Sep 28, 2005
 * Time: 1:22:06 PM
 */
public class ContractsDepFromContractorListForm extends BaseForm
{
  String filter;
  String ctr_id;
  String cur_id;
  String is_for_closed;
  String con_seller;
  boolean have_all = false;
  String allCon = "";
  String onlyReusable = "";
  String conFinalDateAfter = "";

  List list = new ArrayList();

  public String getFilter()
  {
    return filter;
  }

  public void setFilter(String filter)
  {
    this.filter = filter;
  }

  public String getCtr_id()
  {
    return ctr_id;
  }

  public void setCtr_id(String ctr_id)
  {
    this.ctr_id = ctr_id;
  }

  public String getCur_id()
  {
    return cur_id;
  }

  public void setCur_id(String cur_id)
  {
    this.cur_id = cur_id;
  }

  public String getIs_for_closed()
  {
    return is_for_closed;
  }

  public void setIs_for_closed(String is_for_closed)
  {
    this.is_for_closed = is_for_closed;
  }

  public String getCon_seller()
  {
    return con_seller;
  }

  public void setCon_seller(String con_seller)
  {
    this.con_seller = con_seller;
  }

  public boolean isHave_all()
  {
    return have_all;
  }

  public void setHave_all(boolean have_all)
  {
    this.have_all = have_all;
  }

  public String getAllCon()
  {
    return allCon;
  }

  public void setAllCon(String allCon)
  {
    this.allCon = allCon;
  }

  public String getOnlyReusable()
  {
    return onlyReusable;
  }

  public void setOnlyReusable(String onlyReusable)
  {
    this.onlyReusable = onlyReusable;
  }

  public String getConFinalDateAfter()
  {
    return conFinalDateAfter;
  }

  public String getConFinalDateAfterDb()
  {
    return StringUtil.appDateString2dbDateString(getConFinalDateAfter());
  }

  public void setConFinalDateAfter(String conFinalDateAfter)
  {
    this.conFinalDateAfter = conFinalDateAfter;
  }

  public List getList()
  {
    return list;
  }

  public void setList(List list)
  {
    this.list = list;
  }
}
