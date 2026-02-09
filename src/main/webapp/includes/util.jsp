<%@ page import="net.sam.dcl.config.Config" %>
<%@ page import="net.sam.dcl.util.ConstDefinitions" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>

<div id="glassFrame" style="position:absolute;left:0;top:0;width:expression(document.body.clientWidth);height:expression(document.body.clientHeight);z-index:1000;background-color:gray;opacity:0.2;filter:alpha(opacity=20);display:none;">
</div>

<script language="JScript" type="text/javascript">

var isIE = window.navigator.appName == "Microsoft Internet Explorer";

var oPopup = null;
var oPopupBody = null;
var __dclPreloaderTimer = null;
var __dclPreloaderBumpTimer = null;

function __dclPreloaderShow(text, percent)
{
	var root = document.getElementById('dcl-preloader');
	if (!root)
	{
		return;
	}
	var txt = document.getElementById('dcl-preloader-text');
	if (txt && text != null)
	{
		txt.innerHTML = String(text).replace(/</g, '&lt;').replace(/>/g, '&gt;');
	}
	if (percent != null)
	{
		__dclPreloaderSetPercent(percent);
	}
	root.style.display = 'block';
}

function __dclPreloaderSetPercent(value)
{
	var pct = parseInt(value, 10);
	if (isNaN(pct))
	{
		return;
	}
	if (pct < 0) pct = 0;
	if (pct > 100) pct = 100;
	var bar = document.getElementById('dcl-preloader-bar-fill');
	if (bar)
	{
		bar.style.width = pct + '%';
	}
	var label = document.getElementById('dcl-preloader-percent');
	if (label)
	{
		label.innerHTML = pct + '%';
	}
}

function __dclPreloaderHide()
{
	var root = document.getElementById('dcl-preloader');
	if (root)
	{
		root.style.display = 'none';
	}
	if (__dclPreloaderBumpTimer)
	{
		window.clearInterval(__dclPreloaderBumpTimer);
		__dclPreloaderBumpTimer = null;
	}
}

function __dclStartPagePreloader()
{
	if (!document.getElementById('dcl-preloader'))
	{
		return;
	}
	__dclPreloaderShow('Загрузка страницы...', 5);
	var last = 5;
	var update = function ()
	{
		var state = document.readyState;
		var pct = last;
		if (state == 'loading')
		{
			pct = Math.max(pct, 20);
		}
		else if (state == 'interactive')
		{
			pct = Math.max(pct, 60);
		}
		else if (state == 'complete')
		{
			pct = 100;
		}
		__dclPreloaderSetPercent(pct);
		last = pct;
		if (state == 'complete')
		{
			window.setTimeout(__dclPreloaderHide, 250);
			if (__dclPreloaderTimer)
			{
				window.clearInterval(__dclPreloaderTimer);
				__dclPreloaderTimer = null;
			}
		}
	};
	update();
	__dclPreloaderTimer = window.setInterval(update, 120);
}

function __dclPreloaderStartIndeterminate(text)
{
	__dclPreloaderShow(text, 30);
	if (__dclPreloaderBumpTimer)
	{
		window.clearInterval(__dclPreloaderBumpTimer);
	}
	var pct = 30;
	__dclPreloaderBumpTimer = window.setInterval(function ()
	{
		var root = document.getElementById('dcl-preloader');
		if (!root || root.style.display == 'none')
		{
			window.clearInterval(__dclPreloaderBumpTimer);
			__dclPreloaderBumpTimer = null;
			return;
		}
		if (pct < 90)
		{
			pct += 3;
			if (pct > 90) pct = 90;
			__dclPreloaderSetPercent(pct);
		}
	}, 400);
}

if (typeof ErrorObj !== 'function')
{
	function ErrorObj(name, msg, details, exception)
	{
		this.name = name;
		this.msg = msg;
		this.details = details;
		this.exception = exception;
	}
}

