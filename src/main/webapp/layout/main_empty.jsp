<%@ page import="net.sam.dcl.App" %>
<%@ page import="net.sam.dcl.util.StrutsUtil" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="http://ditchnet.org/jsp-tabs-taglib" prefix="tab" %>

<html>
<head>
  <meta http-equiv="pragma" content="no-cache">
  <meta http-equiv="expires" content="-1">
  <meta http-equiv="content-type" content="text/html; charset=UTF-8">
  <link href="<ctrl:rewrite action='/trusted/css'/>?v=20260120e" type="text/css" rel=stylesheet/>
  <link rel=stylesheet type="text/css" href="<ctrl:rewrite action='/trusted/css'/>?v=20260120e"/>
  <title><bean:message key="app.name"/> v<%=App.VERSION%><%=StrutsUtil.getCurrSubMenuTitleOrFormTitle(pageContext)%></title>
  <%@ include file="/includes/util.jsp" %>
	<script language="JScript" type="text/javascript" src='<ctrl:rewrite page='/includes/list.js'/>?v=20260120h'></script>
	<script language="JScript" type="text/javascript" src='<ctrl:rewrite page='/includes/calendar.js'/>?v=20260120b'></script>
</head>

<body leftmargin=0 rightmargin=0 topmargin=0 bottommargin=0>
<div id="dcl-error-banner" style="display:none; position:relative; width:100%; background:#f8d7da; color:#721c24; border:1px solid #f5c6cb; padding:8px 12px; font-family:Tahoma,sans-serif; font-size:12px; z-index:9999;">
  <span id="dcl-error-banner-text"></span>
  <button type="button" id="dcl-error-banner-ok" style="margin-left:12px; cursor:pointer;">OK</button>
</div>
<script type="text/javascript">
window.__dclShowErrorInBanner = function(text) {
  var el = document.getElementById('dcl-error-banner');
  var txt = document.getElementById('dcl-error-banner-text');
  if (el && txt) { txt.innerHTML = (text != null && text !== '') ? String(text).replace(/</g, '&lt;').replace(/>/g, '&gt;') : ''; el.style.display = (txt.innerHTML ? '' : 'none'); }
};
window.__dclHideErrorBanner = function() {
  var b = document.getElementById('dcl-error-banner');
  if (b) b.style.display = 'none';
};
(function() {
  var ok = document.getElementById('dcl-error-banner-ok');
  if (ok) ok.onclick = function() { __dclHideErrorBanner(); };
})();
</script>
<A name=top></A>
<table cellSpacing=0 cellPadding=0 width='100%' height='100%' border=0>
  <tr>
    <td valign=top height='100%'>
      <table cellSpacing=0 cellPadding=0 width='100%' border=0 height='100%'>

        <%--form--%>
        <tr>
          <td width='100%' class="padding5" valign=top>
            <!----------------body----------------->
            <tiles:insert attribute="body"/>
            <!----------------body----------------->
          </td>
        </tr>

      </table>
    </td>
  </tr>
</table>
<tiles:insert attribute="errors"/>
<iframe name='__reloader'   style='display:none;' src='<ctrl:rewrite action="/trusted/Reloader"/>' APPLICATION='yes' ></iframe>
<script language="JScript" type="text/javascript" src='<ctrl:rewrite page='/includes/wz_tooltip.js'/>'></script>
</body>
<head>
  <meta HTTP-EQUIV="Pragma" CONTENT="no-cache">
</head>
</html>
