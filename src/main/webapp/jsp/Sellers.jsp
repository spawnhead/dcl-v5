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
          <grid:table property="grid" key="sln_id" scrollableGrid="true" height="expression(document.body.clientHeight-235)" autoLockName="Sellers">
            <grid:column align="center"><jsp:attribute name="title"><bean:message key="Sellers.sln_name"/></jsp:attribute></grid:column>
	          <grid:column width="100px" align="center"><jsp:attribute name="title"><bean:message key="Sellers.sln_is_resident"/></jsp:attribute></grid:column>
	          <grid:column width="100px" align="center"><jsp:attribute name="title"><bean:message key="Sellers.sln_used_in_order"/></jsp:attribute></grid:column>
	          <grid:column width="100px" align="center"><jsp:attribute name="title"><bean:message key="Sellers.sln_prefix_for_order"/></jsp:attribute></grid:column>
            <grid:column title=""/>
            <grid:column title=""/>
            <grid:row>
              <grid:colCustom property="sln_name"/>
	            <grid:colCheckbox width="100px" align="center" property="sln_is_resident" readonlyCheckerId="alwaysReadonly"/>
	            <grid:colCheckbox width="100px" align="center" property="sln_used_in_order" readonlyCheckerId="alwaysReadonly"/>
	            <grid:colCustom width="100px" property="sln_prefix_for_order"/>
              <grid:colEdit width="1" action="/SellerAction.do?dispatch=edit" type="link" tooltip="tooltip.Sellers.edit"/>
              <grid:colDelete width="1" action="/SellersAction.do?dispatch=delete" type="link" tooltip="tooltip.Sellers.delete" showCheckerId="show-delete-checker"/>
            </grid:row>
          </grid:table>
        </div>

        <div class=gridBottom>
          <ctrl:ubutton type="link" action="/SellerAction.do?dispatch=input" styleClass="width80">
            <bean:message key="button.create"/>
          </ctrl:ubutton>
        </div>
      </td>
    </tr>
  </table>
</ctrl:form>
