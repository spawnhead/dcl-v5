<%@ page import="net.sam.dcl.beans.User" %>
<%@ page import="net.sam.dcl.util.StoreUtil" %>
<%@ page import="net.sam.dcl.util.TextResource" %>
<%@ page import="net.sam.dcl.util.UserUtil" %>
<%@ page import="net.sam.dcl.beans.Message" %>
<%@ page import="net.sam.dcl.controller.IActionContext" %>
<%@ page import="net.sam.dcl.controller.ActionContext" %>
<%@ page import="net.sam.dcl.db.VDbConnection" %>
<%@ page import="net.sam.dcl.db.VDbConnectionManager" %>
<%@ page import="net.sam.dcl.util.DAOUtils" %>
<%@ page import="net.sam.dcl.db.VParameter" %>
<%@ page import="java.sql.Types" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>

<%!
  private void saveInformationMessages(Message message)
  {
    IActionContext context = ActionContext.threadInstance();
    VDbConnection conn = null;
    try
    {
      conn = VDbConnectionManager.getVDbConnection();
      conn.beginTransaction();
	    String sql = "insert\n" +
	                  "     into dcl_inf_message (\n" +
	                  "       usr_id,\n" +
	                  "       inm_message\n" +
	                  "     ) values ( \n" +
	                  "       :usr_id, \n" +
	                  "       :message \n" +
	                  "     )";

			VParameter parameters = new VParameter();
      parameters.add("usr_id", message.getUser().getUsr_id(), Types.VARCHAR);
      parameters.add("message", message.getMessage(), Types.VARCHAR);
      DAOUtils.update(conn, sql, null, parameters);
      conn.commit();
    }
    catch (Exception e)
    {
      throw new RuntimeException(e);
    }
    finally
    {
      if (conn != null) conn.close();
    }
 	}
  %>

<p align=center></p>

<p align=center><b><font size=3 face="Arial">
  <%
    TextResource slogan = (TextResource) StoreUtil.getApplication(getServletConfig().getServletContext(), TextResource.class);
  %>
  <%=slogan.getRandom()%>
</font></b></p>

<script language="JScript" type="text/javascript">
  function forwardToPersonalOffice()
  {
    document.location = '<html:rewrite action="/OfficeAction.do?dispatch=input"/>';
  }
  <%
  User currentUser = UserUtil.getCurrentUser(request);
  if (currentUser.isManager())
  {
    saveInformationMessages(new Message(slogan.getRandom(), currentUser));
  %>
    initFunctions.push(forwardToPersonalOffice);
  <%
  }
  %>
</script>