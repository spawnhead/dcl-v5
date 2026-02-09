package net.sam.dcl.beans;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * Date: 14.09.2005
 * Time: 17:17:20
 * To change this template use File | Settings | File Templates.
 */
public class OrderExecutedDateLine implements Serializable
{
  String date;
  double countInDate;
  boolean showDeleteColumn;

  public OrderExecutedDateLine(String date)
  {
    this.date = date;
  }

  public OrderExecutedDateLine(String date, boolean showDeleteColumn)
  {
    this.date = date;
    this.showDeleteColumn = showDeleteColumn;
  }

  public OrderExecutedDateLine(OrderExecutedDateLine orderExecutedDateLine)
  {
    this.date = orderExecutedDateLine.getDate();
    this.countInDate = orderExecutedDateLine.getCountInDate();
    this.showDeleteColumn = orderExecutedDateLine.isShowDeleteColumn();
  }

  public String getDate()
  {
    return date;
  }

  public void setDate(String date)
  {
    this.date = date;
  }

  public double getCountInDate()
  {
    return countInDate;
  }

  public void setCountInDate(double countInDate)
  {
    this.countInDate = countInDate;
  }

  public boolean isShowDeleteColumn()
  {
    return showDeleteColumn;
  }

  public void setShowDeleteColumn(boolean showDeleteColumn)
  {
    this.showDeleteColumn = showDeleteColumn;
  }
}