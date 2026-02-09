package net.sam.dcl.form;

import net.sam.dcl.controller.BaseDispatchValidatorForm;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.beans.ComplexityCategory;

/**
 * @author: DG
 * Date: Aug 6, 2005
 * Time: 5:37:29 PM
 */
public class MontageAdjustmentForm extends BaseDispatchValidatorForm
{
  String stf_id;
  String mad_id;
  String mad_machine_type;
  ComplexityCategory complexityCategory = new ComplexityCategory();
  String mad_annul;
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

  public String getStf_id()
  {
    return stf_id;
  }

  public void setStf_id(String stf_id)
  {
    this.stf_id = stf_id;
  }

  public String getMad_id()
  {
    return mad_id;
  }

  public void setMad_id(String mad_id)
  {
    this.mad_id = mad_id;
  }

  public String getMad_machine_type()
  {
    return mad_machine_type;
  }

  public void setMad_machine_type(String mad_machine_type)
  {
    this.mad_machine_type = mad_machine_type;
  }

  public ComplexityCategory getComplexityCategory()
  {
    return complexityCategory;
  }

  public void setComplexityCategory(ComplexityCategory complexityCategory)
  {
    this.complexityCategory = complexityCategory;
  }

  public String getMad_annul()
  {
    return mad_annul;
  }

  public void setMad_annul(String mad_annul)
  {
    this.mad_annul = mad_annul;
  }

  public String getMad_date_from()
  {
    return mad_date_from;
  }

  public String getMad_date_from_formatted()
  {
    return StringUtil.dbDateString2appDateString(mad_date_from);
  }

  public void setMad_date_from(String mad_date_from)
  {
    this.mad_date_from = mad_date_from;
  }

  public void setMad_date_from_formatted(String mad_date_from)
  {
    this.mad_date_from = StringUtil.appDateString2dbDateString(mad_date_from);
  }

  public double getMad_mech_work_tariff()
  {
    return mad_mech_work_tariff;
  }

  public String getMad_mech_work_tariff_formatted()
  {
    return StringUtil.double2appCurrencyStringByMask(mad_mech_work_tariff, "##0.0");
  }

  public void setMad_mech_work_tariff(double mad_mech_work_tariff)
  {
    this.mad_mech_work_tariff = mad_mech_work_tariff;
  }

  public void setMad_mech_work_tariff_formatted(String mad_mech_work_tariff)
  {
    this.mad_mech_work_tariff = StringUtil.appCurrencyString2double(mad_mech_work_tariff);
  }

  public double getMad_mech_work_rule_montage()
  {
    return mad_mech_work_rule_montage;
  }

  public String getMad_mech_work_rule_montage_formatted()
  {
    return StringUtil.double2appCurrencyStringByMask(mad_mech_work_rule_montage, "##0.0");
  }

  public void setMad_mech_work_rule_montage(double mad_mech_work_rule_montage)
  {
    this.mad_mech_work_rule_montage = mad_mech_work_rule_montage;
  }

  public void setMad_mech_work_rule_montage_formatted(String mad_mech_work_rule_montage)
  {
    this.mad_mech_work_rule_montage = StringUtil.appCurrencyString2double(mad_mech_work_rule_montage);
  }

  public double getMad_mech_work_rule_adjustment()
  {
    return mad_mech_work_rule_adjustment;
  }

  public String getMad_mech_work_rule_adjustment_formatted()
  {
    return StringUtil.double2appCurrencyStringByMask(mad_mech_work_rule_adjustment, "##0.0");
  }

  public void setMad_mech_work_rule_adjustment(double mad_mech_work_rule_adjustment)
  {
    this.mad_mech_work_rule_adjustment = mad_mech_work_rule_adjustment;
  }

  public void setMad_mech_work_rule_adjustment_formatted(String mad_mech_work_rule_adjustment)
  {
    this.mad_mech_work_rule_adjustment = StringUtil.appCurrencyString2double(mad_mech_work_rule_adjustment);
  }

  public double getMad_mech_road_tariff()
  {
    return mad_mech_road_tariff;
  }

  public String getMad_mech_road_tariff_formatted()
  {
    return StringUtil.double2appCurrencyStringByMask(mad_mech_road_tariff, "##0.0");
  }

