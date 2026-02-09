<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>

<ctrl:form styleId="striped-form">
  <ctrl:hidden property="readOnlyForManager"/>
  <ctrl:hidden property="showForChiefDep"/>

	<div id="filter-form" >
	
	<table id=filterTbl align="center" border="0" width="750">
    <tr>
      <td colspan=20>
        <table border="0">
          <tr>
            <td>
              <bean:message key="ShippingReport.date"/>&nbsp;<bean:message key="ShippingReport.start"/>
            </td>
            <td>
              <ctrl:date property="date_begin" styleClass="filter-long" afterSelect='enter_date' endField="date_end" showHelp="false"/>
            </td>
            <td>
              <bean:message key="ShippingReport.end"/>
            </td>
            <td>
              <ctrl:date property="date_end" styleClass="filter-long" afterSelect='enter_date' startField="date_begin" showHelp="false"/>
            </td>
          </tr>
        </table>
      </td>
    </tr>

    <tr>
      <td width="50%" valign="top">
        <table id=filterTbl1 border="0" width="90%">
          <tr>
            <td colspan="2" align="center" class="table-header" width="87%">
              <bean:message key="ShippingReport.form_by"/>
            </td>
          </tr>
          <tr>
            <td align="right">
              <bean:message key="ShippingReport.user"/>
            </td>
            <logic:equal name="ShippingReport" property="showForChiefDep" value="true">
              <td>
                <ctrl:serverList property="user.userFullName" idName="user.usr_id" action="/UsersListAction"
                                 styleClass="filter-long" callback="onChangeUser" filter="filter"
                                 scriptUrl="dep_id=${ShippingReport.department.id}&have_all=true"/>
              </td>
            </logic:equal>
            <logic:notEqual name="ShippingReport" property="showForChiefDep" value="true">
              <td>
                <ctrl:serverList property="user.userFullName" idName="user.usr_id" action="/UsersListAction"
                                 styleClass="filter-long" callback="onChangeUser" filter="filter" scriptUrl="have_all=true"
                                 readonly="${ShippingReport.readOnlyForManager}"/>
              </td>
            </logic:notEqual>
          </tr>
          <tr>
            <td align="right">
              <bean:message key="ShippingReport.department"/>
            </td>
            <td>
              <ctrl:serverList property="department.name" idName="department.id" action="/DepartmentsListAction"
                               styleClass="filter-long" selectOnly="true" callback="onChangeDepartment"
                               scriptUrl="have_all=true" readonly="${ShippingReport.readOnlyForManager}"/>
            </td>
          </tr>
          <tr>
            <td align="right">
              <bean:message key="ShippingReport.contractor"/>
            </td>
            <td>
              <ctrl:serverList property="contractor.name" idName="contractor.id" action="/ContractorsListAction"
                               styleClass="filter-long" filter="filter" callback="onChangeContractor" scriptUrl="have_all=true" readonly="${ShippingReport.readOnlyForManager}"/>
            </td>
          </tr>
          <tr>
            <td align="right">
              &nbsp;
            </td>
            <td>
              &nbsp;
            </td>
            <td align="center">
              &nbsp;
            </td>
          </tr>
          <tr>
            <td align="right">
              <bean:message key="ShippingReport.onlyTotal"/>
            </td>
            <td>
              <ctrl:checkbox property="onlyTotal" styleClass="checkbox" value="1" onclick="onlyTotalOnClick()"/>
            </td>
            <td align="center">
              &nbsp;
            </td>
          </tr>
          <tr>
            <td align="right">
              <bean:message key="ShippingReport.itog_by_shp"/>
            </td>
            <td>
              <ctrl:checkbox property="itog_by_shp" styleClass="checkbox" value="1" onclick="itogByShpOnClick()"/>
            </td>
            <td align="center">
              &nbsp;
            </td>
          </tr>
          <tr>
            <td align="right">
              <bean:message key="ShippingReport.itog_by_user"/>
            </td>
            <td>
              <ctrl:checkbox property="itog_by_user" styleClass="checkbox" value="1" onclick="itogByUserOnClick()"/>
            </td>
            <td align="center">
              &nbsp;
            </td>
          </tr>
          <tr>
            <td align="right">
              <bean:message key="ShippingReport.itog_by_product"/>
            </td>
            <td>
              <ctrl:checkbox property="itog_by_product" styleClass="checkbox" value="1" onclick="itogByProductOnClick()"/>
            </td>
            <td align="center">
              &nbsp;
            </td>
          </tr>
          <tr>
            <td align="right">
              <bean:message key="ShippingReport.itog_by_produce"/>
            </td>
            <td>
              <ctrl:checkbox property="itog_by_produce" styleClass="checkbox" value="1" onclick="itogByProduceOnClick()"/>
            </td>
            <td align="center">
              &nbsp;
            </td>
          </tr>
          <tr>
            <td align="right" nowrap="nowrap">
              <bean:message key="ShippingReport.not_include_zero"/>
            </td>
            <td>
              <ctrl:checkbox property="not_include_zero" styleClass="checkbox" value="1"/>
            </td>
            <td align="center">
              &nbsp;
            </td>
          </tr>
          <tr>
            <td align="right">
              <bean:message key="ShippingReport.include"/>
            </td>
            <td>
              <bean:message key="ShippingReport.include_all"/>
              <ctrl:checkbox property="include_all" styleClass="checkbox" value="1" onclick="includeAllOnClick()"/>
              <bean:message key="ShippingReport.include_closed"/>
              <ctrl:checkbox property="include_closed" styleClass="checkbox" value="1" onclick="includeClosedOnClick()"/>
              <bean:message key="ShippingReport.include_opened"/>
              <ctrl:checkbox property="include_opened" styleClass="checkbox" value="1" onclick="includeOpenedOnClick()"/>
            </td>
            <td align="center">
              &nbsp;
            </td>
          </tr>

        </table>
      </td>
      <td width="50%" valign="top">
        <table id=viewTbl border="0">
          <tr>
            <td colspan="2" align="center"  class="table-header">
              <bean:message key="ShippingReport.view_columns"/>
            </td>
          </tr>
          <tr>
            <td align="right">
              <bean:message key="ShippingReport.view_contractor"/>
            </td>
            <td width="15%">
              <ctrl:checkbox property="view_contractor" styleClass="checkbox" value="1"/>
            </td>
          </tr>
          <tr>
            <td align="right">
              <bean:message key="ShippingReport.view_country"/>
            </td>
            <td width="15%">
              <ctrl:checkbox property="view_country" styleClass="checkbox" value="1"/>
            </td>
          </tr>
          <tr>
            <td align="right">
              <bean:message key="ShippingReport.view_contract"/>
            </td>
            <td width="15%">
              <ctrl:checkbox property="view_contract" styleClass="checkbox" value="1"/>
            </td>
          </tr>
          <tr>
            <td align="right" nowrap="nowrap">
              <bean:message key="ShippingReport.view_stuff_category"/>
            </td>
            <td width="15%">
              <ctrl:checkbox property="view_stuff_category" styleClass="checkbox" value="1"/>
            </td>
          </tr>
          <tr>
            <td align="right">
              <bean:message key="ShippingReport.view_produce"/>
            </td>
            <td width="15%">
              <ctrl:checkbox property="view_produce" styleClass="checkbox" value="1"/>
            </td>
          </tr>
          <tr>
            <td align="right">
              <bean:message key="ShippingReport.view_summ"/>
            </td>
            <td width="15%">
              <ctrl:checkbox property="view_summ" styleClass="checkbox" value="1"/>
            </td>
          </tr>
          <tr>
            <td align="right">
              <bean:message key="ShippingReport.view_summ_without_nds"/>
            </td>
            <td width="15%">
              <ctrl:checkbox property="view_summ_without_nds" styleClass="checkbox" value="1"/>
            </td>
          </tr>
          <tr>
            <td align="right">
              <bean:message key="ShippingReport.view_summ_eur"/>
            </td>
            <td width="15%">
              <ctrl:checkbox property="view_summ_eur" styleClass="checkbox" value="1"/>
            </td>
          </tr>
          <tr>
            <td align="right">
              <bean:message key="ShippingReport.view_user"/>
            </td>
            <td width="15%">
              <ctrl:checkbox property="view_user" styleClass="checkbox" value="1"/>
            </td>
          </tr>
          <tr>
            <td align="right">
              <bean:message key="ShippingReport.view_department"/>
            </td>
            <td width="15%">
              <ctrl:checkbox property="view_department" styleClass="checkbox" value="1"/>
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

  <logic:equal name="ShippingReport" property="showLegend" value="true">
    <table width="100%">
      <tr>
        <td>
          <table cellspacing=0>
            <tr>
              <td style="border-left:2px solid black;">
                &nbsp;&nbsp;<bean:message key="ShippingReport.legend"/>
              </td>
            </tr>
            <tr>
              <td style="border-left:2px solid black;color:#008000">
                &nbsp;&nbsp;<bean:message key="ShippingReport.legend_closed_shp"/>
              </td>
            </tr>
          </table>
        </td>
      </tr>
    </table>
  </logic:equal>

	<div class="gridBack" style="overflow:scroll;width:expression(document.body.clientWidth-15); height:expression(document.body.clientHeight-220)">
    <grid:table property="grid" scrollableGrid="false"  nothingWasFoundMsg="msg.howToShowFilter">
			</tr>
      <tr class="locked-header">
				<logic:equal name="ShippingReport" property="view_contractor" value="1">
          <th class="table-header"></th>
        </logic:equal>
				<logic:equal name="ShippingReport" property="view_country" value="1">
          <th class="table-header"></th>
        </logic:equal>
        <logic:equal name="ShippingReport" property="view_user_left" value="1">
          <th class="table-header"></th>
        </logic:equal>
        <logic:equal name="ShippingReport" property="view_department_left" value="1">
          <th class="table-header"></th>
        </logic:equal>
        <logic:equal name="ShippingReport" property="view_contract" value="1">
          <th class="table-header" colspan="6"><bean:message key="ShippingReport.top_contract"/></th>
        </logic:equal>
        <logic:equal name="ShippingReport" property="showShpNumDate" value="true">
          <th class="table-header"></th>
          <th class="table-header"></th>
        </logic:equal>
        <logic:equal name="ShippingReport" property="view_stuff_category" value="1">
          <th class="table-header"></th>
        </logic:equal>
        <logic:equal name="ShippingReport" property="view_produce" value="1">
          <th class="table-header" colspan="3"><bean:message key="ShippingReport.top_produce"/></th>
        </logic:equal>
        <logic:equal name="ShippingReport" property="view_summ" value="1">
          <th class="table-header"></th>
        </logic:equal>
        <logic:equal name="ShippingReport" property="view_summ_without_nds" value="1">
          <th class="table-header" colspan="2"><bean:message key="ShippingReport.top_sum_out_nds"/></th>
        </logic:equal>
        <logic:equal name="ShippingReport" property="view_summ_eur" value="1">
          <th class="table-header" colspan="3"></th>
        </logic:equal>
        <logic:equal name="ShippingReport" property="view_user" value="1">
          <th class="table-header"></th>
        </logic:equal>
        <logic:equal name="ShippingReport" property="view_department" value="1">
          <th class="table-header"></th>
        </logic:equal>
      </tr>
      <tr class="locked-header">

      <logic:equal name="ShippingReport" property="view_contractor" value="1">
        <grid:column align="center"><jsp:attribute name="title"><bean:message key="ShippingReport.contractor"/></jsp:attribute></grid:column>
      </logic:equal>
      <logic:equal name="ShippingReport" property="view_country" value="1">
        <grid:column align="center"><jsp:attribute name="title"><bean:message key="ShippingReport.country"/></jsp:attribute></grid:column>
      </logic:equal>
      <logic:equal name="ShippingReport" property="view_user_left" value="1">
        <grid:column align="center"><jsp:attribute name="title"><bean:message key="ShippingReport.manager"/></jsp:attribute></grid:column>
      </logic:equal>
      <logic:equal name="ShippingReport" property="view_department_left" value="1">
        <grid:column align="center"><jsp:attribute name="title"><bean:message key="ShippingReport.department"/></jsp:attribute></grid:column>
      </logic:equal>
      <logic:equal name="ShippingReport" property="view_contract" value="1">
        <grid:column align="center"><jsp:attribute name="title"><bean:message key="ShippingReport.con_number"/></jsp:attribute></grid:column>
        <grid:column align="center"><jsp:attribute name="title"><bean:message key="ShippingReport.con_date"/></jsp:attribute></grid:column>
        <grid:column align="center"><jsp:attribute name="title"><bean:message key="ShippingReport.spc_number"/></jsp:attribute></grid:column>
        <grid:column align="center"><jsp:attribute name="title"><bean:message key="ShippingReport.spc_date"/></jsp:attribute></grid:column>
        <grid:column align="center"><jsp:attribute name="title"><bean:message key="ShippingReport.spc_summ"/></jsp:attribute></grid:column>
        <grid:column align="center"><jsp:attribute name="title"><bean:message key="ShippingReport.currency"/></jsp:attribute></grid:column>
      </logic:equal>
      <logic:equal name="ShippingReport" property="showShpNumDate" value="true">
        <grid:column align="center"><jsp:attribute name="title"><bean:message key="ShippingReport.shp_number"/></jsp:attribute></grid:column>
        <grid:column align="center"><jsp:attribute name="title"><bean:message key="ShippingReport.shp_date"/></jsp:attribute></grid:column>
      </logic:equal>
      <logic:equal name="ShippingReport" property="view_stuff_category" value="1">
        <grid:column align="center"><jsp:attribute name="title"><bean:message key="ShippingReport.stf_name"/></jsp:attribute></grid:column>
      </logic:equal>
      <logic:equal name="ShippingReport" property="view_produce" value="1">
        <grid:column align="center"><jsp:attribute name="title"><bean:message key="ShippingReport.produce_full_name"/></jsp:attribute></grid:column>
        <grid:column align="center"><jsp:attribute name="title"><bean:message key="ShippingReport.ctn_number"/></jsp:attribute></grid:column>
        <grid:column align="center"><jsp:attribute name="title"><bean:message key="ShippingReport.lps_count"/></jsp:attribute></grid:column>
      </logic:equal>
      <logic:equal name="ShippingReport" property="view_summ" value="1">
        <grid:column align="center"><jsp:attribute name="title"><bean:message key="ShippingReport.sumPlusNds"/></jsp:attribute></grid:column>
      </logic:equal>
      <logic:equal name="ShippingReport" property="view_summ_without_nds" value="1">
        <grid:column align="center"><jsp:attribute name="title"><bean:message key="ShippingReport.lps_summ_out_nds"/></jsp:attribute></grid:column>
        <grid:column align="center"><jsp:attribute name="title"><bean:message key="ShippingReport.lps_summ_out_nds_eur"/></jsp:attribute></grid:column>
      </logic:equal>
      <logic:equal name="ShippingReport" property="view_summ_eur" value="1">
        <grid:column align="center"><jsp:attribute name="title"><bean:message key="ShippingReport.lps_summ_zak"/></jsp:attribute></grid:column>
        <grid:column align="center"><jsp:attribute name="title"><bean:message key="ShippingReport.lps_sum_transport"/></jsp:attribute></grid:column>
        <grid:column align="center"><jsp:attribute name="title"><bean:message key="ShippingReport.lps_custom"/></jsp:attribute></grid:column>
      </logic:equal>
      <logic:equal name="ShippingReport" property="view_user" value="1">
        <grid:column align="center"><jsp:attribute name="title"><bean:message key="ShippingReport.manager"/></jsp:attribute></grid:column>
      </logic:equal>
      <logic:equal name="ShippingReport" property="view_department" value="1">
        <grid:column align="center"><jsp:attribute name="title"><bean:message key="ShippingReport.department"/></jsp:attribute></grid:column>
      </logic:equal>
      <grid:row>
        <logic:equal name="ShippingReport" property="view_contractor" value="1">
          <grid:colCustom property="shp_contractor" styleClassCheckerId="style-checker"/>
        </logic:equal>
        <logic:equal name="ShippingReport" property="view_country" value="1">
          <grid:colCustom property="country" styleClassCheckerId="style-checker"/>
        </logic:equal>
        <logic:equal name="ShippingReport" property="view_user_left" value="1">
          <grid:colCustom property="manager" styleClassCheckerId="style-checker"/>
        </logic:equal>
        <logic:equal name="ShippingReport" property="view_department_left" value="1">
          <grid:colCustom property="department" styleClassCheckerId="style-checker"/>
        </logic:equal>
        <logic:equal name="ShippingReport" property="view_contract" value="1">
          <grid:colCustom property="con_number" styleClassCheckerId="style-checker"/>
          <grid:colCustom property="con_date_formatted" styleClassCheckerId="style-checker"/>
          <grid:colCustom property="spc_number" styleClassCheckerId="style-checker"/>
          <grid:colCustom property="spc_date_formatted" styleClassCheckerId="style-checker"/>
          <grid:colCustom property="spc_summ_formatted" align="right" styleClassCheckerId="style-checker" showCheckerId="show-checker"/>
          <grid:colCustom property="currency" styleClassCheckerId="style-checker"/>
        </logic:equal>
        <logic:equal name="ShippingReport" property="showShpNumDate" value="true">
          <grid:colCustom property="shp_number" styleClassCheckerId="style-checker"/>
          <grid:colCustom property="shp_date_formatted" styleClassCheckerId="style-checker"/>
        </logic:equal>
        <logic:equal name="ShippingReport" property="view_stuff_category" value="1">
          <grid:colCustom property="stf_name" styleClassCheckerId="style-checker"/>
        </logic:equal>
        <logic:equal name="ShippingReport" property="view_produce" value="1">
          <grid:colCustom property="produce_full_name" styleClassCheckerId="style-checker"/>
          <grid:colCustom property="ctn_number" styleClassCheckerId="style-checker"/>
          <grid:colCustom property="lps_count_formatted" align="right" styleClassCheckerId="style-checker" showCheckerId="show-checker"/>
        </logic:equal>
        <logic:equal name="ShippingReport" property="view_summ" value="1">
          <grid:colCustom property="sumPlusNdsFormatted" align="right" styleClassCheckerId="style-checker"/>
        </logic:equal>
        <logic:equal name="ShippingReport" property="view_summ_without_nds" value="1">
          <grid:colCustom property="lps_summ_out_nds_formatted" align="right" styleClassCheckerId="style-checker"/>
          <grid:colCustom property="lps_summ_out_nds_eur_formatted" align="right" styleClassCheckerId="style-checker"/>
        </logic:equal>
        <logic:equal name="ShippingReport" property="view_summ_eur" value="1">
          <grid:colCustom property="lps_summ_zak_formatted" align="right" styleClassCheckerId="style-checker"/>
          <grid:colCustom property="lps_sum_transport_formatted" align="right" styleClassCheckerId="style-checker"/>
          <grid:colCustom property="lps_custom_formatted" align="right" styleClassCheckerId="style-checker"/>
        </logic:equal>
        <logic:equal name="ShippingReport" property="view_user" value="1">
          <grid:colCustom property="manager" styleClassCheckerId="style-checker"/>
        </logic:equal>
        <logic:equal name="ShippingReport" property="view_department" value="1">
          <grid:colCustom property="department" styleClassCheckerId="style-checker"/>
        </logic:equal>
      </grid:row>
    </grid:table>
  </div>
