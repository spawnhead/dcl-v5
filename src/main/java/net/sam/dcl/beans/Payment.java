package net.sam.dcl.beans;

import net.sam.dcl.controller.ActionContext;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.StrutsUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Date: 31.08.2005
 * Time: 18:53:07
 * To change this template use File | Settings | File Templates.
 */
public class Payment implements Serializable
{
  protected static Log log = LogFactory.getLog(Payment.class);

  public static String managerRoleId = "2";

  String is_new_doc;

  String pay_id;
  String id;

  User createUser = new User();
  User editUser = new User();
  String usr_date_create;
  String usr_date_edit;

  String pay_date;
  Contractor contractor = new Contractor();
  String pay_account;
  Currency currency = new Currency();
  double pay_summ;
  double pay_course;
  double pay_course_nbrb;
  String pay_course_nbrb_date;
  double pay_summ_eur;
  double pay_summ_eur_nbrb;
  double pay_summ_nr;
  String pay_block;
  String pay_comment;
  String pay_closed;

  List<PaySum> paySums = new ArrayList<PaySum>();
  List<PaySum> paySumsOld = null;

  public Payment()
  {
  }

  public Payment(String pay_id)
  {
    this.pay_id = pay_id;
  }

  public User getCreateUser()
  {
    return createUser;
  }

  public void setCreateUser(User createUser)
  {
    this.createUser = createUser;
  }

  public User getEditUser()
  {
    return editUser;
  }

  public void setEditUser(User editUser)
  {
    this.editUser = editUser;
  }

  public String getIs_new_doc()
  {
    return is_new_doc;
  }

  public void setIs_new_doc(String is_new_doc)
  {
    this.is_new_doc = is_new_doc;
  }

  public String getPay_id()
  {
    return pay_id;
  }

  public void setPay_id(String pay_id)
  {
    this.pay_id = pay_id;
  }

  public String getId()
  {
    return id;
  }

  public void setId(String id)
  {
    this.id = id;
  }

  public String getUsr_date_create()
  {
    return usr_date_create;
  }

  public void setUsr_date_create(String usr_date_create)
  {
    this.usr_date_create = usr_date_create;
  }

  public String getUsr_date_edit()
  {
    return usr_date_edit;
  }

  public void setUsr_date_edit(String usr_date_edit)
  {
    this.usr_date_edit = usr_date_edit;
  }

  public String getPay_date()
  {
    return pay_date;
  }

  public Date getPayDate() throws ParseException
  {
	  return StringUtil.dbDateString2Date(getPay_date());
  }

  public String getPay_date_ts()
  {
    return StringUtil.appDateString2dbDateString(getPay_date());
  }

  public void setPay_date(String pay_date)
  {
    this.pay_date = pay_date;
  }

  public Contractor getContractor()
  {
    return contractor;
  }

  public void setContractor(Contractor contractor)
  {
    this.contractor = contractor;
  }

  public String getPay_account()
  {
    return pay_account;
  }

  public void setPay_account(String pay_account)
  {
    this.pay_account = pay_account;
  }

  public Currency getCurrency()
  {
    return currency;
  }

  public void setCurrency(Currency currency)
  {
    this.currency = currency;
  }

  public double getPay_summ()
  {
    return pay_summ;
  }

  public void setPay_summ(double pay_summ)
  {
    this.pay_summ = pay_summ;
  }

  public double getPay_course()
  {
    return pay_course;
  }

  public void setPay_course(double pay_course)
  {
    this.pay_course = pay_course;
  }

  public double getPay_course_nbrb()
  {
    return pay_course_nbrb;
  }

  public void setPay_course_nbrb(double pay_course_nbrb)
  {
    this.pay_course_nbrb = pay_course_nbrb;
  }

  public String getPay_course_nbrb_date()
  {
    return pay_course_nbrb_date;
  }

  public String getPay_course_nbrb_date_ts()
  {
    return StringUtil.appDateString2dbDateString(getPay_course_nbrb_date());
  }

