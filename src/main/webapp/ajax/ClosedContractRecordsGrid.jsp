<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>

<div class="gridBackNarrow" id="contractsGrid">
  <grid:table property="grid" key="number" >
    <grid:column width="1"><jsp:attribute name="title"><input type="checkbox" class="grid-header-checkbox" onclick="changeGridSelection(this)" ></jsp:attribute></grid:column>
    <grid:column width="15%" align="center"><jsp:attribute name="title"><bean:message key="ClosedRecords.warnMessage"/></jsp:attribute></grid:column>
    <grid:column align="center"><jsp:attribute name="title"><bean:message key="ClosedRecords.ctc_name"/></jsp:attribute></grid:column>
    <grid:column width="15%" align="center"><jsp:attribute name="title"><bean:message key="ClosedRecords.con_number"/></jsp:attribute></grid:column>
    <grid:column width="8%" align="center"><jsp:attribute name="title"><bean:message key="ClosedRecords.con_date"/></jsp:attribute></grid:column>
    <grid:column width="8%" align="center"><jsp:attribute name="title"><bean:message key="ClosedRecords.spc_number"/></jsp:attribute></grid:column>
    <grid:column width="8%" align="center"><jsp:attribute name="title"><bean:message key="ClosedRecords.spc_date"/></jsp:attribute></grid:column>
    <grid:column width="6%" align="center"><jsp:attribute name="title"><bean:message key="ClosedRecords.lcc_charge"/></jsp:attribute></grid:column>
    <grid:column width="6%" align="center"><jsp:attribute name="title"><bean:message key="ClosedRecords.lcc_montage"/></jsp:attribute></grid:column>
    <grid:column width="6%" align="center"><jsp:attribute name="title"><bean:message key="ClosedRecords.lcc_transport"/></jsp:attribute></grid:column>
    <grid:column width="6%" align="center"><jsp:attribute name="title"><bean:message key="ClosedRecords.lcc_update_sum"/></jsp:attribute></grid:column>
    <grid:column title=""/>
    <grid:column title=""/>
    <grid:row>
      <grid:colCheckbox  width="1%" property="selectedContractId" result="grid" resultProperty="selectedContractId" useIndexAsPK="true" styleClass="first-column"/>
      <grid:colCustom width="15%" property="warnDates" styleClassCheckerId="style-checker"/>
      <grid:colCustom property="contractor.name" styleClassCheckerId="style-checker"/>
      <grid:colCustom width="15%" property="contract_number_formatted" styleClassCheckerId="style-checker"/>
      <grid:colCustom width="8%" property="contract.con_date_formatted" styleClassCheckerId="style-checker"/>
      <grid:colCustom width="8%" property="specification_number_formatted" styleClassCheckerId="style-checker"/>
      <grid:colCustom width="8%" property="specification.spc_date" styleClassCheckerId="style-checker"/>
      <grid:colCustom width="6%" property="lcc_charges_formatted" align="right" styleClassCheckerId="style-checker"/>
      <grid:colCustom width="6%" property="lcc_montage_formatted" align="right" styleClassCheckerId="style-checker"/>
      <grid:colCustom width="6%" property="lcc_transport_formatted" align="right" styleClassCheckerId="style-checker"/>
      <grid:colCustom width="6%" property="lcc_update_sum_formatted" align="right" styleClassCheckerId="style-checker"/>
      <grid:colEdit width="1" dispatch="editClosedRecord" type="submit"
                    action="/ContractClosedAction.do"
                    tooltip="tooltip.ClosedRecords.edit" readonlyCheckerId="alwaysNotReadonly" styleClassCheckerId="style-checker"/>
      <grid:colDelete width="1" type="script" action="/ContractClosedAction.fakeForDelete" onclick="removeFromClosedRecords(${record.number})" showWait="false"
                      tooltip="tooltip.ClosedRecords.delete" styleClassCheckerId="style-checker"/>
    </grid:row>
  </grid:table>
</div>
