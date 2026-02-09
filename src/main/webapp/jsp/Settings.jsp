<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>

<ctrl:form styleId="striped-form">
  <div class="gridBack">
    <grid:table property="gridSettings" key="stn_id" scrollableGrid="true" height="expression(document.body.clientHeight-235)">
      <grid:column align="center"><jsp:attribute name="title"><bean:message key="Settings.stn_name"/></jsp:attribute></grid:column>
      <grid:column align="center"><jsp:attribute name="title"><bean:message key="Settings.stn_description"/></jsp:attribute></grid:column>
      <grid:column align="center"><jsp:attribute name="title"><bean:message key="Settings.stn_value"/></jsp:attribute></grid:column>
      <grid:column title=""/>
      <grid:row>
        <grid:colCustom width="20%" property="stn_name"/>
        <grid:colCustom property="stn_description"/>
        <grid:colCustom width="20%" property="stn_value"/>
        <grid:colEdit width="1" action="/SettingAction.do?dispatch=edit" type="link" tooltip="tooltip.Settings.edit"/>
      </grid:row>
    </grid:table>
  </div>

</ctrl:form>
