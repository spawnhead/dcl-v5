package net.sam.dcl.util;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.tiles.TilesRequestProcessor;
import org.apache.struts.config.ModuleConfig;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.*;
import java.util.Iterator;

import net.sam.dcl.util.classloading.AlwaysLoadClassLoader;
import net.sam.dcl.util.classloading.ClassCashe;

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
public class DebugRequestProcessor extends TilesRequestProcessor {

	// ----------------------------------------------------- Processing Methods


	//AlwaysLoadClassLoader classLoader = new AlwaysLoadClassLoader();

	public void init(ActionServlet servlet, ModuleConfig moduleConfig) throws ServletException {
		super.init(servlet, moduleConfig);
		ClassCashe.init(new File(servlet.getServletContext().getRealPath("."),
																											 "WEB-INF\\classes").getAbsolutePath());
	}

	/**
	 * <p>Return an <code>Action</code> instance that will be used to process
	 * the current request, creating a new one if necessary.</p>
	 *
	 * @param request	The servlet request we are processing
	 * @param response The servlet response we are creating
	 * @param mapping	The mapping we are using
	 * @throws java.io.IOException if an input/output error occurs
	 */
	protected Action processActionCreate(HttpServletRequest request,
																			 HttpServletResponse response,
																			 ActionMapping mapping)
			throws IOException {

		// Acquire the Action instance we will be using (if there is one)
		String className = mapping.getType();
		if (log.isDebugEnabled()) {
			log.debug(" Looking for Action instance for class " + className);
		}

		// :TODO: If there were a mapping property indicating whether
		// an Action were a singleton or not ([true]),
		// could we just instantiate and return a new instance here?

		Action instance = null;

		// Create and return a new Action instance
		if (log.isTraceEnabled()) {
			log.trace("  Creating new Action instance");
		}

		try {
//		    Class action = new AlwaysLoadClassLoader().loadClass(className);
//			  instance = (Action) action.newInstance();
			synchronized (ClassCashe.getClassesCache()) {
				if (ClassCashe.getClassesCache().containsKey(className)) {
					ClassCashe.ObjectInstance objectInstance = (ClassCashe.ObjectInstance) ClassCashe.getClassesCache().get(className);
					if (!objectInstance.isUpToDate()){
						objectInstance.reloadClassAndInstance();
					}
					instance = (Action) objectInstance.getInstance();
				} else {
					new AlwaysLoadClassLoader().loadClass(className);
					ClassCashe.ObjectInstance objectInstance = ClassCashe.getClassesCache().get(className);
					instance = (Action) objectInstance.getInstance();
				}
			}
			// :TODO: Maybe we should propagate this exception
			// instead of returning null.
		} catch (Throwable e) {
			log.error(
					getInternal().getMessage("actionCreate", mapping.getPath()),
					e);

			response.sendError(
					HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
					getInternal().getMessage("actionCreate", mapping.getPath()));

			return (null);
		}

		instance.setServlet(this.servlet);

		return (instance);

	}


	public static void readInputSteamOutputStream(InputStream is, OutputStream out) throws IOException {
		final int BUFFER_SIZE = 10000;
		byte buff[] = new byte[BUFFER_SIZE];
		for (int nRead = is.read(buff); nRead != -1; nRead = is.read(buff)) {
			out.write(buff, 0, nRead);
		}
	}
	public void destroy() {
		  ClassCashe.destroy();
			this.servlet = null;

	}

}
