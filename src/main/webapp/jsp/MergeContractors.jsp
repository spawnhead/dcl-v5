<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>

<ctrl:form>
  <ctrl:hidden property="oldId"/>
  <ctrl:hidden property="newId"/>
  <table class=formBack align="center">
    <tr>
      <td colspan="2">
        <table cellspacing="0" cellpadding="0" border="0">
          <tr>
            <td  colspan="2">
              <bean:message key="MergeContractors.textOver"/>
            </td>
          </tr>

          <tr>
            <td colspan="2" align="right" class=formSpace>
              &nbsp;
            </td>
          </tr>

          <tr>
            <td colspan="2">
              <table cellspacing="0" cellpadding="0" border="0">
                <tr>
                  <td>
                    <table cellspacing="5" cellpadding="0" border="0">
                      <tr>
                        <td>
                          <ctrl:checkbox property="leftName" styleClass="checkbox" value="1" onclick="onLeftClick(this)"/>
                        </td>
                        <td width="150px">
                          <bean:message key="MergeContractors.ctr_name_left"/>
                        </td>
                        <td>
                          <ctrl:text property="contractorLeft.name" style="width:300px;"/>
                        </td>
                      </tr>

                      <tr>
                        <td>
                          <ctrl:checkbox property="leftFullName" styleClass="checkbox" value="1" onclick="onLeftClick(this)"/>
                        </td>
                        <td>
                          <bean:message key="MergeContractors.ctr_full_name_left"/>
                        </td>
                        <td>
                          <ctrl:text property="contractorLeft.fullname" style="width:300px;"/>
                        </td>
                      </tr>

                      <tr>
                        <td>
                          <ctrl:checkbox property="leftIndex" styleClass="checkbox" value="1" onclick="onLeftClick(this)"/>
                        </td>
                        <td>
                          <bean:message key="MergeContractors.ctr_index_left"/>
                        </td>
                        <td>
                          <ctrl:text property="contractorLeft.index" style="width:300px;"/>
                        </td>
                      </tr>

                      <tr>
                        <td>
                          <ctrl:checkbox property="leftRegion" styleClass="checkbox" value="1" onclick="onLeftClick(this)"/>
                        </td>
                        <td>
                          <bean:message key="MergeContractors.ctr_region_left"/>
                        </td>
                        <td>
                          <ctrl:text property="contractorLeft.region" style="width:300px;"/>
                        </td>
                      </tr>

                      <tr>
                        <td>
                          <ctrl:checkbox property="leftPlace" styleClass="checkbox" value="1" onclick="onLeftClick(this)"/>
                        </td>
                        <td>
                          <bean:message key="MergeContractors.ctr_place_left"/>
                        </td>
                        <td>
                          <ctrl:text property="contractorLeft.place" style="width:300px;"/>
                        </td>
                      </tr>

                      <tr>
                        <td>
                          <ctrl:checkbox property="leftStreet" styleClass="checkbox" value="1" onclick="onLeftClick(this)"/>
                        </td>
                        <td>
                          <bean:message key="MergeContractors.ctr_street_left"/>
                        </td>
                        <td>
                          <ctrl:text property="contractorLeft.street" style="width:300px;"/>
                        </td>
                      </tr>

                      <tr>
                        <td>
                          <ctrl:checkbox property="leftBuilding" styleClass="checkbox" value="1" onclick="onLeftClick(this)"/>
                        </td>
                        <td>
                          <bean:message key="MergeContractors.ctr_building_left"/>
                        </td>
                        <td>
                          <ctrl:text property="contractorLeft.building" style="width:300px;"/>
                        </td>
                      </tr>

                      <tr>
                        <td>
                          <ctrl:checkbox property="leftAddInfo" styleClass="checkbox" value="1" onclick="onLeftClick(this)"/>
                        </td>
                        <td>
                          <bean:message key="MergeContractors.ctr_add_info_left"/>
                        </td>
                        <td>
                          <ctrl:text property="contractorLeft.addInfo" style="width:300px;"/>
                        </td>
                      </tr>

                      <tr>
                        <td>
                          <ctrl:checkbox property="leftPhone" styleClass="checkbox" value="1" onclick="onLeftClick(this)"/>
                        </td>
                        <td>
                          <bean:message key="MergeContractors.ctr_phone_left"/>
                        </td>
                        <td>
                          <ctrl:text property="contractorLeft.phone" style="width:300px;"/>
                        </td>
                      </tr>

                      <tr>
                        <td>
                          <ctrl:checkbox property="leftFax" styleClass="checkbox" value="1" onclick="onLeftClick(this)"/>
                        </td>
                        <td>
                          <bean:message key="MergeContractors.ctr_fax_left"/>
                        </td>
                        <td>
                          <ctrl:text property="contractorLeft.fax" style="width:300px;"/>
                        </td>
                      </tr>

                      <tr>
                        <td>
                          <ctrl:checkbox property="leftEMail" styleClass="checkbox" value="1" onclick="onLeftClick(this)"/>
                        </td>
                        <td>
                          <bean:message key="MergeContractors.ctr_email_left"/>
                        </td>
                        <td>
                          <ctrl:text property="contractorLeft.email" style="width:300px;"/>
                        </td>
                      </tr>

                      <tr>
                        <td>
                          <ctrl:checkbox property="leftBank" styleClass="checkbox" value="1" onclick="onLeftClick(this)"/>
                        </td>
                        <td>
                          <bean:message key="MergeContractors.ctr_bank_props_left"/>
                        </td>
                        <td>
                          <ctrl:text property="contractorLeft.bank_props" style="width:300px;"/>
                        </td>
                      </tr>

                      <tr>
                        <td>
                          <ctrl:checkbox property="leftUNP" styleClass="checkbox" value="1" onclick="onLeftClick(this)"/>
                        </td>
                        <td>
                          <bean:message key="MergeContractors.ctr_unp_left"/>
                        </td>
                        <td>
                          <ctrl:text property="contractorLeft.unp" style="width:300px;"/>
                        </td>
                      </tr>

                      <tr>
                        <td>
                          <ctrl:checkbox property="leftOKPO" styleClass="checkbox" value="1" onclick="onLeftClick(this)"/>
                        </td>
                        <td>
                          <bean:message key="MergeContractors.ctr_okpo_left"/>
                        </td>
                        <td>
                          <ctrl:text property="contractorLeft.okpo" style="width:300px;"/>
                        </td>
                      </tr>

                      <tr>
                        <td>
                          <ctrl:checkbox property="leftReputation" styleClass="checkbox" value="1" onclick="onLeftClick(this)"/>
                        </td>
                        <td>
                          <bean:message key="MergeContractors.reputation_left"/>
                        </td>
                        <td>
                          <ctrl:serverList property="contractorLeft.reputation.description"
                                           idName="contractorLeft.reputation.id"
                                           action="/ReputationsListAction"
                                           style="width:225px;"/>
                        </td>
                      </tr>

                      <tr>
                        <td>
                          <ctrl:checkbox property="leftCountry" styleClass="checkbox" value="1" onclick="onLeftClick(this)"/>
                        </td>
                        <td>
                          <bean:message key="MergeContractors.country_left"/>
                        </td>
                        <td>
                          <ctrl:serverList property="contractorLeft.country.name"
                                           idName="contractorLeft.country.id"
                                           action="/CountriesListAction"
                                           style="width:225px;"/>
                        </td>
                      </tr>

                      <tr>
                        <td>
                          <ctrl:checkbox property="leftComment" styleClass="checkbox" value="1" onclick="onLeftClick(this)"/>
                        </td>
                        <td>
                          <bean:message key="MergeContractors.comment_left"/>
                        </td>
                        <td>
	                        <ctrl:textarea property="contractorLeft.comment" style="width:300px;height:305px;"/>
                        </td>
                      </tr>
                    </table>
                  </td>

                  <td width="40px">
                    &nbsp;
                  </td>

                  <td>
                    <table cellspacing="5" cellpadding="0" border="0">
                      <tr>
                        <td>
                          <ctrl:checkbox property="rightName" styleClass="checkbox" value="1" onclick="onRightClick(this)"/>
                        </td>
                        <td width="150px">
                          <bean:message key="MergeContractors.ctr_name_right"/>
                        </td>
                        <td>
                          <ctrl:text property="contractorRight.name" style="width:300px;"/>
                        </td>
                      </tr>

                      <tr>
                        <td>
                          <ctrl:checkbox property="rightFullName" styleClass="checkbox" value="1" onclick="onRightClick(this)"/>
                        </td>
                        <td>
                          <bean:message key="MergeContractors.ctr_full_name_right"/>
                        </td>
                        <td>
                          <ctrl:text property="contractorRight.fullname" style="width:300px;"/>
                        </td>
                      </tr>

                      <tr>
                        <td>
                          <ctrl:checkbox property="rightIndex" styleClass="checkbox" value="1" onclick="onRightClick(this)"/>
                        </td>
                        <td>
                          <bean:message key="MergeContractors.ctr_index_right"/>
                        </td>
                        <td>
                          <ctrl:text property="contractorRight.index" style="width:300px;"/>
                        </td>
                      </tr>

                      <tr>
                        <td>
                          <ctrl:checkbox property="rightRegion" styleClass="checkbox" value="1" onclick="onRightClick(this)"/>
                        </td>
                        <td>
                          <bean:message key="MergeContractors.ctr_region_right"/>
                        </td>
                        <td>
                          <ctrl:text property="contractorRight.region" style="width:300px;"/>
                        </td>
                      </tr>

                      <tr>
                        <td>
                          <ctrl:checkbox property="rightPlace" styleClass="checkbox" value="1" onclick="onRightClick(this)"/>
                        </td>
                        <td>
                          <bean:message key="MergeContractors.ctr_place_right"/>
                        </td>
                        <td>
                          <ctrl:text property="contractorRight.place" style="width:300px;"/>
                        </td>
                      </tr>

                      <tr>
                        <td>
                          <ctrl:checkbox property="rightStreet" styleClass="checkbox" value="1" onclick="onRightClick(this)"/>
                        </td>
                        <td>
                          <bean:message key="MergeContractors.ctr_street_right"/>
                        </td>
                        <td>
                          <ctrl:text property="contractorRight.street" style="width:300px;"/>
                        </td>
                      </tr>

                      <tr>
                        <td>
                          <ctrl:checkbox property="rightBuilding" styleClass="checkbox" value="1" onclick="onRightClick(this)"/>
                        </td>
                        <td>
                          <bean:message key="MergeContractors.ctr_building_right"/>
                        </td>
                        <td>
                          <ctrl:text property="contractorRight.building" style="width:300px;"/>
                        </td>
                      </tr>

                      <tr>
                        <td>
                          <ctrl:checkbox property="rightAddInfo" styleClass="checkbox" value="1" onclick="onRightClick(this)"/>
                        </td>
                        <td>
                          <bean:message key="MergeContractors.ctr_add_info_right"/>
                        </td>
                        <td>
                          <ctrl:text property="contractorRight.addInfo" style="width:300px;"/>
                        </td>
                      </tr>

                      <tr>
                        <td>
                          <ctrl:checkbox property="rightPhone" styleClass="checkbox" value="1" onclick="onRightClick(this)"/>
                        </td>
                        <td>
                          <bean:message key="MergeContractors.ctr_phone_right"/>
                        </td>
                        <td>
                          <ctrl:text property="contractorRight.phone" style="width:300px;"/>
                        </td>
                      </tr>

                      <tr>
                        <td>
                          <ctrl:checkbox property="rightFax" styleClass="checkbox" value="1" onclick="onRightClick(this)"/>
                        </td>
                        <td>
                          <bean:message key="MergeContractors.ctr_fax_right"/>
                        </td>
                        <td>
                          <ctrl:text property="contractorRight.fax" style="width:300px;"/>
                        </td>
                      </tr>

                      <tr>
                        <td>
                          <ctrl:checkbox property="rightEMail" styleClass="checkbox" value="1" onclick="onRightClick(this)"/>
                        </td>
                        <td>
                          <bean:message key="MergeContractors.ctr_email_right"/>
                        </td>
                        <td>
                          <ctrl:text property="contractorRight.email" style="width:300px;"/>
                        </td>
                      </tr>

                      <tr>
                        <td>
                          <ctrl:checkbox property="rightBank" styleClass="checkbox" value="1" onclick="onRightClick(this)"/>
                        </td>
                        <td>
                          <bean:message key="MergeContractors.ctr_bank_props_right"/>
                        </td>
                        <td>
                          <ctrl:text property="contractorRight.bank_props" style="width:300px;"/>
                        </td>
                      </tr>

                      <tr>
                        <td>
                          <ctrl:checkbox property="rightUNP" styleClass="checkbox" value="1" onclick="onRightClick(this)"/>
                        </td>
                        <td>
                          <bean:message key="MergeContractors.ctr_unp_right"/>
                        </td>
                        <td>
                          <ctrl:text property="contractorRight.unp" style="width:300px;"/>
                        </td>
                      </tr>

                      <tr>
                        <td>
                          <ctrl:checkbox property="rightOKPO" styleClass="checkbox" value="1" onclick="onRightClick(this)"/>
                        </td>
                        <td>
                          <bean:message key="MergeContractors.ctr_okpo_right"/>
                        </td>
                        <td>
                          <ctrl:text property="contractorRight.okpo" style="width:300px;"/>
                        </td>
                      </tr>

                      <tr>
                        <td>
                          <ctrl:checkbox property="rightReputation" styleClass="checkbox" value="1" onclick="onRightClick(this)"/>
                        </td>
                        <td>
                          <bean:message key="MergeContractors.reputation_right"/>
                        </td>
                        <td>
                          <ctrl:serverList property="contractorRight.reputation.description"
                                           idName="contractorRight.reputation.id"
                                           action="/ReputationsListAction"
                                           style="width:225px;"/>
                        </td>
                      </tr>

                      <tr>
                        <td>
                          <ctrl:checkbox property="rightCountry" styleClass="checkbox" value="1" onclick="onRightClick(this)"/>
                        </td>
                        <td>
                          <bean:message key="MergeContractors.country_right"/>
                        </td>
                        <td>
                          <ctrl:serverList property="contractorRight.country.name"
                                           idName="contractorRight.country.id"
                                           action="/CountriesListAction"
                                           style="width:225px;"/>
                        </td>
                      </tr>

                      <tr>
                        <td>
                          <ctrl:checkbox property="rightComment" styleClass="checkbox" value="1" onclick="onRightClick(this)"/>
                        </td>
                        <td>
                          <bean:message key="MergeContractors.comment_right"/>
                        </td>
                        <td>
	                        <ctrl:textarea property="contractorRight.comment" style="width:300px;height:305px;"/>
                        </td>
                      </tr>
                    </table>
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
            <td  colspan="2">
              <bean:message key="MergeContractors.textUnder1"/>
            </td>
          </tr>
          <tr>
            <td  colspan="2">
              <bean:message key="MergeContractors.textUnder2"/>
            </td>
          </tr>

          <tr>
            <td colspan="2" align="right" class=formSpace>
              &nbsp;
            </td>
          </tr>

          <tr>
            <td colspan="2" align="right">
              <ctrl:ubutton type="link" actionForward="back" styleClass="width80" readonly="false">
                <bean:message key="button.cancel"/>
              </ctrl:ubutton>
              <input type=button id="submitBtn" onclick="return checkFormCheckboxes();" class="width80" value="<bean:message key="button.merge"/>">
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>

