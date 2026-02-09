package net.sam.dcl.beans;

import java.io.Serializable;

public class BlankType implements Serializable
{
  String id;
  String name;

  public BlankType()
  {
  }

  public BlankType(String id)
  {
    this.id = id;
  }

  public BlankType(String id, String name)
  {
    this.id = id;
    this.name = name;
  }

  public BlankType(BlankType blankType)
  {
    this.id = blankType.getId();
    this.name = blankType.getName();
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