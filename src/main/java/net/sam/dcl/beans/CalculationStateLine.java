package net.sam.dcl.beans;

import net.sam.dcl.controller.ActionContext;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.StrutsUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * Date: 14.09.2005
 * Time: 17:17:20
 * To change this template use File | Settings | File Templates.
 */
public class CalculationStateLine implements Serializable
{
  protected static Log log = LogFactory.getLog(CalculationStateLine.class);

  boolean debet = false;

  int key_id;
  String con_id;
  String con_number;
  String con_date;
  String con_currency;
  String con_annul;
  String con_annul_date;
  int    con_comment_flag;
  String con_comment;
  String con_final_date;
  int    con_day_before_final;
  String con_incorrect_final_date;
  String usr_id_list_con;
  String dep_id_list_con;
  String shp_currency;
  String pay_currency;
  String spc_id;
  String spc_number;
  String spc_date;
  double spc_summ;
  String spc_add_pay_cond;
  String spc_delivery_cond;
  String spc_delivery_date;
  String spc_delivery_date_math;
  String spc_annul;
  String spc_annul_date;
  String spc_letter1_date;
  String spc_letter2_date;
  String spc_letter3_date;
  String spc_complaint_in_court_date;
  int    spc_comment_flag;
  String spc_comment;
  String spc_pay_expiration;
  String spc_whose_id;
  String spc_whose;
  int count_day;

  String shp_id;
  String shp_number;
  String shp_date;
  String shp_date_expiration;
  double shp_summ;
  double shp_saldo;
  String shp_closed;
  String shp_letter1_date;
  String shp_letter2_date;
  String shp_letter3_date;
  String shp_complaint_in_court_date;
  String shp_comment;
  String shp_original;
  String usr_id_list_shp;
  int shp_no_act;
  String managers;
  String stuff_categories;

  String pay_id;
  String pay_date;
  String pay_date_expiration;
  double pay_summ;
  String pay_closed;
  String pay_block;
  String pay_comment;
  String dates_for_produce_on_storage;
  String shp_closed_correct;
  String get_pay_expiration_from_proc;

  boolean ctr_line = false;
  boolean itog_line = false;
  boolean rest_doc_line = false;
  boolean only_spec = false;
  boolean no_shipping = false;
  boolean alone_shipping = false;

  boolean deviateLine = false;
  boolean balanceLine = false;
  boolean commonBalanceLine = false;
  boolean needFormatCon = true;

  public boolean isDebet()
  {
    return debet;
  }

  public void setDebet(boolean debet)
  {
    this.debet = debet;
  }

  public int getKey_id()
  {
    return key_id;
  }

  public void setKey_id(int key_id)
  {
    this.key_id = key_id;
  }

  public String getCon_id()
  {
    return con_id;
  }

  public void setCon_id(String con_id)
  {
    this.con_id = con_id;
  }

  public String getCon_number()
  {
    return con_number;
  }

  public void setCon_number(String con_number)
  {
    this.con_number = con_number;
  }

  public String getCon_date()
  {
    return con_date;
  }

  public String getCon_date_formatted()
  {
    return StringUtil.dbDateString2appDateString(con_date);
  }

  public void setCon_date(String con_date)
  {
    this.con_date = con_date;
  }

  public String getCon_number_date()
  {
    if (StringUtil.isEmpty(getCon_number()) && StringUtil.isEmpty(getCon_date_formatted()))
    {
      return "";
    }

    if ( isCtr_line() )
    {
      return getCon_number();
    }

    String retStr;
    IActionContext context = ActionContext.threadInstance();

    if ( !isNeedFormatCon() )
    {
      retStr = getCon_number();
    }
    else
    {
      retStr = "";
      try
      {
        retStr = StrutsUtil.getMessage(context, "msg.common.from", getCon_number(), getCon_date_formatted());
        if ( !StringUtil.isEmpty(getCon_annul()) )
        {
          String annulDateStr = StrutsUtil.getMessage(context, "CalculationState.na_expiration");
          if ( !StringUtil.isEmpty(getCon_annul_date()) )
          {
            annulDateStr = getConAnnulDateFormatted();
          }
          annulDateStr = " " + annulDateStr;
          retStr += " " + StrutsUtil.getMessage(context, "Common.redBold", StrutsUtil.getMessage(context, "CalculationState.annul") + annulDateStr);
        }
        if ( !StringUtil.isEmpty(getCon_final_date()) )
        {
          if ( getCon_day_before_final() > 0 && getCon_day_before_final() <= 35 )
          {
            retStr += (StringUtil.isEmpty(retStr) ? "" : ReportDelimiterConsts.html_separator) + StrutsUtil.getMessage(context, "CalculationState.incorrectFinalDate0_35", Integer.toString(getCon_day_before_final()), getCon_final_date_formatted());
          }
          if ( getCon_day_before_final() <= 0 )
          {
            retStr += (StringUtil.isEmpty(retStr) ? "" : ReportDelimiterConsts.html_separator) + StrutsUtil.getMessage(context, "CalculationState.incorrectFinalDateLess0", getCon_final_date_formatted());
          }
        }
      }
      catch (Exception e)
      {
        log.error(e);
      }
    }

    return retStr;
  }

  public String getCon_currency()
  {
    return con_currency;
  }

  public void setCon_currency(String con_currency)
  {
    this.con_currency = con_currency;
  }

  public String getCon_annul()
  {
    return con_annul;
  }

  public void setCon_annul(String con_annul)
  {
    this.con_annul = con_annul;
  }

  public String getCon_annul_date()
  {
    return con_annul_date;
  }

  public String getConAnnulDateFormatted()
  {
    return StringUtil.dbDateString2appDateString(getCon_annul_date());
  }