  public void setPay_course_nbrb_date(String pay_course_nbrb_date)
  {
    this.pay_course_nbrb_date = pay_course_nbrb_date;
  }

  public double getPay_summ_eur()
  {
    return pay_summ_eur;
  }

  public void setPay_summ_eur(double pay_summ_eur)
  {
    this.pay_summ_eur = pay_summ_eur;
  }

  public double getPay_summ_eur_nbrb()
  {
    return pay_summ_eur_nbrb;
  }

  public void setPay_summ_eur_nbrb(double pay_summ_eur_nbrb)
  {
    this.pay_summ_eur_nbrb = pay_summ_eur_nbrb;
  }

  public double getPay_summ_nr()
  {
    return pay_summ_nr;
  }

  public void setPay_summ_nr(double pay_summ_nr)
  {
    this.pay_summ_nr = pay_summ_nr;
  }

  public String getPay_block()
  {
    return pay_block;
  }

  public void setPay_block(String pay_block)
  {
    this.pay_block = pay_block;
  }

  public String getPay_comment()
  {
    return pay_comment;
  }

  public void setPay_comment(String pay_comment)
  {
    this.pay_comment = pay_comment;
  }

  public String getPay_closed()
  {
    return pay_closed;
  }

  public void setPay_closed(String pay_closed)
  {
    this.pay_closed = pay_closed;
  }

  public List<PaySum> getPaySums()
  {
    return paySums;
  }

  public void setPaySums(List<PaySum> paySums)
  {
    this.paySums = paySums;
  }

  public List<PaySum> getPaySumsOld()
  {
    return paySumsOld;
  }

  public void setPaySumsOld(List<PaySum> paySumsOld)
  {
    this.paySumsOld = paySumsOld;
  }

  public String getManagerRoleId()
  {
    return managerRoleId;
  }

  public String getMsgReceivedPayment()
  {
    String retStr = "";
    IActionContext context = ActionContext.threadInstance();
    try
    {
      retStr = StrutsUtil.getMessage(context, "PaymentMessage.msgReceivedPayment", getPay_date());
      if (StringUtil.isEmpty(getContractor().getId()))
      {
        retStr += " " + StrutsUtil.getMessage(context, "PaymentMessage.msgAccount", getPay_account());   
      }
    }
    catch (Exception e)
    {
      log.error(e);
    }

    return retStr;
  }

  public String getMsgReceivedPaymentByCon()
  {
    String retStr = "";
    IActionContext context = ActionContext.threadInstance();
    try
    {
      retStr = StrutsUtil.getMessage(context, "PaymentMessage.msgReceivedPaymentByCon", getPay_date());
    }
    catch (Exception e)
    {
      log.error(e);
    }

    return retStr;
  }

  public String getMsgFrom()
  {
    String retStr = "";
    IActionContext context = ActionContext.threadInstance();
    try
    {
      retStr = StrutsUtil.getMessage(context, "msg.common.from_only");
    }
    catch (Exception e)
    {
      log.error(e);
    }

    return retStr;
  }

  public String getMsgSpec()
  {
    String retStr = "";
    IActionContext context = ActionContext.threadInstance();
    try
    {
      retStr = StrutsUtil.getMessage(context, "PaymentMessage.msgSpec");
    }
    catch (Exception e)
    {
      log.error(e);
    }

    return retStr;
  }

  public String getMsgComment()
  {
    String retStr = "";
    IActionContext context = ActionContext.threadInstance();
    try
    {
      if (!StringUtil.isEmpty(getPay_comment()))
        retStr = StrutsUtil.getMessage(context, "PaymentMessage.msgComment", getPay_comment());
    }
    catch (Exception e)
    {
      log.error(e);
    }

    return retStr;
  }

