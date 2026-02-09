package net.sam.dcl.action;

import net.sam.dcl.beans.Constants;
import net.sam.dcl.beans.Contractor;
import net.sam.dcl.beans.Currency;
import net.sam.dcl.config.Config;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.controller.IDispatchable;
import net.sam.dcl.controller.IFormAutoSave;
import net.sam.dcl.controller.actions.DBAction;
import net.sam.dcl.form.PaymentsForm;
import net.sam.dcl.taglib.table.IShowChecker;
import net.sam.dcl.taglib.table.ShowCheckerContext;
import net.sam.dcl.taglib.table.impl.AlwaysReadonlyChecker;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.util.StringUtil;
import org.apache.struts.action.ActionForward;

import java.util.Calendar;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class PaymentsAction extends DBAction implements IDispatchable, IFormAutoSave
{

  public ActionForward filter(IActionContext context) throws Exception
  {
    final PaymentsForm form = (PaymentsForm) context.getForm();

    if ( "1".equals(form.getClosed_open()) )
    {
      form.setClosed_closed("");
      form.setClosed_all("");
    }

    DAOUtils.fillGrid(context, form.getGrid(), "select-payments", PaymentsForm.Payment.class, null, null);

    context.getRequest().setAttribute("alwaysReadonly",  new AlwaysReadonlyChecker());

    context.getRequest().setAttribute("showCommentChecker", new IShowChecker()
    {
      public boolean check(ShowCheckerContext context)
      {
        PaymentsForm.Payment payment = (PaymentsForm.Payment) form.getGrid().getDataList().get(context.getTable().getRecordCounter() - 1);
        return payment.isHasComment();
      }
    }
    );


    return context.getMapping().getInputForward();
  }

  public ActionForward input(IActionContext context) throws Exception
  {
    PaymentsForm form = (PaymentsForm) context.getForm();
    form.setContractor(new Contractor());
    form.setCurrency(new Currency());
    form.setSum_max_formatted("");
    form.setSum_min_formatted("");
    form.setBlock("");
    form.setClosed_open("1");
    form.setPay_account("");
    if ( Config.haveNumber(Constants.dayCountDeductPayments) )
    {
      int dayCount = Config.getNumber(Constants.dayCountDeductPayments, 10);
      if (dayCount > 0)
      {
        Calendar calendarBegin = Calendar.getInstance();
        calendarBegin.add(Calendar.DATE, -dayCount);
        form.setDate_begin(StringUtil.date2appDateString(calendarBegin.getTime()));
        Calendar calendarEnd = Calendar.getInstance();
        calendarEnd.add(Calendar.DATE, 1);
        form.setDate_end(StringUtil.date2appDateString(calendarEnd.getTime()));
      }
      else
      {
        form.setDate_begin("");
        form.setDate_end("");
      }
    }
    else
    {
      form.setDate_begin("");
      form.setDate_end("");
    }

    return filter(context);
  }

  public ActionForward restore(IActionContext context) throws Exception
  {
    restoreForm(context);
    return filter(context);
  }

}