  public void setCon_annul_date(String con_annul_date)
  {
    this.con_annul_date = con_annul_date;
  }

  public int getCon_comment_flag()
  {
    return con_comment_flag;
  }

  public void setCon_comment_flag(int con_comment_flag)
  {
    this.con_comment_flag = con_comment_flag;
  }

  public String getCon_comment()
  {
    return con_comment;
  }

  public void setCon_comment(String con_comment)
  {
    this.con_comment = con_comment;
  }

  public String getCon_final_date()
  {
    return con_final_date;
  }

  public String getCon_final_date_formatted()
  {
    return StringUtil.dbDateString2appDateString(getCon_final_date());
  }

  public void setCon_final_date(String con_final_date)
  {
    this.con_final_date = con_final_date;
  }

  public int getCon_day_before_final()
  {
    return con_day_before_final;
  }

  public void setCon_day_before_final(int con_day_before_final)
  {
    this.con_day_before_final = con_day_before_final;
  }

  public String getCon_incorrect_final_date()
  {
    return con_incorrect_final_date;
  }

  public void setCon_incorrect_final_date(String con_incorrect_final_date)
  {
    this.con_incorrect_final_date = con_incorrect_final_date;
  }

  public String getUsr_id_list_con()
  {
    return usr_id_list_con;
  }

  public void setUsr_id_list_con(String usr_id_list_con)
  {
    this.usr_id_list_con = usr_id_list_con;
  }

  public String getDep_id_list_con()
  {
    return dep_id_list_con;
  }

  public void setDep_id_list_con(String dep_id_list_con)
  {
    this.dep_id_list_con = dep_id_list_con;
  }

  public String getShp_currency()
  {
    return shp_currency;
  }

  public void setShp_currency(String shp_currency)
  {
    this.shp_currency = shp_currency;
  }

  public String getPay_currency()
  {
    return pay_currency;
  }

  public void setPay_currency(String pay_currency)
  {
    this.pay_currency = pay_currency;
  }

  public String getSpc_id()
  {
    return spc_id;
  }

  public void setSpc_id(String spc_id)
  {
    this.spc_id = spc_id;
  }

  public String getSpc_number()
  {
    return spc_number;
  }

  public void setSpc_number(String spc_number)
  {
    this.spc_number = spc_number;
  }

  public String getSpc_date()
  {
    return spc_date;
  }

  public String getSpc_date_formatted()
  {
    return StringUtil.dbDateString2appDateString(spc_date);
  }

  public void setSpc_date(String spc_date)
  {
    this.spc_date = spc_date;
  }

  public String getSpc_number_date()
  {
    if (StringUtil.isEmpty(getSpc_number()) && StringUtil.isEmpty(getSpc_date_formatted()))
    {
      return "";
    }

    String retStr = "";
    IActionContext context = ActionContext.threadInstance();

    try
    {
      retStr = StrutsUtil.getMessage(context, "msg.common.from", getSpc_number(), getSpc_date_formatted());
      if ( !StringUtil.isEmpty(getSpc_annul()) )
      {
        String annulDateStr = StrutsUtil.getMessage(context, "CalculationState.na_expiration");
        if ( !StringUtil.isEmpty(getSpc_annul_date()) )
        {
          annulDateStr = getSpc_annul_date_formatted();
        }
        annulDateStr = " " + annulDateStr;
        retStr += " " + StrutsUtil.getMessage(context, "Common.redBold", StrutsUtil.getMessage(context, "CalculationState.annul") + annulDateStr);
      }
    }
    catch (Exception e)
    {
      log.error(e);
    }

    return retStr;
  }

  public double getSpc_summ()
  {
    return spc_summ;
  }

  public String getSpc_summ_formatted()
  {
    if (spc_summ == 0 && StringUtil.isEmpty(getSpc_number_date()))
    {
      return "";
    }

    return StringUtil.double2appCurrencyString(spc_summ);
  }

  public void setSpc_summ(double spc_summ)
  {
    this.spc_summ = spc_summ;
  }

  public String getSpc_add_pay_condExcel()
  {
    return spc_add_pay_cond;
  }

  public String getSpc_add_pay_cond()
  {
    String retStr = getSpc_add_pay_condExcel();

    if (!StringUtil.isEmpty(retStr))
    {
      retStr = retStr.replaceAll("\r\n", ReportDelimiterConsts.html_separator);
    }

    return retStr;
  }

  public void setSpc_add_pay_cond(String spc_add_pay_cond)
  {
    this.spc_add_pay_cond = spc_add_pay_cond;
  }

  public String getSpc_delivery_condExcel()
  {
    return spc_delivery_cond;
  }

  public String getSpc_delivery_cond()
  {
    String retStr = getSpc_delivery_condExcel();

    if (!StringUtil.isEmpty(retStr))
    {
      retStr = retStr.replaceAll("\r\n", ReportDelimiterConsts.html_separator);
    }

    return retStr;
  }

  public void setSpc_delivery_cond(String spc_delivery_cond)
  {
    this.spc_delivery_cond = spc_delivery_cond;
  }

  public String getSpc_delivery_date()
  {
    return spc_delivery_date;
  }

  public String getSpc_delivery_date_formatted()
  {
    return StringUtil.dbDateString2appDateString(spc_delivery_date);
  }

  public void setSpc_delivery_date(String spc_delivery_date)
  {
    this.spc_delivery_date = spc_delivery_date;
  }

  public String getSpc_delivery_date_math()
  {
    return spc_delivery_date_math;
  }

  public String getSpc_delivery_date_math_formatted()
  {
    return StringUtil.dbDateString2appDateString(spc_delivery_date_math);
  }

