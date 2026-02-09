<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>

<ctrl:askUser name="askCopy" key="msg.nomenclature.askCopy" showOkCancel="false" height="60" width="400"/>

<div id="movingProduceModeMessage"
     style="position:absolute;	visibility:hidden; border:1px solid black; background : red; width:180px;height:20px">
  <bean:message key="Nomenclature.moving.text"/><br></div>
<div id="mergingProduceModeMessage"
     style="position:absolute;	visibility:hidden; border:1px solid black; background : red; width:180px;height:20px">
  <bean:message key="Nomenclature.merge.text"/><br></div>
<%--<div >Выбирите категорию или нажмите на "<bean:message key="button.dont-move"/>" для отмены</div>--%>
<script language="JScript" type="text/javascript" src='<ctrl:rewrite page='/includes/xtree.js'/>'></script>
<%--<script type="text/javascript" src='<ctrl:rewrite page="/includes/cerny/js/json/HtmlPrettyPrinter.js"/>' ></script>--%>
<ctrl:showIfSelectMode>
  <div style="color:red;text-align:center"><bean:message key="Nomenclature.not-show.blocked.mode"/></div>
  <div style="color:#008000;text-align:center"><bean:message key="Nomenclature.not-show.blocked.mode_legend"/></div>
</ctrl:showIfSelectMode>
<ctrl:form styleId="n-form">
  <ctrl:hidden property="cat_id" styleId="cat_id"/>
  <ctrl:hidden property="mergeTargetId"/>
  <table id=filterTbl style="" align="center" border="0" width="100%">
    <tr>
      <td align="right"><bean:message key="Nomenclature.filter.catalog_number"/></td>
      <td><ctrl:text property="filter_catalog_number" styleClass="filter"/></td>
      <td width="15">&nbsp;</td>
      <td align="right"><bean:message key="Nomenclature.filter.produce"/></td>
      <td><ctrl:text property="filter_produce" styleClass="filter"/></td>
      <td width="15">&nbsp;</td>
      <td align="right"><bean:message key="Nomenclature.filter.type"/></td>
      <td><ctrl:text property="filter_type" styleClass="filter" style="width:220px"/></td>
      <td width="15">&nbsp;</td>
      <td align="right"><bean:message key="Nomenclature.filter.stuffCategory"/></td>
      <td><ctrl:serverList property="stf_name" idName="stf_id" action="/StuffCategoriesListAction" selectOnly="true"
                           filter="filter" styleClass="filter" readonly="${Nomenclature.selectMode}"/></td>
    </tr>
    <tr>
      <td align="right"><bean:message key="Nomenclature.filter.params"/></td>
      <td><ctrl:text property="filter_params" styleClass="filter"/></td>
      <td width="15">&nbsp;</td>
      <td align="right"><bean:message key="Nomenclature.filter.add_params"/></td>
      <td><ctrl:text property="filter_add_params" styleClass="filter"/></td>
      <td width="15">&nbsp;</td>
      <td align="right"><bean:message key="Nomenclature.filter.cusCode"/></td>
      <td><ctrl:serverList property="filter_cusCode" idName="filter_cusCode_id" action="/CustomCodesListAction"
                           selectOnly="true" filter="filter" styleClass="filter"/></td>
      <td width="15">&nbsp;</td>
      <td align="right"><bean:message key="Nomenclature.filter.common"/></td>
      <td><ctrl:text property="filter_common" styleClass="filter"/></td>
    </tr>
    <tr>

    </tr>
    <tr>
      <td align="right" colspan=20>
        <ctrl:ubutton type="submit" dispatch="input" styleClass="width120"><bean:message key="button.clearFilter"/></ctrl:ubutton>&nbsp;
        <ctrl:ubutton type="submit" dispatch="filter" styleClass="width120"><bean:message key="button.applyFilter"/></ctrl:ubutton>
      </td>
    </tr>
  </table>
  <table width='100%' border="0">
    <tr>
      <td width="25%" valign="top" style="vertical-align:top">
        <div id='treeGrid' class="gridBack" style='height:expression(document.body.clientHeight-320);overflow-y:scroll;vertical-align:top'>
          <img src='<%= request.getContextPath() %>/images/wait.gif'>
        </div>
      </td>
      <td valign="top" style="vertical-align:top">&nbsp;</td>
      <td width="73%" valign="top" align="top" style="vertical-align:top">
        <div id='nomenclatureGrid' class="gridBack" style='height:expression(document.body.clientHeight-320);'>
          <div id='nomenclatureGridInner'>
            <img src='<%= request.getContextPath() %>/images/wait.gif'>
          </div>
        </div>
      </td>
    </tr>
  </table>
  <ctrl:notShowIfSelectMode>
    <div class=gridBottom>
    <table width='100%' border="0">
    <tr>
    <td width="25%">
      <ctrl:text property="category_name" styleClass="filter"/>
      <ctrl:ubutton type="script" dispatch="fakeDispatchForEdit" styleClass="width80" onclick="return addCategory()">
        <bean:message key="button.add"/>
      </ctrl:ubutton>
      &nbsp;
      <ctrl:ubutton type="script" dispatch="fakeDispatchForEdit" styleClass="width80" onclick="return changeCategory();"
                    styleId="changeCategoryBtn">
        <bean:message key="button.change"/>
      </ctrl:ubutton>
      &nbsp;
      <ctrl:ubutton type="script" dispatch="fakeDispatchForDelete" styleClass="width80"
                    onclick="return deleteCategory();" styleId="deleteCategoryBtn">
        <bean:message key="button.delete"/>
      </ctrl:ubutton>
      &nbsp;
      <ctrl:ubutton type="script" dispatch="fakeDispatchForShowAllTree" styleClass="width200"
                    onclick="return showAllTree();" styleId="showAllTreeBtn">
        <bean:message key="button.showAllTree"/>
      </ctrl:ubutton>
    </td>
    <td valign=center align="middle">&nbsp;</td>
    <td width="73%" align="right">

    <logic:equal name="Nomenclature" property="currentUser.admin" value="true">
      <ctrl:help htmlName="downloadDoubleValuesButton"/>
      <ctrl:ubutton type="script" styleClass="width120" onclick="return download();">
        <bean:message key="button.downloadDoubleValues"/>
      </ctrl:ubutton>
      <ctrl:ubutton type="submit" dispatch="uploadTemplate"
                    scriptUrl="referencedTable=DCL_TEMPLATE&referencedID=${Nomenclature.templateIdNomenclature}&id=${Nomenclature.templateId}"
                    styleClass="width165" readonly="false">
        <bean:message key="button.attachTemplate"/>
      </ctrl:ubutton>
    </logic:equal>

    <logic:equal name="Nomenclature" property="showDownload" value="true">
      <input type=button id="downloadTemplate" onclick="return downloadAttach(${Nomenclature.templateId});"
             class="width165" value="<bean:message key="button.saveTemplate"/>">
    </logic:equal>

    <logic:equal name="Nomenclature" property="currentUser.admin" value="true">
      <ctrl:ubutton type="script" dispatch="fakeDispatchForMerging" styleClass="width120"
                    onclick="return toggleMergingProduces();" styleId="mergeProducesBtn" showWait="false">
        <bean:message key="button.merge"/>
      </ctrl:ubutton>
    </logic:equal>
    <ctrl:ubutton type="script" dispatch="fakeDispatchForMove" styleClass="width120"
                  onclick="return startMovingProduces();" styleId="moveProducesBtn" showWait="false">
      <bean:message key="button.move"/>
    </ctrl:ubutton>
    <ctrl:ubutton type="link" dispatch="importProduces" scriptUrl="cat_id=$(cat_id)" styleClass="width120" style="">
      <bean:message key="button.importExcel"/>
    </ctrl:ubutton>
    <ctrl:ubutton type="script" styleClass="width120" onclick="return produceMoovmentReport();">
      <bean:message key="button.moovmentReport"/>
    </ctrl:ubutton>

  </ctrl:notShowIfSelectMode>
  <div style="text-align:right;">
    <ctrl:ubutton type="link" dispatch="createNomenclatureProduce" scriptUrl="cat_id=$(cat_id)" styleClass="width80"
                  styleId="createProduceBtn" style="" onclick="if (topCategory()) return false;">
      <bean:message key="button.create"/>
    </ctrl:ubutton>
  </div>
  <ctrl:notShowIfSelectMode>
    </td>
    </tr>
    </table>
    </div>
  </ctrl:notShowIfSelectMode>
