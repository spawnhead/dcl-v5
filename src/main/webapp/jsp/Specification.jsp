<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>
<%@ taglib prefix="tab" uri="http://ditchnet.org/jsp-tabs-taglib" %>

<div style="display:none" id="resultMsg"></div>

<div id='for-insert'></div>
<ctrl:askUser name="deleteAttachAsk" key="msg.specification.delete_copy" showOkCancel="false" height="120"/>

<ctrl:form readonly="${Specification.formReadOnly}">
  <ctrl:hidden property="is_new_doc"/>
  <ctrl:hidden property="old_number"/>
  <ctrl:hidden property="spc_id"/>
  <ctrl:hidden property="spc_executed"/>
  <ctrl:hidden property="spc_occupied"/>
  <ctrl:hidden property="spc_occupied_in_pay_shp"/>
  <ctrl:hidden property="payed_summ"/>
  <ctrl:hidden property="spc_in_ctc"/>
  <ctrl:hidden property="noRoundSum"/>
  <div style="display:none;" id="ndsRate"><ctrl:write name="Specification" property="ndsRate"/></div>
  <table class=formBackTop align="center" width="890px">
    <tr>
      <td>
        <tab:tabContainer id="specificationComtainer" selectedTabPaneId="mainPanel" defaultSelectedTab="mainPanel">

          <tab:tabPane id="mainPanel" tabTitle="Specification.main">
            <table align="center">
              <tr>
                <td>
                  <table width="100%" cellpadding="0" cellspacing="0">
                    <tr>
                      <td width="170px">
                        <bean:message key="Specification.user"/>
                        <ctrl:help htmlName="Specification.user"/>
                      </td>
                      <td>
                        <ctrl:serverList property="user.userFullName" idName="user.usr_id" action="/UsersListAction"
                                         filter="filter" style="width:150px;" showHelp="false" readonly="${Specification.readOnlyIfNotAdminEconomistLawyerForExecuted}"/>
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr>
                <td>
                  <table width="100%" cellpadding="0" cellspacing="0">
                    <tr>
                      <td width="170px"><bean:message key="Specification.spc_number"/></td>
                      <td width="210px"><ctrl:text property="spc_number" showHelp="false" style="width:170px;" onchange="selectRequiredFields()"
                                     readonly="${Specification.readOnlyIfNotAdminForExecuted}"/></td>
                      <td width="70px"><bean:message key="Specification.spc_date"/></td>
                      <td>
                        <ctrl:date property="spc_date" style="width:150px;" showHelp="false" afterSelect="changeSpcDate"
                                   readonly="${Specification.readOnlyIfNotAdminForExecuted}"/>
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr>
                <td>
                  <table width="100%" cellpadding="0" cellspacing="0">
                    <tr>
                      <td width="170px"><bean:message key="Specification.spc_summ"/></td>
                      <td width="210px"><ctrl:text property="spc_summ" style="width:170px;" showHelp="false" onchange="submitReloadPrices(this);"/></td>
                      <td width="70px"><bean:message key="Specification.spc_summ_nds"/></td>
                      <td><ctrl:text property="spc_summ_nds" style="width:170px;" showHelp="false"/></td>
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
                  <table width="100%" cellpadding="0" cellspacing="0">
                    <tr valign="top">
                      <td width="170px">
                        <bean:message key="Specification.spc_delivery_cond"/>
                        <ctrl:help htmlName="Specification.spc_delivery_cond"/>
                      </td>
                      <td>
                        <ctrl:textarea property="spc_delivery_cond" showHelp="false" style="width:600px;height:78px;" 
                                       readonly="${Specification.readOnlyIfNotAdminForExecuted}"/>
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr>
                <td>
                  <table width="100%" cellpadding="0" cellspacing="0">
                    <tr>
                      <td width="170px"><bean:message key="Specification.spc_delivery_date"/></td>
                      <td width="250px">
                        <ctrl:serverList property="deliveryTerm.name" idName="deliveryTerm.id"
                                         action="/DeliveryTermTypesListAction" style="width:220px;" selectOnly="true"
                                          showHelp="false" readonly="${Specification.readOnlyIfNotAdminEconomistLawyerForExecuted}" callback="changedDeliveryTerm"/>
                      </td>
                      <td>&nbsp;</td>
                      <td id="additionalDaysCount">
                        <ctrl:help htmlName="Specification.spc_additional_days_count"/>
                        <bean:message key="Specification.plus"/>
                        <ctrl:text property="spc_additional_days_count" style="width:63px;text-align:right;"
                                   showHelp="false" readonly="${Specification.readOnlyIfNotAdminEconomistLawyerForExecuted}" onchange="updateAdditionalDaysCount(this)"/>
                        <bean:message key="Specification.days"/>
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr id="afterPrepay">
                <td>
                  <table width="100%" cellpadding="0" cellspacing="0">
                    <tr>
                      <td width="170px">&nbsp;</td>
                      <td width="256px">
                        <ctrl:checkbox property="spc_percent_or_sum_percent" styleClass="checkbox" value="1" onclick="percentOnClick();" readonly="${Specification.readOnlyIfNotAdminEconomistLawyerForExecuted}"/>&nbsp;
                        <ctrl:text property="spc_delivery_percent" style="width:100px;text-align:right;"
                                   showHelp="false" readonly="${Specification.readOnlyIfNotAdminEconomistLawyerForExecuted}"
                                   onchange="updateReminder()"/>
                        <bean:message key="Specification.spc_delivery_percent"/>
                      </td>
                      <td>
                        <ctrl:checkbox property="spc_percent_or_sum_sum" styleClass="checkbox" value="1" onclick="sumOnClick();" readonly="${Specification.readOnlyIfNotAdminEconomistLawyerForExecuted}"/>&nbsp;
                        <ctrl:text property="spc_delivery_sum" style="width:100px;text-align:right;"
                                   showHelp="false" readonly="${Specification.readOnlyIfNotAdminEconomistLawyerForExecuted}"
                                   onchange="updateReminder()"/>&nbsp;
                        <ctrl:write name="Specification" property="currencyName"/>
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr>
                <td>
                  <table width="100%" cellpadding="0" cellspacing="0">
                    <tr id="payedDate">
                      <td width="170px"></td>
                      <td>
                        <span style="color:#008000" id="payed_date"><ctrl:write name="Specification" property="payed_date_formatted"/></span>
                      </td>
                    </tr>
                    <tr>
                      <td width="170px"></td>
                      <td>
                        <table width="100%" cellpadding="0" cellspacing="0">
                          <tr>
                            <td width="35px"><bean:message key="Specification.payed_date_text"/></td>
                            <td width="220px">
                              <ctrl:date property="spc_delivery_date" style="width:185px;" showHelp="false"
                                         readonly="${Specification.readOnlyIfNotAdminEconomistLawyerForExecuted}"
                                         onchange="updateReminder()" afterSelect="updateReminder"/>
                            </td>
                            <td>
                              <span style="color:red" id="reminder"></span>
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
                  <table width="100%" cellpadding="0" cellspacing="0">
                    <tr valign="top">
                      <td width="170px">
                        <bean:message key="Specification.spc_add_pay_cond"/>
                        <ctrl:help htmlName="Specification.spc_add_pay_cond"/>
                      </td>
                      <td>
                        <ctrl:textarea property="spc_add_pay_cond" style="width:600px;height:78px;" showHelp="false"
                                         readonly="${Specification.readOnlyIfNotAdminForExecuted}"/>
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr>
                <td>
                  <table width="100%" cellpadding="0" cellspacing="0">
                    <tr valign="top">
                      <td width="170px">
                        <bean:message key="Specification.spc_payments"/>
                        <ctrl:help htmlName="Specification.spc_payments"/>
                      </td>
                      <td id="payments">
                        <script type="text/javascript" language="JScript">
                            doAjax({
                              synchronous:true,
                              url:'<ctrl:rewrite action="/SpecificationAction" dispatch="ajaxSpecificationPaymentsGrid"/>',
                              params:{counter:'0'},
                              update:'payments'
                            });
                        </script>
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
                  <table width="100%" cellpadding="0" cellspacing="0">
                    <tr>
                      <td width="35px"><ctrl:checkbox property="spc_montage" styleClass="checkbox" value="1" onclick="spcMontageOnClick()"/></td>
                      <td width="190px"><bean:message key="Specification.spc_montage"/></td>
                      <td width="35px"><ctrl:checkbox property="spc_pay_after_montage" styleClass="checkbox" value="1"/></td>
                      <td><bean:message key="Specification.spc_pay_after_montage"/></td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr>
                <td>
                  <table width="100%" cellpadding="0" cellspacing="0">
                    <tr>
                      <td width="35px"><ctrl:checkbox property="spc_fax_copy" styleClass="checkbox" value="1" onclick="spcFaxCopyOnClick()" readonly="${Specification.readOnlyIfNotAdminEconomistLawyerForExecuted}"/></td>
                      <td width="190px"><bean:message key="Specification.spc_fax_copy"/></td>
                      <td width="35px"><ctrl:checkbox property="spc_original" styleClass="checkbox" value="1" onclick="spcOriginalOnClick()" readonly="${Specification.readOnlyIfNotAdminEconomistLawyerForExecuted}"/></td>
                      <td><bean:message key="Specification.spc_original"/></td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr>
                <td>
                  <table width="100%" cellpadding="0" cellspacing="0">
                    <tr>
                      <td width="35px"><ctrl:checkbox property="spc_group_delivery" styleClass="checkbox" value="1" readonly="${Specification.readOnlyGroupDelivery}"/></td>
                      <td><bean:message key="Specification.spc_group_delivery"/></td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr>
                <td>
                  <table width="100%" cellpadding="0" cellspacing="0">
                    <tr>
                      <td width="35px">
                        <ctrl:checkbox property="spc_annul" styleClass="checkbox" value="1"/>&nbsp;&nbsp;
                      </td>
                      <td width="100px">
                        <bean:message key="Specification.spc_annul"/>
                      </td>
                      <td><ctrl:date property="spc_annul_date" style="width:150px;" showHelp="false"/></td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr>
                <td align="right" class=formSpace>
                  &nbsp;
                </td>
              </tr>

             <tr>
                <td >
                  <table cellspacing="2" width="100%">
                    <tr>
                      <td>
                        <bean:message key="Specification.attachments"/>
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
                                <grid:colCustom>
                                  <a href="" onclick="return downloadAttachment(${record.idx});">${record.originalFileName}</a>
                                  <logic:notEmpty name="record" property="original">
                                    <span style="color:green"> <bean:message key="Attachments.copy"/></span>
                                  </logic:notEmpty>
                                </grid:colCustom>
                              </logic:equal>
                              <logic:equal name="Contract" property="showAttachFiles" value="false">
                                <grid:colCustom property="originalFileName">
                                  <logic:notEmpty name="record" property="original">
                                    <span style="color:green"> <bean:message key="Attachments.copy"/></span>
                                  </logic:notEmpty>
                                </grid:colCustom>
                              </logic:equal>
                              <logic:empty name="record" property="original">
                                <grid:colDelete width="1%" dispatch="deleteAttachment"  scriptUrl="attachmentId=${record.idx}" type="submit" tooltip="tooltip.Attachments.delete"/>
                              </logic:empty>
                              <logic:notEmpty name="record" property="original">
                                <grid:colDelete width="1%" dispatch="deleteAttachment" askUser="deleteAttachAsk" scriptUrl="attachmentId=${record.idx}"  type="submit" tooltip="tooltip.Attachments.delete" />
                              </logic:notEmpty>
                            </grid:row>
                          </grid:table>
                        </div>
                        <logic:equal name="Specification" property="showAttach" value="true">
                          <div class=gridBottom>
                            <table width="100%">
                              <tr>
                                <td width="10%">
                                  <ctrl:serverList property="copy_attachment_name" idName="copy_attachment_id" action="/ContractAttacmentsListAction"
                                                   style="width:140px;" readonly="${Specification.readOnlyIfNotAdminEconomistLawyerForExecuted}"/>
                                </td>
                                <td align="left">
                                  <ctrl:ubutton type="submit" dispatch="deferredAttachCopy" styleClass="width240" readonly="${Specification.readOnlyIfNotAdminEconomistLawyerForExecuted}">
                                    <bean:message key="button.attachCopy"/>
                                  </ctrl:ubutton>
                                </td>
                                <td align="right">
                                  <ctrl:ubutton type="submit" dispatch="deferredAttach" styleClass="width80" readonly="${Specification.readOnlyIfNotAdminEconomistLawyerForExecuted}">
                                    <bean:message key="button.attach"/>
                                  </ctrl:ubutton>
                                </td>
                              </tr>
                            </table>
                          </div>
                        </logic:equal>
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
            </table>
          </tab:tabPane>

          <tab:tabPane id="complaintSpecification" tabTitle="Specification.complaint">
            <table align="center">

              <tr>
                <td>
                  <table width="100%" cellpadding="0" cellspacing="0">
                    <tr>
                      <td width="100px"><bean:message key="Specification.spc_letter1_date"/></td>
                      <td><ctrl:date property="spc_letter1_date" style="width:230px;" showHelp="false"
                                     readonly="${Specification.readOnlyIfNotAdminForExecuted}"/></td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr>
                <td>
                  <table width="100%" cellpadding="0" cellspacing="0">
                    <tr>
                      <td width="100px"><bean:message key="Specification.spc_letter2_date"/></td>
                      <td><ctrl:date property="spc_letter2_date" style="width:230px;" showHelp="false"
                                     readonly="${Specification.readOnlyIfNotAdminForExecuted}"/></td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr>
                <td>
                  <table width="100%" cellpadding="0" cellspacing="0">
                    <tr>
                      <td width="100px"><bean:message key="Specification.spc_letter3_date"/></td>
                      <td><ctrl:date property="spc_letter3_date" style="width:230px;" showHelp="false"
                                     readonly="${Specification.readOnlyIfNotAdminForExecuted}"/></td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr>
                <td>
                  <table width="100%" cellpadding="0" cellspacing="0">
                    <tr>
                      <td width="100px"><bean:message key="Specification.spc_complaint_in_court_date"/></td>
                      <td><ctrl:date property="spc_complaint_in_court_date" style="width:230px;" showHelp="false" 
                                     readonly="${Specification.readOnlyIfNotAdminForExecuted}"/></td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr>
                <td>
                  <table width="100%" cellpadding="0" cellspacing="0">
                    <tr>
                      <td><bean:message key="Specification.spc_comment"/></td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr>
                <td>
                  <table width="100%" cellpadding="0" cellspacing="0">
                    <tr>
                      <td>
                        <ctrl:textarea property="spc_comment" style="width:340px;height:78px;" readonly="${Specification.readOnlyIfNotAdminEconomistLawyerForExecuted}"/>
                      </td>
                    </tr>
                  </table>
                </td>
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
        <ctrl:ubutton type="link" dispatch="back" styleClass="width80" readonly="false">
          <bean:message key="button.cancel"/>
        </ctrl:ubutton>
        <input id='buttonSave' type='button' onclick='processClick()' class='width80' value='<bean:message key="button.save"/>'/>
      </td>
    </tr>
  </table>

