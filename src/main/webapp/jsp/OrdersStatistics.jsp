<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>

<ctrl:form styleId="striped-form">

  <div id="filter-form" >

	<table id=filterTbl align="center" border="0" width="680">
    <tr>
      <td colspan=20 align="center">
        <table border="0">
          <tr>
            <td>
              &nbsp;&nbsp;
              <bean:message key="OrdersStatistics.date_begin"/>
            </td>
            <td>
              <ctrl:date property="date_begin" styleClass="filter-long" afterSelect='enter_date' endField="date_end" showHelp="false"/>
            </td>
            <td>
              &nbsp;
              <bean:message key="OrdersStatistics.date_end"/>
            </td>
            <td>
              <ctrl:date property="date_end" styleClass="filter-long" afterSelect='enter_date' startField="date_begin" showHelp="false"/>
            </td>
          </tr>
        </table>
      </td>
    </tr>

    <tr>
      <td valign="top" colspan="20" width="100%">
        <table id=filterTbl1 border="0" width="450">
          <tr>
            <td colspan="2" align="center" class="table-header">
              <bean:message key="OrdersStatistics.form_by"/>
            </td>
          </tr>
          <tr>
            <td align="right">
              <bean:message key="OrdersStatistics.department"/>
            </td>
            <td>
              <ctrl:serverList property="department.name" idName="department.id" action="/DepartmentsListAction" callback="enter_date" styleClass="filter-long" selectOnly="true" scriptUrl="have_all=true" />
            </td>
          </tr>
          <tr>
            <td align="right">
              <bean:message key="OrdersStatistics.stuffCategory"/>
            </td>
            <td>
              <ctrl:serverList property="stuffCategory.name" idName="stuffCategory.id"
                               action="/StuffCategoriesListAction" callback="enter_date" styleClass="filter-long"
                               scriptUrl="have_all=true" filter="filter"/>
            </td>
          </tr>
          <tr>
            <td align="right">
              <bean:message key="OrdersStatistics.contractor"/>
            </td>
            <td>
              <ctrl:serverList property="contractor.name" idName="contractor.id" action="/ContractorsListAction"
                               callback="enter_date" styleClass="filter-long" scriptUrl="have_all=true" filter="filter"/>
            </td>
          </tr>
          <tr>
            <td align="right">
              <bean:message key="OrdersStatistics.contractor_for"/>
            </td>
            <td>
              <ctrl:serverList property="contractor_for.name" idName="contractor_for.id" action="/ContractorsListAction"
                               callback="enter_date" styleClass="filter-long" scriptUrl="have_all=true" filter="filter"/>
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
  </div>
	<div class="gridBack" style="overflow:scroll;width:expression(document.body.clientWidth-15); height:expression(document.body.clientHeight-180)">
  <div>
    <grid:table property="grid" scrollableGrid="false"  nothingWasFoundMsg="msg.howToShowFilter">
			</tr><tr class="locked-header">
			<grid:column align="center"><jsp:attribute name="title"><bean:message key="OrdersStatistics.stf_name"/></jsp:attribute></grid:column>
      <grid:column align="center"><jsp:attribute name="title"><bean:message key="OrdersStatistics.ord_summ"/></jsp:attribute></grid:column>
      <grid:column align="center"><jsp:attribute name="title"><bean:message key="OrdersStatistics.ord_summ_sent_to_prod"/></jsp:attribute></grid:column>
      <grid:column align="center"><jsp:attribute name="title"><bean:message key="OrdersStatistics.ord_summ_part_executed"/></jsp:attribute></grid:column>
      <grid:column align="center"><jsp:attribute name="title"><bean:message key="OrdersStatistics.ord_summ_executed"/></jsp:attribute></grid:column>
      <grid:column align="center"><jsp:attribute name="title"><bean:message key="OrdersStatistics.ord_summ_executed_before"/></jsp:attribute></grid:column>
      <grid:column align="center"><jsp:attribute name="title"><bean:message key="OrdersStatistics.cur_name"/></jsp:attribute></grid:column>
      <grid:row>
        <grid:colCustom property="stf_name" styleClassCheckerId="style-checker"/>
        <grid:colCustom align="right" property="ord_summ_formatted" styleClassCheckerId="style-checker"/>
        <grid:colCustom align="right" property="ord_summ_sent_to_prod_formatted" styleClassCheckerId="style-checker"/>
        <grid:colCustom align="right" property="ord_summ_part_executed_formatted" styleClassCheckerId="style-checker"/>
        <grid:colCustom align="right" property="ord_summ_executed_formatted" styleClassCheckerId="style-checker"/>
        <grid:colCustom align="right" property="ord_summ_executed_before_formatted" styleClassCheckerId="style-checker"/>
        <grid:colCustom property="cur_name" styleClassCheckerId="style-checker"/>
      </grid:row>
    </grid:table>
  </div>
  </div>
</ctrl:form>
<script language="JScript" type="text/javascript">
  var generateButton = document.getElementById("generateButton");
  var generateButtonExcel = document.getElementById("generateButtonExcel");

  function getFormEl(form, name) {
    if (!form || !form.elements) return null;
    return form.elements[name] || null;
  }

  function enter_date()
  {
    var form = document.getElementById("striped-form") || (document.forms && document.forms[0]) || null;
    var v = function(name) {
      var el = getFormEl(form, name);
      return (el && el.value !== undefined && el.value != null) ? String(el.value).trim() : '';
    };
    var dateBegin = v("date_begin");
    var dateEnd = v("date_end");
    var depId = v("department.id");
    var stuffId = v("stuffCategory.id");
    var ctrId = v("contractor.id");
    var ctrForId = v("contractor_for.id");
    if (dateBegin !== '' && dateEnd !== '' && depId !== '' && stuffId !== '' && ctrId !== '' && ctrForId !== '')
    {
      if (generateButton) generateButton.disabled = false;
      if (generateButtonExcel) generateButtonExcel.disabled = false;
    }
    else
    {
      if (generateButton) generateButton.disabled = true;
      if (generateButtonExcel) generateButtonExcel.disabled = true;
    }
  }

  if (typeof window.addEventListener !== 'undefined') {
    window.addEventListener('DOMContentLoaded', enter_date);
  } else {
    window.attachEvent('onload', enter_date);
  }
  enter_date();

  function generateExcelClick(){
	  document.getElementById('for-insert').innerHTML='<iframe src="<ctrl:rewrite action="/OrdersStatisticsAction" dispatch="generateExcel"/>" style="display:none" />';
  }
</script>
<div id='for-insert'></div>
