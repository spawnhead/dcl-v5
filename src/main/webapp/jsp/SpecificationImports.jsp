<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>

<ctrl:askUser name="ask_block" key="msg.specification_import.block" showOkCancel="false" height="120"/>
<ctrl:askUser name="ask_unblock" key="msg.specification_import.unblock" showOkCancel="false" height="140"/>

<table id="headerTable" style="width:100%;text-align:center" border="0">
  <tr>
	  <td style="width:20%; text-align: left">
		  <h2>
	      <bean:message key="SpecificationImports.title"/> <ctrl:help htmlName="SpecificationImports"/>
		  </h2>
	  </td>
	  <td style="width:60%; text-align: center">
		  <h2>
		    <span id="laterDates"></span>
		  </h2>
	  </td>
	  <td>
	  </td>
  </tr>
</table>

<ctrl:form styleId="striped-form">
  <table id=filterTbl style="" align="center" border="0" width="100%">
    <tr>
      <td colspan="3"></td>
      <td align="right"><bean:message key="SpecificationImports.date"/></td>
    </tr>
    <tr>
      <td align="right"><bean:message key="SpecificationImports.number"/></td>
      <td><ctrl:text property="number" styleClass="filter"/></td>
      <td width="20">&nbsp;</td>

      <td align="right"><bean:message key="SpecificationImports.start"/></td>
      <td ><ctrl:date property="date_begin" styleClass="filter" endField="date_end" showHelp="false"/></td>
      <td width="20">&nbsp;</td>
    </tr>
    <tr>
      <td align="right"><bean:message key="SpecificationImports.user"/></td>
      <td><ctrl:serverList property="user.userFullName" idName="user.usr_id" action="/UsersListAction" styleClass="filter" /></td>
      <td width="20">&nbsp;</td>

      <td align="right"><bean:message key="SpecificationImports.end"/></td>
      <td ><ctrl:date property="date_end" styleClass="filter" startField="date_begin" showHelp="false"/></td>
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
    <grid:table property="grid" key="spi_id" autoLockName="SpecificationImport" >
      <grid:column width="25%" align="center"><jsp:attribute name="title"><bean:message key="SpecificationImports.number"/></jsp:attribute></grid:column>
      <grid:column width="15%" align="center"><jsp:attribute name="title"><bean:message key="SpecificationImports.date"/></jsp:attribute></grid:column>
      <grid:column width="20%" align="center"><jsp:attribute name="title"><bean:message key="SpecificationImports.spi_cost"/></jsp:attribute></grid:column>
      <grid:column align="center"><jsp:attribute name="title"><bean:message key="SpecificationImports.user"/></jsp:attribute></grid:column>
			<grid:column width="1%" align="center"><jsp:attribute name="title"><bean:message key="SpecificationImports.spi_send_date"/></jsp:attribute></grid:column>
			<grid:column width="1%" align="center"><jsp:attribute name="title"><img title='<bean:message key="tooltip.SpecificationImports.block"/>' src='<ctrl:rewrite page="/images/lock.gif"/>'></jsp:attribute></grid:column>
			<grid:column width="1%" title=""/>
      <grid:row>
        <grid:colCustom width="25%" property="spi_number"/>
        <grid:colCustom width="15%" property="spiDateFormatted"/>
        <grid:colCustom width="20%" align="right" property="spiCostFormatted"/>
        <grid:colCustom property="spi_users"/>
        <grid:colDate property="spiSendDateFormatted" afterSelect="dateSelected" afterSelectParams="${record.spi_id}" readonlyCheckerId="sendDateChecker"/>
        <grid:colCheckbox width="1%" align="center" property="spi_arrive" askUser="${record.msg_check_block}" readonlyCheckerId="blockChecker" type="submit" dispatch="block" scriptUrl="spi_arrive=${record.spi_arrive}"/>
        <grid:colEdit width="1%" action="/SpecificationImportAction.do?dispatch=edit" type="link" tooltip="tooltip.SpecificationImports.edit"/>
      </grid:row>
    </grid:table>
  </div>

  <div class=gridBottom>
    <ctrl:ubutton type="link" action="/SpecificationImportAction.do?dispatch=input" styleClass="width80">
      <bean:message key="button.create"/>
    </ctrl:ubutton>
  </div>
</ctrl:form>

<script language="JScript" type="text/javascript">
	function dateSelected(arg){
		doAjax({
			url:'<ctrl:rewrite action="/SpecificationImportsAction.do" dispatch="ajaxSetSentDate"/>',
			params:{spi_id:arg.params, spi_send_date:arg.value},
			synchronous:true
		});

		updateLaterDates();

		return true;
	}

	function updateLaterDates()
  {
    doAjax({
        url:'<ctrl:rewrite action="/SpecificationImportsAction.do" dispatch="ajaxGetLaterDates"/>',
        synchronous:false,
        update:'laterDates'
      });
  }

	initFunctions.push(updateLaterDates);
</script>
