<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>
<%@ taglib uri="http://ditchnet.org/jsp-tabs-taglib" prefix="tab" %>

<ctrl:form readonly="${contractor.formReadOnly}">
  <ctrl:hidden property="ctr_id"/>
  <ctrl:hidden property="is_new_doc"/>
  <ctrl:hidden property="usr_date_create"/>
  <ctrl:hidden property="usr_date_edit"/>
  <ctrl:hidden property="createUser.usr_name"/>
  <ctrl:hidden property="createUser.usr_surname"/>
  <ctrl:hidden property="editUser.usr_name"/>
  <ctrl:hidden property="editUser.usr_surname"/>
  <ctrl:hidden property="lastNumber"/>
  <ctrl:hidden property="lastNumberAcc"/>

  <table width="870px" class=formBack align="center">

    <logic:notEqual name="contractor" property="is_new_doc" value="true">
      <tr>
        <td>
          <table cellspacing="2">
            <tr>
              <td width="110px"><bean:message key="Contractor.create"/></td>
              <td><ctrl:write name="contractor" property="usr_date_create"/>&nbsp;<ctrl:write name="contractor" property="createUser.userFullName"/></td>
            </tr>
            <tr>
              <td><bean:message key="Contractor.edit"/></td>
              <td><ctrl:write name="contractor" property="usr_date_edit"/>&nbsp;<ctrl:write name="contractor" property="editUser.userFullName"/></td>
            </tr>
          </table>
        </td>
      </tr>
    </logic:notEqual>

    <tr>
      <td>
        <tab:tabContainer id="contractorComtainer" selectedTabPaneId="mainPanel">

          <tab:tabPane id="mainPanel" tabTitle="Contractor.main">
            <table align="center" width="660px">
              <tr>
                <td>
                  <table cellspacing="2" align="center">
                    <tr>
                      <td><bean:message key="Contractor.ctr_name"/></td>
                      <td><ctrl:text property="ctr_name" style="width:400px;"/></td>
                    </tr>
                    <tr>
                      <td><bean:message key="Contractor.ctr_full_name"/></td>
                      <td><ctrl:text property="ctr_full_name" style="width:400px;"/></td>
                    </tr>
                    <tr>
                      <td><bean:message key="Contractor.country"/></td>
                      <td>
                        <table cellspacing="0" cellpadding="0" width="1%">
                          <tr>
                            <td>
                              <ctrl:serverList property="country.name" idName="country.id"
                                               action="/CountriesListAction" style="width:170px;"/>
                            </td>
                            <td>
                              &nbsp;
                              <ctrl:ubutton type="submit" dispatch="addCountry" styleClass="width200">
                                <bean:message key="button.addAbsentInList"/>
                              </ctrl:ubutton>
                            </td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                    <tr>
                      <td valign="top"><bean:message key="Contractor.ctr_address"/></td>
                      <td>
                        <table cellpadding="0" cellspacing="0">
                          <tr>
                            <td>
                              <ctrl:text property="ctr_address" style="width:400px;" readonly="true"/>
                            </td>
                          </tr>
                          <tr>
                            <td>
                              <bean:message key="Contractor.ctr_index"/>&nbsp;
                              <ctrl:text property="ctr_index" style="width:100px;" onchange="onAddressChanged();"/>&nbsp;&nbsp;
                              <bean:message key="Contractor.ctr_region"/>&nbsp;
                              <ctrl:text property="ctr_region" style="width:170px;" onchange="onAddressChanged();"/>
                            </td>
                          </tr>
                          <tr>
                            <td>
                              <bean:message key="Contractor.ctr_place"/>&nbsp;
                              <ctrl:text property="ctr_place" style="width:285px;" onchange="onAddressChanged();"/>
                            </td>
                          </tr>
                          <tr>
                            <td>
                              <bean:message key="Contractor.ctr_street"/>&nbsp;
                              <ctrl:text property="ctr_street" style="width:170px;" onchange="onAddressChanged();"/>&nbsp;&nbsp;
                              <bean:message key="Contractor.ctr_building"/>&nbsp;
                              <ctrl:text property="ctr_building" style="width:95px;" onchange="onAddressChanged();"/>
                            </td>
                          </tr>
                          <tr>
                            <td>
                              <bean:message key="Contractor.ctr_add_info"/>&nbsp;
                              <ctrl:text property="ctr_add_info" style="width:217px;" onchange="onAddressChanged();"/>
                            </td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                    <tr>
                      <td><bean:message key="Contractor.ctr_phone"/></td>
                      <td><ctrl:text property="ctr_phone" style="width:400px;"/></td>
                    </tr>
                    <tr>
                      <td><bean:message key="Contractor.ctr_fax"/></td>
                      <td><ctrl:text property="ctr_fax" style="width:400px;"/></td>
                    </tr>
                    <tr>
                      <td><bean:message key="Contractor.ctr_email"/></td>
                      <td><ctrl:text property="ctr_email" style="width:400px;"/></td>
                    </tr>
                    <tr>
                      <td><bean:message key="Contractor.ctr_unp"/></td>
                      <td><ctrl:text property="ctr_unp" style="width:400px;"/></td>
                    </tr>
                    <tr>
                      <td><bean:message key="Contractor.ctr_okpo"/></td>
                      <td><ctrl:text property="ctr_okpo" style="width:400px;"/></td>
                    </tr>
                    <tr>
                      <td><bean:message key="Contractor.reputation"/></td>
                      <td>
                        <table width="100%" border="0" cellSpacing="0" cellPadding="0">
                          <tr>
                            <td>
                              <ctrl:serverList property="reputation.description" idName="reputation.id"
                                               action="/ReputationsListAction"  style="width:225px;"
                                               readonly="${contractor.readOnlyReputation}"/>
                            </td>
                            <td>&nbsp;</td>
                            <td>
                              <ctrl:ubutton type="submit" dispatch="editReputation" styleClass="width145" readonly="${contractor.readOnlyIfNotAdmin}">
                                <bean:message key="button.editList"/>
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
          </tab:tabPane>

          <tab:tabPane id="usersContractor" tabTitle="Contractor.users">
            <table align="center" width="660px">
              <tr>
                <td>
                  <table align="center" width="250px">
                    <tr>
                      <td>
                        <div class="gridBack">
                          <grid:table property="gridUsers" key="number" scrollableGrid="false" autoLockName="ContractorUsers">
                            <grid:column align="center"><jsp:attribute name="title"><bean:message key="ContractorUsers.user"/></jsp:attribute></grid:column>
                            <grid:column width="1%" title=""/>
                            <grid:row>
                              <grid:colServerList property="user.userFullName" idName="user.usr_id" result="gridUsers"
                                                  resultProperty="user.userFullName" useIndexAsPK="true" action="/UsersListAction"
                                                  selectOnly="true" readonlyCheckerId="readonlyChangeUserForNotAdmin"/>
                              <grid:colDelete width="1%" type="submit" dispatch="deleteRowFromUserGrid" scriptUrl="number=${record.number}"
                                              tooltip="tooltip.ContractorUsers.delete" showCheckerId="showDeleteUserForAdmin"/>
                            </grid:row>
                          </grid:table>
                        </div>
                      </td>
                    </tr>
                    <tr>
                      <td>
                        <div class=gridBottom>
                          <input id='buttonAddUser' type='button' onclick='addUserClick()' class='width80' value='<bean:message key="button.add"/>'/>
                        </div>
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
            </table>
          </tab:tabPane>

          <tab:tabPane id="accountsContractor" tabTitle="Contractor.accounts">
            <table align="center" width="660px">
              <tr>
                <td><bean:message key="Contractor.ctr_bank_props"/></td>
                <td><ctrl:textarea property="ctr_bank_props" style="width:400px;"/></td>
              </tr>
              <tr>
                <td colspan="20">
                  <table align="center" width="100%">
                    <tr>
                      <td>
                        <div class="gridBack">
                          <grid:table property="gridAccounts" key="number" scrollableGrid="false" autoLockName="Accounts">
                            <grid:column width="40%" title=""/>
                            <grid:column align="center"><jsp:attribute name="title"><bean:message key="Accounts.acc_account"/></jsp:attribute></grid:column>
                            <grid:column width="1%" align="center"><jsp:attribute name="title"><bean:message key="Accounts.currency"/></jsp:attribute></grid:column>
                            <grid:column width="1%" title=""/>
                            <grid:row>
                              <grid:colCustom width="40%" property="acc_name"/>
                              <grid:colInput property="acc_account" result="gridAccounts" resultProperty="acc_account" useIndexAsPK="true"/>
                              <grid:colServerList property="currency.name" idName="currency.id" result="gridAccounts"
                                                  resultProperty="currency.name"  useIndexAsPK="true" action="/CurrenciesListAction"
                                                  selectOnly="true"/>
                              <grid:colDelete width="1%" type="submit" dispatch="deleteRowFromAccountGrid" scriptUrl="numberAcc=${record.number}"
                                              tooltip="tooltip.Accounts.delete" showCheckerId="show-delete-checker"/>
                            </grid:row>
                          </grid:table>
                        </div>
                      </td>
                    </tr>
                    <tr>
                      <td>
                        <div class=gridBottom>
                          <ctrl:ubutton type="submit" dispatch="addRowInAccountGrid" styleClass="width80">
                            <bean:message key="button.add"/>
                          </ctrl:ubutton>
                        </div>
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
            </table>
          </tab:tabPane>

          <tab:tabPane id="contactPersonsContractor" tabTitle="Contractor.contactPersons">
            <table align="center" width="900px">
              <tr>
                <td colspan="20">
                  <table align="center" width="100%">
                    <tr>
                      <td>
                        <div class="gridBack">
                          <grid:table property="gridContactPersons" key="number" scrollableGrid="false" autoLockName="ContactPersons">
                            <grid:column align="center"><jsp:attribute name="title"><bean:message key="ContactPersons.cps_name"/></jsp:attribute></grid:column>
                            <grid:column align="center"><jsp:attribute name="title"><bean:message key="ContactPersons.cps_position"/></jsp:attribute></grid:column>
                            <grid:column align="center"><jsp:attribute name="title"><bean:message key="ContactPersons.cps_on_reason"/></jsp:attribute></grid:column>
                            <grid:column align="center"><jsp:attribute name="title"><bean:message key="ContactPersons.cps_phone"/></jsp:attribute></grid:column>
                            <grid:column align="center"><jsp:attribute name="title"><bean:message key="ContactPersons.cps_mob_phone"/></jsp:attribute></grid:column>
                            <grid:column align="center"><jsp:attribute name="title"><bean:message key="ContactPersons.cps_fax"/></jsp:attribute></grid:column>
                            <grid:column align="center"><jsp:attribute name="title"><bean:message key="ContactPersons.cps_email"/></jsp:attribute></grid:column>
                            <grid:column align="center"><jsp:attribute name="title"><bean:message key="ContactPersons.cps_contract_comment"/></jsp:attribute></grid:column>
                            <grid:column align="center" width="1%"><jsp:attribute name="title"><img title='<bean:message key="tooltip.ContactPersons.fired"/>' src='<ctrl:rewrite page="/images/brick.gif"/>'></jsp:attribute></grid:column>
                            <grid:column align="center" width="1%"><jsp:attribute name="title"><img title='<bean:message key="tooltip.ContactPersons.block_status"/>' src='<ctrl:rewrite page="/images/lock.gif"/>'></jsp:attribute></grid:column>
                            <grid:column title=""/>
                            <grid:row>
                              <grid:colCustom property="cps_name"/>
                              <grid:colCustom width="10%" property="cps_position"/>
                              <grid:colCustom width="15%" property="cps_on_reason"/>
                              <grid:colCustom width="10%" property="cps_phone"/>
                              <grid:colCustom width="10%" property="cps_mob_phone"/>
                              <grid:colCustom width="10%" property="cps_fax"/>
                              <grid:colCustom width="10%">
                                <a href="mailto:${record.cps_email}">${record.cps_email}</a>
                              </grid:colCustom>
                              <grid:colCustom width="15%" property="cps_contract_comment"/>
                              <grid:colCheckbox align="center" width="1" property="cps_fire" type="submit" dispatch="fireContactPerson" scriptUrl="cps_fire=${record.cps_fire}"/>
                              <grid:colCheckbox align="center" width="1" property="cps_block" readonlyCheckerId="blockChecker" type="submit" dispatch="blockContactPerson" scriptUrl="cps_block=${record.cps_block}"/>
                              <grid:colEdit width="1" type="submit" dispatch="editPersonInContractor" tooltip="tooltip.ContactPersons.edit"/>
                            </grid:row>
                          </grid:table>
                        </div>
                      </td>
                    </tr>
                    <tr>
                      <td>
                        <div class=gridBottom>
                          <ctrl:ubutton type="submit" dispatch="addPersonInContractor" paramId="ctr_id" paramName="contractor" paramProperty="ctr_id" styleClass="width80" >
                            <bean:message key="button.create"/>
                          </ctrl:ubutton>
                        </div>
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
            </table>
          </tab:tabPane>

          <tab:tabPane id="commentContractor" tabTitle="Contractor.comment">
	          <table align="center" width="660px">
             <tr>
               <td><bean:message key="Contractor.ctr_comment"/></td>
               <td><ctrl:textarea property="ctr_comment" style="width:400px;height:305px;" readonly="${contractor.readOnlyComment}"/></td>
             </tr>
           </table>
          </tab:tabPane>

        </tab:tabContainer>
      </td>
    </tr>
    <tr>
      <td align="right" class=formSpace>
        &nbsp;
      </td>
    </tr>
    <tr>
      <td align="right">
        <ctrl:ubutton type="link" actionForward="back" styleClass="width80" readonly="false">
          <bean:message key="button.cancel"/>
        </ctrl:ubutton>
        <ctrl:ubutton type="submit" dispatch="process" styleClass="width80">
          <bean:message key="button.save"/>
        </ctrl:ubutton>
      </td>
    </tr>
  </table>

