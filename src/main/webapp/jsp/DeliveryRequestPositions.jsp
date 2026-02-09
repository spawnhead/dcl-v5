<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>

<ctrl:form>
  <ctrl:hidden property="target"/>
  <ctrl:hidden property="needReload"/>
  <logic:equal name="DeliveryRequestPositions" property="target" value="order">
    <table >
      <tr>
        <td><bean:message key="DeliveryRequestPositions.ord_number"/></td>
        <td><html:text property="ord_number" style="width:230px"/></td>
      </tr>
      <tr>
        <td><bean:message key="DeliveryRequestPositions.search"/></td>
        <td><html:text property="name_filter" style="width:230px"/></td>
        <td><bean:message key="DeliveryRequestPositions.manager"/></td>
        <td>
          <ctrl:serverList property="manager.userFullName" idName="manager.usr_id" action="/UsersListAction" 
                           enterDispatch="filter" styleClass="filter"  style="width:125px;" scriptUrl="have_all=true" callback="onManagerChange"/>
        </td>
        <td colspan="2" >
          <ctrl:ubutton type="submit" dispatch="clear" styleClass="width120"><bean:message key="button.clearFilter"/></ctrl:ubutton>&nbsp;
          <ctrl:ubutton type="submit" dispatch="filter" styleClass="width120"><bean:message key="button.applyFilter"/></ctrl:ubutton>
        </td>
      </tr>
    </table>
  </logic:equal>
  <table width='100%'>
    <tr>
      <td width="45%">
        <div class="gridBack">
          <grid:table property="gridLeft" key="id" scrollableGrid="true"
                      height="expression(document.body.clientHeight-330)" groupBy="number_date" expandableGroup="true">
            <grid:column width="1%"/>
            <grid:column width="1%"/>
            <grid:column width="20%" align="center"><jsp:attribute name="title"><bean:message key="DeliveryRequestPositions.name1"/></jsp:attribute></grid:column>
            <grid:column width="10%" align="center"><jsp:attribute name="title"><bean:message key="DeliveryRequestPositions.type1"/></jsp:attribute></grid:column>
            <grid:column align="center"><jsp:attribute name="title"><bean:message key="DeliveryRequestPositions.param1"/></jsp:attribute></grid:column>
            <grid:column width="15%" align="center"><jsp:attribute name="title"><bean:message key="DeliveryRequestPositions.add_param1"/></jsp:attribute></grid:column>
            <logic:equal name="DeliveryRequestPositions" property="target" value="order">
              <grid:column width="10%" align="center"><jsp:attribute name="title"><bean:message key="DeliveryRequestPositions.cat_num1"/></jsp:attribute></grid:column>
            </logic:equal>
            <grid:column width="15%" align="center"><jsp:attribute name="title"><bean:message key="DeliveryRequestPositions.rest_count"/></jsp:attribute></grid:column>
            <grid:column width="12%" align="center"><jsp:attribute name="title"><bean:message key="DeliveryRequestPositions.shipped_count1"/></jsp:attribute></grid:column>
            <grid:row rowParserId="row-parser">
              <grid:colCustom width="1%">&nbsp;</grid:colCustom>
              <grid:colCheckbox width="1%" property="selected_id" result="gridLeft" resultProperty="selected_id" useIndexAsPK="true"/>
              <grid:colCustom width="20%" property="produce.name"/>
              <grid:colCustom width="10%" property="produce.type"/>
              <grid:colCustom property="produce.params"/>
              <grid:colCustom width="15%" property="produce.addParams"/>
              <logic:equal name="DeliveryRequestPositions" property="target" value="order">
                <grid:colCustom width="10%" property="ctn_number"/>
              </logic:equal>
              <grid:colCustom width="15%" align="right" property="count_formatted"/>
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
                      height="expression(document.body.clientHeight-330)" >
            <grid:column width="1%"/>
            <grid:column width="20%" align="center"><jsp:attribute name="title"><bean:message key="DeliveryRequestPositions.name2"/></jsp:attribute></grid:column>
            <grid:column width="10%" align="center"><jsp:attribute name="title"><bean:message key="DeliveryRequestPositions.type2"/></jsp:attribute></grid:column>
            <grid:column align="center"><jsp:attribute name="title"><bean:message key="DeliveryRequestPositions.param2"/></jsp:attribute></grid:column>
            <grid:column width="15%" align="center"><jsp:attribute name="title"><bean:message key="DeliveryRequestPositions.add_param2"/></jsp:attribute></grid:column>
            <logic:equal name="DeliveryRequestPositions" property="target" value="order">
              <grid:column width="10%" align="center"><jsp:attribute name="title"><bean:message key="DeliveryRequestPositions.cat_num2"/></jsp:attribute></grid:column>
            </logic:equal>
            <grid:column width="10%" align="center"><jsp:attribute name="title"><bean:message key="DeliveryRequestPositions.shipped_count2"/></jsp:attribute></grid:column>
            <grid:row>
              <grid:colCheckbox width="1%" property="selected_id" result="gridRight" resultProperty="selected_id" useIndexAsPK="true"/>
              <grid:colCustom width="20%" property="position.produce.name"/>
              <grid:colCustom width="10%" property="position.produce.type"/>
              <grid:colCustom property="position.produce.params"/>
              <grid:colCustom width="15%" property="position.produce.addParams"/>
              <logic:equal name="DeliveryRequestPositions" property="target" value="order">
                <grid:colCustom width="10%" property="position.ctn_number"/>
              </logic:equal>
              <grid:colCustom width="10%" property="position.count_formatted" align="right"/>
            </grid:row>
          </grid:table>
        </div>
      </td>
    </tr>
  </table>

  <div class=gridBottom>
    <ctrl:ubutton type="submit"  dispatch="save" styleClass="width80" textId="button.ok"/>
  </div>
</ctrl:form>

<script language="JScript" type="text/javascript">
  <logic:equal name="DeliveryRequestPositions" property="target" value="order">
    var needReload = document.getElementById("needReload");

    function onManagerChange()
    {
      needReload.value = 'true';
    }
  </logic:equal>
</script>