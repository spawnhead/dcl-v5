<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>

<div class="gridBackNarrow" id="paymentSumsGrid">
  <grid:table property="grid" key="number" >
    <grid:column width="20%" align="center"><jsp:attribute name="title"><bean:message key="PaySums.con_number"/></jsp:attribute></grid:column>
    <grid:column width="15%" align="center"><jsp:attribute name="title"><bean:message key="PaySums.con_date"/></jsp:attribute></grid:column>
    <grid:column width="20%" align="center"><jsp:attribute name="title"><bean:message key="PaySums.spc_number"/></jsp:attribute></grid:column>
    <grid:column width="15%" align="center"><jsp:attribute name="title"><bean:message key="PaySums.spc_date"/></jsp:attribute></grid:column>
    <grid:column width="15%" align="center"><jsp:attribute name="title"><bean:message key="PaySums.lps_summ"/></jsp:attribute></grid:column>
    <grid:column align="center"><jsp:attribute name="title"><bean:message key="PaySums.lps_summ_eur"/></jsp:attribute></grid:column>
    <grid:column title=""/>
    <grid:column title=""/>
    <grid:row>
      <grid:colCustom width="20%" property="contract.con_number"/>
      <grid:colCustom width="15%" property="contract.con_date_formatted"/>
      <grid:colCustom width="20%" property="specification.spc_number"/>
      <grid:colCustom width="15%" property="specification.spc_date_formatted"/>
      <grid:colCustom width="15%" property="lps_summ_formatted" align="right"/>
      <grid:colCustom property="lps_summ_eur_formatted" align="right"/>
      <grid:colEdit width="1" dispatch="editPaySum" type="submit" tooltip="tooltip.PaySums.edit"
                    readonlyCheckerId="deleteReadonly" action="/PaymentAction.do"/>
      <grid:colDelete width="1" dispatch="deletePaySum" type="submit" tooltip="tooltip.PaySums.delete"
                      readonlyCheckerId="deleteReadonly" action="/PaymentAction.do"/>
    </grid:row>
  </grid:table>
</div>
