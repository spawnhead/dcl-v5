<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>
<ctrl:form >
  <ctrl:hidden property="stf_id"/>
  <ctrl:hidden property="mad_id"/>
  <table class=formBack align="center"><tr><td >
  <table cellspacing="2"  >
    <tr>
      <td><bean:message key="MontageAdjustment.mad_machine_type"/></td>
      <td><ctrl:text property="mad_machine_type"/></td>
    </tr>
    <tr>
      <td><bean:message key="MontageAdjustment.mad_complexity"/></td>
      <td>
        <ctrl:serverList property="complexityCategory.name" idName="complexityCategory.id"
                         action="/ComplexityCategoryListAction" selectOnly="true" style="width:110px;"/>
      </td>
    </tr>
    <tr>
      <td><bean:message key="MontageAdjustment.mad_annul"/></td>
      <td>
        <ctrl:checkbox property="mad_annul" styleClass="checkbox" value="1"/>
      </td>
    </tr>
    <logic:equal name="MontageAdjustment" property="newRecord" value="true">
      <tr>
        <td><bean:message key="MontageAdjustment.mad_date_from"/></td>
        <td><ctrl:date property="mad_date_from_formatted" style="width:230px;"/></td>
      </tr>
      <tr>
        <td colspan="2">&nbsp;</td>
      </tr>
      <tr>
        <td colspan="2"><bean:message key="MontageAdjustment.mad_work_type1"/></td>
      </tr>
      <tr>
        <td colspan="2"><bean:message key="MontageAdjustment.mad_work"/></td>
      </tr>
      <tr>
        <td><bean:message key="MontageAdjustment.mad_work_tariff"/></td>
        <td><ctrl:text property="mad_mech_work_tariff_formatted"/></td>
      </tr>
      <tr>
        <td><bean:message key="MontageAdjustment.mad_work_rule_montage"/></td>
        <td><ctrl:text property="mad_mech_work_rule_montage_formatted"/></td>
      </tr>
      <tr>
        <td><bean:message key="MontageAdjustment.mad_work_rule_adjustment"/></td>
        <td><ctrl:text property="mad_mech_work_rule_adjustment_formatted"/></td>
      </tr>
      <tr>
        <td colspan="2"><bean:message key="MontageAdjustment.mad_road"/></td>
      </tr>
      <tr>
        <td><bean:message key="MontageAdjustment.mad_road_tariff"/></td>
        <td><ctrl:text property="mad_mech_road_tariff_formatted"/></td>
      </tr>
      <tr>
        <td><bean:message key="MontageAdjustment.mad_road_rule"/></td>
        <td><ctrl:text property="mad_mech_road_rule_formatted"/></td>
      </tr>
      <tr>
        <td colspan="2">&nbsp;</td>
      </tr>
      <tr>
        <td colspan="2"><bean:message key="MontageAdjustment.mad_work_type2"/></td>
      </tr>
      <tr>
        <td colspan="2"><bean:message key="MontageAdjustment.mad_work"/></td>
      </tr>
      <tr>
        <td><bean:message key="MontageAdjustment.mad_work_tariff"/></td>
        <td><ctrl:text property="mad_el_work_tariff_formatted"/></td>
      </tr>
      <tr>
        <td><bean:message key="MontageAdjustment.mad_work_rule_montage"/></td>
        <td><ctrl:text property="mad_el_work_rule_montage_formatted"/></td>
      </tr>
      <tr>
        <td><bean:message key="MontageAdjustment.mad_work_rule_adjustment"/></td>
        <td><ctrl:text property="mad_el_work_rule_adjustment_formatted"/></td>
      </tr>
      <tr>
        <td colspan="2"><bean:message key="MontageAdjustment.mad_road"/></td>
      </tr>
      <tr>
        <td><bean:message key="MontageAdjustment.mad_road_tariff"/></td>
        <td><ctrl:text property="mad_el_road_tariff_formatted"/></td>
      </tr>
      <tr>
        <td><bean:message key="MontageAdjustment.mad_road_rule"/></td>
        <td><ctrl:text property="mad_el_road_rule_formatted"/></td>
      </tr>
    </logic:equal>
    <tr>
      <td colspan="2" align="right" class=formSpace>
        &nbsp;
      </td>
    </tr>
    <tr>
      <td colspan="2" align="right">
        <ctrl:ubutton type="link" actionForward="back" styleClass="width80">
          <bean:message key="button.cancel"/>
        </ctrl:ubutton>
        <ctrl:ubutton type="submit" dispatch="process" styleClass="width80">
          <bean:message key="button.save"/>
        </ctrl:ubutton>
      </td>
    </tr>
  </table>
  </td></tr></table>

</ctrl:form>