if (typeof showCustomError !== 'function')
{
	function showCustomError(name, msg, details, exception)
	{
		if (window.console && exception)
		{
			console.error(exception);
		}
		if (msg)
		{
			if (typeof window.__dclShowErrorInBanner === 'function') {
				window.__dclShowErrorInBanner((name ? name + ': ' : '') + msg + (details ? ' (' + details + ')' : ''));
				return;
			}
			alert(msg);
		}
	}
}

if (typeof showCustomErrors !== 'function')
{
	function showCustomErrors(customErrArr)
	{
		if (!customErrArr || customErrArr.length === 0)
		{
			return;
		}
		showCustomError(null, customErrArr.join("\n"));
	}
}

function pause(iMilliseconds)
{
	var sDialogScript = 'window.setTimeout( function () { window.close(); }, ' + iMilliseconds + ');';
	window.showModalDialog('javascript:document.writeln("<script>' + sDialogScript + '<' + '/script>")');
}

function runProgress()
{
	if (null == oPopup)
	{
		__dclPreloaderStartIndeterminate('Идёт загрузка данных...');
		var backgroundWindow = document.getElementById('glassFrame');
		if (backgroundWindow)
		{
			backgroundWindow.style.top = (document.documentElement.scrollTop || document.body.scrollTop || 0) + "px";
			backgroundWindow.style.left = (document.documentElement.scrollLeft || document.body.scrollLeft || 0) + "px";
			backgroundWindow.style.display = 'block';
		}

		// В современных браузерах window.createPopup отсутствует
		if (typeof window.createPopup === 'function')
		{
			oPopup = window.createPopup();
			oPopupBody = oPopup.document.body;
			oPopupBody.style.backgroundColor = "ThreeDLightShadow";
			oPopupBody.style.padding = "7px";
			oPopupBody.style.border = "1px solid";
			oPopupBody.style.borderColor = "ThreeDHighlight ThreeDDarkShadow ThreeDDarkShadow ThreeDHighlight";
			oPopupBody.innerHTML = "<div style='padding-left:38px;'><img src='<ctrl:rewrite page='/images/icons/32/wait.png'/>' alt='Пожалуйста, подождите...' style='position:absolute; left:7px; top:10px; behavior:url(<ctrl:rewrite page='/includes/pngbehavior.htc'/>); width:32px; height:32px;'/><p style='margin:4px 0; font:bold 11px/150% Tahoma, sans-serif;'>Пожалуйста, подождите.</p><p style='margin:4px 0; font:normal 11px Tahoma, sans-serif;'>Идёт загрузка данных...<span style='position:relative; top: 6px; float:left; margin:4px 0; border:1px solid; border-style:inset; background-image:url(<ctrl:rewrite page='/images/progress.gif'/>); width:275px; height:18px;'></span></p></div>";
			oPopup.show(document.body.clientWidth / 2 - 145, document.body.clientHeight / 2 - 40, 290, 75, document.body);
		}

		setCursor("wait");
	}
}

function stopProgress()
{
	__dclPreloaderHide();
	restoreCursor();
	if (null != oPopup)
	{
		oPopup.hide();
		oPopup = null;
		//oPopupBody = null;
	}
	var backgroundWindow = document.getElementById('glassFrame');
	if (backgroundWindow)
	{
		backgroundWindow.style.display = 'none';
	}
}

window.name = '_default';
document.onkeydown = disableEnter;
function disableEnter(e)
{
	var evt = e || window.event;
	if (!evt) return true;
	var target = evt.target || evt.srcElement;
	if (target && target.tagName == 'INPUT' && evt.keyCode == 13)
	{
		if (evt.preventDefault) evt.preventDefault();
		evt.returnValue = false;
		return false;
	}
	return true;
}

function __rowOnMouseOver(obj)
{
	obj.__SavedCursor = obj.style.cursor;
	obj.style.cursor = 'pointer';
	obj.__SavedBackgroundColor = obj.style.backgroundColor;
	obj.style.backgroundColor = '#dddddd'
}

function __rowOnMouseOut(obj)
{
	obj.style.backgroundColor = obj.__SavedBackgroundColor;
	obj.style.cursor = obj.__SavedCursor;
}

