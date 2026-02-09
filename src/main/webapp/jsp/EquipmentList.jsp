<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>

<logic:notEmpty name="EquipmentList" property="list">
  <logic:iterate id="obj" name="EquipmentList" property="list">
    <tr class=line onclick="return ItemSelected(
          '<ctrl:write name="obj" property="lps_id" jsFilter="true"/>',
          '<ctrl:write name="obj" property="fullList" jsFilter="true"/>',
          '<ctrl:write name="obj" property="crq_equipment" jsFilter="true"/>',
          '<ctrl:write name="obj" property="ctn_number" jsFilter="true"/>',
          '<ctrl:write name="obj" property="stf_name" jsFilter="true"/>',
          '<ctrl:write name="obj" property="lps_serial_num" jsFilter="true"/>',
          '<ctrl:write name="obj" property="lps_year_out" jsFilter="true"/>',
          '<ctrl:write name="obj" property="lps_usr_id" jsFilter="true"/>',
          '<ctrl:write name="obj" property="lps_usr_fullname" jsFilter="true"/>',
          '<ctrl:write name="obj" property="lps_enter_in_use_date" jsFilter="true"/>',
          '<ctrl:write name="obj" property="mad_complexity" jsFilter="true"/>',
          '<ctrl:write name="obj" property="lps_occupied" jsFilter="true"/>',
          '<ctrl:write name="obj" property="crq_number" jsFilter="true"/>',
          '<ctrl:write name="obj" property="shp_date" jsFilter="true"/>',
          '<ctrl:write name="obj" property="con_number" jsFilter="true"/>',
          '<ctrl:write name="obj" property="con_date" jsFilter="true"/>',
          '<ctrl:write name="obj" property="spc_number" jsFilter="true"/>',
          '<ctrl:write name="obj" property="spc_date" jsFilter="true"/>',
          '<ctrl:write name="obj" property="con_seller_id" jsFilter="true"/>',
          '<ctrl:write name="obj" property="con_seller" jsFilter="true"/>'
          );"
        style='cursor:pointer;'>
      <td nowrap="nowrap">
        <ctrl:write name="obj" property="fullList"/>
      </td>
    </tr>
  </logic:iterate>
</logic:notEmpty>
<logic:empty name="EquipmentList" property="list">
  <tr class=error>
    <td class=txt bgcolor='#eeeeee'><bean:message key="msg.nothingWasFound"/></td>
  </tr>
</logic:empty>
