<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>

<ctrl:form styleId="striped-form">
  <table id=filterTbl style="" align="center" border="0" width="100%">
    <tr>
      <td align="right"><bean:message key="ContractorRequests.number"/></td>
      <td><ctrl:text property="number" styleClass="filter"/></td>
      <td width="20">&nbsp;</td>

      <td align="right"><bean:message key="ContractorRequests.crq_request_type"/></td>
      <td ><ctrl:serverList property="requestType.name" idName="requestType.id"
                            action="/ContractorRequestTypeListAction" styleClass="filter"
                            selectOnly="true" style="width:380px;"/></td>
      <td width="20">&nbsp;</td>

      <td align="right"><bean:message key="ContractorRequests.crqNotDeliver"/></td>
      <td><ctrl:checkbox property="crqNotDeliver" styleClass="checkbox_filter" value="1"/></td>
    </tr>
    <tr>
      <td align="right"><bean:message key="ContractorRequests.contractor"/></td>
      <td><ctrl:serverList property="contractor.name" idName="contractor.id" action="/ContractorsListAction"
                           styleClass="filter"/></td>
      <td width="20">&nbsp;</td>

      <td align="right"><bean:message key="ContractorRequests.crq_equipment"/></td>
      <td><ctrl:text property="crq_equipment" style="width:400px;" styleClass="filter"/></td>
      <td width="20">&nbsp;</td>

      <td align="right"><bean:message key="ContractorRequests.crq_ticket_number"/></td>
      <td><ctrl:text property="crq_ticket_number" styleClass="filter"/></td>
    </tr>

    <tr>
      <td align="right"><bean:message key="ContractorRequests.stuffCategory"/></td>
      <td><ctrl:serverList property="stuffCategory.name" idName="stuffCategory.id" action="/StuffCategoriesListAction" styleClass="filter"/></td>
      <td width="20">&nbsp;</td>

      <td align="right"><bean:message key="ContractorRequests.crq_seller"/></td>
      <td>
        <ctrl:serverList property="seller.name" idName="seller.id" action="/SellersListAction"
                         selectOnly="true" styleClass="filter" style="width:380px;"/>
      </td>
      <td width="20">&nbsp;</td>
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
      <th class="table-header" rowspan="2"><bean:message key="ContractorRequests.number"/></th>
      <th class="table-header" rowspan="2"><bean:message key="ContractorRequests.crq_ticket_number"/></th>
      <th class="table-header" rowspan="2"><bean:message key="ContractorRequests.crq_receive_date"/></th>
      <th class="table-header" rowspan="2"><bean:message key="ContractorRequests.contractor"/></th>
      <th class="table-header" rowspan="2"><bean:message key="ContractorRequests.crq_seller"/></th>
      <th class="table-header" rowspan="2"><bean:message key="ContractorRequests.crq_request_type"/></th>
      <th class="table-header" colspan="2"><bean:message key="ContractorRequests.crq_equipment"/></th>
      <th class="table-header" rowspan="2"><img title='<bean:message key="tooltip.ContractorRequests.crq_deliver"/>' src='<ctrl:rewrite page="/images/lock.gif"/>'></th>
      <th class="table-header" rowspan="2"></th>
      <th class="table-header" rowspan="2"></th>
      <th class="table-header" rowspan="2"></th>
      <th class="table-header" rowspan="2"></th>
      </tr><tr>
      <grid:column align="center"><jsp:attribute name="title"><bean:message key="ContractorRequests.crq_full_name"/></jsp:attribute></grid:column>
      <grid:column align="center" width="10%"><jsp:attribute name="title"><bean:message key="ContractorRequests.crq_serial_number"/></jsp:attribute></grid:column>
      <grid:row>
        <grid:colCustom width="8%" property="crq_number" styleClassCheckerId="style-checker"/>
        <grid:colCustom width="8%" property="crq_ticket_number" styleClassCheckerId="style-checker"/>
        <grid:colCustom width="8%" property="crq_receive_date_formatted" styleClassCheckerId="style-checker"/>
        <grid:colCustom width="12%" property="crq_contractor" styleClassCheckerId="style-checker"/>
        <grid:colCustom width="12%" property="crq_seller" styleClassCheckerId="style-checker"/>
        <grid:colCustom width="7%" property="crq_request_type" styleClassCheckerId="style-checker"/>
        <grid:colCustom property="crq_equipment" styleClassCheckerId="style-checker"/>
        <grid:colCustom width="10%" property="crq_serial_number" styleClassCheckerId="style-checker"/>
        <grid:colCheckbox align="center" property="crq_deliver" readonlyCheckerId="deliverChecker" type="submit" dispatch="deliver" scriptUrl="crq_deliver=${record.crq_deliver}"/>
        <grid:colImage width="1%" action="/ContractorRequestAction.do?dispatch=clone" type="link"
                       tooltip="tooltip.ContractorRequests.clone" image="images/copy.gif"
                       styleClass="grid-image-without-border" showCheckerId="showCloneChecker"/>
        <grid:colEdit width="1%" action="/ContractorRequestAction.do?dispatch=edit" type="link"
                      tooltip="tooltip.ContractorRequests.edit"/>
        <grid:colImage type="link" enableOnClickAction="false" width="1%"
                       styleClass="grid-image-without-border" image="images/attach${record.attach_idx}.gif"
                       tooltip="tooltip.ContractorRequests.attach${record.attach_idx}"/>
        <grid:colImage type="link" enableOnClickAction="false" width="1%" showCheckerId="showCommentChecker"
                       styleClass="grid-image-without-border" image="images/comments.gif"
                       tooltipProperty="crq_comment"/>
      </grid:row>
    </grid:table>
  </div>

  <ctrl:notShowIfSelectMode>
    <div class=gridBottom>
      <ctrl:ubutton type="link" action="/ContractorRequestAction.do?dispatch=input" styleClass="width80">
        <bean:message key="button.create"/>
      </ctrl:ubutton>
    </div>
  </ctrl:notShowIfSelectMode>
</ctrl:form>