  public void setSpc_delivery_date_math(String spc_delivery_date_math)
  {
    this.spc_delivery_date_math = spc_delivery_date_math;
  }

  public String getSpc_annul()
  {
    return spc_annul;
  }

  public void setSpc_annul(String spc_annul)
  {
    this.spc_annul = spc_annul;
  }

  public String getSpc_annul_date()
  {
    return spc_annul_date;
  }

  public String getSpc_annul_date_formatted()
  {
    return StringUtil.dbDateString2appDateString(spc_annul_date);
  }

  public void setSpc_annul_date(String spc_annul_date)
  {
    this.spc_annul_date = spc_annul_date;
  }

  public String getSpc_letter1_date()
  {
    return spc_letter1_date;
  }

  public String getSpc_letter1_date_formatted()
  {
    return StringUtil.dbDateString2appDateString(spc_letter1_date);
  }

  public void setSpc_letter1_date(String spc_letter1_date)
  {
    this.spc_letter1_date = spc_letter1_date;
  }

  public String getSpc_letter2_date()
  {
    return spc_letter2_date;
  }

  public String getSpc_letter2_date_formatted()
  {
    return StringUtil.dbDateString2appDateString(spc_letter2_date);
  }

  public void setSpc_letter2_date(String spc_letter2_date)
  {
    this.spc_letter2_date = spc_letter2_date;
  }

  public String getSpc_letter3_date()
  {
    return spc_letter3_date;
  }

  public String getSpc_letter3_date_formatted()
  {
    return StringUtil.dbDateString2appDateString(spc_letter3_date);
  }

  public void setSpc_letter3_date(String spc_letter3_date)
  {
    this.spc_letter3_date = spc_letter3_date;
  }

  public String getSpc_complaint_in_court_date()
  {
    return spc_complaint_in_court_date;
  }

  public String getSpc_complaint_in_court_date_formatted()
  {
    return StringUtil.dbDateString2appDateString(spc_complaint_in_court_date);
  }

  public void setSpc_complaint_in_court_date(String spc_complaint_in_court_date)
  {
    this.spc_complaint_in_court_date = spc_complaint_in_court_date;
  }

  public int getSpc_comment_flag()
  {
    return spc_comment_flag;
  }

  public void setSpc_comment_flag(int spc_comment_flag)
  {
    this.spc_comment_flag = spc_comment_flag;
  }

  public String getSpc_comment()
  {
    return spc_comment;
  }

  public void setSpc_comment(String spc_comment)
  {
    this.spc_comment = spc_comment;
  }

  public String getSpc_pay_expiration()
  {
    if ( StringUtil.isEmpty(spc_pay_expiration) || "0".equals(spc_pay_expiration) )
    {
      return "";
    }

    IActionContext context = ActionContext.threadInstance();
    try
    {
      return StrutsUtil.getMessage(context, "CalculationState.spc_pay_expiration", spc_pay_expiration);
    }
    catch (Exception e)
    {
      log.error(e);
    }

    return "";
  }

  public String getSpc_pay_expiration_excel()
  {
    if ( StringUtil.isEmpty(spc_pay_expiration) )
    {
      return "";
    }

    IActionContext context = ActionContext.threadInstance();
    try
    {
      return StrutsUtil.getMessage(context, "CalculationState.spc_pay_expiration_excel", spc_pay_expiration);
    }
    catch (Exception e)
    {
      log.error(e);
    }

    return "";
  }

  public void setSpc_pay_expiration(String spc_pay_expiration)
  {
    this.spc_pay_expiration = spc_pay_expiration;
  }

  public String getSpc_whose_id()
  {
    return spc_whose_id;
  }

  public void setSpc_whose_id(String spc_whose_id)
  {
    this.spc_whose_id = spc_whose_id;
  }

  public String getSpc_whose()
  {
    return spc_whose;
  }

  public void setSpc_whose(String spc_whose)
  {
    this.spc_whose = spc_whose;
  }

  public int getCount_day()
  {
    return count_day;
  }

