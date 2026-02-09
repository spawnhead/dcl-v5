package net.sam.dcl.form;

import net.sam.dcl.controller.BaseForm;
import net.sam.dcl.taglib.table.model.impl.HolderImplUsingList;

/**
 * @author: DG
 * Date: Aug 6, 2005
 * Time: 5:37:29 PM
 */
public class DepartmentsForm extends BaseForm {
  HolderImplUsingList grid = new HolderImplUsingList();
  public HolderImplUsingList getGrid() {
    return grid;
  }
  public void setGrid(HolderImplUsingList grid) {
    this.grid = grid;
  }
  static public class Department{
    String dep_id;
    String dep_name;
    public String getDep_id() {
      return dep_id;
    }
    public void setDep_id(String dep_id) {
      this.dep_id = dep_id;
    }
    public String getDep_name() {
      return dep_name;
    }
    public void setDep_name(String dep_name) {
      this.dep_name = dep_name;
    }
  }


}
