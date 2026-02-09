<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>

<script type="text/javascript" src="includes/js_util.js"></script>
<script type="text/javascript">
	window.onload = function ()
	{
		document.onkeydown = null;
		var grid = document.getElementById('tableBodygridRight');
		if (grid != null)
		{
			var allInput = grid.getElementsByTagName('input');
			var necessaryInputs = [];
			var i;
			for (i = 0; i < allInput.length; i++)
			{
				var input = allInput[i];
				if (input.getAttribute("name").indexOf("saleSumPlusNdsFormatted") > -1)
				{
					necessaryInputs.add(input);
				}
			}

			for (i = 0; i < necessaryInputs.length - 1; i++)
			{
				moveFocusToNextTableRow(necessaryInputs[i]);
			}
		}
	}
</script>
<ctrl:form>
  <table>
    <tr>
      <td><bean:message key="ShippingPositions.route"/></td>
      <td>
          <ctrl:serverList property="route.name" idName="route.id" action="/RoutesListAction"
                           selectOnly="true" style="width:200px;" callback="routeChange" enterDispatch="filter"/>
      </td>
      <td align="right"><bean:message key="ShippingPositions.prc_date_min"/></td>
      <td><ctrl:date property="prc_date_min" style="width:200px;" enterDispatch="filter"/></td>
    </tr>
    <tr>
      <td><bean:message key="ShippingPositions.search"/></td>
      <td><ctrl:text property="name_filter" style="width:220px" enterDispatch="filter"/></td>
      <td><bean:message key="ShippingPositions.ctn_number"/></td>
      <td ><ctrl:text property="ctn_number" style="width:220px" enterDispatch="filter"/></td>
    </tr>
    <tr>
      <td><bean:message key="ShippingPositions.number_1c"/></td>
      <td><ctrl:text property="number_1c" style="width:220px" enterDispatch="filter"/></td>
	    <td align="right"><bean:message key="ShippingPositions.orderFor"/></td>
	    <td>
         <ctrl:serverList property="contractorForPositions.name" idName="contractorForPositions.id" action="/ContractorsListAction" filter="filter" style="width:200px;"/>
     </td>
    </tr>
    <tr>
      <td colspan="2"></td>
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
          <grid:table property="gridLeft" key="lpc_id" scrollableGrid="true"
                      height="expression(document.body.clientHeight-330)" groupBy="prc_number_date"
                      expandableGroup="true" showGroupsExpanded="${ShippingPositions.showGroupsExpanded}">
            <grid:column/>
            <grid:column/>
            <grid:column align="center"><jsp:attribute name="title"><bean:message key="ShippingPositions.lpc_1c_number"/></jsp:attribute></grid:column>
            <grid:column align="center"><jsp:attribute name="title"><bean:message key="ShippingPositions.name1"/></jsp:attribute></grid:column>
            <grid:column align="center"><jsp:attribute name="title"><bean:message key="ShippingPositions.ctn_number1"/></jsp:attribute></grid:column>
            <grid:column align="center"><jsp:attribute name="title"><bean:message key="ShippingPositions.rest_count"/></jsp:attribute></grid:column>
            <grid:column align="center"><jsp:attribute name="title"><bean:message key="ShippingPositions.shipped_count1"/></jsp:attribute></grid:column>
            <grid:row>
              <grid:colCustom width="1%">&nbsp;</grid:colCustom>
              <grid:colCheckbox width="1%" property="selected_id" result="gridLeft" resultProperty="selected_id" useIndexAsPK="true"/>
              <grid:colCustom width="10%" property="lpc_1c_number"/>
              <grid:colCustom width="35%" property="produce_name"/>
              <grid:colCustom width="15%" property="ctn_number"/>
              <grid:colCustom align="right" property="free_count_formatted"/>
              <grid:colInput  width="12%" property="shipped_count_formatted"  result="gridLeft" resultProperty="shipped_count_formatted" useIndexAsPK="true" />
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
          <grid:table property="gridRight" key="_id" scrollableGrid="true"
                      height="expression(document.body.clientHeight-330)" >
            <grid:column/>
            <grid:column align="center"><jsp:attribute name="title"><bean:message key="ShippingPositions.name2"/></jsp:attribute></grid:column>
            <grid:column align="center"><jsp:attribute name="title"><bean:message key="ShippingPositions.ctn_number2"/></jsp:attribute></grid:column>
            <grid:column align="center"><jsp:attribute name="title"><bean:message key="ShippingPositions.shipped_count2"/></jsp:attribute></grid:column>
            <grid:column align="center"><jsp:attribute name="title"><bean:message key="ShippingPositions.summ"/></jsp:attribute></grid:column>
            <grid:row>
              <grid:colCheckbox width="1%" property="selected_id" result="gridRight" resultProperty="selected_id" useIndexAsPK="true"/>
              <grid:colCustom property="position.fullNameProduce"/>
              <grid:colCustom width="20%" property="position.ctn_number"/>
              <grid:colCustom width="13%" property="position.countFormatted" align="right"/>
              <grid:colInput width="15%" property="position.saleSumPlusNdsFormatted" result="gridRight" resultProperty="position.saleSumPlusNdsFormatted" useIndexAsPK="true"/>
            </grid:row>
          </grid:table>
        </div>
      </td>
    </tr>
  </table>

  <div class=gridBottom>
    <ctrl:ubutton type="submit"  dispatch="save" styleClass="width80" textId="button.ok"/>
  </div>

<script type="text/javascript" language="JScript">
  var savedRouteId = document.getElementById("route.id").value;
  var savedRouteName = document.getElementById("route.name").value;

  function routeChange(arg)
  {
    if (savedRouteId != arg.arguments[0])
    {
      var ret = true;
      <logic:notEmpty name="ShippingPositions" property="gridRight.dataList">
        ret = isUserAgree('<bean:message key="ShippingPositions.routeChangeAskUser"/>',false);
      </logic:notEmpty>
      if (ret)
      {
        submitDispatchForm("changeRoute");
      }
      else
      {
	      document.getElementById("route.id").value = savedRouteId;
	      document.getElementById("route.name").value = savedRouteName;
      }
    }
  }
</script>
</ctrl:form>
