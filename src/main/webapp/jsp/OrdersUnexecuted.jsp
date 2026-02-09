<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>

<ctrl:form styleId="striped-form">

  <div id="filter-form" >

  <table id=filterTbl align="center" border="0" width="550">
    <tr>
      <td>
        <table width="100%">
          <tr>
            <td valign="top">
              <table>
                <tr>
                  <td colspan="2" align="center" class="table-header">
                    <bean:message key="OrdersUnexecuted.order_by"/>
                  </td>
                </tr>
                <tr>
                  <td align="right">
                    <bean:message key="OrdersUnexecuted.order_by_date"/>
                  </td>
                  <td width="15%">
                    <ctrl:checkbox property="order_by_date" styleClass="checkbox" value="1" onclick="orderByDateOnClick();"/>
                  </td>
                </tr>
                <tr>
                  <td align="right">
                    <bean:message key="OrdersUnexecuted.order_by_number"/>
                  </td>
                  <td width="15%">
                    <ctrl:checkbox property="order_by_number" styleClass="checkbox" value="1" onclick="orderByNumberOnClick();"/>
                  </td>
                </tr>
                <tr>
                  <td align="right">
                    <bean:message key="OrdersUnexecuted.order_by_stf_name"/>
                  </td>
                  <td width="15%">
                    <ctrl:checkbox property="order_by_stf_name" styleClass="checkbox" value="1" onclick="orderByStfNameOnClick();"/>
                  </td>
                </tr>
                <tr>
                  <td align="right">
                    <bean:message key="OrdersUnexecuted.order_by_produce_full_name"/>
                  </td>
                  <td width="15%">
                    <ctrl:checkbox property="order_by_produce_full_name" styleClass="checkbox" value="1" onclick="orderByProduceFullNameOnClick();"/>
                  </td>
                </tr>
                <tr>
                  <td align="right">
                    <bean:message key="OrdersUnexecuted.order_by_ctn_number"/>
                  </td>
                  <td width="15%">
                    <ctrl:checkbox property="order_by_ctn_number" styleClass="checkbox" value="1" onclick="orderByCtnNumberOnClick();"/>
                  </td>
                </tr>
              </table>
            </td>

          </tr>
        </table>
      </td>
    </tr>

    <tr>
      <td colspan=20>
        <table border="0" width="100%">
          <tr>
            <td width="70%" align="right" colspan=2>
              <ctrl:ubutton styleId="generateButton" type="submit" dispatch="generate" styleClass="width120"><bean:message key="button.form"/></ctrl:ubutton>
              <input type=button id="generateButtonExcel"  onclick="generateExcelClick();"  class="width165" value="<bean:message key="button.formExcel"/>">
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
	</div>

  <div class="gridBack" style="overflow:scroll;width:expression(document.body.clientWidth-15); height:expression(document.body.clientHeight-180)">
  <div>
    <grid:table property="grid" scrollableGrid="false"  nothingWasFoundMsg="msg.howToShowFilter">
			</tr><tr class="locked-header">
      <th class="table-header" colspan="2"><bean:message key="OrdersUnexecuted.order"/></th>
      <th class="table-header"/>
      <th class="table-header"/>
      <th class="table-header"/>
      <th class="table-header" colspan="3"><bean:message key="OrdersUnexecuted.count"/></th>
      </tr>

      <tr class="locked-header">
			<grid:column align="center"><jsp:attribute name="title"><bean:message key="OrdersUnexecuted.ord_date"/></jsp:attribute></grid:column>
			<grid:column align="center"><jsp:attribute name="title"><bean:message key="OrdersUnexecuted.ord_number"/></jsp:attribute></grid:column>
      <grid:column align="center"><jsp:attribute name="title"><bean:message key="OrdersUnexecuted.stf_name"/></jsp:attribute></grid:column>
			<grid:column align="center"><jsp:attribute name="title"><bean:message key="OrdersUnexecuted.produce_full_name"/></jsp:attribute></grid:column>
			<grid:column align="center"><jsp:attribute name="title"><bean:message key="OrdersUnexecuted.ctn_number"/></jsp:attribute></grid:column>
      <grid:column align="center"><jsp:attribute name="title"><bean:message key="OrdersUnexecuted.ord_count"/></jsp:attribute></grid:column>
      <grid:column align="center"><jsp:attribute name="title"><bean:message key="OrdersUnexecuted.ord_count_executed"/></jsp:attribute></grid:column>
      <grid:column align="center"><jsp:attribute name="title"><bean:message key="OrdersUnexecuted.ord_count_unexecuted"/></jsp:attribute></grid:column>
      <grid:row>
        <grid:colCustom property="ord_date_formatted" styleClassCheckerId="style-checker"/>
        <grid:colCustom property="ord_number" styleClassCheckerId="style-checker"/>
        <grid:colCustom property="stf_name" styleClassCheckerId="style-checker"/>
        <grid:colCustom property="fullName" styleClassCheckerId="style-checker"/>
        <grid:colCustom property="ctn_number" styleClassCheckerId="style-checker"/>
        <grid:colCustom align="right" property="ord_count_formatted" styleClassCheckerId="style-checker"/>
        <grid:colCustom align="right" property="ord_count_executed_formatted" styleClassCheckerId="style-checker"/>
        <grid:colCustom align="right" property="ord_count_unexecuted_formatted" styleClassCheckerId="style-checker"/>
      </grid:row>
    </grid:table>
  </div>
  </div>

