package net.sam.dcl.form;

import net.sam.dcl.controller.BaseForm;
import net.sam.dcl.taglib.table.model.impl.HolderImplUsingList;
import net.sam.dcl.util.StringUtil;

public class RoutesForm extends BaseForm
{
  String rut_id;

  HolderImplUsingList grid = new HolderImplUsingList();

  public String getRut_id()
  {
    return rut_id;
  }

  public void setRut_id(String rut_id)
  {
    this.rut_id = rut_id;
  }

  public HolderImplUsingList getGrid()
  {
    return grid;
  }

  public void setGrid(HolderImplUsingList grid)
  {
    this.grid = grid;
  }

  static public class Route
  {
    String rut_id;
    String rut_name;
    String rut_occupied;

    public String getRut_id()
    {
      return rut_id;
    }

    public void setRut_id(String rut_id)
    {
      this.rut_id = rut_id;
    }

    public String getRut_name()
    {
      return rut_name;
    }

    public void setRut_name(String rut_name)
    {
      this.rut_name = rut_name;
    }

    public String getRut_occupied()
    {
      return rut_occupied;
    }

    public void setRut_occupied(String rut_occupied)
    {
      this.rut_occupied = rut_occupied;
    }

    public boolean isOccupied()
    {
      return !StringUtil.isEmpty(getRut_occupied());
    }

  }


}