</ctrl:form>

<script language="JScript" type="text/javascript">
  function onLeftClick(obj)
  {
    if ( obj.checked )
    {
      var otherCheckBox = document.getElementsByName(obj.name.replace('left', 'right'))[0];
      otherCheckBox.checked = false;
    }
  }

  function onRightClick(obj)
  {
    if ( obj.checked )
    {
      var otherCheckBox = document.getElementsByName(obj.name.replace('right', 'left'))[0];
      otherCheckBox.checked = false;
    }
  }

  function checkFormCheckboxes()
  {
    if (
         ( !document.getElementsByName('leftName')[0].checked && !document.getElementsByName('rightName')[0].checked ) ||
         ( !document.getElementsByName('leftFullName')[0].checked && !document.getElementsByName('rightFullName')[0].checked ) ||
         ( !document.getElementsByName('leftIndex')[0].checked && !document.getElementsByName('rightIndex')[0].checked ) ||
         ( !document.getElementsByName('leftRegion')[0].checked && !document.getElementsByName('rightRegion')[0].checked ) ||
         ( !document.getElementsByName('leftPlace')[0].checked && !document.getElementsByName('rightPlace')[0].checked ) ||
         ( !document.getElementsByName('leftStreet')[0].checked && !document.getElementsByName('rightStreet')[0].checked ) ||
         ( !document.getElementsByName('leftBuilding')[0].checked && !document.getElementsByName('rightBuilding')[0].checked ) ||
         ( !document.getElementsByName('leftAddInfo')[0].checked && !document.getElementsByName('rightAddInfo')[0].checked ) ||
         ( !document.getElementsByName('leftPhone')[0].checked && !document.getElementsByName('rightPhone')[0].checked ) ||
         ( !document.getElementsByName('leftFax')[0].checked && !document.getElementsByName('rightFax')[0].checked ) ||
         ( !document.getElementsByName('leftEMail')[0].checked && !document.getElementsByName('rightEMail')[0].checked ) ||
         ( !document.getElementsByName('leftBank')[0].checked && !document.getElementsByName('rightBank')[0].checked ) || 
         ( !document.getElementsByName('leftUNP')[0].checked && !document.getElementsByName('rightUNP')[0].checked ) ||
         ( !document.getElementsByName('leftOKPO')[0].checked && !document.getElementsByName('rightOKPO')[0].checked ) ||
         ( !document.getElementsByName('leftReputation')[0].checked && !document.getElementsByName('rightReputation')[0].checked ) ||
         ( !document.getElementsByName('leftCountry')[0].checked && !document.getElementsByName('rightCountry')[0].checked ) ||
         ( !document.getElementsByName('leftComment')[0].checked && !document.getElementsByName('rightComment')[0].checked )
       )
    {
      alert('<bean:message key="msg.merge_contractors.msg_incorrect_check"/>');
    }
    else
    {
      submitDispatchForm("process");
    }
  }
</script>
