package net.sam.dcl.beans;

import net.sam.dcl.util.StringUtil;

import java.io.Serializable;

public class OrderPaySum implements Serializable
{
  String ops_id;
  String ord_id;
  double ops_sum;
  String ops_date;

  public OrderPaySum()
  {
  }

  public OrderPaySum(double ops_sum, String ops_date)
  {
    this.ops_sum = ops_sum;
    this.ops_date = ops_date;
  }

  public OrderPaySum(OrderPaySum orderPaySum)
  {
    this.ops_id = orderPaySum.getOps_id();
    this.ord_id = orderPaySum.getOrd_id();
    this.ops_sum = orderPaySum.getOps_sum();
    this.ops_date = orderPaySum.getOps_date();
  }

  public String getOps_id()
  {
    return ops_id;
  }

  public void setOps_id(String ops_id)
  {
    this.ops_id = ops_id;
  }

  public String getOrd_id()
  {
    return ord_id;
  }

  public void setOrd_id(String ord_id)
  {
    this.ord_id = ord_id;
  }

  public double getOps_sum()
  {
    return ops_sum;
  }

  public String getOps_sum_formatted()
  {
    return StringUtil.double2appCurrencyString(ops_sum);
  }

  public void setOps_sum(double ops_sum)
  {
    this.ops_sum = ops_sum;
  }

  public void setOps_sum_formatted(String ops_sum)
  {
    this.ops_sum = StringUtil.appCurrencyString2double(ops_sum);
  }

  public String getOps_date()
  {
    return ops_date;
  }

  public String getOps_date_formatted()
  {
    return StringUtil.dbDateString2appDateString(ops_date);
  }

  public void setOps_date(String ops_date)
  {
    this.ops_date = ops_date;
  }

  public void setOps_date_formatted(String ops_date)
  {
    this.ops_date = StringUtil.appDateString2dbDateString(ops_date);
  }
}