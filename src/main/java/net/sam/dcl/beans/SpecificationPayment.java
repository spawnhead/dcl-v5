package net.sam.dcl.beans;

import net.sam.dcl.util.StringUtil;

import java.io.Serializable;
import java.text.ParseException;

public class SpecificationPayment implements Serializable
{
  String spp_id;
  String spc_id;
  double spp_percent;
  double spp_sum;
  String spp_date;

  String currencyName;
  String description;
  boolean processed = false;

  public SpecificationPayment()
  {
  }

  public SpecificationPayment(double spp_percent, double spp_sum, String currencyName)
  {
    this.spp_percent = spp_percent;
    this.spp_sum = spp_sum;
    this.currencyName = currencyName;
  }

  public SpecificationPayment(double spp_percent, double spp_sum, String currencyName, String spp_date)
  {
    this.spp_percent = spp_percent;
    this.spp_sum = spp_sum;
    this.currencyName = currencyName;
    this.spp_date = spp_date;
  }

  public SpecificationPayment(SpecificationPayment specificationPayment)
  {
    this.spp_id = specificationPayment.getSpp_id();
    this.spc_id = specificationPayment.getSpc_id();
    this.spp_percent = specificationPayment.getSpp_percent();
    this.spp_sum = specificationPayment.getSpp_sum();
    this.spp_date = specificationPayment.getSpp_date();

    this.currencyName = specificationPayment.getCurrencyName();
    this.description = specificationPayment.getDescription();
    this.processed = specificationPayment.isProcessed();
  }

  public String getSpp_id()
  {
    return spp_id;
  }

  public void setSpp_id(String spp_id)
  {
    this.spp_id = spp_id;
  }

  public String getSpc_id()
  {
    return spc_id;
  }

  public void setSpc_id(String spc_id)
  {
    this.spc_id = spc_id;
  }

  public double getSpp_percent()
  {
    return spp_percent;
  }

  public String getSppPercentFormatted()
  {
    return StringUtil.double2appCurrencyStringByMask(spp_percent, "#,##0.00000");
  }

  public void setSpp_percent(double spp_percent)
  {
    this.spp_percent = spp_percent;
  }

  public void setSppPercentFormatted(String sppPercent)
  {
    this.spp_percent = StringUtil.appCurrencyString2double(sppPercent);
  }

  public double getSpp_sum()
  {
    return spp_sum;
  }

  public String getSppSumFormatted()
  {
    return StringUtil.double2appCurrencyString(spp_sum);
  }

  public void setSpp_sum(double spp_sum)
  {
    this.spp_sum = spp_sum;
  }

  public void setSppSumFormatted(String sppSum)
  {
    this.spp_sum = StringUtil.appCurrencyString2double(sppSum);
  }

  public String getSpp_date()
  {
    return spp_date;
  }

  public String getSppDateFormatted()
  {
    return StringUtil.dbDateString2appDateString(spp_date);
  }

  public void setSpp_date(String spp_date)
  {
    this.spp_date = spp_date != null ? spp_date.trim() : spp_date;
  }

  public void setSppDateFormatted(String sppDate) throws ParseException
  {
    this.spp_date = StringUtil.appDateString2dbDateStringWithErr(sppDate);
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