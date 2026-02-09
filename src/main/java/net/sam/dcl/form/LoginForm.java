package net.sam.dcl.form;

import net.sam.dcl.controller.BaseDispatchValidatorForm;
import net.sam.dcl.beans.Department;

/**
 * Date: Aug 6, 2005
 * Time: 5:37:29 PM
 */
public class LoginForm extends BaseDispatchValidatorForm
{
  String usr_id;
  String usr_code;
  String usr_name;
  String usr_surname;
  String usr_login;
  String usr_passwd;
  String usr_position;
  Department department = new Department();
  String usr_phone;
  String usr_block;
  String usr_no_login;
  String usr_chief_dep;
  String usr_fax;
  String usr_email;
  String usr_local_entry;
  String usr_internet_entry;
  String userScreenWith;

  public String getUsr_id()
  {
    return usr_id;
  }

  public void setUsr_id(String usr_id)
  {
    this.usr_id = usr_id;
  }

  public String getUsr_surname()
  {
    return usr_surname;
  }

  public void setUsr_surname(String usr_surname)
  {
    this.usr_surname = usr_surname;
  }

  public String getUsr_passwd()
  {
    return usr_passwd;
  }

  public void setUsr_passwd(String usr_passwd)
  {
    this.usr_passwd = usr_passwd;
  }

  public String getUsr_position()
  {
    return usr_position;
  }

  public void setUsr_position(String usr_position)
  {
    this.usr_position = usr_position;
  }

  public String getUsr_phone()
  {
    return usr_phone;
  }

  public void setUsr_phone(String usr_phone)
  {
    this.usr_phone = usr_phone;
  }

  public String getUsr_block()
  {
    return usr_block;
  }

  public void setUsr_block(String usr_block)
  {
    this.usr_block = usr_block;
  }

  public String getUsr_no_login()
  {
    return usr_no_login;
  }

  public void setUsr_no_login(String usr_no_login)
  {
    this.usr_no_login = usr_no_login;
  }

  public String getUsr_chief_dep()
  {
    return usr_chief_dep;
  }

  public void setUsr_chief_dep(String usr_chief_dep)
  {
    this.usr_chief_dep = usr_chief_dep;
  }

  public String getUsr_name()
  {
    return usr_name;
  }

  public void setUsr_name(String usr_name)
  {
    this.usr_name = usr_name;
  }

  public String getUsr_login()
  {
    return usr_login;
  }

  public void setUsr_login(String usr_login)
  {
    this.usr_login = usr_login;
  }

  public String getUsr_code()
  {
    return usr_code;
  }

  public void setUsr_code(String usr_code)
  {
    this.usr_code = usr_code;
  }

  public String getUsr_fax()
  {
    return usr_fax;
  }

  public void setUsr_fax(String usr_fax)
  {
    this.usr_fax = usr_fax;
  }

  public String getUsr_email()
  {
    return usr_email;
  }

  public void setUsr_email(String usr_email)
  {
    this.usr_email = usr_email;
  }

  public Department getDepartment()
  {
    return department;
  }

  public void setDepartment(Department department)
  {
    this.department = department;
  }

  public String getUsr_local_entry()
  {
    return usr_local_entry;
  }

  public void setUsr_local_entry(String usr_local_entry)
  {
    this.usr_local_entry = usr_local_entry;
  }

  public String getUsr_internet_entry()
  {
    return usr_internet_entry;
  }

  public void setUsr_internet_entry(String usr_internet_entry)
  {
    this.usr_internet_entry = usr_internet_entry;
  }

  public String getUserScreenWith()
  {
    return userScreenWith;
  }

  public void setUserScreenWith(String userScreenWith)
  {
    this.userScreenWith = userScreenWith;
  }
}
