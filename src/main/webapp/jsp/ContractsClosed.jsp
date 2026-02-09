<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>

<ctrl:askUser name="ask_unblock" key="msg.contracts_closed.unblock" showOkCancel="false" height="120"/>
<ctrl:askUser name="ask_delete" key="msg.contracts_closed.delete" showOkCancel="false" height="120"/>

<ctrl:form styleId="striped-form">
  <table id=filterTbl style="" align="center" border="0" width="100%">
    <tr>
      <td colspan="3"></td>
      <td align="right"><bean:message key="ContractsClosed.date"/></td>
    </tr>
    <tr>
      <td align="right"><bean:message key="ContractsClosed.contractor"/></td>
      <td><ctrl:serverList property="contractor.name" idName="contractor.id"
                           action="/ContractorsForContractsClosedListAction" callback="submitReloadForm"
                           styleClass="filter" filter="filter"/></td>
      <td width="20">&nbsp;</td>

      <td align="right"><bean:message key="ContractsClosed.start"/></td>
      <td ><ctrl:date property="date_begin" styleClass="filter" endField="date_end" showHelp="false"/></td>
      <td width="20">&nbsp;</td>
    </tr>
    <tr>
      <td align="right"><bean:message key="ContractsClosed.con_number"/></td>
      <td><ctrl:serverList property="contract.con_number" idName="contract.con_id"
                           action="/ContractsDepFromContractorListAction"
                           callback="submitReloadFormContract"
                           scriptUrl="ctr_id=$(contractor.id)&is_for_closed=true"
                           styleClass="filter"/></td>
      <td width="20">&nbsp;</td>


      <td align="right"><bean:message key="ContractsClosed.end"/></td>
      <td ><ctrl:date property="date_end" styleClass="filter" startField="date_begin" showHelp="false"/></td>
      <td width="20">&nbsp;</td>
    </tr>

    <tr>
      <td align="right"><bean:message key="ContractsClosed.not_block"/></td>
      <td><ctrl:checkbox property="not_block" styleClass="checkbox_filter" value="1"/></td>
      <td width="20">&nbsp;</td>
    </tr>

    <tr>
   </tr>

    <tr>
      <td align="right" colspan=20>
        <ctrl:ubutton type="submit" dispatch="input" styleClass="width120"><bean:message key="button.clearFilter"/></ctrl:ubutton>&nbsp;
        <ctrl:ubutton type="submit" dispatch="filter" styleClass="width120"><bean:message key="button.applyFilter"/></ctrl:ubutton>
      </td>
    </tr>
  </table>

  <div class="gridBack" id="contractsGrid">
    <grid:table property="grid" key="ctc_id" scrollableGrid="true" height="expression(document.body.clientHeight-385)" autoLockName="ContractClosed" >
      <logic:equal name="ContractsClosed" property="admin" value="1">
        <grid:column width="1"><jsp:attribute name="title"><input type="checkbox" class="grid-header-checkbox" onclick="changeGridSelection(this)" ></jsp:attribute></grid:column>
      </logic:equal>
      <grid:column align="center" width="10%"><jsp:attribute name="title"><bean:message key="ContractsClosed.number"/></jsp:attribute></grid:column>
      <grid:column align="center" width="10%"><jsp:attribute name="title"><bean:message key="ContractsClosed.date"/></jsp:attribute></grid:column>
      <grid:column align="center"><jsp:attribute name="title"><bean:message key="ContractsClosed.contractor"/></jsp:attribute></grid:column>
      <grid:column align="center" width="20%"><jsp:attribute name="title"><bean:message key="ContractsClosed.con_contract"/></jsp:attribute></grid:column>
      <grid:column align="center" width="1%"><jsp:attribute name="title">&nbsp;<ctrl:help htmlName="ContractsClosedWarnsHeader"/></jsp:attribute></grid:column>
      <grid:column align="center" width="1%"><jsp:attribute name="title"><img title='<bean:message key="tooltip.ContractsClosed.block_status"/>' src='<ctrl:rewrite page="/images/lock.gif"/>'></jsp:attribute></grid:column>
      <grid:column title=""/>
      <logic:equal name="ContractsClosed" property="admin" value="1">
        <grid:column title=""/>
      </logic:equal>
      <grid:row>
        <logic:equal name="ContractsClosed" property="admin" value="1">
          <grid:colCheckbox  width="1%" property="selectedContractId" result="grid" resultProperty="selectedContractId"
                             useIndexAsPK="true" styleClass="first-column" showCheckerId="show-delete-checker"/>
        </logic:equal>
        <grid:colCustom width="10%" property="ctc_number"/>
        <grid:colCustom width="10%" property="ctc_date_date"/>
        <grid:colCustom property="ctc_contractor"/>
        <grid:colCustom width="20%" property="con_number"/>
        <grid:colCustom width="1%" showCheckerId="showWarnChecker"><img title='' src='<ctrl:rewrite page="/images/attention.gif"/>'></grid:colCustom>
        <grid:colCheckbox align="center" width="30" property="ctc_block" askUser="ask_unblock" readonlyCheckerId="blockChecker" type="submit" dispatch="unblock"/>
        <grid:colEdit width="1" type="submit" dispatch="edit" tooltip="tooltip.ContractsClosed.edit"/>
        <logic:equal name="ContractsClosed" property="admin" value="1">
          <grid:colDelete width="1" type="submit" dispatch="delete" askUser="ask_delete" tooltip="tooltip.ContractsClosed.delete" showCheckerId="show-delete-checker"/>
        </logic:equal>
      </grid:row>
    </grid:table>
  </div>

  <div class=gridBottom>
    <table width="100%">
      <tr>
        <td align="left">
          <table width="50%">
            <tr>
              <td align="left">
                <logic:equal name="ContractsClosed" property="admin" value="1">
                  <input type='button' onclick='blockSelectedClick()' class='width165' value='<bean:message key="button.blockSelected"/>'/>
                </logic:equal>
              </td>
            </tr>
          </table>
        </td>
        <td align="right" colspan="1">
          <ctrl:ubutton type="submit" dispatch="create" styleClass="width80">
            <bean:message key="button.create"/>
          </ctrl:ubutton>
        </td>
      </tr>
    </table>
  </div>
