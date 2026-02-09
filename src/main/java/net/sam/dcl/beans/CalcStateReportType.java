package net.sam.dcl.beans;

import java.io.Serializable;

public class CalcStateReportType implements Serializable
{
  String id;
  String name;

  public CalcStateReportType()
  {
  }

  public CalcStateReportType(String id)
  {
    this.id = id;
  }

  public CalcStateReportType(String id, String name)
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