  public String getShp_expiration()
  {
    IActionContext context = ActionContext.threadInstance();
    if ( isDebet() )
    {
      if ( isItog_line() || isCtr_line() || StringUtil.isEmpty(shp_number) )
      {
        return "";
      }

      if ( StringUtil.isEmpty(getShp_date_expiration()) )
      {
        try
        {
          return StrutsUtil.getMessage(context, "CalculationState.na_expiration");
        }
        catch (Exception e)
        {
          log.error(e);
        }
      }

      if ( count_day < -3 )
      {
        return "";
      }

      if ( count_day <= 0 )
      {
        return "<b>" + Integer.toString(count_day) + "</b>";
      }

      if ( count_day > 0 )
      {
        return "<span style=\"color:red\"><b>" + Integer.toString(count_day) + "</b></span>";
      }

      return Integer.toString(count_day);
    }
    else
    {
      if ( isItog_line() || isCtr_line() )
      {
        return "";
      }

      if ( StringUtil.isEmpty(getSpc_delivery_date_math_formatted()) )
      {
        try
        {
          return StrutsUtil.getMessage(context, "CalculationState.na_expiration");
        }
        catch (Exception e)
        {
          log.error(e);
        }
      }

      /*
       * A -	срок поставки, по спецификации
       * B -	дата отгрузки, привязанной к спецификации
       * D -	текущая дата
      */
      Date deliveryDate;
      /*  п. 870
       * I. Если по спецификации нет ни одной отгрузки, то
       *    1. Если текущая дата в жёлтом диапазоне, то напротив спецификации вставлять жирным чёрным шрифтом "K" (где K=D-A). (с минусом!)
       *    2. Если текущая дата в розовом диапазоне, то напротив спецификации вставлять жирным красным шрифтом "L" (где L=D-A).
       *    3. Если "А" не установлена, то то напротив спецификации писать простым текстом (без выделения жирным) "N/A"
       *    4. Если текущая дата в зелёном диапазоне, то напротив спецификации вставлять чёрным шрифтом "K" (где K=D-A). (с минусом!)
       */
      if ( isNo_shipping() )
      {
        Date now = StringUtil.getCurrentDateTime();
        try
        {
          deliveryDate = StringUtil.appDateString2Date(getSpc_delivery_date_math_formatted());
          double days = StringUtil.getDaysBetween(deliveryDate, now);
          if ( days <= 0 && days > -7 )
          {
            return "<b>" + Integer.toString(Double.valueOf(days).intValue()) + "</b>";
          }
          else if ( days > 0 )
          {
            return StrutsUtil.getMessage(context, "Common.redBold", Integer.toString(Double.valueOf(days).intValue()));
          }
          else
          {
            return Integer.toString(Double.valueOf(days).intValue());
          }
        }
        catch (Exception e)
        {
          log.error(e);
        }
      }
      else
      {
      /*
       * II. Если по спецификации есть отгрузки, то
       *    1. Если B<=A, то напротив отгрузки вставлять нежиррым черным шрифтом "M" (где M = B-А). (с минусом!)
       *    2. Если B>A, то напротив отгрузки вставлять красным шрифтом "N" (где N = B-А).
      */
        Date shpDate;
        try
        {
          deliveryDate = StringUtil.appDateString2Date(getSpc_delivery_date_math_formatted());
          shpDate = StringUtil.appDateString2Date(getShp_date_formatted());
          double days = StringUtil.getDaysBetween(deliveryDate, shpDate);
          if ( days <= 0 )
          {
            return Integer.toString(Double.valueOf(days).intValue());
          }
          else
          {
            return "<span style=\"color:red\">" + Integer.toString(Double.valueOf(days).intValue()) + "</span>";
          }
        }
        catch (Exception e)
        {
          log.error(e);
        }
      }

      return "";
    }
  }

  public String getShp_expiration_excel()
  {
    if ( isDebet() )
    {
      if ( isItog_line() || isCtr_line() || StringUtil.isEmpty(shp_number) )
      {
        return "";
      }

      if ( StringUtil.isEmpty(getShp_date_expiration()) )
      {
        IActionContext context = ActionContext.threadInstance();
        try
        {
          return StrutsUtil.getMessage(context, "CalculationState.na_expiration");
        }
        catch (Exception e)
        {
          log.error(e);
        }
      }

      if ( count_day < -3 )
      {
        return "";
      }

      return Integer.toString(count_day);
    }
    else
    {
      if ( isItog_line() || isCtr_line() )
      {
        return "";
      }

      if ( StringUtil.isEmpty(getSpc_delivery_date_math_formatted()) )
      {
        IActionContext context = ActionContext.threadInstance();
        try
        {
          return StrutsUtil.getMessage(context, "CalculationState.na_expiration");
        }
        catch (Exception e)
        {
          log.error(e);
        }
      }

      Date deliveryDate;
      if ( isNo_shipping() )
      {
        Date now = StringUtil.getCurrentDateTime();
        try
        {
          deliveryDate = StringUtil.appDateString2Date(getSpc_delivery_date_math_formatted());
          double days = StringUtil.getDaysBetween(deliveryDate, now);
          return Integer.toString(Double.valueOf(days).intValue());
        }
        catch (Exception e)
        {
          log.error(e);
        }
      }
      else
      {
        Date shpDate;
        try
        {
          deliveryDate = StringUtil.appDateString2Date(getSpc_delivery_date_math_formatted());
          shpDate = StringUtil.appDateString2Date(getShp_date_formatted());
          double days = StringUtil.getDaysBetween(deliveryDate, shpDate);
          return Integer.toString(Double.valueOf(days).intValue());
        }
        catch (Exception e)
        {
          log.error(e);
        }
      }

      return "";
    }
  }

  public String getPay_expiration()
  {
    IActionContext context = ActionContext.threadInstance();
    if ( isItog_line() || isCtr_line() )
    {
      return "";
    }

    if ( StringUtil.isEmpty(shp_number) )
    {
      return "";
    }

    if ( StringUtil.isEmpty(getShp_date_expiration()) )
    {
      try
      {
        return StrutsUtil.getMessage(context, "CalculationState.na_expiration");
      }
      catch (Exception e)
      {
        log.error(e);
      }
    }

    if ( isNo_shipping() )
    {
      if ( isAlone_shipping() )
      {
        try
        {
          Date now = StringUtil.getCurrentDateTime();
          Date shpExpirationDate = StringUtil.appDateString2Date(getShp_date_expiration_formatted());
          int days = Double.valueOf(StringUtil.getDaysBetween(shpExpirationDate, now)).intValue();
          if ( days > 0 )
          {
            return StrutsUtil.getMessage(context, "Common.redBold", Integer.toString(days));
          }

          return Integer.toString(days);
        }
        catch (Exception e)
        {
          return "";
        }
      }
      else
      {
        try
        {
          return StrutsUtil.getMessage(context, "CalculationState.na_expiration");
        }
        catch (Exception e)
        {
          log.error(e);
        }
      }
    }

    if ( !StringUtil.isEmpty(get_pay_expiration_from_proc) || StringUtil.isEmpty(shp_closed_correct) )
    {
      if ( count_day < -3 )
      {
        return Integer.toString(count_day);
      }

      if ( count_day <= 0 )
      {
        return "<b>" + Integer.toString(count_day) + "</b>";
      }

      if ( count_day > 0 )
      {
        return "<span style=\"color:red\"><b>" + Integer.toString(count_day) + "</b></span>";
      }

      return Integer.toString(count_day);
    }
    else
    {
      Date payDate = StringUtil.getCurrentDateTime();
      Date shpExpirationDate;
      try
      {
        if ( StringUtil.isEmpty(getPay_date()) && StringUtil.isEmpty(getPay_date_expiration()) )
        {
          return "";
        }

        if ( !StringUtil.isEmpty(getPay_date()) )
        {
          payDate = StringUtil.appDateString2Date(getPay_date_formatted());
        }
        else if ( !StringUtil.isEmpty(getPay_date_expiration()) )
        {
          payDate = StringUtil.appDateString2Date(getPay_date_expiration_formatted());
        }
        shpExpirationDate = StringUtil.appDateString2Date(getShp_date_expiration_formatted());
        double days = StringUtil.getDaysBetween(payDate, shpExpirationDate);
        if ( days >= 0 )
        {
          return Integer.toString(Double.valueOf(-days).intValue());
        }
        else
        {
          return "<span style=\"color:red\">" + Integer.toString(Double.valueOf(-days).intValue()) + "</span>";
        }
      }
      catch (Exception e)
      {
        log.error(e);
      }
    }

    return "";
  }

