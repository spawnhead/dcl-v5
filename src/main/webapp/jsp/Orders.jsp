<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>

<ctrl:askUser name="ask_block" key="msg.order.block" showOkCancel="false" height="120"/>
<ctrl:askUser name="ask_unblock" key="msg.order.unblock" showOkCancel="false" height="140"/>

<ctrl:form styleId="striped-form">

  <ctrl:hidden property="order_by"/>
  <ctrl:hidden property="previousContractorFor.name"/>
  <ctrl:hidden property="previousContract.con_number"/>
  <table id=filterTbl style="" align="center" border="0" width="100%">
    <tr>
      <td colspan="9"></td>
      <td align="right"><bean:message key="Orders.date"/></td>
    </tr>

    <tr>
      <td align="right"><bean:message key="Orders.number"/></td>
      <td><ctrl:text property="number" styleClass="filter"/></td>
      <td width="20">&nbsp;</td>

      <td align="right"><bean:message key="Orders.contractor"/></td>
      <td><ctrl:serverList property="contractor.name" idName="contractor.id" action="/ContractorsListAction" styleClass="filter"/></td>
      <td width="20">&nbsp;</td>

      <td align="right"><bean:message key="Orders.ord_contractor_client"/></td>
      <td><ctrl:serverList property="contractor_for.name" idName="contractor_for.id" action="/ContractorsListAction" styleClass="filter" filter="filter" callback="onChangeContractorFor"/></td>
      <td width="20">&nbsp;</td>

      <td align="right"><bean:message key="Orders.start"/></td>
      <td ><ctrl:date property="date_begin" styleClass="filter" endField="date_end" showHelp="false"/></td>
      <td width="20">&nbsp;</td>
    </tr>

    <tr>
      <td align="right"><bean:message key="Orders.user"/></td>
      <td><ctrl:serverList property="user.userFullName" idName="user.usr_id" action="/UsersListAction" styleClass="filter" /></td>
      <td width="20">&nbsp;</td>

      <td align="right"><bean:message key="Orders.stuffCategory"/></td>
      <td><ctrl:serverList property="stuffCategory.name" idName="stuffCategory.id" action="/StuffCategoriesListAction" styleClass="filter"/></td>
      <td width="20">&nbsp;</td>

      <td align="right"><bean:message key="Orders.contract"/></td>
      <td>
        <logic:notEmpty name="Orders" property="contractor_for.name">
          <ctrl:serverList property="contract.con_number" idName="contract.con_id" filter="filter"
                           action="/ContractsDepFromContractorListAction"
                           scriptUrl="ctr_id=$(contractor_for.id)&allCon=1"
                           style="width:190px;" callback="onChangeContract"/>
        </logic:notEmpty>

        <logic:empty name="Orders" property="contractor_for.name">
          <ctrl:text property="contract.con_number" styleClass="filter"/>
        </logic:empty>
      </td>
      <td width="20">&nbsp;</td>

      <td align="right"><bean:message key="Orders.end"/></td>
      <td ><ctrl:date property="date_end" styleClass="filter" startField="date_begin" showHelp="false"/></td>
    </tr>
    <tr>
      <td align="right"><bean:message key="Orders.department"/></td>
      <td><ctrl:serverList property="department.name" idName="department.id" action="/DepartmentsListAction" styleClass="filter" filter="filter"/></td>
      <td width="20">&nbsp;</td>

	    <td align="right"><bean:message key="Orders.sellerForWho"/></td>
      <td><ctrl:serverList property="sellerForWho.name" idName="sellerForWho.id" action="/SellersListAction" scriptUrl="forOrder=true" styleClass="filter"/></td>
      <td width="20">&nbsp;</td>

      <td align="right"><bean:message key="Orders.specification"/></td>
      <td>
        <logic:notEmpty name="Orders" property="contract.con_number">
	        <ctrl:serverList property="specification.spc_number" idName="specification.spc_id" action="/SpecificationsDepFromContractListAction"
	                         scriptUrl="ctr_id=$(contractor_for.id)&id=$(contract.con_id)&withExecuted=true" styleClass="filter" filter="filter" style="width:190px;"/>
        </logic:notEmpty>

        <logic:empty name="Orders" property="contract.con_number">
          <ctrl:text property="specification.spc_number" styleClass="filter"/>
        </logic:empty>
      </td>

      <td colspan="1"></td>
      <td align="right"><bean:message key="Orders.sum"/></td>
    </tr>
    <tr>
      <td align="right"><ctrl:checkbox property="executed" styleClass="checkbox_filter" value="1" onclick="executedOnClick()"/></td>
      <td><bean:message key="Orders.executed"/></td>
      <td width="20">&nbsp;</td>

	    <td align="right"></td>
      <td></td>
      <td width="20">&nbsp;</td>

      <td align="right"><ctrl:checkbox property="ord_ready_for_deliv" styleClass="checkbox_filter" value="1"/></td>
      <td><bean:message key="Orders.ord_ready_for_deliv"/></td>
      <td width="20">&nbsp;</td>

      <td align="right"><bean:message key="Orders.min"/></td>
      <td width="1%"><ctrl:text property="sum_min_formatted" styleClass="filter" showHelp="false"/></td>
    </tr>

    <tr>
			<td align="right"><ctrl:checkbox property="not_executed" styleClass="checkbox_filter" value="1" onclick="notExecutedOnClick()"/></td>
			<td><bean:message key="Orders.not_executed"/></td>
      <td width="20">&nbsp;</td>

      <td align="right"></td>
      <td></td>
      <td width="20">&nbsp;</td>

      <td align="right"><ctrl:checkbox property="ord_annul_not_show" styleClass="checkbox_filter" value="1"/></td>
      <td><bean:message key="Orders.ord_annul_not_show"/></td>
      <td width="20">&nbsp;</td>

      <td align="right"><bean:message key="Orders.max"/></td>
      <td width="1%"><ctrl:text property="sum_max_formatted" styleClass="filter" showHelp="false"/></td>
    </tr>

    <tr>
      <td colspan="20">
        <bean:message key="Orders.ord_current_state"/>
      </td>
    </tr>
    <tr>
      <td colspan="20">
        <bean:message key="Orders.ord_current_state_a"/>&nbsp;<ctrl:checkbox property="state_a" styleClass="checkbox_filter" value="1" onclick="stateA3OnClick()"/>&nbsp;
        <bean:message key="Orders.ord_current_state_3"/>&nbsp;<ctrl:checkbox property="state_3" styleClass="checkbox_filter" value="1" onclick="stateA3OnClick()"/>
      </td>
    </tr>
    <tr>
      <td colspan="20">
        <bean:message key="Orders.ord_current_state_b"/>&nbsp;<ctrl:checkbox property="state_b" styleClass="checkbox_filter" value="1" onclick="stateBOnClick()"/>&nbsp;
        <bean:message key="Orders.ord_num_conf"/>&nbsp;<ctrl:text property="ord_num_conf" styleClass="filter"/>&nbsp;
        <bean:message key="Orders.ord_current_state_exclamation"/>&nbsp;<ctrl:checkbox property="state_exclamation" styleClass="checkbox_filter" value="1"/>&nbsp;
        <bean:message key="Orders.ord_current_state_c"/>&nbsp;<ctrl:checkbox property="state_c" styleClass="checkbox_filter" value="1" onclick="stateCOnClick()"/>&nbsp;&nbsp;&nbsp;
        <ctrl:checkbox property="ord_show_movement" styleClass="checkbox_filter" value="1"/>&nbsp;<bean:message key="Orders.ord_show_movement"/>&nbsp;
      </td>
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
  <div class="gridBack" >
    <grid:table property="grid" key="ord_id" autoLockName="Order" >
      <grid:column align="center" width="10%"><jsp:attribute name="title"><bean:message key="Orders.number"/></jsp:attribute></grid:column>
      <grid:column align="center" width="5%"><jsp:attribute name="title"><bean:message key="Orders.date"/></jsp:attribute></grid:column>
      <grid:column align="center"><jsp:attribute name="title"><bean:message key="Orders.contractor"/></jsp:attribute></grid:column>
      <grid:column align="center" width="5%"><jsp:attribute name="title"><bean:message key="Orders.sum"/></jsp:attribute></grid:column>
      <grid:column align="center" width="10%"><jsp:attribute name="title"><bean:message key="Orders.ord_contractor_for"/></jsp:attribute></grid:column>
      <grid:column align="center" width="35%"><jsp:attribute name="title"><bean:message key="Orders.ord_current_state1"/></jsp:attribute></grid:column>
      <grid:column width="1%" title=""/>
      <grid:column width="1%" title=""/>
      <grid:column align="center" width="10%"><jsp:attribute name="title"><bean:message key="Orders.user"/></jsp:attribute></grid:column>
      <grid:column align="center" width="10%"><jsp:attribute name="title"><bean:message key="Orders.department"/></jsp:attribute></grid:column>
      <grid:column align="center" width="1%"><jsp:attribute name="title"><img title='<bean:message key="tooltip.Orders.block"/>' src='<ctrl:rewrite page="/images/lock.gif"/>'></jsp:attribute></grid:column>
      <grid:column width="1%" title=""/>
      <grid:column width="1%" title=""/>
      <grid:row>
        <grid:colCustom width="10%" property="ord_number" styleClassCheckerId="style-checker"/>
        <grid:colCustom width="5%" property="ordDateFormatted" styleClassCheckerId="style-checker"/>
        <grid:colCustom property="ord_contractor" styleClassCheckerId="style-checker"/>
        <grid:colCustom align="right" width="5%" property="ordSumFormatted" styleClassCheckerId="style-checker"/>
        <grid:colCustom width="10%" property="ord_contractor_for" styleClassCheckerId="style-checker"/>
        <grid:colCustom width="35%" image="images/linkToSpec${record.ord_link_to_spec}.gif" tooltipProperty="linkToSpecText" property="ordCurrentStateFormatted" styleClassCheckerId="style-checker"/>
        <grid:colCustom width="1%" property="threeDayMsg" styleClassCheckerId="style-checker"/>
        <grid:colCustom width="1%" image="images/attention.gif" tooltip="tooltip.Orders.warn" showCheckerId="showWarn"/>
        <grid:colCustom width="10%" property="ord_user" styleClassCheckerId="style-checker"/>
        <grid:colCustom width="10%" property="ord_department" styleClassCheckerId="style-checker"/>
        <grid:colCheckbox align="center" width="1%" property="ord_block" askUser="${record.msg_check_block}"
                          readonlyCheckerId="blockChecker" type="submit" dispatch="block"
                          scriptUrl="block=${record.ord_block}"/>
        <grid:colImage width="1%" action="/OrderAction.do?dispatch=clone" type="link" tooltip="tooltip.Orders.clone"
                       image="images/copy.gif" styleClass="grid-image-without-border"
                       readonlyCheckerId="editCloneChecker"/>
        <grid:colEdit width="1%" action="/OrderAction.do?dispatch=edit" type="link" tooltip="tooltip.Orders.edit"
                      readonlyCheckerId="editCloneChecker"/>
      </grid:row>
    </grid:table>
  </div>

  <ctrl:notShowIfSelectMode>
    <div class=gridBottom>
      <ctrl:ubutton type="link" action="/OrderAction.do?dispatch=input" styleClass="width80">
        <bean:message key="button.create"/>
      </ctrl:ubutton>
    </div>
  </ctrl:notShowIfSelectMode>
