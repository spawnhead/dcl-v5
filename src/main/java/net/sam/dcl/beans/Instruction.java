package net.sam.dcl.beans;

import net.sam.dcl.util.StringUtil;

import java.io.Serializable;
import java.lang.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by IntelliJ IDEA.
 * Date: 31.08.2005
 * Time: 18:53:07
 * To change this template use File | Settings | File Templates.
 */
public class Instruction implements Serializable
{
  protected static Log log = LogFactory.getLog(Instruction.class);

  String is_new_doc;
  String ins_id;

  User createUser = new User();
  User editUser = new User();
  String usr_date_create;
  String usr_date_edit;

  InstructionType type = new InstructionType();
  String ins_number;
  String ins_date_sign;
  String ins_date_from;
  String ins_date_to;
  String ins_concerning;

  public Instruction()
  {
  }

  public Instruction(String ins_id)
  {
    this.ins_id = ins_id;
  }

  public Instruction(Instruction instruction)
  {
    this.ins_id = instruction.getIns_id();
    this.createUser = new User(instruction.getCreateUser());
    this.editUser = new User(instruction.getEditUser());
    this.usr_date_create = instruction.getUsr_date_create();
    this.usr_date_edit = instruction.getUsr_date_edit();

    type = new InstructionType(instruction.getType());
    this.ins_number = instruction.getIns_number();
    this.ins_date_sign = instruction.getIns_date_sign();
    this.ins_date_from = instruction.getIns_date_from();
    this.ins_date_to = instruction.getIns_date_to();
    this.ins_concerning = instruction.getIns_concerning();
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

  public String getIs_new_doc()
  {
    return is_new_doc;
  }

  public void setIs_new_doc(String is_new_doc)
  {
    this.is_new_doc = is_new_doc;
  }

  public String getIns_id()
  {
    return ins_id;
  }

  public void setIns_id(String ins_id)
  {
    this.ins_id = ins_id;
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

  public InstructionType getType()
  {
    return type;
  }

  public void setType(InstructionType type)
  {
    this.type = type;
  }

  public String getIns_number()
  {
    return ins_number;
  }

  public void setIns_number(String ins_number)
  {
    this.ins_number = ins_number;
  }

  public String getIns_date_sign()
  {
    return ins_date_sign;
  }

  public String getIns_date_sign_ts()
  {
    return StringUtil.appDateString2dbDateString(ins_date_sign);
  }

  public void setIns_date_sign(String ins_date_sign)
  {
    this.ins_date_sign = ins_date_sign;
  }

  public String getIns_date_from()
  {
    return ins_date_from;
  }

  public String getIns_date_from_ts()
  {
    return StringUtil.appDateString2dbDateString(ins_date_from);
  }

  public void setIns_date_from(String ins_date_from)
  {
    this.ins_date_from = ins_date_from;
  }

  public String getIns_date_to()
  {
    return ins_date_to;
  }

  public String getIns_date_to_ts()
  {
    return StringUtil.appDateString2dbDateString(ins_date_to);
  }

  public void setIns_date_to(String ins_date_to)
  {
    this.ins_date_to = ins_date_to;
  }

  public String getIns_concerning()
  {
    return ins_concerning;
  }

  public void setIns_concerning(String ins_concerning)
  {
    this.ins_concerning = ins_concerning;
  }
}