  public String getPay_expiration_formatted()
  {
    String retStr = getPay_expiration();
    String expiration = getSpc_pay_expiration();
    if ( !StringUtil.isEmpty(retStr) && !StringUtil.isEmpty(expiration))
    {
      retStr += ReportDelimiterConsts.html_separator;  
    }
    retStr += getSpc_pay_expiration();
    return retStr;
  }

  public String getPay_expiration_excel()
  {
    if ( isItog_line() || isCtr_line() )
    {
      return "";
    }

    if ( StringUtil.isEmpty(shp_number) )
    {
      return "";
    }

    if ( StringUtil.isEmpty(getShp_date_expiration()) )
    {
      IActionContext context = ActionContext.threadInstance();
      try
      {
        return StrutsUtil.getMessage(context, "CalculationState.na_expiration");
      }
      catch (Exception e)
      {
        log.error(e);
      }
    }

    if ( isNo_shipping() )
    {
      if ( isAlone_shipping() )
      {
        try
        {
          Date now = StringUtil.getCurrentDateTime();
          Date shpExpirationDate = StringUtil.appDateString2Date(getShp_date_expiration_formatted());
          int days = Double.valueOf(StringUtil.getDaysBetween(shpExpirationDate, now)).intValue();

          return Integer.toString(days);
        }
        catch (Exception e)
        {
          return "";
        }
      }
      else
      {
        IActionContext context = ActionContext.threadInstance();
        try
        {
          return StrutsUtil.getMessage(context, "CalculationState.na_expiration");
        }
        catch (Exception e)
        {
          log.error(e);
        }
      }
    }

    if ( !StringUtil.isEmpty(get_pay_expiration_from_proc) || StringUtil.isEmpty(shp_closed_correct) )
    {
      return Integer.toString(count_day);
    }
    else
    {
      Date payDate;
      Date shpExpirationDate;
      try
      {
        payDate = StringUtil.appDateString2Date(getPay_date_formatted());
        shpExpirationDate = StringUtil.appDateString2Date(getShp_date_expiration_formatted());
        double days = StringUtil.getDaysBetween(payDate, shpExpirationDate);
        return Integer.toString(Double.valueOf(days).intValue());
      }
      catch (Exception e)
      {
        log.error(e);
      }
    }

    return "";
  }

  public String getPay_expiration_excel_formatted()
  {
    String retStr = getPay_expiration_excel();
    retStr += getSpc_pay_expiration_excel();
    return retStr;
  }

  public void setCount_day(int count_day)
  {
    this.count_day = count_day;
  }

  public String getShp_id()
  {
    return shp_id;
  }

  public void setShp_id(String shp_id)
  {
    this.shp_id = shp_id;
  }

  public String getShp_number()
  {
    return shp_number;
  }

  public String getShpNumberFormatted()
  {
    String retStr = "";
    if ( StringUtil.isEmpty(getShp_number()))
    {
      return "";
    }
    else
    {
      retStr += getShp_number() + ( isShp_no_act_out() ? ReportDelimiterConsts.html_separator + getShp_no_act_text() : "" );
    }

    return retStr;
  }

  public String getShpNumberAddText()
  {
    String retStr = "";

    IActionContext context = ActionContext.threadInstance();
    try
    {
      if (!isShpOriginal() && !StringUtil.isEmpty(getShp_number()))
      {
        retStr += ( !StringUtil.isEmpty(getShp_number()) ? " " : "" ) + StrutsUtil.getMessage(context, "Common.redBold", StrutsUtil.getMessage(context, "CalculationState.shpNoOriginal"));
      }

      if (!StringUtil.isEmpty(getDates_for_produce_on_storage()) )
      {
        String strTmp = getDates_for_produce_on_storage().substring(0, getDates_for_produce_on_storage().length() - 4);
        retStr += ( !StringUtil.isEmpty(retStr) ? ReportDelimiterConsts.html_separator : "" ) + StrutsUtil.getMessage(context, "Common.green", StrutsUtil.getMessage(context, "CalculationState.dates_for_produce_on_storage", strTmp));
      }
    }
    catch (Exception e)
    {
      log.error(e);
    }

    return retStr;
  }

