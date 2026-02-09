<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>
<ctrl:form>

  <div class="gridBack">
    <grid:table property="grid" key="rpt_id" scrollableGrid="true" height="expression(document.body.clientHeight-235)" autoLockName="Reputations">
      <grid:column align="center" width="20%"><jsp:attribute name="title"><bean:message key="Reputations.rpt_level"/></jsp:attribute></grid:column>
      <grid:column align="center"><jsp:attribute name="title"><bean:message key="Reputations.rpt_description"/></jsp:attribute></grid:column>
      <grid:column align="center" width="20%"><jsp:attribute name="title"><bean:message key="Reputations.rpt_default_in_ctc"/></jsp:attribute></grid:column>
      <grid:column title=""/>
      <grid:row>
        <grid:colCustom align="right" width="20%" property="rpt_level"/>
        <grid:colCustom property="rpt_description"/>
        <grid:colCheckbox align="center" width="20%" property="rpt_default_in_ctc" type="submit" dispatch="set_default" scriptUrl="setDefault=${record.rpt_default_in_ctc}&rpt_id=${record.rpt_id}"/>
        <grid:colEdit width="1" dispatch="edit" type="submit" tooltip="tooltip.Reputations.edit"/>
      </grid:row>
    </grid:table>
  </div>

  <div class=gridBottom>
    <ctrl:ubutton type="link" actionForward="back" styleClass="width80">
      <bean:message key="button.back"/>
    </ctrl:ubutton>
    <ctrl:ubutton type="link" actionForward="input" styleClass="width80">
      <bean:message key="button.create"/>
    </ctrl:ubutton>
  </div>
</ctrl:form>
