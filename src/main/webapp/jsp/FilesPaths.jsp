<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>

<ctrl:form styleId="striped-form">
  <div class="gridBack">
    <grid:table property="gridFilesPaths" key="flp_id" scrollableGrid="true" height="expression(document.body.clientHeight-235)">
      <grid:column align="center"><jsp:attribute name="title"><bean:message key="FilesPaths.flp_table_name"/></jsp:attribute></grid:column>
      <grid:column align="center"><jsp:attribute name="title"><bean:message key="FilesPaths.flp_path"/></jsp:attribute></grid:column>
      <grid:column align="center"><jsp:attribute name="title"><bean:message key="FilesPaths.flp_description"/></jsp:attribute></grid:column>
      <grid:column title=""/>
      <grid:row>
        <grid:colCustom width="20%" property="flp_table_name"/>
        <grid:colCustom property="flp_path"/>
        <grid:colCustom width="30%" property="flp_description"/>
        <grid:colEdit width="1" action="/FilesPathAction.do?dispatch=edit" type="link" tooltip="tooltip.FilesPaths.edit"/>
      </grid:row>
    </grid:table>
  </div>

</ctrl:form>
