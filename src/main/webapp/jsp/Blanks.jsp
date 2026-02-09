<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>

<div id='for-insert'></div>

<ctrl:form styleId="striped-form">
  <div class="gridBack">
    <grid:table property="gridBlanks" key="bln_id" scrollableGrid="true" height="expression(document.body.clientHeight-235)">
      <grid:column align="center"><jsp:attribute name="title"><bean:message key="Blanks.bln_name"/></jsp:attribute></grid:column>
      <grid:column align="center" width="8%"><jsp:attribute name="title"><bean:message key="Blanks.sellerName"/></jsp:attribute></grid:column>
      <grid:column align="center" width="10%"><jsp:attribute name="title"><bean:message key="Blanks.bln_images"/></jsp:attribute></grid:column>
      <grid:column align="center" width="5%"><jsp:attribute name="title"><bean:message key="Blanks.bln_charset"/></jsp:attribute></grid:column>
      <grid:column align="center" width="5%"><jsp:attribute name="title"><bean:message key="Blanks.bln_language"/></jsp:attribute></grid:column>
      <grid:column align="center" width="18%"><jsp:attribute name="title"><bean:message key="Blanks.bln_preamble"/></jsp:attribute></grid:column>
      <grid:column align="center" width="18%"><jsp:attribute name="title"><bean:message key="Blanks.bln_note"/></jsp:attribute></grid:column>
      <grid:column align="center" width="7%"><jsp:attribute name="title"><bean:message key="Blanks.bln_usage"/></jsp:attribute></grid:column>
      <grid:column align="center" width="10%"><jsp:attribute name="title"><bean:message key="Blanks.bln_type"/></jsp:attribute></grid:column>
      <grid:column width="1%" title=""/>
      <grid:column width="5%" title=""/>
      <grid:row>
        <grid:colCustom property="bln_name"/>
        <grid:colCustom width="8%" property="sln_name"/>
        <grid:colCustom width="10%" property="bln_images"/>
        <grid:colCustom width="5%" property="bln_charset"/>
        <grid:colCustom width="5%" property="bln_language"/>
        <grid:colCustom width="18%" property="bln_preamble"/>
        <grid:colCustom width="18%" property="bln_note"/>
        <grid:colCustom width="7%" property="bln_usage_formatted"/>
        <grid:colCustom width="10%" property="bln_type"/>
        <grid:colEdit width="1%" action="/BlankAction.do?dispatch=edit" type="link" tooltip="tooltip.Blanks.edit" readonlyCheckerId="editReadonly"/>
        <grid:colLink width="5%" type="submit" dispatch="printExample" property="printExample" readonlyCheckerId="editReadonly"/>
      </grid:row>
    </grid:table>
  </div>

  <div class=gridBottom>
    <ctrl:ubutton type="link" action="/BlankAction.do?dispatch=create" styleClass="width80">
      <bean:message key="button.create"/>
    </ctrl:ubutton>
  </div>

</ctrl:form>

<logic:equal name="Blanks" property="printExample" value="true">
  <script language="JScript" type="text/javascript" >
    document.getElementById('for-insert').innerHTML='<iframe src="<ctrl:rewrite action="${Blanks.printAction}" />" style="display:none" />';
  </script>
</logic:equal>
