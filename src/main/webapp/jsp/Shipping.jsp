<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>

<div id='for-insert'></div>
<div style="display:none" id="resultMsg"></div>

<ctrl:form readonly="${Shipping.formReadOnly}">
<ctrl:hidden property="shp_id"/>
<ctrl:hidden property="shp_closed"/>
<ctrl:hidden property="shp_block"/>
<ctrl:hidden property="shp_montage"/>
<ctrl:hidden property="shp_serial_num_year_out"/>

<table class=formBackTop align="center" width="99% ">
  <tr>
    <td>
      <table cellspacing="0" width="100%" border="0">
        <logic:notEmpty name="Shipping" property="shp_id">
          <tr>
            <td colspan="2">
              <table width="100%" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="10%"><bean:message key="Shipping.create"/></td>
                  <td width="40%"><ctrl:write name="Shipping" property="shp_date_create"/> <ctrl:write name="Shipping"
                                                                                                       property="createUser.userFullName"/></td>
                  <td align="right"><bean:message key="Shipping.edit"/></td>
                  <td width="33%">&nbsp;&nbsp;&nbsp;&nbsp;<ctrl:write name="Shipping" property="shp_date_edit"/> <ctrl:write
                    name="Shipping" property="editUser.userFullName"/></td>
                </tr>
              </table>
            </td>
          </tr>
        </logic:notEmpty>
        <tr>
          <td colspan="10">
            <table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="214px"><bean:message key="Shipping.shp_number"/></td>
                <td><ctrl:text property="shp_number" style="width:230px;" showHelp="false"/></td>
              </tr>
            </table>
          </td>
        </tr>

        <tr>
          <td colspan="10">
            <table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="214px"><bean:message key="Shipping.shp_date"/></td>
                <td><ctrl:date property="shp_date" style="width:230px;" showHelp="false"/></td>
              </tr>
            </table>
          </td>
        </tr>

        <tr>
          <td colspan="2">
            <table width="100%" cellspacing="0" cellpadding="0">
              <tr valign="top">
                <td width="200px"><bean:message key="Shipping.shp_contractor"/></td>
                <td width="34%" align="left">
                  <table border="0" cellSpacing="0" cellPadding="0">
                    <tr>
                      <td>
                        <ctrl:serverList property="contractor.name" idName="contractor.id" action="/ContractorsListAction"
                                         filter="filter"  style="width:230px;" callback="onContractorSelect"/>
                      </td>
                      <td>&nbsp;</td>
                      <td>
                        <ctrl:ubutton type="submit" dispatch="newContractor" styleClass="width80">
                          <bean:message key="button.add"/>
                        </ctrl:ubutton>
                      </td>
                    </tr>
                  </table>
                </td>
                <td width="22%" align="right"><bean:message key="Shipping.shp_montage"/>&nbsp;&nbsp;</td>
                <td>
                  <ctrl:checkbox property="shp_montage_checkbox" styleClass="checkbox"
                                 value="1" onclick="onMontageClick()"/>
                  &nbsp;&nbsp;&nbsp;&nbsp;
                  <bean:message key="Shipping.shp_serial_num_year_out"/>&nbsp;&nbsp;
                  <ctrl:checkbox property="shp_serial_num_year_out_checkbox" styleClass="checkbox"
                                 onclick="onSerialNumYearOutClick()" value="1"/>
                </td>
              </tr>
            </table>
          </td>
        </tr>

        <tr>
          <td colspan="2">
            <table width="100%" cellspacing="0" cellpadding="0">
              <tr>
                <td width="50%">
                  <table cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="200px"><bean:message key="Shipping.shp_contract"/></td>
                      <td colspan=2><ctrl:serverList property="contractNumberWithDate" idName="contract.con_id"
                                           action="/ContractsDepFromContractorListAction" scriptUrl="ctr_id=$(contractor.id)" selectOnly="true"
                                           style="width:330px;" callback="onContractSelect"/></td>
                    </tr>
                    <tr>
                      <td><bean:message key="Shipping.shp_specification"/></td>
                      <td colspan=2><ctrl:serverList property="specificationNumberWithDate" idName="specification.spc_id"
                                           action="/SpecificationsDepFromContractListAction" scriptUrl="id=$(contract.con_id)&with_summ=true"
                                           selectOnly="true" style="width:330px;" callback="onSpecificationSelect" /></td>
                    </tr>
                    <tr>
                      <td><bean:message key="Shipping.shp_date_expiration"/></td>
                      <td><ctrl:date property="shp_date_expiration" style="width:230px;"/></td>
                      <td>
                        <logic:equal name="Shipping" property="showPayAfterMontage" value="true">
                          &nbsp;<span style="color:#008000"><bean:message key="Shipping.shp_pay_after_montage"/></span>
                        </logic:equal>
                      </td>
                    </tr>
                  </table>
                </td>
                <td width="22%" valign="top" align="right">
                  <bean:message key="Shipping.specification_pay_cond"/>&nbsp;&nbsp;
                </td>
                <td>
                  <ctrl:textarea property="specification.spc_add_pay_cond" style="width:332px;height:80px;" readonly="true"/>
                </td>
              </tr>
            </table>
          </td>
        </tr>

        <tr>
          <td colspan="10">
            <table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="214px"><bean:message key="Shipping.shp_summ_plus_nds"/></td>
                <td>
                  <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><ctrl:text property="shp_summ_plus_nds_formatted" style="width:276px;" showHelp="false"/></td>
                      <td width="70px" align="right"><bean:message key="Shipping.shp_currency"/>&nbsp;</td>
                      <td><ctrl:serverList property="currency.name" idName="currency.id" action="/CurrenciesListAction"
                                           selectOnly="true" style="width:50px;" showHelp="false"/></td>
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
          <td colspan="10" id="shippingsGrid">
            <script type="text/javascript" language="JScript">
              doAjax({
                synchronous:true,
                url:'<ctrl:rewrite action="/ShippingAction" dispatch="ajaxShippingsGrid"/>',
                update:'shippingsGrid'
              });
            </script>
          </td>
        </tr>

        <tr>
          <td colspan="2">
            <div class=gridBottom>
              <table width="100%">
                <tr>
                  <td align="left">
                    <table width="50%">
                      <tr>
                        <td align="left">
                          <input id='buttonMakeMine' type='button' onclick='makeMineClick()' class='nowidth' value='<bean:message key="button.ShippingMakeMine"/>'/>
                        </td>
                        <td align="left">
                          <ctrl:serverList property="manager.userFullName" idName="manager.usr_id" action="/UsersListAction"
                                           filter="filter" style="width:150px;"/>
                        </td>
                      </tr>
                    </table>
                  </td>
                  <td align="right" colspan="1">
                    <ctrl:ubutton type="submit" dispatch="editShippingPositions" styleClass="width165" readonly="${Shipping.formReadOnly}" askUser="">
                      <bean:message key="button.editTable"/>
                    </ctrl:ubutton>
                  </td>
                </tr>
              </table>
            </div>
          </td>
        </tr>

        <tr>
          <td colspan="10">
            <table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="190px"><bean:message key="Shipping.shp_summ_transport"/></td>
                <td><ctrl:text property="shp_summ_transport_formatted" style="width:165px;"/></td>
                <td width="30px"></td>
                <td width="130px"><bean:message key="Shipping.shp_sum_update"/></td>
                <td><ctrl:text property="shp_sum_update_formatted" style="width:165px;"/></td>
              </tr>
            </table>
          </td>
        </tr>

        <tr>
          <td colspan="4">
            <table width="100%">
              <tr>
                <hr>
              </tr>
            </table>
          </td>
        </tr>

        <tr>
          <td colspan="4">
            <table width="100%">
              <tr>
                <td><bean:message key="Shipping.shp_letter_text"/></td>
              </tr>
            </table>
          </td>
        </tr>

        <tr>
          <td colspan="4">
            <table>
              <tr>
                <td>
                  <table>
                    <tr>
                      <td><bean:message key="Shipping.shp_letter1_date"/></td>
                      <td><ctrl:date property="shp_letter1_date" style="width:230px;" readonly="${Shipping.readOnlyIfNotAdmOrLawyer}" showHelp="false"/></td>
                    </tr>
                    <tr>
                      <td><bean:message key="Shipping.shp_letter2_date"/></td>
                      <td><ctrl:date property="shp_letter2_date" style="width:230px;" readonly="${Shipping.readOnlyIfNotAdmOrLawyer}" showHelp="false"/></td>
                    </tr>
                    <tr>
                      <td><bean:message key="Shipping.shp_letter3_date"/></td>
                      <td><ctrl:date property="shp_letter3_date" style="width:230px;" readonly="${Shipping.readOnlyIfNotAdmOrLawyer}" showHelp="false"/></td>
                    </tr>
                    <tr>
                      <td><bean:message key="Shipping.shp_complaint_in_court_date"/></td>
                      <td><ctrl:date property="shp_complaint_in_court_date" style="width:230px;" readonly="${Shipping.readOnlyIfNotAdmOrLawyer}" showHelp="false"/></td>
                    </tr>
                  </table>
                </td>
                <td>
                  <table>
                    <tr>
                      <td>
                        <ctrl:textarea property="shp_comment" style="width:332px;height:95px;" readonly="${Shipping.readOnlyComment}"/>
                      </td>
                     </tr>
                  </table>
                </td>
              </tr>
            </table>
          </td>
        </tr>

        <tr>
          <td colspan="4">
            <table width="100%">
              <tr>
                <hr>
              </tr>
            </table>
          </td>
        </tr>

        <tr>
          <td colspan="2">
            <table width="100%">
              <tr>
                <td align="left">
                  <table width="90%">
                    <tr>
                      <td align="left">
                        <bean:message key="Shipping.contractorWhere"/>
                      </td>
                      <td align="left">
                        <ctrl:serverList property="contractorWhere.name" idName="contractorWhere.id" action="/ContractorsListAction"
                                         filter="filter" style="width:200px;" callback="onContractorWhereSelect" readonly="false"/>
                      </td>
                      <td align="left">
                        &nbsp;&nbsp;<bean:message key="Shipping.contractWhere"/>
                      </td>
                      <td align="left">
                        <ctrl:serverList property="contractNumberWithDateWhere" idName="contractWhere.con_id"
                                         action="/ContractsDepFromContractorListAction" scriptUrl="ctr_id=$(contractorWhere.id)&allCon=1"
                                         style="width:200px;" selectOnly="true" readonly="false"/>
                      </td>
                      <td align="left">
                        &nbsp;&nbsp;<bean:message key="Shipping.shp_notice_date"/>
                      </td>
                      <td align="left">
                        <ctrl:date property="shp_notice_date" style="width:200px;" readonly="false"/>
                      </td>
                    </tr>
                  </table>
                </td>
                <td align="right" colspan="1">
                  <ctrl:ubutton type="submit" dispatch="printNotice" styleClass="width165" readonly="false" askUser="">
                    <bean:message key="button.printNotice"/>
                  </ctrl:ubutton>
                  &nbsp;
                  <ctrl:text property="noticeScale" style="width:40px;text-align:right;" readonly="false"/>
                  <bean:message key="Common.percent"/>
                </td>
              </tr>
            </table>
          </td>
        </tr>

        <tr>
          <td colspan="4">
            <table width="100%">
              <tr>
                <hr>
              </tr>
            </table>
          </td>
        </tr>

        <tr>
          <td colspan="10">
            <table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td><ctrl:checkbox property="shp_original" styleClass="checkbox" value="1" showHelp="false"/></td>
                <td>&nbsp;<ctrl:help htmlName="shp_original"/></td>
                <td><bean:message key="Shipping.shp_original"/></td>
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
            <ctrl:ubutton type="submit" dispatch="back" styleClass="width80" readonly="false" askUser="">
              <bean:message key="button.cancel"/>
            </ctrl:ubutton>
	          <input id='buttonSave' type='button' onclick='processClick()' class='width80' value='<bean:message key="button.save"/>'/>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>