function __rowOnMouseOverHighlight(obj)
{
	obj.__SavedBackgroundColor = obj.style.backgroundColor;
	obj.style.backgroundColor = '#dddddd'
}

function __rowOnMouseOutHighlight(obj)
{
	obj.style.backgroundColor = obj.__SavedBackgroundColor;
}

function getForm(element)
{
	while (element != null && element.tagName != "FORM")
	{
		element = element.parentNode;
	}
	if (element == null)
	{
		alert('getForm can not find [Form]');
	}
	if (element.elements.namedItem('action') != null)
	{
		alert('You use field [action] in form:' + element.namedItem('action').value);
	}

	return element;
}

function disableTableCtrls(name)
{
	var prev = document.getElementById(name + '.prev');
	if (prev)
	{
		prev.disabled = true;
	}
	var next = document.getElementById(name + '.next');
	if (next)
	{
		next.disabled = true;
	}
}

function disableCtrlsDef()
{
	disableTableCtrls('grid');
}

function disableFormControls(form, disable)
{
	for (var i = 0; i < form.elements.length; i++)
	{
		var elem = form.elements[i];
		if (elem.type == 'button' || elem.type == 'submit' || elem.type == 'reset')
		{
			elem.disabled = disable;
		}
	}
}

function enableFormCheckboxes(form)
{
	for (var i = 0; i < form.elements.length; i++)
	{
		var elem = form.elements[i];
		if (elem.type == 'checkbox')
		{
			elem.disabled = false;
		}
	}
}

function submitDispatchForm(dispatch)
{
	runProgress();

	if (lockForm()) return;
	var form = document.forms[0] || document.forms.item && document.forms.item(0);
	if (!form)
	{
		return;
	}
	form.action += '?dispatch=' + dispatch;
	form.submit();
}

function isUserAgree(msg, showOkCancel, w, h, isDefaultCancel, noSeconds, yesSeconds)
{
	if (!w)
	{
		w = 500;
	}
	if (!h)
	{
		h = 100;
	}
	if (showOkCancel)
	{
		showOkCancel = "1";
	}
	else
	{
		showOkCancel = "";
	}
	if (isDefaultCancel)
	{
		isDefaultCancel = "1";
	}
	else
	{
		isDefaultCancel = "";
	}
	if (!noSeconds)
	{
		noSeconds = 0;
	}
	if (!yesSeconds)
	{
		yesSeconds = 0;
	}

	if (isIE)
	{
		return showModalDialog("<ctrl:rewrite action='/YesNo'/>?showOkCancel=" + showOkCancel + "&isDefaultCancel=" + isDefaultCancel + "&noSeconds=" + noSeconds + "&yesSeconds=" + yesSeconds, msg, "dialogWidth:" + w + "px;dialogHeight:" + h + "px;help:0;status:0;scroll:0");
	}
	else
	{
		return window.confirm(msg);
	}
}

var inProcess = {};

