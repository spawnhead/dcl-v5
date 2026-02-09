package net.sam.dcl.form;

import net.sam.dcl.controller.BaseDispatchValidatorForm;
import net.sam.dcl.beans.*;
import net.sam.dcl.taglib.table.model.impl.HolderImplUsingList;
import net.sam.dcl.util.StringUtil;


import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: DG
 * Date: Aug 6, 2005
 * Time: 5:37:29 PM
 */
public class ShippingPositionsForm extends BaseDispatchValidatorForm
{
  User manager = new User();
  String name_filter;
  String ctn_number;
  String number_1c;
  Route route = new Route();
  String prc_date_min;
	Contractor contractorForPositions = new Contractor();
  String showGroupsExpanded = null;

  HolderImplUsingList gridLeft = new HolderImplUsingList();
  HolderImplUsingList gridRight = new HolderImplUsingList();

  public void reset(ActionMapping mapping, HttpServletRequest request)
  {
    //clear selected checkboxes
    if (gridLeft.getDataList() != null)
    {
      for (int i = 0; i < gridLeft.getDataList().size(); i++)
      {
        ShippingData.LeftRecord record = (ShippingData.LeftRecord) gridLeft.getDataList().get(i);
        record.setSelected_id("");
      }
    }
    if (gridRight.getDataList() != null)
    {
      for (int i = 0; i < gridRight.getDataList().size(); i++)
      {
        net.sam.dcl.form.ShippingData.RightRecord record = (net.sam.dcl.form.ShippingData.RightRecord) gridRight.getDataList().get(i);
        record.setSelected_id("");
      }
    }
    showGroupsExpanded = null;
    super.reset(mapping, request);
  }

  public void resetGrids()
  {
    gridLeft = new HolderImplUsingList();
    gridRight = new HolderImplUsingList();
  }

  public User getManager()
  {
    return manager;
  }

  public void setManager(User manager)
  {
    this.manager = manager;
  }

  public String getName_filter()
  {
    return name_filter;
  }

  public void setName_filter(String name_filter)
  {
    this.name_filter = name_filter;
  }

  public String getCtn_number()
  {
    return ctn_number;
  }

  public void setCtn_number(String ctn_number)
  {
    this.ctn_number = ctn_number;
  }

  public String getNumber_1c()
  {
    return number_1c;
  }

  public void setNumber_1c(String number_1c)
  {
    this.number_1c = number_1c;
  }

  public Route getRoute()
  {
    return route;
  }

  public void setRoute(Route route)
  {
    this.route = route;
  }

  public String getPrc_date_min()
  {
    return prc_date_min;
  }

  public String getPrc_date_min_ts()
  {
    return StringUtil.appDateString2dbDateString(prc_date_min);
  }

  public void setPrc_date_min(String prc_date_min)
  {
    this.prc_date_min = prc_date_min;
  }

	public Contractor getContractorForPositions()
	{
		return contractorForPositions;
	}

	public void setContractorForPositions(Contractor contractorForPositions)
	{
		this.contractorForPositions = contractorForPositions;
	}

	public HolderImplUsingList getGridLeft()
  {
    return gridLeft;
  }

  public void setGridLeft(HolderImplUsingList gridLeft)
  {
    this.gridLeft = gridLeft;
  }

  public HolderImplUsingList getGridRight()
  {
    return gridRight;
  }

  public void setGridRight(HolderImplUsingList gridRight)
  {
    this.gridRight = gridRight;
  }

  public String getShowGroupsExpanded()
  {
    return showGroupsExpanded;
  }

  public void setShowGroupsExpanded(String showGroupsExpanded)
  {
    this.showGroupsExpanded = showGroupsExpanded;
  }
}
