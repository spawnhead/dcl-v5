<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>

<ctrl:form styleId="striped-form">
  
	<table id=filterTbl align="center" border="0" width="100%">

    <tr>
      <td colspan=20>
        <table>
          <tr>
            <td>
              <bean:message key="OrdersLogistics.date_begin"/>
            </td>
            <td>
              <ctrl:date property="date_begin" styleClass="filter-long" afterSelect='enter_date' endField="date_end" style="width:200px;" showHelp="false"/>
            </td>
            <td>
              <bean:message key="OrdersLogistics.date_end"/>
            </td>
            <td>
              <ctrl:date property="date_end" styleClass="filter-long" afterSelect='enter_date' startField="date_begin" style="width:200px;" showHelp="false"/>
            </td>
          </tr>
        </table>
      </td>
    </tr>

    <tr>
      <td colspan="20">
        <table width="100%">
          <tr>
            <td>
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td>
                    <bean:message key="OrdersLogistics.include_empty_dates"/>&nbsp;
                    <ctrl:checkbox property="include_empty_dates" styleClass="checkbox_filter" value="1"/>
                  </td>
                </tr>
                <tr>
                  <td>
                    <bean:message key="OrdersLogistics.by_product"/>&nbsp;
                    <ctrl:checkbox property="by_product" styleClass="checkbox_filter" value="1"/>
                  </td>
                </tr>
              </table>
            </td>
            <td width="50%">
              <table>
                <tr>
                  <td align="center" class="table-header">
                    &nbsp;&nbsp;<bean:message key="OrdersLogistics.view_columns"/>&nbsp;&nbsp;
                  </td>
                </tr>
                <tr>
                  <td align="center">
                    <table>
                      <tr>
                        <td>
                          <bean:message key="OrdersLogistics.view_weight"/>
                        </td>
                        <td width="40px" align="center">
                          <ctrl:checkbox property="view_weight" styleClass="checkbox_filter" value="1"/>
                        </td>
                      </tr>
                    </table>
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
        <table width="100%">
          <tr>
            <td width="70%" align="right" colspan=2>
              <ctrl:ubutton type="submit" dispatch="cleanAll" styleClass="width120"><bean:message key="button.clearAll"/></ctrl:ubutton>&nbsp;
              <ctrl:ubutton styleId="generateButton" type="submit" dispatch="generate" styleClass="width120"><bean:message key="button.form"/></ctrl:ubutton>
              <input type=button id="generateButtonExcel"  onclick="generateExcelClick();"  class="width165" value="<bean:message key="button.formExcel"/>">
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>

	<div class="gridBack" style="overflow:scroll;width:expression(document.body.clientWidth-15); height:expression(document.body.clientHeight-275)">
  <div>
    <grid:table property="grid" scrollableGrid="false"  nothingWasFoundMsg="msg.howToShowFilter">
      </tr><tr class="locked-header">
			<grid:column align="center"><jsp:attribute name="title"><bean:message key="OrdersLogistics.shp_doc_type_num"/></jsp:attribute></grid:column>
      <grid:column align="center"><jsp:attribute name="title"><bean:message key="OrdersLogistics.ord_conf_num"/></jsp:attribute></grid:column>
      <logic:equal name="OrdersLogistics" property="view_weight" value="1">
        <grid:column align="center"><jsp:attribute name="title"><bean:message key="OrdersLogistics.weight"/></jsp:attribute></grid:column>
      </logic:equal>
      <grid:column align="center"><jsp:attribute name="title"><bean:message key="OrdersLogistics.conf_date"/></jsp:attribute></grid:column>
      <grid:column align="center"><jsp:attribute name="title"><bean:message key="OrdersLogistics.contractor_contact_person"/></jsp:attribute></grid:column>
      <grid:column width="40%" align="center"><jsp:attribute name="title"><bean:message key="OrdersLogistics.comment"/></jsp:attribute></grid:column>
      <grid:row>
        <grid:colCustom property="shp_doc_type_num" styleClassCheckerId="style-checker"/>
        <grid:colCustom property="ord_conf_num" styleClassCheckerId="style-checker"/>
        <logic:equal name="OrdersLogistics" property="view_weight" value="1">
          <grid:colCustom align="right" property="weight_formatted" styleClassCheckerId="style-checker"/>
        </logic:equal>
        <grid:colCustom property="conf_date" styleClassCheckerId="style-checker"/>
        <grid:colCustom property="contractor_contact_person" styleClassCheckerId="style-checker"/>
        <grid:colCustom width="40%" property="comment" styleClassCheckerId="style-checker"/>
      </grid:row>
    </grid:table>
  </div>
  </div>
</ctrl:form>
<script language="JScript" type="text/javascript">
  var date_begin = document.getElementById("date_begin");
  var date_end = document.getElementById("date_end");
  var generateButton = document.getElementById("generateButton");
  var generateButtonExcel = document.getElementById("generateButtonExcel");

  enter_date();

  function enter_date()
  {
    if (
         date_begin.value != '' ||
         date_end.value != ''
       )
    {
      generateButton.disabled = false;
      generateButtonExcel.disabled = false;
    }
    else
    {
      generateButton.disabled = true;
      generateButtonExcel.disabled = true;
    }
  }

  function generateExcelClick(){
	  document.getElementById('for-insert').innerHTML='<iframe src="<ctrl:rewrite action="/OrdersLogisticsAction" dispatch="generateExcel"/>" style="display:none" />';
  }
</script>
<div id='for-insert'></div>
