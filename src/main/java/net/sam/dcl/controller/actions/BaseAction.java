package net.sam.dcl.controller.actions;

import net.sam.dcl.beans.User;
import net.sam.dcl.config.Config;
import net.sam.dcl.controller.*;
import net.sam.dcl.controller.processor.ActionProcessor;
import net.sam.dcl.controller.processor.ActionProcessorImpl;
import net.sam.dcl.locking.LockedRecords;
import net.sam.dcl.locking.SyncObject;
import net.sam.dcl.navigation.ControlActions;
import net.sam.dcl.navigation.DbLogging;
import net.sam.dcl.util.*;
import net.sam.dcl.db.VDbException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;
import org.apache.struts.action.*;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.taglib.TagUtils;
import org.apache.struts.taglib.html.Constants;
import org.apache.struts.util.ModuleUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * User: Dima
 * Date: Sep 29, 2004
 * Time: 5:01:29 PM
 */
public class BaseAction extends Action
{
  public static final String PARAM_LOCK_NAME = "__lock_name__";
  public static final String PARAM_LOCK_ID = "__lock_id__";
  protected static Log log = LogFactory.getLog(BaseAction.class);
  protected DispatchActionHelper dispatchActionHelper = new DispatchActionHelper(this);

  public IActionContext getActionContext() throws Exception
  {
    Class clazz = ActionContext.class;
    String name = Config.getString("global.action.context.class");
    if (!StringUtil.isEmpty(name))
    {
      try
      {
        clazz = Class.forName(name);
      }
      catch (ClassNotFoundException e)
      {
        log.error("Can't get Class for name:" + name, e);
      }
    }
    return (IActionContext) clazz.newInstance();
  }

  public void initActionContext(IActionContext context, ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, ActionProcessor actionProcessor) throws Exception
  {
    context.setMapping(mapping);
    context.setForm(actionForm);
    context.setRequest(request);
    context.setResponse(response);
    context.setErrors(new ActionMessages());
    context.setMessages(new ActionMessages());
    context.setSqlResource((XMLResource) StoreUtil.getApplication(getServlet().getServletContext(), XMLResource.class));
    context.setActionProcessor(actionProcessor);
    context.setServletContext(getServlet().getServletContext());
  }

  public ActionForward execute(final ActionMapping mapping, final ActionForm actionForm, final HttpServletRequest request, final HttpServletResponse response) throws Exception
  {
    Callback callback = new Callback()
    {
      public Object call()
      {
        mapping.equals("1");
        return null;
      }
    };
    ActionForward forward = null;
    IActionContext context;
    if (actionForm instanceof ActionBean)
    {
      context = (ActionBean) actionForm;
    }
    else
    {
      context = getActionContext();
    }
    ActionProcessorImpl actionProcessorImpl = new ActionProcessorImpl();
    try
    {
      initActionContext(context, mapping, actionForm, request, response, actionProcessorImpl);
      if ((forward = processRecordLocking(context)) != null)
      {
        return forward;
      }
      request.setAttribute(Constants.BEAN_KEY, actionForm);
      actionProcessorImpl.doRegisterControls(context);
      if (this instanceof IProcessBefore)
      {
        if ((forward = ((IProcessBefore) this).processBefore(context)) != null)
        {
          return forward;
        }
      }
      if (actionForm instanceof ActionBean)
      {
        for (Method method : actionForm.getClass().getMethods())
        {
          for (Annotation annotation : method.getDeclaredAnnotations())
          {
            if (annotation instanceof ProcessBefore)
            {
              if ((forward = (ActionForward) method.invoke(actionForm)) != null)
              {
                return forward;
              }
            }
          }
        }
      }
      forward = actionProcessorImpl.execute(context);
      if (forward == null)
      {
        String methodName = DispatchActionHelper.getDispatchMethodName(context.getMapping(), context.getRequest());
        User currentUser = UserUtil.getCurrentUser(context.getRequest());
        if ( null != currentUser )
        {
          String dispatcherRequestPath = (String)context.getRequest().getAttribute(org.apache.catalina.Globals.DISPATCHER_REQUEST_PATH_ATTR);
          if ( "/Menu.do".equals(dispatcherRequestPath) )
          {
            methodName = "?" + context.getRequest().getQueryString();
          }
          else
          {
            methodName = (StringUtil.isEmpty(methodName) ? "" : "?dispatch=" + methodName);
          }
          String actionKey = dispatcherRequestPath + methodName;
          ControlActions actions = (ControlActions) StoreUtil.getApplication(context.getServletContext(), ControlActions.class);
          net.sam.dcl.beans.Action action = actions.getAction(actionKey);
          //если null - пишем в базу или если логировать нужно - логируем (это в одной хранимке)
          if ( null == action || action.isLoggingAction() )
          {
            DbLogging.logActionBySystemName(context, actionKey, context.getRequest().getRemoteAddr());
          }
          if ( null == action ) //кладем в мап
          {
            actions.putAction(new net.sam.dcl.beans.Action("", actionKey));
          }
        }
        
        if (actionForm instanceof ActionBean)
        {
          forward = DispatchActionHelper.dispatchMethod(actionForm, context, null, null);
        }
        else
        {
          if (this instanceof IDispatchable)
          {
            forward = DispatchActionHelper.dispatchMethod(this, context, IActionContext.class, new Object[]{context});
          }
          else
          {
            forward = execute(context);
          }
        }
      }
      if (this instanceof IProcessAfter)
      {
        try
        {
          forward = ((IProcessAfter) this).processAfter(context, forward);
        }
        catch (Exception e)
        {
          forward = null;
          throw e;
        }
      }
      if (actionForm instanceof ActionBean)
      {
        for (Method method : actionForm.getClass().getMethods())
        {
          for (Annotation annotation : method.getDeclaredAnnotations())
          {
            if (annotation instanceof ProcessAfter)
            {
              try
              {
                forward = (ActionForward) method.invoke(actionForm, forward);
              }
              catch (Exception e)
              {
                forward = null;
                throw e;
              }

            }
          }
        }
      }
      if (this instanceof IFormAutoSave)
      {
        saveForm(context);
      }
    }
    catch (ValidationException e)
    {
      forward = context.getMapping().getInputForward();
    }
    catch (Exception e)
    {
      forward = processException(context, forward, e);
    }
    finally
    {
      processFinally(context);
    }
    return forward;
  }

