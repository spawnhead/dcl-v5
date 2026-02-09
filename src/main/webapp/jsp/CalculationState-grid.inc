<div>
	<grid:table property="gridCalcState" scrollableGrid="false" key="key_id" >
		</tr><tr class="locked-header">
			<th class="table-header" colspan="${CalculationState.top_contract}" ><bean:message key="CalculationState.top_contract"/></th>
			<logic:notEqual name="CalculationState" property="isDebit" value="1">
				<logic:equal name="CalculationState" property="view_expiration" value="1">
					<th class="table-header" colspan="2"><bean:message key="CalculationState.top_expiration"/></th>
				</logic:equal>
			</logic:notEqual>
			<logic:equal name="CalculationState" property="isDebit" value="1">
				<logic:equal name="CalculationState" property="view_expiration" value="1">
					<th class="table-header"></th>
				</logic:equal>
			</logic:equal>
			<logic:equal name="CalculationState" property="view_complaint" value="1">
				<th class="table-header"></th>
			</logic:equal>
			<logic:equal name="CalculationState" property="view_comment" value="1">
				<th class="table-header"></th>
			</logic:equal>
			<th class="table-header" colspan="3"><bean:message key="CalculationState.top_shipping"/></th>
			<th class="table-header" colspan="2"><bean:message key="CalculationState.top_payment"/></th>
			<logic:equal name="CalculationState" property="isDebit" value="1">
				 <th class="table-header"></th>
			</logic:equal>
			<logic:equal name="CalculationState" property="view_manager" value="1">
				<th class="table-header"></th>
			</logic:equal>
			<logic:equal name="CalculationState" property="view_stuff_category" value="1">
				<th class="table-header"></th>
			</logic:equal>
		</tr>
		<tr class="locked-header">

    <logic:equal name="CalculationState" property="showConNumber" value="true">
		  <grid:column align="center"><jsp:attribute name="title"><bean:message key="CalculationState.con_number_date"/></jsp:attribute></grid:column>
    </logic:equal>
		<grid:column align="center"><jsp:attribute name="title"><bean:message key="CalculationState.spc_number_date"/></jsp:attribute></grid:column>
		<grid:column align="center"><jsp:attribute name="title"><bean:message key="CalculationState.spc_summ"/></jsp:attribute></grid:column>
		<logic:notEqual name="CalculationState" property="selectedCurrency" value="true">
			<grid:column align="center"><jsp:attribute name="title"><bean:message key="CalculationState.con_currency"/></jsp:attribute></grid:column>
		</logic:notEqual>
		<logic:equal name="CalculationState" property="view_pay_cond" value="1">
			<grid:column align="center"><jsp:attribute name="title"><bean:message key="CalculationState.spc_add_pay_cond"/></jsp:attribute></grid:column>
		</logic:equal>
		<logic:notEqual name="CalculationState" property="isDebit" value="1">
			<logic:equal name="CalculationState" property="view_delivery_cond" value="1">
				<grid:column align="center"><jsp:attribute name="title"><bean:message key="CalculationState.spc_delivery_cond"/></jsp:attribute></grid:column>
				<grid:column align="center"><jsp:attribute name="title"><bean:message key="CalculationState.spc_delivery_date"/></jsp:attribute></grid:column>
			</logic:equal>
		</logic:notEqual>
		<logic:equal name="CalculationState" property="isDebit" value="1">
			<logic:equal name="CalculationState" property="view_expiration" value="1">
				<grid:column title=""/>
			</logic:equal>
		</logic:equal>
		<logic:notEqual name="CalculationState" property="isDebit" value="1">
			<logic:equal name="CalculationState" property="view_expiration" value="1">
				<grid:column align="center"><jsp:attribute name="title"><bean:message key="CalculationState.shp_expiration"/></jsp:attribute></grid:column>
				<grid:column align="center"><jsp:attribute name="title"><bean:message key="CalculationState.pay_expiration"/></jsp:attribute></grid:column>
			</logic:equal>
		</logic:notEqual>
		<logic:equal name="CalculationState" property="view_complaint" value="1">
			<grid:column align="center"><jsp:attribute name="title"><bean:message key="CalculationState.complaint"/></jsp:attribute></grid:column>
		</logic:equal>
		<logic:equal name="CalculationState" property="view_comment" value="1">
			<grid:column align="center"><jsp:attribute name="title"><bean:message key="CalculationState.comment"/></jsp:attribute></grid:column>
		</logic:equal>
		<grid:column align="center"><jsp:attribute name="title"><bean:message key="CalculationState.shp_date"/></jsp:attribute></grid:column>
		<grid:column align="center"><jsp:attribute name="title"><bean:message key="CalculationState.shp_number"/></jsp:attribute></grid:column>
		<grid:column align="center"><jsp:attribute name="title"><bean:message key="CalculationState.shp_summ"/></jsp:attribute></grid:column>
		<grid:column align="center"><jsp:attribute name="title"><bean:message key="CalculationState.pay_date"/></jsp:attribute></grid:column>
		<grid:column align="center"><jsp:attribute name="title"><bean:message key="CalculationState.pay_summ"/></jsp:attribute></grid:column>
		<logic:equal name="CalculationState" property="isDebit" value="1">
			<grid:column align="center"><jsp:attribute name="title"><bean:message key="CalculationState.shp_saldo"/></jsp:attribute></grid:column>
		</logic:equal>
		<logic:equal name="CalculationState" property="view_manager" value="1">
			<grid:column align="center"><jsp:attribute name="title"><bean:message key="CalculationState.managers"/></jsp:attribute></grid:column>
		</logic:equal>
		<logic:equal name="CalculationState" property="view_stuff_category" value="1">
			<grid:column align="center"><jsp:attribute name="title"><bean:message key="CalculationState.stuff_categories"/></jsp:attribute></grid:column>
		</logic:equal>
		<grid:row rowParserId="row-parser">
      <logic:equal name="CalculationState" property="showConNumber" value="true">
			  <grid:colLink onclick="rememberScroll()" type="submit" dispatch="contract" scriptUrl="con_id=${record.con_id}" property="con_number_date" styleClassCheckerId="style-checker" readonlyCheckerId="linkReadonlyContract"/>
      </logic:equal>
      <grid:colLink onclick="rememberScroll()" type="submit" dispatch="contract" scriptUrl="con_id=${record.con_id}" property="spc_number_date" styleClassCheckerId="style-checker" readonlyCheckerId="linkReadonlyContract"/>
			<grid:colCustom align="right" property="spc_summ_formatted" styleClassCheckerId="style-checker"/>
      <logic:notEqual name="CalculationState" property="selectedCurrency" value="true">
				<grid:colCustom property="con_currency" styleClassCheckerId="style-checker"/>
			</logic:notEqual>
			<logic:equal name="CalculationState" property="view_pay_cond" value="1">
				<grid:colCustom property="spc_add_pay_cond" styleClassCheckerId="style-checker"/>
			</logic:equal>
			<logic:notEqual name="CalculationState" property="isDebit" value="1">
				<logic:equal name="CalculationState" property="view_delivery_cond" value="1">
					<grid:colCustom property="spc_delivery_cond" styleClassCheckerId="style-checker"/>
					<grid:colCustom property="spc_delivery_date_formatted" styleClassCheckerId="style-checker"/>
				</logic:equal>
			</logic:notEqual>
			<logic:notEqual name="CalculationState" property="isDebit" value="1">
				<logic:equal name="CalculationState" property="view_expiration" value="1">
					<grid:colCustom align="right" property="shp_expiration" styleClassCheckerId="style-checker"/>
					<grid:colCustom align="right" property="pay_expiration_formatted" styleClassCheckerId="style-checker"/>
				</logic:equal>
			</logic:notEqual>
			<logic:equal name="CalculationState" property="isDebit" value="1">
				<logic:equal name="CalculationState" property="view_expiration" value="1">
					<grid:colCustom align="right" property="shp_expiration" styleClassCheckerId="style-checker"/>
				</logic:equal>
			</logic:equal>
			<logic:equal name="CalculationState" property="view_complaint" value="1">
				<grid:colCustom property="complaint" styleClassCheckerId="style-checker"/>
			</logic:equal>
			<logic:equal name="CalculationState" property="view_comment" value="1">
				<grid:colCustom property="comment" styleClassCheckerId="style-checker"/>
			</logic:equal>
			<grid:colLink onclick="rememberScroll()" type="submit" dispatch="shipping" scriptUrl="shp_id=${record.shp_id}" property="shp_date_formatted" styleClassCheckerId="${record.style_shp_closed}" readonlyCheckerId="linkReadonlyShipping"/>
			<grid:colLink onclick="rememberScroll()" type="submit" dispatch="shipping" scriptUrl="shp_id=${record.shp_id}" property="shpNumberFormatted" styleClassCheckerId="${record.style_shp_closed}" readonlyCheckerId="linkReadonlyShipping" addText="${record.shpNumberAddText}"/>
			<grid:colLink onclick="rememberScroll()" type="submit" dispatch="shipping" scriptUrl="shp_id=${record.shp_id}" align="right" property="shp_summ_formatted" styleClassCheckerId="${record.style_shp}" readonlyCheckerId="linkReadonlyShipping"/>
      <grid:colLink onclick="rememberScroll()" type="submit" dispatch="payment" scriptUrl="pay_id=${record.pay_id}" property="pay_date_formatted" styleClassCheckerId="${record.style_pay_closed}" readonlyCheckerId="linkReadonlyShipping"/>
      <grid:colLink onclick="rememberScroll()" type="submit" dispatch="payment" scriptUrl="pay_id=${record.pay_id}" align="right" property="pay_summ_formatted" styleClassCheckerId="${record.style_pay}" readonlyCheckerId="linkReadonlyShipping"/>
			<logic:equal name="CalculationState" property="isDebit" value="1">
				<grid:colCustom align="right" property="shp_saldo_formatted" styleClassCheckerId="style-checker"/>
			</logic:equal>
			<logic:equal name="CalculationState" property="view_manager" value="1">
				<grid:colCustom property="managersFormatted" styleClassCheckerId="style-checker"/>
			</logic:equal>
			<logic:equal name="CalculationState" property="view_stuff_category" value="1">
				<grid:colCustom property="stuff_categories" styleClassCheckerId="style-checker"/>
			</logic:equal>
		</grid:row>

	</grid:table>
</div>
