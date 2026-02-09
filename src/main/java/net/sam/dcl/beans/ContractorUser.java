package net.sam.dcl.beans;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * Date: 31.08.2005
 * Time: 18:53:07
 * To change this template use File | Settings | File Templates.
 */
public class ContractorUser implements Serializable
{
  String number;
  User user = new User();
  String ctr_id;

  public ContractorUser()
  {
  }

  public ContractorUser(String number, User user)
  {
    this.number = number;
    this.user = new User(user);
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

  public String getCtr_id()
  {
    return ctr_id;
  }

  public void setCtr_id(String ctr_id)
  {
    this.ctr_id = ctr_id;
  }
}