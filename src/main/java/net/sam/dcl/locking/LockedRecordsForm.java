package net.sam.dcl.locking;

import net.sam.dcl.controller.BaseForm;
import net.sam.dcl.taglib.table.model.impl.HolderImplUsingList;
import net.sam.dcl.beans.User;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.UserUtil;
import net.sam.dcl.session.SessionBooking;

import javax.servlet.http.HttpSession;
import java.util.Calendar;

/**
 * @author: DG
 * Date: Aug 6, 2005
 * Time: 5:37:29 PM
 */
public class LockedRecordsForm extends BaseForm
{
	String id;
	String name;
	HolderImplUsingList grid = new HolderImplUsingList();

	public HolderImplUsingList getGrid()
	{
		return grid;
	}

	public void setGrid(HolderImplUsingList grid)
	{
		this.grid = grid;
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

	static public class LockedRecord
	{
		String id;
		String name;
		String owner;
		String creationTime;
		String creationElapsedTime;
		String userName = "";

		public LockedRecord()
		{
		}

		public LockedRecord(SyncObject syncObject)
		{
			id = syncObject.getId();
			name = syncObject.getName();
			owner = syncObject.getOwner();
			creationTime = StringUtil.date2appDateTimeString(syncObject.getCreationTime());
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(System.currentTimeMillis() - syncObject.getCreationTime().getTime());
			calendar.add(Calendar.MILLISECOND, -calendar.get(Calendar.ZONE_OFFSET));
			creationElapsedTime = StringUtil.date2appTimeString(calendar.getTime());
			HttpSession session = SessionBooking.getSessionBooking().find(owner);
			if (session != null)
			{
				User user = UserUtil.getCurrentUser(session);
				if (user != null)
				{
					userName = user.getUserFullName();
				}
			}
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

		public String getOwner()
		{
			return owner;
		}

		public void setOwner(String owner)
		{
			this.owner = owner;
		}

		public String getCreationTime()
		{
			return creationTime;
		}

		public void setCreationTime(String creationTime)
		{
			this.creationTime = creationTime;
		}

		public String getCreationElapsedTime()
		{
			return creationElapsedTime;
		}

		public void setCreationElapsedTime(String creationElapsedTime)
		{
			this.creationElapsedTime = creationElapsedTime;
		}

		public String getUserName()
		{
			return userName;
		}

		public void setUserName(String userName)
		{
			this.userName = userName;
		}

	}
}
