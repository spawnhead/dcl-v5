<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>

<ctrl:form styleId="striped-form">
  <div class="gridBack">
    <grid:table property="grid" key="id" scrollableGrid="true" height="expression(document.body.clientHeight-170)" >
      <grid:column align="center"><jsp:attribute name="title"><bean:message key="lockedRecords.id"/></jsp:attribute></grid:column>
      <grid:column align="center"><jsp:attribute name="title"><bean:message key="lockedRecords.name"/></jsp:attribute></grid:column>
      <grid:column align="center"><jsp:attribute name="title"><bean:message key="lockedRecords.owner"/></jsp:attribute></grid:column>
      <grid:column align="center"><jsp:attribute name="title"><bean:message key="lockedRecords.creationTime"/></jsp:attribute></grid:column>
      <grid:column align="center"><jsp:attribute name="title"><bean:message key="lockedRecords.userName"/></jsp:attribute></grid:column>
      <grid:column align="center"></grid:column>
      <grid:row>
        <grid:colCustom width="20%" property="id"/>
        <grid:colCustom width="20%" property="name"/>
        <grid:colCustom width="20%" property="owner"/>
        <grid:colCustom width="20%" >$(creationTime)($(creationElapsedTime))</grid:colCustom>
        <grid:colCustom width="20%" property="userName"/>
        <grid:colDelete type="link" scriptUrl="name=${record.name}" dispatch="unlock" tooltip="tooltip.LockedRecords.unlock" />
      </grid:row>
    </grid:table>
  </div>
</ctrl:form>
