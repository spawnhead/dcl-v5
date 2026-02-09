package net.sam.dcl.beans;

import net.sam.dcl.util.StringUtil;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by IntelliJ IDEA.
 * Date: 14.09.2005
 * Time: 17:17:20
 * To change this template use File | Settings | File Templates.
 */
public class TimeboardWork implements Serializable
{
  protected static Log log = LogFactory.getLog(TimeboardWork.class);

  String tbw_id;
  String tmb_id;
  String number;
  String selectLine;

  String tbw_date;
  String tbw_from;
  String tbw_to;
  double tbw_hours_update;
  ContractorRequest contractorRequest = new ContractorRequest();
  String tbw_comment;

  String tbw_hours_all_out;
  String tbw_hours_total_out;
  boolean itogLine = false;

  public TimeboardWork()
  {
  }

  public TimeboardWork(TimeboardWork timeboardWork)
  {
    tbw_id = timeboardWork.getTbw_id();
    tmb_id = timeboardWork.getTmb_id();
    number = timeboardWork.getNumber();
    selectLine = timeboardWork.getSelectLine();
    tbw_date = timeboardWork.getTbw_date();
    tbw_from = timeboardWork.getTbw_from();
    tbw_to = timeboardWork.getTbw_to();
    tbw_hours_update = timeboardWork.getTbw_hours_update();
    contractorRequest = new ContractorRequest(timeboardWork.getContractorRequest());
    tbw_comment = timeboardWork.getTbw_comment();
  }

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

  public String getSelectLine()
  {
    return selectLine;
  }

  public void setSelectLine(String selectLine)
  {
    this.selectLine = selectLine;
  }

  public String getTbw_date()
  {
    return tbw_date;
  }

  public String getTbw_date_formatted()
  {
    if ( isItogLine() )
    {
      return tbw_date;
    }
    
    return StringUtil.dbDateString2appDateString(tbw_date);
  }

  public void setTbw_date(String tbw_date)
  {
    this.tbw_date = tbw_date;
  }

  public void setTbw_date_formatted(String tbw_date)
  {
    this.tbw_date = StringUtil.appDateString2dbDateString(tbw_date);
  }

  public String getTbw_day_of_week()
  {
    return StringUtil.dbDateString2WeekDay(tbw_date);
  }

  public String getTbw_from()
  {
    return tbw_from;
  }

  public void setTbw_from(String tbw_from)
  {
    this.tbw_from = tbw_from;
  }

  public void setTbw_from_db(String tbw_from)
  {
    this.tbw_from = StringUtil.dbTimeString2appTimeString(tbw_from);
  }

  public String getTbw_to()
  {
    return tbw_to;
  }

  public void setTbw_to(String tbw_to)
  {
    this.tbw_to = tbw_to;
  }

  public void setTbw_to_db(String tbw_to)
  {
    this.tbw_to = StringUtil.dbTimeString2appTimeString(tbw_to);
  }

  public double getTbw_hours_all()
  {
    double hours = 0.0;
    try
    {
      hours = StringUtil.getHoursBetween(StringUtil.timeString2Date(getTbw_from()), StringUtil.timeString2Date(getTbw_to()));
    }
    catch (Exception e)
    {
      log.error(e);
    }

    return hours;
  }

  public double getTbw_hours_all_correct()
  {
    /**
     * e=(d-c)
     * если e>9, то
     * f=e-9, иначе f=0
     */
    double e = getTbw_hours_all();
    double f = 0.0;
    if ( e > 9 )
    {
      f = e - 9;
    }

    return f;
  }

  public void getTbw_hours_all_formatted()
  {
    String retStr = "";
    double e = getTbw_hours_all();
    double f = getTbw_hours_all_correct();
    retStr += StringUtil.double2appCurrencyString(e) + " ( " + StringUtil.double2appCurrencyString(f) + " )";

    setTbw_hours_all_out(retStr);
  }

  public double getTbw_hours_update()
  {
    return tbw_hours_update;
  }

  public String getTbw_hours_update_formatted()
  {
    return StringUtil.doubleWithMinus2appCurrencyString(tbw_hours_update);
  }

  public void setTbw_hours_update(double tbw_hours_update)
  {
    this.tbw_hours_update = tbw_hours_update;
  }

  public double getTbw_hours_total()
  {
    double hours = getTbw_hours_all();
    return hours - getTbw_hours_update();
  }

  public double getTbw_hours_total_correct()
  {
    /**
     * g=(e-k)
     * если g>9, то
     * h=e-9, иначе h=0
     */
    double hours = getTbw_hours_all();
    double g = getTbw_hours_total();
    double h = 0;
    if ( g > 9 )
    {
      h = hours - 9;
    }

    return h;
  }

  public void getTbw_hours_total_formatted()
  {
    String retStr = "";
    double g = getTbw_hours_total();
    double h = getTbw_hours_total_correct();
    retStr += StringUtil.double2appCurrencyString(g) + " ( " + StringUtil.double2appCurrencyString(h) + " )";

    setTbw_hours_total_out(retStr);
  }

  public ContractorRequest getContractorRequest()
  {
    return contractorRequest;
  }

  public void setContractorRequest(ContractorRequest contractorRequest)
  {
    this.contractorRequest = contractorRequest;
  }

  public String getTbw_comment()
  {
    return tbw_comment;
  }

  public String getTbw_comment_formatted()
  {
    return tbw_comment.replaceAll("\r\n", ReportDelimiterConsts.html_separator);
  }

  public void setTbw_comment(String tbw_comment)
  {
    this.tbw_comment = tbw_comment;
  }

  public String getTbw_hours_all_out()
  {
    return tbw_hours_all_out;
  }

  public void setTbw_hours_all_out(String tbw_hours_all_out)
  {
    this.tbw_hours_all_out = tbw_hours_all_out;
  }

  public String getTbw_hours_total_out()
  {
    return tbw_hours_total_out;
  }

  public void setTbw_hours_total_out(String tbw_hours_total_out)
  {
    this.tbw_hours_total_out = tbw_hours_total_out;
  }

  public boolean isItogLine()
  {
    return itogLine;
  }

  public void setItogLine(boolean itogLine)
  {
    this.itogLine = itogLine;
  }
}