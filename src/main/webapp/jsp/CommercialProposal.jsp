<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>

<div id='for-insert'></div>

<script language="JScript" type="text/javascript">
  function submitReloadForm()
  {
    submitDispatchForm("reload");
  }
  function submitReloadFormPrice()
  {
    submitDispatchForm("reloadPrice");
  }
</script>

<ctrl:form readonly="${CommercialProposal.formReadOnly}">
<ctrl:hidden property="cpr_id"/>
<ctrl:hidden property="is_new_doc"/>
<ctrl:hidden property="printMode"/>
<ctrl:hidden property="usr_date_create"/>
<ctrl:hidden property="usr_date_edit"/>
<ctrl:hidden property="createUser.usr_id"/>
<ctrl:hidden property="editUser.usr_id"/>
<ctrl:hidden property="priceCondition.name"/>
<ctrl:askUser name="deleteAttachAsk" key="msg.order.delete_attach" showOkCancel="false" height="120"/>
<logic:equal name="CommercialProposal" property="showAllTransport" value="false">
  <ctrl:hidden property="cpr_all_transport"/>
</logic:equal>

<ctrl:hidden property="cpr_sum_transport_formatted"/>
<ctrl:hidden property="cpr_sum_assembling_formatted"/>

<logic:equal name="CommercialProposal" property="showDoubleFields" value="false">
  <ctrl:hidden property="cpr_pay_condition_invoice"/>
</logic:equal>

<span id="total" style="display:none">${CommercialProposal.total}</span>
<logic:notEqual name="CommercialProposal" property="cpr_assemble_minsk_store" value="1">
  <ctrl:hidden property="cpr_guaranty_in_month"/>
  <ctrl:hidden property="cpr_prepay_percent"/>
  <ctrl:hidden property="cpr_delay_days"/>
  <ctrl:hidden property="cpr_free_prices"/>
</logic:notEqual>

