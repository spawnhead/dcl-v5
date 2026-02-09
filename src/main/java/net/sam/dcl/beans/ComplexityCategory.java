package net.sam.dcl.beans;

import java.io.Serializable;

/**
 * @author: DG
 * Date: Aug 18, 2005
 * Time: 2:31:03 PM
 */
public class ComplexityCategory implements Serializable
{
  String id;
  String name;

  public ComplexityCategory()
  {
  }

  public ComplexityCategory(String id)
  {
    this.id = id;
  }

  public ComplexityCategory(String id, String name)
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