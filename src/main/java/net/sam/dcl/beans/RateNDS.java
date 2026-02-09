package net.sam.dcl.beans;

import net.sam.dcl.util.StringUtil;

import java.io.Serializable;

public class RateNDS implements Serializable
{
  String id;
  String dateFrom;
  double percent;

  public RateNDS()
  {
  }

  public RateNDS(String id)
  {
    this.id = id;
  }

  public RateNDS(String id, String dateFrom)
  {
    this.id = id;
    this.dateFrom = dateFrom;
  }

  public RateNDS(RateNDS purpose)
  {
    id = purpose.getId();
    dateFrom = purpose.getDateFrom();
    percent = purpose.getPercent();
  }

  public String getId()
  {
    return id;
  }

  public void setId(String id)
  {
    this.id = id;
  }

  public String getDateFrom()
  {
    return dateFrom;
  }

  public String getDateFromTS()
  {
    return StringUtil.appDateString2dbDateString(dateFrom);
  }

  public void setDateFrom(String dateFrom)
  {
    this.dateFrom = dateFrom;
  }

  public double getPercent()
  {
    return percent;
  }

  public void setPercent(double percent)
  {
    this.percent = percent;
  }
}