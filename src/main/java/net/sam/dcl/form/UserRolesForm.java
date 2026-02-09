package net.sam.dcl.form;

import net.sam.dcl.controller.BaseForm;
import net.sam.dcl.taglib.table.model.impl.HolderImplUsingList;
import net.sam.dcl.taglib.table.model.impl.GridResult;
import net.sam.dcl.beans.Role;

/**
 * @author: DG
 * Date: Aug 6, 2005
 * Time: 5:37:29 PM
 */
public class UserRolesForm extends BaseForm
{
  String rol_id;
  Role role = new Role();
  HolderImplUsingList gridOut = new HolderImplUsingList();
  HolderImplUsingList gridIn = new HolderImplUsingList();
  GridResult selected_ids_in = new GridResult(String.class);
  GridResult selected_ids_out = new GridResult(String.class);

  public String getRol_id()
  {
    return rol_id;
  }

  public void setRol_id(String rol_id)
  {
    this.rol_id = rol_id;
  }

  public Role getRole()
  {
    return role;
  }

  public void setRole(Role role)
  {
    this.role = role;
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

  static public class User
  {
    String usr_id;
    String usr_name;
    String usr_surname;

    public String getUsr_id()
    {
      return usr_id;
    }

    public void setUsr_id(String usr_id)
    {
      this.usr_id = usr_id;
    }

    public String getUsr_name()
    {
      return usr_name;
    }

    public void setUsr_name(String usr_name)
    {
      this.usr_name = usr_name;
    }

    public String getUsr_surname()
    {
      return usr_surname;
    }

    public void setUsr_surname(String usr_surname)
    {
      this.usr_surname = usr_surname;
    }
  }
}
