package net.sam.dcl.beans;

import java.io.Serializable;

/**
 * Date: Aug 18, 2005
 * Time: 2:31:03 PM
 */
public class UserLanguage implements Serializable
{

  String usr_id;
  Language language = new Language();
  String usr_name;
  String usr_surname;
  String usr_position;

  public UserLanguage()
  {
  }

  public UserLanguage(String usr_id)
  {
    this.usr_id = usr_id;
  }

  public UserLanguage(UserLanguage user)
  {
    this.usr_id = user.usr_id;
    this.language = new Language(user.language);
    this.usr_name = user.usr_name;
    this.usr_surname = user.usr_surname;
    this.usr_position = user.usr_position;
  }

  public String getUsr_id()
  {
    return usr_id;
  }

  public String getUsr_name()
  {
    return usr_name;
  }

  public Language getLanguage()
  {
    return language;
  }

  public void setLanguage(Language language)
  {
    this.language = language;
  }

  public void setUsr_name(String usr_name)
  {
    this.usr_name = usr_name;
  }

  public String getUsr_surname()
  {
    return usr_surname;
  }

  public void setUsr_surname(String usr_surname)
  {
    this.usr_surname = usr_surname;
  }

  public void setUsr_id(String usr_id)
  {
    this.usr_id = usr_id;
  }

  public String getUsr_position()
  {
    return usr_position;
  }

  public void setUsr_position(String usr_position)
  {
    this.usr_position = usr_position;
  }

  public String getUserFullName()
  {

    if (null == usr_name && null == usr_surname)
      return "";

    String retStr = "";
    if (null != usr_surname)
      retStr += usr_surname;
    retStr += " ";
    if (null != usr_name)
      retStr += usr_name;

    return retStr;
  }

  /*
    Use with great care
  */
  public void setUserFullName(String fullName)
  {
    if (fullName != null)
    {
      String arr[] = fullName.split(" ");
      if (arr.length > 0)
      {
        usr_surname = arr[0];
      }
      if (arr.length > 1)
      {
        usr_name = arr[1];
      }
    }
  }
}
