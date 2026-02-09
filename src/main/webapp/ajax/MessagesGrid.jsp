<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>

<div class="gridBackNarrow" id="messagesGrid">
  <grid:table property="gridMessages" key="uniqueId">
    <grid:column align="center"><jsp:attribute name="title"><bean:message key="Messages.date"/></jsp:attribute></grid:column>
    <grid:column align="center"><jsp:attribute name="title"><bean:message key="Messages.message"/></jsp:attribute></grid:column>
    <grid:column align="center"><jsp:attribute name="title"><bean:message key="Messages.sum"/></jsp:attribute></grid:column>
    <grid:column title=""/>
    <grid:column align="center"><jsp:attribute name="title"><bean:message key="Messages.contractor"/></jsp:attribute></grid:column>
    <grid:column align="center"><jsp:attribute name="title"><bean:message key="Messages.comment"/></jsp:attribute></grid:column>
    <grid:column title=""/>
    <grid:row>
      <grid:colCustom width="6%" property="date"/>
      <grid:colCustom property="message"/>
      <grid:colCustom width="5%" property="sum" align="right"/>
      <grid:colCustom width="1" property="shortInfo" align="center"/>
      <grid:colCustom width="10%" property="contractor.name"/>
      <grid:colCustom width="5%" property="comment" align="right"/>
      <grid:colDelete width="1" type="script" action="/OfficeAction.do" onclick='removeMessages("${record.uniqueId}")'
                      tooltip="tooltip.Messages.delete" readonlyCheckerId="deleteChecker" showWait="false"/>
    </grid:row>
  </grid:table>
</div>  
