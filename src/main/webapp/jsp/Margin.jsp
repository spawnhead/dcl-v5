<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%
  String ctx = request.getContextPath();
  String gridUrl = response.encodeURL(ctx + "/test/MarginReportGridStandalone.jsp");
  String sessionId = (session != null) ? session.getId() : null;
  if (sessionId != null && gridUrl.indexOf(";jsessionid=") == -1)
  {
    int q = gridUrl.indexOf('?');
    if (q >= 0)
    {
      gridUrl = gridUrl.substring(0, q) + ";jsessionid=" + sessionId + gridUrl.substring(q);
    }
    else
    {
      gridUrl = gridUrl + ";jsessionid=" + sessionId;
    }
  }
%>

<ctrl:form>
  <ctrl:hidden property="readOnlyForManager"/>
  <ctrl:hidden property="showForChiefDep"/>

	<div id="filter-form" >

	<table id=filterTbl align="center" border="0" width="100%">
    <tr>
      <td colspan=20>
        <table border="0">
          <tr>
            <td>
              <bean:message key="Margin.date"/>&nbsp;<bean:message key="Margin.start"/>
            </td>
            <td>
              <ctrl:date property="date_begin" styleClass="filter-long" afterSelect='enterDate'
                         onchange="enterDate();" endField="date_end" showHelp="false"/>
            </td>
            <td>
              <bean:message key="Margin.end"/>
            </td>
            <td>
              <ctrl:date property="date_end" styleClass="filter-long" afterSelect='enterDate'
                         onchange="enterDate();" startField="date_begin" showHelp="false"/>
            </td>
          </tr>
        </table>
      </td>
    </tr>

    <tr>
      <td width="70%" valign="top">
        <table id=filterTbl1 border="0" width="100%">
          <tr>
            <td colspan="2" align="center" class="table-header" width="87%">
              <bean:message key="Margin.form_by"/>
            </td>
            <td class="table-header" align="center" nowrap="nowrap">
              <bean:message key="Margin.aspect_by"/>
            </td>
          </tr>

          <tr>
            <td align="right">
              <bean:message key="Margin.user"/>
            </td>
            <logic:equal name="Margin" property="showForChiefDep" value="true">
              <td>
                <ctrl:serverList property="user.userFullName" idName="user.usr_id" action="/UsersListAction"
                                 styleClass="filter-long" callback="onChangeUser" filter="filter"
                                 scriptUrl="dep_id=${Margin.department.id}&have_all=true"/>
              </td>
            </logic:equal>
            <logic:notEqual name="Margin" property="showForChiefDep" value="true">
              <td>
                <ctrl:serverList property="user.userFullName" idName="user.usr_id" action="/UsersListAction"
                                 styleClass="filter-long" callback="onChangeUser" filter="filter" scriptUrl="have_all=true"
                                 readonly="${Margin.readOnlyForManager}"/>
              </td>
            </logic:notEqual>
            <td align="center">
              <ctrl:checkbox property="user_aspect" styleClass="checkbox" value="1" onclick="userAspectOnClick()"
                             disabled="${Margin.userDisabled}"/>
            </td>
          </tr>
          <tr>
            <td align="right">
              <bean:message key="Margin.department"/>
            </td>
            <td>
              <ctrl:serverList property="department.name" idName="department.id" action="/DepartmentsListAction"
                               styleClass="filter-long" selectOnly="true" callback="onChangeDepartment"
                               scriptUrl="have_all=true" readonly="${Margin.readOnlyForManager}"/>
            </td>
            <td align="center">
              <ctrl:checkbox property="department_aspect" styleClass="checkbox" value="1"
                             onclick="departmentAspectOnClick()" disabled="${Margin.departmentDisabled}"/>
            </td>
          </tr>
          <tr>
            <td align="right">
              <bean:message key="Margin.contractor"/>
            </td>
            <td>
              <ctrl:serverList property="contractor.name" idName="contractor.id" action="/ContractorsListAction"
                               styleClass="filter-long" filter="filter" callback="onChangeContractor"
                               scriptUrl="have_all=true" readonly="${Margin.readOnlyForManager}"/>
            </td>
            <td align="center">
              <ctrl:checkbox property="contractor_aspect" styleClass="checkbox" value="1"
                             onclick="contractorAspectOnClick()" disabled="${Margin.contractorDisabled}"/>
            </td>
          </tr>
          <tr>
            <td align="right">
              <bean:message key="Margin.stuffCategory"/>
            </td>
            <td>
              <ctrl:serverList property="stuffCategory.name" idName="stuffCategory.id"
                               action="/StuffCategoriesListAction" styleClass="filter-long" callback="onChangeStuffCategory"
                               scriptUrl="have_all=true" filter="filter" readonly="${Margin.readOnlyForManager}"/>
            </td>
            <td align="center">
              <ctrl:checkbox property="stuff_category_aspect" styleClass="checkbox" value="1"
                             onclick="stuffCategoryAspectOnClick()"
                             disabled="${Margin.stuff_categoryDisabled}"/>
            </td>
          </tr>
          <tr>
            <td align="right">
              <bean:message key="Margin.route"/>
            </td>
            <td>
              <ctrl:serverList property="route.name" idName="route.id" action="/RoutesListAction"
                               styleClass="filter-long" selectOnly="true" callback="onChangeRoute"
                               scriptUrl="have_all=true" readonly="${Margin.readOnlyForManager}"/>
            </td>
            <td align="center">
              <ctrl:checkbox property="route_aspect" styleClass="checkbox" value="1"
                             onclick="routeAspectOnClick()" disabled="${Margin.routeDisabled}"/>
            </td>
          </tr>

          <tr>
            <td colspan="3">
              &nbsp;
            </td>
          </tr>

          <tr>
            <td align="right">
              <bean:message key="Margin.onlyTotal"/>
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
              <bean:message key="Margin.itog_by_spec"/>
            </td>
            <td>
              <ctrl:checkbox property="itog_by_spec" styleClass="checkbox" value="1" onclick="itogBySpecOnClick()"/>
            </td>
            <td align="center">
              &nbsp;
            </td>
          </tr>

          <tr>
            <td align="right">
              <bean:message key="Margin.itog_by_user"/>
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
              <bean:message key="Margin.itog_by_product"/>
            </td>
            <td>
              <ctrl:checkbox property="itog_by_product" styleClass="checkbox" value="1"/>
            </td>
            <td align="center">
              &nbsp;
            </td>
          </tr>

          <tr>
            <td align="right" nowrap="nowrap">
              <bean:message key="Margin.get_not_block"/>
            </td>
            <td>
              <ctrl:checkbox property="get_not_block" styleClass="checkbox" value="1"/>
            </td>
            <td align="center">
              &nbsp;
            </td>
          </tr>
        </table>
      </td>
      
      <td width="30%">
        <table id=viewTbl border="0" width="100%">
          <tr>
            <td colspan="2" align="center"  class="table-header">
              <bean:message key="Margin.view_columns"/>
            </td>
          </tr>
          <tr>
            <td align="right">
              <bean:message key="Margin.view_contractor"/>
            </td>
            <td width="15%">
              <ctrl:checkbox property="view_contractor" styleClass="checkbox" value="1"/>
            </td>
          </tr>
          <tr>
            <td align="right">
              <bean:message key="Margin.view_country"/>
            </td>
            <td width="15%">
              <ctrl:checkbox property="view_country" styleClass="checkbox" value="1"/>
            </td>
          </tr>
          <tr>
            <td align="right">
              <bean:message key="Margin.view_contract"/>
            </td>
            <td width="15%">
              <ctrl:checkbox property="view_contract" styleClass="checkbox" value="1"/>
            </td>
          </tr>
          <tr>
            <td align="right">
              <bean:message key="Margin.view_stuff_category"/>
            </td>
            <td width="15%">
              <ctrl:checkbox property="view_stuff_category" styleClass="checkbox" value="1"/>
            </td>
          </tr>
          <tr>
            <td align="right">
              <bean:message key="Margin.view_shipping"/>
            </td>
            <td width="15%">
              <ctrl:checkbox property="view_shipping" styleClass="checkbox" value="1"/>
            </td>
          </tr>
          <tr>
            <td align="right">
              <bean:message key="Margin.view_payment"/>
            </td>
            <td width="15%">
              <ctrl:checkbox property="view_payment" styleClass="checkbox" value="1"/>
            </td>
          </tr>
          <tr>
            <td align="right">
              <bean:message key="Margin.view_transport"/>
            </td>
            <td width="15%">
              <ctrl:checkbox property="view_transport" styleClass="checkbox" value="1"/>
            </td>
          </tr>
          <tr>
            <td align="right" nowrap="nowrap">
              <bean:message key="Margin.view_transport_sum"/>
            </td>
            <td width="15%">
              <ctrl:checkbox property="view_transport_sum" styleClass="checkbox" value="1"/>
            </td>
          </tr>
          <tr>
            <td align="right">
              <bean:message key="Margin.view_custom"/>
            </td>
            <td width="15%">
              <ctrl:checkbox property="view_custom" styleClass="checkbox" value="1"/>
            </td>
          </tr>
          <tr>
            <td align="right">
              <bean:message key="Margin.view_other_sum"/>
            </td>
            <td width="15%">
              <ctrl:checkbox property="view_other_sum" styleClass="checkbox" value="1" readonly="${Margin.logisticsReadOnly}"/>
            </td>
          </tr>
          <tr>
            <td align="right">
              <bean:message key="Margin.view_montage_sum"/>
            </td>
            <td width="15%">
              <ctrl:checkbox property="view_montage_sum" styleClass="checkbox" value="1"/>
            </td>
          </tr>
          <tr>
            <td align="right">
              <bean:message key="Margin.view_montage_time"/>
            </td>
            <td width="15%">
              <ctrl:checkbox property="view_montage_time" styleClass="checkbox" value="1"/>
            </td>
          </tr>
          <tr>
            <td align="right">
              <bean:message key="Margin.view_montage_cost"/>
            </td>
            <td width="15%">
              <ctrl:checkbox property="view_montage_cost" styleClass="checkbox" value="1"/>
            </td>
          </tr>
          <tr>
            <td align="right">
              <bean:message key="Margin.view_update_sum"/>
            </td>
            <td width="15%">
              <ctrl:checkbox property="view_update_sum" styleClass="checkbox" value="1"/>
            </td>
          </tr>
          <tr>
            <td align="right">
              <bean:message key="Margin.view_summ_zak"/>
            </td>
            <td width="15%">
              <ctrl:checkbox property="view_summ_zak" styleClass="checkbox" value="1"/>
            </td>
          </tr>
          <tr>
            <td align="right">
              <bean:message key="Margin.view_koeff"/>
            </td>
            <td width="15%">
              <ctrl:checkbox property="view_koeff" styleClass="checkbox" value="1"/>
            </td>
          </tr>
          <tr>
            <td align="right">
              <bean:message key="Margin.view_user"/>
            </td>
            <td width="15%">
              <ctrl:checkbox property="view_user" styleClass="checkbox" value="1"/>
            </td>
          </tr>
          <tr>
            <td align="right">
              <bean:message key="Margin.view_department"/>
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

