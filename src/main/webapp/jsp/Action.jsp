<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>

<ctrl:form>
  <ctrl:hidden property="act_id"/>
  <table class=formBack align="center">
    <tr>
      <td>
        <table cellspacing="2">
          <tr>
            <td><bean:message key="Action.act_system_name"/></td>
            <td><ctrl:text property="act_system_name" readonly="true"/></td>
          </tr>
          <tr>
            <td><bean:message key="Action.act_name"/></td>
            <td><ctrl:text property="act_name"/></td>
          </tr>
          <tr>
            <td><bean:message key="Action.act_logging"/></td>
            <td><ctrl:checkbox property="act_logging" styleClass="checkbox" value="1"/></td>
          </tr>
          <tr>
            <td><bean:message key="Action.act_check_access"/></td>
            <td><ctrl:checkbox property="act_check_access" styleClass="checkbox" value="1"/></td>
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
      </td>
    </tr>
  </table>

</ctrl:form>
