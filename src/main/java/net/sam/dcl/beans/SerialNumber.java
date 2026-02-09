package net.sam.dcl.beans;

import java.io.Serializable;

public class SerialNumber implements Serializable
{
  String lps_id;
  String lps_serial_num;
  String stf_name;
  String lps_year_out;
  String mad_complexity;

  public SerialNumber()
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

  public String getMad_complexity()
  {
    return mad_complexity;
  }

  public void setMad_complexity(String mad_complexity)
  {
    this.mad_complexity = mad_complexity;
  }
}