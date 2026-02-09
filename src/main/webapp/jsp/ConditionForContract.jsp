  <%@ taglib uri="/tags/struts-bean" prefix="bean" %>
  <%@ taglib uri="/tags/struts-html" prefix="html" %>
  <%@ taglib uri="/tags/struts-logic" prefix="logic" %>
  <%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
  <%@ taglib uri="/tags/html-grid" prefix="grid" %>

  <div style="display:none" id="resultMsg"></div>

  <div id='for-insert'></div>

  <ctrl:form readonly="${ConditionForContract.formReadOnly}">
  <ctrl:hidden property="cfc_id"/>
  <ctrl:hidden property="is_new_doc"/>
  <ctrl:hidden property="usr_date_create"/>
  <ctrl:hidden property="usr_date_edit"/>
  <ctrl:hidden property="usr_date_place"/>
  <ctrl:hidden property="usr_date_execute"/>
  <ctrl:hidden property="createUser.usr_id"/>
  <ctrl:hidden property="editUser.usr_id"/>
  <ctrl:hidden property="placeUser.usr_id"/>
  <ctrl:hidden property="executeUser.usr_id"/>
  <ctrl:hidden property="annulUser.usr_id"/>
  <ctrl:hidden property="conFinalDate"/>
  <ctrl:hidden property="cfc_execute"/>
  <ctrl:hidden property="cfc_check_price"/>
  <ctrl:hidden property="cfc_check_price_date"/>
  <ctrl:hidden property="usr_id_check_price"/>
  <table class=formBackTop align="center" width="99%">
    <tr>
      <td >
        <table cellspacing="0" width="100%" >
        <logic:notEqual name="ConditionForContract" property="is_new_doc" value="true">
          <tr>
            <td>
              <table width="100%">
                <tr>
                  <td width="10%"><bean:message key="ConditionForContract.create"/></td>
                  <td width="40%"><ctrl:write name="ConditionForContract" property="usr_date_create"/> <ctrl:write name="ConditionForContract" property="createUser.userFullName"/> </td>
                  <td width="10%"><bean:message key="ConditionForContract.edit"/></td>
                  <td width="40%"><ctrl:write name="ConditionForContract" property="usr_date_edit"/> <ctrl:write name="ConditionForContract" property="editUser.userFullName"/></td>
                </tr>
              </table>
            </td>
          </tr>
        </logic:notEqual>
        <tr>
          <td>
            <table width="100%">
              <tr>
                <logic:notEqual name="ConditionForContract" property="placeUser.usr_id" value="">
                  <td width="10%"><bean:message key="ConditionForContract.place"/></td>
                  <td width="40%"><ctrl:write name="ConditionForContract" property="usr_date_place"/> <ctrl:write name="ConditionForContract" property="placeUser.userFullName"/> </td>
                </logic:notEqual>
                <logic:notEqual name="ConditionForContract" property="executeUser.usr_id" value="">
                  <td width="10%"><bean:message key="ConditionForContract.execute"/></td>
                  <td width="40%"><ctrl:write name="ConditionForContract" property="usr_date_execute"/> <ctrl:write name="ConditionForContract" property="executeUser.userFullName"/> </td>
                </logic:notEqual>
                <logic:notEqual name="ConditionForContract" property="annulUser.usr_id" value="">
                  <td width="10%"><bean:message key="ConditionForContract.annul"/></td>
                  <td width="40%"><ctrl:write name="ConditionForContract" property="usr_date_annul"/> <ctrl:write name="ConditionForContract" property="annulUser.userFullName"/> </td>
                </logic:notEqual>
                <logic:equal name="ConditionForContract" property="placeUser.usr_id" value="">
                  <td width="10%"></td>
                  <td width="40%"></td>
                </logic:equal>
                <logic:equal name="ConditionForContract" property="executeUser.usr_id" value="">
                  <logic:equal name="ConditionForContract" property="annulUser.usr_id" value="">
                    <td width="10%"></td>
                    <td width="40%"></td>
                  </logic:equal>
                </logic:equal>
              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td colspan="20">
            <table width="100%">
              <tr>
	              <td width="10%"><bean:message key="ConditionForContract.seller"/></td>
                <td>
	                <ctrl:serverList property="seller.name" idName="seller.id" action="/SellersListAction" selectOnly="true" style="width:230px;" callback="submitReloadFormSeller"/>
                </td>
              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td>
            <table width="100%" border="0">
              <tr>
                <td width="10%"><bean:message key="ConditionForContract.contractor"/></td>
                <td>
                  <table border="0" cellSpacing="0" cellPadding="0">
                    <tr>
                      <td>
                        <ctrl:serverList property="contractor.name" idName="contractor.id"
                                         action="/ContractorsListAction" filter="filter"
                                         callback="submitReloadFormContractor" style="width:230px;"/>
                      </td>
                      <td>&nbsp;</td>
                      <td>
                        <ctrl:ubutton type="submit" dispatch="newContractor" styleClass="width120">
                          <bean:message key="button.addNew"/>
                        </ctrl:ubutton>
                        <ctrl:ubutton type="submit" dispatch="editContractor" scriptUrl="ctr_id=$(contractor.id)"
                                       readonly="${ConditionForContract.readOnlyAddPerson}" styleClass="width165">
                          <bean:message key="button.editSelected"/>
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
        <logic:notEqual name="ConditionForContract" property="contractor.id" value="">
          <tr>
            <td>
              <table width="100%">
                <tr>
                  <td>
                    <bean:message key="ConditionForContract.contractor_full"/>&nbsp;
                    <ctrl:write name="ConditionForContract" property="contractor.fullname"/>
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
                    <bean:message key="ConditionForContract.juridical_address"/>&nbsp;
                    <ctrl:write name="ConditionForContract" property="contractor.address"/>
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
                    <bean:message key="ConditionForContract.bank_address"/>&nbsp;
                    <ctrl:write name="ConditionForContract" property="contractor.bank_props"/>
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
                    <bean:message key="ConditionForContract.account1"/>&nbsp;
                    ${ConditionForContract.contractor.accountsBYNFormatted}&nbsp;&nbsp;
                    <bean:message key="ConditionForContract.account_val"/>&nbsp;
                    ${ConditionForContract.contractor.accountsOtherFormatted}
                  </td>
                </tr>
              </table>
            </td>
          </tr>
          <tr>
            <td colspan="20">
              <table width="100%">
                <tr>
                  <td>
                    <bean:message key="ConditionForContract.unp"/>&nbsp;
                    ${ConditionForContract.contractor.unpFormatted}&nbsp;&nbsp;&nbsp;&nbsp;
                    <bean:message key="ConditionForContract.okpo"/>&nbsp;
                    ${ConditionForContract.contractor.okpoFormatted}&nbsp;&nbsp;&nbsp;&nbsp;
                    <bean:message key="ConditionForContract.phone"/>&nbsp;
                    <ctrl:write name="ConditionForContract" property="contractor.phone"/>&nbsp;&nbsp;&nbsp;&nbsp;
                    <bean:message key="ConditionForContract.fax"/>&nbsp;
                    <ctrl:write name="ConditionForContract" property="contractor.fax"/>&nbsp;&nbsp;&nbsp;&nbsp;
                    <bean:message key="ConditionForContract.email"/>&nbsp;
	                  <a href='mailto:<ctrl:write name="ConditionForContract" property="contractor.email"/>'><ctrl:write name="ConditionForContract" property="contractor.email"/></a>
                  </td>
                </tr>
              </table>
            </td>
          </tr>
        </logic:notEqual>
        <tr>
          <td colspan="20">
            <table width="100%">
              <tr>
                <td>
                  <bean:message key="ConditionForContract.cfc_doc_type"/>&nbsp;&nbsp;&nbsp;&nbsp;
                  <ctrl:checkbox property="cfc_doc_type1" styleClass="checkbox" value="1" onclick="cfcDocType1OnClick()"/>&nbsp;
                  <bean:message key="ConditionForContract.cfc_doc_type1"/>&nbsp;&nbsp;&nbsp;&nbsp;
                  <ctrl:checkbox property="cfc_doc_type2" styleClass="checkbox" value="1" onclick="cfcDocType2OnClick()"/>&nbsp;
                  <bean:message key="ConditionForContract.cfc_doc_type2"/>
                </td>
              </tr>
            </table>
          </td>
        </tr>
        <logic:notEqual name="ConditionForContract" property="cfc_doc_type1" value="">
          <tr>
            <td>
              <table width="100%">
                <tr>
                  <td width="190px"><bean:message key="ConditionForContract.cfc_con_number_txt"/></td>
                  <td width="30%"><ctrl:text property="cfc_con_number_txt" style="width:350px;"/></td>
                  <td width="190px" align="right">
                    <table border="0" cellSpacing="0" cellPadding="0">
                      <tr>
                        <td width="170px">
                          <bean:message key="ConditionForContract.cfc_con_date"/>
                        </td>
                      </tr>
                    </table>
                  </td>
                  <td><ctrl:date property="cfc_con_date" style="width:200px;"/></td>
                </tr>
              </table>
            </td>
          </tr>
          <tr>
            <td>
              <table width="100%">
                <tr>
                  <td width="190px"><bean:message key="ConditionForContract.currency"/></td>
                  <td width="30%">
                    <ctrl:serverList property="currency.name" idName="currency.id" action="/CurrenciesListAction"
                                     selectOnly="true" style="width:110px;" callback="submitReloadForm"/>
                  </td>
                  <td width="190px" align="right">
                    <table border="0" cellSpacing="0" cellPadding="0">
                      <tr>
                        <td width="170px">
                          <bean:message key="ConditionForContract.purchase_purpose"/>
                        </td>
                      </tr>
                    </table>
                  </td>
                  <td>
                    <table width="100%">
                      <tr>
                        <td width="240px">
                          <ctrl:serverList property="purchasePurpose.name" idName="purchasePurpose.id"
                                           action="/PurchasePurposesListAction" selectOnly="true" style="width:200px;"/>
                        </td>
                        <logic:equal name="ConditionForContract" property="showForAdmin" value="true">
                          <td>
                            <ctrl:ubutton type="submit" dispatch="editPurchasePurposes" styleClass="width145">
                              <bean:message key="button.editList"/>
                            </ctrl:ubutton>
                          </td>
                        </logic:equal>
                      </tr>
                    </table>
                  </td>
                </tr>
              </table>
            </td>
          </tr>
        </logic:notEqual>
        <logic:notEqual name="ConditionForContract" property="cfc_doc_type2" value="">
          <tr>
            <td>
              <table width="100%">
                <tr>
                  <td width="190px"><bean:message key="ConditionForContract.contract"/></td>
                  <td width="30%">
                    <ctrl:serverList property="contractNumberWithDate" idName="contract.con_id"
                                     action="/ContractsDepFromContractorListAction" scriptUrl="ctr_id=$(contractor.id)&allCon=1&onlyReusable=1&con_seller=${ConditionForContract.seller.id}"
                                     style="width:325px;" callback="onChangeContract" filter="filter"/>
                  </td>
                  <td width="190px" align="right">
                    <table border="0" cellSpacing="0" cellPadding="0">
                      <tr>
                        <td width="170px">
                          <bean:message key="ConditionForContract.currency"/>
                        </td>
                      </tr>
                    </table>
                  </td>
                  <td>
                    <ctrl:serverList property="currency.name" idName="currency.id" action="/CurrenciesListAction"
                                     selectOnly="true" style="width:110px;" readonly="true"/>
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
                    <b><span style="color:green" id="idReusableContract"><ctrl:write name="ConditionForContract" property="contract.numberWithDateAndReusable"/></span></b>
                    <b><span style="color:red" id="idAnnulContract"><ctrl:write name="ConditionForContract" property="contract.annulStr"/></span></b>
                  </td>
                </tr>
              </table>
            </td>
          </tr>
          <tr>
            <td>
              <table width="100%">
                <tr>
                  <td width="50%">
                    <table border="0" cellSpacing="0" cellPadding="0">
                      <tr>
                        <td width="285px"><bean:message key="ConditionForContract.spc_numbers"/></td>
                        <td><span id="spcNumbers"><ctrl:write name="ConditionForContract" property="spc_numbers"/></span></td>
                      </tr>
                    </table>
                  </td>
                  <td>
                    <table border="0" cellSpacing="0" cellPadding="0">
                      <tr>
                        <td width="300px"><bean:message key="ConditionForContract.cfc_spc_numbers"/>&nbsp;<ctrl:help htmlName="ConditionForContract.cfc_spc_numbers_help"/><bean:message key="Common.colon"/></td>
                        <td><span id="cfcSpcNumbers"><ctrl:write name="ConditionForContract" property="cfc_spc_numbers"/></span></td>
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
                  <td width="190px"><bean:message key="ConditionForContract.cfc_spc_number_txt"/></td>
                  <td width="30%"><ctrl:text property="cfc_spc_number_txt" style="width:350px;"/></td>
                  <td width="190px" align="right">
                    <table border="0" cellSpacing="0" cellPadding="0">
                      <tr>
                        <td width="170px">
                          <bean:message key="ConditionForContract.cfc_spc_date"/>
                        </td>
                      </tr>
                    </table>
                  </td>
                  <td><ctrl:date property="cfc_spc_date" style="width:200px;"/></td>
                </tr>
              </table>
            </td>
          </tr>
          <tr>
            <td>
              <table width="100%">
                <tr>
                  <td width="190px"><bean:message key="ConditionForContract.purchase_purpose"/></td>
                  <td>
                    <table width="100%">
                      <tr>
                        <td width="210px">
                          <ctrl:serverList property="purchasePurpose.name" idName="purchasePurpose.id"
                                           action="/PurchasePurposesListAction" selectOnly="true" style="width:170px;"/>
                        </td>
                        <logic:equal name="ConditionForContract" property="showForAdmin" value="true">
                          <td>
                            <ctrl:ubutton type="submit" dispatch="editPurchasePurposes" styleClass="width145">
                              <bean:message key="button.editList"/>
                            </ctrl:ubutton>
                          </td>
                        </logic:equal>
                      </tr>
                    </table>
                  </td>
                </tr>
              </table>
            </td>
          </tr>
        </logic:notEqual>
        <logic:equal name="ConditionForContract" property="showFields" value="true">
          <tr>
            <td>
              <table width="100%">
                <tr>
                  <td width="190px">
                    <bean:message key="ConditionForContract.cfc_pay_cond"/><br>
                    <span style="color:#008000"><bean:message key="ConditionForContract.cfc_pay_cond_legend1"/></span><br>
                    <span style="color:#008000"><bean:message key="ConditionForContract.cfc_pay_cond_legend2"/></span>
                  </td>
                  <td width="30%"><ctrl:textarea property="cfc_pay_cond" style="width:350px;height:155px;"/></td>
                  <td width="190px" align="right">
                    <table border="0" cellSpacing="0" cellPadding="0">
                      <tr>
                        <td width="170px">
                          <bean:message key="ConditionForContract.cfc_delivery_cond"/><br>
                          <span style="color:#008000"><bean:message key="ConditionForContract.cfc_delivery_cond_legend"/></span>
                        </td>
                      </tr>
                    </table>
                  </td>
                  <td><ctrl:textarea property="cfc_delivery_cond" style="width:350px;height:155px;"/></td>
                </tr>
              </table>
            </td>
          </tr>

          <logic:equal name="ConditionForContract" property="seller.id" value="0">
            <tr>
              <td>
                <table width="100%">
                  <tr>
                    <td width="190px">
                      <bean:message key="ConditionForContract.cfc_custom_point"/>
                    </td>
                    <td><ctrl:textarea property="cfc_custom_point" style="width:350px;height:155px;"/></td>
                  </tr>
                </table>
              </td>
            </tr>
          </logic:equal>

          <tr>
            <td>
              <table width="100%">
                <tr>
                  <td width="190px"><bean:message key="ConditionForContract.cfc_guarantee_cond"/></td>
                  <td width="30%"><ctrl:textarea property="cfc_guarantee_cond" style="width:350px;height:50px;"/></td>
                  <td width="190px" align="right">
                    <table border="0" cellSpacing="0" cellPadding="0">
                      <tr>
                        <td width="170px">
                          <bean:message key="ConditionForContract.cfc_montage_cond"/>
                        </td>
                      </tr>
                    </table>
                  </td>
                  <td><ctrl:textarea property="cfc_montage_cond" style="width:350px;height:50px;"/></td>
                </tr>
              </table>
            </td>
          </tr>
          <logic:equal name="ConditionForContract" property="cfc_doc_type2" value="">
            <tr>
              <td>
                <table width="100%">
                  <tr>
                    <td width="190px"><bean:message key="ConditionForContract.cfc_date_con_to"/></td>
                    <td width="30%"><ctrl:date property="cfc_date_con_to" style="width:200px;"/></td>
                    <td width="190px" align="right">
                      <table border="0" cellSpacing="0" cellPadding="0">
                        <tr>
                          <td width="170px">
                            <bean:message key="ConditionForContract.cfc_delivery_count0"/>&nbsp;
                            <ctrl:checkbox property="cfc_delivery_count1" styleClass="checkbox" value="1" onclick="cfcDeliveryCount1OnClick()" showHelp="false"/>
                            <bean:message key="ConditionForContract.cfc_delivery_count1"/>&nbsp;
                            <ctrl:checkbox property="cfc_delivery_count2" styleClass="checkbox" value="1" onclick="cfcDeliveryCount2OnClick()" showHelp="false"/>
                            <bean:message key="ConditionForContract.cfc_delivery_count2"/>
                          </td>
                        </tr>
                      </table>
                    </td>
                    <td><ctrl:textarea property="cfc_count_delivery" style="width:350px;height:50px;"/></td>
                  </tr>
                </table>
              </td>
            </tr>
          </logic:equal>
          <tr>
            <td>
              <table width="100%" border="0">
                <tr>
                  <td width="28%"><bean:message key="ConditionForContract.contactPersonSign"/></td>
                  <td>
                    <table border="0" cellSpacing="0" cellPadding="0">
                      <tr>
                        <td>
                          <ctrl:serverList property="contactPersonSign.cps_name" idName="contactPersonSign.cps_id"
                                           action="/ContactPersonsListAction"
                                           scriptUrl="ctr_id=$(contractor.id)" callback="onContactPersonSignSelect"
                                           selectOnly="true" style="width:230px;"/>
                        </td>
                        <td>&nbsp;</td>
                        <td>
                          <ctrl:ubutton type="submit" dispatch="newContactPersonSign" readonly="${ConditionForContract.readOnlyAddPerson}" styleClass="width120">
                            <bean:message key="button.addNew"/>
                          </ctrl:ubutton>
                          <ctrl:ubutton styleId="editContactPersonSignButton" type="submit" dispatch="editContactPersonSign" styleClass="width165">
                            <bean:message key="button.editSelected"/>
                          </ctrl:ubutton>
                        </td>
                      </tr>
                    </table>
                  </td>
                </tr>
              </table>
            </td>
          </tr>
          <tr id="idContactPersonSignInfo">
            <td>
              <table width="100%">
                <tr>
                  <td>
                    <bean:message key="ConditionForContract.positionSign"/>&nbsp;
                    <span id='idContactPersonSignPosition'><ctrl:write name="ConditionForContract" property="contactPersonSign.cps_position"/></span>&nbsp;&nbsp;&nbsp;&nbsp;
                    <bean:message key="ConditionForContract.on_reasonSign"/>&nbsp;
                    <span id='idContactPersonSignOnReason'><ctrl:write name="ConditionForContract" property="contactPersonSign.cps_on_reason"/></span>
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
            <td>
              <table width="100%" border="0">
                <tr>
                  <td width="28%"><bean:message key="ConditionForContract.contactPerson"/></td>
                  <td>
                    <table border="0" cellSpacing="0" cellPadding="0">
                      <tr>
                        <td>
                          <ctrl:serverList property="contactPerson.cps_name" idName="contactPerson.cps_id"
                                           action="/ContactPersonsListAction"
                                           scriptUrl="ctr_id=$(contractor.id)" callback="onContactPersonSelect"
                                           selectOnly="true" style="width:230px;"/>
                        </td>
                        <td>&nbsp;</td>
                        <td>
                          <ctrl:ubutton type="submit" dispatch="newContactPerson" readonly="${ConditionForContract.readOnlyAddPerson}" styleClass="width120">
                            <bean:message key="button.addNew"/>
                          </ctrl:ubutton>
                          <ctrl:ubutton styleId="editContactPersonButton" type="submit" dispatch="editContactPerson" styleClass="width165">
                            <bean:message key="button.editSelected"/>
                          </ctrl:ubutton>
                        </td>
                      </tr>
                    </table>
                  </td>
                </tr>
              </table>
            </td>
          </tr>
          <tr id="idContactPersonInfo">
            <td>
              <table width="100%">
                <tr>
                  <td width="28%"></td>
                  <td>
                    <bean:message key="ConditionForContract.personPosition"/>&nbsp;
                    <span id='idContactPersonPosition'><ctrl:write name="ConditionForContract" property="contactPerson.cps_position"/></span>&nbsp;&nbsp;&nbsp;&nbsp;
                    <bean:message key="ConditionForContract.personPhone"/>&nbsp;
                    <span id='idContactPersonPhone'><ctrl:write name="ConditionForContract" property="contactPerson.cps_phone"/></span>&nbsp;&nbsp;&nbsp;&nbsp;
                    <bean:message key="ConditionForContract.personFax"/>&nbsp;
                    <span id='idContactPersonFax'><ctrl:write name="ConditionForContract" property="contactPerson.cps_fax"/></span>&nbsp;&nbsp;&nbsp;&nbsp;
                    <bean:message key="ConditionForContract.personEmail"/>&nbsp;
                    <span id='idContactPersonEmail'><ctrl:write name="ConditionForContract" property="contactPerson.cps_email"/></span>&nbsp;&nbsp;&nbsp;&nbsp;
                    <bean:message key="ConditionForContract.personMobPhone"/>&nbsp;
                    <span id='idContactPersonMobPhone'><ctrl:write name="ConditionForContract" property="contactPerson.cps_mob_phone"/></span>
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
        </logic:equal>
        <tr>
          <td>
            <table width="100%">
              <tr>
                <td width="25%"><bean:message key="ConditionForContract.specification"/></td>
                <td style="text-align: right"><b><span style="font-size: 15px"><ctrl:write name="ConditionForContract" property="currency.name"/></span></b></td>
              </tr>
            </table>
          </td>
        </tr>

        <logic:equal name="ConditionForContract" property="showTable" value="true">
          <tr>
            <td>
              <div class="gridBackNarrow">
                <grid:table property="grid" key="number">
                  <grid:column align="center"><jsp:attribute name="title"><bean:message key="ConditionForContractProduces.number"/></jsp:attribute></grid:column>
                  <logic:equal name="ConditionForContract" property="seller.id" value="1">
                    <grid:column align="center"><jsp:attribute name="title"><bean:message key="ConditionForContractProduces.number1C"/>&nbsp;<ctrl:help htmlName="ConditionForContractProducesNumber1CHeader"/></jsp:attribute></grid:column>
                  </logic:equal>
                  <grid:column align="center"><jsp:attribute name="title"><bean:message key="ConditionForContractProduces.produce_name"/></jsp:attribute></grid:column>
                  <grid:column align="center"><jsp:attribute name="title"><bean:message key="ConditionForContractProduces.catalog_num"/></jsp:attribute></grid:column>
                  <grid:column align="center"><jsp:attribute name="title"><bean:message key="ConditionForContractProduces.ccp_price"/></jsp:attribute></grid:column>
                  <grid:column align="center"><jsp:attribute name="title"><bean:message key="ConditionForContractProduces.ccp_count"/></jsp:attribute></grid:column>
                  <grid:column align="center"><jsp:attribute name="title"><bean:message key="ConditionForContractProduces.unit"/></jsp:attribute></grid:column>
                  <grid:column align="center"><jsp:attribute name="title"><bean:message key="ConditionForContractProduces.ccp_cost"/></jsp:attribute></grid:column>
                  <logic:equal name="ConditionForContract" property="seller.id" value="1">
                    <grid:column align="center"><jsp:attribute name="title"><bean:message key="ConditionForContractProduces.ccp_nds_rate"/></jsp:attribute></grid:column>
                    <grid:column align="center"><jsp:attribute name="title"><bean:message key="ConditionForContractProduces.ccp_nds"/></jsp:attribute></grid:column>
                    <grid:column align="center"><jsp:attribute name="title"><bean:message key="ConditionForContractProduces.ccp_nds_cost"/></jsp:attribute></grid:column>
                  </logic:equal>
                  <grid:column align="center"><jsp:attribute name="title"><bean:message key="ConditionForContractProduces.stuffCategory"/></jsp:attribute></grid:column>
                  <grid:column align="center"><jsp:attribute name="title"><bean:message key="ConditionForContractProduces.commercial_proposal"/></jsp:attribute></grid:column>
                  <grid:column title=""/>
                  <grid:column title=""/>
                  <grid:column title=""/>
                  <grid:row>
                    <grid:colCustom property="number" styleClassCheckerId="style-checker"/>
                    <logic:equal name="ConditionForContract" property="seller.id" value="1">
                      <grid:colCustom property="number1C" styleClassCheckerId="style-checker"/>
                    </logic:equal>
                    <grid:colCustom property="produceFullName" styleClassCheckerId="style-checker"/>
                    <grid:colCustom width="10%" property="catalogNumberForStuffCategory" styleClassCheckerId="style-checker"/>
                    <grid:colCustom width="7%" property="ccp_price_formatted" align="right" showCheckerId="show-checker" styleClassCheckerId="style-checker"/>
                    <grid:colCustom width="7%" property="ccp_count_formatted" align="right" showCheckerId="show-checker" styleClassCheckerId="style-checker"/>
                    <grid:colCustom width="7%" property="unitName" showCheckerId="show-checker" styleClassCheckerId="unit-style-checker"/>
                    <grid:colCustom width="7%" property="ccp_cost_formatted" align="right" styleClassCheckerId="style-checker"/>
                    <logic:equal name="ConditionForContract" property="seller.id" value="1">
                      <grid:colCustom width="8%" property="ccp_nds_rate_formatted" align="right" showCheckerId="show-checker" styleClassCheckerId="style-checker"/>
                      <grid:colCustom width="8%" property="ccp_nds_formatted" align="right" styleClassCheckerId="style-checker"/>
                      <grid:colCustom width="8%" property="ccp_nds_cost_formatted" align="right" styleClassCheckerId="style-checker"/>
                    </logic:equal>
                    <grid:colCustom width="5%" property="stuffCategory.name" styleClassCheckerId="style-checker"/>
                    <grid:colCustom width="7%" property="cpr_number_date" styleClassCheckerId="style-checker"/>
                    <grid:colImage width="1" image="images/copy.gif" dispatch="cloneProduce" type="submit" scriptUrl="sellerId=${ConditionForContract.seller.id}" tooltip="tooltip.ConditionForContractProduces.clone" showCheckerId="show-checker" styleClassCheckerId="style-checker-image"/>
                    <grid:colEdit width="1" dispatch="editProduce" type="submit" scriptUrl="sellerId=${ConditionForContract.seller.id}" tooltip="tooltip.ConditionForContractProduces.edit" showCheckerId="show-checker" styleClassCheckerId="style-checker" readonlyCheckerId="editReadonly"/>
                    <grid:colDelete width="1" dispatch="deleteProduce" type="submit" tooltip="tooltip.ConditionForContractProduces.delete" showCheckerId="show-checker" styleClassCheckerId="style-checker" readonlyCheckerId="deleteReadonly"/>
                  </grid:row>
                </grid:table>
              </div>
            </td>
          </tr>
          <tr>
            <td>
              <div class=gridBottom>
                <logic:equal name="ConditionForContract" property="showDownload" value="true">
                  <input type=button id="downloadTemplate" onclick="return download(${ConditionForContract.templateId});" class="width165" value="<bean:message key="button.saveTemplate"/>">
                </logic:equal>
                <ctrl:ubutton styleId="buttonImportProduce1" type="submit" dispatch="selectCP" styleClass="width240">
                  <bean:message key="button.importCommercialProposal"/>
                </ctrl:ubutton>
                <ctrl:ubutton styleId="buttonImportProduce2" type="submit" dispatch="importExcel" styleClass="width165">
                  <bean:message key="button.importExcel"/>
                </ctrl:ubutton>
                <ctrl:ubutton styleId="buttonAddProduce" type="submit" dispatch="newProduce" styleClass="width80" scriptUrl="sellerId=${ConditionForContract.seller.id}">
                  <bean:message key="button.add"/>
                </ctrl:ubutton>
              </div>
            </td>
          </tr>
          <logic:equal name="ConditionForContract" property="showForAdmin" value="true">
            <tr>
              <td>
                <div class=gridBottom>
                  <ctrl:ubutton type="submit" dispatch="uploadTemplate" scriptUrl="referencedTable=DCL_TEMPLATE&referencedID=${ConditionForContract.templateIdCFC}&id=${ConditionForContract.templateId}" styleClass="width165" readonly="false">
                    <bean:message key="button.attachTemplate"/>
                  </ctrl:ubutton>
                </div>
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
          <td colspan="2">
            <ctrl:checkbox property="cfc_need_invoice" styleClass="checkbox" value="1"/>&nbsp;
            <bean:message key="ConditionForContract.cfc_need_invoice"/>
          </td>
        </tr>
        <tr>
          <td colspan="2">
            <bean:message key="ConditionForContract.cfc_comment"/>
          </td>
        </tr>
        <tr>
          <td colspan="2">
            <ctrl:textarea property="cfc_comment" style="width:100%;height:95px;"/>
          </td>
        </tr>

        <tr>
          <td colspan="2" align="right" class=formSpace>
            &nbsp;
          </td>
        </tr>
        <tr>
          <td >
            <table cellspacing="2" width="100%">
              <tr>
                <td>
                  <bean:message key="ConditionForContract.attachments"/>
                </td>
              </tr>
              <tr>
                <td>
                  <div class="gridBack">
                    <grid:table property="attachmentsGrid" key="idx" >
                      <grid:column><jsp:attribute name="title"><bean:message key="Attachments.name"/></jsp:attribute></grid:column>
                      <grid:column title=""/>
                      <grid:row>
                        <grid:colCustom width="100%" ><a href="" onclick="return downloadAttachment(${record.idx});">${record.originalFileName}</a></grid:colCustom>
                        <grid:colDelete width="1" dispatch="deleteAttachment" scriptUrl="attachmentId=${record.idx}" type="submit" tooltip="tooltip.Attachments.delete"/>
                      </grid:row>
                    </grid:table>
                  </div>
                  <div class=gridBottom>
                    <ctrl:ubutton type="submit" dispatch="attach" styleClass="width80" >
                      <bean:message key="button.attach"/>
                    </ctrl:ubutton>
                  </div>
                </td>
              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td colspan="20">
            <table width="100%" border="0" cellSpacing="0" cellPadding="0">
              <tr>
                <td style="width:145px;vertical-align: middle">
	                <bean:message key="ConditionForContract.cfc_annul"/>
                </td>
                <td style="width:40px;vertical-align: bottom">
                  <ctrl:checkbox property="cfc_annul" styleClass="checkbox" value="1" onclick="cfcAnnulOnClick()" readonly="${ConditionForContract.readOnlyForAnnul}"/>
                </td>
                <td style="vertical-align: bottom">
	                <ctrl:date property="cfcAnnulDateFormatted" style="width:200px;" readonly="${ConditionForContract.readOnlyForAnnul}"/>
                </td>
                <td style="text-align: right">
                  <ctrl:checkbox property="cfc_place" styleClass="checkbox" value="1" readonly="${ConditionForContract.readOnlyForPlace}"/>&nbsp;
                  <bean:message key="ConditionForContract.cfc_place"/>
                </td>
              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td colspan="2" align="right">
            <ctrl:ubutton type="submit" dispatch="print"  styleClass="width80" readonly="false">
              <bean:message key="button.print"/>
            </ctrl:ubutton>
            &nbsp;
            <ctrl:text property="printScale" style="width:40px;text-align:right;" readonly="false"/>
            <bean:message key="Common.percent"/>
            &nbsp;&nbsp;
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

  function submitReloadForm()
  {
    submitDispatchForm("reload");
  }

  function submitReloadFormSeller()
  {
    submitDispatchForm("reloadSeller");
  }

  var contractorId = document.getElementById("contractor.id");
  if (!contractorId)
  {
    var contractorIdByName = document.getElementsByName("contractor.id");
    if (contractorIdByName && contractorIdByName.length > 0)
    {
      contractorId = contractorIdByName[0];
    }
  }
  var cfcDocType1 = document.getElementById('cfc_doc_type1');
  var cfcDocType2 = document.getElementById('cfc_doc_type2');
  var cfcDeliveryCount1 = document.getElementById('cfc_delivery_count1');
  var cfcDeliveryCount2 = document.getElementById('cfc_delivery_count2');
  var conFinalDate = document.getElementById('conFinalDate');

  var cfcPlace = document.getElementById('cfc_place');

  var buttonSave = document.getElementById("buttonSave");
  if (buttonSave) { buttonSave.disabled = ${ConditionForContract.readOnlyForSave}; }

  <logic:notEqual name="ConditionForContract" property="cfc_doc_type2" value="">
    var conId = document.getElementById('contract.con_id');
    var contractNumberWithDate = document.getElementById('contractNumberWithDate');
    var contractNumberWithDateFrm = document.getElementById('contractNumberWithDateFrm');
    var cfcSpcNumberTxt = document.getElementById('cfc_spc_number_txt');
    var cfcSpcDate = document.getElementById('cfc_spc_date');
    var currencyId = document.getElementById('currency.id');
    var currencyName = document.getElementById('currency.name');
  </logic:notEqual>

  var cpsIdSign = document.getElementById('contactPersonSign.cps_id');
  var cpsNameSign = document.getElementById('contactPersonSign.cps_name');
  var cpsId = document.getElementById('contactPerson.cps_id');
  var cpsName = document.getElementById('contactPerson.cps_name');

  var editContactPersonSignButton = document.getElementById("editContactPersonSignButton");
  var editContactPersonButton = document.getElementById("editContactPersonButton");

  var cfcAnnul = document.getElementById("cfc_annul");
  var cfcAnnulDate = document.getElementById("cfcAnnulDateFormatted");

  if (contractorId)
  {
    contractorAjax(contractorId.value);
  }
  <logic:equal name="ConditionForContract" property="showFields" value="true">
    showHideContactPersonSignInfo();
    showHideContactPersonInfo();
  </logic:equal>

  function processClick()
  {
    if (cfcDocType1.checked && !cfcDeliveryCount1.checked && !cfcDeliveryCount2.checked)
    {
      alert('<bean:message key="msg.ConditionForContract.deliveryCountNotSelected"/>');
      return;
    }

    <logic:notEqual name="ConditionForContract" property="cfc_doc_type2" value="">
      if ( getDateForJS(conFinalDate.value) < getDateForJS(cfcSpcDate.value) )
      {
          alert('<bean:message key="msg.ConditionForContract.incorrectSpecDate"/>');
          return;
      }
    </logic:notEqual>

    submitDispatchForm("process");
  }

  function showHideContactPersonSignInfo()
  {
    document.getElementById('idContactPersonSignInfo').style.display = (cpsIdSign.value == '' ? 'none' : '');
    editContactPersonSignButton.disabled = eval(${ConditionForContract.formReadOnly}) || cpsIdSign.value == '';
  }

  function onContactPersonSignSelect(arg)
  {
    cpsIdSign.value = arg?arg.arguments[0]:null;
    document.getElementById('idContactPersonSignPosition').innerHTML = arg?arg.arguments[6]:null;
    document.getElementById('idContactPersonSignOnReason').innerHTML = arg?arg.arguments[7]:null;

    showHideContactPersonSignInfo();
  }
  
  function showHideContactPersonInfo()
  {
    document.getElementById('idContactPersonInfo').style.display = (cpsId.value == '' ? 'none' : '');
    editContactPersonButton.disabled = eval(${ConditionForContract.formReadOnly}) || cpsId.value == '';
  }

  function onContactPersonSelect(arg)
  {
    cpsId.value = arg?arg.arguments[0]:null;
    document.getElementById('idContactPersonPhone').innerHTML = arg?arg.arguments[2]:null;
    document.getElementById('idContactPersonFax').innerHTML = arg?arg.arguments[3]:null;
    document.getElementById('idContactPersonEmail').innerHTML = arg?arg.arguments[4]:null;
    document.getElementById('idContactPersonMobPhone').innerHTML = arg?arg.arguments[5]:null;
    document.getElementById('idContactPersonPosition').innerHTML = arg?arg.arguments[6]:null;

    showHideContactPersonInfo();
  }

  function submitReloadFormContractor()
  {
  <logic:notEqual name="ConditionForContract" property="cfc_doc_type2" value="">
      conId.value = '';
  </logic:notEqual>
    
    if ( null != cpsIdSign )
    {
      cpsIdSign.value='';
      cpsNameSign.value='';
    }
    if ( null != cpsId )
    {
      cpsId.value='';
      cpsName.value='';
    }
    
    submitReloadForm();
  }

  function contractorAjax(id)
  {
    doAjax({
      url:'<ctrl:rewrite action="/ConditionForContractAction" dispatch="ajaxGetReputation"/>',
      params:{'contractor-id':id},
      update:'reputation'
    });
  }

  function cfcDocType1OnClick()
  {
    if ( cfcDocType1.checked )
    {
      cfcDocType2.checked = false;
      submitReloadForm();
    }
    else
    {
      cfcDocType1.checked = !cfcDocType1.checked;
    }
  }
  function cfcDocType2OnClick()
  {
    if ( cfcDocType2.checked )
    {
      cfcDocType1.checked = false;
      submitReloadForm();
    }
    else
    {
      cfcDocType2.checked = !cfcDocType2.checked;
    }
  }

  function cfcDeliveryCount1OnClick()
  {
    cfcDeliveryCount2.checked = false;
  }

  function cfcDeliveryCount2OnClick()
  {
    cfcDeliveryCount1.checked = false;
  }

  function cfcAnnulOnClick()
  {
    if ( !cfcAnnul.checked )
    {
	    cfcAnnulDate.value = '';
    }
  }

  <logic:notEqual name="ConditionForContract" property="cfc_doc_type2" value="">
    onChangeContract();

    function onChangeContract()
    {
      doAjax({
        url:'<ctrl:rewrite action="/ConditionForContractAction" dispatch="ajaxChangeContract"/>',
        params:{'conId':conId.value},
        synchronous:false,
        update:'resultMsg',
        okCallback:function()
        {
          if ( conId.value != '' )
          {
            var resultStr = document.getElementById('resultMsg').innerHTML;
            var arrayResult = resultStr.split('|');
            currencyId.value = arrayResult[0];
            currencyName.value = arrayResult[1];
            contractNumberWithDate.value = arrayResult[2];
            document.getElementById('spcNumbers').innerHTML = arrayResult[3];
            document.getElementById('cfcSpcNumbers').innerHTML = arrayResult[4];
            document.getElementById('idReusableContract').innerHTML = arrayResult[5];
            document.getElementById('idReusableContract').style.display = (arrayResult[5] == '' ? 'none' : '');
            document.getElementById('idAnnulContract').innerHTML = arrayResult[6];
            document.getElementById('idAnnulContract').style.display = (arrayResult[6] == '' ? 'none' : '');
            conFinalDate.value = arrayResult[7];

            <logic:notEqual name="ConditionForContract" property="formReadOnly" value="true">
              cfcSpcNumberTxt.focus();
              contractNumberWithDateFrm.style.display = 'none';
            </logic:notEqual>
          }
        }
      });
    }
  </logic:notEqual>

  function download(id)
  {
	  document.getElementById('for-insert').innerHTML='<iframe src=\'<html:rewrite action="/AttachmentsAction.do?dispatch=download"/>&id='+id+'\' style=\'display:none\' />';
    return false;
  }
	function downloadAttachment(id){
		document.getElementById('for-insert').innerHTML='<iframe src=\'<html:rewrite action="/ConditionForContractAction.do?dispatch=downloadAttachment"/>&attachmentId='+id+'\' style=\'display:none\' />';
		return false;
	}

  function showMsg()
  {
  <logic:equal value="true" name="ConditionForContract" property="showMsg">
    if (isUserAgree('<bean:message key="msg.condition_for_contract.place"/>', true, 600, 160))
    {
      submitDispatchForm("processForce");
    }
    else
    {
      cfcPlace.checked = false;
    }
  </logic:equal>
  }
  initFunctions.push(showMsg);
</script>
<logic:equal name="ConditionForContract" property="print" value="true">
  <script language="JScript" type="text/javascript" >
	  document.getElementById('for-insert').innerHTML='<iframe src="<ctrl:rewrite action="ConditionForContractPrintAction"/>" style="display:none"/>';
  </script>
</logic:equal>