  public void calculateInString()
  {
    pay_summ_nr = StringUtil.roundN(getPay_summ(), 2);

    for (int i = 0; i < paySums.size(); i++)
    {
      PaySum paySum = paySums.get(i);

      paySum.setNumber(String.valueOf(i + 1));

      double sum = StringUtil.roundN(paySum.getLps_summ(), 2);
      if ( getPay_summ() == 0 )
      {
        paySum.setLps_summ_eur(Double.NaN);
      }
      else
      {
        double sumAll = StringUtil.roundN(getPay_summ(), 2);
        double sumAllEUR = StringUtil.roundN(getPay_summ_eur(), 2);
        paySum.setLps_summ_eur( sumAllEUR / sumAll * sum );
      }

      double sumOutNds = paySum.getLps_summ_eur();
      sumOutNds = sumOutNds / (100 + paySum.getSpecification().getSpc_nds_rate()) * 100;
      sumOutNds = StringUtil.roundN(sumOutNds, 2);
      paySum.setLps_summ_out_nds(sumOutNds);

      pay_summ_nr -= sum;
    }

    setPay_summ_nr(StringUtil.roundN(getPay_summ_nr(), 2));
  }

  public PaySum getEmptyPaySum()
  {
    PaySum paySum = new PaySum();
    paySum.setNumber("");
    paySum.setLps_summ(0.0);
    paySum.setContract(new Contract());
    paySum.setSpecification(new Specification());
    paySum.setLps_occupied("");

    return paySum;
  }

  public void courseChanged()
  {
    if ( getPay_course() != 0.0 )
    {
      setPay_summ_eur(StringUtil.roundN(getPay_summ() / getPay_course(), 2));
    }
    else
    {
      setPay_summ_eur(Double.NaN);
    }

    calculateInString();
  }

  public void courseNBRBChanged()
  {
    if ( getPay_course_nbrb() != 0.0 )
    {
      setPay_summ_eur_nbrb(StringUtil.roundN(getPay_summ() / getPay_course_nbrb(), 2));
    }
    else
    {
      setPay_summ_eur_nbrb(Double.NaN);
    }
  }

  public void calculate()
  {
    if ( getPay_course() != 0.0 )
    {
      setPay_summ_eur(StringUtil.roundN(getPay_summ() / getPay_course(), 2));
    }
    else
    {
      setPay_summ_eur(Double.NaN);
    }

    if ( getPay_course_nbrb() != 0.0 )
    {
      setPay_summ_eur_nbrb(StringUtil.roundN(getPay_summ() / getPay_course_nbrb(), 2));
    }
    else
    {
      setPay_summ_eur_nbrb(Double.NaN);
    }

    calculateInString();
  }

  public double getAllocatedDocSum(String spcId)
  {
    double allocatedSum = 0.0;
    for (PaySum paySum : paySums)
    {
      if (paySum.getSpecification().getSpc_id().equals(spcId))
      {
        allocatedSum += paySum.getLps_summ();
      }
    }

    return allocatedSum;
  }

  public void setListParentIds()
  {
    for (PaySum paySum : paySums)
    {
      paySum.setPay_id(getPay_id());
    }
  }

  public void setListIdsToNull()
  {
    for (PaySum paySum : paySums)
    {
      paySum.setLps_id(null);
    }
  }

  public PaySum findPaySum(String number)
  {
    for (PaySum paySum : paySums)
    {
      if (paySum.getNumber().equalsIgnoreCase(number))
        return paySum;
    }

    return null;
  }

  public void updatePaySum(String number, PaySum paySumIn)
  {
    for (int i = 0; i < paySums.size(); i++)
    {
      PaySum paySum = paySums.get(i);

      if (paySum.getNumber().equalsIgnoreCase(number))
      {
        paySums.set(i, paySumIn);
        return;
      }
    }
  }

  public void deletePaySum(String number)
  {
    for (int i = 0; i < paySums.size(); i++)
    {
      PaySum paySum = paySums.get(i);

      if (paySum.getNumber().equalsIgnoreCase(number))
        paySums.remove(i);
    }
  }

  public void insertPaySum(PaySum paySum)
  {
    paySums.add(paySums.size(), paySum);
  }

  public boolean isIncorrectCurrency(String cur_id)
  {
    for (PaySum paySum : paySums)
    {
      if ( !cur_id.equalsIgnoreCase(paySum.getContract().getCurrency().getId()) )
        return true;
    }

    return false;
  }

}
