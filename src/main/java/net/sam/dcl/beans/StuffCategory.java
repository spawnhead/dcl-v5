package net.sam.dcl.beans;

import java.io.Serializable;

public class StuffCategory implements Serializable
{
  String id;
  String name;
  String showInMontage;

  public StuffCategory()
  {
  }

  public StuffCategory(String id)
  {
    this.id = id;
  }

  public StuffCategory(String id, String name)
  {
    this.id = id;
    this.name = name;
  }

  public StuffCategory(StuffCategory stuffCategory)
  {
    this.id = stuffCategory.id;
    this.name = stuffCategory.name;
    this.showInMontage = stuffCategory.showInMontage;
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

  public String getShowInMontage()
  {
    return showInMontage;
  }

  public void setShowInMontage(String showInMontage)
  {
    this.showInMontage = showInMontage;
  }
}
