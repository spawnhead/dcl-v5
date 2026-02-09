package net.sam.dcl.beans;

import net.sam.dcl.controller.ActionContext;
import net.sam.dcl.dao.ProduceDAO;
import net.sam.dcl.dbo.DboCatalogNumber;
import net.sam.dcl.dbo.DboCustomCode;
import net.sam.dcl.dbo.DboProduce;
import net.sam.dcl.service.helper.NomenclatureProduceCustomCodeHistoryHelper;
import net.sam.dcl.util.HibernateUtil;
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
public class CommercialProposalProduce implements Serializable
{
  protected static Log log = LogFactory.getLog(CommercialProposalProduce.class);

  String lpr_id;
  String cpr_id;
  String cpr_date;
  String number;
  String lpr_produce_name;
  String lpr_catalog_num;
  double lpr_price_brutto;
  double lpr_discount;
  double lpr_price_netto;
  double lpr_count;
  CustomCode customCode = new CustomCode();
  double lpr_coeficient;
  StuffCategory stuffCategory = new StuffCategory();
  DboProduce produce = new DboProduce();
  String lpr_comment;

  double sale_price;
  double sale_cost;
  double parking_trans;
  double sale_price_parking_trans;
  double sale_cost_parking_trans;
  double custom_duty;
  double sale_price_parking_trans_custom;
  double sale_cost_parking_trans_custom;
  double nds;
  double cost_nds;

  double lpr_price_brutto_print;
  double lpr_discount_print;
  double lpr_price_netto_print;
  double sale_price_print;
  double sale_cost_print;
  double parking_trans_print;
  double sale_price_parking_trans_print;
  double sale_cost_parking_trans_print;
  double custom_duty_print;
  double sale_price_parking_trans_custom_print;
  double sale_cost_parking_trans_custom_print;
  double nds_print;
  double cost_nds_print;

  String lpc_id; //для импорта из Товар-себестоимость
  double lpc_cost_one_by;
  double lpc_price_list_by;
  String lpc_1c_number;
  double extra_percent;
  double discount_percent;
  double lpr_sale_price;
  double lpr_sale_cost_parking_trans;

  double outValue;
  String totalText;

  boolean doubleSums = false;
  boolean needRoundPrint = false;
  boolean itogLine = false;
  boolean incoTermCaseA = false;

  double sumLprPrice;
  double rest_lpc_count;

  String calculatedField;

  public CommercialProposalProduce()
  {
  }

  public CommercialProposalProduce(String cpr_id, String cpr_date, String lpc_id)
  {
    this.cpr_id = cpr_id;
    this.cpr_date = cpr_date;
    this.lpc_id = lpc_id;
  }

