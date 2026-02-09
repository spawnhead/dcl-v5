package net.sam.dcl.util;

import net.sam.dcl.locking.SyncObject;
import org.apache.struts.taglib.TagUtils;
import org.apache.struts.taglib.tiles.ComponentConstants;
import org.apache.struts.Globals;
import org.apache.struts.tiles.ComponentContext;
import org.apache.struts.util.MessageResources;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.config.MessageResourcesConfig;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.ActionMessage;

import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.JspException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletRequest;

import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.message.EMessage;
import net.sam.dcl.session.SessionBooking;
import net.sam.dcl.beans.User;
import net.sam.dcl.beans.ReportDelimiterConsts;
import net.sam.dcl.action.Outline;

import java.util.Calendar;

/**
 * @author: DG
 * Date: Jul 30, 2005
 * Time: 5:01:34 PM
 */
public class StrutsUtil
{
  public final static String COMMENT_FOR_INSERTING_ERRORS = "/*comment for inserting errors(not erase)*/";

  static public String getMessage(IActionContext context, String key) throws Exception
  {
    return getMessage(context, key, null, null, null);
  }

  static public String getMessage(IActionContext context, String key, String arg0) throws Exception
  {
    return getMessage(context, key, arg0, null);
  }

  static public String getMessage(IActionContext context, String key, String arg0, String arg1) throws Exception
  {
    return getMessage(context, key, arg0, arg1, null);
  }

  static public String getMessage(IActionContext context, String key, String arg0, String arg1, String arg2) throws Exception
  {
    return getMessage(context, key, arg0, arg1, arg2, null);
  }

  static public String getMessage(IActionContext context, String key, String arg0, String arg1, String arg2, String arg3) throws Exception
  {
    ModuleConfig moduleConfig = context.getMapping().getModuleConfig();
    MessageResourcesConfig[] mrc = moduleConfig.findMessageResourcesConfigs();
    String value;
    for (int i = 0; i < mrc.length; i++)
    {
      MessageResources mr = (MessageResources) context.getServletContext().getAttribute(mrc[i].getKey() + moduleConfig.getPrefix());
      if (mr != null)
      {
        value = mr.getMessage(key, arg0, arg1, arg2, arg3);
        if (value != null) return value;
      }
    }
    throw new Exception("Unknown message recource key:[" + key + "]");
  }

  static public String getMessage(PageContext pageContext, String key, String arg0, String arg1, String arg2, String arg3, String arg4) throws JspException
  {
    Object args[] = new Object[]{arg0, arg1, arg2, arg3, arg4};
    return TagUtils.getInstance().message(pageContext,
            null,
            Globals.LOCALE_KEY,
            key,
            args);
  }

  static public String getMessage(PageContext pageContext, String key, String arg0, String arg1, String arg2, String arg3) throws JspException
  {
    return getMessage(pageContext, key, arg0, arg1, arg2, arg3, null);
  }

  static public String getMessage(PageContext pageContext, String key, String arg0, String arg1, String arg2) throws JspException
  {
    return getMessage(pageContext, key, arg0, arg1, arg2, null);
  }

  static public String getMessage(PageContext pageContext, String key, String arg0, String arg1) throws JspException
  {
    return getMessage(pageContext, key, arg0, arg1, null);
  }

  static public String getMessage(PageContext pageContext, String key, String arg0) throws JspException
  {
    return getMessage(pageContext, key, arg0, null);
  }

  static public String getMessage(PageContext pageContext, String key) throws JspException
  {
    return getMessage(pageContext, key, null);
  }

  static public void addError(IActionContext context, String key, Exception exception)
  {
    addError(context.getRequest(), key, null, null, null, exception);
  }

  static public void addError(IActionContext context, String key, String value0, Exception exception)
  {
    addError(context.getRequest(), key, value0, null, null, exception);
  }

  static public void addError(IActionContext context, String key, String value0, String value1, Exception exception)
  {
    addError(context.getRequest(), key, value0, value1, null, exception);
  }

  static public void addError(IActionContext context, String key, String value0, String value1, String value2, Exception exception)
  {
    addError(context.getRequest(), key, value0, value1, value2, exception);
  }

  static public void addError(HttpServletRequest request, String key, String value0, String value1, String value2, Exception exception)
  {
    addError(request, new EMessage(key, value0, value1, value2, exception));
  }

  static public void addError(IActionContext context, Exception exception)
  {
    addError(context.getRequest(), new EMessage(exception));
  }

  static public void addError(PageContext context, EMessage err)
  {
    addError(context.getRequest(), err);
  }

  static public void addError(ServletRequest request, EMessage err)
  {
    ActionMessages errors = (ActionMessages) request.getAttribute(Globals.ERROR_KEY);
    if (errors == null)
    {
      errors = new ActionMessages();
    }
    errors.add(ActionMessages.GLOBAL_MESSAGE, err);
    request.setAttribute(Globals.ERROR_KEY, errors);
  }

  //Messages
  static public void addMessage(IActionContext context, String key)
  {
    addMessage(context.getRequest(), key, null, null, null);
  }

