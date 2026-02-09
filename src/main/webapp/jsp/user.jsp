<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>

<div id='for-insert'></div>

<ctrl:form>
  <ctrl:hidden property="usr_id"/>
  <ctrl:hidden property="usr_block"/>
  <ctrl:hidden property="usr_no_login_saved"/>
  <table class=formBack align="center">
    <tr>
      <td>
        <table cellspacing="2">
          <tr>
            <td><bean:message key="user.usr_code"/></td>
            <td><ctrl:text property="usr_code"/></td>
          </tr>
          <tr>
            <td><bean:message key="user.usr_login"/></td>
            <td><ctrl:text property="usr_login"/></td>
          </tr>
          <tr>
            <td><bean:message key="user.usr_passwd"/></td>
            <td>&nbsp;&nbsp;&nbsp;<ctrl:password property="usr_passwd"/></td>
          </tr>
          <tr>
            <td><bean:message key="user.usr_passwd2"/></td>
            <td>&nbsp;&nbsp;&nbsp;<ctrl:password property="usr_passwd2"/></td>
          </tr>
          <tr>
            <td><bean:message key="user.usr_department"/></td>
            <td><ctrl:serverList property="department.name" idName="department.id" action="/DepartmentsListAction"
                                 selectOnly="true"/></td>
          </tr>
          <tr>
            <td><bean:message key="user.usr_phone"/></td>
            <td><ctrl:text property="usr_phone"/></td>
          </tr>
          <tr>
            <td><bean:message key="user.usr_fax"/></td>
            <td><ctrl:text property="usr_fax"/></td>
          </tr>
          <tr>
            <td><bean:message key="user.facsimile"/></td>
            <td>
              <table cellpadding="0" cellspacing="0">
                <tr>
                  <td><ctrl:help htmlName="userFacsimile"/></td>
                  <td><a href="" onclick="return downloadAttachment(${user.idx});">${user.originalFileName}</a></td>
                  <td>
                    <div>
                      <ctrl:ubutton type="submit" dispatch="deferredAttach" styleClass="width80">
                        <bean:message key="button.attach"/>
                      </ctrl:ubutton>
                    </div>
                  </td>
                </tr>
              </table>
            </td>
          </tr>
          <tr>
            <td><bean:message key="user.usr_email"/></td>
            <td><ctrl:text property="usr_email"/></td>
          </tr>
          <tr>
            <td><bean:message key="user.usr_chief_dep"/></td>
            <td><ctrl:checkbox property="usr_chief_dep" styleClass="checkbox" value="1"/></td>
          </tr>
          <tr>
            <td colspan="10">
              <div class="gridBackNarrow" id="namesGrid">
                <grid:table property="usergrid" key="lng_id">
                  <grid:column align="center">
                    <jsp:attribute name="title"><bean:message key="UserLanguages.lng_name"/></jsp:attribute>
                  </grid:column>
                  <grid:column align="center">
                    <jsp:attribute name="title"><bean:message key="UserLanguages.usr_surname"/></jsp:attribute>
                  </grid:column>
                  <grid:column align="center">
                    <jsp:attribute name="title"><bean:message key="UserLanguages.usr_name"/></jsp:attribute>
                  </grid:column>
                  <grid:column align="center">
                    <jsp:attribute name="title"><bean:message key="UserLanguages.usr_position"/></jsp:attribute>
                  </grid:column>
                  <grid:row>
                    <grid:colInput property="lng_name" result="usergrid" resultProperty="lng_name" useIndexAsPK="true"
                                   readonlyCheckerId="readOnlyChecker"/>
                    <grid:colInput property="usr_surname" result="usergrid" resultProperty="usr_surname"
                                   useIndexAsPK="true"/>
                    <grid:colInput property="usr_name" result="usergrid" resultProperty="usr_name" useIndexAsPK="true"/>
                    <grid:colInput property="usr_position" result="usergrid" resultProperty="usr_position"
                                   useIndexAsPK="true"/>
                  </grid:row>
                </grid:table>
              </div>
            </td>
          </tr>
          <tr>
            <td><bean:message key="user.usr_respons_person"/></td>
            <td>
              <ctrl:checkbox property="usr_respons_person" styleClass="checkbox" value="1"/>&nbsp;&nbsp;&nbsp;&nbsp;
              <bean:message key="user.usr_no_login"/>&nbsp;
              <ctrl:checkbox property="usr_no_login" styleClass="checkbox" value="1"/>
            </td>
          </tr>
          <tr>
            <td colspan="2">
              <bean:message key="user.usrEnter"/>&nbsp;
              <bean:message key="user.usr_local_entry"/>&nbsp;
              <ctrl:checkbox property="usr_local_entry" styleClass="checkbox" value="1"/>&nbsp;&nbsp;
              <bean:message key="user.usr_internet_entry"/>&nbsp;
              <ctrl:checkbox property="usr_internet_entry" styleClass="checkbox" value="1"/>
            </td>
          </tr>
          <tr>
            <td colspan="2" align="right" class=formSpace>
              &nbsp;
            </td>
          </tr>
          <tr>
            <td colspan="2" align="right">
              <ctrl:ubutton type="submit" dispatch="back" styleClass="width80" readonly="false">
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

<script language="JScript" type="text/javascript">
  function downloadAttachment(id)
  {
	  document.getElementById('for-insert').innerHTML='<iframe src=\'<html:rewrite action="/UserAction.do?dispatch=downloadAttachment"/>&attachmentId='+id+'\' style=\'display:none\' />';
		return false;
	}

</script>