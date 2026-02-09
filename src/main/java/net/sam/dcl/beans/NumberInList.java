package net.sam.dcl.beans;

import java.io.Serializable;

/**
 * @author: DG
 * Date: Aug 18, 2005
 * Time: 2:31:03 PM
 */
public class NumberInList implements Serializable
{
  String number;
  String number_str;

  public NumberInList()
  {
  }

  public NumberInList(String id)
  {
    this.number = id;
  }

  public NumberInList(String id, String name)
  {
    this.number = id;
    this.number_str = name;
  }

  public NumberInList(NumberInList department)
  {
    this.number = department.number;
    this.number_str = department.number_str;
  }

  public String getNumber()
  {
    return number;
  }

  public void setNumber(String number)
  {
    this.number = number;
  }

  public String getNumber_str()
  {
    return number_str;
  }

  public void setNumber_str(String number_str)
  {
    this.number_str = number_str;
  }
}
