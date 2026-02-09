<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>

<ctrl:form styleId="striped-form">
  <table id=filterTbl align="center" border="0" width="100%">
    <tr>
      <td colspan="6"></td>
      <td align="right"><bean:message key="Contracts.date"/></td>
      <td colspan="2"></td>
      <td align="right"><bean:message key="Contracts.sum"/></td>
    </tr>
    <tr>
      <td align="right"><bean:message key="Contracts.number"/></td>
      <td><ctrl:text property="number" styleClass="filter"/></td>
      <td width="20">&nbsp;</td>

      <td align="right"><bean:message key="Contracts.contractor"/></td>
      <td>
        <ctrl:serverList property="contractor.name" idName="contractor.id"
                         action="/ContractorsListAction"  styleClass="filter" />
      </td>
      <td width="20">&nbsp;</td>

      <td align="right"><bean:message key="Contracts.start"/></td>
      <td ><ctrl:date property="date_begin" styleClass="filter" endField="date_end" showHelp="false"/></td>
      <td width="20">&nbsp;</td>

      <td align="right"><bean:message key="Contracts.min"/></td>
      <td width="1%"><ctrl:text property="sum_min_formatted" styleClass="filter" showHelp="false"/></td>
    </tr>
    <tr>
      <td align="right"><bean:message key="Contracts.user"/></td>
      <td><ctrl:serverList property="user.usr_name" idName="user.usr_id" action="/UsersListAction" styleClass="filter" /></td>
      <td width="20">&nbsp;</td>

      <td align="right"><bean:message key="Contracts.con_seller"/></td>
      <td><ctrl:serverList property="seller.name" idName="seller.id" action="/SellersListAction"  styleClass="filter" /></td>
      <td width="20">&nbsp;</td>

      <td align="right"><bean:message key="Contracts.end"/></td>
      <td ><ctrl:date property="date_end" styleClass="filter" startField="date_begin" showHelp="false"/></td>
      <td width="20">&nbsp;</td>

      <td align="right"><bean:message key="Contracts.max"/></td>
      <td width="1%"><ctrl:text property="sum_max_formatted" styleClass="filter" showHelp="false"/></td>
    </tr>

    <tr>
      <td colspan="100">
        <table border="0">
          <tr>
            <td width="120px" align="right"><ctrl:checkbox property="executed" styleClass="checkbox_filter" value="1" onclick="executedOnClick()"/></td>
            <td width="300px"><bean:message key="Contracts.executed"/></td>

            <td align="right"><ctrl:checkbox property="oridinal_absent" styleClass="checkbox_filter" value="1"/></td>
            <td><bean:message key="Contracts.oridinal_absent"/></td>
          </tr>
        </table>
      </td>
    </tr>
    <tr>
      <td colspan="100">
        <table border="0">
          <tr>
            <td style="width:120px;text-align:right"><ctrl:checkbox property="not_executed" styleClass="checkbox_filter" value="1" onclick="notExecutedOnClick()"/></td>
            <td><bean:message key="Contracts.not_executed"/></td>
          </tr>
        </table>
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
    <grid:table property="grid" key="con_id" autoLockName="Contract" >
      <grid:column align="center" width="10%"><jsp:attribute name="title"><bean:message key="Contracts.number"/></jsp:attribute></grid:column>
      <grid:column align="center" width="7%"><jsp:attribute name="title"><bean:message key="Contracts.date"/></jsp:attribute></grid:column>
      <grid:column align="center"><jsp:attribute name="title"><bean:message key="Contracts.contractor"/></jsp:attribute></grid:column>
      <grid:column align="center" width="10%"><jsp:attribute name="title"><bean:message key="Contracts.sum"/></jsp:attribute></grid:column>
      <grid:column align="center" width="5%"><jsp:attribute name="title"><bean:message key="Contracts.currency"/></jsp:attribute></grid:column>
      <grid:column align="center" width="20%"><jsp:attribute name="title"><bean:message key="Contracts.notes"/>&nbsp;<ctrl:help htmlName="ContractsNotesHeader"/></jsp:attribute></grid:column>
      <grid:column align="center" width="1%"><jsp:attribute name="title"><img title='<bean:message key="tooltip.Contracts.execute_status"/>' src='<ctrl:rewrite page="/images/accept.gif"/>'></jsp:attribute></grid:column>
      <grid:column align="center" width="10%"><jsp:attribute name="title"><bean:message key="Contracts.user"/></jsp:attribute></grid:column>
      <grid:column align="center" width="15%"><jsp:attribute name="title"><bean:message key="Contracts.con_reminder"/>&nbsp;<ctrl:help htmlName="ContractsReminderHeader"/></jsp:attribute></grid:column>
      <grid:column width="1%" title=""/>
      <grid:column width="1%" title=""/>
      <logic:equal name="Contracts" property="admin" value="true">
        <grid:column width="1%" title=""/>
      </logic:equal>
      <grid:row>
        <grid:colCustom width="10%" property="con_number" styleClassCheckerId="style-checker"/>
        <grid:colCustom width="7%" property="con_date_formatted" styleClassCheckerId="style-checker"/>
        <grid:colCustom property="con_contractor" styleClassCheckerId="style-checker"/>
        <grid:colCustom align="right" width="10%" property="con_summ_formatted" styleClassCheckerId="style-checker"/>
        <grid:colCustom align="center" width="5%" property="con_currency" styleClassCheckerId="style-checker"/>
        <grid:colCustom width="20%" property="notes" styleClassCheckerId="style-checker"/>
        <grid:colCheckbox align="center" width="1%" property="con_executed" readonlyCheckerId="alwaysReadonly"/>
        <grid:colCustom width="10%" property="con_user" styleClassCheckerId="style-checker"/>
        <grid:colCustom width="15%" property="con_reminder_formatted" styleClassCheckerId="style-checker"/>
        <grid:colEdit width="1%" action="/ContractAction.do?dispatch=edit" type="link" tooltip="tooltip.Contracts.edit"
                      readonlyCheckerId="editChecker"/>
        <grid:colImage type="link" enableOnClickAction="false" width="1%" styleClass="grid-image-without-border"
                       image="images/attach${record.attach_idx}.gif" tooltip="tooltip.Contracts.attach${record.attach_idx}"/>
        <logic:equal name="Contracts" property="admin" value="true">
          <grid:colDelete width="1%" action="/ContractAction.do?dispatch=delete" type="link"
                          tooltip="tooltip.Contracts.delete" showCheckerId="show-delete-checker"/>
        </logic:equal>
      </grid:row>
    </grid:table>
  </div>

  <div class=gridBottom>
    <ctrl:ubutton type="submit" dispatch="selectCP" scriptUrl="minsk_store=1" styleClass="width120">
      <bean:message key="button.importCP"/>
    </ctrl:ubutton>
    <ctrl:ubutton type="link" action="/ContractAction.do?dispatch=input" styleClass="width80">
      <bean:message key="button.create"/>
    </ctrl:ubutton>
  </div>
</ctrl:form>

<script language="JScript" type="text/javascript">
  var executed = document.getElementById('executed');
  var not_executed = document.getElementById('not_executed');

  function executedOnClick()
  {
    not_executed.checked = false;
  }

  function notExecutedOnClick()
  {
    executed.checked = false;
  }
</script>
