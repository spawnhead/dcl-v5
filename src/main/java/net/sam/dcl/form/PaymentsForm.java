package net.sam.dcl.form;

import net.sam.dcl.controller.ActionContext;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.taglib.table.model.impl.HolderImplUsingList;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.StrutsUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: DG
 * Date: Aug 6, 2005
 * Time: 5:37:29 PM
 */
public class PaymentsForm extends JournalForm
{
  protected static Log log = LogFactory.getLog(PaymentsForm.class);
  HolderImplUsingList grid = new HolderImplUsingList();
  String pay_id;
  String closed_closed;
  String closed_open;
  String closed_all;
  String pay_account;

  public String getPay_id()
  {
    return pay_id;
  }

  public void setPay_id(String pay_id)
  {
    this.pay_id = pay_id;
  }

  public String getClosed_closed()
  {
    return closed_closed;
  }

  public void setClosed_closed(String closed_closed)
  {
    this.closed_closed = closed_closed;
  }

  public String getClosed_open()
  {
    return closed_open;
  }

  public void setClosed_open(String closed_open)
  {
    this.closed_open = closed_open;
  }

  public String getClosed_all()
  {
    return closed_all;
  }

  public void setClosed_all(String closed_all)
  {
    this.closed_all = closed_all;
  }

  public String getPay_account()
  {
    return pay_account;
  }

  public void setPay_account(String pay_account)
  {
    this.pay_account = pay_account;
  }

  public String getClosed()
  {
    if ("1".equals(getClosed_all()))
    {
      return "0";
    }
    if ("1".equals(getClosed_closed()))
    {
      return "1";
    }
    if ("1".equals(getClosed_open()))
    {
      return "-1";
    }

    return "0";
  }

  public HolderImplUsingList getGrid()
  {
    return grid;
  }

  public void setGrid(HolderImplUsingList grid)
  {
    this.grid = grid;
  }

  public String getCalculatedSums()
  {
    String retStr = "";

    Map<String, Double> currencySum = new HashMap<String, Double>();
    for (int i = 0; i < getGrid().getDataList().size(); i++)
    {
      Payment payment = (Payment) getGrid().getDataList().get(i);
      Double sum = currencySum.get(payment.getPay_currency());
      sum = (sum == null ? payment.getPay_summ() : sum + payment.getPay_summ());
      currencySum.put(payment.getPay_currency(), sum);
    }

    IActionContext context = ActionContext.threadInstance();
    try
    {
      if ( currencySum.keySet().size() > 0 )
        retStr = StrutsUtil.getMessage(context, "Payments.itog");
    }
    catch (Exception e)
    {
      log.error(e);
    }
    
    for (String currency : currencySum.keySet())
    {
      Double sum = currencySum.get(currency);
      retStr += "&nbsp;&nbsp;&nbsp;&nbsp;" + StringUtil.double2appCurrencyString(sum) + "&nbsp;" + currency;
    }

    return retStr;
  }

  static public class Payment
  {
    String pay_id;
    String pay_date;
    double pay_summ;
    String pay_contractor;
    String pay_currency;
    String pay_block;
    String usr_id_create;
    String pay_account;
    double pay_course;
    double pay_course_nbrb;
    double pay_summ_eur;
    double pay_summ_eur_nbrb;
    String double_account;
    String double_contractors;
    String pay_comment;

    public String getPay_id()
    {
      return pay_id;
    }

    public void setPay_id(String pay_id)
    {
      this.pay_id = pay_id;
    }

    public String getPay_date()
    {
      return pay_date;
    }

    public void setPay_date(String pay_date)
    {
      this.pay_date = pay_date;
    }

    public String getPay_date_formatted()
    {
      return StringUtil.dbDateString2appDateString(pay_date);
    }

    public double getPay_summ()
    {
      return pay_summ;
    }

    public String getPay_summ_formatted()
    {
      return StringUtil.double2appCurrencyString(pay_summ);
    }

    public void setPay_summ(double pay_summ)
    {
      this.pay_summ = pay_summ;
    }

    public void setPay_summ_formatted(String pay_summ)
    {
      this.pay_summ = StringUtil.appCurrencyString2double(pay_summ);
    }

