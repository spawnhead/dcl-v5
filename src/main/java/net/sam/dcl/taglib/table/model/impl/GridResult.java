package net.sam.dcl.taglib.table.model.impl;

import net.sam.dcl.taglib.table.model.IGridResult;
import org.apache.commons.beanutils.PropertyUtils;

import java.util.*;

/**
 * @author: DG
 * Date: Mar 28, 2005
 * Time: 8:42:19 PM
 */
public class GridResult<T> implements IGridResult<T>
{
	protected Class<T> dataClass;
	protected Map<String, T> records = new LinkedHashMap<String, T>();

	public GridResult(Class<T> dataClass)
	{
		this.dataClass = dataClass;
	}

	public T checkRow(String pk) throws Exception
	{
		return records.get(pk);
	}

	public T getRow(String pk) throws Exception
	{
		T obj = records.get(pk);
		if (obj == null)
		{
			obj = (T) dataClass.newInstance();
			records.put(pk, obj);
		}
		return obj;
	}

	public void setRow(String pk, T value) throws Exception
	{
		getRow(pk);
		PropertyUtils.setProperty(records, pk, value);
	}

	public List<T> getRecordList()
	{
		return new ArrayList<T>(records.values());
	}

	public Map<String, T> getRecords()
	{
		return records;
	}

	public List<String> toList()
	{
		List<String> result = new ArrayList<String>();
		for (String s : records.keySet())
		{
			result.add(s);
		}
		return result;
	}

	public String toString()
	{
		Iterator keys = records.keySet().iterator();
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
