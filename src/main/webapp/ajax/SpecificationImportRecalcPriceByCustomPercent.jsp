<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>

<script type="text/javascript" language="JavaScript">

	result = {
		price:'<ctrl:write name="SpecificationImport" property="gridSpec.row(${SpecificationImport.recordNumberForRecalc}).sip_price_formatted"/>',
		cost:'<ctrl:write name="SpecificationImport" property="gridSpec.row(${SpecificationImport.recordNumberForRecalc}).sip_cost_formatted"/>',
		itogo:'<ctrl:write name="SpecificationImport" property="itogo"/>'
	}
</script>
