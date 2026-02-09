	<div class="gridBack" id="goodsRestGrid" style="overflow:scroll;width:expression(document.body.clientWidth-15); height:expression(document.body.clientHeight-180)">
  <div style='${GoodsRest.grid_width}'>
    <grid:table property="grid" scrollableGrid="false" key="lpc_id" nothingWasFoundMsg="msg.howToShowFilter">
    </tr><tr class="locked-header">
				<logic:notEqual name="GoodsRest" property="onlyTotal" value="1">
          <th class="table-header" colspan="${GoodsRest.top_view_count}"></th>
        </logic:notEqual>
        <logic:equal name="GoodsRest" property="onlyTotal" value="1">
          <logic:equal name="GoodsRest" property="departmentShow" value="1">
            <th class="table-header"></th>
          </logic:equal>
        </logic:equal>
        <logic:equal name="GoodsRest" property="goods_on_storage" value="1">
          <logic:equal name="GoodsRest" property="view_sums" value="1">
            <th class="table-header" colspan="5"><bean:message key="GoodsRest.top_periods"/></th>
          </logic:equal>
        </logic:equal>
        <logic:equal name="GoodsRest" property="shipping_goods" value="1">
          <th class="table-header" colspan="5"><bean:message key="GoodsRest.top_periods_shipping"/></th>
        </logic:equal>
        <logic:equal name="GoodsRest" property="have_date_to" value="1">
          <logic:notEqual name="GoodsRest" property="onlyTotal" value="1">
            <th class="table-header"></th>
          </logic:notEqual>
          <logic:equal name="GoodsRest" property="goods_on_storage" value="1">
            <logic:equal name="GoodsRest" property="view_sums" value="1">
              <th class="table-header" colspan="5"><bean:message key="GoodsRest.top_periods"/></th>
            </logic:equal>
          </logic:equal>
          <logic:equal name="GoodsRest" property="shipping_goods" value="1">
            <th class="table-header" colspan="5"><bean:message key="GoodsRest.top_periods_shipping"/></th>
          </logic:equal>
        </logic:equal>
        <logic:notEqual name="GoodsRest" property="onlyTotal" value="1">
          <logic:notEqual name="GoodsRest" property="have_date_to" value="1">
            <logic:equal name="GoodsRest" property="shipping_goods" value="1">
              <logic:equal name="GoodsRest" property="view_debt" value="1">
                <th class="table-header" colspan="2"><bean:message key="GoodsRest.debt"/></th>
              </logic:equal>
             </logic:equal>
          </logic:notEqual>
        </logic:notEqual>
        <logic:notEqual name="GoodsRest" property="onlyTotal" value="1">
          <logic:equal name="GoodsRest" property="shipping_goods" value="1">
            <logic:equal name="GoodsRest" property="view_purpose" value="1">
              <th class="table-header"></th>
            </logic:equal>
           </logic:equal>
          <logic:equal name="GoodsRest" property="goods_on_storage" value="1">
            <logic:equal name="GoodsRest" property="purpose.id" value="-1">
              <th class="table-header"></th>
            </logic:equal>
           </logic:equal>
        </logic:notEqual>
        <logic:notEqual name="GoodsRest" property="onlyTotal" value="1">
          <logic:equal name="GoodsRest" property="goods_on_storage" value="1">
            <logic:equal name="GoodsRest" property="view_1c_number" value="1">
              <th class="table-header"></th>
            </logic:equal>
          </logic:equal>
        </logic:notEqual>
        <logic:notEqual name="GoodsRest" property="onlyTotal" value="1">
          <logic:equal name="GoodsRest" property="goods_on_storage" value="1">
            <logic:equal name="GoodsRest" property="view_cost_one_by" value="1">
              <th class="table-header"></th>
            </logic:equal>
            <logic:equal name="GoodsRest" property="view_price_list_by" value="1">
              <th class="table-header"></th>
            </logic:equal>
          </logic:equal>
        </logic:notEqual>
        <logic:equal name="GoodsRest" property="userShow" value="1">
          <th class="table-header"></th>
        </logic:equal>
        <logic:notEqual name="GoodsRest" property="onlyTotal" value="1">
          <logic:equal name="GoodsRest" property="view_comment" value="1">
            <th class="table-header"></th>
          </logic:equal>
          <th class="table-header"></th>
        </logic:notEqual>
      </tr>
      <tr class="locked-header">

      <logic:notEqual name="GoodsRest" property="onlyTotal" value="1">
        <grid:column align="center"><jsp:attribute name="title"><bean:message key="GoodsRest.fullName"/></jsp:attribute></grid:column>
        <grid:column align="center"><jsp:attribute name="title"><bean:message key="GoodsRest.ctn_number"/></jsp:attribute></grid:column>
      </logic:notEqual>
      <logic:equal name="GoodsRest" property="stuffCategoryShow" value="1">
        <grid:column align="center"><jsp:attribute name="title"><bean:message key="GoodsRest.stf_name"/></jsp:attribute></grid:column>
      </logic:equal>
      <logic:notEqual name="GoodsRest" property="onlyTotal" value="1">
        <logic:equal name="GoodsRest" property="goods_on_storage" value="1">
          <logic:equal name="GoodsRest" property="view_order_for" value="1">
            <grid:column align="center"><jsp:attribute name="title"><bean:message key="GoodsRest.order_for"/></jsp:attribute></grid:column>
            <grid:column align="center"><jsp:attribute name="title"><bean:message key="GoodsRest.con_spc"/></jsp:attribute></grid:column>
          </logic:equal>
        </logic:equal>
      </logic:notEqual>
      <logic:equal name="GoodsRest" property="departmentShow" value="1">
        <grid:column align="center"><jsp:attribute name="title"><bean:message key="GoodsRest.dep_name"/></jsp:attribute></grid:column>
      </logic:equal>
      <logic:notEqual name="GoodsRest" property="onlyTotal" value="1">
        <grid:column align="center"><jsp:attribute name="title"><bean:message key="GoodsRest.unit"/></jsp:attribute></grid:column>
        <logic:equal name="GoodsRest" property="goods_on_storage" value="1">
          <logic:equal name="GoodsRest" property="view_prc_date" value="1">
            <grid:column align="center"><jsp:attribute name="title"><bean:message key="GoodsRest.prc_date"/></jsp:attribute></grid:column>
          </logic:equal>
          <logic:equal name="GoodsRest" property="view_prc_number" value="1">
            <grid:column align="center"><jsp:attribute name="title"><bean:message key="GoodsRest.prc_number"/></jsp:attribute></grid:column>
          </logic:equal>
        </logic:equal>
        <logic:equal name="GoodsRest" property="shipping_goods" value="1">
          <grid:column align="center"><jsp:attribute name="title"><bean:message key="GoodsRest.shp_date"/></jsp:attribute></grid:column>
          <grid:column align="center"><jsp:attribute name="title"><bean:message key="GoodsRest.ctr_name"/></jsp:attribute></grid:column>
          <logic:equal name="GoodsRest" property="view_usr_shipping" value="1">
            <grid:column align="center"><jsp:attribute name="title"><bean:message key="GoodsRest.usr_shipping"/></jsp:attribute></grid:column>
          </logic:equal>
        </logic:equal>
        <logic:equal name="GoodsRest" property="view_lpc_count" value="1">
          <grid:column align="center"><jsp:attribute name="title"><bean:message key="GoodsRest.lpc_count"/></jsp:attribute></grid:column>
        </logic:equal>
        <grid:column align="center"><jsp:attribute name="title"><bean:message key="GoodsRest.lpc_count_free"/></jsp:attribute></grid:column>
      </logic:notEqual>
      <logic:equal name="GoodsRest" property="goods_on_storage" value="1">
        <logic:equal name="GoodsRest" property="view_sums" value="1">
          <grid:column align="center"><jsp:attribute name="title"><bean:message key="GoodsRest.less_3"/></jsp:attribute></grid:column>
          <grid:column align="center"><jsp:attribute name="title"><bean:message key="GoodsRest.month_3_6"/></jsp:attribute></grid:column>
          <grid:column align="center"><jsp:attribute name="title"><bean:message key="GoodsRest.month_6_9"/></jsp:attribute></grid:column>
          <grid:column align="center"><jsp:attribute name="title"><bean:message key="GoodsRest.month_9_12"/></jsp:attribute></grid:column>
          <grid:column align="center"><jsp:attribute name="title"><bean:message key="GoodsRest.more_12_month"/></jsp:attribute></grid:column>
        </logic:equal>
      </logic:equal>
      <logic:equal name="GoodsRest" property="shipping_goods" value="1">
        <grid:column align="center"><jsp:attribute name="title"><bean:message key="GoodsRest.less_1"/></jsp:attribute></grid:column>
        <grid:column align="center"><jsp:attribute name="title"><bean:message key="GoodsRest.month_1_2"/></jsp:attribute></grid:column>
        <grid:column align="center"><jsp:attribute name="title"><bean:message key="GoodsRest.month_2_3"/></jsp:attribute></grid:column>
        <grid:column align="center"><jsp:attribute name="title"><bean:message key="GoodsRest.month_3_6"/></jsp:attribute></grid:column>
        <grid:column align="center"><jsp:attribute name="title"><bean:message key="GoodsRest.more_6_month"/></jsp:attribute></grid:column>
      </logic:equal>
      <logic:equal name="GoodsRest" property="have_date_to" value="1">
        <logic:notEqual name="GoodsRest" property="onlyTotal" value="1">
          <grid:column align="center"><jsp:attribute name="title"><bean:message key="GoodsRest.lpc_count_free"/></jsp:attribute></grid:column>
        </logic:notEqual>
        <logic:equal name="GoodsRest" property="goods_on_storage" value="1">
          <logic:equal name="GoodsRest" property="view_sums" value="1">
            <grid:column align="center"><jsp:attribute name="title"><bean:message key="GoodsRest.less_3"/></jsp:attribute></grid:column>
            <grid:column align="center"><jsp:attribute name="title"><bean:message key="GoodsRest.month_3_6"/></jsp:attribute></grid:column>
            <grid:column align="center"><jsp:attribute name="title"><bean:message key="GoodsRest.month_6_9"/></jsp:attribute></grid:column>
            <grid:column align="center"><jsp:attribute name="title"><bean:message key="GoodsRest.month_9_12"/></jsp:attribute></grid:column>
            <grid:column align="center"><jsp:attribute name="title"><bean:message key="GoodsRest.more_12_month"/></jsp:attribute></grid:column>
          </logic:equal>
        </logic:equal>
        <logic:equal name="GoodsRest" property="shipping_goods" value="1">
          <grid:column align="center"><jsp:attribute name="title"><bean:message key="GoodsRest.less_1"/></jsp:attribute></grid:column>
          <grid:column align="center"><jsp:attribute name="title"><bean:message key="GoodsRest.month_1_2"/></jsp:attribute></grid:column>
          <grid:column align="center"><jsp:attribute name="title"><bean:message key="GoodsRest.month_2_3"/></jsp:attribute></grid:column>
          <grid:column align="center"><jsp:attribute name="title"><bean:message key="GoodsRest.month_3_6"/></jsp:attribute></grid:column>
          <grid:column align="center"><jsp:attribute name="title"><bean:message key="GoodsRest.more_6_month"/></jsp:attribute></grid:column>
        </logic:equal>
      </logic:equal>
      <logic:notEqual name="GoodsRest" property="onlyTotal" value="1">
        <logic:notEqual name="GoodsRest" property="have_date_to" value="1">
          <logic:equal name="GoodsRest" property="shipping_goods" value="1">
            <logic:equal name="GoodsRest" property="view_debt" value="1">
              <grid:column align="center"><jsp:attribute name="title"><bean:message key="GoodsRest.debt_summ"/></jsp:attribute></grid:column>
              <grid:column align="center"><jsp:attribute name="title"><bean:message key="GoodsRest.debt_currency"/></jsp:attribute></grid:column>
            </logic:equal>
           </logic:equal>
        </logic:notEqual>
      </logic:notEqual>
      <logic:notEqual name="GoodsRest" property="onlyTotal" value="1">
        <logic:equal name="GoodsRest" property="shipping_goods" value="1">
          <logic:equal name="GoodsRest" property="view_purpose" value="1">
            <grid:column align="center"><jsp:attribute name="title"><bean:message key="GoodsRest.purpose"/></jsp:attribute></grid:column>
          </logic:equal>
         </logic:equal>
        <logic:equal name="GoodsRest" property="goods_on_storage" value="1">
          <logic:equal name="GoodsRest" property="purpose.id" value="-1">
            <grid:column align="center"><jsp:attribute name="title"><bean:message key="GoodsRest.purpose"/></jsp:attribute></grid:column>
          </logic:equal>
         </logic:equal>
      </logic:notEqual>
      <logic:notEqual name="GoodsRest" property="onlyTotal" value="1">
        <logic:equal name="GoodsRest" property="goods_on_storage" value="1">
          <logic:equal name="GoodsRest" property="view_1c_number" value="1">
            <grid:column align="center"><jsp:attribute name="title"><bean:message key="GoodsRest.lpc_1c_number"/></jsp:attribute></grid:column>
          </logic:equal>
        </logic:equal>
      </logic:notEqual>
      <logic:notEqual name="GoodsRest" property="onlyTotal" value="1">
        <logic:equal name="GoodsRest" property="goods_on_storage" value="1">
          <logic:equal name="GoodsRest" property="view_cost_one_by" value="1">
            <grid:column align="center"><jsp:attribute name="title"><bean:message key="GoodsRest.lpc_cost_one_by"/></jsp:attribute></grid:column>
          </logic:equal>
          <logic:equal name="GoodsRest" property="view_price_list_by" value="1">
            <grid:column align="center"><jsp:attribute name="title"><bean:message key="GoodsRest.lpc_price_list_by"/></jsp:attribute></grid:column>
          </logic:equal>
        </logic:equal>
      </logic:notEqual>
      <logic:equal name="GoodsRest" property="userShow" value="1">
        <grid:column align="center"><jsp:attribute name="title"><bean:message key="GoodsRest.usr_name"/></jsp:attribute></grid:column>
      </logic:equal>
      <logic:notEqual name="GoodsRest" property="onlyTotal" value="1">
        <logic:equal name="GoodsRest" property="view_comment" value="1">
          <grid:column align="center"><jsp:attribute name="title"><bean:message key="GoodsRest.lpc_comment"/></jsp:attribute></grid:column>
        </logic:equal>
        <grid:column align="center"/>
      </logic:notEqual>

      <grid:row >
        <logic:notEqual name="GoodsRest" property="onlyTotal" value="1">
