package net.sam.dcl.navigation;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

import net.sam.dcl.db.VDbConnection;
import net.sam.dcl.db.VResultSet;
import net.sam.dcl.db.VParameter;
import net.sam.dcl.db.VDbConnectionManager;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.util.StringUtil;

public class ControlActions
{
  protected static Log log = LogFactory.getLog(ControlActions.class);

  final private Object mutex = new Object();
  private Map<String, net.sam.dcl.beans.Action> actions = new LinkedHashMap();

  public ControlActions()
  {
  }

  public void clear()
  {
    synchronized (mutex)
    {
      actions.clear();
    }
  }

  public void load()
  {
    synchronized (mutex)
    {
      VDbConnection conn = null;
      try
      {
        conn = VDbConnectionManager.getVDbConnection();
        String sql = "\nselect\n" +
                     "  act_id,\n" +
                     "  act_system_name,\n" +
                     "  act_logging,\n" +
                     "  act_check_access\n" +
                     "from dcl_action\n";
        VResultSet resultSet = DAOUtils.executeQuery(conn, sql, null, new VParameter());
        while (resultSet.next())
        {
          net.sam.dcl.beans.Action action = new net.sam.dcl.beans.Action(resultSet.getData("act_id"), resultSet.getData("act_system_name"), resultSet.getData("act_logging"), resultSet.getData("act_check_access"));
          actions.put(resultSet.getData("act_system_name"), action);
        }
      }
      catch (Exception e)
      {
        log.error("Error while loading actions:", e);
      }
      finally
      {
        if (conn != null) conn.close();
      }
    }
  }

  public void reload()
  {
    synchronized (mutex)
    {
      clear();
      load();
    }
  }

  public boolean isLoggingAction(String key)
  {
    synchronized (mutex)
    {
      net.sam.dcl.beans.Action action = getAction(key);
      if ( null != action )
      {
        return !StringUtil.isEmpty(action.getAct_logging());
      }
      return false;
    }
  }

  public boolean isCheckAccess(String key)
  {
    synchronized (mutex)
    {
      net.sam.dcl.beans.Action action = getAction(key);
      if ( null != action )
      {
        return !StringUtil.isEmpty(action.getAct_check_access());
      }
      return false;
    }
  }

  public net.sam.dcl.beans.Action getAction(String key)
  {
    synchronized (mutex)
    {
      net.sam.dcl.beans.Action action;
      action = actions.get(key);
      return action;
    }
  }

  public void putAction(net.sam.dcl.beans.Action action)
  {
    synchronized (mutex)
    {
      net.sam.dcl.beans.Action actionCheck = getAction(action.getAct_system_name());
      if ( null != actionCheck )
      {
        actionCheck.setAct_logging(action.getAct_logging());
        actionCheck.setAct_check_access(action.getAct_check_access());
      }
      else
      {
        actions.put(action.getAct_system_name(), action);
      }
    }
  }
}