  public String getShpNumberExcel()
  {
    String retStr = "";

    IActionContext context = ActionContext.threadInstance();
    try
    {
      if ( !StringUtil.isEmpty(getShp_number()) )
      {
        retStr += getShp_number() + ( isShp_no_act_out() ? ", " + StrutsUtil.getMessage(context, "CalculationState.shp_no_act") : "" );
      }

      if (!isShpOriginal() && !StringUtil.isEmpty(getShp_number()))
      {
        retStr += ( !StringUtil.isEmpty(retStr) ? ", " : "" ) + StrutsUtil.getMessage(context, "CalculationState.shpNoOriginal");
      }

      if ( !StringUtil.isEmpty(getDates_for_produce_on_storage()) )
      {
        String strTmp = getDates_for_produce_on_storage().substring(0, getDates_for_produce_on_storage().length() - 4);
        retStr += ( !StringUtil.isEmpty(retStr) ? ", " : "" ) +  StrutsUtil.getMessage(context, "CalculationState.dates_for_produce_on_storage", strTmp);
      }
    }
    catch (Exception e)
    {
      log.error(e);
    }

    return retStr;
  }

  public void setShp_number(String shp_number)
  {
    this.shp_number = shp_number;
  }

  public String getShp_date()
  {
    return shp_date;
  }

  public String getShp_date_formatted()
  {
    return StringUtil.dbDateString2appDateString(shp_date);
  }

  public void setShp_date(String shp_date)
  {
    this.shp_date = shp_date;
  }

  public String getShp_date_expiration()
  {
    return shp_date_expiration;
  }

  public String getShp_date_expiration_formatted()
  {
    return StringUtil.dbDateString2appDateString(shp_date_expiration);
  }

  public void setShp_date_expiration(String shp_date_expiration)
  {
    this.shp_date_expiration = shp_date_expiration;
  }

  public double getShp_summ()
  {
    return shp_summ;
  }

  public String getShp_summ_formatted()
  {
    if (shp_summ == 0 && isBalanceLine())
    {
      return "";
    }

    if (shp_summ == 0 && StringUtil.isEmpty(shp_number) && !isItog_line())
    {
      return "";
    }

    return StringUtil.double2appCurrencyString(shp_summ);
  }

  public void setShp_summ(double shp_summ)
  {
    this.shp_summ = shp_summ;
  }

  public double getShp_saldo()
  {
    return shp_saldo;
  }

  public String getShp_saldo_formatted()
  {
    //сначала эта проверка
    if (isCtr_line())
    {
      return "";
    }
    if (!isItog_line() && !isRest_doc_line())
    {
      return "";
    }

    return StringUtil.double2appCurrencyString(shp_saldo);
  }

  public void setShp_saldo(double shp_saldo)
  {
    this.shp_saldo = shp_saldo;
  }

  public String getShp_closed()
  {
    return shp_closed;
  }

  public void setShp_closed(String shp_closed)
  {
    this.shp_closed = shp_closed;
  }

  public String getShp_closed_correct()
  {
    return shp_closed_correct;
  }

  public void setShp_closed_correct(String shp_closed_correct)
  {
    this.shp_closed_correct = shp_closed_correct;
  }

  public String getGet_pay_expiration_from_proc()
  {
    return get_pay_expiration_from_proc;
  }

  public void setGet_pay_expiration_from_proc(String get_pay_expiration_from_proc)
  {
    this.get_pay_expiration_from_proc = get_pay_expiration_from_proc;
  }

  public String getShp_letter1_date()
  {
    return shp_letter1_date;
  }

  public String getShp_letter1_date_formatted()
  {
    return StringUtil.dbDateString2appDateString(shp_letter1_date);
  }

  public void setShp_letter1_date(String shp_letter1_date)
  {
    this.shp_letter1_date = shp_letter1_date;
  }

  public String getShp_letter2_date()
  {
    return shp_letter2_date;
  }

  public String getShp_letter2_date_formatted()
  {
    return StringUtil.dbDateString2appDateString(shp_letter2_date);
  }

  public void setShp_letter2_date(String shp_letter2_date)
  {
    this.shp_letter2_date = shp_letter2_date;
  }

  public String getShp_letter3_date()
  {
    return shp_letter3_date;
  }

  public String getShp_letter3_date_formatted()
  {
    return StringUtil.dbDateString2appDateString(shp_letter3_date);
  }

  public void setShp_letter3_date(String shp_letter3_date)
  {
    this.shp_letter3_date = shp_letter3_date;
  }

  public String getShp_complaint_in_court_date()
  {
    return shp_complaint_in_court_date;
  }

  public String getShp_complaint_in_court_date_formatted()
  {
    return StringUtil.dbDateString2appDateString(shp_complaint_in_court_date);
  }

  public void setShp_complaint_in_court_date(String shp_complaint_in_court_date)
  {
    this.shp_complaint_in_court_date = shp_complaint_in_court_date;
  }

  public String getShp_comment()
  {
    return shp_comment;
  }

  public void setShp_comment(String shp_comment)
  {
    this.shp_comment = shp_comment;
  }

  public String getShp_original()
  {
    return shp_original;
  }

  public boolean isShpOriginal()
  {
    return !StringUtil.isEmpty(getShp_original());  
  }

  public void setShp_original(String shp_original)
  {
    this.shp_original = shp_original;
  }

  public String getUsr_id_list_shp()
  {
    return usr_id_list_shp;
  }

  public void setUsr_id_list_shp(String usr_id_list_shp)
  {
    this.usr_id_list_shp = usr_id_list_shp;
  }

  public int getShp_no_act()
  {
    return shp_no_act;
  }

  public boolean isShp_no_act_out()
  {
    return shp_no_act != 0;
  }

