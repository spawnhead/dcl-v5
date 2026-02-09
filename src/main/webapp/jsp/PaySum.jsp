<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/html-controls" prefix="ctrl" %>
<%@ taglib uri="/tags/html-grid" prefix="grid" %>

<div style="display:none" id="resultMsg"></div>

<ctrl:form>
  <ctrl:hidden property="ctr_id"/>
  <ctrl:hidden property="cur_id"/>
  <ctrl:hidden property="number"/>
  <ctrl:hidden property="ctr_name"/>
  <ctrl:hidden property="pay_summ_nr"/>
  <ctrl:hidden property="lps_occupied"/>
  
  <table class=formBack align="center">
    <tr>
      <td>
        <table cellspacing="2"  >
          <tr>
            <td><b><ctrl:write name="PaySum" property="ctr_name"/></b></td>
          </tr>
          <tr>
            <td><bean:message key="PaySum.lps_summ_nr"/>&nbsp;&nbsp;&nbsp;<ctrl:write name="PaySum" property="pay_summ_nr"/></td>
          </tr>
          <tr>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td>
              <table cellpadding="0" cellspacing="0">
                <tr>
                  <td width="120px"><bean:message key="PaySum.con_number"/></td>
                  <td>
                    <table cellpadding="0" cellspacing="0">
                      <tr>
                        <td>
                          <ctrl:serverList property="contractNumberWithDate" idName="contract.con_id"
                                             action="/ContractsDepFromContractorListAction"
                                             scriptUrl="ctr_id=$(ctr_id)&cur_id=$(cur_id)" callback="onChangeContract"
                                             selectOnly="true" style="width:230px;"/>
                        </td>
                        <td>
                          &nbsp;<b><span style="color:red" id="idAnnulContract"><ctrl:write name="PaySum" property="contract.annulStr"/></span></b>
                        </td>
                      </tr>
                    </table>
                  </td>
                </tr>
              </table>
            </td>
          </tr>
          <tr>
            <td>
              <table cellpadding="0" cellspacing="0">
                <tr>
                  <td width="120px"><bean:message key="PaySum.spc_number"/><td>
                  <td>
                    <table cellpadding="0" cellspacing="0">
                      <tr>
                        <td>
                          <ctrl:serverList property="specificationNumberWithDate" idName="specification.spc_id"
                                               action="/SpecificationsDepFromContractListAction"
                                               scriptUrl="id=$(contract.con_id)&with_summ=true"
                                               callback="onChangeSpecification" selectOnly="true" style="width:230px;"/>
                        </td>
                        <td>
                          &nbsp;<b><span style="color:red" id="idAnnulSpecification"><ctrl:write name="PaySum" property="specification.annulStr"/></span></b>
                        </td>
                      </tr>
                    </table>
                  </td>
                </tr>
              </table>
            </td>
          </tr>
          <tr>
            <td>
              <table cellpadding="0" cellspacing="0">
                <tr>
                  <td><bean:message key="PaySum.spc_summ_nr"/>&nbsp;<td>
                  <td id="tdSpcSumNR"><ctrl:write name="PaySum" property="specification.spc_summ_nr_formatted"/></td>
                </tr>
              </table>
            </td>
          </tr>
          <tr>
            <td>
              <table cellpadding="0" cellspacing="0">
                <tr>
                  <td width="25%"><bean:message key="PaySum.lps_summ"/></td>
                  <td><ctrl:text property="lps_summ"/></td>
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
              <ctrl:ubutton type="submit" dispatch="process" styleClass="width80" readonly="false">
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
  var contactId = document.getElementById("contract.con_id");
  var specificationId = document.getElementById("specification.spc_id");
  var specificationNumberWithDate = document.getElementById("specificationNumberWithDate");
  var specificationNumberWithDateBtn = document.getElementById("specificationNumberWithDateBtn");
  var sum = document.getElementById("lps_summ");
  var clearSpc = false;

  onChangeContract(null);
  sum.disabled = (specificationId.value == '');

  function onChangeContract(arg)
  {
    var annul = arg?arg.arguments[10]:null;
    if ( contactId.value == '' )
    {
      specificationNumberWithDate.disabled = true;
      disableImgButton(specificationNumberWithDateBtn, true);
      sum.disabled = true;
    }
    else
    {
      specificationNumberWithDate.disabled = false;
      disableImgButton(specificationNumberWithDateBtn, false);
    }

    if ( clearSpc )
    {
      specificationId.value = '';
      specificationNumberWithDate.value = '';
      sum.value = '0';
    }
    clearSpc = true;

    if (annul == '1')
    {
      document.getElementById('idAnnulContract').innerHTML = '<bean:message key="Contract.conAnnul1"/>';
    }
  }

  function onChangeSpecification(arg)
  {
    var annul = arg?arg.arguments[3]:null;
    sum.disabled = (specificationId.value == '');
    if ( specificationId.value != '' )
    {
      doAjax({
        url:'<ctrl:rewrite action="/PaySumAction" dispatch="ajaxChangeSpecification"/>',
        params:{'specificationId':specificationId.value},
        synchronous:true,
        update:'resultMsg',
        okCallback:function()
        {
          var resultStr = document.getElementById('resultMsg').innerHTML;
          var arrayResulr = resultStr.split('|');
          sum.value = arrayResulr[0];
          document.getElementById('tdSpcSumNR').innerHTML = arrayResulr[1];
        }
      });
    }
    if (annul == '1')
    {
      document.getElementById('idAnnulSpecification').innerHTML = '<bean:message key="Specification.spcAnnul"/>';
    }
  }

</script>
