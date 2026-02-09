<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>

<ctrl:form readonly="${Timeboard.formReadOnly}">
<ctrl:hidden property="tmb_id"/>
<ctrl:hidden property="tmb_checked_old"/>
<table class=formBackTop align="center" width="99%">
  <tr>
    <td>
      <table width="100%" cellspacing="0" >
        <logic:notEqual name="Timeboard" property="is_new_doc" value="true">
          <tr>
            <td>
              <table width="100%">
                <tr>
                  <td width="130px"><bean:message key="Timeboard.create"/></td>
                  <td><ctrl:write name="Timeboard" property="usr_date_create"/> <ctrl:write name="Timeboard" property="createUser.userFullName"/> </td>
                </tr>
              </table>
            </td>
          </tr>
          <tr>
            <td>
              <table width="100%">
                <tr>
                  <td width="130px"><bean:message key="Timeboard.edit"/></td>
                  <td><ctrl:write name="Timeboard" property="usr_date_edit"/> <ctrl:write name="Timeboard" property="editUser.userFullName"/></td>
                </tr>
              </table>
            </td>
          </tr>
        </logic:notEqual>

        <tr>
          <td>
            <table cellspacing="0" >
              <tr>
                <td width="110px"><bean:message key="Timeboard.tmb_user"/></td>
                <td><ctrl:serverList property="user.userFullName" idName="user.usr_id" action="/UsersListAction"
                                    styleClass="filter" selectOnly="true"/></td>
              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td>
            <table cellspacing="0" >
              <tr>
                <td width="110px"><bean:message key="Timeboard.tmb_date"/></td>
                <td><ctrl:date property="tmb_date_formatted" style="width:190px;" afterSelect='onDateEnter'
                               onlyHeader="true" readonly="${Timeboard.dateReadOnly}"/></td>
              </tr>
            </table>
          </td>
        </tr>

        <tr>
          <td>
            <div class="gridBackNarrow" id="worksGrid">
              <grid:table property="gridWorks" key="number">
                <th class="table-header" rowspan="3"><input type="checkbox" class="grid-header-checkbox" onclick="changeGridSelection(this)" ></th>
                <th class="table-header" rowspan="3"><bean:message key="TimeboardWorks.tbw_date"/></th>
                <th class="table-header" rowspan="3"><bean:message key="TimeboardWorks.tbw_day_of_week"/></th>
                <th class="table-header" colspan="5"><bean:message key="TimeboardWorks.tbw_time"/></th>
                <th class="table-header" rowspan="3"><bean:message key="TimeboardWorks.tbw_crq_number"/></th>
                <th class="table-header" rowspan="3"><bean:message key="TimeboardWorks.tbw_work_type"/></th>
                <th class="table-header" rowspan="3"><bean:message key="TimeboardWorks.tbw_contractor"/></th>
                <th class="table-header" rowspan="3"><bean:message key="TimeboardWorks.tbw_equipment"/></th>
                <th class="table-header" rowspan="3"><bean:message key="TimeboardWorks.tbw_comment"/></th>
                <th class="table-header" rowspan="3"></th>
                <th class="table-header" rowspan="3"></th>
                <th class="table-header" rowspan="3"></th>
                </tr><tr class="locked-header1">
                <th class="table-header" rowspan="2"><bean:message key="TimeboardWorks.tbw_from"/></th>
                <th class="table-header" rowspan="2"><bean:message key="TimeboardWorks.tbw_to"/></th>
                <th class="table-header" colspan="3"><bean:message key="TimeboardWorks.tbw_hours"/></th>
                </tr><tr class="locked-header1">
                <grid:column width="5%" align="center"><jsp:attribute name="title"><bean:message key="TimeboardWorks.tbw_hours_all"/></jsp:attribute></grid:column>
                <grid:column width="5%" align="center"><jsp:attribute name="title"><bean:message key="TimeboardWorks.tbw_hours_update"/></jsp:attribute></grid:column>
                <grid:column width="5%" align="center"><jsp:attribute name="title"><bean:message key="TimeboardWorks.tbw_hours_total"/></jsp:attribute></grid:column>
                <grid:row>
                  <grid:colCheckbox  width="1%" property="selectLine" result="gridWorks" resultProperty="selectLine"
                                     useIndexAsPK="true" showCheckerId="show-checker" styleClass="first-column"
                                     styleClassCheckerId="style-checker"/>
                  <grid:colCustom width="5%" property="tbw_date_formatted" styleClassCheckerId="style-checker"/>
                  <grid:colCustom width="5%" align="center" property="tbw_day_of_week" showCheckerId="show-checker" styleClassCheckerId="style-checker"/>
                  <grid:colCustom width="5%" align="right" property="tbw_from" styleClassCheckerId="style-checker"/>
                  <grid:colCustom width="5%" align="right" property="tbw_to" styleClassCheckerId="style-checker"/>
                  <grid:colCustom width="5%" align="right" property="tbw_hours_all_out" styleClassCheckerId="style-checker"/>
                  <grid:colCustom width="5%" align="right" property="tbw_hours_update_formatted" showCheckerId="show-checker" styleClassCheckerId="style-checker"/>
                  <grid:colCustom width="10%" align="right" property="tbw_hours_total_out" styleClassCheckerId="style-checker"/>
                  <grid:colCustom width="10%" property="contractorRequest.crqNumberDeliver" styleClassCheckerId="style-checker"/>
                  <grid:colCustom width="8%" property="contractorRequest.requestType.nameById" styleClassCheckerId="style-checker"/>
                  <grid:colCustom width="9%" property="contractorRequest.contractor.name" styleClassCheckerId="style-checker"/>
                  <grid:colCustom width="13%" property="contractorRequest.crq_equipment" styleClassCheckerId="style-checker"/>
                  <grid:colCustom property="tbw_comment" styleClassCheckerId="style-checker"/>
                  <grid:colImage width="1" dispatch="cloneWork" type="submit" tooltip="tooltip.TimeboardWorks.clone"
                                 image="images/copy.gif" styleClass="grid-image-without-border"
                                 showCheckerId="show-checker" styleClassCheckerId="styleCheckerImage"
                                 scriptUrl="tmb_date_formatted=$(tmb_date_formatted)"/>
                  <grid:colEdit width="1" dispatch="editWork" type="submit" tooltip="tooltip.TimeboardWorks.edit"
                                showCheckerId="show-checker" styleClassCheckerId="style-checker"
                                scriptUrl="tmb_date_formatted=$(tmb_date_formatted)" readonlyCheckerId="editReadOnly"/>
                  <grid:colDelete width="1" dispatch="deleteWork" type="submit" tooltip="tooltip.TimeboardWorks.delete"
                                  showCheckerId="show-checker" styleClassCheckerId="style-checker"/>
                </grid:row>
              </grid:table>
            </div>

            <div class=gridBottom>
              <table width="100%">
                <tr>
                  <td align="left">
                    <table width="50%">
                      <tr>
                        <td align="left">
                          <ctrl:ubutton type="submit" dispatch="selectContractorRequest" styleClass="nowidth" readonly="${Timeboard.linkReadOnly}">
                            <bean:message key="button.linkToContractorRequest"/>
                          </ctrl:ubutton>
                        </td>
                      </tr>
                    </table>
                  </td>
                  <td align="right" colspan="1">
                    <ctrl:ubutton styleId="addWork" type="submit" dispatch="newWork" styleClass="width80" scriptUrl="tmb_date_formatted=$(tmb_date_formatted)">
                      <bean:message key="button.add"/>
                    </ctrl:ubutton>
                  </td>
                </tr>
              </table>
            </div>
          <td/>
        </tr>

        <tr>
          <td>
            <table width="100%">
              <tr>
                <td>
                  <bean:message key="Timeboard.tmb_checked"/>&nbsp;
                  <ctrl:checkbox property="tmb_checked" styleClass="checkbox" value="1" readonly="${Timeboard.tmbCheckedReadOnly}"/>
                  <logic:equal name="Timeboard" property="showCheckedUser" value="true">
                    <ctrl:write name="Timeboard" property="tmb_checked_date"/> <ctrl:write name="Timeboard" property="checkedUser.userFullName"/>
                  </logic:equal>
                </td>
              </tr>
            </table>
          </td>
        </tr>

        <tr>
          <td colspan="2" align="right" class=formSpace>
            &nbsp;
          </td>
        </tr>
        <tr>
          <td colspan="2" align="right">
            <ctrl:ubutton type="submit" dispatch="back" styleClass="width80" readonly="false">
              <bean:message key="button.cancel"/>
            </ctrl:ubutton>
            <input id='saveButton' type='button' onclick='processClick()' class='width80' value='<bean:message key="button.save"/>'/>
          </td>
        </tr>

      </table>
    </td>
  </tr>
