package net.sam.dcl.beans;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * Date: 31.08.2005
 * Time: 18:53:07
 * To change this template use File | Settings | File Templates.
 */
public class ContactPersonUser implements Serializable
{
  String number;
  User user = new User();
  String cps_id;

  public ContactPersonUser()
  {
  }

  public ContactPersonUser(String number, User user)
  {
    this.number = number;
    this.user = new User(user);
  }

  public ContactPersonUser(ContactPersonUser contactPersonUser)
  {
    this.cps_id = contactPersonUser.getCps_id();
    this.number = contactPersonUser.getNumber();
    this.user = new User(contactPersonUser.getUser());
  }

  public String getNumber()
  {
    return number;
  }

  public void setNumber(String number)
  {
    this.number = number;
  }

  public User getUser()
  {
    return user;
  }

  public void setUser(User user)
  {
    this.user = user;
  }

  public String getCps_id()
  {
    return cps_id;
  }

  public void setCps_id(String cps_id)
  {
    this.cps_id = cps_id;
  }
}