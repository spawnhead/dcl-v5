package net.sam.dcl.form;

import net.sam.dcl.controller.BaseDispatchValidatorForm;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.controller.ActionContext;
import net.sam.dcl.beans.*;
import net.sam.dcl.dbo.DboProduce;
import net.sam.dcl.dbo.DboCustomCode;
import net.sam.dcl.service.helper.NomenclatureProduceCustomCodeHistoryHelper;
import net.sam.dcl.util.HibernateUtil;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.StrutsUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author: DG
 * Date: Aug 6, 2005
 * Time: 5:37:29 PM
 */
public class CommercialProposalProduceForm extends BaseDispatchValidatorForm
{
  protected static Log log = LogFactory.getLog(CommercialProposalProduceForm.class);
  String numberBefore;
  String number;
  String lpr_id;
  String cpr_id;
  String lpr_produce_name;
  String lpr_catalog_num;
  String lpr_price_brutto;
  String lpr_discount;
  String lpr_price_netto;
  String lpr_count;
  String cus_id;
  CustomCode custom_code = new CustomCode();
  String lpr_coeficient;
  String lpr_sale_price;
  String lpr_rate_nds;
  StuffCategory stuffCategory = new StuffCategory();
  DboProduce produce = new DboProduce();
  String lpr_comment;

  String cpr_date;
  String cpr_old_version;
  String cpr_reverse_calc;
  String cur_id;
  String cur_name;

  String donot_calculate_netto;

  boolean oldVersion = false;

  public String getNumberBefore()
  {
    return numberBefore;
  }

  public void setNumberBefore(String numberBefore)
  {
    this.numberBefore = numberBefore;
  }

  public String getNumber()
  {
    return number;
  }

