package net.sam.dcl.beans;

import net.sam.dcl.util.StringUtil;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * Date: 14.09.2005
 * Time: 17:17:20
 * To change this template use File | Settings | File Templates.
 */
public class OrdersStatisticsLine implements Serializable
{
  String stf_name;
  double ord_summ;
  double ord_summ_sent_to_prod;
  double ord_summ_part_executed;
  double ord_summ_executed;
  double ord_summ_executed_before;
  String cur_name;

  boolean itogLine = false;

  public String getStf_name()
  {
    return stf_name;
  }

  public void setStf_name(String stf_name)
  {
    this.stf_name = stf_name;
  }

  public double getOrd_summ()
  {
    return ord_summ;
  }

  public String getOrd_summ_formatted()
  {
    return StringUtil.double2appCurrencyString(ord_summ);
  }

  public void setOrd_summ(double ord_summ)
  {
    this.ord_summ = ord_summ;
  }

  public double getOrd_summ_sent_to_prod()
  {
    return ord_summ_sent_to_prod;
  }

  public String getOrd_summ_sent_to_prod_formatted()
  {
    return StringUtil.double2appCurrencyString(ord_summ_sent_to_prod);
  }

  public void setOrd_summ_sent_to_prod(double ord_summ_sent_to_prod)
  {
    this.ord_summ_sent_to_prod = ord_summ_sent_to_prod;
  }

  public double getOrd_summ_part_executed()
  {
    return ord_summ_part_executed;
  }

  public String getOrd_summ_part_executed_formatted()
  {
    return StringUtil.double2appCurrencyString(ord_summ_part_executed);
  }

  public void setOrd_summ_part_executed(double ord_summ_part_executed)
  {
    this.ord_summ_part_executed = ord_summ_part_executed;
  }

  public double getOrd_summ_executed()
  {
    return ord_summ_executed;
  }

  public String getOrd_summ_executed_formatted()
  {
    return StringUtil.double2appCurrencyString(ord_summ_executed);
  }

  public void setOrd_summ_executed(double ord_summ_executed)
  {
    this.ord_summ_executed = ord_summ_executed;
  }

  public double getOrd_summ_executed_before()
  {
    return ord_summ_executed_before;
  }

  public String getOrd_summ_executed_before_formatted()
  {
    return StringUtil.double2appCurrencyString(ord_summ_executed_before);
  }

  public void setOrd_summ_executed_before(double ord_summ_executed_before)
  {
    this.ord_summ_executed_before = ord_summ_executed_before;
  }

  public String getCur_name()
  {
    return cur_name;
  }

  public void setCur_name(String cur_name)
  {
    this.cur_name = cur_name;
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