</ctrl:form>

<script type="text/javascript" language="JScript">
  var spcSum = document.getElementById('spc_summ');
  var noRoundSum = document.getElementById('noRoundSum');

  var spcPercentOrSumPercent = document.getElementById('spc_percent_or_sum_percent');
  var spcPercentOrSumSum = document.getElementById('spc_percent_or_sum_sum');
  var spcDeliveryPercent = document.getElementById('spc_delivery_percent');
  var spcDeliverySum = document.getElementById('spc_delivery_sum');

  var spcMontage = document.getElementById('spc_montage');
  var spcPayAfterMontage = document.getElementById('spc_pay_after_montage');

  var spcFaxCopy = document.getElementById('spc_fax_copy');
  var spcOriginal = document.getElementById('spc_original');

  var deliveryTermId = document.getElementById("deliveryTerm.id");
  var deliveryTermName = document.getElementById('deliveryTerm.name');
  var spcSumNds = document.getElementById("spc_summ_nds");
  var ndsRate = document.getElementById('ndsRate');
  var spcNumber = document.getElementById('spc_number');
  var spcDate = document.getElementById('spc_date');
  var spcDeliveryDate = document.getElementById('spc_delivery_date');

  var buttonSave = document.getElementById('buttonSave');
  buttonSave.disabled = ${Specification.readOnlySaveButton};
  
  <logic:equal value="false" name="Specification" property="readOnlySaveButton">
    spcMontageOnClick();
  </logic:equal>

  checkGraphic();
  showAfterPrepay();
  selectRequiredFields();
  setDescriptionColour();
  checkPercentOrSumOnLoad();
  updateAdditionalDaysCount(document.getElementById('spc_additional_days_count'));

  function addToPaymentGrid()
  {
    var params =  $H(Form.serializeElements($$('#payments input'),true));
    params.merge({counter:'0'});
    doAjax({
      url:'<ctrl:rewrite action="/SpecificationAction" dispatch="ajaxAddToPaymentGrid"/>',
      params:params,
      synchronous:true,
      update:('payments'),
      okCallback:function()
      {
        checkGraphic();
      }
    });
  }

  function removeFromPaymentGrid(id)
  {
    var params =  $H(Form.serializeElements($$('#payments input'),true));
    params.merge({counter:'0',id:id});

    doAjax({
      url:'<ctrl:rewrite action="/SpecificationAction" dispatch="ajaxRemoveFromPaymentGrid"/>',
      params:params,
      synchronous:true,
      update:'payments',
      okCallback:function()
      {
        checkGraphic();
      }
    });
  }

  function percentChanged(obj)
  {
    var sumObj = document.getElementById(obj.name.replace('PercentFormatted', 'SumFormatted'));
    var sumStr = getSumForJS(spcSum.value);
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

    checkGraphic();
  }

  function sumChanged(obj)
  {
    var percentObj = document.getElementById(obj.name.replace('SumFormatted', 'PercentFormatted'));
    var sumStr = getSumForJS(spcSum.value);
    var sum = getSumForJS(obj.value);
    var tmpPercent = parseFloat(sum) * 100 / parseFloat(sumStr);
    percentObj.value = Math.round(tmpPercent * 100000) / 100000;
    percentObj.value = percentObj.value.replace('.', ',');

    checkGraphic();
  }

  //фиктивная фукнция вызывается после выбора даты, чтобы не передавала в checkGraphic() лишнее
  function checkGraphicDate()
  {
    checkGraphic();  
  }

  function recalcSums()
  {
    var i = 0;
    for ( ; i < 100; i++ )
    {
      var percentObj = document.getElementById('specificationPayments['+ i +'].sppPercentFormatted');
      if ( percentObj )
      {
        percentChanged(percentObj);  
      }
    }
  }

  function checkGraphic(showMsg)
  {
    var sumCheck = parseFloat(getSumForJS(spcSum.value));
    var percentCheck = 100;
    var i = 0;

    var dateCheck = document.getElementById('specificationPayments[0].sppDateFormatted');
    var correctSum = true;
    var correctPercent = true;    
    var correctDate = true;
    for ( ; i < 100; i++ )
    {
      var percentObj = document.getElementById('specificationPayments['+ i +'].sppPercentFormatted');
      var sumObj = document.getElementById('specificationPayments['+ i +'].sppSumFormatted');
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
          var dateObj = document.getElementById('specificationPayments['+ i +'].sppDateFormatted');
          if ( dateObj && '' != dateObj.value.trim() && '' != dateCheck.value.trim() )
          {
            if ( getDateForJS(dateCheck.value.trim()) > getDateForJS(dateObj.value.trim()) )
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
      var percentObj = document.getElementById('specificationPayments['+ i +'].sppPercentFormatted');
      var sumObj = document.getElementById('specificationPayments['+ i +'].sppSumFormatted');
      var dateObj = document.getElementById('specificationPayments['+ i +'].sppDateFormatted');
      if ( percentObj && sumObj )
      {
        sumObj.style.backgroundColor = ( !correctSum ) ? 'pink' : 'white';
        percentObj.style.backgroundColor = ( !correctPercent ) ? 'pink' : 'white';
        dateObj.style.backgroundColor = ( !correctDate ) ? 'pink' : 'white';
      }
    }

    if ( showMsg && !correctSum )
    {
      alert('<bean:message key="msg.specification.incorrectSum"/>');
      return false;
    }
    if ( showMsg && !correctPercent )
    {
      alert('<bean:message key="msg.specification.incorrectPercent"/>');
      return false;
    }
    if ( showMsg && !correctDate )
    {
      alert('<bean:message key="msg.specification.incorrectDate"/>');
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
    var sumCheck = parseFloat(getSumForJS(spcSum.value));
    var percentCheck = 100;
    var sum = 0;
    var percent = 0;
    var i = 0;

    for ( ; i < 100; i++ )
    {
      var percentObj = document.getElementById('specificationPayments['+ i +'].sppPercentFormatted');
      var sumObj = document.getElementById('specificationPayments['+ i +'].sppSumFormatted');
      var j = i + 1;
      var percentObjLast = document.getElementById('specificationPayments['+ j +'].sppPercentFormatted');
      var sumObjLast = document.getElementById('specificationPayments['+ j +'].sppSumFormatted');
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
    var checkStr = '<bean:message key="Specification.checkRedStr"/>';
    var checkStr1 = '<bean:message key="Specification.checkRedStr1"/>';
    var checkStr2 = '<bean:message key="Specification.checkRedStr2"/>';
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

  function processClick()
  {
    if ( deliveryTermId.value == "1" && spcDeliveryDate.value == '' )
    {
      alert('<bean:message key="error.specification.add_delivery_date"/>');
      return;
    }

    var days = parseInt(document.getElementById('spc_additional_days_count').value);
    if ( days < 0 )
    {
      alert('<bean:message key="error.specification.spcAdditionalDaysCountError"/>');
      return;
    }

    if ( deliveryTermId.value == "2" && !spcPercentOrSumPercent.checked && !spcPercentOrSumSum.checked )
    {
      alert('<bean:message key="error.specification.add_percent_sum"/>');
      return;
    }

    var validate = checkGraphic(true);
    var ratePrecision = parseFloat(getSumForJS('<bean:message key="Specification.default_nds_precision"/>'));
    var sum = parseFloat(getSumForJS(spcSum.value));
    var sumNDS = parseFloat(getSumForJS(spcSumNds.value));
    var rate = parseFloat(getSumForJS(ndsRate.innerHTML));
    var spcRateNDS = 0;
    var correctPercent = true;
    if ( sum - sumNDS == 0 )
    {
      correctPercent = false;
    }
    else
    {
      spcRateNDS = sum / (sum - sumNDS) * 100 - 100;
    }
    if (spcRateNDS > rate + ratePrecision || spcRateNDS < rate - ratePrecision)
    {
      correctPercent = false;
    }
    if ( !correctPercent )
    {
      if (!isUserAgree('<bean:message key="msg.specification.percent"/>', false, 400, 100))
      {
        correctPercent = true;
      }
    }
    if ( validate && correctPercent )
    {
      submitDispatchForm("beforeSave");
    }
  }

  function selectRequiredFields()
  {
	  spcNumber.style.backgroundColor = ( spcNumber.value == '' ) ? 'pink' : 'white';
	  spcDate.style.backgroundColor = ( spcDate.value == '' ) ? 'pink' : 'white';
    spcSum.style.backgroundColor = ( spcSum.value == '' ) ? 'pink' : 'white';
    deliveryTermName.style.backgroundColor = ( deliveryTermName.value == '' ) ? 'pink' : 'white';
  }

  function checkPercentOrSumOnLoad()
  {
    if ( spcPercentOrSumPercent.checked )
    {
	    spcPercentOrSumSum.checked = false;
	    spcDeliverySum.readOnly = true;
	    spcDeliverySum.value = '0,00';
    }
    if ( spcPercentOrSumSum.checked )
    {
	    spcPercentOrSumPercent.checked = false;
	    spcDeliveryPercent.readOnly = true;
	    spcDeliveryPercent.value = '0,00';
    }
  }

  function percentOnClick()
  {
    if ( spcPercentOrSumSum.checked )
    {
	    spcPercentOrSumSum.checked = false;
	    spcDeliverySum.readOnly = true;
	    spcDeliverySum.value = '0,00';
    }

	  spcPercentOrSumPercent.checked = true;
	  spcDeliveryPercent.readOnly = false;

    updateReminder();
	  recalculatePaymentGrid();
  }

  function sumOnClick()
  {
    if ( spcPercentOrSumPercent.checked || (!spcPercentOrSumSum.checked && !spcPercentOrSumPercent.checked) )
    {
	    spcPercentOrSumPercent.checked = false;
	    spcDeliveryPercent.readOnly = true;
	    spcDeliveryPercent.value = '0,00';
    }

	  spcPercentOrSumSum.checked = true;
	  spcDeliverySum.readOnly = false;
    
    updateReminder();
	  recalculatePaymentGrid();
  }

  function updateAdditionalDaysCount(obj)
  {
    if (document.getElementById('payed_date').innerHTML != '' && obj.value != '' && spcDeliveryDate.value == '')
    {
      var days = parseInt(document.getElementById('spc_additional_days_count').value);
      if ( days < 0 )
      {
        alert('<bean:message key="error.specification.spcAdditionalDaysCountError"/>');
        obj.value = '';
        return;
      }

      doAjax({
        url:'<ctrl:rewrite action="/SpecificationAction" dispatch="ajaxCalculateDeliveryDate"/>',
        params:{'additionalDaysCount':obj.value},
        synchronous:true,
        update:'resultMsg',
        okCallback:function()
        {
	        spcDeliveryDate.value = document.getElementById('resultMsg').innerHTML.trim();
          updateReminder();
        }
      });
    }
  }

  function updateReminder()
  {
    var percentOrSum = '';
    if ( spcPercentOrSumPercent.checked )
    {
      percentOrSum = '0';
    }
    if ( spcPercentOrSumSum.checked )
    {
      percentOrSum = '1';
    }

    doAjax({
        url:'<ctrl:rewrite action="/SpecificationAction" dispatch="ajaxReloadReminder"/>',
        params:{'percentOrSum':percentOrSum, 'deliveryTermId':deliveryTermId.value, 'percent':spcDeliveryPercent.value, 'sum':spcDeliverySum.value, 'deliveryDate':spcDeliveryDate.value},
        synchronous:true,
        update:'reminder'
      });
  }

  function spcMontageOnClick()
  {
    if ( spcMontage.checked )
    {
	    spcPayAfterMontage.disabled = false;
    }
    else
    {
	    spcPayAfterMontage.checked = false;
	    spcPayAfterMontage.disabled = true;
    }
  }

  function spcFaxCopyOnClick()
  {
	  spcOriginal.checked=false;
  }

  function spcOriginalOnClick()
  {
	  spcFaxCopy.checked = false;
  }

  function changedDeliveryTerm()
  {
    showAfterPrepay();
    selectRequiredFields();
    updateReminder();
  }

  function changeSpcDate()
  {
    selectRequiredFields();
    doAjax({
        url:'<ctrl:rewrite action="/SpecificationAction" dispatch="ajaxReloadDate"/>',
        params:{'spcDate':spcDate.value},
        synchronous:true,
        update:'ndsRate'
      });
  }

  function submitReloadPrices(obj)
  {
    selectRequiredFields();
    if ( deliveryTermId.value == "2" )
    {
      doAjax({
        url:'<ctrl:rewrite action="/SpecificationAction" dispatch="ajaxReloadPrices"/>',
        params:{'spcSum':obj.value},
        synchronous:true,
        update:'payed_date',
        okCallback:function()
        {
	        spcDeliveryDate.value = '';
          updateAdditionalDaysCount(document.getElementById('spc_additional_days_count'));
        }
      });
    }
    recalcSums();
    checkGraphic();
  }

  function recalculatePaymentGrid()
  {
    var params =  $H(Form.serializeElements($$('#payments input'),true));
    doAjax({
      url:'<ctrl:rewrite action="/SpecificationAction" dispatch="ajaxRecalculatePaymentGrid"/>',
      params:params,
      synchronous:true,
      update:'payments'
    });

    setDescriptionColour();
  }

  function showAfterPrepay()
  {
    if ( deliveryTermId.value == "2" )
    {
      document.getElementById('additionalDaysCount').style.display = 'block';
      document.getElementById('afterPrepay').style.display = 'block';
      document.getElementById('payedDate').style.display = 'block';
    }
    else
    {
      document.getElementById('additionalDaysCount').style.display = 'none';
      document.getElementById('afterPrepay').style.display = 'none';
      document.getElementById('payedDate').style.display = 'none';
    }
  }

  function downloadAttachment(id)
  {
	  document.getElementById('for-insert').innerHTML='<iframe src=\'<html:rewrite action="/SpecificationAction.do?dispatch=downloadAttachment"/>&attachmentId='+id+'\' style=\'display:none\' />';
		return false;
	}

  initFunctions.push(updateReminder);
</script>
