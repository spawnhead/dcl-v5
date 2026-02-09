<%@ page import="net.sam.dcl.App"%>
<%@ page import="net.sam.dcl.action.Outline" %>
<%@ page import="net.sam.dcl.util.StrutsUtil" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>

<html>
  <head>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="expires" content="-1">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <link href="<ctrl:rewrite action='/trusted/css'/>" type="text/css" rel=stylesheet />
    <link rel=stylesheet type="text/css" href="<ctrl:rewrite action='/trusted/css'/>" />
    <title><bean:message key="app.name"/> v<%=App.VERSION%><%=StrutsUtil.getCurrSubMenuTitleOrFormTitle(pageContext)%></title>
	</head>
  <body leftmargin="0" RIGHTMARGIN=0 TOPMARGIN=0 BOTTOMMARGIN=0>
<!----------------body----------------->
<tiles:insert attribute="body"/>
<!----------------body----------------->
	</body>
  <head>
    <meta HTTP-EQUIV="Pragma" CONTENT="no-cache">
  </head>
</html>
