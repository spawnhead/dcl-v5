<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>

<ctrl:form styleId="striped-form">
  <table id=filterTbl style="" align="center" border="0" width="100%">
    <tr>
      <td colspan="3"></td>
      <td align="right"><bean:message key="Payments.date"/></td>
      <td colspan="2"></td>
      <td align="right"><bean:message key="Payments.sum"/></td>
    </tr>
    <tr>
      <td align="right"><bean:message key="Payments.contractor"/></td>
      <td><ctrl:serverList property="contractor.name" idName="contractor.id" action="/ContractorsListAction" styleClass="filter" scriptUrl="have_empty=true"/></td>
      <td width="20">&nbsp;</td>

      <td align="right"><bean:message key="Payments.start"/></td>
      <td ><ctrl:date property="date_begin" styleClass="filter" endField="date_end" showHelp="false"/></td>
      <td width="20">&nbsp;</td>

      <td align="right"><bean:message key="Payments.min"/></td>
      <td><ctrl:text property="sum_min_formatted" styleClass="filter" showHelp="false"/></td>
    </tr>
    <tr>
      <td align="right"><bean:message key="Payments.currency"/></td>
      <td><ctrl:serverList property="currency.name" idName="currency.id" action="/CurrenciesListAction" styleClass="filter" selectOnly="true"/></td>
      <td width="20">&nbsp;</td>

      <td align="right"><bean:message key="Payments.end"/></td>
      <td ><ctrl:date property="date_end" styleClass="filter" startField="date_begin" showHelp="false"/></td>
      <td width="20">&nbsp;</td>

      <td align="right"><bean:message key="Payments.max"/></td>
      <td><ctrl:text property="sum_max_formatted" styleClass="filter" showHelp="false"/></td>
    </tr>

    <tr>
      <td align="right"><bean:message key="Payments.block"/></td>
      <td><ctrl:checkbox property="block" styleClass="checkbox_filter" value="1"/></td>
      <td></td>
      <td></td>

      <td colspan="2">
        <table align="center" width="100%">
          <tr>
            <td>
              <bean:message key="Payments.closed_closed"/>&nbsp;
              <ctrl:checkbox property="closed_closed" styleClass="checkbox_filter" value="1" onclick="closed_closedOnClick()"/>&nbsp;
              <bean:message key="Payments.closed_open"/>&nbsp;
              <ctrl:checkbox property="closed_open" styleClass="checkbox_filter" value="1" onclick="closed_openOnClick()"/>&nbsp;
              <bean:message key="Payments.closed_all"/>&nbsp;
              <ctrl:checkbox property="closed_all" styleClass="checkbox_filter" value="1" onclick="closed_allOnClick()"/>&nbsp;
            </td>
          </tr>
        </table>
      </td>

      <td align="right"><bean:message key="Payments.pay_account"/></td>
      <td><ctrl:text property="pay_account" styleClass="filter"/></td>
    </tr>

    <tr>
      <td align="right" colspan=20>
        <ctrl:ubutton type="submit" dispatch="input" styleClass="width120"><bean:message key="button.clearFilter"/></ctrl:ubutton>&nbsp;
        <ctrl:ubutton type="submit" dispatch="filter" styleClass="width120"><bean:message key="button.applyFilter"/></ctrl:ubutton>
      </td>
    </tr>
  </table>
  <div class="gridBack" >
    <grid:table property="grid" key="pay_id" scrollableGrid="true" height="expression(document.body.clientHeight-360)" autoLockName="Payment" >
      <grid:column align="center" width="5%"><jsp:attribute name="title"><bean:message key="Payments.date"/></jsp:attribute></grid:column>
      <grid:column align="center"><jsp:attribute name="title"><bean:message key="Payments.contractor"/></jsp:attribute></grid:column>
      <grid:column align="center" width="15%"><jsp:attribute name="title"><bean:message key="Payments.pay_account"/></jsp:attribute></grid:column>
      <grid:column align="center" width="10%"><jsp:attribute name="title"><bean:message key="Payments.sum"/></jsp:attribute></grid:column>
      <grid:column align="center" width="5%"><jsp:attribute name="title"><bean:message key="Payments.currency"/></jsp:attribute></grid:column>
      <grid:column align="center" width="5%"><jsp:attribute name="title"><bean:message key="Payments.pay_course"/></jsp:attribute></grid:column>
      <grid:column align="center" width="7%"><jsp:attribute name="title"><bean:message key="Payments.pay_summ_eur"/></jsp:attribute></grid:column>
      <grid:column align="center" width="5%"><jsp:attribute name="title"><bean:message key="Payments.pay_course_nbrb"/>&nbsp;<ctrl:help htmlName="PaymentsNotesHeaderCourseNBRB"/></jsp:attribute></grid:column>
      <grid:column align="center" width="8%"><jsp:attribute name="title"><bean:message key="Payments.pay_summ_eur_nbrb"/></jsp:attribute></grid:column>
      <grid:column align="center" width="1%"><jsp:attribute name="title"><img title='<bean:message key="tooltip.Payments.block_status"/>' src='<ctrl:rewrite page="/images/lock.gif"/>'></jsp:attribute></grid:column>
      <grid:column title=""/>
      <grid:column title=""/>
      <grid:column title=""/>
      <grid:row>
        <grid:colCustom width="10%" property="pay_date_formatted"/>
        <grid:colCustom property="pay_contractor"/>
        <grid:colCustom width="15%" property="pay_account"/>
        <grid:colCustom align="right" width="5%" property="pay_summ_formatted"/>
        <grid:colCustom width="5%" property="pay_currency"/>
        <grid:colCustom align="right" width="5%" property="payCourseFormatted"/>
        <grid:colCustom align="right" width="7%" property="pay_summ_eur_formatted"/>
        <grid:colCustom align="right" width="5%" property="payCourseNbrbFormatted"/>
        <grid:colCustom align="right" width="8%" property="pay_summ_eur_nbrb_formatted"/>
        <grid:colCheckbox align="center" width="30" property="pay_block" readonlyCheckerId="alwaysReadonly"/>
        <grid:colImage width="1" action="/PaymentAction.do?dispatch=clone" type="link" tooltip="tooltip.Payments.clone" image="images/copy.gif" styleClass="grid-image-without-border"/>
        <grid:colEdit width="1" action="/PaymentAction.do?dispatch=edit" type="link" tooltip="tooltip.Payments.edit"/>
        <grid:colImage width="1%" enableOnClickAction="false" type="link" style="vertical-align:top;" showCheckerId="showCommentChecker"
                       styleClass="grid-image-without-border" image="images/comments.gif" tooltipProperty="pay_comment"/>
      </grid:row>
    </grid:table>
  </div>

  <table align="center" border="0" width="100%">
    <tr>
      <td>
        <div class=gridBottomLeft>
          <ctrl:write name="Payments" property="calculatedSums" jsFilter="true"/>
        </div>
      </td>
      <td>
        <div class=gridBottom>
          <ctrl:ubutton type="link" action="/PaymentAction.do?dispatch=input" styleClass="width80">
            <bean:message key="button.create"/>
          </ctrl:ubutton>
        </div>
      </td>
    </tr>
  </table>

</ctrl:form>

<script language="JScript" type="text/javascript">
  var closed_closed = document.getElementById('closed_closed');
  var closed_open = document.getElementById('closed_open');
  var closed_all = document.getElementById('closed_all');

  function closed_closedOnClick()
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

  function closed_openOnClick()
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

  function closed_allOnClick()
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