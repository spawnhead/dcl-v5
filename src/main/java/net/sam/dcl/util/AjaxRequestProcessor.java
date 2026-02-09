package net.sam.dcl.util;

import org.apache.struts.tiles.TilesRequestProcessor;
import org.apache.struts.action.*;
import org.apache.struts.config.ForwardConfig;
import org.apache.struts.Globals;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * <p><strong>RequestProcessor</strong> contains the processing logic that
 * the @link(ActionServlet) performs as it receives each servlet request
 * from the container. You can customize the request processing behavior by
 * subclassing this class and overriding the method(s) whose behavior you are
 * interested in changing.</p>
 *
 * @version $Revision: 1.45 $ $Date: 2004/04/25 02:29:41 $
 * @since Struts 1.1
 */
public class AjaxRequestProcessor extends TilesRequestProcessor {

	// ----------------------------------------------------- Processing Methods


	protected boolean processValidate(HttpServletRequest request, HttpServletResponse response, ActionForm form, ActionMapping mapping)
			throws IOException, ServletException {
		if (form == null) {
				return (true);
		}
																					// Was this request cancelled?
		if (request.getAttribute(Globals.CANCEL_KEY) != null) {
				if (log.isDebugEnabled()) {
						log.debug(" Cancelled transaction, skipping validation");
				}
				return (true);
		}

		// Has validation been turned off for this mapping?
		if (!mapping.getValidate()) {
				return (true);
		}

		// Call the form bean's validation method
		if (log.isDebugEnabled()) {
				log.debug(" Validating input form properties");
		}
		ActionMessages errors = form.validate(mapping, request);
		if ((errors == null) || errors.isEmpty()) {
				if (log.isTraceEnabled()) {
						log.trace("  No errors detected, accepting input");
				}
				return (true);
		}

		// Special handling for multipart request
		if (form.getMultipartRequestHandler() != null) {
				if (log.isTraceEnabled()) {
						log.trace("  Rolling back multipart request");
				}
				form.getMultipartRequestHandler().rollback();
		}

		// Was an input path (or forward) specified for this mapping?
		String input;
		if (StrutsUtil.isAjaxRequest(request)){
			input = mapping.findForward("ajax").getPath() ;
		} else {
			input = mapping.getInput();
		}
		if (input == null) {
				if (log.isTraceEnabled()) {
						log.trace("  Validation failed but no input form available");
				}
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
													 getInternal().getMessage("noInput",
																										mapping.getPath()));
				return (false);
		}

		// Save our error messages and return to the input form if possible
		if (log.isDebugEnabled()) {
				log.debug(" Validation failed, returning to '" + input + "'");
		}
		request.setAttribute(Globals.ERROR_KEY, errors);

		if (moduleConfig.getControllerConfig().getInputForward()) {
				ForwardConfig forward = mapping.findForward(input);
				processForwardConfig( request, response, forward);
		} else {
				internalModuleRelativeForward(input, request, response);
		}

		return (false);
	}


	/**
	 * <p>Process an <code>HttpServletRequest</code> and create the
	 * corresponding <code>HttpServletResponse</code> or dispatch
	 * to another resource.</p>
	 *
	 * @param request The servlet request we are processing
	 * @param response The servlet response we are creating
	 *
	 * @exception IOException if an input/output error occurs
	 * @exception ServletException if a processing exception occurs
	 */
	public void process(HttpServletRequest request,
											HttpServletResponse response)
			throws IOException, ServletException {

			// Wrap multipart requests with a special wrapper
			 request = processMultipart(request);

			// Identify the path component we will use to select a mapping
			String path = processPath(request, response);
			if (path == null) {
					return;
			}

			if (log.isDebugEnabled()) {
					log.debug("Processing a '" + request.getMethod() +
										"' for path '" + path + "'");
			}

			// Select a Locale for the current user if requested
			processLocale(request, response);

			// Set the content type and no-caching headers if requested
			processContent(request, response);
			processNoCache(request, response);

			// General purpose preprocessing hook
			if (!processPreprocess(request, response)) {
					return;
			}

			this.processCachedMessages(request, response);

			// Identify the mapping for this request
			ActionMapping mapping = processMapping(request, response, path);
			if (mapping == null) {
					return;
			}

			// Check for any role required to perform this action
			if (!processRoles(request, response, mapping)) {
					return;
			}

			// Process any ActionForm bean related to this request
			ActionForm form = processActionForm(request, response, mapping);
		  if ("request".equals(mapping.getScope())) {
				syncCall(request, response, mapping, form);	
			} else {
				synchronized (form){
					syncCall(request, response, mapping, form);
				}
			}
	}

	protected void syncCall(HttpServletRequest request, HttpServletResponse response, ActionMapping mapping, ActionForm form)
			throws ServletException, IOException {
		processPopulate(request, response, form, mapping);
		if (!processValidate(request, response, form, mapping)) {
				return;
		}

		// Process a forward or include specified by this mapping
		if (!processForward(request, response, mapping)) {
				return;
		}

		if (!processInclude(request, response, mapping)) {
				return;
		}

		// Create or acquire the Action instance to process this request
		Action action = processActionCreate(request, response, mapping);
		if (action == null) {
				return;
		}

		// Call the Action instance itself
		ActionForward forward =
				processActionPerform(request, response,
														 action, form, mapping);

		// Process the returned ActionForward instance
		processForwardConfig(request, response, forward);
	}

}
