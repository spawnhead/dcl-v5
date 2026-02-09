<%@ page import="net.sam.dcl.action.*" %>
<%@ page import="net.sam.dcl.beans.User" %>
<%@ page import="net.sam.dcl.util.StringUtil" %>
<%@ page import="java.util.Date" %>
<%@ page import="net.sam.dcl.App" %>
<%@ page import="net.sam.dcl.config.Config" %>
<%@ page import="net.sam.dcl.util.UserUtil" %>
<%@ page import="net.sam.dcl.controller.actions.SelectFromGridAction" %>

<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>

<div style="display:none" id="resultMsg"></div>

<script type="text/javascript">

  webfxMenuUseHover = false;
  webfxMenuImagePath = "images/";

  function showMenu(menu, obj)
  {
    var item;
    for (item in webFXMenuHandler.all)
    {
      var curr = webFXMenuHandler.all[item];
      if (curr instanceof WebFXMenu && curr.shown)
      {
        curr.hide();
      }
    }
    webFXMenuHandler.showMenu(menu, obj);
  }

  function toggleSubmenu(menu, obj)
  {
    if (menu && menu.shown)
    {
      menu.hide();
    }
    else
    {
      showMenu(menu, obj);
    }
  }

  function closeAllSubmenus()
  {
    var item;
    for (item in webFXMenuHandler.all)
    {
      var curr = webFXMenuHandler.all[item];
      if (curr instanceof WebFXMenu && curr.shown)
      {
        curr.hide();
      }
    }
  }

  document.addEventListener('click', function() { closeAllSubmenus(); });

  (function() {
    function stopPropagation(e) { e = e || window.event; if (e.stopPropagation) e.stopPropagation(); else e.cancelBubble = true; }
    function bindMenuClicks() {
      var menus = document.querySelectorAll('.webfx-menu');
      for (var i = 0; i < menus.length; i++)
        menus[i].addEventListener('click', stopPropagation);
    }
    if (document.readyState === 'loading')
      document.addEventListener('DOMContentLoaded', bindMenuClicks);
    else
      bindMenuClicks();
  })();
</script>

<!--<tiles:useAttribute name="menuActive" />-->
<%
  int jsAutoCounter = 0;
  if (SelectFromGridAction.isSelectMode(session))
  {
    request.setAttribute("menuActive", "0");
  }
