<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>

<h2>
  <bean:message key="CustomCodeHistory.title"/>&nbsp;<ctrl:write name="CustomCodeHistory" property="cus_description"/>
</h2>

<ctrl:form styleId="striped-form">
  <div class="gridBack">
    <grid:table property="grid" key="cus_id" scrollableGrid="true" height="expression(document.body.clientHeight-235)">
      <grid:column align="center"><jsp:attribute name="title"><bean:message key="CustomCodeHistory.cus_percent"/></jsp:attribute></grid:column>
      <grid:column align="center"><jsp:attribute name="title"><bean:message key="CustomCodeHistory.cus_instant"/></jsp:attribute></grid:column>
      <grid:column title=""/>
      <grid:row>
        <grid:colCustom width="85%" property="cus_percent_formatted"/>
        <grid:colCustom width="30%" property="cus_instant_date"/>
        <grid:colEdit width="1" action="/CustomCodeFromHistoryAction.do?dispatch=edit" type="link" tooltip="tooltip.CustomCodeHistory.edit"/>
      </grid:row>
    </grid:table>
  </div>

  <div class=gridBottom>
    <ctrl:ubutton type="link" action="/CustomCodesAction.do?dispatch=execute" styleClass="width80">
      <bean:message key="button.back"/>
    </ctrl:ubutton>
    <ctrl:ubutton type="link" action="/CustomCodeFromHistoryAction.do" dispatch="create" paramId="cus_code" paramName="CustomCodeHistory" paramProperty="cus_code"  styleClass="width80" >
      <bean:message key="button.create"/>
    </ctrl:ubutton>
  </div>


</ctrl:form>
