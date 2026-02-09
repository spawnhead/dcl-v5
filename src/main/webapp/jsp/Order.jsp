  <%@ taglib uri="/tags/struts-bean" prefix="bean" %>
  <%@ taglib uri="/tags/struts-html" prefix="html" %>
  <%@ taglib uri="/tags/struts-logic" prefix="logic" %>
  <%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
  <%@ taglib uri="/tags/html-grid" prefix="grid" %>

  <div id='for-insert'></div>
  <div style="display:none" id="resultMsg"></div>

  <ctrl:askUser name="deleteAttachAsk" key="msg.order.delete_attach" showOkCancel="false" height="120"/>

  <ctrl:form readonly="${Order.formReadOnly}">
  <ctrl:hidden property="ord_id"/>
  <ctrl:hidden property="is_new_doc"/>
  <ctrl:hidden property="usr_date_create"/>
  <ctrl:hidden property="usr_date_edit"/>
  <ctrl:hidden property="createUser.usr_id"/>
  <ctrl:hidden property="editUser.usr_id"/>
  <ctrl:hidden property="ord_summ"/>
  <ctrl:hidden property="noRoundSum"/>
  <ctrl:hidden property="deliveryCondition.name"/>
  <ctrl:hidden property="ord_donot_calculate_netto"/>
  <logic:equal name="Order" property="ord_in_one_spec" value="1">
	  <ctrl:hidden property="seller.resident"/>
	</logic:equal>


  <table class=formBackTop align="center" width="99%">
    <tr>
      <td >
        <table width="100%" cellspacing="0" >

        <logic:notEqual name="Order" property="is_new_doc" value="true">
          <tr>
            <td>
              <table width="100%">
                <tr>
                  <td width="10%"><bean:message key="Order.create"/></td>
                  <td width="40%"><ctrl:write name="Order" property="usr_date_create"/> <ctrl:write name="Order" property="createUser.userFullName"/> </td>
                  <td align="right"><bean:message key="Order.edit"/></td>
                  <td width="33%">&nbsp;&nbsp;&nbsp;&nbsp;<ctrl:write name="Order" property="usr_date_edit"/> <ctrl:write name="Order" property="editUser.userFullName"/></td>
                </tr>
              </table>
            </td>
          </tr>
	        <tr>
	          <td>
	            <table width="100%">
	              <tr>
	                <td width="10%"><bean:message key="Order.sellerForWho"/></td>
	                <td>
		                <ctrl:serverList property="sellerForWho.name" idName="sellerForWho.id" action="/SellersListAction" selectOnly="true" style="width:297px;" scriptUrl="forOrder=true"/>
	                </td>
		            </tr>
	            </table>
	          </td>
	        </tr>
          <tr>
            <td>
              <table width="100%">
                <tr>
                  <td width="10%"><bean:message key="Order.ord_number"/></td>
                  <td width="40%"><ctrl:text property="ord_number" style="width:332px;" readonly="true" showHelp="false"/></td>
                  <td width="17%" align="right"><bean:message key="Order.ord_contractor"/></td>
                  <td width="33%" align="right">
                    <table border="0" cellSpacing="0" cellPadding="0">
                      <tr>
                        <td>
                          <ctrl:serverList property="contractor.name" idName="contractor.id" action="/ContractorsListAction"
                                           filter="filter" style="width:225px;" callback="onContractorSelect" readonly="${Order.readOnlyIfNotLikeManager}"/>
                        </td>
                        <td>&nbsp;</td>
                        <td>
                          <ctrl:ubutton type="submit" dispatch="newContractor" styleClass="width80" readonly="${Order.readOnlyIfNotLikeManager}" askUser="">
                            <bean:message key="button.add"/>
                          </ctrl:ubutton>
                        </td>
                      </tr>
                    </table>
                  </td>
                </tr>
              </table>
            </td>
          </tr>
        </logic:notEqual>
        <logic:equal name="Order" property="is_new_doc" value="true">
          <tr>
            <td>
              <table width="100%">
                <tr>
                  <td width="10%"><bean:message key="Order.sellerForWho"/></td>
                  <td width="40%">
	                  <ctrl:serverList property="sellerForWho.name" idName="sellerForWho.id" action="/SellersListAction" selectOnly="true" style="width:297px;" scriptUrl="forOrder=true"/>
                  </td>
                  <td width="17%" align="right"><bean:message key="Order.ord_contractor"/></td>
                  <td width="33%" align="right">
                    <table border="0" cellSpacing="0" cellPadding="0">
                      <tr>
                        <td>
                          <ctrl:serverList property="contractor.name" idName="contractor.id" action="/ContractorsListAction"
                                           filter="filter" style="width:225px;" callback="onContractorSelect" readonly="${Order.readOnlyIfNotLikeManager}"/>
                        </td>
                        <td>&nbsp;</td>
                        <td>
                          <ctrl:ubutton type="submit" dispatch="newContractor" styleClass="width80" readonly="${Order.readOnlyIfNotLikeManager}" askUser="">
                            <bean:message key="button.add"/>
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
                <td width="10%"><bean:message key="Order.ord_date"/></td>
                <td><ctrl:date property="ord_date" style="width:230px;" readonly="${Order.readOnlyIfNotLikeManager}" showHelp="false"/></td>
                <td width="17%" align="right"><bean:message key="Order.ord_contact_person"/></td>
                <td width="33%" align="right">
                  <table border="0" cellSpacing="0" cellPadding="0">
                    <tr>
                      <td>
                        <ctrl:serverList property="contact_person.cps_name" idName="contact_person.cps_id" action="/ContactPersonsListAction"
                                         scriptUrl="ctr_id=$(contractor.id)" callback="onContactPersonSelect" selectOnly="true" style="width:225px;"
                                         readonly="${Order.readOnlyIfNotLikeManager}"/>
                      </td>
                      <td>&nbsp;</td>
                      <td>
                        <ctrl:ubutton type="submit" styleId="addNewPerson" dispatch="newContactPerson" styleClass="width80" readonly="${Order.readOnlyIfNotLikeManager}" askUser="">
                          <bean:message key="button.add"/>
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
                <td width="10%"><bean:message key="Order.bln_id"/></td>
                <td>
                  <ctrl:serverList property="blank.bln_name" idName="blank.bln_id" callback="submitReloadFormBlank"
                                   action="/BlanksListAction" selectOnly="true" style="width:310px;"
                                   scriptUrl="type=2" showHelp="false"/>
                </td>
                <td align="right">
                  <bean:message key="Order.ord_phone"/>&nbsp;&nbsp;
                  <span id='idContactPersonPhone'><ctrl:write name="Order" property="contact_person.cps_phone"/></span>&nbsp;&nbsp;&nbsp;&nbsp;
                  <bean:message key="Order.ord_fax"/>&nbsp;&nbsp;
                  <span id='idContactPersonFax'><ctrl:write name="Order" property="contact_person.cps_fax"/></span>&nbsp;&nbsp;&nbsp;&nbsp;
                  <bean:message key="Order.ord_email"/>&nbsp;&nbsp;
                  <span id='idContractorEmail'><ctrl:write name="Order" property="contractor.email"/></span>&nbsp;&nbsp;
                  <span id='idContactPersonEmail'><ctrl:write name="Order" property="contact_person.cps_email"/></span>
                </td>
              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td>
            <table width="100%">
              <tr>
                <td width="10%"><bean:message key="Order.ord_concerning"/></td>
                <td><ctrl:text property="ord_concerning" style="width:332px;"
                               readonly="${Order.readOnlyIfNotLikeManager}" showHelp="false"/></td>
                <td width="17%" align="right"></td>
                <td width="33%" align="right"></td>
              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td>
            <table width="100%">
              <tr>
                <td width="10%"><bean:message key="Order.ord_preamble1"/></td>
                <td width="40%"><ctrl:textarea property="ord_preamble" style="width:332px;height:100px;"
                                               readonly="${Order.readOnlyIfNotLikeManager}" showHelp="false"/></td>
                <td width="17%" align="right"><bean:message key="Order.ord_preamble2"/></td>
                <td width="33%" align="right"><ctrl:textarea property="blank.bln_preamble" style="width:332px;height:100px;" readonly="true"/></td>
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
            <table width="100%">
              <tr>
                <td>
                  <bean:message key="Order.ord_in_one_spec"/>&nbsp;&nbsp;
                  <ctrl:checkbox property="ord_in_one_spec" styleClass="checkbox" value="1" onclick="ordInOneSpecClick();"/>&nbsp;&nbsp;
                  <span style="color:#008000"><bean:message key="Order.ord_in_one_spec_legend"/></span>
                </td>
              </tr>
            </table>
          </td>
        </tr>

        <logic:equal name="Order" property="ord_in_one_spec" value="1">
          <tr>
            <td>
              <table>
                <tr>
                  <td>
                    <table border="0" cellSpacing="0" cellPadding="0">
                      <tr>
                        <td width="145"><bean:message key="Order.ordSeller"/></td>
                        <td>&nbsp;&nbsp;</td>
                        <td>
                          <table border="0" cellSpacing="0" cellPadding="0">
                            <tr>
                              <td>
	                              <ctrl:serverList property="seller.name" idName="seller.id" action="/SellersListAction" selectOnly="true"	scriptUrl="forOrder=true"
	                                               readonly="${Order.readOnlyIfNotLikeManager}" callback="onSellerSelect"/>
                              </td>
                            </tr>
                          </table>
                        </td>
                      </tr>
                      <tr>
                        <td width="145"><bean:message key="Order.ord_contractor_for"/></td>
                        <td>&nbsp;&nbsp;</td>
                        <td>
                          <table border="0" cellSpacing="0" cellPadding="0">
                            <tr>
                              <td>
                                <ctrl:serverList property="contractor_for.name" idName="contractor_for.id" action="/ContractorsListAction"
                                                 filter="filter" callback="onContractorForSelect" readonly="${Order.readOnlyIfNotLikeManager}"/>
                              </td>
                              <td>&nbsp;</td>
                              <td>
                                <ctrl:ubutton type="submit" dispatch="newContractorFor" styleClass="width80" readonly="${Order.readOnlyIfNotLikeManager}" askUser="">
                                  <bean:message key="button.add"/>
                                </ctrl:ubutton>
                              </td>
                            </tr>
                          </table>
                        </td>
                      </tr>
                      <tr>
                        <td><bean:message key="Order.ord_contract"/></td>
                        <td>&nbsp;&nbsp;</td>
                        <td>
                          <table border="0" cellSpacing="0" cellPadding="0">
                            <tr>
                              <td><ctrl:serverList property="contractNumberWithDate" idName="contract.con_id"
                                                   action="/ContractsDepFromContractorListAction" scriptUrl="ctr_id=$(contractor_for.id)&con_seller=$(seller.id)"
                                                   callback="contractChanged" selectOnly="true" readonly="${Order.readOnlyIfNotLikeManager}"/>
                              </td>
                              <td>&nbsp;</td>
                              <td>
                                <span style="color:red"><b id="conCopyId"></b></span>
                              </td>
                            </tr>
                          </table>
                        </td>
                      </tr>
                      <tr>
                        <td><bean:message key="Order.ord_specification"/></td>
                        <td>&nbsp;&nbsp;</td>
                        <td>
                          <table border="0" cellSpacing="0" cellPadding="0">
                            <tr>
                              <td><ctrl:serverList property="specificationNumberWithDate" idName="specification.spc_id"
                                                   action="/SpecificationsDepFromContractListAction" scriptUrl="id=$(contract.con_id)"
                                                   callback="specificationChanged" selectOnly="true" readonly="${Order.readOnlyIfNotLikeManager}"/>
                              </td>
                              <td>&nbsp;</td>
                              <td>
                                <span style="color:red"><b id="spcCopyId"></b></span>
                              </td>
                            </tr>
                          </table>
                        </td>
                      </tr>
                    </table>
                  </td>
                  <td>
                    <table border="0" cellSpacing="0" cellPadding="0">
                      <tr>
                        <td>
                          <input type=button name="cleanButton" onclick="cleanFieldsClick();" class="height60" value="<bean:message key="button.cleanFields"/>">
                        </td>
                       </tr>
                    </table>
                  </td>
                </tr>
              </table>
            </td>
          </tr>
        </logic:equal>

        <tr id="ordAllIncludeInSpecScope">
          <td>
            <table width="100%">
              <tr>
                <td>
                  <ctrl:checkbox property="ord_all_include_in_spec" styleClass="checkbox" value="1" onclick="submitReload();" readonly="${Order.readOnlyIfNotLikeManager}"/>&nbsp;
                  <bean:message key="Order.ord_all_include_in_spec"/>
                  <span style="color:#008000"><bean:message key="Order.ord_all_include_in_spec_legend"/></span>
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
                  <ctrl:checkbox property="ord_by_guaranty" styleClass="checkbox" value="1" onclick="submitReload();"/>&nbsp;<bean:message key="Order.ord_by_guaranty"/>
                </td>
              </tr>
            </table>
          </td>
        </tr>

        <tr>
          <td>
            <table width="100%" border="0" cellSpacing="0" cellPadding="0">
              <tr>
                <td width="155"><bean:message key="Order.ord_stuff_category"/></td>
                <td width="1">
                  <ctrl:serverList property="stuffCategory.name" idName="stuffCategory.id" action="/StuffCategoriesListAction"
                                   style="width:310px;" callback="onStuffCategorySelect" filter="filter"
                                   readonly="${Order.readOnlyStuffCategory}"/>
                </td>
                <logic:equal name="Order" property="readOnlyStuffCategory" value="true">
                  <td>&nbsp;
                  <span style="color:#008000"><bean:message key="Order.ord_stuff_category_legend"/></span>
                  </td>
                </logic:equal>
                <logic:equal name="Order" property="readOnlyStuffCategory" value="false">
                  <td>&nbsp;
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
                <hr>
              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td>
            <table width="100%">
              <tr>
                <td width="50%">
                  <bean:message key="Order.donot_calculate_netto"/>&nbsp;
                  <ctrl:checkbox property="donot_calculate_netto" styleClass="checkbox" value="1" onclick="doNotCalculateNettoOnClick();" readonly="${Order.readOnlyIfNotLikeManager}"/>&nbsp;
                  <bean:message key="Order.calculate_netto"/>&nbsp;
                  <ctrl:checkbox property="calculate_netto" styleClass="checkbox" value="1" onclick="calculateNettoOnClick();" readonly="${Order.readOnlyIfNotLikeManager}"/>&nbsp;
                </td>
                <td width="17%" align="right"><bean:message key="Order.ord_currency"/></td>
                <td width="33%" align="right"><ctrl:serverList property="currency.name" idName="currency.id" action="/CurrenciesListAction"
                                                               selectOnly="true" style="width:312px;" callback="onCurrencySelect"
                                                               readonly="${Order.readOnlyIfNotLikeManager}" showHelp="false"/></td>
              </tr>
            </table>
          </td>
        </tr>
        <logic:notEqual name="Order" property="ord_donot_calculate_netto" value="1">
          <tr>
            <td>
              <table width="100%">
                <tr>
                  <td width="45%">
                    <bean:message key="Order.ord_discount_first"/>&nbsp;
                    <ctrl:checkbox property="ord_discount_all" styleClass="checkbox" value="1" onclick="discountAllOnClick()" readonly="${Order.readOnlyIfNotLikeManager}"/>&nbsp;
                    <ctrl:text property="ord_discount" style="width:100px;text-align:right;" disabled="${Order.discountDisabled}" onchange="submitReloadIncludeNDS();" readonly="${Order.readOnlyIfNotLikeManager}"/>
                    <bean:message key="Order.ord_discount_third"/>
                  </td>
                  <td>
                    <bean:message key="Order.ord_count_itog_flag_text"/>&nbsp;
                    <ctrl:checkbox property="ord_count_itog_flag" styleClass="checkbox" value="1" onclick="submitReload()" readonly="${Order.readOnlyIfNotLikeManager}"/>&nbsp;
                  </td>
                </tr>
              </table>
            </td>
          </tr>
        </logic:notEqual>
        <tr>
          <td>
            <table width="100%">
              <tr>
                <td width="45%">
                  <bean:message key="Order.ord_add_reduction_flag_text"/>&nbsp;
                  <ctrl:checkbox property="ord_add_reduction_flag" styleClass="checkbox" value="1" onclick="submitReload()"
                                 readonly="${Order.readOnlyIfNotLikeManager}" showHelp="false"/>&nbsp;
                  <ctrl:text property="ord_add_reduction" style="width:100px;text-align:right;"
                             disabled="${Order.addReductionDisabled}" onchange="submitReloadIncludeNDS();"
                             readonly="${Order.readOnlyIfNotLikeManager}" showHelp="false"/>
                  <bean:message key="Order.ord_add_reduction"/>
                </td>
                <td>
                  <bean:message key="Order.ord_add_red_pre_pay_flag_text"/>&nbsp;
                  <ctrl:checkbox property="ord_add_red_pre_pay_flag" styleClass="checkbox" value="1"
                                 onclick="submitReload()" readonly="${Order.readOnlyIfNotLikeManager}"
                                 showHelp="false"/>&nbsp;
                  <ctrl:text property="ord_add_red_pre_pay" style="width:100px;text-align:right;"
                             disabled="${Order.addRedPreRayDisabled}" onchange="submitReloadIncludeNDS();"
                             readonly="${Order.readOnlyIfNotLikeManager}" showHelp="false"/>
                  <bean:message key="Order.ord_add_red_pre_pay"/>
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
                  <bean:message key="Order.ord_include_nds"/>&nbsp;
                  <ctrl:checkbox property="ord_include_nds" styleClass="checkbox" value="1" onclick="includeNdsOnClick()" readonly="${Order.readOnlyIfNotLikeManager}"/>&nbsp;&nbsp;
                  <bean:message key="Order.ord_nds_rate_before"/>
                  <ctrl:text property="ord_nds_rate" style="width:100px;text-align:right;"
                             disabled="${Order.nds_rateDisabled}" onchange="submitReloadIncludeNDS();"
                             readonly="${Order.readOnlyIfNotLikeManager}" showHelp="false"/>
                  <bean:message key="Order.ord_nds_rate_after"/>
                </td>
              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td>
            <div id="orderGrid" class="gridBackNarrow">
              <grid:table property="orderProducesGrid" key="number">
                <grid:column align="center"><jsp:attribute name="title"><bean:message key="OrderProduces.number"/></jsp:attribute></grid:column>
                <grid:column title=""/>
                <grid:column align="center"><jsp:attribute name="title"><bean:message key="OrderProduces.produce_name"/>&nbsp;<ctrl:help htmlName="OrderProduceNameHeader"/></jsp:attribute></grid:column>
                <grid:column align="center"><jsp:attribute name="title"><bean:message key="OrderProduces.produce_type"/></jsp:attribute></grid:column>
                <grid:column align="center"><jsp:attribute name="title"><bean:message key="OrderProduces.produce_params"/></jsp:attribute></grid:column>
                <grid:column align="center"><jsp:attribute name="title"><bean:message key="OrderProduces.produce_add_params"/></jsp:attribute></grid:column>
                <grid:column align="center"><jsp:attribute name="title"><bean:message key="OrderProduces.catalog_num"/></jsp:attribute></grid:column>
                <grid:column align="center"><jsp:attribute name="title"><bean:message key="OrderProduces.opr_count"/></jsp:attribute></grid:column>
                <grid:column align="center"><jsp:attribute name="title"><bean:message key="OrderProduces.opr_count_executed"/><br><img title='<bean:message key="tooltip.OrderProduces.editExecuted"/>' src='<ctrl:rewrite page="/images/opn.gif"/>' onclick='editOrderExecuted()' style='cursor:pointer;'></jsp:attribute></grid:column>
                <logic:notEqual name="Order" property="ord_donot_calculate_netto" value="1">
                  <grid:column align="center"><jsp:attribute name="title"><bean:message key="OrderProduces.opr_price_brutto"/></jsp:attribute></grid:column>
                  <logic:notEqual name="Order" property="ord_count_itog_flag" value="1">
                    <grid:column align="center"><jsp:attribute name="title"><bean:message key="OrderProduces.opr_discount"/></jsp:attribute></grid:column>
                  </logic:notEqual>
                </logic:notEqual>
                <logic:notEqual name="Order" property="ord_count_itog_flag" value="1">
                  <grid:column align="center"><jsp:attribute name="title"><bean:message key="OrderProduces.opr_price_netto"/></jsp:attribute></grid:column>
                </logic:notEqual>
                <grid:column align="center"><jsp:attribute name="title"><bean:message key="OrderProduces.opr_summ"/></jsp:attribute></grid:column>
                <logic:equal name="Order" property="ord_all_include_in_spec" value="1">
                  <logic:notEqual name="Order" property="ord_by_guaranty" value="1">
                    <grid:column align="center"><jsp:attribute name="title"><bean:message key="OrderProduces.drp_price"/></jsp:attribute></grid:column>
                  </logic:notEqual>
                  <grid:column align="center"><jsp:attribute name="title"><bean:message key="OrderProduces.specification"/></jsp:attribute></grid:column>
                </logic:equal>
                <logic:notEqual name="Order" property="ord_in_one_spec" value="1">
                  <grid:column align="center"><jsp:attribute name="title"><bean:message key="Order.ord_contractor_for"/></jsp:attribute></grid:column>
                  <grid:column align="center"><jsp:attribute name="title"><bean:message key="Order.ord_contract"/></jsp:attribute></grid:column>
                  <grid:column align="center"><jsp:attribute name="title"><bean:message key="Order.grid.specification"/></jsp:attribute></grid:column>
                </logic:notEqual>
                <grid:column title=""/>
                <grid:column title=""/>
                <logic:equal value="true" name="Order" property="executedOrPartExecuted">
                  <grid:column title=""/>
                </logic:equal>
                <logic:notEqual value="true" name="Order" property="executedOrPartExecuted">
                  <grid:column><jsp:attribute name="title"><img title='<bean:message key="tooltip.OrderProduces.deleteAll"/>' src='<ctrl:rewrite page="/images/sub.gif"/>' onclick='deleteAllRows()' style='cursor:pointer;'></jsp:attribute></grid:column>
                </logic:notEqual>
                <grid:row>
                  <logic:equal value="1" name="record" property="opr_use_prev_number">
                    <grid:colCustom width="2%"/>
                  </logic:equal>
                  <logic:empty name="record" property="opr_use_prev_number">
                    <grid:colCustom width="2%" property="viewNumber" showCheckerId="show-checker"
                                    styleClassCheckerId="style-checker" align="center"/>
                  </logic:empty>
                  <grid:colCheckbox align="center" width="20" property="opr_use_prev_number"
                                    showCheckerId="show-checker" styleClassCheckerId="style-checker"
                                    type="submit" dispatch="changeViewNumber"
                                    scriptUrl="sameNumberAsPrevious=${record.opr_use_prev_number}"
                                    readonlyCheckerId="checkbox-readonly-checker"/>

                  <grid:colLink property="produce_name" type="submit" dispatch="produceMovement" scriptUrl="produce.id=${record.produce.id}" styleClassCheckerId="style-checker" readonlyCheckerId="linkReadonly"/>
                  <grid:colCustom width="10%" property="produce.type" styleClassCheckerId="style-checker"/>
                  <grid:colCustom width="10%" property="produce.params" styleClassCheckerId="style-checker"/>
                  <grid:colCustom width="10%" property="produce.addParams" styleClassCheckerId="style-checker"/>
                  <grid:colCustom width="10%" property="catalogNumberForStuffCategory" styleClassCheckerId="style-checker"/>
                  <grid:colCustom width="5%" property="opr_count_formatted" align="right" showCheckerId="show-checker" styleClassCheckerId="style-checker"/>
                  <grid:colCustom width="5%" property="opr_count_executed_formatted" align="right" showCheckerId="show-checker" styleClassCheckerId="styleCheckerExecuted"/>
                  <logic:notEqual name="Order" property="ord_donot_calculate_netto" value="1">
                    <grid:colCustom width="10%" property="opr_price_brutto_formatted" align="right" showCheckerId="show-checker" styleClassCheckerId="style-checker"/>
                    <logic:notEqual name="Order" property="ord_count_itog_flag" value="1">
                      <grid:colCustom width="5%" property="opr_discount_formatted" align="right" showCheckerId="show-checker" styleClassCheckerId="style-checker"/>
                    </logic:notEqual>
                  </logic:notEqual>
                  <logic:notEqual name="Order" property="ord_count_itog_flag" value="1">
                    <grid:colCustom width="5%" property="opr_price_netto_formatted" align="right" showCheckerId="show-checker" styleClassCheckerId="style-checker"/>
                  </logic:notEqual>
                  <grid:colCustom width="10%" property="opr_summ_formatted" align="right" styleClassCheckerId="style-checker"/>
                  <logic:equal name="Order" property="ord_all_include_in_spec" value="1">
                    <logic:notEqual name="Order" property="ord_by_guaranty" value="1">
                      <grid:colCustom width="5%" property="drp_price_formatted" align="right" showCheckerId="show-checker" styleClassCheckerId="style-checker"/>
                     </logic:notEqual>
                    <grid:colCustom width="5%" property="specificationNumbers" styleClassCheckerId="style-checker"/>
                  </logic:equal>
                  <logic:notEqual name="Order" property="ord_in_one_spec" value="1">
                    <grid:colServerList property="contractor.name" idName="contractor.id"
                                        result="orderProducesGrid" resultProperty="contractor.name" useIndexAsPK="true"
                                        action="/ContractorsListAction"	filter="filter"
                                        callback="onGridContractorForSelect" data="${counter-1}"
                                        readonlyCheckerId="editByManager" showCheckerId="show-checker" styleClassCheckerId="style-checker"
                                        styleId="contractor" listWidth="200"/>
                    <grid:colServerList property="contract.con_number" idName="contract.con_id"
                                        result="orderProducesGrid" resultProperty="contract.con_number"  useIndexAsPK="true"
                                        action="/ContractsDepFromContractorListAction" scriptUrl="ctr_id=$(orderProducesGrid.row(${counter-1}\).contractor.id)"
                                        callback="onGridContractChanged" data="${counter-1}"
                                        readonlyCheckerId="editByManager" selectOnly="true" showCheckerId="show-checker"
                                        styleClassCheckerId="style-checker" styleId="contract"	listWidth="200"/>
	                  <grid:colInput style="display:none" property="contract.seller.resident" resultProperty="contract.seller.resident" result="orderProducesGrid" useIndexAsPK="true" showCheckerId="show-checker"/>
                    <grid:colServerList property="specification.spc_number" idName="specification.spc_id"
                                        result="orderProducesGrid" resultProperty="specification.spc_number" useIndexAsPK="true"
                                        action="/SpecificationsDepFromContractListAction" scriptUrl="id=$(orderProducesGrid.row(${counter-1}\).contract.con_id)"
                                        callback="onGridSpecificationChanged" data="${counter-1}"
                                        readonlyCheckerId="editByManager" selectOnly="true" showCheckerId="show-checker"
                                        styleClassCheckerId="style-checker" styleId="specification" listWidth="150"/>
                  </logic:notEqual>
                  <grid:colImage width="1" image="images/copy.gif" dispatch="cloneProduce" type="submit" scriptUrl="donot_calculate_netto=$(ord_donot_calculate_netto)&ord_all_include_in_spec=${Order.ord_all_include_in_spec}&ord_discount_all=${Order.ord_discount_all}&ord_by_guaranty=${Order.ord_by_guaranty}&stf_id=$(stuffCategory.id)" tooltip="tooltip.OrderProduces.clone" showCheckerId="show-checker" styleClassCheckerId="style-checker-image" readonlyCheckerId="cloneReadonly"/>
                  <grid:colEdit width="1" dispatch="editProduce" type="submit" scriptUrl="donot_calculate_netto=$(ord_donot_calculate_netto)&ord_all_include_in_spec=${Order.ord_all_include_in_spec}&ord_discount_all=${Order.ord_discount_all}&ord_by_guaranty=${Order.ord_by_guaranty}&stf_id=$(stuffCategory.id)&opr_use_prev_number=${record.opr_use_prev_number}" tooltip="tooltip.OrderProduces.edit" showCheckerId="show-checker" styleClassCheckerId="style-checker" readonlyCheckerId="editReadonly"/>
                  <grid:colDelete width="1" dispatch="deleteProduce" type="submit" tooltip="tooltip.OrderProduces.delete" showCheckerId="show-checker" styleClassCheckerId="style-checker" readonlyCheckerId="deleteReadonly"/>
                </grid:row>
              </grid:table>
            </div>
          </td>
        </tr>
        <tr>
          <td>
            <div class=gridBottom>
              <logic:equal name="Order" property="showDownload" value="true">
                <input type=button id="downloadTemplate" onclick="return download(${Order.templateId});" class="width165" value="<bean:message key="button.saveTemplate"/>">
              </logic:equal>
              <ctrl:ubutton styleId="buttonImportProduce1" type="submit" dispatch="selectCP" scriptUrl="stf_id=$(stuffCategory.id)" styleClass="width240" readonly="${Order.readOnlyIfNotLikeManager}" askUser="">
                <bean:message key="button.importCommercialProposal"/>
              </ctrl:ubutton>
              <ctrl:ubutton styleId="buttonImportProduce2" type="submit" dispatch="importExcel" styleClass="width165" readonly="${Order.readOnlyIfNotLikeManager}" askUser="">
                <bean:message key="button.importExcel"/>
              </ctrl:ubutton>
              <ctrl:ubutton styleId="buttonAddProduce" type="submit" dispatch="newProduce" scriptUrl="donot_calculate_netto=$(ord_donot_calculate_netto)&ord_all_include_in_spec=${Order.ord_all_include_in_spec}&ord_discount_all=${Order.ord_discount_all}&ord_by_guaranty=${Order.ord_by_guaranty}&stf_id=$(stuffCategory.id)" styleClass="width80" readonly="${Order.readOnlyIfNotLikeManager}" askUser="">
                <bean:message key="button.add"/>
              </ctrl:ubutton>
            </div>
          </td>
        </tr>
        <logic:equal name="Order" property="showForAdmin" value="true">
          <tr>
            <td>
              <div class=gridBottom>
                <ctrl:ubutton type="submit" dispatch="uploadTemplate" scriptUrl="referencedTable=DCL_TEMPLATE&referencedID=${Order.templateIdOrder}&id=${Order.templateId}" styleClass="width165" readonly="false" askUser="">
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
                <td width="135px"><bean:message key="Order.ord_delivery_condition"/></td>
                <td width="180px">
                  <ctrl:serverList property="deliveryCondition.nameExtended" idName="deliveryCondition.id"
                                   action="/IncoTermsListAction" scriptUrl="forOrder=true" callback="onDeliveryConditionSelect" selectOnly="true"
                                   style="width:150px;" readonly="${Order.readOnlyIfNotLikeManager}"/>
                </td>
                <td width="55px"><bean:message key="Order.ord_addr"/></td>
                <td><ctrl:text property="ord_addr" style="width:492px;" readonly="${Order.readOnlyIfNotLikeManager}"/></td>
              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td>
            <table width="100%">
              <tr>
                <td>
                  <bean:message key="Order.ord_delivery_cost_by"/>&nbsp;
                  <ctrl:text property="ord_delivery_cost_by" style="width:320px;"/>
                  &nbsp;&nbsp;<bean:message key="Order.ord_delivery_cost"/>&nbsp;
                  <ctrl:text property="ord_delivery_cost" style="width:132px;text-align:right;"/>
                  <span id='ord_delivery_cost_currency'>
                    <ctrl:write name="Order" property="ord_delivery_cost_currency"/>
                  </span>
                </td>
              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td>
            <table width="100%">
              <tr>
                <td width="10%"><bean:message key="Order.ord_pay_condition"/></td>
                <td><ctrl:textarea property="ord_pay_condition" style="width:724px;height:95px;"
                                   readonly="${Order.readOnlyIfNotLikeManager}" showHelp="false"/></td>
              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td>
            <table width="100%">
              <tr>
                <td width="10%"><bean:message key="Order.ord_add_info1"/></td>
                <td width="40%"><ctrl:textarea property="ord_add_info" style="width:332px;height:100px;"
                                               readonly="${Order.readOnlyIfNotLikeManager}" showHelp="false"/></td>
                <td width="17%" align="right"><bean:message key="Order.ord_add_info2"/></td>
                <td width="33%" align="right"><ctrl:textarea property="blank.bln_note" style="width:332px;height:100px;" readonly="true"/></td>
              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td>
            <table width="100%">
              <tr>
                <td width="10%"><bean:message key="Order.ord_delivery_term"/></td>
                <td><ctrl:text property="ord_delivery_term" style="width:332px;"
                               readonly="${Order.readOnlyIfNotLikeManager}" showHelp="false"/></td>
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
            <table width="100%">
              <tr>
                <td width="185px"><bean:message key="Order.ord_director"/></td>
                <td width="350px"><ctrl:serverList property="director.userFullName" idName="director.usr_id" action="/UsersListAction"
                                     selectOnly="true" style="width:310px;" scriptUrl="respons_person=true"
                                     readonly="${Order.readOnlyIfNotLikeManager}" showHelp="false"/></td>
                <td><bean:message key="Order.sendToPrint"/></td>
              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td>
            <table width="100%">
              <tr>
                <td width="185px"><bean:message key="Order.ord_logist"/></td>
                <td width="395px"><ctrl:serverList property="logist.userFullName" idName="logist.usr_id" action="/UsersListAction"
                                     selectOnly="true" style="width:310px;" scriptUrl="respons_person=true"
                                     readonly="${Order.readOnlyIfNotLikeManager}" showHelp="false"/></td>
	              <td><ctrl:checkbox property="ord_logist_signature" styleClass="checkbox" value="1" readonly="${Order.readOnlyIfNotLikeLogistOrManager}" showHelp="false"/></td>
              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td>
            <table width="100%">
              <tr>
                <td width="185px"><bean:message key="Order.ord_director_rb"/></td>
                <td width="395px"><ctrl:serverList property="director_rb.userFullName" idName="director_rb.usr_id" action="/UsersListAction"
                                     selectOnly="true" style="width:310px;" scriptUrl="respons_person=true"
                                     readonly="${Order.readOnlyIfNotLikeManager}" showHelp="false"/></td>
	              <td><ctrl:checkbox property="ord_director_rb_signature" styleClass="checkbox" value="1" readonly="${Order.readOnlyIfNotLikeLogistOrManager}" showHelp="false"/></td>
              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td>
            <table width="100%">
              <tr>
                <td width="185px"><bean:message key="Order.ord_chief_dep"/></td>
                <td width="395px"><ctrl:serverList property="chief_dep.userFullName" idName="chief_dep.usr_id" action="/UsersListAction"
                                     selectOnly="true" style="width:310px;" scriptUrl="respons_person=true"
                                     readonly="${Order.readOnlyIfNotLikeManager}" showHelp="false"/></td>
	              <td><ctrl:checkbox property="ord_chief_dep_signature" styleClass="checkbox" value="1" readonly="${Order.readOnlyIfNotLikeLogistOrManager}" showHelp="false"/></td>
              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td>
            <table width="100%">
              <tr>
                <td width="185px"><bean:message key="Order.ord_manager"/></td>
                <td width="395px"><ctrl:serverList property="manager.userFullName" idName="manager.usr_id" action="/UsersListAction"
                                     selectOnly="true" style="width:310px;" showHelp="false" 
                                     readonly="${Order.readOnlyIfNotLikeManager}"/></td>
	              <td><ctrl:checkbox property="ord_manager_signature" styleClass="checkbox" value="1" readonly="${Order.readOnlyIfNotLikeLogistOrManager}" showHelp="false"/></td>
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
            <table width="100%">
              <tr>
                <td><bean:message key="Order.fill_logistic"/></td>
              </tr>
            </table>
          </td>
        </tr>

        <tr>
          <td>
            <table cellpadding="0" cellspacing="0">
              <tr>
                <td>
                  <table cellpadding="0" cellspacing="0">
                    <tr>
                      <td>
                        <table>
                          <tr>
                            <td width="100%"><bean:message key="Order.ord_sent_to_prod"/></td>
                            <td>
                              <table cellpadding="0" cellspacing="0">
                                <tr>
                                  <td>
                                    <ctrl:date property="ord_sent_to_prod_date" style="width:230px;" readonly="${Order.readOnlySentToProdDate}"/>
                                  </td>
                                </tr>
                              </table>
                            </td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                    <logic:equal name="Order" property="showSendMailMsg" value="true">
                      <tr>
                        <td align="right">
                          <table>
                            <tr>
                              <td>
                                <bean:message key="Order.response_not_received1"/>
                              </td>
                            </tr>
                          </table>
                        </td>
                      </tr>
                      <tr>
                        <td align="right">
                          <table>
                            <tr>
                              <td>
                                <bean:message key="Order.response_not_received2"/>
                                <a href="mailto:<ctrl:write name="Order" property="contractor.email"/>
                                  <bean:message key="Order.mail_subject"/><ctrl:write name="Order" property="ord_number"/>_<ctrl:write name="Order" property="ord_date"/>">
                                  <logic:notEqual name="Order" property="contractor.email" value="">
                                    mailto:<ctrl:write name="Order" property="contractor.email"/>
                                  </logic:notEqual>
                                  <logic:equal name="Order" property="contractor.email" value="">
                                    mailto:<bean:message key="Order.empty_mail_subject"/>
                                  </logic:equal>
                                </a>
                              </td>
                            </tr>
                          </table>
                        </td>
                      </tr>
                    </logic:equal>
                    <tr>
                      <td>
                        <table>
                          <tr>
                            <td width="100%"><bean:message key="Order.ord_received_conf"/></td>
                            <td>
                              <table cellpadding="0" cellspacing="0">
                                <tr>
                                  <td>
                                    <ctrl:date property="ord_received_conf_date" style="width:230px;" readonly="${Order.readOnlyIfNotLikeLogist}"/>
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
                        <table>
                          <tr>
                            <td align="right" width="100%"><bean:message key="Order.ord_num_conf"/></td>
                            <td><ctrl:text property="ord_num_conf" style="width:251px;" readonly="${Order.readOnlyIfNotLikeLogist}"/></td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                    <tr>
                      <td>
                        <table>
                          <tr>
                            <td align="right" width="100%">
                              <bean:message key="Order.ord_date_conf"/>&nbsp;
                              <ctrl:checkbox property="ord_date_conf_all" styleClass="checkbox" value="1" onclick="ordDateConfAllClickSubmit()" readonly="${Order.readOnlyIfNotLikeLogist}"/>
                            </td>
                            <td>
                              <table cellpadding="0" cellspacing="0">
                                <tr>
                                  <td>
                                    <logic:equal name="Order" property="ord_date_conf_all" value="1">
                                      <ctrl:date property="ord_date_conf" style="width:230px;" afterSelect='submitReloadIsWarn' selectOnly="true" readonly="${Order.readOnlyDateCond}"/>
                                    </logic:equal>
                                    <logic:notEqual name="Order" property="ord_date_conf_all" value="1">
                                      <span style="width:250px;color:#008000"><bean:message key="Order.ord_conf_sent_legend"/></span>
                                    </logic:notEqual>
                                  </td>
                                </tr>
                              </table>
                            </td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                    <logic:equal name="Order" property="is_warn" value="1">
                      <tr>
                        <td>
                          <table>
                            <tr>
                              <td width="100%"></td>
                              <td>
                                <table cellpadding="0" cellspacing="0">
                                  <tr>
                                    <td>
                                      <span style="width:250px;color:red"><bean:message key="Order.warn"/></span>
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
                        <table>
                          <tr>
                            <td width="100%"><bean:message key="Order.ord_conf_sent"/></td>
                            <td>
                              <table cellpadding="0" cellspacing="0">
                                <tr>
                                  <td>
                                    <ctrl:date property="ord_conf_sent_date" style="width:230px;" readonly="${Order.readOnlyIfNotLikeLogist}"/>
                                  </td>
                                </tr>
                              </table>
                            </td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                    <logic:equal name="Order" property="ord_date_conf_all" value="1">
                      <tr>
                        <td>
                          <table>
                            <tr>
                              <td width="100%">
                                <bean:message key="Order.ord_ready_for_deliv_date"/>&nbsp;
                                <ctrl:checkbox property="ord_ready_for_deliv_date_all" styleClass="checkbox" value="1" onclick="ordReadyForDeliveryDateAllClick()" readonly="${Order.readOnlyIfNotLikeLogist}"/>
                              </td>
                              <logic:equal name="Order" property="ord_ready_for_deliv_date_all" value="1">
                                <td>
                                  <table cellpadding="0" cellspacing="0">
                                    <tr>
                                      <td>
                                        <ctrl:date property="ord_ready_for_deliv_date" style="width:230px;" readonly="${Order.readOnlyIfNotLikeLogist}"/>
                                      </td>
                                    </tr>
                                  </table>
                                </td>
                              </logic:equal>
                              <logic:notEqual name="Order" property="ord_ready_for_deliv_date_all" value="1">
                                <td>
                                  <table cellpadding="0" cellspacing="0">
                                    <tr>
                                      <td>
                                        <span style="width:250px;color:#008000"><bean:message key="Order.ord_ready_for_deliv_date_legend"/></span>
                                      </td>
                                    </tr>
                                  </table>
                                </td>
                              </logic:notEqual>
                            </tr>
                          </table>
                        </td>
                      </tr>
                      <logic:equal name="Order" property="ord_ready_for_deliv_date_all" value="1">
                        <tr>
                          <td>
                            <table>
                              <tr>
                                <td align="right" width="100%"><bean:message key="Order.shippingDocType"/></td>
                                <td>
                                  <ctrl:serverList property="shippingDocType.name" idName="shippingDocType.id" action="/ShippingDocTypesListAction"
                                                   style="width:230px;" readonly="${Order.readOnlyIfNotLikeLogist}"/>
                                </td>
                              </tr>
                            </table>
                          </td>
                        </tr>
                        <tr>
                          <td>
                            <table>
                              <tr>
                                <td align="right" width="100%"><bean:message key="Order.ord_shp_doc_number"/></td>
                                <td><ctrl:text property="ord_shp_doc_number" style="width:251px;" readonly="${Order.readOnlyIfNotLikeLogist}"/></td>
                              </tr>
                            </table>
                          </td>
                        </tr>
                        <tr>
                          <td>
                            <table>
                              <tr>
                                <td align="right" width="100%"><bean:message key="Order.ord_ship_from_stock"/></td>
                                <td><ctrl:date property="ord_ship_from_stock" style="width:230px;" readonly="${Order.readOnlyIfNotLikeLogist}"/></td>
                              </tr>
                            </table>
                          </td>
                        </tr>
                        <tr>
                          <td>
                            <table>
                              <tr>
                                <td align="right" width="100%"><bean:message key="Order.ord_arrive_in_lithuania"/></td>
                                <td><ctrl:date property="ord_arrive_in_lithuania" style="width:230px;" readonly="${Order.readOnlyIfNotLikeLogistOrUIL}"/></td>
                              </tr>
                            </table>
                          </td>
                        </tr>
                      </logic:equal>
                    </logic:equal>
                    <tr>
                      <td>
                        <table>
                          <tr>
                            <td width="100%"><bean:message key="Order.ord_executed"/></td>
                            <td>
                              <table cellpadding="0" cellspacing="0">
                                <tr>
                                  <td>
                                    <ctrl:date property="ord_executed_date" style="width:230px;" readonly="${Order.readOnlyIfNotLikeLogist}"/>
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
                <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
                <td valign="top">
                  <table cellpadding="0" cellspacing="0">
                    <tr>
                      <td>
                        <table>
                          <tr>
                            <td><bean:message key="Order.ord_comment"/></td>
                          </tr>
                          <tr>
                            <td><ctrl:textarea property="ord_comment" style="width:332px;height:175px;"
                                               readonly="${Order.readOnlyIfNotLikeLogist}" showHelp="false"/></td>
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

        <tr>
          <td>
            <table width="100%">
              <tr>
                <td>
                  <bean:message key="Order.ord_annul"/>&nbsp;
                  <ctrl:checkbox property="ord_annul" styleClass="checkbox" value="1" readonly="${Order.readOnlyIfNotLikeLogist}"/>
                </td>
              </tr>
            </table>
          </td>
        </tr>

        <tr>
          <td >
            <table cellspacing="2" width="100%">
              <tr>
                <td>
                  <bean:message key="Order.attachments"/>
                </td>
              </tr>
              <tr>
                <td>
                  <div class="gridBack">
                    <grid:table property="attachmentsGrid" key="idx" >
                      <grid:column><jsp:attribute name="title"><bean:message key="Attachments.name"/></jsp:attribute></grid:column>
                      <logic:equal name="Order" property="readOnlyIfNotLikeLogist" value="false">
                        <grid:column width="1%" title=""/>
                      </logic:equal>
                      <grid:row>
                        <grid:colCustom><a href="" onclick="return downloadAttachment(${record.idx});">${record.originalFileName}</a></grid:colCustom>
                        <logic:equal name="Order" property="readOnlyIfNotLikeLogist" value="false">
                          <grid:colDelete width="1%" dispatch="deleteAttachment" scriptUrl="attachmentId=${record.idx}" type="submit" tooltip="tooltip.Attachments.delete" askUser="deleteAttachAsk"/>
                        </logic:equal>
                      </grid:row>
                    </grid:table>
                  </div>
                  <div class=gridBottom>
                    <ctrl:ubutton type="submit" dispatch="deferredAttach" styleClass="width80" readonly="${Order.readOnlyIfNotLikeLogistOrManager}" >
                      <bean:message key="button.attach"/>
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
          <td>
            <table width="100%">
              <tr>
                <td><bean:message key="Order.payments"/></td>
              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td>
            <table width="100%">
              <tr>
                <td valign="top" width="750px">
                  <table>
                    <tr>
                      <td>
                        <bean:message key="Order.ord_payments"/>
                      </td>
                    </tr>
                    <tr>
                      <td id="payments" valign="top" width="750px">
                        <script type="text/javascript" language="JScript">
                            doAjax({
                              synchronous:true,
                              url:'<ctrl:rewrite action="/OrderAction" dispatch="ajaxOrderPaymentsGrid"/>',
                              update:'payments'
                            });
                        </script>
                      </td>
                    </tr>
                  </table>
                </td>
                <td valign="top" style="border-left:black solid 2px;">
                  <table>
                    <tr>
                      <td>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <bean:message key="Order.ord_pay_sums"/>
                      </td>
                    </tr>
                    <tr>
                      <td id="paySums">
                        <script type="text/javascript" language="JScript">
                            doAjax({
                              synchronous:true,
                              url:'<ctrl:rewrite action="/OrderAction" dispatch="ajaxOrderPaySumsGrid"/>',
                              update:'paySums'
                            });
                        </script>
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
        <tr>
          <td>
            <table width="100%">
              <tr>
                <td><bean:message key="Order.coveringLetter"/></td>
              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td>
            <table width="100%">
              <tr>
                <td valign="top" width="750px">
                  <table>
                    <tr>
                      <td>
                        <bean:message key="Order.ord_comment_covering_letter"/>
                      </td>
                      <td>
	                      <ctrl:textarea property="ord_comment_covering_letter" style="width:600px;height:80px;" readonly="${Order.readOnlyForCoveringLetter}"/>
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
        <tr>
          <td>
            <table width="100%">
              <tr>
                <td><bean:message key="Order.print_params"/></td>
              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td>
            <table width="100%">
              <tr>
                <td>
                  <bean:message key="Order.show_unit"/>&nbsp;
                  <ctrl:checkbox property="show_unit" styleClass="checkbox" value="1" readonly="false" showHelp="false"/>&nbsp;&nbsp;&nbsp;&nbsp;
                  <bean:message key="Order.merge_positions"/>&nbsp;
                  <ctrl:checkbox property="merge_positions" styleClass="checkbox" value="1" readonly="false"/>
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
          <td colspan="4" align="right">
            <table border="0" cellSpacing="0" cellPadding="0">
              <tr>
                <td>&nbsp;&nbsp;
                  <ctrl:ubutton styleId="generateButtonLetter" type="submit" dispatch="printLetter" styleClass="width240" readonly="false" askUser="">
                    <bean:message key="button.printLetter"/>
                  </ctrl:ubutton>
                  &nbsp;
                  <ctrl:text property="ord_letter_scale" style="width:40px;text-align:right;" readonly="false"/>
                  <bean:message key="Common.percent"/>
                  &nbsp;&nbsp;
                </td>
                <td>
                  <ctrl:ubutton styleId="printButton" type="submit" dispatch="print" styleClass="width80" readonly="false" askUser="">
                    <bean:message key="button.print"/>
                  </ctrl:ubutton>
                  &nbsp;
                  <ctrl:text property="ord_print_scale" style="width:40px;text-align:right;" readonly="false"/>
                  <bean:message key="Common.percent"/>
                  &nbsp;&nbsp;
                </td>
                <td>
                  <ctrl:ubutton type="submit" dispatch="back" styleClass="width80" readonly="false" askUser="">
                    <bean:message key="button.cancel"/>
                  </ctrl:ubutton>&nbsp;&nbsp;
                </td>
                <td>
                  <input id='buttonSave' type='button' onclick='processClick()' class='width80' value='<bean:message key="button.save"/>'/>
                </td>
              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td align="right">
            <table border="0" cellSpacing="0" cellPadding="0">
              <tr>
                <td>
                  <span style="color:#008000" id="printLegend"></span>
                </td>
              </tr>
            </table>
          </td>
        </tr>
        </table>
      </td>
    </tr>
  </table>
  </ctrl:form>

  <script language="JScript" type="text/javascript">

    var contractorId = document.getElementById("contractor.id");
    var contactPersonName = document.getElementById("contact_person.cps_name");
    var contactPersonNameBtn = document.getElementById("contact_person.cps_nameBtn");
    var addNewPersonBtn = document.getElementById("addNewPerson");
    var contactPersonId = document.getElementById("contact_person.cps_id");

    var generateButtonLetter = document.getElementById("generateButtonLetter");
    var printButton = document.getElementById("printButton");

    var ordInOneSpec = document.getElementById("ord_in_one_spec");

    var ordDoNotCalculateNetto = document.getElementById("ord_donot_calculate_netto");
    var doNotCalculateNetto = document.getElementById("donot_calculate_netto");
    var calculateNetto = document.getElementById("calculate_netto");
  <logic:notEqual name="Order" property="ord_donot_calculate_netto" value="1">
    var ordDiscountAll = document.getElementById("ord_discount_all");
    var ordDiscount = document.getElementById("ord_discount");
    var ordCountItogFlag = document.getElementById("ord_count_itog_flag");
  </logic:notEqual>

    var currencyName = document.getElementById("currency.name");

    var ordIncludeNds = document.getElementById("ord_include_nds");
    var ordNdsRate = document.getElementById("ord_nds_rate");

    var stuffCategoryId = document.getElementById("stuffCategory.id");
    var buttonAddProduce = document.getElementById("buttonAddProduce");
    var buttonImportProduce1 = document.getElementById("buttonImportProduce1");
    var buttonImportProduce2 = document.getElementById("buttonImportProduce2");

    var deliveryConditionName = document.getElementById("deliveryCondition.name");
    var ordDeliveryCostBy = document.getElementById("ord_delivery_cost_by");
    var ordDeliveryCost = document.getElementById("ord_delivery_cost");

    <logic:equal name="Order" property="ord_date_conf_all" value="1">
      var ordReadyForDeliveryDateAll = document.getElementById("ord_ready_for_deliv_date_all");
      var ordReadyForDeliveryDate = document.getElementById("ord_ready_for_deliv_date");
      var shippingDocTypeId = document.getElementById("shippingDocType.id");
      var shippingDocTypeName = document.getElementById("shippingDocType.name");
      var ordShpDocNumber = document.getElementById("ord_shp_doc_number");
    </logic:equal>

    <logic:notEqual name="Order" property="formReadOnly" value="true">
      <logic:notEqual name="Order" property="readOnlyIfNotLikeManager" value="true">
        onStuffCategorySelect();
      </logic:notEqual>
    </logic:notEqual>

    <logic:equal name="Order" property="ord_in_one_spec" value="1">
	    var sellerName = document.getElementById("seller.name");
	    var sellerId = document.getElementById("seller.id");
	    var isResident = document.getElementById("seller.resident");
	    var contractorForName = document.getElementById("contractor_for.name");
	    var contractorForId = document.getElementById("contractor_for.id");

	    var contractNumber = document.getElementById("contractNumberWithDate");
      var contractNumberBtn = document.getElementById("contractNumberWithDateBtn");
	    var contractId = document.getElementById("contract.con_id");
	    var specificationNumber = document.getElementById("specificationNumberWithDate");
      var specificationNumberBtn = document.getElementById("specificationNumberWithDateBtn");
	    var specificationId = document.getElementById("specification.spc_id");

      <logic:equal name="Order" property="formReadOnly" value="true">
        var cleanButton = document.getElementById("cleanButton");
        cleanButton.disabled = true;
      </logic:equal>

      <logic:equal name="Order" property="readOnlyIfNotLikeManager" value="true">
        var cleanButton = document.getElementById("cleanButton");
        cleanButton.disabled = true;
      </logic:equal>

      var ordAllIncludeInSpec = document.getElementById("ord_all_include_in_spec");
    </logic:equal>

    var ordAllIncludeInSpecScope = document.getElementById("ordAllIncludeInSpecScope");

    defineAttributesAndObligatoryFields();

    <logic:equal name="Order" property="readOnlyIfNotLikeManager" value="false">
      isContractSelected();
    </logic:equal>

    var ordSum = document.getElementById("ord_summ");
    var noRoundSum = document.getElementById("noRoundSum");
 
    var buttonSave = document.getElementById("buttonSave");
    buttonSave.disabled = ${Order.readOnlySave};

    setDeliveryConditionDepField(deliveryConditionName.value);

    function processClick()
    {
      doAjax({
        url:'<ctrl:rewrite action="/OrderAction" dispatch="ajaxCheckSave"/>',
        synchronous:true,
        update:'resultMsg'
      });

      var resultMsg = document.getElementById('resultMsg').innerHTML;
      if ( resultMsg != '' )
      {
        if (!isUserAgree(resultMsg, true, 500, 180))
        {
          return;
        }
      }

      var validatePayments = checkPaymentsGraphic(true);
      var validatePaySums = checkPaySumsGraphic(true);

      if ( validatePayments && validatePaySums )
      {
        submitDispatchForm("process");
      }
    }

    var onLoadContactPerson = true;
    onContactPersonSelect(null);

    recalculatePaymentGrid();

    function onContactPersonSelect(arg)
    {
      if (!onLoadContactPerson)
      {
        contactPersonId.value = arg?arg.arguments[0]:null;
      }
      var contactPersonPhone = onLoadContactPerson ? document.getElementById('idContactPersonPhone').innerHTML : arg?arg.arguments[2]:null;
      contactPersonPhone = contactPersonPhone + ((contactPersonPhone.length > 0) ? ',' : '');
      document.getElementById('idContactPersonPhone').innerHTML = contactPersonPhone;

      var contactPersonFax = onLoadContactPerson ? document.getElementById('idContactPersonFax').innerHTML : arg?arg.arguments[3]:null;
      contactPersonFax = contactPersonFax + ((contactPersonFax.length > 0) ? ',' : '');
      document.getElementById('idContactPersonFax').innerHTML = contactPersonFax;

      var contactPersonEmail = onLoadContactPerson ? document.getElementById('idContactPersonEmail').innerHTML : arg?arg.arguments[4]:null;
      document.getElementById('idContactPersonEmail').innerHTML = contactPersonEmail;

      if (document.getElementById('idContactPersonEmail').innerHTML.length > 0)
      {
        var contractorEmail = document.getElementById('idContractorEmail').innerHTML;
        contractorEmail = contractorEmail + ((contractorEmail.length > 0) ? ',' : '');
        document.getElementById('idContractorEmail').innerHTML = contractorEmail;
      }
      
      onLoadContactPerson = false;
    }

    function submitReloadFormBlank()
    {
      submitDispatchForm("reloadBlank");
    }

    function submitReloadIsWarn()
    {
      submitDispatchForm("loadIsWarn");
    }

    function submitReload()
    {
      submitDispatchForm("reload");
    }

    function onDeliveryConditionSelect(arg)
    {
      setDeliveryConditionDepField(arg?arg.arguments[2]:null);
    }

    function setDeliveryConditionDepField(deliveryConditionName)
    {
      var elementsDisabled = true;
      if (deliveryConditionName)
      {
        if (
                deliveryConditionName == 'CIP' ||
                deliveryConditionName == 'CPT' ||
                deliveryConditionName == 'CIP_2010' ||
                deliveryConditionName == 'CPT_2010'
           )
        {
          elementsDisabled = ${Order.readOnlyIfNotLikeManager};
        }
      }

      ordDeliveryCostBy.disabled = elementsDisabled;
      ordDeliveryCost.disabled = elementsDisabled;  
    }

    function isContractSelected()
    {
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

    function onContractorSelect(arg)
    {
      document.getElementById('idContractorEmail').innerHTML = arg?arg.arguments[2]:null;
      contactPersonName.value = "";
      contactPersonId.value = "";
      document.getElementById('idContactPersonPhone').innerHTML = "";
      document.getElementById('idContactPersonFax').innerHTML = "";
      document.getElementById('idContactPersonEmail').innerHTML = "";
      isContractSelected();
    }

    function onSellerSelect(arg)
    {
	    isResident.value = arg?arg.arguments[4]:null;

	    onContractorForSelect();
    }

    function onContractorForSelect()
    {
      contractNumber.value = "";
      contractId.value = "";
      specificationId.value = "";
      specificationNumber.value = "";

      $(conCopyId).update("");
      $(spcCopyId).update("");

      showPrintLegend();
	    defineAttributesAndObligatoryFields();

      generateButtonLetter.disabled = true;
      printButton.disabled = true;
    }

    function onGridContractorForSelect(arg)
    {
	    document.getElementById("orderProducesGrid.row("+arg.data+").contract.con_id").value = "";
	    document.getElementById("orderProducesGrid.row("+arg.data+").contract.con_number").value = "";
	    document.getElementById("orderProducesGrid.row("+arg.data+").specification.spc_id").value = "";
	    document.getElementById("orderProducesGrid.row("+arg.data+").specification.spc_number").value = "";
    }

    function onStuffCategorySelect()
    {
      if ( stuffCategoryId.value == '' )
      {
        buttonAddProduce.disabled = true;
        buttonImportProduce1.disabled = true;
        buttonImportProduce2.disabled = true;
      }
      else
      {
        buttonAddProduce.disabled = false;
        buttonImportProduce1.disabled = false;
        buttonImportProduce2.disabled = false;
      }
    }

    function doNotCalculateNettoOnClick()
    {
	    ordDoNotCalculateNetto.value = doNotCalculateNetto.checked ? '1' : '';
	    calculateNetto.checked = !doNotCalculateNetto.checked;
      checkCalculated();
    }

    function calculateNettoOnClick()
    {
	    doNotCalculateNetto.checked = !calculateNetto.checked;
	    ordDoNotCalculateNetto.value = doNotCalculateNetto.checked ? '1' : '';
      checkCalculated();
    }

    function checkCalculated()
    {
      if ( calculateNetto.checked )
      {
        var reload = true;
        if ( ${Order.showAskClearTable} )
        {
          if (!isUserAgree('<bean:message key="msg.order.lost_data"/>', true, 400, 100))
          {
            reload = false;
	          calculateNetto.checked = false;
	          doNotCalculateNetto.checked = !calculateNetto.checked;
	          ordDoNotCalculateNetto.value = doNotCalculateNetto.checked ? '1' : '';
          }
        }
        if (reload)
        {
          submitDispatchForm("reloadCalculate");
        }
      }
      else
      {
        submitDispatchForm("reloadDontCalculate");
      }
    }

    <logic:notEqual name="Order" property="ord_donot_calculate_netto" value="1">
      <logic:notEqual name="Order" property="readOnlyIfNotLikeManager" value="true">
        discountAllOnClick();
      </logic:notEqual>

      function discountAllOnClick()
      {
        if ( ordDiscountAll.checked )
        {
	        ordDiscount.disabled = false;
	        ordCountItogFlag.disabled = false;
        }
        else
        {
	        ordDiscount.disabled = true;
	        ordCountItogFlag.disabled = true;
        }
      }
    </logic:notEqual>

    function includeNdsOnClick()
    {
      if ( ordIncludeNds.checked )
      {
	      ordNdsRate.disabled = false;
      }
      else
      {
	      ordNdsRate.disabled = true;
	      ordNdsRate.value = '0';
      }

      submitDispatchForm("reloadIncludeNDS");
    }

    function submitReloadIncludeNDS()
    {
      submitDispatchForm("reloadIncludeNDS");
    }

    function onCurrencySelect()
    {
      var currencyEl = document.getElementById("ord_delivery_cost_currency");
      if (currencyEl)
      {
        currencyEl.innerHTML = currencyName.value;
      }
    }

    function cleanFieldsClick()
    {
	    sellerName.value = "";
	    sellerId.value = "";
	    contractorForName.value = "";
      contractorForId.value = "";

	    onContractorForSelect();
    }

    function ordInOneSpecClick()
    {
      if ( !ordInOneSpec.checked )
      {
        submitDispatchForm("reloadInOneSpec");
      }
      else
      {
        submitReload();
      }
    }

    function ordDateConfAllClickSubmit()
    {
      submitDispatchForm("reloadOrdDateConfAll");
    }

    function ordReadyForDeliveryDateAllClick()
    {
      if ( !ordReadyForDeliveryDateAll.checked )
      {
	      ordReadyForDeliveryDate.value = '';
	      shippingDocTypeId.value = '';
	      shippingDocTypeName.value = '';
	      ordShpDocNumber.value = '';
      }
      submitDispatchForm("reloadOrdReadyForDelivDateAll");
    }

    function showPrintLegend()
    {
      if ( specificationId.value == '' && ordInOneSpec.checked )
      {
        $(printLegend).update('${Order.printLegend}');
      }
      else
      {
        $(printLegend).update('');
      }
    }

    function defineAttributesAndObligatoryFields()
    {
      <logic:equal name="Order" property="ord_in_one_spec" value="1">
	      if ( sellerId.value == '' )
        {
          sellerName.className = 'width225-pink';
        }
		    else
		    {
		      sellerName.className = 'width225';
		    }

		    if ( contractorForId.value == '' )
		    {
		      contractorForName.className = 'width225-pink';
		    }
		    else
		    {
		      contractorForName.className = 'width225';
		    }

		    if ( contractId.value == '' )
		    {
		      contractNumber.className = 'width225-pink';
		    }
		    else
		    {
		      contractNumber.className = 'width225';
		    }

		    if ( specificationId.value == '' )
		    {
		      specificationNumber.className = 'width225-pink';
		    }
		    else
		    {
		      specificationNumber.className = 'width225';
		    }

		    if (sellerId.value != '' && contractorForId.value != '')
		    {
			    contractNumber.disabled = false;
		      disableImgButton(contractNumberBtn, false);
			    specificationNumber.disabled = false;
		      disableImgButton(specificationNumberBtn, false);
		    }
		    else
		    {
			    contractNumber.disabled = true;
		      disableImgButton(contractNumberBtn, true);
			    specificationNumber.disabled = true;
		      disableImgButton(specificationNumberBtn, true);
		    }

		    if (isResident.value == '') //если не резидент
		      ordAllIncludeInSpecScope.checked = false;
		    else if (ordAllIncludeInSpecScope.style.display == "none") //или не было видно
	        ordAllIncludeInSpecScope.checked = false;
		    ordAllIncludeInSpecScope.style.display = isResident.value != '' ? "block" : "none";
      </logic:equal>

	    <logic:notEqual name="Order" property="ord_in_one_spec" value="1">
		    var i = 0;
		    var allIsResident = true;
	      for ( ; i < 500; i++ )
	      {
	        var obj = document.getElementById("orderProducesGrid.row(" + i + ").contract.seller.resident");
	        if ( obj && obj.value == ''  )
	        {
		        allIsResident = false;
		        break;
	        }
	      }

	      if (!allIsResident) //если не резидент
		      ordAllIncludeInSpecScope.checked = false;
	      else if (ordAllIncludeInSpecScope.style.display == "none") //или не было видно
		      ordAllIncludeInSpecScope.checked = false;
		    ordAllIncludeInSpecScope.style.display = allIsResident ? "block" : "none";
	    </logic:notEqual>
    }

    function contractAjax(id)
    {
      doAjax({
        url:'<ctrl:rewrite action="/OrderAction" dispatch="ajaxIsContractCopy"/>',
        params:{'contract-id':id},
        update:'conCopyId'
      });
    }

    function contractChanged(arg)
    {
			var id = null;
			if (arg)
      {
				id = arg.arguments[0];
			}
	    contractAjax(id);

			specificationId.value = "";
      specificationNumber.value = "";
	    defineAttributesAndObligatoryFields();
		}
            
    function onGridContractChanged(arg)
    {
	    document.getElementById("orderProducesGrid.row("+arg.data+").contract.con_number").value = arg.arguments[4];
	    document.getElementById("orderProducesGrid.row("+arg.data+").contract.seller.resident").value = arg.arguments[11];
	    document.getElementById("orderProducesGrid.row("+arg.data+").specification.spc_id").value = "";
	    document.getElementById("orderProducesGrid.row("+arg.data+").specification.spc_number").value = "";

	    defineAttributesAndObligatoryFields();
    }

    function onGridSpecificationChanged(arg)
    {
	    document.getElementById("orderProducesGrid.row("+arg.data+").specification.spc_number").value = arg.arguments[2];
    }

    function setContactCopyOnPageLoad()
    {
      contractAjax(<ctrl:write name="Order" property="contract.con_id"/>);
    }

    function specificationAjax(id)
    {
      doAjax({
        url:'<ctrl:rewrite action="/OrderAction" dispatch="ajaxIsSpecificationCopy"/>',
        params:{'specification-id':id},
        update:'spcCopyId'
      });
    }

    function specificationChanged(arg)
    {
			var id = null;
			if (arg)
      {
				id = arg.arguments[0];
			}
	    specificationAjax(id);

			if ( specificationId.value == '' )
      {
        generateButtonLetter.disabled = true;
        printButton.disabled = true;
      }
      else
      {
        generateButtonLetter.disabled = false;
        printButton.disabled = false;
      }

      showPrintLegend();
	    defineAttributesAndObligatoryFields();
    }

    function setSpecificationCopyOnPageLoad()
    {
			specificationAjax(<ctrl:write name="Order" property="specification.spc_id"/>);
    }

    <logic:equal name="Order" property="ord_in_one_spec" value="1">
      initFunctions.push(setContactCopyOnPageLoad);
      initFunctions.push(setSpecificationCopyOnPageLoad);
    </logic:equal>

    function download(id)
    {
	    document.getElementById('for-insert').innerHTML='<iframe src=\'<html:rewrite action="/AttachmentsAction.do?dispatch=download"/>&id='+id+'\' style=\'display:none\' />';
      return false;
    }

    function downloadAttachment(id)
    {
	    document.getElementById('for-insert').innerHTML='<iframe src=\'<html:rewrite action="/OrderAction.do?dispatch=downloadAttachment"/>&attachmentId='+id+'\' style=\'display:none\' />';
      return false;
    }

    function editOrderExecuted()
    {
      if (!${Order.readOnlyForExecuted})
      {
        submitDispatchForm("editExecuted");
      }
    }

    function deleteAllRows()
    {
      if (isUserAgree('<bean:message key="msg.order.clean_table"/>', true, 400,100))
      {
        submitDispatchForm("reloadDeleteAllRows");
      }
    }

    function addToPaymentGrid()
    {
      var params =  $H(Form.serializeElements($$('#payments input'),true));
      params.merge({counter:'0'});
      doAjax({
        url:'<ctrl:rewrite action="/OrderAction" dispatch="ajaxAddToPaymentGrid"/>',
        params:params,
        synchronous:true,
        update:('payments'),
        okCallback:function()
        {
          checkPaymentsGraphic();
        }
      });
    }

    function removeFromPaymentGrid(id)
    {
      var params =  $H(Form.serializeElements($$('#payments input'),true));
      params.merge({counter:'0',id:id});

      doAjax({
        url:'<ctrl:rewrite action="/OrderAction" dispatch="ajaxRemoveFromPaymentGrid"/>',
        params:params,
        synchronous:true,
        update:'payments',
        okCallback:function()
        {
          checkPaymentsGraphic();
        }
      });
    }

    function percentChanged(obj)
    {
      var sumObj = document.getElementById(obj.name.replace('_percent_', '_sum_'));
      var sumStr = getSumForJS(ordSum.value);
      var tmpSum = parseFloat(sumStr) * parseFloat(obj.value.replace(',', '.')) / 100;
      if ( '' == noRoundSum.value )
      {
        tmpSum = parseInt(tmpSum);
      }
      sumObj.value = tmpSum;
      sumObj.value = sumObj.value.replace('.', ',');
      if ( '' == noRoundSum.value )
      {
        sumObj.value += ',00';
      }

      checkPaymentsGraphic();
    }

    function sumOrderPaymentChanged(obj)
    {
      var percentObj = document.getElementById(obj.name.replace('_sum_', '_percent_'));
      var sumStr = getSumForJS(ordSum.value);
      var sum = getSumForJS(obj.value);
      var tmpPercent = parseFloat(sum) * 100 / parseFloat(sumStr);
      percentObj.value = Math.round(tmpPercent * 100000) / 100000;
      percentObj.value = percentObj.value.replace('.', ',');

      checkPaymentsGraphic();
    }

    //фиктивная фукнция вызывается после выбора даты, чтобы не передавала в checkPaymentsGraphic() лишнее
    function checkPaymentsGraphicDate()
    {
      checkPaymentsGraphic();
    }

    function recalcSums()
    {
      var i = 0;
      for ( ; i < 100; i++ )
      {
        var percentObj = document.getElementById('orderPayments['+ i +'].orp_percent_formatted');
        if ( percentObj )
        {
          percentChanged(percentObj);
        }
      }
    }

    function checkPaymentsGraphic(showMsg)
    {
      var sumCheck = parseFloat(getSumForJS(ordSum.value));
      var percentCheck = 100;
      var i = 0;

      var dateCheck = document.getElementById('orderPayments[0].orp_date_formatted');
      var correctSum = true;
      var correctPercent = true;
      var correctDate = true;
      for ( ; i < 100; i++ )
      {
        var percentObj = document.getElementById('orderPayments['+ i +'].orp_percent_formatted');
        var sumObj = document.getElementById('orderPayments['+ i +'].orp_sum_formatted');
        if ( percentObj && sumObj )
        {
          if ( parseFloat(getSumForJS(sumObj.value)) > sumCheck )
          {
            correctSum = false;
          }
          if ( parseFloat(getSumForJS(percentObj.value)) > 100 )
          {
            correctPercent = false;
          }
          if ( i != 0 )
          {
            var dateObj = document.getElementById('orderPayments['+ i +'].orp_date_formatted');
            if ( dateObj && ('' != dateObj.value) )
            {
              if ( getDateForJS(dateCheck.value) > getDateForJS(dateObj.value) )
              {
                correctDate = false;
              }
              dateCheck = dateObj;
            }
          }
        }
      }

      i = 0;
      for ( ; i < 100; i++ )
      {
        var percentObj = document.getElementById('orderPayments['+ i +'].orp_percent_formatted');
        var sumObj = document.getElementById('orderPayments['+ i +'].orp_sum_formatted');
        var dateObj = document.getElementById('orderPayments['+ i +'].orp_date_formatted');
        if ( percentObj && sumObj )
        {
          sumObj.style.backgroundColor = ( !correctSum ) ? 'pink' : 'white';
          percentObj.style.backgroundColor = ( !correctPercent ) ? 'pink' : 'white';
          dateObj.style.backgroundColor = ( !correctDate ) ? 'pink' : 'white';
        }
      }

      if ( showMsg && !correctSum )
      {
        alert('<bean:message key="msg.order.incorrectSum"/>');
        return false;
      }
      if ( showMsg && !correctPercent )
      {
        alert('<bean:message key="msg.order.incorrectPercent"/>');
        return false;
      }
      if ( showMsg && !correctDate )
      {
        alert('<bean:message key="msg.order.incorrectDate"/>');
        return false;
      }

      if ( !showMsg && correctSum && correctPercent && correctDate )
      {
	      recalculatePaymentGrid();
      }

      calculateLastSums();
      return true;
    }

    function calculateLastSums()
    {
      var sumCheck = parseFloat(getSumForJS(ordSum.value));
      var percentCheck = 100;
      var sum = 0;
      var percent = 0;
      var i = 0;

      for ( ; i < 100; i++ )
      {
        var percentObj = document.getElementById('orderPayments['+ i +'].orp_percent_formatted');
        var sumObj = document.getElementById('orderPayments['+ i +'].orp_sum_formatted');
        var j = i + 1;
        var percentObjLast = document.getElementById('orderPayments['+ j +'].orp_percent_formatted');
        var sumObjLast = document.getElementById('orderPayments['+ j +'].orp_sum_formatted');
        if ( percentObjLast && sumObjLast )
        {
          sum += parseFloat(getSumForJS(sumObj.value));
          percent += parseFloat(getSumForJS(percentObj.value));
        }
        else
        {
          percentObj.disabled = true;
          sumObj.disabled = true;

          var tmpSum = sumCheck - sum;
          if ( '' == noRoundSum.value )
          {
            tmpSum = parseInt(tmpSum);
          }
          else
          {
            tmpSum = Math.round(tmpSum * 100) / 100;
          }
          sumObj.value = tmpSum;
          sumObj.value = sumObj.value.replace('.', ',');
          if ( '' == noRoundSum.value )
          {
            sumObj.value += ',00';
          }

          var tmpPercent = Math.round((percentCheck - percent) * 100000) / 100000;
          percentObj.value = tmpPercent;
          percentObj.value = percentObj.value.replace('.', ',');

          break;
        }
      }
    }

    function setDescriptionColour()
    {
      var checkStr = '<bean:message key="Order.checkRedStr"/>';
      var checkStr1 = '<bean:message key="Order.checkRedStr1"/>';
      var checkStr2 = '<bean:message key="Order.checkRedStr2"/>';
      var i = 0;
      for ( ; i < 100; i++ )
      {
        var descriptionObj = document.getElementById('description'+ i);
        if ( descriptionObj )
        {
          var description = descriptionObj.innerHTML;
          if ( description.indexOf('span') == -1 )
          {
            if (
                 description.indexOf(checkStr) != -1 ||
                 ( description.indexOf(checkStr1) != -1 && description.indexOf(checkStr2) == -1 )
               )
            {
              description = '<span style="color:red">' + description + '</span>';
            }
            else
            {
              description = '<span style="color:green">' + description + '</span>';
            }
            descriptionObj.innerHTML = description;
          }
        }
      }
    }

    function recalculatePaymentGrid()
    {
      var params =  $H(Form.serializeElements($$('#payments input'),true));
      doAjax({
        url:'<ctrl:rewrite action="/OrderAction" dispatch="ajaxRecalculatePaymentGrid"/>',
        params:params,
        synchronous:true,
        update:'payments'
      });

      setDescriptionColour();
    }

    function addToPaymSumGrid()
    {
      var params =  $H(Form.serializeElements($$('#paySums input'),true));
      params.merge({counter:'0'});
      doAjax({
        url:'<ctrl:rewrite action="/OrderAction" dispatch="ajaxAddToPaySumGrid"/>',
        params:params,
        synchronous:true,
        update:('paySums'),
        okCallback:function()
        {
          checkPaySumsGraphic();
        }
      });
    }

    function removeFromPaySumGrid(id)
    {
      var params =  $H(Form.serializeElements($$('#paySums input'),true));
      params.merge({counter:'0',id:id});

      doAjax({
        url:'<ctrl:rewrite action="/OrderAction" dispatch="ajaxRemoveFromPaySumsGrid"/>',
        params:params,
        synchronous:true,
        update:'paySums',
        okCallback:function()
        {
          checkPaySumsGraphic();
        }
      });
    }

    function recalcPaySumGrid()
    {
      var params =  $H(Form.serializeElements($$('#paySums input'),true));
      doAjax({
        url:'<ctrl:rewrite action="/OrderAction" dispatch="ajaxRecalcPaySumGrid"/>',
        params:params,
        synchronous:true,
        update:'paySums'
      });
    }

    function sumPayChanged(obj)
    {
      checkPaySumsGraphic();
    }

    //фиктивная фукнция вызывается после выбора даты, чтобы не передавала в checkPaySumsGraphic() лишнее
    function checkPaySumsGraphicDate()
    {
      checkPaySumsGraphic();
    }

    function checkPaySumsGraphic(showMsg)
    {
      if ( !showMsg )
      {
        recalcPaySumGrid();
      }

      var sumCheckOrd = parseFloat(getSumForJS(ordSum.value));
      var sumCheck = 0;
      var i = 0;

      var dateCheck = document.getElementById('orderPaySums[0].ops_date_formatted');
      var correctSum = true;
      var correctDate = true;
      for ( ; i < 100; i++ )
      {
        var sumObj = document.getElementById('orderPaySums['+ i +'].ops_sum_formatted');
        if ( sumObj )
        {
          sumCheck = sumCheck + parseFloat(getSumForJS(sumObj.value));
          if ( sumCheck > sumCheckOrd )
          {
            correctSum = false;
          }
          if ( i != 0 )
          {
            var dateObj = document.getElementById('orderPaySums['+ i +'].ops_date_formatted');
            if ( dateObj && ('' != dateObj.value) )
            {
              if ( getDateForJS(dateCheck.value) > getDateForJS(dateObj.value) )
              {
                correctDate = false;
              }
              dateCheck = dateObj;
            }
          }
        }
      }

      i = 0;
      for ( ; i < 100; i++ )
      {
        var sumObj = document.getElementById('orderPaySums['+ i +'].ops_sum_formatted');
        var dateObj = document.getElementById('orderPaySums['+ i +'].ops_date_formatted');
        if ( sumObj )
        {
          sumObj.style.backgroundColor = ( !correctSum ) ? 'pink' : 'white';
          dateObj.style.backgroundColor = ( !correctDate ) ? 'pink' : 'white';
        }
      }

      if ( showMsg && !correctSum )
      {
        alert('<bean:message key="msg.order.incorrectPaySum"/>');
        return false;
      }
      if ( showMsg && !correctDate )
      {
        alert('<bean:message key="msg.order.incorrectPayDate"/>');
        return false;
      }

      if ( !showMsg )
      {
	      recalculatePaymentGrid();
      }

      return true;
    }

	</script>

  <logic:equal name="Order" property="print" value="true">
		<script language="JScript" type="text/javascript" >
			document.getElementById('for-insert').innerHTML='<iframe src="<ctrl:rewrite action="OrderPrintAction" scriptUrl="ord_id=${Order.ord_id}" />" style="display:none" />';
		</script>
	</logic:equal>
	<logic:equal name="Order" property="printLetter" value="true">
		<script language="JScript" type="text/javascript" >
			document.getElementById('for-insert').innerHTML='<iframe src="<ctrl:rewrite action="CoveringLetterForOrderPrintAction" scriptUrl="ord_id=${Order.ord_id}" />" style="display:none" />';
		</script>
	</logic:equal>