<script type="text/javascript" language="JScript">
  function addToGrid(counter)
  {
    var params =  $H(Form.serializeElements($$('#ajax'+counter+' input'),true));
    params.merge({counter:counter});
    doAjax({
      url:'<ctrl:rewrite action="/ShippingAction" dispatch="ajaxAddToGrid"/>',
      params:params,
      update:('ajax'+counter)
    });
  }

  function removeFromGrid(counter,id)
  {
    var params =  $H(Form.serializeElements($$('#ajax'+counter+' input'),true));
    params.merge({counter:counter,id:id});

    doAjax({
      url:'<ctrl:rewrite action="/ShippingAction" dispatch="ajaxRemoveFromGrid"/>',
      params:params,
      update:'ajax'+counter
    });
  }
</script>

</ctrl:form>

<script language="JScript" type="text/javascript">
  var contractNumber = document.getElementById("contractNumberWithDate");
  var contractId = document.getElementById("contract.con_id");
  var specification_number = document.getElementById("specificationNumberWithDate");
  var specificationId = document.getElementById("specification.spc_id");
  var spcAddPayCondition = document.getElementById("specification.spc_add_pay_cond");
  var currencyNumber = document.getElementById("currency.name");
  var currencyId = document.getElementById("currency.id");

  var shpDate = document.getElementById("shp_date");
  var shpDateExpiration = document.getElementById("shp_date_expiration");
  var shpDateExpirationBtn = document.getElementById("shp_date_expirationBtn");
  shpDateExpiration.readOnly = ${Shipping.readOnlyDateExpiration};
  disableImgButton(shpDateExpirationBtn, ${Shipping.readOnlyDateExpiration});
  var shpSumPlusNdsFormatted = document.getElementById("shp_summ_plus_nds_formatted");

  var contractNumberWhere = document.getElementById("contractNumberWithDateWhere");
  var contractIdWhere = document.getElementById("contractWhere.con_id");

  var shpMontage = document.getElementById("shp_montage");
  var shpMontageCheckbox = document.getElementById("shp_montage_checkbox");
  shpMontageCheckbox.disabled = ${Shipping.readOnlyIfSpecHaveMontage};
  var shpSerialNumYearOut = document.getElementById("shp_serial_num_year_out");
  var shpSerialNumYearOutCheckbox = document.getElementById("shp_serial_num_year_out_checkbox");

  var managerId = document.getElementById("manager.usr_id");

  var buttonMakeMine = document.getElementById("buttonMakeMine");
  buttonMakeMine.disabled = ${Shipping.formReadOnly};
  var buttonSave = document.getElementById('buttonSave');
  buttonSave.disabled = ${Shipping.saveReadOnly};

  <logic:equal value="true" name="Shipping" property="showPayAfterMontage">
  shpDateExpiration.value = '';
  </logic:equal>

  <logic:notEqual value="true" name="Shipping" property="formReadOnly">
    setSerialNumYearOut();
    checkMontageOnLoad();
  </logic:notEqual>

  function setSerialNumYearOut()
  {
    if ( shpMontageCheckbox.checked )
    {
      shpSerialNumYearOutCheckbox.disabled = false;
    }
    else
    {
      shpSerialNumYearOutCheckbox.checked = false;
      shpSerialNumYearOut.value = '';
      shpSerialNumYearOutCheckbox.disabled = true;
      onSerialNumYearOutClick();
    }
  }

  function onContractorSelect()
  {
    contractNumber.value = "";
    contractId.value = "";
    specificationId.value = "";
    specification_number.value = "";
    spcAddPayCondition.value = "";

    shpDateExpiration.value = shpDate.value;
    shpDateExpiration.readOnly = true;
    disableImgButton(shpDateExpirationBtn.disabled, true);
  }

  function onContractSelect(arg)
  {
    specificationId.value = "";
    specification_number.value = "";
    spcAddPayCondition.value = "";
    currencyId.value = arg?arg.arguments[2]:null;
    currencyNumber.value = arg?arg.arguments[3]:null;
  }

  function onMontageClick()
  {
    setSerialNumYearOut();
    if ( shpMontageCheckbox.checked )
    {
      shpSerialNumYearOutCheckbox.checked = true;
      shpSerialNumYearOut.value = '1';
      onSerialNumYearOutClick();
    }
    shpMontage.value = shpMontageCheckbox.checked ? '1' : '';

    doAjax({
      synchronous:true,
      params:{'montage':shpMontage.value},
      url:'<ctrl:rewrite action="/ShippingAction" dispatch="ajaxChangeMontage"/>',
      update:'shippingsGrid'
    });

    if ( shpMontageCheckbox.checked )
    {
      var i = 0;
      for ( ; i < 1000; i++ )
      {
        var checkBox = document.getElementsByName("grid.row(" + i + ").position.montageOffCheckbox")[0];
        if ( !checkBox )
        {
          break;
        }
        checkBox.checked = false;
        montageOffClick(checkBox);
      }
    }
  }

  function checkMontageOnLoad()
  {
    if ( shpMontageCheckbox.checked )
    {
      var i = 0;
      for ( ; i < 1000; i++ )
      {
        var checkBox = document.getElementsByName("grid.row(" + i + ").position.montageOffCheckbox")[0];
        if ( !checkBox )
        {
          break;
        }
        montageOffClick(checkBox);
      }
    }
  }

  function onSerialNumYearOutClick()
  {
    shpSerialNumYearOut.value = shpSerialNumYearOutCheckbox.checked ? '1' : '';
    doAjax({
      synchronous:true,
      params:{'serialNumYearOut':shpSerialNumYearOut.value},
      url:'<ctrl:rewrite action="/ShippingAction" dispatch="ajaxChangeSerialNumYearOut"/>',
      update:'shippingsGrid'
    });
  }

  function onSpecificationSelect()
  {
    doAjax({
      synchronous:true,
      params:{'specificationId':specificationId.value},
      url:'<ctrl:rewrite action="/ShippingAction" dispatch="ajaxChangeSpecification"/>',
      update:'resultMsg',
      okCallback:function()
      {
        var resultStr = document.getElementById('resultMsg').innerHTML;
        var arrayResult = resultStr.split('|');

        shpDateExpiration.readOnly = eval(arrayResult[1]);
        disableImgButton(shpDateExpirationBtn, eval(arrayResult[1]));

        if ( arrayResult[0] != shpMontage.value && arrayResult[0] != '' )
        {
          shpMontageCheckbox.checked = (arrayResult[0] == '1');
          onMontageClick();
        }
        shpMontageCheckbox.disabled = eval(arrayResult[2]);
        spcAddPayCondition.value = arrayResult[3];
      }
    });
  }

  function montageOffClick(obj)
  {
		var prefix = obj.name.substring(0, obj.name.lastIndexOf('.') + 1);
    var ctrlCheckCtrl = document.getElementsByName(prefix + "montageOff")[0];
    var ctrlCheck = document.getElementsByName(prefix + "montageOffCheckbox")[0];
    var ctrlMontageTime = document.getElementsByName(prefix + "montageTime_formatted")[0];
		ctrlMontageTime.disabled = ctrlCheck.checked;
		var ctrlEnterInUseDate = document.getElementsByName(prefix + "enterInUseDate")[0];
		var ctrlEnterInUseDateBtn = document.getElementsByName(prefix + "enterInUseDateBtn")[0];
		ctrlEnterInUseDate.disabled = ctrlCheck.checked;
		disableImgButton(ctrlEnterInUseDateBtn, ctrlCheck.checked);
    if ( ctrlCheck.checked )
    {
      ctrlMontageTime.value = '0,0';
      ctrlEnterInUseDate.value = '';
    }
    ctrlCheckCtrl.value = ctrlCheck.checked ? '1' : '';
    var idx = prefix.split('(')[1].split(')')[0];
    var showFlag = document.getElementById('idShpNameWarnFlag' + idx).innerHTML;
    var showMsg = !ctrlCheck.checked && showFlag == 'true';
    document.getElementById('idShpNameWarn' + idx).style.display = showMsg ? '' : 'none' ;
    document.getElementById('idShpNameWarn' + idx).innerHTML = showMsg ? '<bean:message key="ShippingProduces.warnInNomenclatureText"/>' : '' ;
  }

  function getCheckboxes1stColumn()
  {
    return $$('#shippingGrid td.first-column input.grid-checkbox');
  }

  function changeGridSelection(origObj)
  {
    var chs = getCheckboxes1stColumn();
    var i = 0;
    for (; i < chs.length; i++)
    {
      if ( !$(chs[i]).disabled )
      {
       $(chs[i]).checked = origObj.checked;
      }
    }
  }

  function makeMineClick()
  {
    var chs = getCheckboxes1stColumn();
    var i = 0;
    var foundCheck = false;
    var ids = '|';
    for (; i < chs.length; i++)
    {
      if ( $(chs[i]).checked )
      {
        foundCheck = true;
        ids += i + '|';
      }
    }

    if ( foundCheck && managerId.value != '' )
    {
      var params = $H(Form.serializeElements($$('#shippingsGrid input'),true));
      doAjax({
        synchronous:true,
        params:params,
        url:'<ctrl:rewrite action="/ShippingAction" dispatch="ajaxShippingsGrid"/>',
        update:'shippingsGrid'
      });

      doAjax({
        synchronous:true,
        params:{'ids':ids, 'managerId':managerId.value},
        url:'<ctrl:rewrite action="/ShippingAction" dispatch="ajaxMakeMine"/>',
        update:'shippingsGrid'
      });

      checkMontageOnLoad();
    }
  }

  function onContractorWhereSelect()
  {
    contractNumberWhere.value = "";
    contractIdWhere.value = "";
  }

  function processClick()
  {
	  doAjax({
     synchronous:true,
		  params:{'specificationId':specificationId.value, 'shpSumPlusNdsFormatted':shpSumPlusNdsFormatted.value},
     url:'<ctrl:rewrite action="/ShippingAction" dispatch="ajaxCheckBeforeSave"/>',
     update:'resultMsg'
   });

	  if (document.getElementById('resultMsg').innerHTML != '')
	  {
		  alert('<bean:message key="error.shipping.incorrectSumNds"/>');
		  return;
	  }

	  submitDispatchForm("beforeSave");
  }

  function showMsg()
  {
  <logic:equal value="true" name="Shipping" property="showBlockMsg">
    if (isUserAgree('<bean:message key="msg.shipping.block2"/>',true,600,160))
    {
      submitDispatchForm("process");
    }
  </logic:equal>
  }
  initFunctions.push(showMsg);
</script>

<logic:equal name="Shipping" property="printNotice" value="true">
  <script language="JScript" type="text/javascript" >
	  document.getElementById('for-insert').innerHTML='<iframe src="<ctrl:rewrite action="NoticeForShippingPrintAction" scriptUrl="shp_id=${Shipping.shp_id}" />" style="display:none" />';
  </script>
</logic:equal>

