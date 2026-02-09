package net.sam.dcl.locking;

import net.sam.dcl.util.StringUtil;

import java.util.*;

/**
 * @author: DG
 * Date: Jan 24, 2006
 * Time: 6:39:12 PM
 */
public class LockedRecords
{
	protected List list = new ArrayList();

	protected LockedRecords()
	{
	}

	static private LockedRecords lockedRecords = new LockedRecords();

	public static LockedRecords getLockedRecords()
	{
		return lockedRecords;
	}

	public SyncObject lock(SyncObject obj)
	{
		synchronized (list)
		{
			SyncObject syncObject = findLock(obj);
			if (syncObject == null)
			{
				obj.setCreationTime();
				list.add(obj);
				return obj;
			}
			else
			{
				return syncObject;
			}
		}
	}

	public void unlock(SyncObject obj)
	{
		synchronized (list)
		{
			list.remove(obj);
		}
	}

	public boolean isLocked(SyncObject obj)
	{
		synchronized (list)
		{
			return list.contains(obj);
		}
	}

	public SyncObject findLock(SyncObject obj)
	{
		synchronized (list)
		{
			for (int i = 0; i < list.size(); i++)
			{
				SyncObject syncObject = (SyncObject) list.get(i);
				if (StringUtil.equal(obj.getName(), syncObject.getName()) &&
								StringUtil.equal(obj.getId(), syncObject.getId()))
				{
					return syncObject;
				}
			}
			return null;
		}
	}

	public boolean isLockedByOther(SyncObject obj)
	{
		synchronized (list)
		{
			SyncObject syncObject = findLock(obj);
			if (syncObject == null)
			{
				return false;
			}
			else
			{
				return !obj.equals(syncObject);
			}
		}
	}

	public void unlockWithTheSame(String name, String owner)
	{
		synchronized (list)
		{
			for (int i = 0; i < list.size(); i++)
			{
				SyncObject syncObject = (SyncObject) list.get(i);
				if (StringUtil.equal(name, syncObject.getName()) &&
								StringUtil.equal(owner, syncObject.getOwner()))
				{
					list.remove(i);
					i--;
				}
			}
		}
	}

	public void unlockWithTheSame(String owner)
	{
		synchronized (list)
		{
			for (int i = 0; i < list.size(); i++)
			{
				SyncObject syncObject = (SyncObject) list.get(i);
				if (StringUtil.equal(owner, syncObject.getOwner()))
				{
					list.remove(i);
					i--;
				}
			}
		}
	}

	public void iterate(LockedRecordsProcessor processor) throws Exception
	{
		synchronized (list)
		{
			for (int i = 0; i < list.size(); i++)
			{
				processor.process((SyncObject) list.get(i));
			}
		}
	}

	public void clear()
	{
		synchronized (list)
		{
			list.clear();
		}
	}
}
