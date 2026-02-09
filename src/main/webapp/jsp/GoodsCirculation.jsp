<%@ page import="net.sam.dcl.beans.GoodsCirculation" %>
<%@ page import="net.sam.dcl.beans.GoodsCirculationQuarter" %>
<%@ page import="net.sam.dcl.beans.GoodsCirculationYear" %>
<%@ page import="net.sam.dcl.util.StoreUtil" %>
<%@ page import="net.sam.dcl.beans.GoodsCirculationMonth" %>
<%@ page import="java.util.Date" %>
<%@ page import="net.sam.dcl.util.StringUtil" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>

<ctrl:form styleId="striped-form">
	<div id="filter-form" >

  <table id=filterTbl align="center" border="0" width="660">

    <tr>
      <td colspan=20 align="center">
        <table border="0">
          <tr>
            <td>
              <bean:message key="GoodsCirculation.date"/>
            </td>
            <td>
              <ctrl:date property="date_begin" styleClass="filter-long" afterSelect='checkDatesAndOther' endField="date_end" showHelp="false" onchange="checkDatesAndOther()"/>
            </td>
            <td>
              &nbsp;&nbsp;&nbsp;&nbsp;<bean:message key="GoodsCirculation.date_to"/>
            </td>
            <td>
              <ctrl:date property="date_end" styleClass="filter-long" afterSelect='checkDatesAndOther' startField="date_begin" showHelp="false" onchange="checkDatesAndOther()"/>
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
              <bean:message key="GoodsCirculation.by"/>&nbsp;
              <ctrl:checkbox property="by_quarter" styleClass="checkbox" value="1" onclick="checkDatesAndOther()"/>&nbsp;<bean:message key="GoodsCirculation.by_quarter"/>&nbsp;
              <ctrl:checkbox property="by_month" styleClass="checkbox" value="1" onclick="checkDatesAndOther()"/>&nbsp;<bean:message key="GoodsCirculation.by_month"/>
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
	            <bean:message key="GoodsCirculation.includeWithNoCirculationAndWithRest"/>&nbsp;
	            <ctrl:checkbox property="includeWithNoCirculationAndWithRest" styleClass="checkbox" value="1" onclick="checkIncludeWithNoCirculationAndWithRest()"/>
            </td>
          </tr>
        </table>
      </td>
    </tr>
    <tr>
      <td>
        <table width="100%">
          <tr>
            <td width="170px">
              <bean:message key="GoodsCirculation.produceName"/>
            </td>
            <td>
              <ctrl:text property="produceName" styleClass="filter"/>
            </td>
          </tr>
        </table>
      </td>
    </tr>
    <tr>
      <td>
        <table width="100%">
          <tr>
            <td width="170px">
              <bean:message key="GoodsCirculation.stuffCategory"/>
            </td>
            <td>
              <ctrl:serverList property="stuffCategory.name" idName="stuffCategory.id" action="/StuffCategoriesListAction"
                               styleClass="filter" scriptUrl="have_all=true" filter="filter"/>
            </td>
          </tr>
        </table>
      </td>
    </tr>
    <tr>
      <td>
        <table width="100%">
          <tr>
            <td width="170px">
              <bean:message key="GoodsCirculation.seller"/>
            </td>
            <td>
              <ctrl:serverList property="seller.name" idName="seller.id" action="/SellersListAction"
                               selectOnly="true" styleClass="filter"/>
            </td>
          </tr>
        </table>
      </td>
    </tr>
    <tr>
      <td>
        <table width="100%">
          <tr>
            <td width="170px">
              <bean:message key="GoodsCirculation.contractor"/>
            </td>
            <td>
              <ctrl:serverList property="contractorGoodsCirculation.name" idName="contractorGoodsCirculation.id" action="/ContractorsListAction"
                               styleClass="filter" filter="filter" scriptUrl="have_all=true" callback="onChangeContractor"/>
            </td>
          </tr>
        </table>
      </td>
    </tr>
    <tr>
      <td>
        <table width="100%">
          <tr>
            <td width="170px">
              <bean:message key="GoodsCirculation.by_contractor"/>
            </td>
            <td>
              <ctrl:checkbox property="by_contractor" styleClass="checkbox" value="1"/>&nbsp;
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
	<div class="gridBack" id="scrollableDiv" style="overflow:scroll;width:expression(document.body.clientWidth-15); height:expression(document.body.clientHeight-180)">
  <div style='${GoodsCirculation.grid_width}'>
    <grid:table property="grid" scrollableGrid="false" key="prd_id" nothingWasFoundMsg="msg.howToShowFilter">
      <tr class="locked-header">
        <th class="table-header" colspan="1"></th>
        <th class="table-header" colspan="1"></th>
        <th class="table-header" colspan="1"></th>
        <logic:equal name="GoodsCirculation" property="showContractor" value="true">
          <th class="table-header" colspan="1"></th>
        </logic:equal>
	      <th class="table-header" colspan="1"></th>
	      <%
		      GoodsCirculation goodsCirculation = StoreUtil.getGoodsCirculation(request);
         for (int i = 0; i < goodsCirculation.getGoodsCirculationYears().size(); i++)
         {
           GoodsCirculationYear goodsCirculationYear = goodsCirculation.getGoodsCirculationYears().get(i);
	       %>
	       <th class="table-header" colspan="<%=goodsCirculationYear.getColspanForYear(goodsCirculation.isByQuarter(), goodsCirculation.isByMonth())%>"><%=goodsCirculationYear.getYear()%></th>
	       <%
	         }
	       %>
	      <th class="table-header" colspan="1"></th>
        <th class="table-header" colspan="3"><%=StringUtil.date2appDateString(new Date())%></th>
      </tr>
      <tr class="locked-header">

      <grid:column align="center"><jsp:attribute name="title"><bean:message key="GoodsCirculation.fullName"/></jsp:attribute></grid:column>
      <grid:column align="center"><jsp:attribute name="title"><bean:message key="GoodsCirculation.ctn_number"/></jsp:attribute></grid:column>
      <grid:column align="center"><jsp:attribute name="title"><bean:message key="GoodsCirculation.stuffCategory"/></jsp:attribute></grid:column>
      <logic:equal name="GoodsCirculation" property="showContractor" value="true">
        <grid:column align="center"><jsp:attribute name="title"><bean:message key="GoodsCirculation.contractor"/></jsp:attribute></grid:column>
      </logic:equal>
      <grid:column align="center"><jsp:attribute name="title"><bean:message key="GoodsCirculation.unit"/></jsp:attribute></grid:column>
      <%
        for (int i = 0; i < goodsCirculation.getGoodsCirculationYears().size(); i++)
        {
          GoodsCirculationYear goodsCirculationYear = goodsCirculation.getGoodsCirculationYears().get(i);
          if (goodsCirculation.isByQuarter())
          {
            for (int j = 0; j < goodsCirculationYear.getGoodsCirculationYearQuarters().size(); j++)
            {
              GoodsCirculationQuarter goodsCirculationQuarter = goodsCirculationYear.getGoodsCirculationYearQuarters().get(j);
              if (goodsCirculation.isByMonth())
              {
                for (int k = 0; k < goodsCirculationQuarter.getGoodsCirculationQuarterMonths().size(); k++)
                {
                  GoodsCirculationMonth goodsCirculationMonth = goodsCirculationQuarter.getGoodsCirculationQuarterMonths().get(k);
      %>
      <grid:column align="center"><jsp:attribute name="title"><%=goodsCirculationMonth.getMonthFormatted()%></jsp:attribute></grid:column>
      <%
                }
              }
      %>
      <grid:column align="center"><jsp:attribute name="title"><%=goodsCirculationQuarter.getQuarterFormatted()%></jsp:attribute></grid:column>
      <%
            }
          }
          if (!goodsCirculation.isByQuarter() && goodsCirculation.isByMonth())
          {
            for (int j = 0; j < goodsCirculationYear.getGoodsCirculationYearMonths().size(); j++)
            {
              GoodsCirculationMonth goodsCirculationMonth = goodsCirculationYear.getGoodsCirculationYearMonths().get(j);
      %>
      <grid:column align="center"><jsp:attribute name="title"><%=goodsCirculationMonth.getMonthFormatted()%></jsp:attribute></grid:column>
      <%
            }
          }
        }
      %>
      <grid:column align="center"><jsp:attribute name="title"><bean:message key="GoodsCirculation.totalCount"/></jsp:attribute></grid:column>
      <grid:column align="center"><jsp:attribute name="title"><bean:message key="GoodsCirculation.restInMinsk"/></jsp:attribute></grid:column>
      <grid:column align="center"><jsp:attribute name="title"><bean:message key="GoodsCirculation.restInLithuania"/></jsp:attribute></grid:column>
      <grid:column align="center"><jsp:attribute name="title"><bean:message key="GoodsCirculation.ordInProducer"/></jsp:attribute></grid:column>
      <grid:row>
	      <grid:colLink onclick="rememberScroll()" property="fullName" action="/ProduceMovementForGoodsCirculationAction.do?dispatch=input" type="link" scriptUrl="produce.id=${record.prd_id}" styleClassCheckerId="style-checker"/>
        <grid:colCustom property="ctn_number" styleClassCheckerId="style-checker"/>
        <grid:colCustom property="stf_name" styleClassCheckerId="style-checker"/>
        <logic:equal name="GoodsCirculation" property="showContractor" value="true">
          <grid:colCustom property="contractor" styleClassCheckerId="style-checker"/>
        </logic:equal>
        <grid:colCustom property="unit" styleClassCheckerId="style-checker"/>
          <%
            for (int i = 0; i < goodsCirculation.getGoodsCirculationYears().size(); i++)
            {
              GoodsCirculationYear goodsCirculationYear = goodsCirculation.getGoodsCirculationYears().get(i);
              if (goodsCirculation.isByQuarter())
              {
                for (int j = 0; j < goodsCirculationYear.getGoodsCirculationYearQuarters().size(); j++)
                {
                  GoodsCirculationQuarter goodsCirculationQuarter = goodsCirculationYear.getGoodsCirculationYearQuarters().get(j);
                  if (goodsCirculation.isByMonth())
                  {
                    for (int k = 0; k < goodsCirculationQuarter.getGoodsCirculationQuarterMonths().size(); k++)
                    {
          %>
          <grid:colCustom align="right" styleClassCheckerId="style-checker"><%=goodsCirculation.getMonthForQuarterCount(goodsCirculation.getCurrentLine(), i, j, k)%></grid:colCustom>
          <%
                    }
                  }
          %>
          <grid:colCustom align="right" styleClassCheckerId="style-checker"><%=goodsCirculation.getQuarterCount(goodsCirculation.getCurrentLine(), i, j)%></grid:colCustom>
          <%
                }
              }
              if (!goodsCirculation.isByQuarter() && goodsCirculation.isByMonth())
              {
                for (int j = 0; j < goodsCirculationYear.getGoodsCirculationYearMonths().size(); j++)
                {
          %>
          <grid:colCustom align="right" styleClassCheckerId="style-checker"><%=goodsCirculation.getMonthCount(goodsCirculation.getCurrentLine(), i, j)%></grid:colCustom>
          <%
                }
              }
            }
            
            goodsCirculation.setCurrentLine(goodsCirculation.getCurrentLine() + 1);
          %>
        <grid:colCustom align="right" property="lpsCountFormatted" styleClassCheckerId="style-checker"/>
        <grid:colCustom align="right" property="restInMinskFormatted" styleClassCheckerId="style-checker"/>
        <grid:colCustom align="right" property="restInLithuaniaFormatted" styleClassCheckerId="style-checker"/>
        <grid:colCustom align="right" property="ordInProducerFormatted" styleClassCheckerId="style-checker"/>
      </grid:row>
    </grid:table>
  </div>
  </div>
