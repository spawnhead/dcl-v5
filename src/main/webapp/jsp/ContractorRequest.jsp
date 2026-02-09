  <%@ taglib uri="/tags/struts-bean" prefix="bean" %>
  <%@ taglib uri="/tags/struts-html" prefix="html" %>
  <%@ taglib uri="/tags/struts-logic" prefix="logic" %>
  <%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
  <%@ taglib uri="/tags/html-grid" prefix="grid" %>

  <div id='for-insert'></div>

  <ctrl:askUser name="deleteAttachAsk" key="msg.contractor_request.delete_attach" showOkCancel="false" height="120"/>

  <script language="JScript" type="text/javascript">
    function setDisabledElementById(id)
    {
      var isDeliveryCheck = document.getElementById('contractorRequestPrints['+ id +'].crp_deliver_checkbox');

      if ( isDeliveryCheck )
      {
        var isDelivery = isDeliveryCheck.checked;
        var crp_reclamation_date = document.getElementById('contractorRequestPrints['+ id +'].crp_reclamation_date_formatted');
        var crp_reclamation_dateBtn = document.getElementById('contractorRequestPrints['+ id +'].crp_reclamation_date_formattedBtn');
        var crp_lintera_request_date = document.getElementById('contractorRequestPrints['+ id +'].crp_lintera_request_date_formatted');
        var crp_lintera_request_dateBtn = document.getElementById('contractorRequestPrints['+ id +'].crp_lintera_request_date_formattedBtn');
        var crp_no_defect_act_checkbox = document.getElementById('contractorRequestPrints['+ id +'].crp_no_defect_act_checkbox');
        var crp_no_reclamation_act_checkbox = document.getElementById('contractorRequestPrints['+ id +'].crp_no_reclamation_act_checkbox');
        var crp_lintera_agreement_date = document.getElementById('contractorRequestPrints['+ id +'].crp_lintera_agreement_date_formatted');
        var crp_lintera_agreement_dateBtn = document.getElementById('contractorRequestPrints['+ id +'].crp_lintera_agreement_date_formattedBtn');

        if ( crp_reclamation_date ) //если есть дата - то дизэйблим. Если нет - это Сервис.
        {
          crp_reclamation_date.disabled = isDelivery;
          disableImgButton(crp_reclamation_dateBtn, isDelivery);
          crp_lintera_request_date.disabled = isDelivery;
          disableImgButton(crp_lintera_request_dateBtn, isDelivery);
          crp_lintera_agreement_date.disabled = isDelivery;
          disableImgButton(crp_lintera_agreement_dateBtn, isDelivery);
        }
        if ( crp_no_defect_act_checkbox )
        {
          crp_no_defect_act_checkbox.disabled = isDelivery;
        }
        if ( crp_no_reclamation_act_checkbox )
        {
          crp_no_reclamation_act_checkbox.disabled = isDelivery;
        }

        <logic:equal name="ContractorRequest" property="showForAdmin" value="false">
          if ( isDelivery )
          {
            isDeliveryCheck.disabled = ${ContractorRequest.deliverReadOnly};
          }
          else
          {
            isDeliveryCheck.disabled = false;
          }
          <logic:equal name="ContractorRequest" property="showForManager" value="true">
            isDeliveryCheck.disabled = true;
          </logic:equal>
        </logic:equal>
        <logic:equal name="ContractorRequest" property="showForAdmin" value="true">
          isDeliveryCheck.disabled = false;
        </logic:equal>
      }
    }

    function setDelivery()
    {
      var i = 0;
      for ( ; i < 100; i++ )
      {
        setDisabledElementById(i);
      }
    }

    function changeDeliver(obj, id)
    {
      <logic:equal name="ContractorRequest" property="showForAdmin" value="false">
        var isDeliveryCheck = document.getElementById('contractorRequestPrints['+ id +'].crp_deliver_checkbox');
        if ( isDeliveryCheck.checked )
        {
          if (!isUserAgree('<bean:message key="msg.contractor_request.will_block"/>', false, 400, 100))
          {
            isDeliveryCheck.checked = !isDeliveryCheck.checked;
            return;
          }
        }
      </logic:equal>

      changeAssociatedHidden(obj);
      setDisabledElementById(id);
    }
  </script>

  <ctrl:form readonly="${ContractorRequest.formReadOnly}">
  <ctrl:hidden property="crq_id"/>
  <ctrl:hidden property="is_new_doc"/>
  <ctrl:hidden property="usr_date_create"/>
  <ctrl:hidden property="usr_date_edit"/>
  <ctrl:hidden property="createUser.usr_id"/>
  <ctrl:hidden property="editUser.usr_id"/>
  <ctrl:hidden property="lps_id"/>

  <ctrl:hidden property="requestTypeIdCheck"/>

  <logic:equal name="ContractorRequest" property="showServiceOrGuaranty" value="true">
    <ctrl:hidden property="crq_reclamation_date"/>
    <ctrl:hidden property="crq_lintera_request_date"/>
    <ctrl:hidden property="crq_defect_act"/>
    <ctrl:hidden property="crq_reclamation_act"/>
    <ctrl:hidden property="crq_lintera_agreement_date"/>
    <ctrl:hidden property="visitId"/>
  </logic:equal>

  <logic:equal name="ContractorRequest" property="showContractEquipment" value="true">
    <ctrl:hidden property="crq_equipment"/>
    <ctrl:hidden property="ctn_number"/>
    <ctrl:hidden property="stf_name"/>
    <ctrl:hidden property="lps_serial_num"/>
    <ctrl:hidden property="lps_year_out"/>
    <ctrl:hidden property="lps_enter_in_use_date"/>
    <ctrl:hidden property="mad_complexity"/>
  </logic:equal>
  <logic:equal name="ContractorRequest" property="showServiceOrGuaranty" value="false">
    <ctrl:hidden property="crq_no_contract"/>
  </logic:equal>
  <logic:equal name="ContractorRequest" property="showServiceOrGuaranty" value="true">
    <ctrl:hidden property="shp_date"/>
    <ctrl:hidden property="con_number"/>
    <ctrl:hidden property="con_date"/>
    <ctrl:hidden property="spc_number"/>
    <ctrl:hidden property="spc_date"/>
    <ctrl:hidden property="con_seller_id"/>
    <ctrl:hidden property="con_seller"/>
    <ctrl:hidden property="crqSellerAgreementDateDialog"/>
    <ctrl:hidden property="needDetailReturn"/>
  </logic:equal>
  <ctrl:hidden property="produce.id"/>
  <table class=formBackTop align="center" width="99%">
    <tr>
      <td >
        <table cellspacing="0" width="100%" >
          <logic:notEqual name="ContractorRequest" property="is_new_doc" value="true">
            <tr>
              <td>
                <table width="100%">
                  <tr>
                    <td width="200px"><bean:message key="ContractorRequest.create"/></td>
                    <td width="415px"><ctrl:write name="ContractorRequest" property="usr_date_create"/> <ctrl:write name="ContractorRequest" property="createUser.userFullName"/> </td>
                    <td width="10%"><bean:message key="ContractorRequest.edit"/></td>
                    <td><ctrl:write name="ContractorRequest" property="usr_date_edit"/> <ctrl:write name="ContractorRequest" property="editUser.userFullName"/></td>
                  </tr>
                </table>
              </td>
            </tr>

            <tr>
              <td>
                <table width="100%" border="0">
                  <tr>
                    <td width="200px"><bean:message key="ContractorRequest.crq_number"/></td>
                    <td width="415px"><ctrl:text property="crq_number" style="width:230px;" readonly="true"/></td>
                    <td width="10%"><bean:message key="ContractorRequest.crq_ticket_number"/></td>
                    <td><ctrl:text property="crq_ticket_number" style="width:230px;"/></td>
                  </tr>
                </table>
              </td>
            </tr>
          </logic:notEqual>

          <logic:equal name="ContractorRequest" property="is_new_doc" value="true">
            <tr>
              <td>
                <table width="100%" border="0">
                  <tr>
                    <td width="200px"><bean:message key="ContractorRequest.crq_ticket_number"/></td>
                    <td><ctrl:text property="crq_ticket_number" style="width:230px;"/></td>
                  </tr>
                </table>
              </td>
            </tr>
          </logic:equal>


          <tr>
            <td>
              <table width="100%" border="0">
                <tr valign="top">
                  <td width="620px">
                    <table width="100%" border="0" cellpadding="0" cellspacing="0">
                      <tr>
                        <td>
                          <table width="100%" border="0" cellpadding="0" cellspacing="0">
                            <tr>
                              <td>
                                <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                  <tr>
                                    <td width="200px"><bean:message key="ContractorRequest.crq_receive_date"/></td>
                                    <td>
                                      <table border="0">
                                        <tr>
                                          <td>
                                            <ctrl:date property="crq_receive_date" style="width:230px;"/>
                                          </td>
                                        </tr>
                                      </table>
                                    </td>
                                  </tr>
                                </table>
                              </td>
                            </tr>

                            <tr>
                              <td>
                                <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                  <tr>
                                    <td width="200px"><bean:message key="ContractorRequest.contractor"/></td>
                                    <td>
                                      <table border="0">
                                        <tr>
                                          <td>
                                            <ctrl:serverList property="contractor.name" idName="contractor.id"
                                                             action="/ContractorsListAction" filter="filter"
                                                             style="width:230px;" callback="onContractorSelect"/>
                                          </td>
                                          <td>&nbsp;</td>
                                          <td>
                                            <ctrl:ubutton type="submit" dispatch="newContractor" styleClass="width120">
                                              <bean:message key="button.addNew"/>
                                            </ctrl:ubutton>
                                          </td>
                                        </tr>
                                      </table>
                                    </td>
                                  </tr>
                                </table>
                              </td>
                            </tr>

                            <tr>
                              <td>
                                <table width="100%" border="0" cellSpacing="0" cellPadding="0">
                                  <tr>
                                    <td width="200px"><bean:message key="ContractorRequest.contactPerson"/></td>
                                    <td>
                                      <table border="0">
                                        <tr>
                                          <td>
                                            <ctrl:serverList property="contactPerson.cps_name" idName="contactPerson.cps_id"
                                                             action="/ContactPersonsListAction"
                                                             scriptUrl="ctr_id=$(contractor.id)"
                                                             selectOnly="true" style="width:230px;"/>
                                          </td>
                                          <td>&nbsp;</td>
                                          <td>
                                            <ctrl:ubutton type="submit" dispatch="newContactPerson" styleId="addNewPerson" styleClass="width120">
                                              <bean:message key="button.addNew"/>
                                            </ctrl:ubutton>
                                          </td>
                                        </tr>
                                      </table>
                                    </td>
                                  </tr>
                                </table>
                              </td>
                            </tr>
                          </table>
                        </td>
                      </tr>
                    </table>
                  </td>
                  <td>
                    <table width="100%" border="0" cellpadding="0" cellspacing="0">
                      <tr><td><bean:message key="ContractorRequest.crq_comment"/></td></tr>
                      <tr><td><ctrl:textarea property="crq_comment" style="width:365px;height:70px;" showHelp="false" readonly="false"/></td></tr>
                    </table>
                  </td>
                </tr>
              </table>
            </td>
          </tr>

          <tr>
            <td>
              <table width="100%" border="0">
                <tr>
                  <td width="200px"><bean:message key="ContractorRequest.requestType"/></td>
                  <td width="1px">
                    <ctrl:serverList property="requestType.name" idName="requestType.id"
                                     action="/ContractorRequestTypeListAction" callback="onRequestTypeChange"
                                     selectOnly="true" style="width:230px;"/>
                  </td>
                  <logic:equal name="ContractorRequest" property="showService" value="false">
                    <td>&nbsp;</td>
                  </logic:equal>
                  <logic:equal name="ContractorRequest" property="showService" value="true">
                    <td width="132px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<bean:message key="ContractorRequest.contractForWork"/></td>
                    <td>
                      <ctrl:serverList property="contractForWorkNumberWithDate" idName="contractForWork.con_id"
                                     action="/ContractsDepFromContractorListAction" scriptUrl="ctr_id=$(contractor.id)&conFinalDateAfter=$(crq_receive_date)&allCon=1"
                                     style="width:230px;" filter="filter"/>
                    </td>
                  </logic:equal>
                </tr>
              </table>
            </td>
          </tr>

          <tr>
            <td>
              <table width="100%">
                <tr>
                  <td width="200px"><bean:message key="ContractorRequest.contract"/></td>
                  <td width="1px">
                    <ctrl:serverList property="contractNumberWithDate" idName="contract.con_id"
                                     action="/ContractsDepFromContractorListAction" scriptUrl="ctr_id=$(contractor.id)&allCon=1"
                                     style="width:230px;" callback="onContractSelect" filter="filter" readonly="${ContractorRequest.contractReadOnly}"/>
                  </td>
                  <logic:equal name="ContractorRequest" property="showServiceOrGuaranty" value="false">
                    <td>&nbsp;</td>
                  </logic:equal>
                  <logic:equal name="ContractorRequest" property="showServiceOrGuaranty" value="true">
                    <td width="210px" id="tdShowSeller">
                      <div id="divShowSeller">
                      </div>
                    </td>
                    <td width="35px">
                      <ctrl:checkbox property="crq_no_contract" styleClass="checkbox" value="1" onclick="submitReloadNoContractForm();"/>&nbsp;
                    </td>
                    <td>
                      <bean:message key="ContractorRequest.crq_no_contract"/>
                    </td>
                  </logic:equal>
                </tr>
              </table>
            </td>
          </tr>

          <logic:equal name="ContractorRequest" property="showContractEquipment" value="true">
            <tr>
              <td>
                <table width="100%">
                  <tr>
                    <td width="200px"><bean:message key="ContractorRequest.equipment"/></td>
                    <td>
                      <ctrl:serverList property="equipment.fullList" idName="equipment.lps_id"
                                       action="/EquipmentListAction" scriptUrl="con_id=$(contract.con_id)&requestTypeId=$(requestType.id)"
                                       style="width:600px;" callback="onEquipmentSelect" filter="filter"/>
                    </td>
                  </tr>
                </table>
              </td>
            </tr>
            <logic:equal name="ContractorRequest" property="showPNP" value="true">
              <tr>
                <td>
                  <div class="gridBack">
                    <table width='100%' cellSpacing=1 cellPadding=2 border=0 class="grid">
                      <tbody>
                        <tr>
                          <td class="table-header" align="center" width="50%">
                            <bean:message key="ContractorRequest.crq_equipment"/>
                          </td>
                          <td class="table-header" align="center" width="15%">
                            <bean:message key="ContractorRequest.ctn_number"/>
                          </td>
                          <td class="table-header" align="center" width="15%">
                            <bean:message key="ContractorRequest.stf_name"/>
                          </td>
                          <td class="table-header" align="center" width="10%">
                            <bean:message key="ContractorRequest.lps_serial_num"/>
                          </td>
                          <td class="table-header" align="center" width="10%">
                            <bean:message key="ContractorRequest.lps_year_out"/>
                          </td>
                        </tr>
                        <tr>
                          <td class=txt bgcolor=#eeeeee width="50%">
                            <span id='crq_equipmentSpan'><ctrl:write name="ContractorRequest" property="crq_equipment"/></span>
                          </td>
                          <td class=txt bgcolor=#eeeeee width="15%">
                            <span id='ctn_numberSpan'><ctrl:write name="ContractorRequest" property="ctn_number"/></span>
                          </td>
                          <td class=txt bgcolor=#eeeeee width="15%">
                            <span id='stf_nameSpan'><ctrl:write name="ContractorRequest" property="stf_name"/></span>
                          </td>
                          <td class=txt bgcolor=#eeeeee width="10%">
                            <span id='lps_serial_numSpan'><ctrl:write name="ContractorRequest" property="lps_serial_num"/></span>
                          </td>
                          <td class=txt bgcolor=#eeeeee width="10%">
                            <span id='lps_year_outSpan'><ctrl:write name="ContractorRequest" property="lps_year_out"/></span>
                          </td>
                        </tr>
                      </tbody>
                    </table>
                  </div>
                </td>
              </tr>
            </logic:equal>
            <logic:equal name="ContractorRequest" property="showServiceOrGuaranty" value="true">
              <tr>
                <td>
                  <div class="gridBack">
                    <table width='100%' cellSpacing=1 cellPadding=2 border=0 class="grid">
                      <tbody>
                        <tr>
                          <td class="table-header" align="center" width="40%">
                            <bean:message key="ContractorRequest.crq_equipment"/>
                          </td>
                          <td class="table-header" align="center" width="10%">
                            <bean:message key="ContractorRequest.ctn_number"/>
                          </td>
                          <td class="table-header" align="center" width="10%">
                            <bean:message key="ContractorRequest.stf_name"/>
                          </td>
                          <td class="table-header" align="center" width="10%">
                            <bean:message key="ContractorRequest.lps_serial_num"/>
                          </td>
                          <td class="table-header" align="center" width="10%">
                            <bean:message key="ContractorRequest.lps_year_out"/>
                          </td>
                          <td class="table-header" align="center" width="10%">
                            <bean:message key="ContractorRequest.lps_enter_in_use_date"/>
                          </td>
                          <td class="table-header" align="center" width="10%">
                            <bean:message key="ContractorRequest.mad_complexity"/>
                          </td>
                        </tr>
                        <tr>
                          <td class=txt bgcolor=#eeeeee width="40%">
                            <span id='crq_equipmentSpan'><ctrl:write name="ContractorRequest" property="crq_equipment"/></span>
                          </td>
                          <td class=txt bgcolor=#eeeeee width="10%">
                            <span id='ctn_numberSpan'><ctrl:write name="ContractorRequest" property="ctn_number"/></span>
                          </td>
                          <td class=txt bgcolor=#eeeeee width="10%">
                            <span id='stf_nameSpan'><ctrl:write name="ContractorRequest" property="stf_name"/></span>
                          </td>
                          <td class=txt bgcolor=#eeeeee width="10%">
                            <span id='lps_serial_numSpan'><ctrl:write name="ContractorRequest" property="lps_serial_num"/></span>
                          </td>
                          <td class=txt bgcolor=#eeeeee width="10%">
                            <span id='lps_year_outSpan'><ctrl:write name="ContractorRequest" property="lps_year_out"/></span>
                          </td>
                          <td class=txt bgcolor=#eeeeee width="10%">
                            <span id='lps_enter_in_use_dateSpan'><ctrl:write name="ContractorRequest" property="lps_enter_in_use_date"/></span>
                          </td>
                          <td class=txt bgcolor=#eeeeee width="10%">
                            <span id='mad_complexitySpan'><ctrl:write name="ContractorRequest" property="mad_complexity"/></span>
                          </td>
                        </tr>
                      </tbody>
                    </table>
                  </div>
                </td>
              </tr>
            </logic:equal>
          </logic:equal>

          <logic:equal name="ContractorRequest" property="showEquipmentFromProduce" value="true">
            <tr>
              <td>
                <table width="100%">
                  <tr>
                    <td width="200px"><bean:message key="ContractorRequest.stuffCategory"/></td>
                    <td><ctrl:serverList property="stuffCategory.name" idName="stuffCategory.id"
                                         action="/StuffCategoriesListAction" style="width:230px;"
                                         callback="onStuffCategorySelect" filter="filter"/></td>
                  </tr>
                </table>
              </td>
            </tr>

            <tr>
              <td>
                <table width="100%">
                  <tr>
                    <td width="200px"><bean:message key="ContractorRequest.equipment"/></td>
                    <td>
                      <table border="0" cellSpacing="0" cellPadding="0">
                        <tr>
                          <td><ctrl:text property="produce.name" style="width:500px;" readonly="true"/></td>
                          <td>&nbsp;</td>
                          <td>
                            <ctrl:ubutton styleId="buttonSelectProduce" type="submit" dispatch="selectProduce" scriptUrl="stf_id=$(stuffCategory.id)" styleClass="width120">
                              <bean:message key="button.select"/>
                            </ctrl:ubutton>
                          </td>
                        </tr>
                      </table>
                    </td>
                  </tr>
                </table>
              </td>
            </tr>

            <tr>
              <td>
                <div class="gridBack">
                  <table width='100%' cellSpacing=1 cellPadding=2 border=0 class="grid">
                    <tbody>
                      <tr>
                        <td class="table-header" align="center" width="50%">
                          <bean:message key="ContractorRequest.crq_equipment"/>
                        </td>
                        <td class="table-header" align="center" width="15%">
                          <bean:message key="ContractorRequest.ctn_number"/>
                        </td>
                        <td class="table-header" align="center" width="15%">
                          <bean:message key="ContractorRequest.stf_name"/>
                        </td>
                        <td class="table-header" align="center" width="15%">
                          <bean:message key="ContractorRequest.mad_complexity"/>
                        </td>
                      </tr>
                      <tr>
                        <td class=txt bgcolor=#eeeeee width="50%">
                          <span id='crq_equipmentSpan'><ctrl:write name="ContractorRequest" property="crq_equipment"/></span>
                        </td>
                        <td class=txt bgcolor=#eeeeee width="15%">
                          <span id='ctn_numberSpan'><ctrl:write name="ContractorRequest" property="ctn_number"/></span>
                        </td>
                        <td class=txt bgcolor=#eeeeee width="15%">
                          <span id='stf_nameSpan'><ctrl:write name="ContractorRequest" property="stf_name"/></span>
                        </td>
                        <td class=txt bgcolor=#eeeeee width="15%">
                          <span id='mad_complexitySpan'><ctrl:write name="ContractorRequest" property="mad_complexity"/></span>
                        </td>
                      </tr>
                    </tbody>
                  </table>
                </div>
              </td>
            </tr>

            <tr>
              <td>
                <table width="100%">
                  <tr>
                    <td width="200px"><bean:message key="ContractorRequest.crq_serial_num"/></td>
                    <td><ctrl:text property="crq_serial_num" style="width:230px;"/></td>
                  </tr>
                </table>
              </td>
            </tr>

            <tr>
              <td>
                <table width="100%">
                  <tr>
                    <td width="200px"><bean:message key="ContractorRequest.crq_year_out"/></td>
                    <td><ctrl:text property="crq_year_out" style="width:230px;"/></td>
                  </tr>
                </table>
              </td>
            </tr>

            <tr>
              <td>
                <table width="100%">
                  <tr>
                    <td width="200px"><bean:message key="ContractorRequest.crq_enter_in_use_date"/></td>
                    <td width="1px"><ctrl:date property="crq_enter_in_use_date" style="width:230px;"/></td>
                    <td width="120px">&nbsp;&nbsp;&nbsp;<bean:message key="ContractorRequest.crq_operating_time"/></td>
                    <td><ctrl:text property="crq_operating_time" style="width:130px;"/></td>
                  </tr>
                </table>
              </td>
            </tr>
          </logic:equal>

          <logic:notEqual name="ContractorRequest" property="showEquipmentFromProduce" value="true">
            <logic:equal name="ContractorRequest" property="showServiceOrGuaranty" value="true">
              <tr>
                <td>
                  <table width="100%">
                    <tr>
                      <td width="200px"><bean:message key="ContractorRequest.crq_operating_time"/></td>
                      <td><ctrl:text property="crq_operating_time" style="width:130px;"/></td>
                    </tr>
                  </table>
                </td>
              </tr>
            </logic:equal>
          </logic:notEqual>


          <logic:equal name="ContractorRequest" property="showPNP" value="true">
            <tr>
              <td>
                <div class=gridBottom>
                  <table width="100%">
                    <tr>
                      <td align="right">
                        <ctrl:ubutton type="submit" dispatch="printLetterRequest" styleClass="width145" readonly="false">
                          <bean:message key="button.printLetterRequest"/>
                        </ctrl:ubutton>
                        &nbsp;
                        <ctrl:text property="letterScale" style="width:40px;text-align:right;" readonly="false"/>
                        <bean:message key="Common.percent"/>
                      </td>
                    </tr>
                  </table>
                </div>
              </td>
            </tr>
            
            <tr>
              <td>
                <table width="100%">
                  <tr>
                    <hr>
                  </tr>
                </table>
              </td>
            </tr>

            <tr>
              <td>
                <table width="100%" border="0">
                  <tr>
                    <td>
                      <b><bean:message key="ContractorRequest.crq_deliver_print"/></b>
                    </td>
                  </tr>
                </table>
              </td>
            </tr>

            <tr>
              <td>
                <table width="100%" border="0">
                  <tr>
                    <td width="310px"><bean:message key="ContractorRequest.seller"/></td>
                    <td width="250px">
                      <ctrl:serverList property="seller.name" idName="seller.id" action="/SellersListAction"
                                       selectOnly="true" style="width:200px;" readonly="true"/>
                    </td>
                    <td>
                      <ctrl:serverList property="contractorOther.name" idName="contractorOther.id"
                                       action="/ContractorsListAction" filter="filter"
                                       style="width:230px;"/>
                    </td>
                  </tr>
                </table>
              </td>
            </tr>

            <tr>
              <td>
                <table width="100%" border="0">
                  <tr>
                    <td width="310px"><bean:message key="ContractorRequest.crq_manager"/></td>
                    <td>
                      <ctrl:serverList property="manager.userFullName" idName="manager.usr_id" action="/UsersListAction"
                                       filter="filter" style="width:200px;"/>
                    </td>
                  </tr>
                </table>
              </td>
            </tr>

            <tr>
              <td>
                <table width="100%" border="0">
                  <tr>
                    <td width="310px"><bean:message key="ContractorRequest.crq_chief"/></td>
                    <td>
                      <ctrl:serverList property="chief.userFullName" idName="chief.usr_id" action="/UsersListAction"
                                       filter="filter" style="width:200px;"/>
                    </td>
                  </tr>
                </table>
              </td>
            </tr>

            <tr>
              <td>
                <table width="100%" border="0">
                  <tr>
                    <td width="310px"><bean:message key="ContractorRequest.crq_specialist"/></td>
                    <td>
                      <ctrl:serverList property="specialist.userFullName" idName="specialist.usr_id" action="/UsersListAction"
                                       filter="filter" style="width:200px;"/>
                    </td>
                  </tr>
                </table>
              </td>
            </tr>

            <tr>
              <td>
                <table width="100%" border="0">
                  <tr>
                    <td width="310px"><bean:message key="ContractorRequest.crq_city"/></td>
                    <td><ctrl:text property="crq_city" style="width:200px;"/></td>
                  </tr>
                </table>
              </td>
            </tr>

            <tr>
              <td colspan="10">
                <div class="gridBackNarrow">
                  <grid:table property="gridStages" key="number" >
                    <grid:column align="center"><jsp:attribute name="title"><bean:message key="ContractorRequestStages.comment"/></jsp:attribute></grid:column>
                    <grid:column width="1%" align="center"><jsp:attribute name="title"><bean:message key="ContractorRequestStages.needPrint"/></jsp:attribute></grid:column>
                    <grid:column width="1%"/>
                    <grid:row>
                      <span comment="${record.comment}"/>
                      <grid:colCustom>
                        <table width="100%" cellpadding="0" cellspacing="0" class="content">
                          <tr><grid:colCustom property="nameFormatted"/></tr>
                          <tr><grid:colCustom property="commentFormatted" styleId="comment_field_${record.number}"/></tr>
                        </table>
                      </grid:colCustom>
                      <grid:colCheckbox width="1%" align="center" property="needPrint" result="gridStages" resultProperty="needPrint" useIndexAsPK="true" showWait="false"/>
                      <grid:colEdit width="1%" align="center" type="script" tooltip="tooltip.ContractorRequest.editComment" onclick='setComment("${record.number}", this);' showWait="false"/>
                    </grid:row>
                  </grid:table>
                </div>
              </td>
            </tr>

            <tr>
              <td>
                &nbsp;
              </td>
            </tr>

            <tr>
              <td colspan="2" align="right">
	              <input id="buttonPrintPNPAct" type='button' onclick='printPNPAct()' class='width80' value='<bean:message key="button.printAct"/>'/>
                &nbsp;
                <ctrl:text property="actScale" style="width:40px;text-align:right;" readonly="false"/>
                <bean:message key="Common.percent"/>
              </td>
            </tr>

            <tr>
              <td>
                <table width="100%">
                  <tr>
                    <hr>
                  </tr>
                </table>
              </td>
            </tr>

          </logic:equal>


            <tr>
              <td>
                &nbsp;
              </td>
            </tr>

            <tr>
              <td id="printBlock">
                <script type="text/javascript" language="JScript">
                    doAjax({
                      synchronous:true,
                      url:'<ctrl:rewrite action="/ContractorRequestAction" dispatch="ajaxContractorRequestPrintGrid"/>',
                      update:'printBlock',
                      okCallback:function()
                      {
                        setDelivery();
                      }
                    });
                </script>
              </td>
            </tr>
          <logic:equal name="ContractorRequest" property="showServiceOrGuaranty" value="true">
            <tr>
              <td>
                <bean:message key="Common.printScale"/>
                &nbsp;
                <ctrl:text property="actScale" style="width:40px;text-align:right;" readonly="false"/>
                <bean:message key="Common.percent"/>
              </td>
            </tr>
            <tr>
              <td>
                &nbsp;
              </td>
            </tr>
          </logic:equal>

          <tr>
            <td >
              <table cellspacing="2" width="940px">
                <tr>
                  <td>
                    <bean:message key="ContractorRequest.linkedOrders"/>
                  </td>
                </tr>
                <tr>
                  <td id="linkedOrders">
                    <script type="text/javascript" language="JScript">
                        doAjax({
                          synchronous:true,
                          url:'<ctrl:rewrite action="/ContractorRequestAction" dispatch="ajaxLinkedOrdersGrid"/>',
                          update:'linkedOrders'
                        });
                    </script>
                  </td>
                </tr>
                <tr>
                  <td>
                    <div class=gridBottom>
                      <ctrl:ubutton type="submit" styleId="linkOrder" dispatch="selectOrder" askUser="" styleClass="width165">
                        <bean:message key="button.linkOrder"/>
                      </ctrl:ubutton>
                    </div>
                  </td>
                </tr>
              </table>
            </td>
          </tr>

          <tr>
            <td>
              <table width="100%">
                <tr>
                  <hr>
                </tr>
              </table>
            </td>
          </tr>
          
          <tr>
            <td >
              <table cellspacing="2" width="100%">
                <tr>
                  <td>
                    <bean:message key="ContractorRequest.attachments"/>
                  </td>
                </tr>
                <tr>
                  <td>
                    <div class="gridBack">
                      <grid:table property="attachmentsGrid" key="idx" >
                        <grid:column><jsp:attribute name="title"><bean:message key="Attachments.name"/></jsp:attribute></grid:column>
                        <grid:column width="1%" title=""/>
                        <grid:row>
                          <grid:colCustom><a href="" onclick="return downloadAttachment(${record.idx});">${record.originalFileName}</a></grid:colCustom>
                          <grid:colDelete width="1%" dispatch="deleteAttachment" scriptUrl="attachmentId=${record.idx}" type="submit" tooltip="tooltip.Attachments.delete" askUser="deleteAttachAsk"/>
                        </grid:row>
                      </grid:table>
                    </div>
                    <div class=gridBottom>
                      <ctrl:ubutton type="submit" dispatch="deferredAttach" styleClass="width80" readonly="${ContractorRequest.addAttachReadOnly}">
                        <bean:message key="button.attach"/>
                      </ctrl:ubutton>
                    </div>
                  </td>
                </tr>
              </table>
            </td>
          </tr>

          <logic:equal name="ContractorRequest" property="showPNP" value="true">
            <tr>
              <td>
                <table width="100%" border="0">
                  <tr>
                    <td>
                      <bean:message key="ContractorRequest.crq_deliver"/>&nbsp;&nbsp;
                      <ctrl:checkbox property="crq_deliver" styleClass="checkbox" value="1" readonly="${ContractorRequest.deliverReadOnly}"/>
                    </td>
                  </tr>
                </table>
              </td>
            </tr>
          </logic:equal>
          
          <tr>
            <td>
              <table width="100%" border="0">
                <tr>
                  <td>
                    <bean:message key="ContractorRequest.crq_annul"/>&nbsp;&nbsp;
                    <ctrl:checkbox property="crq_annul" styleClass="checkbox" value="1" readonly="${ContractorRequest.deliverReadOnly}"/>
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
              <input id="buttonSave" type='button' onclick='processClick()' class='width80' value='<bean:message key="button.save"/>'/>
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>

  <div id="comment-form" style="display:none;" >
    <div style="padding:2px">
      <textarea name="comment" style="width:346px" rows=10></textarea><br>
      <div align="right">
        <input type="button" onclick="saveComment()" class="width80" value="<bean:message key="button.save"/>" style="text-align:right"/>
      </div>
    </div>
  </div>

  <div id="printSellerAgreement-form" style="display:none;" >
    <div style="padding:2px">
      <bean:message key="ContractorRequest.crq_lintera_agreement_date_dialog"/> <ctrl:write name="ContractorRequest" property="crq_number"/> <bean:message key="ContractorRequest.crq_lintera_agreement_date_dialog1"/>
      <ctrl:text property="crq_lintera_agreement_date_dialog" style="width:100px;" showHelp="false" readonly="false"/>
      <br>
      <br>
      <div align="right">
        <input type="button" onclick="printSellerAgreement()" class="width80" value="<bean:message key="button.continue"/>" style="text-align:right"/>
      </div>
    </div>
  </div>
  </ctrl:form>

