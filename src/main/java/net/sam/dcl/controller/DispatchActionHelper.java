package net.sam.dcl.controller;


import net.sam.dcl.db.VDbException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * User: Dima
 * Date: Oct 13, 2004
 * Time: 4:59:35 PM
 */
public class DispatchActionHelper
{
  // ----------------------------------------------------- Instance Variables

  protected Object action = null;
  public static final String defaultParameter = "dispatch";
  /**
   * Commons Logging instance.
   */
  protected static Log log = LogFactory.getLog(DispatchActionHelper.class);

  /**
   * The message resources for this package.
   */
  protected static MessageResources messages =
          MessageResources.getMessageResources("org.apache.struts.actions.LocalStrings");

  /**
   * The set of Method objects we have introspected for this class,
   * keyed by method name.  This collection is populated as different
   * methods are called, so that introspection needs to occur only
   * once per method name.
   */
  protected HashMap methods = new HashMap();

  /**
   * The set of argument type classes for the reflected method call.  These
   * are the same for all calls, so calculate them only once.
   */
  //protected Class types[] = {ActionContext.class};
  public DispatchActionHelper(Object action)
  {
    this.action = action;
  }

  public static String getDispatchParameter(ActionMapping mapping)
  {
    String parameter = mapping.getParameter();
    if (parameter == null)
    {
      parameter = defaultParameter;
    }
    return parameter;
  }

  public static String getDispatchMethodName(ActionMapping mapping, HttpServletRequest request)
  {
    String methodName = request.getParameter(getDispatchParameter(mapping));
    return methodName;
  }

  /**
   * Dispatch to the specified method.
   *
   * @since Struts 1.1
   */

  static public ActionForward dispatchMethod(Object action, IActionContext context, Class clazz, Object args[]) throws Exception
  {
    DispatchActionHelper helper = new DispatchActionHelper(action);
    context.getRequest().setAttribute(defaultParameter, getDispatchMethodName(context.getMapping(), context.getRequest()));
    return helper.dispatchMethod(context, clazz, args);
  }

  public ActionForward dispatchMethod(IActionContext context, Class clazz, Object args[]) throws Exception
  {
    String methodName = getDispatchMethodName(context.getMapping(), context.getRequest());
    // Make sure we have a valid method name to call.
    // This may be null if the user hacks the query string.
    ActionForward forward = null;
    if (methodName != null)
    {
      Method method = null;
      try
      {
        method = getMethod(methodName, clazz == null ? null : new Class[]{clazz});
      }
      catch (NoSuchMethodException e)
      {
        String message = messages.getMessage("dispatch.method", context.getMapping().getPath(), methodName + "(" + context.getClass() + ")");
        logAndThrow(e, message);
      }
      try
      {
        forward = (ActionForward) method.invoke(action, args);
      }
      catch (ClassCastException e)
      {
        String message = messages.getMessage("dispatch.return", context.getMapping().getPath(), methodName);
        logAndThrow(e, message);
      }
      catch (IllegalAccessException e)
      {
        String message = messages.getMessage("dispatch.error", context.getMapping().getPath(), methodName);
        logAndThrow(e, message);
      }
      catch (InvocationTargetException e)
      {
        // Rethrow the target exception if possible so that the
        // exception handling machinery can deal with it
        Throwable t = e.getCause();
        if (t instanceof VDbException)
        {
          throw ((VDbException) t);
        }
        else if (t instanceof Exception)
        {
          throw ((Exception) t);
        }
        else
        {
          String message = messages.getMessage("dispatch.error", context.getMapping().getPath(), methodName);
          logAndThrow(e, message);
        }
      }
    }
    else
    {
      String message = messages.getMessage("dispatch.parameter", context.getMapping().getPath(), getDispatchParameter(context.getMapping()));
      log.error(message);
      throw new Exception(message);
    }
    return (forward);
  }

  public boolean isDispatchNeeded(Class clazz)
  {
    return clazz.equals(action.getClass().getSuperclass()) &&
            action instanceof IDispatchable;
  }

  /**
   * Introspect the current class to identify a method of the specified
   * name that accepts the same parameter types as the <code>execute</code>
   * method does.
   *
   * @param name Name of the method to be introspected
   * @throws NoSuchMethodException if no such method can be found
   */
  protected Method getMethod(String name, Class[] types)
          throws NoSuchMethodException
  {

    synchronized (methods)
    {
      Method method = (Method) methods.get(name);
      if (method == null)
      {
        method = action.getClass().getMethod(name, types);
        methods.put(name, method);
      }
      return (method);
    }

  }

  void logAndThrow(Exception e, String message) throws Exception
  {
    log.error(message, e);
    throw new Exception(message, e);
  }
}
