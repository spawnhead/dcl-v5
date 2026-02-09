package net.sam.dcl.form;

import net.sam.dcl.controller.BaseDispatchValidatorForm;
import net.sam.dcl.taglib.table.model.impl.HolderImplUsingList;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: DG
 * Date: Aug 6, 2005
 * Time: 5:37:29 PM
 */
public class ProduceCostPositionsForm extends BaseDispatchValidatorForm
{
  public static final String ORDER_TARGET = "order";
  public static final String ASSEMBLE_TARGET = "assemble";
  public static final String DELIVERY_REQUEST_TARGET = "delivery_request";
  public static final String SPECIFICATION_IMPORT_TARGET = "specification_import";

  String prc_id;
  String target;
  String name_filter;
  String ctn_number;
  HolderImplUsingList gridLeft = new HolderImplUsingList();
  HolderImplUsingList gridRight = new HolderImplUsingList();

  public void reset(ActionMapping mapping, HttpServletRequest request)
  {
    //clear selected checkboxes
    if (gridLeft.getDataList() != null)
    {
      for (int i = 0; i < gridLeft.getDataList().size(); i++)
      {
        ImportData.LeftRecord record = (ImportData.LeftRecord) gridLeft.getDataList().get(i);
        record.setSelected_id("");
      }
    }
    if (gridRight.getDataList() != null)
    {
      for (int i = 0; i < gridRight.getDataList().size(); i++)
      {
        ImportData.RightRecord record = (ImportData.RightRecord) gridRight.getDataList().get(i);
        record.setSelected_id("");
      }
    }
    super.reset(mapping, request);
  }

  public void resetGrids()
  {
    gridLeft = new HolderImplUsingList();
    gridRight = new HolderImplUsingList();
  }

  public String getPrc_id()
  {
    return prc_id;
  }

  public void setPrc_id(String prc_id)
  {
    this.prc_id = prc_id;
  }

  public String getTarget()
  {
    return target;
  }

  public void setTarget(String target)
  {
    this.target = target;
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

  public boolean isShowDeliveryRequestLegend()
  {
    return target.equals(DELIVERY_REQUEST_TARGET);
  }
}
