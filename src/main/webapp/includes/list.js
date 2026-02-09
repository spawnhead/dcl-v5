var __listObject = null;
var __listObjectSkipFilter = false;
var __listClosing = false;
var __listClosing = false;

var __divTbl = null;
var __listTrs = null;
var __listTrsLenght = -1;
var __currentTrIdx = -1;

function setNornalRow(tableRow)
{
  tableRow.style.textDecoration='none';
  tableRow.getElementsByTagName("td")[0].style.backgroundColor = '#eeeeee';
}

function setSelectedRow(tableRow)
{
  tableRow.style.textDecoration='underline';
  tableRow.getElementsByTagName("td")[0].style.backgroundColor = '#cccccc';
}

function __keydownList(frm, input, btn, filter, dispatch)
{
  if (window.event.keyCode == 27 && __listObject)
  {
    document.onclick();
    return;
  }
  if (window.event.keyCode == 40 && !__listObject)
  {
    var btnObj = __getElementByIdOrName(btn);
    if (btnObj)
    {
      if (btnObj.onclick)
      {
        btnObj.onclick();
      }
      else if (btnObj.click)
      {
        btnObj.click();
      }
    }
  }
  if ((window.event.keyCode == 38 || window.event.keyCode == 40) && __listObject)
  {
    if ( __listTrs )
    {
      var tableRow;
      if ( __currentTrIdx > -1 )
      {
        tableRow = __listTrs[__currentTrIdx];
        setNornalRow(tableRow);
      }
      if (window.event.keyCode == 40 && __currentTrIdx < __listTrsLenght - 1)
      {
        __currentTrIdx++;
      }
      if (window.event.keyCode == 38 && __currentTrIdx > 0)
      {
        __currentTrIdx--;
      }
      tableRow = __listTrs[__currentTrIdx];
      __divTbl.scrollTop = tableRow.offsetTop;
      setSelectedRow(tableRow);
    }
  }
  //до следующшей проверки, чтобы __listObject не разрушался. 
  if (window.event.keyCode == 13 && !__listObject && dispatch != '')
  {
    submitDispatchForm(dispatch);
  }
  if (window.event.keyCode == 13 && __listObject)
  {
    if ( __listTrs )
    {
      __listTrs[__currentTrIdx].onclick();
    }
  }
}

function __filterList(frm, input, btn)
{
  if (__listClosing) return;
  if (__listObjectSkipFilter) return;
  var evt = event || window.event;
  if (!evt)
  {
    return;
  }
  if (evt.propertyName && evt.propertyName != 'value')
  {
    return;
  }
  {
    if (!__listObject)
    {
      var btnObj = __getElementByIdOrName(btn);
      if (btnObj)
      {
        if (btnObj.onclick)
        {
          btnObj.onclick();
        }
        else if (btnObj.click)
        {
          btnObj.click();
        }
      }
    }
    var sel = __getElementByIdOrName(frm);
    var inp = __getElementByIdOrName(input);
    if (!sel || !inp)
    {
      return;
    }
    sel.src = sel.originalSrc + encodeURI(inp.value);
  }
}

function __getElementByIdOrName(name)
{
  return document.getElementById(name) || document.getElementsByName(name)[0];
}

function __showList2(src, input, idInput, frm, btnStr, callback, size, filter, data, width)
{
  __documentMouseClick();

  var btn = __getElementByIdOrName(btnStr);
  var inp = __getElementByIdOrName(input);
  var sel = __getElementByIdOrName(frm);
  if (!btn || !inp || !sel)
  {
    return false;
  }
  sel.style.display = "";

  sel.originalSrc = src;
  sel.originalValue = inp.value;
  sel.originalReadOnly = inp.readOnly;
  sel.filter = filter;

  if (filter)
  {
    __listObjectSkipFilter = true;
    if (inp.readOnly)
    {
      inp.readOnly = false;
    }
    inp.focus();
    __listObjectSkipFilter = false;
    src += encodeURI(inp.value);
  }

  sel.src = src;

  sel.style.left = getAbsPosX(inp);
  sel.style.top = getAbsPosY(inp) + inp.offsetHeight - 1;
  if (width)
  {
    sel.style.width = width;
  }
  else
  {
    var calcWidth = getAbsPosX(btn) + btn.offsetWidth - getAbsPosX(inp) - 2;
    var minWidth = inp.offsetWidth + (btn ? btn.offsetWidth : 0);
    if (calcWidth < minWidth)
    {
      calcWidth = minWidth;
    }
    sel.style.width = calcWidth;
  }

  sel.style.height = 0;
  sel.style.display = "block";
  sel.callback = callback;
  sel.data = data;
  sel.inputName = inp.name || inp.id || input;
  if (idInput)
  {
    var idEl = __getElementByIdOrName(idInput);
    sel.idName = idEl ? (idEl.name || idEl.id || idInput) : idInput;
  }
  else
  {
    sel.idName = idInput;
  }
  sel.btnObject = btn;
  sel.prefferedSize = size ? size : 100;
  __savedDocumentClickHandler = document.onclick;
  document.onclick = __documentMouseClick;
  __listObject = sel;
  if (window.event != null)
  {
    window.event.cancelBubble = true;
  }

  return false;
}

