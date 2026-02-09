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
public class SpecificationImportPositionsForm extends BaseDispatchValidatorForm
{
  public static final String ORDER_TARGET = "order";
  public static final String DELIVERY_REQUEST_TARGET = "delivery_request";

  String spi_id;
  String target;
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

  public String getSpi_id()
  {
    return spi_id;
  }

  public void setSpi_id(String spi_id)
  {
    this.spi_id = spi_id;
  }

  public String getTarget()
  {
    return target;
  }

  public void setTarget(String target)
  {
    this.target = target;
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

}
