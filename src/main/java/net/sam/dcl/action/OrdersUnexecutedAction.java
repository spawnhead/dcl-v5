package net.sam.dcl.action;

import net.sam.dcl.controller.actions.DBAction;
import net.sam.dcl.controller.IDispatchable;
import net.sam.dcl.controller.IFormAutoSave;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.form.OrdersUnexecutedForm;
import net.sam.dcl.beans.OrdersUnexecuted;
import net.sam.dcl.beans.OrdersUnexecutedLine;
import net.sam.dcl.util.StoreUtil;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.report.excel.Grid2Excel;
import org.apache.struts.action.ActionForward;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class OrdersUnexecutedAction extends DBAction implements IDispatchable, IFormAutoSave
{
  private void saveFormToBean(IActionContext context)
  {
    OrdersUnexecutedForm form = (OrdersUnexecutedForm) context.getForm();

    OrdersUnexecuted OrdersUnexecuted = (OrdersUnexecuted) StoreUtil.getSession(context.getRequest(), OrdersUnexecuted.class);

    OrdersUnexecuted.setOrdersUnexecutedLines(form.getGrid().getDataList());

    StoreUtil.putSession(context.getRequest(), OrdersUnexecuted);
  }

  public ActionForward filter(IActionContext context) throws Exception
  {
    final OrdersUnexecutedForm form = (OrdersUnexecutedForm) context.getForm();

    if (form.isCanForm())
    {
      form.setOrder_by("order by ord_date");
      if ( !StringUtil.isEmpty(form.getOrder_by_number()) )
      {
        form.setOrder_by("order by ord_number_sort");
      }
      if ( !StringUtil.isEmpty(form.getOrder_by_stf_name()) )
      {
        form.setOrder_by("order by stf_name_sort");
      }
      if ( !StringUtil.isEmpty(form.getOrder_by_produce_full_name()) )
      {
        form.setOrder_by("order by produce_full_name_sort");
      }
      if ( !StringUtil.isEmpty(form.getOrder_by_ctn_number()) )
      {
        form.setOrder_by("order by ctn_number_sort");
      }

      DAOUtils.fillGrid(context, form.getGrid(), "select-orders_unexecuted", OrdersUnexecutedLine.class, null, null);
      saveFormToBean(context);
    }

    return context.getMapping().getInputForward();
  }

  public ActionForward generate(IActionContext context) throws Exception
  {
    OrdersUnexecutedForm form = (OrdersUnexecutedForm) context.getForm();
    form.setCanForm(true);

    return filter(context);
  }

  public ActionForward generateExcel(IActionContext context) throws Exception
  {
    OrdersUnexecuted OrdersUnexecuted = (OrdersUnexecuted) StoreUtil.getSession(context.getRequest(), OrdersUnexecuted.class);
    Grid2Excel grid2Excel = new Grid2Excel("OrdersUnexecuted Report");
    grid2Excel.doGrid2Excel(context, OrdersUnexecuted.getExcelTable());
    return null;
  }

  public ActionForward input(IActionContext context) throws Exception
  {
    OrdersUnexecutedForm form = (OrdersUnexecutedForm) context.getForm();
    form.setCanForm(false);
    form.setOrder_by_date("1");

    OrdersUnexecuted OrdersUnexecuted = new OrdersUnexecuted();
    StoreUtil.putSession(context.getRequest(), OrdersUnexecuted);
    saveFormToBean(context);

    return filter(context);
  }
}