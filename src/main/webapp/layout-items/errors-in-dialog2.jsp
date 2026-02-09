<%--
  User: Dima
  Date: Mar 13, 2003
  Time: 12:24:02 PM
--%>
<%@ page import="org.apache.struts.Globals,
                 org.apache.struts.taglib.TagUtils,
                 org.apache.struts.action.ActionMessages,
                 java.util.Iterator,
                 org.apache.struts.action.ActionMessage" %>
<%@ page import="org.apache.struts.action.ActionMessage" %>
<%@ page import="net.sam.dcl.message.EMessage" %>
<%@ page import="net.sam.dcl.util.StringUtil" %>
<%@ page import="net.sam.dcl.action.ShowPrevResponse" %>
<%@ page import="net.sam.dcl.util.StrutsUtil" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>

<%
  ActionMessages errors = null;
  try
  {
    errors = TagUtils.getInstance().getActionMessages(pageContext, Globals.ERROR_KEY);
  }
  catch (JspException e)
  {
    TagUtils.getInstance().saveException(pageContext, e);
    throw e;
  }
%>
<%--<html:messages id="" --%>
<script language="JScript" type="text/javascript">
  var __errorSavedEvenHandler = null;
  function ErrorObj(name, msg, details, exception)
  {
    this.name = name;
    this.msg = msg;
    this.details = details;
    this.exception = exception;
  }
  var pageErrorsArr = new Array();
  var __errorsInited = false;
  function __formatErrorsForAlert(arr)
  {
    var parts = [];
    for (var i = 0; i < arr.length; i++)
    {
      var o = arr[i];
      var line = "";
      if (o && o.name)
      {
        line += o.name + ": ";
      }
      if (o && o.msg)
      {
        line += o.msg;
      }
      if (o && o.details)
      {
        line += " (" + o.details + ")";
      }
      if (line.length > 0)
      {
        parts.push(line);
      }
    }
    return parts.join("\n");
  }
  function showModalDialogCompat(url, args, features)
  {
    var msg = __formatErrorsForAlert(args || []);
    if (msg && msg.length > 0 && typeof window.__dclShowErrorInBanner === "function") {
      window.__dclShowErrorInBanner(msg);
      return null;
    }
    if (typeof window.showModalDialog === "function")
    {
      return window.showModalDialog(url, args, features);
    }
    if (msg && msg.length > 0)
    {
      alert(msg);
    }
    return null;
  }
  function showCustomError(name, msg, details, exception)
  {
    var errText = (name ? name + ": " : "") + (msg || "") + (details ? " (" + details + ")" : "");
    if (errText && typeof window.__dclShowErrorInBanner === "function") {
      window.__dclShowErrorInBanner(errText);
      return;
    }
    var customErr = [new ErrorObj(name, msg, details, exception)];
    showModalDialogCompat("<ctrl:rewrite action='/trusted/ErrorDialog'/>", customErr, "dialogWidth:500px;dialogHeight:100px;help:0;status:0;scroll:0");
  }
  function showCustomErrors(customErrArr)
  {
    var errText = __formatErrorsForAlert(customErrArr || []);
    if (errText && typeof window.__dclShowErrorInBanner === "function") {
      window.__dclShowErrorInBanner(errText);
      return;
    }
    showModalDialogCompat("<ctrl:rewrite action='/trusted/ErrorDialog'/>", customErrArr, "dialogWidth:500px;dialogHeight:100px;help:0;status:0;scroll:0");
  }

  function __showPageError()
  {
    showModalDialogCompat("<ctrl:rewrite action='/trusted/ErrorDialog'/>", pageErrorsArr, "dialogWidth:500px;dialogHeight:100px;help:0;status:0;scroll:0");
    return false;
  }
  function __ErrorReadyStateChange()
  {
    if (__errorSavedEvenHandler)
    {
      __errorSavedEvenHandler.call();
    }
    if (__errorsInited == false)
    {
      __errorsInited = true;
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
        if (error instanceof EMessage){
          exc = ((EMessage)error).getException();
        }
        out.print(StrutsUtil.getError4JavaScript(msg, exc));
        out.flush();
      }

    %>
    <%=StrutsUtil.COMMENT_FOR_INSERTING_ERRORS%>
      if (pageErrorsArr.length != 0)
      {
        __showPageError();
      }
    }
  }
  __errorSavedEvenHandler = document.onreadystatechange;
  document.onreadystatechange = __ErrorReadyStateChange;
</script>