  public CommercialProposalProduce(CommercialProposalProduce commercialProposalProduce)
  {
    lpr_id = commercialProposalProduce.getLpr_id();
    cpr_id = commercialProposalProduce.getCpr_id();
    cpr_date = commercialProposalProduce.getCpr_date();
    number = commercialProposalProduce.getNumber();
    lpr_produce_name = commercialProposalProduce.getLpr_produce_name();
    lpr_catalog_num = commercialProposalProduce.getLpr_catalog_num();
    lpr_price_brutto = commercialProposalProduce.getLpr_price_brutto();
    lpr_discount = commercialProposalProduce.getLpr_discount();
    lpr_price_netto = commercialProposalProduce.getLpr_price_netto();
    lpr_count = commercialProposalProduce.getLpr_count();
    customCode = new CustomCode(commercialProposalProduce.getCustomCode());
    lpr_coeficient = commercialProposalProduce.getLpr_coeficient();
    stuffCategory = new StuffCategory(commercialProposalProduce.getStuffCategory());
    if (null != commercialProposalProduce.getProduce() && null != commercialProposalProduce.getProduce().getId())
    {
      produce = ProduceDAO.loadProduceWithUnit(commercialProposalProduce.getProduce().getId());
    }
    lpr_comment = commercialProposalProduce.getLpr_comment();

    sale_price = commercialProposalProduce.getSale_price();
    sale_cost = commercialProposalProduce.getSale_cost();
    parking_trans = commercialProposalProduce.getParking_trans();
    sale_price_parking_trans = commercialProposalProduce.getSale_price_parking_trans();
    sale_cost_parking_trans = commercialProposalProduce.getSale_cost_parking_trans();
    custom_duty = commercialProposalProduce.getCustom_duty();
    sale_price_parking_trans_custom = commercialProposalProduce.getSale_price_parking_trans_custom();
    sale_cost_parking_trans_custom = commercialProposalProduce.getSale_cost_parking_trans_custom();
    nds = commercialProposalProduce.getNds();
    cost_nds = commercialProposalProduce.getCost_nds();

    lpr_price_brutto_print = commercialProposalProduce.getLpr_price_brutto_print();
    lpr_discount_print = commercialProposalProduce.getLpr_discount_print();
    lpr_price_netto_print = commercialProposalProduce.getLpr_price_netto_print();
    sale_price_print = commercialProposalProduce.getSale_price_print();
    sale_cost_print = commercialProposalProduce.getSale_cost_print();
    parking_trans_print = commercialProposalProduce.getParking_trans_print();
    sale_price_parking_trans_print = commercialProposalProduce.getSale_price_parking_trans_print();
    sale_cost_parking_trans_print = commercialProposalProduce.getSale_cost_parking_trans_print();
    custom_duty_print = commercialProposalProduce.getCustom_duty_print();
    sale_price_parking_trans_custom_print = commercialProposalProduce.getSale_price_parking_trans_custom_print();
    sale_cost_parking_trans_custom_print = commercialProposalProduce.getSale_cost_parking_trans_custom_print();
    nds_print = commercialProposalProduce.getNds_print();
    cost_nds_print = commercialProposalProduce.getCost_nds_print();

    lpc_id = commercialProposalProduce.getLpc_id();
    lpc_cost_one_by = commercialProposalProduce.getLpc_cost_one_by();
    lpc_price_list_by = commercialProposalProduce.getLpc_price_list_by();
    lpc_1c_number = commercialProposalProduce.getLpc_1c_number();
    extra_percent = commercialProposalProduce.getExtra_percent();
    discount_percent = commercialProposalProduce.getDiscount_percent();

    outValue = commercialProposalProduce.getOutValue();
    doubleSums = commercialProposalProduce.isDoubleSums();
    needRoundPrint = commercialProposalProduce.isNeedRoundPrint();
    itogLine = commercialProposalProduce.isItogLine();
    incoTermCaseA = commercialProposalProduce.isIncoTermCaseA();

    sumLprPrice = commercialProposalProduce.getSumLprPrice();
    rest_lpc_count = commercialProposalProduce.getRest_lpc_count();
  }

  public String getLpr_id()
  {
    return lpr_id;
  }

  public void setLpr_id(String lpr_id)
  {
    this.lpr_id = lpr_id;
  }

  public String getCpr_id()
  {
    return cpr_id;
  }

  public void setCpr_id(String cpr_id)
  {
    this.cpr_id = cpr_id;
  }

  public String getCpr_date()
  {
    return cpr_date;
  }

  public void setCpr_date(String cpr_date)
  {
    this.cpr_date = cpr_date;
  }

  public String getNumber()
  {
    return number;
  }

  public void setNumber(String number)
  {
    this.number = number;
  }

  public String getLpr_produce_name()
  {
    return lpr_produce_name;
  }

  public String getProduce_name()
  {
    if (isOldVersion())
    {
      return getLpr_produce_name();
    }
    else
    {
      return produce.getFullName();
    }
  }

  public String getProduceNamePrint()
  {
    return getProduce_name() + (StringUtil.isEmpty(getLpr_comment()) ? "" : "\n" + getLpr_comment());
  }

  public void setLpr_produce_name(String lpr_produce_name)
  {
    this.lpr_produce_name = lpr_produce_name;
  }

  public String getLpr_catalog_num()
  {
    return lpr_catalog_num;
  }

  public String getCatalog_num()
  {
    if (isOldVersion())
    {
      return getLpr_catalog_num();
    }
    else
    {
      return getCatalogNumberForStuffCategory();
    }
  }

  public String getUnit()
  {
    if (isOldVersion())
    {
      return "";
    }
    else
    {
      return produce.getUnit().getName();
    }
  }

