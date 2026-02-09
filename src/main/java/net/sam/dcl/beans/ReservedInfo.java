package net.sam.dcl.beans;

import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.StrutsUtil;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.controller.ActionContext;
import net.sam.dcl.config.Config;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Date;
import java.util.Calendar;

/**
 * Created by IntelliJ IDEA.
 * Date: 12.05.2010
 * Time: 12:55:50
 * To change this template use File | Settings | File Templates.
 */
public class ReservedInfo
{
  protected static Log log = LogFactory.getLog(ReservedInfo.class);

  String cpr_id;
  String lpc_id;
  double reserved;
  String reserved_for;
  String number;
  String reserved_end;
  String date_accept;

  public ReservedInfo()
  {
  }

  public ReservedInfo(String cpr_id, String lpc_id)
  {
    this.cpr_id = cpr_id;
    this.lpc_id = lpc_id;
  }

  public String getCpr_id()
  {
    return cpr_id;
  }

  public void setCpr_id(String cpr_id)
  {
    this.cpr_id = cpr_id;
  }

  public String getLpc_id()
  {
    return lpc_id;
  }

  public void setLpc_id(String lpc_id)
  {
    this.lpc_id = lpc_id;
  }

  public double getReserved()
  {
    return reserved;
  }

  public String getReservedFormatted()
  {
    return StringUtil.double2appCurrencyString(reserved);
  }

  public void setReserved(double reserved)
  {
    this.reserved = reserved;
  }

  public String getReserved_for()
  {
    return reserved_for;
  }

  public void setReserved_for(String reserved_for)
  {
    this.reserved_for = reserved_for;
  }

  public String getNumber()
  {
    return number;
  }

  public void setNumber(String number)
  {
    this.number = number;
  }

  public String getReserved_end()
  {
    return reserved_end;
  }

  public String getReservedEndFormatted()
  {
    String retStr = StringUtil.dbDateString2appDateString(getReserved_end());
    if (!StringUtil.isEmpty(getDate_accept()))
    {
      IActionContext context = ActionContext.threadInstance();
      try
      {
        Date movedFinishDate = StringUtil.dbDateString2Date(getReserved_end());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(movedFinishDate);
        calendar.add(Calendar.DATE, Config.getNumber(Constants.dayCountWaitReservedForShipping, 30));
        retStr += " " + StrutsUtil.getMessage(context, "ReservedInfoLines.date_accept", StringUtil.date2appDateString(calendar.getTime()));
      }
      catch (Exception e)
      {
        log.error(e);
      }
    }

    return retStr;
  }

  public void setReserved_end(String reserved_end)
  {
    this.reserved_end = reserved_end;
  }

  public String getDate_accept()
  {
    return date_accept;
  }

  public String getDateAcceptFormatted()
  {
    return StringUtil.dbDateString2appDateString(getDate_accept());
  }

  public void setDate_accept(String date_accept)
  {
    this.date_accept = date_accept;
  }
}
