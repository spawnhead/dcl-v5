package net.sam.dcl.form;

import net.sam.dcl.controller.BaseDispatchValidatorForm;
import net.sam.dcl.beans.*;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.taglib.table.model.impl.HolderImplUsingList;

/**
 * Date: Aug 6, 2005
 * Time: 5:37:29 PM
 */
public class ContractClosedForm extends BaseDispatchValidatorForm
{
  String is_new_doc;
  String number;

  String ctc_id;

  User createUser = new User();
  User editUser = new User();
  String usr_date_create;
  String usr_date_edit;

  String ctc_date;
  String ctc_number;
  String ctc_block;

  boolean formReadOnly = false;

  HolderImplUsingList grid = new HolderImplUsingList();

  public String getIs_new_doc()
  {
    return is_new_doc;
  }

  public void setIs_new_doc(String is_new_doc)
  {
    this.is_new_doc = is_new_doc;
  }

  public String getNumber()
  {
    return number;
  }

  public void setNumber(String number)
  {
    this.number = number;
  }

  public String getCtc_id()
  {
    return ctc_id;
  }

  public void setCtc_id(String ctc_id)
  {
    this.ctc_id = ctc_id;
  }

  public User getCreateUser()
  {
    return createUser;
  }

  public void setCreateUser(User createUser)
  {
    this.createUser = createUser;
  }

  public User getEditUser()
  {
    return editUser;
  }

  public void setEditUser(User editUser)
  {
    this.editUser = editUser;
  }

  public String getUsr_date_create()
  {
    return usr_date_create;
  }

  public void setUsr_date_create(String usr_date_create)
  {
    this.usr_date_create = usr_date_create;
  }

  public String getUsr_date_edit()
  {
    return usr_date_edit;
  }

  public void setUsr_date_edit(String usr_date_edit)
  {
    this.usr_date_edit = usr_date_edit;
  }

  public String getCtc_date()
  {
    return ctc_date;
  }

  public String getCtc_date_ts()
  {
    return StringUtil.appDateString2dbDateString(ctc_date);
  }

  public void setCtc_date(String ctc_date)
  {
    this.ctc_date = ctc_date;
  }

  public String getCtc_number()
  {
    return ctc_number;
  }

  public void setCtc_number(String ctc_number)
  {
    this.ctc_number = ctc_number;
  }

  public String getCtc_block()
  {
    return ctc_block;
  }

  public void setCtc_block(String ctc_block)
  {
    this.ctc_block = ctc_block;
  }

  public boolean isFormReadOnly()
  {
    return formReadOnly;
  }

  public void setFormReadOnly(boolean formReadOnly)
  {
    this.formReadOnly = formReadOnly;
  }

  public HolderImplUsingList getGrid()
  {
    return grid;
  }

  public void setGrid(HolderImplUsingList grid)
  {
    this.grid = grid;
  }
}
