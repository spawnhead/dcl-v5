package net.sam.dcl.taglib.table.model.impl;

import org.apache.commons.beanutils.PropertyUtils;

import java.util.*;

import net.sam.dcl.taglib.table.model.IGridResult;

/**
 * @author: DG
 * Date: Mar 28, 2005
 * Time: 8:42:19 PM
 */
public class GridResultUsingList implements IGridResult
{
	protected Class dataClass;
	protected List records = new ArrayList();

	public GridResultUsingList(Class dataClass)
	{
		this.dataClass = dataClass;
	}

	public Object checkRow(String pk) throws Exception
	{
		return records.get(Integer.parseInt(pk));
	}

	public Object getRow(String pk) throws Exception
	{
		Object obj = records.get(Integer.parseInt(pk));
		if (obj == null)
		{
			obj = dataClass.newInstance();
			records.set(Integer.parseInt(pk), obj);
		}
		return obj;
	}

	public void setRow(String pk, Object value) throws Exception
	{
		getRow(pk);
		records.set(Integer.parseInt(pk), value);
	}

	public List getRecordList()
	{
		return records;
	}

	public String toString()
	{
		Iterator keys = records.iterator();
		String res = "";
		while (keys.hasNext())
		{
			res += (String) keys.next();
			if (keys.hasNext())
			{
				res += ",";
			}
		}
		return res;
	}
}
