package net.sam.dcl.beans;

import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.HibernateUtil;
import net.sam.dcl.util.StrutsUtil;
import net.sam.dcl.dbo.DboProduce;
import net.sam.dcl.dbo.DboCatalogNumber;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.controller.ActionContext;
import net.sam.dcl.dao.ProduceDAO;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by IntelliJ IDEA.
 * Date: 14.09.2005
 * Time: 17:17:20
 * To change this template use File | Settings | File Templates.
 */
public class ConditionForContractProduce implements Serializable
{
  protected static Log log = LogFactory.getLog(ConditionForContractProduce.class);

  String ccp_id;
  String cfc_id;
  String number;
  String number1C;
  StuffCategory stuffCategory = new StuffCategory();
  DboProduce produce = new DboProduce();
  double ccp_price;
  double ccp_count;
  double ccp_cost;
  double ccp_nds_rate;
  double ccp_nds;
  double ccp_nds_cost;
  String cpr_id;
  String cpr_number;
  String cpr_date;

  boolean itogLine = false;
  String itogStr;

  boolean roundToInt = false;

  public ConditionForContractProduce()
  {
  }

  public ConditionForContractProduce(ConditionForContractProduce conditionForContractProduce)
  {
    ccp_id = conditionForContractProduce.getCcp_id();
    cfc_id = conditionForContractProduce.getCfc_id();
    number = conditionForContractProduce.getNumber();
    stuffCategory = new StuffCategory(conditionForContractProduce.getStuffCategory());
    if ( null == conditionForContractProduce.getProduce().getId() )
    {
      produce = new DboProduce();
    }
    else
    {
      produce = ProduceDAO.loadProduceWithUnit(conditionForContractProduce.getProduce().getId());
    }
    ccp_price = conditionForContractProduce.getCcp_price();
    ccp_count = conditionForContractProduce.getCcp_count();
    ccp_cost = conditionForContractProduce.getCcp_cost();
    ccp_nds_rate = conditionForContractProduce.getCcp_nds_rate();
    ccp_nds = conditionForContractProduce.getCcp_nds();
    ccp_nds_cost = conditionForContractProduce.getCcp_nds_cost();
    cpr_id = conditionForContractProduce.getCpr_id();
    cpr_number = conditionForContractProduce.getCpr_number();
    cpr_date = conditionForContractProduce.getCpr_date();

    itogLine = conditionForContractProduce.isItogLine();
    itogStr = conditionForContractProduce.getItogStr();

    roundToInt = conditionForContractProduce.isRoundToInt();
  }

  public String getCcp_id()
  {
    return ccp_id;
  }

  public void setCcp_id(String ccp_id)
  {
    this.ccp_id = ccp_id;
  }

  public String getCfc_id()
  {
    return cfc_id;
  }

  public void setCfc_id(String cfc_id)
  {
    this.cfc_id = cfc_id;
  }

  public String getNumber()
  {
    return number;
  }

  public void setNumber(String number)
  {
    this.number = number;
  }

  public StuffCategory getStuffCategory()
  {
    return stuffCategory;
  }

  public void setStuffCategory(StuffCategory stuffCategory)
  {
    this.stuffCategory = stuffCategory;
  }

  public DboProduce getProduce()
  {
    return produce;
  }

  public void setProduce(DboProduce produce)
  {
    this.produce = produce;
  }

  public double getCcp_price()
  {
    return ccp_price;
  }

  public String getCcp_price_formatted()
  {
    if ( roundToInt )
    {
      return StringUtil.double2appCurrencyStringWithoutDec(ccp_price);
    }
    return StringUtil.double2appCurrencyString(ccp_price);
  }

  public void setCcp_price(double ccp_price)
  {
    this.ccp_price = ccp_price;
  }

  public double getCcp_count()
  {
    return ccp_count;
  }

  public String getCcp_count_formatted()
  {
    return StringUtil.double2appCurrencyString(ccp_count);
  }

  public void setCcp_count(double ccp_count)
  {
    this.ccp_count = ccp_count;
  }

  public double getCcp_cost()
  {
    return ccp_cost;
  }

