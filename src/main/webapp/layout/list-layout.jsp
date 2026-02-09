<%--
  Created by IntelliJ IDEA.
  User: dgena
  Date: Sep 27, 2004
  Time: 10:00:07 PM
--%>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<tiles:importAttribute name="css"/>
<HTML>
<HEAD>
  <META HTTP-EQUIV="Pragma" CONTENT="no-cache">
  <META HTTP-EQUIV="Expires" CONTENT="-1">
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <%@include file="/includes/util.jsp" %>
  <link rel="stylesheet" href="<ctrl:rewrite page='<%=(String)pageContext.getAttribute("css")%>'/>" type="text/css">
  <script language="JScript" type="text/javascript" src='<ctrl:rewrite page='/includes/list.js'/>?v=20260120h'></script>
</HEAD>
<BODY leftmargin=0 rightmargin=0 topmargin=0 bottommargin=0>
<table id=outerTable cellSpacing=0 cellPadding=0 width='100%' height='100%' border=0>
  <tr>
    <td height='100%' valign=top>
      <%--body--%>
      <div id=outerDiv style='overflow-y:auto;overflow-x:auto;'>
        <table id=list-table name='list-table' width='100%' cellSpacing=1 cellPadding=2 border=0>
          <tiles:get name='body'/>
        </table>
      </div>
    </td>
  </tr>
</table>
</BODY>
<script type="text/javascript" language="JScript">
  function getListObject()
  {
    return parent.__listObject;
  }

  function getParentElementByIdOrName(name)
  {
    return parent.document.getElementById(name) || parent.document.getElementsByName(name)[0];
  }

  function ItemSelected()
  {
    var obj = getListObject();
    if (!obj)
    {
      return false;
    }
    if ( arguments[1] == '<hr>' )
      return;

    parent.document.onclick = parent.__savedDocumentClickHandler;

    parent.__listObjectSkipFilter = true;
    parent.__listClosing = true;
    var input = getParentElementByIdOrName(obj.inputName);
    if (!input)
    {
      parent.__listObjectSkipFilter = false;
      return false;
    }
    //alert("["+obj.idName+"]");
    if (obj.idName != "")
    {
      var idInput = getParentElementByIdOrName(obj.idName);
      if (!idInput)
      {
        parent.__listObjectSkipFilter = false;
        return false;
      }
      idInput.value = arguments[0];
      var resValue = arguments[1];
      if ( arguments[1].indexOf('<span') != -1 )
      {
        var strArr = arguments[1].split('<span');
        resValue = strArr[0];
      }
      input.value = resValue;
    }
    else
    {
      input.value = arguments[0];
    }
    window.setTimeout(function () {
      parent.__listObjectSkipFilter = false;
      parent.__listClosing = false;
    }, 50);

    var callbackError = null;
    if (obj.callback)
    {
      //    obj.callback.call(null,arguments[0],arguments[1],arguments[2],arguments[3],arguments[4],arguments[5],
      //                         arguments[6],arguments[7],arguments[8],arguments[9],arguments[10],arguments[11] );
      var args = {
        "arguments":arguments,
        data:obj.data,
        object:obj
      };
      try
      {
        obj.callback.call(null, args);
      }
      catch (e)
      {
        callbackError = e;
      }
    }
    parent.__doneForFilter(parent, false);
    if (parent.__hideList)
    {
      parent.__hideList(obj);
    }
    else
    {
      obj.style.display = 'none';
      parent.__listObject = null;
    }
    if (callbackError && parent.console && parent.console.error)
    {
      parent.console.error(callbackError);
    }
    return false;
  }

  var __listSavedPropertyChangeHandler = null;
  function stateChanged()
  {
    var obj = getListObject();
    if (obj == undefined || obj == null)
    {
      return;
    }
    if (__listSavedPropertyChangeHandler)
    {
      __listSavedPropertyChangeHandler.call();
    }
    var tbl = document.getElementsByName("list-table")[0];
    var trs = tbl.getElementsByTagName("tr");
    //alert(tbl.innerHTML);
    parent.__divTbl = outerDiv;
    parent.__listTrs = trs;
    parent.__listTrsLenght = trs.length;
    parent.__currentTrIdx = -1;
    if (obj.prefferedSize > tbl.offsetHeight)
    {
      obj.style.height = tbl.offsetHeight;
      outerDiv.style.height = parseInt(tbl.offsetHeight);
    }
    else
    {
      obj.style.height = obj.prefferedSize;
      outerDiv.style.height = obj.prefferedSize;
    }
    if (tbl.offsetWidth > parseInt(obj.style.width))
    {
      outerDiv.style.width = obj.style.width;
      var scrSize = parseInt(outerDiv.style.height) - parseInt(outerDiv.clientHeight);
      obj.style.height = parseInt(obj.style.height) + scrSize;
      outerDiv.style.height = parseInt(outerDiv.style.height) + scrSize;
    }
    //outerDiv.focus();
  }
  __listSavedOnLoadHandler = window.onload;
  window.onload = function ()
  {
    if (__listSavedOnLoadHandler)
    {
      __listSavedOnLoadHandler.call();
    }
    stateChanged();
  };
</script>
<tiles:insert attribute="errors"/>
<HEAD>
  <META HTTP-EQUIV="Pragma" CONTENT="no-cache">
</head>
</HTML>
