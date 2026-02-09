<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>

<ctrl:form>
  <table class=formBackTop align="center" width="99%">
    <tr>
      <td>
        <table cellspacing="0" cellpadding="0" width="100%">
          <tr>
            <td width="130px">
              <bean:message key="Office.manager"/>
            </td>
            <logic:equal name="Office" property="showForChiefDep" value="true">
              <td>
                <ctrl:serverList property="user.userFullName" idName="user.usr_id" action="/UsersListAction"
                                 styleClass="filter-long" filter="filter"  callback="loadMessages"
                                 scriptUrl="dep_id=${Office.department.id}"/>
              </td>
            </logic:equal>
            <logic:notEqual name="Office" property="showForChiefDep" value="true">
              <td>
                <ctrl:serverList property="user.userFullName" idName="user.usr_id" action="/UsersListAction"
                                 styleClass="filter-long" filter="filter"  callback="loadMessages"
                                 readonly="${Office.readOnlyForManager}"/>
              </td>
            </logic:notEqual>
          </tr>
        </table>
      </td>
    </tr>

    <tr>
      <td>
        <table cellspacing="0" cellpadding="0" width="100%">
          <tr>
            <td id="paymentMessages">
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
</ctrl:form>

<script language="JScript" type="text/javascript">
  function loadMessagesForUser(userId)
  {
    doAjax({
      synchronous:false,
      url:'<ctrl:rewrite action="/OfficeAction" dispatch="ajaxMessagesGrid"/>',
      params:{'userId':userId},
      update:'paymentMessages'
    });
  }

  function removeMessages(uniqueId)
  {
    var userId = document.getElementById("user.usr_id").value;
    doAjax({
      synchronous:true,
      url:'<ctrl:rewrite action="/OfficeAction" dispatch="ajaxRemoveMessages"/>',
      params:{'uniqueId':uniqueId, 'userId':userId},
      update:'paymentMessages'
    });
  }

  function loadMessages()
  {
    loadMessagesForUser(document.getElementById("user.usr_id").value);
    setTimeout("loadMessages()", ${Office.refreshTimeout});
  }

  function loadMessagesByTime()
  {
    setTimeout("loadMessages()", ${Office.refreshTimeout});
  }

  initFunctions.push(loadMessages);
  initFunctions.push(loadMessagesByTime);
</script>
