package net.sam.dcl.dao;

import net.sam.dcl.beans.CurrencyRate;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.util.StringUtil;

import java.util.Date;

public class CurrencyRateDAO
{
  public static CurrencyRate loadRateForDate(IActionContext context, String cur_id, String date) throws Exception
  {
    CurrencyRate currencyRate = new CurrencyRate();
    currencyRate.setCur_id(cur_id);
    currencyRate.setCrt_date(date);
    loadRateForDate(context, currencyRate);

    return currencyRate;
  }

  public static void loadRateForDate(IActionContext context, CurrencyRate currencyRate) throws Exception
  {
    DAOUtils.load(context, "load-currency_rate_for_date", currencyRate, null);
  }

  public static boolean haveRateForDate(IActionContext context, String cur_id, String date) throws Exception
  {
    CurrencyRate currencyRate = new CurrencyRate();
    currencyRate.setCur_id(cur_id);
    currencyRate.setCrt_date(date);
    return haveRateForDate(context, currencyRate);
  }

  public static boolean haveRateForDate(IActionContext context, CurrencyRate currencyRate) throws Exception
  {
    DAOUtils.load(context, "load-currency_rate_for_date_count", currencyRate, null);
    
    return (currencyRate.getCountRatesBefore() > 0);
  }

  public static Date loadRateMinDate(IActionContext context) throws Exception
  {
    CurrencyRate currencyRate = new CurrencyRate();
    DAOUtils.load(context, "load-currency_rate_min_date", currencyRate, null);
    return StringUtil.dbDateString2Date(currencyRate.getCrt_date()); 
  }

}