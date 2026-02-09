<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>

<ctrl:askUser name="ask_block" key="msg.produce_cost.block" showOkCancel="false" height="120"/>
<ctrl:askUser name="ask_unblock" key="msg.produce_cost.unblock" showOkCancel="false" height="140"/>

<ctrl:form styleId="striped-form">
  <table id=filterTbl style="" align="center" border="0" width="100%">
    <tr>
      <td colspan="3"></td>
      <td align="right"><bean:message key="ProducesCost.date"/></td>
    </tr>
    <tr>
      <td align="right"><bean:message key="ProducesCost.number"/></td>
      <td><ctrl:text property="number" styleClass="filter"/></td>
      <td width="20">&nbsp;</td>

      <td align="right"><bean:message key="ProducesCost.start"/></td>
      <td ><ctrl:date property="date_begin" styleClass="filter" endField="date_end" showHelp="false"/></td>
      <td width="20">&nbsp;</td>

      <td align="right"><bean:message key="ProducesCost.number_1c"/></td>
      <td ><ctrl:text property="number_1c" styleClass="filter"/></td>
      <td width="20">&nbsp;</td>
    </tr>
    <tr>
      <td align="right"><bean:message key="ProducesCost.route"/></td>
      <td><ctrl:serverList property="route.name" idName="route.id" action="/RoutesListAction"  styleClass="filter" /></td>
      <td width="20">&nbsp;</td>

      <td align="right"><bean:message key="ProducesCost.end"/></td>
      <td><ctrl:date property="date_end" styleClass="filter" startField="date_begin" showHelp="false"/></td>
      <td width="20">&nbsp;</td>

      <td></td>
      <td><bean:message key="ProducesCost.block_in_filter"/>&nbsp;<ctrl:checkbox property="block_in_filter" styleClass="checkbox_filter" value="1"/></td>
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
    <grid:table property="grid" key="prc_id" autoLockName="ProduceCost" >
      <grid:column align="center" width="20%"><jsp:attribute name="title"><bean:message key="ProducesCost.date"/></jsp:attribute></grid:column>
      <grid:column align="center" width="20%"><jsp:attribute name="title"><bean:message key="ProducesCost.number"/></jsp:attribute></grid:column>
      <grid:column align="center"><jsp:attribute name="title"><bean:message key="ProducesCost.route"/></jsp:attribute></grid:column>
      <grid:column align="center" width="1%"><jsp:attribute name="title"><img title='<bean:message key="tooltip.ProducesCost.block_status"/>' src='<ctrl:rewrite page="/images/lock.gif"/>'></jsp:attribute></grid:column>
      <grid:column width="1%" title=""/>
      <grid:row>
        <grid:colCustom width="20%" property="prc_date_date"/>
        <grid:colCustom width="20%" property="prc_number"/>
        <grid:colCustom property="prc_route"/>
        <grid:colCheckbox width="1%" align="center" property="prc_block" askUser="${record.msg_check_block}"
                          readonlyCheckerId="blockChecker" type="submit" dispatch="block" 
                          scriptUrl="block=${record.prc_block}"/>
        <grid:colEdit width="1%" action="/ProduceCostAction.do?dispatch=edit" type="link"
                      tooltip="tooltip.ProducesCost.edit" readonlyCheckerId="editChecker"/>
      </grid:row>
    </grid:table>
  </div>

  <div class=gridBottom>
    <ctrl:ubutton type="link" action="/ProduceCostAction.do?dispatch=input" styleClass="width80">
      <bean:message key="button.create"/>
    </ctrl:ubutton>
  </div>
</ctrl:form>
