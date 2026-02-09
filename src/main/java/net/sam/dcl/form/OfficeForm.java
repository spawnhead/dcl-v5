package net.sam.dcl.form;

import net.sam.dcl.beans.*;
import net.sam.dcl.controller.BaseDispatchValidatorForm;
import net.sam.dcl.taglib.table.model.impl.HolderImplUsingList;

/**
 * Date: Aug 6, 2005
 * Time: 5:37:29 PM
 */
public class OfficeForm extends BaseDispatchValidatorForm
{
  User user = new User();
  Department department = new Department();

  boolean readOnlyForManager;
  boolean showForChiefDep;
  int refreshTimeout;

  HolderImplUsingList gridMessages = new HolderImplUsingList();

  public User getUser()
  {
    return user;
  }

  public void setUser(User user)
  {
    this.user = user;
  }

  public Department getDepartment()
  {
    return department;
  }

  public void setDepartment(Department department)
  {
    this.department = department;
  }

  public boolean isReadOnlyForManager()
  {
    return readOnlyForManager;
  }

  public void setReadOnlyForManager(boolean readOnlyForManager)
  {
    this.readOnlyForManager = readOnlyForManager;
  }

  public boolean isShowForChiefDep()
  {
    return showForChiefDep;
  }

  public void setShowForChiefDep(boolean showForChiefDep)
  {
    this.showForChiefDep = showForChiefDep;
  }

  public HolderImplUsingList getGridMessages()
  {
    return gridMessages;
  }

  public void setGridMessages(HolderImplUsingList gridMessages)
  {
    this.gridMessages = gridMessages;
  }

  public int getRefreshTimeout()
  {
    return refreshTimeout;
  }

  public void setRefreshTimeout(int refreshTimeout)
  {
    this.refreshTimeout = refreshTimeout;
  }
}