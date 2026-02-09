<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>

<ctrl:form>
  <ctrl:hidden property="asm_type"/>
  <table width='100%'>
    <tr><td width="45%">
    <div class="gridBack">
      <grid:table property="gridLeft" key="id" scrollableGrid="true"
                  height="expression(document.body.clientHeight-330)" groupBy="number_date" expandableGroup="true">
        <grid:column width="1%"/>
        <grid:column width="1%" />
        <grid:column width="20%" align="center"><jsp:attribute name="title"><bean:message key="AssemblePositions.name1"/></jsp:attribute></grid:column>
        <grid:column width="10%" align="center"><jsp:attribute name="title"><bean:message key="AssemblePositions.type1"/></jsp:attribute></grid:column>
        <grid:column align="center"><jsp:attribute name="title"><bean:message key="AssemblePositions.param1"/></jsp:attribute></grid:column>
        <grid:column width="15%" align="center"><jsp:attribute name="title"><bean:message key="AssemblePositions.add_param1"/></jsp:attribute></grid:column>
        <grid:column width="15%" align="center"><jsp:attribute name="title"><bean:message key="AssemblePositions.rest_count"/></jsp:attribute></grid:column>

        <grid:row>
          <grid:colCustom width="1%">&nbsp;</grid:colCustom>
          <grid:colCheckbox width="1%" property="selected_id" result="gridLeft" resultProperty="selected_id" useIndexAsPK="true"/>
          <grid:colCustom width="20%" property="produce.name"/>
          <grid:colCustom width="10%" property="produce.type"/>
          <grid:colCustom property="produce.params"/>
          <grid:colCustom width="15%" property="produce.addParams"/>
          <grid:colCustom width="15%" align="right" property="count_formatted"/>
        </grid:row>
      </grid:table>
    </div>
  </td>
  <td valign=center align="middle">
    <ctrl:ubutton type="submit" dispatch="add" styleClass="moveBtn"><bean:message key="button.moveRight"/></ctrl:ubutton><br><br>
    <ctrl:ubutton type="submit" dispatch="delete" styleClass="moveBtn"><bean:message key="button.moveLeft"/></ctrl:ubutton><br><br>
  </td>
  <td width="45%">
    <div class="gridBack">
      <grid:table property="gridRight" key="id" scrollableGrid="true"
                  height="expression(document.body.clientHeight-330)" >
        <grid:column width="1%"/>
        <grid:column width="20%" align="center"><jsp:attribute name="title"><bean:message key="AssemblePositions.name2"/></jsp:attribute></grid:column>
        <grid:column width="10%" align="center"><jsp:attribute name="title"><bean:message key="AssemblePositions.type2"/></jsp:attribute></grid:column>
        <grid:column align="center"><jsp:attribute name="title"><bean:message key="AssemblePositions.param2"/></jsp:attribute></grid:column>
        <grid:column width="15%" align="center"><jsp:attribute name="title"><bean:message key="AssemblePositions.add_param2"/></jsp:attribute></grid:column>
        <logic:equal name="AssemblePositions" property="asm_type" value="0">
          <grid:column width="10%" align="center"><jsp:attribute name="title"><bean:message key="AssemblePositions.apr_count"/></jsp:attribute></grid:column>
        </logic:equal>
        <grid:row>
          <grid:colCheckbox width="1%" property="selected_id" result="gridRight" resultProperty="selected_id" useIndexAsPK="true"/>
          <grid:colCustom width="20%" property="position.produce.name"/>
          <grid:colCustom width="10%" property="position.produce.type"/>
          <grid:colCustom property="position.produce.params"/>
          <grid:colCustom width="15%" property="position.produce.addParams"/>
          <logic:equal name="AssemblePositions" property="asm_type" value="0">
            <grid:colInput width="10%" textAllign="right" property="position.count_formatted" result="gridRight" resultProperty="position.count_formatted" useIndexAsPK="true"/>
          </logic:equal>
        </grid:row>
      </grid:table>
    </div>
  </td></tr></table>

  <div class=gridBottom>
    <ctrl:ubutton type="submit" dispatch="save" styleClass="width80" textId="button.ok"/>
  </div>
</ctrl:form>
