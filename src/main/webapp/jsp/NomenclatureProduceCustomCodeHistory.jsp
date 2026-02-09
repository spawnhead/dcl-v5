<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>

<h2>
    <bean:message key="NomenclatureProduceCustomCodeHistory.title"/>
    <br/>
    <ctrl:write name="NomenclatureProduceCustomCodeHistory" property="produceDescription"/>
</h2>

<ctrl:form styleId="striped-form">
    <div class="gridBackForHistory">
        <grid:table property="grid" key="id" scrollableGrid="true" height="expression(document.body.clientHeight-235)">
            <grid:column align="center">
                <jsp:attribute name="title"><bean:message key="NomenclatureProduceCustomCodeHistory.code"/></jsp:attribute>
            </grid:column>
            <grid:column align="center">
                <jsp:attribute name="title"><bean:message key="NomenclatureProduceCustomCodeHistory.date"/></jsp:attribute>
            </grid:column>
            <grid:column title=""/>
            <grid:row>
                <grid:colCustom width="85%" property="cus_code"/>
                <grid:colCustom width="30%" property="date_created"/>
                <grid:colEdit width="1" type="link" action="/NomenclatureProduceCustomCodeFromHistoryAction.do?dispatch=edit"/>
            </grid:row>
        </grid:table>
    </div>
    <div class=gridBottomForHistory>
        <ctrl:ubutton type="link" action="NomenclatureProduceAction.do?dispatch=edit&id=${NomenclatureProduceCustomCodeHistory.prd_id}" styleClass="width80">
            <bean:message key="button.back"/>
        </ctrl:ubutton>
        <ctrl:ubutton type="link" styleClass="width80" action="/NomenclatureProduceCustomCodeFromHistoryAction.do?dispatch=show">
            <bean:message key="button.add"/>
        </ctrl:ubutton>
    </div>


</ctrl:form>
