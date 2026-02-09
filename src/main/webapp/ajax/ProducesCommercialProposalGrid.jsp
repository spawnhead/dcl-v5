<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>

<div class="gridBackNarrow" id="producesCommercialProposalGrid">
  <grid:table property="gridProduces" key="number" >
    <grid:column title=""/>
    <grid:column align="center"><jsp:attribute name="title"><bean:message key="CommercialProposalProduces.number"/></jsp:attribute></grid:column>
    <grid:column align="center"><jsp:attribute name="title"><bean:message key="CommercialProposalProduces.produce_name"/></jsp:attribute></grid:column>
    <logic:notEqual name="CommercialProposal" property="cpr_old_version" value="1">
      <grid:column align="center"><jsp:attribute name="title"><bean:message key="CommercialProposalProduces.stuffCategory"/></jsp:attribute></grid:column>
    </logic:notEqual>
    <grid:column align="center"><jsp:attribute name="title"><bean:message key="CommercialProposalProduces.catalog_num"/></jsp:attribute></grid:column>
    <logic:notEqual name="CommercialProposal" property="cpr_donot_calculate_netto" value="1">
      <grid:column align="center"><jsp:attribute name="title">${CommercialProposal.lprPriceBruttoHeader}</jsp:attribute></grid:column>
      <grid:column align="center"><jsp:attribute name="title"><bean:message key="CommercialProposalProduces.lpr_discount"/></jsp:attribute></grid:column>
    </logic:notEqual>
    <grid:column align="center"><jsp:attribute name="title">${CommercialProposal.lprPriceNettoHeader}</jsp:attribute></grid:column>
    <grid:column align="center"><jsp:attribute name="title"><bean:message key="CommercialProposalProduces.lpr_count"/></jsp:attribute></grid:column>
    <grid:column align="center"><jsp:attribute name="title"><bean:message key="CommercialProposalProduces.custom_code"/></jsp:attribute></grid:column>
    <grid:column align="center"><jsp:attribute name="title"><bean:message key="CommercialProposalProduces.custom_percent"/></jsp:attribute></grid:column>
    <grid:column align="center"><jsp:attribute name="title"><bean:message key="CommercialProposalProduces.lpr_coeficient"/></jsp:attribute></grid:column>
    <grid:column align="center"><jsp:attribute name="title"><bean:message key="CommercialProposalProduces.sale_price"/></jsp:attribute></grid:column>
    <grid:column align="center"><jsp:attribute name="title"><bean:message key="CommercialProposalProduces.sale_cost"/></jsp:attribute></grid:column>
    <grid:column align="center"><jsp:attribute name="title"><bean:message key="CommercialProposalProduces.parking_trans"/></jsp:attribute></grid:column>
    <grid:column align="center"><jsp:attribute name="title"><bean:message key="CommercialProposalProduces.sale_price_parking_trans"/></jsp:attribute></grid:column>
    <grid:column align="center"><jsp:attribute name="title"><bean:message key="CommercialProposalProduces.sale_cost_parking_trans"/></jsp:attribute></grid:column>
    <grid:column align="center"><jsp:attribute name="title"><bean:message key="CommercialProposalProduces.custom_duty"/></jsp:attribute></grid:column>
    <grid:column align="center"><jsp:attribute name="title"><bean:message key="CommercialProposalProduces.sale_price_parking_trans_custom"/></jsp:attribute></grid:column>
    <grid:column align="center"><jsp:attribute name="title"><bean:message key="CommercialProposalProduces.sale_cost_parking_trans_custom"/></jsp:attribute></grid:column>
    <logic:equal name="CommercialProposal" property="cpr_nds_by_string" value="1">
      <grid:column align="center"><jsp:attribute name="title"><bean:message key="CommercialProposalProduces.nds"/></jsp:attribute></grid:column>
      <grid:column align="center"><jsp:attribute name="title"><bean:message key="CommercialProposalProduces.cost_nds"/></jsp:attribute></grid:column>
    </logic:equal>
    <grid:column title=""/>
    <grid:column title=""/>
    <grid:column><jsp:attribute name="title"><img title='<bean:message key="tooltip.CommercialProposalProduces.deleteAll"/>' src='<ctrl:rewrite page="/images/sub.gif"/>' onclick='deleteAllroducesCommercialProposalGrid()' style='cursor:pointer;'></jsp:attribute></grid:column>
    <grid:row>
      <grid:colImage width="1" action="/CommercialProposalAction.do?dispatch=newProduce"
                     type="submit" tooltip="tooltip.CommercialProposalProduces.insertBefore"
                     image="images/arrow_insert.gif" styleClass="grid-image-without-border"
                     showCheckerId="show-checker"
                     scriptUrl="numberBefore=${record.number}&donot_calculate_netto=$(cpr_donot_calculate_netto)&cpr_reverse_calc=$(cpr_reverse_calc)&cur_id=$(currency.id)"/>
      <grid:colCustom width="2%" property="number" styleClassCheckerId="style-checker-short"/>
      <grid:colCustom property="produce_name" styleClassCheckerId="style-checker-long"/>
      <logic:notEqual name="CommercialProposal" property="cpr_old_version" value="1">
        <grid:colCustom width="5%" property="stuffCategory.name" showCheckerId="show-checker" styleClassCheckerId="style-checker"/>
      </logic:notEqual>
      <grid:colCustom width="5%" property="catalog_num" showCheckerId="show-checker" styleClassCheckerId="style-checker-short" align="right"/>
      <logic:notEqual name="CommercialProposal" property="cpr_donot_calculate_netto" value="1">
        <grid:colCustom width="5%" property="priceBruttoFormatted" showCheckerId="show-checker" styleClassCheckerId="style-checker" align="right"/>
        <grid:colCustom width="5%" property="discountFormatted" showCheckerId="show-checker" styleClassCheckerId="style-checker" align="right"/>
      </logic:notEqual>
      <logic:equal value="false" name="record" property="itogLine">
        <grid:colCustom width="5%" property="priceNettoFormatted" showCheckerId="show-checker" styleClassCheckerId="style-checker" align="right"/>
      </logic:equal>
      <logic:equal value="true" name="record" property="itogLine">
        <grid:colCustom align="center">
          <bean:message key="CommercialProposalProduces.SumLprPrice"/>&nbsp;<ctrl:help htmlName="SumLprPrice"/><br>${record.sumLprPriceFormatted}
        </grid:colCustom>
      </logic:equal>
      <grid:colCustom width="5%" property="lpr_count_formatted" showCheckerId="show-checker" styleClassCheckerId="style-checker-short" align="right"/>
      <grid:colCustom width="5%" property="code" styleClassCheckerId="style-checker-custom"/>
      <grid:colCustom width="5%" property="customPercentFormatted" showCheckerId="show-checker" styleClassCheckerId="style-checker-custom" align="right"/>
      <grid:colCustom width="5%" property="lpr_coeficient_formatted" showCheckerId="show-checker" styleClassCheckerId="style-checker" align="right"/>
      <grid:colCustom width="5%" property="sale_price_formatted" styleClassCheckerId="${CommercialProposal.style_sale_price}" showCheckerId="show-checker" align="right"/>
      <grid:colCustom width="5%" property="sale_cost_formatted" styleClassCheckerId="${CommercialProposal.style_sale_cost}" showCheckerId="${CommercialProposal.check_sale_cost}" align="right"/>
      <grid:colCustom width="5%" property="parking_trans_formatted" showCheckerId="show-checker" styleClassCheckerId="style-checker" align="right"/>
      <grid:colCustom width="5%" property="sale_price_parking_trans_formatted" styleClassCheckerId="${CommercialProposal.style_sale_price_parking_trans}" showCheckerId="show-checker" align="right"/>
      <grid:colCustom width="5%" property="sale_cost_parking_trans_formatted" styleClassCheckerId="${CommercialProposal.style_sale_cost_parking_trans}" showCheckerId="${CommercialProposal.check_sale_cost_parking_trans}" align="right"/>
      <grid:colCustom width="5%" property="custom_duty_formatted" showCheckerId="show-checker" styleClassCheckerId="style-checker" align="right"/>
      <grid:colCustom width="5%" property="sale_price_parking_trans_custom_formatted" styleClassCheckerId="${CommercialProposal.style_sale_price_parking_trans_custom}" showCheckerId="show-checker" align="right"/>
      <grid:colCustom width="5%" property="sale_cost_parking_trans_custom_formatted" styleClassCheckerId="${CommercialProposal.style_sale_cost_parking_trans_custom}" showCheckerId="${CommercialProposal.check_sale_cost_parking_trans_custom}" align="right"/>
      <logic:equal name="CommercialProposal" property="cpr_nds_by_string" value="1">
        <grid:colCustom width="5%" property="nds_formatted" styleClassCheckerId="style-checker-long" showCheckerId="show-checker-all" align="right"/>
        <grid:colCustom width="5%" property="cost_nds_formatted" styleClassCheckerId="style-checker-long" showCheckerId="show-checker-all" align="right"/>
      </logic:equal>
      <grid:colImage width="1" action="/CommercialProposalProduceAction.do?dispatch=clone"
                     type="link" tooltip="tooltip.CommercialProposalProduces.clone"
                     image="images/copy.gif" styleClass="grid-image-without-border"
                     showCheckerId="show-checker"
                     scriptUrl="cpr_reverse_calc=$(cpr_reverse_calc)&cur_id=$(currency.id)"/>
      <grid:colEdit width="1" dispatch="editProduce" type="submit"
                    action="/CommercialProposalAction.do" tooltip="tooltip.CommercialProposalProduces.edit"
                    showCheckerId="show-checker"
                    scriptUrl="donot_calculate_netto=$(cpr_donot_calculate_netto)&cpr_reverse_calc=$(cpr_reverse_calc)&cur_id=$(currency.id)"/>
      <grid:colDelete width="1" type="script" action="/CommercialProposal.fakeForDelete" onclick="removeFromCommercialProposalGrid(${record.number})" showWait="false"
                      tooltip="tooltip.CommercialProposalProduces.delete" showCheckerId="show-checker"/>
    </grid:row>
  </grid:table>
</div>
