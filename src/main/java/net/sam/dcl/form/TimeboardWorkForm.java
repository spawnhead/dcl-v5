package net.sam.dcl.form;

import net.sam.dcl.controller.BaseDispatchValidatorForm;
import net.sam.dcl.util.StringUtil;

public class TimeboardWorkForm extends BaseDispatchValidatorForm
{
  String tbw_id;
  String tmb_id;
  String number;

  String tbw_date;
  String tbw_from;
  String tbw_to;
  double tbw_hours_update;
  String tbw_comment;

  //сохраняем поля из ContractorRequest
  String crq_id;

  //поле для проверки
  String tmb_date;

  boolean formReadOnly = false;
  boolean hoursUpdateReadOnly = false;

  public String getTbw_id()
  {
    return tbw_id;
  }

  public void setTbw_id(String tbw_id)
  {
    this.tbw_id = tbw_id;
  }

  public String getTmb_id()
  {
    return tmb_id;
  }

  public void setTmb_id(String tmb_id)
  {
    this.tmb_id = tmb_id;
  }

  public String getNumber()
  {
    return number;
  }

  public void setNumber(String number)
  {
    this.number = number;
  }

  public String getTbw_date()
  {
    return tbw_date;
  }

  public void setTbw_date(String tbw_date)
  {
    this.tbw_date = tbw_date;
  }

  public String getTbw_from()
  {
    return tbw_from;
  }

  public void setTbw_from(String tbw_from)
  {
    this.tbw_from = tbw_from;
  }

  public String getTbw_to()
  {
    return tbw_to;
  }

  public void setTbw_to(String tbw_to)
  {
    this.tbw_to = tbw_to;
  }

  public double getTbw_hours_update()
  {
    return tbw_hours_update;
  }

  public String getTbw_hours_update_formatted()
  {
    return StringUtil.double2appCurrencyString(tbw_hours_update);
  }

  public void setTbw_hours_update(double tbw_hours_update)
  {
    this.tbw_hours_update = tbw_hours_update;
  }

  public void setTbw_hours_update_formatted(String tbw_hours_update)
  {
    if ( !StringUtil.isEmpty(tbw_hours_update) )
    {
      this.tbw_hours_update = StringUtil.appCurrencyString2double(tbw_hours_update);
    }
    else
    {
      this.tbw_hours_update = 0.0;  
    }
  }

  public String getTbw_comment()
  {
    return tbw_comment;
  }

  public void setTbw_comment(String tbw_comment)
  {
    this.tbw_comment = tbw_comment;
  }

  public String getCrq_id()
  {
    return crq_id;
  }

  public void setCrq_id(String crq_id)
  {
    this.crq_id = crq_id;
  }

  public String getTmb_date()
  {
    return tmb_date;
  }

  public void setTmb_date(String tmb_date)
  {
    this.tmb_date = tmb_date;
  }

  public void setTmb_date_formatted(String tmb_date)
  {
    this.tmb_date = StringUtil.appFullMonthYearString2appDate(tmb_date);
  }

  public boolean isFormReadOnly()
  {
    return formReadOnly;
  }

  public void setFormReadOnly(boolean formReadOnly)
  {
    this.formReadOnly = formReadOnly;
  }

  public boolean isHoursUpdateReadOnly()
  {
    return hoursUpdateReadOnly;
  }

  public void setHoursUpdateReadOnly(boolean hoursUpdateReadOnly)
  {
    this.hoursUpdateReadOnly = hoursUpdateReadOnly;
  }

  public boolean isSaveReadOnly()
  {
    return formReadOnly && hoursUpdateReadOnly;
  }
}