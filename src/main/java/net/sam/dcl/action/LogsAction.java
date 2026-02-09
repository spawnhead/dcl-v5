package net.sam.dcl.action;

import net.sam.dcl.controller.actions.DBAction;
import net.sam.dcl.controller.*;
import net.sam.dcl.controller.processor.ActionHandler;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.util.StrutsUtil;
import net.sam.dcl.beans.User;
import net.sam.dcl.taglib.table.model.PageableDataHolder;
import net.sam.dcl.form.LogsForm;
import net.sam.dcl.report.excel.Grid2Excel;
import org.apache.struts.action.ActionForward;

import java.util.List;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class LogsAction extends DBAction implements IDispatchable, IFormAutoSave, IProcessBefore
{
  public ActionForward processBefore(IActionContext context) throws Exception
  {
    context.getActionProcessor().addActionHandler("grid", PageableDataHolder.NEXT_PAGE, new ActionHandler()
    {
      public ActionForward process(IActionContext context) throws Exception
      {
        LogsForm form = (LogsForm) context.getForm();
        form.getGrid().incPage();
        return internalFilter(context);
      }
    });
    context.getActionProcessor().addActionHandler("grid", PageableDataHolder.PREV_PAGE, new ActionHandler()
    {
      public ActionForward process(IActionContext context) throws Exception
      {
        LogsForm form = (LogsForm) context.getForm();
        form.getGrid().decPage();
        return internalFilter(context);
      }
    });
    return null;
  }

  public ActionForward filter(IActionContext context) throws Exception
  {
    LogsForm form = (LogsForm) context.getForm();
    form.getGrid().setPage(1);
    return internalFilter(context);
  }

  public ActionForward internalFilter(IActionContext context) throws Exception
  {
    final LogsForm form = (LogsForm) context.getForm();
    DAOUtils.fillGrid(context, form.getGrid(), "select-logs", LogsForm.DBLog.class, null, null);
    return context.getMapping().getInputForward();
  }

  public ActionForward input(IActionContext context) throws Exception
  {
    LogsForm form = (LogsForm) context.getForm();
    form.setUser(new User());
    form.setAction(new net.sam.dcl.beans.Action());
    form.setDate_begin("");
    form.setDate_end("");
    form.setIp("");

    return internalFilter(context);
  }

  public ActionForward restore(IActionContext context) throws Exception
  {
    restoreForm(context);
    return internalFilter(context);
  }

  public ActionForward generateExcel(IActionContext context) throws Exception
  {
    LogsForm formContext = (LogsForm) context.getForm();
    LogsForm form = new LogsForm();
    form.setAction(formContext.getAction());
    form.setIp(formContext.getIp());
    form.setDate_begin(formContext.getDate_begin());
    form.setDate_end(formContext.getDate_end());
    form.setUser(formContext.getUser());
    List<LogsForm.DBLog> lst = DAOUtils.fillList(context, "select-logs", form, LogsForm.DBLog.class, null, null);

    Grid2Excel grid2Excel = new Grid2Excel("Events list");
    grid2Excel.doGrid2Excel(context, toExcelGrid(context, lst));
    return null;
  }

  List toExcelGrid(IActionContext context, List contractors)
  {
    List<Object> rows = new ArrayList<Object>();

    List<Object> header = new ArrayList<Object>();
    try
    {
      header.add(StrutsUtil.getMessage(context, "Logs.action"));
      header.add(StrutsUtil.getMessage(context, "Logs.user"));
      header.add(StrutsUtil.getMessage(context, "Logs.ip"));
      header.add(StrutsUtil.getMessage(context, "Logs.date"));
      rows.add(header);

      for (Object contractor : contractors)
      {
        LogsForm.DBLog dbLog = (LogsForm.DBLog) contractor;
        List<Object> record = new ArrayList<Object>();
        record.add(dbLog.getLog_action());
        record.add(dbLog.getLog_user());
        record.add(dbLog.getLog_ip());
        record.add(dbLog.getLog_time_formatted());
        rows.add(record);
      }
    }
    catch (Exception e)
    {
      throw new RuntimeException(e);
    }
    return rows;
  }
}