var __savedDocumentClickHandler = null;
function __documentMouseClick()
{
  if (__savedDocumentClickHandler)
  {
    __savedDocumentClickHandler.call();
  }
  var evt = event || window.event;
  var target = evt ? (evt.target || evt.srcElement) : null;
  if (__listObject != undefined && __listObject && target && target != __listObject)
  {
    document.onclick = __savedDocumentClickHandler;
    __doneForFilter(window, true);
    __listObject.style.display = "none";
    __listObject.callback = null;
    __listObject = null;
    if (target.tagName == 'INPUT' && target.type == 'image')
    {
      return false;
    }
    if (target.tagName == 'A' && (!evt || evt.returnValue != true))
    {
      return false;
    }
  }
  return true;
}

function __hideList(listObj)
{
  var obj = listObj || __listObject;
  if (!obj)
  {
    return;
  }
  obj.style.display = "none";
  obj.callback = null;
  __listObject = null;
  document.onclick = __savedDocumentClickHandler;
  __savedDocumentClickHandler = null;
}

function __doneForFilter(win, restoreOriginalValue)
{
  var obj = win.__listObject;
  if (obj.inputName)
  {
    win.__listObjectSkipFilter = true;
    var inp = __getElementByIdOrName(obj.inputName);
    if (!inp)
    {
      win.__listObjectSkipFilter = false;
      return;
    }
    if (restoreOriginalValue)
    {
      inp.value = obj.originalValue;
    }
    if (obj.originalReadOnly)
    {
      inp.readOnly = 'true';
      obj.focus();
    }
    win.__listObjectSkipFilter = false;
  }
}

function getAbsPosX(obj)
{
  if (!obj)
  {
    return 0;
  }
  var x = obj.offsetLeft;
  var parObj = obj.offsetParent;
  while (parObj)
  {
    var style = parObj.currentStyle || (window.getComputedStyle ? window.getComputedStyle(parObj, null) : null);
    var position = style ? style.position : parObj.style.position;
    var overflow = style ? style.overflow : parObj.style.overflow;
    var overflowX = style ? style.overflowX : parObj.style.overflowX;
    var overflowY = style ? style.overflowY : parObj.style.overflowY;
    if (position == 'absolute' || overflow == 'scroll' || overflowX == 'scroll' || overflowY == 'scroll')
    {
      break;
    }
    var bw = 0;
    if (parObj.style.borderLeftWidth)
    {
      bw = parObj.style.borderLeftWidth.substr(0, parObj.style.borderLeftWidth.length - 2);
    }
    x += parObj.offsetLeft + (bw * 1);
    if (parObj.tagName == 'DIV')
    {
      x += parObj.clientLeft;
    }

    parObj = parObj.offsetParent;
  }
  return x;
}

function getAbsPosY(obj)
{
  if (!obj)
  {
    return 0;
  }
  var x = obj.offsetTop;
  var parObj = obj.offsetParent;
  while (parObj)
  {
    var style = parObj.currentStyle || (window.getComputedStyle ? window.getComputedStyle(parObj, null) : null);
    var position = style ? style.position : parObj.style.position;
    var overflow = style ? style.overflow : parObj.style.overflow;
    var overflowX = style ? style.overflowX : parObj.style.overflowX;
    var overflowY = style ? style.overflowY : parObj.style.overflowY;
    if (position == 'absolute' || overflow == 'scroll' || overflowX == 'scroll' || overflowY == 'scroll')
    {
      break;
    }
    x += parObj.offsetTop
    if (parObj.tagName == 'DIV')
    {
      x += parObj.clientTop;
    }
    parObj = parObj.offsetParent;
  }
  return x;
}