</ctrl:form>

<script language="JScript" type="text/javascript">

var showAllTreeBtn = document.getElementById('showAllTreeBtn');
if (showAllTreeBtn != null)
	showAllTreeBtn.style.visibility = 'hidden';

var movingProducesMode = false;
var mergingProducesMode = false;
function loadTree(ignoreFilter, restoreFilter)
{
  if (document.getElementById('deleteCategoryBtn'))
  {
	  document.getElementById('deleteCategoryBtn').disabled = true;
  }

  $('treeGrid').update("<img src='<%= request.getContextPath() %>/images/wait.gif'>");
  doAjax({
    url:'<ctrl:rewrite action="/NomenclatureAction" dispatch="ajaxTree"/>',
    params:{ignoreFilter:ignoreFilter,restoreFilter:restoreFilter},
    form:'n-form',
    okCallback:function(transport)
    {
      var str = _wTH.tree.toString();
      $('treeGrid').update(str);
      catTree = _wTH.tree;
      if (_wTH.selectedNode)
      {
        var parent = _wTH.selectedNode.parentNode;
        while (parent && parent.parentNode)
        {
          parent.expand();
          parent = parent.parentNode;
        }
        _wTH.select(_wTH.selectedNode);
      }
      else
      {
        if (catTree)
        {
          _wTH.select(catTree);
        }
      }
    }
  });
}

