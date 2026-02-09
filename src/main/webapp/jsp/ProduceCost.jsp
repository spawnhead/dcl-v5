  <%@ taglib uri="/tags/struts-bean" prefix="bean" %>
  <%@ taglib uri="/tags/struts-html" prefix="html" %>
  <%@ taglib uri="/tags/struts-logic" prefix="logic" %>
  <%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
  <%@ taglib uri="/tags/html-grid" prefix="grid" %>

  <script language="JScript" type="text/javascript">
    function submitReloadForm()
    {
      submitDispatchForm("reload");
    }
  </script>
  <script type="text/javascript" src="includes/js_util.js"></script>
  <script type="text/javascript">
      window.onload = function () {
          document.onkeydown = null;
          var allInput = $("tableBodygridProduces").getElementsByTagName('input');
          var necessaryInputs = [];
          for (var i = 0; i < allInput.length; i++) {
              var input = allInput[i];
              if ((input.getAttribute("name").indexOf("lpc_1c_number") > -1)
                      || (input.getAttribute("name").indexOf("lpc_cost_one_ltl_formatted") > -1)
                      || (input.getAttribute("name").indexOf("lpc_weight_formatted") > -1)
                      || (input.getAttribute("name").indexOf("lpc_cost_one_by_formatted") > -1)
                      || (input.getAttribute("name").indexOf("lpc_price_list_by_formatted") > -1)) {
                  necessaryInputs.add(input);
              }
          }
          for (var i = 0; i < necessaryInputs.length; i++) {
              var input = necessaryInputs[i];
              moveFocusToNextTableRow(input);
              input.onfocus = function () {
                  if (parseFloat(this.value.replace(",", ".")) == 0) this.value = "";
              }
              input.onblur = function () {
                  if (this.value.blank()) this.value = this.defaultValue;
              }
          }
      }
  </script>

  <ctrl:form readonly="${ProduceCost.formReadOnly}">
  <ctrl:hidden property="prc_id"/>
  <ctrl:hidden property="prc_block"/>
  <ctrl:hidden property="is_new_doc"/>
  <ctrl:hidden property="usr_date_create"/>
  <ctrl:hidden property="usr_date_edit"/>
  <ctrl:hidden property="createUser.usr_id"/>
  <ctrl:hidden property="editUser.usr_id"/>
  <ctrl:hidden property="needRecalc"/>
  <table class=formBackTop align="center" width="99%">
    <tr>
      <td>
        <table cellspacing="0" width="100%" >
          <logic:notEqual name="ProduceCost" property="is_new_doc" value="true">
            <tr>
              <td>
                <table width="100%">
                  <tr>
                    <td width="10%"><bean:message key="ProduceCost.create"/></td>
                    <td width="40%"><ctrl:write name="ProduceCost" property="usr_date_create"/> <ctrl:write name="ProduceCost" property="createUser.userFullName"/> </td>
                    <td align="right"><bean:message key="ProduceCost.edit"/></td>
                    <td width="33%">&nbsp;&nbsp;&nbsp;&nbsp;<ctrl:write name="ProduceCost" property="usr_date_edit"/> <ctrl:write name="ProduceCost" property="editUser.userFullName"/></td>
                  </tr>
                </table>
              </td>
            </tr>
          </logic:notEqual>
          <tr>
            <td>
              <table width="100%">
                <tr>
                  <td width="10%"><bean:message key="ProduceCost.prc_number"/></td>
                  <td><ctrl:text property="prc_number" style="width:230px;"/></td>
                </tr>
              </table>
            </td>
          </tr>
          <tr>
            <td>
              <table width="100%">
                <tr>
                  <td width="10%"><bean:message key="ProduceCost.prc_date"/></td>
                  <td><ctrl:date property="prc_date" style="width:230px;" afterSelect='onDateChange' onchange="onDateChange()"/></td>
                </tr>
              </table>
            </td>
          </tr>
          <tr>
            <td>
              <table width="100%">
                <tr>
                  <td width="10%"><bean:message key="ProduceCost.prc_route"/></td>
                  <td width="40%"><ctrl:serverList property="route.name" idName="route.id" action="/RoutesListAction" selectOnly="true" style="width:230px;"/></td>
                  <td align="right"><bean:message key="ProduceCost.prc_course_ltl_eur"/></td>
                  <td width="33%">&nbsp;&nbsp;&nbsp;&nbsp;<ctrl:text property="prc_course_ltl_eur_formatted" style="width:70px;text-align:right;" onchange="submitReloadForm();"/></td>
                </tr>
              </table>
            </td>
          </tr>

          <tr>
            <td>
              <div class="gridBackNarrow">
                <grid:table property="gridProduces" key="number" >

                  <th class="table-header" colspan="8"><bean:message key="ProduceCostProduces.produce"/></th>
                  <th class="table-header" colspan="7"><bean:message key="ProduceCostProduces.cost"/></th>
                  <th class="table-header" rowspan="2"><bean:message key="ProduceCostProduces.lpc_cost_one_by"/></th>
                  <th class="table-header" rowspan="2"><bean:message key="ProduceCostProduces.lpc_price_list_by"/></th>
                  <th class="table-header" colspan="2"></th>

                  </tr>
                  <tr>

                  <grid:column width="5%" align="center"><jsp:attribute name="title"><bean:message key="ProduceCostProduces.lpc_1c_number"/></jsp:attribute></grid:column>
                  <grid:column align="center"><jsp:attribute name="title"><bean:message key="ProduceCostProduces.produce_name"/></jsp:attribute></grid:column>
                  <grid:column width="5%" align="center"><jsp:attribute name="title"><bean:message key="ProduceCostProduces.lpc_stuff_category"/></jsp:attribute></grid:column>
                  <grid:column width="5%" align="center"><jsp:attribute name="title"><bean:message key="ProduceCostProduces.ctn_number"/></jsp:attribute></grid:column>
                  <grid:column verticalOrientation="true" width="3%" align="center"><jsp:attribute name="title"><bean:message key="ProduceCostProduces.lpc_manager"/></jsp:attribute></grid:column>
                  <grid:column width="5%" align="center"><jsp:attribute name="title"><bean:message key="ProduceCostProduces.lpc_department"/></jsp:attribute></grid:column>
                  <grid:column verticalOrientation="true" width="3%" align="center"><jsp:attribute name="title"><bean:message key="ProduceCostProduces.unit"/></jsp:attribute></grid:column>
                  <grid:column verticalOrientation="true" width="5%" align="center"><jsp:attribute name="title"><bean:message key="ProduceCostProduces.lpc_count"/></jsp:attribute></grid:column>
                  <grid:column verticalOrientation="true" width="5%" align="center"><jsp:attribute name="title"><bean:message key="ProduceCostProduces.lpc_cost_one_ltl"/></jsp:attribute></grid:column>
                  <grid:column verticalOrientation="true" width="2%" align="center"><jsp:attribute name="title"><bean:message key="ProduceCostProduces.lpc_cost_one"/></jsp:attribute></grid:column>
                  <grid:column width="5%" align="center"><jsp:attribute name="title"><bean:message key="ProduceCostProduces.lpc_weight"/></jsp:attribute></grid:column>
                  <grid:column verticalOrientation="true" width="5%" align="center"><jsp:attribute name="title"><bean:message key="ProduceCostProduces.lpc_sum_transport"/></jsp:attribute></grid:column>
                  <grid:column verticalOrientation="true" width="5%" align="center"><jsp:attribute name="title"><bean:message key="ProduceCostProduces.lpc_percent"/></jsp:attribute></grid:column>
                  <grid:column verticalOrientation="true" width="5%" align="center"><jsp:attribute name="title"><bean:message key="ProduceCostProduces.lpc_summ"/></jsp:attribute></grid:column>
                  <grid:column verticalOrientation="true" width="5%" align="center"><jsp:attribute name="title"><bean:message key="ProduceCostProduces.lpc_custom"/></jsp:attribute></grid:column>
                  <grid:column title=""/>
                  <grid:column title=""/>
                  <grid:row>
                    <grid:colInput width="5%" property="lpc_1c_number" result="gridProduces" textAllign="right" resultProperty="lpc_1c_number"
                                   useIndexAsPK="true" showCheckerId="show-checker" styleClassCheckerId="style-checker"
                                   onfocus="setRowSelectedStile" onblur="setRowUnselectedStile"/>
                    <grid:colCustom property="produce_name" styleClassCheckerId="style-checker"/>
                    <grid:colCustom width="5%" property="stuffCategory.name" showCheckerId="show-checker" styleClassCheckerId="style-checker"/>
                    <grid:colCustom width="5%" property="catalogNumberForStuffCategory" styleClassCheckerId="style-checker"/>
                    <grid:colCustom width="3%" property="manager.usr_code" showCheckerId="show-checker" styleClassCheckerId="style-checker"/>
                    <grid:colCustom width="5%" property="department.name" showCheckerId="show-checker" styleClassCheckerId="style-checker"/>
                    <logic:empty name="record" property='produce.unit'>
                      <grid:colCustom width="3%" styleClassCheckerId="style-checker"/>
                    </logic:empty>
                    <logic:notEmpty name="record" property='produce.unit'>
                      <grid:colCustom width="3%" property="produce.unit.name" styleClassCheckerId="style-checker"/>
                    </logic:notEmpty>
                    <grid:colCustom width="5%" property="lpc_count_formatted" align="right" showCheckerId="show-checker" styleClassCheckerId="style-checker"/>
                    <grid:colInput width="5%" property="lpc_cost_one_ltl_formatted" align="right" textAllign="right" result="gridProduces"
                                   resultProperty="lpc_cost_one_ltl_formatted" useIndexAsPK="true" onchange="changeCostOneLtlWeight"
                                   showCheckerId="show-checker" styleClassCheckerId="style-checker"
                                   onfocus="setRowSelectedStile" onblur="setRowUnselectedStile"/>
                    <grid:colCustom width="2%" property="lpc_cost_one_formatted" align="right" showCheckerId="show-checker" styleClassCheckerId="style-checker"/>
                    <logic:equal value="false" name="lastRecord">
                      <grid:colInput width="5%" property="lpc_weight_formatted" align="right" textAllign="right" result="gridProduces"
                                     resultProperty="lpc_weight_formatted" useIndexAsPK="true" onchange="changeCostOneLtlWeight"
                                     styleClassCheckerId="style-checker" showCheckerId="show-checker"
                                     onfocus="setRowSelectedStile" onblur="setRowUnselectedStile"/>
                    </logic:equal>
                    <logic:equal value="true" name="lastRecord">
                      <grid:colCustom width="5%" property="lpc_weight_formatted" align="right"  styleClassCheckerId="style-checker" />
                    </logic:equal>
                    <grid:colCustom width="5%" property="lpc_sum_transport_formatted" align="right" styleClassCheckerId="style-checker"/>
                    <grid:colCustom width="5%" property="lpc_percent_formatted" align="right" showCheckerId="show-checker" styleClassCheckerId="style-checker"/>
                    <grid:colCustom width="5%" property="lpc_summ_formatted" align="right" styleClassCheckerId="style-checker"/>
                    <grid:colCustom width="5%" property="lpc_custom_formatted" align="right" styleClassCheckerId="style-checker"/>
                    <grid:colInput width="5%" property="lpc_cost_one_by_formatted" align="right" textAllign="right"
                                   result="gridProduces" resultProperty="lpc_cost_one_by_formatted" useIndexAsPK="true"
                                   showCheckerId="show-checker" styleClassCheckerId="style-checker"
                                   onfocus="setRowSelectedStile" onblur="setRowUnselectedStile"/>
                    <grid:colInput width="5%" property="lpc_price_list_by_formatted" align="right" textAllign="right"
                                   result="gridProduces" resultProperty="lpc_price_list_by_formatted" useIndexAsPK="true"
                                   showCheckerId="show-checker" styleClassCheckerId="style-checker"
                                   onfocus="setRowSelectedStile" onblur="setRowUnselectedStile"/>
                    <grid:colEdit width="1" dispatch="editProduce" type="submit" scriptUrl="prc_date=$(prc_date)" showCheckerId="show-checker"
                                  tooltip="tooltip.ProduceCostProduces.edit" styleClassCheckerId="style-checker"/>
                    <grid:colDelete width="1" dispatch="deleteProduce" type="submit" showCheckerId="show-checker"
                                    tooltip="tooltip.ProduceCostProduces.delete" readonlyCheckerId="deleteReadonly" styleClassCheckerId="style-checker"/>
                  </grid:row>
                </grid:table>
              </div>
            </td>
          </tr>

          <tr>
            <td>
              <span style="color:#008000"><bean:message key="ProduceCost.percent_legend"/></span>
            </td>
          </tr>
          <tr>
            <td>
              <div class=gridBottom>
                <ctrl:ubutton styleId="buttonRecalc" type="submit" dispatch="reload" styleClass="width165">
                  <bean:message key="button.recalc"/>
                </ctrl:ubutton>
              </div>
            </td>
          </tr>
          <tr>
            <td>
              <div class=gridBottom>
                <ctrl:ubutton styleId="buttonImportSpc" type="submit" dispatch="selectFromDocs" scriptUrl="prc_id=${ProduceCost.prc_id}&target=specification_import" styleClass="width165">
                  <bean:message key="button.importSpecificationImport"/>
                </ctrl:ubutton>
                <ctrl:ubutton styleId="buttonImportDlr" type="submit" dispatch="selectFromDocs" scriptUrl="prc_id=${ProduceCost.prc_id}&target=delivery_request" styleClass="width260">
                  <bean:message key="button.importDeliveryRequestFairTrade"/>
                </ctrl:ubutton>
                <ctrl:ubutton styleId="buttonImportOrd" type="submit" dispatch="selectFromDocs" scriptUrl="prc_id=${ProduceCost.prc_id}&target=order" styleClass="width200">
                  <bean:message key="button.importOrder"/>
                </ctrl:ubutton>
                <ctrl:ubutton styleId="buttonImportAsm" type="submit" dispatch="selectFromDocs" scriptUrl="prc_id=${ProduceCost.prc_id}&target=assemble" styleClass="width200">
                  <bean:message key="button.importAssemble"/>
                </ctrl:ubutton>
                <ctrl:ubutton type="submit" dispatch="importExcel" scriptUrl="prc_id=${ProduceCost.prc_id}" styleClass="width165">
                  <bean:message key="button.importExcel"/>
                </ctrl:ubutton>
                <ctrl:ubutton styleId="buttonEnter" type="submit" dispatch="newProduce" scriptUrl="prc_id=${ProduceCost.prc_id}" styleClass="width165">
                  <bean:message key="button.add"/>
                </ctrl:ubutton>
              </div>
            </td>
          </tr>

          <tr>
            <td>
              <table width="100%">
                <tr>
                  <td width="15%"><bean:message key="ProduceCost.prc_sum_transport"/></td>
                  <td>
                    <ctrl:text property="prc_sum_transport_formatted" style="width:70px;text-align:right;"
                               onchange="submitReloadForm();"/>
                    &nbsp;&nbsp;&nbsp;&nbsp;<bean:message key="ProduceCost.prc_weight"/>
                    &nbsp;<ctrl:text property="prc_weight_formatted" style="width:70px;text-align:right;"/>
                  </td>
                </tr>
              </table>
            </td>
          </tr>

          <tr>
            <td>
              <table width="100%">
                <tr>
                  <td width="15%"><bean:message key="ProduceCost.prc_custom"/></td>
                </tr>
              </table>
            </td>
          </tr>

          <tr>
            <td>
              <table>
                <tr>
                  <td>
                    <div class="gridBackNarrow" >
                      <grid:table property="gridCustom" key="lpc_percent" >
                        <grid:column align="center"><jsp:attribute name="title"><bean:message key="ProduceCostCustoms.lpc_percent"/></jsp:attribute></grid:column>
                        <grid:column width="30%" align="center"><jsp:attribute name="title"><bean:message key="ProduceCostCustoms.lpc_summ"/></jsp:attribute></grid:column>
                        <grid:column width="30%" align="center"><jsp:attribute name="title"><bean:message key="ProduceCostCustoms.lpc_summ_allocation"/></jsp:attribute></grid:column>
                        <grid:column title=""/>
                        <grid:row>
                          <grid:colCustom align="right" property="lpc_percent_formatted"/>
                          <grid:colCustom align="right" width="30%" property="lpc_summ_formatted"/>
                          <grid:colCustom align="right" width="30%" property="lpc_summ_allocation_formatted"/>
                          <grid:colEdit width="1" dispatch="editCustom" type="submit" showCheckerId="show-checker" tooltip="tooltip.ProduceCostCustoms.edit"/>
                        </grid:row>
                      </grid:table>
                    </div>
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
              <ctrl:ubutton type="submit" dispatch="back" styleClass="width80" readonly="false">
                <bean:message key="button.cancel"/>
              </ctrl:ubutton>
              <ctrl:ubutton type="submit" dispatch="process" styleClass="width80" readonly="${ProduceCost.formReadOnly}">
                <bean:message key="button.save"/>
              </ctrl:ubutton>
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
  </ctrl:form>

  <script language="JScript" type="text/javascript">
    var prc_date = document.getElementById('prc_date');
    var buttonImportSpc = document.getElementById('buttonImportSpc');
    var buttonImportDlr = document.getElementById('buttonImportDlr');
    var buttonImportOrd = document.getElementById('buttonImportOrd');
    var buttonImportAsm = document.getElementById('buttonImportAsm');
    var buttonEnter = document.getElementById('buttonEnter');

    var needRecalc = document.getElementById('needRecalc');
    var buttonRecalc = document.getElementById('buttonRecalc');
    buttonRecalc.disabled = true;
    if (needRecalc.value == '1')
    {
      buttonRecalc.disabled = false;
    }
    else
    {
      needRecalc.value = '';
    }