<ctrl:hidden property="cpr_donot_calculate_netto"/>

  <table class=formBackInner align="center" width="99%">
    <tr>
      <td>
        <table cellspacing="0" >
          <logic:notEqual name="CommercialProposal" property="is_new_doc" value="true">
            <tr>
              <td>
                <table width="100%">
                  <tr>
                    <td width="120px"><bean:message key="CommercialProposal.create"/></td>
                    <td width="400px"><ctrl:write name="CommercialProposal" property="usr_date_create"/> <ctrl:write name="CommercialProposal" property="createUser.userFullName"/> </td>
                    <td width="200px"><bean:message key="CommercialProposal.edit"/></td>
                    <td><ctrl:write name="CommercialProposal" property="usr_date_edit"/> <ctrl:write name="CommercialProposal" property="editUser.userFullName"/></td>
                  </tr>
                </table>
              </td>
            </tr>
            <tr>
              <td>
                <table width="100%">
                  <tr>
                    <td width="120px"><bean:message key="CommercialProposal.cpr_number"/></td>
                    <td width="400px"><ctrl:text property="cpr_number" style="width:332px;" readonly="true"/></td>
                    <td width="200px"><bean:message key="CommercialProposal.cpr_contractor"/></td>
                    <td>
                      <table border="0" cellSpacing="0" cellPadding="0">
                        <tr>
                          <td>
                            <ctrl:serverList property="contractor.name" idName="contractor.id" action="/ContractorsListAction"
                                             filter="filter" style="width:225px;" callback="onContractorSelect"/>
                          </td>
                          <td>&nbsp;</td>
                          <td>
                            <ctrl:ubutton type="submit" dispatch="newContractor" styleClass="width120">
                              <bean:message key="button.addNew"/>
                            </ctrl:ubutton>
	                          <span id="reputation"></span>
                          </td>
                        </tr>
                      </table>
                    </td>
                  </tr>
                </table>
              </td>
            </tr>
          </logic:notEqual>

          <logic:equal name="CommercialProposal" property="is_new_doc" value="true">
            <tr>
              <td>
                <table width="100%">
                  <tr>
                    <td width="120px">&nbsp;</td>
                    <td width="400px">&nbsp;</td>
                    <td width="200px"><bean:message key="CommercialProposal.cpr_contractor"/></td>
                    <td>
                      <table border="0" cellSpacing="0" cellPadding="0">
                        <tr>
                          <td>
                            <ctrl:serverList property="contractor.name" idName="contractor.id" action="/ContractorsListAction"
                                             filter="filter" style="width:225px;" callback="onContractorSelect"/>
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
          </logic:equal>
          <tr>
            <td>
              <table width="100%">
                <tr>
                  <td width="120px"><bean:message key="CommercialProposal.cpr_date"/></td>
                  <td width="400px"><ctrl:date property="cpr_date" style="width:230px;" onchange="submitReloadForm()"
                                 afterSelect="submitReloadForm" readonly="${CommercialProposal.readOnlyForAssembleMinsk}"/></td>
                  <td width="200px"><bean:message key="CommercialProposal.cpr_contact_person"/></td>
                  <td>
                    <table border="0" cellSpacing="0" cellPadding="0">
                      <tr>
                        <td>
                          <ctrl:serverList property="contactPerson.cps_name" idName="contactPerson.cps_id"
                                           action="/ContactPersonsListAction" scriptUrl="ctr_id=$(contractor.id)"
                                           callback="onContactPersonSelect" selectOnly="true" style="width:225px;"/>
                        </td>
                        <td>&nbsp;</td>
                        <td>
                          <ctrl:ubutton type="submit" styleId="addNewPerson" dispatch="newContactPerson" styleClass="width120">
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
              <table width="100%">
                <tr>
                  <td width="120px"><bean:message key="CommercialProposal.cpr_img_name"/></td>
                  <td width="400px"> <ctrl:serverList property="cpr_img_name" action="/LogoImgsListAction" style="width:310px;" selectOnly="true"/></td>
                  <td width="200px"><bean:message key="CommercialProposal.cpr_phone"/></td>
                  <td><ctrl:text property="contactPerson.cps_phone" readonly="true" style="width:369px;"/></td>
                </tr>
              </table>
            </td>
          </tr>
          <tr>
            <td>
              <table width="100%">
                <tr>
                  <td width="120px"><bean:message key="CommercialProposal.bln_id"/></td>
                  <td width="400px"><ctrl:serverList property="blank.bln_name" idName="blank.bln_id" action="/BlanksListAction"
                                       selectOnly="true" style="width:310px;" scriptUrl="type=1"/></td>
                  <td width="200px"><bean:message key="CommercialProposal.cpr_fax"/></td>
                  <td><ctrl:text property="contactPerson.cps_fax" readonly="true" style="width:369px;"/></td>
                </tr>
              </table>
            </td>
          </tr>
          <tr>
            <td>
              <table width="100%">
                <tr>
                  <td width="120px">
                    <bean:message key="CommercialProposal.cpr_concerning"/>
                  </td>
                  <td width="400px">
                    <table cellSpacing="0" cellPadding="0">
                      <tr>
                        <td>
                          <ctrl:help htmlName="concerning"/>
                        </td>
                        <td>
                          <table height="100px" cellSpacing="0" cellPadding="5">
                            <tr>
                              <td id="idConcerningCP" onclick='switchInvoiceCP(this)' class="topActiveTD">
                                <bean:message key="CommercialProposal.cpr"/>
                                <input id="idConcerningCPRB" type="radio" checked="true" class="radioActive"/>
                              </td>
                            </tr>
                            <tr>
                              <td id="idConcerningInvoice" onclick='switchInvoiceCP(this)' class="bottomInactiveTD">
                                <bean:message key="CommercialProposal.invoice"/>
                                <input id="idConcerningInvoiceRB" type="radio" class="radioInactive"/>
                              </td>
                            </tr>
                          </table>
                        </td>
                        <td id="idConcerningCPText">
                          <ctrl:textarea property="cpr_concerning" style="width:260px;height:100px;" showHelp="false"/>
                        </td>
                        <td id="idConcerningInvoiceText">
                          <ctrl:textarea property="cpr_concerning_invoice" style="width:260px;height:100px;" showHelp="false"/>
                        </td>
                      </tr>
                    </table>
                  </td>
                  <td width="200px"><bean:message key="CommercialProposal.cpr_preamble"/></td>
                  <td><ctrl:textarea property="cpr_preamble" style="width:369px;height:100px;"/></td>
                </tr>
              </table>
            </td>
          </tr>
          <tr>
            <td>
              <table width="100%">
                <tr>
                  <td>&nbsp;</td>
                </tr>
              </table>
            </td>
          </tr>
          <tr>
            <td>
              <table width="100%">
                <tr>
                  <td width="25%" style="color:#008000"><bean:message key="CommercialProposal.table_part"/></td>
                </tr>
              </table>
            </td>
          </tr>
          <tr>
            <td>
              <table width="100%">
                <tr>
                  <td>
                    <bean:message key="CommercialProposal.cpr_old_version"/>&nbsp;
                    <ctrl:checkbox property="cpr_old_version" styleClass="checkbox" value="1"
                                   onclick="askCleanReloadForm(this);" readonly="${CommercialProposal.readOnlyForAssembleMinsk}"/>
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
                    <bean:message key="CommercialProposal.cpr_assemble_minsk_store"/>&nbsp;
                    <ctrl:checkbox property="cpr_assemble_minsk_store" styleClass="checkbox" value="1"
                                   onclick="askCleanReloadForm(this);"/>
                    <logic:equal name="CommercialProposal" property="cpr_assemble_minsk_store" value="1">
                      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                      <bean:message key="CommercialProposal.cpr_free_prices"/>&nbsp;
                      <ctrl:checkbox property="cpr_free_prices" styleClass="checkbox" value="1"
                                     onclick="freePricesCheck();"/>
                    </logic:equal>
                  </td>
                </tr>
              </table>
            </td>
          </tr>
          <tr>
            <td>
              <table width="100%">
                <tr>
                  <td width="100px"><bean:message key="CommercialProposal.cpr_currency_table"/></td>
                  <td width="180px">
                    <ctrl:serverList property="currencyTable.name" idName="currencyTable.id"
                                     action="/CurrenciesListAction" selectOnly="true" style="width:110px;"
                                     callback="submitReloadForm" readonly="${CommercialProposal.readOnlyForAssembleMinsk}"/>
                  </td>
                  <td width="90px"><bean:message key="CommercialProposal.cpr_currency"/></td>
                  <td width="180px">
                    <ctrl:serverList property="currency.name" idName="currency.id"
                                     action="/CurrenciesListAction" selectOnly="true"
                                     style="width:110px;" readonly="${CommercialProposal.readOnlyForAssembleMinsk}"
                                     callback="onCurrencyChange"/>
                  </td>
                  <td width="90px"><bean:message key="CommercialProposal.cpr_course"/></td>
                  <td width="130px">
                    <ctrl:text style="width:110px"
                               property="cpr_course_formatted" onchange="changeCourse(this);"
                               readonly="${CommercialProposal.readOnlyForAssembleMinsk}"/>
                  </td>
                  <td width="250px" id="idCourseRecommended">${CommercialProposal.courseRecommendText}</td>
                  <td width="20px" id="idCourseRecommendedEmpty"></td>
                  <td width="90px"><bean:message key="CommercialProposal.cpr_nds"/></td>
                  <td><ctrl:text property="cpr_nds_formatted" style="width:110px;text-align:right;" onchange="changeNDS(this);"/></td>
                </tr>
              </table>
            </td>
          </tr>
          <logic:notEqual name="CommercialProposal" property="cpr_assemble_minsk_store" value="1">
            <tr>
              <td>
                <table width="100%">
                  <tr>
                    <td>
                      <ctrl:checkbox property="cpr_reverse_calc" styleClass="checkbox" value="1"
                                     onclick="reverseCalcOnClick();"/>
                      <bean:message key="CommercialProposal.cpr_reverse_calc"/>&nbsp;
                      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                      <bean:message key="CommercialProposal.donot_calculate_netto"/>&nbsp;
                      <ctrl:checkbox property="donot_calculate_netto" styleClass="checkbox" value="1"
                                     onclick="doNotCalculateNettoOnClick();"/>&nbsp;
                      <bean:message key="CommercialProposal.calculate_netto"/>&nbsp;
                      <ctrl:checkbox property="calculate_netto" styleClass="checkbox" value="1"
                                     onclick="calculateNettoOnClick();"/>&nbsp;
                    </td>
                  </tr>
                </table>
              </td>
            </tr>
            <tr>
              <td id="producesCommercialProposal">
                <script type="text/javascript" language="JScript">
                    doAjax({
                      synchronous:true,
                      url:'<ctrl:rewrite action="/CommercialProposalAction" dispatch="ajaxProducesCommercialProposalGrid"/>',
                      update:'producesCommercialProposal'
                    });
                </script>
              </td>
            </tr>
          </logic:notEqual>
          <logic:equal name="CommercialProposal" property="cpr_assemble_minsk_store" value="1">
            <tr>
              <td id="producesForAssembleMinsk">
                <script type="text/javascript" language="JScript">
                    doAjax({
                      synchronous:true,
                      url:'<ctrl:rewrite action="/CommercialProposalAction" dispatch="ajaxProducesForAssembleMinskGrid"/>',
                      update:'producesForAssembleMinsk'
                    });
                </script>
              </td>
            </tr>
          </logic:equal>
          <tr>
            <td>
              <div class=gridBottom>
                <logic:notEqual name="CommercialProposal" property="cpr_assemble_minsk_store" value="1">
                  <logic:equal name="CommercialProposal" property="showDownload" value="true">
                    <input type=button id="downloadTemplate" onclick="return download(${CommercialProposal.templateId});" class="width165" value="<bean:message key="button.saveTemplate"/>">
                  </logic:equal>
                  <ctrl:ubutton type="submit" dispatch="importExcel" styleClass="width165">
                    <bean:message key="button.importExcel"/>
                  </ctrl:ubutton>
                </logic:notEqual>
                <logic:notEqual name="CommercialProposal" property="cpr_assemble_minsk_store" value="1">
                  <ctrl:ubutton type="submit" dispatch="newProduce" scriptUrl="donot_calculate_netto=$(cpr_donot_calculate_netto)&cpr_reverse_calc=$(cpr_reverse_calc)&cur_id=$(currency.id)" styleClass="width80">
                    <bean:message key="button.add"/>
                  </ctrl:ubutton>
                </logic:notEqual>
                <logic:equal name="CommercialProposal" property="cpr_assemble_minsk_store" value="1">
                  <ctrl:ubutton type="submit" dispatch="newProduce" scriptUrl="cpr_id=${CommercialProposal.cpr_id}" styleClass="width80">
                    <bean:message key="button.add"/>
                  </ctrl:ubutton>
                </logic:equal>
                <input type=button id="generateButtonExcel"  onclick="generateExcelClick();"  class="width165" value="<bean:message key="button.formExcel"/>">
              </div>
            </td>
          </tr>
          <logic:equal name="CommercialProposal" property="showForAdmin" value="true">
            <tr>
              <td>
                <div class=gridBottom>
                  <ctrl:ubutton type="submit" dispatch="uploadTemplate" scriptUrl="referencedTable=DCL_TEMPLATE&referencedID=${CommercialProposal.templateIdCP}&id=${CommercialProposal.templateId}" styleClass="width165" readonly="false">
                    <bean:message key="button.attachTemplate"/>
                  </ctrl:ubutton>
                </div>
              </td>
            </tr>
          </logic:equal>
          <tr>
            <td>
              <table width="100%">
                <tr>
                  <td>&nbsp;</td>
                </tr>
              </table>
            </td>
          </tr>
          <tr>
            <td>
              <table width="100%">
                <tr>
                  <td width="15%"><bean:message key="CommercialProposal.priceCondition"/></td>
                  <td width="15%">
                    <ctrl:serverList property="priceCondition.nameExtended" idName="priceCondition.id"
                                     action="/IncoTermsListAction" callback="submitReloadFormPrice"
                                     selectOnly="true" style="width:150px;"
                                     readonly="${CommercialProposal.readOnlyForAssembleMinsk}"/>
                  </td>
                  <td width="6%"><bean:message key="CommercialProposal.cpr_country"/></td>
                  <td><ctrl:text property="cpr_country" style="width:332px;"/></td>
                </tr>
              </table>
            </td>
          </tr>
          <tr>
            <td>
              <table width="100%">
                <tr>
                  <td width="15%"><bean:message key="CommercialProposal.deliveryCondition"/></td>
                  <td width="15%">
                    <ctrl:serverList property="deliveryCondition.nameExtended" idName="deliveryCondition.id"
                                     action="/DeliveryConditionListAction"
                                     scriptUrl="name=$(priceCondition.name)" callback="submitReloadForm"
                                     selectOnly="true" style="width:150px;"
                                     readonly="${CommercialProposal.readOnlyForAssembleMinsk}"/>
                  </td>
                  <td width="6%"><bean:message key="CommercialProposal.cpr_delivery_address"/></td>
                  <td><ctrl:text property="cpr_delivery_address" style="width:332px;"/></td>
                </tr>
              </table>
            </td>
          </tr>
          <tr>
            <td>
              <table width="100%">
                <tr>
                  <td width="15%"><bean:message key="CommercialProposal.cpr_nds_by_string"/></td>
                  <td width="15%"><ctrl:checkbox property="cpr_nds_by_string" styleClass="checkbox" value="1"
                                                 onclick="ndsByStringOnClick();" readonly="${CommercialProposal.ndsByString}"/></td>
                  <td></td>
                  <td></td>
                </tr>
              </table>
            </td>
          </tr>

          <tr>
            <td>
              <table width="60%">
                <tr>
                  <td colspan="20">
                    <div class="gridBackNarrow" >
                      <grid:table property="gridCharges" key="number" >
                        <grid:column align="center"><jsp:attribute name="title"><bean:message key="CommercialProposalCharges.charge"/></jsp:attribute></grid:column>
                        <grid:column width="15%" align="center"><jsp:attribute name="title">${CommercialProposal.chargeSumHeader}</jsp:attribute></grid:column>
                        <grid:column width="15%" align="center"><jsp:attribute name="title"><bean:message key="CommercialProposalCharges.includeInPrice"/></jsp:attribute></grid:column>
                        <grid:row>
                          <logic:equal name="record" property='showAllTransport' value="true">
                            <grid:colCustom>
                              <bean:message key="CommercialProposalCharges.charge_transport_all"/>
                              <ctrl:checkbox property="cpr_all_transport" styleClass="checkbox" value="1" onclick="submitReloadForm();" readonly="${CommercialProposal.readOnlyForAssembleMinsk}"/>
                              )
                            </grid:colCustom>
                          </logic:equal>
                          <logic:equal name="record" property='showAllTransport' value="false">
                            <grid:colCustom property="charge"/>
                          </logic:equal>
                          <grid:colInput width="15%" textAllign="right" property="sumFormatted"
                                         result="gridCharges" resultProperty="sumFormatted" useIndexAsPK="true"
                                         onchange="submitReloadForm();" readonlyCheckerId="transportChecker"/>
                          <grid:colCustom align="center" width="15%" property="includeInPrice"/>
                        </grid:row>
                      </grid:table>
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
                  <td width="120px"><bean:message key="CommercialProposal.cpr_pay_condition"/></td>
                  <logic:equal name="CommercialProposal" property="showDoubleFields" value="false">
                    <td width="400px"><ctrl:textarea property="cpr_pay_condition" style="width:332px;height:100px;" /></td>
                  </logic:equal>
                  <logic:equal name="CommercialProposal" property="showDoubleFields" value="true">
                    <td width="400px">
                      <table cellSpacing="0" cellPadding="0">
                        <tr>
                          <td>
                            <ctrl:help htmlName="payCondition"/>
                          </td>
                          <td>
                            <table height="100px" cellSpacing="0" cellPadding="5">
                              <tr>
                                <td id="idPayConditionCP" onclick='switchInvoiceCP(this)' class="topActiveTD">
                                  <bean:message key="CommercialProposal.cpr"/>
                                  <input id="idPayConditionCPRB" type="radio" checked="true" class="radioActive"/>
                                </td>
                              </tr>
                              <tr>
                                <td id="idPayConditionInvoice" onclick='switchInvoiceCP(this)'  class="bottomInactiveTD">
                                  <bean:message key="CommercialProposal.invoice"/>
                                  <input id="idPayConditionInvoiceRB" type="radio" class="radioInactive"/>
                                </td>
                              </tr>
                            </table>
                          </td>
                          <td id="idPayConditionCPText">
                            <ctrl:textarea property="cpr_pay_condition" style="width:260px;height:100px;" showHelp="false"/>
                          </td>
                          <td id="idPayConditionInvoiceText">
                            <ctrl:textarea property="cpr_pay_condition_invoice" style="width:260px;height:100px;" showHelp="false" readonly="false"/>
                          </td>
                        </tr>
                      </table>
                    </td>
                  </logic:equal>
                  <td width="200px"><bean:message key="CommercialProposal.cpr_add_info"/></td>
                  <td><ctrl:textarea property="cpr_add_info" style="width:332px;height:100px;"/></td>
                </tr>
              </table>
            </td>
          </tr>
          <tr>
            <td>
              <table width="100%">
                <tr>
                  <td width="120px"><bean:message key="CommercialProposal.cpr_delivery_term"/></td>
                  <logic:equal name="CommercialProposal" property="showDoubleFields" value="false">
                    <td width="400px"><ctrl:text property="cpr_delivery_term" style="width:332px;"/></td>
                  </logic:equal>
                  <logic:equal name="CommercialProposal" property="showDoubleFields" value="true">
                    <td width="400px">
                      <table cellSpacing="0" cellPadding="0">
                        <tr>
                          <td>
                            <ctrl:help htmlName="deliveryTerm"/>
                          </td>
                          <td>
                            <table height="100px" cellSpacing="0" cellPadding="5">
                              <tr>
                                <td id="idDeliveryTermCP" onclick='switchInvoiceCP(this)' class="topActiveTD">
                                  <bean:message key="CommercialProposal.cpr"/>
                                  <input id="idDeliveryTermCPRB" type="radio" checked="true" class="radioActive"/>
                                </td>
                              </tr>
                              <tr>
                                <td id="idDeliveryTermInvoice" onclick='switchInvoiceCP(this)'  class="bottomInactiveTD">
                                  <bean:message key="CommercialProposal.invoice"/>
                                  <input id="idDeliveryTermInvoiceRB" type="radio" class="radioInactive"/>
                                </td>
                              </tr>
                            </table>
                          </td>
                          <td id="idDeliveryTermCPText">
                            <ctrl:textarea property="cpr_delivery_term" style="width:260px;height:100px;" showHelp="false"/>
                          </td>
                          <td id="idDeliveryTermInvoiceText">
                            <ctrl:textarea property="cpr_delivery_term_invoice" style="width:260px;height:100px;" showHelp="false" readonly="false"/>
                          </td>
                        </tr>
                      </table>
                    </td>
                  </logic:equal>
                  <td width="200px"><bean:message key="CommercialProposal.cpr_final_date"/></td>
                  <logic:equal name="CommercialProposal" property="showDoubleFields" value="false">
                    <td>
                      <ctrl:date property="cpr_final_date" style="width:200px;" readonly="${CommercialProposal.readOnlyForAssembleMinsk}"/>
                    </td>
                  </logic:equal>
                  <logic:equal name="CommercialProposal" property="showDoubleFields" value="true">
                    <td>
                      <table cellSpacing="0" cellPadding="0">
                        <tr>
                          <td>
                            <ctrl:help htmlName="finalDate"/>
                          </td>
                          <td>
                            <table height="100px" cellSpacing="0" cellPadding="5">
                              <tr>
                                <td id="idFinalDateCP" onclick='switchInvoiceCP(this)' class="topActiveTD">
                                  <bean:message key="CommercialProposal.cpr"/>
                                  <input id="idFinalDateCPRB" type="radio" checked="true" class="radioActive"/>
                                </td>
                              </tr>
                              <tr>
                                <td id="idFinalDateInvoice" onclick='switchInvoiceCP(this)'  class="bottomInactiveTD">
                                  <bean:message key="CommercialProposal.invoice"/>
                                  <input id="idFinalDateInvoiceRB" type="radio" class="radioInactive"/>
                                </td>
                              </tr>
                            </table>
                          </td>
                          <td id="idFinalDateCPText">
                            <ctrl:date property="cpr_final_date" style="width:200px;" showHelp="false"
                                       readonly="${CommercialProposal.readOnlyForAssembleMinsk}"/>
                          </td>
                          <td id="idFinalDateInvoiceText">
                            <ctrl:date property="cpr_final_date_invoice" style="width:200px;" showHelp="false" readonly="false"/>
                          </td>
                        </tr>
                      </table>
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
                  <td width="523px"></td>
                  <td width="200px"><bean:message key="CommercialProposal.cpr_user"/></td>
                  <td width="240px">
                    <ctrl:serverList property="user.userFullName" idName="user.usr_id" action="/UsersListAction" selectOnly="true" style="width:200px;"/>
                  </td>
                  <td><ctrl:checkbox property="facsimile_flag" styleClass="checkbox" value="1"/>&nbsp;<bean:message key="CommercialProposal.facsimile"/></td>
                </tr>
              </table>
            </td>
          </tr>
          <tr>
            <td>
              <table width="100%">
                <tr>
                  <td width="523px"></td>
                  <td width="200px">
                    <bean:message key="CommercialProposal.cpr_executor"/>&nbsp;
                    <ctrl:checkbox property="cpr_executor_flag" styleClass="checkbox" value="1"/>
                  </td>
                  <td><ctrl:serverList property="executor.userFullName" idName="executor.usr_id" action="/UsersListAction" selectOnly="true" style="width:310px;"/></td>
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
              <td>
                  <table cellpadding="2" cellspacing="2" width="99%">
                      <tr>
                          <td>
                              <table width="55%" cellpadding="0" cellspacing="0">
                                  <tr>
                                      <td width="32%" valign="middle"><bean:message key="CommercialProposal.title"/></td>
                                      <td width="12%" valign="middle"><bean:message key="CommercialProposal.declined"/></td>
                                      <td width="7%" valign="middle"><ctrl:checkbox property="cpr_proposal_declined"
                                                                    styleClass="checkbox"
                                                                    readonly="${CommercialProposal.cpr_proposal_received_flag}"
                                                                    onclick="onProposalDeclinedClick();"
                                                                    value="1"/></td>
                                      <td width="13%" valign="middle"><bean:message key="CommercialProposal.accepted"/></td>
                                      <td width="7%" valign="middle"><ctrl:checkbox property="cpr_proposal_received_flag"
                                                                    styleClass="checkbox"
                                                                    onclick="onProposalReceivedFlagClick();"
                                                                    readonly="${CommercialProposal.cprReceivedFlagReadonly}"
                                                                    value="1"/></td>
                                      <td width="13%" valign="middle"><bean:message key="CommercialProposal.date_accept"/></td>
                                      <td width="31%" valign="bottom"><ctrl:date property="cpr_date_accept" onchange="checkAcceptDate()"
                                                                 style="width:auto"
                                                                 afterSelect="checkAcceptDate"
                                                                 readonly="${CommercialProposal.dateAcceptReadOnly}"/>
                                      </td>
                                  </tr>
                              </table>
                          </td>
                      </tr>
                      <tr>
                          <td>
                              <table width="63%" cellpadding="3" cellspacing="0">
                                  <tr>
                                      <td width="5%"><bean:message key="CommercialProposal.tender"/></td>
                                      <td width="7%"><ctrl:checkbox property="cpr_tender_number_editable"
                                                                     styleClass="checkbox"
                                                                     onclick="onTenderFlagClick()"/></td>
                                      <td width="10%"><bean:message key="CommercialProposal.cpr_tender_number"/></td>
                                      <td width="78%"><ctrl:text property="cpr_tender_number"
                                                                 readonly="${CommercialProposal.cpr_tender_name_readonly}"
                                                                 style="width:98%; float: right"/>
                                  </tr>

                                  <tr>
                                      <td width="22%" colspan="3"><bean:message key="CommercialProposal.cpr_comment"/></td>
                                      <td width="78%"><div class="no_padding"><ctrl:textarea property="cpr_comment" style="width: 98%; height: 80px; float: right"/></div></td>
                                  </tr>
                              </table>
                          </td>
                      </tr>

                      <tr>
                          <td>
                              <table cellspacing="0" cellpadding="0" width="80%">
                                  <tr>
                                      <td>
                                          <bean:message key="CommercialProposal.attachments"/>
                                      </td>
                                  </tr>
                                  <tr>
                                      <td>
                                          <div class="gridBack">
                                              <grid:table property="attachmentsGrid" key="idx">
                                                  <grid:column>
                                                      <jsp:attribute name="title"><bean:message key="Attachments.name"/></jsp:attribute>
                                                  </grid:column>
                                                  <grid:column width="1%" title=""/>
                                                  <grid:row>
                                                      <grid:colCustom><a href=""
                                                                         onclick="return downloadAttachment(${record.idx});">${record.originalFileName}</a></grid:colCustom>
                                                      <grid:colDelete width="1%" dispatch="deleteAttachment"
                                                                      scriptUrl="attachmentId=${record.idx}"
                                                                      type="submit" tooltip="tooltip.Attachments.delete"
                                                                      askUser="deleteAttachAsk"/>
                                                  </grid:row>
                                              </grid:table>
                                          </div>
                                          <div class=gridBottom>
                                              <ctrl:ubutton type="submit" dispatch="deferredAttach"
                                                            styleClass="width80">
                                                  <bean:message key="button.attach"/>
                                              </ctrl:ubutton>
                                          </div>
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
              <table width="100%">
                <tr>
                  <hr>
                </tr>
              </table>
            </td>
          </tr>

          <logic:notEqual name="CommercialProposal" property="cpr_assemble_minsk_store" value="1">
            <tr>
              <td>
                <table width="100%" cellpadding="0" cellspacing="0">
                  <tr>
                    <td><bean:message key="CommercialProposal.print_invoice"/></td>
                  </tr>
                </table>
              </td>
            </tr>
          </logic:notEqual>
          <logic:equal name="CommercialProposal" property="cpr_assemble_minsk_store" value="1">
            <tr>
              <td>
                <table width="100%" cellpadding="0" cellspacing="0">
                  <tr>
                    <td><bean:message key="CommercialProposal.print_invoice_contract"/></td>
                  </tr>
                </table>
              </td>
            </tr>
          </logic:equal>

          <logic:equal name="CommercialProposal" property="cpr_assemble_minsk_store" value="1">
            <tr>
              <td>
                <table width="100%" cellpadding="0" cellspacing="0">
                  <tr>
                    <td width="170px"><bean:message key="CommercialProposal.contactPersonSeller"/></td>
                    <td width="250px">
                      <ctrl:serverList property="contactPersonSeller.cps_name" idName="contactPersonSeller.cps_id"
                                       action="/ContactPersonsListAction" scriptUrl="ctr_id=${CommercialProposal.contractorLTS}"
                                       selectOnly="true" style="width:200px;"/>
                    </td>
                    <td width="200px"><bean:message key="CommercialProposal.contactPersonCustomer"/></td>
                    <td>
                      &nbsp;
                      <ctrl:serverList property="contactPersonCustomer.cps_name" idName="contactPersonCustomer.cps_id"
                                       action="/ContactPersonsListAction" scriptUrl="ctr_id=$(contractor.id)"
                                       selectOnly="true" style="width:200px;" callback="onContactPersonCustomerSelect"/>
                    </td>
                  </tr>
                </table>
              </td>
            </tr>
          </logic:equal>

          <tr>
            <td>
              <table width="100%" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="170px"><bean:message key="CommercialProposal.purchase_purpose"/></td>
                  <td width="240px">
                    <ctrl:serverList property="purchasePurpose.name" idName="purchasePurpose.id"
                                     action="/PurchasePurposesListAction" selectOnly="true" style="width:200px;"/>
                  </td>
                  <td>
                    &nbsp;
                    <logic:equal name="CommercialProposal" property="showForAdmin" value="true">
                      <ctrl:ubutton type="submit" dispatch="editPurchasePurposes" styleClass="width145">
                        <bean:message key="button.editList"/>
                      </ctrl:ubutton>
                    </logic:equal>
                  </td>
                </tr>
              </table>
            </td>
          </tr>

          <logic:equal name="CommercialProposal" property="cpr_assemble_minsk_store" value="1">
            <tr>
              <td>
                <table width="100%" cellpadding="0" cellspacing="0">
                  <tr>
                    <td width="170px"><bean:message key="CommercialProposal.cpr_guaranty_in_month"/></td>
                    <td width="80px"><ctrl:text property="cpr_guaranty_in_month" style="width:70px;text-align:right;" onchange="changeGuarantyInMonth()"/></td>
                    <td>&nbsp;<bean:message key="CommercialProposal.cpr_guaranty_in_month2"/></td>
                  </tr>
                </table>
              </td>
            </tr>
            <tr>
              <td>
                <table width="100%" cellpadding="0" cellspacing="0">
                  <tr>
                    <td width="170px"><bean:message key="CommercialProposal.cpr_prepay_percent_text1"/></td>
                    <td width="80px"><ctrl:text property="cpr_prepay_percent" style="width:70px;text-align:right;" onchange="changePrepayPercent()"/></td>
                    <td width="20px"><bean:message key="CommercialProposal.cpr_prepay_percent_text2"/></td>
                    <td width="135px"><ctrl:text property="cpr_prepay_sum" style="width:129px;text-align:right;" showHelp="false" onchange="changePrepaySum()"/></td>
                    <td width="40px">&nbsp;&nbsp;${CommercialProposal.currency.name}</td>
                    <td width="120px"><bean:message key="CommercialProposal.cpr_delay_days_text1"/></td>
                    <td width="80px"><ctrl:text property="cpr_delay_days" style="width:70px;text-align:right;" onchange="changeDelayDays()"/></td>
                    <td>&nbsp;<bean:message key="CommercialProposal.cpr_delay_days_text2"/></td>
                  </tr>
                </table>
              </td>
            </tr>
            <tr>
              <td>
                <table width="100%" cellpadding="0" cellspacing="0">
                  <tr>
                    <td width="183px"><bean:message key="CommercialProposal.cpr_provider_delivery"/></td>
                    <td width="20px"><ctrl:checkbox property="cpr_provider_delivery" styleClass="checkbox" showHelp="false" value="1" onclick="providerDeliveryOnClick()"/></td>
                    <td width="40px"><bean:message key="CommercialProposal.cpr_provider_delivery_address"/></td>
                    <td width="415px"><ctrl:text property="cpr_provider_delivery_address" style="width:409px;" showHelp="false"/></td>
                    <td width="40px"><bean:message key="CommercialProposal.cpr_delivery_count_day"/></td>
                    <td width="80px"><ctrl:text property="cpr_delivery_count_day" style="width:70px;text-align:right;" onchange="deliveryCountDayOnChange();"/></td>
                    <td width="195px">&nbsp;<bean:message key="CommercialProposal.cpr_delivery_term_type"/>&nbsp;<ctrl:help htmlName="CprDeliveryTermType"/></td>
                    <td id="idTermTypeText"></td>
                  </tr>
                </table>
              </td>
            </tr>
            <tr>
              <td>
                <table width="100%" cellpadding="0" cellspacing="0">
                  <tr>
                    <td width="170px"><bean:message key="CommercialProposal.consignee"/></td>
                    <td><ctrl:serverList property="consignee.name" idName="consignee.id" action="/ContractorsListAction"
                                             filter="filter" style="width:225px;" callback="onConsigneeSelect"/></td>
                  </tr>
                </table>
              </td>
            </tr>
            <tr>
              <td>
                <table width="100%" cellpadding="0" cellspacing="0">
                  <tr>
                    <ctrl:checkbox property="cpr_no_reservation" styleClass="checkbox" value="1" readonly="${CommercialProposal.noReservationReadOnly}"/>&nbsp;
                    <bean:message key="CommercialProposal.cpr_no_reservation"/>
                  </tr>
                </table>
              </td>
            </tr>
          </logic:equal>

          <logic:equal name="CommercialProposal" property="showForAdminOrEconomist" value="true">
            <tr>
              <td>
                <table width="100%" cellpadding="0" cellspacing="0">
                  <tr>
                    <ctrl:checkbox property="cpr_can_edit_invoice" styleClass="checkbox" value="1" readonly="false" onclick="submitReloadForm();"/>&nbsp;
                    <bean:message key="CommercialProposal.cpr_can_edit_invoice"/>
                  </tr>
                </table>
              </td>
            </tr>
          </logic:equal>
          
          <logic:notEqual name="CommercialProposal" property="cpr_assemble_minsk_store" value="1">
            <tr>
              <td colspan="2" align="right">
                <ctrl:help htmlName="printInvoiceButton"/>
                <input id='printInvoiceButton' type='button' onclick='printInvoiceClick()' class='width80' value='<bean:message key="button.printInvoice"/>'/>
                &nbsp;
                <ctrl:text property="cpr_invoice_scale" style="width:40px;text-align:right;" readonly="false"/>
                <bean:message key="Common.percent"/>
              </td>
            </tr>
          </logic:notEqual>
          <logic:equal name="CommercialProposal" property="cpr_assemble_minsk_store" value="1">
            <tr>
              <td colspan="2" align="right">
                <ctrl:help htmlName="printContractButton"/>
                <input id='printContractButton' type='button' onclick='printContractClick()' class='width120' value='<bean:message key="button.printContract"/>'/>
                &nbsp;
                <ctrl:text property="cpr_contract_scale" style="width:40px;text-align:right;" readonly="false"/>
                <bean:message key="Common.percent"/>
                &nbsp;&nbsp;
                <ctrl:help htmlName="printInvoiceButton"/>
                <input id='printInvoiceButton' type='button' onclick='printInvoiceClick()' class='width80' value='<bean:message key="button.printInvoice"/>'/>
                &nbsp;
                <ctrl:text property="cpr_invoice_scale" style="width:40px;text-align:right;" readonly="false"/>
                <bean:message key="Common.percent"/>
              </td>
            </tr>
          </logic:equal>

          <logic:equal name="CommercialProposal" property="showPrintUnit" value="false">
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
              <table width="100%">
                <tr>
                  <hr>
                </tr>
              </table>
            </td>
          </tr>
          <tr>
            <td>
              <table width="100%" cellpadding="0" cellspacing="0">
                <tr>
                  <td><bean:message key="CommercialProposal.print_params"/></td>
                </tr>
              </table>
            </td>
          </tr>
          <logic:equal name="CommercialProposal" property="showPrintUnit" value="true">
            <tr>
              <td>
                <table width="100%" cellpadding="0" cellspacing="0">
                  <tr>
                    <td>
                      <bean:message key="CommercialProposal.show_unit"/>&nbsp;
                      <ctrl:checkbox property="show_unit" styleClass="checkbox" value="1" readonly="false"/>
                    </td>
                  </tr>
                </table>
              </td>
            </tr>
          </logic:equal>
          <tr>
            <td>
              <table width="100%" cellpadding="0" cellspacing="0">
                <tr>
                  <td>
                    <bean:message key="CommercialProposal.cpr_final_date_above"/>&nbsp;
                    <ctrl:checkbox property="cpr_final_date_above" styleClass="checkbox" value="1" readonly="false"/>
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
              <input id='buttonPrint' type='button' onclick='printClick()' class='width80' value='<bean:message key="button.print"/>'/>
              &nbsp;
              <ctrl:text property="cpr_print_scale" style="width:40px;text-align:right;" readonly="false"/>
              <bean:message key="Common.percent"/>
              &nbsp;&nbsp;
              <ctrl:ubutton type="submit" dispatch="back" styleClass="width80" readonly="false" askUser="">
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
    var printMode = document.getElementById("printMode");
    var contractorId = document.getElementById("contractor.id");
    if (!contractorId)
    {
      var contractorIdByName = document.getElementsByName("contractor.id");
      if (contractorIdByName && contractorIdByName.length > 0)
      {
        contractorId = contractorIdByName[0];
      }
    }
    var contactPersonName = document.getElementById("contactPerson.cps_name");
    var contactPersonNameBtn = document.getElementById("contactPerson.cps_nameBtn");
    var addNewPersonBtn = document.getElementById("addNewPerson");
    var contactPersonId = document.getElementById("contactPerson.cps_id");
    var contactPersonPhone = document.getElementById("contactPerson.cps_phone");
    var contactPersonFax = document.getElementById("contactPerson.cps_fax");
    var cprOldVersion = document.getElementById("cpr_old_version");
    var currencyId = document.getElementById("currency.id");
    var currencyName = document.getElementById("currency.name");
    if (!currencyName)
    {
      var currencyNameByName = document.getElementsByName("currency.name");
      if (currencyNameByName && currencyNameByName.length > 0)
      {
        currencyName = currencyNameByName[0];
      }
    }
    var currencyNameTable = document.getElementById("currencyTable.name");
    if (!currencyNameTable)
    {
      var currencyTableByName = document.getElementsByName("currencyTable.name");
      if (currencyTableByName && currencyTableByName.length > 0)
      {
        currencyNameTable = currencyTableByName[0];
      }
    }
    var cprCourseFormatted = document.getElementById("cpr_course_formatted");
    var cprNdsByString = document.getElementById("cpr_nds_by_string");
    var cprAssembleMinskStore = document.getElementById("cpr_assemble_minsk_store");
    var cprProposalReceivedFlag = document.getElementById("cpr_proposal_received_flag");
    var cprDateAccept = document.getElementById("cpr_date_accept");
    var cprProposalDeclined = document.getElementById("cpr_proposal_declined");
    var cprTenderNumberEditable = document.getElementById("cpr_tender_number_editable");
    var cprTenderNumber = document.getElementById("cpr_tender_number");
    var cprDateAcceptBtn = document.getElementById("cpr_date_acceptBtn");
    <logic:equal name="CommercialProposal" property="showPrintUnit" value="true">
      var show_unit = document.getElementById("show_unit");
    </logic:equal>
    var printInvoiceButton = document.getElementById("printInvoiceButton");
    <logic:equal name="CommercialProposal" property="cpr_assemble_minsk_store" value="1">
      var printContractButton = document.getElementById("printContractButton");
    </logic:equal>
    <logic:notEqual name="CommercialProposal" property="cpr_assemble_minsk_store" value="1">
      var cpr_reverse_calc = document.getElementById("cpr_reverse_calc");
      if ( cpr_reverse_calc.checked ) //не знаю почему
      {
        cpr_reverse_calc.value = '1';
      }
      else
      {
        cpr_reverse_calc.value = '';
      }

      var cpr_donot_calculate_netto = document.getElementById("cpr_donot_calculate_netto");
      var donot_calculate_netto = document.getElementById("donot_calculate_netto");
      var calculate_netto = document.getElementById("calculate_netto");
    </logic:notEqual>
    var cpr_prepay_percent = document.getElementById("cpr_prepay_percent");
    var cpr_delay_days = document.getElementById("cpr_delay_days");
    var cpr_free_prices = document.getElementById("cpr_free_prices");
    var total = document.getElementById("total");
    <logic:equal name="CommercialProposal" property="cpr_assemble_minsk_store" value="1">
      var cpr_prepay_sum = document.getElementById("cpr_prepay_sum");
      var providerDelivery = document.getElementById("cpr_provider_delivery");
      var providerDeliveryAddress = document.getElementById("cpr_provider_delivery_address");
      var deliveryCountDay = document.getElementById("cpr_delivery_count_day");

      var contactPersonCustomerId = document.getElementById("contactPersonCustomer.cps_id");
      var contactPersonCustomerName = document.getElementById("contactPersonCustomer.cps_name");
      var consigneeId = document.getElementById("consignee.id");
      var consigneeName = document.getElementById("consignee.Name");
    </logic:equal>
    var cpr_date = document.getElementById("cpr_date");
    var cprFinalDate = document.getElementById("cpr_final_date");

    showInvoiceButton();

    var idConcerningInvoiceText = document.getElementById('idConcerningInvoiceText');
    if (idConcerningInvoiceText) idConcerningInvoiceText.style.display = 'none';
    var idConcerningCp = document.getElementById('idConcerningCp');
    if (idConcerningCp) idConcerningCp.style.backgroundColor = '#d4d0c8';
    var idConcerningInvoice = document.getElementById('idConcerningInvoice');
    if (idConcerningInvoice) idConcerningInvoice.style.cursor = 'pointer';

    <logic:equal name="CommercialProposal" property="showDoubleFields" value="true">
      var idPayConditionInvoiceText = document.getElementById('idPayConditionInvoiceText');
      if (idPayConditionInvoiceText) idPayConditionInvoiceText.style.display = 'none';
      var idPayConditionCp = document.getElementById('idPayConditionCp');
      if (idPayConditionCp) idPayConditionCp.style.backgroundColor = '#d4d0c8';
      var idPayConditionInvoice = document.getElementById('idPayConditionInvoice');
      if (idPayConditionInvoice) idPayConditionInvoice.style.cursor = 'pointer';

      var idDeliveryTermInvoiceText = document.getElementById('idDeliveryTermInvoiceText');
      if (idDeliveryTermInvoiceText) idDeliveryTermInvoiceText.style.display = 'none';
      var idDeliveryTermCp = document.getElementById('idDeliveryTermCp');
      if (idDeliveryTermCp) idDeliveryTermCp.style.backgroundColor = '#d4d0c8';
      var idDeliveryTermInvoice = document.getElementById('idDeliveryTermInvoice');
      if (idDeliveryTermInvoice) idDeliveryTermInvoice.style.cursor = 'pointer';

      var idFinalDateInvoiceText = document.getElementById('idFinalDateInvoiceText');
      if (idFinalDateInvoiceText) idFinalDateInvoiceText.style.display = 'none';
      var idFinalDateCp = document.getElementById('idFinalDateCp');
      if (idFinalDateCp) idFinalDateCp.style.backgroundColor = '#d4d0c8';
      var idFinalDateInvoice = document.getElementById('idFinalDateInvoice');
      if (idFinalDateInvoice) idFinalDateInvoice.style.cursor = 'pointer';
    </logic:equal>

    var buttonSave = document.getElementById("buttonSave");
    if (buttonSave) { buttonSave.disabled = ${CommercialProposal.formReadOnly}; }

    if (contractorId)
    {
      contractorAjax(contractorId.value);
    }
    <logic:equal name="CommercialProposal" property="formReadOnly" value="false">
      isContractSelected();
      onProposalReceivedFlagClick();
    </logic:equal>

    <logic:equal name="CommercialProposal" property="cpr_assemble_minsk_store" value="1">
      checkSalePrices();
      changePrepayPercent();
      providerDeliveryOnClick();
      showCorrectTermTypeText();
    </logic:equal>

    function onContactPersonSelect(arg)
    {
      contactPersonId.value = arg?arg.arguments[0]:null;
      contactPersonPhone.value = arg?arg.arguments[2]:null;
      contactPersonFax.value = arg?arg.arguments[3]:null;
    }

    function onContactPersonCustomerSelect(arg)
    {
      var contractComment = arg?arg.arguments[8]:null;
      if (contractComment == null || contractComment == '')
      {
        alert('<bean:message key="msg.commercial.noContractComment"/>');
        contactPersonCustomerId.value = '';
        contactPersonCustomerName.value = ''; 
      }
    }

    function onProposalReceivedFlagClick()
    {
      <logic:equal name="CommercialProposal" property="dateAcceptReadOnly" value="false">
        //все это делаем, если дата акцепта доступна
        if (!cprDateAccept || !cprProposalReceivedFlag || !cprProposalDeclined || !cprDateAcceptBtn)
        {
          return;
        }
        cprDateAccept.disabled = !cprProposalReceivedFlag.checked;
        cprProposalDeclined.disabled = cprProposalReceivedFlag.checked;
        cprProposalDeclined.readonly = cprProposalReceivedFlag.checked;
        cprDateAcceptBtn.disabled = !cprProposalReceivedFlag.checked;
        disableImgButton(cprDateAcceptBtn, !cprProposalReceivedFlag.checked);

        checkAcceptDate();
      </logic:equal>
    }

    function onProposalDeclinedClick() {
        if (!cprProposalReceivedFlag || !cprProposalDeclined)
        {
          return;
        }
        cprProposalReceivedFlag.disabled = cprProposalDeclined.checked;
        cprProposalReceivedFlag.readonly = cprProposalDeclined.checked;
    }

    function onTenderFlagClick()
    {
        cprTenderNumber.disabled = !cprTenderNumberEditable.checked;
        cprTenderNumber.readOnly = !cprTenderNumberEditable.checked;
    }

    function showRecommendedCourse()
    {
      if (!currencyName || !currencyNameTable)
      {
        return;
      }
      var tdCourseRecommended = document.getElementById("idCourseRecommended");
      var tdCourseRecommendedEmpty = document.getElementById("idCourseRecommendedEmpty");
      if (!tdCourseRecommended || !tdCourseRecommendedEmpty)
      {
        return;
      }
      var showRecommended = (currencyNameTable.value != 'BYN' && currencyName.value == 'BYN');
      tdCourseRecommended.style.display = (showRecommended ? "" : "none");
      tdCourseRecommendedEmpty.style.display = (showRecommended ? "none" : "");
    }

    function checkFieldsOnChangeCourse()
    {
      if (!cprCourseFormatted || !currencyName || !currencyNameTable)
      {
        return;
      }
      var course = parseFloat(getSumForJS(cprCourseFormatted.value));
      var incorrectCourse = ( currencyName.value == currencyNameTable.value && course != 1 ) || ( currencyName.value != currencyNameTable.value && course == 1 );
      currencyName.style.backgroundColor = incorrectCourse ? 'pink' : 'white';
      currencyNameTable.style.backgroundColor = incorrectCourse ? 'pink' : 'white';
      cprCourseFormatted.style.backgroundColor = incorrectCourse ? 'pink' : 'white';
   }

    function showInvoiceButton()
    {
      if (!currencyName || !cprNdsByString || !printInvoiceButton)
      {
        return;
      }
      showRecommendedCourse();

      if ( ( !${CommercialProposal.correctKoef} || currencyName.value != 'BYN' || !cprNdsByString.checked ) &&
             !${CommercialProposal.showPrintInvoice}
         )
      {
        printInvoiceButton.disabled = true;
        <logic:equal name="CommercialProposal" property="cpr_assemble_minsk_store" value="1">
          if (printContractButton) { printContractButton.disabled = true; }
        </logic:equal>
        <logic:equal name="CommercialProposal" property="showPrintUnit" value="true">
          if (show_unit) { show_unit.disabled = true; show_unit.checked = false; }
        </logic:equal>
        return;
      }

      printInvoiceButton.disabled = false;
      <logic:equal name="CommercialProposal" property="cpr_assemble_minsk_store" value="1">
        if (printContractButton) { printContractButton.disabled = false; }
      </logic:equal>
      <logic:equal name="CommercialProposal" property="showPrintUnit" value="true">
        if (show_unit) { show_unit.disabled = false; show_unit.checked = true; }
      </logic:equal>

      checkFieldsOnChangeCourse();
    }

    function onCurrencyChange()
    {
      showInvoiceButton();  

      doAjax({
        url:'<ctrl:rewrite action="/CommercialProposalAction" dispatch="ajaxChangeCurrency"/>',
        params:{'currencyId':currencyId.value},
        synchronous:true
      });
      if ( !cprAssembleMinskStore.checked )
      {
        recalcCommercialProposalGrid();
      }
    }

    function isContractSelected()
    {
      if (!contractorId || !contactPersonName || !contactPersonNameBtn || !addNewPersonBtn)
      {
        return;
      }
      if ( contractorId.value == "" )
      {
        contactPersonName.disabled = true;
        disableImgButton(contactPersonNameBtn, true);
        addNewPersonBtn.disabled = true;
      }
      else
      {
        contactPersonName.disabled = false;
        disableImgButton(contactPersonNameBtn, false);
        addNewPersonBtn.disabled = false;
      }
    }

    function onContractorSelect()
    {
      contactPersonName.value = "";
      contactPersonId.value = "";
      contactPersonPhone.value = "";
      contactPersonFax.value = "";
      isContractSelected();
      <logic:equal name="CommercialProposal" property="cpr_assemble_minsk_store" value="1">
        checkContractorAndConsignee();
      </logic:equal>
	    contractorAjax(contractorId.value);
    }

    function contractorAjax(id)
    {
			<logic:notEqual name="CommercialProposal" property="is_new_doc" value="true">
	      doAjax({
	        url:'<ctrl:rewrite action="/CommercialProposalAction" dispatch="ajaxGetReputation"/>',
	        params:{'contractor-id':id},
	        update:'reputation'
	      });
			</logic:notEqual>
    }

    function onConsigneeSelect()
    {
      checkContractorAndConsignee();
    }

    function checkContractorAndConsignee()
    {
      if (consigneeId.value == contractorId.value)
      {
        alert('<bean:message key="msg.commercial.errorInConsignee"/>');
        consigneeId.value = '';
        consigneeName.value = '';
      }
    }

    function generateExcelClick()
    {
      document.getElementById('for-insert').innerHTML='<iframe src="<ctrl:rewrite action="/CommercialProposalAction" dispatch="generateExcel"/>" style="display:none" />';
    }

    function askCleanReloadForm(obj)
    {
      if ( ${CommercialProposal.showAskClearTable} )
      {
        if (isUserAgree('<bean:message key="msg.commercial.clean_table"/>', true, 400, 100))
        {
          if ( obj.name == 'cprAssembleMinskStore' )
          {
            cpr_prepay_percent.value = '100';
            cpr_delay_days.value = '0';
            cpr_free_prices.value = '1';
            <logic:equal name="CommercialProposal" property="cpr_assemble_minsk_store" value="1">
              changePrepayPercent();
            </logic:equal>
          }
          submitDispatchForm("reloadWithClean");
        }
        else
        {
          obj.checked = !obj.checked;
        }
      }
      else
      {
        if ( obj.name == 'cpr_assemble_minsk_store' )
        {
          cpr_prepay_percent.value = '100';
          cpr_delay_days.value = '0';
          cpr_free_prices.value = '1';
          submitDispatchForm("reloadWithClean");
        }
      }
    }

    function freePricesCheck()
    {
      doAjax({
        url:'<ctrl:rewrite action="/CommercialProposalAction" dispatch="ajaxChangeFreePrices"/>',
        params:{'freePrices':cpr_free_prices.checked},
        synchronous:true
      });

      var params = $H(Form.serializeElements($$('#producesForAssembleMinsk input'),true));
      doAjax({
        url:'<ctrl:rewrite action="/CommercialProposalAction" dispatch="ajaxRecalcForAssembleMinskGrid"/>',
        params:params,
        synchronous:true,
        update:'producesForAssembleMinsk'
      });

      checkSalePrices();
    }

    function download(id)
    {
      document.getElementById('for-insert').innerHTML='<iframe src=\'<html:rewrite action="/AttachmentsAction.do?dispatch=download"/>&id='+id+'\' style=\'display:none\' />';
      return false;
    }

    function downloadAttachment(id)
    {
      document.getElementById('for-insert').innerHTML='<iframe src=\'<html:rewrite action="/CommercialProposalAction.do?dispatch=downloadAttachment"/>&attachmentId='+id+'\' style=\'display:none\' />';
      return false;
    }

    function switchInvoiceCP(obj)
    {
      obj.style.backgroundColor = '#d4d0c8';
      obj.style.cursor = '';
      var showField = document.getElementById(obj.id + 'Text');
      var rbControl = document.getElementById(obj.id + 'RB');
      showField.style.display = '';
      rbControl.checked = true;
      rbControl.className = "radioActive";
      var hideField;
      var otherControl;
      var otherControlRB;
      if ( obj.id.indexOf('CP') > 0 )
      {
        hideField = document.getElementById(obj.id.replace('CP', 'Invoice') + 'Text');
        otherControl = document.getElementById(obj.id.replace('CP', 'Invoice'));
        otherControlRB = document.getElementById(obj.id.replace('CP', 'Invoice') + 'RB');
        obj.className = "topActiveTD";
        otherControl.className = "bottomInactiveTD";
      }
      if ( obj.id.indexOf('Invoice') > 0 )
      {
        hideField = document.getElementById(obj.id.replace('Invoice', 'CP') + 'Text');
        otherControl = document.getElementById(obj.id.replace('Invoice', 'CP'));
        otherControlRB = document.getElementById(obj.id.replace('Invoice', 'CP') + 'RB');
        obj.className = "bottomActiveTD";
        otherControl.className = "topInactiveTD";
      }
      otherControl.style.backgroundColor = '#eeeeee';
      otherControl.style.cursor = 'pointer';
      hideField.style.display = 'none';
      otherControlRB.checked = false;
      otherControlRB.className = "radioInactive";
    }

    function getSalePriceColumn()
    {
      return $$('#producesForAssembleMinskGrid td.selected-cell input.grid-input');
    }

    function checkSalePrices()
    {
      var correctPrices = true;
      var correctPrice;
      var prices = getSalePriceColumn();
      var i = 0;
      for (; i < prices.length; i++)
      {
        correctPrice = checkSalePrice($(prices[i]));
        if ( !correctPrice )
        {
          correctPrices = false;
        }
      }

      return correctPrices;
    }

    function checkSalePrice(obj)
    {
      var salePriceStr = obj.value;
      var lpcCostOneByStr;
      var lpcPriceListByStr;
      var arrCalculatedField = obj.parentElement.parentElement.parentElement.children[0].innerHTML.split('\"');
      var calculatedFieldsStr = arrCalculatedField[1];
      var calculatedFieldStr = calculatedFieldsStr.split('|')[0];
      var checkCalculatedField = calculatedFieldsStr.split('|')[1];
      if (!cpr_free_prices.checked)
      {
        lpcCostOneByStr = obj.parentElement.previousSibling.previousSibling.previousSibling.previousSibling.innerHTML;
        lpcPriceListByStr = obj.parentElement.previousSibling.previousSibling.previousSibling.innerHTML;
      }
      else
      {
        lpcCostOneByStr = obj.parentElement.previousSibling.previousSibling.innerHTML;
        lpcPriceListByStr = obj.parentElement.nextSibling.innerHTML;
      }
      var salePrice = parseFloat(getSumForJS(salePriceStr));
      var lpcCostOneBy = parseFloat(getSumForJS(lpcCostOneByStr));
      var lpcPriceListBy = parseFloat(getSumForJS(lpcPriceListByStr));
      var correctPrice;
      if (!cpr_free_prices.checked)
      {
        correctPrice = ( salePrice > 0 && salePrice <= lpcPriceListBy );
      }
      else
      {
        var calculatedField = parseFloat(getSumForJS(calculatedFieldStr));
        correctPrice = ( salePrice > 0 && salePrice > lpcCostOneBy );
        if ( correctPrice && checkCalculatedField == '1' ) //если лежит больее 360 дней, условие может не выполнятся
        {
          correctPrice = ( salePrice > calculatedField );
        }
      }
      obj.style.backgroundColor = ( !correctPrice ) ? 'pink' : 'white';
      return correctPrice;
    }

    function changeSalePrice(obj, id)
    {
      var correctPrice = checkSalePrice(obj);
      if ( correctPrice )
      {
        doAjax({
          url:'<ctrl:rewrite action="/CommercialProposalAction" dispatch="ajaxChangeSalePriceForAssembleMinskGrid"/>',
          params:{'salePrice':obj.value, 'priceId':id},
          synchronous:true,
          update:'producesForAssembleMinsk'
        });
        recalcForAssembleMinskGrid();
      }
    }

    function recalcForAssembleMinskGrid()
    {
      var params = $H(Form.serializeElements($$('#producesForAssembleMinsk input'),true));
      doAjax({
        url:'<ctrl:rewrite action="/CommercialProposalAction" dispatch="ajaxRecalcForAssembleMinskGrid"/>',
        params:params,
        synchronous:true,
        update:'producesForAssembleMinsk'
      });

      doAjax({
        url:'<ctrl:rewrite action="/CommercialProposalAction" dispatch="ajaxGetTotal"/>',
        synchronous:true,
        update:'total'
      });

      checkSalePrices();
      calcPrepaySum();
    }

    function deleteAllProducesForAssembleMinskGrid()
    {
      doAjax({
        url:'<ctrl:rewrite action="/CommercialProposalAction" dispatch="ajaxDeleteAllProducesForAssembleMinskGrid"/>',
        synchronous:true,
        update:'producesForAssembleMinsk'
      });

      recalcForAssembleMinskGrid();
    }

    function removeFromProducesForAssembleMinskGrid(number)
    {
      doAjax({
        url:'<ctrl:rewrite action="/CommercialProposalAction" dispatch="ajaxRemoveFromProducesForAssembleMinskGrid"/>',
        params:{'number':number},
        synchronous:true,
        update:'producesForAssembleMinsk'
      });

      recalcForAssembleMinskGrid();
    }

    function deleteAllroducesCommercialProposalGrid()
    {
      doAjax({
        url:'<ctrl:rewrite action="/CommercialProposalAction" dispatch="ajaxDeleteAllProducesCommercialProposalGrid"/>',
        synchronous:true,
        update:'producesCommercialProposal'
      });

      recalcCommercialProposalGrid();
    }

    function removeFromCommercialProposalGrid(number)
    {
      doAjax({
        url:'<ctrl:rewrite action="/CommercialProposalAction" dispatch="ajaxRemoveFromCommercialProposalGrid"/>',
        params:{'number':number},
        synchronous:true,
        update:'producesCommercialProposal'
      });

      recalcCommercialProposalGrid();
    }

    function recalcCommercialProposalGrid()
    {
      var params = $H(Form.serializeElements($$('#producesCommercialProposal input'),true));
      doAjax({
        url:'<ctrl:rewrite action="/CommercialProposalAction" dispatch="ajaxRecalcCommercialProposalGrid"/>',
        params:params,
        synchronous:true,
        update:'producesCommercialProposal'
      });
    }

    function ndsByStringOnClick()
    {
      doAjax({
        url:'<ctrl:rewrite action="/CommercialProposalAction" dispatch="ajaxChangeNDSByString"/>',
        params:{'ndsByString':cprNdsByString.checked},
        synchronous:true
      });
      
      if ( cprAssembleMinskStore.checked )
      {
        recalcForAssembleMinskGrid();
      }
      else
      {
        recalcCommercialProposalGrid();
      }
    }

    function reverseCalcOnClick()
    {
      doAjax({
        url:'<ctrl:rewrite action="/CommercialProposalAction" dispatch="ajaxChangeReverseCalc"/>',
        params:{'reverseCalc':cpr_reverse_calc.checked},
        synchronous:true,
        okCallback:function()
        {
          if ( cpr_reverse_calc.checked )
          {
            cpr_reverse_calc.value = '1';
          }
          else
          {
            cpr_reverse_calc.value = '';
          }
        }
      });
      recalcCommercialProposalGrid();
    }

    function doNotCalculateNettoOnClick()
    {
      cpr_donot_calculate_netto.value = donot_calculate_netto.checked ? '1' : '';
      calculate_netto.checked = !donot_calculate_netto.checked;
      checkCalculated();
    }

    function calculateNettoOnClick()
    {
      donot_calculate_netto.checked = !calculate_netto.checked;
      cpr_donot_calculate_netto.value = donot_calculate_netto.checked ? '1' : '';
      checkCalculated();
    }

    function checkCalculated()
    {
      var reload = true;
      if ( calculate_netto.checked )
      {
        if ( ${CommercialProposal.showAskClearTable} )
        {
          if (!isUserAgree('<bean:message key="msg.commercial.lost_data"/>', true, 400, 100))
          {
            reload = false;
            calculate_netto.checked = false;
            donot_calculate_netto.checked = !calculate_netto.checked;
            cpr_donot_calculate_netto.value = donot_calculate_netto.checked ? '1' : '';
          }
        }
      }
      if (reload)
      {
        doAjax({
          url:'<ctrl:rewrite action="/CommercialProposalAction" dispatch="ajaxChangeCalculate"/>',
          params:{'calculateNtto':calculate_netto.checked},
          synchronous:true
        });

        recalcCommercialProposalGrid();
      }
    }

    function changeCourse(obj)
    {
      doAjax({
        url:'<ctrl:rewrite action="/CommercialProposalAction" dispatch="ajaxChangeCourse"/>',
        params:{'course':obj.value},
        synchronous:true
      });
      recalcCommercialProposalGrid();

      checkFieldsOnChangeCourse();
    }

    function changeNDS(obj)
    {
      doAjax({
        url:'<ctrl:rewrite action="/CommercialProposalAction" dispatch="ajaxChangeNDS"/>',
        params:{'nds':obj.value},
        synchronous:true
      });
      recalcCommercialProposalGrid();
    }

    <logic:equal name="CommercialProposal" property="cpr_assemble_minsk_store" value="1">
      function changeGuarantyInMonth()
      {
        var guarantyInMonth = parseInt(document.getElementById('cpr_guaranty_in_month').value);
        if ( guarantyInMonth < 0 )
        {
          alert('<bean:message key="error.commercial.guarantyInMonthError"/>');
          document.getElementById('cpr_guaranty_in_month').value = '0';
        }
      }

      function changePrepayPercent()
      {
        var prepayPercent = parseFloat(getSumForJS(cpr_prepay_percent.value));
        if ( prepayPercent > 100 || prepayPercent < 0 )
        {
          alert('<bean:message key="msg.commercial.incorrectPercentValue"/>');
          cpr_prepay_percent.value = 100;
          return;
        }
        if ( prepayPercent == 100 )
        {
          cpr_delay_days.disabled = true;
          cpr_delay_days.value = '0';
        }
        else
        {
          cpr_delay_days.disabled = false;
        }

        calcPrepaySum();
        showCorrectTermTypeText();
      }

      function changePrepaySum()
      {
        var prepaySum = parseFloat(getSumForJS(cpr_prepay_sum.value));
        var totalSum = parseFloat(getSumForJS(total.innerHTML));
        var tmpPercent = prepaySum / totalSum * 100;
        tmpPercent = Math.round(tmpPercent * 100) / 100;
        cpr_prepay_percent.value = tmpPercent;
        cpr_prepay_percent.value = cpr_prepay_percent.value.replace('.', ',');

        showCorrectTermTypeText();
      }

      function calcPrepaySum()
      {
        var prepayPercent = parseFloat(getSumForJS(cpr_prepay_percent.value));
        var totalSum = parseFloat(getSumForJS(total.innerHTML));
        var tmpSum = totalSum * prepayPercent / 100;
        tmpSum = Math.round(tmpSum * 100) / 100;
        cpr_prepay_sum.value = tmpSum;
        cpr_prepay_sum.value = cpr_prepay_sum.value.replace('.', ',');
      }

      function changeDelayDays()
      {
        var prepayPercent = parseFloat(getSumForJS(cpr_prepay_percent.value));
        var delayDays = parseFloat(getSumForJS(cpr_delay_days.value));
        if ( prepayPercent <= 100 && ( delayDays < 0 || delayDays > 60 ) )
        {
          alert('<bean:message key="msg.commercial.incorrectDelayDaysValue"/>');
          cpr_delay_days.value = '0';
        }
      }

      function providerDeliveryOnClick()
      {
        providerDeliveryAddress.disabled = !providerDelivery.checked;
        deliveryCountDay.disabled = !providerDelivery.checked;
        if (!providerDelivery.checked)
        {
          providerDeliveryAddress.value = '';
          deliveryCountDay.value = '';
        }
      }

      function deliveryCountDayOnChange()
      {
        deliveryCountDay.style.backgroundColor = deliveryCountDayCorrect() ? 'white' : 'pink';
      }

      function showCorrectTermTypeText()
      {
        var prepayPercent = parseFloat(getSumForJS(cpr_prepay_percent.value));
        if ( prepayPercent <= 100 && prepayPercent > 0 )
        {
          document.getElementById('idTermTypeText').innerHTML = '<bean:message key="CrmDeliveryTerm.afterPrepaid"/>';
        }
        else
        {
          document.getElementById('idTermTypeText').innerHTML = '<bean:message key="CrmDeliveryTerm.afterSignContract"/>';
        }
      }
    </logic:equal>

    function deliveryCountDayCorrect()
    {
      <logic:equal name="CommercialProposal" property="cpr_assemble_minsk_store" value="1">
        if ( deliveryCountDay.value != '' )
        {
          var dayCountLimit = parseInt(${CommercialProposal.dayCountWaitReservedForShipping});
          var dayCount = parseInt(deliveryCountDay.value);
          if ( isNaN(dayCount) || dayCount > dayCountLimit )
            return false;
        }
      </logic:equal>

      return true;
    }
    
    function checkAcceptDate()
    {
      <logic:equal name="CommercialProposal" property="cpr_assemble_minsk_store" value="1">
        if ( cprDateAccept.value != '' )
        {
          var finalDate = getDateForJS(cprFinalDate.value);
          var cprDate = getDateForJS(cpr_date.value);
          var acceptDate = getDateForJS(cprDateAccept.value);
          if ( acceptDate > finalDate || acceptDate < cprDate )
          {
            cprDateAccept.style.backgroundColor = 'pink';
            return false;
          }

          cprDateAccept.style.backgroundColor = 'white';
        }
      </logic:equal>

      return true;
    }

    function checkProviderDelivery()
    {
      <logic:equal name="CommercialProposal" property="cpr_assemble_minsk_store" value="1">
        if (providerDelivery.checked)
        {
          return (
                  providerDeliveryAddress.value != '' &&
                  deliveryCountDay.value != '' &&
                  deliveryCountDay.value.length <= 2
                 );
        }
      </logic:equal>

      return true;
    }

    function processClick()
    {
      var course = parseFloat(getSumForJS(cprCourseFormatted.value));
      if (
          ( currencyName.value == currencyNameTable.value && course != 1 ) ||
          ( currencyName.value != currencyNameTable.value && course == 1 )
         )
      {
        alert('<bean:message key="msg.commercial.incorrectCourse"/>');
        return;
      }

      var correctPrices = checkSalePrices();
      if ( !correctPrices )
      {
        alert('<bean:message key="msg.commercial.incorrectSalePrices"/>');
        return;
      }

      var correctAcceptDate = checkAcceptDate();
      if ( !correctAcceptDate )
      {
        alert('<bean:message key="msg.commercial.incorrectAcceptDate"/>');
        return;
      }

      var correctProviderDelivery = checkProviderDelivery();
      if ( !correctProviderDelivery )
      {
        alert('<bean:message key="msg.commercial.incorrectProviderDeliverySection"/>');
        return;
      }

      var correctDeliveryCountDay = deliveryCountDayCorrect();
      if ( !correctDeliveryCountDay )
      {
        alert('<bean:message key="msg.commercial.incorrectDeliveryCountDay"/>');
        return;
      }

      submitDispatchForm("process");
    }

    function printClick()
    {
      <logic:equal name="CommercialProposal" property="is_new_doc" value="true">
        if (!isUserAgree('<bean:message key="msg.commercial.notSavedDoc"/>', true, 400, 100))
        {
          return;
        }
        printMode.value = 'print';
        processClick();
      </logic:equal>

      <logic:notEqual name="CommercialProposal" property="is_new_doc" value="true">
        submitDispatchForm("print");
      </logic:notEqual>
    }

    function printInvoiceClick()
    {
      <logic:equal name="CommercialProposal" property="is_new_doc" value="true">
        if (!isUserAgree('<bean:message key="msg.commercial.notSavedDoc"/>', true, 400, 100))
        {
          return;
        }
        printMode.value = 'printInvoice';
        processClick();
      </logic:equal>
      <logic:notEqual name="CommercialProposal" property="is_new_doc" value="true">
        submitDispatchForm("printInvoice");
      </logic:notEqual>
    }

    function printContractClick()
    {
      <logic:equal name="CommercialProposal" property="is_new_doc" value="true">
        if (!isUserAgree('<bean:message key="msg.commercial.notSavedDoc"/>', true, 400, 100))
        {
          return;
        }
        printMode.value = 'printContract';
        processClick();
      </logic:equal>
      <logic:notEqual name="CommercialProposal" property="is_new_doc" value="true">
        submitDispatchForm("printContract");
      </logic:notEqual>
    }
  </script>

  <logic:equal name="CommercialProposal" property="print" value="true">
    <script language="JScript" type="text/javascript" >
	    document.getElementById('for-insert').innerHTML='<iframe src="<ctrl:rewrite action="CommercialProposalPrintAction"/>" style="display:none" />';
    </script>
  </logic:equal>
  <logic:equal name="CommercialProposal" property="printInvoice" value="true">
    <script language="JScript" type="text/javascript" >
	    document.getElementById('for-insert').innerHTML='<iframe src="<ctrl:rewrite action="CommercialProposalPrintAction" scriptUrl="invoice=true" />" style="display:none" />';
    </script>
  </logic:equal>
  <logic:equal name="CommercialProposal" property="printContract" value="true">
    <script language="JScript" type="text/javascript" >
	    document.getElementById('for-insert').innerHTML='<iframe src="<ctrl:rewrite action="CommercialProposalPrintAction" scriptUrl="contract=true" />" style="display:none" />';
    </script>
  </logic:equal>
