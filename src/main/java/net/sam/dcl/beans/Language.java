package net.sam.dcl.beans;

import java.io.Serializable;

/**
 * @author: DG
 * Date: Aug 23, 2005
 * Time: 8:42:31 PM
 */
public class Language implements Serializable
{
  String id;
  String name;
	String letterId;

	public Language()
  {
  }

  public Language(Language language)
  {
    this.id = language.id;
    this.name = language.name;
    this.letterId = language.letterId;
  }

  public Language(String id)
  {
    this.id = id;
  }

  public Language(String id, String name)
  {
    this.id = id;
    this.name = name;
  }

	public Language(String id, String name, String letterId) {
		this.id = id;
		this.name = name;
		this.letterId = letterId;
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

	public String getLetterId() {
		return letterId;
	}

	public void setLetterId(String letterId) {
		this.letterId = letterId;
	}
}
