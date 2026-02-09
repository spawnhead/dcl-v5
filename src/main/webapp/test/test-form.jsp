<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>

<ctrl:form method="post">

<ctrl:text property="name1"/>
<ctrl:text property="name2"/>
<ctrl:text property="name3"/>
<ctrl:ubutton type="submit" dispatch="test2" >Submit</ctrl:ubutton>
<ctrl:ulink type="link" dispatch="test1" >Link</ctrl:ulink>
<bean:message key='errors.general' arg0="sdfsf"/>
<br>
<%--<ctrl:ulink type="link"  page="/rep/LinteraRep_v2.doc">Report</ctrl:ulink>  --%>
<a href="rep/LinteraRep_v2.doc">Report</a>    
</ctrl:form>
${param.test}
<%=request.getParameter("test")%>
