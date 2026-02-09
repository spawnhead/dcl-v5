  <%@ taglib uri="/tags/struts-bean" prefix="bean" %>
  <%@ taglib uri="/tags/struts-html" prefix="html" %>
  <%@ taglib uri="/tags/struts-logic" prefix="logic" %>
  <%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
  <%@ taglib uri="/tags/html-grid" prefix="grid" %>

  <div id='for-insert'></div>

  <ctrl:askUser name="deleteAttachAsk" key="msg.instruction.delete_attach" showOkCancel="false" height="120"/>

  <ctrl:form readonly="${Instruction.formReadOnly}">
  <ctrl:hidden property="ins_id"/>
  <ctrl:hidden property="is_new_doc"/>
  <ctrl:hidden property="usr_date_create"/>
  <ctrl:hidden property="usr_date_edit"/>
  <ctrl:hidden property="createUser.usr_id"/>
  <ctrl:hidden property="editUser.usr_id"/>
  <table class=formBackTop align="center" width="99%">
    <tr>
      <td>
        <table cellspacing="0" width="100%" >
          <logic:notEqual name="Instruction" property="is_new_doc" value="true">
            <tr>
              <td>
                <table width="100%">
                  <tr>
                    <td width="130px"><bean:message key="Instruction.create"/></td>
                    <td><ctrl:write name="Instruction" property="usr_date_create"/> <ctrl:write name="Instruction" property="createUser.userFullName"/> </td>
                  </tr>
                </table>
              </td>
            </tr>
            <tr>
              <td>
                <table width="100%">
                  <tr>
                    <td width="130px"><bean:message key="Instruction.edit"/></td>
                    <td><ctrl:write name="Instruction" property="usr_date_edit"/> <ctrl:write name="Instruction" property="editUser.userFullName"/></td>
                  </tr>
                </table>
              </td>
            </tr>
          </logic:notEqual>

          <tr>
            <td>
              <table width="100%">
                <tr>
                  <td width="130px"><bean:message key="Instruction.type"/></td>
                  <td>
                    <table width="100%" border="0" cellSpacing="0" cellPadding="0">
                      <tr>
                        <td width="270px">
                          <ctrl:serverList property="type.name" idName="type.id" action="/InstructionTypesListAction"
                                           selectOnly="true" style="width:230px;"/>
                        </td>
                        <logic:equal name="Instruction" property="showForAdmin" value="true">
                          <td>
                            <ctrl:ubutton type="submit" dispatch="editType" styleClass="width145">
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
          <tr>
            <td>
              <table width="100%">
                <tr>
                  <td width="130px"><bean:message key="Instruction.ins_number"/></td>
                  <td><ctrl:text property="ins_number" style="width:250px;"/></td>
                </tr>
              </table>
            </td>
          </tr>
          <tr>
            <td>
              <table width="100%">
                <tr>
                  <td width="130px"><bean:message key="Instruction.ins_date_sign"/></td>
                  <td><ctrl:date property="ins_date_sign" style="width:230px;"/></td>
                </tr>
              </table>
            </td>
          </tr>
          <tr>
            <td colspan="20">
              <table width="100%">
                <tr>
                  <td width="130px"><bean:message key="Instruction.ins_date_from"/></td>
                  <td width="270px"><ctrl:date property="ins_date_from" style="width:230px;"/></td>
                  <td width="70px" align="right"><bean:message key="Instruction.ins_date_to"/></td>
                  <td><ctrl:date property="ins_date_to" style="width:230px;"/></td>
                </tr>
              </table>
            </td>
          </tr>
          <tr>
            <td>
              <table width="100%">
                <tr>
                  <td width="130px"><bean:message key="Instruction.ins_concerning"/></td>
                  <td><ctrl:textarea property="ins_concerning" style="width:600px;height:155px;"/></td>
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
                    <bean:message key="Instruction.attachments"/>
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
            <td colspan="2" align="right" class=formSpace>
              &nbsp;
            </td>
          </tr>

          <tr>
            <td colspan="2" align="right">
              <ctrl:ubutton type="submit" dispatch="back" styleClass="width80" readonly="false">
                <bean:message key="button.cancel"/>
              </ctrl:ubutton>
              <ctrl:ubutton type="submit" dispatch="process" styleClass="width80" readonly="${Instruction.formReadOnly}">
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
  function downloadAttachment(id)
  {
	  document.getElementById('for-insert').innerHTML='<iframe src=\'<html:rewrite action="/InstructionAction.do?dispatch=downloadAttachment"/>&attachmentId='+id+'\' style=\'display:none\' />';
		return false;
	}

</script>