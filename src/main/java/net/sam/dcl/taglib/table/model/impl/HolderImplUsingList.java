package net.sam.dcl.taglib.table.model.impl;

import net.sam.dcl.taglib.table.model.DataHolder;
import net.sam.dcl.taglib.table.model.IGridResult;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * User: Dima
 * Date: Mar 20, 2005
 * Time: 2:59:39 PM
 */
public class HolderImplUsingList implements DataHolder, IGridResult
{
	protected List list = null;
	protected Iterator iterator = null;
	protected Object current = null;

	public HolderImplUsingList()
	{
		setDataList(new ArrayList());
	}

	public HolderImplUsingList(List list)
	{
		this.list = list;
	}

	public boolean next()
	{
		if (iterator == null)
		{
			this.iterator = list.iterator();
		}
		if (iterator.hasNext())
		{
			current = iterator.next();
			return true;
		}
		iterator = null;
		return false;
	}

	public boolean hasNext()
	{
		if (iterator == null)
		{
			this.iterator = list.iterator();
		}
		return iterator.hasNext();
	}

	public Object getCurrentRow()
	{
		return current;
	}

	public Object checkRow(String idx) throws Exception
	{
		return list.get(Integer.parseInt(idx));
	}

	public Object getRow(String idx) throws Exception
	{
		return list.get(Integer.parseInt(idx));
	}

	public void setRow(String idx, Object value) throws Exception
	{
		list.set(Integer.parseInt(idx), value);
	}

	public void setDataList(List list)
	{
		this.list = list;
		this.iterator = null;
		this.current = null;
	}

	public List getDataList()
	{
		return list;
	}
}
