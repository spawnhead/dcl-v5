package net.sam.dcl.action;

import net.sam.dcl.beans.Seller;
import net.sam.dcl.controller.actions.DBAction;
import net.sam.dcl.controller.*;
import net.sam.dcl.controller.processor.ActionHandler;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.beans.Contractor;
import net.sam.dcl.beans.User;
import net.sam.dcl.taglib.table.model.PageableDataHolder;
import net.sam.dcl.form.OutgoingLettersForm;
import org.apache.struts.action.ActionForward;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class OutgoingLettersAction extends DBAction implements IDispatchable, IFormAutoSave, IProcessBefore
{
  public ActionForward processBefore(IActionContext context) throws Exception
  {
    context.getActionProcessor().addActionHandler("gridOutgoingLetters", PageableDataHolder.NEXT_PAGE, new ActionHandler()
    {
      public ActionForward process(IActionContext context) throws Exception
      {
        OutgoingLettersForm form = (OutgoingLettersForm) context.getForm();
        form.getGridOutgoingLetters().incPage();
        return internalFilter(context);
      }
    });
    context.getActionProcessor().addActionHandler("gridOutgoingLetters", PageableDataHolder.PREV_PAGE, new ActionHandler()
    {
      public ActionForward process(IActionContext context) throws Exception
      {
        OutgoingLettersForm form = (OutgoingLettersForm) context.getForm();
        form.getGridOutgoingLetters().decPage();
        return internalFilter(context);
      }
    });
    return null;
  }

  public ActionForward filter(IActionContext context) throws Exception
  {
    OutgoingLettersForm form = (OutgoingLettersForm) context.getForm();
    form.getGridOutgoingLetters().setPage(1);
    return internalFilter(context);
  }

  public ActionForward internalFilter(IActionContext context) throws Exception
  {
    OutgoingLettersForm form = (OutgoingLettersForm) context.getForm();
    DAOUtils.fillGrid(context, form.getGridOutgoingLetters(), "select-outgoing_letters", OutgoingLettersForm.OutgoingLetter.class, null, null);

    return context.getMapping().getInputForward();
  }

  public ActionForward input(IActionContext context) throws Exception
  {
    OutgoingLettersForm form = (OutgoingLettersForm) context.getForm();
    form.setContractor(new Contractor());
    form.setDate_begin("");
    form.setDate_end("");
    form.setNumber("");
    form.setUser(new User());
    form.setSeller(new Seller());

    return internalFilter(context);
  }

  public ActionForward restore(IActionContext context) throws Exception
  {
    restoreForm(context);
    return internalFilter(context);
  }

}