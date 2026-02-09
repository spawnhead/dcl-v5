<%@ page import="org.apache.struts.taglib.html.Constants" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>
<script type="text/javascript" language="JavaScript">
	if (catTree.getSelected()) {
		var node = new WebFXTreeItem('<bean:write name="Nomenclature" property="category_name"/>');
		node.cat_id = '<bean:write name="Nomenclature" property="cat_id"/>';
		catTree.getSelected().add(node);
		catTree.getSelected().expand();
		node.select();
	}
</script> 