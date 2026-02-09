package net.sam.dcl.beans;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * Date: 14.09.2005
 * Time: 17:17:20
 * To change this template use File | Settings | File Templates.
 */
public class OrderExecutedAndDate implements Serializable
{
  protected static Log log = LogFactory.getLog(OrderExecutedAndDate.class);

  String opr_id;
  double opr_count_executed;
  String opr_date_executed;
  String opr_fictive_executed;

  public OrderExecutedAndDate()
  {
  }

  public OrderExecutedAndDate(OrderExecutedAndDate executedAndDate)
  {
    this.opr_id = executedAndDate.getOpr_id();
    this.opr_count_executed = executedAndDate.getOpr_count_executed();
    this.opr_date_executed = executedAndDate.getOpr_date_executed();
    this.opr_fictive_executed = executedAndDate.getOpr_fictive_executed();
  }

  public String getOpr_id()
  {
    return opr_id;
  }

  public void setOpr_id(String opr_id)
  {
    this.opr_id = opr_id;
  }

  public double getOpr_count_executed()
  {
    return opr_count_executed;
  }

  public void setOpr_count_executed(double opr_count_executed)
  {
    this.opr_count_executed = opr_count_executed;
  }

  public String getOpr_date_executed()
  {
    return opr_date_executed;
  }

  public void setOpr_date_executed(String opr_date_executed)
  {
    this.opr_date_executed = opr_date_executed;
  }

  public String getOpr_fictive_executed()
  {
    return opr_fictive_executed;
  }

  public void setOpr_fictive_executed(String opr_fictive_executed)
  {
    this.opr_fictive_executed = opr_fictive_executed;
  }
}