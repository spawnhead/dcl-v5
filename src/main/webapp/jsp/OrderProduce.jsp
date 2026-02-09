<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>

<ctrl:askUser name="deleteRowAsk" key="msg.order_produce.delete_row" showOkCancel="false" height="120"/>

<ctrl:form readonly="${OrderProduce.formReadOnly}">
  <ctrl:hidden property="number"/>
  <ctrl:hidden property="opr_id"/>
  <ctrl:hidden property="ord_id"/>
  <ctrl:hidden property="opr_use_prev_number"/>
  <ctrl:hidden property="donot_calculate_netto"/>
  <ctrl:hidden property="ord_discount_all"/>
  <ctrl:hidden property="ord_date_conf_all"/>
  <ctrl:hidden property="ord_ready_for_deliv_date_all"/>
  <ctrl:hidden property="stf_id"/>
  <ctrl:hidden property="opr_occupied"/>
  <ctrl:hidden property="opr_count_executed"/>
  <ctrl:hidden property="opr_count_occupied"/>
  <ctrl:hidden property="ord_all_include_in_spec"/>
  <ctrl:hidden property="ord_by_guaranty"/>
  <ctrl:hidden property="course"/>
  <ctrl:hidden property="drpPriceCoefficient"/>
  <ctrl:hidden property="contractorOpr.id"/>
  <ctrl:hidden property="contractorOpr.name"/>
  <ctrl:hidden property="contractOpr.con_id"/>
  <ctrl:hidden property="contractOpr.con_number"/>
  <ctrl:hidden property="specificationOpr.spc_id"/>
  <ctrl:hidden property="specificationOpr.spc_number"/>
  <logic:equal name="OrderProduce" property="donot_calculate_netto" value="1">
    <ctrl:hidden property="opr_price_brutto"/>
    <ctrl:hidden property="opr_discount"/>
  </logic:equal>
  <logic:notEqual name="OrderProduce" property="donot_calculate_netto" value="1">
    <ctrl:hidden property="opr_price_netto"/>
  </logic:notEqual>
  <table class=formBackTop align="center" width="70%">
    <tr>
      <td >
        <table cellspacing="2" width="100%">
          <tr>
            <td width="280px"><bean:message key="OrderProduce.opr_produce_name"/></td>
            <td>
              <ctrl:text property="produce.name" style="width:265px;" readonly="true"/>
              <ctrl:ubutton styleId="buttonSelectProduce" type="submit" dispatch="selectProduce" scriptUrl="stf_id=$(stf_id)" styleClass="width80" readonly="${OrderProduce.readOnlyIfNotLikeManager}">
                <bean:message key="button.select"/>
              </ctrl:ubutton>
            </td>
          </tr>
          <tr>
            <td><bean:message key="OrderProduce.opr_produce_name_old_version"/></td>
            <td><ctrl:textarea property="opr_produce_name" style="width:350px;height:80px;" readonly="true"/></td>
          </tr>
          <tr>
            <td><bean:message key="OrderProduce.opr_catalog_num_old_version"/></td>
            <td><ctrl:text property="opr_catalog_num" readonly="true"/></td>
          </tr>
          <tr>
            <td><bean:message key="OrderProduce.opr_count"/></td>
            <td><ctrl:text property="opr_count" readonly="${OrderProduce.readOnlyIfNotLikeManager}"/></td>
          </tr>
          <logic:notEqual name="OrderProduce" property="donot_calculate_netto" value="1">
            <tr>
              <td><bean:message key="OrderProduce.opr_price_brutto"/></td>
              <td><ctrl:text property="opr_price_brutto" readonly="${OrderProduce.readOnlyIfNotLikeManager}"/></td>
            </tr>
            <logic:notEqual name="OrderProduce" property="ord_discount_all" value="1">
              <tr>
                <td><bean:message key="OrderProduce.opr_discount"/></td>
                <td><ctrl:text property="opr_discount" readonly="${OrderProduce.readOnlyIfNotLikeManager}"/></td>
              </tr>
            </logic:notEqual>
          </logic:notEqual>
          <logic:equal name="OrderProduce" property="donot_calculate_netto" value="1">
            <tr>
              <td><bean:message key="OrderProduce.opr_price_netto"/></td>
              <td><ctrl:text property="opr_price_netto" readonly="${OrderProduce.readOnlyIfNotLikeManager}"/></td>
            </tr>
          </logic:equal>
          <logic:equal name="OrderProduce" property="ord_all_include_in_spec" value="1">
            <logic:notEqual name="OrderProduce" property="ord_by_guaranty" value="1">
              <tr>
                <td><bean:message key="OrderProduce.drp_price"/></td>
                <td>
                  <ctrl:text property="drp_price" style="width:90px;" readonly="${OrderProduce.readOnlyDrpPrice}"/>&nbsp;&nbsp;
                  <ctrl:checkbox property="drp_max_extra" styleClass="checkbox" value="1"/>&nbsp;
                  <bean:message key="OrderProduce.drp_max_extra"/>
                </td>
              </tr>
            </logic:notEqual>
          </logic:equal>
          <tr>
            <td><bean:message key="OrderProduce.opr_comment"/>&nbsp;<span style="color:#008000"><bean:message key="OrderProduce.opr_comment1"/></span></td>
            <td><ctrl:textarea property="opr_comment" style="width:350px;height:80px;" readonly="${OrderProduce.readOnlyIfNotLikeManager}"/></td>
          </tr>
          <tr>
            <td colspan="2">
              <hr>
            </td>
          </tr>
          <tr>
            <td colspan="2">
              <bean:message key="OrderProduce.fill_logistic"/>
            </td>
          </tr>
          <tr>
            <td colspan="2">
              <bean:message key="OrderProduce.production_term"/>
            </td>
          </tr>
          <tr>
            <td colspan="2">
              <div class="gridBackNarrow">
                <grid:table property="gridProductTerm" key="numberProductionTerm" >
                  <grid:column width="15%" align="center"><jsp:attribute name="title"><bean:message key="OrderProduce.ptr_count"/></jsp:attribute></grid:column>
                  <grid:column width="10%" align="center"><jsp:attribute name="title"><bean:message key="OrderProduce.ptr_date"/></jsp:attribute></grid:column>
                  <grid:column align="center"><jsp:attribute name="title"><bean:message key="OrderProduce.ptr_comment"/></jsp:attribute></grid:column>
                  <grid:column width="1%" title=""/>
                  <grid:row>
                    <grid:colInput width="15%" textAllign="right" property="ptr_count_formatted" result="gridProductTerm" resultProperty="ptr_count_formatted" useIndexAsPK="true" readonlyCheckerId="productionTermReadonly"/>
                    <grid:colDate property="ptr_date_formatted" result="gridProductTerm" resultProperty="ptr_date_formatted" useIndexAsPK="true" readonlyCheckerId="productionTermReadonly"/>
                    <grid:colInput property="ptr_comment" result="gridProductTerm" resultProperty="ptr_comment" useIndexAsPK="true" readonlyCheckerId="readonlyNotLikeLogist"/>
                    <grid:colDelete width="1%" dispatch="deleteProductTerm" type="submit" tooltip="tooltip.OrderProduce.deleteProductTerm" readonlyCheckerId="productionTermReadonly" askUser="deleteRowAsk"/>
                  </grid:row>
                </grid:table>
              </div>
            </td>
          </tr>
          <tr>
            <td colspan="2">
              <div class=gridBottom>
                <ctrl:ubutton type="submit" dispatch="newProductTerm" readonly="${OrderProduce.readOnlyNewProductTerm}" styleClass="width165">
                  <bean:message key="button.add"/>
                </ctrl:ubutton>
              </div>
            </td>
          </tr>
          <tr>
            <td colspan="2">
              <bean:message key="OrderProduce.ready_for_shipping"/>
            </td>
          </tr>
          <tr>
            <td colspan="2">
              <div class="gridBackNarrow">
                <grid:table property="gridReadyForShipping" key="numberReadyForShipping" >
                  <grid:column width="10%" align="center"><jsp:attribute name="title"><bean:message key="OrderProduce.shp_doc_type"/></jsp:attribute></grid:column>
                  <grid:column width="10%" align="center"><jsp:attribute name="title"><bean:message key="OrderProduce.rfs_number"/></jsp:attribute></grid:column>
                  <grid:column width="10%" align="center"><jsp:attribute name="title"><bean:message key="OrderProduce.rfs_count"/></jsp:attribute></grid:column>
                  <grid:column width="10%" align="center"><jsp:attribute name="title"><bean:message key="OrderProduce.rfs_date"/></jsp:attribute></grid:column>
                  <grid:column width="10%" align="center"><jsp:attribute name="title"><bean:message key="OrderProduce.rfs_ship_from_stock"/></jsp:attribute></grid:column>
                  <grid:column width="10%" align="center"><jsp:attribute name="title"><bean:message key="OrderProduce.rfs_arrive_in_lithuania"/></jsp:attribute></grid:column>
                  <grid:column width="10%" align="center"><jsp:attribute name="title"><bean:message key="OrderProduce.rfs_weight"/></jsp:attribute></grid:column>
                  <grid:column width="10%" align="center"><jsp:attribute name="title"><bean:message key="OrderProduce.rfs_gabarit"/></jsp:attribute></grid:column>
                  <grid:column align="center"><jsp:attribute name="title"><bean:message key="OrderProduce.rfs_comment"/></jsp:attribute></grid:column>
                  <grid:column width="1%" title=""/>
                  <grid:row>
                    <grid:colServerList property="shippingDocType.name" idName="shippingDocType.id" result="gridReadyForShipping" resultProperty="shippingDocType.name"
                                        useIndexAsPK="true" action="/ShippingDocTypesListAction" selectOnly="true" readonlyCheckerId="readyForShippingReadonly"/>
                    <grid:colInput width="10%" property="rfs_number" result="gridReadyForShipping" resultProperty="rfs_number" useIndexAsPK="true" readonlyCheckerId="readyForShippingReadonly"/>
                    <grid:colInput width="10%" textAllign="right" property="rfs_count_formatted" result="gridReadyForShipping" resultProperty="rfs_count_formatted" useIndexAsPK="true" readonlyCheckerId="readyForShippingReadonly"/>
                    <grid:colDate property="rfs_date_formatted" result="gridReadyForShipping" resultProperty="rfs_date_formatted" useIndexAsPK="true" readonlyCheckerId="readyForShippingReadonly"/>
                    <grid:colDate property="rfs_ship_from_stock_formatted" result="gridReadyForShipping" resultProperty="rfs_ship_from_stock_formatted" useIndexAsPK="true" readonlyCheckerId="readyForShippingReadonly"/>
                    <grid:colDate property="rfs_arrive_in_lithuania_formatted" result="gridReadyForShipping" resultProperty="rfs_arrive_in_lithuania_formatted" useIndexAsPK="true" readonlyCheckerId="rfsArriveInLithuaniaReadonly"/>
                    <grid:colInput width="10%" textAllign="right" property="rfs_weight_formatted" result="gridReadyForShipping" resultProperty="rfs_weight_formatted" useIndexAsPK="true" readonlyCheckerId="readonlyNotLikeLogist"/>
                    <grid:colInput width="10%" property="rfs_gabarit" result="gridReadyForShipping" resultProperty="rfs_gabarit" useIndexAsPK="true" readonlyCheckerId="readonlyNotLikeLogist"/>
                    <grid:colInput property="rfs_comment" result="gridReadyForShipping" resultProperty="rfs_comment" useIndexAsPK="true" readonlyCheckerId="readonlyNotLikeLogist"/>
                    <grid:colDelete width="1%" dispatch="deleteReadyForShipping" type="submit" tooltip="tooltip.OrderProduce.deleteReadyForShipping" readonlyCheckerId="readyForShippingReadonly" askUser="deleteRowAsk"/>
                  </grid:row>
                </grid:table>
              </div>
            </td>
          </tr>
          <tr>
            <td colspan="2">
              <div class=gridBottom>
                <ctrl:ubutton type="submit" dispatch="newReadyForShipping" readonly="${OrderProduce.readOnlyNewReadyForShipping}" styleClass="width165">
                  <bean:message key="button.add"/>
                </ctrl:ubutton>
              </div>
            </td>
          </tr>
          <tr>
            <td colspan="2" align="right" class=formSpace>
              &nbsp;
            </td>
          </tr>
          <tr>
            <td colspan="2" align="right">
              <ctrl:ubutton type="link" dispatch="back" styleClass="width80" readonly="false">
                <bean:message key="button.cancel"/>
              </ctrl:ubutton>
              <input id='buttonSave' type='button' onclick='processClick()' class='width80' value='<bean:message key="button.save"/>'/>
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
</ctrl:form>