function recalcTableColumnWidth2(headerTableName, bodyTableName, recurs)
{
	if (recurs == null)
	{
		recurs = 0;
	}

	var headerTable = document.getElementById(headerTableName);
	var bodyTable = document.getElementById(bodyTableName);
	var headerTr = headerTable.rows(headerTable.rows.length - 1);
	var bodyTr = null;
	var idx = 0;
	for (idx = 0; idx < bodyTable.rows.length; idx++)
	{
		if (bodyTable.isGroupBy == 1)
		{
			if (bodyTable.rows(idx).expandgroup == undefined &&
							(bodyTable.rows(idx).style.display == 'block' ||
											bodyTable.rows(idx).style.display == ''))
			{
				bodyTr = bodyTable.rows(idx);
				break;
			}
		}
		else
		{
			if (bodyTable.rows(idx).style.display != 'none')
			{
				bodyTr = bodyTable.rows(idx);
				break;
			}
		}
	}
	if (bodyTr == null)
	{
		return;
	}

	var ofs = 4;
	var scroll = 16;
	var tmp;
	for (idx = 0; idx < headerTr.cells.length; idx++)
	{
		tmp = 12;

		if (idx != headerTr.cells.length - 1)
		{
			tmp = -ofs;
		}
		if (headerTr.cells(idx).offsetWidth - ofs - (bodyTr.cells(idx).offsetWidth + tmp) != 0)
		{
			if (bodyTr.cells(idx).offsetWidth + tmp > 0)
			{
				headerTr.cells(idx).width = bodyTr.cells(idx).offsetWidth + tmp;
				if (headerTr.cells(idx).offsetWidth - ofs - (bodyTr.cells(idx).offsetWidth + tmp) > 1)
				{
					bodyTr.cells(idx).width = headerTr.cells(idx).offsetWidth + tmp;
					if (headerTr.cells(idx).offsetWidth - ofs - (bodyTr.cells(idx).offsetWidth + tmp) != 0)
					{
						//headerTr.cells(idx).width = bodyTr.cells(idx).offsetWidth + tmp;
					}
					//alert(idx);
				}
			}
		}
	}

	var hasBad = false;
	for (idx = 0; idx < headerTr.cells.length; idx++)
	{
		tmp = 12;
		if (idx != headerTr.cells.length - 1)
		{
			tmp = -ofs;
		}
		if (headerTr.cells(idx).offsetWidth - ofs - (bodyTr.cells(idx).offsetWidth + tmp) != 0)
		{
			hasBad = true;
		}
	}
	if (recurs < 4 && hasBad)
	{
		recalcTableColumnWidth2(headerTableName, bodyTableName, recurs + 1);
	}
	else
	{
		inProcess.headerTableName = false;
	}
}

function decreaseWidth(idx, ofs, recurs, bodyTr, headerTr)
{
	var bs = bodyTr.cells(idx).offsetWidth;
	var hs = headerTr.cells(idx).offsetWidth;
	headerTr.cells(idx).width = hs - 1 - ofs;
	bodyTr.cells(idx).width = bs - 1 - ofs;
	if (headerTr.cells(idx).offsetWidth != hs - 1 || bodyTr.cells(idx).offsetWidth != bs - 1)
	{
		headerTr.cells(idx).width = hs - ofs;
		bodyTr.cells(idx).width = bs - ofs;
	}
}

var __formloc = false;
function lockForm()
{
	if (__formloc) return true;
	__formloc = true;
	return false;
}

function unlockForm()
{
	__formloc = false;
}

function expandGrid(obj, name)
{
	var tr = obj.parentElement.parentElement;
	var closedInp = getForm(obj).all(name);
	var group = tr.expandgroup;
	var curImage = obj.src;
	var isClosed = closedInp.value == '1';
	if (isClosed)
	{
		obj.src = '<%=response.encodeURL(Config.getString("row-tag.opened-image"))%>';
		closedInp.value = '0';
	}
	else
	{
		obj.src = '<%=response.encodeURL(Config.getString("row-tag.closed-image"))%>';
		closedInp.value = '1';
	}
	var next = tr;
	while ((next = next.nextSibling) != null)
	{
		if (next.expandgroup != undefined)
		{
			break;
		}
		if (isClosed)
		{
			next.style.display = 'block';
		}
		else
		{
			next.style.display = 'none';
		}
	}
}

var __increment_counter = 0;
function getNextValue()
{
	return ++__increment_counter;
}

function getCurrValue()
{
	return __increment_counter;
}

var sharedData = {};
function initSharedData()
{
	var cnt = getNextValue();
	sharedData['ses' + cnt] = {};
	return cnt;
}
function getSharedData(ses)
{
	return sharedData['ses' + ses];
}

var ajaxRequests = {};
var $skipRequest = {};
function doAjaxAsync(obj)
{
	$("WaitPlace").update("<img src='<%= request.getContextPath() %>/images/wait.gif' >");
	setTimeout(doAjaxImpl.bind(doAjaxImpl, obj), 10000);
}

