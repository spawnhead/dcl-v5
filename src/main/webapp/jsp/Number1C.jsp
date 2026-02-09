<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>
<ctrl:form>

    <table class=formBack align="center">
        <tr>
            <td>
                <table cellspacing="2">

                    <tr>
                        <td><bean:message key="Product.name"/></td>
                        <td><ctrl:text property="productName" readonly="true"/></td>
                    </tr>

                    <tr>
                        <td><bean:message key="Number1CHistory.number"/></td>
                        <td><ctrl:text property="number1C"/></td>
                    </tr>
                    <tr>
                        <td><bean:message key="Number1CHistory.date"/></td>
                        <td><ctrl:date property="dateCreated" style="width:230px;"/></td>
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
                            <ctrl:ubutton type="submit" styleClass="width80" action="/Number1CFromHistoryAction.do?dispatch=save">
                                <bean:message key="button.save"/>
                            </ctrl:ubutton>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>

</ctrl:form>
