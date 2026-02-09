<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>
<ctrl:form>
  <div class="gridBack">
    <grid:table property="grid" key="idx" scrollableGrid="true" height="expression(document.body.clientHeight-235)" >
      <grid:column><jsp:attribute name="title"><bean:message key="Attachments.name"/></jsp:attribute></grid:column>
      <grid:column title=""/>
      <grid:row>
        <grid:colCustom width="99%" ><a href="" onclick="return download(${record.idx});">${record.originalFileName}</a></grid:colCustom>
        <grid:colDelete width="1" dispatch="delete" scriptUrl="id=${record.idx}"type="link" tooltip="tooltip.Attachments.delete"/>
      </grid:row>
    </grid:table>
  </div>

  <div class=gridBottom>
		<ctrl:ubutton type="link" dispatch="back" styleClass="width80">
			<bean:message key="button.back"/>
		</ctrl:ubutton>

		<ctrl:ubutton type="submit" action="/DefferedUploadFileAction.do?dispatch=input"  styleClass="width80">
      <bean:message key="button.attach"/>
    </ctrl:ubutton>
  </div>


</ctrl:form>
<script type="text/javascript" language="JavaScript">
	function download(id){
		document.getElementById('for-insert').innerHTML='<iframe src=\'<html:rewrite action="/DeferredAttachmentsAction.do?dispatch=download"/>&id='+id+'\' style=\'display:none\' />';
		return false;
	}
</script>
<div id='for-insert'></div>