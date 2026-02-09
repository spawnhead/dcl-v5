<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>

<logic:present name="form-title">
  <ctrl:write name="form-title"/>
</logic:present>
<logic:present name="form-title-id">
  <bean:message key="${requestScope['form-title-id']}"/>
</logic:present>

<logic:notPresent name="form-title">
  <logic:notPresent name="form-title-id">
    <tiles:importAttribute name="form-title" ignore="true" scope="request"/>
    <logic:present name="form-title">
      <ctrl:write name="form-title"/>
    </logic:present>

    <tiles:importAttribute name="form-title-id" ignore="true" scope="request"/>
    <logic:present name="form-title-id">
      <bean:message key="${requestScope['form-title-id']}"/>
      <ctrl:help htmlName="${requestScope['form-title-id']}"/>
    </logic:present>

  </logic:notPresent>
</logic:notPresent>


