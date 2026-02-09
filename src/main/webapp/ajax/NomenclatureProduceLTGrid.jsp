<%@ page import="org.apache.struts.taglib.html.Constants" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>

<grid:table property="gridLT" key="langCode" scrollableGrid="false"  >
	<grid:column ><jsp:attribute name="title"><bean:message key="NomenclatureProduce.gridLT.language"/></jsp:attribute></grid:column>
	<grid:column ><jsp:attribute name="title"><bean:message key="NomenclatureProduce.gridLT.translation"/></jsp:attribute></grid:column>
	<grid:row>
		<grid:colCustom  width="20%"  property="langName" readonlyCheckerId="langChecker"/>
		<grid:colInput  property="text" result="gridLT" resultProperty="text" useIndexAsPK="true" readonlyCheckerId="langChecker" />
	</grid:row>
</grid:table>