<logic:equal name="ContractorRequest" property="printAct" value="true">
  <script language="JScript" type="text/javascript" >
	  document.getElementById('for-insert').innerHTML='<iframe src="<ctrl:rewrite action="ContractorRequestPrintActAction" scriptUrl="printExecutedWorkAct=true"/>" style="display:none" />';
  </script>
</logic:equal>
<logic:equal name="ContractorRequest" property="printPNPTimeSheet" value="true">
  <script language="JScript" type="text/javascript" >
	  document.getElementById('for-insert').innerHTML='<iframe src="<ctrl:rewrite action="ContractorRequestPrintActAction" scriptUrl="isPNPTimeSheet=true"/>" style="display:none" />';
  </script>
</logic:equal>
<logic:equal name="ContractorRequest" property="printLetterRequest" value="true">
  <script language="JScript" type="text/javascript" >
	  document.getElementById('for-insert').innerHTML='<iframe src="<ctrl:rewrite action="ContractorRequestPrintLetterRequestAction"/>" style="display:none" />';
  </script>
</logic:equal>
<logic:equal name="ContractorRequest" property="printEnumerationWork" value="true">
  <script language="JScript" type="text/javascript" >
	  document.getElementById('for-insert').innerHTML='<iframe src="<ctrl:rewrite action="ContractorRequestPrintActAction" scriptUrl="printEnumerationWork=true"/>" style="display:none" />';
  </script>
