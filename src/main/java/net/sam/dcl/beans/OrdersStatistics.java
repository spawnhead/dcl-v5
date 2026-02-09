package net.sam.dcl.beans;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

import net.sam.dcl.util.StrutsUtil;
import net.sam.dcl.controller.ActionContext;
import net.sam.dcl.controller.IActionContext;

/**
 * Created by IntelliJ IDEA.
 * Date: 31.08.2005
 * Time: 18:53:07
 * To change this template use File | Settings | File Templates.
 */
public class OrdersStatistics implements Serializable
{
  protected static Log log = LogFactory.getLog(OrdersStatistics.class);
  List<OrdersStatisticsLine> ordersStatisticsLines = new ArrayList<OrdersStatisticsLine>();

  Department department = new Department();
  StuffCategory stuffCategory = new StuffCategory();

  public OrdersStatistics()
  {
  }

  public List<OrdersStatisticsLine> getOrdersStatisticsLines()
  {
    return ordersStatisticsLines;
  }

  public void setOrdersStatisticsLines(List<OrdersStatisticsLine> ordersStatisticsLines)
  {
    this.ordersStatisticsLines = ordersStatisticsLines;
  }

  public Department getDepartment()
  {
    return department;
  }

  public void setDepartment(Department department)
  {
    this.department = department;
  }

  public StuffCategory getStuffCategory()
  {
    return stuffCategory;
  }

  public void setStuffCategory(StuffCategory stuffCategory)
  {
    this.stuffCategory = stuffCategory;
  }

  public void cleanList()
  {
    ordersStatisticsLines.clear();
  }

  public OrdersStatisticsLine getEmptyLine()
  {
    OrdersStatisticsLine ordersStatisticsLine = new OrdersStatisticsLine();

    ordersStatisticsLine.setStf_name("");
    ordersStatisticsLine.setOrd_summ(0.0);
    ordersStatisticsLine.setOrd_summ_sent_to_prod(0.0);
    ordersStatisticsLine.setOrd_summ_part_executed(0.0);
    ordersStatisticsLine.setOrd_summ_executed(0.0);
    ordersStatisticsLine.setOrd_summ_executed_before(0.0);
    ordersStatisticsLine.setCur_name("");

    return ordersStatisticsLine;
  }

  public List getExcelTable()
  {
    List<Object> rows = new ArrayList<Object>();

    IActionContext context = ActionContext.threadInstance();

    List<Object> header = new ArrayList<Object>();
    try
    {
      header.add(StrutsUtil.getMessage(context, "OrdersStatistics.stf_name"));
      header.add(StrutsUtil.getMessage(context, "OrdersStatistics.ord_summ"));
      header.add(StrutsUtil.getMessage(context, "OrdersStatistics.ord_summ_sent_to_prod"));
      header.add(StrutsUtil.getMessage(context, "OrdersStatistics.ord_summ_part_executed"));
      header.add(StrutsUtil.getMessage(context, "OrdersStatistics.ord_summ_executed"));
      header.add(StrutsUtil.getMessage(context, "OrdersStatistics.ord_summ_executed_before"));
      header.add(StrutsUtil.getMessage(context, "OrdersStatistics.cur_name"));
    }
    catch (Exception e)
    {
      log.error(e);
    }
    rows.add(header);

    for (OrdersStatisticsLine ordersStatisticsLine : ordersStatisticsLines)
    {
      List<Object> record = new ArrayList<Object>();
      record.add(ordersStatisticsLine.getStf_name());
      record.add(ordersStatisticsLine.getOrd_summ());
      record.add(ordersStatisticsLine.getOrd_summ_sent_to_prod());
      record.add(ordersStatisticsLine.getOrd_summ_part_executed());
      record.add(ordersStatisticsLine.getOrd_summ_executed());
      record.add(ordersStatisticsLine.getOrd_summ_executed_before());
      record.add(ordersStatisticsLine.getCur_name());

      rows.add(record);
    }

    return rows;
  }

}
