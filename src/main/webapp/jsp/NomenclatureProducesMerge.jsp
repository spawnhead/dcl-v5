<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>

<ctrl:form>
  <ctrl:hidden property="oldId"/>
  <ctrl:hidden property="newId"/>
  <table class=formBackTop align="center">
    <tr>
      <td colspan="2">
        <table cellspacing="0" cellpadding="0" border="0">
          <tr>
            <td  colspan="2">
              <bean:message key="NomenclatureProducesMerge.textOver"/>
            </td>
          </tr>

          <tr>
            <td colspan="2" align="right" class=formSpace>
              &nbsp;
            </td>
          </tr>

          <tr>
            <td colspan="2">
              <table cellspacing="0" cellpadding="0" border="0">
                <tr>
                  <td>
                    <table cellspacing="5" cellpadding="0" border="0">
                      <tr>
                        <td>
                          <ctrl:checkbox property="leftNameRU" styleClass="checkbox" value="1" onclick="onLeftClick(this)"/>
                        </td>
                        <td width="150px">
                          <bean:message key="NomenclatureProducesMerge.leftNameRU"/>
                        </td>
                        <td>
                          <ctrl:text property="sourceProduce.name" style="width:300px;"/>
                        </td>
                      </tr>

                      <tr>
                        <td>
                          <ctrl:checkbox property="leftNameEN" styleClass="checkbox" value="1" onclick="onLeftClick(this)"/>
                        </td>
                        <td>
                          <bean:message key="NomenclatureProducesMerge.leftNameEN"/>
                        </td>
                        <td>
                          <ctrl:text property="leftNameENValue" style="width:300px;"/>
                        </td>
                      </tr>

                      <tr>
                        <td>
                          <ctrl:checkbox property="leftNameDE" styleClass="checkbox" value="1" onclick="onLeftClick(this)"/>
                        </td>
                        <td>
                          <bean:message key="NomenclatureProducesMerge.leftNameDE"/>
                        </td>
                        <td>
                          <ctrl:text property="leftNameDEValue" style="width:300px;"/>
                        </td>
                      </tr>

                      <tr>
                        <td>
                          <ctrl:checkbox property="leftType" styleClass="checkbox" value="1" onclick="onLeftClick(this)"/>
                        </td>
                        <td>
                          <bean:message key="NomenclatureProducesMerge.leftType"/>
                        </td>
                        <td>
                          <ctrl:text property="sourceProduce.type" style="width:300px;"/>
                        </td>
                      </tr>

                      <tr>
                        <td>
                          <ctrl:checkbox property="leftParams" styleClass="checkbox" value="1" onclick="onLeftClick(this)"/>
                        </td>
                        <td>
                          <bean:message key="NomenclatureProducesMerge.leftParams"/>
                        </td>
                        <td>
                          <ctrl:text property="sourceProduce.params" style="width:300px;"/>
                        </td>
                      </tr>

                      <tr>
                        <td>
                          <ctrl:checkbox property="leftAddParams" styleClass="checkbox" value="1" onclick="onLeftClick(this)"/>
                        </td>
                        <td>
                          <bean:message key="NomenclatureProducesMerge.leftAddParams"/>
                        </td>
                        <td>
                          <ctrl:text property="sourceProduce.addParams" style="width:300px;"/>
                        </td>
                      </tr>

                      <tr>
                        <td>
                          <ctrl:checkbox property="leftUnit" styleClass="checkbox" value="1" onclick="onLeftClick(this)"/>
                        </td>
                        <td>
                          <bean:message key="NomenclatureProducesMerge.leftUnit"/>
                        </td>
                        <td>
                          <ctrl:serverList property="sourceUnit.name" idName="sourceUnit.id" action="/UnitsListAction"
                                           selectOnly="true" style="width:280px;"/>
                        </td>
                      </tr>

                      <tr>
                        <td>
                          <ctrl:checkbox property="leftMaterial" styleClass="checkbox" value="1" onclick="onLeftClick(this)"/>
                        </td>
                        <td>
                          <bean:message key="NomenclatureProducesMerge.leftMaterial"/>
                        </td>
                        <td>
                          <ctrl:textarea styleClass="three-lines" style="width:300px;" property="sourceProduce.material"/>
                        </td>
                      </tr>

                      <tr>
                        <td>
                          <ctrl:checkbox property="leftPurporse" styleClass="checkbox" value="1" onclick="onLeftClick(this)"/>
                        </td>
                        <td>
                          <bean:message key="NomenclatureProducesMerge.leftPurporse"/>
                        </td>
                        <td>
                          <ctrl:textarea styleClass="three-lines" style="width:300px;" property="sourceProduce.purpose"/>
                        </td>
                      </tr>

                      <tr>
                        <td>
                          <ctrl:checkbox property="leftSpecification" styleClass="checkbox" value="1" onclick="onLeftClick(this)"/>
                        </td>
                        <td>
                          <bean:message key="NomenclatureProducesMerge.leftSpecification"/>
                        </td>
                        <td>
                          <ctrl:textarea styleClass="three-lines" style="width:300px;" property="sourceProduce.specification"/>
                        </td>
                      </tr>

                      <tr>
                        <td>
                          <ctrl:checkbox property="leftPrinciple" styleClass="checkbox" value="1" onclick="onLeftClick(this)"/>
                        </td>
                        <td>
                          <bean:message key="NomenclatureProducesMerge.leftPrinciple"/>
                        </td>
                        <td>
                          <ctrl:textarea styleClass="three-lines" style="width:300px;" property="sourceProduce.principle"/>
                        </td>
                      </tr>
                    </table>
                  </td>

                  <td width="40px">
                    &nbsp;
                  </td>

                  <td>
                    <table cellspacing="5" cellpadding="0" border="0">
                      <tr>
                        <td>
                          <ctrl:checkbox property="rightNameRU" styleClass="checkbox" value="1" onclick="onRightClick(this)"/>
                        </td>
                        <td width="150px">
                          <bean:message key="NomenclatureProducesMerge.rightNameRU"/>
                        </td>
                        <td>
                          <ctrl:text property="targetProduce.name" style="width:300px;"/>
                        </td>
                      </tr>

                      <tr>
                        <td>
                          <ctrl:checkbox property="rightNameEN" styleClass="checkbox" value="1" onclick="onRightClick(this)"/>
                        </td>
                        <td>
                          <bean:message key="NomenclatureProducesMerge.rightNameEN"/>
                        </td>
                        <td>
                          <ctrl:text property="rightNameENValue" style="width:300px;"/>
                        </td>
                      </tr>

                      <tr>
                        <td>
                          <ctrl:checkbox property="rightNameDE" styleClass="checkbox" value="1" onclick="onRightClick(this)"/>
                        </td>
                        <td>
                          <bean:message key="NomenclatureProducesMerge.rightNameDE"/>
                        </td>
                        <td>
                          <ctrl:text property="rightNameDEValue" style="width:300px;"/>
                        </td>
                      </tr>

                      <tr>
                        <td>
                          <ctrl:checkbox property="rightType" styleClass="checkbox" value="1" onclick="onRightClick(this)"/>
                        </td>
                        <td>
                          <bean:message key="NomenclatureProducesMerge.rightType"/>
                        </td>
                        <td>
                          <ctrl:text property="targetProduce.type" style="width:300px;"/>
                        </td>
                      </tr>

                      <tr>
                        <td>
                          <ctrl:checkbox property="rightParams" styleClass="checkbox" value="1" onclick="onRightClick(this)"/>
                        </td>
                        <td>
                          <bean:message key="NomenclatureProducesMerge.rightParams"/>
                        </td>
                        <td>
                          <ctrl:text property="targetProduce.params" style="width:300px;"/>
                        </td>
                      </tr>

                      <tr>
                        <td>
                          <ctrl:checkbox property="rightAddParams" styleClass="checkbox" value="1" onclick="onRightClick(this)"/>
                        </td>
                        <td>
                          <bean:message key="NomenclatureProducesMerge.rightAddParams"/>
                        </td>
                        <td>
                          <ctrl:text property="targetProduce.addParams" style="width:300px;"/>
                        </td>
                      </tr>

                      <tr>
                        <td>
                          <ctrl:checkbox property="rightUnit" styleClass="checkbox" value="1" onclick="onRightClick(this)"/>
                        </td>
                        <td>
                          <bean:message key="NomenclatureProducesMerge.rightUnit"/>
                        </td>
                        <td>
                          <ctrl:serverList property="targetUnit.name" idName="targetUnit.id" action="/UnitsListAction"
                                           selectOnly="true" style="width:280px;"/>
                        </td>
                      </tr>

                      <tr>
                        <td>
                          <ctrl:checkbox property="rightMaterial" styleClass="checkbox" value="1" onclick="onRightClick(this)"/>
                        </td>
                        <td>
                          <bean:message key="NomenclatureProducesMerge.rightMaterial"/>
                        </td>
                        <td>
                          <ctrl:textarea styleClass="three-lines" style="width:300px;" property="targetProduce.material"/>
                        </td>
                      </tr>

                      <tr>
                        <td>
                          <ctrl:checkbox property="rightPurporse" styleClass="checkbox" value="1" onclick="onRightClick(this)"/>
                        </td>
                        <td>
                          <bean:message key="NomenclatureProducesMerge.rightPurporse"/>
                        </td>
                        <td>
                          <ctrl:textarea styleClass="three-lines" style="width:300px;" property="targetProduce.purpose"/>
                        </td>
                      </tr>

                      <tr>
                        <td>
                          <ctrl:checkbox property="rightSpecification" styleClass="checkbox" value="1" onclick="onRightClick(this)"/>
                        </td>
                        <td>
                          <bean:message key="NomenclatureProducesMerge.rightSpecification"/>
                        </td>
                        <td>
                          <ctrl:textarea styleClass="three-lines" style="width:300px;" property="targetProduce.specification"/>
                        </td>
                      </tr>

                      <tr>
                        <td>
                          <ctrl:checkbox property="rightPrinciple" styleClass="checkbox" value="1" onclick="onRightClick(this)"/>
                        </td>
                        <td>
                          <bean:message key="NomenclatureProducesMerge.rightPrinciple"/>
                        </td>
                        <td>
                          <ctrl:textarea styleClass="three-lines" style="width:300px;" property="targetProduce.principle"/>
                        </td>
                      </tr>
                    </table>
                  </td>
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
            <td  colspan="2">
              <bean:message key="NomenclatureProducesMerge.textUnder"/>
            </td>
          </tr>

          <tr>
            <td colspan="2" align="right" class=formSpace>
              &nbsp;
            </td>
          </tr>

          <tr>
            <td colspan="2" align="right">
              <ctrl:ubutton type="link" actionForward="back" styleClass="width80" readonly="false">
                <bean:message key="button.cancel"/>
              </ctrl:ubutton>
              <input type=button id="submitBtn" onclick="return checkFormCheckboxes();" class="width80" value="<bean:message key="button.merge"/>">
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>

