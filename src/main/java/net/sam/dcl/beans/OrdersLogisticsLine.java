package net.sam.dcl.beans;

import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.StrutsUtil;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.controller.ActionContext;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by IntelliJ IDEA.
 * Date: 14.09.2005
 * Time: 17:17:20
 * To change this template use File | Settings | File Templates.
 */
public class OrdersLogisticsLine implements Serializable
{
  protected static Log log = LogFactory.getLog(OrdersLogisticsLine.class);
  public static String conf_date_is_empty_english = "CONF_DATE_IS_EMPTY";

  String shp_doc_type_num;
  String ord_num;
  String conf_num;
  double weight;
  String conf_date;
  String contractor;
  String contact_person;
  String comment;
  String ord_received_conf_date;
  String have_empty_date_conf;

  boolean itogLine = false;

  public String getShp_doc_type_num()
  {
    return shp_doc_type_num;
  }

  public void setShp_doc_type_num(String shp_doc_type_num)
  {
    this.shp_doc_type_num = shp_doc_type_num;
  }

  public String getOrd_num()
  {
    return ord_num;
  }

  public void setOrd_num(String ord_num)
  {
    this.ord_num = ord_num;
  }

  public String getConf_num()
  {
    return conf_num;
  }

  public void setConf_num(String conf_num)
  {
    this.conf_num = conf_num;
  }

  public String getOrd_conf_num()
  {
    return ord_num + ReportDelimiterConsts.html_separator + conf_num;
  }

  public double getWeight()
  {
    return weight;
  }

  public String getWeight_formatted()
  {
    return StringUtil.double2appCurrencyStringByMask(weight, "#,##0.000");
  }

  public void setWeight(double weight)
  {
    this.weight = weight;
  }

  public String getConf_date()
  {
    String strForReplase = "";
    IActionContext context = ActionContext.threadInstance();
    try
    {
      strForReplase = StrutsUtil.getMessage(context, "OrdersLogistics.conf_date_is_empty");
    }
    catch (Exception e)
    {
      log.error(e);
    }
    String retStr = conf_date.replaceAll(conf_date_is_empty_english, strForReplase);

    try
    {
      if ( !StringUtil.isEmpty(getOrd_received_conf_date()) && !StringUtil.isEmpty(getHave_empty_date_conf()) )
      {
        Date today = StringUtil.getCurrentDateTime();
        Date dateCheck = StringUtil.appDateString2Date(getOrd_received_conf_date_formatted());
        long days = StringUtil.getDaysBetween(dateCheck, today);
        retStr += " " + StrutsUtil.getMessage(context, "Orders.have_empty_date_conf", Long.toString(days));
      }
    }
    catch (Exception e)
    {
      log.error(e);
    }

    return retStr;
  }

  public void setConf_date(String conf_date)
  {
    this.conf_date = conf_date;
  }

  public String getContractor()
  {
    return contractor;
  }

  public void setContractor(String contractor)
  {
    this.contractor = contractor;
  }

  public String getContact_person()
  {
    return contact_person;
  }

  public void setContact_person(String contact_person)
  {
    this.contact_person = contact_person;
  }

  public String getContractor_contact_person()
  {
    return contractor + ReportDelimiterConsts.html_separator + contact_person;
  }

  public String getCommentExcel()
  {
    return comment;
  }

  public String getComment()
  {
    String retStr = getCommentExcel();

    if (!StringUtil.isEmpty(retStr))
    {
      retStr = retStr.replaceAll("\r\n", ReportDelimiterConsts.html_separator);
    }

    return retStr;
  }

  public void setComment(String comment)
  {
    this.comment = comment;
  }

  public String getOrd_received_conf_date()
  {
    return ord_received_conf_date;
  }

  public String getOrd_received_conf_date_formatted()
  {
    return StringUtil.dbDateString2appDateString(ord_received_conf_date);
  }

  public void setOrd_received_conf_date(String ord_received_conf_date)
  {
    this.ord_received_conf_date = ord_received_conf_date;
  }

  public String getHave_empty_date_conf()
  {
    return have_empty_date_conf;
  }

  public void setHave_empty_date_conf(String have_empty_date_conf)
  {
    this.have_empty_date_conf = have_empty_date_conf;
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
