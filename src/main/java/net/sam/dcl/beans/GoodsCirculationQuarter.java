package net.sam.dcl.beans;

import net.sam.dcl.controller.ActionContext;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.StrutsUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Date: 14.09.2005
 * Time: 17:17:20
 * To change this template use File | Settings | File Templates.
 */
public class GoodsCirculationQuarter implements Serializable
{
  protected static Log log = LogFactory.getLog(GoodsCirculationQuarter.class);
  
  int quarter;
  double count;
  List<GoodsCirculationMonth> goodsCirculationQuarterMonths = new ArrayList<GoodsCirculationMonth>();

  public GoodsCirculationQuarter()
  {
  }

  public GoodsCirculationQuarter(int quarter)
  {
    this.quarter = quarter;
  }

  public GoodsCirculationQuarter(GoodsCirculationQuarter goodsCirculationQuarter)
  {
    this.quarter = goodsCirculationQuarter.getQuarter();
    this.count = goodsCirculationQuarter.getCount();
  }

  public int getQuarter()
  {
    return quarter;
  }

  public String getQuarterFormatted()
  {
    IActionContext context = ActionContext.threadInstance();

    try
    {
      return Integer.toString(quarter) + StrutsUtil.getMessage(context, "GoodsCirculation.quarter");
    }
    catch (Exception e)
    {
      log.error(e);
    }

    return Integer.toString(quarter);
  }

  public void setQuarter(int quarter)
  {
    this.quarter = quarter;
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

  public List<GoodsCirculationMonth> getGoodsCirculationQuarterMonths()
  {
    return goodsCirculationQuarterMonths;
  }

  public void setGoodsCirculationQuarterMonths(List<GoodsCirculationMonth> goodsCirculationQuarterMonths)
  {
    this.goodsCirculationQuarterMonths = goodsCirculationQuarterMonths;
  }

  public boolean findMonth(int month)
  {
    for ( GoodsCirculationMonth goodsCirculationQuarter : getGoodsCirculationQuarterMonths() )
    {
      if ( month == goodsCirculationQuarter.getMonth() )
      {
        return true;
      }
    }

    return false;
  }

  public void cloneGoodsCirculationMonths(List<GoodsCirculationMonth> goodsCirculationQuarterMonthsIn)
  {
    getGoodsCirculationQuarterMonths().clear();
    for (GoodsCirculationMonth goodsCirculationMonth : goodsCirculationQuarterMonthsIn)
    {
      GoodsCirculationMonth goodsCirculationMonthNew = new GoodsCirculationMonth(goodsCirculationMonth);
      getGoodsCirculationQuarterMonths().add(goodsCirculationMonthNew);
    }
  }
}
