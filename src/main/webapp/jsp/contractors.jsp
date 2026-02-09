<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>

<div id='for-insert'></div>

<div id="mergingContractorsModeMessage" style="position:absolute;	visibility:hidden; border:1px solid black; background : red; width:190px;height:20px">
  <bean:message key="Contractors.merge.text"/>
  <br>
</div>

<ctrl:askUser name="ask_block" key="msg.contractors.msg_block" showOkCancel="false" height="120"/>
<ctrl:askUser name="ask_unblock" key="msg.contractors.msg_unblock" showOkCancel="false" height="140"/>

<ctrl:form styleId="striped-form">
  <ctrl:hidden property="mergeTargetId"/>

  <table id=filterTbl style="" align="center" border="0" width="100%">
    <tr>
      <td align="right"><bean:message key="Contractors.ctr_name"/></td>
      <td><ctrl:text property="ctr_name_journal" styleClass="filter"/></td>
      <td width="20">&nbsp;</td>

      <td align="right"><bean:message key="Contractors.ctr_full_name"/></td>
      <td><ctrl:text property="ctr_full_name_journal" styleClass="filter"/></td>
      <td width="20">&nbsp;</td>

      <td align="right"><bean:message key="Contractors.ctr_account"/></td>
      <td><ctrl:text property="ctr_account_journal" styleClass="filter"/></td>
      <td width="20">&nbsp;</td>

      <td align="right"><bean:message key="Contractors.ctr_address"/></td>
      <td><ctrl:text property="ctr_address_journal" styleClass="filter"/></td>
    </tr>
    
    <tr>
      <td align="right"><bean:message key="Contractors.user"/></td>
	    <td><ctrl:serverList property="user.userFullName" idName="user.usr_id" action="/UsersListAction" styleClass="filter" filter="filter"/></td>
      <td width="20">&nbsp;</td>

      <td align="right"><bean:message key="Contractors.department"/></td>
      <td><ctrl:serverList property="department.name" idName="department.id" action="/DepartmentsListAction" styleClass="filter"/></td>
      <td width="20">&nbsp;</td>

      <td align="right"><bean:message key="Contractors.ctr_email"/></td>
      <td><ctrl:text property="ctr_email_journal" styleClass="filter"/></td>
      <td width="20">&nbsp;</td>
      
      <td align="right"><bean:message key="Contractors.ctr_unp"/></td>
      <td><ctrl:text property="ctr_unp_journal" styleClass="filter"/></td>
    </tr>

    <tr>
    </tr>

    <tr>
      <td align="right" colspan=20>
        <ctrl:ubutton type="submit" dispatch="input" styleClass="width120"><bean:message key="button.clearFilter"/></ctrl:ubutton>&nbsp;
        <ctrl:ubutton type="submit" dispatch="filter" styleClass="width120"><bean:message key="button.applyFilter"/></ctrl:ubutton>
      </td>
    </tr>
  </table>

  <div class="gridBack" id="contractorsGrid">
    <grid:table property="grid" key="ctr_id" height="expression(document.body.clientHeight-255)" autoLockName="Contractor">
      <logic:equal name="contractors" property="adminOrOtherUserInMinsk" value="1">
        <grid:column width="1"><jsp:attribute name="title"><input type="checkbox" id="mainCheckbox" class="grid-header-checkbox" onclick="changeGridSelection(this)" ></jsp:attribute></grid:column>
      </logic:equal>
      <grid:column width="20%" align="center"><jsp:attribute name="title"><bean:message key="Contractors.ctr_name"/></jsp:attribute></grid:column>
      <grid:column align="center"><jsp:attribute name="title"><bean:message key="Contractors.ctr_full_name"/></jsp:attribute></grid:column>
      <grid:column width="20%" align="center"><jsp:attribute name="title"><bean:message key="Contractors.ctr_address"/></jsp:attribute></grid:column>
      <grid:column width="5%" align="center"><jsp:attribute name="title"><bean:message key="Contractors.ctr_phone"/></jsp:attribute></grid:column>
      <grid:column width="5%" align="center"><jsp:attribute name="title"><bean:message key="Contractors.ctr_fax"/></jsp:attribute></grid:column>
      <grid:column width="8%" align="center"><jsp:attribute name="title"><bean:message key="Contractors.ctr_email"/></jsp:attribute></grid:column>
      <grid:column width="15%" align="center"><jsp:attribute name="title"><bean:message key="Contractors.ctr_bank_props"/></jsp:attribute></grid:column>
      <grid:column width="1%" title=""/>
      <grid:column align="center" width="1%"><jsp:attribute name="title"><img title='<bean:message key="tooltip.Contractors.block_status"/>' src='<ctrl:rewrite page="/images/lock.gif"/>'></jsp:attribute></grid:column>
      <grid:column width="1%" title=""/>
      <logic:equal name="contractors" property="admin" value="1">
        <grid:column width="1%" title=""/>
      </logic:equal>
      <grid:row enableSelection="isEnableContractorsSelection" selectionCallback="mergeContractors">
        <logic:equal name="contractors" property="adminOrOtherUserInMinsk" value="1">
          <grid:colCheckbox width="1%" property="selectedItem" resultProperty="selectedItem" result="grid" useIndexAsPK="true" styleClass="first-column"/>
        </logic:equal>
        <grid:colCustom width="20%" property="ctr_name"/>
        <grid:colCustom property="ctr_full_name"/>
        <grid:colCustom width="20%" property="ctr_address"/>
        <grid:colCustom width="5%"  property="ctr_phone"/>
        <grid:colCustom width="5%"  property="ctr_fax"/>
        <grid:colCustom width="8%">
          <a href="mailto:${record.ctr_email}">${record.ctr_email}</a>
        </grid:colCustom>
        <grid:colCustom width="15%" property="ctr_bank_props"/>
        <grid:colLink  width="1%" action="/ContractorAction.do?dispatch=editContactPersons" type="link" >
          <bean:message key="Contractors.contact_persons"/>
        </grid:colLink>
        <grid:colCheckbox align="center"  width="1%" property="ctr_block" askUser="${record.msg_check_block}" readonlyCheckerId="blockChecker" type="submit" dispatch="block" scriptUrl="block=${record.ctr_block}&ctr_id_journal=${record.ctr_id}"/>
        <grid:colEdit width="1%" action="/ContractorAction.do?dispatch=edit" type="link" tooltip="tooltip.Contractors.edit"/>
        <logic:equal name="contractors" property="admin" value="1">
          <grid:colDelete  width="1%" action="/ContractorAction.do?dispatch=delete" type="link" tooltip="tooltip.Contractors.delete" showCheckerId="show-delete-checker"/>
        </logic:equal>
      </grid:row>
    </grid:table>
  </div>

  <table align="center" border="0" width="100%">
    <tr>
      <td>
        <div class=gridBottomLeft>
          <logic:equal name="contractors" property="adminOrOtherUserInMinsk" value="1">
            <input type=button onclick="clearSelection();"  class="width80" value="<bean:message key="button.clear"/>">
          </logic:equal>
        </div>
      </td>
      <td>
        <div class=gridBottom>
          <logic:equal name="contractors" property="adminOrOtherUserInMinsk" value="1">
            <ctrl:ubutton type="submit" dispatch="print" styleClass="width165" readonly="false">
              <bean:message key="button.printAddress"/>
            </ctrl:ubutton>
            &nbsp;
            <ctrl:text property="printScale" style="width:40px;text-align:right;" readonly="false"/>
            <bean:message key="Common.percent"/>
            &nbsp;&nbsp;
          </logic:equal>
          <logic:equal name="contractors" property="admin" value="1">
            <input type=button id="generateButtonExcel"  onclick="generateExcelClick();"  class="width165" value="<bean:message key="button.formExcel"/>">
            <ctrl:ubutton type="script" dispatch="fakeDispatchForMerging" styleClass="width120"
                          onclick="return toggleMergingContractors();" styleId="mergeContractorsBtn" showWait="false">
              <bean:message key="button.merge"/>
            </ctrl:ubutton>
          </logic:equal>
          <ctrl:ubutton type="link" action="/ContractorAction.do?dispatch=create" styleClass="width80">
            <bean:message key="button.create"/>
          </ctrl:ubutton>
        </div>
      </td>
    </tr>
  </table>
