<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>
<ctrl:form >
  <ctrl:hidden property="rtn_id"/>
  <table class=formBack align="center">
    <tr>
      <td >
        <table cellspacing="2"  >
          <tr>
            <td><bean:message key="RateNDS.rtn_date_from"/></td>
            <td><ctrl:date property="rtn_date_from_formatted" style="width:230px;"/></td>
          </tr>
          <tr>
            <td><bean:message key="RateNDS.rtn_percent"/></td>
            <td><ctrl:text property="rtn_percent_formatted" style="width:230px;"/></td>
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
