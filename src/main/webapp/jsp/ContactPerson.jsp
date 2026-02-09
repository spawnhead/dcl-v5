<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>
<%@ taglib uri="http://ditchnet.org/jsp-tabs-taglib" prefix="tab" %>

<ctrl:form>
  <ctrl:hidden property="ctr_id"/>
  <ctrl:hidden property="cps_id"/>
  <ctrl:hidden property="cps_block"/>
  <ctrl:hidden property="number"/>
  <ctrl:hidden property="fromContractor"/>
  <ctrl:hidden property="lastNumberUser"/>

  <table class=formBack align="center">
    <tr>
      <td>
        <tab:tabContainer id="contactPersonComtainer" selectedTabPaneId="mainPanel">
          <tab:tabPane id="mainPanel" tabTitle="ContactPerson.main">
            <table cellspacing="2" width="570px">
              <tr>
                <td><bean:message key="ContactPerson.cps_name"/></td>
                <td><ctrl:text property="cps_name"/></td>
              </tr>
              <tr>
                <td><bean:message key="ContactPerson.cps_position"/></td>
                <td><ctrl:text property="cps_position"/></td>
              </tr>
              <tr>
                <td><bean:message key="ContactPerson.cps_on_reason"/></td>
                <td><ctrl:text property="cps_on_reason"/></td>
              </tr>
              <tr>
                <td><bean:message key="ContactPerson.cps_phone"/></td>
                <td><ctrl:text property="cps_phone"/></td>
              </tr>
              <tr>
                <td><bean:message key="ContactPerson.cps_mob_phone"/></td>
                <td><ctrl:text property="cps_mob_phone"/></td>
              </tr>
              <tr>
                <td><bean:message key="ContactPerson.cps_fax"/></td>
                <td><ctrl:text property="cps_fax"/></td>
              </tr>
              <tr>
                <td><bean:message key="ContactPerson.cps_email"/></td>
                <td><ctrl:text property="cps_email"/></td>
              </tr>
              <tr>
                <td><bean:message key="ContactPerson.cps_contract_comment"/></td>
                <td><ctrl:text property="cps_contract_comment"/></td>
              </tr>
              <tr>
                <td><bean:message key="ContactPerson.cps_fire"/></td>
                <td><ctrl:checkbox property="cps_fire" styleClass="checkbox" value="1"/></td>
              </tr>
              <tr>
                <td colspan="2" align="right" class=formSpace>
                  &nbsp;
                </td>
              </tr>
            </table>
          </tab:tabPane>

          <tab:tabPane id="usersContactPerson" tabTitle="ContactPerson.users">
            <table align="center" width="570px">
              <tr>
                <td>
                  <table align="center" width="250px">
                    <tr>
                      <td>
                        <div class="gridBack">
                          <grid:table property="gridContactPersonUsers" key="number" scrollableGrid="false" autoLockName="ContactPersonUsers">
                            <grid:column align="center"><jsp:attribute name="title"><bean:message key="ContactPersonUsers.user"/></jsp:attribute></grid:column>
                            <grid:column width="1%" title=""/>
                            <grid:row>
                              <grid:colServerList property="user.userFullName" idName="user.usr_id" result="gridContactPersonUsers"
                                                  resultProperty="user.userFullName" useIndexAsPK="true" action="/UsersListAction"
                                                  selectOnly="true" readonlyCheckerId="readonlyChangeUserForNotAdmin"/>
                              <grid:colDelete width="1%" type="submit" dispatch="deleteRowFromUserGrid" scriptUrl="numberUser=${record.number}"
                                              tooltip="tooltip.ContactPersonUsers.delete" showCheckerId="showDeleteUserForAdmin"/>
                            </grid:row>
                          </grid:table>
                        </div>
                      </td>
                    </tr>
                    <tr>
                      <td>
                        <div class=gridBottom>
                          <input id='buttonAddUser' type='button' onclick='addUserClick()' class='width80' value='<bean:message key="button.add"/>'/>
                        </div>
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
            </table>
          </tab:tabPane>

        </tab:tabContainer>
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

</ctrl:form>

<script language="JScript" type="text/javascript">
  var buttonAddUser = document.getElementById("buttonAddUser");
  buttonAddUser.disabled = ${ContactPerson.addUserReadOnly};

  <logic:equal name="ContactPerson" property="fillActivePanel" value="true">
    org.ditchnet.jsp.TabUtils.selectPanelById('${ContactPerson.activePanelName}');
  </logic:equal>

  function addUserClick()
  {
    if (!${ContactPerson.adminRole})
    {
      if (!isUserAgree('<bean:message key="msg.contact_person.askUserForAddToList"/>', true, 400,100))
      {
        return;
      }
    }

    submitDispatchForm("addRowInUserGrid");
  }
</script>