<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>

<ctrl:askUser name="checkPriceAsk" key="msg.ConditionForContract.checkPrice" showOkCancel="false" height="140"/>

<ctrl:form styleId="striped-form">
  <table id=filterTbl style="" align="center" border="0" width="100%">
    <tr>
	    <td align="right"><bean:message key="ConditionsForContract.contractor"/></td>
	    <td><ctrl:serverList property="contractor.name" idName="contractor.id" action="/ContractorsListAction"  styleClass="filter" /></td>
	    <td width="20">&nbsp;</td>

	    <td align="right"><bean:message key="ConditionsForContract.date"/>&nbsp;<ctrl:help htmlName="ConditionsForContract.date"/></td>
	    <td ></td>
	    <td width="20">&nbsp;</td>

	    <td align="right"></td>
 	    <td ><ctrl:checkbox property="cfc_not_executed" styleClass="checkbox_filter" value="1"/>&nbsp;<bean:message key="ConditionsForContract.cfc_not_executed"/></td>
		  <td width="20">&nbsp;</td>
    </tr>
    <tr>
      <td align="right"><bean:message key="ConditionsForContract.user"/></td>
      <td><ctrl:serverList property="user.usr_name" idName="user.usr_id" action="/UsersListAction"  styleClass="filter" /></td>
      <td width="20">&nbsp;</td>

	    <td align="right"><bean:message key="ConditionsForContract.start"/></td>
	    <td ><ctrl:date property="date_begin" styleClass="filter" endField="date_end" showHelp="false"/></td>
	    <td width="20">&nbsp;</td>

	    <td align="right"></td>
 	    <td ><ctrl:checkbox property="annul_exclude" styleClass="checkbox_filter" value="1"/>&nbsp;<bean:message key="ConditionsForContract.annul_exclude"/></td>
		  <td width="20">&nbsp;</td>
    </tr>

    <tr>
	    <td align="right"><bean:message key="ConditionsForContract.cfc_seller"/></td>
      <td ><ctrl:serverList property="seller.name" idName="seller.id" action="/SellersListAction" styleClass="filter"/></td>
      <td width="20">&nbsp;</td>

	    <td align="right"><bean:message key="ConditionsForContract.end"/></td>
      <td ><ctrl:date property="date_end" styleClass="filter" startField="date_begin" showHelp="false"/></td>
      <td width="20">&nbsp;</td>

	    <td align="right"></td>
 	    <td ><ctrl:checkbox property="cfc_not_placed" styleClass="checkbox_filter" value="1"/>&nbsp;<bean:message key="ConditionsForContract.cfc_not_placed"/></td>
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
  <div class="gridBack" >
    <grid:table property="grid" key="cfc_id" autoLockName="ConditionForContract" >
      <grid:column align="center" width="15%"><jsp:attribute name="title"><bean:message key="ConditionsForContract.cfc_place_date"/></jsp:attribute></grid:column>
      <grid:column align="center"><jsp:attribute name="title"><bean:message key="ConditionsForContract.contractor"/></jsp:attribute></grid:column>
      <grid:column align="center" width="15%"><jsp:attribute name="title"><bean:message key="ConditionsForContract.user"/></jsp:attribute></grid:column>
      <grid:column align="center" width="25%"><jsp:attribute name="title"><bean:message key="ConditionsForContract.cfc_seller"/></jsp:attribute></grid:column>
      <grid:column align="center" width="20%"><jsp:attribute name="title"><bean:message key="ConditionsForContract.cfc_check_price"/></jsp:attribute></grid:column>
      <grid:column align="center" width="1%"><jsp:attribute name="title"><img title='<bean:message key="tooltip.ConditionsForContract.execute_status"/>' src='<ctrl:rewrite page="/images/accept.gif"/>'></jsp:attribute></grid:column>
      <grid:column width="1%" title=""/>
      <grid:column width="1%" title=""/>
      <grid:row>
        <grid:colCustom width="15%" property="cfc_place_date_formatted" styleClassCheckerId="style-checker"/>
        <grid:colCustom property="cfc_contractor" styleClassCheckerId="style-checker"/>
        <grid:colCustom width="15%" property="cfc_user" styleClassCheckerId="style-checker"/>
        <grid:colCustom width="25%" property="cfc_seller" styleClassCheckerId="style-checker"/>
	      <grid:colCheckbox width="20%" property="cfc_check_price" askUser="checkPriceAsk" type="submit" readonlyCheckerId="checkPriceChecker" styleClassCheckerId="style-checker"
	                        dispatch="checkPrice" scriptUrl="cfc_check_price=${record.cfc_check_price}">&nbsp;${record.checkPricesText}</grid:colCheckbox>
        <grid:colCheckbox align="center" width="1%" property="cfc_execute" readonlyCheckerId="executeChecker" type="submit" dispatch="markExecute" scriptUrl="cfc_execute=${record.cfc_execute}"/>
        <grid:colImage width="1%" action="/ConditionForContractAction.do?dispatch=clone" type="link"
                       tooltip="tooltip.ConditionsForContract.clone" image="images/copy.gif"
                       styleClass="grid-image-without-border" readonlyCheckerId="cloneEditChecker"/>
        <grid:colEdit width="1%" action="/ConditionForContractAction.do?dispatch=edit" type="link"
                      tooltip="tooltip.ConditionsForContract.edit" readonlyCheckerId="cloneEditChecker"/>
      </grid:row>
    </grid:table>
  </div>

  <div class=gridBottom>
    <ctrl:ubutton type="link" action="/ConditionForContractAction.do?dispatch=input" styleClass="width80">
      <bean:message key="button.create"/>
    </ctrl:ubutton>
  </div>
</ctrl:form>
