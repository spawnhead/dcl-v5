<%@ page import="java.util.Map"   %>
<%@ page import="java.util.HashMap"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>
<%!
  String testVal = "тест";
%>
Журнал «Коммерческие предложения»
<bean:message key="menus.custom_code" locale="ru"/>
<%--<ctrl:askUser name="test" key="test.Msg" arg0="aaaaaaaaaaaaaaa" showOkCancel="false" />--%>
<%--<ctrl:dialog name="test" width="800" height="800"   />--%>
<%=testVal%><br>тест
<ctrl:form >

  <input type=text name='test' value=''>
  <input type=text name='test' value='bbbbbbbbbbbbbbb'>
  <input type=text name='test' value='aaaaaaaaaaaaaaaa'>
  <ctrl:ubutton type='submit' dispatch='test2' styleClass='width60'>Test</ctrl:ubutton>
  <ctrl:ulink type='link' dispatch='test2'>Test</ctrl:ulink>
  <ctrl:serverList property="test1" size="40" action="test-list"  /><br><br><br><br>
  <ctrl:date property="test2" size="40" ></ctrl:date><br><br><br><br><br><br><br><br><br>
  <grid:table property="gridCtrl2"   key="id,name"  >
        <grid:column width="10"  />
        <grid:column width="10%" title="abc1" align="left" />
        <grid:column  title="abc1" align="left" />
        <grid:column width="10%" title="abc1" align="left" />
        <grid:column  title="abc1" align="left" />
        <%--<grid:column   title="abc2" align="center" />--%>
        <grid:column  />
        <grid:column  />
        <grid:column  />
<%--        <grid:column width="10" title="abc3" align="left" />--%>
<%--        <grid:column width="10" title="abc4" align="left" />--%>
<%--        <grid:column width="10" title="abc5" align="left" />--%>
<%--        <grid:column width="20" title="abc2" align="left" />--%>
        <grid:row  >
          <grid:colCustom width="50" >$(id)-$(name)-${record.id}</grid:colCustom>
          <grid:colCustom property="id" ></grid:colCustom>
            <%--<grid:colCheckbox  property="property2"    />--%>

            <grid:colLink property="id"   type="link" askUser="test" scriptUrl="test1=$(test)&test2=$(test)"/>
            <grid:colLink property="name"   type="submit" scriptUrl="test1=$(test)&test2=$(test)" />
            <%--<grid:colLink property="group"   type ="frame" />--%>
            <grid:colLink property="id"   type="dialog" dialog="test" />
            <grid:colButton   width="1" type="link" askUser="test" scriptUrl="$(test)&test2=$(test)" tooltip="tooltip.sk.anl-estw.rechner-anzahl.edit">Test</grid:colButton>
            <grid:colEdit   width="1" dialog="test"  askUser="test" scriptUrl="$(test)&test2=$(test)" tooltip="tooltip.sk.anl-estw.rechner-anzahl.edit"/>
            <grid:colDelete  width="1"  askUser="test" scriptUrl="$(test)&test2=$(test)" tooltip="tooltip.sk.anl-estw.rechner-anzahl.edit"/>
<!---->
        </grid:row>
      </grid:table>
    <%--<ctrl:ubutton type="submit" >Submit</ctrl:ubutton>--%>
</ctrl:form>

