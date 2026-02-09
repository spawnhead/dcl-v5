package net.sam.dcl.taglib;

import org.apache.struts.taglib.tiles.ComponentConstants;
import org.apache.struts.taglib.tiles.InsertTag;
import org.apache.struts.tiles.*;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import java.io.PrintWriter;
import java.util.Map;

/**
 * @author DG
 *         Date: 13-Jun-2007
 *         Time: 20:14:05
 */
public class InsertTagWithExc extends InsertTag {


	protected TagHandler processDefinition(ComponentDefinition definition) throws JspException {
		// Declare local variable in order to not change Tag attribute values.
		String role = this.role;
		String page = this.page;
		Controller controller = null;

		try {
			controller = definition.getOrCreateController();

			// Overload definition with tag's template and role.
			if (role == null) {
				role = definition.getRole();
			}

			if (page == null) {
				page = definition.getTemplate();
			}

			if (controllerName != null) {
				controller =
					ComponentDefinition.createController(
						controllerName,
						controllerType);
			}

			// Can check if page is set
			return new InsertHandlerWithExc(
				definition.getAttributes(),
				page,
				role,
				controller);

		} catch (InstantiationException ex) {
			throw new JspException(ex.getMessage());
		}

	}

	public TagHandler processTypedAttribute(AttributeDefinition value) throws JspException {
		if (value instanceof DirectStringAttribute) {
			return new DirectStringHandler((String) value.getValue());

		} else if (value instanceof DefinitionAttribute) {
			return processDefinition((ComponentDefinition) value.getValue());

		} else if (value instanceof DefinitionNameAttribute) {
			return processDefinitionName((String) value.getValue());
		}

		return new InsertHandlerWithExc(
			(String) value.getValue(),
			role,
			getController());

	}

	public TagHandler processUrl(String url) throws JspException {
		return new InsertHandlerWithExc(url, role, getController());
	}
	private Controller getController() throws JspException {
		if (controllerType == null) {
			return null;
		}

		try {
			return ComponentDefinition.createController(
				controllerName,
				controllerType);

		} catch (InstantiationException ex) {
			throw new JspException(ex.getMessage());
		}
	}

	protected class InsertHandlerWithExc extends InsertTag.InsertHandler {
		public InsertHandlerWithExc(Map attributes, String page, String role, Controller controller) {
			super(attributes, page, role, controller);
		}

		public InsertHandlerWithExc(String page, String role, Controller controller) {
			super(page, role, controller);
		}


		protected void processException(Throwable ex, String msg) throws JspException {

			if (msg == null) {
				msg = ex.getMessage();
			}

			if (log.isDebugEnabled()) { // show full trace
				log.debug(msg, ex);

				System.err.println(msg);
				ex.printStackTrace(new PrintWriter(System.err, true));
			} else { // show only message
				System.err.println(msg);
			}
			pageContext.setAttribute(
					ComponentConstants.EXCEPTION_KEY,
					ex,
					PageContext.REQUEST_SCOPE);
			throw new JspException(msg);

		}
	}
}
