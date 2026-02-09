<%@ page import="net.sam.dcl.controller.actions.ListAction" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>

<logic:notEmpty name="CustomCodesList" property="list">
  <logic:iterate id="obj" name="CustomCodesList" property="list">
    <tr class=line onclick="return ItemSelected(
            '<ctrl:write name="obj" property="cus_id" jsFilter="true"/>',
            '<ctrl:write name="obj" property="cus_code" jsFilter="true"/>',
            '<ctrl:write name="obj" property="percentFormatted" jsFilter="true"/>'
            );"
        style='cursor:pointer;'>
      <td>
        <ctrl:write name="obj" property="cusDescriptionFormatted" jsFilter="false"/>,<ctrl:write name="obj" property="cus_code"/>,<ctrl:write name="obj" property="percentFormatted"/>
      </td>
    </tr>
  </logic:iterate>
</logic:notEmpty>
<logic:empty name="CustomCodesList" property="list">
  <tr class=error>
    <td class=txt bgcolor='#eeeeee'><bean:message key="msg.nothingWasFound"/></td>
  </tr>
</logic:empty>
