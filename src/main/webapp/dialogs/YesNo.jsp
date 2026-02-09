<%@ page import="net.sam.dcl.util.StrutsUtil" %>
<%@ page import="net.sam.dcl.util.StringUtil" %>
<%@ page import="java.net.URLEncoder" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>


<table cellSpacing=0 cellPadding=0 width='100%' height='100%' border=0>
	<tr height='100%'>
		<td height='100%' valign=top>
			<script type="text/javascript" language="JavaScript">
				document.write(dialogArguments);
			</script>
		</td>
	</tr>
	<tr>
		<logic:empty name="YesNo" property="showOkCancel">
			<td align=right>
				<input type=button id="yesButton" value='<bean:message key="button.yes" />' onClick='onYes();' class=width80>&nbsp;
				<input type=button id="noButton" value='<bean:message key="button.no" />' onClick='onNo(); 'tabindex='${param.isDefaultCancel}' class=width80>
			</td>
		</logic:empty>
		<logic:notEmpty name="YesNo" property="showOkCancel">
			<td align=right>
				<input type=button id="yesButton" value='<bean:message key="button.ok" />' onClick='onYes();' class=width80>&nbsp;
				<input type=button id="noButton" value='<bean:message key="button.cancel" />' onClick='onNo();' tabindex='${param.isDefaultCancel}' class=width80>
			</td>
		</logic:notEmpty>
	</tr>
</table>
<logic:notEqual name="YesNo" property="noSeconds" value="0">
	<input type=hidden id="timerIdNo" value=${param.noSeconds}>
</logic:notEqual>
<logic:notEqual name="YesNo" property="yesSeconds" value="0">
	<input type=hidden id="timerIdYes" value=${param.yesSeconds}>
</logic:notEqual>

<script type="text/javascript" LANGUAGE="JScript">
	returnValue = null;

	function onYes()
	{
		returnValue = true;
		window.close();
	}

	function onNo(from)
	{
		returnValue = false;
		window.close();
	}

	function noTimer()
	{
		var obj = document.getElementById('timerIdNo');
		obj.value--;
		if (obj.value == 0)
		{
			document.getElementById('noButton').disabled = false;
			document.getElementById('noButton').value = getNoText();
		}
		else
		{
			document.getElementById('noButton').value = getNoText() + ' (' + obj.value + ')';
			setTimeout(noTimer, 1000);
		}
	}

	function yesTimer()
	{
		var obj = document.getElementById('timerIdYes');
		obj.value--;
		if (obj.value == 0)
		{
			document.getElementById('yesButton').disabled = false;
			document.getElementById('yesButton').value = getYesText();
		}
		else
		{
			document.getElementById('yesButton').value = getYesText() + ' (' + obj.value + ')';
			setTimeout(yesTimer, 1000);
		}
	}

	function getNoText()
	{
		<logic:empty name="YesNo" property="showOkCancel">
			return '<bean:message key="button.no" />';
		</logic:empty>
		<logic:notEmpty name="YesNo" property="showOkCancel">
			return '<bean:message key="button.cancel" />';
		</logic:notEmpty>
	}

	function getYesText()
	{
		<logic:empty name="YesNo" property="showOkCancel">
			return '<bean:message key="button.yes" />';
		</logic:empty>
		<logic:notEmpty name="YesNo" property="showOkCancel">
			return '<bean:message key="button.ok" />';
		</logic:notEmpty>
	}

<logic:notEqual name="YesNo" property="noSeconds" value="0">
	returnValue = true;
	document.getElementById('noButton').disabled = true;
	setTimeout(noTimer, ${param.noSeconds});
</logic:notEqual>

<logic:notEqual name="YesNo" property="yesSeconds" value="0">
	returnValue = false;
	document.getElementById('yesButton').disabled = true;
	setTimeout(yesTimer, ${param.yesSeconds});
</logic:notEqual>
</script>





