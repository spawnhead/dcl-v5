package net.sam.dcl.form;

import net.sam.dcl.beans.Seller;
import net.sam.dcl.beans.StuffCategory;
import net.sam.dcl.beans.Contractor;
import net.sam.dcl.taglib.table.model.impl.HolderImplUsingList;
import net.sam.dcl.util.StringUtil;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Date: Aug 6, 2005
 * Time: 5:37:29 PM
 */
public class GoodsCirculationForm extends ReportBaseForm
{
  HolderImplUsingList grid = new HolderImplUsingList();

  String by_quarter;
  String by_month;
  String includeWithNoCirculationAndWithRest;
  String produceName;
  StuffCategory stuffCategory = new StuffCategory();
  Seller seller = new Seller();
  Contractor contractorGoodsCirculation = new Contractor();
  String by_contractor;

  int gridWith;

  public void reset(ActionMapping mapping, HttpServletRequest request)
  {
    setBy_quarter("");
    setBy_month("");
    setIncludeWithNoCirculationAndWithRest("");
    setBy_contractor("");
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

  public String getBy_quarter()
  {
    return by_quarter;
  }

  public void setBy_quarter(String by_quarter)
  {
    this.by_quarter = by_quarter;
  }

	public String getIncludeWithNoCirculationAndWithRest()
	{
		return includeWithNoCirculationAndWithRest;
	}

	public void setIncludeWithNoCirculationAndWithRest(String includeWithNoCirculationAndWithRest)
	{
		this.includeWithNoCirculationAndWithRest = includeWithNoCirculationAndWithRest;
	}

	public String getProduceName()
  {
    return produceName;
  }

  public void setProduceName(String produceName)
  {
    this.produceName = produceName;
  }

  public StuffCategory getStuffCategory()
  {
    return stuffCategory;
  }

  public void setStuffCategory(StuffCategory stuffCategory)
  {
    this.stuffCategory = stuffCategory;
  }

  public Seller getSeller()
  {
    return seller;
  }

  public void setSeller(Seller seller)
  {
    this.seller = seller;
  }

  public Contractor getContractorGoodsCirculation()
  {
    return contractorGoodsCirculation;
  }

  public void setContractorGoodsCirculation(Contractor contractorGoodsCirculation)
  {
    this.contractorGoodsCirculation = contractorGoodsCirculation;
  }

  public String getBy_contractor()
  {
    return by_contractor;
  }

  public void setBy_contractor(String by_contractor)
  {
    this.by_contractor = by_contractor;
  }

  public void setGridWith(int width)
  {
    gridWith = width;  
  }

  public String getGrid_width()
  {
    return "width:" + Integer.toString(gridWith) + "px";
  }

  public boolean isShowContractor()
  {
    return !StringUtil.isEmpty(by_contractor);
  }
}
