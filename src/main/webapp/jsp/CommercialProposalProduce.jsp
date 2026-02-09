<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>

<ctrl:form >
  <ctrl:hidden property="number"/>
  <ctrl:hidden property="numberBefore"/>
  <ctrl:hidden property="cpr_old_version"/>
  <ctrl:hidden property="donot_calculate_netto"/>
  <logic:notEqual name="CommercialProposalProduce" property="reverseCalc" value="true">
    <ctrl:hidden property="lpr_sale_price"/>
  </logic:notEqual>
  <logic:equal name="CommercialProposalProduce" property="reverseCalc" value="true">
    <ctrl:hidden property="lpr_coeficient"/>
  </logic:equal>
  <logic:notEqual name="CommercialProposalProduce" property="cpr_old_version" value="1">
    <logic:equal name="CommercialProposalProduce" property="enterCustomCode" value="true">
      <ctrl:hidden property="custom_code.custom_percent_formatted"/>
    </logic:equal>
  </logic:notEqual>
  <logic:equal name="CommercialProposalProduce" property="cpr_old_version" value="1">
    <ctrl:hidden property="custom_code.custom_percent_formatted"/>
  </logic:equal>

  <logic:equal name="CommercialProposalProduce" property="donot_calculate_netto" value="1">
    <ctrl:hidden property="lpr_price_brutto"/>
    <ctrl:hidden property="lpr_discount"/>
  </logic:equal>
  <logic:notEqual name="CommercialProposalProduce" property="donot_calculate_netto" value="1">
    <ctrl:hidden property="lpr_price_netto"/>
  </logic:notEqual>

  <table class=formBack align="center">
    <tr>
      <td >
        <table cellspacing="2">
          <logic:notEqual name="CommercialProposalProduce" property="cpr_old_version" value="1">
            <tr>
              <td><bean:message key="CommercialProposalProduce.stuffCategory"/></td>
              <td><ctrl:serverList property="stuffCategory.name" idName="stuffCategory.id" action="/StuffCategoriesListAction"
                                   filter="filter" style="width:330px;" callback="onStuffCategorySelect"/></td>
            </tr>
            <tr>
              <td><bean:message key="CommercialProposalProduce.produce_name"/></td>
              <td>
                <ctrl:text property="produce.fullName" style="width:267px;" readonly="true"/>
                <ctrl:ubutton styleId="buttonSelectProduce" type="submit" dispatch="selectProduce"
                              scriptUrl="stf_id=$(stuffCategory.id)" styleClass="width80">
                  <bean:message key="button.select"/>
                </ctrl:ubutton>
              </td>
            </tr>
            <logic:equal name="CommercialProposalProduce" property="emptyProduce" value="false">
              <tr>
                <td><bean:message key="CommercialProposalProduce.custom_code"/></td>
                <logic:equal name="CommercialProposalProduce" property="haveProduceButEmptyCusCode" value="false">
                  <td><ctrl:write name="CommercialProposalProduce" property="customCodeFormatted"/>&nbsp;</td>
                </logic:equal>
                <logic:equal name="CommercialProposalProduce" property="haveProduceButEmptyCusCode" value="true">
                  <td><span style="color:red"><b><bean:message key="CommercialProposalProduce.custom_code_dismiss"/></b></span></td>
                </logic:equal>
              </tr>
            </logic:equal>
          </logic:notEqual>
          <logic:equal name="CommercialProposalProduce" property="cpr_old_version" value="1">
            <tr>
              <td><bean:message key="CommercialProposalProduce.lpr_produce_name"/></td>
              <td><ctrl:textarea property="lpr_produce_name" style="width:350px;height:80px;"/></td>
            </tr>
            <tr>
              <td><bean:message key="CommercialProposalProduce.lpr_catalog_num"/></td>
              <td><ctrl:text property="lpr_catalog_num"/></td>
            </tr>
          </logic:equal>
          <logic:notEqual name="CommercialProposalProduce" property="donot_calculate_netto" value="1">
            <tr>
              <td><bean:message key="CommercialProposalProduce.lpr_price_brutto"/></td>
              <td><ctrl:text property="lpr_price_brutto"/></td>
            </tr>
            <tr>
              <td><bean:message key="CommercialProposalProduce.lpr_discount"/></td>
              <td><ctrl:text property="lpr_discount"/></td>
            </tr>
          </logic:notEqual>
          <logic:equal name="CommercialProposalProduce" property="donot_calculate_netto" value="1">
            <tr>
              <td><bean:message key="CommercialProposalProduce.lpr_price_netto"/></td>
              <td><ctrl:text property="lpr_price_netto"/></td>
            </tr>
          </logic:equal>
          <tr>
            <td><bean:message key="CommercialProposalProduce.lpr_count"/></td>
            <td><ctrl:text property="lpr_count"/></td>
          </tr>
          <logic:notEqual name="CommercialProposalProduce" property="cpr_old_version" value="1">
            <logic:equal name="CommercialProposalProduce" property="enterCustomCode" value="true">
              <tr>
                <td><bean:message key="CommercialProposalProduce.custom_code_manual"/></td>
                <td>
                <table cellspacing="0" cellpadding="0">
                  <tr>
                    <td>
                      <ctrl:serverList property="custom_code.code" idName="custom_code.id" action="/CustomCodesListAction"
                                       style="width:230px;" height="300" filter="filter" callback="onCustomCodeSelect"/>&nbsp;
                    </td>
                    <td>
                      <span id='percentSpan'><ctrl:write name="CommercialProposalProduce" property="custom_code.percentFormatted"/></span>
                    </td>
                  </tr>
                </table>
              </td>
              </tr>
            </logic:equal>
          </logic:notEqual>
          <logic:equal name="CommercialProposalProduce" property="cpr_old_version" value="1">
            <tr>
              <td><bean:message key="CommercialProposalProduce.custom_code_manual"/></td>
              <td>
                <table cellspacing="0" cellpadding="0">
                  <tr>
                    <td>
                      <ctrl:serverList property="custom_code.code" idName="custom_code.id" action="/CustomCodesListAction"
                                       style="width:230px;" height="300" filter="filter" callback="onCustomCodeSelect"/>&nbsp;
                    </td>
                    <td>
                      <span id='percentSpan'><ctrl:write name="CommercialProposalProduce" property="custom_code.percentFormatted"/></span>
                    </td>
                  </tr>
                </table>
              </td>
            </tr>
          </logic:equal>
          <logic:notEqual name="CommercialProposalProduce" property="reverseCalc" value="true">
            <tr>
              <td><bean:message key="CommercialProposalProduce.lpr_coeficient"/></td>
              <td><ctrl:text property="lpr_coeficient"/></td>
            </tr>
          </logic:notEqual>
          <logic:equal name="CommercialProposalProduce" property="reverseCalc" value="true">
            <tr>
              <td>${CommercialProposalProduce.lpr_sale_price_text}</td>
              <td><ctrl:text property="lpr_sale_price"/></td>
            </tr>
          </logic:equal>
          <tr>
            <td><bean:message key="CommercialProposalProduce.lpr_comment"/></td>
            <td><ctrl:textarea property="lpr_comment" style="width:350px;height:80px;"/></td>
          </tr>
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
      </td>
    </tr>
  </table>

</ctrl:form>

<script language="JScript" type="text/javascript">

<logic:notEqual name="CommercialProposalProduce" property="cpr_old_version" value="1">
  var stuffCategoryId = document.getElementById("stuffCategory.id");
  var produceName = document.getElementById("produce.name");
  var buttonSelectProduce = document.getElementById("buttonSelectProduce");

  onStuffCategoryLoad();

  function onStuffCategoryLoad()
  {
    buttonSelectProduce.disabled = (stuffCategoryId.value == '');
  }

  function onStuffCategorySelect()
  {
    buttonSelectProduce.disabled = false;
    if (produceName != null)
      produceName.value = '';
  }
</logic:notEqual>

  function onCustomCodeSelect(arg)
  {
    var percentSpan = document.getElementById("percentSpan");
    var percent = document.getElementById("custom_code.custom_percent_formatted");
    percentSpan.innerHTML = arg?arg.arguments[2]:null;
    percent.value = arg?arg.arguments[2]:null;
    percent.value = percent.value.replace('<bean:message key="Common.percent"/>', '');
  }
  
</script>