<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>

<ctrl:form styleId="striped-form">

    <table width="350px" align="center">
        <tr>
            <td>


                <div class="gridBack">
                    <grid:table property="grid" key="unt_id" scrollableGrid="true" height="expression(document.body.clientHeight-235)" autoLockName="Units">
                        <grid:column align="center"><jsp:attribute name="title"><bean:message key="Units.unt_name_ru"/></jsp:attribute></grid:column>
                        <grid:column align="center"><jsp:attribute name="title"><bean:message key="Units.unt_name_en"/></jsp:attribute></grid:column>
                        <grid:column align="center"><jsp:attribute name="title"><bean:message key="Units.unt_name_de"/></jsp:attribute></grid:column>
                        <grid:column align="center"><jsp:attribute name="title"><bean:message key="Units.is_acc_for_contract"/>&nbsp;<ctrl:help htmlName="UnitAcceptedForContractHeader"/></jsp:attribute></grid:column>
                        <grid:column title=""/>
                        <grid:row>
                            <grid:colCustom width="25%" property="unt_name_ru"/>
                            <grid:colCustom width="25%" property="unt_name_en"/>
                            <grid:colCustom width="25%" property="unt_name_de"/>
                            <grid:colCheckbox width="25%" property="is_acceptable_for_cpr" readonlyCheckerId="alwaysReadonly" align="center"/>
                            <grid:colEdit width="1" action="/UnitAction.do?dispatch=edit" type="link" tooltip="tooltip.Units.edit"/>
                        </grid:row>
                    </grid:table>
                </div>

                <div class=gridBottom>
                    <ctrl:ubutton type="link" action="/UnitAction.do?dispatch=create" styleClass="width80">
                        <bean:message key="button.create"/>
                    </ctrl:ubutton>
                </div>



            </td>
        </tr>
    </table>





</ctrl:form>
