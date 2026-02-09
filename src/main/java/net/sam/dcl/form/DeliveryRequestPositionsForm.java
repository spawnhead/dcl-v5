package net.sam.dcl.form;

import net.sam.dcl.controller.BaseDispatchValidatorForm;
import net.sam.dcl.taglib.table.model.impl.HolderImplUsingList;
import net.sam.dcl.beans.User;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

public class DeliveryRequestPositionsForm extends BaseDispatchValidatorForm
{
  public static final String ORDER_TARGET = "order";
  public static final String ASSEMBLE_TARGET = "assemble";
  public static final String SPECIFICATION_IMPORT_TARGET = "specification_import";

  String dlr_id;
  String target;
  String ord_number;
  String name_filter;
  User manager = new User();
  HolderImplUsingList gridLeft = new HolderImplUsingList();
  HolderImplUsingList gridRight = new HolderImplUsingList();

  boolean needReload = true;

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

  public String getDlr_id()
  {
    return dlr_id;
  }

  public void setDlr_id(String dlr_id)
  {
    this.dlr_id = dlr_id;
  }

  public String getTarget()
  {
    return target;
  }

  public void setTarget(String target)
  {
    this.target = target;
  }

  public String getOrd_number()
  {
    return ord_number;
  }

  public void setOrd_number(String ord_number)
  {
    this.ord_number = ord_number;
  }

  public String getName_filter()
  {
    return name_filter;
  }

  public void setName_filter(String name_filter)
  {
    this.name_filter = name_filter;
  }

  public User getManager()
  {
    return manager;
  }

  public void setManager(User manager)
  {
    this.manager = manager;
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

  public boolean isNeedReload()
  {
    return needReload;
  }

  public void setNeedReload(boolean needReload)
  {
    this.needReload = needReload;
  }
}
