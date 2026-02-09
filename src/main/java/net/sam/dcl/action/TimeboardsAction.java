package net.sam.dcl.action;

import net.sam.dcl.controller.actions.DBAction;
import net.sam.dcl.controller.*;
import net.sam.dcl.controller.processor.ActionHandler;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.util.UserUtil;
import net.sam.dcl.form.TimeboardsForm;
import net.sam.dcl.beans.User;
import net.sam.dcl.beans.Timeboard;
import net.sam.dcl.taglib.table.model.PageableDataHolder;
import net.sam.dcl.taglib.table.IReadOnlyChecker;
import net.sam.dcl.taglib.table.ReadOnlyCheckerContext;
import net.sam.dcl.dao.TimeboardDAO;
import org.apache.struts.action.ActionForward;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class TimeboardsAction extends DBAction implements IDispatchable, IFormAutoSave, IProcessBefore
{
  public ActionForward processBefore(IActionContext context) throws Exception
  {
    context.getActionProcessor().addActionHandler("grid", PageableDataHolder.NEXT_PAGE, new ActionHandler()
    {
      public ActionForward process(IActionContext context) throws Exception
      {
        TimeboardsForm form = (TimeboardsForm) context.getForm();
        form.getGrid().incPage();
        return internalFilter(context);
      }
    });
    context.getActionProcessor().addActionHandler("grid", PageableDataHolder.PREV_PAGE, new ActionHandler()
    {
      public ActionForward process(IActionContext context) throws Exception
      {
        TimeboardsForm form = (TimeboardsForm) context.getForm();
        form.getGrid().decPage();
        return internalFilter(context);
      }
    });
    return null;
  }

  public ActionForward filter(IActionContext context) throws Exception
  {
    TimeboardsForm form = (TimeboardsForm) context.getForm();
    form.getGrid().setPage(1);
    return internalFilter(context);
  }

  public ActionForward internalFilter(IActionContext context) throws Exception
  {
    TimeboardsForm form = (TimeboardsForm) context.getForm();
    DAOUtils.fillGrid(context, form.getGrid(), "select-timeboards", TimeboardsForm.Timeboard.class, null, null);

    final User currentUser = UserUtil.getCurrentUser(context.getRequest());
    context.getRequest().setAttribute("blockChecker", new IReadOnlyChecker()
    {
      public boolean check(ReadOnlyCheckerContext ctx) throws Exception
      {
        return !currentUser.isAdmin();
      }
    });

    return context.getMapping().getInputForward();
  }

  public ActionForward block(IActionContext context) throws Exception
  {
    TimeboardsForm form = (TimeboardsForm) context.getForm();
    Timeboard timeboard = new Timeboard();
    timeboard.setTmb_id(form.getTmb_id());
    if ("1".equals(form.getBlock()))
    {
      timeboard.setTmb_checked("");
    }
    else
    {
      timeboard.setTmb_checked("1");
    }
    TimeboardDAO.saveChecked(context, timeboard);

    return internalFilter(context);
  }

  public ActionForward input(IActionContext context) throws Exception
  {
    TimeboardsForm form = (TimeboardsForm) context.getForm();
    form.setUser(new User());

    return internalFilter(context);
  }

  public ActionForward restore(IActionContext context) throws Exception
  {
    restoreForm(context);
    return internalFilter(context);
  }
}