package net.sam.dcl.form;

import net.sam.dcl.controller.BaseDispatchValidatorForm;
import net.sam.dcl.util.StringUtil;

/**
 * @author: DG
 * Date: Aug 6, 2005
 * Time: 5:37:29 PM
 */
public class RateNDSForm extends BaseDispatchValidatorForm
{
  String rtn_id;
  String rtn_date_from;
  double rtn_percent;

  public String getRtn_id()
  {
    return rtn_id;
  }

  public void setRtn_id(String rtn_id)
  {
    this.rtn_id = rtn_id;
  }

  public String getRtn_date_from()
  {
    return rtn_date_from;
  }

  public String getRtn_date_from_formatted()
  {
    return StringUtil.dbDateString2appDateString(rtn_date_from);
  }

  public void setRtn_date_from(String rtn_date_from)
  {
    this.rtn_date_from = rtn_date_from;
  }

  public void setRtn_date_from_formatted(String rtn_date_from)
  {
    this.rtn_date_from = StringUtil.appDateString2dbDateString(rtn_date_from);
  }

  public double getRtn_percent()
  {
    return rtn_percent;
  }

  public String getRtn_percent_formatted()
  {
    return StringUtil.double2appCurrencyString(rtn_percent);
  }

  public void setRtn_percent(double rtn_percent)
  {
    this.rtn_percent = rtn_percent;
  }

  public void setRtn_percent_formatted(String rtn_percent)
  {
    this.rtn_percent = StringUtil.appCurrencyString2double(rtn_percent);
  }
}