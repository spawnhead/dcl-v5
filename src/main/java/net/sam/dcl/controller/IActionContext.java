package net.sam.dcl.controller;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessages;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletContext;

import net.sam.dcl.util.XMLResource;
import net.sam.dcl.db.VDbConnection;
import net.sam.dcl.controller.processor.ActionProcessor;

/**
 * @author DG
 *         Date: 15-Jul-2007
 *         Time: 18:06:19
 */
public interface IActionContext {
	void setMapping(ActionMapping mapping);

	void setForm(ActionForm form);

	void setResponse(HttpServletResponse response);

	void setRequest(HttpServletRequest request);

	void setErrors(ActionMessages errors);

	void setMessages(ActionMessages messages);

	void setSqlResource(XMLResource sqlResource);

	void setConnection(VDbConnection conn);

	void setActionProcessor(ActionProcessor actionProcessor);

	void setServletContext(ServletContext servletContext);

	ActionMapping getMapping();

	ActionForm getForm();

	HttpServletResponse getResponse();

	HttpServletRequest getRequest();

	ActionMessages getErrors();

	ActionMessages getMessages();

	XMLResource getSqlResource();

	VDbConnection getConnection();

	ActionProcessor getActionProcessor();

	ServletContext getServletContext();
}
