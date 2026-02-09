<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>
<ctrl:form >
  <ctrl:hidden property="bln_id"/>
  <logic:notEqual name="Blank" property="showForBlankType" value="true">
    <ctrl:hidden property="bln_charset"/>
    <ctrl:hidden property="language.id"/>
  </logic:notEqual>
  <table class=formBack align="center"><tr><td >
  <table cellspacing="2"  >
    <tr>
      <td><bean:message key="Blank.bln_name"/></td>
      <td><ctrl:text property="bln_name"/></td>
    </tr>
    <tr>
      <td><bean:message key="Blank.sellerName"/></td>
      <td><ctrl:serverList property="seller.name" idName="seller.id" action="/SellersListAction"
                           selectOnly="true" style="width:330px;"/></td>
    </tr>
    <tr>
      <td><bean:message key="Blank.bln_type"/></td>
      <td><ctrl:serverList property="blankType.name" idName="blankType.id" action="/BlankTypesListAction"
                           selectOnly="true" callback="submitReload" style="width:330px;"/></td>
    </tr>
    <tr>
      <td><bean:message key="Blank.bln_usage"/></td>
      <td><ctrl:textarea property="bln_usage" style="width:350px;height:80px;"/></td>
    </tr>

    <logic:equal name="Blank" property="showForBlankType" value="true">
      <tr>
        <td><bean:message key="Blank.bln_charset"/></td>
        <td><ctrl:text property="bln_charset"/></td>
      </tr>
      <tr>
        <td><bean:message key="Blank.bln_language"/></td>
        <td>
          <ctrl:serverList property="language.name" idName="language.id" action="/LanguagesListAction"
                           selectOnly="true" style="width:330px;"/>
        </td>
      </tr>
      <tr>
        <td><bean:message key="Blank.bln_preamble"/></td>
        <td><ctrl:textarea property="bln_preamble" style="width:350px;height:125px;"/></td>
      </tr>
      <tr>
        <td><bean:message key="Blank.bln_note"/></td>
        <td><ctrl:textarea property="bln_note" style="width:350px;height:125px;"/></td>
      </tr>
    </logic:equal>

    <tr>
      <td colspan="10">
        <div class="gridBack">
          <grid:table property="gridImages" key="number" scrollableGrid="false" autoLockName="BlankImages">
            <grid:column align="center"><jsp:attribute name="title"><bean:message key="BlankImage.bim_name"/></jsp:attribute></grid:column>
            <grid:column align="center"><jsp:attribute name="title"><bean:message key="BlankImage.bim_image"/></jsp:attribute></grid:column>
            <grid:row>
              <grid:colCustom width="50%" property="bim_name"/>
              <grid:colServerList property="bim_image" action="/HeadImgsListAction" result="gridImages"
                                  resultProperty="bim_image" useIndexAsPK="true" selectOnly="true"/>
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

<script language="JScript" type="text/javascript">
  function submitReload()
  {
    submitDispatchForm("reload");
  }
</script>