</ctrl:form>

<script language="JScript" type="text/javascript">
  var buttonAddUser = document.getElementById("buttonAddUser");
  buttonAddUser.disabled = ${contractor.formReadOnly};
  if (!buttonAddUser.disabled)
  {
    buttonAddUser.disabled = ${contractor.addUserReadOnly};  
  }

  <logic:equal name="contractor" property="fillActivePanel" value="true">
    org.ditchnet.jsp.TabUtils.selectPanelById('${contractor.activePanelName}');
  </logic:equal>

  function onAddressChanged()
  {
    var retStr = '';
    retStr += document.getElementById('ctr_index').value == '' ? '' : document.getElementById('ctr_index').value + ', ';
    retStr += document.getElementById('ctr_region').value == '' ? '' : document.getElementById('ctr_region').value + ', ';
    retStr += document.getElementById('ctr_place').value == '' ? '' : document.getElementById('ctr_place').value + ', ';
    retStr += document.getElementById('ctr_street').value == '' ? '' : document.getElementById('ctr_street').value + ', ';
    retStr += document.getElementById('ctr_building').value == '' ? '' : document.getElementById('ctr_building').value + ', ';
    retStr += document.getElementById('ctr_add_info').value == '' ? '' : document.getElementById('ctr_add_info').value;
    if (document.getElementById('ctr_add_info').value == '' && retStr != '')
    {
      retStr = retStr.substring(0, retStr.length - 2);
    }
    document.getElementById('ctr_address').value = retStr;
  }


  function addUserClick()
  {
    if (!${contractor.adminRole})
    {
      if (!isUserAgree('<bean:message key="msg.contractor.askUserForAddToList"/>', true, 400,100))
      {
        return;
      }
    }

    submitDispatchForm("addRowInUserGrid");
  }
</script>