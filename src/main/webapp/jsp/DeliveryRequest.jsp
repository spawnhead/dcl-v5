  <%@ taglib uri="/tags/struts-bean" prefix="bean" %>
  <%@ taglib uri="/tags/struts-html" prefix="html" %>
  <%@ taglib uri="/tags/struts-logic" prefix="logic" %>
  <%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
  <%@ taglib uri="/tags/html-grid" prefix="grid" %>

	<div id='for-insert'></div>

  <div style="display:none" id="resultMsg"></div>

  <script language="JScript" type="text/javascript">
    function submitReload()
    {
      submitDispatchForm("reload");
    }
  </script>

  <h2>
    <logic:equal name="DeliveryRequest" property="inDoc" value="true">
      <bean:message key="DeliveryRequestIn.title"/> <ctrl:help htmlName="DeliveryRequestIn"/>
    </logic:equal>
    <logic:equal name="DeliveryRequest" property="outDoc" value="true">
      <bean:message key="DeliveryRequestOut.title"/> <ctrl:help htmlName="DeliveryRequestOut"/>
    </logic:equal>
  </h2>

  <ctrl:form readonly="${DeliveryRequest.formReadOnly}">
  <ctrl:hidden property="direction"/>
  <ctrl:hidden property="dlr_id"/>
  <ctrl:hidden property="is_new_doc"/>
  <ctrl:hidden property="usr_date_create"/>
  <ctrl:hidden property="usr_date_edit"/>
  <ctrl:hidden property="usr_date_place"/>
  <ctrl:hidden property="createUser.usr_id"/>
  <ctrl:hidden property="editUser.usr_id"/>
  <ctrl:hidden property="placeUser.usr_id"/>
  <ctrl:hidden property="dlr_place_request_save"/>
  <ctrl:hidden property="dlr_executed_old"/>
  <ctrl:hidden property="dlr_ord_not_form"/>
  <table class=formBackTop align="center" width="99%">
    <tr>
      <td>
        <table cellspacing="0" width="100%" >
          <logic:notEqual name="DeliveryRequest" property="is_new_doc" value="true">
            <tr>
              <td>
                <table width="100%">
                  <tr>
                    <td width="10%"><bean:message key="DeliveryRequest.create"/></td>
                    <td><ctrl:write name="DeliveryRequest" property="usr_date_create"/> <ctrl:write name="DeliveryRequest" property="createUser.userFullName"/> </td>
                    <td width="10%"><bean:message key="DeliveryRequest.edit"/></td>
                    <td width="40%"><ctrl:write name="DeliveryRequest" property="usr_date_edit"/> <ctrl:write name="DeliveryRequest" property="editUser.userFullName"/></td>
                  </tr>
                </table>
              </td>
            </tr>
          </logic:notEqual>
          <logic:notEqual name="DeliveryRequest" property="placeUser.usr_id" value="">
            <tr>
              <td>
                <table width="100%">
                  <tr>
                    <td width="10%"><bean:message key="DeliveryRequest.place"/></td>
                    <td><ctrl:write name="DeliveryRequest" property="usr_date_place"/> <ctrl:write name="DeliveryRequest" property="placeUser.userFullName"/></td>
                  </tr>
                </table>
              </td>
            </tr>
          </logic:notEqual>
          <tr>
            <td>
              <table width="100%">
                <tr>
                  <logic:notEqual name="DeliveryRequest" property="is_new_doc" value="true">
                    <td width="10%"><bean:message key="DeliveryRequest.dlr_number"/></td>
                    <td><ctrl:text property="dlr_number" style="width:230px;" readonly="true"/></td>
                  </logic:notEqual>
                  <td width="10%"><bean:message key="DeliveryRequest.dlr_date"/></td>
                  <logic:notEqual name="DeliveryRequest" property="is_new_doc" value="true">
                    <td width="40%">
                  </logic:notEqual>
                  <logic:equal name="DeliveryRequest" property="is_new_doc" value="true">
                    <td>
                  </logic:equal>
                    <ctrl:date property="dlr_date" style="width:200px;"/>
                  </td>
                </tr>
              </table>
            </td>
          </tr>
          
          <tr>
            <td>
              <table width="100%">
                <tr>
                  <td width="10%"><bean:message key="DeliveryRequest.dlr_fair_trade"/></td>
                  <td>
                    <logic:equal name="DeliveryRequest" property="inDoc" value="true">
                      <ctrl:checkbox property="dlr_fair_trade" styleClass="checkbox" value="1" onclick="submitReloadWithClean()"/>
                    </logic:equal>
                    <logic:equal name="DeliveryRequest" property="outDoc" value="true">
                      <ctrl:checkbox property="dlr_fair_trade" styleClass="checkbox" value="1" readonly="true"/>
                    </logic:equal>
                  </td>
                </tr>
              </table>
            </td>
          </tr>

          <logic:notEqual name="DeliveryRequest" property="dlr_fair_trade" value="1">
            <tr>
              <td>
                <table width="100%">
                  <tr>
                    <td colspan="2">
                      <bean:message key="DeliveryRequest.dlr_guarantee_repair"/>&nbsp;&nbsp;
                      <ctrl:checkbox property="dlr_guarantee_repair" styleClass="checkbox" value="1" onclick="submitReload()"/>
                    </td>
                  </tr>
                </table>
              </td>
            </tr>
          </logic:notEqual>

          <logic:equal name="DeliveryRequest" property="dlr_fair_trade" value="1">
            <tr>
              <td>
                <table width="100%">
                  <tr>
                    <td width="10%">
                      <bean:message key="DeliveryRequest.dlr_wherefrom"/>
                    </td>
                    <td width="110px">
                      <ctrl:checkbox property="fakeOne" styleClass="checkbox" value="1" readonly="true"/>&nbsp;
                      <logic:equal name="DeliveryRequest" property="inDoc" value="true">
                        <bean:message key="DeliveryRequest.dlr_wherefrom1"/>
                      </logic:equal>
                      <logic:equal name="DeliveryRequest" property="outDoc" value="true">
                        <bean:message key="DeliveryRequest.dlr_wherefrom2"/>
                      </logic:equal>
                    </td>
                    <td>
                      <ctrl:text property="dlr_wherefrom" style="width:200px;"/>
                    </td>
                  </tr>
                </table>
              </td>
            </tr>

            <logic:equal name="DeliveryRequest" property="inDoc" value="true">
              <tr>
                  <td>
                    <table width="100%">
                      <tr>
                        <td colspan="2">
                          <bean:message key="DeliveryRequest.dlr_need_deliver"/>&nbsp;&nbsp;
                          <ctrl:checkbox property="dlr_need_deliver" styleClass="checkbox" value="1" onclick="submitReloadWithCleanNeedDeliver()"/>
                        </td>
                      </tr>
                    </table>
                  </td>
                </tr>
                <logic:notEqual name="DeliveryRequest" property="dlr_need_deliver" value="1">
                  <tr>
                    <td>
                      <table width="100%">
                        <tr>
                          <td colspan="2">
                            <bean:message key="DeliveryRequest.dlr_include_in_spec"/>&nbsp;&nbsp;
                            <ctrl:checkbox property="dlr_include_in_spec" styleClass="checkbox" value="1" onclick="submitReload()"/>
                          </td>
                        </tr>
                      </table>
                    </td>
                  </tr>
                </logic:notEqual>
            </logic:equal>

            <tr>
              <td>
                <bean:message key="DeliveryRequest.dlr_comment"/>
              </td>
            </tr>
            <tr>
              <td>
                <ctrl:textarea property="dlr_comment" style="width:630px;height:70px;" />
              </td>
            </tr>
          </logic:equal>

          <logic:notEqual name="DeliveryRequest" property="dlr_fair_trade" value="1">
            <tr>
              <td>
                <div class="gridBackNarrow">
                  <grid:table property="gridResult" key="number" >
                    <grid:column width="10%" align="center"><jsp:attribute name="title"><bean:message key="DeliveryRequestProduces.produce_name"/></jsp:attribute></grid:column>
                    <grid:column width="10%" align="center"><jsp:attribute name="title"><bean:message key="DeliveryRequestProduces.produce_type"/></jsp:attribute></grid:column>
                    <grid:column width="10%" align="center"><jsp:attribute name="title"><bean:message key="DeliveryRequestProduces.produce_params"/></jsp:attribute></grid:column>
                    <grid:column width="10%" align="center"><jsp:attribute name="title"><bean:message key="DeliveryRequestProduces.produce_add_params"/></jsp:attribute></grid:column>
                    <grid:column align="center"><jsp:attribute name="title"><bean:message key="DeliveryRequestProduces.ctn_number"/></jsp:attribute></grid:column>
                    <grid:column width="3%" align="center"><jsp:attribute name="title"><bean:message key="DeliveryRequestProduces.unit"/></jsp:attribute></grid:column>
                    <logic:notEqual name="DeliveryRequest" property="dlr_guarantee_repair" value="1">
                      <grid:column width="5%" align="center"><jsp:attribute name="title"><bean:message key="DeliveryRequestProduces.drp_price"/></jsp:attribute></grid:column>
                    </logic:notEqual>
                    <grid:column width="5%" align="center"><jsp:attribute name="title"><bean:message key="DeliveryRequestProduces.drp_count"/></jsp:attribute></grid:column>
                    <grid:column width="8%" align="center"><jsp:attribute name="title"><bean:message key="DeliveryRequestProduces.stuffCategory"/></jsp:attribute></grid:column>
                    <grid:column width="7%" align="center"><jsp:attribute name="title"><bean:message key="DeliveryRequestProduces.order"/></jsp:attribute></grid:column>
                    <grid:column width="13%" align="center"><jsp:attribute name="title"><bean:message key="DeliveryRequestProduces.customerContractSpec"/></jsp:attribute></grid:column>
                    <grid:column width="5%" align="center"><jsp:attribute name="title"><bean:message key="DeliveryRequestProduces.purpose"/></jsp:attribute></grid:column>
                    <logic:equal name="DeliveryRequest" property="showSpecification" value="true">
                      <grid:column width="5%" align="center"><jsp:attribute name="title"><bean:message key="DeliveryRequestProduces.specification"/></jsp:attribute></grid:column>
                    </logic:equal>
                    <grid:column title=""/>
                    <grid:column title=""/>
                    <grid:row>
                      <grid:colCustom width="10%" property="produce.name"/>
                      <grid:colCustom width="10%" property="produce.type"/>
                      <grid:colCustom width="10%" property="produce.params"/>
                      <grid:colCustom width="10%" property="produce.addParams"/>
                      <grid:colCustom property="catalogNumberForStuffCategory"/>
                      <grid:colCustom width="3%" property="unitName"/>
                      <logic:notEqual name="DeliveryRequest" property="dlr_guarantee_repair" value="1">
                        <grid:colCustom width="5%" align="right" property="drp_price_formatted"/>
                      </logic:notEqual>
                      <grid:colCustom width="5%" align="right" property="drp_count"/>
                      <grid:colCustom width="8%" property="stuffCategory.name"/>
                      <grid:colCustom width="7%" property="ord_number_date"/>
                      <grid:colCustom width="13%" property="customerContractSpec"/>
                      <grid:colCustom width="5%" property="purpose.name"/>
                      <logic:equal name="DeliveryRequest" property="showSpecification" value="true">
                        <grid:colCustom width="5%" property="specificationNumbers"/>
                      </logic:equal>
                      <grid:colEdit width="1" dispatch="editProduce" type="submit" tooltip="tooltip.DeliveryRequestProduces.edit" scriptUrl="opr_id=${record.opr_id}&dlr_fair_trade=${DeliveryRequest.dlr_fair_trade}&dlr_include_in_spec=${DeliveryRequest.dlr_include_in_spec}&dlr_guarantee_repair=${DeliveryRequest.dlr_guarantee_repair}&showMaxExtra=${DeliveryRequest.showMaxExtra}"/>
                      <grid:colDelete width="1" dispatch="deleteProduce" type="submit" tooltip="tooltip.DeliveryRequestProduces.delete" readonlyCheckerId="deleteReadonly"/>
                    </grid:row>
                  </grid:table>
                </div>
              </td>
            </tr>
          </logic:notEqual>

          <logic:equal name="DeliveryRequest" property="dlr_fair_trade" value="1">
            <tr>
              <td>
                <div class="gridBackNarrow" id="deliveryRequestGrid">
                  <grid:table property="gridResult" key="number" >

                    <logic:equal name="DeliveryRequest" property="specImportHide" value="false">
                      <th class="table-header" colspan="10"/>
                      <th class="table-header" colspan="2"><bean:message key="DeliveryRequestProduces.produce_received"/></th>
                      <th class="table-header" colspan="2"/>
                      </tr>
                      <tr>
                    </logic:equal>

                    <grid:column width="15%" align="center"><jsp:attribute name="title"><bean:message key="DeliveryRequestProduces.produce_name"/></jsp:attribute></grid:column>
                    <grid:column width="10%" align="center"><jsp:attribute name="title"><bean:message key="DeliveryRequestProduces.produce_type"/></jsp:attribute></grid:column>
                    <grid:column width="5%" align="center"><jsp:attribute name="title"><bean:message key="DeliveryRequestProduces.produce_params"/></jsp:attribute></grid:column>
                    <grid:column width="5%" align="center"><jsp:attribute name="title"><bean:message key="DeliveryRequestProduces.produce_add_params"/></jsp:attribute></grid:column>
                    <grid:column width="5%" align="center"><jsp:attribute name="title"><bean:message key="DeliveryRequestProduces.ctn_number"/></jsp:attribute></grid:column>
                    <grid:column width="3%" align="center"><jsp:attribute name="title"><bean:message key="DeliveryRequestProduces.unit"/></jsp:attribute></grid:column>
                    <logic:equal name="DeliveryRequest" property="dlr_include_in_spec" value="1">
                      <grid:column width="5%" align="center"><jsp:attribute name="title"><bean:message key="DeliveryRequestProduces.drp_price"/></jsp:attribute></grid:column>
                    </logic:equal>
                    <grid:column width="5%" align="center"><jsp:attribute name="title"><bean:message key="DeliveryRequestProduces.drp_count"/></jsp:attribute></grid:column>
                    <logic:equal name="DeliveryRequest" property="numDateOrdShowForFairTrade" value="true">
                      <grid:column width="15%" align="center"><jsp:attribute name="title"><bean:message key="DeliveryRequestProduces.ord_num_date"/></jsp:attribute></grid:column>
                    </logic:equal>
                    <logic:equal name="DeliveryRequest" property="dlr_include_in_spec" value="1">
                      <grid:column width="5%" align="center"><jsp:attribute name="title"><bean:message key="DeliveryRequestProduces.stuffCategory"/></jsp:attribute></grid:column>
                      <grid:column width="7%" align="center"><jsp:attribute name="title"><bean:message key="DeliveryRequestProduces.order"/></jsp:attribute></grid:column>
                      <grid:column width="13%" align="center"><jsp:attribute name="title"><bean:message key="DeliveryRequestProduces.customerContractSpec"/></jsp:attribute></grid:column>
                    </logic:equal>
                    <grid:column align="center"><jsp:attribute name="title"><bean:message key="DeliveryRequestProduces.drp_purpose"/></jsp:attribute></grid:column>
                    <logic:equal name="DeliveryRequest" property="specImportHide" value="false">
                      <grid:column width="5%" align="center"><jsp:attribute name="title"><bean:message key="DeliveryRequestProduces.spi_num_date"/></jsp:attribute></grid:column>
                    </logic:equal>
                    <grid:column width="5%" align="center"><jsp:attribute name="title"><bean:message key="DeliveryRequestProduces.purpose"/></jsp:attribute></grid:column>
                    <logic:equal name="DeliveryRequest" property="specImportHide" value="false">
                      <grid:column width="8%" align="center"><jsp:attribute name="title"><bean:message key="DeliveryRequestProduces.receive_manager"/></jsp:attribute></grid:column>
                      <grid:column width="10%" align="center"><jsp:attribute name="title"><bean:message key="DeliveryRequestProduces.receive_date"/></jsp:attribute></grid:column>
                    </logic:equal>
                    <logic:equal name="DeliveryRequest" property="showSpecification" value="true">
                      <grid:column width="5%" align="center"><jsp:attribute name="title"><bean:message key="DeliveryRequestProduces.specification"/></jsp:attribute></grid:column>
                    </logic:equal>
                    <grid:column title=""/>
                    <grid:column title=""/>
                    <grid:row>
                      <grid:colCustom width="15%" property="produce.name"/>
                      <grid:colCustom width="10%" property="produce.type"/>
                      <grid:colCustom width="5%" property="produce.params"/>
                      <grid:colCustom width="5%" property="produce.addParams"/>
                      <grid:colCustom width="5%" property="catalogNumberForStuffCategory"/>
                      <logic:empty name="record" property='produce.unit'>
                        <grid:colCustom width="3%" />
                      </logic:empty>
                      <logic:notEmpty name="record" property='produce.unit'>
                        <grid:colCustom width="3%" property="produce.unit.name"/>
                      </logic:notEmpty>
                      <logic:equal name="DeliveryRequest" property="dlr_include_in_spec" value="1">
                        <grid:colCustom width="5%" align="right" property="drp_price_formatted"/>
                      </logic:equal>
                      <grid:colInput width="5%" property="drp_count" align="right" textAllign="right" result="gridResult"
                                     resultProperty="drp_count" useIndexAsPK="true" onchange="changeCostOneLtlWeight"
                                     showCheckerId="show-checker" styleClassCheckerId="style-checker"/>
                      <logic:equal name="DeliveryRequest" property="numDateOrdShowForFairTrade" value="true">
                        <grid:colCustom width="15%" property="ord_number_date"/>
                      </logic:equal>
                      <logic:equal name="DeliveryRequest" property="dlr_include_in_spec" value="1">
                        <grid:colCustom width="5%" property="stuffCategory.name"/>
                        <grid:colCustom width="7%" property="ord_number_date"/>
                        <grid:colCustom width="13%" property="customerContractSpec"/>
                      </logic:equal>
                      <grid:colCustom property="drp_purpose"/>
                      <logic:equal name="DeliveryRequest" property="specImportHide" value="false">
                        <grid:colCustom width="5%" property="spi_number_date"/>
                      </logic:equal>
                      <grid:colCustom width="5%" property="purpose.name"/>
                      <logic:equal name="DeliveryRequest" property="specImportHide" value="false">
                        <grid:colServerList  property="receiveManager.userFullName" idName="receiveManager.usr_id" result="gridResult" resultProperty="receiveManager.userFullName"
                                             useIndexAsPK="true" action="/UsersListAction" selectOnly="true"
                                             readonlyCheckerId="receiveReadOnlyChecker"/>
                        <grid:colDate property="receive_date_formatted" result="gridResult" resultProperty="receive_date_formatted" useIndexAsPK="true" readonlyCheckerId="receiveReadOnlyChecker"/>
                      </logic:equal>
                      <logic:equal name="DeliveryRequest" property="showSpecification" value="true">
                        <grid:colCustom width="5%" property="specificationNumbers"/>
                      </logic:equal>
                      <grid:colEdit width="1" dispatch="editProduce" type="submit" tooltip="tooltip.DeliveryRequestProduces.edit" scriptUrl="opr_id=${record.opr_id}&dlr_fair_trade=${DeliveryRequest.dlr_fair_trade}&dlr_include_in_spec=${DeliveryRequest.dlr_include_in_spec}&dlr_need_deliver=${DeliveryRequest.dlr_need_deliver}&dlr_guarantee_repair=${DeliveryRequest.dlr_guarantee_repair}&showMaxExtra=${DeliveryRequest.showMaxExtra}"/>
                      <grid:colDelete width="1" dispatch="deleteProduce" type="submit" tooltip="tooltip.DeliveryRequestProduces.delete" readonlyCheckerId="deleteReadonly"/>
                    </grid:row>
                  </grid:table>
                </div>
              </td>
            </tr>
          </logic:equal>

          <tr>
            <td>
              <div class=gridBottom>
                <logic:equal name="DeliveryRequest" property="specImportHide" value="false">
                  <ctrl:ubutton type="submit" dispatch="selectDocsForDeliveryRequest" scriptUrl="dlr_id=${DeliveryRequest.dlr_id}&target=specification_import" styleClass="width240">
                    <bean:message key="button.importSpecificationImportFull"/>
                  </ctrl:ubutton>
                </logic:equal>
                <logic:equal name="DeliveryRequest" property="orderImportHide" value="false">
                  <ctrl:ubutton type="submit" dispatch="selectDocsForDeliveryRequest" scriptUrl="dlr_id=${DeliveryRequest.dlr_id}&target=order" styleClass="width165">
                    <bean:message key="button.importOrder"/>
                  </ctrl:ubutton>
                  <ctrl:ubutton type="submit" dispatch="selectDocsForDeliveryRequest" scriptUrl="dlr_id=${DeliveryRequest.dlr_id}&target=assemble" styleClass="width200">
                    <bean:message key="button.importAssemble"/>
                  </ctrl:ubutton>
                </logic:equal>
                <logic:equal name="DeliveryRequest" property="addHide" value="false">
                  <ctrl:ubutton type="submit" dispatch="newProduce" scriptUrl="dlr_fair_trade=${DeliveryRequest.dlr_fair_trade}&dlr_guarantee_repair=${DeliveryRequest.dlr_guarantee_repair}&showMaxExtra=${DeliveryRequest.showMaxExtra}" styleClass="width165">
                    <bean:message key="button.add"/>
                  </ctrl:ubutton>
                </logic:equal>
              </div>
            </td>
          </tr>

          <logic:equal name="DeliveryRequest" property="outDoc" value="true">
            <tr>
              <td>
                <table width="100%">
                  <tr>
                    <td colspan="2">
                      <bean:message key="DeliveryRequest.dlr_executed"/>&nbsp;
                      <ctrl:checkbox property="dlr_executed" styleClass="checkbox" value="1" readonly="${DeliveryRequest.executedReadOnly}"/>
                    </td>
                  </tr>
                </table>
              </td>
            </tr>
          </logic:equal>

          <tr>
            <td>
              <table width="100%">
                <tr>
                  <td colspan="2">
                    <bean:message key="DeliveryRequest.dlr_place_request"/>&nbsp;
                    <ctrl:checkbox property="dlr_place_request_form" styleClass="checkbox" value="1" readonly="${DeliveryRequest.placeRequestReadOnly}"/>&nbsp;&nbsp;&nbsp;&nbsp;
                    <logic:equal name="DeliveryRequest" property="showAnnul" value="true">
                      <bean:message key="DeliveryRequest.dlr_annul"/>&nbsp;
                      <ctrl:checkbox property="dlr_annul" styleClass="checkbox" value="1" readonly="${DeliveryRequest.annulReadOnly}"/>
                    </logic:equal>
                  </td>
                </tr>
              </table>
            </td>
          </tr>

          <tr>
            <td colspan="2" align="right" class=formSpace>
              &nbsp;
            </td>
          </tr>
          <tr>
            <td colspan="2" align="right">
              <logic:equal name="DeliveryRequest" property="dlr_fair_trade" value="1">
                <ctrl:ubutton type="submit" styleId="buttonPrint" dispatch="print" styleClass="width80" readonly="false" onclick="printForm()">
                  <bean:message key="button.print"/>
                </ctrl:ubutton>
                &nbsp;
                <ctrl:text property="printScale" style="width:40px;text-align:right;" readonly="false"/>
                <bean:message key="Common.percent"/>
                &nbsp;&nbsp;
              </logic:equal>
              <ctrl:ubutton type="submit" dispatch="back" styleClass="width80" readonly="false">
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
	  var form = document.forms[0];

  <logic:equal name="DeliveryRequest" property="dlr_fair_trade" value="1">
    var dlr_need_deliver = document.getElementById('dlr_need_deliver');
    var buttonPrint = document.getElementById('buttonPrint');
  </logic:equal>

    var buttonSave = document.getElementById('buttonSave');
    buttonSave.disabled = ${DeliveryRequest.saveReadOnly};

    var dlr_place_request_form = document.getElementById('dlr_place_request_form');
    var dlr_fair_trade = document.getElementById('dlr_fair_trade');
		function printForm()
    {
			enableFormCheckboxes(form);
		}

		function processClick()
    {
      doAjax({
        url:'<ctrl:rewrite action="/DeliveryRequestAction" dispatch="ajaxCheckSaveError"/>',
        synchronous:true,
        update:'resultMsg'
      });

      var resultMsg = document.getElementById('resultMsg').innerHTML;
      if ( resultMsg != '' )
      {
        alert(resultMsg);
        return;
      }

      <logic:notEqual name="DeliveryRequest" property="formReadOnly" value="true">
        doAjax({
          url:'<ctrl:rewrite action="/DeliveryRequestAction" dispatch="ajaxCheckSave"/>',
          synchronous:true,
          update:'resultMsg'
        });

        resultMsg = document.getElementById('resultMsg').innerHTML;
        if ( resultMsg != '' )
        {
          if (!isUserAgree(resultMsg, true, 500, 180))
          {
            return;
          }
        }

        if ( dlr_place_request_form.checked )
        {
          if (isUserAgree('<bean:message key="msg.delivery_request.block_from_request"/>', true, 500, 140))
          {
            submitDispatchForm("process");
          }
        }
        else
        {
          submitDispatchForm("process");
        }
      </logic:notEqual>
      <logic:equal name="DeliveryRequest" property="formReadOnly" value="true">
        submitDispatchForm("process");
      </logic:equal>
    }

    function submitReloadWithClean()
    {
      if (isUserAgree('<bean:message key="msg.delivery_request.clean_table"/>', true, 400,100))
      {
        submitDispatchForm("reloadWithClean");
      }
      else
      {
        dlr_fair_trade.checked = !dlr_fair_trade.checked;
      }
    }

    function submitReloadWithCleanNeedDeliver()
    {
      if (isUserAgree('<bean:message key="msg.delivery_request.clean_table"/>', true, 400, 100))
      {
        submitDispatchForm("reloadWithClean");
      }
      else
      {
        dlr_need_deliver.checked = !dlr_need_deliver.checked;
      }
    }

  </script>

	<logic:equal name="DeliveryRequest" property="print" value="true">
		<script language="JScript" type="text/javascript" >
			document.getElementById('for-insert').innerHTML='<iframe src="<ctrl:rewrite action="DeliveryRequestPrintAction" />" style="display:none" />';
		</script>
	</logic:equal>
