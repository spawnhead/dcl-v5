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
          <grid:table property="grid" key="rut_id" scrollableGrid="true" height="expression(document.body.clientHeight-235)" autoLockName="Routes">
            <grid:column align="center"><jsp:attribute name="title"><bean:message key="Routes.rut_name"/></jsp:attribute></grid:column>
            <grid:column title=""/>
            <grid:column title=""/>
            <grid:row>
              <grid:colCustom property="rut_name"/>
              <grid:colEdit width="1" action="/RouteAction.do?dispatch=edit" type="link" tooltip="tooltip.Routes.edit"/>
              <grid:colDelete width="1" action="/RoutesAction.do?dispatch=delete" type="link" tooltip="tooltip.Routes.delete" showCheckerId="show-delete-checker"/>
            </grid:row>
          </grid:table>
        </div>

        <div class=gridBottom>
          <ctrl:ubutton type="link" action="/RouteAction.do?dispatch=input" styleClass="width80">
            <bean:message key="button.create"/>
          </ctrl:ubutton>
        </div>
      </td>
    </tr>
  </table>
</ctrl:form>
