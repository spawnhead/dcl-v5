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
          <grid:table property="grid" key="rtn_id" scrollableGrid="true" height="expression(document.body.clientHeight-245)" autoLockName="RatesNDS">
            <grid:column align="center"><jsp:attribute name="title"><bean:message key="RatesNDS.rtn_date_from"/></jsp:attribute></grid:column>
            <grid:column width="40%" align="center"><jsp:attribute name="title"><bean:message key="RatesNDS.rtn_percent"/></jsp:attribute></grid:column>
            <grid:column width="1%" title=""/>
            <grid:row>
              <grid:colCustom property="rtn_date_from_formatted"/>
              <grid:colCustom width="40%" align="right" property="rtn_percent_formatted"/>
              <grid:colEdit width="1%" action="/RateNDSAction.do?dispatch=edit" type="link" tooltip="tooltip.RatesNDS.edit"/>
            </grid:row>
          </grid:table>
        </div>

        <div class=gridBottom>
          <ctrl:ubutton type="link" action="/RateNDSAction.do?dispatch=input" styleClass="width80">
            <bean:message key="button.create"/>
          </ctrl:ubutton>
        </div>
      </td>
    </tr>
  </table>
</ctrl:form>
