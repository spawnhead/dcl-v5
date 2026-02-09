package net.sam.dcl.beans;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

/**
 * @author: DG
 * Date: Aug 23, 2005
 * Time: 8:42:31 PM
 */
public class Unit implements Serializable
{
  String id;
  List unit_language = new ArrayList();
  boolean is_acceptable_for_cpr;

  public Unit()
  {
  }

  public Unit(String id)
  {
    this.id = id;
  }

  public Unit(String id, String name)
  {
    this.id = id;
  }

  public String getId()
  {
    return id;
  }

  public void setId(String id)
  {
    this.id = id;
  }

  public List getUnit_language()
  {
    return unit_language;
  }

  public void setUnit_language(List unit_language)
  {
    this.unit_language = unit_language;
  }

	public boolean isIs_acceptable_for_cpr() {
		return is_acceptable_for_cpr;
	}

	public void setIs_acceptable_for_cpr(boolean is_acceptable_for_cpr) {
		this.is_acceptable_for_cpr = is_acceptable_for_cpr;
	}
}