</ctrl:form>

<script language="JScript" type="text/javascript">

  var contractId = document.getElementById("contract.con_id");
  var contractNumber = document.getElementById("contract.con_number");

  function submitReloadForm()
  {
    contractId.value = '';
    contractNumber.value = '';
    submitDispatchForm("reload");
  }

  function submitReloadFormContract()
  {
    submitDispatchForm("reloadContract");
  }

<logic:equal name="ContractsClosed" property="admin" value="1">
  function getCheckboxes1stColumnEven()
  {
    return $$('#contractsGrid td.first-column input.grid-checkbox-even');
  }
  function getCheckboxes1stColumnNotEven()
  {
    return $$('#contractsGrid td.first-column input.grid-checkbox-not-even');
  }

  function changeGridSelection(origObj)
  {
    var chs = getCheckboxes1stColumnEven();
    var i = 0;
    for (; i < chs.length; i++)
    {
      if ( !$(chs[i]).disabled )
      {
       $(chs[i]).checked = origObj.checked;
      }
    }

    chs = getCheckboxes1stColumnNotEven();
    i = 0;
    for (; i < chs.length; i++)
    {
      if ( !$(chs[i]).disabled )
      {
       $(chs[i]).checked = origObj.checked;
      }
    }
  }

  function haveSelection()
  {
    var chs = getCheckboxes1stColumnEven();
    var i = 0;
    for (; i < chs.length; i++)
    {
      if ( $(chs[i]).checked )
      {
        return true;
      }
    }

    chs = getCheckboxes1stColumnNotEven();
    i = 0;
    for (; i < chs.length; i++)
    {
      if ( $(chs[i]).checked )
      {
        return true;
      }
    }

    return false;
  }

  function blockSelectedClick()
  {
    if ( !haveSelection() )
    {
      alert('<bean:message key="msg.contracts_closed.noSelected"/>');
    }
    else
    {
      submitDispatchForm("blockSelected");
    }
  }
</logic:equal>
</script>
