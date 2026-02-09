<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>

<ctrl:askUser name="ask_block" key="msg.delivery_request.block" showOkCancel="false" height="120"/>
<ctrl:askUser name="ask_unblock" key="msg.delivery_request.unblock" showOkCancel="false" height="140"/>

<h2>
  <logic:equal name="DeliveryRequests" property="inDoc" value="true">
    <bean:message key="DeliveryRequestsIn.title"/> <ctrl:help htmlName="DeliveryRequestsIn"/>
  </logic:equal>
  <logic:equal name="DeliveryRequests" property="outDoc" value="true">
    <bean:message key="DeliveryRequestsOut.title"/> <ctrl:help htmlName="DeliveryRequestsOut"/>
  </logic:equal>
</h2>

<ctrl:form styleId="striped-form">
  <ctrl:hidden property="direction"/>

  <table id=filterTbl style="" align="center" border="0" width="100%">
    <tr>
      <td colspan="3"></td>
      <td align="right"><bean:message key="DeliveryRequests.date"/></td>

      <td colspan="3">&nbsp;</td>
    </tr>
    <tr>
      <td align="right"><bean:message key="DeliveryRequests.number"/></td>
      <td><ctrl:text property="number" styleClass="filter"/></td>
      <td width="20">&nbsp;</td>

      <td align="right"><bean:message key="DeliveryRequests.start"/></td>
      <td ><ctrl:date property="date_begin" styleClass="filter" endField="date_end" showHelp="false"/></td>
      <td width="20">&nbsp;</td>

      <td align="right"></td>
      <td ><bean:message key="DeliveryRequests.annul_exclude"/>&nbsp;<ctrl:checkbox property="annul_exclude" styleClass="checkbox_filter" value="1"/></td>
      <td width="20">&nbsp;</td>
    </tr>
    <tr>
      <td align="right"><bean:message key="DeliveryRequests.user"/></td>
      <td><ctrl:serverList property="user.usr_name" idName="user.usr_id" action="/UsersListAction"  styleClass="filter" /></td>
      <td width="20">&nbsp;</td>

      <td align="right"><bean:message key="DeliveryRequests.end"/></td>
      <td ><ctrl:date property="date_end" styleClass="filter" startField="date_begin" showHelp="false"/></td>
      <td width="20">&nbsp;</td>

      <td align="right"></td>
      <td ><bean:message key="DeliveryRequests.unexecuted"/>&nbsp;<ctrl:checkbox property="unexecuted" styleClass="checkbox_filter" value="1"/></td>
      <td width="20">&nbsp;</td>
    </tr>

    <tr>
      <td align="right"><bean:message key="DeliveryRequests.specification_numbers"/></td>
      <td><ctrl:text property="specification_numbers" styleClass="filter"/></td>
      <td width="20">&nbsp;</td>

      <logic:equal name="DeliveryRequests" property="direction" value="in">
        <td align="right"></td>
        <td><bean:message key="DeliveryRequests.fair_trade"/>&nbsp;<ctrl:checkbox property="dlr_fair_trade" styleClass="checkbox_filter" value="1"/></td>
        <td width="20">&nbsp;</td>
      </logic:equal>

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
    <grid:table property="grid" key="dlr_id" autoLockName="DeliveryRequest" >
      <grid:column width="10%" align="center"><jsp:attribute name="title"><bean:message key="DeliveryRequests.number"/></jsp:attribute></grid:column>
      <grid:column width="10%" align="center"><jsp:attribute name="title"><bean:message key="DeliveryRequests.date"/></jsp:attribute></grid:column>
      <grid:column width="10%" align="center"><jsp:attribute name="title"><bean:message key="DeliveryRequests.dlr_fair_trade"/></jsp:attribute></grid:column>
      <grid:column width="10%" align="center"><jsp:attribute name="title"><bean:message key="DeliveryRequests.user"/></jsp:attribute></grid:column>
      <grid:column width="10%" align="center"><jsp:attribute name="title"><bean:message key="DeliveryRequests.specificationNumbers"/></jsp:attribute></grid:column>
      <grid:column width="10%" align="center"><jsp:attribute name="title"><bean:message key="DeliveryRequests.customers"/></jsp:attribute></grid:column>
      <grid:column align="center"><jsp:attribute name="title"><bean:message key="DeliveryRequests.contracts"/></jsp:attribute></grid:column>
      <grid:column width="15%" align="center"><jsp:attribute name="title"><bean:message key="DeliveryRequests.orders"/></jsp:attribute></grid:column>
      <grid:column width="1%" align="center"><jsp:attribute name="title"><img title='<bean:message key="tooltip.DeliveryRequests.block"/>' src='<ctrl:rewrite page="/images/lock.gif"/>'></jsp:attribute></grid:column>
      <grid:column width="1%" title=""/>
      <grid:column width="1%"><jsp:attribute name="title">&nbsp;<ctrl:help htmlName="DeliveryRequestsDeleteHeader"/></jsp:attribute></grid:column>
      <grid:row>
        <grid:colCustom width="10%" property="dlr_number" styleClassCheckerId="style-checker"/>
        <grid:colCustom width="10%" property="dlr_date_formatted" styleClassCheckerId="style-checker"/>
        <grid:colCustom width="10%" property="fairTradeFormatted" styleClassCheckerId="style-checker"/>
        <grid:colCustom width="10%" property="dlr_user" styleClassCheckerId="style-checker"/>
        <grid:colCustom width="10%" property="specification_numbers" styleClassCheckerId="style-checker"/>
        <grid:colCustom width="10%" property="customers" styleClassCheckerId="style-checker"/>
        <grid:colCustom property="contracts" styleClassCheckerId="style-checker"/>
        <grid:colCustom width="15%" property="orders" styleClassCheckerId="style-checker"/>
        <grid:colCheckbox width="1%" align="center" property="dlr_place_request" askUser="${record.msg_check_block}"
                          readonlyCheckerId="blockChecker" type="submit" dispatch="block"
                          scriptUrl="dlr_place_request=${record.dlr_place_request}"/>
        <grid:colEdit width="1%" action="/DeliveryRequestAction.do?dispatch=edit&direction=${DeliveryRequests.direction}"
                      type="link" tooltip="tooltip.DeliveryRequests.edit" readonlyCheckerId="editChecker"/>
        <grid:colDelete  width="1%" action="/DeliveryRequestAction.do?dispatch=delete"
                         type="link" tooltip="tooltip.DeliveryRequests.delete" showCheckerId="show-delete-checker"/>
      </grid:row>
    </grid:table>
  </div>

  <div class=gridBottom>
    <ctrl:ubutton type="link" action="/DeliveryRequestAction.do?dispatch=input&direction=${DeliveryRequests.direction}" styleClass="width80">
      <bean:message key="button.create"/>
    </ctrl:ubutton>
  </div>
</ctrl:form>
