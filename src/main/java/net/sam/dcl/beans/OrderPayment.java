package net.sam.dcl.beans;

import net.sam.dcl.util.StringUtil;

import java.io.Serializable;

public class OrderPayment implements Serializable
{
  String orp_id;
  String ord_id;
  double orp_percent;
  double orp_sum;
  String orp_date;

  String currencyName;
  String description;
  boolean processed = false;

  public OrderPayment()
  {
  }

  public OrderPayment(double orp_percent, double orp_sum, String currencyName)
  {
    this.orp_percent = orp_percent;
    this.orp_sum = orp_sum;
    this.currencyName = currencyName;
  }

  public OrderPayment(OrderPayment orderPayment)
  {
    this.orp_id = orderPayment.getOrp_id();
    this.ord_id = orderPayment.getOrd_id();
    this.orp_percent = orderPayment.getOrp_percent();
    this.orp_sum = orderPayment.getOrp_sum();
    this.orp_date = orderPayment.getOrp_date();

    this.currencyName = orderPayment.getCurrencyName();
    this.description = orderPayment.getDescription();
    this.processed = orderPayment.isProcessed();
  }

  public String getOrp_id()
  {
    return orp_id;
  }

  public void setOrp_id(String orp_id)
  {
    this.orp_id = orp_id;
  }

  public String getOrd_id()
  {
    return ord_id;
  }

  public void setOrd_id(String ord_id)
  {
    this.ord_id = ord_id;
  }

  public double getOrp_percent()
  {
    return orp_percent;
  }

  public String getOrp_percent_formatted()
  {
    return StringUtil.double2appCurrencyStringByMask(orp_percent, "#,##0.00000");
  }

  public void setOrp_percent(double orp_percent)
  {
    this.orp_percent = orp_percent;
  }

  public void setOrp_percent_formatted(String orp_percent)
  {
    this.orp_percent = StringUtil.appCurrencyString2double(orp_percent);
  }

  public double getOrp_sum()
  {
    return orp_sum;
  }

  public String getOrp_sum_formatted()
  {
    return StringUtil.double2appCurrencyString(orp_sum);
  }

  public void setOrp_sum(double orp_sum)
  {
    this.orp_sum = orp_sum;
  }

  public void setOrp_sum_formatted(String orp_sum)
  {
    this.orp_sum = StringUtil.appCurrencyString2double(orp_sum);
  }

  public String getOrp_date()
  {
    return orp_date;
  }

  public String getOrp_date_formatted()
  {
    return StringUtil.dbDateString2appDateString(orp_date);
  }

  public void setOrp_date(String orp_date)
  {
    this.orp_date = orp_date;
  }

  public void setOrp_date_formatted(String orp_date)
  {
    this.orp_date = StringUtil.appDateString2dbDateString(orp_date);
  }

  public String getCurrencyName()
  {
    return currencyName;
  }

  public void setCurrencyName(String currencyName)
  {
    this.currencyName = currencyName;
  }

  public String getDescription()
  {
    return description;
  }

  public void setDescription(String description)
  {
    this.description = description;
  }

  public boolean isProcessed()
  {
    return processed;
  }

  public void setProcessed(boolean processed)
  {
    this.processed = processed;
  }
}