function doAjax(obj)
{
	//debugger;
	var logger = CERNY.Logger("DCL");
	var params = $H({});
	var doAsynchronous = true;
	if (obj.synchronous) doAsynchronous = !obj.synchronous;
	if (obj.form) params.merge($H($(obj.form).serialize(true)));
	if (obj.params) params.merge($H(obj.params));
	var requestId = initSharedData();
	ajaxRequests[obj.url] = requestId;
	params['<%=ConstDefinitions.AJAX_REQUEST_ID%>'] = requestId;
	getSharedData(requestId).pageErrorsArr = [];
	if (obj.callback) getSharedData(requestId).callback = obj.callback;
	if (obj.okCallback) getSharedData(requestId).okCallback = obj.okCallback;
	if (obj.showWaitCursor)
	{
		setCursor("wait");
	}
	else
	{
		$("WaitPlace").update("<img src='<%= request.getContextPath() %>/images/wait.gif' >");
	}

	new Ajax.Request(obj.url,
					{
						asynchronous: doAsynchronous,
						parameters: params,
						onLoading: function ()
						{
							if (ajaxRequests[obj.url] != requestId) throw $skipRequest;
						},
						onLoaded: function ()
						{
							if (ajaxRequests[obj.url] != requestId) throw $skipRequest;
						},
						onInteractive: function ()
						{
							if (ajaxRequests[obj.url] != requestId) throw $skipRequest;
						},
						onSuccess: function (transport)
						{
							if (ajaxRequests[obj.url] != requestId) throw $skipRequest;

							var pageErrorsArr = [];
							var respText = transport.responseText;
							var evalScriptsSaved = respText.evalScripts;
							if (obj.update)
							{
								var trimmed = (respText && respText.trim) ? respText.trim() : respText;
								if (trimmed && (trimmed.match(/^\s*<!DOCTYPE/i) || trimmed.match(/^\s*<html[\s>]/i)))
								{
									if (typeof showCustomError === 'function')
										showCustomError(null, 'AJAX: получен полный HTML вместо фрагмента. Проверьте серверный ответ.', null, null);
									$("WaitPlace").update("");
									return;
								}
								$(obj.update).update(respText.stripScripts());
							}
							if (obj.showWaitCursor)
							{
								restoreCursor();
							}
							if (!obj.skipScripts)
							{
								getSharedData(requestId).transport = transport;
								respText.evalScripts();
							}
							$("WaitPlace").update("");
						},
						onFailure: function (e)
						{
							//debugger;
							var obj = {};
							obj.toJSONString = function ()
							{
								return Object.toJSON(this);
							};
							obj.status = e.status;
							obj.statusText = e.statusText;
							obj.responseText = e.responseText.truncate(1000);
							var pr = new CERNY.json.TextPrettyPrinter();
							var r = pr.print(obj);
							showCustomError(null, 'Something went wrong...', null, r);
							restoreCursorAndContent(obj);
						},
						onException: function (obj, e)
						{
							//debugger;
							if (e == $skipRequest)
							{
								logger.debug("skip:" + obj.url + ":" + ajaxRequests[obj.url] + ":" + requestId);
								return;
							}
							obj.toJSONString = function ()
							{
								return Object.toJSON(this);
							};
							obj.resultText = obj.transport.responseText.truncate(1000);
							var pr = new CERNY.json.TextPrettyPrinter();
							var r = pr.print(obj);
							showCustomError(null, e.name + " : " + e.message, null, r);
							restoreCursorAndContent(obj);
						}
					});
}

function restoreCursorAndContent(obj)
{
	if (obj.showWaitCursor)
	{
		restoreCursor();
	}
	else
	{
		$("WaitPlace").update("");
	}
}

function setCursor(cur)
{
	document.body.origCursor = document.body.style.cursor;
	document.body.style.cursor = cur;
	var inpObjs = document.getElementsByTagName('INPUT');
	for (var i = 0; i < inpObjs.length; i++)
	{
		inpObjs[i].origCursor = inpObjs[i].style.cursor;
		inpObjs[i].style.cursor = 'wait';
	}
}

function restoreCursor()
{
	document.body.style.cursor = document.body.origCursor;
	var inpObjs = document.getElementsByTagName('INPUT');
	for (var i = 0; i < inpObjs.length; i++)
	{
		inpObjs[i].style.cursor = inpObjs[i].origCursor;
	}
}

