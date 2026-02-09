package net.sam.dcl.filters;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.StringWriter;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

import net.sam.dcl.navigation.PermissionChecker;
import net.sam.dcl.util.ResponseImpl;
import net.sam.dcl.util.StoreUtil;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.config.Config;
import net.sam.dcl.controller.ActionContext;
import com.sun.security.auth.callback.DialogCallbackHandler;

/**
 * @author: DG
 * Date: Aug 9, 2005
 * Time: 1:13:07 PM
 */

public class ResponseCollectFilter implements Filter {
  public static class RequestResult{
    String stringResult = null;
    byte[] arrayResult = null;
    public RequestResult(String stringResult) {
      this.stringResult = stringResult;
    }
    public RequestResult(byte[] arrayResult) {
      this.arrayResult = arrayResult;
    }
    public void toResponse(ServletResponse response) throws IOException {
      try {
        if (stringResult != null){
          response.getWriter().print(stringResult);
        } else {
          response.getOutputStream().write(arrayResult);
        }
      } catch (IOException ioe) {
        // Swallow client-abort/EoF (browser closed socket, etc.) to avoid noisy stacktraces
        if (ResponseCollectFilter.isClientAbort(ioe)) {
          return;
        }
        throw ioe;
      }
    }
    public StringBuffer toStringBuffer(String charsetName) throws UnsupportedEncodingException {
      if (stringResult != null){
        return new StringBuffer(stringResult);
      } else {
        return new StringBuffer(new String(arrayResult,charsetName));
      }
    }
  }
  protected static Log log = LogFactory.getLog(ResponseCollectFilter.class);
  FilterConfig config = null;

  public void doFilter(ServletRequest request, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
		HttpServletRequest servletRequest = (HttpServletRequest) request;
		String uri = servletRequest.getRequestURI().substring(servletRequest.getContextPath().length(),servletRequest.getRequestURI().length());
		if (!StringUtil.isEmpty(servletRequest.getQueryString())){
			uri +="?"+servletRequest.getQueryString();
		}

		if (uri.matches(Config.getString("responseCollect.exclude"))){
			chain.doFilter(request, resp);
		} else {
			StringWriter stringWriter = new StringWriter(100*1024);
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream(100*1024);

			ResponseImpl wrapper = new ResponseImpl((HttpServletResponse) resp,stringWriter,outputStream);
			chain.doFilter(request, wrapper);
			RequestResult requestResult;
			if (wrapper.isWriterGetted()){
				stringWriter.flush();
				requestResult = new RequestResult(stringWriter.toString().getBytes(Config.getString("global.encoding")));
				//resp.getWriter().print(res);
			} else {
				outputStream.flush();
				requestResult = new RequestResult(outputStream.toByteArray());
				//resp.getOutputStream().write(outputStream.toByteArray());
			}
			// Явно задаем charset для HTML-ответов, чтобы браузер не гадал (иначе кириллица ломается)
			String charsetName = Config.getString("global.encoding");
			if (charsetName == null || charsetName.isEmpty()) {
				charsetName = "UTF-8";
			}
			String contentType = resp.getContentType();
			if (contentType == null) {
				resp.setContentType("text/html; charset=" + charsetName);
			} else if (contentType.startsWith("text/") && !contentType.toLowerCase().contains("charset=")) {
				resp.setContentType(contentType + "; charset=" + charsetName);
			}
			resp.setCharacterEncoding(charsetName);
			if (isNeedResponseCollect((HttpServletRequest) request)){
			  StoreUtil.putSession((HttpServletRequest) request,requestResult);
			}
			try {
			  requestResult.toResponse(resp);
			} catch (IOException ioe) {
			  if (isClientAbort(ioe)) {
			    return;
			  } else {
			    throw ioe;
			  }
			} finally {
			  stringWriter.close();
			  outputStream.close();
			}
		}
		//ActionContext.threadInstance().clear();
	}

  public void init(FilterConfig config) throws ServletException {
    this.config = config;
  }
  public void destroy() {

  }
  private static final String NEED_RESPONSE_COLLECT_ATTR = "__NEED_RESPONSE_COLLECT__";
  static public void setNeedResponseCollect(HttpServletRequest request){
    request.setAttribute(NEED_RESPONSE_COLLECT_ATTR,"1");
  }
  static public void resetNeedResponseCollect(HttpServletRequest request){
    request.setAttribute(NEED_RESPONSE_COLLECT_ATTR,null);
  }
  static public boolean isNeedResponseCollect(HttpServletRequest request){
    return request.getAttribute(NEED_RESPONSE_COLLECT_ATTR)!=null;
  }

  // Detect client-abort equivalents across containers (Tomcat/Jetty/JDK)
  private static boolean isClientAbort(Throwable t){
    while (t != null){
      String n = t.getClass().getName();
      if ("org.apache.catalina.connector.ClientAbortException".equals(n)
          || "org.eclipse.jetty.io.EofException".equals(n)
          || "java.io.EOFException".equals(n)){
        return true;
      }
      t = t.getCause();
    }
    return false;
  }
}
