<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>

<ctrl:form>
  <ctrl:hidden property="target"/>
  <table >
    <tr>
      <td><bean:message key="ProduceCostPositions.search"/></td>
      <td><ctrl:text property="name_filter" style="width:230px" enterDispatch="filter"/></td>
    </tr>
    <tr>
      <td><bean:message key="ProduceCostPositions.ctn_number"/></td>
      <td><ctrl:text property="ctn_number" style="width:230px" enterDispatch="filter"/></td>
      <td colspan="2" >
        <ctrl:ubutton type="submit" dispatch="clear" styleClass="width120"><bean:message key="button.clearFilter"/></ctrl:ubutton>&nbsp;
        <ctrl:ubutton type="submit" dispatch="filter" styleClass="width120"><bean:message key="button.applyFilter"/></ctrl:ubutton>
      </td>
    </tr>
  </table>
  <logic:equal name="ProduceCostPositions" property="showDeliveryRequestLegend" value="true">
    <table width='45%'>
      <tr>
        <td style="color:#008000">
          <bean:message key="ProduceCostPositions.legend_delivery_request"/>
        </td>
      </tr>
    </table>
  </logic:equal>
  <table width='100%'>
    <tr><td width="45%">
    <div class="gridBack">
      <grid:table property="gridLeft" key="id" scrollableGrid="true"
                  height="expression(document.body.clientHeight-360)" groupBy="number_date" expandableGroup="true">
        <grid:column/>
        <grid:column/>
        <grid:column align="center"><jsp:attribute name="title"><bean:message key="ProduceCostPositions.name1"/></jsp:attribute></grid:column>
        <grid:column align="center"><jsp:attribute name="title"><bean:message key="ProduceCostPositions.type1"/></jsp:attribute></grid:column>
        <grid:column align="center"><jsp:attribute name="title"><bean:message key="ProduceCostPositions.param1"/></jsp:attribute></grid:column>
        <grid:column align="center"><jsp:attribute name="title"><bean:message key="ProduceCostPositions.add_param1"/></jsp:attribute></grid:column>
        <grid:column align="center"><jsp:attribute name="title"><bean:message key="ProduceCostPositions.cat_num1"/></jsp:attribute></grid:column>
        <grid:column align="center"><jsp:attribute name="title"><bean:message key="ProduceCostPositions.rest_count"/></jsp:attribute></grid:column>
        <grid:column align="center"><jsp:attribute name="title"><bean:message key="ProduceCostPositions.shipped_count1"/></jsp:attribute></grid:column>
        <grid:row>
          <grid:colCustom width="3%">&nbsp;</grid:colCustom>
          <grid:colCheckbox width="1%" property="selected_id" result="gridLeft" resultProperty="selected_id" useIndexAsPK="true"/>
          <grid:colCustom width="20%" property="produce.name"/>
          <grid:colCustom width="7%" property="produce.type"/>
          <grid:colCustom width="20%" property="produce.params"/>
          <grid:colCustom property="produce.addParams"/>
          <grid:colCustom width="10%" property="ctn_number"/>
          <grid:colCustom width="12%" align="right" property="count_formatted"/>
          <grid:colInput width="12%" property="shipped_count_formatted"  result="gridLeft" resultProperty="shipped_count_formatted" useIndexAsPK="true" />
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
                  height="expression(document.body.clientHeight-360)" >
        <grid:column/>
        <grid:column align="center"><jsp:attribute name="title"><bean:message key="ProduceCostPositions.name2"/></jsp:attribute></grid:column>
        <grid:column align="center"><jsp:attribute name="title"><bean:message key="ProduceCostPositions.type2"/></jsp:attribute></grid:column>
        <grid:column align="center"><jsp:attribute name="title"><bean:message key="ProduceCostPositions.param2"/></jsp:attribute></grid:column>
        <grid:column align="center"><jsp:attribute name="title"><bean:message key="ProduceCostPositions.add_param2"/></jsp:attribute></grid:column>
        <grid:column align="center"><jsp:attribute name="title"><bean:message key="ProduceCostPositions.cat_num2"/></jsp:attribute></grid:column>
        <grid:column align="center"><jsp:attribute name="title"><bean:message key="ProduceCostPositions.shipped_count2"/></jsp:attribute></grid:column>
        <grid:row>
          <grid:colCheckbox width="1%" property="selected_id" result="gridRight" resultProperty="selected_id" useIndexAsPK="true"/>
          <grid:colCustom property="position.produce.name"/>
          <grid:colCustom width="5%" property="position.produce.type"/>
          <grid:colCustom width="20%" property="position.produce.params"/>
          <grid:colCustom width="15%" property="position.produce.addParams"/>
          <grid:colCustom width="10%" property="position.ctn_number"/>
          <grid:colCustom width="10%" property="position.count_formatted" align="right"/>
        </grid:row>
      </grid:table>
    </div>
  </td></tr></table>

  <div class=gridBottom>
    <ctrl:ubutton type="submit"  dispatch="save" styleClass="width80" textId="button.ok"/>
  </div>
</ctrl:form>
