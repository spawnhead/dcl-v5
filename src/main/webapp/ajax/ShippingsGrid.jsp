<%@ page import="org.apache.struts.taglib.html.Constants" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>

<div class="gridBackNarrow" id="shippingGrid">
  <grid:table property="grid" key="id" >
    <grid:column width="1"><jsp:attribute name="title"><input type="checkbox" class="grid-header-checkbox" onclick="changeGridSelection(this)"/></jsp:attribute></grid:column>
    <grid:column align="center"><jsp:attribute name="title"><bean:message key="ShippingProduces.name"/></jsp:attribute></grid:column>
    <grid:column align="center"><jsp:attribute name="title"><bean:message key="ShippingProduces.type"/></jsp:attribute></grid:column>
    <grid:column align="center"><jsp:attribute name="title"><bean:message key="ShippingProduces.params"/></jsp:attribute></grid:column>
    <grid:column align="center"><jsp:attribute name="title"><bean:message key="ShippingProduces.add_params"/></jsp:attribute></grid:column>
    <grid:column align="center"><jsp:attribute name="title"><bean:message key="ShippingProduces.stuff"/></jsp:attribute></grid:column>
    <grid:column align="center"><jsp:attribute name="title"><bean:message key="ShippingProduces.ctn_number"/></jsp:attribute></grid:column>
    <grid:column align="center"><jsp:attribute name="title"><bean:message key="ShippingProduces.count"/></jsp:attribute></grid:column>
    <logic:equal name="Shipping" property="showPurchaseSum" value="true">
      <grid:column align="center"><jsp:attribute name="title"><bean:message key="ShippingProduces.purchaseSum"/></jsp:attribute></grid:column>
    </logic:equal>
    <grid:column align="center"><jsp:attribute name="title"><bean:message key="ShippingProduces.transportSum"/></jsp:attribute></grid:column>
    <grid:column align="center"><jsp:attribute name="title"><bean:message key="ShippingProduces.customCharges"/></jsp:attribute></grid:column>
    <grid:column align="center"><jsp:attribute name="title"><bean:message key="ShippingProduces.saleSumPlusNds"/></jsp:attribute></grid:column>
    <grid:column align="center"><jsp:attribute name="title"><bean:message key="ShippingProduces.prc_date"/></jsp:attribute></grid:column>
    <grid:column align="center"><jsp:attribute name="title"><bean:message key="ShippingProduces.manager"/></jsp:attribute></grid:column>
    <logic:equal name="Shipping" property="shp_serial_num_year_out" value="1">
      <grid:column align="center" ><jsp:attribute name="title"><bean:message key="ShippingProduces.shp_serial_num_year_out"/></jsp:attribute></grid:column>
    </logic:equal>
    <logic:equal name="Shipping" property="shp_montage" value="1">
      <grid:column align="center"><jsp:attribute name="title"><bean:message key="ShippingProduces.enterInUseDateMontageTime"/></jsp:attribute></grid:column>
      <grid:column align="center"><jsp:attribute name="title"><bean:message key="ShippingProduces.montageOff"/></jsp:attribute></grid:column>
    </logic:equal>
    <grid:row>
      <grid:colCheckbox  width="1%" property="selected_manager_id" result="grid"
                         resultProperty="selected_manager_id" useIndexAsPK="true"
                         readonlyCheckerId="readOnlyChecker" showCheckerId="controlShowChecker"
                         styleClassCheckerId="style-checker-first-column"/>
      <grid:colCustom property="position.produceNameFormatted" styleClassCheckerId="style-checker"/>
      <grid:colCustom width="5%" property="position.produce.produce.type" styleClassCheckerId="style-checker"/>
      <grid:colCustom width="5%" property="position.produce.produce.params" styleClassCheckerId="style-checker"/>
      <grid:colCustom width="5%" property="position.produce.produce.addParams" styleClassCheckerId="style-checker"/>
      <grid:colServerList  property="position.stuffCategory.name" idName="position.stuffCategory.id"
                           result="grid" resultProperty="position.stuffCategory.name"
                           useIndexAsPK="true" action="/StuffCategoriesListAction" selectOnly="true"
                           readonlyCheckerId="stuffCategoryReadOnlyChecker" showCheckerId="controlShowChecker" styleClassCheckerId="style-checker"/>
      <grid:colCustom width="5%" property="position.catalogNumberForStuffCategory" styleClassCheckerId="style-checker"/>
      <grid:colCustom width="5%" property="position.countFormatted" align="right" styleClassCheckerId="style-checker"/>
      <logic:equal name="Shipping" property="showPurchaseSum" value="true">
        <grid:colCustom width="5%" property="position.purchaseSumFormatted" align="right" styleClassCheckerId="style-checker"/>
      </logic:equal>
      <grid:colCustom width="5%" property="position.transportSumFormatted" align="right" styleClassCheckerId="style-checker"/>
      <grid:colCustom width="5%" property="position.customChargesFormatted" align="right" styleClassCheckerId="style-checker"/>
      <grid:colCustom width="5%" property="position.saleSumPlusNdsFormatted" align="right" styleClassCheckerId="style-checker"/>
      <grid:colCustom width="5%" property="position.prc_date_formatted" styleClassCheckerId="style-checker"/>
      <grid:colCustom width="5%" styleId="ajax${counter-1}" showCheckerId="controlShowChecker" styleClassCheckerId="style-checker">
        <script type="text/javascript" language="JScript">
            doAjax({
              synchronous:true,
              url:'<ctrl:rewrite action="/ShippingAction" dispatch="ajaxManagersGrid"/>',
              params:{counter:'${counter-1}'},
              update:'ajax${counter-1}'
            });
        </script>
      </grid:colCustom>
      <logic:equal name="Shipping" property="shp_serial_num_year_out" value="1">
        <grid:colCustom styleClassCheckerId="style-checker">
          <table width="100%" cellpadding="0" cellspacing="0" class="content">
            <tr>
              <grid:colInput styleClass="" property="position.serialNumber" result="grid"
                             resultProperty="position.serialNumber" useIndexAsPK="true"
                             showCheckerId="controlShowChecker" styleClassCheckerId="style-checker"/>
            </tr>
            <tr>
              <grid:colInput property="position.yearOut" result="grid" resultProperty="position.yearOut"
                             useIndexAsPK="true" showCheckerId="controlShowChecker" styleClassCheckerId="style-checker"/>
            </tr>
          </table>
        </grid:colCustom>
      </logic:equal>
      <logic:equal name="Shipping" property="shp_montage" value="1">
        <grid:colCustom width="5%" styleClassCheckerId="style-checker">
          <table width="100%" cellpadding="0" cellspacing="0" class="content">
            <tr>
              <grid:colDate property="position.enterInUseDate" result="grid"
                            resultProperty="position.enterInUseDate" useIndexAsPK="true"
                            showCheckerId="controlShowChecker" styleClassCheckerId="style-checker"/>
            </tr>
            <tr>
              <grid:colInput width="8%" textAllign="right" property="position.montageTime_formatted"
                             result="grid" resultProperty="position.montageTime_formatted"
                             useIndexAsPK="true" showCheckerId="controlShowChecker" styleClassCheckerId="style-checker"/>
            </tr>
          </table>
        </grid:colCustom>
        <grid:colCheckbox align="center" width="1%" property="position.montageOffCheckbox" result="grid"
                          resultProperty="position.montageOffCheckbox" useIndexAsPK="true"
                          showCheckerId="controlShowChecker" type="script" action="ShippingAction.do"
                          onclick="montageOffClick(this); return true;" showWait="false" hiddenCtrl="true"
                          styleClassCheckerId="style-checker">
        </grid:colCheckbox>
      </logic:equal>
    </grid:row>
  </grid:table>
</div>
