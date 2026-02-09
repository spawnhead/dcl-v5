<%@page pageEncoding="UTF8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%!
  String testVal = "тест";
%>
<%=testVal%><br>тест
<bean:message key="menus.custom_code" />
<bean:message key="test.Msg" />