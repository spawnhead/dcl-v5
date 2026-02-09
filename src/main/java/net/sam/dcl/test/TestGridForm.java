package net.sam.dcl.test;

import net.sam.dcl.taglib.table.model.impl.PageableHolderImplUsingList;
import net.sam.dcl.taglib.table.model.impl.HolderImplUsingList;
import net.sam.dcl.controller.BaseForm;

/**
 * @author: DG
 * Date: Aug 7, 2005
 * Time: 2:46:55 PM
 */
public class TestGridForm extends BaseForm {

  String test1;
  String test2;
  public String getTest1() {
    return test1;
  }
  public void setTest1(String test1) {
    this.test1 = test1;
  }
  public String getTest2() {
    return test2;
  }
  public void setTest2(String test2) {
    this.test2 = test2;
  }
  /**
   * Grid
   */

  HolderImplUsingList gridCtrl1 = new HolderImplUsingList();
  PageableHolderImplUsingList gridCtrl2 = new PageableHolderImplUsingList();
  public HolderImplUsingList getGridCtrl1() {
    return gridCtrl1;
  }
  public void setGridCtrl1(HolderImplUsingList gridCtrl1) {
    this.gridCtrl1 = gridCtrl1;
  }
  public PageableHolderImplUsingList getGridCtrl2() {
    return gridCtrl2;
  }
  public void setGridCtrl2(PageableHolderImplUsingList gridCtrl2) {
    this.gridCtrl2 = gridCtrl2;
  }
  public void setTest(String a1) {

  }
  public void setTest(String a1, String a2) {

  }
  public void setTest(String a1, String a2, String a3) {

  }

  static public class TestSim{
    private String name, id;

    public String getName() {
      return name;
    }
    public void setName(String name) {
      this.name = name;
    }
    public String getId() {
      return id;
    }
    public void setId(String id) {
      this.id = id;
    }
  }

}