</table>
</ctrl:form>

<script language="JScript" type="text/javascript">
  var saveButton = document.getElementById("saveButton");
  saveButton.disabled = ${Timeboard.saveReadOnly};
  
  var tmb_checked = document.getElementById("tmb_checked");
  var tmb_date = document.getElementById("tmb_date_formatted");
  var addWork = document.getElementById['addWork'];

  <logic:notEqual name="Timeboard" property="formReadOnly" value="true">
    onDateEnter();
  </logic:notEqual>

  function onDateEnter()
  {
    addWork.disabled = (tmb_date.value == '');
  }

  function getCheckboxes1stColumn()
  {
    return $$('#worksGrid td.first-column input.grid-checkbox');
  }

  function changeGridSelection(origObj)
  {
    var chs = getCheckboxes1stColumn();
    var i = 0;
    for (; i<chs.length; i++)
    {
      if ( !$(chs[i]).disabled )
      {
       $(chs[i]).checked = origObj.checked;
      }
    }
  }

  function processClick()
  {
    <logic:notEqual name="Timeboard" property="saveReadOnly" value="true">
      if ( tmb_checked.checked )
      {
        if (isUserAgree('<bean:message key="msg.timeboard.timeboard_check"/>', true, 500, 140))
        {
          submitDispatchForm("process");
        }
      }
      else
      {
        submitDispatchForm("process");
      }
    </logic:notEqual>
  }
</script>