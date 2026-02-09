package net.sam.dcl.form;

import net.sam.dcl.controller.BaseDispatchValidatorForm;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.controller.ActionContext;
import net.sam.dcl.beans.*;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.StrutsUtil;
import net.sam.dcl.taglib.table.model.impl.HolderImplUsingList;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Date: Aug 6, 2005
 * Time: 5:37:29 PM
 */
public class PaymentForm extends BaseDispatchValidatorForm
{
  protected static Log log = LogFactory.getLog(PaymentForm.class);

  String is_new_doc;
  String number;

  String pay_id;

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

  boolean formReadOnly = false;
  boolean readOnlyForManager = false;
  boolean readOnlySave = false;

  HolderImplUsingList grid = new HolderImplUsingList();

  public String getIs_new_doc()
  {
    return is_new_doc;
  }

  public void setIs_new_doc(String is_new_doc)
  {
    this.is_new_doc = is_new_doc;
  }

  public String getNumber()
  {
    return number;
  }

  public void setNumber(String number)
  {
    this.number = number;
  }

  public String getPay_id()
  {
    return pay_id;
  }

  public void setPay_id(String pay_id)
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

  public String getPay_date_ts()
  {
    return StringUtil.appDateString2dbDateString(pay_date);
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

  public String getPay_summ_formatted()
  {
    return StringUtil.doubleWithMinus2appCurrencyString(pay_summ);
  }

  public void setPay_summ(double pay_summ)
  {
    this.pay_summ = pay_summ;
  }

  public void setPay_summ_formatted(String pay_summ)
  {
    this.pay_summ = StringUtil.appCurrencyString2double(pay_summ);
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

  public String getPay_course_nbrb_date()
  {
    return pay_course_nbrb_date;
  }

  public String getPayCourseNbrbDateFormatted()
  {
   String retStr = "";
    IActionContext context = ActionContext.threadInstance();
    try
    {
      if (!StringUtil.isEmpty(getPay_course_nbrb_date()))
      {
        retStr += " " + StrutsUtil.getMessage(context, "msg.common.from_only") + " " + getPay_course_nbrb_date();
      }
    }
    catch (Exception e)
    {
      log.error(e);
    }

    return retStr;
  }

  public void setPayCourseNbrbDate(String pay_course_nbrb_date)
  {
    this.pay_course_nbrb_date = pay_course_nbrb_date;
  }

  public double getPay_summ_eur()
  {
    return pay_summ_eur;
  }

  public String getPay_summ_eur_formatted()
  {
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

  public double getPay_summ_nr()
  {
    return pay_summ_nr;
  }

  public String getPay_summ_nr_formatted()
  {
    return StringUtil.double2appCurrencyString(pay_summ_nr);
  }

  public void setPay_summ_nr(double pay_summ_nr)
  {
    this.pay_summ_nr = pay_summ_nr;
  }

  public void setPay_summ_nr_formatted(String pay_summ_nr)
  {
    this.pay_summ_nr = StringUtil.appCurrencyString2double(pay_summ_nr);
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

  public boolean isFormReadOnly()
  {
    return formReadOnly;
  }

  public void setFormReadOnly(boolean formReadOnly)
  {
    this.formReadOnly = formReadOnly;
  }

  public boolean isReadOnlyForManager()
  {
    return readOnlyForManager;
  }

  public void setReadOnlyForManager(boolean readOnlyForManager)
  {
    this.readOnlyForManager = readOnlyForManager;
  }

  public boolean isReadOnlySave()
  {
    return formReadOnly & readOnlyForManager;
  }

  public HolderImplUsingList getGrid()
  {
    return grid;
  }

  public void setGrid(HolderImplUsingList grid)
  {
    this.grid = grid;
  }
}
