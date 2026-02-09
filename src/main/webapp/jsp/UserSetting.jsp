<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>

<ctrl:form>
  <ctrl:hidden property="ust_id"/>
  <ctrl:hidden property="ust_name"/>
  <ctrl:hidden property="ust_type"/>
  <ctrl:hidden property="ust_action"/>
  <table class=formBack align="center">
    <tr>
      <td>
        <table cellspacing="2">
          <tr>
            <td><bean:message key="UserSetting.ust_description"/></td>
            <td><ctrl:textarea property="ust_description" style="width:350px;height:125px;"/></td>
          </tr>
          <tr>
            <td><bean:message key="UserSetting.ust_value"/></td>
            <logic:equal name="UserSetting" property="showSimple" value="true">
              <td><ctrl:text property="ust_value"/></td>
            </logic:equal>
            <logic:equal name="UserSetting" property="showListAction" value="true">
              <td>
                <ctrl:serverList property="UserSettingList.name" idName="UserSettingList.id"
                                 action="/${UserSetting.ust_action}" selectOnly="true" style="width:330px;"/>
              </td>
            </logic:equal>
            <logic:equal name="UserSetting" property="showCheckboxAction" value="true">
              <td>
                <ctrl:checkbox property="ust_value" styleClass="checkbox" value="1"/>
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
