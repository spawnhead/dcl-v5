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
public class ShippingReportLine implements Serializable
{
  protected static Log log = LogFactory.getLog(ShippingReportLine.class);

  int    shp_id;
  String shp_number;
  String shp_date;
  String shp_contractor;
  double shp_summ_plus_nds;
  double shp_sum_nr;
  String country;
  int    shp_prd_count;
  String shp_closed;
  String con_number;
  String con_date;
  String spc_number;
  String spc_date;
  double spc_summ;
  String currency;
  int    prd_id;
  String prd_name;
  String prd_type;
  String prd_params;
  String prd_add_params;
  String prdFullName;
  String ctn_number;
  int    stf_id;
  String stf_name;
  String lps_id;
  double lps_count;
  double lps_summ_plus_nds;
  double lps_summ_out_nds;
  double lps_summ_out_nds_eur;
  double lps_summ_zak;
  double lps_sum_transport;
  double lps_custom;
  int    manager_id;
  String manager;
  String department;

  boolean itogLine = false;
  boolean showSum = false;
  boolean useSumPlusNdsFromShipping = false;

  public int getShp_id()
  {
    return shp_id;
  }

  public void setShp_id(int shp_id)
  {
    this.shp_id = shp_id;
  }

  public String getShp_number()
  {
    return shp_number;
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
    return StringUtil.dbDateString2appDateString(getShp_date());
  }

  public void setShp_date(String shp_date)
  {
    this.shp_date = shp_date;
  }

  public String getShp_contractor()
  {
    return shp_contractor;
  }

  public void setShp_contractor(String shp_contractor)
  {
    this.shp_contractor = shp_contractor;
  }

  public double getShp_summ_plus_nds()
  {
    return shp_summ_plus_nds;
  }

  public void setShp_summ_plus_nds(double shp_summ_plus_nds)
  {
    this.shp_summ_plus_nds = shp_summ_plus_nds;
  }

  public double getShp_sum_nr()
  {
    return shp_sum_nr;
  }

  public void setShp_sum_nr(double shp_sum_nr)
  {
    this.shp_sum_nr = shp_sum_nr;
  }

  public String getCountry()
  {
    return country;
  }

  public void setCountry(String country)
  {
    this.country = country;
  }

  public int getShp_prd_count()
  {
    return shp_prd_count;
  }

  public void setShp_prd_count(int shp_prd_count)
  {
    this.shp_prd_count = shp_prd_count;
  }

  public String getShp_closed()
  {
    return shp_closed;
  }

  public void setShp_closed(String shp_closed)
  {
    this.shp_closed = shp_closed;
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
    return StringUtil.dbDateString2appDateString(getCon_date());
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
    return StringUtil.dbDateString2appDateString(getSpc_date());
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
    return StringUtil.double2appCurrencyString(getSpc_summ());
  }

  public void setSpc_summ(double spc_summ)
  {
    this.spc_summ = spc_summ;
  }

  public String getCurrency()
  {
    return currency;
  }

  public void setCurrency(String currency)
  {
    this.currency = currency;
  }

  public String getProduce_full_name()
  {
    if ( !StringUtil.isEmpty(getPrdFullName()) )
    {
      return getPrdFullName();
    }

    String produce_full_name = getPrd_name();
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
    return produce_full_name;
  }

  public int getPrd_id()
  {
    return prd_id;
  }

  public void setPrd_id(int prd_id)
  {
    this.prd_id = prd_id;
  }

  public String getPrd_name()
  {
    return prd_name;
  }

