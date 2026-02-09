package net.sam.dcl.controller;

import net.sam.dcl.controller.processor.ActionProcessor;
import net.sam.dcl.db.VDbConnection;
import net.sam.dcl.util.XMLResource;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletContext;

/**
 * Action context.
 *
 * @author Dmitry Genov
 * @version $Revision: 1.33 $
 */
public class ActionContext implements IActionContext
{
  /**
   * Mapping
   */
  private ActionMapping mapping;
  /**
   * Form
   */
  private ActionForm form;
  /**
   * Request
   */
  private HttpServletRequest request;
  /**
   * Response
   */
  private HttpServletResponse response;
  /**
   * Contains errors, occured during processing user request
   */
  private ActionMessages errors = null;

  private ActionMessages messages = null;

  private XMLResource sqlResource = null;

  private VDbConnection connection = null;

  private ActionProcessor actionProcessor = null;

  private ServletContext servletContext = null;

  public ActionContext()
  {
    context.set(this);
  }

  /**
   * Constructor
   *
   * @param mapping  mapping
   * @param form     form
   * @param request  request
   * @param response response
   */

  public ActionContext(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
  {
    this.mapping = mapping;
    this.form = form;
    this.request = request;
    this.response = response;
  }


  public ActionMapping getMapping()
  {
    return mapping;
  }

  public void setMapping(ActionMapping mapping)
  {
    this.mapping = mapping;
  }

  public ActionForm getForm()
  {
    return form;
  }

  public void setForm(ActionForm form)
  {
    this.form = form;
  }

  public HttpServletResponse getResponse()
  {
    return response;
  }

  public void setResponse(HttpServletResponse response)
  {
    this.response = response;
  }

  public HttpServletRequest getRequest()
  {
    return request;
  }

  public void setRequest(HttpServletRequest request)
  {
    this.request = request;
  }

  public ActionMessages getErrors()
  {
    return errors;
  }

  public void setErrors(ActionMessages errors)
  {
    this.errors = errors;
  }

  public ActionMessages getMessages()
  {
    return messages;
  }

  public void setMessages(ActionMessages messages)
  {
    this.messages = messages;
  }

  public XMLResource getSqlResource()
  {
    return sqlResource;
  }

  public void setSqlResource(XMLResource sqlResource)
  {
    this.sqlResource = sqlResource;
  }

  public VDbConnection getConnection()
  {
    return connection;
  }

  public void setConnection(VDbConnection conn)
  {
    this.connection = conn;
  }

  public ActionProcessor getActionProcessor()
  {
    return actionProcessor;
  }

  public void setActionProcessor(ActionProcessor actionProcessor)
  {
    this.actionProcessor = actionProcessor;
  }

  public ServletContext getServletContext()
  {
    return servletContext;
  }

  public void setServletContext(ServletContext servletContext)
  {
    this.servletContext = servletContext;
  }

  protected static ThreadLocal context = new ThreadLocal();

  public static IActionContext threadInstance()
  {
    return (IActionContext) context.get();
  }

  public void clear()
  {
    context.set(null);
    mapping = null;
    form = null;
    request = null;
    response = null;
    errors = null;
    messages = null;
    sqlResource = null;
    connection = null;
    actionProcessor = null;
    servletContext = null;
  }
}
