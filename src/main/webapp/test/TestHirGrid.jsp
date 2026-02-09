<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>
<ctrl:form>
  <%--<ctrl:hidden property="rol_id" />--%>
  <table width='100%'>
    <tr>
      <td width=55%><bean:message key="UserRoles.available"/></td>
      <td width=45%><bean:message key="UserRoles.selected"/></td>
    </tr>
  </table>
  <table width='100%'>
    <tr><td width="45%">
    <div class="gridBack">
      <grid:table property="gridOut" key="usr_id,role_id" scrollableGrid="true"
                  height="expression(document.body.clientHeight-260)" groupBy="role_name" expandableGroup="true" >
        <grid:column />
        <grid:column />
        <grid:column><jsp:attribute name="title"><bean:message key="UserRoles.usr_surname"/></jsp:attribute></grid:column>
        <grid:column><jsp:attribute name="title"><bean:message key="UserRoles.usr_name"/></jsp:attribute></grid:column>
        <grid:column/>

        <grid:row>
          <grid:colCustom width="5%">&nbsp;</grid:colCustom>
          <grid:colCheckbox  width="1%" result="selected_ids_in"/>
          <%--<grid:colCheckbox  width="1%" result="result_in" resultProperty="id"/>--%>
          <grid:colCustom  property="usr_surname"/>
          <grid:colCustom width="50%" property="usr_name"/>
          <%--<grid:colInput property="usr_name" result="gridOut" resultProperty="usr_name" useIndexAsPK="true" width="50"/>--%>
          <grid:colServerList  result="gridOut" resultProperty="dep.name" idName="dep.id" useIndexAsPK="true" listWidth="30" action="/DepartmentsListAction"/>

        </grid:row>
      </grid:table>
    </div>
  </td>
  <td valign=center align="middle">
    <ctrl:ubutton type="submit" dispatch="add" styleClass="moveBtn"><bean:message key="UserRoles.add"/></ctrl:ubutton><br><br>
    <ctrl:ubutton type="submit" dispatch="delete" styleClass="moveBtn"><bean:message key="UserRoles.delete"/></ctrl:ubutton><br><br>
    <ctrl:ubutton type="submit" dispatch="addAll" styleClass="moveBtn"><bean:message key="UserRoles.addAll"/></ctrl:ubutton><br><br>
    <ctrl:ubutton type="submit" dispatch="deleteAll" styleClass="moveBtn"><bean:message key="UserRoles.deleteAll"/></ctrl:ubutton><br><br>
  </td>
  <td width="45%">
    <div class="gridBack">
      <grid:table property="gridIn" key="usr_id" scrollableGrid="true"
                  height="expression(document.body.clientHeight-260)" groupBy="role_name" expandableGroup="false">
        <grid:column/>
        <grid:column/>
        <grid:column><jsp:attribute name="title"><bean:message key="UserRoles.usr_surname"/></jsp:attribute></grid:column>
        <grid:column><jsp:attribute name="title"><bean:message key="UserRoles.usr_name"/></jsp:attribute></grid:column>
        <grid:row>
          <grid:colCustom   width="5%">&nbsp;</grid:colCustom>
          <grid:colCheckbox width="1%" result="selected_ids_out"/>
          <grid:colCustom  property="usr_surname"/>
          <grid:colCustom width="50%" property="usr_name"/>
          <%--<grid:colEdit width="1" action="/UserAction.do?dispatch=edit" type="link" tooltip="tooltip.UserRoles.edit"/>--%>
        </grid:row>
      </grid:table>
    </div>
  </td></tr></table>

  <div class=gridBottom>
    <ctrl:ubutton type="submit"  dispatch="test" styleClass="width80">Test</ctrl:ubutton>
  </div>
</ctrl:form>
