  <%@ taglib uri="/tags/struts-bean" prefix="bean" %>
  <%@ taglib uri="/tags/struts-html" prefix="html" %>
  <%@ taglib uri="/tags/struts-logic" prefix="logic" %>
  <%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
  <%@ taglib uri="/tags/html-grid" prefix="grid" %>

  <script language="JScript" type="text/javascript">
    function submitReload()
    {
      submitDispatchForm("reload");
    }
  </script>

  <ctrl:form  readonly="${SpecificationImport.formReadOnly}">
  <ctrl:hidden property="spi_id"/>
  <ctrl:hidden property="is_new_doc"/>
  <ctrl:hidden property="usr_date_create"/>
  <ctrl:hidden property="usr_date_edit"/>
  <ctrl:hidden property="createUser.usr_id"/>
  <ctrl:hidden property="editUser.usr_id"/>
  <table class=formBackTop align="center" width="99%">
    <tr>
      <td>
        <table cellspacing="0" width="100%" >
          <logic:notEqual name="SpecificationImport" property="is_new_doc" value="true">
            <tr>
              <td>
                <table width="100%">
                  <tr>
                    <td width="10%"><bean:message key="SpecificationImport.create"/></td>
                    <td><ctrl:write name="SpecificationImport" property="usr_date_create"/> <ctrl:write name="SpecificationImport" property="createUser.userFullName"/> </td>
                  </tr>
                </table>
              </td>
            </tr>
            <tr>
              <td>
                <table width="100%">
                  <tr>
                    <td width="10%"><bean:message key="SpecificationImport.edit"/></td>
                    <td><ctrl:write name="SpecificationImport" property="usr_date_edit"/> <ctrl:write name="SpecificationImport" property="editUser.userFullName"/></td>
                  </tr>
                </table>
              </td>
            </tr>
          </logic:notEqual>
          <tr>
            <td colspan="2">
              <table width="100%" cellpadding="0" cellspacing="0">
                <tr valign="top">
                  <td>
                    <table>
                      <tr>
                        <td>
                          <table width="100%">
                            <tr>
                              <td width="40%"><bean:message key="SpecificationImport.spi_date"/></td>
                              <td><ctrl:date property="spi_date" style="width:230px;" afterSelect='submitReload'/></td>
                            </tr>
                          </table>
                        </td>
                      </tr>
                      <tr>
                        <td>
                          <table width="100%">
                            <tr>
                              <td width="40%"><bean:message key="SpecificationImport.spi_number"/></td>
                              <td><ctrl:text property="spi_number" style="width:250px;"/></td>
                            </tr>
                          </table>
                        </td>
                      </tr>
                    </table>
                  </td>
                  <td valign="top" width="5%">
                    <bean:message key="SpecificationImport.spi_comment"/>&nbsp;&nbsp;
                  </td>
                  <td>
                    <ctrl:textarea property="spi_comment" style="width:432px;height:55px;"/>
                  </td>
                </tr>
              </table>
            </td>
          <tr>

          <tr>
            <td>
              <table width="100%">
                <tr>
                  <td>
                    <bean:message key="SpecificationImport.spi_course"/>&nbsp;&nbsp;
                    <ctrl:text property="spi_course" style="width:110px;text-align:right;" onchange="onCourseKoefChange();"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <bean:message key="SpecificationImport.spi_koeff"/>&nbsp;&nbsp;
                    <ctrl:text property="spi_koeff" style="width:110px;text-align:right;" onchange="onCourseKoefChange();"/>&nbsp;&nbsp;
                    <ctrl:ubutton styleId="buttonRecalc" type="submit" dispatch="recalcPrices" styleClass="width165">
                      <bean:message key="button.recalcPrices"/>
                    </ctrl:ubutton>
                  </td>
                </tr>
              </table>
            </td>
          </tr>

          <tr>
          </tr>

          <tr>
            <td>
              <div class="gridBackNarrow" id="specificationImportGrid">
                <grid:table property="gridSpec" key="number" >
                  <grid:column align="center"><jsp:attribute name="title"><bean:message key="SpecificationImportProduces.fullName"/></jsp:attribute></grid:column>
                  <grid:column align="center"><jsp:attribute name="title"><bean:message key="SpecificationImportProduces.unit"/></jsp:attribute></grid:column>
                  <grid:column align="center"><jsp:attribute name="title"><bean:message key="SpecificationImportProduces.ctn_number"/></jsp:attribute></grid:column>
                  <grid:column align="center"><jsp:attribute name="title"><bean:message key="SpecificationImportProduces.custom_code"/>&nbsp;&nbsp;<ctrl:help htmlName="SpecificationImportProducesCustomCodeHeader"/></jsp:attribute></grid:column>
                  <grid:column align="center"><jsp:attribute name="title"><bean:message key="SpecificationImportProduces.custom_rate"/></jsp:attribute></grid:column>
                  <grid:column align="center"><jsp:attribute name="title"><bean:message key="SpecificationImportProduces.drp_price"/></jsp:attribute></grid:column>
                  <grid:column align="center"><jsp:attribute name="title"><bean:message key="SpecificationImportProduces.sip_price"/></jsp:attribute></grid:column>
                  <grid:column align="center"><jsp:attribute name="title"><bean:message key="SpecificationImportProduces.sip_count"/></jsp:attribute></grid:column>
                  <grid:column align="center"><jsp:attribute name="title"><bean:message key="SpecificationImportProduces.sip_cost"/></jsp:attribute></grid:column>
                  <grid:column align="center"><jsp:attribute name="title"><bean:message key="SpecificationImportProduces.stuffCategory"/></jsp:attribute></grid:column>
                  <grid:column align="center"><jsp:attribute name="title"><bean:message key="SpecificationImportProduces.order"/></jsp:attribute></grid:column>
                  <grid:column align="center"><jsp:attribute name="title"><bean:message key="SpecificationImportProduces.sip_weight"/></jsp:attribute></grid:column>
                  <grid:column align="center"><jsp:attribute name="title"><bean:message key="SpecificationImportProduces.customer"/></jsp:attribute></grid:column>
                  <grid:column align="center"><jsp:attribute name="title">&nbsp;<ctrl:help htmlName="SpecificationImportExpirationHeader"/></jsp:attribute></grid:column>
                  <grid:column align="center"><jsp:attribute name="title"><bean:message key="SpecificationImportProduces.purpose"/></jsp:attribute></grid:column>
                  <grid:column title=""/>
                  <grid:row enableHighlight="true">
                    <grid:colLink property="produce.fullName" type="submit" dispatch="produceView" scriptUrl="id=${record.produce.id}&allReadOnly=true" styleClassCheckerId="style-checker"/>
                    <logic:empty name="record" property='produce.unit'>
                      <grid:colCustom width="5%" styleClassCheckerId="style-checker"/>
                    </logic:empty>
                    <logic:notEmpty name="record" property='produce.unit'>
                      <grid:colCustom width="5%" property="produce.unit.name" styleClassCheckerId="style-checker"/>
                    </logic:notEmpty>
                    <grid:colCustom width="5%" property="catalogNumberForStuffCategory" styleClassCheckerId="style-checker"/>
                    <grid:colCustom width="5%" align="right" property="customCode" styleClassCheckerId="style-checker"/>
                    <grid:colInput width="5%" textAllign="right" property="custom_percent_formatted" showCheckerId="show-checker" styleClassCheckerId="style-checker"
                                   onchange="customCodeChanged"/>
                    <grid:colCustom width="5%" align="right" property="drp_price_formatted" showCheckerId="show-checker" styleClassCheckerId="style-checker"/>
                    <grid:colInput width="5%" textAllign="right" property="sip_price_formatted" showCheckerId="show-checker" styleClassCheckerId="style-checker"
                                   onchange="priceEURChanged"/>
                    <grid:colCustom width="5%" align="right" property="sip_count_formatted" showCheckerId="show-checker" styleClassCheckerId="style-checker"/>
                    <grid:colCustom width="5%" align="right" property="sip_cost_formatted" styleClassCheckerId="style-checker"/>
                    <grid:colCustom width="5%" property="stuffCategory.name" styleClassCheckerId="style-checker"/>
                    <grid:colCustom width="10%" property="ord_number_date" styleClassCheckerId="style-checker"/>
                    <grid:colInput  width="5%" textAllign="right" property="sip_weight_formatted" result="gridSpec" resultProperty="sip_weight_formatted" useIndexAsPK="true" showCheckerId="show-checker" styleClassCheckerId="style-checker"/>
                    <grid:colCustom width="5%" styleClassCheckerId="style-checker" showCheckerId="show-checker">
                      <table width="100%" cellpadding="0" cellspacing="0" class="content">
                        <tr>
                          <span fullComment="${record.fullComment}" onmouseover='showComment(this)' onmouseout="hideComment(this)"/>
                          <grid:colCustom property="customer.name"/>
                        </tr>
                      </table>
                    </grid:colCustom>
                    <grid:colCustom width="1%" property="expiration" styleClassCheckerId="style-checker"/>
                    <grid:colServerList property="purpose.name" idName="purpose.id" result="gridSpec" resultProperty="purpose.name"
                                        useIndexAsPK="true" action="/PurposesListAction" selectOnly="true" showCheckerId="show-checker" styleClassCheckerId="style-checker"/>
                    <grid:colDelete width="1" dispatch="deleteProduce" type="submit" tooltip="tooltip.SpecificationImportProduces.delete" scriptUrl="number=${record.number}" showCheckerId="show-checker" styleClassCheckerId="style-checker" readonlyCheckerId="deleteReadonly"/>
                  </grid:row>
                </grid:table>
              </div>
            </td>
          </tr>

          <tr>
            <td>
              <div class=gridBottom>
                <ctrl:ubutton type="submit" dispatch="selectDocsForSpecImport" scriptUrl="spi_id=${SpecificationImport.spi_id}&target=order" styleClass="width165">
                  <bean:message key="button.importOrder"/>
                </ctrl:ubutton>
                <ctrl:ubutton type="submit" dispatch="selectDocsForSpecImport" scriptUrl="spi_id=${SpecificationImport.spi_id}&target=delivery_request" styleClass="width260">
                  <bean:message key="button.importDeliveryRequest"/>
                </ctrl:ubutton>
              </div>
            </td>
          </tr>

          <tr>
            <td>
              <table width="100%">
                <tr>
                  <td><bean:message key="SpecificationImport.spi_arrive"/>&nbsp;&nbsp;<ctrl:checkbox property="spi_arrive_from" styleClass="checkbox" value="1"/></td>
                </tr>
              </table>
            </td>
          </tr>

          <tr>
            <td colspan="2" align="right" class=formSpace>
              &nbsp;
            </td>
          </tr>
          <tr>
            <td colspan="2" align="right">
              <ctrl:ubutton type="submit" dispatch="back" styleClass="width80" readonly="false">
                <bean:message key="button.cancel"/>
              </ctrl:ubutton>
              <input id='saveButton' type='button' onclick='processClick()' class='width80' value="<bean:message key="button.save"/>"/>
              <input type=button id="generateButtonExcel"  onclick="generateExcelClick();"  class="width165" value="<bean:message key="button.formExcel"/>">
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
  </ctrl:form>

  <script language="JScript" type="text/javascript">
    var saveButton = document.getElementById('saveButton');
    saveButton.disabled = ${SpecificationImport.formReadOnly} || ${SpecificationImport.currentUserManager};

    var spiArriveFrom = document.getElementById('spi_arrive_from');

    var buttonRecalc = document.getElementById('buttonRecalc');
    buttonRecalc.disabled = true;

    function onCourseKoefChange()
    {
      if ( buttonRecalc.disabled )
      {
        buttonRecalc.disabled = false;
      }
    }

    function processClick()
    {
      if ( spiArriveFrom.checked )
      {
        if (isUserAgree('<bean:message key="msg.specification_import.block_from_request"/>', true, 500, 140))
        {
          submitDispatchForm("process");
        }
      }
      else
      {
        submitDispatchForm("process");
      }
    }
    
		function generateExcelClick()
		{
			document.getElementById('for-insert').innerHTML='<iframe src="<ctrl:rewrite action="/SpecificationImportAction" dispatch="generateExcel"/>" style="display:none" />';
		}

    function customCodeChanged(obj, id)
    {
      doAjax({
        url:'<ctrl:rewrite action="/SpecificationImportAction" dispatch="recalcPriceByCustomPercent"/>',
        params:{recordNumberForRecalc:id,valueForRecalc:obj.value},
        callback:function()
        {
          obj.parentElement.nextSibling.nextSibling.firstChild.value = result.price;
          obj.parentElement.nextSibling.nextSibling.nextSibling.nextSibling.innerHTML = result.cost;
          var r = obj.parentElement.parentElement.parentElement.parentElement.rows;
          r(r.length - 1).childNodes[11].innerHTML = result.itogo;
        }
      });
      return false;
    }

    function priceEURChanged(obj, id)
    {
      doAjax({
        url:'<ctrl:rewrite action="/SpecificationImportAction" dispatch="recalcCostByPrice"/>',
        params:{recordNumberForRecalc:id,valueForRecalc:obj.value},
        callback:function()
        {
          obj.parentElement.nextSibling.nextSibling.innerHTML = result.cost;
          var r = obj.parentElement.parentElement.parentElement.parentElement.rows;
          r(r.length - 1).childNodes[11].innerHTML = result.itogo;
        }
      });
      return false;
    }

    function showComment(obj)
    {
      if (!(tt_iState & 0x8))
      {
        tt_HideInit();
      }
      var comment = obj.getAttribute('fullComment');
      var res = '<table width="200px" border=0 style="background-color:#aaaaaa" cellspacing=1 cellpadding=1><tr><td wrap style="background-color:#FEFFD6">' + comment + '</td></tr></table>';
      if (comment != "")
      {
        Tip(res, LEFT, false, PADDING, 0, STICKY, true, CLICKCLOSE, true, DELAY, 1, BGCOLOR, '#FEFFD6', BORDERCOLOR, '#eeeeee');
      }
    }

    function hideComment(obj)
    {
      if (!(tt_iState & 0x8))
      {
        tt_HideInit();
      }
    }
  </script>
<div id='for-insert'></div>