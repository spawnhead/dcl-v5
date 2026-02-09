<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>

<ctrl:form styleId="striped-form">
  <table id=filterTbl align="center" border="0" width="100%">
    <tr>
      <td colspan="6"></td>
      <td align="right"><bean:message key="Logs.date"/></td>
    </tr>
    <tr>
      <td align="right"><bean:message key="Logs.user"/></td>
      <td><ctrl:serverList property="user.usr_name" idName="user.usr_id" action="/UsersListAction" styleClass="filter"/></td>
      <td width="20">&nbsp;</td>

      <td align="right"><bean:message key="Logs.ip"/></td>
      <td><ctrl:text property="ip" styleClass="filter"/></td>
      <td width="20">&nbsp;</td>

      <td align="right"><bean:message key="Logs.start"/></td>
      <td ><ctrl:date property="date_begin" styleClass="filter" endField="date_end"/></td>
    </tr>
    <tr>
      <td align="right"><bean:message key="Logs.action"/></td>
      <td><ctrl:serverList property="action.act_name" idName="action.act_id" action="/ActionsListAction" styleClass="filter" selectOnly="true"/></td>
      <td width="20">&nbsp;</td>

      <td colspan="3">&nbsp;</td>

      <td align="right"><bean:message key="Logs.end"/></td>
      <td ><ctrl:date property="date_end" styleClass="filter" startField="date_begin"/></td>
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
    <grid:table property="grid" key="log_id">
      <grid:column align="center"><jsp:attribute name="title"><bean:message key="Logs.action"/></jsp:attribute></grid:column>
      <grid:column align="center" width="25%"><jsp:attribute name="title"><bean:message key="Logs.user"/></jsp:attribute></grid:column>
      <grid:column align="center" width="15%"><jsp:attribute name="title"><bean:message key="Logs.ip"/></jsp:attribute></grid:column>
      <grid:column align="center" width="15%"><jsp:attribute name="title"><bean:message key="Logs.date"/></jsp:attribute></grid:column>
      <grid:row>
        <grid:colCustom property="log_action"/>
        <grid:colCustom width="25%" property="log_user"/>
        <grid:colCustom width="15%" property="log_ip"/>
        <grid:colCustom width="15%" property="log_time_formatted"/>
      </grid:row>
    </grid:table>
  </div>

  <div class=gridBottom>
    <input type=button id="generateButtonExcel"  onclick="generateExcelClick();"  class="width165" value="<bean:message key="button.formExcel"/>">
  </div>
</ctrl:form>

<script type="text/javascript" language="JavaScript">
  function generateExcelClick()
  {
	  document.getElementById('for-insert').innerHTML='<iframe src="<ctrl:rewrite action="/LogsAction" dispatch="generateExcel"/>" style="display:none" />';
  }
</script>

<div id='for-insert'></div>