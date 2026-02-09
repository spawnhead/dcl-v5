package net.sam.dcl.beans;

import java.io.Serializable;

/**
 * @author: DG
 * Date: Aug 18, 2005
 * Time: 2:31:03 PM
 */
public class Department implements Serializable
{
  String id;
  String name;

  public Department()
  {
  }

  public Department(String id)
  {
    this.id = id;
  }

  public Department(String id, String name)
  {
    this.id = id;
    this.name = name;
  }

  public Department(Department department)
  {
    this.id = department.id;
    this.name = department.name;
  }

  public String getId()
  {
    return id;
  }

  public void setId(String id)
  {
    this.id = id;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }
}
