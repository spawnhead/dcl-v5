<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>

<ctrl:form styleId="striped-form">

  <table id=filterTbl align="center" border="0" width="100%">
    <tr>
      <td colspan=20>
        <table border="0">
          <tr>
            <td>
              <bean:message key="ProduceCostReport.date_begin"/>
            </td>
            <td>
              <ctrl:date property="date_begin" styleClass="filter-long" afterSelect='enter_date' endField="date_end" showHelp="false"/>
            </td>
            <td>
              &nbsp;&nbsp;&nbsp;&nbsp;<bean:message key="ProduceCostReport.date_end"/>
            </td>
            <td>
              <ctrl:date property="date_end" styleClass="filter-long" afterSelect='enter_date' startField="date_begin" showHelp="false"/>
            </td>
          </tr>
        </table>
      </td>
    </tr>
    <tr>
      <td>
        <table width="100%">
          <tr>
            <td>
              <bean:message key="ProduceCostReport.by_month"/>&nbsp;
              <ctrl:checkbox property="by_month" styleClass="checkbox_filter" value="1"/>
            </td>
          </tr>
        </table>
      </td>
    </tr>
    <tr>
      <td>
        <table width="100%">
          <tr>
            <td>
              <bean:message key="ProduceCostReport.only_block"/>&nbsp;
              <ctrl:checkbox property="only_block" styleClass="checkbox_filter" value="1"/>
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
              <ctrl:ubutton type="submit" dispatch="cleanAll" styleClass="width120"><bean:message key="button.clearAll"/></ctrl:ubutton>&nbsp;
              <ctrl:ubutton styleId="generateButton" type="submit" dispatch="generate" styleClass="width120"><bean:message key="button.form"/></ctrl:ubutton>
              <input type=button id="generateButtonExcel"  onclick="generateExcelClick();"  class="width165" value="<bean:message key="button.formExcel"/>">
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>

	<div class="gridBack" style="overflow:scroll;width:expression(document.body.clientWidth-15); height:expression(document.body.clientHeight-310)">
  <div>
    <grid:table property="grid" scrollableGrid="false" nothingWasFoundMsg="msg.howToShowFilter">
      </tr><tr class="locked-header">
			<grid:column align="center"><jsp:attribute name="title"><bean:message key="ProduceCostReport.prc_date"/></jsp:attribute></grid:column>
      <logic:equal name="ProduceCostReport" property="showNumberRoute" value="true">
        <grid:column align="center"><jsp:attribute name="title"><bean:message key="ProduceCostReport.prc_number"/></jsp:attribute></grid:column>
        <grid:column align="center"><jsp:attribute name="title"><bean:message key="ProduceCostReport.route"/></jsp:attribute></grid:column>
      </logic:equal>
      <grid:column align="center"><jsp:attribute name="title"><bean:message key="ProduceCostReport.weight"/></jsp:attribute></grid:column>
      <grid:column align="center"><jsp:attribute name="title"><bean:message key="ProduceCostReport.transport"/></jsp:attribute></grid:column>
      <grid:column align="center"><jsp:attribute name="title"><bean:message key="ProduceCostReport.custom"/></jsp:attribute></grid:column>
      <grid:row>
        <grid:colCustom property="prc_date_formatted" styleClassCheckerId="style-checker"/>
        <logic:equal name="ProduceCostReport" property="showNumberRoute" value="true">
          <grid:colCustom property="prc_number" styleClassCheckerId="style-checker"/>
          <grid:colCustom property="route" styleClassCheckerId="style-checker"/>
        </logic:equal>
        <grid:colCustom align="right" property="weightFormatted" styleClassCheckerId="style-checker"/>
        <grid:colCustom align="right" property="transportFormatted" styleClassCheckerId="style-checker"/>
        <grid:colCustom align="right" property="customFormatted" styleClassCheckerId="style-checker"/>
      </grid:row>
    </grid:table>
  </div>
  </div>
</ctrl:form>
<script language="JScript" type="text/javascript">
  var date_begin = document.getElementById('date_begin');
  var date_end = document.getElementById('date_end');

  var generateButton = document.getElementById('generateButton');
  var generateButtonExcel = document.getElementById('generateButtonExcel');

  enter_date();

  function enter_date()
  {
    if (
         date_begin.value != '' &&
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

  function generateExcelClick()
  {
	  document.getElementById('for-insert').innerHTML='<iframe src="<ctrl:rewrite action="/ProduceCostReportAction" dispatch="generateExcel"/>" style="display:none" />';
  }
</script>

<div id='for-insert'></div>
