package net.sam.dcl.beans;

import net.sam.dcl.util.StringUtil;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * Date: 14.09.2005
 * Time: 17:17:20
 * To change this template use File | Settings | File Templates.
 */
public class ProduceCostReportLine implements Serializable
{
  String prc_date;
  String prc_number;
  String route;
  double weight;
  double transport;
  double custom;

  boolean needShortFormat = false;

  public String getPrc_date()
  {
    return prc_date;
  }

  public String getPrc_date_formatted()
  {
    if ( !isNeedShortFormat() )
    {
      return StringUtil.dbDateString2appDateString(prc_date);
    }
    else
    {
      return StringUtil.dbDateString2appShotMonth(prc_date);
    }
  }

  public void setPrc_date(String prc_date)
  {
    this.prc_date = prc_date;
  }

  public String getPrc_number()
  {
    return prc_number;
  }

  public void setPrc_number(String prc_number)
  {
    this.prc_number = prc_number;
  }

  public String getRoute()
  {
    return route;
  }

  public void setRoute(String route)
  {
    this.route = route;
  }

  public double getWeight()
  {
    return weight;
  }

  public String getWeightFormatted()
  {
    return StringUtil.double2appCurrencyStringByMask(weight, "#,##0.000");
  }

  public void setWeight(double weight)
  {
    this.weight = weight;
  }

  public double getTransport()
  {
    return transport;
  }

  public String getTransportFormatted()
  {
    return StringUtil.double2appCurrencyString(transport);
  }

  public void setTransport(double transport)
  {
    this.transport = transport;
  }

  public double getCustom()
  {
    return custom;
  }

  public String getCustomFormatted()
  {
    return StringUtil.double2appCurrencyString(custom);
  }

  public void setCustom(double custom)
  {
    this.custom = custom;
  }

  public boolean isNeedShortFormat()
  {
    return needShortFormat;
  }

  public void setNeedShortFormat(boolean needShortFormat)
  {
    this.needShortFormat = needShortFormat;
  }
}