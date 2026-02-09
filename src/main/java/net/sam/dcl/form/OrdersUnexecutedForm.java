package net.sam.dcl.form;

import net.sam.dcl.taglib.table.model.impl.HolderImplUsingList;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Date: Aug 6, 2005
 * Time: 5:37:29 PM
 */
public class OrdersUnexecutedForm extends ReportBaseForm
{
  HolderImplUsingList grid = new HolderImplUsingList();

  String order_by = "";

  String order_by_date;
  String order_by_number;
  String order_by_stf_name;
  String order_by_produce_full_name;
  String order_by_ctn_number;

  public void reset(ActionMapping mapping, HttpServletRequest request)
  {
    setOrder_by_date("");
    setOrder_by_number("");
    setOrder_by_stf_name("");
    setOrder_by_produce_full_name("");
    setOrder_by_ctn_number("");

    super.reset(mapping, request);
  }

  public HolderImplUsingList getGrid()
  {
    return grid;
  }

  public void setGrid(HolderImplUsingList grid)
  {
    this.grid = grid;
  }

  public String getOrder_by()
  {
    return order_by;
  }

  public void setOrder_by(String order_by)
  {
    this.order_by = order_by;
  }

  public String getOrder_by_date()
  {
    return order_by_date;
  }

  public void setOrder_by_date(String order_by_date)
  {
    this.order_by_date = order_by_date;
  }

  public String getOrder_by_number()
  {
    return order_by_number;
  }

  public void setOrder_by_number(String order_by_number)
  {
    this.order_by_number = order_by_number;
  }

  public String getOrder_by_stf_name()
  {
    return order_by_stf_name;
  }

  public void setOrder_by_stf_name(String order_by_stf_name)
  {
    this.order_by_stf_name = order_by_stf_name;
  }

  public String getOrder_by_produce_full_name()
  {
    return order_by_produce_full_name;
  }

  public void setOrder_by_produce_full_name(String order_by_produce_full_name)
  {
    this.order_by_produce_full_name = order_by_produce_full_name;
  }

  public String getOrder_by_ctn_number()
  {
    return order_by_ctn_number;
  }

  public void setOrder_by_ctn_number(String order_by_ctn_number)
  {
    this.order_by_ctn_number = order_by_ctn_number;
  }
}