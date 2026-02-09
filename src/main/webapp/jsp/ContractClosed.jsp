  <%@ taglib uri="/tags/struts-bean" prefix="bean" %>
  <%@ taglib uri="/tags/struts-html" prefix="html" %>
  <%@ taglib uri="/tags/struts-logic" prefix="logic" %>
  <%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
  <%@ taglib uri="/tags/html-grid" prefix="grid" %>

  <ctrl:form readonly="${ContractClosed.formReadOnly}">
  <ctrl:hidden property="ctc_id"/>
  <ctrl:hidden property="ctc_block"/>
  <ctrl:hidden property="is_new_doc"/>
  <ctrl:hidden property="usr_date_create"/>
  <ctrl:hidden property="usr_date_edit"/>
  <ctrl:hidden property="createUser.usr_id"/>
  <ctrl:hidden property="editUser.usr_id"/>
  <table class=formBackTop align="center" width="99%">
    <tr>
      <td>
        <table cellspacing="0" width="100%" >
          <logic:notEqual name="ContractClosed" property="is_new_doc" value="true">
            <tr>
              <td>
                <table width="100%">
                  <tr>
                    <td width="10%"><bean:message key="ContractClosed.create"/></td>
                    <td width="40%"><ctrl:write name="ContractClosed" property="usr_date_create"/> <ctrl:write name="ContractClosed" property="createUser.userFullName"/> </td>
                    <td align="right"><bean:message key="ContractClosed.edit"/></td>
                    <td width="33%">&nbsp;&nbsp;&nbsp;&nbsp;<ctrl:write name="ContractClosed" property="usr_date_edit"/> <ctrl:write name="ContractClosed" property="editUser.userFullName"/></td>
                  </tr>
                </table>
              </td>
            </tr>
          </logic:notEqual>
          <tr>
            <td>
              <table width="100%">
                <tr>
                  <td width="10%"><bean:message key="ContractClosed.ctc_date"/></td>
                  <td><ctrl:date property="ctc_date" style="width:150px;" onchange="changeContractClosedDate()" afterSelect="changeContractClosedDate"/></td>
                </tr>
              </table>
            </td>
          </tr>

          <logic:notEqual name="ContractClosed" property="is_new_doc" value="true">
            <tr>
              <td>
                <table width="100%">
                  <tr>
                    <td width="10%"><bean:message key="ContractClosed.ctc_number"/></td>
                    <td> <ctrl:write name="ContractClosed" property="ctc_number"/> </td>
                  </tr>
                </table>
              </td>
            </tr>
          </logic:notEqual>

          <tr>
            <td id="closedContractRecords">
	            <script type="text/javascript" language="JScript">
                  doAjax({
                    synchronous:true,
                    url:'<ctrl:rewrite action="/ContractClosedAction" dispatch="ajaxClosedContractRecordsGrid"/>',
                    update:'closedContractRecords'
                  });
              </script>
            </td>
          </tr>
          <tr>
            <td colspan="2" align="right" class=formSpace>
              &nbsp;
            </td>
          </tr>
          <tr>
            <td colspan="2" align="right">
	            <input type='button' onclick='deleteSelected()' class='width145' value='<bean:message key="button.deleteSelected"/>'/>
              <ctrl:ubutton type="submit" dispatch="back" styleClass="width80" readonly="false">
                <bean:message key="button.cancel"/>
              </ctrl:ubutton>
              <ctrl:ubutton type="submit" dispatch="process" styleClass="width80"  >
                <bean:message key="button.save"/>
              </ctrl:ubutton>
              <ctrl:ubutton type="submit" dispatch="processBlock" styleClass="width165" >
                <bean:message key="button.saveBlockClosedContract"/>
              </ctrl:ubutton>
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
  </ctrl:form>

<script language="JScript" type="text/javascript">
	function getCheckboxes1stColumn()
	{
		return $$('#contractsGrid td.first-column input.grid-checkbox');
	}

	function changeGridSelection(origObj)
	{
		var chs = getCheckboxes1stColumn();
		var i = 0;
		for (; i < chs.length; i++)
		{
			if (!$(chs[i]).disabled)
			{
				$(chs[i]).checked = origObj.checked;
			}
		}
	}

	function changeContractClosedDate(ctcDate)
	{
		doAjax({
			url: '<ctrl:rewrite action="/ContractClosedAction" dispatch="ajaxChangeContractClosedDate"/>',
			params:{'ctcDate':document.getElementById('ctc_date').value},
			synchronous: true,
			update: 'closedContractRecords'
		});
	}

	function removeFromClosedRecords(number)
	{
		doAjax({
			url: '<ctrl:rewrite action="/ContractClosedAction" dispatch="ajaxDeleteClosedRecord"/>',
			params: {'number': number},
			synchronous: true,
			update: 'closedContractRecords'
		});
	}

	function deleteSelected(number)
	{
		var params = $H(Form.serializeElements($$('#closedContractRecords input'),true));

		doAjax({
			url: '<ctrl:rewrite action="/ContractClosedAction" dispatch="ajaxDeleteSelected"/>',
			params:params,
			synchronous: true,
			update: 'closedContractRecords'
		});
	}

</script>

