package net.sam.dcl.form;

import net.sam.dcl.controller.BaseDispatchValidatorForm;
import net.sam.dcl.taglib.table.model.impl.HolderImplUsingList;
import net.sam.dcl.util.StringUtil;

/**
 * @author: DG
 * Date: Aug 6, 2005
 * Time: 5:37:29 PM
 */
public class CurrenciesForm extends BaseDispatchValidatorForm
{
  HolderImplUsingList currenciesGrid = new HolderImplUsingList();
  HolderImplUsingList currenciesRatesForMod = new HolderImplUsingList();
  String courseDate;

  boolean showConfirmMsg = false;
  boolean showOkMsg = false;
  String message;

  public HolderImplUsingList getCurrenciesGrid()
  {
    return currenciesGrid;
  }

  public void setCurrenciesGrid(HolderImplUsingList currenciesGrid)
  {
    this.currenciesGrid = currenciesGrid;
  }

  public HolderImplUsingList getCurrenciesRatesForMod()
  {
    return currenciesRatesForMod;
  }

  public void setCurrenciesRatesForMod(HolderImplUsingList currenciesRatesForMod)
  {
    this.currenciesRatesForMod = currenciesRatesForMod;
  }

  public String getCourseDate()
  {
    return courseDate;
  }

  public String getCourseDateTs()
  {
    return StringUtil.appDateString2dbDateString(courseDate);
  }

  public void setCourseDate(String courseDate)
  {
    this.courseDate = courseDate;
  }

  public boolean isShowConfirmMsg()
  {
    return showConfirmMsg;
  }

  public void setShowConfirmMsg(boolean showConfirmMsg)
  {
    this.showConfirmMsg = showConfirmMsg;
  }

  public boolean isShowOkMsg()
  {
    return showOkMsg;
  }

  public void setShowOkMsg(boolean showOkMsg)
  {
    this.showOkMsg = showOkMsg;
  }

  public String getMessage()
  {
    return message;
  }

  public void setMessage(String message)
  {
    this.message = message;
  }

  static public class Currency
  {
    String cur_id;
    String cur_name;
    String cur_no_round;

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

    public String getCur_no_round()
    {
      return cur_no_round;
    }

    public void setCur_no_round(String cur_no_round)
    {
      this.cur_no_round = cur_no_round;
    }
  }

  static public class CurrencyRate
  {
    String cur_id;
    String cur_name;
    String crt_id;
    String crt_date;
    double crt_rate;

    boolean needInsert = false;

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

    public String getCrt_id()
    {
      return crt_id;
    }

    public void setCrt_id(String crt_id)
    {
      this.crt_id = crt_id;
    }

    public String getCrt_date()
    {
      return crt_date;
    }

    public void setCrt_date(String crt_date)
    {
      this.crt_date = crt_date;
    }

    public double getCrt_rate()
    {
      return crt_rate;
    }

    public void setCrt_rate(double crt_rate)
    {
      this.crt_rate = crt_rate;
    }

    public boolean isNeedInsert()
    {
      return needInsert;
    }

    public void setNeedInsert(boolean needInsert)
    {
      this.needInsert = needInsert;
    }
  }
}