<logic:notEqual name="ProduceCost" property="formReadOnly" value="true">
    onDateChange();
</logic:notEqual>

    function changeCostOneLtlWeight()
    {
      if ( buttonRecalc.disabled )
      {
        buttonRecalc.disabled = false;
        needRecalc.value = '1';
      }
    }

    function setRowSelectedStile(obj, id)
    {
      var firstCol = document.getElementById('gridProduces.row(' + id + ').lpc_1c_number');
      firstCol.parentElement.parentElement.style.background = '#dddddd';
    }

    function setRowUnselectedStile(obj, id)
    {
      var firstCol = document.getElementById('gridProduces.row(' + id + ').lpc_1c_number');
      firstCol.parentElement.parentElement.style.background = '#eeeeee';
    }

    function onDateChange()
    {
      if ( prc_date.value == '' )
      {
        buttonImportSpc.disabled = true;
        buttonImportDlr.disabled = true;
        buttonImportOrd.disabled = true;
        buttonImportAsm.disabled = true;
        buttonEnter.disabled = true;
      }
      else
      {
        buttonImportSpc.disabled = false;
        buttonImportDlr.disabled = false;
        buttonImportOrd.disabled = false;
        buttonImportAsm.disabled = false;
        buttonEnter.disabled = false;
      }
    }
  </script>