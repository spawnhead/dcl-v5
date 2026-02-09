<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>

<ctrl:form styleId="striped-form">
  <div class="gridBack">
    <grid:table property="grid" key="id" scrollableGrid="true" height="expression(document.body.clientHeight-170)" >
      <grid:column align="center"><jsp:attribute name="title"><bean:message key="sessions.id"/></jsp:attribute></grid:column>
      <grid:column align="center"><jsp:attribute name="title"><bean:message key="sessions.creationTime"/></jsp:attribute></grid:column>
      <grid:column align="center"><jsp:attribute name="title"><bean:message key="sessions.lastAccessedTime"/></jsp:attribute></grid:column>
      <grid:column align="center"><jsp:attribute name="title"><bean:message key="sessions.userName"/></jsp:attribute></grid:column>
      <grid:column align="center"><jsp:attribute name="title"><bean:message key="sessions.ip"/></jsp:attribute></grid:column>
      <grid:row>
        <grid:colCustom width="20%" property="id"/>
        <grid:colCustom width="20%" >$(creationTime)($(creationElapsedTime))</grid:colCustom>
        <grid:colCustom width="20%" >$(lastAccessedTime)($(lastAccesElapsedTime))</grid:colCustom>
        <grid:colCustom width="20%" property="userName"/>
        <grid:colCustom width="20%" property="userIp"/>
      </grid:row>
    </grid:table>
  </div>
</ctrl:form>
