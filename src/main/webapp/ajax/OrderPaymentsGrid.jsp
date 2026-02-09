<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>

<table id="OrderPaymentsGrid" width="100%" style="background-color:#eeeeee" border="0" cellpadding="0"
       cellspacing="0">
  <logic:iterate id="val" indexId="id" name="Order" property="orderPayments">
    <tr>
      <logic:equal value="1" name="Order" property="orderPaymentsCount">
        <td width="100px">
          <ctrl:text property="orderPayments[${id}].orp_percent_formatted" style="width:80px;text-align:right;" readonly="true"
                       showHelp="false"/> %
        </td>
        <td width="120px">
          <ctrl:text property="orderPayments[${id}].orp_sum_formatted" style="width:80px;text-align:right;" readonly="true"
                       showHelp="false"/> <ctrl:write name="Order" property="orderPayments[${id}].currencyName"/>
        </td>
      </logic:equal>
      <logic:notEqual value="1" name="Order" property="orderPaymentsCount">
        <td width="100px">
          <ctrl:text property="orderPayments[${id}].orp_percent_formatted" style="width:80px;text-align:right;"
                     showHelp="false" onchange="return percentChanged(this)"/> %
        </td>
        <td width="120px">
          <ctrl:text property="orderPayments[${id}].orp_sum_formatted" style="width:80px;text-align:right;"
                       showHelp="false" onchange="return sumOrderPaymentChanged(this)"/> <ctrl:write name="Order" property="orderPayments[${id}].currencyName"/>
        </td>
      </logic:notEqual>
      <td width="130px">
        <ctrl:date property="orderPayments[${id}].orp_date_formatted" style="width:120px;" showHelp="false" onchange="return checkPaymentsGraphic()" afterSelect="checkPaymentsGraphicDate"/>
      </td>
      <td id="description${id}">
        &nbsp;<ctrl:write name="Order" property="orderPayments[${id}].description"/>
      </td>
      <td>
        <logic:equal value="0" name="id">
          <ctrl:ubutton type="script" action="/Order.fakeForAdd" styleClass="width165"
                        onclick="addToPaymentGrid()"
                        style="width:19px;background-color:#eeeeee;border:0px;font-weight:bold;color:green;font-size:14px"
                        showWait="false">
            <bean:message key="button.plus"/>
          </ctrl:ubutton>
        </logic:equal>
        <logic:notEqual value="0" name="id">
          <ctrl:ubutton type="script" action="/Order.fakeForDelete" styleClass="width165"
                        onclick="removeFromPaymentGrid(${id})"
                        style="width:19px;background-color:#eeeeee;border:0px;font-weight:bold;color:red;font-size:16px"
                        showWait="false">
            <bean:message key="button.minus"/>
          </ctrl:ubutton>

        </logic:notEqual>
      </td>
    </tr>
  </logic:iterate>
</table>
