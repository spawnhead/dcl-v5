package net.sam.dcl.form;

import net.sam.dcl.taglib.table.model.impl.HolderImplUsingList;
import net.sam.dcl.beans.Department;
import net.sam.dcl.beans.StuffCategory;
import net.sam.dcl.beans.Contractor;

/**
 * Date: Aug 6, 2005
 * Time: 5:37:29 PM
 */
public class OrdersStatisticsForm extends ReportBaseForm
{
  HolderImplUsingList grid = new HolderImplUsingList();

  Department department = new Department();
  StuffCategory stuffCategory = new StuffCategory();
  Contractor contractor = new Contractor();
  Contractor contractor_for = new Contractor();

  public HolderImplUsingList getGrid()
  {
    return grid;
  }

  public void setGrid(HolderImplUsingList grid)
  {
    this.grid = grid;
  }

  public Department getDepartment()
  {
    return department;
  }

  public void setDepartment(Department department)
  {
    this.department = department;
  }

  public StuffCategory getStuffCategory()
  {
    return stuffCategory;
  }

  public void setStuffCategory(StuffCategory stuffCategory)
  {
    this.stuffCategory = stuffCategory;
  }

  public Contractor getContractor()
  {
    return contractor;
  }

  public void setContractor(Contractor contractor)
  {
    this.contractor = contractor;
  }

  public Contractor getContractor_for()
  {
    return contractor_for;
  }

  public void setContractor_for(Contractor contractor_for)
  {
    this.contractor_for = contractor_for;
  }
}
