package net.sam.dcl.beans;

import java.io.Serializable;

public class Seller implements Serializable
{
  String id;
  String name;
  String usedInOrder;
  String prefixForOrder;
  String isResident;

  public Seller()
  {
  }

  public Seller(String id)
  {
    this.id = id;
  }

  public Seller(String id, String name)
  {
    this.id = id;
    this.name = name;
  }

  public Seller(Seller seller)
  {
    this.id = seller.getId();
    this.name = seller.getName();
    this.usedInOrder = seller.getUsedInOrder();
    this.prefixForOrder = seller.getPrefixForOrder();
    this.isResident = seller.getResident();
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

  // Поддержка маппинга из колонок БД с префиксом SLN_
  public void setSln_id(String id)
  {
    this.id = id;
  }

  public void setSln_name(String name)
  {
    this.name = name;
  }

  public void setSln_used_in_order(String usedInOrder)
  {
    this.usedInOrder = usedInOrder;
  }

  public void setSln_prefix_for_order(String prefixForOrder)
  {
    this.prefixForOrder = prefixForOrder;
  }

  public void setSln_is_resident(String resident)
  {
    this.isResident = resident;
  }

	public String getUsedInOrder()
	{
		return usedInOrder;
	}

	public void setUsedInOrder(String usedInOrder)
	{
		this.usedInOrder = usedInOrder;
	}

	public String getPrefixForOrder()
	{
		return prefixForOrder;
	}

	public void setPrefixForOrder(String prefixForOrder)
	{
		this.prefixForOrder = prefixForOrder;
	}

	public String getResident()
	{
		return isResident;
	}

	public void setResident(String resident)
	{
		isResident = resident;
	}
}