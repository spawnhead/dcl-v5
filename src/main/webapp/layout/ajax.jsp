<%@ page import="net.sam.dcl.App" %>
<%@ page import="net.sam.dcl.util.StrutsUtil" %>
<%@ page import="org.apache.struts.action.ActionMessage" %>
<%@ page import="net.sam.dcl.message.EMessage" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="org.apache.struts.Globals" %>
<%@ page import="org.apache.struts.taglib.TagUtils" %>
<%@ page import="org.apache.struts.action.ActionMessages" %>
<%@ page import="net.sam.dcl.util.ConstDefinitions" %>
<%@ page import="org.apache.struts.taglib.html.Constants" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<!----------------body----------------->
<%
//	response.sendError(500);
//	if (true) return;
	try {
%>
<tiles:importAttribute name="body" ignore="true" scope="request"/>
<logic:present name="body">
	<ctrl:insertWithExc attribute="body"/>
</logic:present>
<%=StrutsUtil.getAjaxResponse(request)%>
<%
	}catch(Throwable e){
 		StrutsUtil.addError(request, new EMessage("errors.common", e.getMessage(),e));
	}
	ActionMessages errors = null;
	try {
		errors = TagUtils.getInstance().getActionMessages(pageContext, Globals.ERROR_KEY);
	} catch (JspException e) {
		TagUtils.getInstance().saveException(pageContext, e);
		throw e;
	}


%>
<script language="JScript" type="text/javascript">
(function (){
	var requestId = <%=request.getParameter(ConstDefinitions.AJAX_REQUEST_ID)%>;
<%
	Iterator iterator = errors.get();
	while (iterator.hasNext()) {
		ActionMessage error = (ActionMessage) iterator.next();
		String msg =
				TagUtils.getInstance().message(
						pageContext,
						null,
						Globals.LOCALE_KEY,
						error.getKey(),
						error.getValues());
		Throwable exc = null;
		if (error instanceof EMessage) {
			exc = ((EMessage) error).getException();
		}
		out.print(StrutsUtil.getError4JavaScript(msg, exc,"getSharedData(requestId).pageErrorsArr"));
	}

%>
	//debugger;
	if (getSharedData(requestId).pageErrorsArr.length!=0){
		showCustomErrors(getSharedData(requestId).pageErrorsArr);
	}

//	document.all('treeGrid').innerHTML=_wTH.tree.toString();

	var callback = getSharedData(requestId).callback;
	if (callback) callback(getSharedData(requestId).transport,getSharedData(requestId).pageErrorsArr.length!=0);

	var okCallback = getSharedData(requestId).okCallback;
	if (okCallback && getSharedData(requestId).pageErrorsArr.length==0) okCallback(getSharedData(requestId).transport);
	})();
</script>