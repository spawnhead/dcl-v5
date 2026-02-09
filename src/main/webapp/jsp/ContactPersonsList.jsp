<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>

<logic:notEmpty name="ContactPersonsList" property="list">
  <logic:iterate id="obj" name="ContactPersonsList" property="list">
    <tr class=line onclick="return ItemSelected(
          '<ctrl:write name="obj" property="cps_id" jsFilter="true"/>',
          '<ctrl:write name="obj" property="cps_name" jsFilter="true"/>',
          '<ctrl:write name="obj" property="cps_phone" jsFilter="true"/>',
          '<ctrl:write name="obj" property="cps_fax" jsFilter="true"/>',
          '<ctrl:write name="obj" property="cps_email" jsFilter="true"/>',
          '<ctrl:write name="obj" property="cps_mob_phone" jsFilter="true"/>',
          '<ctrl:write name="obj" property="cps_position" jsFilter="true"/>',
          '<ctrl:write name="obj" property="cps_on_reason" jsFilter="true"/>',
          '<ctrl:write name="obj" property="cps_contract_comment" jsFilter="true"/>'
          );"
        style='cursor:pointer;'>
      <td nowrap="nowrap">
        <ctrl:write name="obj" property="nameFormatted"/>
      </td>
    </tr>
  </logic:iterate>
</logic:notEmpty>
<logic:empty name="ContactPersonsList" property="list">
  <tr class=error>
    <td class=txt bgcolor='#eeeeee'><bean:message key="msg.nothingWasFound"/></td>
  </tr>
</logic:empty>
