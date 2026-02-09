package net.sam.dcl.form;

import net.sam.dcl.controller.BaseForm;
import net.sam.dcl.taglib.table.model.impl.HolderImplUsingList;

public class PurposesForm extends BaseForm
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

  static public class Purpose
  {
    String prs_id;
    String prs_name;

    public String getPrs_id()
    {
      return prs_id;
    }

    public void setPrs_id(String prs_id)
    {
      this.prs_id = prs_id;
    }

    public String getPrs_name()
    {
      return prs_name;
    }

    public void setPrs_name(String prs_name)
    {
      this.prs_name = prs_name;
    }
  }


}
