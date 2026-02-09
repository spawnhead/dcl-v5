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

                    <td><bean:message key="NomenclatureProduceCustomCodeHistory.code"/></td>
                    <td><ctrl:serverList property="customCode.code" idName="customCode.id" action="/CustomCodesListAction"
                                         selectOnly="true" filter="filter"/></td>
                    <tr>
                        <td><bean:message key="NomenclatureProduceCustomCodeHistory.date"/></td>
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
                            <ctrl:ubutton type="submit" styleClass="width80" action="/NomenclatureProduceCustomCodeFromHistoryAction.do?dispatch=save">
                                <bean:message key="button.save"/>
                            </ctrl:ubutton>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>

</ctrl:form>
