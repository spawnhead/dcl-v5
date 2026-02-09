<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>

<div id='for-insert'></div>

<ctrl:askUser name="ask_block" key="msg.commercial.block" showOkCancel="false" height="120"/>
<ctrl:askUser name="ask_unblock" key="msg.commercial.unblock" showOkCancel="false" height="140"/>
<ctrl:askUser name="checkPriceAsk" key="msg.commercial.checkPrice" showOkCancel="false" height="140"/>

<ctrl:form styleId="striped-form">
	<logic:equal value="true" name="CommercialProposals" property="showCloneMsg">
		<ctrl:hidden property="cpr_id"/>
	</logic:equal>
	<table id=filterTbl style="" align="center" border="0">
		<tr>
			<td colspan="6"></td>
			<td align="right"><bean:message key="CommercialProposals.date"/></td>
			<td colspan="2"></td>
			<td align="right"><bean:message key="CommercialProposals.sum"/></td>
		</tr>
		<tr>
			<td align="right"><bean:message key="CommercialProposals.number"/></td>
			<td><ctrl:text property="number" styleClass="filter"/></td>
			<td width="20">&nbsp;</td>

			<td align="right"><bean:message key="CommercialProposals.department"/></td>
			<td><ctrl:serverList property="department.name" idName="department.id" action="/DepartmentsListAction"
			                     styleClass="filter"/></td>
			<td width="20">&nbsp;</td>

			<td align="right"><bean:message key="CommercialProposals.start"/></td>
			<td><ctrl:date property="date_begin" styleClass="filter" endField="date_end" showHelp="false"/></td>
			<td width="20">&nbsp;</td>

			<td align="right"><bean:message key="CommercialProposals.min"/></td>
			<td width="1%"><ctrl:text property="sum_min_formatted" styleClass="filter" showHelp="false"/></td>
		</tr>
		<tr>
			<td align="right"><bean:message key="CommercialProposals.user"/></td>
			<td><ctrl:serverList property="user.usr_name" idName="user.usr_id" action="/UsersListAction"
			                     styleClass="filter"/></td>
			<td width="20">&nbsp;</td>

			<td align="right"><bean:message key="CommercialProposals.contractor"/></td>
			<td><ctrl:serverList property="contractor.name" idName="contractor.id" action="/ContractorsListAction"
			                     styleClass="filter"/></td>
			<td width="20">&nbsp;</td>

			<td align="right"><bean:message key="CommercialProposals.end"/></td>
			<td><ctrl:date property="date_end" styleClass="filter" startField="date_begin" showHelp="false"/></td>
			<td width="20">&nbsp;</td>

			<td align="right"><bean:message key="CommercialProposals.max"/></td>
			<td width="1%"><ctrl:text property="sum_max_formatted" styleClass="filter" showHelp="false"/></td>
		</tr>
		<tr>
			<td align="right"><bean:message key="CommercialProposals.stuffCategory"/></td>
			<td><ctrl:serverList property="stuffCategory.name" idName="stuffCategory.id" action="/StuffCategoriesListAction"
			                     styleClass="filter"/></td>
			<td width="20">&nbsp;</td>

			<td align="right">
				<ctrl:checkbox property="cpr_proposal_received_flag_in" styleClass="checkbox_filter" onclick="onProposalReceivedFlagClick();" value="1"/>
			</td>
			<td><bean:message key="CommercialProposals.accepted"/></td>
			<td width="20">&nbsp;</td>

			<td align="right">
				<ctrl:checkbox property="cpr_proposal_declined_in" styleClass="checkbox_filter" onclick="onProposalDeclinedClick();" value="1"/>
			</td>
			<td><bean:message key="CommercialProposals.declined"/></td>
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

	<div class="gridBack">
		<grid:table property="grid" key="cpr_id" autoLockName="CommercialProposal">
			<grid:column align="center" width="10%"><jsp:attribute name="title"><bean:message key="CommercialProposals.number"/></jsp:attribute></grid:column>
			<grid:column align="center" width="5%"><jsp:attribute name="title"><bean:message key="CommercialProposals.date"/></jsp:attribute></grid:column>
			<grid:column align="center"><jsp:attribute name="title"><bean:message key="CommercialProposals.contractor"/></jsp:attribute></grid:column>
			<grid:column align="center" width="10%"><jsp:attribute name="title"><bean:message key="CommercialProposals.sum"/></jsp:attribute></grid:column>
			<grid:column align="center" width="5%"><jsp:attribute name="title"><bean:message key="CommercialProposals.currency"/></jsp:attribute></grid:column>
			<grid:column align="center" width="8%"><jsp:attribute name="title"><bean:message key="CommercialProposalProduces.stuffCategory"/></jsp:attribute></grid:column>
			<grid:column align="center" width="9%"><jsp:attribute name="title"><bean:message key="CommercialProposals.reservedState"/></jsp:attribute></grid:column>
			<grid:column width="1%"><jsp:attribute name="title">&nbsp;<ctrl:help htmlName="CommercialProposalsCurrentStatus"/></jsp:attribute></grid:column>
			<grid:column align="center" width="1%"><jsp:attribute name="title"><img title='<bean:message key="tooltip.CommercialProposals.block_status"/>'src='<ctrl:rewrite page="/images/lock.gif"/>'></jsp:attribute></grid:column>
			<grid:column align="center" width="10%"><jsp:attribute name="title"><bean:message key="CommercialProposals.user"/></jsp:attribute></grid:column>
			<grid:column align="center" width="10%"><jsp:attribute name="title"><bean:message key="CommercialProposals.department"/></jsp:attribute></grid:column>
			<grid:column align="center" width="10%"><jsp:attribute name="title"><bean:message key="CommercialProposals.cpr_check_price"/></jsp:attribute></grid:column>
			<grid:column width="1%" title="" hideOnSelectMode="true"/>
			<grid:column width="1%" title="" hideOnSelectMode="true"/>
			<grid:row>
				<grid:colCustom width="10%" property="cpr_number"/>
				<grid:colCustom width="5%" property="cpr_date_date"/>
				<grid:colCustom property="cpr_contractor"/>
				<grid:colCustom align="right" width="10%" property="cprSumFormatted"/>
				<grid:colCustom align="center" width="5%" property="cpr_currency"/>
				<grid:colCustom width="8%" property="cpr_stf_name"/>
				<grid:colCustom width="9%" property="reservedState"/>
				<grid:colImage align="center" width="1%" type="link" enableOnClickAction="false"
				               styleClass="grid-image-without-border" image="images/sqr_${record.attachSqr}.gif"
				               tooltip="tooltip.CommercialProposals.attach_${record.attachSqr}"/>
				<grid:colCheckbox align="center" width="1%" property="cpr_block" askUser="${record.msg_check_block}"
				                  readonlyCheckerId="blockChecker" type="submit" dispatch="block"
				                  scriptUrl="block=${record.cpr_block}"/>
				<grid:colCustom width="10%" property="cpr_user"/>
				<grid:colCustom width="10%" property="cpr_department"/>
				<grid:colCheckbox width="10%" property="cpr_check_price" askUser="checkPriceAsk"
				                  readonlyCheckerId="blockCheckerPrice" type="submit"
				                  dispatch="checkPrice">&nbsp;${record.checkPricesText}</grid:colCheckbox>
				<grid:colImage width="1%" dispatch="clone" type="submit" tooltip="tooltip.CommercialProposals.clone"
				               image="images/copy.gif" styleClass="grid-image-without-border"
				               readonlyCheckerId="editCloneChecker"/>
				<grid:colEdit width="1%" action="/CommercialProposalAction.do?dispatch=edit" type="link"
				              tooltip="tooltip.CommercialProposals.edit" readonlyCheckerId="editCloneChecker"/>
			</grid:row>
		</grid:table>
	</div>

	<ctrl:notShowIfSelectMode>
		<div class=gridBottom>
			<input type=button id="generateButtonExcel" onclick="generateExcelClick();" class="width165"
			       value="<bean:message key="button.formExcel"/>">
			<ctrl:ubutton type="link" action="/CommercialProposalAction.do?dispatch=input" styleClass="width80">
				<bean:message key="button.create"/>
			</ctrl:ubutton>
		</div>
	</ctrl:notShowIfSelectMode>

</ctrl:form>

<script language="JScript" type="text/javascript">
	<logic:equal value="true" name="CommercialProposals" property="showCloneMsg">
	function submitAfterQuestion()
	{
		if (isUserAgree('<bean:message key="msg.commercial.clone"/>', false, 400, 100))
		{
			submitDispatchForm("cloneLikeOldVersion");
		}
		else
		{
			submitDispatchForm("cloneLikeNewVersion");
		}
	}
	initFunctions.push(submitAfterQuestion);
	</logic:equal>

	var cpr_proposal_received_flag_in = document.getElementById('cpr_proposal_received_flag_in');
	var cpr_proposal_declined_in = document.getElementById('cpr_proposal_declined_in');
	function onProposalDeclinedClick()
	{
		cpr_proposal_received_flag_in.checked = false;
	}

	function onProposalReceivedFlagClick()
	{
		cpr_proposal_declined_in.checked = false;
	}

	function generateExcelClick()
	{
		document.getElementById('for-insert').innerHTML = '<iframe src="<ctrl:rewrite action="/CommercialProposalsAction" dispatch="generateExcel"/>" style="display:none" />';
	}
</script>

