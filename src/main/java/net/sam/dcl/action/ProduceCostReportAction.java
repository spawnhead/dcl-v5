package net.sam.dcl.action;

import net.sam.dcl.controller.actions.DBAction;
import net.sam.dcl.controller.IDispatchable;
import net.sam.dcl.controller.IFormAutoSave;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.form.ProduceCostReportForm;
import net.sam.dcl.beans.ProduceCostReport;
import net.sam.dcl.beans.ProduceCostReportLine;
import net.sam.dcl.util.StoreUtil;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.StrutsUtil;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.report.excel.Grid2Excel;
import org.apache.struts.action.ActionForward;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class ProduceCostReportAction extends DBAction implements IDispatchable, IFormAutoSave
{
  private void saveCurrentFormToBean(IActionContext context)
  {
    ProduceCostReportForm form = (ProduceCostReportForm) context.getForm();

    ProduceCostReport produceCostReport = (ProduceCostReport) StoreUtil.getSession(context.getRequest(), ProduceCostReport.class);

    produceCostReport.setShowNumberRoute(form.isShowNumberRoute());
    produceCostReport.setProduceCostReportLines(form.getGrid().getDataList());

    StoreUtil.putSession(context.getRequest(), produceCostReport);
  }

  public ActionForward filter(IActionContext context) throws Exception
  {
    ProduceCostReportForm form = (ProduceCostReportForm) context.getForm();

    if (StringUtil.isEmpty(form.getDate_begin()))
    {
      return context.getMapping().getInputForward();
    }

    boolean incorrectDates = false;
    if (!StringUtil.isEmpty(form.getDate_end()))
    {
      Date dateBegin = StringUtil.getCurrentDateTime();
      Date dateEnd = StringUtil.getCurrentDateTime();
      try
      {
        dateBegin = StringUtil.appDateString2Date(form.getDate_begin());
        dateEnd = StringUtil.appDateString2Date(form.getDate_end());
      }
      catch (Exception e)
      {
        log.error(e);
      }
      if (dateEnd.before(dateBegin))
      {
        StrutsUtil.addError(context, "error.produce_cost_report.incorrect_dates", null);
        incorrectDates = true;
      }
    }

    if (form.isCanForm() && !incorrectDates)
    {
      if ( StringUtil.isEmpty(form.getBy_month()) )
      {
        DAOUtils.fillGrid(context, form.getGrid(), "select-produce_cost_report", ProduceCostReportLine.class, null, null);
        saveCurrentFormToBean(context);
      }
      else
      {
        DAOUtils.fillGrid(context, form.getGrid(), "select-produce_cost_report_sum", ProduceCostReportLine.class, null, null);
        saveCurrentFormToBean(context);
        ProduceCostReport produceCostReport = (ProduceCostReport) StoreUtil.getSession(context.getRequest(), ProduceCostReport.class);
        produceCostReport.setNeedFormatDate();
        StoreUtil.putSession(context.getRequest(), produceCostReport);
        form.getGrid().setDataList(produceCostReport.getProduceCostReportLines());
      }
    }

    return show(context);
  }

  public ActionForward show(IActionContext context) throws Exception
  {
    return context.getMapping().getInputForward();
  }

  public ActionForward generate(IActionContext context) throws Exception
  {
    ProduceCostReportForm form = (ProduceCostReportForm) context.getForm();
    form.setCanForm(true);

    return filter(context);
  }

  public ActionForward generateExcel(IActionContext context) throws Exception
  {
    ProduceCostReport goodsCirculation = (ProduceCostReport) StoreUtil.getSession(context.getRequest(), ProduceCostReport.class);
    Grid2Excel grid2Excel = new Grid2Excel("ProduceCostReport Report");
    grid2Excel.doGrid2Excel(context, goodsCirculation.getExcelTable());
    return null;
  }

  public ActionForward cleanAll(IActionContext context) throws Exception
  {
    ProduceCostReportForm form = (ProduceCostReportForm) context.getForm();
    form.setCanForm(false);
    ProduceCostReport goodsCirculation = (ProduceCostReport) StoreUtil.getSession(context.getRequest(), ProduceCostReport.class);
    goodsCirculation.cleanList();

    return filter(context);
  }

  public ActionForward input(IActionContext context) throws Exception
  {
    ProduceCostReportForm form = (ProduceCostReportForm) context.getForm();
    form.setCanForm(false);

    ProduceCostReport produceCostReport = new ProduceCostReport();
    StoreUtil.putSession(context.getRequest(), produceCostReport);
    saveCurrentFormToBean(context);

    return filter(context);
  }
}