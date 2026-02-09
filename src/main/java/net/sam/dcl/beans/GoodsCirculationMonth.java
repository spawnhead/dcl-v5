package net.sam.dcl.beans;

import net.sam.dcl.util.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * Date: 14.09.2005
 * Time: 17:17:20
 * To change this template use File | Settings | File Templates.
 */
public class GoodsCirculationMonth implements Serializable
{
  protected static Log log = LogFactory.getLog(GoodsCirculationMonth.class);

  int month;
  double count;

  public GoodsCirculationMonth()
  {
  }

  public GoodsCirculationMonth(int quarter)
  {
    this.month = quarter;
  }

  public GoodsCirculationMonth(GoodsCirculationMonth goodsCirculationQuarter)
  {
    this.month = goodsCirculationQuarter.getMonth();
    this.count = goodsCirculationQuarter.getCount();
  }

  public int getMonth()
  {
    return month;
  }

  public String getMonthFormatted()
  {
    return StringUtil.getMonth(getMonth());
  }

  public void setMonth(int month)
  {
    this.month = month;
  }

  public double getCount()
  {
    return count;
  }

  public String getCountFormatted()
  {
    return StringUtil.double2appCurrencyString(count);
  }

  public void setCount(double count)
  {
    this.count = count;
  }
}