<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>

<ctrl:form >
  <ctrl:hidden property="number"/>
  <ctrl:hidden property="drp_id"/>
  <ctrl:hidden property="dlr_fair_trade"/>
  <ctrl:hidden property="dlr_need_deliver"/>
  <ctrl:hidden property="dlr_include_in_spec"/>
  <ctrl:hidden property="dlr_guarantee_repair"/>
  <ctrl:hidden property="opr_id"/>
  <ctrl:hidden property="asm_id"/>
  <ctrl:hidden property="apr_id"/>
  <ctrl:hidden property="ord_number"/>
  <ctrl:hidden property="ord_date"/>
  <ctrl:hidden property="sip_id"/>
  <ctrl:hidden property="spi_number"/>
  <ctrl:hidden property="spi_date"/>
  <ctrl:hidden property="drp_occupied"/>
  <ctrl:hidden property="ordInfoAssemble"/>
  <ctrl:hidden property="customerAssemble"/>
  <ctrl:hidden property="showMaxExtra"/>
  <ctrl:hidden property="course"/>
  <ctrl:hidden property="opr_price_netto"/>
  <ctrl:hidden property="drpPriceCoefficient"/>
  <logic:equal name="DeliveryRequestProduce" property="dlr_fair_trade" value="1">
    <logic:notEqual name="DeliveryRequestProduce" property="dlr_include_in_spec" value="1">
      <ctrl:hidden property="drp_price"/>
    </logic:notEqual>
    <logic:notEqual name="DeliveryRequestProduce" property="dlr_need_deliver" value="1">
      <ctrl:hidden property="receiveManager.usr_id"/>
      <ctrl:hidden property="receive_date"/>
    </logic:notEqual>
  </logic:equal>
  <logic:notEqual name="DeliveryRequestProduce" property="dlr_fair_trade" value="1">
    <logic:equal name="DeliveryRequestProduce" property="dlr_guarantee_repair" value="1">
      <ctrl:hidden property="drp_price"/>
    </logic:equal>
  </logic:notEqual>
  <table class=formBack align="center">
    <tr>
      <td>
        <table cellspacing="2">
          <tr>
            <td><bean:message key="DeliveryRequestProduce.stuffCategory"/></td>
            <td><ctrl:serverList property="stuffCategory.name" idName="stuffCategory.id" action="/StuffCategoriesListAction"
                                 filter="filter" style="width:237px;" callback="onStuffCategorySelect"
                                 readonly="${DeliveryRequestProduce.readonliLikeImported}"/>
            </td>
          </tr>
          <tr>
            <td><bean:message key="DeliveryRequestProduce.produce_name"/></td>
            <td>
              <ctrl:text property="produce.name" style="width:257px;" readonly="true"/>
              <ctrl:ubutton styleId="buttonSelectProduce" type="submit" dispatch="selectProduce" scriptUrl="stf_id=$(stuffCategory.id)" styleClass="width80" readonly="${DeliveryRequestProduce.readonliLikeImported}">
                <bean:message key="button.select"/>
              </ctrl:ubutton>
            </td>
          </tr>
          <tr>
            <td><bean:message key="DeliveryRequestProduce.produce_type"/></td>
            <td><ctrl:write name="DeliveryRequestProduce" property="produce.type"/></td>
          </tr>
          <tr>
            <td><bean:message key="DeliveryRequestProduce.produce_params"/></td>
            <td><ctrl:write name="DeliveryRequestProduce" property="produce.params"/></td>
          </tr>
          <tr>
            <td><bean:message key="DeliveryRequestProduce.produce_add_params"/></td>
            <td><ctrl:write name="DeliveryRequestProduce" property="produce.addParams"/></td>
          </tr>
          <logic:equal name="DeliveryRequestProduce" property="showDrpPrice" value="true">
            <tr>
              <td><bean:message key="DeliveryRequestProduce.drp_price"/></td>
              <td>
                <ctrl:text property="drp_price" style="width:90px;"/>&nbsp;&nbsp;
                <logic:equal name="DeliveryRequestProduce" property="showMaxExtra" value="true">
                  <ctrl:checkbox property="drp_max_extra" styleClass="checkbox" value="1"/>&nbsp;
                  <bean:message key="DeliveryRequestProduce.drp_max_extra"/>
                </logic:equal>
              </td>
            </tr>
          </logic:equal>
          <tr>
            <td><bean:message key="DeliveryRequestProduce.drp_count"/></td>
            <td><ctrl:text property="drp_count" style="width:257px;" /></td>
          </tr>
          <logic:notEqual name="DeliveryRequestProduce" property="dlr_fair_trade" value="1">
            <tr>
              <td><bean:message key="DeliveryRequestProduce.customer"/></td>
              <td><ctrl:serverList property="customer.name" idName="customer.id" action="/ContractorsListAction"
                                   filter="filter"  callback="onChangeContractor" style="width:237px;"/></td>
            </tr>
            <tr>
              <td><bean:message key="DeliveryRequestProduce.contract"/></td>
              <td><ctrl:serverList property="contractNumberWithDate" idName="contract.con_id"
                                             action="/ContractsDepFromContractorListAction"
                                             scriptUrl="ctr_id=$(customer.id)" callback="onChangeContract"
                                             selectOnly="true"  style="width:237px;"/></td>
            </tr>
            <tr>
              <td><bean:message key="DeliveryRequestProduce.specification"/></td>
              <td><ctrl:serverList property="specificationNumberWithDate" idName="specification.spc_id"
                                               action="/SpecificationsDepFromContractListAction"
                                               scriptUrl="id=$(contract.con_id)"
                                               selectOnly="true" style="width:237px;"/></td>
            </tr>
          </logic:notEqual>
          <logic:notEqual name="DeliveryRequestProduce" property="dlr_fair_trade" value="1">
            <tr>
              <td><bean:message key="DeliveryRequestProduce.purpose"/></td>
              <td><ctrl:serverList property="purpose.name" idName="purpose.id" action="/PurposesListAction"
                                   selectOnly="true" style="width:237px;" readonly="${DeliveryRequestProduce.readonliLikeGuarantee}"/></td>
            </tr>
          </logic:notEqual>
          <logic:equal name="DeliveryRequestProduce" property="dlr_fair_trade" value="1">
            <tr>
              <td><bean:message key="DeliveryRequestProduce.drp_purpose"/></td>
              <td><ctrl:textarea property="drp_purpose" style="width:257px;height:80px;"/></td>
            </tr>
            <tr>
              <td><bean:message key="DeliveryRequestProduce.purpose"/></td>
              <td><ctrl:serverList property="purpose.name" idName="purpose.id" action="/PurposesListAction" selectOnly="true" style="width:237px;" readonly="true"/></td>
            </tr>
            <logic:equal name="DeliveryRequestProduce" property="dlr_need_deliver" value="1">
              <tr>
                <td><bean:message key="DeliveryRequestProduce.receive_manager"/></td>
                <td><ctrl:serverList property="receiveManager.userFullName" idName="receiveManager.usr_id" action="/UsersListAction" selectOnly="true" style="width:237px;"/></td>
              </tr>
              <tr>
                <td><bean:message key="DeliveryRequestProduce.receive_date"/></td>
                <td><ctrl:date property="receive_date" style="width:237px;"/></td>
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
              <ctrl:ubutton type="link" dispatch="back" styleClass="width80">
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

  var stuff_category_id = document.getElementById("stuffCategory.id");
  var produce_name = document.getElementById("produce.name");
  var buttonSelectProduce = document.getElementById("buttonSelectProduce");

  var buttonSave = document.getElementById("buttonSave");
  <logic:equal name="DeliveryRequestProduce" property="showDrpPrice" value="true">
    var drp_price = document.getElementById("drp_price");
    var course = document.getElementById("course");
    var opr_price_netto = document.getElementById("opr_price_netto");
    var drpPriceCoefficient = document.getElementById("drpPriceCoefficient");
  </logic:equal>
  
  <logic:notEqual value="true" name="DeliveryRequestProduce" property="readonliLikeImported">
    onStuffCategoryLoad();
  </logic:notEqual>

  function onStuffCategoryLoad()
  {
    buttonSelectProduce.disabled = (stuff_category_id.value == '');
  }

  function onStuffCategorySelect()
  {
    buttonSelectProduce.disabled = false;
    produce_name.value = '';
  }

  function processClick()
  {
    <logic:equal name="DeliveryRequestProduce" property="showDrpPrice" value="false">
      submitDispatchForm("process");
    </logic:equal>

    <logic:equal name="DeliveryRequestProduce" property="showDrpPrice" value="true">
      // осуществлять проверку по следующей формуле:
      // если a/b/c>1,5, где
      // a - Продажная цена за единицу без НДС, бел.руб.
      // b - курс из справочника "валюты" валюты, указанной в графе "Валюта" на дату заказа
      // с - Цена нетто, указанная в заказе, из которого импортирована позиция
      // то выдавать сообщение
      var drpPrice = parseFloat(getSumForJS(drp_price.value));
      var checkCourse = parseFloat(getSumForJS(course.value));
      var oprPriceNetto = parseFloat(getSumForJS(opr_price_netto.value));
      var checkCoefficient = parseFloat(getSumForJS(drpPriceCoefficient.value));
      if ( checkCourse != 0 && oprPriceNetto != 0 )
      {
        if ( drpPrice / checkCourse / oprPriceNetto > checkCoefficient )
        {
          if (!isUserAgree('<bean:message key="msg.delivery_request_produce.check_dlr_price"/>', true, 500, 180))
          {
            return;
          }
        }
      }

      submitDispatchForm("process");
    </logic:equal>
  }

  <logic:notEqual name="DeliveryRequestProduce" property="dlr_fair_trade" value="1">
    var contactId = document.getElementById("contract.con_id");
    var contactNumberWithDate = document.getElementById("contractNumberWithDate");
    var specificationId = document.getElementById("specification.spc_id");
    var specificationNumberWithDate = document.getElementById("specificationNumberWithDate");
    var specificationNumberWithDateBtn = document.getElementById("specificationNumberWithDateBtn");
    var clearSpc = false;

    onChangeContract(null);

    function onChangeContractor(arg)
    {
      contactId.value = '';
      contactNumberWithDate.value = '';
      clearSpc = true;
      onChangeContract(null);
    }

    function onChangeContract(arg)
    {
      if ( contactId.value == '' )
      {
        specificationNumberWithDate.disabled = true;
        disableImgButton(specificationNumberWithDateBtn, true);
      }
      else
      {
        specificationNumberWithDate.disabled = false;
        disableImgButton(specificationNumberWithDateBtn, false);
      }

       if ( clearSpc )
      {
        specificationId.value = '';
        specificationNumberWithDate.value = '';
      }
      clearSpc = true;
    }
  </logic:notEqual>

</script>
