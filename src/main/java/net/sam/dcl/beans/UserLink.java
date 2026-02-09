package net.sam.dcl.beans;

import net.sam.dcl.util.StringUtil;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * Date: 31.08.2005
 * Time: 18:53:07
 * To change this template use File | Settings | File Templates.
 */
public class UserLink implements Serializable
{
  public static String economistRoleId = "4";

  String id;
  String date;
  String url;
  String parameters;
  String text;
  String menuId;

  public UserLink()
  {
  }

  public UserLink(String Id)
  {
    this.id = Id;
  }

  public UserLink(UserLink userLink)
  {
    this.id = userLink.getId();
    this.date = userLink.getDate();
    this.url = userLink.getUrl();
    this.parameters = userLink.getParameters();
    this.text = userLink.getText();
    this.menuId = userLink.getMenuId();
  }

  public String getId()
  {
    return id;
  }

  public void setId(String id)
  {
    this.id = id;
  }

  public String getDate()
  {
    return date;
  }

  public String getDateFormatted()
  {
    return StringUtil.dbDateTimeString2appDateTimeString(getDate());
  }

  public void setDate(String date)
  {
    this.date = date;
  }

  public String getUrl()
  {
    return url;
  }

  public void setUrl(String url)
  {
    this.url = url;
  }

  public String getParameters()
  {
    return parameters;
  }

  public void setParameters(String parameters)
  {
    this.parameters = parameters;
  }

  public String getText()
  {
    return text;
  }

  public void setText(String text)
  {
    this.text = text;
  }

  public String getMenuId()
  {
    return menuId;
  }

  public void setMenuId(String menuId)
  {
    this.menuId = menuId;
  }

  public String getEconomistRoleId()
  {
    return economistRoleId;
  }
}