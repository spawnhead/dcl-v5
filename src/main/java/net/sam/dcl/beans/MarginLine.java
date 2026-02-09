package net.sam.dcl.beans;

import net.sam.dcl.controller.ActionContext;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.StrutsUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * Date: 14.09.2005
 * Time: 17:17:20
 * To change this template use File | Settings | File Templates.
 */
public class MarginLine implements Serializable
{
  protected static Log log = LogFactory.getLog(MarginLine.class);

  String ctr_id;
  String ctr_block;
  String ctr_name;
  String cut_id;
  String cut_name;
  String con_id;
  String con_number;
  String con_date;
  String con_original;
  String spc_id;
  String spc_number;
  String spc_date;
  double spc_summ;
  String spc_original;
  String spc_group_delivery;
  String cur_id;
  String cur_name;
  String stf_id;
  String stf_name;
  String stf_name_show;
  String shp_id;
  String shp_number;
  String shp_number_show;
  String shp_date;
  String shp_date_show;
  String shp_original;
  String pay_id;
  String pay_date;
  String pay_date_show;
  double pay_course;
  String lps_id;
  double lps_summ_eur;
  double lps_summ;
  double lps_sum_transport;
  double lps_custom;
  double lcc_charges;
  double lcc_montage;
  double lcc_transport;
  double lps_montage_time;
  double montage_cost;
  double lcc_update_sum;
  double summ;
  double summ_zak;
  double margin;
  double koeff;
  String usr_id;
  String usr_name;
  String usr_name_show;
  String dep_id;
  String dep_name;
  String dep_name_show;
  String have_unblocked_prc;

  boolean itogLine = false;

  public String getCtr_id()
  {
    return ctr_id;
  }

  public void setCtr_id(String ctr_id)
  {
    this.ctr_id = ctr_id;
  }

  public String getCtr_block()
  {
    return ctr_block;
  }

  public void setCtr_block(String ctr_block)
  {
    this.ctr_block = ctr_block;
  }

  public String getCut_id()
  {
    return cut_id;
  }

  public void setCut_id(String cut_id)
  {
    this.cut_id = cut_id;
  }

  public String getCtr_name()
  {
    return ctr_name;
  }

  public void setCtr_name(String ctr_name)
  {
    this.ctr_name = ctr_name;
  }

  public String getCut_name()
  {
    return cut_name;
  }

