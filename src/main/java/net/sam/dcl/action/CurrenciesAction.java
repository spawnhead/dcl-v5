package net.sam.dcl.action;

import net.sam.dcl.controller.actions.DBAction;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.controller.IDispatchable;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.StrutsUtil;
import net.sam.dcl.form.CurrenciesForm;
import net.sam.dcl.taglib.table.IReadOnlyChecker;
import net.sam.dcl.taglib.table.ReadOnlyCheckerContext;
import org.apache.struts.action.ActionForward;

import java.util.List;

import by.nbrb.www.CurrenciesRates;

public class CurrenciesAction extends DBAction implements IDispatchable
{
  public ActionForward execute(IActionContext context) throws Exception
  {
    CurrenciesForm form = (CurrenciesForm) context.getForm();
    form.setShowConfirmMsg(false);
    form.setShowOkMsg(false);
    DAOUtils.fillGrid(context, form.getCurrenciesGrid(), "select-currencies", CurrenciesForm.Currency.class, null, null);

    return show(context);
  }

  public ActionForward receiveFromBankCourses(IActionContext context) throws Exception
  {
    CurrenciesForm form = (CurrenciesForm) context.getForm();
    //очищаем старые, если были
    form.getCurrenciesRatesForMod().getDataList().clear();
    form.setShowConfirmMsg(false);
    form.setShowOkMsg(false);

    List<CurrenciesForm.CurrencyRate> listCurrenciesRatesDB = DAOUtils.fillList(context, "load-currencies_rates_for_date", form, CurrenciesForm.CurrencyRate.class, null, null);

    //получаем список курсов из банка
    CurrenciesRates.getRatesOnDate(form.getCourseDateTs());

    String message = "";
    for ( CurrenciesForm.CurrencyRate currencyRate : listCurrenciesRatesDB )
    {
      currencyRate.setCrt_date(form.getCourseDateTs());
      double course = CurrenciesRates.getRateForCurrency(currencyRate.getCur_name());
      if ( StringUtil.isEmpty(currencyRate.getCrt_id()) ) //курса на дату нет - пишем в базу
      {
        currencyRate.setCrt_rate(course);
        currencyRate.setNeedInsert(true);
      }
      else
      {
        //есть в базе, но такой же как и полученный - пропускаем
        if ( currencyRate.getCrt_rate() != course ) //отличается от того, что в базе - вывести сообщение с подтвержением
        {
          form.setShowConfirmMsg(true);
          message += StrutsUtil.getMessage(context, "msg.currencies.changed_rate", currencyRate.getCur_name(), Double.toString(currencyRate.getCrt_rate()), Double.toString(course));
          currencyRate.setCrt_rate(course);
          form.getCurrenciesRatesForMod().getDataList().add(currencyRate);
        }
      }
    }

    if ( form.isShowConfirmMsg() )
    {
      form.setMessage(StrutsUtil.getMessage(context, "msg.currencies.changed_rate_common", message));
    }
    else
    {
      form.setShowOkMsg(true);
    }

    context.getConnection().beginTransaction();
    for ( CurrenciesForm.CurrencyRate currencyRate : listCurrenciesRatesDB )
    {
      if ( currencyRate.isNeedInsert() )
      {
        DAOUtils.update(context.getConnection(), context.getSqlResource().get("currency_rate-insert"), currencyRate, null);
      }
    }
    //комитим вручную, потому что акшн не транзакционный
    context.getConnection().commit();
    
    return show(context);
  }

  //обработка подтвержденных изменений курса
  public ActionForward postReceiveFromBankCourses(IActionContext context) throws Exception
  {
    CurrenciesForm form = (CurrenciesForm) context.getForm();
    form.setShowConfirmMsg(false);
    form.setShowOkMsg(true);
    context.getConnection().beginTransaction();
    for ( int i = 0; i < form.getCurrenciesRatesForMod().getDataList().size(); i++ )
    {
      CurrenciesForm.CurrencyRate currencyRate = (CurrenciesForm.CurrencyRate)form.getCurrenciesRatesForMod().getDataList().get(i);
      DAOUtils.update(context.getConnection(), context.getSqlResource().get("currency_rate-update"), currencyRate, null);
    }
    //комитим вручную, потому что акшн не транзакционный
    context.getConnection().commit();

    return show(context);
  }

  public ActionForward show(IActionContext context) throws Exception
  {
    context.getRequest().setAttribute("alwaysReadonly", new IReadOnlyChecker()
    {
      public boolean check(ReadOnlyCheckerContext ctx) throws Exception
      {
        return true;
      }
    });

    return context.getMapping().getInputForward();
  }
}
