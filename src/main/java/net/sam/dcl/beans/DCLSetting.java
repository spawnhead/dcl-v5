package net.sam.dcl.beans;

import java.io.Serializable;

/**
 * @author: DG
 * Date: Aug 23, 2005
 * Time: 8:42:31 PM
 */
public class DCLSetting implements Serializable
{
  String id;
  String name;
  String description;
  String value;

  public DCLSetting()
  {
  }

  public DCLSetting(String id)
  {
    this.id = id;
  }

  public DCLSetting(String id, String name)
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

  public String getDescription()
  {
    return description;
  }

  public void setDescription(String description)
  {
    this.description = description;
  }

  public String getValue()
  {
    return value;
  }

  public void setValue(String value)
  {
    this.value = value;
  }
}
