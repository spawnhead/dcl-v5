package net.sam.dcl.action;

import net.sam.dcl.controller.actions.DBTransactionAction;
import net.sam.dcl.controller.IDispatchable;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.StrutsUtil;
import net.sam.dcl.form.CurrencyRateForm;
import org.apache.struts.action.ActionForward;
import by.nbrb.www.CurrenciesRates;

public class CurrencyRateAction extends DBTransactionAction implements IDispatchable
{
  public ActionForward input(IActionContext context) throws Exception
  {
    return context.getMapping().getInputForward();
  }

  public ActionForward create(IActionContext context) throws Exception
  {
    return input(context);
  }

  public ActionForward edit(IActionContext context) throws Exception
  {
    DAOUtils.load(context, "currency_rate-load", null);
    return input(context);
  }

  public ActionForward process(IActionContext context) throws Exception
  {
    CurrencyRateForm form = (CurrencyRateForm) context.getForm();

    if (StringUtil.isEmpty(form.getCrt_id()))
    {
      CurrencyRateForm.CurrencyRate currencyRate = new CurrencyRateForm.CurrencyRate();
      currencyRate.setCur_id(form.getCur_id());
      currencyRate.setCrt_date(form.getCrt_date());
      DAOUtils.load(context, "currency_rate-check", currencyRate, null);
      if ( !StringUtil.isEmpty(currencyRate.getCrt_id()) )
      {
        StrutsUtil.addError(context, "errors.currency_rate.double_index", null);
        return input(context);
      }

      DAOUtils.update(context, "currency_rate-insert", null);
    }
    else
    {
      DAOUtils.update(context, "currency_rate-update", null);
    }
    return context.getMapping().findForward("back");
  }

  public ActionForward receiveFromBank(IActionContext context) throws Exception
  {
    CurrencyRateForm form = (CurrencyRateForm) context.getForm();

    form.setCrt_rate(CurrenciesRates.getRateForCurrency(form.getCrt_date(), form.getCur_name()));

    return input(context);
  }

}