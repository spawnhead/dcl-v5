package net.sam.dcl.beans;

import java.io.Serializable;

public class ContractorRequestType implements Serializable
{
  String id;
  String name;

  public ContractorRequestType()
  {
  }

  public ContractorRequestType(String id)
  {
    this.id = id;
  }

  public ContractorRequestType(String id, String name)
  {
    this.id = id;
    this.name = name;
  }

  public ContractorRequestType(ContractorRequestType contractorRequestType)
  {
    this.id = contractorRequestType.getId();
    this.name = contractorRequestType.getName();
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

  public String getNameById()
  {
    return ContractorRequest.getRequestTypeName(id, false);
  }
}