  protected void saveForm(IActionContext context)
  {
    context.getRequest().getSession().setAttribute(IFormAutoSave.SESSION_KEY + context.getMapping().getAttribute(), context.getForm());
  }

  protected void restoreForm(IActionContext context)
  {
    context.setForm((ActionForm) context.getRequest().getSession().getAttribute(IFormAutoSave.SESSION_KEY + context.getMapping().getAttribute()));
    context.getRequest().setAttribute(context.getMapping().getAttribute(), context.getForm());
  }

  public ActionForward processException(IActionContext context, ActionForward forward, Exception e)
  {
    try
    {
      String addInfo = "";
      if (e instanceof VDbException)
      {
        if (((VDbException) e).executionContext != null)
          addInfo = "\r\noriginalSql =" + ((VDbException) e).executionContext.getOriginalSql();
      }
      log.error(e.getMessage() + addInfo, e);
      StrutsUtil.addError(context, "common.msg", e.getMessage(), e);
      if (forward != null)
      {
        return forward;
      }
      Boolean alreadyProcess = (Boolean) context.getRequest().getAttribute("processing.exception");
      if (alreadyProcess != null && alreadyProcess)
      {
        if (context.getMapping().findForward("form") != null)
        {
          return context.getMapping().findForward("form");
        }
        else
        {
          return context.getMapping().findForward("global-error");
        }
      }
      if (StrutsUtil.isAjaxRequest(context.getRequest()))
      {
        if (context.getMapping().findForward("ajax-error-forward").getPath() != null)
        {
          return context.getMapping().findForward("ajax-error-forward");
        }
      }
      else
      {
        if (context.getMapping().getInputForward().getPath() != null)
        {
          return context.getMapping().getInputForward();
        }
      }
      return context.getMapping().findForward("global-error");
    }
    finally
    {
      context.getRequest().setAttribute("processing.exception", Boolean.TRUE);
    }
  }

  public void processFinally(IActionContext context)
  {
    if (!context.getErrors().isEmpty())
    {
      saveErrors(context.getRequest(), context.getErrors());
    }
    if (!context.getMessages().isEmpty())
    {
      saveMessages(context.getRequest(), context.getMessages());
    }
  }

  public ActionForward processRecordLocking(IActionContext context)
  {
    Object skipProcessLocking = context.getRequest().getAttribute("skip.processing.locking");
    if (skipProcessLocking != null)
    {
      return null;
    }
    String lockName = context.getRequest().getParameter(PARAM_LOCK_NAME);
    String lockId = context.getRequest().getParameter(PARAM_LOCK_ID);
    if (!StringUtil.isEmpty(lockName) && !StringUtil.isEmpty(lockId))
    {
      SyncObject syncObjectForLock = new SyncObject(lockName, lockId, context.getRequest().getSession().getId());
      SyncObject syncObjectCurrent = LockedRecords.getLockedRecords().lock(syncObjectForLock);
      if (!syncObjectForLock.equals(syncObjectCurrent))
      {
        //return context.getMapping().findForward("global-lock-error");
        context.getRequest().setAttribute("skip.processing.locking", syncObjectCurrent);
        return context.getMapping().findForward("global-show-prev-response");
      }
    }
    return null;
  }

  public ActionForward execute(IActionContext context) throws Exception
  {
    throw new NotImplementedException();
  }


  public static class NotImplementedException extends RuntimeException
  {
    public NotImplementedException()
    {
      super("Not implemented");
    }
  }

  public String getActionMappingURL(IActionContext context, String action)
  {
    return getActionMappingURL(context, action, false);
  }

  public String getActionMappingURL(IActionContext context, String action, boolean contextRelative)
  {

    HttpServletRequest request = context.getRequest();
    StringBuffer value = new StringBuffer(request.getContextPath());
    ModuleConfig moduleConfig = ModuleUtils.getInstance().getModuleConfig(request);

    if ((moduleConfig != null) && (!contextRelative))
    {
      value.append(moduleConfig.getPrefix());
    }

    // Use our servlet mapping, if one is specified
    String servletMapping = (String) servlet.getServletContext().getAttribute(Globals.SERVLET_KEY);

    if (servletMapping != null)
    {

      String queryString = null;
      int question = action.indexOf("?");
      if (question >= 0)
      {
        queryString = action.substring(question);
      }

      String actionMapping = TagUtils.getInstance().getActionMappingName(action);
      if (servletMapping.startsWith("*."))
      {
        value.append(actionMapping);
        value.append(servletMapping.substring(1));

      }
      else if (servletMapping.endsWith("/*"))
      {
        value.append(
                servletMapping.substring(0, servletMapping.length() - 2));
        value.append(actionMapping);

      }
      else if (servletMapping.equals("/"))
      {
        value.append(actionMapping);
      }
      if (queryString != null)
      {
        value.append(queryString);
      }
    }

    // Otherwise, assume extension mapping is in use and extension is
    // already included in the action property
    else
    {
      if (!action.startsWith("/"))
      {
        value.append("/");
      }
      value.append(action);
    }

    return value.toString();
  }

  interface Callback
  {
    Object call();
  }
}