var initFunctions = [];

function callInitFunctions()
{
	var idx = 0;
	for (; idx < initFunctions.length; idx++)
	{
		initFunctions[idx].call();
	}
	initFunctions = [];
}

function JSONtoDBGText(json)
{
	json = json.replace(/,/g, '\n');
	json = json.replace(/{/g, '{\n');
	json = json.replace(/}/g, '\n}');
	return json;
}

function pageWidth()
{
	return window.innerWidth != null ? window.innerWidth : document.documentElement && document.documentElement.clientWidth ? document.documentElement.clientWidth : document.body != null ? document.body.clientWidth : null;
}

function posLeft()
{
	return typeof window.pageXOffset != 'undefined' ? window.pageXOffset : document.documentElement && document.documentElement.scrollLeft ? document.documentElement.scrollLeft : document.body.scrollLeft ? document.body.scrollLeft : 0;
}

var showPanel = false;

function showFilterPanel()
{
	var obj = $('filter-form');
	if (obj != null)
	{
		obj.style.display = 'block';
		//debugger;

		var scrWidth = (window.document.body.offsetWidth - 20);
		if (obj.offsetWidth > scrWidth)
		{
			obj.style.width = scrWidth + "px";
			obj.style.height = (obj.offsetHeight + 20) + "px";
		}
		var savedWidth = obj.offsetWidth;
		obj.style.left = (posLeft() + (pageWidth() - obj.offsetWidth) / 2) + "px";
		obj.style.width = savedWidth + "px";
		showPanel = true;
		addDocEvent('mousemove', checkMotion);
	}
}

function checkMotion(e)
{
	var evt = getEventObj(e);
	if (!evt)
	{
		return;
	}
	var obj = $('filter-form');
	if (obj == null)
	{
		removeDocEvent('mousemove', checkMotion);
		return;
	}

	if (evt.clientX < obj.offsetLeft ||
					evt.clientY < obj.offsetTop ||
					evt.clientX > obj.offsetLeft + obj.offsetWidth ||
					evt.clientY > obj.offsetTop + obj.offsetHeight)
	{
		if (showPanel)
		{
			showPanel = false;
			window.setTimeout(hideFilterPanel, 500);
		}
	}
	else
	{
		showPanel = true;
	}
}

function hideFilterPanel()
{

	if (!showPanel)
	{
		var obj = $('filter-form');
		if (obj != null)
		{
			removeDocEvent('mousemove', checkMotion);
			obj.style.display = 'none';
		}
	}
}

function showControlComment(obj, dontCheckSrcElement, e)
{
	var target = getEventTarget(e);
	if (!dontCheckSrcElement && target && obj != target)
	{
		return;
	}
	var cmt = obj.__comment;
	if (cmt != null && cmt.length > 0)
	{
		while (cmt.indexOf('\r\n') != -1)
		{
			cmt = cmt.replace('\r\n', '<br>');
		}
	}
	Tip(cmt, FIX, null, LEFT, true, JUMPHORZ, true, WIDTH, 350, PADDING, 2, STICKY, true, FOLLOWMOUSE, false, CLICKCLOSE, true, CLOSEBTN, false, EXCLUSIVE, true, BGCOLOR, "#eeeeee", BORDERCOLOR, "#323232", DELAY, 0);
}

var __control_comment_key = null;
var __control_comment_save = null;
var __control_comment_obj = null;

function editControlComment(obj, key, save)
{
	var target = getEventTarget();
	if (target && obj != target)
	{
		return;
	}
	__control_comment_key = key;
	__control_comment_save = save;
	__control_comment_obj = obj;
	tt_HideInit();
	getTemplateControlComment().value = obj.__comment;
	Tip($("__control-comment-form").innerHTML, LEFT, true, WIDTH, 350, PADDING, 0, STICKY, true, CLICKCLOSE, false, CLOSEBTN, true, EXCLUSIVE, true, BGCOLOR, "#eeeeee", BORDERCOLOR, "#323232", TITLEBGCOLOR, "#323232", CLOSEBTNCOLORS, ["#323232", "#ffffff", "#323232", "#ffffff"], DELAY, 0);
}

