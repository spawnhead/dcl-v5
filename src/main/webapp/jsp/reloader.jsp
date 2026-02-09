<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ page import="net.sam.dcl.config.Config"%>
<%@ page import="net.sam.dcl.App"%>
<%@ page import="net.sam.dcl.action.PrepareAppToShutdownAction"%>
<%@ page import="net.sam.dcl.util.StringUtil"%>
<html>
<head>
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="-1">
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
</head>
<body>
<%
  String userMsg = (String) session.getAttribute(PrepareAppToShutdownAction.USER_MESSAGE);
  if (!StringUtil.isEmpty(userMsg)){
    session.removeAttribute(PrepareAppToShutdownAction.USER_MESSAGE);
  %><%@include file="/layout-items/errors-in-dialog2.jsp" %><%
  }
%>

<script LANGUAGE="JScript">
function reloadPage(){
	if (!parent.__formloc){
		document.location.reload();
	}
}
<%
  if (!StringUtil.isEmpty(userMsg)){
  %>
    showCustomError(null,'<%=userMsg%>');
  <%
}
%>
setInterval(reloadPage,<%=Config.getNumber("reloader.sleep",60000)%>);
</script>
</body>
</html>