<span lpc_comment="${record.commentForSplit}" onmouseover='showComment(this,"${record.imagesIdsAsString}")' onmouseout="hideComment(this)"/>
          <grid:colLink onclick="rememberScroll()" property="fullName" action="/ProduceMovementForGoodsRestAction.do?dispatch=input" type="link" scriptUrl="produce.id=${record.prd_id}" showCheckerId="show-count-sum" styleClassCheckerId="style-checker"/>
          <grid:colCustom property="ctn_number" styleClassCheckerId="style-checker"/>
        </logic:notEqual>
        <logic:equal name="GoodsRest" property="stuffCategoryShow" value="1">
          <grid:colCustom property="stf_name" styleClassCheckerId="style-checker"/>
        </logic:equal>
        <logic:notEqual name="GoodsRest" property="onlyTotal" value="1">
          <logic:equal name="GoodsRest" property="goods_on_storage" value="1">
            <logic:equal name="GoodsRest" property="view_order_for" value="1">
              <grid:colCustom property="order_for" styleClassCheckerId="style-checker"/>
              <grid:colCustom property="con_spc" styleClassCheckerId="style-checker"/>
            </logic:equal>
          </logic:equal>
        </logic:notEqual>
        <logic:equal name="GoodsRest" property="departmentShow" value="1">
          <logic:equal name="GoodsRest" property="canEditDepartment" value="false">
            <grid:colCustom property="dep_name" styleClassCheckerId="style-checker"/>
          </logic:equal>
          <logic:equal name="GoodsRest" property="canEditDepartment" value="true">
            <grid:colServerList property="dep_name" idName="dep_id" result="grid" resultProperty="dep_name"
                                useIndexAsPK="true"  callback="onChangeDepartmentInRow" data="${record.lpc_id}"
                                action="/DepartmentsListAction" selectOnly="true" showCheckerId="show-count-sum" styleClassCheckerId="style-checker"/>
          </logic:equal>
        </logic:equal>
        <logic:notEqual name="GoodsRest" property="onlyTotal" value="1">
          <grid:colCustom property="unit" styleClassCheckerId="style-checker"/>
          <logic:equal name="GoodsRest" property="goods_on_storage" value="1">
            <logic:equal name="GoodsRest" property="view_prc_date" value="1">
              <grid:colCustom property="prc_date_formatted" styleClassCheckerId="style-checker"/>
            </logic:equal>
            <logic:equal name="GoodsRest" property="view_prc_number" value="1">
              <grid:colCustom property="prc_number" styleClassCheckerId="style-checker"/>
            </logic:equal>
          </logic:equal>
          <logic:equal name="GoodsRest" property="shipping_goods" value="1">
            <grid:colCustom property="shp_date_formatted" styleClassCheckerId="style-checker"/>
            <grid:colCustom property="ctr_name" styleClassCheckerId="style-checker"/>
            <logic:equal name="GoodsRest" property="view_usr_shipping" value="1">
              <grid:colCustom property="usr_shipping" styleClassCheckerId="style-checker"/>
            </logic:equal>
          </logic:equal>
          <logic:equal name="GoodsRest" property="view_lpc_count" value="1">
            <grid:colCustom align="right" property="lpc_count_formatted" styleClassCheckerId="style-checker" showCheckerId="show-count-sum"/>
          </logic:equal>
          <grid:colCustom align="right" property="lpc_count_free_formatted" styleClassCheckerId="style-checker" showCheckerId="show-count-sum"/>
        </logic:notEqual>
        <logic:equal name="GoodsRest" property="goods_on_storage" value="1">
          <logic:equal name="GoodsRest" property="view_sums" value="1">
            <grid:colCustom align="right" property="less_3_month_formatted" styleClassCheckerId="style-checker"/>
            <grid:colCustom align="right" property="month_3_6_formatted" styleClassCheckerId="style-checker"/>
            <grid:colCustom align="right" property="month_6_9_formatted" styleClassCheckerId="style-checker"/>
            <grid:colCustom align="right" property="month_9_12_formatted" styleClassCheckerId="style-checker"/>
            <grid:colCustom align="right" property="more_12_month_formatted" styleClassCheckerId="style-checker"/>
          </logic:equal>
        </logic:equal>
        <logic:equal name="GoodsRest" property="shipping_goods" value="1">
          <grid:colCustom align="right" property="less_3_month_formatted" styleClassCheckerId="style-checker"/>
          <grid:colCustom align="right" property="month_3_6_formatted" styleClassCheckerId="style-checker"/>
          <grid:colCustom align="right" property="month_6_9_formatted" styleClassCheckerId="style-checker"/>
          <grid:colCustom align="right" property="month_9_12_formatted" styleClassCheckerId="style-checker"/>
          <grid:colCustom align="right" property="more_12_month_formatted" styleClassCheckerId="style-checker"/>
        </logic:equal>
        <logic:equal name="GoodsRest" property="have_date_to" value="1">
          <logic:notEqual name="GoodsRest" property="onlyTotal" value="1">
            <grid:colCustom align="right" property="lpc_count_free_to_formatted" styleClassCheckerId="style-checker" showCheckerId="show-count-sum"/>
          </logic:notEqual>
          <logic:equal name="GoodsRest" property="goods_on_storage" value="1">
            <logic:equal name="GoodsRest" property="view_sums" value="1">
              <grid:colCustom align="right" property="less_3_month_to_formatted" styleClassCheckerId="style-checker"/>
              <grid:colCustom align="right" property="month_3_6_to_formatted" styleClassCheckerId="style-checker"/>
              <grid:colCustom align="right" property="month_6_9_to_formatted" styleClassCheckerId="style-checker"/>
              <grid:colCustom align="right" property="month_9_12_to_formatted" styleClassCheckerId="style-checker"/>
              <grid:colCustom align="right" property="more_12_month_to_formatted" styleClassCheckerId="style-checker"/>
            </logic:equal>
          </logic:equal>
          <logic:equal name="GoodsRest" property="shipping_goods" value="1">
            <grid:colCustom align="right" property="less_3_month_to_formatted" styleClassCheckerId="style-checker"/>
            <grid:colCustom align="right" property="month_3_6_to_formatted" styleClassCheckerId="style-checker"/>
            <grid:colCustom align="right" property="month_6_9_to_formatted" styleClassCheckerId="style-checker"/>
            <grid:colCustom align="right" property="month_9_12_to_formatted" styleClassCheckerId="style-checker"/>
            <grid:colCustom align="right" property="more_12_month_to_formatted" styleClassCheckerId="style-checker"/>
          </logic:equal>
        </logic:equal>
        <logic:notEqual name="GoodsRest" property="onlyTotal" value="1">
          <logic:notEqual name="GoodsRest" property="have_date_to" value="1">
            <logic:equal name="GoodsRest" property="shipping_goods" value="1">
              <logic:equal name="GoodsRest" property="view_debt" value="1">
                <grid:colCustom align="right" property="debt_summ_formatted" styleClassCheckerId="style-checker"/>
                <grid:colCustom property="debt_currency" styleClassCheckerId="style-checker"/>
              </logic:equal>
             </logic:equal>
          </logic:notEqual>
        </logic:notEqual>
        <logic:notEqual name="GoodsRest" property="onlyTotal" value="1">
          <logic:equal name="GoodsRest" property="shipping_goods" value="1">
            <logic:equal name="GoodsRest" property="view_purpose" value="1">
              <grid:colCustom property="purpose" styleClassCheckerId="style-checker"/>
            </logic:equal>
           </logic:equal>
          <logic:equal name="GoodsRest" property="goods_on_storage" value="1">
            <logic:equal name="GoodsRest" property="purpose.id" value="-1">
              <grid:colCustom property="purpose" styleClassCheckerId="style-checker"/>
            </logic:equal>
           </logic:equal>
        </logic:notEqual>
        <logic:notEqual name="GoodsRest" property="onlyTotal" value="1">
          <logic:equal name="GoodsRest" property="goods_on_storage" value="1">
            <logic:equal name="GoodsRest" property="view_1c_number" value="1">
              <grid:colCustom property="lpc_1c_number" styleClassCheckerId="style-checker"/>
            </logic:equal>
          </logic:equal>
        </logic:notEqual>
        <logic:notEqual name="GoodsRest" property="onlyTotal" value="1">
          <logic:equal name="GoodsRest" property="goods_on_storage" value="1">
            <logic:equal name="GoodsRest" property="view_cost_one_by" value="1">
              <grid:colCustom align="right" property="lpc_cost_one_by_formatted" styleClassCheckerId="style-checker" showCheckerId="show-count-sum"/>
            </logic:equal>
            <logic:equal name="GoodsRest" property="view_price_list_by" value="1">
              <grid:colCustom align="right" property="lpc_price_list_by_formatted" styleClassCheckerId="style-checker" showCheckerId="show-count-sum"/>
            </logic:equal>
          </logic:equal>
        </logic:notEqual>
        <logic:equal name="GoodsRest" property="userShow" value="1">
          <grid:colCustom property="usr_name" styleClassCheckerId="style-checker"/>
        </logic:equal>
        <logic:notEqual name="GoodsRest" property="onlyTotal" value="1">
          <logic:equal name="GoodsRest" property="view_comment" value="1">
            <grid:colCustom property="comment" styleClassCheckerId="style-checker" styleId="comment_field_${record.lpc_id}"/>
          </logic:equal>
 </span>
          <grid:colEdit type="script" readonlyCheckerId="commentEditReadOnly" showCheckerId="show-count-sum"
                        styleClassCheckerId="style-checker" tooltip="tooltip.GoodsRest.editComment"
                        onclick='setComment("${record.lpc_id}", this);' showWait="false"/>
        </logic:notEqual>

      </grid:row>
    </grid:table>
  </div>
  </div>

  <div id="comment-form" style="display:none;" >
    <div style="padding:2px">
      <textarea name="comment" style="width:346px" rows=10>${record.stf_name}</textarea><br>
      <div align="right"><input type="button"  onclick="saveComment()" class="width80" value="<bean:message key="button.save"/>" style="text-align:right"/></div>
    </div>
  </div>
