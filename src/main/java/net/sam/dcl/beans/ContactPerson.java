package net.sam.dcl.beans;

import net.sam.dcl.controller.ActionContext;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.StrutsUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: DG
 * Date: Aug 23, 2005
 * Time: 8:40:39 PM
 */
public class ContactPerson implements Serializable
{
  protected static Log log = LogFactory.getLog(ContactPerson.class);
  public static String current_contact_person_id = "current_contact_person_id";

  String cps_id;
  String ctr_id;
  String cps_name;
  String cps_position;
  String cps_on_reason;
  String cps_phone;
  String cps_mob_phone;
  String cps_fax;
  String cps_email;
  String cps_block;
  String cps_contract_comment;
  String cps_fire;

  String number;

  List<ContactPersonUser> users = new ArrayList<ContactPersonUser>();

  public ContactPerson()
  {
  }

  public ContactPerson(String cps_id)
  {
    this.cps_id = cps_id;
  }

  public ContactPerson(ContactPerson contactPerson)
  {
    this.cps_id = contactPerson.getCps_id();
    this.ctr_id = contactPerson.getCtr_id();
    this.cps_name = contactPerson.getCps_name();
    this.cps_position = contactPerson.getCps_position();
    this.cps_on_reason = contactPerson.getCps_on_reason();
    this.cps_phone = contactPerson.getCps_phone();
    this.cps_mob_phone = contactPerson.getCps_mob_phone();
    this.cps_fax = contactPerson.getCps_fax();
    this.cps_email = contactPerson.getCps_email();
    this.cps_block = contactPerson.getCps_block();
    this.cps_contract_comment = contactPerson.getCps_contract_comment();
    this.cps_fire = contactPerson.getCps_fire();

    this.number = contactPerson.getNumber();

    users = contactPerson.getUsers();
  }

  public String getCps_id()
  {
    return cps_id;
  }

  public void setCps_id(String cps_id)
  {
    this.cps_id = cps_id;
  }

  public String getCtr_id()
  {
    return ctr_id;
  }

  public void setCtr_id(String ctr_id)
  {
    this.ctr_id = ctr_id;
  }

  public String getCps_name()
  {
    return cps_name;
  }

  public void setCps_name(String cps_name)
  {
    this.cps_name = cps_name;
  }

  public String getCps_position()
  {
    return cps_position;
  }

  public void setCps_position(String cps_position)
  {
    this.cps_position = cps_position;
  }

  public String getCps_on_reason()
  {
    return cps_on_reason;
  }

  public void setCps_on_reason(String cps_on_reason)
  {
    this.cps_on_reason = cps_on_reason;
  }

  public String getCps_phone()
  {
    return cps_phone;
  }

  public void setCps_phone(String cps_phone)
  {
    this.cps_phone = cps_phone;
  }

  public String getCps_mob_phone()
  {
    return cps_mob_phone;
  }

  public void setCps_mob_phone(String cps_mob_phone)
  {
    this.cps_mob_phone = cps_mob_phone;
  }

  public String getCps_fax()
  {
    return cps_fax;
  }

  public void setCps_fax(String cps_fax)
  {
    this.cps_fax = cps_fax;
  }

  public String getCps_email()
  {
    return cps_email;
  }

  public void setCps_email(String cps_email)
  {
    this.cps_email = cps_email;
  }

  public String getCps_block()
  {
    return cps_block;
  }

  public void setCps_block(String cps_block)
  {
    this.cps_block = cps_block;
  }

  public String getCps_contract_comment()
  {
    return cps_contract_comment;
  }

  public void setCps_contract_comment(String cps_contract_comment)
  {
    this.cps_contract_comment = cps_contract_comment;
  }

  public String getCps_fire()
  {
    return cps_fire;
  }

  public void setCps_fire(String cps_fire)
  {
    this.cps_fire = cps_fire;
  }

  public boolean isFired()
  {
    return !StringUtil.isEmpty(getCps_fire());
  }

  public String getNumber()
  {
    return number;
  }

  public void setNumber(String number)
  {
    this.number = number;
  }

  public String getNameFormatted()
  {
    if (!isFired()) return getCps_name();

    String resStr = "";
    IActionContext context = ActionContext.threadInstance();
    try
    {
      resStr = StrutsUtil.getMessage(context, "ContactPerson.fire", getCps_name());
    }
    catch (Exception e)
    {
      log.error(e);
    }

    return resStr;
  }

  public List<ContactPersonUser> getUsers()
  {
    return users;
  }

  public void setUsers(List<ContactPersonUser> users)
  {
    this.users = users;
  }
}
