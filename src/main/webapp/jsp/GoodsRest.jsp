<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>

<ctrl:askUser name="deleteAttachAsk" key="msg.goods_rest.delete_attach" showOkCancel="false" height="120"/>

<ctrl:form styleId="striped-form">
  <logic:equal name="GoodsRest" property="shipping_goods" value="1">
    <ctrl:hidden property="purpose.id"/>
    <ctrl:hidden property="purpose.name"/>
  </logic:equal>
  
<div id="filter-form" >

  <table id=filterTbl align="center" border="0" width="880">
    <tr>
      <td colspan=20>
        <table border="0">
          <tr>
            <td>
              <bean:message key="GoodsRest.date"/>
            </td>
            <td>
              <ctrl:date property="date_begin" styleClass="filter-long" afterSelect='enterDate' endField="date_end" showHelp="false"/>
            </td>
            <td>
              &nbsp;&nbsp;&nbsp;
              <ctrl:checkbox property="have_date_to" styleClass="checkbox" value="1" onclick="haveDateToOnClick();"/>
            </td>
            <td>
              <bean:message key="GoodsRest.date_to"/>
            </td>
            <td>
              <ctrl:date property="date_end" styleClass="filter-long" startField="date_begin" showHelp="false"/>
            </td>
          </tr>
        </table>
      </td>
    </tr>
    <tr>
      <td>
        <table width="100%">
          <tr>
            <td>
              <bean:message key="GoodsRest.goods_on_storage"/>&nbsp;
              <ctrl:checkbox property="goods_on_storage" styleClass="checkbox" value="1" onclick="goodsOnStorageSubmitReload();"/>&nbsp;
              <bean:message key="GoodsRest.shipping_goods"/>&nbsp;
              <ctrl:checkbox property="shipping_goods" styleClass="checkbox" value="1" onclick="shippingGoodsSubmitReload();"/>&nbsp;
            </td>
          </tr>
        </table>
      </td>
    </tr>
    <tr>
      <td>
        <table width="100%">
          <tr>
            <td valign="top" colspan="20">
              <table>
                <tr>
                  <td colspan="2" align="center" class="table-header">
                    <bean:message key="GoodsRest.form_by"/>
                  </td>
                </tr>
                <tr>
                  <td align="right" width="30%">
                    <bean:message key="GoodsRest.user"/>
                  </td>
                  <td>
                    <ctrl:serverList property="user.userFullName" idName="user.usr_id" action="/UsersListAction"
                                     styleClass="filter-long" callback="onChangeUser" scriptUrl="have_all=true"
                                     filter="filter"/>
                  </td>
                </tr>
                <tr>
                  <td align="right">
                    <bean:message key="GoodsRest.department"/>
                  </td>
                  <td>
                    <ctrl:serverList property="department.name" idName="department.id" action="/DepartmentsListAction"
                                     styleClass="filter-long" selectOnly="true" callback="onChangeDepartment"
                                     scriptUrl="have_all=true"/>
                  </td>
                </tr>
                <tr>
                  <td align="right">
                    <bean:message key="GoodsRest.stuffCategory"/>
                  </td>
                  <td>
                    <ctrl:serverList property="stuffCategory.name" idName="stuffCategory.id" action="/StuffCategoriesListAction"
                                     styleClass="filter-long" callback="onChangeStuffCategory" scriptUrl="have_all=true"
                                     filter="filter"/>
                  </td>
                </tr>
                <logic:equal name="GoodsRest" property="goods_on_storage" value="1">
                  <tr>
                    <td align="right">
                      <bean:message key="GoodsRest.purpose"/>
                    </td>
                    <td>
                      <ctrl:serverList property="purpose.name" idName="purpose.id" action="/PurposesListAction"
                                       styleClass="filter-long" selectOnly="true" scriptUrl="have_all=true"/>
                    </td>
                  </tr>
                </logic:equal>
                <tr>
                  <td align="right" nowrap="nowrap">
                    <bean:message key="GoodsRest.onlyTotal"/>
                  </td>
                  <td>
                    <ctrl:checkbox property="onlyTotal" styleClass="checkbox" value="1" onclick="onlyTotalOnClick();"/>&nbsp;&nbsp;&nbsp;&nbsp;
                    <bean:message key="GoodsRest.by_user"/>&nbsp;
                    <ctrl:checkbox property="by_user" styleClass="checkbox" value="1"/>
                  </td>
                </tr>

              </table>
            </td>

            <td valign="top" width="210px">
              <table>
                <tr>
                  <td colspan="2" align="center" class="table-header">
                    <bean:message key="GoodsRest.view_columns"/>
                  </td>
                </tr>
                <tr>
                  <td align="right">
                    <bean:message key="GoodsRest.view_department"/>
                  </td>
                  <td width="15%">
                    <ctrl:checkbox property="view_department" styleClass="checkbox" value="1"/>
                  </td>
                </tr>
                <logic:equal name="GoodsRest" property="goods_on_storage" value="1">
                  <tr>
                    <td align="right">
                      <bean:message key="GoodsRest.view_order_for"/>
                    </td>
                    <td width="15%">
                      <ctrl:checkbox property="view_order_for" styleClass="checkbox" value="1"/>
                    </td>
                  </tr>
                  <tr>
                    <td align="right">
                      <bean:message key="GoodsRest.view_1c_number"/>
                    </td>
                    <td width="15%">
                      <ctrl:checkbox property="view_1c_number" styleClass="checkbox" value="1"/>
                    </td>
                  </tr>
                  <tr>
                    <td align="right">
                      <bean:message key="GoodsRest.view_cost_one_by"/>
                    </td>
                    <td width="15%">
                      <ctrl:checkbox property="view_cost_one_by" styleClass="checkbox" value="1"/>
                    </td>
                  </tr>
                  <tr>
                    <td align="right">
                      <bean:message key="GoodsRest.view_price_list_by"/>
                    </td>
                    <td width="15%">
                      <ctrl:checkbox property="view_price_list_by" styleClass="checkbox" value="1"/>
                    </td>
                  </tr>
                  <tr>
                    <td align="right" nowrap="nowrap">
                      <bean:message key="GoodsRest.view_prc_date"/>
                    </td>
                    <td width="15%" >
                      <ctrl:checkbox property="view_prc_date" styleClass="checkbox" value="1"/>
                    </td>
                  </tr>
                  <tr>
                    <td align="right" nowrap="nowrap">
                      <bean:message key="GoodsRest.view_prc_number"/>
                    </td>
                    <td width="15%" >
                      <ctrl:checkbox property="view_prc_number" styleClass="checkbox" value="1"/>
                    </td>
                  </tr>
                  <tr>
                    <td align="right" nowrap="nowrap">
                      <bean:message key="GoodsRest.view_lpc_count"/>
                    </td>
                    <td width="15%" >
                      <ctrl:checkbox property="view_lpc_count" styleClass="checkbox" value="1"/>
                    </td>
                  </tr>
                </logic:equal>
                <logic:equal name="GoodsRest" property="shipping_goods" value="1">
                  <tr>
                    <td align="right" nowrap="nowrap">
                      <bean:message key="GoodsRest.view_usr_shipping"/>
                    </td>
                    <td width="25%">
                      <ctrl:checkbox property="view_usr_shipping" styleClass="checkbox" value="1"/>
                    </td>
                  </tr>
                  <tr>
                    <td align="right">
                      <bean:message key="GoodsRest.view_debt"/>
                    </td>
                    <td width="25%">
                      <ctrl:checkbox property="view_debt" styleClass="checkbox" value="1"/>
                    </td>
                  </tr>
                  <tr>
                    <td align="right">
                      <bean:message key="GoodsRest.view_purpose"/>
                    </td>
                    <td width="25%">
                      <ctrl:checkbox property="view_purpose" styleClass="checkbox" value="1"/>
                    </td>
                  </tr>
                </logic:equal>
                <tr>
                  <td align="right">
                    <bean:message key="GoodsRest.view_comment"/>
                  </td>
                  <td width="25%">
                    <ctrl:checkbox property="view_comment" styleClass="checkbox" value="1"/>
                  </td>
                </tr>
                <tr>
                  <td align="right">
                    <bean:message key="GoodsRest.view_sums"/>
                  </td>
                  <td width="25%">
                    <ctrl:checkbox property="view_sums" styleClass="checkbox" value="1"/>
                  </td>
                </tr>
              </table>
            </td>

            <td valign="top" width="150px">
              <table>
                <tr>
                  <td colspan="2" align="center" class="table-header">
                    <bean:message key="GoodsRest.order_by"/>
                  </td>
                </tr>
                <tr>
                  <td align="right">
                    <bean:message key="GoodsRest.order_by_name"/>
                  </td>
                  <td width="15%">
                    <ctrl:checkbox property="order_by_name" styleClass="checkbox" value="1" onclick="orderByNameOnClick();"/>
                  </td>
                </tr>
                <tr>
                  <td align="right">
                    <bean:message key="GoodsRest.order_by_stuff_category"/>
                  </td>
                  <td width="15%">
                    <ctrl:checkbox property="order_by_stuff_category" styleClass="checkbox" value="1" onclick="orderByStuffCategoryOnClick();"/>
                  </td>
                </tr>
                <tr>
                  <td align="right">
                    <bean:message key="GoodsRest.order_by_date_receipt"/>
                  </td>
                  <td width="15%">
                    <ctrl:checkbox property="order_by_date_receipt" styleClass="checkbox" value="1" onclick="orderByDateReceiptOnClick();"/>
                  </td>
                </tr>
              </table>
            </td>

          </tr>
        </table>
      </td>
    </tr>

    <tr>
      <td colspan=20>
        <table border="0" width="100%">
          <tr>
            <td width="70%" align="right" colspan=2>
              <ctrl:ubutton type="submit" dispatch="cleanAll" styleClass="width120"><bean:message key="button.clearAll"/></ctrl:ubutton>&nbsp;
              <ctrl:ubutton styleId="generateButton" type="submit" dispatch="generate" styleClass="width120"><bean:message key="button.form"/></ctrl:ubutton>
              <input type=button id="generateButtonExcel"  onclick="generateExcelClick();"  class="width165" value="<bean:message key="button.formExcel"/>">
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
</div>