  public void setPrd_name(String prd_name)
  {
    this.prd_name = prd_name;
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

  public String getPrdFullName()
  {
    return prdFullName;
  }

  public void setPrdFullName(String prdFullName)
  {
    this.prdFullName = prdFullName;
  }

  public String getCtn_number()
  {
    return ctn_number;
  }

  public void setCtn_number(String ctn_number)
  {
    this.ctn_number = ctn_number;
  }

  public int getStf_id()
  {
    return stf_id;
  }

  public void setStf_id(int stf_id)
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

  public String getLps_count_formatted()
  {
    return StringUtil.double2appCurrencyString(getLps_count());
  }

  public void setLps_count(double lps_count)
  {
    this.lps_count = lps_count;
  }

  public double getLps_summ_plus_nds()
  {
    return lps_summ_plus_nds;
  }

  public void setLps_summ_plus_nds(double lps_summ_plus_nds)
  {
    this.lps_summ_plus_nds = lps_summ_plus_nds;
  }

  public double getSumPlusNds()
  {
    if (isUseSumPlusNdsFromShipping())
      return getShp_summ_plus_nds();
    else
      return getLps_summ_plus_nds();
  }

  public String getSumPlusNdsFormatted()
  {
    return StringUtil.double2appCurrencyString(getSumPlusNds());
  }

  public void setSumPlusNds(double sumPlusNds)
  {
    if (isUseSumPlusNdsFromShipping())
      setShp_summ_plus_nds(sumPlusNds);
    else
      setLps_summ_plus_nds(sumPlusNds);
  }

  public double getLps_summ_out_nds()
  {
    return lps_summ_out_nds;
  }

  public String getLps_summ_out_nds_formatted()
  {
    if ( isShowSum() )
    {
      return StringUtil.double2appCurrencyString(getLps_summ_out_nds());
    }

    if ( StringUtil.isEmpty(spc_number) )
    {
      IActionContext context = ActionContext.threadInstance();
      try
      {
        return StrutsUtil.getMessage(context, "ShippingReport.no_nds_rate");
      }
      catch (Exception e)
      {
        log.error(e);
      }
    }

    return StringUtil.double2appCurrencyString(getLps_summ_out_nds());
  }

  public void setLps_summ_out_nds(double lps_summ_out_nds)
  {
    this.lps_summ_out_nds = lps_summ_out_nds;
  }

  public double getLps_summ_out_nds_eur()
  {
    return lps_summ_out_nds_eur;
  }

  public String getLps_summ_out_nds_eur_formatted()
  {
    if ( isShowSum() )
    {
      return StringUtil.double2appCurrencyString(getLps_summ_out_nds_eur());
    }

    if ( StringUtil.isEmpty(spc_number) )
    {
      IActionContext context = ActionContext.threadInstance();
      try
      {
        return StrutsUtil.getMessage(context, "ShippingReport.no_nds_rate");
      }
      catch (Exception e)
      {
        log.error(e);
      }
    }

    return StringUtil.double2appCurrencyString(getLps_summ_out_nds_eur());
  }

  public void setLps_summ_out_nds_eur(double lps_summ_out_nds_eur)
  {
    this.lps_summ_out_nds_eur = lps_summ_out_nds_eur;
  }

  public double getLps_summ_zak()
  {
    return lps_summ_zak;
  }

  public String getLps_summ_zak_formatted()
  {
    if ( isShowSum() )
    {
      return StringUtil.double2appCurrencyString(getLps_summ_zak());
    }

    if (getShp_prd_count() == 0)
    {
      IActionContext context = ActionContext.threadInstance();
      try
      {
        return StrutsUtil.getMessage(context, "ShippingReport.no_data");
      }
      catch (Exception e)
      {
        log.error(e);
      }
    }
    
    return StringUtil.double2appCurrencyString(getLps_summ_zak());
  }

  public void setLps_summ_zak(double lps_summ_zak)
  {
    this.lps_summ_zak = lps_summ_zak;
  }

  public double getLps_sum_transport()
  {
    return lps_sum_transport;
  }

  public String getLps_sum_transport_formatted()
  {
    if ( isShowSum() )
    {
      return StringUtil.double2appCurrencyString(getLps_sum_transport());
    }

    if (getShp_prd_count() == 0)
    {
      IActionContext context = ActionContext.threadInstance();
      try
      {
        return StrutsUtil.getMessage(context, "ShippingReport.no_data");
      }
      catch (Exception e)
      {
        log.error(e);
      }
    }

    return StringUtil.double2appCurrencyString(getLps_sum_transport());
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
    if ( isShowSum() )
    {
      return StringUtil.double2appCurrencyString(getLps_custom());
    }

    if (getShp_prd_count() == 0)
    {
      IActionContext context = ActionContext.threadInstance();
      try
      {
        return StrutsUtil.getMessage(context, "ShippingReport.no_data");
      }
      catch (Exception e)
      {
        log.error(e);
      }
    }

    return StringUtil.double2appCurrencyString(getLps_custom());
  }

  public void setLps_custom(double lps_custom)
  {
    this.lps_custom = lps_custom;
  }

  public int getManager_id()
  {
    return manager_id;
  }

  public void setManager_id(int manager_id)
  {
    this.manager_id = manager_id;
  }

  public String getManager()
  {
    return manager;
  }

  public void setManager(String manager)
  {
    this.manager = manager;
  }

  public String getDepartment()
  {
    return department;
  }

  public void setDepartment(String department)
  {
    this.department = department;
  }

  public boolean isItogLine()
  {
    return itogLine;
  }

  public void setItogLine(boolean itogLine)
  {
    this.itogLine = itogLine;
  }

  public boolean isShowSum()
  {
    return showSum;
  }

  public void setShowSum(boolean showSum)
  {
    this.showSum = showSum;
  }

  public boolean isUseSumPlusNdsFromShipping()
  {
    return useSumPlusNdsFromShipping;
  }

  public void setUseSumPlusNdsFromShipping(boolean useSumPlusNdsFromShipping)
  {
    this.useSumPlusNdsFromShipping = useSumPlusNdsFromShipping;
  }
}
