<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>

<div class="gridBackNarrow" id="reservedInfoGrid">
  <grid:table property="reservedInfoLines" key="number">
    <grid:column align="center"><jsp:attribute name="title"><bean:message key="ReservedInfoLines.reserved"/></jsp:attribute></grid:column>
    <grid:column align="center"><jsp:attribute name="title"><bean:message key="ReservedInfoLines.reserved_for"/></jsp:attribute></grid:column>
    <grid:column align="center"><jsp:attribute name="title"><bean:message key="ReservedInfoLines.number"/></jsp:attribute></grid:column>
    <grid:column align="center"><jsp:attribute name="title"><bean:message key="ReservedInfoLines.reserved_end"/></jsp:attribute></grid:column>
    <grid:row>
      <grid:colCustom width="20%" align="right" property="reservedFormatted"/>
      <grid:colCustom property="reserved_for"/>
      <grid:colCustom width="30%" property="number"/>
      <grid:colCustom width="20%" property="reservedEndFormatted"/>
    </grid:row>
  </grid:table>
</div>  
