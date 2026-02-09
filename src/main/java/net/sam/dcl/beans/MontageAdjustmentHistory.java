package net.sam.dcl.beans;

import java.io.Serializable;

public class MontageAdjustmentHistory implements Serializable
{
  String mad_id;
  String madh_id;
  String mad_date_from;
  double mad_mech_work_tariff;
  double mad_mech_work_rule_montage;
  double mad_mech_work_rule_adjustment;
  double mad_mech_road_tariff;
  double mad_mech_road_rule;
  double mad_el_work_tariff;
  double mad_el_work_rule_montage;
  double mad_el_work_rule_adjustment;
  double mad_el_road_tariff;
  double mad_el_road_rule;

  public MontageAdjustmentHistory()
  {
  }

  public String getMad_id()
  {
    return mad_id;
  }

  public void setMad_id(String mad_id)
  {
    this.mad_id = mad_id;
  }

  public String getMadh_id()
  {
    return madh_id;
  }

  public void setMadh_id(String madh_id)
  {
    this.madh_id = madh_id;
  }

  public String getMad_date_from()
  {
    return mad_date_from;
  }

  public void setMad_date_from(String mad_date_from)
  {
    this.mad_date_from = mad_date_from;
  }

  public double getMad_mech_work_tariff()
  {
    return mad_mech_work_tariff;
  }

  public void setMad_mech_work_tariff(double mad_mech_work_tariff)
  {
    this.mad_mech_work_tariff = mad_mech_work_tariff;
  }

  public double getMad_mech_work_rule_montage()
  {
    return mad_mech_work_rule_montage;
  }

  public void setMad_mech_work_rule_montage(double mad_mech_work_rule_montage)
  {
    this.mad_mech_work_rule_montage = mad_mech_work_rule_montage;
  }

  public double getMad_mech_work_rule_adjustment()
  {
    return mad_mech_work_rule_adjustment;
  }

  public void setMad_mech_work_rule_adjustment(double mad_mech_work_rule_adjustment)
  {
    this.mad_mech_work_rule_adjustment = mad_mech_work_rule_adjustment;
  }

  public double getMad_mech_road_tariff()
  {
    return mad_mech_road_tariff;
  }

  public void setMad_mech_road_tariff(double mad_mech_road_tariff)
  {
    this.mad_mech_road_tariff = mad_mech_road_tariff;
  }

  public double getMad_mech_road_rule()
  {
    return mad_mech_road_rule;
  }

  public void setMad_mech_road_rule(double mad_mech_road_rule)
  {
    this.mad_mech_road_rule = mad_mech_road_rule;
  }

  public double getMad_el_work_tariff()
  {
    return mad_el_work_tariff;
  }

  public void setMad_el_work_tariff(double mad_el_work_tariff)
  {
    this.mad_el_work_tariff = mad_el_work_tariff;
  }

  public double getMad_el_work_rule_montage()
  {
    return mad_el_work_rule_montage;
  }

  public void setMad_el_work_rule_montage(double mad_el_work_rule_montage)
  {
    this.mad_el_work_rule_montage = mad_el_work_rule_montage;
  }

  public double getMad_el_work_rule_adjustment()
  {
    return mad_el_work_rule_adjustment;
  }

  public void setMad_el_work_rule_adjustment(double mad_el_work_rule_adjustment)
  {
    this.mad_el_work_rule_adjustment = mad_el_work_rule_adjustment;
  }

  public double getMad_el_road_tariff()
  {
    return mad_el_road_tariff;
  }

  public void setMad_el_road_tariff(double mad_el_road_tariff)
  {
    this.mad_el_road_tariff = mad_el_road_tariff;
  }

  public double getMad_el_road_rule()
  {
    return mad_el_road_rule;
  }

  public void setMad_el_road_rule(double mad_el_road_rule)
  {
    this.mad_el_road_rule = mad_el_road_rule;
  }
}