<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>

<ctrl:form >
  <ctrl:hidden property="number"/>
  <ctrl:hidden property="ccp_id"/>
  <ctrl:hidden property="cpr_id"/>
  <ctrl:hidden property="cpr_number"/>
  <ctrl:hidden property="cpr_date"/>
  <logic:equal name="ConditionForContractProduce" property="sellerId" value="0">
    <ctrl:hidden property="ccp_nds_rate"/>
  </logic:equal>
  <table class=formBack align="center"><tr><td >
  <table cellspacing="2"  >
    <tr>
      <td><bean:message key="ConditionForContractProduce.stuffCategory"/></td>
      <td><ctrl:serverList property="stuffCategory.name" idName="stuffCategory.id" action="/StuffCategoriesListAction" selectOnly="true" style="width:237px;" callback="onStuffCategorySelect"/></td>
    </tr>
    <tr>
      <td><bean:message key="ConditionForContractProduce.produce_name"/></td>
      <td>
        <ctrl:text property="produce.name" style="width:257px;" readonly="true"/>
        <ctrl:ubutton styleId="buttonSelectProduce" type="submit" dispatch="selectProduce" scriptUrl="stf_id=$(stuffCategory.id)" styleClass="width80">
          <bean:message key="button.select"/>
        </ctrl:ubutton>
      </td>
    </tr>
    <tr>
      <td><bean:message key="ConditionForContractProduce.ccp_price"/></td>
      <td><ctrl:text property="ccp_price" style="width:257px;"/></td>
    </tr>
    <tr>
      <td><bean:message key="ConditionForContractProduce.ccp_count"/></td>
      <td><ctrl:text property="ccp_count" style="width:257px;"/></td>
    </tr>
    <logic:notEqual name="ConditionForContractProduce" property="sellerId" value="0">
      <tr>
        <td><bean:message key="ConditionForContractProduce.ccp_nds_rate"/></td>
        <td><ctrl:text property="ccp_nds_rate" style="width:257px;"/></td>
      </tr>
    </logic:notEqual>
    <tr>
      <td colspan="2" align="right" class=formSpace>
        &nbsp;
      </td>
    </tr>
    <tr>
      <td colspan="2" align="right">
        <ctrl:ubutton type="link" dispatch="back" styleClass="width80">
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

<script language="JScript" type="text/javascript">

  var stuffCategoryId = document.getElementById("stuffCategory.id");
  var produceName = document.getElementById("produce.name");
  var buttonSelectProduce = document.getElementById('buttonSelectProduce');

  onStuffCategoryLoad();

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
