<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>

<logic:notEmpty name="SpecificationsDepFromContractList" property="list">
  <logic:iterate id="obj" name="SpecificationsDepFromContractList" property="list">
    <tr class=line onclick="return ItemSelected(
          '<ctrl:write name="obj" property="id" jsFilter="true"/>',
          '<ctrl:write name="obj" property="value" jsFilter="true"/>',
          '<ctrl:write name="obj" property="number" jsFilter="true"/>',
          '<ctrl:write name="obj" property="annul" jsFilter="true"/>'
          );"
        style='cursor:pointer;'>
      <td nowrap="nowrap">
        <ctrl:write name="obj" property="value"/>
      </td>
    </tr>
  </logic:iterate>
</logic:notEmpty>
<logic:empty name="SpecificationsDepFromContractList" property="list">
  <tr class=error>
    <td class=txt bgcolor='#eeeeee'><bean:message key="msg.nothingWasFound"/></td>
  </tr>
</logic:empty>