  public void setMad_mech_road_tariff(double mad_mech_road_tariff)
  {
    this.mad_mech_road_tariff = mad_mech_road_tariff;
  }

  public void setMad_mech_road_tariff_formatted(String mad_mech_road_tariff)
  {
    this.mad_mech_road_tariff = StringUtil.appCurrencyString2double(mad_mech_road_tariff);
  }

  public double getMad_mech_road_rule()
  {
    return mad_mech_road_rule;
  }

  public String getMad_mech_road_rule_formatted()
  {
    return StringUtil.double2appCurrencyStringByMask(mad_mech_road_rule, "##0.0");
  }

  public void setMad_mech_road_rule(double mad_mech_road_rule)
  {
    this.mad_mech_road_rule = mad_mech_road_rule;
  }

  public void setMad_mech_road_rule_formatted(String mad_mech_road_rule)
  {
    this.mad_mech_road_rule = StringUtil.appCurrencyString2double(mad_mech_road_rule);
  }

  public double getMad_el_work_tariff()
  {
    return mad_el_work_tariff;
  }
                   
  public String getMad_el_work_tariff_formatted()
  {
    return StringUtil.double2appCurrencyStringByMask(mad_el_work_tariff, "##0.0");
  }

  public void setMad_el_work_tariff(double mad_el_work_tariff)
  {
    this.mad_el_work_tariff = mad_el_work_tariff;
  }

  public void setMad_el_work_tariff_formatted(String mad_el_work_tariff)
  {
    this.mad_el_work_tariff = StringUtil.appCurrencyString2double(mad_el_work_tariff);
  }

  public double getMad_el_work_rule_montage()
  {
    return mad_el_work_rule_montage;
  }

  public String getMad_el_work_rule_montage_formatted()
  {
    return StringUtil.double2appCurrencyStringByMask(mad_el_work_rule_montage, "##0.0");
  }

  public void setMad_el_work_rule_montage(double mad_el_work_rule_montage)
  {
    this.mad_el_work_rule_montage = mad_el_work_rule_montage;
  }

  public void setMad_el_work_rule_montage_formatted(String mad_el_work_rule_montage)
  {
    this.mad_el_work_rule_montage = StringUtil.appCurrencyString2double(mad_el_work_rule_montage);
  }

  public double getMad_el_work_rule_adjustment()
  {
    return mad_el_work_rule_adjustment;
  }

  public String getMad_el_work_rule_adjustment_formatted()
  {
    return StringUtil.double2appCurrencyStringByMask(mad_el_work_rule_adjustment, "##0.0");
  }

  public void setMad_el_work_rule_adjustment(double mad_el_work_rule_adjustment)
  {
    this.mad_el_work_rule_adjustment = mad_el_work_rule_adjustment;
  }

  public void setMad_el_work_rule_adjustment_formatted(String mad_el_work_rule_adjustment)
  {
    this.mad_el_work_rule_adjustment = StringUtil.appCurrencyString2double(mad_el_work_rule_adjustment);
  }

  public double getMad_el_road_tariff()
  {
    return mad_el_road_tariff;
  }

  public String getMad_el_road_tariff_formatted()
  {
    return StringUtil.double2appCurrencyStringByMask(mad_el_road_tariff, "##0.0");
  }

  public void setMad_el_road_tariff(double mad_el_road_tariff)
  {
    this.mad_el_road_tariff = mad_el_road_tariff;
  }

  public void setMad_el_road_tariff_formatted(String mad_el_road_tariff)
  {
    this.mad_el_road_tariff = StringUtil.appCurrencyString2double(mad_el_road_tariff);
  }

  public double getMad_el_road_rule()
  {
    return mad_el_road_rule;
  }

  public String getMad_el_road_rule_formatted()
  {
    return StringUtil.double2appCurrencyStringByMask(mad_el_road_rule, "##0.0");
  }

  public void setMad_el_road_rule(double mad_el_road_rule)
  {
    this.mad_el_road_rule = mad_el_road_rule;
  }

  public void setMad_el_road_rule_formatted(String mad_el_road_rule)
  {
    this.mad_el_road_rule = StringUtil.appCurrencyString2double(mad_el_road_rule);
  }

  public boolean isNewRecord()
  {
    return StringUtil.isEmpty(mad_id);
  }

}
