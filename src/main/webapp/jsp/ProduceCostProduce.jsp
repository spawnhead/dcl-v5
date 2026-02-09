<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>

<ctrl:form>
  <ctrl:hidden property="lpc_id"/>
  <ctrl:hidden property="lpc_1c_number"/>
  <ctrl:hidden property="lpc_occupied"/>
  <ctrl:hidden property="lpc_comment"/>
  <ctrl:hidden property="prc_id"/>
  <ctrl:hidden property="prc_date"/>
  <ctrl:hidden property="number"/>
  <ctrl:hidden property="opr_id"/>
  <ctrl:hidden property="asm_id"/>
  <ctrl:hidden property="apr_id"/>
  <ctrl:hidden property="drp_id"/>
  <ctrl:hidden property="sip_id"/>
  <table class=formBack align="center">
    <tr>
      <td>
        <table cellspacing="2">
          <tr>
            <td><bean:message key="ProduceCostProduce.lpc_stuff_category"/></td>
            <td><ctrl:serverList property="stuffCategory.name" idName="stuffCategory.id" action="/StuffCategoriesListAction" filter="filter" style="width:330px;" callback="onStuffCategorySelect" readonly="${ProduceCostProduce.readonliLikeImported}"/></td>
          </tr>
          <tr>
            <td><bean:message key="ProduceCostProduce.produce_name"/></td>
            <td>
              <ctrl:text property="produce.name" style="width:267px;" readonly="true"/>
              <ctrl:ubutton styleId="buttonSelectProduce" type="submit" dispatch="selectProduce" scriptUrl="stf_id=$(stuffCategory.id)" styleClass="width80" readonly="${ProduceCostProduce.readonliLikeImported}">
                <bean:message key="button.select"/>
              </ctrl:ubutton>
            </td>
          </tr>
          <tr>
            <td><bean:message key="ProduceCostProduce.lpc_produce_name"/></td>
            <td><ctrl:textarea property="lpc_produce_name" style="width:350px;height:80px;" readonly="true"/></td>
          </tr>
          <tr>
            <td><bean:message key="ProduceCostProduce.lpc_percent"/></td>
            <td><ctrl:text property="lpc_percent_dcl_1_4"/></td>
          </tr>
          <tr>
            <td colspan="2" style="color:#008000"><bean:message key="ProduceCostProduce.lpc_percent_legend"/></td>
          </tr>
          <tr>
            <td><bean:message key="ProduceCostProduce.lpc_manager"/></td>
            <td><ctrl:serverList property="manager.userFullName" idName="manager.usr_id" action="/UsersListAction" filter="filter" style="width:330px;" readonly="${ProduceCostProduce.readonliLikeImportedExceptSome}"/></td>
          </tr>
          <tr>
            <td><bean:message key="ProduceCostProduce.lpc_department"/></td>
            <td><ctrl:serverList property="department.name" idName="department.id" action="/DepartmentsListAction" selectOnly="true" style="width:330px;"/></td>
          </tr>
          <tr>
            <td><bean:message key="ProduceCostProduce.lpc_count"/></td>
            <td><ctrl:text property="lpc_count" readonly="${ProduceCostProduce.readonliLikeImported}"/></td>
          </tr>
          <tr>
            <td><bean:message key="ProduceCostProduce.lpc_cost_one_ltl"/></td>
            <td><ctrl:text property="lpc_cost_one_ltl"/></td>
          </tr>
          <tr>
            <td><bean:message key="ProduceCostProduce.lpc_cost_one_by"/></td>
            <td><ctrl:text property="lpc_cost_one_by"/></td>
          </tr>
          <tr>
            <td><bean:message key="ProduceCostProduce.lpc_price_list_by"/></td>
            <td><ctrl:text property="lpc_price_list_by"/></td>
          </tr>
          <tr>
            <td><bean:message key="ProduceCostProduce.lpc_weight"/></td>
            <td><ctrl:text property="lpc_weight"/></td>
          </tr>
          <tr>
            <td><bean:message key="ProduceCostProduce.lpc_summ"/></td>
            <td><ctrl:text property="lpc_summ"/></td>
          </tr>
          <tr>
            <td><bean:message key="ProduceCostProduce.lpc_purpose"/></td>
            <td><ctrl:serverList property="purpose.name" idName="purpose.id" action="/PurposesListAction" filter="filter" style="width:330px;" readonly="${ProduceCostProduce.readonliLikeImported}"/></td>
          </tr>
          <tr>
            <td colspan="2" align="right" class=formSpace>
              &nbsp;
            </td>
          </tr>
          <tr>
            <td colspan="2" align="right">
              <ctrl:ubutton type="link" dispatch="back" styleClass="width80" readonly="false">
                <bean:message key="button.cancel"/>
              </ctrl:ubutton>
              <ctrl:ubutton type="submit" dispatch="process" styleClass="width80" readonly="false">
                <bean:message key="button.save"/>
              </ctrl:ubutton>
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>

</ctrl:form>

<script language="JScript" type="text/javascript">
  var stuffCategoryId = document.getElementById("stuffCategory.id");
  var produceName = document.getElementById("produce.name");
  var buttonSelectProduce = document.getElementById("buttonSelectProduce");

<logic:notEqual value="true" name="ProduceCostProduce" property="readonliLikeImported">
  onStuffCategoryLoad();
</logic:notEqual>

  function onStuffCategoryLoad()
  {
    buttonSelectProduce.disabled = stuffCategoryId.value == '';
  }

  function onStuffCategorySelect()
  {
    buttonSelectProduce.disabled = false;
    produceName.value = '';
  }

</script>
