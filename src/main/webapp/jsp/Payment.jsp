<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>

<div style="display:none" id="resultMsg"></div>

<ctrl:form readonly="${Payment.formReadOnly}">
<ctrl:hidden property="pay_id"/>
<ctrl:hidden property="pay_block"/>
<ctrl:hidden property="is_new_doc"/>
<ctrl:hidden property="usr_date_create"/>
<ctrl:hidden property="usr_date_edit"/>
<ctrl:hidden property="pay_summ_nr_formatted"/>
<ctrl:hidden property="createUser.usr_id"/>
<ctrl:hidden property="editUser.usr_id"/>
<ctrl:hidden property="pay_course_nbrb_date"/>
<table class=formBackTop align="center" width="99%">
    <tr>
        <td >
            <table width="100%">
                <logic:notEqual name="Payment" property="is_new_doc" value="true">
                    <tr>
                        <td>
                            <table width="100%">
                                <tr>
                                    <td width="10%"><bean:message key="Payment.create"/></td>
                                    <td width="40%"><ctrl:write name="Payment" property="usr_date_create"/> <ctrl:write name="Payment" property="createUser.userFullName"/> </td>
                                    <td align="right"><bean:message key="Payment.edit"/></td>
                                    <td width="33%">&nbsp;&nbsp;&nbsp;&nbsp;<ctrl:write name="Payment" property="usr_date_edit"/> <ctrl:write name="Payment" property="editUser.userFullName"/></td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </logic:notEqual>
                <tr>
                    <td>
                        <table width="100%">
                            <tr>
                                <td width="10%"><bean:message key="Payment.pay_date"/></td>
                                <td><ctrl:date property="pay_date" style="width:230px;" readonly="${Payment.readOnlyForManager}" afterSelect="onChangePayDate" onchange="onChangePayDate()"/></td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td>
                        <table width="100%">
                            <tr>
                                <td width="10%"><bean:message key="Payment.pay_account"/></td>
                                <td>
                                    <ctrl:text property="pay_account" style="width:230px;" readonly="${Payment.readOnlyForManager}"
                                               onchange="onChangeAccount(this);"/>
                                    &nbsp;<span style="color:red" id="ctrNamesMsg"></span>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td>
                        <table width="100%" border="0">
                            <tr>
                                <td width="10%"><bean:message key="Payment.pay_summ"/></td>
                                <td  width="498px">
                                    <ctrl:text property="pay_summ_formatted" style="width:230px;"
                                               readonly="${Payment.readOnlyForManager}" onchange="onChangeSum(this);"/>
                                </td>

                                <td width="150px"><bean:message key="Payment.pay_currency"/></td>
                                <td>
                                    <ctrl:serverList property="currency.name" idName="currency.id"
                                                     action="/CurrenciesListAction" selectOnly="true"
                                                     readonly="${Payment.readOnlyForManager}" callback="onChangeCurrency" style="width:110px;"/>
                                </td>

                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td>
                        <table width="100%" border="0">
                            <tr>
                                <td width="10%"><bean:message key="Payment.pay_course"/></td>
                                <td width="260px">
                                    <ctrl:text property="payCourseFormatted" onchange="onChangeCourse(this);"
                                               readonly="${Payment.readOnlyForManager}" style="width:230px;"/>
                                </td>
                                <td width="110px"><bean:message key="Payment.pay_summ_eur"/></td>
                                <td width="120px" id="tdPaySumEUR"><ctrl:write name="Payment" property="pay_summ_eur_formatted"/></td>

                                <td width="150px"><bean:message key="Payment.pay_course_nbrb"/><span id="payCourseNbrbDate"><ctrl:write name="Payment" property="payCourseNbrbDateFormatted"/></span></td>
                                <td width="260px">
                                    <ctrl:text property="payCourseNbrbFormatted" onchange="onChangeCourseNBRB(this);"
                                               readonly="${Payment.readOnlyForManager}" style="width:230px;"/>
                                </td>
                                <td width="130px"><bean:message key="Payment.pay_summ_eur_nbrb"/></td>
                                <td id="tdPaySumEUR_NBRB"><ctrl:write name="Payment" property="pay_summ_eur_nbrb_formatted"/></td>

                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td>
                        <table width="100%" border="0">
                            <tr>
                                <!-- Не убирать ячейки. Сдвитается за счет cellSpacing и cellPadding  -->
                                <td width="10%"></td>
                                <td width="260px"></td>
                                <td width="250px"></td>

                                <td width="132px">
                                    <input type='button' onclick='updateCoursesClick()' class='width120' value='<bean:message key="button.updateCourses"/>'/>
                                </td>
                                <td><ctrl:help htmlName="PaymentUpdateCourses"/></td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td>
                        <table width="100%" border="0">
                            <tr>
                                <td width="10%"><bean:message key="Payment.pay_comment"/></td>
                                <td><ctrl:textarea property="pay_comment" style="width:625px;height:100px;" readonly="${Payment.readOnlyForManager}"/></td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td>
                        <table width="100%" border="0">
                            <tr>
                                <td width="10%"><bean:message key="Payment.pay_contractor"/></td>
                                <td>
                                    <table border="0" cellSpacing="0" cellPadding="0">
                                        <tr>
                                            <td>
                                                <ctrl:serverList property="contractor.name" idName="contractor.id"
                                                                 action="/ContractorsListAction" callback="onChangeContractor"
                                                                 style="width:230px;" filter="filter"/>
                                            </td>
                                            <td>&nbsp;</td>
                                            <td>
                                                <ctrl:ubutton type="submit" dispatch="newContractor" styleClass="width80">
                                                    <bean:message key="button.add"/>
                                                </ctrl:ubutton>
                                            </td>
                                        </tr>
                                    </table>
                                </td>

                            </tr>
                        </table>
                    </td>
                </tr>

                <tr>
                    <td>
                        <table width="100%">
                            <tr>
                                <td width="15%"><bean:message key="Payment.pay_summ_nr"/></td>
                                <td align="left" id="tdPaySumNR"><ctrl:write name="Payment" property="pay_summ_nr_formatted"/></td>
                            </tr>
                        </table>
                    </td>
                </tr>

                <tr>
                    <td>
                        <table width="100%">
                            <tr>
                                <td width="25%"><bean:message key="Payment.table_part"/></td>
                            </tr>
                        </table>
                    </td>
                </tr>

                <tr>
                    <td id="paymentSums">
                        <script type="text/javascript" language="JScript">
                            doAjax({
                                synchronous:true,
                                url:'<ctrl:rewrite action="/PaymentAction" dispatch="ajaxPaymentSumsGrid"/>',
                                update:'paymentSums'
                            });
                        </script>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div class=gridBottom>
                            <ctrl:ubutton type="submit" styleId="newPaySum" dispatch="newPaySum" askUser="" styleClass="width165">
                                <bean:message key="button.add"/>
                            </ctrl:ubutton>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td colspan="2" align="right" class=formSpace>
                        &nbsp;
                    </td>
                </tr>
                <tr>
                    <td colspan="2" align="right">
                        <ctrl:ubutton type="submit" dispatch="back" styleClass="width80" readonly="false" askUser="">
                            <bean:message key="button.cancel"/>
                        </ctrl:ubutton>
                        <input id='buttonSave' type='button' onclick='saveClick()' class='width80' value='<bean:message key="button.save"/>'/>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>
