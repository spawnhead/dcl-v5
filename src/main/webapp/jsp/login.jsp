<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>

<style type="text/css">
/* Legacy IE metrics stabilizer: keep login form visuals consistent across doc modes */
body, table, td, input, select, button {
  font-family: Tahoma, Arial, sans-serif !important;
  font-size: 11px !important;
  line-height: 13px !important;
}
/* Ensure 80px buttons/inputs remain consistent */
input.width80, .width80 {
  width: 80px;
}
</style>
<ctrl:form>
  <ctrl:hidden property="usr_id"/>
  <ctrl:hidden property="userScreenWith"/>
  <input type="hidden" name="dispatch" value="process"/>
  <table class=formBack align="center">
    <tr>
      <td>
        <table cellspacing="2">
          <tr>
            <td><bean:message key="user.usr_login"/></td>
            <td><ctrl:text property="usr_login" showHelp="false"/></td>
          </tr>
          <tr>
            <td><bean:message key="user.usr_passwd"/></td>
            <td><ctrl:password property="usr_passwd"/></td>
          </tr>
          <tr>
            <td colspan="2" align="right" class=formSpace>
              &nbsp;
            </td>
          </tr>
          <tr>
            <td colspan="2" align="right">
              <ctrl:ubutton type="submit" dispatch="process" styleClass="width80">
                <bean:message key="button.login"/>
              </ctrl:ubutton>
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
</ctrl:form>

<script language="JScript" type="text/javascript">
  (function () {
    var userScreenWith = document.getElementById("userScreenWith");
    if (!userScreenWith) {
      var arr = document.getElementsByName("userScreenWith");
      userScreenWith = arr && arr.length ? arr[0] : null;
    }
    if (userScreenWith) {
      var w = 0;
      if (document.documentElement && document.documentElement.clientWidth) {
        w = document.documentElement.clientWidth;
      } else if (document.body && document.body.clientWidth) {
        w = document.body.clientWidth;
      } else if (typeof window.innerWidth === "number") {
        w = window.innerWidth;
      }
      userScreenWith.value = w;
    }
  })();
</script>