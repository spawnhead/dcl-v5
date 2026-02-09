package net.sam.dcl.action;

import net.sam.dcl.controller.actions.DBAction;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.form.CurrencyRatesForm;
import org.apache.struts.action.ActionForward;

public class CurrencyRatesAction extends DBAction
{
  public ActionForward execute(IActionContext context) throws Exception
  {
    CurrencyRatesForm form = (CurrencyRatesForm) context.getForm();
    DAOUtils.fillGrid(context, form.getRatesGrid(), "select-currency_rates", CurrencyRatesForm.CurrencyRate.class, null, null);

    return context.getMapping().getInputForward();
  }

}