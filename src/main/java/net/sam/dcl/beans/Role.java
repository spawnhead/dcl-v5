package net.sam.dcl.beans;

import java.io.Serializable;

/**
 * @author: DG
 * Date: Aug 23, 2005
 * Time: 8:42:31 PM
 */
public class Role implements Serializable
{
  String id;
  String name;

  public Role()
  {
  }

  public Role(String id)
  {
    this.id = id;
  }

  public Role(String id, String name)
  {
    this.id = id;
    this.name = name;
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
