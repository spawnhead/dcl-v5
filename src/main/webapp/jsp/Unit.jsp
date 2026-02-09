<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>
<ctrl:form >
  <ctrl:hidden property="unt_id"/>
  <table class=formBack align="center"><tr><td >
  <table cellspacing="2" >
    <tr>
      <td colspan="10"><bean:message key="Unit.unt_name"/></td>
    </tr>
    <tr>
      <td colspan="10">
        <div class="gridBackNarrow" id="namesGrid">
          <grid:table property="unitgrid" key="lng_id">
            <grid:column  align="center">
              <jsp:attribute name="title"><bean:message key="UnitLanguages.lng_name"/></jsp:attribute>
            </grid:column>
            <grid:column  align="center">
              <jsp:attribute name="title"><bean:message key="UnitLanguages.unt_name"/></jsp:attribute>
            </grid:column>
            <grid:row>
              <grid:colInput property="lng_name" style="width:100px;" result="unitgrid" resultProperty="lng_name" useIndexAsPK="true" readonlyCheckerId="readOnlyChecker"/>
              <grid:colInput property="unt_name" style="width:200px;" result="unitgrid" resultProperty="unt_name" useIndexAsPK="true"/>
            </grid:row>
          </grid:table>
        </div>
      </td>
    </tr>
    <tr>
      <td colspan="2" align="right" class=formSpace>
            &nbsp;
      </td>
    </tr>
    <tr>
      <td colspan="2"><bean:message key="Units.is_acc_for_contract"></bean:message>
          <ctrl:checkbox property="is_acc_for_contract" styleClass="checkbox" value="1"/></td>
    </tr>
    <tr>
      <td colspan="2" align="right" class=formSpace>
        &nbsp;
      </td>
    </tr>
    <tr>
      <td colspan="2" align="right">
        <ctrl:ubutton type="link" actionForward="back" styleClass="width80">
          <bean:message key="button.cancel"/>
        </ctrl:ubutton>
        <ctrl:ubutton type="submit" dispatch="process" styleClass="width80">
          <bean:message key="button.save"/>
        </ctrl:ubutton>
      </td>
    </tr>
  </table>
  </td></tr></table>

</ctrl:form>
