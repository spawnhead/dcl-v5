package net.sam.dcl.dao;

import net.sam.dcl.beans.Action;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.controller.IActionContext;

public class ActionDAO
{

  public static Action load(IActionContext context, String id) throws Exception
  {
    Action action = new Action(id);

    if (load(context, action))
    {
      return action;
    }
    throw new LoadException(action, id);
  }

  public static boolean load(IActionContext context, Action action) throws Exception
  {
    if (DAOUtils.load(context, "action-load", action, null))
    {
      return true;
    }
    else
    {
      return false;
    }
  }

  public static void save(IActionContext context, Action action) throws Exception
  {
    DAOUtils.update(context.getConnection(), context.getSqlResource().get("action-update"), action, null);
  }
}