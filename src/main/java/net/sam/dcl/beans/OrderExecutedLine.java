package net.sam.dcl.beans;

import net.sam.dcl.util.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Date: 14.09.2005
 * Time: 17:17:20
 * To change this template use File | Settings | File Templates.
 */
public class OrderExecutedLine implements Serializable
{
  protected static Log log = LogFactory.getLog(OrderExecutedLine.class);

  String number;
  String opr_id;
  double opr_count;
  String prd_name;
  String prd_type;
  String prd_params;
  String prd_add_params;
  String catalog_num;
  double opr_count_executed;

  public List<String> orderExecutedByDate = new ArrayList<String>();

  public OrderExecutedLine()
  {
  }

  public OrderExecutedLine(OrderExecutedLine orderExecutedLine)
  {
    this.number = orderExecutedLine.getNumber();
    this.opr_id = orderExecutedLine.getOpr_id();
    this.opr_count = orderExecutedLine.getOpr_count();
    this.prd_name = orderExecutedLine.getPrd_name();
    this.prd_type = orderExecutedLine.getPrd_type();
    this.prd_params = orderExecutedLine.getPrd_params();
    this.prd_add_params = orderExecutedLine.getPrd_add_params();
    this.catalog_num = orderExecutedLine.getCatalog_num();
    this.opr_count_executed = orderExecutedLine.getOpr_count_executed();
    
    this.orderExecutedByDate.clear();
    for (String count : orderExecutedLine.getOrderExecutedByDate())
    {
      this.orderExecutedByDate.add(count);
    }
  }

  public String getNumber()
  {
    return number;
  }

  public void setNumber(String number)
  {
    this.number = number;
  }

  public String getOpr_id()
  {
    return opr_id;
  }

  public void setOpr_id(String opr_id)
  {
    this.opr_id = opr_id;
  }

  public double getOpr_count()
  {
    return opr_count;
  }

  public void setOpr_count(double opr_count)
  {
    this.opr_count = opr_count;
  }

  public String getPrd_name()
  {
    return prd_name;
  }

  public void setPrd_name(String prd_name)
  {
    this.prd_name = prd_name;
  }

  public String getPrd_type()
  {
    return prd_type;
  }

  public void setPrd_type(String prd_type)
  {
    this.prd_type = prd_type;
  }

  public String getPrd_params()
  {
    return prd_params;
  }

  public void setPrd_params(String prd_params)
  {
    this.prd_params = prd_params;
  }

  public String getPrd_add_params()
  {
    return prd_add_params;
  }

  public void setPrd_add_params(String prd_add_params)
  {
    this.prd_add_params = prd_add_params;
  }

  public String getCatalog_num()
  {
    return catalog_num;
  }

  public void setCatalog_num(String catalog_num)
  {
    this.catalog_num = catalog_num;
  }

  public String getFullName()
  {
    String produce_full_name = getPrd_name();
    if ( !StringUtil.isEmpty(getPrd_type()) )
    {
      produce_full_name += " " + getPrd_type();
    }
    if ( !StringUtil.isEmpty(getPrd_params()) )
    {
      produce_full_name += " " + getPrd_params();
    }
    if ( !StringUtil.isEmpty(getPrd_add_params()) )
    {
      produce_full_name += " " + getPrd_add_params();
    }

    return produce_full_name;
  }

  public double getOpr_count_executed()
  {
    return opr_count_executed;
  }

  public void setOpr_count_executed(double opr_count_executed)
  {
    this.opr_count_executed = opr_count_executed;
  }

  public List<String> getOrderExecutedByDate()
  {
    return orderExecutedByDate;
  }

  public void setOrderExecutedByDate(List<String> orderExecutedByDate)
  {
    this.orderExecutedByDate = orderExecutedByDate;
  }
}