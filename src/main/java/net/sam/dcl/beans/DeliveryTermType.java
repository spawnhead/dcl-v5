package net.sam.dcl.beans;

import java.io.Serializable;

public class DeliveryTermType implements Serializable
{
  String id;
  String name;

  public DeliveryTermType()
  {
  }

  public DeliveryTermType(String id)
  {
    this.id = id;
  }

  public DeliveryTermType(String id, String name)
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
