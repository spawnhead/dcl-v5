package net.sam.dcl.test;

import net.sam.dcl.controller.BaseForm;
import net.sam.dcl.taglib.table.model.impl.HolderImplUsingList;
import net.sam.dcl.taglib.table.model.impl.GridResult;
import net.sam.dcl.taglib.table.model.IGridResult;
import net.sam.dcl.beans.Role;
import net.sam.dcl.beans.Department;

/**
 * @author: DG
 * Date: Aug 6, 2005
 * Time: 5:37:29 PM
 */
public class TestHirGridForm extends BaseForm {
  String rol_id;
  Role role;
  HolderImplUsingList gridOut;
  HolderImplUsingList gridIn;
  IGridResult selected_ids_in;
  IGridResult names_in;
  IGridResult result_in;
  IGridResult selected_ids_out;
  public TestHirGridForm() {
    reset();
  }
  public static class Result{
    String id;
    String name;
    public String getId() {
      return id;
    }
    public void setId(String id) {
      this.id = id;
    }
    public String getName() {
      return name;
    }
    public void setName(String name) {
      this.name = name;
    }
  }
  public void reset(){
    gridOut = new HolderImplUsingList();
    gridIn = new HolderImplUsingList();
    selected_ids_in = new GridResult(String.class);
    names_in = new GridResult(String.class);
    result_in = new GridResult(Result.class);
    selected_ids_out = new GridResult(String.class);
  }

  public String getRol_id() {
    return rol_id;
  }
  public void setRol_id(String rol_id) {
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
  public IGridResult getSelected_ids_in() {
    return selected_ids_in;
  }
  public void setSelected_ids_in(IGridResult selected_ids_in) {
    this.selected_ids_in = selected_ids_in;
  }
  public IGridResult getSelected_ids_out() {
    return selected_ids_out;
  }
  public void setSelected_ids_out(IGridResult selected_ids_out) {
    this.selected_ids_out = selected_ids_out;
  }
  public HolderImplUsingList getGridOut() {
    return gridOut;
  }
  public void setGridOut(HolderImplUsingList gridOut) {
    this.gridOut = gridOut;
  }
  public HolderImplUsingList getGridIn() {
    return gridIn;
  }
  public void setGridIn(HolderImplUsingList gridIn) {
    this.gridIn = gridIn;
  }
  public IGridResult getNames_in() {
    return names_in;
  }
  public void setNames_in(IGridResult names_in) {
    this.names_in = names_in;
  }
  public IGridResult getResult_in() {
    return result_in;
  }
  public void setResult_in(IGridResult result_in) {
    this.result_in = result_in;
  }
  static public class User{
    String role_id;
    String role_name;
    String usr_id;
    String usr_name;
    String usr_surname;
    Department dep = new Department();
    public String getRole_id() {
      return role_id;
    }
    public void setRole_id(String role_id) {
      this.role_id = role_id;
    }
    public String getRole_name() {
      return role_name;
    }
    public void setRole_name(String role_name) {
      this.role_name = role_name;
    }
    public String getUsr_id() {
      return usr_id;
    }
    public void setUsr_id(String usr_id) {
      this.usr_id = usr_id;
    }
    public String getUsr_name() {
      return usr_name;
    }
    public void setUsr_name(String usr_name) {
      this.usr_name = usr_name;
    }
    public String getUsr_surname() {
      return usr_surname;
    }
    public void setUsr_surname(String usr_surname) {
      this.usr_surname = usr_surname;
    }
    public Department getDep() {
      return dep;
    }
    public void setDep(Department dep) {
      this.dep = dep;
    }
  }
}
