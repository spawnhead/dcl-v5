package net.sam.dcl.dao;

import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.beans.DBLog;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.db.VDbConnectionManager;
import net.sam.dcl.db.VDbConnection;

public class LogDAO
{
  public static void save(IActionContext context, DBLog log) throws Exception
  {
    DAOUtils.update(context.getConnection(), context.getSqlResource().get("log-insert"), log, null);
  }

  public static void saveByActionSystemName(IActionContext context, DBLog log) throws Exception
  {
    VDbConnection conn = null;
    try
    {
      conn = VDbConnectionManager.getVDbConnection();
      DAOUtils.update(conn, context.getSqlResource().get("log-insert_by_action_system_name"), log, null);
    }
    finally
    {
			if (conn != null)
        conn.close();
		}
  }
}
