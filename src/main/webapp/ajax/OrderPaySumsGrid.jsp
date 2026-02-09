<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>

<table id="OrderPaySumsGrid" width="100%" style="background-color:#eeeeee" border="0" cellpadding="0"
       cellspacing="0">
  <logic:iterate id="valSums" indexId="idSum" name="Order" property="orderPaySums">
    <tr>
      <td width="130px">
        &nbsp;&nbsp;&nbsp;&nbsp;
        <ctrl:text property="orderPaySums[${idSum}].ops_sum_formatted" style="width:80px;text-align:right;"
                     showHelp="false" onchange="return sumPayChanged(this)"/>
      </td>
      <td width="130px">
        <ctrl:date property="orderPaySums[${idSum}].ops_date_formatted" style="width:120px;" showHelp="false" onchange="return checkPaySumsGraphic()" afterSelect="checkPaySumsGraphicDate"/>
      </td>
      <td>
        <logic:equal value="0" name="idSum">
          <ctrl:ubutton type="script" action="/Order.fakeForAdd" styleClass="width165"
                        onclick="addToPaymSumGrid()"
                        style="width:19px;background-color:#eeeeee;border:0px;font-weight:bold;color:green;font-size:14px"
                        showWait="false">
            <bean:message key="button.plus"/>
          </ctrl:ubutton>
        </logic:equal>
        <logic:notEqual value="0" name="idSum">
          <ctrl:ubutton type="script" action="/Order.fakeForDelete" styleClass="width165"
                        onclick="removeFromPaySumGrid(${idSum})"
                        style="width:19px;background-color:#eeeeee;border:0px;font-weight:bold;color:red;font-size:16px"
                        showWait="false">
            <bean:message key="button.minus"/>
          </ctrl:ubutton>

        </logic:notEqual>
      </td>
    </tr>
  </logic:iterate>
</table>
