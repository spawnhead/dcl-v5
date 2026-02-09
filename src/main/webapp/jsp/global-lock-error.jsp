<%@ page import="org.apache.commons.logging.Log,
                 org.apache.commons.logging.LogFactory,
                 java.io.PrintWriter,
                 java.io.IOException "%>

<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>


Record already locked<br>
<ctrl:ubutton type="link" action="/ShowPrevResponse" styleClass="width120"><bean:message key="button.back"/></ctrl:ubutton>

