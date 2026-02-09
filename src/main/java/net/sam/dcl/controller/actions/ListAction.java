package net.sam.dcl.controller.actions;

import net.sam.dcl.controller.IActionContext;
import org.apache.struts.action.ActionForward;

import java.util.Map;


/**
 * User: Dima
 * Date: Feb 20, 2005
 * Time: 12:04:54 PM
 */
public class ListAction extends DBAction
{
  public static final String LIST_KEY = ListAction.class.getName();

  public ActionForward execute(IActionContext context) throws Exception
  {
    Object list = listExecute(context);
    context.getRequest().setAttribute(LIST_KEY, list);
    if (list instanceof Map)
    {
      return context.getMapping().findForward("map-default-forward");
    }
    else
    {
      return context.getMapping().findForward("list-default-forward");
    }
  }

  public Object listExecute(IActionContext context) throws Exception
  {
    throw new BaseAction.NotImplementedException();
  }
}
