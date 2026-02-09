<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>

<ctrl:form>
  <ctrl:hidden property="lpc_percent_formatted"/>
  <ctrl:hidden property="lpc_summ"/>
  <table class=formBack align="center"><tr><td >
  <table cellspacing="2"  >
    <tr>
      <td><bean:message key="ProduceCostCustom.lpc_percent"/></td>
      <td><ctrl:write name="ProduceCostCustom" property="lpc_percent_formatted"/></td>
    </tr>
    <tr>
      <td><bean:message key="ProduceCostCustom.lpc_summ"/></td>
      <td><ctrl:write name="ProduceCostCustom" property="lpc_summ"/></td>
    </tr>
    <tr>
      <td><bean:message key="ProduceCostCustom.lpc_summ_allocation"/></td>
      <td><ctrl:text property="lpc_summ_allocation"/></td>
    </tr>
    <tr>
      <td colspan="2" align="right" class=formSpace>
        &nbsp;
      </td>
    </tr>
    <tr>
      <td colspan="2" align="right">
        <ctrl:ubutton type="link" dispatch="back" styleClass="width80" readonly="false">
          <bean:message key="button.cancel"/>
        </ctrl:ubutton>
        <ctrl:ubutton type="submit" dispatch="process" styleClass="width80" readonly="false">
          <bean:message key="button.save"/>
        </ctrl:ubutton>
      </td>
    </tr>
  </table>
  </td></tr></table>

</ctrl:form>
