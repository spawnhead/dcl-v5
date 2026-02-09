<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>

<ctrl:form >
  <ctrl:hidden property="crt_id"/>
  <ctrl:hidden property="cur_id"/>
  <ctrl:hidden property="cur_name"/>

  <h2>
    <bean:message key="CurrencyRate.title"/>&nbsp;<ctrl:write name="CurrencyRate" property="cur_name"/>
  </h2>

  <table class=formBack align="center">
    <tr>
      <td >
        <table cellspacing="2"  >
          <tr>
            <td><bean:message key="CurrencyRate.crt_date"/></td>
            <td><ctrl:date property="crt_date_formatted" style="width:230px;" afterSelect='enterDate'/></td>
          </tr>
          <tr>
            <td><bean:message key="CurrencyRate.crt_rate"/></td>
            <td><ctrl:text property="crtRateFormatted" style="width:230px;"/></td>
          </tr>
          <tr>
            <td colspan="2" align="right">
              <ctrl:ubutton styleId="receiveFromBank" type="submit" dispatch="receiveFromBank" styleClass="width240">
                <bean:message key="button.receiveFromBank"/>
              </ctrl:ubutton>
            </td>
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

<script language="JScript" type="text/javascript">
  var date = document.getElementById("crt_date_formatted");
  var receiveFromBank = document.getElementById("receiveFromBank");

  enterDate();

  function enterDate()
  {
    receiveFromBank.disabled = ( date.value == '' );
  }
</script>