  public void setLpr_catalog_num(String lpr_catalog_num)
  {
    this.lpr_catalog_num = lpr_catalog_num;
  }

  public double getLpr_price_brutto()
  {
    return lpr_price_brutto;
  }

  public String getPriceBruttoFormatted()
  {
    return StringUtil.double2appCurrencyString(getLpr_price_brutto());
  }

  public void setLpr_price_brutto(double lpr_price_brutto)
  {
    this.lpr_price_brutto = lpr_price_brutto;
  }

  public double getLpr_discount()
  {
    return lpr_discount;
  }

  public String getDiscountFormatted()
  {
    return StringUtil.double2appCurrencyString(getLpr_discount());
  }

  public void setLpr_discount(double lpr_discount)
  {
    this.lpr_discount = lpr_discount;
  }

  public double getLpr_price_netto()
  {
    return lpr_price_netto;
  }

  public String getPriceNettoFormatted()
  {
    return StringUtil.double2appCurrencyString(getLpr_price_netto());
  }

  public void setLpr_price_netto(double lpr_price_netto)
  {
    this.lpr_price_netto = lpr_price_netto;
  }

  public double getLpr_count()
  {
    return lpr_count;
  }

  public String getLpr_count_formatted()
  {
    return StringUtil.double2appCurrencyString(lpr_count);
  }

  public void setLpr_count(double lpr_count)
  {
    this.lpr_count = lpr_count;
  }

  public CustomCode getCustomCode()
  {
    return customCode;
  }

  public void setCustomCode(CustomCode customCode)
  {
    this.customCode = customCode;
  }

  public String getCode()
  {
    if (isOldVersion())
    {
      return customCode.getCode();
    }
    else
    {
      DboCustomCode cusCode = getCusCode();
      if (null != cusCode)
        return cusCode.getCode();
      else
        return customCode.getCode();
    }
  }

  public DboCustomCode getCusCode()
  {
    DboCustomCode cusCode = null;
    try
    {
      cusCode = getProduce().getCustomCodeForDate(StringUtil.appDateString2Date(getCpr_date()), NomenclatureProduceCustomCodeHistoryHelper.loadLastCustomCodeByDateAndProduce(getCpr_date(), getProduce().getId()));
    }
    catch (Exception e)
    {
      log.error(e);
    }
    return cusCode;
  }

  public String getCustomPercentFormatted()
  {
    if (isOldVersion())
    {
      return customCode.getCustom_percent_formatted();
    }
    else
    {
      DboCustomCode cusCode = getCusCode();
      if (null != cusCode)
        return StringUtil.double2appCurrencyString(cusCode.getPercent().doubleValue());
      else
        return customCode.getCustom_percent_formatted();
    }
  }

  public double getCustomPercent()
  {
    if (isOldVersion())
    {
      return customCode.getCustom_percent();
    }
    else
    {
      DboCustomCode cusCode = getCusCode();
      if (null != cusCode)
        return cusCode.getPercent().doubleValue();
      else
        return customCode.getCustom_percent();
    }
  }

  public double getLpr_coeficient()
  {
    return lpr_coeficient;
  }

  public String getLpr_coeficient_formatted()
  {
    String coefficient = StringUtil.double2appCurrencyStringByMask(getLpr_coeficient(), "#,##0.00000000");
    int i = 0;
    for (; i < 4; i++)
    {
      if (coefficient.charAt(coefficient.length() - i - 1) != '0')
      {
        break;
      }
    }
    return coefficient.substring(0, coefficient.length() - i);
  }

