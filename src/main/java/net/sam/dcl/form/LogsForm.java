package net.sam.dcl.form;

import net.sam.dcl.taglib.table.model.impl.PageableHolderImplUsingList;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.beans.Action;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LogsForm extends JournalForm
{
  protected static Log log = LogFactory.getLog(LogsForm.class);
  PageableHolderImplUsingList grid = new PageableHolderImplUsingList();
  Action action = new Action();
  String ip;

  public PageableHolderImplUsingList getGrid()
  {
    return grid;
  }

  public void setGrid(PageableHolderImplUsingList grid)
  {
    this.grid = grid;
  }

  public Action getAction()
  {
    return action;
  }

  public void setAction(Action action)
  {
    this.action = action;
  }

  public String getIp()
  {
    return ip;
  }

  public void setIp(String ip)
  {
    this.ip = ip;
  }

  static public class DBLog
  {
    String log_id;
    String log_action;
    String log_user;
    String log_ip;
    String log_time;

    public String getLog_id()
    {
      return log_id;
    }

    public void setLog_id(String log_id)
    {
      this.log_id = log_id;
    }

    public String getLog_action()
    {
      return log_action;
    }

    public void setLog_action(String log_action)
    {
      this.log_action = log_action;
    }

    public String getLog_user()
    {
      return log_user;
    }

    public void setLog_user(String log_user)
    {
      this.log_user = log_user;
    }

    public String getLog_ip()
    {
      return log_ip;
    }

    public void setLog_ip(String log_ip)
    {
      this.log_ip = log_ip;
    }

    public String getLog_time()
    {
      return log_time;
    }

    public String getLog_time_formatted()
    {
      return StringUtil.dbDateTimeString2appDateTimeString(log_time);
    }

    public void setLog_time(String log_time)
    {
      this.log_time = log_time;
    }
  }
}