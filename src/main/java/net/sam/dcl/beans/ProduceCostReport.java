package net.sam.dcl.beans;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.controller.ActionContext;
import net.sam.dcl.util.StrutsUtil;

/**
 * Created by IntelliJ IDEA.
 * Date: 31.08.2005
 * Time: 18:53:07
 * To change this template use File | Settings | File Templates.
 */
public class ProduceCostReport implements Serializable
{
  protected static Log log = LogFactory.getLog(ProduceCostReport.class);

  List<ProduceCostReportLine> produceCostReportLines = new ArrayList<ProduceCostReportLine>();

  boolean showNumberRoute;

  public ProduceCostReport()
  {
  }

  public List<ProduceCostReportLine> getProduceCostReportLines()
  {
    return produceCostReportLines;
  }

  public void setProduceCostReportLines(List<ProduceCostReportLine> produceCostReportLines)
  {
    this.produceCostReportLines = produceCostReportLines;
  }

  public boolean isShowNumberRoute()
  {
    return showNumberRoute;
  }

  public void setShowNumberRoute(boolean showNumberRoute)
  {
    this.showNumberRoute = showNumberRoute;
  }

  public void cleanList()
  {
    produceCostReportLines.clear();
  }

  public void setNeedFormatDate()
  {
    for (ProduceCostReportLine produceCostReportLine : produceCostReportLines)
    {
      produceCostReportLine.setNeedShortFormat(true);
    }
  }

  public ProduceCostReportLine getEmptyLine()
  {
    ProduceCostReportLine produceCostReportLine = new ProduceCostReportLine();

    produceCostReportLine.setPrc_date("");
    produceCostReportLine.setPrc_number("");
    produceCostReportLine.setRoute("");
    produceCostReportLine.setWeight(0.0);
    produceCostReportLine.setTransport(0.0);
    produceCostReportLine.setCustom(0.0);

    return produceCostReportLine;
  }

  public List getExcelTable()
  {
    List<Object> rows = new ArrayList<Object>();

    IActionContext context = ActionContext.threadInstance();

    List<Object> header = new ArrayList<Object>();
    try
    {
      header.add(StrutsUtil.getMessage(context, "ProduceCostReport.prc_date"));
      if ( isShowNumberRoute() )
      {
        header.add(StrutsUtil.getMessage(context, "ProduceCostReport.prc_number"));
        header.add(StrutsUtil.getMessage(context, "ProduceCostReport.route"));
      }
      header.add(StrutsUtil.getMessage(context, "ProduceCostReport.weight"));
      header.add(StrutsUtil.getMessage(context, "ProduceCostReport.transport"));
      header.add(StrutsUtil.getMessage(context, "ProduceCostReport.custom"));
    }
    catch (Exception e)
    {
      log.error(e);
    }
    rows.add(header);

    for (ProduceCostReportLine produceCostReportLine : produceCostReportLines)
    {
      List<Object> record = new ArrayList<Object>();
      record.add(produceCostReportLine.getPrc_date_formatted());
      if ( isShowNumberRoute() )
      {
        record.add(produceCostReportLine.getPrc_number());
        record.add(produceCostReportLine.getRoute());
      }
      record.add(produceCostReportLine.getWeight());
      record.add(produceCostReportLine.getTransport());
      record.add(produceCostReportLine.getCustom());

      rows.add(record);
    }

    return rows;
  }

}