</ctrl:form>

<script language="JScript" type="text/javascript">
  var dateBegin = document.getElementById("date_begin");
  var dateEnd = document.getElementById("date_end");
  var byQuarter = document.getElementById("by_quarter");
  var byMonth = document.getElementById("by_month");
  var includeWithNoCirculationAndWithRest = document.getElementById("includeWithNoCirculationAndWithRest");
  var contractorId = document.getElementById("contractorGoodsCirculation.id");
  var sellerId = document.getElementById("seller.id");
  var sellerName = document.getElementById("seller.name");
  var sellerNameBtn = document.getElementById("seller.nameBtn");
  var byContractor = document.getElementById("by_contractor");

  var generateButton = document.getElementById("generateButton");
  var generateButtonExcel = document.getElementById("generateButtonExcel");

  checkDatesAndOther();
  checkIncludeWithNoCirculationAndWithRest();

  function checkDatesAndOther()
  {
    var beginFirstPart = dateBegin.value.substr(0, 5);
    var endFirstPart = dateEnd.value.substr(0, 5);

    var firstQuarterBegin = '<bean:message key="GoodsCirculation.firstQuarterBegin"/>';
    var firstQuarterEnd = '<bean:message key="GoodsCirculation.firstQuarterEnd"/>';
    var secondQuarterBegin = '<bean:message key="GoodsCirculation.secondQuarterBegin"/>';
    var secondQuarterEnd = '<bean:message key="GoodsCirculation.secondQuarterEnd"/>';
    var thirdQuarterBegin = '<bean:message key="GoodsCirculation.thirdQuarterBegin"/>';
    var thirdQuarterEnd = '<bean:message key="GoodsCirculation.thirdQuarterEnd"/>';
    var fourthQuarterBegin = '<bean:message key="GoodsCirculation.fourthQuarterBegin"/>';
    var fourthQuarterEnd = '<bean:message key="GoodsCirculation.fourthQuarterEnd"/>';

    if (
            (beginFirstPart != firstQuarterBegin && beginFirstPart != secondQuarterBegin && beginFirstPart != thirdQuarterBegin && beginFirstPart != fourthQuarterBegin) ||
            (endFirstPart != firstQuarterEnd && endFirstPart != secondQuarterEnd && endFirstPart != thirdQuarterEnd && endFirstPart != fourthQuarterEnd)
       )
    {
      byQuarter.checked = false;
      byQuarter.disabled = true;
    }
    else
    {
      byQuarter.disabled = false;  
    }

    if (
         dateBegin.value != '' &&
         dateEnd.value != ''
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

  function checkIncludeWithNoCirculationAndWithRest()
  {
		if (includeWithNoCirculationAndWithRest.checked)
		{
			sellerId.value = '';
			sellerId.disabled = true;
			sellerName.value = '';
			sellerName.disabled = true;
			disableImgButton(sellerNameBtn, true);
			byContractor.disabled = true;
			byContractor.checked = false;
		}
	  else
		{
			sellerId.disabled = false;
			sellerName.disabled = false;
			disableImgButton(sellerNameBtn, false);
			byContractor.disabled = false;
		}
  }

  function onChangeContractor()
  {
    if ( contractorId.value == '-1' || contractorId.value == '' )
    {
      byContractor.disabled = false;
    }
    else
    {
      byContractor.disabled = true;
      byContractor.checked = false;
    }
  }

  function generateExcelClick()
  {
	  document.getElementById('for-insert').innerHTML='<iframe src="<ctrl:rewrite action="/GoodsCirculationAction" dispatch="generateExcel"/>" style="display:none" />';
  }

  var scrollableDiv = document.getElementById("scrollableDiv");

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
