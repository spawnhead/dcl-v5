package net.sam.dcl.controller.actions;

import net.sam.dcl.controller.IDispatchable;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.taglib.LinkTagEx;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.Globals;

import javax.servlet.http.HttpSession;

/**
 * @author: DG
 * Date: 13-Jan-2007
 * Time: 15:37:15
 */
public class SelectFromGridAction extends BaseAction implements IDispatchable
{
  public static final String SELECT_ID = SelectFromGridAction.class.getName() + "#SELECT_ID";
  public static final String SELECT_MODE = SelectFromGridAction.class.getName() + "#SELECT_MODE";
  public static final String SELECT_MODE_SAVED = SelectFromGridAction.class.getName() + "#SELECT_MODE_SAVED";
  public static final String SELECT_METHOD = "select";

  public ActionForward input(IActionContext context) throws Exception
  {
    ActionMapping mapping = (ActionMapping) context.getRequest().getAttribute(Globals.MAPPING_KEY);
    String url = LinkTagEx.addDispatchToURL(mapping.getPath(), SelectFromGridAction.SELECT_METHOD);
    setSelectMode(context, url);
    return context.getMapping().getInputForward();
  }

  public ActionForward select(IActionContext context) throws Exception
  {
    String id = context.getRequest().getParameter(SELECT_ID);
    setSelectedId(context, id);
    clearSelectMode(context);
    return context.getMapping().findForward("return");
  }

  static public void setSelectedId(IActionContext context, String id)
  {
    context.getRequest().setAttribute(SELECT_ID, id);
  }

  static public String getSelectedId(IActionContext context)
  {
    return (String) context.getRequest().getAttribute(SELECT_ID);
  }

  static public void setSelectMode(IActionContext context, String url)
  {
    context.getRequest().getSession().setAttribute(SELECT_MODE, url);
  }

  static public void interruptSelectMode(IActionContext context)
  {
    HttpSession session = context.getRequest().getSession();
    if (isSelectMode(session))
    {
      session.setAttribute(SELECT_MODE_SAVED, getSelectModeSelectUrl(session));
      clearSelectMode(context);
    }
  }

  static public void restoreSelectMode(IActionContext context)
  {
    HttpSession session = context.getRequest().getSession();
    String url = (String) session.getAttribute(SELECT_MODE_SAVED);
    if (url != null)
    {
      setSelectMode(context, url);
      session.setAttribute(SELECT_MODE_SAVED, null);
    }
  }

  static public void clearSelectMode(IActionContext context)
  {
    context.getRequest().getSession().removeAttribute(SELECT_MODE);
  }

  static public boolean isSelectMode(HttpSession session)
  {
    return session.getAttribute(SELECT_MODE) != null;
  }

  static public String getSelectModeSelectUrl(HttpSession session)
  {
    return (String) session.getAttribute(SELECT_MODE);
  }
}
