package net.sam.dcl.beans;

import java.io.Serializable;

public class PurchasePurpose implements Serializable
{
  String id;
  String name;

  public PurchasePurpose()
  {
  }

  public PurchasePurpose(String id)
  {
    this.id = id;
  }

  public PurchasePurpose(String id, String name)
  {
    this.id = id;
    this.name = name;
  }

  public PurchasePurpose(PurchasePurpose purpose)
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