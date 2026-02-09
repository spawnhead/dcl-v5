package net.sam.dcl.dao;

import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.beans.Route;

public class RouteDAO
{

  public static Route load(IActionContext context, String id) throws Exception
  {
    Route route = new Route(id);
    if (load(context, route))
    {
      return route;
    }
    throw new LoadException(route, id);
  }

  public static boolean load(IActionContext context, Route route) throws Exception
  {
    return (DAOUtils.load(context, "dao-load-route", route, null));
  }

}
