<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>

<ctrl:form readonly="${ClosedRecord.formReadOnly}">
  <ctrl:hidden property="ctc_id"/>
  <ctrl:hidden property="contractor.id"/>
  <ctrl:hidden property="contract.con_id"/>
  <ctrl:hidden property="specification.spc_id"/>
  <ctrl:hidden property="specification.spc_group_delivery"/>
  <ctrl:hidden property="pay_summ"/>
  <ctrl:hidden property="shp_summ"/>
  <ctrl:hidden property="number"/>
  <table class=formBackTop align="center" width="50%">
    <tr>
      <td>
        <table width="100%">
          <tr>
            <td>
              <table width="100%">
                <tr>
                  <td><b><ctrl:write name="ClosedRecord" property="contractor.name"/></b></td>
                </tr>
              </table>
            </td>
          </tr>
          <tr>
            <td>
              <table width="100%">
                <tr>
                  <td><bean:message key="ClosedRecord.con_number"/>&nbsp;<ctrl:write name="ClosedRecord" property="contract.con_number"/>&nbsp;<bean:message key="ClosedRecord.from"/>&nbsp;<ctrl:write name="ClosedRecord" property="contract.con_date_formatted"/></td>
                </tr>
              </table>
            </td>
          </tr>
          <tr>
            <td>
              <table width="100%">
                <tr>
                  <td width="50%"><bean:message key="ClosedRecord.spc_number"/>&nbsp;<ctrl:write name="ClosedRecord" property="specification.spc_number"/>&nbsp;<bean:message key="ClosedRecord.from"/>&nbsp;<ctrl:write name="ClosedRecord" property="specification.spc_date"/></td>
                  <td align="right"><bean:message key="ClosedRecord.spc_summ"/>&nbsp;<ctrl:write name="ClosedRecord" property="specification.spc_summ_formatted"/>&nbsp;<ctrl:write name="ClosedRecord" property="contract.currency.name"/></td>
                </tr>
              </table>
            </td>
          </tr>
          <logic:equal name="ClosedRecord" property="showDeleteMsg" value="true">
            <tr>
              <td>
                <table width="100%">
                  <tr>
                    <span style="color:red"><bean:message key="ClosedRecord.deleteDocsMsg"/></span>
                  </tr>
                </table>
              </td>
            </tr>
          </logic:equal>

          <tr>
            <td>
              <table width="100%">
                <tr>
                  <td>
                    <div class="gridBackNarrow" >
                      <grid:table property="gridResult" key="id" >
                            <logic:notEqual name="ClosedRecord" property="showForGroupDelivery" value="true">
                              <th class="table-header" colspan="3"><bean:message key="ClosedRecordList.shipping"/></th>
                              <th class="table-header" colspan="2"><bean:message key="ClosedRecordList.pay"/></th>
                            </logic:notEqual>
                            <logic:equal name="ClosedRecord" property="showForGroupDelivery" value="true">
                              <th class="table-header" colspan="4"><bean:message key="ClosedRecordList.shipping"/></th>
                              <th class="table-header" colspan="3"><bean:message key="ClosedRecordList.pay"/></th>
                            </logic:equal>
                          </tr>
                          <tr>
                        <logic:equal name="ClosedRecord" property="showForGroupDelivery" value="true">
                          <grid:column />
                        </logic:equal>
                        <grid:column align="center"><jsp:attribute name="title"><bean:message key="ClosedRecordList.shp_date"/></jsp:attribute></grid:column>
                        <grid:column width="20%" align="center"><jsp:attribute name="title"><bean:message key="ClosedRecordList.shp_number"/></jsp:attribute></grid:column>
                        <grid:column width="20%" align="center"><jsp:attribute name="title"><bean:message key="ClosedRecordList.shp_summ"/></jsp:attribute></grid:column>
                        <logic:equal name="ClosedRecord" property="showForGroupDelivery" value="true">
                          <grid:column />
                        </logic:equal>
                        <grid:column width="20%" align="center"><jsp:attribute name="title"><bean:message key="ClosedRecordList.pay_date"/></jsp:attribute></grid:column>
                        <grid:column width="20%" align="center"><jsp:attribute name="title"><bean:message key="ClosedRecordList.pay_summ"/></jsp:attribute></grid:column>

                        <grid:row>
                          <logic:equal name="ClosedRecord" property="showForGroupDelivery" value="true">
                            <grid:colCheckbox  width="1%" property="selected_shipping" result="gridResult" resultProperty="selected_shipping"
                                               useIndexAsPK="true" showCheckerId="show-checker-shipping" styleClassCheckerId="style-checker" type="script" onclick="selectDeleteClick(this); return true;" showWait="false"/>
                          </logic:equal>
                          <grid:colCustom property="shp_date" styleClassCheckerId="style-checker"/>
                          <grid:colCustom width="20%" property="shippingNumberWithOriginal" styleClassCheckerId="style-checker"/>
                          <grid:colCustom align="right" width="20%" property="shp_summ" styleClassCheckerId="style-checker"/>
                          <logic:equal name="ClosedRecord" property="showForGroupDelivery" value="true">
                            <grid:colCheckbox  width="1%" property="selected_payment" result="gridResult" resultProperty="selected_payment"
                                               useIndexAsPK="true" showCheckerId="show-checker-payment" styleClassCheckerId="style-checker" type="script" onclick="selectDeleteClick(this); return true;" showWait="false"/>
                          </logic:equal>
                          <grid:colCustom width="20%" property="pay_date" styleClassCheckerId="style-checker"/>
                          <grid:colCustom align="right" width="20%" property="pay_summ" styleClassCheckerId="style-checker"/>
                        </grid:row>
                      </grid:table>
                    </div>
                  </td>
                </tr>
              </table>
            </td>
          </tr>
        <tr>
          <td colspan="2" align="right">
            <logic:equal name="ClosedRecord" property="showForGroupDelivery" value="true">
              <ctrl:ubutton styleId="deleteButton" type="submit" dispatch="deleteSelected" styleClass="width145" readonly="false">
                <bean:message key="button.deleteSelected"/>
              </ctrl:ubutton>
            </logic:equal>
          </td>
        </tr>
          <tr>
            <td>
              <table width="100%">
                <tr>
                  <td width="40%"><bean:message key="ClosedRecord.lcc_charges"/></td>
                  <td><ctrl:text property="lcc_charges" style="width:230px;"/></td>
                </tr>
              </table>
            </td>
          </tr>
          <tr>
            <td>
              <table width="100%">
                <tr>
                  <td width="40%"><bean:message key="ClosedRecord.lcc_montage"/></td>
                  <td><ctrl:text property="lcc_montage" style="width:230px;"/></td>
                </tr>
              </table>
            </td>
          </tr>
          <tr>
            <td>
              <table width="100%">
                <tr>
                  <td width="40%"><bean:message key="ClosedRecord.lcc_transport"/></td>
                  <td><ctrl:text property="lcc_transport" style="width:230px;" readonly="true"/></td>
                </tr>
              </table>
            </td>
          </tr>
          <tr>
            <td>
              <table width="100%">
                <tr>
                  <td width="40%"><bean:message key="ClosedRecord.lcc_update_sum"/></td>
                  <td><ctrl:text property="lcc_update_sum" style="width:230px;"/></td>
                </tr>
              </table>
            </td>
          </tr>
          <tr>
            <td colspan="2" align="right" class=formSpace>
              &nbsp;
            </td>
          </tr>
          <tr>
            <td>
              <table width="100%">
                <tr>
                  <td><bean:message key="ClosedRecord.managers"/>&nbsp;<ctrl:write name="ClosedRecord" property="managers"/></td>
                </tr>
              </table>
            </td>
          </tr>
          <tr>
            <td>
              <table width="100%">
                <tr>
                  <td><bean:message key="ClosedRecord.products"/>&nbsp;<ctrl:write name="ClosedRecord" property="products"/></td>
                </tr>
              </table>
            </td>
          </tr>
          <tr>
            <td>
              <table width="100%">
                <tr>
                  <td><bean:message key="ClosedRecord.sum_out_nds_eur"/>&nbsp;<ctrl:write name="ClosedRecord" property="sum_out_nds_eur"/></td>
                </tr>
              </table>
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
              <ctrl:ubutton type="submit" dispatch="process" styleClass="width80" >
                <bean:message key="button.save"/>
              </ctrl:ubutton>
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>

</ctrl:form>

<script language="JScript" type="text/javascript">
<logic:equal name="ClosedRecord" property="showForGroupDelivery" value="true">
	var deleteButton = document.getElementById("deleteButton");

	deleteButton.disabled = true;
</logic:equal>

	function selectDeleteClick(obj)
	{
		var prefix = obj.name.substring(0, obj.name.lastIndexOf('.') + 1);
		var ctrlCheckShipping = document.getElementsByName(prefix + "selected_shipping")[0];
		var ctrlCheckPayment = document.getElementsByName(prefix + "selected_payment")[0];
		if (( null != ctrlCheckShipping && ctrlCheckShipping.checked ) ||
						( null != ctrlCheckPayment && ctrlCheckPayment.checked ))
		{
			deleteButton.disabled = false;
		}
	}
</script>

