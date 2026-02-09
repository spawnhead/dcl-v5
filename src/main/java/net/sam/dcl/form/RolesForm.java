package net.sam.dcl.form;

import net.sam.dcl.controller.BaseForm;
import net.sam.dcl.taglib.table.model.impl.HolderImplUsingList;

/**
 * @author: DG
 * Date: Aug 6, 2005
 * Time: 5:37:29 PM
 */
public class RolesForm extends BaseForm {
  HolderImplUsingList grid = new HolderImplUsingList();
  public HolderImplUsingList getGrid() {
    return grid;
  }
  public void setGrid(HolderImplUsingList grid) {
    this.grid = grid;
  }
  static public class Role{
    String rol_id;
    String rol_name;
    public String getRol_id() {
      return rol_id;
    }
    public void setRol_id(String rol_id) {
      this.rol_id = rol_id;
    }
    public String getRol_name() {
      return rol_name;
    }
    public void setRol_name(String rol_name) {
      this.rol_name = rol_name;
    }
  }


}
