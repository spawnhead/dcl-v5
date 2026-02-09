package net.sam.dcl.beans;

import java.io.Serializable;

public class ShippingDocType implements Serializable
{
  String id;
  String name;

  public ShippingDocType()
  {
  }

  public ShippingDocType(String id)
  {
    this.id = id;
  }

  public ShippingDocType(String id, String name)
  {
    this.id = id;
    this.name = name;
  }

  public ShippingDocType(ShippingDocType shippingDocType)
  {
    this.id = shippingDocType.id;
    this.name = shippingDocType.name;
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
