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
public class GoodsCirculationLine implements Serializable
{
  protected static Log log = LogFactory.getLog(CalculationStateLine.class);

  String lps_id;
  double lps_count;
  String prd_id;
  String produce_name;
  String prd_type;
  String prd_params;
  String prd_add_params;
  double rest_in_minsk;
  double restInLithuania;
  double ordInProducer;
  String ctn_number;
  String stf_id;
  String stf_name;
  String unit;
  String contractor;
  String shp_date;

  public List<GoodsCirculationYear> goodsCirculationYears = new ArrayList<GoodsCirculationYear>();

  boolean itogLine = false;
  boolean divideByContractor = false;

  public GoodsCirculationLine()
  {
  }

  public GoodsCirculationLine(GoodsCirculationLine goodsCirculationLine, boolean divideByContractor)
  {
    this.lps_id = goodsCirculationLine.getLps_id();
    this.lps_count = goodsCirculationLine.getLps_count();
    this.prd_id = goodsCirculationLine.getPrd_id();
    this.produce_name = goodsCirculationLine.getProduce_name();
    this.prd_type = goodsCirculationLine.getPrd_type();
    this.prd_params = goodsCirculationLine.getPrd_params();
    this.prd_add_params = goodsCirculationLine.getPrd_add_params();
    this.rest_in_minsk = goodsCirculationLine.getRest_in_minsk();
    this.restInLithuania = goodsCirculationLine.getRestInLithuania();
    this.ordInProducer = goodsCirculationLine.getOrdInProducer();
    this.ctn_number = goodsCirculationLine.getCtn_number();
    this.stf_id = goodsCirculationLine.getStf_id();
    this.stf_name = goodsCirculationLine.getStf_name();
    this.unit = goodsCirculationLine.getUnit();
    this.contractor = goodsCirculationLine.getContractor();
    this.shp_date = goodsCirculationLine.getShp_date();
    this.divideByContractor = divideByContractor;
    this.itogLine = goodsCirculationLine.isItogLine();
  }

  public String getLps_id()
  {
    return lps_id;
  }

  public void setLps_id(String lps_id)
  {
    this.lps_id = lps_id;
  }

  public double getLps_count()
  {
    return lps_count;
  }

  public String getLpsCountFormatted()
  {
    return StringUtil.double2appCurrencyString(getLps_count());
  }

  public void setLps_count(double lps_count)
  {
    this.lps_count = lps_count;
  }

  public String getPrd_id()
  {
    return prd_id;
  }

  public void setPrd_id(String prd_id)
  {
    this.prd_id = prd_id;
  }

  public String getProduce_name()
  {
    return produce_name;
  }

  public void setProduce_name(String produce_name)
  {
    this.produce_name = produce_name;
  }

  public String getPrd_type()
  {
    return prd_type;
  }

  public void setPrd_type(String prd_type)
  {
    this.prd_type = prd_type;
  }

  public String getPrd_params()
  {
    return prd_params;
  }

  public void setPrd_params(String prd_params)
  {
    this.prd_params = prd_params;
  }

  public String getPrd_add_params()
  {
    return prd_add_params;
  }

  public void setPrd_add_params(String prd_add_params)
  {
    this.prd_add_params = prd_add_params;
  }

  public double getRest_in_minsk()
  {
    return rest_in_minsk;
  }

  public String getRestInMinskFormatted()
  {
    return StringUtil.double2appCurrencyString(getRest_in_minsk());
  }

  public void setRest_in_minsk(double rest_in_minsk)
  {
    this.rest_in_minsk = rest_in_minsk;
  }

  public double getRestInLithuania()
  {
    return restInLithuania;
  }

  public String getRestInLithuaniaFormatted()
  {
    return StringUtil.double2appCurrencyString(getRestInLithuania());
  }

  public void setRestInLithuania(double restInLithuania)
  {
    this.restInLithuania = restInLithuania;
  }

  public double getOrdInProducer()
  {
    return ordInProducer;
  }

  public String getOrdInProducerFormatted()
  {
    return StringUtil.double2appCurrencyString(getOrdInProducer());
  }

