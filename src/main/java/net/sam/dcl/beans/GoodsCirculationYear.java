package net.sam.dcl.beans;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * Date: 14.09.2005
 * Time: 17:17:20
 * To change this template use File | Settings | File Templates.
 */
public class GoodsCirculationYear implements Serializable
{
  int year;

  List<GoodsCirculationQuarter> goodsCirculationYearQuarters = new ArrayList<GoodsCirculationQuarter>();
  List<GoodsCirculationMonth> goodsCirculationYearMonths = new ArrayList<GoodsCirculationMonth>();

  public GoodsCirculationYear()
  {
  }

  public GoodsCirculationYear(int year)
  {
    this.year = year;
  }

  public GoodsCirculationYear(GoodsCirculationYear goodsCirculationYear)
  {
    this.year = goodsCirculationYear.getYear();
  }

  public int getYear()
  {
    return year;
  }

  public void setYear(int year)
  {
    this.year = year;
  }

  public List<GoodsCirculationQuarter> getGoodsCirculationYearQuarters()
  {
    return goodsCirculationYearQuarters;
  }

  public void setGoodsCirculationYearQuarters(List<GoodsCirculationQuarter> goodsCirculationYearQuarters)
  {
    this.goodsCirculationYearQuarters = goodsCirculationYearQuarters;
  }

  public GoodsCirculationQuarter findQuarter(int quarter)
  {
    for (GoodsCirculationQuarter goodsCirculationQuarter : getGoodsCirculationYearQuarters())
    {
      if (quarter == goodsCirculationQuarter.getQuarter())
      {
        return goodsCirculationQuarter;
      }
    }

    return null;
  }

  public void cloneGoodsCirculationQuarters(List<GoodsCirculationQuarter> goodsCirculationYearQuartersIn)
  {
    getGoodsCirculationYearQuarters().clear();
    for (GoodsCirculationQuarter goodsCirculationQuarter : goodsCirculationYearQuartersIn)
    {
      GoodsCirculationQuarter goodsCirculationQuarterNew = new GoodsCirculationQuarter(goodsCirculationQuarter);
      goodsCirculationQuarterNew.cloneGoodsCirculationMonths(goodsCirculationQuarter.getGoodsCirculationQuarterMonths());
      getGoodsCirculationYearQuarters().add(goodsCirculationQuarterNew);
    }
  }

  public List<GoodsCirculationMonth> getGoodsCirculationYearMonths()
  {
    return goodsCirculationYearMonths;
  }

  public void setGoodsCirculationYearMonths(List<GoodsCirculationMonth> goodsCirculationYearMonths)
  {
    this.goodsCirculationYearMonths = goodsCirculationYearMonths;
  }

  public boolean findMonth(int month)
  {
    for (GoodsCirculationMonth goodsCirculationQuarter : getGoodsCirculationYearMonths())
    {
      if (month == goodsCirculationQuarter.getMonth())
      {
        return true;
      }
    }

    return false;
  }

  public void cloneGoodsCirculationMonths(List<GoodsCirculationMonth> goodsCirculationYearMonthsIn)
  {
    getGoodsCirculationYearMonths().clear();
    for (GoodsCirculationMonth goodsCirculationMonth : goodsCirculationYearMonthsIn)
    {
      GoodsCirculationMonth goodsCirculationMonthNew = new GoodsCirculationMonth(goodsCirculationMonth);
      getGoodsCirculationYearMonths().add(goodsCirculationMonthNew);
    }
  }

  public String getColspanForYear(boolean isByQuarter, boolean isByMonth)
  {
    String colspan = "";
    if (isByQuarter && !isByMonth)
    {
      return Integer.toString(getGoodsCirculationYearQuarters().size());
    }
    if (!isByQuarter && isByMonth)
    {
      return Integer.toString(getGoodsCirculationYearMonths().size());
    }
    if (isByQuarter && isByMonth)
    {
      int span = getGoodsCirculationYearQuarters().size();
      for (int j = 0; j < getGoodsCirculationYearQuarters().size(); j++)
      {
        span += getGoodsCirculationYearQuarters().get(j).getGoodsCirculationQuarterMonths().size();
      }
      return Integer.toString(span);
    }

    return colspan;
  }
}
