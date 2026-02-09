package net.sam.dcl.dao;

import net.sam.dcl.beans.Unit;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.util.DAOUtils;

public class UnitDAO
{

  public static Unit load(IActionContext context, String id) throws Exception
  {
    Unit unit = new Unit(id);
    if (load(context, unit))
    {
      return unit;
    }
    throw new LoadException(unit, id);
  }

  public static boolean load(IActionContext context, Unit unit) throws Exception
  {
    return (DAOUtils.load(context, "dao-load-unit", unit, null));
  }

}