  public void setOrdInProducer(double ordInProducer)
  {
    this.ordInProducer = ordInProducer;
  }

  public String getFullName()
  {
    String produce_full_name = getProduce_name();
    if ( !StringUtil.isEmpty(getPrd_type()) )
    {
      produce_full_name += " " + getPrd_type();
    }
    if ( !StringUtil.isEmpty(getPrd_params()) )
    {
      produce_full_name += " " + getPrd_params();
    }
    if ( !StringUtil.isEmpty(getPrd_add_params()) )
    {
      produce_full_name += " " + getPrd_add_params();
    }

    if (itogLine)
    {
      IActionContext context = ActionContext.threadInstance();
      try
      {
        return StrutsUtil.getMessage(context, "GoodsCirculation.totalBy") + " " + produce_full_name;
      }
      catch (Exception e)
      {
        log.error(e);
      }
    }

    return produce_full_name;
  }

  public String getCtn_number()
  {
    return ctn_number;
  }

  public void setCtn_number(String ctn_number)
  {
    this.ctn_number = ctn_number;
  }

  public String getStf_id()
  {
    return stf_id;
  }

  public void setStf_id(String stf_id)
  {
    this.stf_id = stf_id;
  }

  public String getStf_name()
  {
    return stf_name;
  }

  public void setStf_name(String stf_name)
  {
    this.stf_name = stf_name;
  }

  public String getUnit()
  {
    return unit;
  }

  public void setUnit(String unit)
  {
    this.unit = unit;
  }

  public String getContractor()
  {
    return contractor;
  }

  public void setContractor(String contractor)
  {
    this.contractor = contractor;
  }

  public String getShp_date()
  {
    return shp_date;
  }

  public void setShp_date(String shp_date)
  {
    this.shp_date = shp_date;
  }

  public List<GoodsCirculationYear> getGoodsCirculationYears()
  {
    return goodsCirculationYears;
  }

  public void setGoodsCirculationYears(List<GoodsCirculationYear> goodsCirculationYears)
  {
    this.goodsCirculationYears = goodsCirculationYears;
  }

  public boolean isItogLine()
  {
    return itogLine;
  }

  public void setItogLine(boolean itogLine)
  {
    this.itogLine = itogLine;
  }

  public boolean isDivideByContractor()
  {
    return divideByContractor;
  }

  public void setDivideByContractor(boolean divideByContractor)
  {
    this.divideByContractor = divideByContractor;
  }

  public boolean equals(Object o)
  {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    GoodsCirculationLine that = (GoodsCirculationLine) o;

    if ( prd_id != null ? !prd_id.equals(that.prd_id) : that.prd_id != null) return false;
    if ( divideByContractor )
    {
      if ( contractor != null ? !contractor.equals(that.contractor) : that.contractor != null) return false;
    }

    return true;
  }

  public void cloneGoodsCirculationYears(List<GoodsCirculationYear> goodsCirculationYearsIn)
  {
    getGoodsCirculationYears().clear();  
    for (GoodsCirculationYear goodsCirculationYear : goodsCirculationYearsIn)
    {
      GoodsCirculationYear goodsCirculationYearNew = new GoodsCirculationYear(goodsCirculationYear);
      goodsCirculationYearNew.cloneGoodsCirculationQuarters(goodsCirculationYear.getGoodsCirculationYearQuarters());
      goodsCirculationYearNew.cloneGoodsCirculationMonths(goodsCirculationYear.getGoodsCirculationYearMonths());
      getGoodsCirculationYears().add(goodsCirculationYearNew);
    }
  }

