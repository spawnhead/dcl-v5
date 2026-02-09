<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>

<div class="gridBackNarrow" id="linkedOrdersGrid">
  <grid:table property="orders" key="ord_id">
    <grid:column align="center"><jsp:attribute name="title"><bean:message key="LinkedOrders.number"/></jsp:attribute></grid:column>
    <grid:column width="40%" align="center"><jsp:attribute name="title"><bean:message key="LinkedOrders.date"/></jsp:attribute></grid:column>
    <grid:column title=""/>
    <grid:row>
      <grid:colCustom property="ord_number"/>
      <grid:colCustom width="40%" property="ord_date"/>
      <grid:colDelete width="1" type="script" action="/ContractorRequestAction.fakeForDelete" onclick="removeFromOrder(${record.ord_id})" showWait="false"
                      tooltip="tooltip.LinkedOrders.delete"/>
    </grid:row>
  </grid:table>
</div>
