<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>

<ctrl:form >
  <ctrl:hidden property="cut_id"/>
  <ctrl:hidden property="is_new_doc"/>
  <table class=formBack align="center">
    <tr>
      <td>
        <table cellspacing="2"  >
          <logic:notEqual name="Country" property="is_new_doc" value="true">
            <tr>
              <td><bean:message key="Country.create"/></td>
              <td><ctrl:write name="Country" property="usr_date_create"/>&nbsp;<ctrl:write name="Country" property="createUser.userFullName"/></td>
            </tr>
            <tr>
              <td><bean:message key="Country.edit"/></td>
              <td><ctrl:write name="Country" property="usr_date_edit"/>&nbsp;<ctrl:write name="Country" property="editUser.userFullName"/></td>
            </tr>
          </logic:notEqual>
          <tr>
            <td><bean:message key="Country.cut_name"/></td>
            <td><ctrl:text property="cut_name"/></td>
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
