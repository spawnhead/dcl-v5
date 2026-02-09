<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>

<ctrl:form>
	<ctrl:hidden property="sln_id"/>
	<table class=formBack align="center">
		<tr>
			<td>
				<table cellspacing="2">
					<tr>
						<td><bean:message key="Seller.sln_is_resident"/></td>
						<td><ctrl:checkbox property="sln_is_resident" styleClass="checkbox" value="1"/></td>
					</tr>
					<tr>
						<td><bean:message key="Seller.sln_name"/></td>
						<td><ctrl:text property="sln_name"/></td>
					</tr>
					<tr>
						<td><bean:message key="Seller.sln_used_in_order"/></td>
						<td><ctrl:checkbox property="sln_used_in_order" styleClass="checkbox" value="1"/></td>
					</tr>
					<tr>
						<td><bean:message key="Seller.sln_prefix_for_order"/></td>
						<td><ctrl:text property="sln_prefix_for_order"/></td>
					</tr>
					<tr>
						<td colspan="2" align="right" class=formSpace>
							&nbsp;
						</td>
					</tr>
					<tr>
						<td colspan="2" align="right">
							<ctrl:ubutton type="link" actionForward="back" styleClass="width80">
								<bean:message key="button.cancel"/>
							</ctrl:ubutton>
							<ctrl:ubutton type="submit" dispatch="process" styleClass="width80">
								<bean:message key="button.save"/>
							</ctrl:ubutton>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>

</ctrl:form>
