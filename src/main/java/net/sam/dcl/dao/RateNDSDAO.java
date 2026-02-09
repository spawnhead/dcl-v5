package net.sam.dcl.dao;

import net.sam.dcl.beans.RateNDS;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.util.DAOUtils;

public class RateNDSDAO
{

  public static RateNDS loadForDate(IActionContext context, String date) throws Exception
  {
    RateNDS rateNDS = new RateNDS("", date);
    if (loadForDate(context, rateNDS))
    {
      return rateNDS;
    }
    throw new LoadException(rateNDS, date);
  }

  public static boolean loadForDate(IActionContext context, RateNDS rateNDS) throws Exception
  {
    return (DAOUtils.load(context, "select-rate_nds_for_date", rateNDS, null));
  }
}