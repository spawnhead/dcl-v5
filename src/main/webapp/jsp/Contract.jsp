  <%@ taglib uri="/tags/struts-bean" prefix="bean" %>
  <%@ taglib uri="/tags/struts-html" prefix="html" %>
  <%@ taglib uri="/tags/struts-logic" prefix="logic" %>
  <%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
  <%@ taglib uri="/tags/html-grid" prefix="grid" %>

  <div id='for-insert'></div>

  <ctrl:askUser name="deleteAttachAsk" key="msg.contract.delete_attach" showOkCancel="false" height="120"/>

  <ctrl:form readonly="${Contract.formReadOnly}">
  <ctrl:hidden property="con_id"/>
  <ctrl:hidden property="con_executed"/>
  <ctrl:hidden property="is_new_doc"/>
  <ctrl:hidden property="usr_date_create"/>
  <ctrl:hidden property="usr_date_edit"/>
  <ctrl:hidden property="createUser.usr_id"/>
  <ctrl:hidden property="editUser.usr_id"/>
  <table class=formBackTop align="center" width="99%">
    <tr>
      <td>
        <table cellspacing="0" width="100%" >
          <logic:notEqual name="Contract" property="is_new_doc" value="true">
            <tr>
              <td>
                <table width="100%">
                  <tr>
                    <td width="10%"><bean:message key="Contract.create"/></td>
                    <td width="35%"><ctrl:write name="Contract" property="usr_date_create"/> <ctrl:write name="Contract" property="createUser.userFullName"/> </td>
                    <td>
                      <table width="100%">
                        <tr>
                          <td width="120px"><bean:message key="Contract.edit"/></td>
                          <td><ctrl:write name="Contract" property="usr_date_edit"/> <ctrl:write name="Contract" property="editUser.userFullName"/></td>
                        </tr>
                      </table>
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
                  <td width="10%"><bean:message key="Contract.con_number"/></td>
                  <td><ctrl:text property="con_number" style="width:230px;" readonly="${Contract.readOnlyIfNotAdminEconomistLawyer}"/></td>
                </tr>
              </table>
            </td>
          </tr>
          <tr>
            <td>
              <table width="100%">
                <tr>
                  <td width="10%"><bean:message key="Contract.con_date"/></td>
                  <td width="265px"><ctrl:date property="con_date_formatted" style="width:230px;" readonly="${Contract.readOnlyIfNotAdminEconomistLawyer}"/></td>
                  <td width="20px"><ctrl:checkbox property="con_reusable" styleClass="checkbox" value="1"/></td>
                  <td width="100px"><bean:message key="Contract.con_reusable"/></td>
                  <td width="90px"><bean:message key="Contract.con_final_date"/></td>
                  <td><ctrl:date property="con_final_date_formatted" style="width:230px;"/></td>
                </tr>
              </table>
            </td>
          </tr>
          <tr>
            <td>
              <table width="100%" border="0">
                <tr>
                  <td width="10%"><bean:message key="Contract.con_contractor"/></td>
                  <td width="398px">
                    <table border="0" cellSpacing="0" cellPadding="0">
                      <tr>
                        <td>
                          <ctrl:serverList property="contractor.name" idName="contractor.id" action="/ContractorsListAction" filter="filter" style="width:230px;" readonly="${Contract.readOnlyIfOccupied}"/>
                        </td>
                        <td>&nbsp;</td>
                        <td>
                          <ctrl:ubutton type="submit" dispatch="newContractor" styleClass="width80" readonly="${Contract.readOnlyIfOccupied}">
                            <bean:message key="button.add"/>
                          </ctrl:ubutton>
                        </td>
                      </tr>
                    </table>
                  </td>

                  <td>
                    <table width="100%">
                      <tr>
                        <td width="90px"><bean:message key="Contract.con_currency"/></td>
                        <td>
                          <ctrl:serverList property="currency.name" idName="currency.id" action="/CurrenciesListAction"
                                           selectOnly="true" style="width:120px;" readonly="${Contract.readOnlyIfOccupied}"/>
                        </td>
                      </tr>
                    </table>
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
                    <bean:message key="Contract.con_fax_copy"/>&nbsp;
                    <ctrl:checkbox property="con_fax_copy" styleClass="checkbox" value="1" onclick="conFaxCopyOnClick()" readonly="${Contract.readOnlyIfNotAdminEconomistLawyer}"/>&nbsp;&nbsp;&nbsp;&nbsp;
                    <bean:message key="Contract.con_original"/>&nbsp;
                    <ctrl:checkbox property="con_original" styleClass="checkbox" value="1" onclick="conOriginalOnClick()" readonly="${Contract.readOnlyIfNotAdminEconomistLawyer}"/>
                  </td>
                </tr>
              </table>
            </td>
          </tr>
          <tr>
            <td colspan="20">
              <table width="100%" border="0">
                <tr>
                  <td width="10%"><bean:message key="Contract.con_seller"/></td>
                  <td>
                    <ctrl:serverList property="seller.name" idName="seller.id" action="/SellersListAction"
                                     selectOnly="true" style="width:230px;" readonly="${Contract.readOnlyIfNotAdminEconomistLawyer}"/>
                  </td>
                </tr>
              </table>
            </td>
          </tr>

          <tr>
            <td>
              <table width="100%">
                <tr>
                  <td width="25%"><bean:message key="Contract.table_part"/></td>
                </tr>
              </table>
            </td>
          </tr>

          <tr>
            <td>
              <div class="gridBackNarrow">
                <grid:table property="grid" key="spc_number" >
                  <grid:column width="15%" align="center"><jsp:attribute name="title"><bean:message key="Specifications.spc_number"/></jsp:attribute></grid:column>
                  <grid:column width="15%" align="center"><jsp:attribute name="title"><bean:message key="Specifications.spc_date"/></jsp:attribute></grid:column>
                  <grid:column width="15%" align="center"><jsp:attribute name="title"><bean:message key="Specifications.spc_summ"/></jsp:attribute></grid:column>
                  <grid:column width="15%" align="center"><jsp:attribute name="title"><bean:message key="Specifications.nds_percent"/></jsp:attribute></grid:column>
                  <grid:column align="center"><jsp:attribute name="title"><bean:message key="Specifications.spc_summ_nds"/></jsp:attribute></grid:column>
                  <grid:column width="16%" align="center"><jsp:attribute name="title"><bean:message key="Specifications.spc_remainder"/></jsp:attribute></grid:column>
                  <grid:column align="center" width="1%"><jsp:attribute name="title"><img title='<bean:message key="tooltip.Specifications.execute_status"/>' src='<ctrl:rewrite page="/images/accept.gif"/>'></jsp:attribute></grid:column>
                  <grid:column title=""/>
                  <grid:column title=""/>
                  <grid:column title=""/>
                  <grid:row>
                    <grid:colCustom width="15%" property="spc_number" styleClassCheckerId="style-checker"/>
                    <grid:colCustom width="15%" property="spc_date_formatted" styleClassCheckerId="style-checker"/>
                    <grid:colCustom width="15%" property="spc_summ_formatted" align="right" styleClassCheckerId="style-checker"/>
                    <grid:colCustom width="15%" property="spc_nds_rate_formatted" align="right" styleClassCheckerId="style-checker"/>
                    <grid:colCustom property="spc_summ_nds_formatted" align="right" styleClassCheckerId="style-checker"/>
                    <grid:colCustom width="16%" property="spc_remainder" styleClassCheckerId="style-checker"/>
                    <grid:colCheckbox align="center" width="30" property="spc_executed" readonlyCheckerId="alwaysReadonly" />
                    <grid:colEdit width="1" dispatch="editSpecification" type="submit" tooltip="tooltip.Specifications.edit" readonlyCheckerId="alwaysRead" scriptUrl="currencyName=$(currency.name)"/>
                    <grid:colDelete width="1" dispatch="deleteSpecification" type="submit" tooltip="tooltip.Specifications.delete" readonlyCheckerId="deleteReadonly"/>
                    <grid:colImage width="1" dispatch="attach" type="submit" scriptUrl="referencedID=${record.spc_id}" tooltip="tooltip.Contract.attachments"
                                   image='${record.attachmentsCount eq "0"?"images/attach_no.gif":"images/attach_yes.gif"}' style="vertical-align:top;" styleClass="grid-image-without-border"/>
                  </grid:row>
                </grid:table>
              </div>
            </td>
          </tr>
          <tr>
            <td>
              <div class=gridBottom>
                <ctrl:ubutton type="submit" dispatch="newSpecification" styleClass="width165" scriptUrl="currencyName=$(currency.name)">
                  <bean:message key="button.addSpecification"/>
                </ctrl:ubutton>
              </div>
            </td>
          </tr>
          <tr>
            <td colspan="20">
              <table width="100%">
                <tr>
                  <td width="180px">
                    <bean:message key="Contract.con_annul"/>&nbsp;
                    <ctrl:checkbox property="con_annul" styleClass="checkbox" value="1" readonly="${Contract.readOnlyForAnnul}"/>&nbsp;&nbsp;
                  </td>
                  <td>
                    <ctrl:date property="conAnnulDateFormatted" style="width:200px;" readonly="${Contract.readOnlyForAnnul}"/>
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
            <td >
              <table cellspacing="2" width="100%">
                <tr>
                  <td>
                    <bean:message key="Contract.attachments"/>
                  </td>
                </tr>
                <tr>
                  <td>
                    <div class="gridBack">
                      <grid:table property="attachmentsGrid" key="idx" >
                        <grid:column><jsp:attribute name="title"><bean:message key="Attachments.name"/></jsp:attribute></grid:column>
                        <grid:column width="1%" title=""/>
                        <grid:row>
                          <logic:equal name="Contract" property="showAttachFiles" value="true">
                            <grid:colCustom><a href="" onclick="return downloadAttachment(${record.idx});">${record.originalFileName}</a></grid:colCustom>
                          </logic:equal>
                          <logic:equal name="Contract" property="showAttachFiles" value="false">
                            <grid:colCustom property="originalFileName"/>
                          </logic:equal>
                          <grid:colDelete width="1%" dispatch="deleteAttachment" scriptUrl="attachmentId=${record.idx}" type="submit" tooltip="tooltip.Attachments.delete" askUser="deleteAttachAsk"/>
                        </grid:row>
                      </grid:table>
                    </div>
                    <logic:equal name="Contract" property="showAttach" value="true">
                      <div class=gridBottom>
                        <ctrl:ubutton type="submit" dispatch="deferredAttach" styleClass="width80" >
                          <bean:message key="button.attach"/>
                        </ctrl:ubutton>
                      </div>
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
                  <td width="25%"><bean:message key="Contract.con_comment"/></td>
                </tr>
              </table>
            </td>
          </tr>
          <tr>
            <td>
              <table width="100%">
                <tr>
                  <td><ctrl:textarea property="con_comment" style="width:600px;height:75px;" readonly="${Contract.readOnlyIfNotAdminEconomistLawyer}"/></td>
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
              <input id='buttonSave' type='button' onclick='processClick()' class='width80' value='<bean:message key="button.save"/>'/>
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
  </ctrl:form>

<script language="JScript" type="text/javascript">
  var conFaxCopy = document.getElementById('con_fax_copy');
  var conOriginal = document.getElementById('con_original');
  var conReusable = document.getElementById('con_reusable');
  var conFinalDateFormatted = document.getElementById('con_final_date_formatted');
  var sellerId = document.getElementById('seller.id');

  var buttonSave = document.getElementById("buttonSave");
  buttonSave.disabled = ${Contract.formReadOnly};

  function conFaxCopyOnClick()
  {
    conOriginal.checked = false;
  }

  function conOriginalOnClick()
  {
    conFaxCopy.checked = false;
  }

  function processClick()
  {
    if (!(!conReusable.checked && sellerId.value == 1) && conFinalDateFormatted.value == '')
    {
      alert('<bean:message key="msg.contract.requiredConFinalDate"/>');
      return;
    }
    submitDispatchForm("process");
  }

  function downloadAttachment(id)
  {
	  document.getElementById('for-insert').innerHTML='<iframe src=\'<html:rewrite action="/ContractAction.do?dispatch=downloadAttachment"/>&attachmentId='+id+'\' style=\'display:none\' />';
		return false;
	}

</script>