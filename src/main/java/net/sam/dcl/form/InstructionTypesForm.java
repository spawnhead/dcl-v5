package net.sam.dcl.form;

import net.sam.dcl.controller.BaseForm;
import net.sam.dcl.taglib.table.model.impl.HolderImplUsingList;

public class InstructionTypesForm extends BaseForm
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

  static public class InstructionType
  {
    String ist_id;
    String ist_name;

    public String getIst_id()
    {
      return ist_id;
    }

    public void setIst_id(String ist_id)
    {
      this.ist_id = ist_id;
    }

    public String getIst_name()
    {
      return ist_name;
    }

    public void setIst_name(String ist_name)
    {
      this.ist_name = ist_name;
    }
  }
}