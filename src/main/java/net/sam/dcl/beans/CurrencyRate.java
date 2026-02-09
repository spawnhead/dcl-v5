package net.sam.dcl.beans;

import java.io.Serializable;

public class CurrencyRate implements Serializable
{
  String crt_id;
  String cur_id;
  String crt_date;
  double crt_rate;

  int countRatesBefore;

  public CurrencyRate()
  {
  }

  public String getCrt_id()
  {
    return crt_id;
  }

  public void setCrt_id(String crt_id)
  {
    this.crt_id = crt_id;
  }

  public String getCur_id()
  {
    return cur_id;
  }

  public void setCur_id(String cur_id)
  {
    this.cur_id = cur_id;
  }

  public String getCrt_date()
  {
    return crt_date;
  }

  public void setCrt_date(String crt_date)
  {
    this.crt_date = crt_date;
  }

  public double getCrt_rate()
  {
    return crt_rate;
  }

  public void setCrt_rate(double crt_rate)
  {
    this.crt_rate = crt_rate;
  }

  public int getCountRatesBefore()
  {
    return countRatesBefore;
  }

  public void setCountRatesBefore(int countRatesBefore)
  {
    this.countRatesBefore = countRatesBefore;
  }
}