var catTree = null;
var firstTime = true;
function loadGrid(arg)
{
  var selectedNode = arg || _wTH.selected;
  if (!selectedNode) return;
  var catId = selectedNode.cat_id;
  if (catId === undefined || catId === null) return;
  var catIdObj = $('cat_id');
  if (!catIdObj) {
    var f = document.getElementById('n-form');
    if (f) catIdObj = f.elements['cat_id'] || f.elements['Nomenclature.cat_id'];
  }
  if (!catIdObj) return;
  var nameEl = $('category_name');
  if (nameEl && nameEl.value !== undefined)
  {
    nameEl.value = selectedNode.text;
  }
  if (!firstTime)
  {
    if (String(catIdObj.value) === String(catId)) return;
  }
  else
  {
    firstTime = false;
  }
  catIdObj.value = catId;
  var actionUrl = '<ctrl:rewrite action="/NomenclatureAction" dispatch="ajaxGrid"/>';
  if (movingProducesMode)
  {
    actionUrl = '<ctrl:rewrite action="/NomenclatureAction" dispatch="moveProduces"/>';
  }
  var savedTime = new Date().getTime();
  doAjax({
    url:actionUrl,
    form:'n-form',
    update:'nomenclatureGridInner',
    callback:function(transport)
    {
      //debugger;
      //alert(new Date().getTime()-savedTime);
      savedTime = new Date().getTime();

      if (movingProducesMode)
      {
        startMovingProduces();
      }
      if (document.getElementById('deleteCategoryBtn'))
      {
	      document.getElementById('deleteCategoryBtn').disabled = !(catTree.getSelected().getFirst() == null && produceCount == 0);
      }
    }});

}

function addCategory()
{
  doAjax({
    url:'<ctrl:rewrite action="/NomenclatureAction" dispatch="addCategory"/>',
    form:'n-form'
  });
  stopProgress();
}

function deleteCategory()
{
  if (isUserAgree('<bean:message key="msg.nomenclature.delete-category.1"/>' + $F('category_name') + '<bean:message key="msg.nomenclature.delete-category.2"/>', true, 500, 100, true))
  {
    doAjax({
      url:'<ctrl:rewrite action="/NomenclatureAction" dispatch="deleteCategory"/>',
      okCallback:function(transport)
      {
        if (catTree.getSelected())
        {
          catTree.getSelected().remove();
        }
      }
    });
  }
  stopProgress();
}

function showAllTree()
{
	loadTree(true, null);
	stopProgress();
}

function changeCategory()
{
  doAjax({
    url:'<ctrl:rewrite action="/NomenclatureAction" dispatch="changeCategory"/>',
    form:'n-form',
    okCallback:function(transport)
    {
      if (catTree.getSelected())
      {
        $(catTree.getSelected().id + "-anchor").innerHTML = $('category_name').value;
        catTree.getSelected().text = $('category_name').value;
      }
    }
  });
  stopProgress();
}

function getProduceDetails(id)
{
  var result = "";
  doAjax({
    url:'<ctrl:rewrite action="/NomenclatureAction" dispatch="getProduceDetails"/>',
    params:{prd_id:id},
    synchronous:true,
    okCallback:function(transport)
    {
      result = transport.responseText;
    }
  });
  return result;
}