  public void setNumber(String number)
  {
    this.number = number;
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

  public String getLpr_produce_name()
  {
    return lpr_produce_name;
  }

  public void setLpr_produce_name(String lpr_produce_name)
  {
    this.lpr_produce_name = lpr_produce_name;
  }

  public String getLpr_catalog_num()
  {
    return lpr_catalog_num;
  }

  public void setLpr_catalog_num(String lpr_catalog_num)
  {
    this.lpr_catalog_num = lpr_catalog_num;
  }

  public String getLpr_price_brutto()
  {
    return lpr_price_brutto;
  }

  public void setLpr_price_brutto(String lpr_price_brutto)
  {
    this.lpr_price_brutto = lpr_price_brutto;
  }

  public String getLpr_discount()
  {
    return lpr_discount;
  }

  public void setLpr_discount(String lpr_discount)
  {
    this.lpr_discount = lpr_discount;
  }

  public String getLpr_price_netto()
  {
    return lpr_price_netto;
  }

  public void setLpr_price_netto(String lpr_price_netto)
  {
    this.lpr_price_netto = lpr_price_netto;
  }

  public String getLpr_count()
  {
    return lpr_count;
  }

  public void setLpr_count(String lpr_count)
  {
    this.lpr_count = lpr_count;
  }

  public String getCus_id()
  {
    return cus_id;
  }

  public void setCus_id(String cus_id)
  {
    this.cus_id = cus_id;
  }

  public CustomCode getCustom_code()
  {
    return custom_code;
  }

  public void setCustom_code(CustomCode custom_code)
  {
    this.custom_code = custom_code;
  }

  public String getLpr_coeficient()
  {
    return lpr_coeficient;
  }

  public void setLpr_coeficient(String lpr_coeficient)
  {
    this.lpr_coeficient = lpr_coeficient;
  }

  public String getLpr_sale_price()
  {
    return lpr_sale_price;
  }

  public String getLpr_sale_price_text()
  {
    IActionContext context = ActionContext.threadInstance();

    String retStr = "";
    try
    {
      retStr = StrutsUtil.getMessage(context, "CommercialProposalProduce.lpr_sale_price", cur_name);
    }
    catch (Exception e)
    {
      log.error(e);
    }

    return retStr;
  }

  public void setLpr_sale_price(String lpr_sale_price)
  {
    this.lpr_sale_price = lpr_sale_price;
  }

  public String getLpr_rate_nds()
  {
    return lpr_rate_nds;
  }

  public void setLpr_rate_nds(String lpr_rate_nds)
  {
    this.lpr_rate_nds = lpr_rate_nds;
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

  public String getCpr_date()
  {
    return cpr_date;
  }

  public void setCpr_date(String cpr_date)
  {
    this.cpr_date = cpr_date;
  }

  public String getCpr_old_version()
  {
    return cpr_old_version;
  }

  public void setCpr_old_version(String cpr_old_version)
  {
    this.cpr_old_version = cpr_old_version;
  }

  public String getCpr_reverse_calc()
  {
    return cpr_reverse_calc;
  }

  public boolean isReverseCalc()
  {
    return !StringUtil.isEmpty(cpr_reverse_calc);
  }

  public void setCpr_reverse_calc(String cpr_reverse_calc)
  {
    this.cpr_reverse_calc = cpr_reverse_calc;
  }

  public String getCur_id()
  {
    return cur_id;
  }

  public void setCur_id(String cur_id)
  {
    this.cur_id = cur_id;
  }

  public String getCur_name()
  {
    return cur_name;
  }

  public void setCur_name(String cur_name)
  {
    this.cur_name = cur_name;
  }

  public String getDonot_calculate_netto()
  {
    return donot_calculate_netto;
  }

  public void setDonot_calculate_netto(String donot_calculate_netto)
  {
    this.donot_calculate_netto = donot_calculate_netto;
  }

  public boolean isCalculateNetto()
  {
    return StringUtil.isEmpty(getDonot_calculate_netto());
  }
  
  public boolean isHaveProduceButEmptyCusCode()
  {
    if (!isEmptyProduce())
    {
      produce = (DboProduce) HibernateUtil.associateWithCurentSession(produce);
      DboCustomCode cusCode = null;
      try
      {
        cusCode = produce.getCustomCodeForDate(StringUtil.appDateString2Date(cpr_date), NomenclatureProduceCustomCodeHistoryHelper.loadLastCustomCodeByDateAndProduce(cpr_date, produce.getId()));
      }
      catch (Exception e)
      {
        log.error(e);
      }
      if (null == cusCode || StringUtil.isEmpty(cusCode.getCode()))
      {
        return true;
      }
    }
    else
    {
      return false;
    }

    return false;
  }

  public boolean isEmptyProduce()
  {
    return (null == produce || null == produce.getId() || StringUtil.isEmpty(produce.getName()));
  }

  public boolean isEnterCustomCode()
  {
    return oldVersion || isHaveProduceButEmptyCusCode() || !StringUtil.isEmpty(getCustom_code().getCode());
  }

  public String getCustomCodeFormatted()
  {
    String retStr = "";
    if (null == produce.getId())
    {
      return retStr;
    }

    produce = (DboProduce) HibernateUtil.associateWithCurentSession(produce);
    DboCustomCode cusCode = null;
    try
    {
      cusCode = produce.getCustomCodeForDate(StringUtil.appDateString2Date(cpr_date), NomenclatureProduceCustomCodeHistoryHelper.loadLastCustomCodeByDateAndProduce(cpr_date, produce.getId()));
    }
    catch (Exception e)
    {
      log.error(e);
    }
    if (cusCode == null)
    {
      return retStr;
    }

    IActionContext context = ActionContext.threadInstance();
    try
    {
      retStr = cusCode.getCode() + " " +  StringUtil.double2appCurrencyString(cusCode.getPercent().doubleValue()) + StrutsUtil.getMessage(context, "Common.percent");
    }
    catch (Exception e)
    {
      log.error(e);
    }

    return retStr;
  }
}