</logic:equal>
<logic:equal name="ContractorRequest" property="printReclamationAct" value="true">
  <script language="JScript" type="text/javascript" >
	  document.getElementById('for-insert').innerHTML='<iframe src="<ctrl:rewrite action="ContractorRequestPrintActAction" scriptUrl="printReclamationAct=true"/>" style="display:none" />';
  </script>
</logic:equal>
<logic:equal name="ContractorRequest" property="printSellerRequest" value="true">
  <script language="JScript" type="text/javascript" >
	  document.getElementById('for-insert').innerHTML='<iframe src="<ctrl:rewrite action="ContractorRequestPrintActAction" scriptUrl="printSellerRequest=true"/>" style="display:none" />';
  </script>
</logic:equal>
<logic:equal name="ContractorRequest" property="printSellerAgreement" value="true">
  <script language="JScript" type="text/javascript" >
	  document.getElementById('for-insert').innerHTML='<iframe src="<ctrl:rewrite action="ContractorRequestPrintActAction" scriptUrl="printSellerAgreement=true&crqSellerAgreementDateDialog=${ContractorRequest.crqSellerAgreementDateDialog}&needDetailReturn=${ContractorRequest.needDetailReturn}"/>" style="display:none" />';
  </script>
</logic:equal>

<script language="JScript" type="text/javascript">

  function submitReloadNoContractForm()
  {
    submitDispatchForm("reloadNoContract");
  }

  var requestTypeIdCheck = document.getElementById("requestTypeIdCheck");
  var requestTypeId = document.getElementById("requestType.id");
  var contractorId = document.getElementById("contractor.id");
  var contactPersonId = document.getElementById("contactPerson.cps_id");
  var contactPersonName = document.getElementById("contactPerson.cps_name");
  var contactPersonNameBtn = document.getElementById("contactPerson.cps_nameBtn");
  var addNewPersonBtn = document.getElementById("addNewPerson");
  var buttonSave = document.getElementById("buttonSave");
  buttonSave.disabled = ${ContractorRequest.readOnlySave};

  var lpsId = document.getElementById("lps_id");

  <logic:equal name="ContractorRequest" property="showService" value="true">
    var contractForWorkId = document.getElementById("contractForWork.con_id");
    var contractForWorkName = document.getElementById("contractForWorkNumberWithDate");
    var contractForWorkNameBtn = document.getElementById("contractForWorkNumberWithDateBtn");
  </logic:equal>

  <logic:equal name="ContractorRequest" property="showContractEquipment" value="true">
    var contractId = document.getElementById("contract.con_id");
    var contractName = document.getElementById("contractNumberWithDate");
    var contractNameBtn = document.getElementById("contractNumberWithDateBtn");
    var equipmentId = document.getElementById("equipment.lps_id");
    var equipmentName = document.getElementById("equipment.fullList");
    var equipmentNameBtn = document.getElementById("equipment.fullListBtn");

    var crqEquipment = document.getElementById("crq_equipmentSpan");
    var crqEquipmentSave = document.getElementById("crq_equipment");
    var ctnNumber = document.getElementById("ctn_numberSpan");
    var ctnNumberSave = document.getElementById("ctn_number");
    var stf_name = document.getElementById("stf_nameSpan");
    var stf_nameSave = document.getElementById("stf_name");
    var lps_serial_num = document.getElementById("lps_serial_numSpan");
    var lps_serial_numSave = document.getElementById("lps_serial_num");
    var lps_year_out = document.getElementById("lps_year_outSpan");
    var lps_year_outSave = document.getElementById("lps_year_out");

    var lpsEnterInUseDateSave = document.getElementById("lps_enter_in_use_date");
    var madComplexitySave = document.getElementById("mad_complexity");

    <logic:equal name="ContractorRequest" property="showServiceOrGuaranty" value="true">
      var lps_enter_in_use_date = document.getElementById("lps_enter_in_use_dateSpan");
      var mad_complexity = document.getElementById("mad_complexitySpan");

      var shp_date = document.getElementById("shp_date");
      var con_number = document.getElementById("con_number");
      var con_date = document.getElementById("con_date");
      var spc_number = document.getElementById("spc_number");
      var spc_date = document.getElementById("spc_date");
      var conSellerId = document.getElementById("con_seller_id");
      var conSeller = document.getElementById("con_seller");
    </logic:equal>
  </logic:equal>

  <logic:equal name="ContractorRequest" property="showEquipmentFromProduce" value="true">
    var stuffCategoryId = document.getElementById("stuffCategory.id");
    var buttonSelectProduce = document.getElementById("buttonSelectProduce");
    var crqEquipment = document.getElementById("crq_equipmentSpan");
    var ctnNumber = document.getElementById("ctn_numberSpan");
    var stf_name = document.getElementById("stf_nameSpan");
    var mad_complexity = document.getElementById("mad_complexitySpan");
  </logic:equal>

  <logic:equal name="ContractorRequest" property="showPNP" value="true">
    var managerId = document.getElementById("manager.usr_id");
    var managerFullName = document.getElementById("manager.userFullName");

    var sellerId = document.getElementById("seller.id");
    var sellerName = document.getElementById("seller.name");
    var contractorOtherId = document.getElementById("contractorOther.Id");
    var contractorOtherName = document.getElementById("contractorOther.name");
    var contractorOtherBtn = document.getElementById("contractorOther.nameBtn");
  </logic:equal>

  function onRequestTypeChange()
  {
    //проверим, может можно перегрузить только таблицу для печати
    if (
        (requestTypeIdCheck.value == '2' || requestTypeIdCheck.value == '3') &&
        (requestTypeId.value == '2' || requestTypeId.value == '3')
       )
    {
      doAjax({
        params:{requestTypeId:requestTypeId.value},
        synchronous:true,
        url:'<ctrl:rewrite action="/ContractorRequestAction" dispatch="ajaxChangeRequestTypeId"/>',
        update:'printBlock'
      });
      requestTypeIdCheck.value = requestTypeId.value; 
    }
    else
    {
      submitDispatchForm("reload");
    }
  }

  <logic:equal name="ContractorRequest" property="showServiceOrGuaranty" value="true">
    showSeller('${ContractorRequest.contract.seller.name}');

    function showSeller(seller)
    {
      if ( seller != '' )
      {
        document.getElementById('divShowSeller').innerHTML = '<bean:message key="ContractorRequest.contractSeller"/>' + ' ' + seller;
        document.getElementById('tdShowSeller').width = '210';
      }
      else
      {
        document.getElementById('divShowSeller').innerHTML = ''; 
        document.getElementById('tdShowSeller').width = '';
      }
    }
  </logic:equal>

  <logic:equal name="ContractorRequest" property="showPNP" value="true">
    setSeller(sellerId.value, sellerName.value);
  </logic:equal>

  <logic:equal name="ContractorRequest" property="formReadOnly" value="false">
    isContractorSelected();
    <logic:equal name="ContractorRequest" property="showEquipmentFromProduce" value="true">
      onStuffCategoryLoad();
    </logic:equal>
  </logic:equal>

  function isContractorSelected()
  {
    if ( contractorId.value == "" )
    {
      contactPersonName.disabled = true;
      disableImgButton(contactPersonNameBtn, true);
      addNewPersonBtn.disabled = true;
      <logic:equal name="ContractorRequest" property="showContractEquipment" value="true">
        contractName.disabled = true;
        disableImgButton(contractNameBtn, true);
        equipmentName.disabled = true;
        disableImgButton(equipmentNameBtn, true);
      </logic:equal>
      <logic:equal name="ContractorRequest" property="showService" value="true">
        contractForWorkName.disabled = true;
        disableImgButton(contractForWorkNameBtn, true);
      </logic:equal>
    }
    else
    {
      contactPersonName.disabled = false;
      disableImgButton(contactPersonNameBtn, false);
      addNewPersonBtn.disabled = false;
      <logic:equal name="ContractorRequest" property="showContractEquipment" value="true">
        contractName.disabled = false;
        disableImgButton(contractNameBtn, false);
        equipmentName.disabled = false;
        disableImgButton(equipmentNameBtn, false);
      </logic:equal>
      <logic:equal name="ContractorRequest" property="showService" value="true">
        contractForWorkName.disabled = false;
        disableImgButton(contractForWorkNameBtn, false);
      </logic:equal>
    }
  }

  function onContractorSelect()
  {
    contactPersonName.value = "";
    contactPersonId.value = "";
    <logic:equal name="ContractorRequest" property="showContractEquipment" value="true">
      contractName.value = "";
      contractId.value = "";
      setEquipmentNull();
    </logic:equal>
    <logic:equal name="ContractorRequest" property="showService" value="true">
      contractForWorkName.value = "";
      contractForWorkId.value = "";
    </logic:equal>
    isContractorSelected();
  }

  <logic:equal name="ContractorRequest" property="showContractEquipment" value="true">
    function onContractSelect(arg)
    {
      setEquipmentNull();
      var seller = arg?arg.arguments[5]:null;
      var sellerName = arg?arg.arguments[6]:null;
      <logic:equal name="ContractorRequest" property="showPNP" value="true">
        setSeller(seller, sellerName);
      </logic:equal>
      <logic:equal name="ContractorRequest" property="showServiceOrGuaranty" value="true">
        showSeller(sellerName);
        doAjax({
          params:{sellerId:seller},
          synchronous:true,
          url:'<ctrl:rewrite action="/ContractorRequestAction" dispatch="ajaxChangeCrqSeller"/>',
          update:'printBlock'
        });
      </logic:equal>
    }

    function setEquipmentNull()
    {
      equipmentName.value = "";
      equipmentId.value = "";

      lpsId.value = "";
	    crqEquipment.innerHTML = "";
	    crqEquipmentSave.value = "";
	    ctnNumber.innerHTML = "";
	    ctnNumberSave.value = "";
      stf_name.innerHTML = "";
      stf_nameSave.value = "";
      lps_serial_num.innerHTML = "";
      lps_serial_numSave.value = "";
      lps_year_out.innerHTML = "";
      lps_year_outSave.value = "";

      lpsEnterInUseDateSave.value = "";
      madComplexitySave.value = "";

      <logic:equal name="ContractorRequest" property="showPNP" value="true">
        managerId.value = "";
        managerFullName.value = "";
      </logic:equal>
      <logic:equal name="ContractorRequest" property="showServiceOrGuaranty" value="true">
        lps_enter_in_use_date.innerHTML = "";
        mad_complexity.innerHTML = "";

        shp_date.value = "";
        con_number.value = "";
        con_date.value = "";
        spc_number.value = "";
        spc_date.value = "";
	      conSellerId.value = "";
	      conSeller.value = "";
      </logic:equal>
    }

    function onEquipmentSelect(arg)
    {
      lpsId.value = arg?arg.arguments[0]:null;
	    crqEquipment.innerHTML = arg?arg.arguments[2]:null;
	    crqEquipmentSave.value = arg?arg.arguments[2]:null;
	    ctnNumber.innerHTML = arg?arg.arguments[3]:null;
	    ctnNumberSave.value = arg?arg.arguments[3]:null;
      stf_name.innerHTML = arg?arg.arguments[4]:null;
      stf_nameSave.value = arg?arg.arguments[4]:null;
      lps_serial_num.innerHTML = arg?arg.arguments[5]:null;
      lps_serial_numSave.value = arg?arg.arguments[5]:null;
      lps_year_out.innerHTML = arg?arg.arguments[6]:null;
      lps_year_outSave.value = arg?arg.arguments[6]:null;

      lpsEnterInUseDateSave.value = arg?arg.arguments[9]:null;
      madComplexitySave.value = arg?arg.arguments[10]:null;

      <logic:equal name="ContractorRequest" property="showPNP" value="true">
        if ( null == managerId.value || managerId.value == '' )
        {
          managerId.value = arg?arg.arguments[7]:null;
          managerFullName.value = arg?arg.arguments[8]:null;
        }
        var lps_occupied = arg?arg.arguments[11]:null;
        if ( lps_occupied && lps_occupied != '' )
        {
          alert('<bean:message key="msg.contractor_request.select_double_equipment"/>' + ' ' + arg.arguments[12]);
        }
      </logic:equal>
      <logic:equal name="ContractorRequest" property="showServiceOrGuaranty" value="true">
        lps_enter_in_use_date.innerHTML = arg?arg.arguments[9]:null;
        mad_complexity.innerHTML = arg?arg.arguments[10]:null;

        shp_date.value = arg?arg.arguments[13]:null;
        con_number.value = arg?arg.arguments[14]:null;
        con_date.value = arg?arg.arguments[15]:null;
        spc_number.value = arg?arg.arguments[16]:null;
        spc_date.value = arg?arg.arguments[17]:null;
	      conSellerId.value = arg?arg.arguments[18]:null;
	      conSeller.value = arg?arg.arguments[19]:null;
      </logic:equal>
    }
  </logic:equal>

  <logic:equal name="ContractorRequest" property="showEquipmentFromProduce" value="true">
    function onStuffCategoryLoad()
    {
      buttonSelectProduce.disabled = (stuffCategoryId.value == '');
      if (buttonSelectProduce.disabled)
      {
        setEquipmentProduceNull();
      }
    }

    function onStuffCategorySelect()
    {
      buttonSelectProduce.disabled = false;
      setEquipmentProduceNull();
    }

    function setEquipmentProduceNull()
    {
	    crqEquipment.innerHTML = '';
	    ctnNumber.innerHTML = '';
      stf_name.innerHTML = '';
      mad_complexity.innerHTML = '';
    }
  </logic:equal>

  <logic:equal name="ContractorRequest" property="showPNP" value="true">
    function setContractorOther(disable)
    {
      if ( disable )
      {
        contractorOtherId.value = '';
        contractorOtherName.disabled = true;
        disableImgButton(contractorOtherBtn, true);
      }
      else
      {
        contractorOtherName.disabled = false;
        disableImgButton(contractorOtherBtn, false);
      }
    }

    function setSeller(seller, sName)
    {
      if ( seller )
      {
        // 2 - Прочие
        sellerId.value = seller;
        sellerName.value = sName;
        if ( '2' == seller )
        {
          setContractorOther(false);
        }
	      else
        {
	        setContractorOther(true);
        }
      }
      else
      {
        sellerId.value = '';
        sellerName.value = '';
      }
    }

    var currentCommentTag = null;
    var commentNumber = null;
    var comment = null;
    function setComment(number, tag)
    {
      currentCommentTag = tag;
      comment = currentCommentTag.parentNode.parentNode.getAttribute('comment');
      while ( comment.indexOf('<br>') != -1 )
      {
       comment = comment.replace('<br>', '\r\n');
      }
	    document.getElementById('comment').value = comment;
      commentNumber = number;
      TagToTip("comment-form", LEFT, true, WIDTH, 350, PADDING, 0, STICKY, true, CLICKCLOSE, false, CLOSEBTN, true, EXCLUSIVE, true, BGCOLOR, "#eeeeee", BORDERCOLOR, "#323232", TITLEBGCOLOR, "#323232", CLOSEBTNCOLORS,["#323232","#ffffff","#323232","#ffffff"],DELAY,0);
    }

    function saveComment()
    {
        doAjax({
        url:'<ctrl:rewrite action="/ContractorRequestAction" dispatch="saveComment"/>',
        params:{number:commentNumber, comment:document.getElementsByName('comment')[0].value},
        synchronous:true,
        okCallback:function()
        {
          var commentValue = document.getElementsByName('comment')[0].value;
          while ( commentValue.indexOf('\r\n') != -1 )
          {
           commentValue = commentValue.replace('\r\n', '<br>');
          }
          currentCommentTag.parentNode.parentNode.setAttribute('comment', commentValue);
          var commentField = document.getElementById('comment_field_' + commentNumber);
          if (commentField)
          {
            commentField.innerHTML = commentValue;
          }
          tt_HideInit();
        }
      });
    }
  </logic:equal>


    var crq_reclamation_date = document.getElementById("crq_reclamation_date");
    var crq_lintera_request_date = document.getElementById("crq_lintera_request_date");
    var crq_defect_act = document.getElementById("crq_defect_act");
    var crq_reclamation_act = document.getElementById("crq_reclamation_act");
    var crq_lintera_agreement_date = document.getElementById("crq_lintera_agreement_date");
    var visitId = document.getElementById("visitId");

    var crq_no_contract = document.getElementById("crq_no_contract");

    function addToGrid()
    {
      var params =  $H(Form.serializeElements($$('#printBlock input'),true));
      params.merge({counter:'0'});
      doAjax({
        url:'<ctrl:rewrite action="/ContractorRequestAction" dispatch="ajaxAddToGrid"/>',
        params:params,
        synchronous:true,
        update:('printBlock'),
        okCallback:function()
        {
          setDelivery();
        }
      });
    }

    function removeFromGrid(id)
    {
      var params =  $H(Form.serializeElements($$('#printBlock input'),true));
      params.merge({counter:'0',id:id});
      doAjax({
        url:'<ctrl:rewrite action="/ContractorRequestAction" dispatch="ajaxRemoveFromGrid"/>',
        params:params,
        synchronous:true,
        update:'printBlock',
        okCallback:function()
        {
          setDelivery();
        }
      });
    }

    function checkPrintDates()
    {
      var correctDatesK1K2 = true;
      var correctDatesK2K3 = true;
      var i = 0;
      for ( ; i < 100; i++ )
      {
        var crpReclamationDate = document.getElementById('contractorRequestPrints['+ i +'].crp_reclamation_date_formatted');
        var crpSellerRequestDate = document.getElementById('contractorRequestPrints['+ i +'].crp_lintera_request_date_formatted');
        var crpSellerAgreementDate = document.getElementById('contractorRequestPrints['+ i +'].crp_lintera_agreement_date_formatted');
        if ( crpReclamationDate && crpSellerRequestDate && crpSellerAgreementDate )
        {
          if ( crpReclamationDate.value != '' && crpSellerRequestDate.value != '' )
          {
            correctDatesK1K2 = getDateForJS(crpSellerRequestDate.value) >= getDateForJS(crpReclamationDate.value);
            crpReclamationDate.style.backgroundColor = ( !correctDatesK1K2 ) ? 'pink' : 'white';
            crpSellerRequestDate.style.backgroundColor = ( !correctDatesK1K2 ) ? 'pink' : 'white';
          }
          if ( crpSellerRequestDate.value != '' && crpSellerAgreementDate.value != '' )
          {
            correctDatesK2K3 = getDateForJS(crpSellerAgreementDate.value) >= getDateForJS(crpSellerRequestDate.value);
            crpSellerRequestDate.style.backgroundColor = ( !correctDatesK1K2 ) ? 'pink' : 'white';
            crpSellerAgreementDate.style.backgroundColor = ( !correctDatesK1K2 ) ? 'pink' : 'white';
          }
        }
      }

      if ( !correctDatesK1K2 )
      {
        return '<bean:message key="error.contractor_request.incorrectPrintK1K2"/>';
      }
      if ( !correctDatesK2K3 )
      {
        return '<bean:message key="error.contractor_request.incorrectPrintK2K3"/>';
      }

      return '';
    }

    function printReclamationAct(id)
    {
		  var msg = checkPrintDates();
      if ( msg != '' )
      {
        alert(msg);
        return;
      }
      
      var crpReclamationDate = document.getElementById('contractorRequestPrints['+ id +'].crp_reclamation_date_formatted');
      if ( crpReclamationDate == null || crpReclamationDate.value == '' )
      {
        alert('<bean:message key="error.contractor_request.incorrectPrintReclamationActDate"/>');
      }
      else
      {
        visitId.value = id + 1;
        crq_reclamation_date.value = crpReclamationDate.value;
	      setButtonsDisabled(true);
        submitDispatchForm("printReclamationAct");
      }
    }

    function printSellerRequest(id)
    {
      var msg = checkPrintDates();
      if ( msg != '' )
      {
        alert(msg);
        return;
      }

      var crpReclamationDate = document.getElementById('contractorRequestPrints['+ id +'].crp_reclamation_date_formatted');
      var crpSellerRequestDate = document.getElementById('contractorRequestPrints['+ id +'].crp_lintera_request_date_formatted');
      if ( crpSellerRequestDate.value == '' || crpReclamationDate.value == '' )
      {
        alert('<bean:message key="error.contractor_request.incorrectPrintSellerRequestDate"/>');
      }
      else
      {
        visitId.value = id + 1;
        crq_lintera_request_date.value = crpSellerRequestDate.value;
	      setButtonsDisabled(true);
        submitDispatchForm("printSellerRequest");
      }
    }

    function printEnumerationWork(id)
    {
      var crpNoDefectAct = document.getElementById('contractorRequestPrints['+ id +'].crp_no_defect_act');
      var crpNoReclamationAct = document.getElementById('contractorRequestPrints['+ id +'].crp_no_reclamation_act');
      visitId.value = id + 1;
      crq_defect_act.value = '';
      crq_reclamation_act.value = '';
      if ( crpNoDefectAct )
      {
        crq_defect_act.value = (crpNoDefectAct.value == '1') ? '' : '1';
      }
      if ( crpNoReclamationAct )
      {
        crq_reclamation_act.value = (crpNoReclamationAct.value) == '1' ? '' : '1';
      }
	    setButtonsDisabled(true);
      submitDispatchForm("printEnumerationWork");
    }

    function dataForSellerAgreement(id)
    {
      var msg = checkPrintDates();
      if ( msg != '' )
      {
        alert(msg);
        return;
      }
      
      var crpSellerRequestDate = document.getElementById('contractorRequestPrints['+ id +'].crp_lintera_request_date_formatted');
      var crpSellerAgreementDate = document.getElementById('contractorRequestPrints['+ id +'].crp_lintera_agreement_date_formatted');
      if ( crpSellerRequestDate.value == '' || crpSellerAgreementDate.value == '' )
      {
        alert('<bean:message key="error.contractor_request.incorrectPrintSellerAgreementDate"/>');
      }
      else
      {
        visitId.value = id + 1;
        crq_lintera_agreement_date.value = crpSellerAgreementDate.value;
        TagToTip("printSellerAgreement-form", LEFT, true, WIDTH, 580, PADDING, 10, STICKY, true, CLICKCLOSE, false, CLOSEBTN, true, EXCLUSIVE, true, BGCOLOR, "#eeeeee", BORDERCOLOR, "#323232", TITLEBGCOLOR, "#323232", CLOSEBTNCOLORS,["#323232","#ffffff","#323232","#ffffff"], DELAY, 0);
      }
    }

    function printSellerAgreement()
    {
      var crqSellerAgreementDateDialog = document.getElementsByName('crq_lintera_agreement_date_dialog')[0].value;
	    document.getElementById("crqSellerAgreementDateDialog").value = crqSellerAgreementDateDialog;
      var needDetailReturn = false;
      if (isUserAgree('<bean:message key="msg.contractor_request.needDetailReturn"/>', false, 400, 100))
      {
        needDetailReturn = true;
      }
	    document.getElementById("needDetailReturn").value = needDetailReturn;
      tt_HideInit();
	    setButtonsDisabled(true);
      submitDispatchForm("printSellerAgreement");
    }

    function printGSAct(id)
    {
      visitId.value = id + 1;
      var crpNoDefectAct = document.getElementById('contractorRequestPrints['+ id +'].crp_no_defect_act');
      crq_defect_act.value = '';
      if ( crpNoDefectAct )
      {
        crq_defect_act.value = (crpNoDefectAct.value == '1') ? '' : '1';
      }
	    setButtonsDisabled(true);
      submitDispatchForm("printGSAct");
    }

  function printPNPTimesheet(id)
  {
	  setCookie("index", ++id);
	  setButtonsDisabled(true);
	  submitDispatchForm("printPNPTimeSheet");
  }

  function removeFromOrder(ordId)
   {
    doAjax({
      url:'<ctrl:rewrite action="/ContractorRequestAction" dispatch="ajaxRemoveFromOrderGrid"/>',
      params:{'ordId':ordId},
      synchronous:true,
      update:'linkedOrders'
    });
  }

  function downloadAttachment(id)
  {
	  document.getElementById('for-insert').innerHTML='<iframe src=\'<html:rewrite action="/ContractorRequestAction.do?dispatch=downloadAttachment"/>&attachmentId=' + id + '\' style=\'display:none\' />';
		return false;
	}

  function processClick()
  {
    <logic:equal name="ContractorRequest" property="showServiceOrGuaranty" value="true">
      if ( !crq_no_contract.checked && lpsId.value == '' )
      {
        alert('<bean:message key="error.contractor_request.equipment_empty"/>');
        return;
      }
      var msg = checkPrintDates();
      if ( msg != '' )
      {
        alert(msg);
        return;
      }
    </logic:equal>

	  setButtonsDisabled(true);
    submitDispatchForm("process");
  }

  function printPNPAct()
  {
	  setButtonsDisabled(true);
    submitDispatchForm("printPNPAct");
  }

  function setButtonsDisabled(disable)
  {
	  var i = 0;
	  for (; i < 100; i++)
	  {
		  var printReclamationActBtn = document.getElementById('printReclamationActBtn' + i);
		  if (printReclamationActBtn)
			  printReclamationActBtn.disabled = disable;
		  var printSellerRequestBtn = document.getElementById('printSellerRequestBtn' + i);
		  if (printSellerRequestBtn)
			  printSellerRequestBtn.disabled = disable;
		  var printEnumerationWorkBtn = document.getElementById('printEnumerationWorkBtn' + i);
		  if (printEnumerationWorkBtn)
			  printEnumerationWorkBtn.disabled = disable;
		  var dataForSellerAgreementBtn = document.getElementById('dataForSellerAgreementBtn' + i);
		  if (dataForSellerAgreementBtn)
			  dataForSellerAgreementBtn.disabled = disable;
		  var printGSActBtn = document.getElementById('printGSActBtn' + i);
		  if (printGSActBtn)
			  printGSActBtn.disabled = disable;
		  var printPNPTimesheetBtn = document.getElementById('printPNPTimesheetBtn' + i);
		  if (printPNPTimesheetBtn)
			  printPNPTimesheetBtn.disabled = disable;
	  }

	  if (document.getElementById('buttonPrintPNPAct') != null)
	    document.getElementById('buttonPrintPNPAct').disabled = disable;

	  if (document.getElementById('buttonSave') != null)
	    document.getElementById('buttonSave').disabled = disable;
  }

  setButtonsDisabled(false);

</script>
