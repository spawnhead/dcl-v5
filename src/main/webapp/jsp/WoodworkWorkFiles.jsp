<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>

<ctrl:form>
  <div class="gridBack">
    <grid:table property="grid" key="id" scrollableGrid="true" height="expression(document.body.clientHeight-235)">
      <grid:column align="center"><jsp:attribute name="title"><bean:message key="WoodworkWorkFiles.user"/></jsp:attribute></grid:column>
      <grid:column align="center"><jsp:attribute name="title"><bean:message key="WoodworkWorkFiles.date"/></jsp:attribute></grid:column>
      <grid:column align="center"><jsp:attribute name="title"><bean:message key="WoodworkWorkFiles.file"/></jsp:attribute></grid:column>
      <grid:column title=""/>
      <grid:row>
        <grid:colCustom width="20%" property="user.userFullName"/>
        <grid:colCustom width="20%" property="createDate_formatted"/>
        <grid:colCustom width="60%" ><a href="" onclick="return download(${record.id});">${record.originalFileName}</a></grid:colCustom>
        <grid:colDelete width="1" action="/WoodworkWorkFilesAction.do?dispatch=delete" type="link" tooltip="tooltip.Attachments.delete"/>
      </grid:row>
    </grid:table>
  </div>

  <div class=gridBottom>
		<ctrl:ubutton type="submit" action="/WoodworkWorkFilesUploadFileAction.do?dispatch=input&referencedTable=${WoodworkWorkFiles.referencedTable}&referencedID=${WoodworkWorkFiles.referencedID}"  styleClass="width80">
      <bean:message key="button.attach"/>
    </ctrl:ubutton>
  </div>


</ctrl:form>
<script type="text/javascript" language="JavaScript">
	function download(id){
		document.getElementById('for-insert').innerHTML='<iframe src=\'<html:rewrite action="/WoodworkWorkFilesAction.do?dispatch=download"/>&id='+id+'\' style=\'display:none\' />';
		return false;
	}
</script>
<div id='for-insert'></div>