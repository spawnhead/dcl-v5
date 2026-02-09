package net.sam.dcl.beans;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * Date: 14.09.2005
 * Time: 17:17:20
 * To change this template use File | Settings | File Templates.
 */
public class OrderExecutedDateLines implements Serializable
{
  List<OrderExecutedDateLine> orderExecutedDate = new ArrayList<OrderExecutedDateLine>();

  public List<OrderExecutedDateLine> getOrderExecutedDate()
  {
    return orderExecutedDate;
  }

  public void setOrderExecutedDate(List<OrderExecutedDateLine> orderExecutedDate)
  {
    this.orderExecutedDate = orderExecutedDate;
  }
}