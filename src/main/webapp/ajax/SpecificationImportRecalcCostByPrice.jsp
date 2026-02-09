<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>

<script type="text/javascript" language="JavaScript">
	result = {
		cost:'<ctrl:write name="SpecificationImport" property="gridSpec.row(${SpecificationImport.recordNumberForRecalc}).sip_cost_formatted"/>',
		itogo:'<ctrl:write name="SpecificationImport" property="itogo"/>'
	}
</script>
