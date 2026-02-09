package net.sam.dcl.locking;

import net.sam.dcl.util.StringUtil;

import java.util.Date;

/**
 * @author: DG
 * Date: Jan 24, 2006
 * Time: 6:48:55 PM
 */
public class SyncObject
{
	String name;
	String id;
	String owner;
	Date creationTime;

	public SyncObject()
	{
	}

	public SyncObject(String name, String id, String owner)
	{
		this.name = name;
		this.id = id;
		this.owner = owner;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getOwner()
	{
		return owner;
	}

	public void setOwner(String owner)
	{
		this.owner = owner;
	}

	public Date getCreationTime()
	{
		return creationTime;
	}

	public void setCreationTime()
	{
		this.creationTime = StringUtil.getCurrentDateTime();
	}

	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		final SyncObject that = (SyncObject) o;

		if (id != null ? !id.equals(that.id) : that.id != null) return false;
		if (name != null ? !name.equals(that.name) : that.name != null) return false;
		if (owner != null ? !owner.equals(that.owner) : that.owner != null) return false;

		return true;
	}

	public String toString()
	{
		return name + "@" + id;
	}
}