  public String getShp_no_act_text()
  {
    if ( isShp_no_act_out() )
    {
      IActionContext context = ActionContext.threadInstance();
      try
      {
        return " <span style=\"color:green\">" + StrutsUtil.getMessage(context, "CalculationState.shp_no_act") + "</span>";
      }
      catch (Exception e)
      {
        log.error(e);
      }
    }

    return "";
  }

  public void setShp_no_act(int shp_no_act)
  {
    this.shp_no_act = shp_no_act;
  }

  public String getComplaint()
  {
    String retComplain = "";
    IActionContext context = ActionContext.threadInstance();
    try
    {
      if (
            !StringUtil.isEmpty(spc_letter1_date) || !StringUtil.isEmpty(spc_letter2_date) ||
            !StringUtil.isEmpty(spc_letter3_date) || !StringUtil.isEmpty(spc_complaint_in_court_date)
         )
      {
        retComplain += StrutsUtil.getMessage(context, "CalculationState.complaint_spc");
      }

      if ( !StringUtil.isEmpty(spc_letter1_date) )
      {
        retComplain += "1," + ReportDelimiterConsts.html_separator + getSpc_letter1_date_formatted() + ReportDelimiterConsts.html_separator;
      }
      if ( !StringUtil.isEmpty(spc_letter2_date) )
      {
        retComplain += "2," + ReportDelimiterConsts.html_separator + getSpc_letter2_date_formatted() + ReportDelimiterConsts.html_separator;
      }
      if ( !StringUtil.isEmpty(spc_letter3_date) )
      {
        retComplain += "3," + ReportDelimiterConsts.html_separator + getSpc_letter3_date_formatted() + ReportDelimiterConsts.html_separator;
      }
      if ( !StringUtil.isEmpty(spc_complaint_in_court_date) )
      {
        retComplain += "4," + ReportDelimiterConsts.html_separator + getSpc_complaint_in_court_date_formatted();
      }


      if (
           !StringUtil.isEmpty(shp_letter1_date) || !StringUtil.isEmpty(shp_letter2_date) ||
           !StringUtil.isEmpty(shp_letter3_date) || !StringUtil.isEmpty(shp_complaint_in_court_date) 
         )
      {
        if (
             !StringUtil.isEmpty(spc_letter1_date) || !StringUtil.isEmpty(spc_letter2_date) ||
             !StringUtil.isEmpty(spc_letter3_date) || !StringUtil.isEmpty(spc_complaint_in_court_date)
           )
        {
          retComplain += ReportDelimiterConsts.html_separator;
        }
        retComplain += StrutsUtil.getMessage(context, "CalculationState.complaint_shp");
      }

      if ( !StringUtil.isEmpty(shp_letter1_date) )
      {
        retComplain += "1," + ReportDelimiterConsts.html_separator + getShp_letter1_date_formatted() + ReportDelimiterConsts.html_separator;
      }
      if ( !StringUtil.isEmpty(shp_letter2_date) )
      {
        retComplain += "2," + ReportDelimiterConsts.html_separator + getShp_letter2_date_formatted() + ReportDelimiterConsts.html_separator;
      }
      if ( !StringUtil.isEmpty(shp_letter3_date) )
      {
        retComplain += "3," + ReportDelimiterConsts.html_separator + getShp_letter3_date_formatted() + ReportDelimiterConsts.html_separator;
      }
      if ( !StringUtil.isEmpty(shp_complaint_in_court_date) )
      {
        retComplain += "4," + ReportDelimiterConsts.html_separator + getShp_complaint_in_court_date_formatted();
      }
    }
    catch (Exception e)
    {
      log.error(e);
    }

    return retComplain;
  }

  public String getCommentExcel()
  {
    String retStr = "";

    IActionContext context = ActionContext.threadInstance();
    try
    {
      if ( !StringUtil.isEmpty(con_comment) )
      {
        retStr += StrutsUtil.getMessage(context, "CalculationState.con_comment") + " " + con_comment + "<br>";
      }
      if ( !StringUtil.isEmpty(spc_comment) )
      {
        retStr += StrutsUtil.getMessage(context, "CalculationState.spc_comment") + " " + spc_comment + "<br>";
      }
      if ( !StringUtil.isEmpty(shp_comment) )
      {
        retStr += StrutsUtil.getMessage(context, "CalculationState.shp_comment") + " " + shp_comment + "<br>";
      }
      if ( !StringUtil.isEmpty(pay_comment) )
      {
        retStr += StrutsUtil.getMessage(context, "CalculationState.pay_comment") + " " + pay_comment + "<br>";
      }
    }
    catch (Exception e)
    {
      log.error(e);
    }

    if (!StringUtil.isEmpty(retStr))
    {
      retStr = retStr.substring(0, retStr.length() - 4);
    }

    return retStr;
  }

  public String getComment()
  {
    String retStr = getCommentExcel();

    if (!StringUtil.isEmpty(retStr))
    {
      retStr = retStr.replaceAll("\r\n", ReportDelimiterConsts.html_separator);
    }

    return retStr;
  }

  public String getManagersFormatted()
  {
    String retStr = managers;
    String whose = getSpc_whose();
    if ( !StringUtil.isEmpty(whose) )
    {
      if ( null == retStr )
      {
        retStr = "";
      }
      whose += ReportDelimiterConsts.html_separator;
      if (!retStr.contains(whose)) //только если уже нет
      {
        retStr += whose;
      }
    }

    return retStr;
  }

  public String getManagers()
  {
    return managers;
  }

  public void setManagers(String managers)
  {
    this.managers = managers;
  }

  public String getStuff_categories()
  {
    return stuff_categories;
  }

