package net.sam.dcl.form;

import net.sam.dcl.controller.BaseDispatchValidatorForm;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.beans.User;

/**
 * @author: DG
 * Date: Aug 6, 2005
 * Time: 5:37:29 PM
 */
public class CustomCodeForm extends BaseDispatchValidatorForm
{
  boolean newCus = true;

  String cus_id;

  User createUser = new User();
  User editUser = new User();
  String usr_date_create;
  String usr_date_edit;

  User createUserMain = new User();
  User editUserMain = new User();
  String usr_date_create_main;
  String usr_date_edit_main;

  String cus_code;
  String cus_code_old;
  String cus_description;
  String cus_percent;
  String cus_instant;

  boolean hide_percent = false;
  boolean showCreateEditUsers = false;
  boolean readonly_code = false;

  public boolean isNewCus()
  {
    return newCus;
  }

  public void setNewCus(boolean newCus)
  {
    this.newCus = newCus;
  }

  public String getCus_id()
  {
    return cus_id;
  }

  public void setCus_id(String cus_id)
  {
    this.cus_id = cus_id;
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

  public String getUsr_date_create_formatted()
  {
    return StringUtil.dbDateString2appDateString(usr_date_create);
  }

  public void setUsr_date_create(String usr_date_create)
  {
    this.usr_date_create = usr_date_create;
  }

  public String getUsr_date_edit()
  {
    return usr_date_edit;
  }

  public String getUsr_date_edit_formatted()
  {
    return StringUtil.dbDateString2appDateString(usr_date_edit);
  }

  public void setUsr_date_edit(String usr_date_edit)
  {
    this.usr_date_edit = usr_date_edit;
  }

  public User getCreateUserMain()
  {
    return createUserMain;
  }

  public void setCreateUserMain(User createUserMain)
  {
    this.createUserMain = createUserMain;
  }

  public User getEditUserMain()
  {
    return editUserMain;
  }

  public void setEditUserMain(User editUserMain)
  {
    this.editUserMain = editUserMain;
  }

  public String getUsr_date_create_main()
  {
    return usr_date_create_main;
  }

  public String getUsr_date_create_main_formatted()
  {
    return StringUtil.dbDateString2appDateString(usr_date_create_main);
  }

  public void setUsr_date_create_main(String usr_date_create_main)
  {
    this.usr_date_create_main = usr_date_create_main;
  }

  public String getUsr_date_edit_main()
  {
    return usr_date_edit_main;
  }

  public String getUsr_date_edit_main_formatted()
  {
    return StringUtil.dbDateString2appDateString(usr_date_edit_main);
  }

  public void setUsr_date_edit_main(String usr_date_edit_main)
  {
    this.usr_date_edit_main = usr_date_edit_main;
  }

  public String getCus_code()
  {
    return cus_code;
  }

  public void setCus_code(String cus_code)
  {
    this.cus_code = cus_code;
  }

  public String getCus_code_old()
  {
    return cus_code_old;
  }

  public void setCus_code_old(String cus_code_old)
  {
    this.cus_code_old = cus_code_old;
  }

  public String getCus_description()
  {
    return cus_description;
  }

  public void setCus_description(String cus_description)
  {
    this.cus_description = cus_description;
  }

  public double getCus_percent_double()
  {
    return StringUtil.appCurrencyString2double(getCus_percent()).doubleValue();
  }

  public void setCus_percent_double(double cus_percent_double)
  {
    setCus_percent(StringUtil.double2appCurrencyString(cus_percent_double));
  }

  public String getCus_percent()
  {
    return cus_percent;
  }

  public void setCus_percent(String cus_percent)
  {
    this.cus_percent = cus_percent;
  }

  public String getCus_instant()
  {
    return cus_instant;
  }

  public void setCus_instant(String cus_instant)
  {
    this.cus_instant = cus_instant;
  }

  public String getCus_instant_ts()
  {
    return StringUtil.appDateString2dbTimestampString(cus_instant);
  }

  public boolean isHide_percent()
  {
    return hide_percent;
  }

  public void setHide_percent(boolean hide_percent)
  {
    this.hide_percent = hide_percent;
  }

  public boolean isReadonly_code()
  {
    return readonly_code;
  }

  public void setReadonly_code(boolean readonly_code)
  {
    this.readonly_code = readonly_code;
  }

  public void setShowCreateEditUsers(boolean showCreateEditUsers)
  {
    this.showCreateEditUsers = showCreateEditUsers;
  }

  public boolean isShowCreateEditUsers()
  {
    return hide_percent && showCreateEditUsers;
  }
}
