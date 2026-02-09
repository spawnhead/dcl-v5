package net.sam.dcl.form;

import net.sam.dcl.controller.DispatchActionHelper;
import net.sam.dcl.taglib.table.model.impl.HolderImplUsingList;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Date: Aug 6, 2005
 * Time: 5:37:29 PM
 */
public class OrdersLogisticsForm extends ReportBaseForm
{
  public static String selectAll = "*";
  public static String selectDistinct = "distinct *";

  HolderImplUsingList grid = new HolderImplUsingList();

  String select_list = selectAll;

  String include_empty_dates;
  String by_product;
  String view_weight;

  public void reset(ActionMapping mapping, HttpServletRequest request)
  {
		if (!"showGrid".equals(DispatchActionHelper.getDispatchMethodName(mapping, request)))
    {
			setInclude_empty_dates("");
			setBy_product("");
		}
    setView_weight("");
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

  public String getSelect_list()
  {
    return select_list;
  }

  public void setSelect_list(String select_list)
  {
    this.select_list = select_list;
  }

  public String getInclude_empty_dates()
  {
    return include_empty_dates;
  }

  public void setInclude_empty_dates(String include_empty_dates)
  {
    this.include_empty_dates = include_empty_dates;
  }

  public String getBy_product()
  {
    return by_product;
  }

  public void setBy_product(String by_product)
  {
    this.by_product = by_product;
  }

  public String getView_weight()
  {
    return view_weight;
  }

  public void setView_weight(String view_weight)
  {
    this.view_weight = view_weight;
  }
}
