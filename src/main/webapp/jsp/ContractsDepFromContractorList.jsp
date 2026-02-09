<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>

<logic:notEmpty name="ContractsDepFromContractorList" property="list">
  <logic:iterate id="obj" name="ContractsDepFromContractorList" property="list">
    <tr class=line onclick="return ItemSelected(
          '<ctrl:write name="obj" property="con_id" jsFilter="true"/>',
          '<ctrl:write name="obj" property="numberWithDate" jsFilter="true"/>',
          '<ctrl:write name="obj" property="currency.id" jsFilter="true"/>',
          '<ctrl:write name="obj" property="currency.name" jsFilter="true"/>',
          '<ctrl:write name="obj" property="con_number" jsFilter="true"/>',
          '<ctrl:write name="obj" property="seller.id" jsFilter="true"/>',
          '<ctrl:write name="obj" property="seller.name" jsFilter="true"/>',
          '<ctrl:write name="obj" property="con_reusable" jsFilter="true"/>',
          '<ctrl:write name="obj" property="con_final_date_formatted" jsFilter="true"/>',
          '<ctrl:write name="obj" property="fullContractInfo" jsFilter="true"/>',
          '<ctrl:write name="obj" property="con_annul" jsFilter="true"/>',
			    '<ctrl:write name="obj" property="seller.resident" jsFilter="true"/>'
          );"
        style='cursor:pointer;'>
      <td nowrap="nowrap">
        <ctrl:write name="obj" property="fullContractInfo"/></td>
    </tr>
  </logic:iterate>
</logic:notEmpty>
<logic:empty name="ContractsDepFromContractorList" property="list">
  <tr class=error>
    <td class=txt bgcolor='#eeeeee'><bean:message key="msg.nothingWasFound"/></td>
  </tr>
</logic:empty>
