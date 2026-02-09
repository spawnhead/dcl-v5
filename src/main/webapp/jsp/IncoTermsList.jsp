<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>

<logic:notEmpty name="IncoTermsList" property="list">
  <logic:iterate id="obj" name="IncoTermsList" property="list">
    <tr class=line onclick="return ItemSelected(
          '<ctrl:write name="obj" property="trm_id" jsFilter="true"/>',
          '<ctrl:write name="obj" property="trm_name_extended" jsFilter="false"/>',
          '<ctrl:write name="obj" property="trm_name" jsFilter="true"/>'
          );"
        style='cursor:pointer;'>
      <td nowrap="nowrap">
        <ctrl:write name="obj" property="trm_name_extended" jsFilter="false"/>
      </td>
    </tr>
  </logic:iterate>
</logic:notEmpty>
<logic:empty name="IncoTermsList" property="list">
  <tr class=error>
    <td class=txt bgcolor='#eeeeee'><bean:message key="msg.nothingWasFound"/></td>
  </tr>
</logic:empty>
