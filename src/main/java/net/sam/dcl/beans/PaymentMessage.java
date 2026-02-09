package net.sam.dcl.beans;

import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.StrutsUtil;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.controller.ActionContext;

import java.io.Serializable;
import java.lang.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by IntelliJ IDEA.
 * Date: 31.08.2005
 * Time: 18:53:07
 * To change this template use File | Settings | File Templates.
 */
public class PaymentMessage implements Serializable
{
  protected static Log log = LogFactory.getLog(CalculationStateLine.class);

  String pms_id;
  String pms_create_date;
  String pms_message;
  double pms_sum;
  Currency currency = new Currency();
  Contractor contractor = new Contractor();
  double pms_percent;
  String pms_updated;

  public PaymentMessage()
  {
  }

  public PaymentMessage(String pms_id)
  {
    this.pms_id = pms_id;
  }

  public String getPms_id()
  {
    return pms_id;
  }

  public void setPms_id(String pms_id)
  {
    this.pms_id = pms_id;
  }

  public String getPms_create_date()
  {
    return pms_create_date;
  }

  public String getPmsCreateDateFormatted()
  {
    return StringUtil.dbDateTimeString2appDateTimeString(getPms_create_date());
  }

  public void setPms_create_date(String pms_create_date)
  {
    this.pms_create_date = pms_create_date;
  }

  public String getPms_message()
  {
    return pms_message;
  }

  public String getPmsMessageFormatted()
  {
    String retStr = "";
    if (!StringUtil.isEmpty(getPms_updated()))
    {
      IActionContext context = ActionContext.threadInstance();
      try
      {
        retStr = StrutsUtil.getMessage(context, "PaymentMessages.pms_updated");
      }
      catch (Exception e)
      {
        log.error(e);
      }
    }
    retStr += getPms_message();

    return retStr;
  }

  public void setPms_message(String pms_message)
  {
    this.pms_message = pms_message;
  }

  public double getPms_sum()
  {
    return pms_sum;
  }

  public String getPmsSumFormatted()
  {
    return StringUtil.double2appCurrencyString(getPms_sum());
  }

  public void setPms_sum(double pms_sum)
  {
    this.pms_sum = pms_sum;
  }

  public Currency getCurrency()
  {
    return currency;
  }

  public void setCurrency(Currency currency)
  {
    this.currency = currency;
  }

  public Contractor getContractor()
  {
    return contractor;
  }

  public void setContractor(Contractor contractor)
  {
    this.contractor = contractor;
  }

  public double getPms_percent()
  {
    return pms_percent;
  }

  public String getPmsPercentFormatted()
  {
    return StringUtil.double2appCurrencyString(getPms_percent());
  }

  public void setPms_percent(double pms_percent)
  {
    this.pms_percent = pms_percent;
  }

  public String getPms_updated()
  {
    return pms_updated;
  }

  public void setPms_updated(String pms_updated)
  {
    this.pms_updated = pms_updated;
  }
}