package net.sam.dcl.beans;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * Date: 31.08.2005
 * Time: 18:53:07
 * To change this template use File | Settings | File Templates.
 */
public class ForUpdateDependedDocuments implements Serializable
{
  String oldId;
  String newId;

  public ForUpdateDependedDocuments(String oldId, String newId)
  {
    this.oldId = oldId;
    this.newId = newId;
  }

  public String getOldId()
  {
    return oldId;
  }

  public void setOldId(String oldId)
  {
    this.oldId = oldId;
  }

  public String getNewId()
  {
    return newId;
  }

  public void setNewId(String newId)
  {
    this.newId = newId;
  }
}