<%@include file="GoodsRestInMinsk.jsp"%>

</ctrl:form>

<script language="JScript" type="text/javascript">

  var userId = document.getElementById("user.usr_id");
  var user = document.getElementById("user.userFullName");
  var departmentId = document.getElementById("department.id");
  var department = document.getElementById("department.name");
  var stuffCategoryId = document.getElementById("stuffCategory.id");
  var stuffCategory = document.getElementById("stuffCategory.name");

  var dateBegin = document.getElementById("date_begin");
  var haveDateTo = document.getElementById("have_date_to");
  var dateEnd = document.getElementById("date_end");

  var order_by_name = document.getElementById('order_by_name');
  var order_by_stuff_category = document.getElementById('order_by_stuff_category');
  var order_by_date_receipt = document.getElementById('order_by_date_receipt');

  var onlyTotal = document.getElementById('onlyTotal');
  var byUser = document.getElementById('by_user');

  var goods_on_storage = document.getElementById('goods_on_storage');
  var shipping_goods = document.getElementById('shipping_goods');

  var date_endBtn = document.getElementById("date_endBtn");
  var generateButton = document.getElementById("generateButton");
  var generateButtonExcel = document.getElementById("generateButtonExcel");

  enterDate();
  haveDateToOnClick();
  byUserProcess();

  function onChangeUser()
  {
    departmentId.value = '';
    department.value = '';
    stuffCategoryId.value = '';
    stuffCategory.value = '';

    enterDate();
    byUserProcess();
  }

  function onChangeDepartment()
  {
    userId.value = '';
    user.value = '';
    stuffCategoryId.value = '';
    stuffCategory.value = '';

    enterDate();
    byUserProcess();
  }

  function onChangeStuffCategory()
  {
    userId.value = '';
    user.value = '';
    departmentId.value = '';
    department.value = '';

    enterDate();
    byUserProcess();
  }

  function enterDate()
  {
    if (
         dateBegin.value != '' &&
         (
           user.value != '' ||
           department.value != '' ||
           stuffCategory.value != ''
         )
       )
    {
      generateButton.disabled = false;
      generateButtonExcel.disabled = false;
    }
    else
    {
      generateButton.disabled = true;
      generateButtonExcel.disabled = true;
    }
  }

  function haveDateToOnClick()
  {
    if ( haveDateTo.checked )
    {
      dateEnd.disabled = false;
      disableImgButton(date_endBtn, false);
    }
    else
    {
      dateEnd.disabled = true;
      disableImgButton(date_endBtn, true);
      dateEnd.value = '';
    }
  }

  function orderByNameOnClick()
  {
    if ( !order_by_name.checked )
    {
      order_by_name.checked = true;
    }
    else
    {
      order_by_stuff_category.checked = false;
      order_by_date_receipt.checked = false;
    }
  }

  function orderByStuffCategoryOnClick()
  {
    if ( !order_by_stuff_category.checked )
    {
      order_by_stuff_category.checked = true;
    }
    else
    {
      order_by_name.checked = false;
      order_by_date_receipt.checked = false;
    }
  }

  function orderByDateReceiptOnClick()
  {
    if ( !order_by_date_receipt.checked )
    {
      order_by_date_receipt.checked = true;
    }
    else
    {
      order_by_name.checked = false;
      order_by_stuff_category.checked = false;
    }
  }

  function onlyTotalOnClick()
  {
    byUserProcess();
  }

  function byUserProcess()
  {
    if ( onlyTotal.checked && department.value != '' )
    {
      byUser.disabled = false;
    }
    else
    {
      byUser.disabled = true;
      byUser.checked = false;
    }
  }

  function generateExcelClick()
  {
	  document.getElementById('for-insert').innerHTML='<iframe src="<ctrl:rewrite action="/GoodsRestAction" dispatch="generateExcel"/>" style="display:none" />';
  }

  function goodsOnStorageSubmitReload()
  {
    if ( shipping_goods.checked )
    {
      shipping_goods.checked = false;
      submitDispatchForm("cleanAll");
    }

    goods_on_storage.checked = true;
  }

  function shippingGoodsSubmitReload()
  {
    if ( goods_on_storage.checked )
    {
      goods_on_storage.checked = false;
      submitDispatchForm("cleanAll");
    }

    shipping_goods.checked = true;
  }

  var commentId = null;
  var currentCommentTag = null;
  var drpPurpose = null;

  function showComment(obj,idsString) {
    if (!(tt_iState & 0x8)) {
      tt_HideInit();
    }
    var comment = commentTip(obj);
    if (comment != null && comment.length > 0) {
      while ( comment.indexOf('\r\n') != -1 )
      {
       comment = comment.replace('\r\n', '<br>');
      }
    }
    var purpose = purposeTip(obj);
    if (purpose != null && purpose.length > 0) {
      while ( purpose.indexOf('\r\n') != -1 )
      {
       purpose = purpose.replace('\r\n', '<br>');
      }
    }
    var ids = idsString.split(",");
    var res =  "";
    if (ids != "") {
      res +="<tr>"
      for (var idx = 0; idx < ids.length; idx++) {
	      document.getElementById('for-images').innerHTML =
        '<img id="testimg' + ids[idx] + '" src="<ctrl:rewrite action="/GoodsRestAction" dispatch="downloadImage" />&imageId=' + ids[idx] + '">';
        var im = document.getElementById("testimg" + ids[idx]);
        if (im.width > im.height) {
          im.height = 150 * im.height / im.width;
          im.width = 150;
        } else {
          im.width = 150 * im.width / im.height;
          im.height = 150;
        }

        res +='<td width=150 height=150 align=center valign=middle style="background-color:#FEFFD6" ><img  width="' + im.width + '" heigh="' + im.height + '" src="<ctrl:rewrite action="/GoodsRestAction" dispatch="downloadImage" />&imageId=' + ids[idx] + '"></td>';
      }
      res += "</tr>"
    }
    var splitter = '';
    if (purpose != "" && comment != "")
    {
      splitter = '<br><br>';
    }
    res += '<tr><td wrap style="background-color:#FEFFD6" colspan="' + ids.length + 1 + '">' + purpose + splitter + comment + '</td></tr></table>';
    if (ids != "" || purpose != "" || comment != ""){
      var width = 314;
      if (ids.length > 1){
        width = ids.length*150 + 16;
      }
      res = "<table width='" + width + "' border=0 style='background-color:#aaaaaa' cellspacing=1 cellpadding=1>" + res;
      Tip(res, LEFT, false,  PADDING, 0, STICKY, true, CLICKCLOSE, true, DELAY, 1, BGCOLOR, '#FEFFD6', BORDERCOLOR, '#eeeeee');
    }
  }

  function hideComment(obj) {
    if (!(tt_iState & 0x8)) {
      tt_HideInit();
    }
  }

  function setComment(lpc_id, tag){
    currentCommentTag = tag;
    var localCommentArr = currentCommentTag.parentNode.parentNode.children[0].children[0].getAttribute('lpc_comment').split('#');
    drpPurpose = localCommentArr[0];
	  document.getElementById('comment').value = localCommentArr[1];
    commentId = lpc_id;
    TagToTip("comment-form", LEFT,true,WIDTH,350,PADDING,0,STICKY,true,CLICKCLOSE, false,CLOSEBTN, true, EXCLUSIVE, true,BGCOLOR,"#eeeeee",BORDERCOLOR,"#323232",TITLEBGCOLOR,"#323232",CLOSEBTNCOLORS,["#323232","#ffffff","#323232","#ffffff"],DELAY,0);
  }

  function commentTip(obj){
    var localCommentArr = obj.getAttribute('lpc_comment').split('#');
    return localCommentArr[1];
  }

  function purposeTip(obj){
    var localCommentArr = obj.getAttribute('lpc_comment').split('#');
    return localCommentArr[0];
  }

  function saveComment(){
      doAjax({
      url:'<ctrl:rewrite action="/GoodsRestAction" dispatch="saveComment"/>',
      params:{lpc_id:commentId, lpc_comment:document.getElementsByName('comment')[0].value},
      synchronous:true,
      okCallback:function(transport){
        var commentValue = drpPurpose + (drpPurpose == '' ? '' : '<br><br>') + document.getElementsByName('comment')[0].value;
        currentCommentTag.parentNode.parentNode.children[0].children[0].setAttribute('lpc_comment', drpPurpose + '#' + document.getElementsByName('comment')[0].value);
        var commentField = document.getElementById('comment_field_' + commentId);
        if (commentField)
        {
          while ( commentValue.indexOf('\r\n') != -1 )
          {
           commentValue = commentValue.replace('\r\n', '<br>');
          }
          commentField.innerHTML = commentValue;
        }
        tt_HideInit();
      }
    });
  }

  function onChangeDepartmentInRow(arg)
  {
    doAjax({
      url:'<ctrl:rewrite action="/GoodsRestAction" dispatch="saveDepartment"/>',
      params:{lpc_id:arg.data, departmentId:arg?arg.arguments[0]:null, departmentName:arg?arg.arguments[1]:null},
      synchronous:true,
      okCallback:function(transport){
      }
    });
  }

  var scrollableDiv = document.getElementById("goodsRestGrid");

  function rememberScroll()
  {
    if ( scrollableDiv )
    {
      var scroll = scrollableDiv.scrollTop;
      setCookie("rememberScrollY", scroll);
    }
  }

  function goToScroll()
  {
    var scroll = getCookie("rememberScrollY");
    if ( scroll != '' )
    {
      scrollableDiv.scrollTop = parseInt(scroll);
      setCookie("rememberScrollY", "");
    }
  }

  initFunctions.push(goToScroll);
</script>

<div id='for-insert'></div>

<div id='for-images' style="visibility:hidden;position:absolute; z-index:-100; top:0; left:0" ></div>