  public String getCcp_cost_formatted()
  {
    if ( roundToInt )
    {
      return StringUtil.double2appCurrencyStringWithoutDec(ccp_cost);
    }
    return StringUtil.double2appCurrencyString(ccp_cost);
  }

  public void setCcp_cost(double ccp_cost)
  {
    this.ccp_cost = ccp_cost;
  }

  public double getCcp_nds_rate()
  {
    return ccp_nds_rate;
  }

  public String getCcp_nds_rate_formatted()
  {
    return StringUtil.double2appCurrencyString(ccp_nds_rate);
  }

  public void setCcp_nds_rate(double ccp_nds_rate)
  {
    this.ccp_nds_rate = ccp_nds_rate;
  }

  public double getCcp_nds()
  {
    return ccp_nds;
  }

  public String getCcp_nds_formatted()
  {
    if ( roundToInt )
    {
      return StringUtil.double2appCurrencyStringWithoutDec(ccp_nds);
    }
    return StringUtil.double2appCurrencyString(ccp_nds);
  }

  public void setCcp_nds(double ccp_nds)
  {
    this.ccp_nds = ccp_nds;
  }

  public double getCcp_nds_cost()
  {
    return ccp_nds_cost;
  }

  public String getCcp_nds_cost_formatted()
  {
    if ( roundToInt )
    {
      return StringUtil.double2appCurrencyStringWithoutDec(ccp_nds_cost);
    }
    return StringUtil.double2appCurrencyString(ccp_nds_cost);
  }

  public void setCcp_nds_cost(double ccp_nds_cost)
  {
    this.ccp_nds_cost = ccp_nds_cost;
  }

  public String getCpr_id()
  {
    return cpr_id;
  }

  public void setCpr_id(String cpr_id)
  {
    this.cpr_id = cpr_id;
  }

  public String getCpr_number()
  {
    return cpr_number;
  }

  public void setCpr_number(String cpr_number)
  {
    this.cpr_number = cpr_number;
  }

  public String getCpr_date()
  {
    return cpr_date;
  }

  public void setCpr_date(String cpr_date)
  {
    this.cpr_date = cpr_date;
  }

  public String getCpr_number_date()
  {
    String retStr = "";

    if ( StringUtil.isEmpty(cpr_id) )
    {
      retStr = "";
    }
    else
    {
      IActionContext context = ActionContext.threadInstance();
      try
      {
        retStr = StrutsUtil.getMessage(context, "msg.common.from", cpr_number, StringUtil.dbDateString2appDateString(cpr_date));
      }
      catch (Exception e)
      {
        log.error(e);
      }
    }

    return retStr;
  }

  public boolean isItogLine()
  {
    return itogLine;
  }

  public void setItogLine(boolean itogLine)
  {
    this.itogLine = itogLine;
  }

  public String getItogStr()
  {
    return itogStr;
  }

  public void setItogStr(String itogStr)
  {
    this.itogStr = itogStr;
  }

  public boolean isRoundToInt()
  {
    return roundToInt;
  }

  public void setRoundToInt(boolean roundToInt)
  {
    this.roundToInt = roundToInt;
  }

  public String getProduceFullName()
  {
    if ( itogLine )
    {
      return itogStr;
    }

    if ( null != produce)
    {
      return produce.getFullName();
    }
    return "";
  }

  public String getCatalogNumberForStuffCategory()
  {
    if ( null == produce || StringUtil.isEmpty(getStuffCategory().getId()) )
    {
      return "";
    }

    produce = (DboProduce) HibernateUtil.associateWithCurentSession(produce);
		DboCatalogNumber catalogNumber = produce.getCatalogNumbers().get(new Integer(getStuffCategory().getId()));
		return catalogNumber == null ? null : catalogNumber.getNumber();
	}

  public String getUnitName()
  {
		if ( null != produce && null != produce.getUnit() )
    {
      return produce.getUnit().getName();
    }
    return "";
  }

  public String getNumber1C() {
    return number1C;
  }

  public void setNumber1C(String number1C) {
    this.number1C = number1C;
  }
}