</ctrl:form>

<script type="text/javascript" language="JavaScript">

  var mergingContractorsMode = false;
  function isEnableContractorsSelection()
  {
    return mergingContractorsMode;
  }

  function toggleMergingContractors()
  {
    if (mergingContractorsMode)
    {
      mergingContractorsMode = false;
      mergingContractorsModeMessage.style.visibility = 'hidden';
      document.getElementById('mergeContractorsBtn').value = '<bean:message key="button.merge"/>';
    }
    else
    {
      mergingContractorsMode = true;
      mergingContractorsModeMessage.style.visibility = 'visible';
      document.getElementById('mergeContractorsBtn').value = '<bean:message key="button.dont-merge"/>';
    }
  }

  function mergeContractors(obj, id)
  {
    var evt = window.event || null;
    var target = evt ? (evt.target || evt.srcElement) : null;
    if (target && target.type == 'checkbox')
    {
      return;
    }
    if (obj.children[0].children[0].checked)
    {
      return;
    }
    toggleMergingContractors();
    $('mergeTargetId').value = id;
    submitDispatchForm("mergeContractors");
  }
  
  function handlerMM(e)
  {
    if (mergingContractorsMode)
    {
      mergingContractorsModeMessage.style.left = (e) ? e.pageX : document.body.scrollLeft + event.clientX + 10;
      mergingContractorsModeMessage.style.top = (e) ? e.pageY : document.body.scrollTop + event.clientY + 20;
    }
  }
  document.onmousemove = handlerMM;

  function generateExcelClick()
  {
	  document.getElementById('for-insert').innerHTML='<iframe src="<ctrl:rewrite action="/ContractorsAction" dispatch="generateExcel"/>" style="display:none" />';
  }

  function getCheckboxes1stColumnEven()
  {
    return $$('#contractorsGrid td.first-column input.grid-checkbox-even');
  }
  function getCheckboxes1stColumnNotEven()
  {
    return $$('#contractorsGrid td.first-column input.grid-checkbox-not-even');
  }

  function changeGridSelection(origObj)
  {
    var chs = getCheckboxes1stColumnEven();
    var i = 0;
    for (; i < chs.length; i++)
    {
      if ( !$(chs[i]).disabled )
      {
       $(chs[i]).checked = origObj.checked;
      }
    }

    chs = getCheckboxes1stColumnNotEven();
    i = 0;
    for (; i < chs.length; i++)
    {
      if ( !$(chs[i]).disabled )
      {
       $(chs[i]).checked = origObj.checked;
      }
    }
  }

  function clearSelection()
  {
    var mainCheckbox = document.getElementById('mainCheckbox');
    mainCheckbox.checked = false;

    var chs = getCheckboxes1stColumnEven();
    var i = 0;
    for (; i < chs.length; i++)
    {
      if ( !$(chs[i]).disabled )
      {
       $(chs[i]).checked = false;
      }
    }

    chs = getCheckboxes1stColumnNotEven();
    i = 0;
    for (; i < chs.length; i++)
    {
      if ( !$(chs[i]).disabled )
      {
       $(chs[i]).checked = false;
      }
    }
  }
</script>

<logic:equal name="contractors" property="print" value="true">
  <script language="JScript" type="text/javascript" >
	  document.getElementById('for-insert').innerHTML='<iframe src="<ctrl:rewrite action="ContractorsPrintAction" scriptUrl="printScale=${contractors.printScale}"/>" style="display:none" />';
  </script>
</logic:equal>