function __saveControlComment()
{
	var saveUrl = '<ctrl:rewrite action="/ControlCommentAction" dispatch="saveControlComment"/>';
	var insertUrl = '<ctrl:rewrite action="/ControlCommentAction" dispatch="insertControlComment"/>';
	var dialogControlComment = getDialogControlComment();
	if (!dialogControlComment)
	{
		return;
	}
	doAjax({
		url: __control_comment_save ? saveUrl : insertUrl,
		params: {'controlComment.fcm_key': __control_comment_key, 'controlComment.fcm_value': dialogControlComment.value},
		synchronous: true,
		okCallback: function ()
		{
			__control_comment_obj.__comment = dialogControlComment.value;
			tt_HideInit();
		}
	});
}

function getTemplateControlComment()
{
	var el = $('__control-comment-form');
	var ctrls = el ? el.querySelectorAll('#__control-comment') : [];
	if (ctrls.length > 1)
	{
		//debugger
		if (ctrls[0].parentNode.parentNode.id == '__control-comment-form')
		{
			return ctrls[0];
		}
		else
		{
			return ctrls[1];
		}
	}
	else
	{
		return ctrls;
	}
}

function getDialogControlComment()
{
	var ctrls = document.querySelectorAll('#__control-comment');
	if (ctrls.length > 1)
	{
		if (ctrls[0].parentNode.parentNode.id == '__control-comment-form')
		{
			return ctrls[1];
		}
		else
		{
			return ctrls[0];
		}
	}
	else
	{
		return ctrls.length ? ctrls[0] : null;
	}
}

var horizontal_offset = "9px"; //horizontal offset of hint box from anchor link

/////No further editting needed

var vertical_offset = "0"; //horizontal offset of hint box from anchor link. No need to change.
var ie = !!document.documentMode;
var ns6 = !ie;

function getEventObj(e)
{
	return e || window.event || null;
}

function getEventTarget(e)
{
	var evt = getEventObj(e);
	return evt ? (evt.target || evt.srcElement) : null;
}

function addDocEvent(name, handler)
{
	if (document.addEventListener)
	{
		document.addEventListener(name, handler, false);
	}
	else if (document.attachEvent)
	{
		document.attachEvent('on' + name, handler);
	}
}

function removeDocEvent(name, handler)
{
	if (document.removeEventListener)
	{
		document.removeEventListener(name, handler, false);
	}
	else if (document.detachEvent)
	{
		document.detachEvent('on' + name, handler);
	}
}

function getposOffset(what, offsettype)
{
	var totaloffset = (offsettype == "left") ? what.offsetLeft : what.offsetTop;
	var parentEl = what.offsetParent;
	while (parentEl != null)
	{
		totaloffset = (offsettype == "left") ? totaloffset + parentEl.offsetLeft : totaloffset + parentEl.offsetTop;
		parentEl = parentEl.offsetParent;
	}
	return totaloffset;
}

function iecompattest()
{
	return (document.compatMode && document.compatMode != "BackCompat") ? document.documentElement : document.body;
}

function clearbrowseredge(obj, whichedge)
{
	var edgeoffset = (whichedge == "rightedge") ? parseInt(horizontal_offset) * -1 : parseInt(vertical_offset) * -1;
	var windowedge;
	if (whichedge == "rightedge")
	{
		windowedge = ie && !window.opera ? iecompattest().scrollLeft + iecompattest().clientWidth - 30 : window.pageXOffset + window.innerWidth - 40;
		dropmenuobj.contentmeasure = dropmenuobj.offsetWidth;
		if (windowedge - dropmenuobj.x < dropmenuobj.contentmeasure)
		{
			edgeoffset = dropmenuobj.contentmeasure + obj.offsetWidth + parseInt(horizontal_offset);
		}
	}
	else
	{
		windowedge = ie && !window.opera ? iecompattest().scrollTop + iecompattest().clientHeight - 15 : window.pageYOffset + window.innerHeight - 18;
		dropmenuobj.contentmeasure = dropmenuobj.offsetHeight;
		if (windowedge - dropmenuobj.y < dropmenuobj.contentmeasure)
		{
			edgeoffset = dropmenuobj.contentmeasure - obj.offsetHeight;
		}
	}
	return edgeoffset;
}

