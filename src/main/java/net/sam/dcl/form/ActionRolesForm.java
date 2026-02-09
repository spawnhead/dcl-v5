package net.sam.dcl.form;

import net.sam.dcl.controller.BaseForm;
import net.sam.dcl.taglib.table.model.impl.HolderImplUsingList;
import net.sam.dcl.taglib.table.model.impl.GridResult;
import net.sam.dcl.beans.Action;

public class ActionRolesForm extends BaseForm
{
  String act_id;
  Action action = new Action();
  HolderImplUsingList gridOut = new HolderImplUsingList();
  HolderImplUsingList gridIn = new HolderImplUsingList();
  GridResult selected_ids_in = new GridResult(String.class);
  GridResult selected_ids_out = new GridResult(String.class);

  public String getAct_id()
  {
    return act_id;
  }

  public void setAct_id(String act_id)
  {
    this.act_id = act_id;
  }

  public Action getAction()
  {
    return action;
  }

  public void setAction(Action action)
  {
    this.action = action;
  }

  public GridResult getSelected_ids_in()
  {
    return selected_ids_in;
  }

  public void setSelected_ids_in(GridResult selected_ids_in)
  {
    this.selected_ids_in = selected_ids_in;
  }

  public GridResult getSelected_ids_out()
  {
    return selected_ids_out;
  }

  public void setSelected_ids_out(GridResult selected_ids_out)
  {
    this.selected_ids_out = selected_ids_out;
  }

  public HolderImplUsingList getGridOut()
  {
    return gridOut;
  }

  public void setGridOut(HolderImplUsingList gridOut)
  {
    this.gridOut = gridOut;
  }

  public HolderImplUsingList getGridIn()
  {
    return gridIn;
  }

  public void setGridIn(HolderImplUsingList gridIn)
  {
    this.gridIn = gridIn;
  }

  static public class Role
  {
    String rol_id;
    String rol_name;

    public String getRol_id()
    {
      return rol_id;
    }

    public void setRol_id(String rol_id)
    {
      this.rol_id = rol_id;
    }

    public String getRol_name()
    {
      return rol_name;
    }

    public void setRol_name(String rol_name)
    {
      this.rol_name = rol_name;
    }
  }
}