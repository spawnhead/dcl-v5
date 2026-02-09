  <%@ taglib uri="/tags/struts-bean" prefix="bean" %>
  <%@ taglib uri="/tags/struts-html" prefix="html" %>
  <%@ taglib uri="/tags/struts-logic" prefix="logic" %>
  <%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
  <%@ taglib uri="/tags/html-grid" prefix="grid" %>

  <script language="JScript" type="text/javascript">
    function submitReloadWithClean()
    {
      submitDispatchForm("reloadWithClean");
    }
    function submitReloadWithCleanProduce()
    {
      submitDispatchForm("reloadWithCleanProduce");
    }
  </script>

  <ctrl:form readonly="${Assemble.formReadOnly}">
  <ctrl:hidden property="asm_id"/>
  <ctrl:hidden property="asm_block"/>
  <ctrl:hidden property="is_new_doc"/>
  <ctrl:hidden property="usr_date_create"/>
  <ctrl:hidden property="usr_date_edit"/>
  <ctrl:hidden property="createUser.usr_id"/>
  <ctrl:hidden property="editUser.usr_id"/>
  <logic:equal name="Assemble" property="asm_type_assemble" value="1">
    <ctrl:hidden property="produce.id"/>
  </logic:equal>
  <logic:equal name="Assemble" property="asm_type_disassemble" value="1">
    <ctrl:hidden property="stuffCategory.id"/>
  </logic:equal>
  <ctrl:hidden property="asmDisassembleCount"/>
  <table class=formBackTop align="center" width="99%">
    <tr>
      <td >
        <table cellspacing="0" width="100%" >
        <logic:notEqual name="Assemble" property="is_new_doc" value="true">
        <tr>
          <td>
            <table width="100%">
              <tr>
                <td width="10%"><bean:message key="Assemble.create"/></td>
                <td width="40%"><ctrl:write name="Assemble" property="usr_date_create"/> <ctrl:write name="Assemble" property="createUser.userFullName"/> </td>
                <td align="right"><bean:message key="Assemble.edit"/></td>
                <td width="33%">&nbsp;&nbsp;&nbsp;&nbsp;<ctrl:write name="Assemble" property="usr_date_edit"/> <ctrl:write name="Assemble" property="editUser.userFullName"/></td>
              </tr>
            </table>
          </td>
        </tr>
      </logic:notEqual>
        <tr>
          <td>
            <table width="100%">
              <tr>
                <td width="10%"><bean:message key="Assemble.asm_date"/></td>
                <td><ctrl:date property="asm_date" style="width:150px;"/></td>
              </tr>
            </table>
          </td>
        </tr>

      <logic:notEqual name="Assemble" property="is_new_doc" value="true">
        <tr>
          <td>
            <table width="100%">
              <tr>
                <td width="10%"><bean:message key="Assemble.asm_number"/></td>
                <td> <ctrl:write name="Assemble" property="asm_number"/> </td>
              </tr>
            </table>
          </td>
        </tr>
      </logic:notEqual>

        <tr>
          <td>
            <table width="100%">
              <tr>
                <td>
                  <bean:message key="Assemble.asm_type_assemble"/>&nbsp;
                  <ctrl:checkbox property="asm_type_assemble" styleClass="checkbox" value="1" onclick="assembleTypeOnClick();"/>&nbsp;&nbsp;
                  <bean:message key="Assemble.asm_type_disassemble"/>&nbsp;
                  <ctrl:checkbox property="asm_type_disassemble" styleClass="checkbox" value="1" onclick="disassembleTypeOnClick();"/>
                </td>
              </tr>
            </table>
          </td>
        </tr>

        <logic:equal name="Assemble" property="asm_type_assemble" value="1">
          <tr>
            <td>
              <table width="100%">
                <tr>
                  <td><bean:message key="Assemble.assemble_text1"/></td>
                </tr>
              </table>
            </td>
          </tr>
          <tr>
            <td>
              <table width="100%">
                <tr>
                  <td><bean:message key="Assemble.assemble_text2"/></td>
                </tr>
              </table>
            </td>
          </tr>
          <tr>
            <td>
              <table width="100%">
                <tr>
                  <td width="14%"><bean:message key="Assemble.stuffCategory"/></td>
                  <td><ctrl:serverList property="stuffCategory.name" idName="stuffCategory.id"
                                       action="/StuffCategoriesListAction" style="width:237px;"
                                       callback="submitReloadWithCleanProduce" filter="filter"/></td>
                </tr>
              </table>
            </td>
          </tr>
        </logic:equal>

        <logic:equal name="Assemble" property="asm_type_disassemble" value="1">
          <tr>
            <td>
              <table width="100%">
                <tr>
                  <td><bean:message key="Assemble.disAssembleText1"/></td>
                </tr>
              </table>
            </td>
          </tr>
        </logic:equal>

        <tr>
          <td>
            <div class="gridBackNarrow">
              <grid:table property="gridAsmDisasm" key="number" >
                <grid:column align="center"><jsp:attribute name="title"><bean:message key="AssembleProduces.produce_name"/></jsp:attribute></grid:column>
                <grid:column align="center"><jsp:attribute name="title"><bean:message key="AssembleProduces.produce_type"/></jsp:attribute></grid:column>
                <grid:column align="center"><jsp:attribute name="title"><bean:message key="AssembleProduces.produce_params"/></jsp:attribute></grid:column>
                <grid:column align="center"><jsp:attribute name="title"><bean:message key="AssembleProduces.produce_add_params"/></jsp:attribute></grid:column>
                <grid:column align="center"><jsp:attribute name="title"><bean:message key="AssembleProduces.ctn_number"/></jsp:attribute></grid:column>
                <grid:column align="center"><jsp:attribute name="title"><bean:message key="AssembleProduces.unit"/></jsp:attribute></grid:column>
                <grid:column align="center"><jsp:attribute name="title"><bean:message key="AssembleProduces.apr_count"/></jsp:attribute></grid:column>
                <grid:row>
                  <grid:colCustom property="produce.name"/>
                  <grid:colCustom width="10%" property="produce.type"/>
                  <grid:colCustom width="10%" property="produce.params"/>
                  <grid:colCustom width="10%" property="produce.addParams"/>
                  <grid:colCustom width="10%" property="catalogNumberForStuffCategory"/>
                  <logic:empty name="record" property='produce.unit'>
                    <grid:colCustom width="10%"/>
                  </logic:empty>
                  <logic:notEmpty name="record" property='produce.unit'>
                    <grid:colCustom width="10%" property="produce.unit.name"/>
                  </logic:notEmpty>
                  <grid:colCustom width="10%" align="right" property="apr_count_formatted"/>
                </grid:row>
              </grid:table>
            </div>
          </td>
        </tr>
        <tr>
          <td colspan="2" align="right" class=formSpace>
            &nbsp;
          </td>
        </tr>

        <logic:equal name="Assemble" property="asm_type_assemble" value="1">
          <tr>
            <td colspan="2" align="right">
              <ctrl:ubutton styleId="buttonSelectProduce" type="submit" dispatch="selectProduce" scriptUrl="stf_id=$(stuffCategory.id)" styleClass="width165">
                <bean:message key="button.selectFromProduce"/>
              </ctrl:ubutton>
            </td>
          </tr>
        </logic:equal>

        <logic:equal name="Assemble" property="asm_type_disassemble" value="1">
          <tr>
            <td colspan="2" align="right">
              <ctrl:ubutton type="submit" dispatch="selectFromOrder" scriptUrl="stf_id=$(stuffCategory.id)&asm_type=1" styleClass="width165">
                <bean:message key="button.importOrder"/>
              </ctrl:ubutton>
            </td>
          </tr>
        </logic:equal>

        <logic:equal name="Assemble" property="asm_type_assemble" value="1">
          <tr>
            <td>
              <table width="100%">
                <tr>
                  <td><bean:message key="Assemble.assemble_text3"/></td>
                </tr>
              </table>
            </td>
          </tr>
        </logic:equal>

        <logic:equal name="Assemble" property="asm_type_disassemble" value="1">
          <tr>
            <td>
              <table width="100%">
                <tr>
                  <td><bean:message key="Assemble.disAssembleText2"/></td>
                </tr>
              </table>
            </td>
          </tr>
        </logic:equal>

        <tr>
          <td>
            <div class="gridBackNarrow">
              <grid:table property="gridSpec" key="number" >
                <grid:column align="center"><jsp:attribute name="title"><bean:message key="AssembleProduces.produce_name"/></jsp:attribute></grid:column>
                <grid:column align="center"><jsp:attribute name="title"><bean:message key="AssembleProduces.produce_type"/></jsp:attribute></grid:column>
                <grid:column align="center"><jsp:attribute name="title"><bean:message key="AssembleProduces.produce_params"/></jsp:attribute></grid:column>
                <grid:column align="center"><jsp:attribute name="title"><bean:message key="AssembleProduces.produce_add_params"/></jsp:attribute></grid:column>
                <logic:equal name="Assemble" property="asm_type_assemble" value="1">
                  <grid:column align="center"><jsp:attribute name="title"><bean:message key="AssembleProduces.stf_name"/></jsp:attribute></grid:column>
                </logic:equal>
                <grid:column align="center"><jsp:attribute name="title"><bean:message key="AssembleProduces.ctn_number"/></jsp:attribute></grid:column>
                <grid:column align="center"><jsp:attribute name="title"><bean:message key="AssembleProduces.unit"/></jsp:attribute></grid:column>
                <grid:column align="center"><jsp:attribute name="title"><bean:message key="AssembleProduces.apr_count"/></jsp:attribute></grid:column>
                <logic:equal name="Assemble" property="asm_type_disassemble" value="1">
                  <grid:column title=""/>
                </logic:equal>
                <grid:row>
                  <grid:colCustom property="produce.name"/>
                  <grid:colCustom width="10%" property="produce.type"/>
                  <grid:colCustom width="10%" property="produce.params"/>
                  <grid:colCustom width="10%" property="produce.addParams"/>
                  <logic:equal name="Assemble" property="asm_type_assemble" value="1">
                    <grid:colCustom width="10%" property="stf_name"/>
                  </logic:equal>
                  <grid:colCustom width="10%" property="ctn_number"/>
                  <logic:empty name="record" property='produce.unit'>
                    <grid:colCustom width="10%"/>
                  </logic:empty>
                  <logic:notEmpty name="record" property='produce.unit'>
                    <grid:colCustom width="10%" property="produce.unit.name"/>
                  </logic:notEmpty>
                  <logic:equal name="Assemble" property="asm_type_assemble" value="1">
                    <grid:colCustom width="10%" align="right" property="apr_count_formatted"/>
                  </logic:equal>
                  <logic:equal name="Assemble" property="asm_type_disassemble" value="1">
                    <grid:colInput width="10%" textAllign="right" property="apr_count_formatted" result="gridSpec" resultProperty="apr_count_formatted" useIndexAsPK="true"/>
                  </logic:equal>
                  <logic:equal name="Assemble" property="asm_type_disassemble" value="1">
                    <grid:colDelete width="1" dispatch="deleteProduce" type="submit" tooltip="tooltip.Assembles.delete" readonlyCheckerId="deleteReadonly"/>
                  </logic:equal>
                </grid:row>
              </grid:table>
            </div>
          </td>
        </tr>
        <tr>
          <td colspan="2" align="right" class=formSpace>
            &nbsp;
          </td>
        </tr>

        <logic:equal name="Assemble" property="asm_type_assemble" value="1">
          <tr>
            <td colspan="2" align="right">
              <ctrl:ubutton styleId="buttonSelectOrder" type="submit" dispatch="selectFromOrder"  scriptUrl="stf_id=$(stuffCategory.id)&asm_type=0" styleClass="width165">
                <bean:message key="button.importOrder"/>
              </ctrl:ubutton>
            </td>
          </tr>
        </logic:equal>

        <logic:equal name="Assemble" property="asm_type_disassemble" value="1">
          <tr>
            <td colspan="2" align="right">
              <ctrl:ubutton styleId="buttonSelectProduce" type="submit" dispatch="selectProduce" scriptUrl="stf_id=$(stuffCategory.id)" styleClass="width165">
                <bean:message key="button.selectFromProduce"/>
              </ctrl:ubutton>
            </td>
          </tr>
        </logic:equal>

        <tr>
          <td>
            <table width="100%">
              <tr>
                <logic:equal name="Assemble" property="asm_type_assemble" value="1">
                  <td width="10%"><bean:message key="Assemble.asm_count"/></td>
                </logic:equal>
                <logic:equal name="Assemble" property="asm_type_disassemble" value="1">
                  <td width="12%"><bean:message key="Assemble.disasm_count"/></td>
                </logic:equal>
                <td> <ctrl:text name="Assemble" property="asm_count" style="width:50px;text-align:right;"/> </td>
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
            <ctrl:ubutton type="submit" dispatch="process" styleClass="width80"  >
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
    var asmDisassembleCount = document.getElementById('asmDisassembleCount');
    var asmTypeAssemble = document.getElementById('asm_type_assemble');
    var asmTypeDisassemble = document.getElementById('asm_type_disassemble');

  <logic:equal name="Assemble" property="asm_type_assemble" value="1">
    var stuffCategoryId = document.getElementById('stuffCategory.id');
    var buttonSelectProduce = document.getElementById('buttonSelectProduce');

    var produceId = document.getElementById('produce.id');
    var buttonSelectOrder = document.getElementById('buttonSelectOrder');
  </logic:equal>
  <logic:equal name="Assemble" property="asm_type_disassemble" value="1">
    var stuffCategoryId = document.getElementById('stuffCategory.id');
    var buttonSelectProduce = document.getElementById('buttonSelectProduce');
  </logic:equal>

  <logic:notEqual value="true" name="Assemble" property="formReadOnly">
    <logic:equal name="Assemble" property="asm_type_assemble" value="1">
      onStuffCategorySelect();
      onProduceSelect();
    </logic:equal>
    <logic:equal name="Assemble" property="asm_type_disassemble" value="1">
      onStuffCategorySelect();
    </logic:equal>
  </logic:notEqual>

    function onStuffCategorySelect()
    {
      buttonSelectProduce.disabled = (stuffCategoryId.value == '');
    }

    function onProduceSelect()
    {
      buttonSelectOrder.disabled = (produceId.value == '');
    }

    function assembleTypeOnClick()
    {
      if ( asmTypeAssemble.checked )
      {
        if ( asmDisassembleCount.value != '0' )
        {
          if (isUserAgree('<bean:message key="msg.assemble.clean_table"/>', true, 400, 100))
          {
            asmTypeDisassemble.checked = false;
            submitReloadWithClean();
          }
          else
          {
            asmTypeAssemble.checked = !asmTypeAssemble.checked;
          }
        }
        else
        {
          asmTypeDisassemble.checked = false;
          submitReloadWithClean();
        }
      }
      else
      {
        asmTypeAssemble.checked = true;
      }
    }

    function disassembleTypeOnClick()
    {
      if ( asmTypeDisassemble.checked )
      {
        if ( asmDisassembleCount.value != '0' )
        {
          if (isUserAgree('<bean:message key="msg.assemble.clean_table"/>', true, 400, 100))
          {
            asmTypeAssemble.checked = false;
            submitReloadWithClean();
          }
          else
          {
            asmTypeDisassemble.checked = !asmTypeDisassemble.checked;
          }
        }
        else
        {
          asmTypeAssemble.checked = false;
          submitReloadWithClean();
        }
      }
      else
      {
        asmTypeDisassemble.checked = true;
      }
    }

  </script>
