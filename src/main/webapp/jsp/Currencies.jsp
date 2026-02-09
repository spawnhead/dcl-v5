<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>

<ctrl:form styleId="striped-form">


    <table width="230px" align="center">
        <tr>
            <td>


                <table>
                    <tr>
                        <td>
                            <ctrl:date property="courseDate" style="width:230px;" afterSelect='enterDate'/>
                        </td>
                        <td>
                            <ctrl:ubutton styleId="receiveFromBank" type="submit" dispatch="receiveFromBankCourses" styleClass="width200">
                                <bean:message key="button.receiveFromBankCourses"/>
                            </ctrl:ubutton>
                        </td>
                    </tr>
                </table>

                <div class="gridBack">
                    <grid:table property="currenciesGrid" width="500px" key="cur_id" scrollableGrid="true" height="expression(document.body.clientHeight-275)" autoLockName="Currencies">
                        <grid:column align="center"><jsp:attribute name="title"><bean:message key="Currencies.cur_name"/></jsp:attribute></grid:column>
                        <grid:column width="100px" align="center"><jsp:attribute name="title"><bean:message key="Currencies.cur_no_round"/></jsp:attribute></grid:column>
                        <grid:column width="100px" title=""/>
                        <grid:column width="35px" title=""/>
                        <grid:row>
                            <grid:colCustom property="cur_name"/>
                            <grid:colCheckbox width="100px" align="center" property="cur_no_round" readonlyCheckerId="alwaysReadonly"/>
                            <grid:colLink width="100px" align="center" action="/CurrencyRatesAction.do?dispatch=execute" type="link" scriptUrl="cur_id=${record.cur_id}&cur_name=${record.cur_name}">
                                <bean:message key="Currencies.rates"/>
                            </grid:colLink>
                            <grid:colEdit width="19px" action="/CurrencyAction.do?dispatch=edit" type="link" tooltip="tooltip.currencies.edit"/>
                        </grid:row>
                    </grid:table>
                </div>

                <div class=gridBottom>
                    <table>
                        <tr>
                            <td>
                                <ctrl:ubutton type="link" action="/CurrencyAction.do?dispatch=input" styleClass="width80">
                                    <bean:message key="button.create"/>
                                </ctrl:ubutton>
                            </td>
                        </tr>
                    </table>
                </div>



            </td>
        </tr>
    </table>

</ctrl:form>



<script language="JScript" type="text/javascript">
    var date = document.getElementById("courseDate");
    var receiveFromBank = document.getElementById("receiveFromBank");

    enterDate();

    function enterDate()
    {
        receiveFromBank.disabled = ( date.value == '' );
    }

    function showMsg()
    {
        <logic:equal value="true" name="Currencies" property="showConfirmMsg">
        if (isUserAgree('${Currencies.message}', true, 600, 160))
        {
            submitDispatchForm("postReceiveFromBankCourses");
        }
        </logic:equal>

        <logic:equal value="true" name="Currencies" property="showOkMsg">
        alert('<bean:message key="msg.currencies.rates_loaded"/>');
        </logic:equal>
    }
    initFunctions.push(showMsg);
</script>
