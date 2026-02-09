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
public class GoodsRestLine implements Serializable
{
  protected static Log log = LogFactory.getLog(CalculationStateLine.class);

  String lpc_id;
  String prd_id;
  String produce_name;
  String prd_type;
  String prd_params;
  String prd_add_params;
  String ctn_number;
  String stf_name;
  String order_for;
  String con_number;
  String con_date;
  String spc_number;
  String spc_date;
  String usr_id;
  String usr_name;
  String dep_id;
  String dep_name;
  String unit;
  double lpc_count;
  double lpc_count_free;
  String prc_date;
  String prc_number;
  String shp_date;
  String ctr_name;
  String usr_shipping;
  double less_3_month;
  double month_3_6;
  double month_6_9;
  double month_9_12;
  double more_12_month;
  double lpc_count_free_to;
  double less_3_month_to;
  double month_3_6_to;
  double month_6_9_to;
  double month_9_12_to;
  double more_12_month_to;

  double debt_summ;
  String debt_currency;
  String purpose;
  String lpc_1c_number;
  double lpc_cost_one_by;
  double lpc_price_list_by;
  String lpc_comment = "";
  String drp_purpose;
  String dlr_comment;

  boolean itogLine = false;
  boolean allItog = false;

  List<Integer> imagesIds = new ArrayList<Integer>();

  public String getLpc_id()
  {
    return lpc_id;
  }

