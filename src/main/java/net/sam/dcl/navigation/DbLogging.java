package net.sam.dcl.navigation;

import net.sam.dcl.dao.LogDAO;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.beans.DBLog;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DbLogging
{
  protected static Log log = LogFactory.getLog(DbLogging.class);

  public static int loginId = 1;
  public static int logoutId = 2;

  public static void logAction(IActionContext context, int actionId, String IP)
  {
    try
    {
      LogDAO.save(context, new DBLog(Integer.toString(actionId), IP));
    }
    catch (Exception e)
    {
      log.error(e.getMessage(), e);
    }
  }

  public static void logAction(IActionContext context, String usrId, int actionId, String IP)
  {
    try
    {
      LogDAO.save(context, new DBLog(usrId, Integer.toString(actionId), IP));
    }
    catch (Exception e)
    {
      log.error(e.getMessage(), e);
    }
  }

  public static void logActionBySystemName(IActionContext context, String actionSystemName, String IP)
  {
    try
    {
      LogDAO.saveByActionSystemName(context, new DBLog("", "", actionSystemName, IP));
    }
    catch (Exception e)
    {
      log.error(e.getMessage(), e);
    }
  }
}
