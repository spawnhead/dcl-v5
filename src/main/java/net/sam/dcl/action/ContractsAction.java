package net.sam.dcl.action;

import net.sam.dcl.controller.actions.DBAction;
import net.sam.dcl.controller.*;
import net.sam.dcl.controller.processor.ActionHandler;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.util.UserUtil;
import net.sam.dcl.beans.*;
import net.sam.dcl.taglib.table.model.PageableDataHolder;
import net.sam.dcl.taglib.table.impl.AlwaysReadonlyChecker;
import net.sam.dcl.taglib.table.*;
import net.sam.dcl.form.ContractsForm;
import org.apache.struts.action.ActionForward;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class ContractsAction extends DBAction implements IDispatchable, IFormAutoSave, IProcessBefore
{
  public ActionForward processBefore(IActionContext context) throws Exception
  {
    context.getActionProcessor().addActionHandler("grid", PageableDataHolder.NEXT_PAGE, new ActionHandler()
    {
      public ActionForward process(IActionContext context) throws Exception
      {
        ContractsForm form = (ContractsForm) context.getForm();
        form.getGrid().incPage();
        return internalFilter(context);
      }
    });
    context.getActionProcessor().addActionHandler("grid", PageableDataHolder.PREV_PAGE, new ActionHandler()
    {
      public ActionForward process(IActionContext context) throws Exception
      {
        ContractsForm form = (ContractsForm) context.getForm();
        form.getGrid().decPage();
        return internalFilter(context);
      }
    });
    return null;
  }

  public ActionForward filter(IActionContext context) throws Exception
  {
    ContractsForm form = (ContractsForm) context.getForm();
    form.getGrid().setPage(1);
    return internalFilter(context);
  }

  public ActionForward internalFilter(IActionContext context) throws Exception
  {
    final ContractsForm form = (ContractsForm) context.getForm();
    DAOUtils.fillGrid(context, form.getGrid(), "select-contracts", ContractsForm.Contract.class, null, null);

    context.getRequest().setAttribute("alwaysReadonly", new AlwaysReadonlyChecker());

    context.getRequest().setAttribute("style-checker", new IStyleClassChecker()
    {
      public String check(StyleClassCheckerContext context)
      {
        ContractsForm.Contract contract = (ContractsForm.Contract) form.getGrid().getDataList().get(context.getTable().getRecordCounter() - 1);
        if ("1".equals(contract.getCon_annul()))
        {
          return "crossed-cell";
        }
        return "";
      }
    });

    context.getRequest().setAttribute("show-delete-checker", new IShowChecker()
    {
      public boolean check(ShowCheckerContext context)
      {
        ContractsForm.Contract contract = (ContractsForm.Contract) form.getGrid().getDataList().get(context.getTable().getRecordCounter() - 1);
        return !contract.haveSpec();
      }
    }
    );

    final User currentUser = UserUtil.getCurrentUser(context.getRequest());
    // если указан в одной из спек договора в поле "Чья специф-я"
    // менеджер - свои и свой отдел
    context.getRequest().setAttribute("editChecker", new IReadOnlyChecker()
    {
      public boolean check(ReadOnlyCheckerContext ctx) throws Exception
      {
        ContractsForm.Contract record = (ContractsForm.Contract) ctx.getBean();
        /* сохранил здесь бывший вариант работы с правами на всякий случай, если что - быстрее востановлю
        if (
             currentUser.isOnlyManager() &&
             !currentUser.isChiefDepartment() &&
             record.getUsr_id_list().indexOf(currentUser.getUsr_id() + ReportDelimiterConsts.html_separator) == -1
           )
        {
          return true;
        }
        */
        if (
             currentUser.isOnlyManager() &&
             //currentUser.isChiefDepartment() &&
             //record.getUsr_id_list().indexOf(currentUser.getUsr_id() + ReportDelimiterConsts.html_separator) == -1 &&
             record.getDep_id_list().indexOf(currentUser.getDepartment().getId() + ReportDelimiterConsts.html_separator) == -1
           )
        {
          return true;
        }
        return false;
      }
    });

    return context.getMapping().getInputForward();
  }

  public ActionForward input(IActionContext context) throws Exception
  {
    ContractsForm form = (ContractsForm) context.getForm();
    form.setContractor(new Contractor());
    form.setDate_begin("");
    form.setDate_end("");
    form.setNumber("");
    form.setSum_max_formatted("");
    form.setSum_min_formatted("");
    form.setUser(new User());
    form.setCon_executed(null);
    form.setExecuted("");
    form.setNot_executed("");
    form.setOridinal_absent("");
    form.setSeller(new Seller());

    return internalFilter(context);
  }

  public ActionForward restore(IActionContext context) throws Exception
  {
    restoreForm(context);
    return internalFilter(context);
  }

  public ActionForward selectCP(IActionContext context) throws Exception
  {
    return context.getMapping().findForward("selectCP");
  }
}