  public void setLpc_id(String lpc_id)
  {
    this.lpc_id = lpc_id;
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

  public String getFullName()
  {
    String produce_full_name = getProduce_name();
    if (!StringUtil.isEmpty(getPrd_type()))
    {
      produce_full_name += " " + getPrd_type();
    }
    if (!StringUtil.isEmpty(getPrd_params()))
    {
      produce_full_name += " " + getPrd_params();
    }
    if (!StringUtil.isEmpty(getPrd_add_params()))
    {
      produce_full_name += " " + getPrd_add_params();
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

  public String getStf_name()
  {
    return stf_name;
  }

  public void setStf_name(String stf_name)
  {
    this.stf_name = stf_name;
  }

  public String getOrder_for()
  {
    return order_for;
  }

  public void setOrder_for(String order_for)
  {
    this.order_for = order_for;
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

  public String getCon_spc()
  {
    String retStr = "";
    IActionContext context = ActionContext.threadInstance();

    try
    {
      if (!StringUtil.isEmpty(getCon_number()) && !StringUtil.isEmpty(getCon_date()))
      {
        String tmpStr = StrutsUtil.getMessage(context, "msg.common.from", getCon_number(), getCon_date_formatted());
        retStr += tmpStr;
      }

      if (!StringUtil.isEmpty(getSpc_number()) && !StringUtil.isEmpty(getSpc_date()))
      {
        retStr += ReportDelimiterConsts.html_separator;
        String tmpStr = StrutsUtil.getMessage(context, "msg.common.from", getSpc_number(), getSpc_date_formatted());
        retStr += tmpStr;
      }
    }
    catch (Exception e)
    {
      log.error(e);
    }

    return retStr;
  }

  public String getUnit()
  {
    return unit;
  }

  public void setUnit(String unit)
  {
    this.unit = unit;
  }

  public String getPrc_date()
  {
    return prc_date;
  }

  public String getPrc_date_formatted()
  {
    return StringUtil.dbDateString2appDateString(prc_date);
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

  public String getCtr_name()
  {
    return ctr_name;
  }

  public void setCtr_name(String ctr_name)
  {
    this.ctr_name = ctr_name;
  }

  public String getUsr_shipping()
  {
    return usr_shipping;
  }

  public void setUsr_shipping(String usr_shipping)
  {
    this.usr_shipping = usr_shipping;
  }

  public double getLpc_count()
  {
    return lpc_count;
  }

  public String getLpc_count_formatted()
  {
    return StringUtil.double2appCurrencyString(lpc_count);
  }

  public void setLpc_count(double lpc_count)
  {
    this.lpc_count = lpc_count;
  }

  public double getLpc_count_free()
  {
    return lpc_count_free;
  }

  public String getLpc_count_free_formatted()
  {
    return StringUtil.double2appCurrencyString(lpc_count_free);
  }

  public void setLpc_count_free(double lpc_count_free)
  {
    this.lpc_count_free = lpc_count_free;
  }

  public double getLess_3_month()
  {
    return less_3_month;
  }

  public String getLess_3_month_formatted()
  {
    if ((!itogLine && less_3_month == 0) || (allItog && less_3_month == 0))
    {
      return "";
    }

    return StringUtil.double2appCurrencyString(less_3_month);
  }

  public void setLess_3_month(double less_3_month)
  {
    this.less_3_month = less_3_month;
  }

  public double getMonth_3_6()
  {
    return month_3_6;
  }

  public String getMonth_3_6_formatted()
  {
    if ((!itogLine && month_3_6 == 0) || (allItog && month_3_6 == 0))
    {
      return "";
    }

    return StringUtil.double2appCurrencyString(month_3_6);
  }

  public void setMonth_3_6(double month_3_6)
  {
    this.month_3_6 = month_3_6;
  }

  public double getMonth_6_9()
  {
    return month_6_9;
  }

  public String getMonth_6_9_formatted()
  {
    if ((!itogLine && month_6_9 == 0) || (allItog && month_6_9 == 0))
    {
      return "";
    }

    return StringUtil.double2appCurrencyString(month_6_9);
  }

  public void setMonth_6_9(double month_6_9)
  {
    this.month_6_9 = month_6_9;
  }

  public double getMonth_9_12()
  {
    return month_9_12;
  }

  public String getMonth_9_12_formatted()
  {
    if ((!itogLine && month_9_12 == 0) || (allItog && month_9_12 == 0))
    {
      return "";
    }

    return StringUtil.double2appCurrencyString(month_9_12);
  }

  public void setMonth_9_12(double month_9_12)
  {
    this.month_9_12 = month_9_12;
  }

  public double getMore_12_month()
  {
    return more_12_month;
  }

  public String getMore_12_month_formatted()
  {
    if ((!itogLine && more_12_month == 0) || (allItog && more_12_month == 0))
    {
      return "";
    }

    return StringUtil.double2appCurrencyString(more_12_month);
  }

  public void setMore_12_month(double more_12_month)
  {
    this.more_12_month = more_12_month;
  }

  public double getLpc_count_free_to()
  {
    return lpc_count_free_to;
  }

  public String getLpc_count_free_to_formatted()
  {
    return StringUtil.double2appCurrencyString(lpc_count_free_to);
  }

  public void setLpc_count_free_to(double lpc_count_free_to)
  {
    this.lpc_count_free_to = lpc_count_free_to;
  }

  public double getLess_3_month_to()
  {
    return less_3_month_to;
  }

  public String getLess_3_month_to_formatted()
  {
    if ((!itogLine && less_3_month_to == 0) || (allItog && less_3_month_to == 0))
    {
      return "";
    }

    return StringUtil.double2appCurrencyString(less_3_month_to);
  }

  public void setLess_3_month_to(double less_3_month_to)
  {
    this.less_3_month_to = less_3_month_to;
  }

  public double getMonth_3_6_to()
  {
    return month_3_6_to;
  }

  public String getMonth_3_6_to_formatted()
  {
    if ((!itogLine && month_3_6_to == 0) || (allItog && month_3_6_to == 0))
    {
      return "";
    }

    return StringUtil.double2appCurrencyString(month_3_6_to);
  }

  public void setMonth_3_6_to(double month_3_6_to)
  {
    this.month_3_6_to = month_3_6_to;
  }

  public double getMonth_6_9_to()
  {
    return month_6_9_to;
  }

  public String getMonth_6_9_to_formatted()
  {
    if ((!itogLine && month_6_9_to == 0) || (allItog && month_6_9_to == 0))
    {
      return "";
    }

    return StringUtil.double2appCurrencyString(month_6_9_to);
  }

  public void setMonth_6_9_to(double month_6_9_to)
  {
    this.month_6_9_to = month_6_9_to;
  }

  public double getMonth_9_12_to()
  {
    return month_9_12_to;
  }

  public String getMonth_9_12_to_formatted()
  {
    if ((!itogLine && month_9_12_to == 0) || (allItog && month_9_12_to == 0))
    {
      return "";
    }

    return StringUtil.double2appCurrencyString(month_9_12_to);
  }

  public void setMonth_9_12_to(double month_9_12_to)
  {
    this.month_9_12_to = month_9_12_to;
  }

  public double getMore_12_month_to()
  {
    return more_12_month_to;
  }

  public String getMore_12_month_to_formatted()
  {
    if ((!itogLine && more_12_month_to == 0) || (allItog && more_12_month_to == 0))
    {
      return "";
    }

    return StringUtil.double2appCurrencyString(more_12_month_to);
  }

  public void setMore_12_month_to(double more_12_month_to)
  {
    this.more_12_month_to = more_12_month_to;
  }

  public double getDebt_summ()
  {
    return debt_summ;
  }

  public String getDebt_summ_formatted()
  {
    if ((!itogLine && debt_summ == 0) || (allItog && debt_summ == 0))
    {
      return "";
    }

    return StringUtil.double2appCurrencyString(debt_summ);
  }

  public void setDebt_summ(double debt_summ)
  {
    this.debt_summ = debt_summ;
  }

  public String getDebt_currency()
  {
    return debt_currency;
  }

  public void setDebt_currency(String debt_currency)
  {
    this.debt_currency = debt_currency;
  }

  public String getPurpose()
  {
    return purpose;
  }

  public void setPurpose(String purpose)
  {
    this.purpose = purpose;
  }

  public String getLpc_1c_number()
  {
    return lpc_1c_number;
  }

  public void setLpc_1c_number(String lpc_1c_number)
  {
    this.lpc_1c_number = lpc_1c_number;
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

  public void setLpc_price_list_by(double lpc_price_list_by)
  {
    this.lpc_price_list_by = lpc_price_list_by;
  }

  public String getLpc_comment_excel()
  {
    return lpc_comment;
  }

  public String getLpc_comment()
  {
    String retStr = getLpc_comment_excel();

    if (!StringUtil.isEmpty(retStr))
    {
      retStr = retStr.replaceAll("\r\n", ReportDelimiterConsts.html_separator);
    }

    return retStr;
  }

  public void setLpc_comment(String lpc_comment)
  {
    this.lpc_comment = lpc_comment;
  }

  public String getDrp_purpose_excel()
  {
    String retStr = "";
    IActionContext context = ActionContext.threadInstance();
    String dlrComment = dlr_comment;
    try
    {
      String cmpDlrComment = StrutsUtil.getMessage(context, "DeliveryRequest.dlr_comment_default");
      if (dlrComment.equals(cmpDlrComment))
      {
        dlrComment = "";
      }
      if (!StringUtil.isEmpty(drp_purpose) || !StringUtil.isEmpty(dlrComment))
      {
        retStr = StrutsUtil.getMessage(context, "GoodsRest.drp_purpose", !StringUtil.isEmpty(dlrComment) ? dlr_comment + "; " + drp_purpose : drp_purpose);
      }
    }
    catch (Exception e)
    {
      log.error(e);
    }

    return retStr;
  }

  public String getDrp_purpose()
  {
    String retStr = getDrp_purpose_excel();

    if (!StringUtil.isEmpty(retStr))
    {
      retStr = retStr.replaceAll("\r\n", ReportDelimiterConsts.html_separator);
    }

    return retStr;
  }

  public void setDrp_purpose(String drp_purpose)
  {
    this.drp_purpose = drp_purpose;
  }

  public void setDlr_comment(String dlr_comment)
  {
    this.dlr_comment = dlr_comment;
  }

  public String getCommentExcel()
  {
    String retStr = !StringUtil.isEmpty(getDrp_purpose_excel()) ? getDrp_purpose_excel() + " " : "";
    retStr += getLpc_comment_excel();

    return retStr;
  }

  public String getCommentForSplit()
  {
    String retStr = !StringUtil.isEmpty(getDrp_purpose_excel()) ? getDrp_purpose_excel() + "#" : "#";
    retStr += getLpc_comment_excel();

    retStr = retStr.replaceAll("\"", "&quot;");
    return retStr;
  }

  public String getComment()
  {
    if (isItogLine())
    {
      return "";
    }

    String retStr = getDrp_purpose() + ((!StringUtil.isEmpty(getDrp_purpose()) & !StringUtil.isEmpty(getLpc_comment())) ? " <br><br>" : "");
    retStr += getLpc_comment();

    return retStr;
  }

  public String getUsr_id()
  {
    return usr_id;
  }

  public void setUsr_id(String usr_id)
  {
    this.usr_id = usr_id;
  }

  public String getUsr_name()
  {
    return usr_name;
  }

  public void setUsr_name(String usr_name)
  {
    this.usr_name = usr_name;
  }

  public String getDep_id()
  {
    return dep_id;
  }

  public void setDep_id(String dep_id)
  {
    this.dep_id = dep_id;
  }

  public String getDep_name()
  {
    return dep_name;
  }

  public void setDep_name(String dep_name)
  {
    this.dep_name = dep_name;
  }

  public boolean isItogLine()
  {
    return itogLine;
  }

  public void setItogLine(boolean itogLine)
  {
    this.itogLine = itogLine;
  }

  public boolean isAllItog()
  {
    return allItog;
  }

  public void setAllItog(boolean allItog)
  {
    this.allItog = allItog;
  }

  public List<Integer> getImagesIds()
  {
    return imagesIds;
  }

  public void setImagesIds(List<Integer> imagesIds)
  {
    this.imagesIds = imagesIds;
  }

  public String getImagesIdsAsString()
  {
    return StringUtil.list2String(imagesIds, "", "", ",");
  }

}
