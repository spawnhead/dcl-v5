<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>

<ctrl:form>
  <ctrl:hidden property="stn_id"/>
  <ctrl:hidden property="stn_type"/>
  <ctrl:hidden property="stn_action"/>
  <table class=formBack align="center">
    <tr>
      <td>
        <table cellspacing="2">
          <tr>
            <td><bean:message key="Setting.stn_name"/></td>
            <td><ctrl:text property="stn_name" readonly="true"/></td>
          </tr>
          <tr>
            <td><bean:message key="Setting.stn_description"/></td>
            <td><ctrl:textarea property="stn_description" style="width:350px;height:125px;"/></td>
          </tr>
          <tr>
            <td><bean:message key="Setting.stn_value"/></td>
            <logic:equal name="Setting" property="showSimple" value="true">
              <td><ctrl:text property="stn_value"/></td>
            </logic:equal>
            <logic:equal name="Setting" property="showListAction" value="true">
              <td>
                <ctrl:serverList property="settingList.name" idName="settingList.id"
                                 action="/${Setting.stn_action}" selectOnly="true" style="width:330px;"/>
              </td>
            </logic:equal>
            <logic:equal name="Setting" property="showCheckboxAction" value="true">
              <td>
                <ctrl:checkbox property="stn_value" styleClass="checkbox" value="1"/>
              </td>
            </logic:equal>
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