function showHint(menucontents, obj, e, tipwidth)
{
	if ((ie || ns6) && document.getElementById("hintbox"))
	{
		dropmenuobj = document.getElementById("hintbox");
		dropmenuobj.innerHTML = menucontents;
		dropmenuobj.style.left = dropmenuobj.style.top = -500;
		if (tipwidth != "")
		{
			dropmenuobj.widthobj = dropmenuobj.style;
			dropmenuobj.widthobj.width = tipwidth;
		}
		dropmenuobj.x = getposOffset(obj, "left");
		dropmenuobj.y = getposOffset(obj, "top");
		dropmenuobj.style.left = dropmenuobj.x; //- clearbrowseredge(obj, "rightedge") + obj.offsetWidth + "px";
		dropmenuobj.style.top = dropmenuobj.y + 25; //- clearbrowseredge(obj, "bottomedge") + "px";
		dropmenuobj.style.visibility = "visible";
		obj.onmouseout = hideTip;
	}
}

function hideTip()
{
	dropmenuobj.style.visibility = "hidden";
	dropmenuobj.style.left = "-500px";
}

function createHintBox()
{
	var divblock = document.createElement("div");
	divblock.setAttribute("id", "hintbox");
	document.body.appendChild(divblock);
}

function checkEnterPress(key, dispatch)
{
	if (13 == key)
	{
		submitDispatchForm(dispatch);
	}
}

function setCookie(c_name, value)
{
	document.cookie = c_name + "=" + escape(value);
}

function getCookie(c_name)
{
	if (document.cookie.length > 0)
	{
		var c_start = document.cookie.indexOf(c_name + "=");
		if (c_start != -1)
		{
			c_start = c_start + c_name.length + 1;
			var c_end = document.cookie.indexOf(";", c_start);
			if (c_end == -1) c_end = document.cookie.length;
			return unescape(document.cookie.substring(c_start, c_end));
		}
	}
	return "";
}

function disableImgButton(obj, disable)
{
	obj.disabled = disable;
	obj.className = disable ? 'select-btn_disabled' : 'select-btn_enabled';
}

function changeAssociatedHidden(obj)
{
	obj.value = ( obj.checked ? '1' : '' );
	var hiddenObj = document.getElementById(obj.name.replace('_checkbox', ''));
	hiddenObj.value = obj.value;
}

function getSumForJS(str)
{
	while (str.indexOf('.') != -1)
	{
		str = str.replace('.', '');
	}
	str = str.replace(',', '.');

	return str;
}

function getDateForJS(str)
{
	if ('' == str.trim())
	{
		alert('Incorrect date: "' + str + '"');
		return null;
	}

	var segmentsDate = str.split(".");
	if (segmentsDate.length < 3)
	{
		alert('Incorrect date: "' + str + '"');
		return null;
	}

	var vDate = new Date();
	vDate.setYear(0);
	vDate.setMonth(0);
	vDate.setDate(1);

	vDate.setYear(parseInt(segmentsDate[2]));
	var month = gNow.getMonth();
	if (segmentsDate[1].charAt(0) == '0')
	{
		month = parseInt(segmentsDate[1].charAt(1)) - 1;
	}
	else
	{
		month = parseInt(segmentsDate[1]) - 1;
	}
	vDate.setMonth(month);
	var day = gNow.getDate();
	if (segmentsDate[0].charAt(0) == '0')
	{
		day = parseInt(segmentsDate[0].charAt(1));
	}
	else
	{
		day = parseInt(segmentsDate[0]);
	}
	vDate.setDate(day);

	return vDate;
}

initFunctions.push(createHintBox);
initFunctions.push(stopProgress);
window.onload = callInitFunctions;
</script>