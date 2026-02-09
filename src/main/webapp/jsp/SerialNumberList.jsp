<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>

<logic:notEmpty name="SerialNumberList" property="list">
  <logic:iterate id="obj" name="SerialNumberList" property="list">
    <tr class=line onclick="return ItemSelected(
          '<ctrl:write name="obj" property="lps_id" jsFilter="true"/>',
          '<ctrl:write name="obj" property="lps_serial_num" jsFilter="true"/>',
          '<ctrl:write name="obj" property="lps_year_out" jsFilter="true"/>',
          '<ctrl:write name="obj" property="stf_name" jsFilter="true"/>',
          '<ctrl:write name="obj" property="mad_complexity" jsFilter="true"/>'
          );"
        style='cursor:pointer;'>
      <td nowrap="nowrap">
        <ctrl:write name="obj" property="lps_serial_num"/>
      </td>
    </tr>
  </logic:iterate>
</logic:notEmpty>
<logic:empty name="SerialNumberList" property="list">
  <tr class=error>
    <td class=txt bgcolor='#eeeeee'><bean:message key="msg.nothingWasFound"/></td>
  </tr>
</logic:empty>