  public void setCut_name(String cut_name)
  {
    this.cut_name = cut_name;
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

  public String getCon_number_formatted()
  {
    IActionContext context = ActionContext.threadInstance();
    try
    {
      // 0 - "Факсовая и т.п. копия", 1 - "Оригинал"
      if ( isConProject() && !isItogLine() )
      {
        return getCon_number() + " " + StrutsUtil.getMessage(context, "Common.redBold", StrutsUtil.getMessage(context, "Margin.project"));
      }

      if ( isConCopy() && !isItogLine() )
      {
        return getCon_number() + " " + StrutsUtil.getMessage(context, "Common.redBold", StrutsUtil.getMessage(context, "Margin.copy"));
      }
    }
    catch (Exception e)
    {
      log.error(e);
    }

    return getCon_number();
  }

  public String getCon_number_formatted_exel()
  {
    IActionContext context = ActionContext.threadInstance();
    // 0 - "Факсовая и т.п. копия", 1 - "Оригинал"
    if ( isConProject() && !isItogLine() )
    {
      try
      {
        return getCon_number() + " " + StrutsUtil.getMessage(context, "Margin.project");
      }
      catch (Exception e)
      {
        log.error(e);
      }
    }

    if ( isConCopy() && !isItogLine() )
    {
      try
      {
        return getCon_number() + " " + StrutsUtil.getMessage(context, "Margin.copy");
      }
      catch (Exception e)
      {
        log.error(e);
      }
    }

    return getCon_number();
  }

  public void setCon_number(String con_number)
  {
    this.con_number = con_number;
  }

  public void setCon_number_formatted(String con_number)
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

  public String getCon_original()
  {
    return con_original;
  }

  public void setCon_original(String con_original)
  {
    this.con_original = con_original;
  }

  public boolean isConProject()
  {
    return StringUtil.isEmpty(getCon_original());
  }

  public boolean isConCopy()
  {
    return "0".equals(getCon_original());
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

  public String getSpc_number_formatted()
  {
    IActionContext context = ActionContext.threadInstance();
    // 0 - "Факсовая и т.п. копия", 1 - "Оригинал"
    try
    {
      if ( isSpcProject() && !isItogLine() )
      {
        return getSpc_number() + " " + StrutsUtil.getMessage(context, "Common.redBold", StrutsUtil.getMessage(context, "Margin.project"));
      }

      if ( isSpcCopy() && !isItogLine() )
      {
        return getSpc_number() + " " + StrutsUtil.getMessage(context, "Common.redBold", StrutsUtil.getMessage(context, "Margin.copy"));
      }
    }
    catch (Exception e)
    {
      log.error(e);
    }

    return getSpc_number();
  }

  public String getSpc_number_formatted_exel()
  {
    IActionContext context = ActionContext.threadInstance();
    // 0 - "Факсовая и т.п. копия", 1 - "Оригинал"
    if ( isSpcProject() && !isItogLine() )
    {
      try
      {
        return getSpc_number() + " " + StrutsUtil.getMessage(context, "Margin.project");
      }
      catch (Exception e)
      {
        log.error(e);
      }
    }

    if ( isSpcCopy() && !isItogLine() )
    {
      try
      {
        return getSpc_number() + " " + StrutsUtil.getMessage(context, "Margin.copy");
      }
      catch (Exception e)
      {
        log.error(e);
      }
    }

    return getSpc_number();
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

  public double getSpc_summ()
  {
    return spc_summ;
  }

  public String getSpc_summ_formatted()
  {
    return StringUtil.double2appCurrencyString(spc_summ);
  }

  public void setSpc_summ(double spc_summ)
  {
    this.spc_summ = spc_summ;
  }

  public String getSpc_original()
  {
    return spc_original;
  }

  public void setSpc_original(String spc_original)
  {
    this.spc_original = spc_original;
  }

  public boolean isSpcProject()
  {
    return StringUtil.isEmpty(getSpc_original());
  }

  public boolean isSpcCopy()
  {
    return "0".equals(getSpc_original());
  }

  public String getSpc_group_delivery()
  {
    return spc_group_delivery;
  }

  public void setSpc_group_delivery(String spc_group_delivery)
  {
    this.spc_group_delivery = spc_group_delivery;
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

  public String getStf_name_show()
  {
    return stf_name_show;
  }

  public void setStf_name_show(String stf_name_show)
  {
    this.stf_name_show = stf_name_show;
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

  public String getShpNumberWithCopy()
  {
    IActionContext context = ActionContext.threadInstance();
    if ( !isShpOriginal() && !isItogLine() )
    {
      try
      {
        return getShp_number() + StrutsUtil.getMessage(context, "Shipping.copy");
      }
      catch (Exception e)
      {
        log.error(e);
      }
    }
    return getShp_number();
  }

  public void setShp_number(String shp_number)
  {
    this.shp_number = shp_number;
  }

  public String getShp_number_show()
  {
    return shp_number_show;
  }

  public void setShp_number_show(String shp_number_show)
  {
    this.shp_number_show = shp_number_show;
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

  public String getShp_date_show()
  {
    return shp_date_show;
  }

  public void setShp_date_show(String shp_date_show)
  {
    this.shp_date_show = shp_date_show;
  }

  public String getShp_original()
  {
    return shp_original;
  }

  public void setShp_original(String shp_original)
  {
    this.shp_original = shp_original;
  }

  public boolean isShpOriginal()
  {
    return !StringUtil.isEmpty(getShp_original());
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

  public String getPay_date_show()
  {
    return pay_date_show;
  }

  public void setPay_date_show(String pay_date_show)
  {
    this.pay_date_show = pay_date_show;
  }

  public double getPay_course()
  {
    return pay_course;
  }

  public void setPay_course(double pay_course)
  {
    this.pay_course = pay_course;
  }

  public String getLps_id()
  {
    return lps_id;
  }

  public void setLps_id(String lps_id)
  {
    this.lps_id = lps_id;
  }

  public double getLps_summ_eur()
  {
    return lps_summ_eur;
  }

  public String getLps_summ_eur_formatted()
  {
    return StringUtil.double2appCurrencyString(lps_summ_eur);
  }

  public void setLps_summ_eur(double lps_summ_eur)
  {
    this.lps_summ_eur = lps_summ_eur;
  }

  public double getLps_summ()
  {
    return lps_summ;
  }

  public String getLps_summ_formatted()
  {
    return StringUtil.double2appCurrencyString(lps_summ);
  }

  public void setLps_summ(double lps_summ)
  {
    this.lps_summ = lps_summ;
  }

  public double getLps_sum_transport()
  {
    return lps_sum_transport;
  }

  public String getLps_sum_transport_formatted()
  {
    return StringUtil.double2appCurrencyString(lps_sum_transport);
  }

  public void setLps_sum_transport(double lps_sum_transport)
  {
    this.lps_sum_transport = lps_sum_transport;
  }

  public double getLps_custom()
  {
    return lps_custom;
  }

  public String getLps_custom_formatted()
  {
    return StringUtil.double2appCurrencyString(lps_custom);
  }

  public void setLps_custom(double lps_custom)
  {
    this.lps_custom = lps_custom;
  }

  public double getLcc_charges()
  {
    return lcc_charges;
  }

  public String getLcc_charges_formatted()
  {
    return StringUtil.double2appCurrencyString(lcc_charges);
  }

  public void setLcc_charges(double lcc_charges)
  {
    this.lcc_charges = lcc_charges;
  }

  public double getLcc_transport()
  {
    return lcc_transport;
  }

  public String getLcc_transport_formatted()
  {
    return StringUtil.double2appCurrencyString(lcc_transport);
  }

  public void setLcc_transport(double lcc_transport)
  {
    this.lcc_transport = lcc_transport;
  }

  public double getLcc_montage()
  {
    return lcc_montage;
  }

  public String getLcc_montage_formatted()
  {
    return StringUtil.double2appCurrencyString(lcc_montage);
  }

  public void setLcc_montage(double lcc_montage)
  {
    this.lcc_montage = lcc_montage;
  }

  public double getLps_montage_time()
  {
    return lps_montage_time;
  }

  public String getLps_montage_time_formatted()
  {
    return StringUtil.double2appCurrencyStringByMask(lps_montage_time, "##0.0");
  }

  public void setLps_montage_time(double lps_montage_time)
  {
    this.lps_montage_time = lps_montage_time;
  }

  public double getMontage_cost()
  {
    return montage_cost;
  }

  public String getMontage_cost_formatted()
  {
    return StringUtil.double2appCurrencyString(montage_cost);
  }

  public void setMontage_cost(double montage_cost)
  {
    this.montage_cost = montage_cost;
  }

  public double getLcc_update_sum()
  {
    return lcc_update_sum;
  }

  public String getLcc_update_sum_formatted()
  {
    return StringUtil.double2appCurrencyString(lcc_update_sum);
  }

  public void setLcc_update_sum(double lcc_update_sum)
  {
    this.lcc_update_sum = lcc_update_sum;
  }

  public double getSumm()
  {
    return summ;
  }

  public String getSumm_formatted()
  {
    return StringUtil.double2appCurrencyString(summ);
  }

  public void setSumm(double summ)
  {
    this.summ = summ;
  }

  public double getSumm_zak()
  {
    return summ_zak;
  }

  public String getSumm_zak_formatted()
  {
    return StringUtil.double2appCurrencyString(summ_zak);
  }

  public void setSumm_zak(double summ_zak)
  {
    this.summ_zak = summ_zak;
  }

  public double getMargin()
  {
    return margin;
  }

  public String getMargin_formatted()
  {
    return StringUtil.double2appCurrencyString(margin);
  }

  public void setMargin(double margin)
  {
    this.margin = margin;
  }

  public double getKoeff()
  {
    return koeff;
  }

  public String getKoeff_formatted()
  {
    return StringUtil.double2appCurrencyString(koeff);
  }

  public void setKoeff(double koeff)
  {
    this.koeff = koeff;
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

  public String getUsr_name_show()
  {
    return usr_name_show;
  }

  public void setUsr_name_show(String usr_name_show)
  {
    this.usr_name_show = usr_name_show;
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

  public String getDep_name_show()
  {
    return dep_name_show;
  }

  public void setDep_name_show(String dep_name_show)
  {
    this.dep_name_show = dep_name_show;
  }

  public boolean haveUnblockedPrc()
  {
    return !StringUtil.isEmpty(getHave_unblocked_prc());
  }

  public String getHave_unblocked_prc()
  {
    return have_unblocked_prc;
  }

  public void setHave_unblocked_prc(String have_unblocked_prc)
  {
    this.have_unblocked_prc = have_unblocked_prc;
  }

  public boolean isItogLine()
  {
    return itogLine;
  }

  public void setItogLine(boolean itogLine)
  {
    this.itogLine = itogLine;
  }
}
