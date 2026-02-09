<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>

<ctrl:form styleId="striped-form">
  <table id=filterTbl style="" align="center" border="0" width="100%">
    <tr>
      <td align="right"><bean:message key="CurrentWorks.contractor"/></td>
      <td><ctrl:serverList property="contractor.name" idName="contractor.id" action="/ContractorsListAction"
                           styleClass="filter"/></td>
      <td width="20">&nbsp;</td>

      <td align="right"><bean:message key="CurrentWorks.crq_request_type"/></td>
      <td ><ctrl:serverList property="requestType.name" idName="requestType.id"
                            action="/ContractorRequestTypeListAction" styleClass="filter"
                            selectOnly="true" style="width:380px;"/></td>
      <td width="20">&nbsp;</td>

      <td align="right"><bean:message key="CurrentWorks.ord_user"/></td>
      <td><ctrl:serverList property="user.usr_name" idName="user.usr_id" action="/UsersListAction" styleClass="filter"/></td>
    </tr>
    <tr>
      <td align="right"><bean:message key="CurrentWorks.stuffCategory"/></td>
      <td><ctrl:serverList property="stuffCategory.name" idName="stuffCategory.id" action="/StuffCategoriesListAction" styleClass="filter"/></td>
      <td width="20">&nbsp;</td>

      <td align="right"><bean:message key="CurrentWorks.crq_equipment"/></td>
      <td><ctrl:text property="crq_equipment" style="width:400px;" styleClass="filter"/></td>
      <td width="20">&nbsp;</td>

      <td align="right"></td>
      <td></td>
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
  <div class="gridBack" >
    <grid:table property="grid" key="crq_id" autoLockName="ContractorRequest" >
      <th class="table-header" colspan="2"><bean:message key="CurrentWorks.contractorRequest"/></th>
      <th class="table-header" colspan="2"><bean:message key="CurrentWorks.crq_equipment"/></th>
      <th class="table-header" rowspan="2"><bean:message key="CurrentWorks.crq_request_type"/></th>
      <th class="table-header" rowspan="2"><bean:message key="CurrentWorks.contractor"/></th>
      <th class="table-header" rowspan="2"><bean:message key="CurrentWorks.contract"/></th>
      <th class="table-header" colspan="6"><bean:message key="CurrentWorks.linkedOrders"/></th>
      <th class="table-header" rowspan="2"><bean:message key="CurrentWorks.spiSendDates"/></th>
      <th class="table-header" rowspan="2"><bean:message key="CurrentWorks.costProducesDates"/></th>
      <th class="table-header" rowspan="2"><bean:message key="CurrentWorks.shipNumbersDates"/></th>
      <th class="table-header" rowspan="2"></th>
      </tr><tr>
      <grid:column align="center" width="5%"><jsp:attribute name="title"><bean:message key="CurrentWorks.crq_number"/></jsp:attribute></grid:column>
      <grid:column align="center" width="7%"><jsp:attribute name="title"><bean:message key="CurrentWorks.crq_create_date"/></jsp:attribute></grid:column>
      <grid:column align="center"><jsp:attribute name="title"><bean:message key="CurrentWorks.crq_full_name"/></jsp:attribute></grid:column>
      <grid:column align="center" width="5%"><jsp:attribute name="title"><bean:message key="CurrentWorks.stuffCategory"/></jsp:attribute></grid:column>
      <grid:column align="center" width="7%"><jsp:attribute name="title"><bean:message key="CurrentWorks.ord_number"/></jsp:attribute></grid:column>
      <grid:column align="center" width="5%"><jsp:attribute name="title"><bean:message key="CurrentWorks.ord_date"/></jsp:attribute></grid:column>
      <grid:column align="center" width="7%"><jsp:attribute name="title"><bean:message key="CurrentWorks.manager"/></jsp:attribute></grid:column>
      <grid:column align="center" width="3%"><jsp:attribute name="title"><bean:message key="CurrentWorks.ord_sum"/></jsp:attribute></grid:column>
      <grid:column align="center" width="5%"><jsp:attribute name="title"><bean:message key="CurrentWorks.ord_sent_to_prod"/></jsp:attribute></grid:column>
      <grid:column align="center" width="5%"><jsp:attribute name="title"><bean:message key="CurrentWorks.executedDates"/></jsp:attribute></grid:column>
      <grid:row>
        <grid:colCustom width="5%" property="crq_number"/>
        <grid:colCustom width="7%" property="crqCreateDateFormatted"/>
        <grid:colCustom property="crq_equipment"/>
        <grid:colCustom width="5%" property="stf_name"/>
        <grid:colCustom width="5%" property="crq_request_type"/>
        <grid:colCustom width="7%" property="crq_contractor"/>
        <grid:colCustom width="7%" property="conNumberDateFormatted"/>
        <grid:colCustom width="7%" property="ord_number"/>
        <grid:colCustom width="5%" property="ordDateFormatted"/>
        <grid:colCustom width="7%" property="manager"/>
        <grid:colCustom width="3%" align="right" property="ordSumFormatted"/>
        <grid:colCustom width="5%" property="ordSentToProdDateFormatted"/>
        <grid:colCustom width="5%" property="executedDatesFormatted"/>
        <grid:colCustom width="7%" property="spiSendDatesFormatted"/>
        <grid:colCustom width="7%" property="costProducesDatesFormatted"/>
        <grid:colCustom width="7%" property="shipNumbersDatesFormatted"/>
        <grid:colImage type="link" enableOnClickAction="false" width="1%" showCheckerId="showCommentChecker"
                       styleClass="grid-image-without-border" image="images/comments.gif"
                       tooltipProperty="crq_comment"/>
      </grid:row>
    </grid:table>
  </div>
</ctrl:form>
