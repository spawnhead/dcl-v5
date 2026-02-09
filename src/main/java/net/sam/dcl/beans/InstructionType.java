package net.sam.dcl.beans;

import java.io.Serializable;

public class InstructionType implements Serializable
{
  String id;
  String name;

  public InstructionType()
  {
  }

  public InstructionType(String id)
  {
    this.id = id;
  }

  public InstructionType(String id, String name)
  {
    this.id = id;
    this.name = name;
  }

  public InstructionType(InstructionType type)
  {
    this.id = type.getId();
    this.name = type.getName();
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