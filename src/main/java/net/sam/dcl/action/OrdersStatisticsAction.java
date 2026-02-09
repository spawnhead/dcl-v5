package net.sam.dcl.action;

import net.sam.dcl.controller.actions.DBAction;
import net.sam.dcl.controller.IDispatchable;
import net.sam.dcl.controller.IFormAutoSave;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.form.OrdersStatisticsForm;
import net.sam.dcl.beans.OrdersStatistics;
import net.sam.dcl.beans.OrdersStatisticsLine;
import net.sam.dcl.util.StoreUtil;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.StrutsUtil;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.taglib.table.IStyleClassChecker;
import net.sam.dcl.taglib.table.StyleClassCheckerContext;
import net.sam.dcl.report.excel.Grid2Excel;
import org.apache.struts.action.ActionForward;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class OrdersStatisticsAction extends DBAction implements IDispatchable, IFormAutoSave
{
  private void saveFormToBean(IActionContext context)
  {
    OrdersStatisticsForm form = (OrdersStatisticsForm) context.getForm();

    OrdersStatistics ordersStatistics = (OrdersStatistics) StoreUtil.getSession(context.getRequest(), OrdersStatistics.class);

    ordersStatistics.setDepartment(form.getDepartment());
    ordersStatistics.setStuffCategory(form.getStuffCategory());

    ordersStatistics.setOrdersStatisticsLines(form.getGrid().getDataList());

    StoreUtil.putSession(context.getRequest(), ordersStatistics);
  }

  public ActionForward filter(IActionContext context) throws Exception
  {
    final OrdersStatisticsForm form = (OrdersStatisticsForm) context.getForm();

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
        StrutsUtil.addError(context, "error.orders_statistics.incorrect_dates", null);
        incorrectDates = true;
      }
    }

    if (form.isCanForm() && !incorrectDates)
    {
      if (StringUtil.isEmpty(form.getStuffCategory().getName()))
      {
        form.getStuffCategory().setId(null);
      }
      if (StringUtil.isEmpty(form.getDepartment().getName()))
      {
        form.getDepartment().setId(null);
      }

      DAOUtils.fillGrid(context, form.getGrid(), "select-orders_statistics", OrdersStatisticsLine.class, null, null);
      saveFormToBean(context);

      context.getRequest().setAttribute("style-checker", new IStyleClassChecker()
      {
        public String check(StyleClassCheckerContext context)
        {
          OrdersStatisticsLine ordersStatisticsLine = (OrdersStatisticsLine) form.getGrid().getDataList().get(context.getTable().getRecordCounter() - 1);
          if (ordersStatisticsLine.isItogLine())
          {
            return "bold-cell";
          }
          return "";
        }
      });
    }

    return context.getMapping().getInputForward();
  }

  public ActionForward generate(IActionContext context) throws Exception
  {
    OrdersStatisticsForm form = (OrdersStatisticsForm) context.getForm();
    form.setCanForm(true);

    return filter(context);
  }

  public ActionForward generateExcel(IActionContext context) throws Exception
  {
    OrdersStatistics ordersStatistics = (OrdersStatistics) StoreUtil.getSession(context.getRequest(), OrdersStatistics.class);
    Grid2Excel grid2Excel = new Grid2Excel("OrdersStatistics Report");
    grid2Excel.doGrid2Excel(context, ordersStatistics.getExcelTable());
    return null;
  }

  public ActionForward cleanAll(IActionContext context) throws Exception
  {
    OrdersStatisticsForm form = (OrdersStatisticsForm) context.getForm();
    form.setCanForm(false);

    form.getDepartment().setId(StrutsUtil.getMessage(context, "list.all_id"));
    form.getDepartment().setName(StrutsUtil.getMessage(context, "list.all"));
    form.getStuffCategory().setId(StrutsUtil.getMessage(context, "list.all_id"));
    form.getStuffCategory().setName(StrutsUtil.getMessage(context, "list.all"));
    form.getContractor().setId(StrutsUtil.getMessage(context, "list.all_id"));
    form.getContractor().setName(StrutsUtil.getMessage(context, "list.all"));
    form.getContractor_for().setId(StrutsUtil.getMessage(context, "list.all_id"));
    form.getContractor_for().setName(StrutsUtil.getMessage(context, "list.all"));

    OrdersStatistics ordersStatistics = (OrdersStatistics) StoreUtil.getSession(context.getRequest(), OrdersStatistics.class);
    ordersStatistics.cleanList();

    return filter(context);
  }

  public ActionForward input(IActionContext context) throws Exception
  {
    OrdersStatisticsForm form = (OrdersStatisticsForm) context.getForm();
    form.setCanForm(false);

    form.getDepartment().setId(StrutsUtil.getMessage(context, "list.all_id"));
    form.getDepartment().setName(StrutsUtil.getMessage(context, "list.all"));
    form.getStuffCategory().setId(StrutsUtil.getMessage(context, "list.all_id"));
    form.getStuffCategory().setName(StrutsUtil.getMessage(context, "list.all"));
    form.getContractor().setId(StrutsUtil.getMessage(context, "list.all_id"));
    form.getContractor().setName(StrutsUtil.getMessage(context, "list.all"));
    form.getContractor_for().setId(StrutsUtil.getMessage(context, "list.all_id"));
    form.getContractor_for().setName(StrutsUtil.getMessage(context, "list.all"));

    OrdersStatistics ordersStatistics = new OrdersStatistics();
    StoreUtil.putSession(context.getRequest(), ordersStatistics);
    saveFormToBean(context);

    return filter(context);
  }
}