%>
<table cellSpacing=0 cellPadding=0 width='100%' border=0 height="100%">
  <tr>
    <td class=header-top colspan="2"><bean:message key="app.name"/> v<%=App.VERSION%>
      <%
        User currentUser = UserUtil.getCurrentUser(request);
        String currentDate = StringUtil.date2appDateString(new Date());
        if (Config.getBoolean("global.dev-mode", false))
        {
          out.println(currentUser.userRoles());
        }
      %>
    </td>
  </tr>
  <tr>
    <td class=header-top2 colspan="2">
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td align="left">
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
              <tr>
                <td>
                  <bean:message key="menus.above1"/>&nbsp;<%=currentUser.getUserFullName()%>
                  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<bean:message key="menus.above2"/>&nbsp;<%=currentDate%>
                </td>
              </tr>
            </table>
          </td>
          <td id='menus.exchange.rates' align="right" onmouseover='<%=Config.getString("exchange.rates.script")%>'
              style="cursor:pointer;">
            <bean:message key="menus.exchange.rates"/>
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td class=header-middle valign="bottom">
      <table cellSpacing=0 cellPadding=0 border=0 height="100%">
        <tr>
          <%
            String strMenuId = (String) request.getSession().getAttribute(Outline.MENU_ID_NAME);
            Outline topLevelSelectedOutline = Outline.findOutline(Outline.OUTLINE, strMenuId, true, true, session);
            for (int i = 0; i < Outline.OUTLINE.length; i++)
            {
              Outline curOutline = Outline.OUTLINE[i];
              if (!Config.getBoolean("global.dev-mode", false) && Outline.DEV_ZONE.getId().equals(curOutline.getId()))
              {
                continue;
              }
              String styleClass = "top-menu";
              if (curOutline.equals(topLevelSelectedOutline))
              {
                styleClass = "top-menu-selected";
              }
          %>
          <logic:equal value="1" name="menuActive">
            <td class="<%=styleClass%>"><ctrl:ulink type='link' action='Menu' scriptUrl='<%= Outline.MENU_ID_NAME + "=" + curOutline.id %>'><bean:message
              key='<%= curOutline.title %>'/></ctrl:ulink></td>
          </logic:equal>
          <logic:equal value="0" name="menuActive">
            <td class="<%=styleClass%>"><span><bean:message key='<%=curOutline.title%>'/></span></td>
          </logic:equal>
          <%
            }
          %>

        </tr>
      </table>
    </td>
    <td align=right class=header-middle>
      <div id=WaitPlace style="margin-right:10px"></div>
    </td>
  </tr>
  <tr>
    <td class=header-bottom colspan="2">
      <div id="submenu" class="submenu">
          <%
          if (topLevelSelectedOutline != null)
          {
            if (null != topLevelSelectedOutline.subItems)
            {
              Outline secondLevelSelectedOutline = Outline.findOutline(topLevelSelectedOutline.subItems, strMenuId, true, true, session);
              int menuWidth = 230 + (topLevelSelectedOutline.subItems[0]).getElementWidth(); //начальная ширина картинки + ширина первого элемента

              // Dev-only extension: render test_newGrid even if Outline isn't recompiled/updated yet.
              // If Outline already contains id.test_new_grid, we skip it in the loop below to avoid duplicates.
              boolean isDevZone = Outline.DEV_ZONE.equals(topLevelSelectedOutline);
              if (isDevZone)
              {
                String devMarginStyle = "id.dev_margin".equals(strMenuId) ? "submenu-selected" : null;
                String devMarginUrl = response.encodeURL(request.getContextPath() + "/MarginDevAction.do?dispatch=input");

                String devCalcStateStyle = "id.dev_calculation_state".equals(strMenuId) ? "submenu-selected" : null;
                String devCalcStateUrl = response.encodeURL(request.getContextPath() + "/CalculationStateDevAction.do");
                String testNewGridStyle = "id.test_new_grid".equals(strMenuId) ? "submenu-selected" : null;
                String testNewGridUrl = response.encodeURL(request.getContextPath() + "/TestNewGridAction.do?dispatch=show");
          %>
        <logic:equal value="1" name="menuActive">
          <a class='<%=devMarginStyle%>' href="<%= devMarginUrl %>"><bean:message key='menus.dev_margin'/></a>
        </logic:equal>
        <logic:equal value="0" name="menuActive">
          <span class='<%=devMarginStyle%>'><bean:message key='menus.dev_margin'/></span>
        </logic:equal>
          |
        <logic:equal value="1" name="menuActive">
          <a class='<%=devCalcStateStyle%>' href="<%= devCalcStateUrl %>"><bean:message key='menus.dev_calculation_state'/></a>
        </logic:equal>
        <logic:equal value="0" name="menuActive">
          <span class='<%=devCalcStateStyle%>'><bean:message key='menus.dev_calculation_state'/></span>
        </logic:equal>
          |
        <logic:equal value="1" name="menuActive">
          <a class='<%=testNewGridStyle%>' href="<%= testNewGridUrl %>"><bean:message key='menus.test_new_grid'/></a>
        </logic:equal>
        <logic:equal value="0" name="menuActive">
          <span class='<%=testNewGridStyle%>'><bean:message key='menus.test_new_grid'/></span>
        </logic:equal>
          |
          <%
              }
              for (int i = 0; i < topLevelSelectedOutline.subItems.length; i++)
              {
                Outline currentOutline = topLevelSelectedOutline.subItems[i];
                if (isDevZone && ("id.test_new_grid".equals(currentOutline.id) || "id.dev_margin".equals(currentOutline.id) || "id.dev_calculation_state".equals(currentOutline.id)))
                {
                  continue;
                }
                //убрать код в if-е , просто выставлять styleClass='submenu-selected'
                // переписать Outline#getSubMenuInd
                String styleClass = null;
                if (currentOutline.equals(secondLevelSelectedOutline))
                {
                  styleClass = "submenu-selected";
                }
          %>
        <logic:equal value="1" name="menuActive">
          <%
                if (currentOutline instanceof Outline.JSOutline)
                {
                  int curAutoCounter = jsAutoCounter++;
                  String strToggle = "event.stopPropagation(); toggleSubmenu(menu" + curAutoCounter + ", this);";
          %>
          <script type="text/javascript">

            var menu<%=curAutoCounter%> = new WebFXMenu('menu<%=curAutoCounter%>');
            menu<%=curAutoCounter%>.width = <%=((Outline.JSOutline)currentOutline).width%>;
            <%
                  for (int subIdx = 0; subIdx < currentOutline.subItems.length; subIdx++)
                  {
            %>
            menu<%=curAutoCounter%>.add(new WebFXMenuItem("<bean:message key='<%= currentOutline.subItems[subIdx].title %>'/>", "<html:rewrite action='<%="/Menu?"+ Outline.MENU_ID_NAME + "=" + currentOutline.subItems[subIdx].id %>'  />"));
            <%
                  }
            %>

            document.write(menu<%=curAutoCounter%>);
          </script>

          <a class='<%=styleClass%>' href="#" onclick="<%=strToggle%> return false;">
            <bean:message key='<%= currentOutline.title %>'/>
          </a>
          <%
                }
                else
                {
          %>
          <ctrl:ulink styleClass='<%=styleClass%>' type='link' action='Menu'
                      scriptUrl='<%= Outline.MENU_ID_NAME + "=" + currentOutline.id %>'><bean:message
            key='<%= currentOutline.title %>'/></ctrl:ulink>
          <%
                }
          %>

        </logic:equal>
        <logic:equal value="0" name="menuActive">
          <span class='<%=styleClass%>'><bean:message key='<%= currentOutline.title %>'/></span>
        </logic:equal>

          <%
                if (i < topLevelSelectedOutline.subItems.length - 1)
                {
          %>
          |
          <%
                } //if ( i < Outline.OUTLINE[menu_id].subItems.length - 1 )

                //прибавляем ширину следующего элемента
                int nextElementWidth = 0;
                if ( i + 1 < topLevelSelectedOutline.subItems.length )
                {
                  nextElementWidth = (topLevelSelectedOutline.subItems[i + 1]).getElementWidth();   
                }
                menuWidth += nextElementWidth;
                if ( menuWidth > currentUser.getUserScreenWith() )
                {
                  menuWidth = 230 + nextElementWidth; //начальная ширина картинки
      %>
            <br>
            <hr>
      <%
                }
              } //for
            } //if (null != Outline.OUTLINE[menu_id].subItems)
          } //if ( menu_id != -1 )
          %>
      </div>
    </td>
  </tr>
