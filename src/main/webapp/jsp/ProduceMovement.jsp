<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>

<ctrl:form styleId="${ProduceMovement.formStyleId}">
  <table class=formBackTop align="center" width="100%">
    <tr>
      <td>
        <table width="100%">
          <tr>
            <td>
              <table width="100%">
                <tr>
                  <td><b><ctrl:write name="ProduceMovement" property="produceFullName"/></b></td>
                  <td align="right">
                    <ctrl:checkbox property="divide_into_chain" styleClass="checkbox" value="1" onclick="submitDispatchForm('reload');"/>&nbsp;
                    <bean:message key="ProduceMovement.divide_into_chain"/>
                  </td>
                </tr>
              </table>
            </td>
          </tr>

          <tr>
            <td>
              <table width="100%">
                <tr>
                  <td>
                    <div class="gridBackNarrow" >
                      <grid:table property="grid" key="id">
                            <th class="table-header" colspan="4"><bean:message key="ProduceMovementList.order"/></th>
                            <th class="table-header" colspan="3"><bean:message key="ProduceMovementList.transit"/></th>
                            <th class="table-header" colspan="3"><bean:message key="ProduceMovementList.produce_cost"/></th>
                            <th class="table-header" colspan="4"><bean:message key="ProduceMovementList.shipping"/></th>
                        </tr>
                        <tr>

                        <grid:column width="5%" align="center"><jsp:attribute name="title"><bean:message key="ProduceMovementList.ord_date"/></jsp:attribute></grid:column>
                        <grid:column width="8%" align="center"><jsp:attribute name="title"><bean:message key="ProduceMovementList.ord_number"/></jsp:attribute></grid:column>
                        <grid:column width="5%" align="center"><jsp:attribute name="title"><bean:message key="ProduceMovementList.ord_produce_count"/></jsp:attribute></grid:column>
                        <grid:column width="5%" align="center"><jsp:attribute name="title"><bean:message key="ProduceMovementList.ord_produce_count_executed"/>&nbsp;<ctrl:help htmlName="ProduceMovementOrdProduceCountExecutedHeader"/></jsp:attribute></grid:column>
                        <grid:column width="5%" align="center"><jsp:attribute name="title"><bean:message key="ProduceMovementList.trn_date"/></jsp:attribute></grid:column>
                        <grid:column width="14%" align="center"><jsp:attribute name="title"><bean:message key="ProduceMovementList.trn_number"/></jsp:attribute></grid:column>
                        <grid:column width="5%" align="center"><jsp:attribute name="title"><bean:message key="ProduceMovementList.trn_produce_count"/></jsp:attribute></grid:column>
                        <grid:column width="5%" align="center"><jsp:attribute name="title"><bean:message key="ProduceMovementList.prc_date"/></jsp:attribute></grid:column>
                        <grid:column align="center"><jsp:attribute name="title"><bean:message key="ProduceMovementList.prc_number"/></jsp:attribute></grid:column>
                        <grid:column width="10%" align="center"><jsp:attribute name="title"><bean:message key="ProduceMovementList.prc_produce_count"/></jsp:attribute></grid:column>
                        <grid:column width="5%" align="center"><jsp:attribute name="title"><bean:message key="ProduceMovementList.shp_date"/></jsp:attribute></grid:column>
                        <grid:column width="8%" align="center"><jsp:attribute name="title"><bean:message key="ProduceMovementList.shp_number"/></jsp:attribute></grid:column>
                        <grid:column width="10%" align="center"><jsp:attribute name="title"><bean:message key="ProduceMovementList.shp_contractor"/></jsp:attribute></grid:column>
                        <grid:column width="5%" align="center"><jsp:attribute name="title"><bean:message key="ProduceMovementList.shp_produce_count"/></jsp:attribute></grid:column>

                        <grid:row rowParserId="row-parser">
                          <grid:colCustom width="5%" property="ordDateFormatted" styleClassCheckerId="style-checker"/>
                          <grid:colCustom width="8%" property="ordNumberFormatted" styleClassCheckerId="style-checker"/>
                          <grid:colCustom width="5%" align="right" property="ordProduceCountFormatted" styleClassCheckerId="style-checker"/>
                          <grid:colCustom width="5%" align="right" property="ordProduceCountExecutedFormatted" styleClassCheckerId="style-checker"/>
                          <grid:colCustom width="5%" property="trnDateFormatted" styleClassCheckerId="style-checker"/>
                          <grid:colCustom width="14%" property="trnNumberFormatted" styleClassCheckerId="style-checker"/>
                          <grid:colCustom width="5%" align="right" property="trnProduceCountAndRest" styleClassCheckerId="style-checker"/>
                          <grid:colCustom width="5%" property="prcDateFormatted" styleClassCheckerId="style-checker"/>
                          <grid:colCustom property="prc_number" styleClassCheckerId="style-checker"/>
                          <grid:colCustom width="10%" align="right" property="prcProduceCountAndRest" styleClassCheckerId="style-checker"/>
                          <grid:colCustom width="5%" property="shpDateFormatted" styleClassCheckerId="style-checker"/>
                          <grid:colCustom width="8%" property="shp_number" styleClassCheckerId="style-checker"/>
                          <grid:colCustom width="10%" property="shp_contractor" styleClassCheckerId="style-checker"/>
                          <grid:colCustom width="5%" align="right" property="shpProduceCountFormatted" styleClassCheckerId="style-checker"/>
                        </grid:row>
                      </grid:table>
                    </div>
                  </td>
                </tr>
              </table>
            </td>
          </tr>
          <logic:equal name="ProduceMovement" property="showLegend" value="true">
            <tr>
              <td>
                <bean:message key="ProduceMovement.legend"/>
              </td>
            </tr>
            <logic:equal name="ProduceMovement" property="showLegendInput" value="true">
              <tr>
                <td style="color:#008000">
                  <bean:message key="ProduceMovement.legend_text_input"/>
                </td>
              </tr>
            </logic:equal>
            <logic:equal name="ProduceMovement" property="showLegendTransit" value="true">
              <tr>
                <td style="color:#008000">
                  <bean:message key="ProduceMovement.legend_text_transit"/>
                </td>
              </tr>
            </logic:equal>
            <logic:equal name="ProduceMovement" property="showLegendOrder" value="true">
              <tr>
                <td style="color:#008000">
                  <bean:message key="ProduceMovement.legend_text_order"/>
                </td>
              </tr>
            </logic:equal>
          </logic:equal>
          <tr>
            <td colspan="2" align="right" class=formSpace>
              &nbsp;
            </td>
          </tr>
          <tr>
            <td colspan="2" align="right">
              <ctrl:ubutton type="link" dispatch="back" styleClass="width80" readonly="false">
                <bean:message key="button.back"/>
              </ctrl:ubutton>
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
</ctrl:form>
