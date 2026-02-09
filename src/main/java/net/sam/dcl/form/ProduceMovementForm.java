package net.sam.dcl.form;

import net.sam.dcl.controller.BaseDispatchValidatorForm;
import net.sam.dcl.taglib.table.model.impl.HolderImplUsingList;
import net.sam.dcl.dbo.DboProduce;
import net.sam.dcl.util.StringUtil;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Date: Aug 6, 2005
 * Time: 5:37:29 PM
 */
public class ProduceMovementForm extends BaseDispatchValidatorForm
{
  DboProduce produce = new DboProduce();

  String divide_into_chain;
  boolean showLegend;
  boolean showLegendInput;
  boolean showLegendOrder;
  boolean showLegendTransit;

  HolderImplUsingList grid = new HolderImplUsingList();

  public void reset(ActionMapping mapping, HttpServletRequest request)
  {
    setDivide_into_chain("");
    super.reset(mapping, request);
  }

  public DboProduce getProduce()
  {
    return produce;
  }

  public void setProduce(DboProduce produce)
  {
    this.produce = produce;
  }

  public String getProduceFullName()
  {
    return getProduce().getFullName();
  }

  public String getDivide_into_chain()
  {
    return divide_into_chain;
  }

  public void setDivide_into_chain(String divide_into_chain)
  {
    this.divide_into_chain = divide_into_chain;
  }

  public boolean isShowLegend()
  {
    return showLegend;
  }

  public void setShowLegend(boolean showLegend)
  {
    this.showLegend = showLegend;
  }

  public boolean isShowLegendInput()
  {
    return showLegendInput;
  }

  public void setShowLegendInput(boolean showLegendInput)
  {
    this.showLegendInput = showLegendInput;
  }

  public boolean isShowLegendOrder()
  {
    return showLegendOrder;
  }

  public void setShowLegendOrder(boolean showLegendOrder)
  {
    this.showLegendOrder = showLegendOrder;
  }

  public boolean isShowLegendTransit() { return showLegendTransit; }

  public void setShowLegendTransit(boolean showLegendTransit) {
    this.showLegendTransit = showLegendTransit;
  }

  public String getFormStyleId()
  {
    if (StringUtil.isEmpty(getDivide_into_chain()))
    {
      return "striped-form";
    }

    return "";
  }

  public HolderImplUsingList getGrid()
  {
    return grid;
  }

  public void setGrid(HolderImplUsingList grid)
  {
    this.grid = grid;
  }
}
