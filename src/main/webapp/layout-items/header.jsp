<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>

<table cellSpacing=0 cellPadding=0 width='100%' border=0 height='1' class='header'>
  <tr>
    <td class=header-left>&nbsp;</td>
    <td class=header-center>
      <table cellSpacing=0 cellPadding=0 width='100%' border=0 height='1'>
        <tr>
          <td valign=top align=right id="standardLogo"><img src='<ctrl:rewrite page="/images/lintera.jpg"/>'></td>
          <td valign=top align=right id="warnLogo"><img src='<ctrl:rewrite page="/images/lintera_important.jpg"/>' onclick='showControlComment(document.getElementById("warnInHeaderId"), true);' style="cursor:pointer;"><span id="alertForEconomist"></span></td>
          <td valign=top width='100%' height="100%">

<!----------------menu----------------->
<tiles:insert attribute="menu">
  <%--<tiles:useAttribute name="menuActive"/>--%>
  <%--<tiles:put name="menuActive"  value="${menuActive}"/>--%>
</tiles:insert>
<!----------------menu----------------->

          </td>
        </tr>
      </table>
    </td>
    <td class=header-right>&nbsp;</td>
    <td></td>
  </tr>
</table>