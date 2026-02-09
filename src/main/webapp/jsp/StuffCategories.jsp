<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>

<div id="mergingStuffCategoryModeMessage" style="position:absolute;	visibility:hidden; border:1px solid black; background : red; width:190px;height:20px">
    <bean:message key="StuffCategories.merge.text"/>
    <br>
</div>

<ctrl:form styleId="striped-form">


    <table width="40%" align="center">
        <tr>
            <td>



                <ctrl:hidden property="mergeTargetId"/>

                <div class="gridBack">
                    <grid:table property="grid" key="stf_id" scrollableGrid="true" height="expression(document.body.clientHeight-235)" autoLockName="StuffCategories">
                        <logic:equal name="StuffCategories" property="admin" value="1">
                            <grid:column title=""/>
                        </logic:equal>
                        <grid:column align="center"><jsp:attribute name="title"><bean:message key="StuffCategories.stf_name"/></jsp:attribute></grid:column>
                        <grid:column width="30%" align="center"><jsp:attribute name="title"><bean:message key="StuffCategories.stf_show_in_montage"/></jsp:attribute></grid:column>
                        <grid:column title=""/>
                        <logic:equal name="StuffCategories" property="admin" value="1">
                            <grid:column title=""/>
                        </logic:equal>
                        <grid:row enableSelection="isEnableStuffCategoriesSelection" selectionCallback="mergeStuffCategories">
                            <logic:equal name="StuffCategories" property="admin" value="1">
                                <grid:colCheckbox width="1%" result="stuffCategoriesSelectedIds" style="vertical-align:top;"/>
                            </logic:equal>
                            <grid:colCustom property="stf_name"/>
                            <grid:colCheckbox width="30%" align="center" property="stf_show_in_montage" type="submit" dispatch="showInMontage" scriptUrl="showInMontage=${record.stf_show_in_montage}" readonlyCheckerId="checkBoxReadOnlyChecker"/>
                            <grid:colEdit width="1" action="/StuffCategoryAction.do?dispatch=edit" type="link" tooltip="tooltip.StuffCategories.edit"/>
                            <logic:equal name="StuffCategories" property="admin" value="1">
                                <grid:colDelete width="1" action="/StuffCategoriesAction.do?dispatch=delete" type="link" tooltip="tooltip.StuffCategories.delete" showCheckerId="show-delete-checker"/>
                            </logic:equal>
                        </grid:row>
                    </grid:table>
                </div>

                <div class=gridBottom>
                    <logic:equal name="StuffCategories" property="admin" value="1">
                        <ctrl:ubutton type="script" dispatch="fakeDispatchForMerging" styleClass="width120"
                                      onclick="return toggleMergingStuffCategories();" styleId="mergeStuffCategoriesBtn" showWait="false">
                            <bean:message key="button.merge"/>
                        </ctrl:ubutton>
                    </logic:equal>
                    <ctrl:ubutton type="link" action="/StuffCategoryAction.do?dispatch=input" styleClass="width80">
                        <bean:message key="button.create"/>
                    </ctrl:ubutton>
                </div>


            </td>
        </tr>
    </table>



</ctrl:form>

<script type="text/javascript" language="JavaScript">
    var mergingStuffCategoriesMode = false;
    function isEnableStuffCategoriesSelection()
    {
        return mergingStuffCategoriesMode;
    }

    function toggleMergingStuffCategories()
    {
        if (mergingStuffCategoriesMode)
        {
            mergingStuffCategoriesMode = false;
            mergingStuffCategoryModeMessage.style.visibility = 'hidden';
            document.getElementById('mergeStuffCategoriesBtn').value = '<bean:message key="button.merge"/>';
        }
        else
        {
            mergingStuffCategoriesMode = true;
            mergingStuffCategoryModeMessage.style.visibility = 'visible';
            document.getElementById('mergeStuffCategoriesBtn').value = '<bean:message key="button.dont-merge"/>';
        }
    }
    function mergeStuffCategories(obj, id)
    {
        var evt = window.event || null;
        var target = evt ? (evt.target || evt.srcElement) : null;
        if (target && target.type == 'checkbox')
        {
            return;
        }
        if (obj.children[0].children[0].checked)
        {
            return;
        }
        toggleMergingStuffCategories();
        $('mergeTargetId').value = id;
        submitDispatchForm("mergeStuffCategories");
    }
    function handlerMM(e)
    {
        if (mergingStuffCategoriesMode)
        {
            mergingStuffCategoryModeMessage.style.left = (e) ? e.pageX : document.body.scrollLeft + event.clientX + 10;
            mergingStuffCategoryModeMessage.style.top = (e) ? e.pageY : document.body.scrollTop + event.clientY + 20;
        }
    }
    document.onmousemove = handlerMM;
</script>