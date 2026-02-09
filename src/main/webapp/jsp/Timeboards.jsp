<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>

<ctrl:form styleId="striped-form">
  <table id=filterTbl width="100%" style="" align="center" border="0" >
    <tr>
      <td align="right"><bean:message key="Timeboards.date"/></td>
      <td><ctrl:date  property="tmb_date" styleClass="filter" onlyHeader="true"/></td>
    </tr>
    <tr>
      <td align="right"><bean:message key="Timeboards.user"/></td>
      <td><ctrl:serverList property="user.usr_name" idName="user.usr_id" action="/UsersListAction"  styleClass="filter" /></td>
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
    <grid:table property="grid" key="tmb_id" autoLockName="Timeboard" >
      <grid:column align="center"><jsp:attribute name="title"><bean:message key="Timeboards.date"/></jsp:attribute></grid:column>
      <grid:column align="center" width="50%"><jsp:attribute name="title"><bean:message key="Timeboards.user"/></jsp:attribute></grid:column>
      <grid:column align="center" width="1%"><jsp:attribute name="title"><img title='<bean:message key="tooltip.Timeboards.block_status"/>' src='<ctrl:rewrite page="/images/lock.gif"/>'></jsp:attribute></grid:column>
      <grid:column width="1%" title="" hideOnSelectMode="true"/>
      <grid:row>
        <grid:colCustom property="tmb_date_formatted"/>
        <grid:colCustom property="tmb_user"/>
        <grid:colCheckbox align="center" property="tmb_checked" type="submit" readonlyCheckerId="blockChecker" dispatch="block" scriptUrl="block=${record.tmb_checked}"/>
        <grid:colEdit width="1%" action="/TimeboardAction.do?dispatch=edit" type="link" tooltip="tooltip.Timeboards.edit"/>
      </grid:row>
    </grid:table>
  </div>

  <div class=gridBottom>
    <ctrl:ubutton type="link" action="/TimeboardAction.do?dispatch=input" styleClass="width80">
      <bean:message key="button.create"/>
    </ctrl:ubutton>
  </div>
</ctrl:form>
