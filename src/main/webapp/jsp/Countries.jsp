<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>

<div id="mergingCountryModeMessage" style="position:absolute;	visibility:hidden; border:1px solid black; background : red; width:190px;height:20px">
  <bean:message key="Countries.merge.text"/>
  <br>
</div>

<ctrl:form styleId="striped-form">
  <ctrl:hidden property="mergeTargetId"/>
  
  <table width="50%" align="center">
    <tr>
      <td>
        <div class="gridBack">
          <grid:table property="grid" key="cut_id" scrollableGrid="true" height="expression(document.body.clientHeight-235)" autoLockName="Countries">
            <logic:equal name="Countries" property="showMerge" value="true">
              <grid:column title=""/>
            </logic:equal>
            <grid:column align="center"><jsp:attribute name="title"><bean:message key="Countries.cut_name"/></jsp:attribute></grid:column>
            <logic:equal name="Countries" property="showEdit" value="true">
              <grid:column title=""/>
            </logic:equal>
            <grid:column title=""/>
            <grid:row enableSelection="isEnableCountriesSelection" selectionCallback="mergeCountries">
              <logic:equal name="Countries" property="showMerge" value="true">
                <grid:colCheckbox width="1%" result="countriesSelectedIds" style="vertical-align:top;"/>
              </logic:equal>
              <grid:colCustom property="cut_name"/>
              <logic:equal name="Countries" property="showEdit" value="true">
                <grid:colEdit width="1" action="/CountryAction.do?dispatch=edit" type="link"
                              tooltip="tooltip.Countries.edit"/>
              </logic:equal>
              <grid:colDelete width="1" action="/CountryAction.do?dispatch=delete" type="link"
                              tooltip="tooltip.Countries.delete" showCheckerId="show-delete-checker"/>
            </grid:row>
          </grid:table>
        </div>

        <div class=gridBottom>
          <logic:equal name="Countries" property="showMerge" value="true">
            <ctrl:ubutton type="script" dispatch="fakeDispatchForMerging" styleClass="width120"
                          onclick="return toggleMergingCountries();" styleId="mergeCountriesBtn" showWait="false">
              <bean:message key="button.merge"/>
            </ctrl:ubutton>
          </logic:equal>
          <ctrl:ubutton type="link" action="/CountryAction.do?dispatch=create" styleClass="width80">
            <bean:message key="button.create"/>
          </ctrl:ubutton>
        </div>
      </td>
    </tr>
  </table>
</ctrl:form>

<script type="text/javascript" language="JavaScript">
  var mergingCountriesMode = false;
  function isEnableCountriesSelection()
  {
    return mergingCountriesMode;
  }

  function toggleMergingCountries()
  {
    if (mergingCountriesMode)
    {
      mergingCountriesMode = false;
      mergingCountryModeMessage.style.visibility = 'hidden';
      document.getElementById('mergeCountriesBtn').value = '<bean:message key="button.merge"/>';
    }
    else
    {
      mergingCountriesMode = true;
      mergingCountryModeMessage.style.visibility = 'visible';
      document.getElementById('mergeCountriesBtn').value = '<bean:message key="button.dont-merge"/>';
    }
  }

  function mergeCountries(obj, id)
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
    toggleMergingCountries();
    $('mergeTargetId').value = id;
    submitDispatchForm("mergeCountries");
  }
  function handlerMM(e)
  {
    if (mergingCountriesMode)
    {
      mergingCountryModeMessage.style.left = (e) ? e.pageX : document.body.scrollLeft + event.clientX + 10;
      mergingCountryModeMessage.style.top = (e) ? e.pageY : document.body.scrollTop + event.clientY + 20;
    }
  }
  document.onmousemove = handlerMM;
</script>