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
          <grid:table property="grid" key="prs_id" scrollableGrid="true" height="expression(document.body.clientHeight-235)" autoLockName="Purposes">
            <grid:column align="center"><jsp:attribute name="title"><bean:message key="Purposes.prs_name"/></jsp:attribute></grid:column>
            <grid:column width="1%" title=""/>
            <grid:row>
              <grid:colCustom property="prs_name"/>
              <grid:colEdit width="1%" action="/PurposeAction.do?dispatch=edit" type="link" tooltip="tooltip.Purposes.edit"/>
            </grid:row>
          </grid:table>
        </div>

        <div class=gridBottom>
          <ctrl:ubutton type="link" action="/PurposeAction.do?dispatch=input" styleClass="width80">
            <bean:message key="button.create"/>
          </ctrl:ubutton>
        </div>
      </td>
    </tr>
  </table>
</ctrl:form>