</table>

<script language="JScript" type="text/javascript">
  document.getElementById('alertForEconomist').style.display = 'none';
  document.getElementById('warnLogo').style.display = 'none';
  document.getElementById('standardLogo').style.display = 'block';

  function setCorrectMenuId(id)
  {
    doAjax({
      synchronous:true,
      url:'<ctrl:rewrite action="/OfficeAction" dispatch="ajaxSetCorrectMenuId"/>',
      params:{'menuId':document.getElementById('menuIdx' + id).value}
    });
  }

  function loadMessagesEconomistForUser(userId)
  {
    doAjax({
      synchronous:false,
      url:'<ctrl:rewrite action="/OfficeAction" dispatch="ajaxEconomistMessages"/>',
      params:{'userId':userId},
      update:'resultMsg',
      okCallback:function()
      {
        var resultMsg = document.getElementById('resultMsg').innerHTML;
        document.getElementById('alertForEconomist').style.display = resultMsg != '' ? 'block' : 'none';
        document.getElementById('warnLogo').style.display = resultMsg != '' ? 'block' : 'none';
        document.getElementById('standardLogo').style.display = resultMsg != '' ? 'none' : 'block';
        document.getElementById('alertForEconomist').innerHTML = resultMsg;
      }
    });
  }

  function loadMessagesEconomist()
  {
    loadMessagesEconomistForUser(<%=currentUser.getUsr_id()%>);
    setTimeout("loadMessagesEconomist()", 5 * 60 * 1000);
  }

  function loadMessagesEconomistByTime()
  {
    setTimeout("loadMessagesEconomist()", 5 * 60 * 1000);
  }

  initFunctions.push(loadMessagesEconomist);
  initFunctions.push(loadMessagesEconomistByTime);
</script>