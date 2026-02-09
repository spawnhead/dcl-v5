package net.sam.dcl.action;

import by.nbrb.www.CurrenciesRates;
import by.nbrb.www.RatesException;
import net.sam.dcl.beans.*;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.controller.IDispatchable;
import net.sam.dcl.controller.IWriteMessage;
import net.sam.dcl.controller.actions.DBTransactionAction;
import net.sam.dcl.dao.*;
import net.sam.dcl.form.CurrenciesForm;
import net.sam.dcl.form.PaymentForm;
import net.sam.dcl.taglib.table.IReadOnlyChecker;
import net.sam.dcl.taglib.table.ReadOnlyCheckerContext;
import net.sam.dcl.util.*;
import net.sam.dcl.db.VDbConnection;
import net.sam.dcl.db.VDbConnectionManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForward;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class PaymentAction extends DBTransactionAction implements IDispatchable, IWriteMessage
{
  protected static Log log = LogFactory.getLog(PaymentAction.class);

  private void saveCurrentFormToBean(IActionContext context)
  {
    PaymentForm form = (PaymentForm) context.getForm();
    Payment payment = (Payment) StoreUtil.getSession(context.getRequest(), Payment.class);

    payment.setIs_new_doc(form.getIs_new_doc());

    payment.setPay_id(form.getPay_id());
    try
    {
      if (!StringUtil.isEmpty(form.getCreateUser().getUsr_id()))
      {
        payment.setCreateUser(UserDAO.load(context, form.getCreateUser().getUsr_id()));
      }
      if (!StringUtil.isEmpty(form.getEditUser().getUsr_id()))
      {
        payment.setEditUser(UserDAO.load(context, form.getEditUser().getUsr_id()));
      }
    }
    catch (Exception e)
    {
      log.error(e.getMessage(), e);
    }
    payment.setUsr_date_create(form.getUsr_date_create());
    payment.setUsr_date_edit(form.getUsr_date_edit());
    payment.setPay_date(form.getPay_date());
    payment.setPay_account(form.getPay_account());
    payment.setPay_summ(form.getPay_summ());
    payment.setPay_course(form.getPay_course());
    payment.setPay_course_nbrb(form.getPay_course_nbrb());
    payment.setPay_course_nbrb_date(form.getPay_course_nbrb_date());
    payment.setPay_summ_eur(form.getPay_summ_eur());
    payment.setPay_summ_eur_nbrb(form.getPay_summ_eur_nbrb());
    payment.setPay_summ_nr(form.getPay_summ_nr());
    payment.setPay_block(form.getPay_block());
    payment.setPay_comment(form.getPay_comment());
    payment.setPay_closed(form.getPay_closed());

    try
    {
      if (!StringUtil.isEmpty(form.getContractor().getId()))
      {
        payment.setContractor(ContractorDAO.load(context, form.getContractor().getId()));
        //при изменении контрагента из списка - счет не меняется
      }
    }
    catch (Exception e)
    {
      log.error(e.getMessage(), e);
    }

    try
    {
      if (!StringUtil.isEmpty(form.getCurrency().getId()))
      {
        payment.setCurrency(CurrencyDAO.load(context, form.getCurrency().getId()));
      }
    }
    catch (Exception e)
    {
      StrutsUtil.addError(context, e);
    }

    StoreUtil.putSession(context.getRequest(), payment);
  }

  private void getCurrentFormFromBean(IActionContext context, Payment paymentIn)
  {
    PaymentForm form = (PaymentForm) context.getForm();
    Payment payment;
    if (null != paymentIn)
    {
      payment = paymentIn;
    }
    else
    {
      payment = (Payment) StoreUtil.getSession(context.getRequest(), Payment.class);
    }

    if (null != payment)
    {
      form.setIs_new_doc(payment.getIs_new_doc());

      form.setPay_id(payment.getPay_id());
      form.setCreateUser(payment.getCreateUser());
      form.setEditUser(payment.getEditUser());
      form.setUsr_date_create(payment.getUsr_date_create());
      form.setUsr_date_edit(payment.getUsr_date_edit());
      form.setPay_date(payment.getPay_date());
      form.setContractor(payment.getContractor());
      form.setPay_account(payment.getPay_account());
      form.setCurrency(payment.getCurrency());
      form.setPay_summ(payment.getPay_summ());
      form.setPay_course(payment.getPay_course());
      form.setPay_course_nbrb(payment.getPay_course_nbrb());
      form.setPayCourseNbrbDate(payment.getPay_course_nbrb_date());
      form.setPay_summ_eur(payment.getPay_summ_eur());
      form.setPay_summ_eur_nbrb(payment.getPay_summ_eur_nbrb());
      form.setPay_summ_nr(payment.getPay_summ_nr());
      form.setPay_block(payment.getPay_block());
      form.setPay_comment(payment.getPay_comment());
      form.setPay_closed(payment.getPay_closed());

      form.getGrid().setDataList(payment.getPaySums());
    }
  }

  public ActionForward newContractor(IActionContext context) throws Exception
  {
    saveCurrentFormToBean(context);

    PaymentForm form = (PaymentForm) context.getForm();
    context.getRequest().getSession().setAttribute(Contractor.currentContractorId, form.getContractor().getId());

    return context.getMapping().findForward("newContractor");
  }

  public ActionForward newPaySum(IActionContext context) throws Exception
  {
    saveCurrentFormToBean(context);

    return context.getMapping().findForward("newPaySum");
  }

  public ActionForward editPaySum(IActionContext context) throws Exception
  {
    saveCurrentFormToBean(context);

    return context.getMapping().findForward("editPaySum");
  }

  public ActionForward deletePaySum(IActionContext context) throws Exception
  {
    saveCurrentFormToBean(context);

    PaymentForm form = (PaymentForm) context.getForm();
    Payment payment = (Payment) StoreUtil.getSession(context.getRequest(), Payment.class);
    payment.deletePaySum(form.getNumber());

    return retFromPaySumOperation(context);
  }

  public ActionForward retFromPaySumOperation(IActionContext context) throws Exception
  {
    return show(context);
  }

  public ActionForward retFromContractor(IActionContext context) throws Exception
  {
    PaymentForm form = (PaymentForm) context.getForm();

    String contractorId = (String) context.getRequest().getSession().getAttribute(Contractor.currentContractorId);
    if (!StringUtil.isEmpty(contractorId))
      form.setContractor(ContractorDAO.load(context, contractorId));
    context.getRequest().getSession().setAttribute(Contractor.currentContractorId, null);

    return show(context);
  }

  private boolean saveCommon(IActionContext context) throws Exception
  {
    PaymentForm form = (PaymentForm) context.getForm();
    String errMsg = "";

    User currentUser = UserUtil.getCurrentUser(context.getRequest());
    if ((!currentUser.isEconomist() && !currentUser.isAdmin()) && StringUtil.isEmpty(form.getContractor().getId()))
    {
      errMsg = StrutsUtil.addDelimiter(errMsg);
      errMsg += StrutsUtil.getMessage(context, "error.payment.contractor");
    }

    saveCurrentFormToBean(context);

    Payment payment = (Payment) StoreUtil.getSession(context.getRequest(), Payment.class);
    payment.calculate();

    if (payment.isIncorrectCurrency(payment.getCurrency().getId()))
    {
      errMsg = StrutsUtil.addDelimiter(errMsg);
      errMsg += StrutsUtil.getMessage(context, "error.payment.currency");
    }

    if (!StringUtil.isEmpty(errMsg))
    {
      StrutsUtil.addError(context, "errors.msg", errMsg, null);
      return false;
    }

    double paySum = StringUtil.roundN(payment.getPay_summ(), 2);
    double paySumNR = StringUtil.roundN(payment.getPay_summ_nr(), 2);
    if (paySumNR == 0 && paySum != 0)
    {
      payment.setPay_block("1");
    }
    else
    {
      payment.setPay_block("");
    }

    if (StringUtil.isEmpty(form.getPay_id()))
    {
      PaymentDAO.insert(context, payment);
    }
    else
    {
      PaymentDAO.save(context, payment);
    }

    writeMessage(context, payment);

    return true;
  }

  public ActionForward ajaxChangeAccount(IActionContext context) throws Exception
  {
    PaymentForm form = (PaymentForm) context.getForm();
    Payment payment = (Payment) StoreUtil.getSession(context.getRequest(), Payment.class);
    String account = context.getRequest().getParameter("account");
    try
    {
      payment.setContractor(ContractorDAO.loadByAccount(context, account));
      if (payment.getContractor() == null)
      {
        payment.setContractor(new Contractor());
      }
      form.setContractor(payment.getContractor());
      String resultMsg = payment.getContractor().getId() + "|" +
              payment.getContractor().getName() + "|" +
              payment.getContractor().getCtrNamesFormatted();

      StrutsUtil.setAjaxResponse(context, resultMsg, false);
    }
    catch (Exception e)
    {
      log.error(e.getMessage(), e);
    }

    return context.getMapping().findForward("ajax");
  }

  public ActionForward ajaxChangeSum(IActionContext context) throws Exception
  {
    PaymentForm form = (PaymentForm) context.getForm();
    Payment payment = (Payment) StoreUtil.getSession(context.getRequest(), Payment.class);
    String paySum = context.getRequest().getParameter("paySum");
    if (!StringUtil.isEmpty(paySum))
    {
      payment.setPay_summ(StringUtil.appCurrencyString2double(paySum));
      payment.calculate();
      getCurrentFormFromBean(context, payment);

      String resultMsg = form.getPay_summ_eur_formatted() + "|" +
              form.getPay_summ_eur_nbrb_formatted() + "|" +
              form.getPay_summ_nr_formatted();

      StrutsUtil.setAjaxResponse(context, resultMsg, false);
    }

    return context.getMapping().findForward("ajax");
  }

  public ActionForward ajaxChangeCourse(IActionContext context) throws Exception
  {
    PaymentForm form = (PaymentForm) context.getForm();
    Payment payment = (Payment) StoreUtil.getSession(context.getRequest(), Payment.class);
    String payCourse = context.getRequest().getParameter("payCourse");
    String currencyId = context.getRequest().getParameter("currencyId");
    String payDate = context.getRequest().getParameter("payDate");
    if (!StringUtil.isEmpty(payCourse))
    {
      if (!StringUtil.isEmpty(currencyId))
      {
        Currency currency = CurrencyDAO.load(context, currencyId);
        payment.setCurrency(currency);
        form.setCurrency(currency);
      }

      payment.setPay_course(StringUtil.appCurrencyString2double(payCourse));
      payment.setPay_date(payDate);
      payment.courseChanged();
      CurrencyRate currencyRate = CurrencyRateDAO.loadRateForDate(context, CurrencyDAO.loadByName(context, "EUR").getId(), StringUtil.appDateString2dbDateString(payDate));
      payment.setPay_course_nbrb_date(StringUtil.dbDateString2appDateString(currencyRate.getCrt_date()));
      if ("BYN".equals(form.getCurrency().getName()) && currencyRate.getCrt_rate() != payment.getPay_course())
      {
        payment.setPay_course_nbrb(currencyRate.getCrt_rate());
      }
      else
      {
        payment.setPay_course_nbrb(0);
        payment.setPay_course_nbrb_date("");
      }
      payment.courseNBRBChanged();
      getCurrentFormFromBean(context, payment);

      String resultMsg = form.getPay_summ_eur_formatted() + "|" +
              form.getPayCourseNbrbFormatted() + "|" +
              form.getPay_course_nbrb_date() + "|" +
              form.getPay_summ_eur_nbrb_formatted();

      StrutsUtil.setAjaxResponse(context, resultMsg, false);
    }

    return context.getMapping().findForward("ajax");
  }

  public ActionForward ajaxChangeCourseNBRB(IActionContext context) throws Exception
  {
    PaymentForm form = (PaymentForm) context.getForm();
    Payment payment = (Payment) StoreUtil.getSession(context.getRequest(), Payment.class);
    String payCourse = context.getRequest().getParameter("payCourse");
    if (!StringUtil.isEmpty(payCourse))
    {
      payment.setPay_course_nbrb(StringUtil.appCurrencyString2double(payCourse));
      payment.courseNBRBChanged();
      getCurrentFormFromBean(context, payment);

      String resultMsg = form.getPay_summ_eur_nbrb_formatted();

      StrutsUtil.setAjaxResponse(context, resultMsg, false);
    }

    return context.getMapping().findForward("ajax");
  }

  public ActionForward ajaxUpdateCourses(IActionContext context) throws Exception
  {
    Calendar calendarEndDate = Calendar.getInstance();
    calendarEndDate.setTime(StringUtil.getCurrentDateTime());
    calendarEndDate.add(Calendar.DATE, 1);
    Date minDate = CurrencyRateDAO.loadRateMinDate(context);
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(minDate);
    while (calendar.before(calendarEndDate) || calendar.equals(calendarEndDate))
    {
      CurrenciesForm form = new CurrenciesForm();
      form.setCourseDate(StringUtil.date2appDateString(calendar.getTime()));
      List<CurrenciesForm.CurrencyRate> listCurrenciesRatesDB = DAOUtils.fillList(context, "load-currencies_rates_for_date", form, CurrenciesForm.CurrencyRate.class, null, null);

      //получаем список курсов из банка
      try
      {
        CurrenciesRates.getRatesOnDate(form.getCourseDateTs());
      }
      catch (RatesException e)
      {
        calendar.add(Calendar.DATE, 1);
        continue;
      }

      for (CurrenciesForm.CurrencyRate currencyRate : listCurrenciesRatesDB)
      {
        currencyRate.setCrt_date(form.getCourseDateTs());
        double course = CurrenciesRates.getRateForCurrency(currencyRate.getCur_name());
        if (StringUtil.isEmpty(currencyRate.getCrt_id())) //курса на дату нет - пишем в базу
        {
          currencyRate.setCrt_rate(course);
          currencyRate.setNeedInsert(true);
        }
        else
        {
          //есть в базе, но такой же как и полученный - пропускаем
          if (currencyRate.getCrt_rate() != course) //отличается от того, что в базе - вывести сообщение с подтвержением
          {
            currencyRate.setCrt_rate(course);
            form.getCurrenciesRatesForMod().getDataList().add(currencyRate);
          }
        }
      }

      VDbConnection conn = null;
      try
      {
        conn = VDbConnectionManager.getVDbConnection();
        conn.beginTransaction();
        for (int i = 0; i < form.getCurrenciesRatesForMod().getDataList().size(); i++)
        {
          CurrenciesForm.CurrencyRate currencyRate = (CurrenciesForm.CurrencyRate) form.getCurrenciesRatesForMod().getDataList().get(i);
          DAOUtils.update(context.getConnection(), context.getSqlResource().get("currency_rate-update"), currencyRate, null);
        }
        for (CurrenciesForm.CurrencyRate currencyRate : listCurrenciesRatesDB)
        {
          if (currencyRate.isNeedInsert())
          {
            DAOUtils.update(context.getConnection(), context.getSqlResource().get("currency_rate-insert"), currencyRate, null);
          }
        }
        conn.commit();
      }
      catch (Exception e)
      {
        throw new RuntimeException(e);
      }
      finally
      {
        if (conn != null) conn.close();
      }

      calendar.add(Calendar.DATE, 1);
    }

    return context.getMapping().findForward("ajax");
  }

  public ActionForward ajaxPaymentSumsGrid(IActionContext context) throws Exception
  {
    setVisuallyAttributes(context);

    return context.getMapping().findForward("ajaxPaymentSumsGrid");
  }

  public ActionForward ajaxChangeContractor(IActionContext context) throws Exception
  {
    PaymentForm form = (PaymentForm) context.getForm();
    Payment payment = (Payment) StoreUtil.getSession(context.getRequest(), Payment.class);
    payment.setPaySums(new ArrayList<PaySum>());
    form.getGrid().setDataList(payment.getPaySums());

    saveCurrentFormToBean(context);

    return ajaxPaymentSumsGrid(context);
  }

  private void setVisuallyAttributes(IActionContext context)
  {
    final PaymentForm form = (PaymentForm) context.getForm();

    context.getRequest().setAttribute("deleteReadonly", new IReadOnlyChecker()
    {
      public boolean check(ReadOnlyCheckerContext context) throws Exception
      {
        PaySum paySum = (PaySum) form.getGrid().getDataList().get(context.getTable().getRecordCounter() - 1);
        return !(StringUtil.isEmpty(paySum.getLps_occupied()) && !form.isFormReadOnly());
      }
    });
  }

  public ActionForward show(IActionContext context) throws Exception
  {
    final PaymentForm form = (PaymentForm) context.getForm();

    Payment payment = (Payment) StoreUtil.getSession(context.getRequest(), Payment.class);
    payment.calculate();
    StoreUtil.putSession(context.getRequest(), payment);
    getCurrentFormFromBean(context, payment);

    User user = UserUtil.getCurrentUser(context.getRequest());
    if (StringUtil.isEmpty(form.getCreateUser().getUsr_id()) || //новый документ
            !"1".equals(form.getPay_block()) ||
            ("1".equals(form.getPay_block()) && (user.isAdmin() || user.isEconomist()))
            )
    {
      form.setFormReadOnly(false);
    }
    else
    {
      form.setFormReadOnly(true);
    }

    //если документ закрыт (участвует в Закрытии договоров) то его менять никто! не может
    if (!StringUtil.isEmpty(form.getPay_closed()) || user.isOnlyLawyer())
    {
      form.setFormReadOnly(true);
    }

    if (form.isFormReadOnly() || user.isOnlyManager())
    {
      form.setReadOnlyForManager(true);
    }
    else
    {
      form.setReadOnlyForManager(false);
    }

    setVisuallyAttributes(context);

    return context.getMapping().findForward("form");
  }

  public ActionForward input(IActionContext context) throws Exception
  {
    Payment payment = new Payment();
    payment.setPay_date(StringUtil.date2appDateString(StringUtil.getCurrentDateTime()));
    payment.setIs_new_doc("true");
    payment.setPay_block("0");
    payment.setCurrency(CurrencyDAO.loadByName(context, StrutsUtil.getMessage(context, "Payment.default_currency")));

    StoreUtil.putSession(context.getRequest(), payment);

    return show(context);
  }

  public ActionForward clone(IActionContext context) throws Exception
  {
    PaymentForm form = (PaymentForm) context.getForm();

    Payment payment = PaymentDAO.loadClone(context, form.getPay_id());
    payment.setUsr_date_create(null);
    payment.setUsr_date_edit(null);
    User user = UserUtil.getCurrentUser(context.getRequest());
    payment.setCreateUser(user);
    payment.setEditUser(user);
    payment.setPay_id(null);
    payment.setIs_new_doc("true");
    payment.setPay_date(StringUtil.date2appDateString(StringUtil.getCurrentDateTime()));
    payment.setPay_block(null);
    payment.setPay_closed(null);
    payment.setPay_comment("");

    StoreUtil.putSession(context.getRequest(), payment);

    return show(context);
  }

  public ActionForward edit(IActionContext context) throws Exception
  {
    PaymentForm form = (PaymentForm) context.getForm();

    Payment payment = PaymentDAO.load(context, form.getPay_id());
    List<PaySum> paySums = new ArrayList<PaySum>();
    for (PaySum paySum : payment.getPaySums())
    {
      paySums.add(new PaySum(paySum));
    }
    payment.setPaySumsOld(paySums);
    StoreUtil.putSession(context.getRequest(), payment);


    return show(context);
  }

  public ActionForward back(IActionContext context) throws Exception
  {
    return context.getMapping().findForward("back");
  }

  public ActionForward process(IActionContext context) throws Exception
  {
    boolean retFromSave = saveCommon(context);

    // this case for right forward in case, when we go to payments from calculation report
    boolean isPaymentFromCalculationReport = Boolean.parseBoolean(String.valueOf(StoreUtil.getSession(context.getRequest(), "paymentFromCalculationReport")));
    if (isPaymentFromCalculationReport) {
      if (context.getMapping().findForward("backToReport") == null) return context.getMapping().findForward("back");

      return context.getMapping().findForward("backToReport");
    }

    if (retFromSave)
    {
      return context.getMapping().findForward("back");
    }
    else
    {
      return show(context);
    }
  }

  public void writeMessage(IActionContext context, Object bean) throws Exception
  {
    Payment payment = (Payment) bean;
    MessageDAO.savePaymentMessages(context, payment);
  }
}
