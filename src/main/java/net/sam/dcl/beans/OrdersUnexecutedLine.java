package net.sam.dcl.beans;

import net.sam.dcl.util.StringUtil;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * Date: 14.09.2005
 * Time: 17:17:20
 * To change this template use File | Settings | File Templates.
 */
public class OrdersUnexecutedLine implements Serializable
{
  String ord_id;
  String ord_date;
  String ord_number;
  String prd_id;
  String produce_name;
  String prd_type;
  String prd_params;
  String prd_add_params;
  String ctn_number;
  String stf_name;
  double ord_count;
  double ord_count_executed;
  double ord_count_unexecuted;

  boolean itogLine = false;

  public String getOrd_id()
  {
    return ord_id;
  }

  public void setOrd_id(String ord_id)
  {
    this.ord_id = ord_id;
  }

  public String getOrd_date()
  {
    return ord_date;
  }

  public String getOrd_date_formatted()
  {
    return StringUtil.dbDateString2appDateString(ord_date);
  }

  public void setOrd_date(String ord_date)
  {
    this.ord_date = ord_date;
  }

  public String getOrd_number()
  {
    return ord_number;
  }

  public void setOrd_number(String ord_number)
  {
    this.ord_number = ord_number;
  }

  public String getPrd_id()
  {
    return prd_id;
  }

  public void setPrd_id(String prd_id)
  {
    this.prd_id = prd_id;
  }

  public String getProduce_name()
  {
    return produce_name;
  }

  public void setProduce_name(String produce_name)
  {
    this.produce_name = produce_name;
  }

  public String getPrd_type()
  {
    return prd_type;
  }

  public void setPrd_type(String prd_type)
  {
    this.prd_type = prd_type;
  }

  public String getPrd_params()
  {
    return prd_params;
  }

  public void setPrd_params(String prd_params)
  {
    this.prd_params = prd_params;
  }

  public String getPrd_add_params()
  {
    return prd_add_params;
  }

  public void setPrd_add_params(String prd_add_params)
  {
    this.prd_add_params = prd_add_params;
  }

  public String getFullName()
  {
    String produce_full_name = getProduce_name();
    if ( !StringUtil.isEmpty(getPrd_type()) )
    {
      produce_full_name += " " + getPrd_type();
    }
    if ( !StringUtil.isEmpty(getPrd_params()) )
    {
      produce_full_name += " " + getPrd_params();
    }
    if ( !StringUtil.isEmpty(getPrd_add_params()) )
    {
      produce_full_name += " " + getPrd_add_params();
    }
    return produce_full_name;
  }

  public String getCtn_number()
  {
    return ctn_number;
  }

  public void setCtn_number(String ctn_number)
  {
    this.ctn_number = ctn_number;
  }

  public String getStf_name()
  {
    return stf_name;
  }

  public void setStf_name(String stf_name)
  {
    this.stf_name = stf_name;
  }

  public double getOrd_count()
  {
    return ord_count;
  }

  public String getOrd_count_formatted()
  {
    return StringUtil.double2appCurrencyString(ord_count);
  }

  public void setOrd_count(double ord_count)
  {
    this.ord_count = ord_count;
  }

  public double getOrd_count_executed()
  {
    return ord_count_executed;
  }

  public String getOrd_count_executed_formatted()
  {
    return StringUtil.double2appCurrencyString(ord_count_executed);
  }

  public void setOrd_count_executed(double ord_count_executed)
  {
    this.ord_count_executed = ord_count_executed;
  }

  public double getOrd_count_unexecuted()
  {
    return ord_count_unexecuted;
  }

  public String getOrd_count_unexecuted_formatted()
  {
    return StringUtil.double2appCurrencyString(ord_count_unexecuted);
  }

  public void setOrd_count_unexecuted(double ord_count_unexecuted)
  {
    this.ord_count_unexecuted = ord_count_unexecuted;
  }

  public boolean isItogLine()
  {
    return itogLine;
  }

  public void setItogLine(boolean itogLine)
  {
    this.itogLine = itogLine;
  }
}