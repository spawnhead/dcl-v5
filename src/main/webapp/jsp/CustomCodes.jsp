<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>

<ctrl:askUser name="ask_block" key="msg.CustomCodes.block" showOkCancel="false" height="120"/>
<ctrl:askUser name="ask_unblock" key="msg.CustomCodes.unblock" showOkCancel="false" height="140"/>

<script type="text/javascript">
    function getNecessaryDiv() {
        divs = $$('div');
        for (i = 0; i < divs.length; i++) {
            var firstChild = Element.down(divs[i])
            if (firstChild && firstChild.id && (firstChild.id == 'tableBodygrid')) {
                return divs[i];
            }
        }
    }

    window.onload = function () {
        var necessaryDiv = getNecessaryDiv();
        if (necessaryDiv) {
            scrollPos = getCookie("scroll");
            if (scrollPos) {
                necessaryDiv.scrollTop = parseInt(scrollPos);
            }
            necessaryDiv.onscroll = function () {
                setCookie("scroll", this.scrollTop);
            }
        }
    }
</script>

<ctrl:form styleId="striped-form">
  <div class="gridBack">
    <grid:table property="grid" key="cus_code" scrollableGrid="true" height="expression(document.body.clientHeight-235)">
      <grid:column width="20%" align="center"><jsp:attribute name="title"><bean:message key="CustomCodes.cus_code"/></jsp:attribute></grid:column>
      <grid:column width="50"><jsp:attribute name="title">&nbsp;&nbsp;<ctrl:help htmlName="CustomCodesLaw240Header"/></jsp:attribute></grid:column>
      <grid:column align="center"><jsp:attribute name="title"><bean:message key="CustomCodes.cus_description"/></jsp:attribute></grid:column>
      <grid:column width="10%" align="center"><jsp:attribute name="title"><bean:message key="CustomCodes.cus_percent"/></jsp:attribute></grid:column>
      <grid:column width="10%" title=""/>
      <grid:column width="1%" title=""/>
      <grid:column align="center" width="30"><jsp:attribute name="title"><img title='<bean:message key="tooltip.CustomCodes.blockStatus"/>' src='<ctrl:rewrite page="/images/lock.gif"/>'>&nbsp;&nbsp;<ctrl:help htmlName="CustomCodesBlock0Header"/></jsp:attribute></grid:column>
      <grid:column width="1%" title=""/>
      <grid:row>
        <grid:colCustom width="20%" property="cus_code"/>
        <grid:colCheckbox width="30" align="center" property="cus_law_240_flag" type="submit" dispatch="checkLaw240" scriptUrl="cus_law_240_flag=${record.cus_law_240_flag}"/>
        <grid:colCustom property="cus_description"/>
        <grid:colCustom width="10%" align="right" property="cus_percent_formatted"/>
        <grid:colLink width="10%" action="/CustomCodeFastCreateAction.do?dispatch=create" type="link" align="center">
          <bean:message key="CustomCodes.fastCreate"/>
        </grid:colLink>
        <grid:colLink width="1%" action="/CustomCodeHistoryAction.do" type="link" align="center">
          <bean:message key="CustomCodes.history"/>
        </grid:colLink>
        <grid:colCheckbox align="center" width="50" property="cus_block" askUser="${record.msgCheckBlock}" readonlyCheckerId="blockChecker" type="submit" dispatch="block" scriptUrl="block=${record.cus_block}"/>
        <grid:colEdit width="1%" action="/CustomCodeAction.do?dispatch=edit" type="link" tooltip="tooltip.CustomCodes.edit" />
      </grid:row>
    </grid:table>
  </div>

  <div class=gridBottom>
    <ctrl:ubutton type="link" action="/CustomCodeAction.do?dispatch=create" styleClass="width80">
      <bean:message key="button.create"/>
    </ctrl:ubutton>
  </div>


</ctrl:form>
