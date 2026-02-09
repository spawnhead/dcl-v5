<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>

<ctrl:form styleId="striped-form">
  <div class="gridBack">
    <grid:table property="gridActions" key="act_id" scrollableGrid="true" height="expression(document.body.clientHeight-235)">
      <grid:column align="center"><jsp:attribute name="title"><bean:message key="Actions.act_system_name"/></jsp:attribute></grid:column>
      <grid:column align="center"><jsp:attribute name="title"><bean:message key="Actions.act_name"/></jsp:attribute></grid:column>
      <grid:column align="center"><jsp:attribute name="title"><bean:message key="Actions.act_logging"/></jsp:attribute></grid:column>
      <grid:column align="center"><jsp:attribute name="title"><bean:message key="Actions.act_check_access"/></jsp:attribute></grid:column>
      <grid:column title=""/>
      <grid:column title=""/>
      <grid:row>
        <grid:colCustom width="30%" property="act_system_name"/>
        <grid:colCustom property="act_name"/>
        <grid:colCheckbox width="15%" align="center" property="act_logging" readonlyCheckerId="alwaysReadonly"/>
        <grid:colCheckbox width="15%" align="center" property="act_check_access" readonlyCheckerId="alwaysReadonly"/>
        <logic:equal value="1" name="record" property="act_check_access">
          <grid:colLink width="120px" align="center" action="/ActionRolesAction.do?dispatch=show" type="link" ><bean:message key="Actions.check_access_text"/></grid:colLink>
        </logic:equal>
        <logic:empty name="record" property="act_check_access">
          <grid:colCustom/>
        </logic:empty>
        <grid:colEdit width="1%" action="/ActionAction.do?dispatch=edit" type="link" tooltip="tooltip.Actions.edit"/>
      </grid:row>
    </grid:table>
  </div>
</ctrl:form>
