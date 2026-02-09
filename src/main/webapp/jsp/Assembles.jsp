<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>

<ctrl:form styleId="striped-form">
  <table id=filterTbl style="" align="center" border="0" width="100%">
    <tr>
      <td colspan="3"></td>
      <td align="right"><bean:message key="Assembles.date"/></td>
    </tr>
    <tr>
      <td align="right"><bean:message key="Assembles.number"/></td>
      <td><ctrl:text property="number" styleClass="filter"/></td>
      <td width="20">&nbsp;</td>

      <td align="right"><bean:message key="Assembles.start"/></td>
      <td ><ctrl:date property="date_begin" styleClass="filter" endField="date_end" showHelp="false"/></td>
      <td width="20">&nbsp;</td>
    </tr>
    <tr>
      <td align="right"><bean:message key="Assembles.user"/></td>
      <td><ctrl:serverList property="user.usr_name" idName="user.usr_id" action="/UsersListAction"  styleClass="filter" /></td>
      <td width="20">&nbsp;</td>

      <td align="right"><bean:message key="Assembles.end"/></td>
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
    <grid:table property="grid" key="asm_id" autoLockName="Assemble" >
      <grid:column width="20%" align="center"><jsp:attribute name="title"><bean:message key="Assembles.number"/></jsp:attribute></grid:column>
      <grid:column width="20%" align="center"><jsp:attribute name="title"><bean:message key="Assembles.date"/></jsp:attribute></grid:column>
      <grid:column align="center"><jsp:attribute name="title"><bean:message key="Assembles.user"/></jsp:attribute></grid:column>
      <grid:column width="10%" align="center"><jsp:attribute name="title"><bean:message key="Assembles.type"/></jsp:attribute></grid:column>
      <grid:column width="1%" align="center"><jsp:attribute name="title"><img title='<bean:message key="tooltip.Assembles.block"/>' src='<ctrl:rewrite page="/images/lock.gif"/>'></jsp:attribute></grid:column>
      <grid:column width="1%" title=""/>
      <grid:row>
        <grid:colCustom width="20%" property="asm_number"/>
        <grid:colCustom width="20%" property="asm_date_formatted"/>
        <grid:colCustom property="asm_user"/>
        <grid:colCustom width="10%" property="type"/>
        <grid:colCheckbox width="1%" align="center" property="asm_block" onclick="unblockOnClick(this);" readonlyCheckerId="blockChecker"/>
        <grid:colEdit width="1%" action="/AssembleAction.do?dispatch=edit" type="link" tooltip="tooltip.Assembles.edit"/>
      </grid:row>
    </grid:table>
  </div>

  <div class=gridBottom>
    <ctrl:ubutton type="link" action="/AssembleAction.do?dispatch=input" styleClass="width80">
      <bean:message key="button.create"/>
    </ctrl:ubutton>
  </div>
</ctrl:form>

<script language="JScript" type="text/javascript">
  function unblockOnClick(obj)
  {
    obj.checked = true;
    alert('<bean:message key="msg.assemble.unblock"/>');
  }
</script>
