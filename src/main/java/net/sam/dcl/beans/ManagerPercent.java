package net.sam.dcl.beans;

import java.io.Serializable;

public class ManagerPercent implements Serializable
{
  Integer id;
  Integer parentId;
  Integer percent;
  User manager = new User();

  public ManagerPercent()
  {
  }

  public ManagerPercent(Integer percent, User manager)
  {
    this.percent = percent;
    this.manager = manager;
  }

  public ManagerPercent(ManagerPercent managerPercent)
  {
    this.id = managerPercent.id;
    this.parentId = managerPercent.parentId;
    this.percent = managerPercent.percent;
    this.manager = new User(managerPercent.getManager());
  }

  public Integer getId()
  {
    return id;
  }

  public void setId(Integer id)
  {
    this.id = id;
  }

  public Integer getParentId()
  {
    return parentId;
  }

  public void setParentId(Integer parentId)
  {
    this.parentId = parentId;
  }

  public Integer getPercent()
  {
    return percent;
  }

  public void setPercent(Integer percent)
  {
    if ( null == percent )
    {
      this.percent = 0;  
    }
    else
    {
      this.percent = percent;
    }
  }

  public User getManager()
  {
    return manager;
  }

  public void setManager(User manager)
  {
    this.manager = manager;
  }
}