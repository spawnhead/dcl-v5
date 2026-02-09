<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>

<div id='for-insert'></div>

<ctrl:form styleId="np-form" readonly="${NomenclatureProduce.formReadOnly}">
  <table class=formBackTop align="center" border="0">
    <tr>
      <td>
        <table cellspacing="0" border="0">
          <tr>
            <td rowspan="2">
              <table cellspacing="2">
                <logic:notEmpty name="NomenclatureProduce" property="produce.createUser">
                  <tr>
                    <td><bean:message key="NomenclatureProduce.createUser"/></td>
                    <td><bean:write name="NomenclatureProduce" property="produce.createDateFormatted"/> <bean:write name="NomenclatureProduce" property="produce.createUser.userFullName"/></td>
                  </tr>
                </logic:notEmpty>
                <logic:notEmpty name="NomenclatureProduce" property="produce.editUser">
                  <tr>
                    <td><bean:message key="NomenclatureProduce.editUser"/></td>
                    <td><bean:write name="NomenclatureProduce" property="produce.editDateFormatted"/> <bean:write name="NomenclatureProduce" property="produce.editUser.userFullName"/></td>
                  </tr>
                </logic:notEmpty>
                <logic:equal name="NomenclatureProduce" property="produce.block" value="1">
                  <logic:notEmpty name="NomenclatureProduce" property="produce.blockUser">
                    <tr>
                      <td><bean:message key="NomenclatureProduce.blockUser"/></td>
                      <td><bean:write name="NomenclatureProduce" property="produce.blockDateFormatted"/> <bean:write name="NomenclatureProduce" property="produce.blockUser.userFullName"/></td>
                    </tr>
                  </logic:notEmpty>
                </logic:equal>
                <tr>
                  <td colspan="2" name="LTGrid" id="LTGrid">
                    <img src='<%= request.getContextPath() %>/images/wait.gif'>
                  </td>
                </tr>
                <tr>
                  <td><bean:message key="NomenclatureProduce.type"/></td>
                  <td><ctrl:text property="produce.type"/></td>
                </tr>
                <tr>
                  <td><bean:message key="NomenclatureProduce.param"/></td>
                  <td><ctrl:text property="produce.params"/></td>
                </tr>
                <tr>
                  <td><bean:message key="NomenclatureProduce.addParam"/></td>
                  <td><ctrl:text property="produce.addParams"/></td>
                </tr>
                <tr>
                  <td><bean:message key="NomenclatureProduce.unit"/></td>
                  <td><ctrl:serverList property="unit.name" idName="unit.id" action="/UnitsListAction"
                                       selectOnly="true"/></td>
                </tr>
                <tr>

                  <td><bean:message key="NomenclatureProduce.customCode"/></td>
                  <td>
                    <div style="float: left;width: 60%">
                      <ctrl:text property="customCode.code" readonly="true" styleClass="nomenclatureParamNameWithHistory"/>
                    </div>
                    <ctrl:ubutton type="link" action="/NomenclatureProduceCustomCodeHistoryAction.do?dispatch=show" styleClass="nomenclatureHistoryButton" readonly="${NomenclatureProduce.customCodeHistoryButtonReadonly}">
                      <bean:message key="button.customCodesHistory"/>
                    </ctrl:ubutton>
                  </td>
                </tr>
                <tr>
                      <td><bean:message key="NomenclatureProduce.number1C"/></td>
                      <td>
                          <div style="float: left;width: 60%">
                              <ctrl:text property="number1C" readonly="true" styleClass="nomenclatureParamNameWithHistory"/>
                          </div>
                          <ctrl:ubutton type="link" action="/Number1CHistoryAction.do?dispatch=show" styleClass="nomenclatureHistoryButton">
                              <bean:message key="button.1CHistory"/>
                          </ctrl:ubutton>

                      </td>
                  </tr>
                <tr>
                  <td colspan="2" name="CNGrid" id="CNGrid">
                    <img src='<%= request.getContextPath() %>/images/wait.gif'>
                  </td>
                </tr>
                <tr>
                  <td align="left">
                    <ctrl:ubutton type="script" dispatch="fakeDispatchForDelete" styleClass="width165"
                                  onclick="deleteSelectedCNGrid()"
                                  readonly="${NomenclatureProduce.deleteSelectedBtnReadonly}" showWait="false">
                      <bean:message key="button.deleteSelected"/>
                    </ctrl:ubutton>
                  </td>
                  <td align="right">
                    <ctrl:ubutton type="script" dispatch="fakeDispatchForAdd" styleClass="width165"
                                  onclick="addRowCNGrid()" readonly="false" showWait="false">
                      <bean:message key="button.addProduce"/>
                    </ctrl:ubutton>
                  </td>
                </tr>

                <tr>
                  <td><bean:message key="NomenclatureProduce.material"/></td>
                  <td><ctrl:textarea styleClass="three-lines" property="produce.material"
                                     readonly="${NomenclatureProduce.infoFieldsReadOnly}"/></td>
                </tr>
                <tr>
                  <td><bean:message key="NomenclatureProduce.purporse"/></td>
                  <td><ctrl:textarea styleClass="three-lines" property="produce.purpose"
                                     readonly="${NomenclatureProduce.infoFieldsReadOnly}"/></td>
                </tr>
                <tr>
                  <td><bean:message key="NomenclatureProduce.specification"/></td>
                  <td><ctrl:textarea styleClass="three-lines" property="produce.specification"
                                     readonly="${NomenclatureProduce.infoFieldsReadOnly}"/></td>
                </tr>
                <tr>
                  <td><bean:message key="NomenclatureProduce.principle"/></td>
                  <td><ctrl:textarea styleClass="three-lines" property="produce.principle"
                                     readonly="${NomenclatureProduce.infoFieldsReadOnly}"/></td>
                </tr>

                <tr>
                  <td colspan="2"><ctrl:checkbox property="produce.block" value="1" styleClass="checkbox"
                                                 readonly="${NomenclatureProduce.blockReadOnly}"/> <bean:message
                          key="NomenclatureProduce.block"/></td>
                </tr>
              </table>
            </td>
            <td valign="top" style="border-left:black solid 2px;border-bottom:black solid 2px">
              <table cellspacing="2" width="100%">
                <tr>
                  <td>
                    <b><bean:message key="NomenclatureProduce.section.attach"/></b>
                  </td>
                </tr>
                <tr>
                  <td>
                    <div class="gridBack">
                      <grid:table property="attachmentsGrid" key="idx" scrollableGrid="true" height="170">
                        <grid:column title=""/>
                        <grid:column>
                          <jsp:attribute name="title"><bean:message key="Attachments.name"/></jsp:attribute>
                        </grid:column>
                        <grid:column title=""/>
                        <grid:row>
                          <grid:colCustom showCheckerId="attachRadioChecker"><input class="grid-radio" type="radio"
                                                                                    name="selectedImage"
                                                                                    value="${record.idx}"></grid:colCustom>
                          <grid:colCustom width="99%"><a href=""
                                                         onclick="return download(${record.idx});">${record.originalFileName}</a></grid:colCustom>
                          <grid:colDelete width="1" dispatch="deleteAttachment" scriptUrl="attachmentId=${record.idx}"
                                          type="submit" tooltip="tooltip.Attachments.delete"/>
                        </grid:row>
                      </grid:table>
                    </div>
                    <div class=gridBottom>
                      <ctrl:ubutton type="submit" dispatch="attach" styleClass="width80"
                                    readonly="${NomenclatureProduce.infoFieldsReadOnly}">
                        <bean:message key="button.attach"/>
                      </ctrl:ubutton>
                    </div>
                  </td>
                </tr>
              </table>
            </td>
          </tr>
          <tr height="300">
            <td valign="top" style="border-left:black solid 2px;padding:5px">
              <b><bean:message key="NomenclatureProduce.section.print"/></b>
              <table>
                <tr>
                  <td><input type="radio" class="radio" name="doPrintTo" value="0" checked="true"></td>
                  <td><bean:message key="NomenclatureProduce.not-print.photo"/></td>
                </tr>
                <tr>
                  <td><input type="radio" class="radio" name="doPrintTo" value="1"></td>
                  <td width="400"><bean:message key="NomenclatureProduce.print.photo"/><font style="font-size:smaller;"><bean:message key="NomenclatureProduce.print.photo.desc"/></font></td>
                </tr>
                <tr>
                  <td><bean:message key="NomenclatureProduce.blank"/></td>
                  <td><ctrl:serverList property="blank.bln_name" idName="blank.bln_id" action="/BlanksListAction"
                                       selectOnly="true" scriptUrl="type=3"
                                       readonly="${NomenclatureProduce.blankReadOnly}"/></td>
                </tr>
              </table>
            </td>
          </tr>
          <tr>
            <td colspan="2" align="right">
              <ctrl:ubutton type="submit" dispatch="print" styleClass="width80" readonly="false" showWait="false">
                <bean:message key="button.printTO"/>
              </ctrl:ubutton>
              &nbsp;
              <ctrl:text property="printScale" style="width:40px;text-align:right;" readonly="false"/>
              <bean:message key="Common.percent"/>
              &nbsp;&nbsp;
              <ctrl:ubutton type="link" dispatch="back" styleClass="width80" readonly="false">
                <bean:message key="button.cancel"/>
              </ctrl:ubutton>
              <ctrl:ubutton type="submit" dispatch="process" styleClass="width80"
                            readonly="${NomenclatureProduce.saveReadOnly}">
                <bean:message key="button.save"/>
              </ctrl:ubutton>
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
</ctrl:form>

