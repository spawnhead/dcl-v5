<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>

<ctrl:form>
  <table id=filterTbl style="" align="center" border="0" width="100%">
    <tr>
      <td align="right"><bean:message key="MontageAdjustments.stf_name"/></td>
      <td>
        <ctrl:serverList property="stuffCategory.name" idName="stuffCategory.id" action="/StuffCategoriesListAction"
                         styleClass="filter" filter="filter" callback="onStuffCategorySelect" scriptUrl="forMontage=true"/>
      </td>
    </tr>
  </table>

  <logic:equal name="MontageAdjustments" property="showTable" value="true">
    <div class="gridBackMontage" style="overflow-y:scroll;width:expression(document.body.clientWidth-15); height:expression(document.body.clientHeight-240)">
      <grid:table property="grid" key="mad_id" autoLockName="MontageAdjustments" >
        <th class="table-header" rowspan="3"><bean:message key="MontageAdjustments.number"/></th>
        <th class="table-header" rowspan="3"><bean:message key="MontageAdjustments.mad_machine_type"/></th>
        <th class="table-header" rowspan="3"><bean:message key="MontageAdjustments.mad_complexity"/></th>
        <th class="table-header" rowspan="3"><bean:message key="MontageAdjustments.mad_work_type"/></th>
        <th class="table-header" rowspan="3"><bean:message key="MontageAdjustments.mad_date_from"/></th>
        <th class="table-header" colspan="4"><bean:message key="MontageAdjustments.mad_work"/></th>
        <th class="table-header" colspan="3" ><bean:message key="MontageAdjustments.mad_road"/></th>
        <th class="table-header" rowspan="3"><bean:message key="MontageAdjustments.mad_itogo"/></th>
        <th class="table-header" rowspan="3"></th>
        <th class="table-header" rowspan="3"></th>
        </tr><tr class="locked-header1">
        <th class="table-header" rowspan="2"><bean:message key="MontageAdjustments.mad_work_tariff"/></th>
        <th class="table-header" colspan="2"><bean:message key="MontageAdjustments.mad_work_rule"/></th>
        <th class="table-header" rowspan="2"><bean:message key="MontageAdjustments.mad_work_sum"/></th>
        <th class="table-header" rowspan="2"><bean:message key="MontageAdjustments.mad_road_tariff"/></th>
        <th class="table-header" rowspan="2"><bean:message key="MontageAdjustments.mad_road_rule"/></th>
        <th class="table-header" rowspan="2"><bean:message key="MontageAdjustments.mad_road_sum"/></th>
        </tr><tr class="locked-header1">
        <grid:column width="5%" align="center"><jsp:attribute name="title"><bean:message key="MontageAdjustments.mad_work_rule_montage"/></jsp:attribute></grid:column>
        <grid:column width="5%" align="center"><jsp:attribute name="title"><bean:message key="MontageAdjustments.mad_work_rule_adjustment"/></jsp:attribute></grid:column>
        <grid:row>
          <grid:colCustom align="center" width="2%" property="number" styleClassCheckerId="style-checker"/>
          <grid:colCustom property="mad_machine_type" styleClassCheckerId="style-checker"/>
          <grid:colCustom align="center" width="5%" property="mad_complexity" styleClassCheckerId="style-checker"/>
          <grid:colCustom width="200" property="mad_work_type" styleClassCheckerId="style-checker"/>
          <grid:colCustom width="5%" property="mad_date_from_formatted" styleClassCheckerId="style-checker"/>
          <grid:colCustom width="5%" property="mad_work_tariff" styleClassCheckerId="style-checker"/>
          <grid:colCustom width="5%" property="mad_work_rule_montage" styleClassCheckerId="style-checker"/>
          <grid:colCustom width="5%" property="mad_work_rule_adjustment" styleClassCheckerId="style-checker"/>
          <grid:colCustom width="5%" property="mad_work_sum" styleClassCheckerId="style-checker"/>
          <grid:colCustom width="5%" property="mad_road_tariff" styleClassCheckerId="style-checker"/>
          <grid:colCustom width="5%" property="mad_road_rule" styleClassCheckerId="style-checker"/>
          <grid:colCustom width="5%" property="mad_road_sum" styleClassCheckerId="style-checker"/>
          <grid:colCustom width="5%" property="mad_total" styleClassCheckerId="style-checker"/>
          <grid:colLink width="1" action="/MontageAdjustmentsHistoryAction.do?dispatch=show" scriptUrl="mad_machine_type=${record.mad_machine_type}" type="link">
            <bean:message key="MontageAdjustments.history"/>
          </grid:colLink>
          <grid:colEdit width="1%" action="/MontageAdjustmentAction.do?dispatch=edit" type="link" tooltip="tooltip.MontageAdjustments.edit" scriptUrl="stf_id=$(stuffCategory.id)" showCheckerId="showEditChecker"/>
        </grid:row>
      </grid:table>
    </div>

    <div class=gridBottom>
      <ctrl:ubutton type="link" action="/MontageAdjustmentAction.do?dispatch=input" styleClass="width80" scriptUrl="stf_id=$(stuffCategory.id)">
        <bean:message key="button.add"/>
      </ctrl:ubutton>
    </div>
  </logic:equal>
</ctrl:form>

<script language="JScript" type="text/javascript">
  function onStuffCategorySelect()
  {
    submitDispatchForm("show");
  }
</script>