<script type="text/javascript">(function(){ var f=document.getElementById('filter-form'); if(f){ f.style.display='block'; f.style.position='static'; f.style.top=''; f.style.left=''; } })();</script>

  <iframe
    id="marginReportGridFrame"
    src="<%= gridUrl %>"
    style="width:100%; height:calc(100vh - 220px); min-height:650px; border:0; background:#fff;"
    loading="lazy"
    referrerpolicy="no-referrer"
  ></iframe>
  <script>
  (function() {
    var f = document.getElementById('marginReportGridFrame');
    function resize() {
      if (!f) return;
      var rect = f.getBoundingClientRect();
      var vh = window.innerHeight || document.documentElement.clientHeight || 0;
      var h = vh - rect.top - 16;
      if (h < 650) h = 650;
      f.style.height = h + 'px';
    }
    if (window.addEventListener) {
      window.addEventListener('resize', resize);
    }
    resize();
  })();
  </script>
</ctrl:form>
<script language="JScript" type="text/javascript">
  var user_aspect = document.getElementById("user_aspect");
  var department_aspect = document.getElementById("department_aspect");
  var contractor_aspect = document.getElementById("contractor_aspect");
  var stuff_category_aspect = document.getElementById("stuff_category_aspect");
  var route_aspect = document.getElementById("route_aspect");

  var user = document.getElementById("user.userFullName");
  var userId = document.getElementById("user.usr_id");
  var department = document.getElementById("department.name");
  var departmentId = document.getElementById("department.id");
  var contractor = document.getElementById("contractor.name");
  var contractorId = document.getElementById("contractor.id");
  var stuffCategory = document.getElementById("stuffCategory.name");
  var stuffCategoryId = document.getElementById("stuffCategory.id");
  var route = document.getElementById("route.name");
  var routeId = document.getElementById("route.id");

  var onlyTotal = document.getElementById("onlyTotal");
  var itog_by_spec = document.getElementById("itog_by_spec");
  var itog_by_user = document.getElementById("itog_by_user");
  var itog_by_product = document.getElementById("itog_by_product");

  var date_begin = document.getElementById("date_begin");
  var date_end = document.getElementById("date_end");
  var generateButton = document.getElementById("generateButton");
  var generateButtonExcel = document.getElementById("generateButtonExcel");

  enterDate();
  onlyTotalOnClick();

  function userAspectOnClick()
  {
    if ( user_aspect.checked )
    {
      department_aspect.checked = false;
      contractor_aspect.checked = false;
      stuff_category_aspect.checked = false;
      route_aspect.checked = false;
    }
  }

  function departmentAspectOnClick()
  {
    if ( department_aspect.checked )
    {
      user_aspect.checked = false;
      contractor_aspect.checked = false;
      stuff_category_aspect.checked = false;
      route_aspect.checked = false;
    }
  }

  function contractorAspectOnClick()
  {
    if ( contractor_aspect.checked )
    {
      user_aspect.checked = false;
      department_aspect.checked = false;
      stuff_category_aspect.checked = false;
      route_aspect.checked = false;
    }
  }

  function stuffCategoryAspectOnClick()
  {
    if ( stuff_category_aspect.checked )
    {
      user_aspect.checked = false;
      department_aspect.checked = false;
      contractor_aspect.checked = false;
      route_aspect.checked = false;
    }
  }

  function routeAspectOnClick()
  {
    if ( route_aspect.checked )
    {
      user_aspect.checked = false;
      department_aspect.checked = false;
      contractor_aspect.checked = false;
      stuff_category_aspect.checked = false;
    }
  }

  function AllEnable()
  {
    user_aspect.disabled = false;
    department_aspect.disabled = false;
    contractor_aspect.disabled = false;
    stuff_category_aspect.disabled = false;
    route_aspect.disabled = false;
  }

  function onChangeUser()
  {
<logic:notEqual name="Margin" property="showForChiefDep" value="true">
    AllEnable();
    user_aspect.disabled = true;
</logic:notEqual>
    user_aspect.checked = false;

<logic:notEqual name="Margin" property="showForChiefDep" value="true">
    department.value = '';
    departmentId.value = '';
    contractor.value = '';
    contractorId.value = '';

    stuffCategory.value = ''; //задизэйблено - объект не создался если showForChiefDep == true
</logic:notEqual>
    stuffCategoryId.value = '';
    route.value = '';
    routeId.value = '';

	  onlyTotalOnClick();
    enterDate();
  }

  function onChangeDepartment()
  {
    AllEnable();
    department_aspect.disabled = true;
    department_aspect.checked = false;

    user.value = '';
    userId.value = '';
    contractor.value = '';
    contractorId.value = '';
    stuffCategory.value = '';
    stuffCategoryId.value = '';
    route.value = '';
    routeId.value = '';

	  onlyTotalOnClick();
    enterDate();
  }

  function onChangeContractor()
  {
    AllEnable();
    contractor_aspect.disabled = true;
    contractor_aspect.checked = false;

    user.value = '';
    userId.value = '';
    department.value = '';
    departmentId.value = '';
    stuffCategory.value = '';
    stuffCategoryId.value = '';
    route.value = '';
    routeId.value = '';

	  onlyTotalOnClick();
    enterDate();
  }

  function onChangeStuffCategory()
  {
    AllEnable();
    stuff_category_aspect.disabled = true;
    stuff_category_aspect.checked = false;

    user.value = '';
    userId.value = '';
    department.value = '';
    departmentId.value = '';
    contractor.value = '';
    contractorId.value = '';
    route.value = '';
    routeId.value = '';

	  onlyTotalOnClick();
    enterDate();
  }

  function onChangeRoute()
  {
    AllEnable();
    route_aspect.disabled = true;
    route_aspect.checked = false;

    user.value = '';
    userId.value = '';
    department.value = '';
    departmentId.value = '';
    contractor.value = '';
    contractorId.value = '';
    stuffCategory.value = '';
    stuffCategoryId.value = '';

	  onlyTotalOnClick();
    enterDate();
  }

  function onlyTotalOnClick()
  {
    if ( onlyTotal.checked )
    {
      if ( user.value == '' &&
           department.value == '' &&
           contractor.value == '' &&
           stuffCategory.value == '' &&
           route.value == '' )
      {
        onlyTotal.checked = false;
      }
      else
      {
        itog_by_spec.checked = false;
      }
    }

    itogBySpecOnClick();
  }

  function itogBySpecOnClick()
  {
    if ( itog_by_spec.checked )
    {
      onlyTotal.checked = false;
      itog_by_user.disabled = false;
    }
    else
    {
      itog_by_user.checked = false;
      itog_by_user.disabled = true;
    }

    itogByUserOnClick();
  }

  function itogByUserOnClick()
  {
    if ( itog_by_user.checked )
    {
      itog_by_product.disabled = false;
    }
    else
    {
      itog_by_product.checked = false;
      itog_by_product.disabled = true;
    }
  }

  function enterDate()
  {
		if (
         date_begin.value != '' &&
         date_end.value != '' &&
         (
           user.value != '' ||
           department.value != '' ||
           contractor.value != '' ||
           stuffCategory.value != '' ||
           route.value != ''
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
  function generateExcelClick()
  {
	  document.getElementById('for-insert').innerHTML='<iframe src="<ctrl:rewrite action="/MarginAction" dispatch="generateExcel"/>" style="display:none" />';
  }
</script>
<div id='for-insert'></div>