<script type="text/javascript" language="JScript">
  function loadLTGrid()
  {
    doAjax({
      url:'<ctrl:rewrite action="/NomenclatureProduceAction" dispatch="ajaxLTGrid"/>',
      form:'np-form',
      update:'LTGrid'
    });
  }
  function loadCNGrid()
  {
    doAjax({
      url:'<ctrl:rewrite action="/NomenclatureProduceAction" dispatch="ajaxCNGrid"/>',
      form:'np-form',
      update:'CNGrid'
    });
  }
  function addRowCNGrid()
  {
    doAjax({
      url:'<ctrl:rewrite action="/NomenclatureProduceAction" dispatch="ajaxAddRowInCNGrid"/>',
      form:'np-form',
      update:'CNGrid',
      showWaitCursor:true
    });
  }
  function deleteSelectedCNGrid()
  {
    doAjax({
      url:'<ctrl:rewrite action="/NomenclatureProduceAction" dispatch="ajaxDeleteRowFromCNGrid"/>',
      form:'np-form',
      update:'CNGrid',
      showWaitCursor:true
    });
  }
  function showWarning()
  {
  <logic:notEmpty name="NomenclatureProduce" property="warningCyrillicChars">
    if (!isUserAgree('<ctrl:write name="NomenclatureProduce" property="warningCyrillicChars" jsFilter="true"/>', false, 600, 120))
    {
      <logic:empty name="NomenclatureProduce" property="warningDigitChars">
        <logic:empty name="NomenclatureProduce" property="warningIncorrectFirstWord">
          <logic:empty name="NomenclatureProduce" property="warning">
            submitDispatchForm("save");
          </logic:empty>
        </logic:empty>
      </logic:empty>
    }
    else
    {
      return;
    }
  </logic:notEmpty>
  <logic:notEmpty name="NomenclatureProduce" property="warningDigitChars">
    if (!isUserAgree('<ctrl:write name="NomenclatureProduce" property="warningDigitChars" jsFilter="true"/>', false, 600, 120, false, ${NomenclatureProduce.timeOutForNoForProduceError}))
    {
      <logic:empty name="NomenclatureProduce" property="warningIncorrectFirstWord">
        <logic:empty name="NomenclatureProduce" property="warning">
          submitDispatchForm("save");
        </logic:empty>
      </logic:empty>
    }
    else
    {
      return;
    }
  </logic:notEmpty>
  <logic:notEmpty name="NomenclatureProduce" property="warningIncorrectFirstWord">
    if (!isUserAgree('<ctrl:write name="NomenclatureProduce" property="warningIncorrectFirstWord" jsFilter="true"/>', false, 600, 120, false, ${NomenclatureProduce.timeOutForNoForProduceError}))
    {
      <logic:empty name="NomenclatureProduce" property="warning">
        submitDispatchForm("save");
      </logic:empty>
    }
    else
    {
      return;
    }
  </logic:notEmpty>

  <logic:notEmpty name="NomenclatureProduce" property="warning">
    if (isUserAgree('<ctrl:write name="NomenclatureProduce" property="warning" jsFilter="true"/>', false, 600, 50 + 20 * <bean:write name="NomenclatureProduce" property="warningLinesCount"/>, false, 0, ${NomenclatureProduce.timeOutForNoForProduceError}))
    {
      submitDispatchForm("save");
    }
  </logic:notEmpty>
  }

  function download(id)
  {
	  document.getElementById('for-insert').innerHTML = '<iframe src=\'<html:rewrite action="/NomenclatureProduceAction.do?dispatch=downloadAttachment"/>&attachmentId=' + id + '\' style=\'display:none\' />';
    return false;
  }

  initFunctions.push(loadLTGrid);
  initFunctions.push(loadCNGrid);
  initFunctions.push(showWarning);
</script>

<logic:equal name="NomenclatureProduce" property="printTo" value="true">
  <script language="JScript" type="text/javascript">
	  document.getElementById('for-insert').innerHTML = '<iframe src="<ctrl:rewrite action="/NomenclatureProducePrintAction"  />" style="display:none" />';
  </script>
</logic:equal>
