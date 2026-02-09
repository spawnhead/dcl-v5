<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>

<ctrl:form>
  <table width='100%'>
    <tr><td width="45%">
    <div class="gridBack">
      <grid:table property="gridLeft" key="id" scrollableGrid="true"
                  height="expression(document.body.clientHeight-330)" groupBy="number_date" expandableGroup="true">
        <grid:column />
        <grid:column />
        <grid:column align="center"><jsp:attribute name="title"><bean:message key="SpecificationImportPositions.name1"/></jsp:attribute></grid:column>
        <grid:column align="center"><jsp:attribute name="title"><bean:message key="SpecificationImportPositions.type1"/></jsp:attribute></grid:column>
        <grid:column align="center"><jsp:attribute name="title"><bean:message key="SpecificationImportPositions.param1"/></jsp:attribute></grid:column>
        <grid:column align="center"><jsp:attribute name="title"><bean:message key="SpecificationImportPositions.add_param1"/></jsp:attribute></grid:column>
        <grid:column align="center"><jsp:attribute name="title"><bean:message key="SpecificationImportPositions.rest_count"/></jsp:attribute></grid:column>
        <grid:column align="center"><jsp:attribute name="title"><bean:message key="SpecificationImportPositions.shipped_count1"/></jsp:attribute></grid:column>

        <grid:row>
          <grid:colCustom width="5%">&nbsp;</grid:colCustom>
          <grid:colCheckbox  width="1%" property="selected_id" result="gridLeft" resultProperty="selected_id" useIndexAsPK="true"/>
          <grid:colCustom  width="20%" property="produce.name"/>
          <grid:colCustom  width="5%" property="produce.type"/>
          <grid:colCustom  width="20%" property="produce.params"/>
          <grid:colCustom  property="produce.addParams"/>
          <grid:colCustom  width="15%" align="right" property="count_formatted"/>
          <grid:colInput   width="12%" property="shipped_count_formatted"  result="gridLeft" resultProperty="shipped_count_formatted" useIndexAsPK="true" />
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
        <grid:column/>
        <grid:column align="center"><jsp:attribute name="title"><bean:message key="SpecificationImportPositions.name2"/></jsp:attribute></grid:column>
        <grid:column align="center"><jsp:attribute name="title"><bean:message key="SpecificationImportPositions.type2"/></jsp:attribute></grid:column>
        <grid:column align="center"><jsp:attribute name="title"><bean:message key="SpecificationImportPositions.param2"/></jsp:attribute></grid:column>
        <grid:column align="center"><jsp:attribute name="title"><bean:message key="SpecificationImportPositions.add_param2"/></jsp:attribute></grid:column>
        <grid:column align="center"><jsp:attribute name="title"><bean:message key="SpecificationImportPositions.shipped_count2"/></jsp:attribute></grid:column>
        <grid:row>
          <grid:colCheckbox  width="1%" property="selected_id" result="gridRight" resultProperty="selected_id" useIndexAsPK="true"/>
          <grid:colCustom width="20%" property="position.produce.name"/>
          <grid:colCustom  width="5%" property="position.produce.type"/>
          <grid:colCustom  width="20%" property="position.produce.params"/>
          <grid:colCustom  width="15%" property="position.produce.addParams"/>
          <grid:colCustom width="10%" property="position.count_formatted" align="right"/>
        </grid:row>
      </grid:table>
    </div>
  </td></tr></table>

  <div class=gridBottom>
    <ctrl:ubutton type="submit"  dispatch="save" styleClass="width80" textId="button.ok"/>
  </div>
</ctrl:form>