  public void setLpr_coeficient(double lpr_coeficient)
  {
    this.lpr_coeficient = lpr_coeficient;
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

  public String getLpr_comment()
  {
    return lpr_comment;
  }

  public void setLpr_comment(String lpr_comment)
  {
    this.lpr_comment = lpr_comment;
  }

  public double getSale_price()
  {
    return sale_price;
  }

  public String getSale_price_formatted()
  {
    String retStr = StringUtil.double2appCurrencyString(sale_price);
    if (isDoubleSums())
    {
      retStr += "<br><span style=\"color:green\">" + getSale_price_print_formatted(isNeedRoundPrint()) + "</span>";
    }

    return retStr;
  }

  public void setSale_price(double sale_price)
  {
    this.sale_price = sale_price;
  }

  public double getSale_cost()
  {
    return sale_cost;
  }

  public String getSale_cost_formatted()
  {
    String retStr = StringUtil.double2appCurrencyString(sale_cost);

    //показываем итоговую строку без зеленой суммы, но только если не случай А, когда нужно показать все итоговые для колонки.
    if (isItogLine() && !isIncoTermCaseA())
    {
      return retStr;
    }

    if (isDoubleSums())
    {
      retStr += "<br><span style=\"color:green\">" + getSale_cost_print_formatted(isNeedRoundPrint()) + "</span>";
    }

    return retStr;
  }

  public void setSale_cost(double sale_cost)
  {
    this.sale_cost = sale_cost;
  }

  public double getParking_trans()
  {
    return parking_trans;
  }

  public String getParking_trans_formatted()
  {
    return StringUtil.double2appCurrencyString(parking_trans);
  }

  public void setParking_trans(double parking_trans)
  {
    this.parking_trans = parking_trans;
  }

  public double getSale_price_parking_trans()
  {
    return sale_price_parking_trans;
  }

  public String getSale_price_parking_trans_formatted()
  {
    String retStr = StringUtil.double2appCurrencyString(sale_price_parking_trans);
    if (isDoubleSums())
    {
      retStr += "<br><span style=\"color:green\">" + getSale_price_parking_trans_print_formatted(isNeedRoundPrint()) + "</span>";
    }

    return retStr;
  }

  public void setSale_price_parking_trans(double sale_price_parking_trans)
  {
    this.sale_price_parking_trans = sale_price_parking_trans;
  }

  public double getSale_cost_parking_trans()
  {
    return sale_cost_parking_trans;
  }

  public String getSale_cost_parking_trans_formatted()
  {
    String retStr = StringUtil.double2appCurrencyString(sale_cost_parking_trans);
    if (isDoubleSums())
    {
      retStr += "<br><span style=\"color:green\">" + getSale_cost_parking_trans_print_formatted(isNeedRoundPrint()) + "</span>";
    }

    return retStr;
  }

  public void setSale_cost_parking_trans(double sale_cost_parking_trans)
  {
    this.sale_cost_parking_trans = sale_cost_parking_trans;
    this.lpr_sale_cost_parking_trans = sale_cost_parking_trans;
  }

  public double getCustom_duty()
  {
    return custom_duty;
  }

  public String getCustom_duty_formatted()
  {
    return StringUtil.double2appCurrencyString(custom_duty);
  }

  public void setCustom_duty(double custom_duty)
  {
    this.custom_duty = custom_duty;
  }

  public double getSale_price_parking_trans_custom()
  {
    return sale_price_parking_trans_custom;
  }

  public String getSale_price_parking_trans_custom_formatted()
  {
    String retStr = StringUtil.double2appCurrencyString(sale_price_parking_trans_custom);
    if (isDoubleSums())
    {
      retStr += "<br><span style=\"color:green\">" + getSale_price_parking_trans_custom_print_formatted(isNeedRoundPrint()) + "</span>";
    }

    return retStr;
  }

  public void setSale_price_parking_trans_custom_formatted(String sale_price_parking_trans_custom)
  {
    this.sale_price_parking_trans_custom = StringUtil.appCurrencyString2double(sale_price_parking_trans_custom);
    this.lpr_sale_price = this.sale_price_parking_trans_custom;
  }

  public void setSale_price_parking_trans_custom(double sale_price_parking_trans_custom)
  {
    this.sale_price_parking_trans_custom = sale_price_parking_trans_custom;
  }

  public double getSale_cost_parking_trans_custom()
  {
    return sale_cost_parking_trans_custom;
  }

  public void setLpr_sale_price(double lpr_sale_price)
  {
    this.lpr_sale_price = lpr_sale_price;
  }

  public double getLpr_sale_price()
  {
    return lpr_sale_price;
  }

  public double getLpr_sale_cost_parking_trans()
  {
    return lpr_sale_cost_parking_trans;
  }

  public void setLpr_sale_cost_parking_trans(double lpr_sale_cost_parking_trans)
  {
    this.lpr_sale_cost_parking_trans = lpr_sale_cost_parking_trans;
  }

  public String getSale_cost_parking_trans_custom_formatted()
  {
    String retStr = StringUtil.double2appCurrencyString(sale_cost_parking_trans_custom);
    if (isDoubleSums())
    {
      retStr += "<br><span style=\"color:green\">" + getSale_cost_parking_trans_custom_print_formatted(isNeedRoundPrint()) + "</span>";
    }

    return retStr;
  }

  public void setSale_cost_parking_trans_custom(double sale_cost_parking_trans_custom)
  {
    this.sale_cost_parking_trans_custom = sale_cost_parking_trans_custom;
  }

  public double getNds()
  {
    return nds;
  }

  public String getNds_formatted()
  {
    String retStr;
    retStr = StringUtil.double2appCurrencyString(nds);
    if (isDoubleSums())
    {
      retStr += "<br><span style=\"color:green\">" + getNds_print_formatted(isNeedRoundPrint()) + "</span>";
    }

    return retStr;
  }

  public void setNds(double nds)
  {
    this.nds = nds;
  }

  public double getCost_nds()
  {
    return cost_nds;
  }

  public String getCost_nds_formatted()
  {
    String retStr;
    retStr = StringUtil.double2appCurrencyString(getCost_nds());
    if (isDoubleSums())
    {
      retStr += "<br><span style=\"color:green\">" + getCost_nds_print_formatted(isNeedRoundPrint()) + "</span>";
    }

    return retStr;
  }

  public void setCost_nds(double cost_nds)
  {
    this.cost_nds = cost_nds;
  }

  public double getLpr_price_brutto_print()
  {
    return lpr_price_brutto_print;
  }

  public void setLpr_price_brutto_print(double lpr_price_brutto_print)
  {
    this.lpr_price_brutto_print = lpr_price_brutto_print;
  }

  public double getLpr_discount_print()
  {
    return lpr_discount_print;
  }

  public void setLpr_discount_print(double lpr_discount_print)
  {
    this.lpr_discount_print = lpr_discount_print;
  }

  public double getLpr_price_netto_print()
  {
    return lpr_price_netto_print;
  }

  public void setLpr_price_netto_print(double lpr_price_netto_print)
  {
    this.lpr_price_netto_print = lpr_price_netto_print;
  }

  public double getSale_price_print()
  {
    return sale_price_print;
  }

  public String getSale_price_print_formatted(boolean needRound)
  {
    if (needRound)
    {
      return StringUtil.double2appCurrencyStringWithoutDec(sale_price_print);
    }

    return StringUtil.double2appCurrencyString(sale_price_print);
  }

  public void setSale_price_print(double sale_price_print)
  {
    this.sale_price_print = sale_price_print;
  }

  public double getSale_cost_print()
  {
    return sale_cost_print;
  }

  public String getSale_cost_print_formatted(boolean needRound)
  {
    if (needRound)
    {
      return StringUtil.double2appCurrencyStringWithoutDec(sale_cost_print);
    }

    return StringUtil.double2appCurrencyString(sale_cost_print);
  }

  public void setSale_cost_print(double sale_cost_print)
  {
    this.sale_cost_print = sale_cost_print;
  }

  public double getParking_trans_print()
  {
    return parking_trans_print;
  }

  public String getParking_trans_print_formatted(boolean needRound)
  {
    if (needRound)
    {
      return StringUtil.double2appCurrencyStringWithoutDec(parking_trans_print);
    }

    return StringUtil.double2appCurrencyString(parking_trans_print);
  }

  public void setParking_trans_print(double parking_trans_print)
  {
    this.parking_trans_print = parking_trans_print;
  }

  public double getSale_price_parking_trans_print()
  {
    return sale_price_parking_trans_print;
  }

  public String getSale_price_parking_trans_print_formatted(boolean needRound)
  {
    if (needRound)
    {
      return StringUtil.double2appCurrencyStringWithoutDec(sale_price_parking_trans_print);
    }

    return StringUtil.double2appCurrencyString(sale_price_parking_trans_print);
  }

  public void setSale_price_parking_trans_print(double sale_price_parking_trans_print)
  {
    this.sale_price_parking_trans_print = sale_price_parking_trans_print;
  }

  public double getSale_cost_parking_trans_print()
  {
    return sale_cost_parking_trans_print;
  }

  public String getSale_cost_parking_trans_print_formatted(boolean needRound)
  {
    if (needRound)
    {
      return StringUtil.double2appCurrencyStringWithoutDec(sale_cost_parking_trans_print);
    }

    return StringUtil.double2appCurrencyString(sale_cost_parking_trans_print);
  }

  public void setSale_cost_parking_trans_print(double sale_cost_parking_trans_print)
  {
    this.sale_cost_parking_trans_print = sale_cost_parking_trans_print;
  }

  public double getCustom_duty_print()
  {
    return custom_duty_print;
  }

  public String getCustom_duty_print_formatted(boolean needRound)
  {
    if (needRound)
    {
      return StringUtil.double2appCurrencyStringWithoutDec(custom_duty_print);
    }

    return StringUtil.double2appCurrencyString(custom_duty_print);
  }

  public void setCustom_duty_print(double custom_duty_print)
  {
    this.custom_duty_print = custom_duty_print;
  }

  public double getSale_price_parking_trans_custom_print()
  {
    return sale_price_parking_trans_custom_print;
  }

  public String getSale_price_parking_trans_custom_print_formatted(boolean needRound)
  {
    if (needRound)
    {
      return StringUtil.double2appCurrencyStringWithoutDec(sale_price_parking_trans_custom_print);
    }

    return StringUtil.double2appCurrencyString(sale_price_parking_trans_custom_print);
  }

  public void setSale_price_parking_trans_custom_print(double sale_price_parking_trans_custom_print)
  {
    this.sale_price_parking_trans_custom_print = sale_price_parking_trans_custom_print;
  }

  public double getSale_cost_parking_trans_custom_print()
  {
    return sale_cost_parking_trans_custom_print;
  }

  public String getSale_cost_parking_trans_custom_print_formatted(boolean needRound)
  {
    if (needRound)
    {
      return StringUtil.double2appCurrencyStringWithoutDec(sale_cost_parking_trans_custom_print);
    }

    return StringUtil.double2appCurrencyString(sale_cost_parking_trans_custom_print);
  }

  public void setSale_cost_parking_trans_custom_print(double sale_cost_parking_trans_custom_print)
  {
    this.sale_cost_parking_trans_custom_print = sale_cost_parking_trans_custom_print;
  }

  public double getNds_print()
  {
    return nds_print;
  }

  public String getNds_print_formatted(boolean needRound)
  {
    if (needRound)
    {
      return StringUtil.double2appCurrencyStringWithoutDec(getNds_print());
    }

    return StringUtil.double2appCurrencyString(getNds_print());
  }

  public void setNds_print(double nds_print)
  {
    this.nds_print = nds_print;
  }

  public double getCost_nds_print()
  {
    return cost_nds_print;
  }

  public String getCost_nds_print_formatted(boolean needRound)
  {
    if (needRound)
    {
      return StringUtil.double2appCurrencyStringWithoutDec(getCost_nds_print());
    }

    return StringUtil.double2appCurrencyString(getCost_nds_print());
  }

  public void setCost_nds_print(double cost_nds_print)
  {
    this.cost_nds_print = cost_nds_print;
  }

  public String getLpc_id()
  {
    return lpc_id;
  }

  public void setLpc_id(String lpc_id)
  {
    this.lpc_id = lpc_id;
  }

  public double getLpc_cost_one_by()
  {
    return lpc_cost_one_by;
  }

  public String getLpc_cost_one_by_formatted()
  {
    return StringUtil.double2appCurrencyString(lpc_cost_one_by);
  }

  public void setLpc_cost_one_by(double lpc_cost_one_by)
  {
    this.lpc_cost_one_by = lpc_cost_one_by;
  }

  public double getLpc_price_list_by()
  {
    return lpc_price_list_by;
  }

  public String getLpc_price_list_by_formatted()
  {
    return StringUtil.double2appCurrencyString(lpc_price_list_by);
  }

  public String getLpc_price_list_by_print_formatted(boolean needRound)
  {
    if (needRound)
    {
      return StringUtil.double2appCurrencyStringWithoutDec(lpc_price_list_by);
    }

    return StringUtil.double2appCurrencyString(lpc_price_list_by);
  }

  public void setLpc_price_list_by(double lpc_price_list_by)
  {
    this.lpc_price_list_by = lpc_price_list_by;
  }

  public String getLpc_1c_number()
  {
    return lpc_1c_number;
  }

  public void setLpc_1c_number(String lpc_1c_number)
  {
    this.lpc_1c_number = lpc_1c_number;
  }

  public double getExtra_percent()
  {
    return extra_percent;
  }

  public String getExtra_percent_formatted()
  {
    String retStr;
    retStr = StringUtil.double2appCurrencyStringByMask(extra_percent, "#,##0.00000");
    if (lpr_sale_price < lpc_cost_one_by)
    {
      retStr = "<span style=\"color:red\">" + retStr + "</span>";
    }
    return retStr;
  }

  public void setExtra_percent(double extra_percent)
  {
    this.extra_percent = extra_percent;
  }

  public double getDiscount_percent()
  {
    return discount_percent;
  }

  public String getDiscount_percent_formatted()
  {
    return StringUtil.double2appCurrencyStringByMask(discount_percent, "#,##0.00000");
  }

  public void setDiscount_percent(double discount_percent)
  {
    this.discount_percent = discount_percent;
  }

  public double getOutValue()
  {
    return outValue;
  }

  public void setOutValue(double outValue)
  {
    this.outValue = outValue;
  }

  public String getTotalText()
  {
    return totalText;
  }

  public void setTotalText(String totalText)
  {
    this.totalText = totalText;
  }

  public boolean isDoubleSums()
  {
    return doubleSums;
  }

  public void setDoubleSums(boolean doubleSums)
  {
    this.doubleSums = doubleSums;
  }

  public boolean isNeedRoundPrint()
  {
    return needRoundPrint;
  }

  public void setNeedRoundPrint(boolean needRoundPrint)
  {
    this.needRoundPrint = needRoundPrint;
  }

  public boolean isItogLine()
  {
    return itogLine;
  }

  public void setItogLine(boolean itogLine)
  {
    this.itogLine = itogLine;
  }

  public boolean isIncoTermCaseA()
  {
    return incoTermCaseA;
  }

  public void setIncoTermCaseA(boolean incoTermCaseA)
  {
    this.incoTermCaseA = incoTermCaseA;
  }

  public double getRest_lpc_count()
  {
    return rest_lpc_count;
  }

  public void setRest_lpc_count(double rest_lpc_count)
  {
    this.rest_lpc_count = rest_lpc_count;
  }

  public double getSumLprPrice()
  {
    return sumLprPrice;
  }

  public String getSumLprPriceFormatted()
  {
    return StringUtil.double2appCurrencyString(sumLprPrice);
  }

  public void setSumLprPrice(double sumLprPrice)
  {
    this.sumLprPrice = sumLprPrice;
  }

  public String getCalculatedField()
  {
    return calculatedField;
  }

  public void setCalculatedField(String calculatedField)
  {
    this.calculatedField = calculatedField;
  }

  public String getOutValueFormated(boolean needRound)
  {
    if (needRound)
    {
      return StringUtil.double2appCurrencyStringWithoutDec(outValue);
    }

    return StringUtil.double2appCurrencyString(outValue);
  }

  public boolean isOldVersion()
  {
    return isEmptyProduce();
  }

  public boolean isEmptyProduce()
  {
    return (null == getProduce() || null == getProduce().getId());
  }

  public String getCatalogNumberForStuffCategory()
  {
    if (null == getProduce() || null == getProduce().getId() || StringUtil.isEmpty(getStuffCategory().getId()))
    {
      return "";
    }

    produce = (DboProduce) HibernateUtil.associateWithCurentSession(produce);
    DboCatalogNumber catalogNumber = produce.getCatalogNumbers().get(new Integer(getStuffCategory().getId()));
    return catalogNumber == null ? null : catalogNumber.getNumber();
  }

  public static double getRoundSum(double sum, boolean fRound)
  {
    double retSum = StringUtil.roundN(sum, 2);
    if (fRound)
    {
      retSum = Math.round(retSum);
    }

    return retSum;
  }
}
