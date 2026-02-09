package net.sam.dcl.beans;

import net.sam.dcl.util.StringUtil;

import java.io.Serializable;

/**
 * @author: DG
 * Date: Aug 23, 2005
 * Time: 8:42:31 PM
 */
public class Currency implements Serializable
{
  String id;
  String name;
  String no_round;

  public Currency()
  {
  }

  public Currency(String id)
  {
    this.id = id;
  }

  public Currency(String id, String name)
  {
    this.id = id;
    this.name = name;
  }

  public Currency(Currency currency)
  {
    id = currency.getId();
    name = currency.getName();
    no_round = currency.getNo_round();
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

  public String getNo_round()
  {
    return no_round;
  }

  public void setNo_round(String no_round)
  {
    this.no_round = no_round;
  }

  public boolean isNeedRound()
  {
    return StringUtil.isEmpty(getNo_round());
  }
}