    public String getPay_contractor()
    {
      if (StringUtil.isEmpty(double_account))
      {
        return pay_contractor;
      }
      else
      {
        IActionContext context = ActionContext.threadInstance();
        String retStr = "";
        try
        {
          String doubleContractors = double_contractors;
          if (!StringUtil.isEmpty(doubleContractors))
          {
            doubleContractors = doubleContractors.substring(0, doubleContractors.length() - 2);
          }
          retStr = pay_contractor + " <b>(" + StrutsUtil.getMessage(context, "Payments.pay_contractor_before") + " " + doubleContractors + StrutsUtil.getMessage(context, "Payments.pay_contractor_after") + ")</b>";
        }
        catch (Exception e)
        {
          log.error(e);
        }
        return retStr;
      }
    }

    public void setPay_contractor(String pay_contractor)
    {
      this.pay_contractor = pay_contractor;
    }

    public String getPay_currency()
    {
      return pay_currency;
    }

    public void setPay_currency(String pay_currency)
    {
      this.pay_currency = pay_currency;
    }

    public String getPay_block()
    {
      return pay_block;
    }

    public void setPay_block(String pay_block)
    {
      this.pay_block = pay_block;
    }

    public String getUsr_id_create()
    {
      return usr_id_create;
    }

    public void setUsr_id_create(String usr_id_create)
    {
      this.usr_id_create = usr_id_create;
    }

    public String getPay_account()
    {
      return pay_account;
    }

    public void setPay_account(String pay_account)
    {
      this.pay_account = pay_account;
    }

    public double getPay_course()
    {
      return pay_course;
    }

    public String getPayCourseFormatted()
    {
      return StringUtil.double2appCurrencyStringByMask(getPay_course(), "#,##0.000000");
    }

    public void setPay_course(double pay_course)
    {
      this.pay_course = pay_course;
    }

    public void setPayCourseFormatted(String pay_course)
    {
      setPay_course(StringUtil.appCurrencyString2double(pay_course));
    }

    public double getPay_course_nbrb()
    {
      return pay_course_nbrb;
    }

    public String getPayCourseNbrbFormatted()
    {
      return StringUtil.double2appCurrencyStringByMask(getPay_course_nbrb(), "#,##0.000000");
    }

    public void setPay_course_nbrb(double pay_course_nbrb)
    {
      this.pay_course_nbrb = pay_course_nbrb;
    }

    public void setPayCourseNbrbFormatted(String pay_course_nbrb)
    {
      setPay_course_nbrb(StringUtil.appCurrencyString2double(pay_course_nbrb));
    }

    public double getPay_summ_eur()
    {
      return pay_summ_eur;
    }

    public String getPay_summ_eur_formatted()
    {
      if (getPay_summ_eur() == 0)
        setPay_summ_eur(Double.NaN);

      return StringUtil.double2appCurrencyString(getPay_summ_eur());
    }

    public void setPay_summ_eur(double pay_summ_eur)
    {
      this.pay_summ_eur = pay_summ_eur;
    }

    public void setPay_summ_eur_formatted(String pay_summ_eur)
    {
      setPay_summ_eur(StringUtil.appCurrencyString2double(pay_summ_eur));
    }

    public double getPay_summ_eur_nbrb()
    {
      return pay_summ_eur_nbrb;
    }

    public String getPay_summ_eur_nbrb_formatted()
    {
      if (getPay_summ_eur_nbrb() == 0)
        return "";

      return StringUtil.double2appCurrencyString(getPay_summ_eur_nbrb());
    }

    public void setPay_summ_eur_nbrb(double pay_summ_eur_nbrb)
    {
      this.pay_summ_eur_nbrb = pay_summ_eur_nbrb;
    }

    public void setPay_summ_eur_nbrb_formatted(String pay_summ_eur_nbrb)
    {
      setPay_summ_eur_nbrb(StringUtil.appCurrencyString2double(pay_summ_eur_nbrb));
    }

    public String getDouble_account()
    {
      return double_account;
    }

    public void setDouble_account(String double_account)
    {
      this.double_account = double_account;
    }

    public String getDouble_contractors()
    {
      return double_contractors;
    }

    public void setDouble_contractors(String double_contractors)
    {
      this.double_contractors = double_contractors;
    }

    public String getPay_comment()
    {
      return pay_comment;
    }

    public void setPay_comment(String pay_comment)
    {
      this.pay_comment = pay_comment;
    }

    public boolean isHasComment()
    {
      return !StringUtil.isEmpty(getPay_comment());
    }
  }

}
