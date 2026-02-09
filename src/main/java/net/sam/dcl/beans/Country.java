package net.sam.dcl.beans;

import java.io.Serializable;

public class Country implements Serializable
{
  public static String currentCountryId = "currentCountryId";

  String id;
  String name;

  public Country()
  {
  }

  public Country(String id)
  {
    this.id = id;
  }

  public Country(String id, String name)
  {
    this.id = id;
    this.name = name;
  }

  public Country(Country country)
  {
    this.id = country.id;
    this.name = country.name;
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