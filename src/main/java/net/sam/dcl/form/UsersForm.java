package net.sam.dcl.form;

import net.sam.dcl.taglib.table.model.impl.HolderImplUsingList;

/**
 * @author: DG
 * Date: Aug 6, 2005
 * Time: 5:37:29 PM
 */
public class UsersForm extends JournalForm
{
  String usr_id;
  String block;
  HolderImplUsingList grid = new HolderImplUsingList();

  public String getUsr_id()
  {
    return usr_id;
  }

  public void setUsr_id(String usr_id)
  {
    this.usr_id = usr_id;
  }

  public String getBlock()
  {
    return block;
  }

  public void setBlock(String block)
  {
    this.block = block;
  }

  public HolderImplUsingList getGrid()
  {
    return grid;
  }

  public void setGrid(HolderImplUsingList grid)
  {
    this.grid = grid;
  }

  static public class User
  {
    String usr_id;
    String usr_code;
    String usr_name;
    String usr_surname;
    String usr_login;
    String usr_position;
    String usr_department;
    String usr_phone;
    String usr_block;
    String usr_fax;
    String usr_email;
    String roles;

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

    public String getMsg_check_block()
    {
      if ("1".equals(usr_block))
        return "ask_unblock";
      else
        return "ask_block";
    }

    public String getUsr_code()
    {
      return usr_code;
    }

    public void setUsr_code(String usr_code)
    {
      this.usr_code = usr_code;
    }

    public String getUsr_name()
    {
      return usr_name;
    }

    public void setUsr_name(String usr_name)
    {
      this.usr_name = usr_name;
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

    public String getUsr_login()
    {
      return usr_login;
    }

    public void setUsr_login(String usr_login)
    {
      this.usr_login = usr_login;
    }

    public String getUsr_department()
    {
      return usr_department;
    }

    public void setUsr_department(String usr_department)
    {
      this.usr_department = usr_department;
    }

    public String getRoles()
    {
      return roles;
    }

    public void setRoles(String roles)
    {
      this.roles = roles;
    }
  }
}
