<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>

<table id="ShippingManagersGrid" width="150" style="background-color:#eeeeee" border="0" cellpadding="0"
       cellspacing="0">
  <logic:iterate id="val" indexId="id" name="Shipping" property="grid.row(${param.counter}).position.managers">
    <tr>
      <td>
        <ctrl:serverList property="grid.row(${param.counter}).position.managers[${id}].manager.userFullName"
                         idName="grid.row(${param.counter}).position.managers[${id}].manager.usr_id"
                         action="/UsersListAction" filter="filter"
                         style="width:120px" showHelp="false" readonly="${gridReadOnly}"/>
      </td>
      <logic:equal value="1" name="Shipping" property="grid.row(${param.counter}).position.managersCount">
        <ctrl:hidden property="grid.row(${param.counter}).position.managers[${id}].percent" style="width:30px"/>
      </logic:equal>
      <logic:notEqual value="1" name="Shipping" property="grid.row(${param.counter}).position.managersCount">
        <td>
          <ctrl:text property="grid.row(${param.counter}).position.managers[${id}].percent" style="width:30px" size="3"
                     readonly="${gridReadOnly}" showHelp="false"/>
        </td>
      </logic:notEqual>
      <td>
        <logic:equal value="0" name="id">
          <ctrl:ubutton type="script" action="/Shipping.fakeForAdd" styleClass="width165"
                        onclick="addToGrid(${param.counter})"
                        style="width:19px;background-color:#eeeeee;border:0px;font-weight:bold;color:green;font-size:14px"
                        readonly="${gridReadOnly}" showWait="false">
            <bean:message key="button.plus"/>
          </ctrl:ubutton>
        </logic:equal>
        <logic:notEqual value="0" name="id">
          <ctrl:ubutton type="script" action="/Shipping.fakeForDelete" styleClass="width165"
                        onclick="removeFromGrid(${param.counter},${id})"
                        style="width:19px;background-color:#eeeeee;border:0px;font-weight:bold;color:red;font-size:16px"
                        readonly="${gridReadOnly}" showWait="false">
            <bean:message key="button.minus"/>
          </ctrl:ubutton>

        </logic:notEqual>
      </td>
    </tr>
  </logic:iterate>
</table>
