package net.sam.dcl.session;

import net.sam.dcl.controller.BaseForm;
import net.sam.dcl.taglib.table.model.impl.HolderImplUsingList;
import net.sam.dcl.beans.User;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.UserUtil;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Calendar;

/**
 * @author: DG
 * Date: Aug 6, 2005
 * Time: 5:37:29 PM
 */
public class SessionsForm extends BaseForm
{
  HolderImplUsingList grid = new HolderImplUsingList();

  public HolderImplUsingList getGrid()
  {
    return grid;
  }

  public void setGrid(HolderImplUsingList grid)
  {
    this.grid = grid;
  }

  static public class SessionRecord
  {
    String id;
    String creationTime;
    String creationElapsedTime;
    String lastAccessedTime;
    String lastAccesElapsedTime;
    String userName = "";
    String userIp = "";

    public SessionRecord()
    {
    }

    public SessionRecord(HttpSession session)
    {
      id = session.getId();
      creationTime = StringUtil.date2appDateTimeString(new Date(session.getCreationTime()));
      Calendar calendar = Calendar.getInstance();
      calendar.setTimeInMillis(System.currentTimeMillis() - session.getCreationTime());
      calendar.add(Calendar.MILLISECOND, -calendar.get(Calendar.ZONE_OFFSET));
      creationElapsedTime = StringUtil.date2appTimeString(calendar.getTime());

      lastAccessedTime = StringUtil.date2appDateTimeString(new Date(session.getLastAccessedTime()));
      calendar.setTimeInMillis(System.currentTimeMillis() - session.getLastAccessedTime());
      calendar.add(Calendar.MILLISECOND, -calendar.get(Calendar.ZONE_OFFSET));
      lastAccesElapsedTime = StringUtil.date2appTimeString(calendar.getTime());
      User user = UserUtil.getCurrentUser(session);
      if (user != null)
      {
        userName = user.getUserFullName() + "[" + user.getUsr_id() + "]";
        userIp = user.getIp();
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

    public String getCreationTime()
    {
      return creationTime;
    }

    public void setCreationTime(String creationTime)
    {
      this.creationTime = creationTime;
    }

    public String getLastAccessedTime()
    {
      return lastAccessedTime;
    }

    public void setLastAccessedTime(String lastAccessedTime)
    {
      this.lastAccessedTime = lastAccessedTime;
    }

    public String getUserName()
    {
      return userName;
    }

    public void setUserName(String userName)
    {
      this.userName = userName;
    }

    public String getLastAccesElapsedTime()
    {
      return lastAccesElapsedTime;
    }

    public void setLastAccesElapsedTime(String lastAccesElapsedTime)
    {
      this.lastAccesElapsedTime = lastAccesElapsedTime;
    }

    public String getCreationElapsedTime()
    {
      return creationElapsedTime;
    }

    public void setCreationElapsedTime(String creationElapsedTime)
    {
      this.creationElapsedTime = creationElapsedTime;
    }

    public String getUserIp()
    {
      return userIp;
    }

    public void setUserIp(String userIp)
    {
      this.userIp = userIp;
    }
  }

  public static void main(String[] args)
  {
    //Date dt = new Date(1000 - TimeZone.getDefault().getOffset(StringUtil.getCurrentDateTime().getTime()));
    //Date dt = new Date(1000 - );
    //System.out.println(dt);
    Calendar calendar = Calendar.getInstance();

    calendar.setTimeInMillis(1000);
    calendar.add(Calendar.MILLISECOND, -calendar.get(Calendar.ZONE_OFFSET));
    //calendar.set(Calendar.MILLISECOND,1000);
    ///calendar.setTimeZone(TimeZone.getTimeZone("GMT+04:00"));
    System.out.println(calendar.getTime());
    System.out.println(calendar.getTimeZone());
  }
}
