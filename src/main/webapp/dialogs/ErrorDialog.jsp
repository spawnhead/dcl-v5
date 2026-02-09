<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<table id=msgTable cellSpacing=0 cellPadding=2 width='100%' height='100%' border=0>
	<tr id=trMsg>
		<td valign=top>
			<div id=errMSGS width='100%'></div>
		</td>
	</tr>
	<tr id=trBtn align=right valign=bottom>
		<td align=right valign=bottom>
			<input id=moreButton type=button value='<bean:message key="button.showDetails"/>' onclick='more();'
			       class=width120>&nbsp;
			<input type=button value='<bean:message key="button.ok"/>' onclick='window.close();' class=width120>
		</td>
	</tr>
	<tr id=trErr style='display:none;'>
		<td width='100%'><textarea name=errDetail class=error-details wrap=off readonly></textarea></td>
	</tr>
</table>
<script LANGUAGE="JScript" type="text/javascript">
	var errDetail = document.getElementById('errDetail');
	var trMsgHeight = 0;
	var trBtnHeight = 0;
	var dlgCaption = 0;
	var tblPadding = 0;
	var inited = false;
	var errorsArr;

	function addErrMsgs()
	{
		for (i = 0; i < errorsArr.length; i++)
		{
			var o = errorsArr[i];
			var str = "";
			if (o.name)
			{
				str += o.name + ":";
			}
			str += o.msg;
			if (o.details)
			{
				str += ", " + o.details;
			}
			errMSGS.insertAdjacentHTML("beforeEnd", "<div id='msg" + i + "' class=error-msg onclick='showError(" + i + ");'>" + str + "</div>");
		}
	}

	function showError(idx)
	{
		errDetail.value = errorsArr[idx].exception;
	}

	function setDialogHeight()
	{
		var dlgCaption = parseInt(dialogHeight) - document.body.offsetHeight;
		sum = trMsgHeight + trBtnHeight + trErr.offsetHeight + dlgCaption + tblPadding;
		dialogHeight = sum + "px";
	}

	function more()
	{
		if (trErr.style.display == '')
		{
			trErr.style.display = 'none';
			moreButton.value = '<bean:message key="button.showDetails"/>';
		}
		else
		{
			trErr.style.display = '';
			moreButton.value = '<bean:message key="button.hideDetails"/>';
		}
		trMsg.height = trMsgHeight;
		trBtn.height = trBtnHeight;

		errDetail.style.width = errMSGS.offsetWidth;
		setDialogHeight();
	}

	function stateChanged()
	{
		if (document.readyState != "complete")
		{
			return;
		}
		if (inited == false)
		{
			errorsArr = dialogArguments;
			addErrMsgs();
			if (errorsArr.length == 1 && !errorsArr[0].exception)
			{
				moreButton.style.display = 'none';
			}
			dlgCaption = parseInt(dialogHeight) - document.body.offsetHeight;
			tblPadding = outerTable.offsetWidth - msgTable.offsetWidth;
			trMsgHeight = trMsg.offsetHeight;
			trBtnHeight = trBtn.offsetHeight;

			var dif = 100 - dlgCaption - trMsgHeight - trBtnHeight;
			if (dif >= 0)
			{
				trMsgHeight += dif;
				trMsg.height = trMsgHeight;
				trBtnHeight = trBtn.offsetHeight;
			}
			inited = true;
		}

		setDialogHeight();
		showError(0);
	}
	document.onreadystatechange = stateChanged;
</script>





