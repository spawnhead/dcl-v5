<%@ page import="org.apache.commons.logging.Log,
                 org.apache.commons.logging.LogFactory,
                 java.io.PrintWriter,
                 java.io.IOException "%>
<%@ page import="net.sam.dcl.util.StringUtil"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%!
  void printError(JspWriter out,Throwable t) throws IOException {
    out.print("<br><pre class=error><span class=error>Message:</span><br>");
    out.println(t.getMessage());
    out.println("<pre>");
  
    out.print("<br><pre class=error><span class=error>Details:</span><br>");
    PrintWriter wr = new PrintWriter(out);
    t.printStackTrace(wr);
    out.println("<pre><br>");                                                
  }
%>

<%

  System.out.println("1");
  Throwable t = (Throwable)request.getAttribute("javax.servlet.error.exception");
  if (t != null){
    if (t instanceof ServletException && ((ServletException)t).getRootCause() != null){
      t = ((ServletException)t).getRootCause();
    }
    Log log = LogFactory.getLog(this.getClass().getName());
    log.error("unexpected error",t);
  } else {
    if (request.getAttribute("msg")!=null){
      out.println(request.getAttribute("msg"));
    }
  }
%>


