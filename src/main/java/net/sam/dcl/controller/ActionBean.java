package net.sam.dcl.controller;

import net.sam.dcl.controller.processor.ActionProcessor;
import net.sam.dcl.db.VDbConnection;
import net.sam.dcl.util.XMLResource;
import net.sam.dcl.util.HibernateUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.Validator;
import org.apache.commons.validator.ValidatorException;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.validator.Resources;
import org.hibernate.Session;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ActionBean extends BaseForm implements IActionContext
{
  protected static Log log = LogFactory.getLog(ActionBean.class);
  protected ActionMapping mapping;
  protected HttpServletRequest request;
  protected HttpServletResponse response;

  protected ActionMessages errors = null;

  protected ActionMessages messages = null;

  protected XMLResource sqlResource = null;

  protected VDbConnection connection = null;

  protected ActionProcessor actionProcessor = null;

  protected ServletContext servletContext = null;
  protected Session hibSession = null;

  @ProcessBefore
  public void processBefore()
  {
    hibSession = HibernateUtil.getSessionFactory().getCurrentSession();
  }

  public void setMapping(ActionMapping mapping)
  {
    this.mapping = mapping;
  }

  public void setRequest(HttpServletRequest request)
  {
    this.request = request;
  }

  public void setResponse(HttpServletResponse response)
  {
    this.response = response;
  }

  public void setErrors(ActionMessages errors)
  {
    this.errors = errors;
  }

  public void setMessages(ActionMessages messages)
  {
    this.messages = messages;
  }

  public void setSqlResource(XMLResource sqlResource)
  {
    this.sqlResource = sqlResource;
  }

  public void setConnection(VDbConnection connection)
  {
    this.connection = connection;
  }

  public void setActionProcessor(ActionProcessor actionProcessor)
  {
    this.actionProcessor = actionProcessor;
  }

  public void setServletContext(ServletContext servletContext)
  {
    this.servletContext = servletContext;
  }

  public void setForm(ActionForm form)
  {
    assert form == this;
  }

  public ActionMapping getMapping()
  {
    return mapping;
  }

  public HttpServletRequest getRequest()
  {
    return request;
  }

  public HttpServletResponse getResponse()
  {
    return response;
  }

  public ActionMessages getErrors()
  {
    return errors;
  }

  public ActionMessages getMessages()
  {
    return messages;
  }

  public XMLResource getSqlResource()
  {
    return sqlResource;
  }

  public VDbConnection getConnection()
  {
    return connection;
  }

  public ActionProcessor getActionProcessor()
  {
    return actionProcessor;
  }

  public ServletContext getServletContext()
  {
    return servletContext;
  }

  public ActionForm getForm()
  {
    return this;
  }

  public void initFromActionContext(IActionContext context)
  {
    mapping = context.getMapping();
    request = context.getRequest();
    response = context.getResponse();
    errors = context.getErrors();
    messages = context.getMessages();
    sqlResource = context.getSqlResource();
    connection = context.getConnection();
    actionProcessor = context.getActionProcessor();
    servletContext = context.getServletContext();
  }

  /**
   * Validate the properties that have been set from this HTTP request,
   * and return an <code>ActionErrors</code> object that encapsulates any
   * validation errors that have been found.  If no errors are found, return
   * <code>null</code> or an <code>ActionErrors</code> object with no
   * recorded error messages.
   *
   * @param mapping The mapping used to select this instance
   * @param request The servlet request we are processing
   * @return <code>ActionErrors</code> object that encapsulates any  validation errors
   */
  public ActionErrors validate(ActionMapping mapping,
                               HttpServletRequest request)
  {

    ServletContext application = getServlet().getServletContext();
    ActionErrors errors = new ActionErrors();

    String validationKey = getValidationKey(mapping, request);
    internalValidate(validationKey, application, request, errors, this);

    String dispatchName = DispatchActionHelper.getDispatchMethodName(mapping, request);
    if (dispatchName != null)
    {
      internalValidate(validationKey + ":" + dispatchName, application, request, errors, this);
    }

    return errors;
  }

  private boolean internalValidate(String validationKey, ServletContext application, HttpServletRequest request, ActionMessages errors, Object object)
  {
    Validator validator = Resources.initValidator(validationKey,
            object,
            application, request,
            errors, 0);

    try
    {
      int errCount = errors.size();
      validator.validate();
      return errCount == errors.size();
    }
    catch (ValidatorException e)
    {
      throw new RuntimeException(e);
    }
  }

  protected boolean validate(String validationKey, Object object)
  {
    return internalValidate(validationKey, servletContext, request, errors, object);
  }

  /**
   * Returns the Validation key.
   *
   * @param mapping The mapping used to select this instance
   * @param request The servlet request we are processing
   * @return validation key - the form element's name in this case
   */
  public String getValidationKey(ActionMapping mapping,
                                 HttpServletRequest request)
  {
    return mapping.getPath();
	}

}
