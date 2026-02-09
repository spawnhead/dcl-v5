<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>

<script type="text/javascript" src='<ctrl:rewrite page="/includes/dFilter.js"/>' ></script>

<ctrl:form readonly="${TimeboardWork.formReadOnly}">
  <ctrl:hidden property="tmb_id"/>
  <ctrl:hidden property="tbw_id"/>
  <ctrl:hidden property="number"/>
  <ctrl:hidden property="crq_id"/>
  <ctrl:hidden property="tmb_date"/>
  <table class=formBack align="center">
    <tr>
      <td >
        <table cellspacing="2">
          <tr>
            <td><bean:message key="TimeboardWork.tbw_date"/></td>
            <td><ctrl:date property="tbw_date" style="width:330px;"/></td>
          </tr>
          <tr>
            <td><bean:message key="TimeboardWork.tbw_from"/></td>
            <td>
              <table border="0" cellSpacing="0" cellPadding="0">
                <tr>
                  <td><ctrl:text property="tbw_from" style="width:40px" onkeydown="javascript:return dFilter (event.keyCode, this, '##:##');"/></td>
                  <td>&nbsp;&nbsp;<bean:message key="TimeboardWork.tbw_to"/>&nbsp;&nbsp;</td>
                  <td><ctrl:text property="tbw_to" style="width:40px" onkeydown="javascript:return dFilter (event.keyCode, this, '##:##');"/></td>
                </tr>
              </table>
            </td>
          </tr>
          <tr>
            <td><bean:message key="TimeboardWork.tbw_hours_update"/></td>
            <td><ctrl:text property="tbw_hours_update_formatted" readonly="${TimeboardWork.hoursUpdateReadOnly}"/></td>
          </tr>
          <tr>
            <td><bean:message key="TimeboardWork.tbw_comment"/></td>
            <td><ctrl:textarea property="tbw_comment" style="width:350px;height:100px;"/></td>
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
              <ctrl:ubutton type="submit" dispatch="process" styleClass="width80" readonly="${TimeboardWork.saveReadOnly}">
                <bean:message key="button.save"/>
              </ctrl:ubutton>
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
</ctrl:form>
