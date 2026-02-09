<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>

<div class="gridBackNarrow" id="producesForAssembleMinskGrid">
  <grid:table property="gridProduces" key="number">
    <grid:column align="center"><jsp:attribute name="title"><bean:message key="CommercialProposalProduces.number"/></jsp:attribute></grid:column>
    <grid:column align="center"><jsp:attribute name="title"><bean:message key="CommercialProposalProduces.produce_name"/></jsp:attribute></grid:column>
    <grid:column align="center"><jsp:attribute name="title"><bean:message key="CommercialProposalProduces.stuffCategory"/></jsp:attribute></grid:column>
    <grid:column align="center"><jsp:attribute name="title"><bean:message key="CommercialProposalProduces.catalog_num"/></jsp:attribute></grid:column>
    <grid:column align="center"><jsp:attribute name="title"><bean:message key="CommercialProposalProduces.lpr_count"/></jsp:attribute></grid:column>
    <grid:column align="center"><jsp:attribute name="title"><bean:message key="CommercialProposalProduces.lpc_cost_one_by"/></jsp:attribute></grid:column>
    <logic:notEqual name="CommercialProposal" property="cpr_free_prices" value="1">
      <grid:column align="center"><jsp:attribute name="title"><bean:message key="CommercialProposalProduces.lpc_price_list_by"/></jsp:attribute></grid:column>
    </logic:notEqual>
    <grid:column align="center"><jsp:attribute name="title"><bean:message key="CommercialProposalProduces.extra_percent"/></jsp:attribute></grid:column>
    <logic:notEqual name="CommercialProposal" property="cpr_free_prices" value="1">
      <grid:column align="center"><jsp:attribute name="title"><bean:message key="CommercialProposalProduces.discount_percent"/></jsp:attribute></grid:column>
    </logic:notEqual>
    <grid:column align="center"><jsp:attribute name="title"><bean:message key="CommercialProposalProduces.sale_price_parking_trans_custom1"/></jsp:attribute></grid:column>
    <grid:column align="center"><jsp:attribute name="title"><bean:message key="CommercialProposalProduces.sale_cost_parking_trans_custom1"/></jsp:attribute></grid:column>
    <logic:equal name="CommercialProposal" property="cpr_nds_by_string" value="1">
      <grid:column align="center"><jsp:attribute name="title"><bean:message key="CommercialProposalProduces.nds"/></jsp:attribute></grid:column>
      <grid:column align="center"><jsp:attribute name="title"><bean:message key="CommercialProposalProduces.cost_nds"/></jsp:attribute></grid:column>
    </logic:equal>
    <grid:column><jsp:attribute name="title"><img title='<bean:message key="tooltip.CommercialProposalProduces.deleteAll"/>' src='<ctrl:rewrite page="/images/sub.gif"/>' onclick='deleteAllProducesForAssembleMinskGrid()' style='cursor:pointer;'></jsp:attribute></grid:column>
    <grid:row>
      <span calculatedField="${record.calculatedField}"/>
      <grid:colCustom width="2%" property="number" styleClassCheckerId="style-checker-short"/>
      <grid:colCustom property="produce_name" styleClassCheckerId="style-checker-long"/>
      <grid:colCustom width="5%" property="stuffCategory.name" showCheckerId="show-checker" styleClassCheckerId="style-checker"/>
      <grid:colCustom width="5%" property="catalog_num" showCheckerId="show-checker" styleClassCheckerId="style-checker-short" align="right"/>
      <grid:colCustom width="5%" property="lpr_count_formatted" showCheckerId="show-checker" styleClassCheckerId="style-checker-short" align="right"/>
      <grid:colCustom width="5%" property="lpc_cost_one_by_formatted" showCheckerId="show-checker" align="right"/>
      <logic:notEqual name="CommercialProposal" property="cpr_free_prices" value="1">
        <grid:colCustom width="5%" property="lpc_price_list_by_formatted" showCheckerId="show-checker" align="right"
                        styleClassCheckerId="${CommercialProposal.style_lpc_price_list_by}"/>
      </logic:notEqual>
      <grid:colCustom width="5%" property="extra_percent_formatted" showCheckerId="show-checker" align="right"/>
      <logic:notEqual name="CommercialProposal" property="cpr_free_prices" value="1">
        <grid:colCustom width="5%" property="discount_percent_formatted" showCheckerId="show-checker" align="right"
                        styleClassCheckerId="${CommercialProposal.style_discount_percent}"/>
      </logic:notEqual>
      <grid:colInput width="10%" textAllign="right" property="sale_price_parking_trans_custom_formatted"
                     showCheckerId="show-checker" styleClassCheckerId="${CommercialProposal.style_sale_price_parking_trans_custom}"
                     onchange="return changeSalePrice" useIndexAsPK="true"/>
      <grid:colCustom width="5%" property="sale_cost_parking_trans_custom_formatted" styleClassCheckerId="${CommercialProposal.style_sale_cost_parking_trans_custom}" showCheckerId="${CommercialProposal.check_sale_cost_parking_trans_custom}" align="right"/>
      <logic:equal name="CommercialProposal" property="cpr_nds_by_string" value="1">
        <grid:colCustom width="5%" property="nds_formatted" styleClassCheckerId="style-checker-long" showCheckerId="show-checker-all" align="right"/>
        <grid:colCustom width="5%" property="cost_nds_formatted" styleClassCheckerId="style-checker-long" showCheckerId="show-checker-all" align="right"/>
      </logic:equal>
      <grid:colDelete width="1" type="script" action="/CommercialProposal.fakeForDelete" onclick="removeFromProducesForAssembleMinskGrid(${record.number})" showWait="false"
                      tooltip="tooltip.CommercialProposalProduces.delete" showCheckerId="show-checker"/>
    </grid:row>
  </grid:table>
</div>  