function startMovingProduces()
{
  if (movingProducesMode)
  {
    movingProducesMode = false;
    loadTree(null, true);
    movingProduceModeMessage.style.visibility = 'hidden';
	  document.getElementById('moveProducesBtn').value = '<bean:message key="button.move"/>';
	  document.getElementById('showAllTreeBtn').style.visibility = 'hidden';
  }
  else
  {
    //loadTree(null, true);
    movingProducesMode = true;
    movingProduceModeMessage.style.visibility = 'visible';
	  document.getElementById('moveProducesBtn').value = '<bean:message key="button.dont-move"/>';
	  document.getElementById('showAllTreeBtn').style.visibility = 'visible';
  }
}

function toggleMergingProduces()
{
  if (mergingProducesMode)
  {
    mergingProducesMode = false;
    mergingProduceModeMessage.style.visibility = 'hidden';
	  document.getElementById('mergeProducesBtn').value = '<bean:message key="button.merge"/>';
    document.body.style.cursor = 'default';
  }
  else
  {
    mergingProducesMode = true;
    mergingProduceModeMessage.style.visibility = 'visible';
	  document.getElementById('mergeProducesBtn').value = '<bean:message key="button.dont-merge"/>';
  }
}

function isEnableProduceSelection()
{
  return mergingProducesMode;
}

function mergeProduces(obj, id)
{
  if (event.srcElement.type == 'checkbox')
  {
    return;
  }
  if (obj.children[0].children[0].checked)
  {
    return;
  }
	toggleMergingProduces();
  $('mergeTargetId').value = id;
  submitDispatchForm("mergeProduces");
}

initFunctions.push(loadTree);

function handlerMM(e)
{
  if (movingProducesMode)
  {
    movingProduceModeMessage.style.left = (e) ? e.pageX : document.body.scrollLeft + event.clientX + 10;
    movingProduceModeMessage.style.top = (e) ? e.pageY : document.body.scrollTop + event.clientY + 10;
  }
  if (mergingProducesMode)
  {
    mergingProduceModeMessage.style.left = (e) ? e.pageX : document.body.scrollLeft + event.clientX + 10;
    mergingProduceModeMessage.style.top = (e) ? e.pageY : document.body.scrollTop + event.clientY + 20;
  }
}

document.onmousemove = handlerMM;

function changeGridSelection(origObj)
{
  var chs = getCheckboxes1stColumn();
  var i = 0;
  for (; i < chs.length; i++)
  {
    $(chs[i]).checked = origObj.checked;
  }
}

function topCategory()
{
  if (!catTree.getSelected().parentNode)
  {
    stopProgress();
    showCustomError(null, '<bean:message key="Nomenclature.not-selected.category"/> ');
    unlockForm();
    return true;
  }
  return false;
}

function getCheckboxes1stColumn()
{
  return $$('#nomenclatureGrid td.first-column input.grid-checkbox');
}

function produceMoovmentReport()
{
  //var obj = $('nomenclatureGrid');
  //var chs = obj.getElementsByClassName('grid-checkbox');
  var chs = getCheckboxes1stColumn();
  var i = 0;
  var lastChecked = 0;
  var checkedCount = 0;
  for (; i < chs.length; i++)
  {
    if ($(chs[i]).checked)
    {
      lastChecked = $(chs[i]);
      checkedCount++;
    }
  }

  if (checkedCount == 0)
  {
    stopProgress();
    showCustomError(null, '<bean:message key="Nomenclature.not-selected.position"/> ');
    return false;
  }
  if (checkedCount > 1)
  {
    stopProgress();
    showCustomError(null, '<bean:message key="Nomenclature.selected-too-many.position"/> ');
    return false;
  }
  document.location = '<html:rewrite action="/ProduceMovementForNomenclatureAction.do?dispatch=input"/>&produce.id=' + lastChecked.value;
  return false;
}

function download(id)
{
	document.getElementById('for-insert').innerHTML = '<iframe src=\'<html:rewrite action="/NomenclatureAction.do?dispatch=downloadDoubleValues"/>&stf_id=' + document.getElementById("stf_id").value + '\' style=\'display:none\' />';
  stopProgress();
  return false;
}

function setNotCheckDouble(id, obj)
{
  doAjax({
    url:'<ctrl:rewrite action="/NomenclatureAction" dispatch="setNotCheckDouble"/>',
    form:'n-form',
    params:{notCheckDoubleId:id,notCheckDouble:obj.checked ? "1" : ""},
    synchronous:true
  });
  stopProgress();
  return true;
}

function downloadAttach(id)
{
	document.getElementById('for-insert').innerHTML = '<iframe src=\'<html:rewrite action="/AttachmentsAction.do?dispatch=download"/>&id=' + id + '\' style=\'display:none\' />';
  return false;
}

</script>
<div id='for-insert'></div>