</ctrl:form>

<script>
  var executed = document.getElementById('executed');
  var notExecuted = document.getElementById('not_executed');
  var ordReadyForDelivery = document.getElementById('ord_ready_for_deliv');

  var state_a = document.getElementById('state_a');
  var state_3 = document.getElementById('state_3');
  var state_b = document.getElementById('state_b');
  var stateExclamation = document.getElementById('state_exclamation');
  var state_c = document.getElementById('state_c');
  var contract = document.getElementById('contract.con_number');
  var previousContract = document.getElementById('previousContract.con_number');
  var contractorFor = document.getElementById('contractor_for.name');
  var previousContractorFor = document.getElementById('previousContractorFor.name');
  var specification = document.getElementById('specification.spc_number');

  setDisableState();
  function setDisableState()
  {
    var disabled_state = executed.checked;
    state_a.disabled = disabled_state;
    state_3.disabled = disabled_state;
    state_b.disabled = disabled_state;
	  stateExclamation.disabled = disabled_state;
    state_c.disabled = disabled_state;
    if ( disabled_state )
    {
      state_a.checked = false;
      state_3.checked = false;
      state_b.checked = false;
	    stateExclamation.checked = false;
      state_c.checked = false;
    }
  }

  function stateA3OnClick()
  {
    if ( state_a.checked || state_3.checked )
    {
      state_b.checked = false;
      state_b.disabled = true;
      state_c.checked = false;
      state_c.disabled = true;
    }
    else
    {
      state_b.disabled = false;
      state_c.disabled = false;
    }
  }

  function stateBOnClick()
  {
    if ( state_b.checked )
    {
      state_a.checked = false;
      state_a.disabled = true;
      state_3.checked = false;
      state_3.disabled = true;
      state_c.checked = false;
      state_c.disabled = true;
    }
    else
    {
      state_a.disabled = false;
      state_3.disabled = false;
      state_c.disabled = false;
    }
  }

  function stateCOnClick()
  {
    if ( state_c.checked )
    {
      state_a.checked = false;
      state_a.disabled = true;
      state_3.checked = false;
      state_3.disabled = true;
      state_b.checked = false;
      state_b.disabled = true;
    }
    else
    {
      state_a.disabled = false;
      state_3.disabled = false;
      state_b.disabled = false;
    }
  }

  function onChangeContractorFor() {
    if (previousContractorFor !== null && contractorFor !== null) {
      if (previousContractorFor.value === contractorFor.value) {
        return;
      }
    }
    contract.value = "";
    specification.value = "";
    previousContractorFor.value = contractorFor.value;
    submitDispatchForm("reload");
  }


  function onChangeContract(){
    if (previousContract !== null && contract !== null) {
      if(contract.value === "") specification.value = "";
      if (previousContract.value === contract.value) {
        return;
      }
    }
    previousContract.value = contract.value;
    submitDispatchForm("reload");
  }

  function executedOnClick()
  {
	  notExecuted.checked = false;
	  ordReadyForDelivery.checked = false;
    setDisableState();
  }
  function notExecutedOnClick()
  {
    executed.checked = false;
    setDisableState();
  }
</script>
