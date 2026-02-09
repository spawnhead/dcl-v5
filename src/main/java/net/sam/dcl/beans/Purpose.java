package net.sam.dcl.beans;

import java.io.Serializable;

public class Purpose implements Serializable
{
  String id;
  String name;

  public Purpose()
  {
  }

  public Purpose(String id)
  {
    this.id = id;
  }

  public Purpose(String id, String name)
  {
    this.id = id;
    this.name = name;
  }

  public Purpose(Purpose purpose)
  {
    id = purpose.getId();
    name = purpose.getName();
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
