<%@ page import="org.apache.struts.taglib.html.Constants" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>

<grid:table property="gridCNDetails" key="id" scrollableGrid="false"  >
	<grid:column ><jsp:attribute name="title"><bean:message key="Nomenclature.gridCN.stuffCategory"/></jsp:attribute></grid:column>
	<grid:column ><jsp:attribute name="title"><bean:message key="Nomenclature.gridCN.number"/></jsp:attribute></grid:column>
	<grid:row>
		<grid:colCustom property="stuffCategory.name" 	/>
		<grid:colCustom  property="number" />
	</grid:row>
</grid:table>
