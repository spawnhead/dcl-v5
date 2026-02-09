package net.sam.dcl.dao;

import net.sam.dcl.beans.Purpose;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.util.DAOUtils;

public class PurposeDAO
{

  public static Purpose load(IActionContext context, String id) throws Exception
  {
    Purpose purpose = new Purpose(id);
    if (load(context, purpose))
    {
      return purpose;
    }
    throw new LoadException(purpose, id);
  }

  public static Purpose loadByName(IActionContext context, String name) throws Exception
  {
    Purpose purpose = new Purpose(null, name);
    if (loadByName(context, purpose))
    {
      return purpose;
    }
    throw new LoadException(purpose, name);
  }

  public static boolean load(IActionContext context, Purpose purpose) throws Exception
  {
    return (DAOUtils.load(context, "dao-load-purpose", purpose, null));
  }

  public static boolean loadByName(IActionContext context, Purpose purpose) throws Exception
  {
    return (DAOUtils.load(context, "dao-load-purpose-by_name", purpose, null));
  }

}
