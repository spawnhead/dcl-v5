package net.sam.dcl.form;

import net.sam.dcl.taglib.table.model.impl.HolderImplUsingList;
import net.sam.dcl.util.StringUtil;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Date: Aug 6, 2005
 * Time: 5:37:29 PM
 */
public class ProduceCostReportForm extends ReportBaseForm
{
  HolderImplUsingList grid = new HolderImplUsingList();

  String by_month;
  String only_block;

  public void reset(ActionMapping mapping, HttpServletRequest request)
  {
    setBy_month("");
    setOnly_block("");
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

  public String getBy_month()
  {
    return by_month;
  }

  public void setBy_month(String by_month)
  {
    this.by_month = by_month;
  }

  public boolean isShowNumberRoute()
  {
    return StringUtil.isEmpty(by_month);
  }

  public String getOnly_block()
  {
    return only_block;
  }

  public void setOnly_block(String only_block)
  {
    this.only_block = only_block;
  }
}