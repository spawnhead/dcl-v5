package net.sam.dcl.form;

import net.sam.dcl.controller.BaseForm;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.controller.ActionContext;
import net.sam.dcl.taglib.table.model.impl.HolderImplUsingList;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.StrutsUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author: DG
 * Date: Aug 6, 2005
 * Time: 5:37:29 PM
 */
public class MontageAdjustmentsHistoryForm extends BaseForm
{
  protected static Log log = LogFactory.getLog(MontageAdjustmentsHistoryForm.class);

  String mad_id;
  String mad_machine_type;
  HolderImplUsingList grid = new HolderImplUsingList();

  public MontageAdjustmentsHistoryForm()
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

  public String getMad_machine_type()
  {
    return mad_machine_type;
  }

  public void setMad_machine_type(String mad_machine_type)
  {
    this.mad_machine_type = mad_machine_type;
  }

  public HolderImplUsingList getGrid()
  {
    return grid;
  }

  public void setGrid(HolderImplUsingList grid)
  {
    this.grid = grid;
  }

  static public class MontageAdjustmentHistory
  {
    String madh_id;
    String mad_id;
    String mad_date_from;
    double mad_mech_work_tariff;
    double mad_mech_work_rule_montage;
    double mad_mech_work_rule_adjustment;
    double mad_mech_work_sum;
    double mad_mech_road_tariff;
    double mad_mech_road_rule;
    double mad_mech_road_sum;
    double mad_mech_total;
    double mad_el_work_tariff;
    double mad_el_work_rule_montage;
    double mad_el_work_rule_adjustment;
    double mad_el_work_sum;
    double mad_el_road_tariff;
    double mad_el_road_rule;
    double mad_el_road_sum;
    double mad_el_total;

    public String getMadh_id()
    {
      return madh_id;
    }

    public void setMadh_id(String madh_id)
    {
      this.madh_id = madh_id;
    }

    public String getMad_id()
    {
      return mad_id;
    }

    public void setMad_id(String mad_id)
    {
      this.mad_id = mad_id;
    }

    public String getMad_date_from()
    {
      return StringUtil.dbDateString2appDateString(mad_date_from);
    }

    public void setMad_date_from(String mad_date_from)
    {
      this.mad_date_from = mad_date_from;
    }

    public String getMad_work_type()
    {
      String workType = "";

      IActionContext context = ActionContext.threadInstance();
      try
      {
        workType += "<table cellSpacing=0 cellPadding=0 border=0 width='100%' class='grid'><tr class='grid-row-inner1'><td>";
        workType += StrutsUtil.getMessage(context, "MontageAdjustmentsHistory.mad_work_type1");
        workType += "</td></tr>";
        workType += "<tr class='grid-row-inner2'><td>";
        workType += StrutsUtil.getMessage(context, "MontageAdjustmentsHistory.mad_work_type2");
        workType += "</td></tr></table>";
      }
      catch (Exception e)
      {
        log.error(e);
      }

      return workType;
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

    public double getMad_mech_work_sum()
    {
      return mad_mech_work_sum;
    }

    public void setMad_mech_work_sum(double mad_mech_work_sum)
    {
      this.mad_mech_work_sum = mad_mech_work_sum;
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

    public double getMad_mech_road_sum()
    {
      return mad_mech_road_sum;
    }

    public void setMad_mech_road_sum(double mad_mech_road_sum)
    {
      this.mad_mech_road_sum = mad_mech_road_sum;
    }

    public double getMad_mech_total()
    {
      return mad_mech_total;
    }

    public void setMad_mech_total(double mad_mech_total)
    {
      this.mad_mech_total = mad_mech_total;
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

    public double getMad_el_work_sum()
    {
      return mad_el_work_sum;
    }

    public void setMad_el_work_sum(double mad_el_work_sum)
    {
      this.mad_el_work_sum = mad_el_work_sum;
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

    public double getMad_el_road_sum()
    {
      return mad_el_road_sum;
    }

    public void setMad_el_road_sum(double mad_el_road_sum)
    {
      this.mad_el_road_sum = mad_el_road_sum;
    }

    public double getMad_el_total()
    {
      return mad_el_total;
    }

    public void setMad_el_total(double mad_el_total)
    {
      this.mad_el_total = mad_el_total;
    }

    public String getFormattedString(double mechSum, double elSum)
    {
      String formattedString = "";

      formattedString += "<table cellSpacing=0 cellPadding=0 border=0 width='100%' class='grid'><tr class='grid-row-inner1'><td align='right'>";
      formattedString += StringUtil.double2appCurrencyStringByMask(mechSum, "##0.0");
      formattedString += "</td></tr>";
      formattedString += "<tr class='grid-row-inner2'><td align='right'>";
      formattedString += StringUtil.double2appCurrencyStringByMask(elSum, "##0.0");
      formattedString += "</td></tr></table>";

      return formattedString;
    }

    public String getFormattedStringItog(double mechSum, double elSum)
    {
      String workSum = "";

      workSum += "<table cellSpacing=0 cellPadding=0 border=0 width='100%' class='grid'><tr class='grid-row-inner1 dark'><td align='right'>";
      workSum += StringUtil.double2appCurrencyStringByMask(mechSum, "##0.0");
      workSum += "</td></tr>";
      workSum += "<tr class='grid-row-inner2 dark'><td align='right'>";
      workSum += StringUtil.double2appCurrencyStringByMask(elSum, "##0.0");
      workSum += "</td></tr></table>";

      return workSum;
    }

    public String getMad_work_tariff()
    {
      return getFormattedString(mad_mech_work_tariff, mad_el_work_tariff);
    }

    public String getMad_work_rule_montage()
    {
      return getFormattedString(mad_mech_work_rule_montage, mad_el_work_rule_montage);
    }

    public String getMad_work_rule_adjustment()
    {
      return getFormattedString(mad_mech_work_rule_adjustment, mad_el_work_rule_adjustment);
    }

    public String getMad_work_sum()
    {
      return getFormattedStringItog(mad_mech_work_sum, mad_el_work_sum);
    }

    public String getMad_road_tariff()
    {
      return getFormattedString(mad_mech_road_tariff, mad_el_road_tariff);
    }

    public String getMad_road_rule()
    {
      return getFormattedString(mad_mech_road_rule, mad_el_road_rule);
    }

    public String getMad_road_sum()
    {
      return getFormattedStringItog(mad_mech_road_sum, mad_el_road_sum);
    }

    public String getMad_total()
    {
      return getFormattedStringItog(mad_mech_total, mad_el_total);
    }
  }

}