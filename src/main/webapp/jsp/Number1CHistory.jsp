<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>

<h2>
    <bean:message key="Number1CHistory.title"/>&nbsp;<ctrl:write name="Number1CHistory" property="number_description"/>
</h2>

<ctrl:form styleId="striped-form">
    <div class="gridBackForHistory">
        <grid:table property="grid" key="id" scrollableGrid="true" height="expression(document.body.clientHeight-235)">
            <grid:column align="center">
                <jsp:attribute name="title"><bean:message key="Number1CHistory.number"/></jsp:attribute>
            </grid:column>
            <grid:column align="center">
                <jsp:attribute name="title"><bean:message key="Number1CHistory.date"/></jsp:attribute>
            </grid:column>
            <grid:column title=""/>
            <grid:row>
                <grid:colCustom width="85%" property="number_1c"/>
                <grid:colCustom width="30%" property="date_created"/>
                <grid:colEdit width="1" type="link" action="/Number1CFromHistoryAction.do?dispatch=edit"/>
            </grid:row>
        </grid:table>
    </div>
    <div class=gridBottomForHistory>
        <ctrl:ubutton type="link" action="NomenclatureProduceAction.do?dispatch=edit&id=${Number1CHistory.prd_id}"
                      styleClass="width80">
            <bean:message key="button.back"/>
        </ctrl:ubutton>
        <ctrl:ubutton type="link" styleClass="width80" action="/Number1CFromHistoryAction.do?dispatch=create">
            <bean:message key="button.add"/>
        </ctrl:ubutton>
    </div>


</ctrl:form>
