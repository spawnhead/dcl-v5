<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>

<ctrl:askUser name="ask_block" key="msg.users.block" showOkCancel="false" height="120"/>
<ctrl:askUser name="ask_unblock" key="msg.users.unblock" showOkCancel="false" height="140"/>

<ctrl:form styleId="striped-form">
  <table id=filterTbl style="" align="center" border="0" width="100%">
    <tr>
      <td width="60px" align="right"><bean:message key="CommercialProposals.department"/></td>
      <td><ctrl:serverList property="department.name" idName="department.id" action="/DepartmentsListAction" styleClass="filter"/></td>
    </tr>
    <tr>
    </tr>

    <tr>
      <td align="right" colspan=20>
        <ctrl:ubutton type="submit" dispatch="clearFilter" styleClass="width120"><bean:message key="button.clearFilter"/></ctrl:ubutton>&nbsp;
        <ctrl:ubutton type="submit" dispatch="execute" styleClass="width120"><bean:message key="button.applyFilter"/></ctrl:ubutton>
      </td>
    </tr>
  </table>

  <div class="gridBack">
    <grid:table property="grid" key="usr_id" scrollableGrid="true" height="expression(document.body.clientHeight-295)">
      <grid:column align="center"><jsp:attribute name="title"><bean:message key="users.usr_surname"/></jsp:attribute></grid:column>
      <grid:column align="center"><jsp:attribute name="title"><bean:message key="users.usr_name"/></jsp:attribute></grid:column>
      <grid:column align="center"><jsp:attribute name="title"><bean:message key="users.usr_code"/></jsp:attribute></grid:column>
      <grid:column align="center"><jsp:attribute name="title"><bean:message key="users.usr_login"/></jsp:attribute></grid:column>
      <grid:column align="center"><jsp:attribute name="title"><bean:message key="users.usr_department"/></jsp:attribute></grid:column>
      <grid:column align="center"><jsp:attribute name="title"><bean:message key="users.usr_position"/></jsp:attribute></grid:column>
      <grid:column align="center"><jsp:attribute name="title"><bean:message key="users.usr_phone"/></jsp:attribute></grid:column>
      <grid:column align="center"><jsp:attribute name="title"><bean:message key="users.usr_fax"/></jsp:attribute></grid:column>
      <grid:column align="center"><jsp:attribute name="title"><bean:message key="users.usr_email"/></jsp:attribute></grid:column>
      <grid:column align="center"><jsp:attribute name="title"><bean:message key="users.roles"/></jsp:attribute></grid:column>
      <grid:column align="center" width="1%"><jsp:attribute name="title"><img title='<bean:message key="tooltip.users.block_status"/>' src='<ctrl:rewrite page="/images/lock.gif"/>'></jsp:attribute></grid:column>
      <grid:column width="1%" title=""/>
      <grid:row>
        <grid:colCustom width="10%" property="usr_surname"/>
        <grid:colCustom width="10%" property="usr_name"/>
        <grid:colCustom width="3%"  property="usr_code"/>
        <grid:colCustom width="40px" property="usr_login"/>
        <grid:colCustom width="10%" property="usr_department"/>
        <grid:colCustom property="usr_position"/>
        <grid:colCustom width="10%" property="usr_phone"/>
        <grid:colCustom width="10%" property="usr_fax"/>
        <grid:colCustom width="13%" property="usr_email"/>
        <grid:colCustom width="12%" property="roles"/>
        <grid:colCheckbox align="center" width="1%" property="usr_block" askUser="${record.msg_check_block}" readonlyCheckerId="blockChecker" type="submit" dispatch="block" scriptUrl="block=${record.usr_block}"/>
        <grid:colEdit width="1%" action="/UserAction.do?dispatch=edit" type="link" tooltip="tooltip.users.edit"/>
      </grid:row>
    </grid:table>
  </div>

  <div class=gridBottom>
    <ctrl:ubutton type="link" action="/UserAction.do?dispatch=create" styleClass="width80">
      <bean:message key="button.create"/>
    </ctrl:ubutton>
  </div>
</ctrl:form>
