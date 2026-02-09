<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>

<ctrl:form>
  <table width="50%" align="center">
    <tr>
      <td>
        <div class="gridBack">
          <grid:table property="grid" key="ist_id" scrollableGrid="true" height="expression(document.body.clientHeight-235)" autoLockName="InstructionTypes">
            <grid:column align="center"><jsp:attribute name="title"><bean:message key="InstructionTypes.ist_name"/></jsp:attribute></grid:column>
            <grid:column title=""/>
            <grid:row>
              <grid:colCustom property="ist_name"/>
              <grid:colEdit width="1" dispatch="edit" type="submit" tooltip="tooltip.InstructionTypes.edit"/>
            </grid:row>
          </grid:table>
        </div>

        <div class=gridBottom>
          <ctrl:ubutton type="link" actionForward="back" styleClass="width80">
            <bean:message key="button.back"/>
          </ctrl:ubutton>
          <ctrl:ubutton type="link" actionForward="input" styleClass="width80">
            <bean:message key="button.create"/>
          </ctrl:ubutton>
        </div>
      </td>
    </tr>
  </table>
</ctrl:form>
