package net.sam.dcl.form;

import net.sam.dcl.controller.BaseForm;
import net.sam.dcl.taglib.table.model.impl.HolderImplUsingList;
import net.sam.dcl.util.StringUtil;

public class ShippingDocTypesForm extends BaseForm
{
  HolderImplUsingList grid = new HolderImplUsingList();

  public HolderImplUsingList getGrid()
  {
    return grid;
  }

  public void setGrid(HolderImplUsingList grid)
  {
    this.grid = grid;
  }

  static public class ShippingDocType
  {
    String sdt_id;
    String sdt_name;
    String occupied;

    public String getSdt_id()
    {
      return sdt_id;
    }

    public void setSdt_id(String sdt_id)
    {
      this.sdt_id = sdt_id;
    }

    public String getSdt_name()
    {
      return sdt_name;
    }

    public void setSdt_name(String sdt_name)
    {
      this.sdt_name = sdt_name;
    }

    public boolean isOccupiedB()
    {
      return !StringUtil.isEmpty((occupied));
    }

    public String getOccupied()
    {
      return occupied;
    }

    public void setOccupied(String occupied)
    {
      this.occupied = occupied;
    }
  }


}