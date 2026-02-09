<%@ page import="net.sam.dcl.controller.actions.ListAction" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>

<logic:present name="<%=ListAction.LIST_KEY%>">
  <logic:iterate id="obj" name="<%=ListAction.LIST_KEY%>">
    <tr class=line onclick="return ItemSelected('<ctrl:write name="obj" jsFilter="true" />');"
        style='cursor:pointer;'>
      <td><ctrl:write name="obj"/></td>
    </tr>
  </logic:iterate>
</logic:present>
<logic:empty name="<%=ListAction.LIST_KEY%>">
  <tr class=error>
    <td class=txt bgcolor='#eeeeee'><bean:message key="msg.nothingWasFound"/></td>
  </tr>
</logic:empty>