  public void setStuff_categories(String stuff_categories)
  {
    this.stuff_categories = stuff_categories;
  }

  public String getPay_id()
  {
    return pay_id;
  }

  public void setPay_id(String pay_id)
  {
    this.pay_id = pay_id;
  }

  public String getPay_date()
  {
    return pay_date;
  }

  public String getPay_date_formatted()
  {
    return StringUtil.dbDateString2appDateString(pay_date);
  }

  public void setPay_date(String pay_date)
  {
    this.pay_date = pay_date;
  }

  public String getPay_date_expiration()
  {
    return pay_date_expiration;
  }

  public String getPay_date_expiration_formatted()
  {
    return StringUtil.dbDateString2appDateString(pay_date_expiration);
  }

  public void setPay_date_expiration(String pay_date_expiration)
  {
    this.pay_date_expiration = pay_date_expiration;
  }

  public double getPay_summ()
  {
    return pay_summ;
  }

  public String getPay_summ_formatted()
  {
    if (pay_summ == 0 && isCommonBalanceLine())
    {
      return "";
    }

    if (pay_summ == 0 && StringUtil.isEmpty(getPay_date()) && !isItog_line())
    {
      return "";
    }

    return StringUtil.double2appCurrencyString(pay_summ);
  }

  public void setPay_summ(double pay_summ)
  {
    this.pay_summ = pay_summ;
  }

  public String getPay_closed()
  {
    return pay_closed;
  }

  public void setPay_closed(String pay_closed)
  {
    this.pay_closed = pay_closed;
  }

  public String getPay_block()
  {
    return pay_block;
  }

  public void setPay_block(String pay_block)
  {
    this.pay_block = pay_block;
  }

  public String getPay_comment()
  {
    return pay_comment;
  }

  public void setPay_comment(String pay_comment)
  {
    this.pay_comment = pay_comment;
  }

  public String getDates_for_produce_on_storage()
  {
    return dates_for_produce_on_storage;
  }

  public void setDates_for_produce_on_storage(String dates_for_produce_on_storage)
  {
    this.dates_for_produce_on_storage = dates_for_produce_on_storage;
  }

  public boolean isCtr_line()
  {
    return ctr_line;
  }

  public void setCtr_line(boolean ctr_line)
  {
    this.ctr_line = ctr_line;
  }

  public boolean isCon_line()
  {
    return !StringUtil.isEmpty(con_number);
  }

  public boolean isItog_line()
  {
    return itog_line;
  }

  public void setItog_line(boolean itog_line)
  {
    this.itog_line = itog_line;
  }

  public boolean isRest_doc_line()
  {
    return rest_doc_line;
  }

  public void setRest_doc_line(boolean rest_doc_line)
  {
    this.rest_doc_line = rest_doc_line;
  }

  public boolean isOnly_spec()
  {
    return only_spec;
  }

  public void setOnly_spec(boolean only_spec)
  {
    this.only_spec = only_spec;
  }

  public boolean isNo_shipping()
  {
    return no_shipping;
  }

  public void setNo_shipping(boolean no_shipping)
  {
    this.no_shipping = no_shipping;
  }

  public boolean isAlone_shipping()
  {
    return alone_shipping;
  }

  public void setAlone_shipping(boolean alone_shipping)
  {
    this.alone_shipping = alone_shipping;
  }

  public boolean isDeviateLine()
  {
    return deviateLine;
  }

  public void setDeviateLine(boolean deviateLine)
  {
    this.deviateLine = deviateLine;
  }

  public boolean isBalanceLine()
  {
    return balanceLine;
  }

  public void setBalanceLine(boolean balanceLine)
  {
    this.balanceLine = balanceLine;
  }

  public boolean isCommonBalanceLine()
  {
    return commonBalanceLine;
  }

  public void setCommonBalanceLine(boolean commonBalanceLine)
  {
    this.commonBalanceLine = commonBalanceLine;
  }

  public boolean isNeedFormatCon()
  {
    return needFormatCon;
  }

  public void setNeedFormatCon(boolean needFormatCon)
  {
    this.needFormatCon = needFormatCon;
  }

  public String getStyle_shp()
  {
    if ( !StringUtil.isEmpty(shp_closed) )
    {
      return "style-checker-shp_green";
    }

    if ( isDeviateLine() && getShp_summ() < 0 )
    {
      return "style-checker-red";
    }
    else if ( isBalanceLine() )
    {
      return "style-checker-red";
    }
    else
    {
      return "style-checker";
    }
  }

  public String getStyle_shp_closed()
  {
    if ( !StringUtil.isEmpty(shp_closed) )
    {
      return "style-checker-shp_green";
    }
    else
    {
      return "style-checker";
    }
  }

  public String getStyle_pay()
  {
    if ( !StringUtil.isEmpty(pay_closed) )
    {
      return "style-checker-pay_green";
    }

    if ( isDeviateLine() && getPay_summ() < 0 )
    {
      return "style-checker-red";
    }
    else if ( isBalanceLine() )
    {
      return "style-checker-blue";
    }
    else
    {
      if ( !isItog_line() && !isCtr_line() && StringUtil.isEmpty(pay_block) && !StringUtil.isEmpty(pay_date) )
      {
        return "style-checker-pay_light_orange";
      }

      return "style-checker";
    }
  }

  public String getStyle_pay_closed()
  {
    if ( !StringUtil.isEmpty(pay_closed) )
    {
      return "style-checker-pay_green";
    }
    else
    {
      if ( !isItog_line() && !isCtr_line() && StringUtil.isEmpty(pay_block) && !StringUtil.isEmpty(pay_date) )
      {
        return "style-checker-pay_light_orange";
      }

      return "style-checker";
    }
  }

}
