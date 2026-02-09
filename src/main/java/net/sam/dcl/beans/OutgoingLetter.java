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
public class OutgoingLetter implements Serializable
{
  protected static Log log = LogFactory.getLog(OutgoingLetter.class);
  String is_new_doc;

  String otl_id;

  User createUser = new User();
  User editUser = new User();
  String usr_date_create;
  String usr_date_edit;

  String otl_number;
  String otl_date;
  Contractor contractor = new Contractor();
  ContactPerson contactPerson = new ContactPerson();
  Seller seller = new Seller();
  String otl_comment;

  public OutgoingLetter()
  {
  }

  public OutgoingLetter(String otl_id)
  {
    this.otl_id = otl_id;
  }

  public OutgoingLetter(OutgoingLetter outgoingLetter)
  {
    this.otl_id = outgoingLetter.getOtl_id();
    this.createUser = new User(outgoingLetter.getCreateUser());
    this.editUser = new User(outgoingLetter.getEditUser());
    this.usr_date_create = outgoingLetter.getUsr_date_create();
    this.usr_date_edit = outgoingLetter.getUsr_date_edit();

    this.otl_number = outgoingLetter.getOtl_number();
    this.otl_date = outgoingLetter.getOtl_date();
    this.contractor = outgoingLetter.getContractor();
    this.contactPerson = outgoingLetter.getContactPerson();
    this.seller = outgoingLetter.getSeller();
    this.otl_comment = outgoingLetter.getOtl_comment();
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

  public String getOtl_id()
  {
    return otl_id;
  }

  public void setOtl_id(String otl_id)
  {
    this.otl_id = otl_id;
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

  public String getOtl_number()
  {
    return otl_number;
  }

  public void setOtl_number(String otl_number)
  {
    this.otl_number = otl_number;
  }

  public String getOtl_date()
  {
    return otl_date;
  }

  public String getOtl_date_formatted()
  {
    return StringUtil.dbDateString2appDateString(otl_date);
  }

  public String getOtl_date_ts()
  {
    return StringUtil.appDateString2dbDateString(otl_date);
  }

  public void setOtl_date(String otl_date)
  {
    this.otl_date = otl_date;
  }

  public Contractor getContractor()
  {
    return contractor;
  }

  public void setContractor(Contractor contractor)
  {
    this.contractor = contractor;
  }

  public ContactPerson getContactPerson()
  {
    return contactPerson;
  }

  public void setContactPerson(ContactPerson contactPerson)
  {
    this.contactPerson = contactPerson;
  }

	public Seller getSeller()
	{
		return seller;
	}

	public void setSeller(Seller seller)
	{
		this.seller = seller;
	}

	public String getOtl_comment()
  {
    return otl_comment;
  }

  public void setOtl_comment(String otl_comment)
  {
    this.otl_comment = otl_comment;
  }
}