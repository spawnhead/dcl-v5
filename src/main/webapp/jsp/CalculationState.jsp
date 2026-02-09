<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>

<ctrl:form>

  <logic:equal name="CalculationState" property="isDebit" value="1">
    <ctrl:hidden property="not_include_if_earliest"/>
    <ctrl:hidden property="not_include_zero"/>
    <ctrl:hidden property="include_all_specs"/>
    <ctrl:hidden property="not_show_annul"/>
  </logic:equal>

	<table id=filterTbl align="center" border="0" width="100%">
    <tr>
      <td>
        <table>
          <tr>
            <td>
              <bean:message key="CalculationState.reportType"/>&nbsp;
            </td>
            <td>
              <ctrl:serverList property="reportType.name" idName="reportType.id"
                               action="/CalcStateReportTypesListAction" style="width:207px;"
                               callback="onChangeReportType" selectOnly="true"/>
            </td>
          </tr>
        </table>
      </td>
    </tr>
    <tr>
      <td colspan="20">
        <table width="100%">
          <tr>
            <td width="420px" valign="top">
              <table width="100%">
                <tr>
                  <td colspan="2" align="center" class="table-header">
                    <bean:message key="CalculationState.mainSettings"/>
                  </td>
                </tr>
                <tr>
                  <td>
                    <bean:message key="CalculationState.contractor"/>
                  </td>
                  <td>
                    <ctrl:serverList property="contractorCalcState.name" idName="contractorCalcState.id" action="/ContractorsListAction"
                                     style="width:200px;" filter="filter" callback="onChangeContractor"
                                     scriptUrl="have_all=true"/>
                  </td>
                </tr>

                <tr>
                  <td colspan="20">
                    <div id='showCtrButton'>
                      <span id="reputation"></span>
                      <logic:notEqual name="CalculationState" property="isDebit" value="1">
                        <logic:equal name="CalculationState" property="readonlyEditCtrButton" value="false">
                          <ctrl:ubutton type="submit" onclick="form.target=\"\";" dispatch="editContractor" scriptUrl="ctr_id=$(contractorCalcState.id)" styleClass="width120" readonly="false">
                            <bean:message key="button.edit"/>
                          </ctrl:ubutton>
                        </logic:equal>
                      </logic:notEqual>
                    </div>
                  </td>
                </tr>
                
                <tr>
                  <td>
                    <bean:message key="CalculationState.seller"/>
                  </td>
                  <td>
                    <ctrl:serverList property="sellerCalcState.name" idName="sellerCalcState.id" action="/SellersListAction"
                                     selectOnly="true" style="width:200px;" callback="onChangeSeller"/>
                  </td>
                </tr>

                <logic:notEqual name="CalculationState" property="isDebit" value="1">
                  <tr>
                    <td>
                      <bean:message key="CalculationState.contract"/>&nbsp;
                    </td>
                    <td>
                      <ctrl:serverList property="contractNumberWithDate" idName="contract.con_id" filter="filter"
                                       action="/ContractsDepFromContractorListAction" scriptUrl="ctr_id=$(contractorCalcState.id)&have_all=true&allCon=1"
                                       style="width:200px;"/>
                    </td>
                  </tr>

                  <tr>
                    <td colspan="20">
                      <ctrl:checkbox property="not_include_zero" styleClass="checkbox_filter" value="1" showHelp="false"/>&nbsp;
                      <bean:message key="CalculationState.not_include_zero"/>
                    </td>
                  </tr>
                </logic:notEqual>


                <tr>
                  <td>
                    <bean:message key="CalculationState.currency"/>
                  </td>
                  <td>
                    <table align="left" cellpadding="0" cellspacing="0" border="0">
                      <tr>
                        <td width="14px">
                        </td>
                        <td>
                          <ctrl:serverList property="currencyCalcState.name" idName="currencyCalcState.id"
                                           action="/CurrenciesListAction" style="width:200px;" selectOnly="true" showHelp="false"/>
                        </td>
                      </tr>
                    </table>
                  </td>
                </tr>
              </table>
            </td>
            
            <td width="350px" valign="top">
              <table width="100%">
                <tr>
                  <td colspan="2" align="center" class="table-header">
                    <bean:message key="CalculationState.specification"/>
                  </td>
                </tr>

                <logic:notEqual name="CalculationState" property="isDebit" value="1">
                  <tr>
                    <td colspan="20">
                      <table border="0" cellpadding="0" cellspacing="0">
                        <tr>
                          <td width="35px">
                            <ctrl:checkbox property="not_include_if_earliest" styleClass="checkbox_filter" value="1"
                                           onclick="notIncludeIfEarliestOnClick()"/>
                          </td>
                          <td>
                            <bean:message key="CalculationState.not_include_if_earliest"/>
                          </td>
                        </tr>
                      </table>
                    </td>
                  </tr>
                  <tr>
                    <td colspan="20">
                      <table border="0" cellpadding="0" cellspacing="0">
                        <tr>
                          <td width="35px"></td>
                          <td>
                            <ctrl:date property="earliest_doc_date" style="width:200px;" showHelp="false"/>
                          </td>
                        </tr>
                      </table>
                    </td>
                  </tr>

                  <tr>
                    <td colspan="20">
                      <table border="0" cellpadding="0" cellspacing="0">
                        <tr>
                          <td width="35px">
                            <ctrl:checkbox property="include_all_specs" styleClass="checkbox_filter" value="1"/>
                          </td>
                          <td>
                            <bean:message key="CalculationState.include_all_specs"/>
                          </td>
                        </tr>
                      </table>
                    </td>
                  </tr>

                  <tr>
                    <td colspan="20">
                      <table border="0" cellpadding="0" cellspacing="0">
                        <tr>
                          <td width="35px">
                            <ctrl:checkbox property="not_show_annul" styleClass="checkbox_filter" value="1"/>
                          </td>
                          <td>
                            <bean:message key="CalculationState.not_show_annul"/>
                          </td>
                        </tr>
                      </table>
                    </td>
                  </tr>

                  <tr>
                    <td colspan="20">
                      <table border="0" cellpadding="0" cellspacing="0">
                        <tr>
                          <td width="35px">
                            <ctrl:checkbox property="notShowExpiredContractZeroBalance" styleClass="checkbox_filter" value="1"/>
                          </td>
                          <td>
                            <bean:message key="CalculationState.notShowExpiredContractZeroBalance"/>
                          </td>
                        </tr>
                      </table>
                    </td>
                  </tr>
                </logic:notEqual>

                <tr>
                  <td colspan="20" style="text-decoration:underline;">
                    <bean:message key="CalculationState.belonging"/>
                  </td>
                </tr>

                <tr>
                  <td>
                    <bean:message key="CalculationState.user"/>
                  </td>
                  <td>
                    <ctrl:serverList property="userCalcState.userFullName" idName="userCalcState.usr_id" action="/UsersListAction"
                                     style="width:200px;" scriptUrl="have_all=true" filter="filter" callback="onChangeUser"/>
                  </td>
                </tr>
                
                <tr>
                  <td>
                    <bean:message key="CalculationState.department"/>&nbsp;
                  </td>
                  <td>
                    <ctrl:serverList property="departmentCalcState.name" idName="departmentCalcState.id"
                                     action="/DepartmentsListAction" selectOnly="true" style="width:200px;"
                                     scriptUrl="have_all=true" callback="onChangeDepartment"/>
                  </td>
                </tr>
              </table>
            </td>

            <td width="290px" valign="top">
              <table width="100%">
                <tr>
                  <td colspan="2" align="center" class="table-header">
                    <bean:message key="CalculationState.view_columns"/>
                  </td>
                </tr>
                <tr>
                  <td width="35px">
                    <ctrl:checkbox property="view_pay_cond" styleClass="checkbox_filter" value="1"/>
                  </td>
                  <td>
                    <bean:message key="CalculationState.view_pay_cond"/>
                  </td>
                </tr>
                <logic:notEqual name="CalculationState" property="isDebit" value="1">
                  <tr>
                    <td width="35px">
                      <ctrl:checkbox property="view_delivery_cond" styleClass="checkbox_filter" value="1"/>
                    </td>
                    <td nowrap="nowrap">
                      <bean:message key="CalculationState.view_delivery_cond"/>
                    </td>
                  </tr>
                </logic:notEqual>
                <tr>
                  <td width="35px">
                    <ctrl:checkbox property="view_expiration" styleClass="checkbox_filter" value="1"/>
                  </td>
                  <td>
                    <bean:message key="CalculationState.view_expiration"/>
                  </td>
                </tr>
                <tr>
                  <td width="35px">
                    <ctrl:checkbox property="view_complaint" styleClass="checkbox_filter" value="1"/>
                  </td>
                  <td>
                    <bean:message key="CalculationState.view_complaint"/>
                  </td>
                </tr>
                <tr>
                  <td width="35px">
                    <ctrl:checkbox property="view_comment" styleClass="checkbox_filter" value="1"/>
                  </td>
                  <td>
                    <bean:message key="CalculationState.view_comment"/>
                  </td>
                </tr>
                <tr>
                  <td width="35px">
                    <ctrl:checkbox property="view_manager" styleClass="checkbox_filter" value="1"/>
                  </td>
                  <td>
                    <bean:message key="CalculationState.view_manager"/>
                  </td>
                </tr>
                <tr>
                  <td width="35px">
                    <ctrl:checkbox property="view_stuff_category" styleClass="checkbox_filter" value="1"/>
                  </td>
                  <td>
                    <bean:message key="CalculationState.view_stuff_category"/>
                  </td>
                </tr>
              </table>
            </td>
          </tr>
        </table>
      </td>
    </tr>

    <tr>
      <td colspan=20>
        <table border="0" width="100%">
          <tr>
            <td width="70%" align="right" colspan=2>
              <ctrl:ubutton type="submit" onclick=" form.target=\"\";" dispatch="cleanAll" styleClass="width120"><bean:message key="button.clearAll"/></ctrl:ubutton>&nbsp;
              <ctrl:ubutton styleId="generateButton"   onclick=" form.target=\"\";" type="submit" dispatch="generate" styleClass="width120"><bean:message key="button.form"/></ctrl:ubutton>
              <ctrl:ubutton styleId="generateButtonGrid"  onclick=" form.target=\"_blank\";unlockForm();" type="submit" dispatch="generateGrid" styleClass="width200" showWait="false"><bean:message key="button.formGrid"/></ctrl:ubutton>
              <input type=button id="generateButtonExcel"  onclick="generateExcelClick();"  class="width165" value="<bean:message key="button.formExcel"/>">
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
	                                        
  <div class="gridBack" id="scrollableDiv" style="overflow:scroll;width:expression(document.body.clientWidth-15); height:expression(document.body.clientHeight-460)">
		<%@include file="CalculationState-grid.inc"%>
	</div>
