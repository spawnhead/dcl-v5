<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>


Administrator!!!<br>
Doesn't forget that everything you are doing, it is at your own risk <br><br>
<ctrl:askUser name="fixAttach" key="fixAttach.info" />

<%--<ctrl:form>--%>

<bean:message key="adm_zone.start-shutdown-text"/>
  <ctrl:ubutton type="link" textId="adm_zone.start-shutdown-button" styleClass="width165" action="/PrepareAppToShutdown"></ctrl:ubutton></br>
<bean:message key="adm_zone.start-fix-attach"/>
	<ctrl:ubutton type="link" textId="adm_zone.fix-attachments" styleClass="width165" action="/FixAttachments.do" askUser="fixAttach"></ctrl:ubutton>
<%--</ctrl:form>--%>