<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>

<ctrl:form styleId="striped-form">
  <table width="50%" align="center">
    <tr>
      <td>
        <div class="gridBack">
          <grid:table property="grid" key="lng_id" scrollableGrid="true" height="expression(document.body.clientHeight-235)" autoLockName="Languages">
            <grid:column align="center"><jsp:attribute name="title"><bean:message key="Languages.lng_name"/></jsp:attribute></grid:column>
            <grid:column align="center"><jsp:attribute name="title"><bean:message key="Languages.lng_letter_id"/></jsp:attribute></grid:column>
            <grid:column title=""/>
            <grid:row>
              <grid:colCustom property="lng_name"/>
              <grid:colCustom property="lng_letter_id"/>
              <grid:colEdit width="1" action="/LanguageAction.do?dispatch=edit" type="link" tooltip="tooltip.Languages.edit"/>
            </grid:row>
          </grid:table>
        </div>

        <div class=gridBottom>
          <ctrl:ubutton type="link" action="/LanguageAction.do?dispatch=input" styleClass="width80">
            <bean:message key="button.create"/>
          </ctrl:ubutton>
        </div>
      </td>
    </tr>
  </table>
</ctrl:form>
