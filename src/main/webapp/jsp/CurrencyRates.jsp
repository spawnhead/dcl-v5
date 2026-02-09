<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>

<ctrl:form>


    <table width="250px" align="center">
        <tr>
            <td>


                <h2>
                    <bean:message key="CurrencyRates.title"/>&nbsp;<ctrl:write name="CurrencyRates" property="cur_name"/>
                </h2>

                <div class="gridBack">
                    <grid:table property="ratesGrid" width="250px" key="crt_id" scrollableGrid="true" height="expression(document.body.clientHeight-235)" autoLockName="CurrencyRates">
                        <grid:column align="center"><jsp:attribute name="title"><bean:message key="CurrencyRates.crt_date"/></jsp:attribute></grid:column>
                        <grid:column align="center"><jsp:attribute name="title"><bean:message key="CurrencyRates.crt_rate"/></jsp:attribute></grid:column>
                        <grid:column title=""/>
                        <grid:row>
                            <grid:colCustom property="crt_date_formatted"/>
                            <grid:colCustom width="20%" align="right" property="crtRateFormatted"/>
                            <grid:colEdit width="1%" action="/CurrencyRateAction.do?dispatch=edit" type="link" tooltip="tooltip.currencyRates.edit" scriptUrl="cur_id=${CurrencyRates.cur_id}&cur_name=${CurrencyRates.cur_name}"/>
                        </grid:row>
                    </grid:table>
                </div>

                <div class=gridBottom>
                    <ctrl:ubutton type="link" action="/CurrenciesAction.do?dispatch=execute" styleClass="width80">
                        <bean:message key="button.back"/>
                    </ctrl:ubutton>
                    <ctrl:ubutton type="link" action="/CurrencyRateAction.do?dispatch=input" styleClass="width80" scriptUrl="cur_id=${CurrencyRates.cur_id}&cur_name=${CurrencyRates.cur_name}">
                        <bean:message key="button.create"/>
                    </ctrl:ubutton>
                </div>


            </td>
        </tr>
    </table>


</ctrl:form>
