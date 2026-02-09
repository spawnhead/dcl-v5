package net.sam.dcl.form;

import net.sam.dcl.controller.BaseDispatchValidatorForm;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.beans.*;

public class JournalForm extends BaseDispatchValidatorForm
{
  String number;
  String date_begin;
  String date_end;
  String sum_min;
  String sum_max;
  String block;
  Contractor contractor = new Contractor();
  User user = new User();
  Department department = new Department();
  StuffCategory stuffCategory = new StuffCategory();
  Route route = new Route();
  Currency currency = new Currency();

  public String getNumber()
  {
    return number;
  }

  public void setNumber(String number)
  {
    this.number = number;
  }

  public String getDate_begin()
  {
    return date_begin;
  }

  public String getDate_begin_date()
  {
    return StringUtil.appDateString2dbDateString(getDate_begin());
  }

  public void setDate_begin(String date_begin)
  {
    this.date_begin = date_begin;
  }

  public String getDate_end()
  {
    return date_end;
  }

  public String getDate_end_date()
  {
    return StringUtil.appDateString2dbDateString(getDate_end());
  }

  public void setDate_end(String date_end)
  {
    this.date_end = date_end;
  }

  public Double getSum_min()
  {
    if (StringUtil.isEmpty(sum_min))
      return null;
    return StringUtil.appCurrencyString2double(sum_min);
  }

  public String getSum_min_formatted()
  {
    return sum_min;
  }

  public void setSum_min(Double sum_min)
  {
    this.sum_min = StringUtil.double2appCurrencyString(sum_min);
  }

  public void setSum_min_formatted(String sum_min)
  {
    this.sum_min = sum_min;
  }

  public Double getSum_max()
  {
    if (StringUtil.isEmpty(sum_max))
      return null;
    return StringUtil.appCurrencyString2double(sum_max);
  }

  public String getSum_max_formatted()
  {
    return sum_max;
  }

  public void setSum_max(Double sum_max)
  {
    this.sum_max = StringUtil.double2appCurrencyString(sum_max);
  }

  public void setSum_max_formatted(String sum_max)
  {
    this.sum_max = sum_max;
  }

  public String getBlock()
  {
    return block;
  }

  public void setBlock(String block)
  {
    this.block = block;
  }

  public Contractor getContractor()
  {
    return contractor;
  }

  public void setContractor(Contractor contractor)
  {
    this.contractor = contractor;
  }

  public User getUser()
  {
    return user;
  }

  public void setUser(User user)
  {
    this.user = user;
  }

  public Department getDepartment()
  {
    return department;
  }

  public void setDepartment(Department department)
  {
    this.department = department;
  }

  public StuffCategory getStuffCategory()
  {
    return stuffCategory;
  }

  public void setStuffCategory(StuffCategory stuffCategory)
  {
    this.stuffCategory = stuffCategory;
  }

  public Route getRoute()
  {
    return route;
  }

  public void setRoute(Route route)
  {
    this.route = route;
  }

  public Currency getCurrency()
  {
    return currency;
  }

  public void setCurrency(Currency currency)
  {
    this.currency = currency;
  }
}