<%@ page import="net.sam.dcl.App"%>
<%@ page import="net.sam.dcl.action.Outline" %>
<%@ page import="net.sam.dcl.util.StrutsUtil" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
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
    <link href="<ctrl:rewrite action='/trusted/css'/>?v=20260120e" type="text/css" rel=stylesheet />
    <link rel=stylesheet type="text/css" href="<ctrl:rewrite action='/trusted/css'/>?v=20260120e" />
    <link rel=stylesheet type="text/css" href="<ctrl:rewrite page='/includes/xmenu.css'/>" />
    <title><bean:message key="app.name"/> v<%=App.VERSION%><%=StrutsUtil.getCurrSubMenuTitleOrFormTitle(pageContext)%></title>
    <%@include file="/includes/util.jsp" %>
    <script language="JScript" type="text/javascript" src='<ctrl:rewrite page='/includes/list.js'/>?v=20260120h'></script>
    <script language="JScript" type="text/javascript" src='<ctrl:rewrite page='/includes/calendar.js'/>?v=20260120b'></script>
    <script language="JScript" type="text/javascript" src='<ctrl:rewrite page='/includes/prototype.js'/>'></script>
    <script language="JScript" type="text/javascript" src='<ctrl:rewrite page='/includes/cssexpr.js'/>'></script>
    <script language="JScript" type="text/javascript" src='<ctrl:rewrite page='/includes/xmenu.js'/>'></script>
    <script type="text/javascript" src='<ctrl:rewrite page="/includes/cerny/js/console/console.js"/>' ></script>
    <script type="text/javascript" src='<ctrl:rewrite page="/includes/cerny/js/console/PopupWindow.js"/>'></script>
    <script type="text/javascript" src='<ctrl:rewrite page="/includes/cerny/js/cerny.conf.js"/>?v=20260202a'></script>
    <script type="text/javascript" src='<ctrl:rewrite page="/includes/cerny/js/cerny.js"/>?v=20260202a'></script>
    <script type="text/javascript" src='<ctrl:rewrite page="/includes/cerny/js/json/TextPrettyPrinter.js"/>?v=20260202a'></script>
    <script type="text/javascript" src='<ctrl:rewrite page="/includes/tabs.js"/>'></script>
    <link rel=stylesheet type="text/css" href="<ctrl:rewrite page='/includes/tabs.css'/>" />
    <style type="text/css">
      #dcl-preloader {
        position: fixed;
        inset: 0;
        display: none;
        background: rgba(0, 0, 0, 0.25);
        z-index: 9998;
        font-family: Tahoma, sans-serif;
      }
      #dcl-preloader-box {
        position: absolute;
        left: 50%;
        top: 50%;
        width: 320px;
        transform: translate(-50%, -50%);
        background: #ffffff;
        border: 1px solid #c7c7c7;
        box-shadow: 0 6px 16px rgba(0, 0, 0, 0.25);
        padding: 12px 14px;
      }
      #dcl-preloader-text {
        font-size: 12px;
        color: #333333;
        margin-bottom: 8px;
      }
      #dcl-preloader-bar {
        position: relative;
        width: 100%;
        height: 14px;
        background: #f0f0f0;
        border: 1px solid #b7b7b7;
        overflow: hidden;
      }
      #dcl-preloader-bar-fill {
        height: 100%;
        width: 0%;
        background: linear-gradient(
          135deg,
          rgba(255, 255, 255, 0.25) 25%,
          rgba(255, 255, 255, 0.05) 25%,
          rgba(255, 255, 255, 0.05) 50%,
          rgba(255, 255, 255, 0.25) 50%,
          rgba(255, 255, 255, 0.25) 75%,
          rgba(255, 255, 255, 0.05) 75%,
          rgba(255, 255, 255, 0.05)
        ),
        #4a90e2;
        background-size: 28px 28px;
        transition: width 120ms linear;
        animation: dcl-preloader-stripes 0.7s linear infinite;
      }
      @keyframes dcl-preloader-stripes {
        0% { background-position: 0 0; }
        100% { background-position: 28px 28px; }
      }
      #dcl-preloader-percent {
        font-size: 11px;
        color: #666666;
        margin-top: 6px;
        text-align: right;
      }
    </style>
  </head>

  <body leftmargin=0 rightmargin=0 topmargin=0 bottommargin=0>

    <%-- Баннер ошибок над контентом: виден в снимке страницы для автоматизированных тестов и отладки --%>
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

    <div id="dcl-preloader">
      <div id="dcl-preloader-box">
        <div id="dcl-preloader-text">Загрузка страницы...</div>
        <div id="dcl-preloader-bar">
          <div id="dcl-preloader-bar-fill"></div>
        </div>
        <div id="dcl-preloader-percent">0%</div>
      </div>
    </div>
    <script type="text/javascript">
      if (typeof __dclStartPagePreloader === 'function') {
        __dclStartPagePreloader();
      }
    </script>

    <div id="__control-comment-form" style="display:none;" >
      <div style="padding:2px">
        <textarea name="__control-comment" style="width:346px" rows=10></textarea><br>
        <div align="right"><input type="button"  onclick="__saveControlComment()" class="width80" value="<bean:message key="button.save"/>" style="text-align:right"/></div>
      </div>
    </div>

    <A name=top></A>
    <table cellSpacing=0 cellPadding=0 width='100%' height='100%' border=0 >
      <tr>
        <td valign=top height='100%'>
         <table cellSpacing=0 cellPadding=0 width='100%' border=0 height='100%'>
            <%--top--%>
            <tr>
              <td height=1 width='100%'>


<!----------------header----------------->
<tiles:insert attribute="header">
  <tiles:useAttribute name="menuActive" scope="request"/>

  <tiles:useAttribute name="menu"/>
  <tiles:put name="menu"  value="${menu}"/>
</tiles:insert>
<!----------------header----------------->

              </td>
            </tr>
            <%--form--%>
            <tr >
              <td width='100%' class="padding5" valign=top >
                <h2>
                  <jsp:include page="/layout-items/title.jsp" />
                </h2>
                <div id=prn name=prn></div>


<!----------------body----------------->
<tiles:insert attribute="body"/>
<!----------------body----------------->


              </td>
            </tr>
            <%--footer--%>
            <tr>
              <td height=1 width='100%'>


<!----------------footer----------------->
<tiles:insert attribute="footer"/>
<!----------------footer----------------->


              </td>
            </tr>
          </table>
        </td>
      </tr>
    </table>
    <tiles:insert attribute="errors"/>
    <iframe name='__reloader'   style='display:none;' src='<ctrl:rewrite action="/trusted/Reloader" />' APPLICATION='yes' ></iframe>
    <script language="JScript" type="text/javascript" src='<ctrl:rewrite page='/includes/wz_tooltip.js'/>'></script>
  </body>
  <head>
    <meta HTTP-EQUIV="Pragma" CONTENT="no-cache">
  </head>

</html>
