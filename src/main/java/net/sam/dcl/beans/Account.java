package net.sam.dcl.beans;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * Date: 31.08.2005
 * Time: 18:53:07
 * To change this template use File | Settings | File Templates.
 */
public class Account implements Serializable
{
  String number;
  String acc_id;
  String ctr_id;
  String acc_name;
  String acc_account;
  Currency currency = new Currency();
  int acc_index;

  public Account()
  {
  }

  public Account(Account account)
  {
    number = account.getNumber();
    acc_id = account.getAcc_id();
    ctr_id = account.getCtr_id();
    acc_name = account.getAcc_name();
    acc_account = account.getAcc_account();
    currency = new Currency(account.getCurrency());
    acc_index = account.getAcc_index();
  }

  public String getNumber()
  {
    return number;
  }

  public void setNumber(String number)
  {
    this.number = number;
  }

  public String getAcc_id()
  {
    return acc_id;
  }

  public void setAcc_id(String acc_id)
  {
    this.acc_id = acc_id;
  }

  public String getCtr_id()
  {
    return ctr_id;
  }

  public void setCtr_id(String ctr_id)
  {
    this.ctr_id = ctr_id;
  }

  public String getAcc_name()
  {
    return acc_name;
  }

  public void setAcc_name(String acc_name)
  {
    this.acc_name = acc_name;
  }

  public String getAcc_account()
  {
    return acc_account;
  }

  public void setAcc_account(String acc_account)
  {
    this.acc_account = acc_account;
  }

  public Currency getCurrency()
  {
    return currency;
  }

  public void setCurrency(Currency currency)
  {
    this.currency = currency;
  }

  public int getAcc_index()
  {
    return acc_index;
  }

  public void setAcc_index(int acc_index)
  {
    this.acc_index = acc_index;
  }
}