</ctrl:form>

<script language="JScript" type="text/javascript">
  var view_stuff_category = document.getElementById("view_stuff_category");
  var view_produce = document.getElementById("view_produce");
  var view_user = document.getElementById("view_user");
  var view_department = document.getElementById("view_department");

  var user = document.getElementById("user.userFullName");
  var userId = document.getElementById("user.usr_id");
  var department = document.getElementById("department.name");
  var departmentId = document.getElementById("department.id");
  var contractor = document.getElementById("contractor.name");
  var contractorId = document.getElementById("contractor.id");
  var onlyTotal = document.getElementById("onlyTotal");
  var itog_by_shp = document.getElementById("itog_by_shp");
  var itog_by_user = document.getElementById("itog_by_user");
  var itog_by_product = document.getElementById("itog_by_product");
  var itog_by_produce = document.getElementById("itog_by_produce");

  var date_begin = document.getElementById("date_begin");
  var date_end = document.getElementById("date_end");
  var generateButton = document.getElementById("generateButton");
  var generateButtonExcel = document.getElementById("generateButtonExcel");

  var include_all = document.getElementById("include_all");
  var include_closed = document.getElementById("include_closed");
  var include_opened = document.getElementById("include_opened");

  enter_date();
  onlyTotalOnClick();

  function onChangeUser()
  {
<logic:notEqual name="ShippingReport" property="showForChiefDep" value="true">
    department.value = '';
    departmentId.value = '';
    contractor.value = '';
    contractorId.value = '';
</logic:notEqual>

	  onlyTotalOnClick();
    enter_date();
  }

  function onChangeDepartment()
  {
    user.value = '';
    userId.value = '';
    contractor.value = '';
    contractorId.value = '';

	  onlyTotalOnClick();
    enter_date();
  }

  function onChangeContractor()
  {
    user.value = '';
    userId.value = '';
    department.value = '';
    departmentId.value = '';

	  onlyTotalOnClick();
    enter_date();
  }

  function onlyTotalOnClick()
  {
    if ( onlyTotal.checked == true )
    {
      if ( user.value == '' &&
           department.value == '' &&
           contractor.value == '' )
      {
        onlyTotal.checked = false;
      }
      else
      {
        itog_by_shp.checked = false;
      }
    }

    itogByShpOnClick();
  }

  function checkAllView()
  {
    view_stuff_category.checked = true;
    view_produce.checked = true;
    view_user.checked = true;
    view_department.checked = true;
  }

  function itogByShpOnClick()
  {
    if ( itog_by_shp.checked == true )
    {
      onlyTotal.checked = false;
      itog_by_user.disabled = false;
      itog_by_product.disabled = false;
      itog_by_produce.disabled = false;

      view_stuff_category.checked = false;
      view_produce.checked = false;
      view_user.checked = false;
      view_department.checked = false;
    }
    else
    {
      itog_by_user.checked = false;
      itog_by_user.disabled = true;
      itog_by_product.checked = false;
      itog_by_product.disabled = true;
      itog_by_produce.checked = false;
      itog_by_produce.disabled = true;
    }
  }

  function itogByUserOnClick()
  {
    if ( itog_by_user.checked == true )
    {
      itog_by_product.checked = false;
      itog_by_produce.checked = false;

      checkAllView();
      view_stuff_category.checked = false;
      view_produce.checked = false;
      view_department.checked = false;
    }
  }

  function itogByProductOnClick()
  {
    if ( itog_by_product.checked == true )
    {
      itog_by_user.checked = false;
      itog_by_produce.checked = false;

      checkAllView();
      view_produce.checked = false;
      view_user.checked = false;
      view_department.checked = false;
    }
  }

  function itogByProduceOnClick()
  {
    if ( itog_by_produce.checked == true )
    {
      itog_by_user.checked = false;
      itog_by_product.checked = false;

      checkAllView();
      view_stuff_category.checked = false;
      view_user.checked = false;
      view_department.checked = false;
    }
  }

  function includeAllOnClick()
  {
    if ( include_all.checked == true )
    {
      include_closed.checked = false;
      include_opened.checked = false;
    }

    enter_date();
  }

  function includeClosedOnClick()
  {
    if ( include_closed.checked == true )
    {
      include_all.checked = false;
      include_opened.checked = false;
    }

    enter_date();
  }

  function includeOpenedOnClick()
  {
    if ( include_opened.checked == true )
    {
      include_all.checked = false;
      include_closed.checked = false;
    }

    enter_date();
  }

  function enter_date()
  {
    if (
         date_begin.value != '' &&
         date_end.value != '' &&
         (
           user.value != '' ||
           department.value != '' ||
           contractor.value != ''
         ) &&
         (
           include_all.checked ||
           include_closed.checked ||
           include_opened.checked 
         )
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
	  document.getElementById('for-insert').innerHTML='<iframe src="<ctrl:rewrite action="/ShippingReportAction" dispatch="generateExcel"/>" style="display:none" />';
  }
</script>
<div id='for-insert'></div>
