<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>

<grid:table property="gridCN" key="catalogNumber.id" scrollableGrid="false"  >
	<grid:column width="1"/>
	<grid:column ><jsp:attribute name="title"><bean:message key="NomenclatureProduce.gridCN.stuffCategory"/></jsp:attribute></grid:column>
	<grid:column ><jsp:attribute name="title"><bean:message key="NomenclatureProduce.gridCN.number"/></jsp:attribute></grid:column>
	<grid:column ><jsp:attribute name="title"><bean:message key="NomenclatureProduce.gridCN.montageAdjustment"/></jsp:attribute></grid:column>
	<grid:row>
		<grid:colCheckbox  width="1%" result="catalogNumbersSelectedIds" readonlyCheckerId="grid-checker"/>
		<grid:colServerList  property="catalogNumber.stuffCategory.name" idName="catalogNumber.stuffCategory.id" result="gridCN"
												 resultProperty="catalogNumber.stuffCategory.name"  useIndexAsPK="true" action="/StuffCategoriesListAction" filter="filter"
												 readonlyCheckerId="grid-checker-for-stuffCategory"	/>
		<grid:colInput width="80%" property="catalogNumber.number" result="gridCN" resultProperty="catalogNumber.number" useIndexAsPK="true" readonlyCheckerId="grid-checker"/>
    <grid:colServerList  property="montageAdjustment.machineType" idName="montageAdjustment.id" result="gridCN"
                         scriptUrl="stf_id=$(gridCN.row(${counter-1}\).catalogNumber.stuffCategory.id)"
                         resultProperty="montageAdjustment.machineType" useIndexAsPK="true" action="/MontageAdjustmentListAction"
                         selectOnly="true" readonlyCheckerId="grid-checker-for-montageAdjustment"/>
	</grid:row>
</grid:table>
