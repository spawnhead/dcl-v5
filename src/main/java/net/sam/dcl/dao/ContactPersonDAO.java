package net.sam.dcl.dao;

import net.sam.dcl.beans.ContactPerson;
import net.sam.dcl.beans.ContactPersonUser;
import net.sam.dcl.beans.Contractor;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.db.VDbConnection;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.util.StringUtil;

import java.util.List;

public class ContactPersonDAO
{
  public static ContactPerson load(IActionContext context, String id) throws Exception
  {
    ContactPerson contactPerson = new ContactPerson(id);
    if (load(context, contactPerson))
    {
      return contactPerson;
    }
    throw new LoadException(contactPerson, id);
  }

  public static boolean load(IActionContext context, ContactPerson contactPerson) throws Exception
  {
    return (DAOUtils.load(context, "contact-person-load", contactPerson, null));
  }

  public static void saveBlock(IActionContext context, ContactPerson contactPerson) throws Exception
  {
    DAOUtils.update(context.getConnection(), context.getSqlResource().get("contact_person-update-block"), contactPerson, null);
  }

  public static List loadContactPersonsForContractor(IActionContext context, Contractor contractor) throws Exception
  {
    return DAOUtils.fillList(context, "select-contact-persons", contractor, ContactPerson.class, null, null);
  }

  public static void saveUsers(IActionContext context, ContactPerson contactPerson) throws Exception
  {
    for (ContactPersonUser contactPersonUser : contactPerson.getUsers())
    {
      contactPersonUser.setCps_id(contactPerson.getCps_id());
    }
    VDbConnection conn = context.getConnection();
    DAOUtils.update(conn, context.getSqlResource().get("delete_contact_person_users"), contactPerson, null);
    for (int i = 0; i < contactPerson.getUsers().size(); i++)
    {
      ContactPersonUser user = contactPerson.getUsers().get(i);
      if ( !StringUtil.isEmpty(user.getUser().getUsr_id()) )
      {
        DAOUtils.update(conn, context.getSqlResource().get("insert_contact_person_user"), user, null);
      }
    }
  }
}
