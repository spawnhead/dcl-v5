package net.sam.dcl.beans;

import net.sam.dcl.util.StringUtil;

import java.io.Serializable;

public class Equipment implements Serializable
{
  String lps_id;
  String crq_equipment;
  String ctn_number;
  String stf_name;
  String lps_serial_num;
  String lps_year_out;
  String lps_usr_id;
  String lps_usr_fullname;
  String lps_enter_in_use_date;
  String mad_complexity;
  String lps_occupied;
  String crq_number;

  //используются для печати акта
  String shp_date;
  String con_number;
  String con_date;
  String spc_number;
  String spc_date;
  String con_seller_id;
  String con_seller;

  public Equipment()
  {
  }

  public String getLps_id()
  {
    return lps_id;
  }

  public void setLps_id(String lps_id)
  {
    this.lps_id = lps_id;
  }

  public String getCrq_equipment()
  {
    return crq_equipment;
  }

  public void setCrq_equipment(String crq_equipment)
  {
    this.crq_equipment = crq_equipment;
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

  public String getLps_serial_num()
  {
    return lps_serial_num;
  }

  public void setLps_serial_num(String lps_serial_num)
  {
    this.lps_serial_num = lps_serial_num;
  }

  public String getLps_year_out()
  {
    return lps_year_out;
  }

  public void setLps_year_out(String lps_year_out)
  {
    this.lps_year_out = lps_year_out;
  }

  public String getLps_usr_id()
  {
    return lps_usr_id;
  }

  public void setLps_usr_id(String lps_usr_id)
  {
    this.lps_usr_id = lps_usr_id;
  }

  public String getLps_usr_fullname()
  {
    return lps_usr_fullname;
  }

  public void setLps_usr_fullname(String lps_usr_fullname)
  {
    this.lps_usr_fullname = lps_usr_fullname;
  }

  public String getFullList()
  {
    return "" + 
           (StringUtil.isEmpty(crq_equipment) ? "" : crq_equipment) +
           (StringUtil.isEmpty(ctn_number) ? "" : " " + ctn_number) +
           (StringUtil.isEmpty(stf_name) ? "" : " " + stf_name) +
           (StringUtil.isEmpty(lps_serial_num) ? "" : " " + lps_serial_num);
  }

  public String getLps_enter_in_use_date()
  {
    return lps_enter_in_use_date;
  }

  public void setLps_enter_in_use_date(String lps_enter_in_use_date)
  {
    this.lps_enter_in_use_date = lps_enter_in_use_date;
  }

  public String getMad_complexity()
  {
    return mad_complexity;
  }

  public void setMad_complexity(String mad_complexity)
  {
    this.mad_complexity = mad_complexity;
  }

  public String getLps_occupied()
  {
    return lps_occupied;
  }

  public void setLps_occupied(String lps_occupied)
  {
    this.lps_occupied = lps_occupied;
  }

  public String getCrq_number()
  {
    return crq_number;
  }

  public void setCrq_number(String crq_number)
  {
    this.crq_number = crq_number;
  }

  public String getShp_date()
  {
    return shp_date;
  }

  public void setShp_date(String shp_date)
  {
    this.shp_date = shp_date;
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

  public void setSpc_date(String spc_date)
  {
    this.spc_date = spc_date;
  }

	public String getCon_seller_id()
	{
		return con_seller_id;
	}

	public void setCon_seller_id(String con_seller_id)
	{
		this.con_seller_id = con_seller_id;
	}

	public String getCon_seller()
  {
    return con_seller;
  }

  public void setCon_seller(String con_seller)
  {
    this.con_seller = con_seller;
  }
}