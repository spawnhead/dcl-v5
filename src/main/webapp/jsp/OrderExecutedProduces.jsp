<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>

<div style="display:none" id="resultMsg"></div>

<h2>
  <bean:message key="OrderExecutedProduces.title"/>&nbsp;<ctrl:write name="OrderExecutedProduces" property="ord_number"/>&nbsp;<bean:message key="msg.common.from_only"/>&nbsp;<ctrl:write name="OrderExecutedProduces" property="ord_date"/>
</h2>

<ctrl:form>
  <table class=formBackTop align="center" width="100%">
    <tr>
      <td >
        <table cellspacing="2" width="100%">
          <tr>
            <td id="executedProduces" valign="top">
              <script type="text/javascript" language="JScript">
                  doAjax({
                    synchronous:true,
                    url:'<ctrl:rewrite action="/OrderExecutedProducesAction" dispatch="ajaxOrderExecutedProducesGrid"/>',
                    update:'executedProduces'
                  });
              </script>
            </td>
          </tr>
          <tr>
            <td colspan="2" align="right" class=formSpace>
              &nbsp;
            </td>
          </tr>
          <tr>
            <td colspan="2" align="right">
              <ctrl:ubutton type="link" dispatch="back" styleClass="width80" readonly="false">
                <bean:message key="button.cancel"/>
              </ctrl:ubutton>
              <logic:equal name="OrderExecutedProduces" property="disableEdit" value="">
                <input id='buttonSave' type='button' onclick='processClick()' class='width80' value='<bean:message key="button.save"/>'/>
              </logic:equal>
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
</ctrl:form>

<script language="JScript" type="text/javascript">
  function processClick()
  {
    submitDispatchForm("process");
  }

  function newExecutedDateChanged(obj)
  {
    doAjax({
      url:'<ctrl:rewrite action="/OrderExecutedProducesAction" dispatch="ajaxNewExecutedDateChanged"/>',
      synchronous:true,
      params:{'newExecutedDate':obj.value},
      update:'resultMsg',
      okCallback:function()
      {
        var resultStr = document.getElementById('resultMsg').innerHTML;
        if (resultStr != '')
        {
          alert(resultStr);
        }
      }
    });

    doAjax({
      synchronous:true,
      url:'<ctrl:rewrite action="/OrderExecutedProducesAction" dispatch="ajaxOrderExecutedProducesGrid"/>',
      update:'executedProduces'
    });
  }

  function changeValue(forDate, number, obj)
  {
    doAjax({
      url:'<ctrl:rewrite action="/OrderExecutedProducesAction" dispatch="ajaxChangeValue"/>',
      synchronous:true,
      params:{'forDate':forDate,'number':number,'newValue':obj.value},
      update:'resultMsg',
      okCallback:function()
      {
        var resultStr = document.getElementById('resultMsg').innerHTML;
        if (resultStr != '')
        {
          alert(resultStr);
        }
      }
    });

    doAjax({
      synchronous:true,
      url:'<ctrl:rewrite action="/OrderExecutedProducesAction" dispatch="ajaxOrderExecutedProducesGrid"/>',
      update:'executedProduces'
    });
  }

  function deleteColumn(columnIdx)
  {
    doAjax({
      url:'<ctrl:rewrite action="/OrderExecutedProducesAction" dispatch="ajaxDeleteColumn"/>',
      synchronous:true,
      params:{'columnIdx':columnIdx}
    });

    doAjax({
      synchronous:true,
      url:'<ctrl:rewrite action="/OrderExecutedProducesAction" dispatch="ajaxOrderExecutedProducesGrid"/>',
      update:'executedProduces'
    });
  }

  function setRowSelectedStile(id)
  {
	  var row = document.getElementById('executedRow' + id);
	  row.style.background = '#dddddd';
  }

  function setRowUnselectedStile(id)
  {
	  var row = document.getElementById('executedRow' + id);
	  row.style.background = '#eeeeee';
  }
</script>