</ctrl:form>

<script language="JScript" type="text/javascript">
	var form = document.forms[0];

  function ensureDispatchOnSubmit()
  {
    if (!form || !form.action)
    {
      return true;
    }
    if (form.action.indexOf('dispatch=') === -1)
    {
      var joiner = form.action.indexOf('?') === -1 ? '?' : '&';
      form.action = form.action + joiner + 'dispatch=input';
    }
    return true;
  }

  if (form)
  {
    form.onsubmit = ensureDispatchOnSubmit;
  }

  function __getEl(name)
  {
    return document.getElementById(name) || (document.getElementsByName(name)[0] || null);
  }

  var contractor = __getEl("contractorCalcState.name");
  var contractorId = __getEl("contractorCalcState.id");
  var reportTypeId = __getEl("reportType.id");

  var userId = __getEl('userCalcState.usr_id');
  var userName = __getEl('userCalcState.userFullName');
  var departmentId = __getEl('departmentCalcState.id');
  var departmentName = __getEl('departmentCalcState.name');

  <logic:notEqual name="CalculationState" property="isDebit" value="1">
    var showCtrButton = document.getElementById('showCtrButton');
  </logic:notEqual>

  var sellerId = __getEl('sellerCalcState.id');

  <logic:notEqual name="CalculationState" property="isDebit" value="1">
    var view_pay_cond = document.getElementById('view_pay_cond');
    var view_delivery_cond = document.getElementById('view_delivery_cond');
    var view_expiration = document.getElementById('view_expiration');
    var view_complaint = document.getElementById('view_complaint');
    var view_comment = document.getElementById('view_comment');
    var view_manager = document.getElementById('view_manager');
    var view_stuff_category = document.getElementById('view_stuff_category');

    var not_include_if_earliest = __getEl('not_include_if_earliest');
    var earliest_doc_date = __getEl("earliest_doc_date");
    var earliest_doc_dateBtn = __getEl("earliest_doc_dateBtn");

    var contractNumber = __getEl("contractNumberWithDate");
    var contractBtn = __getEl("contractNumberWithDateBtn");
    var contractId = __getEl("contract.con_id");
  </logic:notEqual>

  var generateButton = document.getElementById('generateButton');
  var generateButtonExcel = document.getElementById('generateButtonExcel');
  var generateButtonGrid = document.getElementById('generateButtonGrid');

  function contractorAjax(id)
  {
    doAjax({
      url:'<ctrl:rewrite action="/CalculationStateAction" dispatch="ajaxGetReputation"/>',
      params:{'contractor-id':id},
      update:'reputation'
    });
  }

  <logic:notEqual name="CalculationState" property="isDebit" value="1">
    setCorrectEarliestDate();
    function setCorrectEarliestDate()
    {
    if (!not_include_if_earliest || !earliest_doc_date || !earliest_doc_dateBtn)
    {
      return;
    }
    if ( not_include_if_earliest.checked )
      {
        earliest_doc_date.disabled = false;
        disableImgButton(earliest_doc_dateBtn, false);
      }
      else
      {
        earliest_doc_date.value = '';
        earliest_doc_date.disabled = true;
        disableImgButton(earliest_doc_dateBtn, true);
      }
    }

    function notIncludeIfEarliestOnClick()
    {
      setCorrectEarliestDate();
    }
  </logic:notEqual>

  function setCheckBoxDependContractor()
  {
    if (!contractorId || !reportTypeId)
    {
      return;
    }
    if ( contractorId.value == '-1' || reportTypeId.value == '2' )
    {
      //empty
    }
    else
    {
    <logic:notEqual name="CalculationState" property="isDebit" value="1">
      view_pay_cond.disabled = false;
      view_delivery_cond.disabled = false;
      view_expiration.disabled = false;
      view_complaint.disabled = false;
      view_comment.disabled = false;
      view_manager.disabled = false;
      view_stuff_category.disabled = false;
    </logic:notEqual>
    }
  }

  function checkContractor()
  {
    if ( contractor && contractor.value != '' )
    {
      generateButton.disabled = false;
      generateButtonExcel.disabled = false;
      generateButtonGrid.disabled = false;
    }
    else
    {
      generateButton.disabled = true;
      generateButtonExcel.disabled = true;
      generateButtonGrid.disabled = true;
    }
  }

  function setContractor()
  {
    checkContractor();
    onChangeSeller();

    <logic:notEqual name="CalculationState" property="isDebit" value="1">
      if (!contractorId || !reportTypeId)
      {
        return;
      }
      if ( contractorId.value == '-1' || contractorId.value == '' || reportTypeId.value == '2' )
      {
        showCtrButton.style.display='none';
      }
      else
      {
        showCtrButton.style.display='';
      }
    </logic:notEqual>

    setCheckBoxDependContractor();
  }

  function onChangeContractor()
  {
    setContractor();
    if (contractorId)
    {
      contractorAjax(contractorId.value);
    }
    <logic:notEqual name="CalculationState" property="isDebit" value="1">
      if (contractNumber) contractNumber.value = "";
      if (contractId) contractId.value = "";
    </logic:notEqual>
  }

  function setContactorPageLoad()
  {
    setContractor();
    contractorAjax(<ctrl:write name="CalculationState" property="contractorCalcState.id"/>);
  }

  function onChangeReportType()
  {
    form.target="";
    submitDispatchForm("cleanAll");
  }

  function onChangeSeller()
  {
    if (!sellerId || !contractorId)
    {
      return;
    }
    if (
         ( sellerId.value == '' ) ||
         ( ${CalculationState.userInLithuania} && sellerId.value != '0' )
       )
    {
      generateButton.disabled = true;
      generateButtonExcel.disabled = true;
      generateButtonGrid.disabled = true;
    }
    else
    {
      checkContractor();
    }

    <logic:notEqual name="CalculationState" property="isDebit" value="1">
      if (!contractNumber || !contractBtn)
      {
        return;
      }
      if ( sellerId.value == '' || contractorId.value == '-1' )
      {
        contractNumber.disabled = true;
        disableImgButton(contractBtn, true);
      }
      else
      {
        contractNumber.disabled = false;
        disableImgButton(contractBtn, false);
      }
    </logic:notEqual>
  }

  function onChangeUser()
  {
    if ( userId.value != '' )
    {
      departmentId.value = '';
      departmentName.value = '';
    }
  }

  function onChangeDepartment()
  {
    if ( departmentId.value != '' )
    {
      userId.value = '';
      userName.value = '';
    }
  }

  function generateExcelClick()
  {
    document.getElementById('for-insert').innerHTML='<iframe src="<ctrl:rewrite action="/CalculationStateAction" dispatch="generateExcel"/>" style="display:none" />';
  }

  var scrollableDiv = document.getElementById("scrollableDiv");

  function rememberScroll()
  {
    if ( scrollableDiv )
    {
      var scroll = scrollableDiv.scrollTop;
      setCookie("rememberScrollY", scroll);
    }
  }

  function goToScroll()
  {
    var scroll = getCookie("rememberScrollY");
    if ( scroll != '' )
    {
      scrollableDiv.scrollTop = parseInt(scroll);
      setCookie("rememberScrollY", "");
    }
  }
  
  initFunctions.push(setContactorPageLoad);
  initFunctions.push(goToScroll);

</script>

<div id='for-insert'></div>
