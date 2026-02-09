<%--<%@ page isELIgnored='true'%>--%>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<ctrl:form >
<ctrl:text property="name1" /> 
<ctrl:text property="name2" />
<!--<script type="text/javascript" >-->
<!--</script>-->

<ctrl:askUser name="ask" key="test.Msg" arg0="123" showOkCancel="true"/>

<%--<ctrl:ubutton type="submit" dispatch="test" >Test link</ctrl:ubutton>--%>
<ctrl:ubutton  askUser="ask"
    type="submit" dispatch="test1" scriptUrl="name1=${Test.name2}&name2=${Test.name1}"
              disableControls="true" >Test link</ctrl:ubutton>
<%--<ctrl:ubutton  paramId="param" paramName="Test" paramProperty="name1" --%>
    <%--type="link" dispatch="test1" --%>
               <%-->Test link</ctrl:ubutton>--%>
<%--<ctrl:ubutton  type="link" dispatch="test1"--%>
               <%-->Test link</ctrl:ubutton>--%>

<ctrl:serverList property="name3" action="/test-list" scriptUrl="name1=$(name2)&name2=$(name1)" />
<ctrl:serverList property="value" idName="key" action="/test-map"  />
<ctrl:date property="name4" size="40" ></ctrl:date>
  тест
</ctrl:form>

