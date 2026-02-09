<%@ page import="org.apache.struts.taglib.html.Constants" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>

<table cellspacing="2" width="940px">
  <tr>
    <td>
      <div class="gridBack">
        <table width='100%' cellSpacing=1 cellPadding=2 border=0 class="grid">
          <tbody>
            <tr>
              <th class="table-header" align="cente"><bean:message key="ContractorRequest.visits"/></th>
              <th class="table-header" align="cente"><bean:message key="ContractorRequest.workType"/></th>
              <th class="table-header" align="cente"><bean:message key="ContractorRequest.docPack"/></th>
              <logic:equal name="ContractorRequest" property="showPNP" value="true">
                  <th class="table-header" align="cente"><bean:message key="ContractorRequest.deliveredPNP"/></th>
              </logic:equal>
              <logic:equal name="ContractorRequest" property="showPNP" value="false">
                  <th class="table-header" align="cente"><bean:message key="ContractorRequest.delivered"/></th>
              </logic:equal>
            </tr>
            <logic:iterate id="val" indexId="id" name="ContractorRequest" property="contractorRequestPrints">
              <tr>
                <td class=txt bgcolor=#eeeeee width="60px">
                  <bean:message key="ContractorRequest.visit"/> ${id + 1}<br><br>
                  <logic:equal value="0" name="id">
                    <font size="1"><bean:message key="ContractorRequest.addVisit"/></font> <ctrl:ubutton type="script" action="/ContractorRequest.fakeForAdd" styleClass="width165"
                                  onclick="addToGrid()"
                                  style="width:19px;background-color:#eeeeee;border:0px;font-weight:bold;color:green;font-size:14px"
                                  showWait="false">
                      <bean:message key="button.plus"/>
                    </ctrl:ubutton>
                  </logic:equal>
                  <logic:notEqual value="0" name="id">
                    <font size="1"><bean:message key="ContractorRequest.delVisit"/></font> <ctrl:ubutton type="script" action="/ContractorRequest.fakeForDelete" styleClass="width165"
                                  onclick="removeFromGrid(${id})"
                                  style="width:19px;background-color:#eeeeee;border:0px;font-weight:bold;color:red;font-size:16px"
                                  showWait="false">
                      <bean:message key="button.minus"/>
                    </ctrl:ubutton>
                  </logic:notEqual>
                </td>
                <logic:equal name="ContractorRequest" property="showGuaranty" value="true">
                  <td class=txt bgcolor=#eeeeee><bean:message key="ctr_request_type_list.guarantee"/></td>
                  <logic:equal name="ContractorRequest" property="sellerType0" value="1"> <!-- Все что не ИП "ЛинтераТехСервис" и Прочие -->
                    <td class=txt bgcolor=#eeeeee>
                      <table cellSpacing=0 cellPadding=0 border=0>
                        <tr valign="bottom">
                          <td bgcolor=#eeeeee>
                            &nbsp;
                            <ctrl:date property="contractorRequestPrints[${id}].crp_reclamation_date_formatted"
                                       style="width:125px;" showHelp="false" readonly="false"
                                       onchange="checkPrintDates()" afterSelect="checkPrintDates"/>
                            <br>
                            &nbsp;
                            <input type='button' id='printReclamationActBtn${id}' onclick='printReclamationAct(${id})' class='width145'  style='height:35px;' value='<bean:message key="button.printReclamationAct"/>'/>
                          </td>
                          <td bgcolor=#eeeeee>
                            &nbsp;
                            <ctrl:date property="contractorRequestPrints[${id}].crp_lintera_request_date_formatted"
                                       style="width:125px;" showHelp="false" readonly="false"
                                       onchange="checkPrintDates()" afterSelect="checkPrintDates"/>
                            <br>
                            &nbsp;
                            <input type='button' id='printSellerRequestBtn${id}' onclick='printSellerRequest(${id})' class='width145'  style='height:35px;' value='<bean:message key="button.printSellerRequest"/>'/>
                          </td>
                          <td bgcolor=#eeeeee>
                            &nbsp;
                            <ctrl:checkbox property="contractorRequestPrints[${id}].crp_no_defect_act_checkbox"
                                           styleClass="checkbox" value="1" showHelp="false" onclick="changeAssociatedHidden(this);"/> <bean:message key="ContractorRequest.crp_no_defect_act"/><br>
                            <ctrl:hidden property="contractorRequestPrints[${id}].crp_no_defect_act"/>
                            &nbsp;
                            <input type='button' id='printEnumerationWorkBtn${id}' onclick='printEnumerationWork(${id})' class='width200' value='<bean:message key="button.printEnumerationWorkDefectAct"/>'/>
                          </td>
                          <td bgcolor=#eeeeee>
                            &nbsp;
                            <ctrl:date property="contractorRequestPrints[${id}].crp_lintera_agreement_date_formatted"
                                       style="width:125px;" showHelp="false" readonly="false"
                                       onchange="checkPrintDates()" afterSelect="checkPrintDates"/><br>
                            &nbsp;
                            <input type='button' id='dataForSellerAgreementBtn${id}' onclick='dataForSellerAgreement(${id})' class='width145' value='<bean:message key="button.printSellerAgreement"/>'/>
                          </td>
                        </tr>
                      </table>
                    </td>
                  </logic:equal>
                  <logic:equal name="ContractorRequest" property="seller.id" value="1"> <!-- ИП "ЛинтераТехСервис" -->
                    <td class=txt bgcolor=#eeeeee width="230px" align="center">
                      <table cellSpacing=0 cellPadding=0 border=0>
                        <tr valign="bottom">
                          <td bgcolor=#eeeeee>
                            &nbsp;
                            <ctrl:date property="contractorRequestPrints[${id}].crp_reclamation_date_formatted"
                                       style="width:125px;" showHelp="false" readonly="false"
                                       onchange="checkPrintDates()" afterSelect="checkPrintDates"/>
                            <br>
                            &nbsp;
                            <input type='button' id='printReclamationActBtn${id}' onclick='printReclamationAct(${id})' class='width145'  style='height:35px;' value='<bean:message key="button.printReclamationAct"/>'/>
                          </td>
                          <td bgcolor=#eeeeee>
                            &nbsp;
                            <ctrl:date property="contractorRequestPrints[${id}].crp_lintera_request_date_formatted"
                                       style="width:125px;" showHelp="false" readonly="false"
                                       onchange="checkPrintDates()" afterSelect="checkPrintDates"/>
                            <br>
                            &nbsp;
                            <input type='button' id='printSellerRequestBtn${id}' onclick='printSellerRequest(${id})' class='width145'  style='height:35px;' value='<bean:message key="button.printSellerRequest"/>'/>
                          </td>
                          <td bgcolor=#eeeeee>
                            &nbsp;
                            <ctrl:checkbox property="contractorRequestPrints[${id}].crp_no_reclamation_act_checkbox"
                                           styleClass="checkbox" value="1" showHelp="false" onclick="changeAssociatedHidden(this);"/> <bean:message key="ContractorRequest.crp_no_reclamation_act"/><br>
                            <ctrl:hidden property="contractorRequestPrints[${id}].crp_no_reclamation_act"/>
                            &nbsp;
                            <input type='button' id='printEnumerationWorkBtn${id}' onclick='printEnumerationWork(${id})' class='width200' value='<bean:message key="button.printEnumerationWorkReclamationAct"/>'/>
                          </td>
                          <td bgcolor=#eeeeee>
                            &nbsp;
                            <ctrl:date property="contractorRequestPrints[${id}].crp_lintera_agreement_date_formatted"
                                       style="width:125px;" showHelp="false" readonly="false"
                                       onchange="checkPrintDates()" afterSelect="checkPrintDates"/><br>
                            &nbsp;
                            <input type='button' id='dataForSellerAgreementBtn${id}' onclick='dataForSellerAgreement(${id})' class='width145' value='<bean:message key="button.printSellerAgreement"/>'/>
                          </td>
                        </tr>
                      </table>
                    </td>
                  </logic:equal>
                  <logic:equal name="ContractorRequest" property="unknownSeller" value="1">
                    <td class=txt bgcolor=#eeeeee width="230px" align="center">
                      <ctrl:checkbox property="contractorRequestPrints[${id}].crp_no_defect_act_checkbox"
                                     styleClass="checkbox" value="1" showHelp="false" onclick="changeAssociatedHidden(this);"/> <bean:message key="ContractorRequest.crp_no_defect_act"/><br>
                      <ctrl:hidden property="contractorRequestPrints[${id}].crp_no_defect_act"/>
                      &nbsp;
                      <input type='button' id='printEnumerationWorkBtn${id}' onclick='printEnumerationWork(${id})' class='width200' value='<bean:message key="button.printEnumerationWorkDefectAct"/>'/>
                    </td>
                  </logic:equal>
                </logic:equal>
                <logic:equal name="ContractorRequest" property="showService" value="true">
                  <td class=txt bgcolor=#eeeeee width="200px"><bean:message key="ctr_request_type_list.service"/></td>
                  <td class=txt bgcolor=#eeeeee width="230px" align="center">
                    <ctrl:checkbox property="contractorRequestPrints[${id}].crp_no_defect_act_checkbox"
                                   styleClass="checkbox" value="1" showHelp="false" onclick="changeAssociatedHidden(this);"/> <bean:message key="ContractorRequest.crp_no_defect_act"/><br>
                    <ctrl:hidden property="contractorRequestPrints[${id}].crp_no_defect_act"/>
                    &nbsp;
                    <input type='button' id='printGSActBtn${id}' onclick='printGSAct(${id})' class='width200' value='<bean:message key="button.printActExecutedWorkDefectAct"/>'/>
                  </td>
                </logic:equal>
                <logic:equal name="ContractorRequest" property="showPNP" value="true">
                      <td class=txt bgcolor=#eeeeee width="200px"><bean:message key="ctr_request_type_list.pnp"/></td>
                      <td class=txt bgcolor=#eeeeee width="230px" align="center">
                          <input type='button' id='printPNPTimesheetBtn${id}' onclick='printPNPTimesheet(${id})' class='width200' value='<bean:message key="button.PNPTimeRecord"/>'/>
                      </td>
                </logic:equal>

                <td class=txt bgcolor=#eeeeee align="center">
                  <ctrl:checkbox property="contractorRequestPrints[${id}].crp_deliver_checkbox" styleClass="checkbox"
                                 value="1" showHelp="false" onclick="changeDeliver(this, ${id});"/>
                  <ctrl:hidden property="contractorRequestPrints[${id}].crp_deliver"/>
                </td>
              </tr>
            </logic:iterate>
          </tbody>
        </table>
      </div>
    </td>
  </tr>
</table>

