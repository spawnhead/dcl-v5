package net.sam.dcl.beans;

import java.io.Serializable;

/**
 * @author: DG
 * Date: Aug 18, 2005
 * Time: 2:31:03 PM
 */
public class Reputation implements Serializable
{
  String id;
  String level;
  String description;
  String defaultInContractor;

  public Reputation()
  {
  }

  public Reputation(String id)
  {
    this.id = id;
  }

  public Reputation(Reputation reputation)
  {
    id = reputation.getId();
    level = reputation.getLevel();
    description = reputation.getDescription();
    defaultInContractor = reputation.getDefaultInContractor();
  }

  public String getId()
  {
    return id;
  }

  public void setId(String id)
  {
    this.id = id;
  }

  public String getLevel()
  {
    return level;
  }

  public void setLevel(String level)
  {
    this.level = level;
  }

  public String getDescription()
  {
    return description;
  }

  public void setDescription(String description)
  {
    this.description = description;
  }

  public String getDefaultInContractor()
  {
    return defaultInContractor;
  }

  public void setDefaultInContractor(String defaultInContractor)
  {
    this.defaultInContractor = defaultInContractor;
  }
}
