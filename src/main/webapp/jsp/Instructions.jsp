<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>

<ctrl:form styleId="striped-form">
  <table id=filterTbl align="center" border="0" width="100%">
    <tr>
      <td>
        <table align="center" border="0" width="100%">
          <tr>
            <td colspan="6"></td>
            <td align="right"><bean:message key="Instructions.date"/></td>
          </tr>

          <tr>
            <td width="200px" align="right"><bean:message key="Instructions.number"/></td>
            <td><ctrl:text property="number" styleClass="filter"/></td>
            <td width="20">&nbsp;</td>

            <td align="right"><bean:message key="Instructions.type"/></td>
            <td>
              <ctrl:serverList property="type.name" idName="type.id" action="/InstructionTypesListAction"
                               styleClass="filter" selectOnly="true" scriptUrl="have_all=true"/>
            </td>
            <td width="20">&nbsp;</td>

            <td align="right"><bean:message key="Instructions.start"/></td>
            <td ><ctrl:date property="date_begin" styleClass="filter" endField="date_end" showHelp="false"/></td>
          </tr>

          <tr>
            <td width="200px" align="right"><bean:message key="Instructions.showActive"/></td>
            <td><ctrl:checkbox property="showActive" styleClass="checkbox_filter" value="1"/></td>
            <td width="20">&nbsp;</td>

            <td colspan="3"></td>

            <td align="right"><bean:message key="Instructions.end"/></td>
            <td ><ctrl:date property="date_end" styleClass="filter" startField="date_begin" showHelp="false"/></td>
          </tr>
        </table>
      </td>
    </tr>

    <tr>
      <td>
        <table align="center" border="0" width="100%">
          <tr>
            <td width="200px" align="right"><bean:message key="Instructions.ins_concerning"/></td>
            <td><ctrl:text property="ins_concerning" style="width:580px;"/></td>
          </tr>
        </table>
      </td>
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
    <grid:table property="gridInstructions" key="ins_id">
      <th class="table-header" rowspan="3"><bean:message key="Instructions.type"/></th>
      <th class="table-header" rowspan="3"><bean:message key="Instructions.ins_number"/></th>
      <th class="table-header" colspan="3"><bean:message key="Instructions.ins_date"/></th>
      <th class="table-header" rowspan="3"><bean:message key="Instructions.ins_concerning"/></th>
      <th class="table-header" rowspan="3"></th>
      <th class="table-header" rowspan="3"></th>
      </tr><tr>
      <th class="table-header" rowspan="2"><bean:message key="Instructions.ins_date_sign"/></th>
      <th class="table-header" colspan="2"><bean:message key="Instructions.ins_date_act"/></th>
      </tr><tr>
      <grid:column align="center"><jsp:attribute name="title"><bean:message key="Instructions.ins_date_from"/></jsp:attribute></grid:column>
      <grid:column align="center"><jsp:attribute name="title"><bean:message key="Instructions.ins_date_to"/></jsp:attribute></grid:column>
      <grid:row>
        <grid:colCustom width="10%" property="ins_type" styleClassCheckerId="style-checker"/>
        <grid:colCustom width="15%" property="ins_number" styleClassCheckerId="style-checker"/>
        <grid:colCustom width="7%" property="ins_date_sign_formatted" styleClassCheckerId="style-checker"/>
        <grid:colCustom width="7%" property="ins_date_from_formatted" styleClassCheckerId="style-checker"/>
        <grid:colCustom width="7%" property="ins_date_to_formatted" styleClassCheckerId="style-checker"/>
        <grid:colCustom property="ins_concerning" styleClassCheckerId="style-checker"/>
        <grid:colEdit width="1%" action="/InstructionAction.do?dispatch=edit" type="link" tooltip="tooltip.Instructions.edit"/>
        <grid:colImage type="link" enableOnClickAction="false" width="1%" styleClass="grid-image-without-border" image="images/attach${record.attach_idx}.gif" tooltip="tooltip.Instructions.attach${record.attach_idx}"/>
      </grid:row>
    </grid:table>
  </div>

  <div class=gridBottom>
    <ctrl:ubutton type="link" action="/InstructionAction.do?dispatch=input" styleClass="width80">
      <bean:message key="button.create"/>
    </ctrl:ubutton>
  </div>
</ctrl:form>

