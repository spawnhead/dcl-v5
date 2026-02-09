package net.sam.dcl.form;

import net.sam.dcl.controller.BaseForm;
import net.sam.dcl.taglib.table.model.impl.HolderImplUsingList;
import net.sam.dcl.util.StringUtil;

public class CurrencyRatesForm extends BaseForm
{
  String cur_id;
  String cur_name;
  HolderImplUsingList ratesGrid = new HolderImplUsingList();

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

  public HolderImplUsingList getRatesGrid()
  {
    return ratesGrid;
  }

  public void setRatesGrid(HolderImplUsingList ratesGrid)
  {
    this.ratesGrid = ratesGrid;
  }

  static public class CurrencyRate
  {
    String crt_id;
    String crt_date;
    double crt_rate;

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

    public String getCrt_date_formatted()
    {
      return StringUtil.dbDateString2appDateString(crt_date);
    }

    public void setCrt_date(String crt_date)
    {
      this.crt_date = crt_date;
    }

    public double getCrt_rate()
    {
      return crt_rate;
    }

    public String getCrtRateFormatted()
    {
      return StringUtil.double2appCurrencyStringByMask(getCrt_rate(), "#,##0.000000");
    }

    public void setCrt_rate(double crt_rate)
    {
      this.crt_rate = crt_rate;
    }
  }


}