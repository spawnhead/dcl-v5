<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>

<h2>
  <bean:message key="MontageAdjustmentsHistory.title"/>&nbsp;<ctrl:write name="MontageAdjustmentsHistory" property="mad_machine_type"/>
</h2>

<ctrl:form>
  <ctrl:hidden property="mad_id"/>
  <div class="gridBackMontage" style="overflow-y:scroll;width:expression(document.body.clientWidth-15); height:expression(document.body.clientHeight-240)">
    <grid:table property="grid" key="madh_id" autoLockName="MontageAdjustmentsHistory" >
      <th class="table-header" rowspan="3"><bean:message key="MontageAdjustmentsHistory.mad_date_from"/></th>
      <th class="table-header" rowspan="3"><bean:message key="MontageAdjustmentsHistory.mad_work_type"/></th>
      <th class="table-header" colspan="4"><bean:message key="MontageAdjustmentsHistory.mad_work"/></th>
      <th class="table-header" colspan="3" ><bean:message key="MontageAdjustmentsHistory.mad_road"/></th>
      <th class="table-header" rowspan="3"><bean:message key="MontageAdjustmentsHistory.mad_itogo"/></th>
      <th class="table-header" rowspan="3"></th>
      </tr><tr class="locked-header1">
      <th class="table-header" rowspan="2"><bean:message key="MontageAdjustmentsHistory.mad_work_tariff"/></th>
      <th class="table-header" colspan="2"><bean:message key="MontageAdjustmentsHistory.mad_work_rule"/></th>
      <th class="table-header" rowspan="2"><bean:message key="MontageAdjustmentsHistory.mad_work_sum"/></th>
      <th class="table-header" rowspan="2"><bean:message key="MontageAdjustmentsHistory.mad_road_tariff"/></th>
      <th class="table-header" rowspan="2"><bean:message key="MontageAdjustmentsHistory.mad_road_rule"/></th>
      <th class="table-header" rowspan="2"><bean:message key="MontageAdjustmentsHistory.mad_road_sum"/></th>
      </tr><tr class="locked-header1">
      <grid:column width="5%" align="center"><jsp:attribute name="title"><bean:message key="MontageAdjustmentsHistory.mad_work_rule_montage"/></jsp:attribute></grid:column>
      <grid:column width="5%" align="center"><jsp:attribute name="title"><bean:message key="MontageAdjustmentsHistory.mad_work_rule_adjustment"/></jsp:attribute></grid:column>
      <grid:row>
        <grid:colCustom width="10%" property="mad_date_from"/>
        <grid:colCustom property="mad_work_type"/>
        <grid:colCustom width="5%" property="mad_work_tariff"/>
        <grid:colCustom width="5%" property="mad_work_rule_montage"/>
        <grid:colCustom width="5%" property="mad_work_rule_adjustment"/>
        <grid:colCustom width="5%" property="mad_work_sum"/>
        <grid:colCustom width="5%" property="mad_road_tariff"/>
        <grid:colCustom width="5%" property="mad_road_rule"/>
        <grid:colCustom width="5%" property="mad_road_sum"/>
        <grid:colCustom width="5%" property="mad_total"/>
        <grid:colEdit width="1%" action="/MontageAdjustmentHistoryAction.do?dispatch=edit" type="link" scriptUrl="mad_id=$(mad_id)" tooltip="tooltip.MontageAdjustmentsHistory.edit" showCheckerId="showEditChecker"/>
      </grid:row>
    </grid:table>
  </div>

  <div class=gridBottom>
    <ctrl:ubutton type="link" actionForward="back" styleClass="width80">
      <bean:message key="button.back"/>
    </ctrl:ubutton>
    <ctrl:ubutton type="link" action="/MontageAdjustmentHistoryAction.do?dispatch=input" scriptUrl="mad_id=$(mad_id)" styleClass="width80">
      <bean:message key="button.add"/>
    </ctrl:ubutton>
  </div>
</ctrl:form>
