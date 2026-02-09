<%@ page import="net.sam.dcl.beans.OrderExecutedDateLine" %>
<%@ page import="net.sam.dcl.beans.OrderExecutedLine" %>
<%@ page import="net.sam.dcl.form.OrderExecutedProducesForm" %>
<%@ page import="net.sam.dcl.util.StringUtil" %>
<%@ page import="net.sam.dcl.beans.OrderExecutedProduces" %>
<%@ page import="net.sam.dcl.util.StoreUtil" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>

<table id="OrderExecutedProducesGrid" width="100%" cellSpacing="1" cellPadding="2" border="0" class="grid">
  <tr>
    <th class="table-header" rowspan="2"><bean:message key="OrderExecutedProduces.number"/></th>
    <th class="table-header" rowspan="2"><bean:message key="OrderExecutedProduces.fullName"/></th>
    <th class="table-header" rowspan="2"><bean:message key="OrderExecutedProduces.catalog_num"/></th>
    <th class="table-header" rowspan="2"><bean:message key="OrderExecutedProduces.opr_count"/></th>
    <th class="table-header" colspan='<ctrl:write name="OrderExecutedProduces" property="colspan"/>'><bean:message key="OrderExecutedProduces.opr_count_executed"/></th>
  </tr>
  <tr class="locked-header1">
    <%
      OrderExecutedProduces orderExecutedProduces = StoreUtil.getOrderExecutedProduces(request);
      for (int i = 0; i < orderExecutedProduces.getOrderExecutedDate().size(); i++)
      {
        if (i == orderExecutedProduces.getOrderExecutedDate().size() - 1 && orderExecutedProduces.getDisableEdit().equals(""))
        {
    %>
      <th class="table-header" width="180px">
        <div class='control-out control-nc' onclick='editControlComment(this,"OrderExecutedProduces.newExecutedDate",false)' __comment=''>
          <table cellSpacing=0 cellPadding=0 border=0 class='date-box'>
            <tr>
              <td valign=top bgcolor="#639ACE">
                <input type="text" name="newExecutedDate" value="<%=((OrderExecutedDateLine)orderExecutedProduces.getOrderExecutedDate().get(i)).getDate()%>" onchange="newExecutedDateChanged(this)" style="width:130px;" <%if (!orderExecutedProduces.getDisableEdit().equals("")) {%>disabled<%}%>>
              </td>
              <td valign="middle" style="padding-top:1px;" bgcolor="#639ACE">
                <div id='newExecutedDateBtn' class='select-btn_enabled' tabindex=-1 onclick = 'return __showCalendar("newExecutedDateFrm","newExecutedDate","newExecutedDateBtn","trusted/css.do",newExecutedDateChanged,"","","false", null, event);'/>
              </td>
            </tr>
          </table>
          <iframe id='newExecutedDateFrm' style='display:none;' FRAMEBORDER=0 SCROLLING=no class='frame-calendar' APPLICATION='yes'></iframe></div>
      </th>
    <%
        }
        else
        {
    %>
      <th class="table-header"><%=((OrderExecutedDateLine)orderExecutedProduces.getOrderExecutedDate().get(i)).getDate()%><%if (((OrderExecutedDateLine)orderExecutedProduces.getOrderExecutedDate().get(i)).isShowDeleteColumn()) {%>&nbsp;&nbsp;<img title='<bean:message key="tooltip.OrderExecutedProduces.deleteColumn"/>' src='<ctrl:rewrite page="/images/sub.gif"/>' onclick='deleteColumn(<%=i%>)' style='cursor:pointer;'><%}%></th>
    <%
        }
      }
    %>
    <th class="table-header"><bean:message key="OrderExecutedProduces.all"/></th>
  </tr>
  <tr class="locked-header1"></tr>

  <%
      for (int i = 0; i < orderExecutedProduces.getOrderExecuted().size(); i++)
      {
        OrderExecutedLine orderExecutedLine = (OrderExecutedLine)orderExecutedProduces.getOrderExecuted().get(i);
    %>
    <tr class="grid-row not-even" id="executedRow<%=orderExecutedLine.getNumber()%>" onmouseover="setRowSelectedStile('<%=orderExecutedLine.getNumber()%>')" onmouseout="setRowUnselectedStile('<%=orderExecutedLine.getNumber()%>')">
        <td width="25px" class="" align="center">
          <%=orderExecutedLine.getNumber()%>
        </td>
        <td width="250px" class="">
          <%=orderExecutedLine.getFullName()%>
        </td>
        <td width="100px" class="">
          <%=orderExecutedLine.getCatalog_num()%>
        </td>
        <td width="60px" align="right">
          <%=StringUtil.double2appCurrencyString(orderExecutedLine.getOpr_count())%>
        </td>
        <%
        for (int indDate = 0; indDate < orderExecutedLine.getOrderExecutedByDate().size(); indDate++)
        {
          if (indDate == orderExecutedLine.getOrderExecutedByDate().size() - 1 && orderExecutedProduces.getDisableEdit().equals(""))
          {
        %>
        <td align="right">
          <%=(String)orderExecutedLine.getOrderExecutedByDate().get(indDate)%>
        </td>
        <%
          }
          else
          {
        %>
        <td align="right">
          <input type="text" <%if (!orderExecutedProduces.getDisableEdit().equals("")) {%>disabled<%}%>
                 name="executedOnDate<%=((OrderExecutedDateLine)orderExecutedProduces.getOrderExecutedDate().get(indDate)).getDate()%><%=orderExecutedLine.getNumber()%>"
                 value="<%=(String)orderExecutedLine.getOrderExecutedByDate().get(indDate)%>" style="width:50px;text-align:right"
                 onchange="changeValue('<%=((OrderExecutedDateLine)orderExecutedProduces.getOrderExecutedDate().get(indDate)).getDate()%>', '<%=orderExecutedLine.getNumber()%>', this)"
                 onfocus="setRowSelectedStile('<%=orderExecutedLine.getNumber()%>')" onblur="setRowUnselectedStile('<%=orderExecutedLine.getNumber()%>')">
        </td>
        <%
          }
        }
        %>
        <td align="right">
          <%=StringUtil.double2appCurrencyString(orderExecutedLine.getOpr_count_executed())%>
        </td>
    </tr>
    <%
      }
    %>
</table>
