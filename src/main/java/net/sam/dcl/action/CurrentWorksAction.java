package net.sam.dcl.action;

import net.sam.dcl.beans.Contractor;
import net.sam.dcl.beans.ContractorRequestType;
import net.sam.dcl.beans.User;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.controller.IDispatchable;
import net.sam.dcl.controller.IFormAutoSave;
import net.sam.dcl.controller.IProcessBefore;
import net.sam.dcl.controller.actions.DBAction;
import net.sam.dcl.controller.processor.ActionHandler;
import net.sam.dcl.form.CurrentWorksForm;
import net.sam.dcl.taglib.table.IShowChecker;
import net.sam.dcl.taglib.table.ShowCheckerContext;
import net.sam.dcl.taglib.table.model.PageableDataHolder;
import net.sam.dcl.util.DAOUtils;
import org.apache.struts.action.ActionForward;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class CurrentWorksAction extends DBAction implements IDispatchable, IFormAutoSave, IProcessBefore
{
  public ActionForward processBefore(IActionContext context) throws Exception
  {
    context.getActionProcessor().addActionHandler("grid", PageableDataHolder.NEXT_PAGE, new ActionHandler()
    {
      public ActionForward process(IActionContext context) throws Exception
      {
        CurrentWorksForm form = (CurrentWorksForm) context.getForm();
        form.getGrid().incPage();
        return internalFilter(context);
      }
    });
    context.getActionProcessor().addActionHandler("grid", PageableDataHolder.PREV_PAGE, new ActionHandler()
    {
      public ActionForward process(IActionContext context) throws Exception
      {
        CurrentWorksForm form = (CurrentWorksForm) context.getForm();
        form.getGrid().decPage();
        return internalFilter(context);
      }
    });
    return null;
  }

  public ActionForward filter(IActionContext context) throws Exception
  {
    CurrentWorksForm form = (CurrentWorksForm) context.getForm();
    form.getGrid().setPage(1);
    return internalFilter(context);
  }

  public ActionForward internalFilter(IActionContext context) throws Exception
  {
    final CurrentWorksForm form = (CurrentWorksForm) context.getForm();
    DAOUtils.fillGrid(context, form.getGrid(), "select-current_works", CurrentWorksForm.ContractorRequest.class, null, null);

    context.getRequest().setAttribute("showCommentChecker", new IShowChecker()
    {
      public boolean check(ShowCheckerContext context)
      {
        CurrentWorksForm.ContractorRequest contractorRequest = (CurrentWorksForm.ContractorRequest) form.getGrid().getDataList().get(context.getTable().getRecordCounter() - 1);
        return contractorRequest.isHasComment();
      }
    }
    );

    return context.getMapping().getInputForward();
  }

  public ActionForward input(IActionContext context) throws Exception
  {
    CurrentWorksForm form = (CurrentWorksForm) context.getForm();
    form.setNumber("");
    form.setContractor(new Contractor());
    form.setRequestType(new ContractorRequestType());
    form.setCrq_equipment("");
    form.setUser(new User());

    return internalFilter(context);
  }

  public ActionForward restore(IActionContext context) throws Exception
  {
    restoreForm(context);
    return internalFilter(context);
  }

}