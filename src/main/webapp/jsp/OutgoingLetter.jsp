  <%@ taglib uri="/tags/struts-bean" prefix="bean" %>
  <%@ taglib uri="/tags/struts-html" prefix="html" %>
  <%@ taglib uri="/tags/struts-logic" prefix="logic" %>
  <%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
  <%@ taglib uri="/tags/html-grid" prefix="grid" %>

  <div id='for-insert'></div>

  <ctrl:askUser name="deleteAttachAsk" key="msg.outgoing_letter.delete_attach" showOkCancel="false" height="120"/>

  <ctrl:form readonly="${OutgoingLetter.formReadOnly}">
  <ctrl:hidden property="otl_id"/>
  <ctrl:hidden property="is_new_doc"/>
  <ctrl:hidden property="usr_date_create"/>
  <ctrl:hidden property="usr_date_edit"/>
  <ctrl:hidden property="createUser.usr_id"/>
  <ctrl:hidden property="editUser.usr_id"/>
  <table class=formBackTop align="center" width="99%">
    <tr>
      <td >
        <table cellspacing="0" width="100%" >
          <logic:notEqual name="OutgoingLetter" property="is_new_doc" value="true">
            <tr>
              <td>
                <table width="100%">
                  <tr>
                    <td width="15%"><bean:message key="OutgoingLetter.create"/></td>
                    <td><ctrl:write name="OutgoingLetter" property="usr_date_create"/> <ctrl:write name="OutgoingLetter" property="createUser.userFullName"/> </td>
                  </tr>
                </table>
              </td>
            </tr>
            <tr>
              <td>
                <table width="100%">
                  <tr>
                    <td width="15%"><bean:message key="OutgoingLetter.edit"/></td>
                    <td><ctrl:write name="OutgoingLetter" property="usr_date_edit"/> <ctrl:write name="OutgoingLetter" property="editUser.userFullName"/></td>
                  </tr>
                </table>
              </td>
            </tr>
            <tr>
              <td>
                <table width="100%">
                  <tr>
                    <td width="15%"><bean:message key="OutgoingLetter.otl_number"/></td>
                    <td><ctrl:text property="otl_number" style="width:230px;" readonly="true"/></td>
                  </tr>
                </table>
              </td>
            </tr>
          </logic:notEqual>

          <tr>
            <td>
              <table width="100%">
                <tr>
                  <td width="15%"><bean:message key="OutgoingLetter.otl_date"/></td>
                  <td><ctrl:date property="otl_date" style="width:230px;"/></td>
                </tr>
              </table>
            </td>
          </tr>
          <tr>
            <td colspan="20">
              <table width="100%">
                <tr>
	                <td width="15%"><bean:message key="OutgoingLetter.seller"/></td>
	                 <td>
	 	                <ctrl:serverList property="seller.name" idName="seller.id" action="/SellersListAction" selectOnly="true" style="width:230px;"/>
	                 </td>
                </tr>
              </table>
            </td>
          </tr>
          <tr>
            <td>
              <table width="100%" border="0">
                <tr>
                  <td width="15%"><bean:message key="OutgoingLetter.otl_contractor"/></td>
                  <td>
                    <table border="0" cellSpacing="0" cellPadding="0">
                      <tr>
                        <td>
                          <ctrl:serverList property="contractor.name" idName="contractor.id"
                                           action="/ContractorsListAction" filter="filter" style="width:230px;"
                                           callback="onContractorSelect"/>
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
              <table width="100%" border="0">
                <tr>
                  <td width="15%"><bean:message key="OutgoingLetter.otl_contact_person"/></td>
                  <td>
                    <table border="0" cellSpacing="0" cellPadding="0">
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
                    <bean:message key="OutgoingLetter.attachments"/>
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
                      <ctrl:ubutton type="submit" dispatch="deferredAttach" styleClass="width80" >
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
                  <td width="25%"><bean:message key="OutgoingLetter.otl_comment"/></td>
                </tr>
              </table>
            </td>
          </tr>
          <tr>
            <td>
              <table width="100%">
                <tr>
                  <td><ctrl:textarea property="otl_comment" style="width:600px;height:160px;"/></td>
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
              <ctrl:ubutton type="submit" dispatch="process" styleClass="width80" readonly="${OutgoingLetter.formReadOnly}">
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
  var contractorId = document.getElementById("contractor.id");
  var contactPersonId = document.getElementById("contactPerson.cps_id");
  var contactPersonName = document.getElementById("contactPerson.cps_name");
  var contactPersonNameBtn = document.getElementById("contactPerson.cps_nameBtn");
  var addNewPersonBtn = document.getElementById("addNewPerson");

  <logic:equal name="OutgoingLetter" property="formReadOnly" value="false">
    isContractorSelected();
  </logic:equal>

  function isContractorSelected()
  {
    if ( contractorId.value == "" )
    {
	    contactPersonName.disabled = true;
	    contactPersonNameBtn.disabled = true;
      addNewPersonBtn.disabled = true;
    }
    else
    {
	    contactPersonName.disabled = false;
	    contactPersonNameBtn.disabled = false;
      addNewPersonBtn.disabled = false;
    }
  }

  function onContractorSelect()
  {
	  contactPersonName.value = "";
	  contactPersonId.value = "";
    isContractorSelected();
  }

  function downloadAttachment(id)
  {
	  document.getElementById('for-insert').innerHTML='<iframe src=\'<html:rewrite action="/OutgoingLetterAction.do?dispatch=downloadAttachment"/>&attachmentId='+id+'\' style=\'display:none\' />';
		return false;
	}

</script>