</ctrl:form>

<script language="JScript" type="text/javascript">
    var defCurrencyForOne = '<bean:message key="Payment.def_currency_for_one"/>';
    var defaultCurrency = '<bean:message key="Payment.default_currency"/>';
    var payDate = document.getElementById("pay_date");
    var course = document.getElementById("payCourseFormatted");
    var courseNbrb = document.getElementById("payCourseNbrbFormatted");
    var courseNbrbDate = document.getElementById("pay_course_nbrb_date");
    var currencyId = document.getElementById("currency.id");
    var currencyName = document.getElementById("currency.name");
    var paySum = document.getElementById("pay_summ_formatted");
    var paySumNR = document.getElementById("pay_summ_nr_formatted");
    var contractorId = document.getElementById("contractor.id");
    var contractorName = document.getElementById("contractor.name");
    var contractorFrm = document.getElementById("contractor.nameFrm");
    var ctrNamesMsg = document.getElementById("ctrNamesMsg");
    ctrNamesMsg.innerHTML = '${Payment.contractor.ctrNamesFormatted}';
    var newPaySum = document.getElementById("newPaySum");
    var buttonSave = document.getElementById("buttonSave");
    buttonSave.disabled = ${Payment.readOnlySave};

    showPaySumButton();
    onChangeCourse(course);
    courseNbrb.disabled = currencyName.value == defaultCurrency;

    function showPaySumButton()
    {
        var paySumVal = getSumForJS(paySum.value);
        newPaySum.disabled = eval(${Payment.formReadOnly}) || contractorId.value == '' || eval(paySumVal) == 0;
    }

    function onChangeCurrency()
    {
        if ( currencyName.value == defCurrencyForOne )
        {
            course.value = '1';
            document.getElementById('tdPaySumEUR').innerHTML = paySum.value;
        }
        onChangeCourse(course);
        courseNbrb.disabled = currencyName.value == defaultCurrency;
    }

    function onChangeAccount(obj)
    {
        doAjax({
            url:'<ctrl:rewrite action="/PaymentAction" dispatch="ajaxChangeAccount"/>',
            params:{'account':obj.value},
            synchronous:true,
            update:'resultMsg',
            okCallback:function()
            {
                var resultStr = document.getElementById('resultMsg').innerHTML;
                var arrayResult = resultStr.split('|');
                if ( arrayResult[0] )
                {
                    contractorId.value = arrayResult[0];
                    contractorName.value = arrayResult[1];
                }
                else
                {
                    contractorId.value = '';
                    contractorName.value = '';
                }
                if ( arrayResult[2] )
                {
                    ctrNamesMsg.innerHTML = arrayResult[2];
                }
                else
                {
                    ctrNamesMsg.innerHTML = '';
                }

                paySum.focus();
                contractorFrm.style.display = 'none';
            }
        });

        onChangeContractor();
    }

    function onChangeSum(obj)
    {
        doAjax({
            url:'<ctrl:rewrite action="/PaymentAction" dispatch="ajaxChangeSum"/>',
            params:{'paySum':obj.value},
            synchronous:true,
            update:'resultMsg',
            okCallback:function()
            {
                var resultStr = document.getElementById('resultMsg').innerHTML;
                var arrayResult = resultStr.split('|');
                document.getElementById('tdPaySumEUR').innerHTML = arrayResult[0];
                document.getElementById('tdPaySumEUR_NBRB').innerHTML = arrayResult[1];
                document.getElementById('tdPaySumNR').innerHTML = arrayResult[2];
                paySumNR.value = arrayResult[2].trim();
            }
        });

        if ( currencyName.value == defCurrencyForOne )
        {
            course.value = '1';
            document.getElementById('tdPaySumEUR').innerHTML = paySum.value;
        }

        showPaySumButton();
    }

    function onChangePayDate()
    {
        onChangeCourse(course);
    }

    function onChangeCourse(obj)
    {
        doAjax({
            url:'<ctrl:rewrite action="/PaymentAction" dispatch="ajaxChangeCourse"/>',
            params:{'payCourse':obj.value,'currencyId':currencyId.value,'payDate':payDate.value},
            synchronous:true,
            update:'resultMsg',
            okCallback:function()
            {
                var resultStr = document.getElementById('resultMsg').innerHTML;
                var arrayResult = resultStr.split('|');
                document.getElementById('tdPaySumEUR').innerHTML = arrayResult[0];
                courseNbrb.value = arrayResult[1];
                courseNbrbDate.value = arrayResult[2];
                if (courseNbrbDate.value != '')
                {
                    document.getElementById('payCourseNbrbDate').innerHTML = '&nbsp;' +  '<bean:message key="msg.common.from_only"/>' + ' ' + courseNbrbDate.value;
                }
                else
                {
                    document.getElementById('payCourseNbrbDate').innerHTML = '';
                }
                document.getElementById('tdPaySumEUR_NBRB').innerHTML = arrayResult[3];
            }
        });
    }

    function onChangeCourseNBRB(obj)
    {
        doAjax({
            url:'<ctrl:rewrite action="/PaymentAction" dispatch="ajaxChangeCourseNBRB"/>',
            params:{'payCourse':obj.value},
            synchronous:true,
            update:'tdPaySumEUR_NBRB'
        });
    }

    function updateCoursesClick()
    {
        doAjax({
            url:'<ctrl:rewrite action="/PaymentAction" dispatch="ajaxUpdateCourses"/>',
            synchronous:false,
            okCallback:function()
            {
                alert('<bean:message key="msg.Payment.updateCourse"/>');
                onChangeCourse(course);
            }
        });
    }

    function onChangeContractor()
    {
        var params = $H(Form.serializeElements($$('#paymentSums input'),true));
        doAjax({
            url:'<ctrl:rewrite action="/PaymentAction" dispatch="ajaxChangeContractor"/>',
            params:params,
            synchronous:true,
            update:'paymentSums'
        });

        onChangeSum(paySum);
    }

    function saveClick()
    {
        var paySumNR = getSumForJS(document.getElementById('tdPaySumNR').innerHTML);
        if ( eval(paySumNR) == 0 && !isUserAgree('<bean:message key="msg.Payment.block"/>', false, 600, 150))
        {
            return;
        }

        submitDispatchForm("process");
    }
</script>
