<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>

<grid:table property="grid" key="id" scrollableGrid="true" height="expression(document.body.clientHeight-345)" >
	<grid:column width="1%" hideOnSelectMode="true"	><jsp:attribute name="title"><input type="checkbox" class="grid-header-checkbox" onclick="changeGridSelection(this)" ></jsp:attribute></grid:column>
	<logic:equal name="Nomenclature" property="currentUser.admin" value="true" >
		<grid:column width="1%" hideOnSelectMode="true"	><jsp:attribute name="title"><img src="images/not-double.GIF" ></jsp:attribute></grid:column>
	</logic:equal>
	<grid:column align="center"><jsp:attribute name="title"><bean:message key="Nomenclature.grid.name"/></jsp:attribute></grid:column>
	<grid:column width="10%" align="center"><jsp:attribute name="title"><bean:message key="Nomenclature.grid.type"/></jsp:attribute></grid:column>
	<grid:column width="17%" align="center"><jsp:attribute name="title"><bean:message key="Nomenclature.grid.params"/></jsp:attribute></grid:column>
	<grid:column width="17%" align="center"><jsp:attribute name="title"><bean:message key="Nomenclature.grid.add-params"/></jsp:attribute></grid:column>
	<grid:column width="10%" align="center"><jsp:attribute name="title"><bean:message key="Nomenclature.grid.productList"/></jsp:attribute></grid:column>
	<grid:column width="10%" align="center"><jsp:attribute name="title"><bean:message key="Nomenclature.grid.numberList"/></jsp:attribute></grid:column>
	<grid:column width="10%" align="center"><jsp:attribute name="title"><bean:message key="Nomenclature.grid.unit"/></jsp:attribute></grid:column>
	<grid:column width="1%"/>
	<grid:column width="1%"/>
	<grid:column width="1%"/>
	<grid:column width="1%"/>
	<grid:row enableSelection="isEnableProduceSelection" selectionCallback="mergeProduces" styleClassCheckerId="checkBlocked">
		<grid:colCheckbox  width="1%" result="producesSelectedIds" style="vertical-align:top;" styleClass="first-column"/>
		<logic:equal name="Nomenclature" property="currentUser.admin" value="true" >
			<grid:colCheckbox width="1%" property="notCheckDouble" style="vertical-align:top;" type="script"  action="/NomenclatureAction" dispatch="setNotCheckDouble" onclick="return setNotCheckDouble(${record.id},this);"/>
		</logic:equal>
		<grid:colCustom property="name" style="vertical-align:top;"/>
		<grid:colCustom width="10%" style="vertical-align:top;" property="type"/>
		<grid:colCustom width="17%" style="vertical-align:top;" property="params"/>
		<grid:colCustom width="17%" style="vertical-align:top;" property="addParams"/>
		<grid:colCustom width="10%" property="productList"/>
		<grid:colCustom width="10%" property="numberList"/>
		<grid:colCustom width="5%" property="unitName"/>
		<grid:colEdit width="1%" action="/NomenclatureProduceAction.do?dispatch=edit" type="link"
                  tooltip="tooltip.Nomenclature.edit" style="vertical-align:top;" readOnlyOnSelectMode="false"
                  readonlyCheckerId="editChecker"/>
		<grid:colImage width="1%" action="/NomenclatureProduceAction.do?dispatch=copy"
                   type="link" tooltip="tooltip.Nomenclature.copy" image="images/copy.gif" style="vertical-align:top;" scriptUrl="cat_id=$(cat_id)"
                   styleClass="grid-image-without-border" readOnlyOnSelectMode="false" askUser="askCopy" showWait="false"/>
		<grid:colImage width="1%" action="/NomenctlatureAttachmentsAction.do?referencedID=${record.id}" type="link"
                   tooltip="tooltip.Nomenclature.attachments" readOnlyOnSelectMode="false"
									 image='${record.attachmentsCount eq "0"?"images/download.gif":"images/download_yes.gif"}'
                   style="vertical-align:top;" styleClass="grid-image-without-border"/>
    <grid:colImage width="1%" enableOnClickAction="false" type="link" style="vertical-align:top;" showCheckerId="showCommentChecker"
                   styleClass="grid-image-without-border" image="images/comments.gif" tooltipProperty="comment"/>
	</grid:row>
</grid:table>

<script type="text/javascript" language="JavaScript">
	produceCount = <bean:write name="Nomenclature" property="produceCount"/>;
</script>