<script language="JScript" type="text/javascript">
  var stf_id = document.getElementById("stf_id");
  var buttonSelectProduce = document.getElementById("buttonSelectProduce");

  var buttonSave = document.getElementById("buttonSave");
  buttonSave.disabled = ${OrderProduce.readOnlySave};
  var ord_all_include_in_spec = document.getElementById("ord_all_include_in_spec");
  var ord_by_guaranty = document.getElementById("ord_by_guaranty");
  var drp_price = document.getElementById("drp_price");
  var course = document.getElementById("course");
  var opr_price_netto = document.getElementById("opr_price_netto");
  var drpPriceCoefficient = document.getElementById("drpPriceCoefficient");

  StuffCategorySelect();

  function StuffCategorySelect()
  {
    if ( stf_id.value == '' )
    {
      buttonSelectProduce.disabled = true;
    }
  }

  function processClick()
  {
    if ( ord_all_include_in_spec.value == "" || ord_by_guaranty.value == '1' )
    {
      submitDispatchForm("process");
      return;
    }

    // Когда заказ будет исполнен, весь товар сразу включить в спецификацию (импорт)= ДА
    // осуществлять проверку по следующей формуле:
    // если a/b/c>1,5, где
    // a - Продажная цена за единицу без НДС, бел.руб.
    // b - курс из справочника "валюты" валюты, указанной в графе "Валюта" на дату заказа
    // с - Цена нетто,
    // то выдавать сообщение
    var drpPrice = parseFloat(getSumForJS(drp_price.value));
    var checkCourse = parseFloat(getSumForJS(course.value));
    var oprPriceNetto = parseFloat(getSumForJS(opr_price_netto.value));
    var checkCoefficient = parseFloat(getSumForJS(drpPriceCoefficient.value));
    if ( checkCourse != 0 && oprPriceNetto != 0 )
    {
      if ( drpPrice / checkCourse / oprPriceNetto > checkCoefficient )
      {
        if (!isUserAgree('<bean:message key="msg.order_produce.check_dlr_price"/>', true, 500, 180))
        {
          return;
        }
      }
    }

    submitDispatchForm("process");
  }

  function showMsg()
  {
  <logic:equal value="true" name="OrderProduce" property="showMsg">
    if (isUserAgree('<bean:message key="msg.order_produce.double_produce"/>', true, 400, 100))
    {
      submitDispatchForm("forceProcess");
    }
  </logic:equal>
  }
  initFunctions.push(showMsg);
</script>
