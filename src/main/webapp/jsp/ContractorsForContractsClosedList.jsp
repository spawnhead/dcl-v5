<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>

<logic:notEmpty name="ContractorsForContractsClosedList" property="list">
  <logic:iterate id="obj" name="ContractorsForContractsClosedList" property="list">
    <tr class=line
        onclick="return ItemSelected(
        '<ctrl:write name="obj" property="ctr_id"/>',
        '<ctrl:write name="obj" property="ctr_name"/>'
        );"
        style='cursor:pointer;'>
      <td>
        <ctrl:write name="obj" property="ctr_name"/></td>
    </tr>
  </logic:iterate>
</logic:notEmpty>
<logic:empty name="ContractorsForContractsClosedList" property="list">
  <tr class=error>
    <td class=txt bgcolor='#eeeeee'><bean:message key="msg.nothingWasFound"/></td>
  </tr>
</logic:empty>
