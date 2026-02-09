package net.sam.dcl.beans;

import java.io.Serializable;

public class FilesPath implements Serializable
{
  String id;
  String tableName;
  String path;
  String description;

  public FilesPath()
  {
  }

  public FilesPath(String tableName)
  {
    this.id = tableName;
  }

  public FilesPath(String tableName, String path)
  {
    this.tableName = tableName;
    this.path = path;
  }

  public String getId()
  {
    return id;
  }

  public void setId(String id)
  {
    this.id = id;
  }

  public String getTableName()
  {
    return tableName;
  }

  public void setTableName(String tableName)
  {
    this.tableName = tableName;
  }

  public String getPath()
  {
    return path;
  }

  public void setPath(String path)
  {
    this.path = path;
  }

  public String getDescription()
  {
    return description;
  }

  public void setDescription(String description)
  {
    this.description = description;
  }
}