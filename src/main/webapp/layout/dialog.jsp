<%@ page import="net.sam.dcl.App"%>
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
    <link href="<ctrl:rewrite action='/trusted/css'/>?v=20260120e" type="text/css" rel=stylesheet />
    <link rel=stylesheet type="text/css" href="<ctrl:rewrite action='/trusted/css'/>?v=20260120e" />
    <title><bean:message key="app.name"/> v<%=App.VERSION%><%=StrutsUtil.getCurrSubMenuTitleOrFormTitle(pageContext)%></title>
    <%@include file="/includes/util.jsp" %>
    <%--<script language="JScript" type="text/javascript" src='<ctrl:rewrite action='/trusted/list'/>'></script>--%>
    <%--<script language="JScript" type="text/javascript" src='<ctrl:rewrite action='/trusted/calendar'/>'></script>--%>
    <script language="JScript" type="text/javascript" src='<ctrl:rewrite page='/includes/list.js'/>?v=20260120h'></script>
    <script language="JScript" type="text/javascript" src='<ctrl:rewrite page='/includes/calendar.js'/>?v=20260120b'></script>
		<script language="JScript" type="text/javascript" src='<ctrl:rewrite page='/includes/prototype.js'/>'></script>
		<script type="text/javascript" src='<ctrl:rewrite page="/includes/cerny/js/console/console.js"/>' ></script>
		<script type="text/javascript" src='<ctrl:rewrite page="/includes/cerny/js/console/PopupWindow.js"/>' ></script>
		<script type="text/javascript" src='<ctrl:rewrite page="/includes/cerny/js/cerny.conf.js"/>?v=20260202a'></script>
		<script type="text/javascript" src='<ctrl:rewrite page="/includes/cerny/js/cerny.js"/>?v=20260202a'></script>
		<script type="text/javascript" src='<ctrl:rewrite page="/includes/cerny/js/json/TextPrettyPrinter.js"/>?v=20260202a'></script>

	</head>
  <body >
    <A name=top></A>
    <table id=outerTable name=outerTable cellSpacing=0 cellPadding=0 width='100%' height='100%' border=0 class="padding5">
      <tr>
        <td valign=top height='100%'>


<!----------------body----------------->
<tiles:insert attribute="body"/>
<!----------------body----------------->

        </td>
      </tr>
    </table>
    <%--<tiles:insert attribute="errors"/>--%>
  </body>
  <head>
    <meta HTTP-EQUIV="Pragma" CONTENT="no-cache">
  </head>
</html>
