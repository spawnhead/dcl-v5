<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>
<ctrl:form >
  <ctrl:hidden property="cus_id"/>
  <ctrl:hidden property="cus_code_old"  />
  <ctrl:hidden property="hide_percent"  />
  <ctrl:hidden property="readonly_code"  />
  <logic:equal value="true" name="CustomCode" property="readonly_code" >
    <ctrl:hidden property="cus_code"  />
    <ctrl:hidden property="cus_description"  />
  </logic:equal>
  <logic:equal value="true" name="CustomCode" property="hide_percent" >
    <ctrl:hidden property="cus_percent" value="1,1"/>
  </logic:equal>

  <table class=formBack align="center"><tr><td >
  <table cellspacing="2" >
    <logic:notEqual name="CustomCode" property="newCus" value="true">
      <tr>
        <td><bean:message key="CustomCode.create"/></td>
        <td><ctrl:write name="CustomCode" property="usr_date_create_formatted"/>&nbsp;<ctrl:write name="CustomCode" property="createUser.userFullName"/></td>
      </tr>
      <tr>
        <td><bean:message key="CustomCode.edit"/></td>
        <td><ctrl:write name="CustomCode" property="usr_date_edit_formatted"/>&nbsp;<ctrl:write name="CustomCode" property="editUser.userFullName"/></td>
      </tr>
    </logic:notEqual>
    <logic:equal name="CustomCode" property="showCreateEditUsers" value="true">
      <tr>
        <td><bean:message key="CustomCode.create"/></td>
        <td><ctrl:write name="CustomCode" property="usr_date_create_main_formatted"/>&nbsp;<ctrl:write name="CustomCode" property="createUserMain.userFullName"/></td>
      </tr>
      <tr>
        <td><bean:message key="CustomCode.edit"/></td>
        <td><ctrl:write name="CustomCode" property="usr_date_edit_main_formatted"/>&nbsp;<ctrl:write name="CustomCode" property="editUserMain.userFullName"/></td>
      </tr>
    </logic:equal>
    <tr>
      <td><bean:message key="CustomCode.cus_code"/></td>
      <td><ctrl:text property="cus_code" disabled="${CustomCode.readonly_code}"/></td>
    </tr>
    <tr>
      <td><bean:message key="CustomCode.cus_description"/></td>
      <td><ctrl:textarea property="cus_description" style="width:350px;height:155px;" disabled="${CustomCode.readonly_code}"/></td>
    </tr>
    <logic:equal value="false" name="CustomCode" property="hide_percent" >
    <tr>
      <td><bean:message key="CustomCode.cus_percent"/></td>
      <td><ctrl:text property="cus_percent"/></td>
    </tr>
    <tr>
      <td><bean:message key="CustomCode.cus_instant"/></td>
      <td><ctrl:date property="cus_instant"  style="width:230px;"/></td>
    </tr>
    </logic:equal>
    <tr>
      <td colspan="2" align="right" class=formSpace>
        &nbsp;
      </td>
    </tr>
    <tr>
      <td colspan="2" align="right">
        <ctrl:ubutton type="link" actionForward="back" styleClass="width80"  paramId="cus_code" paramName="CustomCode" paramProperty="cus_code">
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
