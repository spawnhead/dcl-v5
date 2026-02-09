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
public class OrdersUnexecuted implements Serializable
{
  protected static Log log = LogFactory.getLog(OrdersUnexecuted.class);
  List<OrdersUnexecutedLine> ordersUnexecutedLines = new ArrayList<OrdersUnexecutedLine>();

  public OrdersUnexecuted()
  {
  }

  public List<OrdersUnexecutedLine> getOrdersUnexecutedLines()
  {
    return ordersUnexecutedLines;
  }

  public void setOrdersUnexecutedLines(List<OrdersUnexecutedLine> ordersUnexecutedLines)
  {
    this.ordersUnexecutedLines = ordersUnexecutedLines;
  }

  public void cleanList()
  {
    ordersUnexecutedLines.clear();
  }

  public OrdersUnexecutedLine getEmptyLine()
  {
    OrdersUnexecutedLine ordersUnexecutedLine = new OrdersUnexecutedLine();

    ordersUnexecutedLine.setOrd_id("");
    ordersUnexecutedLine.setOrd_date("");
    ordersUnexecutedLine.setOrd_number("");
    ordersUnexecutedLine.setPrd_id("");
    ordersUnexecutedLine.setProduce_name("");
    ordersUnexecutedLine.setPrd_type("");
    ordersUnexecutedLine.setPrd_params("");
    ordersUnexecutedLine.setPrd_add_params("");
    ordersUnexecutedLine.setCtn_number("");
    ordersUnexecutedLine.setStf_name("");
    ordersUnexecutedLine.setOrd_count(0.0);
    ordersUnexecutedLine.setOrd_count_executed(0.0);
    ordersUnexecutedLine.setOrd_count_unexecuted(0.0);

    return ordersUnexecutedLine;
  }

  public List getExcelTable()
  {
    List<Object> rows = new ArrayList<Object>();

    IActionContext context = ActionContext.threadInstance();

    List<Object> header = new ArrayList<Object>();
    try
    {
      header.add(StrutsUtil.getMessage(context, "OrdersUnexecuted.ord_date"));
      header.add(StrutsUtil.getMessage(context, "OrdersUnexecuted.ord_number"));
      header.add(StrutsUtil.getMessage(context, "OrdersUnexecuted.stf_name"));
      header.add(StrutsUtil.getMessage(context, "OrdersUnexecuted.produce_full_name"));
      header.add(StrutsUtil.getMessage(context, "OrdersUnexecuted.ctn_number"));
      header.add(StrutsUtil.getMessage(context, "OrdersUnexecuted.ord_count"));
      header.add(StrutsUtil.getMessage(context, "OrdersUnexecuted.ord_count_executed"));
      header.add(StrutsUtil.getMessage(context, "OrdersUnexecuted.ord_count_unexecuted"));
    }
    catch (Exception e)
    {
      log.error(e);
    }
    rows.add(header);

    for (OrdersUnexecutedLine ordersUnexecutedLine : ordersUnexecutedLines)
    {
      List<Object> record = new ArrayList<Object>();
      record.add(ordersUnexecutedLine.getOrd_date_formatted());
      record.add(ordersUnexecutedLine.getOrd_number());
      record.add(ordersUnexecutedLine.getStf_name());
      record.add(ordersUnexecutedLine.getFullName());
      record.add(ordersUnexecutedLine.getCtn_number());
      record.add(ordersUnexecutedLine.getOrd_count());
      record.add(ordersUnexecutedLine.getOrd_count_executed());
      record.add(ordersUnexecutedLine.getOrd_count_unexecuted());

      rows.add(record);
    }

    return rows;
  }

}