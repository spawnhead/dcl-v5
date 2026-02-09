<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>

<ctrl:form>
  <ctrl:hidden property="act_id"/>
  <table width='100%'>
    <tr>
      <td width=55%><bean:message key="ActionRoles.available"/></td>
      <td width=45%><bean:message key="ActionRoles.selected1"/> <ctrl:write name="ActionRoles" property="action.name"/> <bean:message key="ActionRoles.selected2"/></td>
    </tr>
  </table>
  <table width='100%'>
    <tr>
      <td width="45%">
        <div class="gridBack">
          <grid:table property="gridOut" key="rol_id" scrollableGrid="true"
                      height="expression(document.body.clientHeight-260)">
            <grid:column title=""/>
            <grid:column><jsp:attribute name="title"><bean:message key="ActionRoles.rol_name"/></jsp:attribute></grid:column>
            <grid:row>
              <grid:colCheckbox width="1%" result="selected_ids_in"/>
              <grid:colCustom property="rol_name"/>
            </grid:row>
          </grid:table>
        </div>
      </td>
      <td valign=center align="middle">
        <ctrl:ubutton type="submit" dispatch="add" styleClass="moveBtn"><bean:message key="button.moveRight"/></ctrl:ubutton><br><br>
        <ctrl:ubutton type="submit" dispatch="delete" styleClass="moveBtn"><bean:message key="button.moveLeft"/></ctrl:ubutton><br><br>
        <ctrl:ubutton type="submit" dispatch="addAll" styleClass="moveBtn"><bean:message key="button.moveRightAll"/></ctrl:ubutton><br><br>
        <ctrl:ubutton type="submit" dispatch="deleteAll" styleClass="moveBtn"><bean:message key="button.moveLeftAll"/></ctrl:ubutton><br><br>
      </td>
      <td width="45%">
        <div class="gridBack">
          <grid:table property="gridIn" key="rol_id" scrollableGrid="true"
                      height="expression(document.body.clientHeight-260)">
            <grid:column title=""/>
            <grid:column><jsp:attribute name="title"><bean:message key="ActionRoles.rol_name"/></jsp:attribute></grid:column>
            <grid:row>
              <grid:colCheckbox width="1%" result="selected_ids_out"/>
              <grid:colCustom property="rol_name"/>
            </grid:row>
          </grid:table>
        </div>
      </td>
    </tr>
  </table>

  <div class=gridBottom>
    <ctrl:ubutton type="link" action="/ActionsAction.do" styleClass="width80">
      <bean:message key="button.ok"/>
    </ctrl:ubutton>
  </div>
</ctrl:form>
