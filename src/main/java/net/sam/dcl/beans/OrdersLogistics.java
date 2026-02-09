package net.sam.dcl.beans;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.controller.ActionContext;
import net.sam.dcl.util.StrutsUtil;
import net.sam.dcl.util.StringUtil;

/**
 * Created by IntelliJ IDEA.
 * Date: 31.08.2005
 * Time: 18:53:07
 * To change this template use File | Settings | File Templates.
 */
public class OrdersLogistics implements Serializable
{
  protected static Log log = LogFactory.getLog(OrdersLogistics.class);
  final static public String html_bold_begin = "<b>";
  final static public String html_bold_end = "</b>";

  String view_weight;

  List<OrdersLogisticsLine> ordersLogisticsLines = new ArrayList<OrdersLogisticsLine>();

  public OrdersLogistics()
  {
  }

  public String getView_weight()
  {
    return view_weight;
  }

  public void setView_weight(String view_weight)
  {
    this.view_weight = view_weight;
  }

  public List<OrdersLogisticsLine> getOrdersLogisticsLines()
  {
    return ordersLogisticsLines;
  }

  public void setOrdersLogisticsLines(List<OrdersLogisticsLine> ordersLogisticsLines)
  {
    this.ordersLogisticsLines = ordersLogisticsLines;
  }

  public void cleanList()
  {
    ordersLogisticsLines.clear();
  }

  public OrdersLogisticsLine getEmptyLine()
  {
    OrdersLogisticsLine ordersLogisticsLine = new OrdersLogisticsLine();

    ordersLogisticsLine.setShp_doc_type_num("");
    ordersLogisticsLine.setOrd_num("");
    ordersLogisticsLine.setConf_num("");
    ordersLogisticsLine.setWeight(0.0);
    ordersLogisticsLine.setConf_date("");
    ordersLogisticsLine.setContractor("");
    ordersLogisticsLine.setContact_person("");
    ordersLogisticsLine.setComment("");

    return ordersLogisticsLine;
  }

  public List getExcelTable()
  {
    List<Object> rows = new ArrayList<Object>();

    IActionContext context = ActionContext.threadInstance();

    List<Object> header = new ArrayList<Object>();
    try
    {
      header.add(StrutsUtil.getMessage(context, "OrdersLogistics.shp_doc_type_num"));
      header.add(StrutsUtil.getMessage(context, "OrdersLogistics.ord_conf_num").replaceAll(ReportDelimiterConsts.html_separator, ReportDelimiterConsts.ecxel_separator));
      if ( !StringUtil.isEmpty(getView_weight()) )
      {
        header.add(StrutsUtil.getMessage(context, "OrdersLogistics.weight"));
      }
      header.add(StrutsUtil.getMessage(context, "OrdersLogistics.conf_date"));
      header.add(StrutsUtil.getMessage(context, "OrdersLogistics.contractor_contact_person").replaceAll(ReportDelimiterConsts.html_separator, ReportDelimiterConsts.ecxel_separator));
      header.add(StrutsUtil.getMessage(context, "OrdersLogistics.comment"));
    }
    catch (Exception e)
    {
      log.error(e);
    }
    rows.add(header);

    for (OrdersLogisticsLine ordersLogisticsLine : ordersLogisticsLines)
    {
      List<Object> record = new ArrayList<Object>();

      String tmp = ordersLogisticsLine.getShp_doc_type_num().replaceAll(ReportDelimiterConsts.html_separator, ReportDelimiterConsts.ecxel_separator);
      tmp = tmp.replaceAll(html_bold_begin, "");
      tmp = tmp.replaceAll(html_bold_end, "");
      if ( tmp.endsWith(ReportDelimiterConsts.ecxel_separator) )
      {
        tmp = tmp.substring(0, tmp.length() - ReportDelimiterConsts.ecxel_separator.length());
      }
      record.add(tmp);

      tmp = ordersLogisticsLine.getOrd_conf_num().replaceAll(ReportDelimiterConsts.html_separator, ReportDelimiterConsts.ecxel_separator);
      if ( tmp.endsWith(ReportDelimiterConsts.ecxel_separator) )
      {
        tmp = tmp.substring(0, tmp.length() - ReportDelimiterConsts.ecxel_separator.length());
      }
      record.add(tmp);

      if ( !StringUtil.isEmpty(getView_weight()) )
      {
        record.add(ordersLogisticsLine.getWeight());
      }

      tmp = ordersLogisticsLine.getConf_date().replaceAll(ReportDelimiterConsts.html_separator, ReportDelimiterConsts.ecxel_separator);
      tmp = tmp.replaceAll(html_bold_begin, "");
      tmp = tmp.replaceAll(html_bold_end, "");
      if ( tmp.endsWith(ReportDelimiterConsts.ecxel_separator) )
      {
        tmp = tmp.substring(0, tmp.length() - ReportDelimiterConsts.ecxel_separator.length());
      }
      record.add(tmp);

      tmp = ordersLogisticsLine.getContractor_contact_person().replaceAll(ReportDelimiterConsts.html_separator, ReportDelimiterConsts.ecxel_separator);
      if ( tmp.endsWith(ReportDelimiterConsts.ecxel_separator) )
      {
        tmp = tmp.substring(0, tmp.length() - ReportDelimiterConsts.ecxel_separator.length());
      }
      record.add(tmp);

      record.add(ordersLogisticsLine.getCommentExcel());

      rows.add(record);
    }

    return rows;
  }

}