</ctrl:form>

<script language="JScript" type="text/javascript">
  function onLeftClick(obj)
  {
    if ( obj.checked )
    {
      var otherCheckBox = document.getElementsByName(obj.name.replace('left', 'right'))[0];
      otherCheckBox.checked = false;
    }
  }

  function onRightClick(obj)
  {
    if ( obj.checked )
    {
      var otherCheckBox = document.getElementsByName(obj.name.replace('right', 'left'))[0];
      otherCheckBox.checked = false;
    }
  }

  function checkFormCheckboxes()
  {
    if (
         ( !document.getElementsByName('leftNameRU')[0].checked && !document.getElementsByName('rightNameRU')[0].checked ) ||
         ( !document.getElementsByName('leftNameEN')[0].checked && !document.getElementsByName('rightNameEN')[0].checked ) ||
         ( !document.getElementsByName('leftNameDE')[0].checked && !document.getElementsByName('rightNameDE')[0].checked ) ||
         ( !document.getElementsByName('leftType')[0].checked && !document.getElementsByName('rightType')[0].checked ) ||
         ( !document.getElementsByName('leftParams')[0].checked && !document.getElementsByName('rightParams')[0].checked ) ||
         ( !document.getElementsByName('leftAddParams')[0].checked && !document.getElementsByName('rightAddParams')[0].checked ) ||
         ( !document.getElementsByName('leftUnit')[0].checked && !document.getElementsByName('rightUnit')[0].checked ) ||
         ( !document.getElementsByName('leftMaterial')[0].checked && !document.getElementsByName('rightMaterial')[0].checked ) ||
         ( !document.getElementsByName('leftPurporse')[0].checked && !document.getElementsByName('rightPurporse')[0].checked ) ||
         ( !document.getElementsByName('leftSpecification')[0].checked && !document.getElementsByName('rightSpecification')[0].checked ) ||
         ( !document.getElementsByName('leftPrinciple')[0].checked && !document.getElementsByName('rightPrinciple')[0].checked )
       )
    {
      alert('<bean:message key="msg.merge_contractors.msg_incorrect_check"/>');
    }
    else
    {
      submitDispatchForm("process");
    }
  }
</script>