  static public void addMessage(IActionContext context, String key, String value0)
  {
    addMessage(context.getRequest(), key, value0, null, null);
  }

  static public void addMessage(IActionContext context, String key, String value0, String value1)
  {
    addMessage(context.getRequest(), key, value0, value1, null);
  }

  static public void addMessage(IActionContext context, String key, String value0, String value1, String value2)
  {
    addMessage(context.getRequest(), key, value0, value1, value2);
  }

  static public void addMessage(HttpServletRequest request, String key, String value0, String value1, String value2)
  {
    addMessage(request, new ActionMessage(key, value0, value1, value2));
  }

  static public void addMessage(ServletRequest request, ActionMessage msg)
  {
    ActionMessages messages = (ActionMessages) request.getAttribute(Globals.MESSAGE_KEY);
    if (messages == null)
    {
      messages = new ActionMessages();
    }
    messages.add(ActionMessages.GLOBAL_MESSAGE, msg);
    request.setAttribute(Globals.MESSAGE_KEY, messages);
  }

  public static String getError4JavaScript(String msg, Throwable exc, String jsErrObj)
  {
    return jsErrObj + ".push(new ErrorObj(" + StringUtil.StringToJString(null) + "," +
            StringUtil.StringToJString(msg) + "," +
            StringUtil.StringToJString(null) + "," +
            StringUtil.ExceptionToJString(exc) + "));";
  }

  public static String getError4JavaScript(String msg, Throwable exc)
  {
    return getError4JavaScript(msg, exc, "pageErrorsArr");
  }

  static public String getError4JavaScript(IActionContext context, String msgId) throws Exception
  {
    return getError4JavaScript(getMessage(context, msgId), null);
  }

  static public String getError4JavaScript(IActionContext context, String key, String arg0, String arg1, String arg2) throws Exception
  {
    return getError4JavaScript(getMessage(context, key, arg0, arg1, arg2), null);
  }

  static public class FormatLockResult
  {
    public String userName = "";
    public String creationTime = "";
    public String creationElapsedTime = "";
  }

  public static FormatLockResult formatLockError(SyncObject syncObject, IActionContext context) throws Exception
  {
    FormatLockResult res = new FormatLockResult();
    HttpSession session = SessionBooking.getSessionBooking().find(syncObject.getOwner());

    if (session != null)
    {
      User user = UserUtil.getCurrentUser(session);
      if (user != null)
      {
        res.userName = user.getUserFullName();
      }
    }
    res.creationTime = StringUtil.date2appDateTimeString(syncObject.getCreationTime());
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(System.currentTimeMillis() - syncObject.getCreationTime().getTime());
    calendar.add(Calendar.MILLISECOND, -calendar.get(Calendar.ZONE_OFFSET));
    res.creationElapsedTime = StringUtil.date2appTimeString(calendar.getTime());
    return res;
  }

  public static String getCurrSubMenuTitleOrFormTitle(PageContext pageContext) throws JspException
  {
    String str_menu_id = (String) pageContext.getSession().getAttribute(Outline.MENU_ID_NAME);
    String menuItem = "";
    Outline outline = Outline.findOutline(Outline.OUTLINE, str_menu_id, true, false, pageContext.getSession());
    if (outline != null && outline.subItems == null)
    {
      menuItem = " - " + StrutsUtil.getMessage(pageContext, outline.title);
    }
    if (StringUtil.isEmpty(menuItem))
    {
      ComponentContext compContext = (ComponentContext) pageContext.getAttribute(ComponentConstants.COMPONENT_CONTEXT, PageContext.REQUEST_SCOPE);
      if (compContext == null)
        throw new JspException("Error - tag importAttribute : no tiles context found.");
      Object value = compContext.getAttribute("form-title-id");
      // Check if value exist and if we must send a runtime exception
      if (value != null)
      {
        assert value instanceof String;
        String message = getMessage(pageContext, (String) value);
        if (!StringUtil.isEmpty(message))
        {
          menuItem = " - " + message;
        }
      }
    }
    return menuItem;
  }

  public static boolean isAjaxRequest(ServletRequest request)
  {
    return !StringUtil.isEmpty(request.getParameter(ConstDefinitions.AJAX_REQUEST_ID));

  }

  public static void setAjaxResponse(ServletRequest request, String response)
  {
    request.setAttribute(ConstDefinitions.AJAX_RESPONSE, response);
  }

  public static void setAjaxResponse(IActionContext context, String response, boolean translate) throws Exception
  {
    context.getRequest().setAttribute(ConstDefinitions.AJAX_RESPONSE, translate ? getMessage(context, response) : response);
  }

  public static String getAjaxResponse(ServletRequest request)
  {
    Object obj = request.getAttribute(ConstDefinitions.AJAX_RESPONSE);
    if (obj != null)
    {
      return obj.toString();
    }
    else
    {
      return "";
    }
  }

  public static String addDelimiter(String msgStr)
  {
    String retStr = msgStr;
    if (!StringUtil.isEmpty(retStr))
    {
      retStr += ReportDelimiterConsts.html_separator;
    }
    return retStr;
  }
}
