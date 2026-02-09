<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>

<ctrl:askUser name="ask_block" key="msg.shipping.block" showOkCancel="false" height="120"/>
<ctrl:askUser name="ask_unblock" key="msg.shipping.unblock" showOkCancel="false" height="140"/>

<ctrl:form styleId="striped-form">
  <table id=filterTbl style="" align="center" border="0" width="100%">
    <tr>
      <td colspan="3"></td>
      <td align="right"><bean:message key="Shippings.date"/></td>
      <td colspan="3" align="right"><bean:message key="Shippings.sum"/></td>
    </tr>

    <tr>
      <td align="right"><bean:message key="Shippings.number"/></td>
      <td><ctrl:text property="number" styleClass="filter"/></td>
      <td width="20">&nbsp;</td>

      <td align="right"><bean:message key="Shippings.start"/></td>
      <td ><ctrl:date property="date_begin" styleClass="filter" endField="date_end" showHelp="false"/></td>
      <td width="20">&nbsp;</td>

      <td align="right"><bean:message key="Shippings.min"/></td>
      <td >&nbsp;&nbsp;&nbsp;<ctrl:text property="sum_min_formatted" styleClass="filter" showHelp="false"/></td>
      <td width="20">&nbsp;</td>
    </tr>

    <tr>
      <td align="right"><bean:message key="Shippings.contractor"/></td>
      <td><ctrl:serverList property="contractor.name" idName="contractor.id" action="/ContractorsListAction" styleClass="filter" /></td>
      <td width="20">&nbsp;</td>

      <td align="right"><bean:message key="Shippings.end"/></td>
      <td ><ctrl:date property="date_end" styleClass="filter" startField="date_begin" showHelp="false"/></td>
      <td width="20">&nbsp;</td>

      <td align="right"><bean:message key="Shippings.max"/></td>
      <td >&nbsp;&nbsp;&nbsp;<ctrl:text property="sum_max_formatted" styleClass="filter" showHelp="false"/></td>
      <td width="20">&nbsp;</td>
    </tr>

    <tr>
      <td align="right"><bean:message key="Shippings.currency"/></td>
      <td><ctrl:serverList property="currency.name" idName="currency.id" action="/CurrenciesListAction"  styleClass="filter" selectOnly="true"/></td>
      <td width="20">&nbsp;</td>

      <td colspan="3">
        <table align="center" width="100%">
          <tr>
            <td>
              <bean:message key="Shippings.closed_closed"/>&nbsp;
              <ctrl:checkbox property="closed_closed" styleClass="checkbox_filter" value="1" onclick="closedClosedOnClick()"/>&nbsp;
              <bean:message key="Shippings.closed_open"/>&nbsp;
              <ctrl:checkbox property="closed_open" styleClass="checkbox_filter" value="1" onclick="closedOpenOnClick()"/>&nbsp;
              <bean:message key="Shippings.closed_all"/>&nbsp;
              <ctrl:checkbox property="closed_all" styleClass="checkbox_filter" value="1" onclick="closedAllOnClick()"/>&nbsp;
            </td>
          </tr>
        </table>
      </td>

      <td align="right"><bean:message key="Shippings.seller"/></td>
      <td>
        <ctrl:serverList property="seller.name" idName="seller.id"
                         action="/SellersListAction"  styleClass="filter"/>
      </td>
    </tr>

    <tr>
      <td align="right" colspan=20>
        <ctrl:ubutton type="submit" dispatch="input" styleClass="width120"><bean:message key="button.clearFilter"/></ctrl:ubutton>&nbsp;
        <ctrl:ubutton type="submit" dispatch="filter" styleClass="width120"><bean:message key="button.applyFilter"/></ctrl:ubutton>
      </td>
    </tr>
  </table>

  <div class="gridBack" >
    <grid:table property="grid" key="shp_id" height="expression(document.body.clientHeight-360)" autoLockName="Shipping" >
      <grid:column width="10%" align="center"><jsp:attribute name="title"><bean:message key="Shippings.number"/></jsp:attribute></grid:column>
      <grid:column width="10%" align="center"><jsp:attribute name="title"><bean:message key="Shippings.date"/></jsp:attribute></grid:column>
      <grid:column align="center"><jsp:attribute name="title"><bean:message key="Shippings.contractor"/></jsp:attribute></grid:column>
      <logic:equal name="Shippings" property="closed_open" value="1">
        <grid:column width="20%" align="center"><jsp:attribute name="title"><bean:message key="Shippings.shp_expiration"/></jsp:attribute></grid:column>
      </logic:equal>
      <grid:column width="10%" align="center"><jsp:attribute name="title"><bean:message key="Shippings.shp_summ_plus_nds"/></jsp:attribute></grid:column>
      <grid:column width="5%" align="center"><jsp:attribute name="title"><bean:message key="Shippings.currency"/></jsp:attribute></grid:column>
      <grid:column align="center" width="1%"><jsp:attribute name="title"><img title='<bean:message key="tooltip.Shippings.block_status"/>' src='<ctrl:rewrite page="/images/lock.gif"/>'></jsp:attribute></grid:column>
      <grid:column title="" width="1%"/>
      <grid:row>
        <grid:colCustom width="10%" property="shp_number"/>
        <grid:colCustom width="10%" property="shp_date_date"/>
        <grid:colCustom property="shp_contractor"/>
        <logic:equal name="Shippings" property="closed_open" value="1">
          <grid:colCustom width="20%" property="shp_expiration"/>
        </logic:equal>
        <grid:colCustom width="10%" align="right" property="shp_summ_plus_nds_formatted"/>
        <grid:colCustom width="5%" align="center" property="shp_currency"/>
        <grid:colCheckbox align="center" width="1%" property="shp_block" askUser="${record.msg_check_block}"
                          readonlyCheckerId="blockChecker" type="submit" dispatch="block"
                          scriptUrl="shp_block=${record.shp_block}"/>
        <grid:colEdit width="1%" action="/ShippingAction.do?dispatch=edit" type="link" tooltip="tooltip.Shippings.edit"
                      readonlyCheckerId="editChecker"/>
      </grid:row>
    </grid:table>
  </div>

  <div class=gridBottom>
    <ctrl:ubutton type="link" action="/ShippingAction.do?dispatch=input" styleClass="width80" >
      <bean:message key="button.create"/>
    </ctrl:ubutton>
  </div>
</ctrl:form>

<script language="JScript" type="text/javascript">
  var closed_closed = document.getElementById('closed_closed');
  var closed_open = document.getElementById('closed_open');
  var closed_all = document.getElementById('closed_all');

  function closedClosedOnClick()
  {
    if ( closed_closed.checked )
    {
      closed_open.checked = false;
      closed_all.checked = false;
    }
    else
    {
      closed_closed.checked = true;
    }
  }

  function closedOpenOnClick()
  {
    if ( closed_open.checked )
    {
      closed_closed.checked = false;
      closed_all.checked = false;
    }
    else
    {
      closed_open.checked = true;
    }
  }

  function closedAllOnClick()
  {
    if ( closed_all.checked )
    {
      closed_open.checked = false;
      closed_closed.checked = false;
    }
    else
    {
      closed_all.checked = true;
    }
  }
</script>