  public void calcGoodsCirculationCounts(List<GoodsCirculationYear> goodsCirculationYearsIn)
  {
    for (int i = 0; i < goodsCirculationYearsIn.size(); i++)
    {
      GoodsCirculationYear goodsCirculationYearIn = goodsCirculationYearsIn.get(i);
      GoodsCirculationYear goodsCirculationYear = getGoodsCirculationYears().get(i);
      for (int j = 0; j < goodsCirculationYearIn.getGoodsCirculationYearQuarters().size(); j++)
      {
        GoodsCirculationQuarter goodsCirculationQuarterIn = goodsCirculationYearIn.getGoodsCirculationYearQuarters().get(j);
        GoodsCirculationQuarter goodsCirculationQuarter = goodsCirculationYear.getGoodsCirculationYearQuarters().get(j);
        goodsCirculationQuarter.setCount(goodsCirculationQuarter.getCount() + goodsCirculationQuarterIn.getCount());
        for (int k = 0; k < goodsCirculationQuarterIn.getGoodsCirculationQuarterMonths().size(); k++)
        {
          GoodsCirculationMonth goodsCirculationMonthIn = goodsCirculationQuarterIn.getGoodsCirculationQuarterMonths().get(k);
          GoodsCirculationMonth goodsCirculationMonth = goodsCirculationQuarter.getGoodsCirculationQuarterMonths().get(k);
          goodsCirculationMonth.setCount(goodsCirculationMonth.getCount() + goodsCirculationMonthIn.getCount());
        }
      }
      for (int j = 0; j < goodsCirculationYearIn.getGoodsCirculationYearMonths().size(); j++)
      {
        GoodsCirculationMonth goodsCirculationMonthIn = goodsCirculationYearIn.getGoodsCirculationYearMonths().get(j);
        GoodsCirculationMonth goodsCirculationMonth = goodsCirculationYear.getGoodsCirculationYearMonths().get(j);
        goodsCirculationMonth.setCount(goodsCirculationMonth.getCount() + goodsCirculationMonthIn.getCount());
      }
    }
  }

  public GoodsCirculationQuarter findGoodsCirculationQuarter(int year, int quarter)
  {
    for (GoodsCirculationYear goodsCirculationYear : getGoodsCirculationYears())
    {
      if ( year == goodsCirculationYear.getYear() )
      {
        for (int j = 0; j < goodsCirculationYear.getGoodsCirculationYearQuarters().size(); j++)
        {
          GoodsCirculationQuarter goodsCirculationQuarter = goodsCirculationYear.getGoodsCirculationYearQuarters().get(j);
          if ( quarter == goodsCirculationQuarter.getQuarter() )
          {
            return goodsCirculationQuarter;  
          }
        }
      }
    }

    return null;
  }

  public GoodsCirculationMonth findGoodsCirculationMonthInQuarter(int year, int quarter, int month)
  {
    for (GoodsCirculationYear goodsCirculationYear : getGoodsCirculationYears())
    {
      if ( year == goodsCirculationYear.getYear() )
      {
        for (int j = 0; j < goodsCirculationYear.getGoodsCirculationYearQuarters().size(); j++)
        {
          GoodsCirculationQuarter goodsCirculationQuarter = goodsCirculationYear.getGoodsCirculationYearQuarters().get(j);
          if ( quarter == goodsCirculationQuarter.getQuarter() )
          {
            for (int k = 0; k < goodsCirculationQuarter.getGoodsCirculationQuarterMonths().size(); k++)
            {
              GoodsCirculationMonth goodsCirculationMonth = goodsCirculationQuarter.getGoodsCirculationQuarterMonths().get(k);
              if ( month == goodsCirculationMonth.getMonth() )
              {
                return goodsCirculationMonth;
              }
            }
          }
        }
      }
    }

    return null;
  }

  public GoodsCirculationMonth findGoodsCirculationMonth(int year, int month)
  {
    for (GoodsCirculationYear goodsCirculationYear : getGoodsCirculationYears())
    {
      if ( year == goodsCirculationYear.getYear() )
      {
        for (int j = 0; j < goodsCirculationYear.getGoodsCirculationYearMonths().size(); j++)
        {
          GoodsCirculationMonth goodsCirculationMonth = goodsCirculationYear.getGoodsCirculationYearMonths().get(j);
          if ( month == goodsCirculationMonth.getMonth() )
          {
            return goodsCirculationMonth;  
          }
        }
      }
    }

    return null;
  }
}