</ctrl:form>

<script language="JScript" type="text/javascript">
  var order_by_date = document.getElementById('order_by_date');
  var order_by_number = document.getElementById('order_by_number');
  var order_by_stf_name = document.getElementById('order_by_stf_name');
  var order_by_produce_full_name = document.getElementById('order_by_produce_full_name');
  var order_by_ctn_number = document.getElementById('order_by_ctn_number');

  function orderByDateOnClick()
  {
    if ( !order_by_date.checked )
    {
      order_by_date.checked = true;
    }
    else
    {
      order_by_number.checked = false;
      order_by_stf_name.checked = false;
      order_by_produce_full_name.checked = false;
      order_by_ctn_number.checked = false;
    }
  }

  function orderByNumberOnClick()
  {
    if ( !order_by_number.checked )
    {
      order_by_number.checked = true;
    }
    else
    {
      order_by_date.checked = false;
      order_by_stf_name.checked = false;
      order_by_produce_full_name.checked = false;
      order_by_ctn_number.checked = false;
    }
  }

  function orderByStfNameOnClick()
  {
    if ( !order_by_stf_name.checked )
    {
      order_by_stf_name.checked = true;
    }
    else
    {
      order_by_date.checked = false;
      order_by_number.checked = false;
      order_by_produce_full_name.checked = false;
      order_by_ctn_number.checked = false;
    }
  }

  function orderByProduceFullNameOnClick()
  {
    if ( !order_by_produce_full_name.checked )
    {
      order_by_produce_full_name.checked = true;
    }
    else
    {
      order_by_date.checked = false;
      order_by_number.checked = false;
      order_by_stf_name.checked = false;
      order_by_ctn_number.checked = false;
    }
  }

  function orderByCtnNumberOnClick()
  {
    if ( !order_by_ctn_number.checked )
    {
      order_by_ctn_number.checked = true;
    }
    else
    {
      order_by_date.checked = false;
      order_by_number.checked = false;
      order_by_stf_name.checked = false;
      order_by_produce_full_name.checked = false;
    }
  }

  function generateExcelClick()
  {
	  document.getElementById('for-insert').innerHTML='<iframe src="<ctrl:rewrite action="/OrdersUnexecutedAction" dispatch="generateExcel"/>" style="display:none" />';
  }
</script>

  <div id='for-insert'></div>
