package net.sam.dcl.action;

import net.sam.dcl.controller.actions.DBAction;
import net.sam.dcl.controller.IDispatchable;
import net.sam.dcl.controller.IFormAutoSave;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.form.OrdersLogisticsForm;
import net.sam.dcl.beans.OrdersLogistics;
import net.sam.dcl.beans.OrdersLogisticsLine;
import net.sam.dcl.util.StoreUtil;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.taglib.table.IStyleClassChecker;
import net.sam.dcl.taglib.table.StyleClassCheckerContext;
import net.sam.dcl.report.excel.Grid2Excel;
import org.apache.struts.action.ActionForward;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class OrdersLogisticsAction extends DBAction implements IDispatchable, IFormAutoSave
{
  private void saveCurrentFormToBean(IActionContext context)
  {
    OrdersLogisticsForm form = (OrdersLogisticsForm) context.getForm();

    OrdersLogistics ordersLogistics = (OrdersLogistics) StoreUtil.getSession(context.getRequest(), OrdersLogistics.class);

    ordersLogistics.setView_weight(form.getView_weight());
    ordersLogistics.setOrdersLogisticsLines(form.getGrid().getDataList());

    StoreUtil.putSession(context.getRequest(), ordersLogistics);
  }

  public ActionForward filter(IActionContext context) throws Exception
  {
    final OrdersLogisticsForm form = (OrdersLogisticsForm) context.getForm();

    if (StringUtil.isEmpty(form.getDate_end()))
    {
      return context.getMapping().getInputForward();
    }

    if ( form.isCanForm() )
    {
      form.setSelect_list( !StringUtil.isEmpty(form.getBy_product()) ? OrdersLogisticsForm.selectAll : OrdersLogisticsForm.selectDistinct );
      DAOUtils.fillGrid(context, form.getGrid(), "select-orders_logistics", OrdersLogisticsLine.class, null, null);
      saveCurrentFormToBean(context);

      context.getRequest().setAttribute("style-checker", new IStyleClassChecker()
      {
        public String check(StyleClassCheckerContext context)
        {
          OrdersLogisticsLine ordersLogisticsLine = (OrdersLogisticsLine) form.getGrid().getDataList().get(context.getTable().getRecordCounter() - 1);
          if (ordersLogisticsLine.isItogLine())
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
    OrdersLogisticsForm form = (OrdersLogisticsForm) context.getForm();
    form.setCanForm(true);

    return filter(context);
  }

  public ActionForward generateExcel(IActionContext context) throws Exception
  {
    OrdersLogistics ordersLogistics = (OrdersLogistics) StoreUtil.getSession(context.getRequest(), OrdersLogistics.class);
    Grid2Excel grid2Excel = new Grid2Excel("OrdersLogistics Report");
    grid2Excel.doGrid2Excel(context, ordersLogistics.getExcelTable());
    return null;
  }

  public ActionForward cleanAll(IActionContext context) throws Exception
  {
    OrdersLogisticsForm form = (OrdersLogisticsForm) context.getForm();
    form.setCanForm(false);

    OrdersLogistics ordersLogistics = (OrdersLogistics) StoreUtil.getSession(context.getRequest(), OrdersLogistics.class);
    ordersLogistics.cleanList();

    return filter(context);
  }

  public ActionForward input(IActionContext context) throws Exception
  {
    OrdersLogisticsForm form = (OrdersLogisticsForm) context.getForm();
    form.setCanForm(false);
    form.setView_weight("");
    form.setBy_product("");
 
    OrdersLogistics ordersLogistics = new OrdersLogistics();
    StoreUtil.putSession(context.getRequest(), ordersLogistics);
    saveCurrentFormToBean(context);

    return filter(context);
  }
}
