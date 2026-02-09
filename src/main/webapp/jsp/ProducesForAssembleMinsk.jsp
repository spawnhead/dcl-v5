<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>

<ctrl:form>
  <table>
    <tr>
      <td><bean:message key="ProducesForAssembleMinsk.route"/></td>
      <td>
          <ctrl:serverList property="route.name" idName="route.id" action="/RoutesListAction"
                           selectOnly="true" style="width:210px;" readonly="true"/>
      </td>
      <td align="right"><bean:message key="ShippingPositions.prc_date_min"/></td>
      <td><ctrl:date property="prc_date_min" styleClass="filter"/></td>
    </tr>
    <tr>
      <td><bean:message key="ProducesForAssembleMinsk.search"/></td>
      <td><ctrl:text property="name_filter" style="width:230px"/></td>
      <td><bean:message key="ProducesForAssembleMinsk.ctn_number"/></td>
      <td ><ctrl:text property="ctn_number" style="width:210px"/></td>
    </tr>
    <tr>
      <td><bean:message key="ProducesForAssembleMinsk.number_1c"/></td>
      <td><ctrl:text property="number_1c" style="width:230px"/></td>
      <td colspan="2" align="right">
        <ctrl:ubutton type="submit" dispatch="clear" styleClass="width120"><bean:message key="button.clearFilter"/></ctrl:ubutton>&nbsp;
        <ctrl:ubutton type="submit" dispatch="filter" styleClass="width120"><bean:message key="button.applyFilter"/></ctrl:ubutton>
      </td>
    </tr>
  </table>
  <table width='100%'>                                                        
    <tr>
      <td width="45%">
        <div class="gridBack">
          <grid:table property="gridLeft" key="id" scrollableGrid="true"
                      height="expression(document.body.clientHeight-330)" groupBy="number_date"
                      expandableGroup="true" showGroupsExpanded="${ProducesForAssembleMinsk.showGroupsExpanded}">
            <grid:column/>
            <grid:column/>
            <grid:column align="center"><jsp:attribute name="title"><bean:message key="ProducesForAssembleMinsk.lpc_1c_number"/></jsp:attribute></grid:column>
            <grid:column align="center"><jsp:attribute name="title"><bean:message key="ProducesForAssembleMinsk.name1"/></jsp:attribute></grid:column>
            <grid:column align="center"><jsp:attribute name="title"><bean:message key="ProducesForAssembleMinsk.ctn_number1"/></jsp:attribute></grid:column>
            <grid:column align="center"><jsp:attribute name="title"><bean:message key="ProducesForAssembleMinsk.rest_count"/>&nbsp;<ctrl:help htmlName="ProducesForAssembleMinskRestCountHeader"/></jsp:attribute></grid:column>
            <grid:column align="center"><jsp:attribute name="title"><bean:message key="ProducesForAssembleMinsk.shipped_count1"/></jsp:attribute></grid:column>
            <grid:row>
              <grid:colCustom width="1%">&nbsp;</grid:colCustom>
              <grid:colCheckbox width="1%" property="selected_id" result="gridLeft" resultProperty="selected_id" useIndexAsPK="true"/>
              <grid:colCustom width="10%" property="lpc_1c_number"/>
              <grid:colCustom width="35%" property="produce.fullName"/>
              <grid:colCustom width="15%" property="ctn_number"/>
              <grid:colCustom align="right" property="countReservedFormatted"/>
              <grid:colInput  width="12%" property="shipped_count_formatted" result="gridLeft" resultProperty="shipped_count_formatted" useIndexAsPK="true" />
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
            <grid:column align="center"><jsp:attribute name="title"><bean:message key="ProducesForAssembleMinsk.name2"/></jsp:attribute></grid:column>
            <grid:column align="center"><jsp:attribute name="title"><bean:message key="ProducesForAssembleMinsk.ctn_number2"/></jsp:attribute></grid:column>
            <grid:column align="center"><jsp:attribute name="title"><bean:message key="ProducesForAssembleMinsk.shipped_count2"/></jsp:attribute></grid:column>
            <grid:row>
              <grid:colCheckbox width="1%" property="selected_id" result="gridRight" resultProperty="selected_id" useIndexAsPK="true"/>
              <grid:colCustom property="position.produce.fullName"/>
              <grid:colCustom width="20%" property="position.ctn_number"/>
              <grid:colCustom width="13%" property="position.count_formatted" align="right"/>
            </grid:row>
          </grid:table>
        </div>
      </td>
    </tr>
  </table>

  <div class=gridBottom>
    <ctrl:ubutton type="submit"  dispatch="save" styleClass="width80" textId="button.ok"/>
  </div>

  <div id="reservedInfoForm" style="display:none;" >
    <div>
      <table width="100%">
        <tr>
          <td id="reservedInfo">
          </td>
        </tr>
      </table>
  </div>
</ctrl:form>

<script language="JScript" type="text/javascript">
  function showReservedInfo(lpcId)
  {
    doAjax({
      url:'<ctrl:rewrite action="/ProducesForAssembleMinskAction" dispatch="ajaxReservedInfoGrid"/>',
      params:{'lpcId':lpcId},
      synchronous:true,
      update:'reservedInfo'
    });

    TagToTip("reservedInfoForm", LEFT, true, WIDTH, 480, PADDING, 10, STICKY, true, CLICKCLOSE, false, CLOSEBTN, true, EXCLUSIVE, true, BGCOLOR, "#eeeeee", BORDERCOLOR, "#323232", TITLEBGCOLOR, "#323232", CLOSEBTNCOLORS,["#323232","#ffffff","#323232","#ffffff"], DELAY, 0);
  }
</script>
