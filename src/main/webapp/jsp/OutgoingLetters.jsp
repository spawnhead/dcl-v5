<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>

<ctrl:form styleId="striped-form">
  <table id=filterTbl align="center" border="0" width="100%">
    <tr>
      <td colspan="6"></td>
      <td align="right"><bean:message key="OutgoingLetters.date"/></td>
    </tr>
    <tr>
      <td align="right"><bean:message key="OutgoingLetters.number"/></td>
      <td><ctrl:text property="number" styleClass="filter"/></td>
      <td width="20">&nbsp;</td>

      <td align="right"><bean:message key="OutgoingLetters.contractor"/></td>
      <td><ctrl:serverList property="contractor.name" idName="contractor.id" action="/ContractorsListAction" styleClass="filter" /></td>
      <td width="20">&nbsp;</td>

      <td align="right"><bean:message key="OutgoingLetters.start"/></td>
      <td ><ctrl:date property="date_begin" styleClass="filter" endField="date_end" showHelp="false"/></td>
    </tr>
    <tr>
      <td align="right"><bean:message key="OutgoingLetters.user"/></td>
      <td><ctrl:serverList property="user.usr_name" idName="user.usr_id" action="/UsersListAction" styleClass="filter" /></td>
      <td width="20">&nbsp;</td>

	    <td align="right"><bean:message key="OutgoingLetters.seller"/></td>
      <td ><ctrl:serverList property="seller.name" idName="seller.id" action="/SellersListAction" styleClass="filter"/></td>
      <td width="20">&nbsp;</td>

      <td align="right"><bean:message key="OutgoingLetters.end"/></td>
      <td ><ctrl:date property="date_end" styleClass="filter" startField="date_begin" showHelp="false"/></td>
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
    <grid:table property="gridOutgoingLetters" key="otl_id" autoLockName="OutgoingLetter" >
      <grid:column align="center" width="10%"><jsp:attribute name="title"><bean:message key="OutgoingLetters.number"/></jsp:attribute></grid:column>
      <grid:column align="center" width="7%"><jsp:attribute name="title"><bean:message key="OutgoingLetters.date"/></jsp:attribute></grid:column>
      <grid:column align="center"><jsp:attribute name="title"><bean:message key="OutgoingLetters.contractor"/></jsp:attribute></grid:column>
      <grid:column width="1%" title=""/>
      <grid:column width="1%" title=""/>
      <grid:row>
        <grid:colCustom width="20%" property="otl_number"/>
        <grid:colCustom width="20%" property="otl_date_formatted"/>
        <grid:colCustom property="otl_contractor"/>
        <grid:colEdit width="1%" action="/OutgoingLetterAction.do?dispatch=edit" type="link" tooltip="tooltip.OutgoingLetters.edit"/>
        <grid:colImage type="link" enableOnClickAction="false" width="1%" styleClass="grid-image-without-border" image="images/attach${record.attach_idx}.gif" tooltip="tooltip.OutgoingLetters.attach${record.attach_idx}"/>
      </grid:row>
    </grid:table>
  </div>

  <div class=gridBottom>
    <ctrl:ubutton type="link" action="/OutgoingLetterAction.do?dispatch=input" styleClass="width80">
      <bean:message key="button.create"/>
    </ctrl:ubutton>
  </div>
</ctrl:form>
