package net.sam.dcl.form;

import net.sam.dcl.controller.BaseDispatchValidatorForm;
import net.sam.dcl.util.StringUtil;

/**
 * @author: DG
 * Date: Aug 6, 2005
 * Time: 5:37:29 PM
 */
public class ProduceCostCustomForm extends BaseDispatchValidatorForm
{
  String lpc_percent;
  String lpc_summ;
  String lpc_summ_allocation;

  public double getLpc_percent()
  {
    return StringUtil.appCurrencyString2double(lpc_percent);
  }


  public String getLpc_percent_formatted()
  {
    return lpc_percent;
  }

  public void setLpc_percent(double lpc_percent)
  {
    this.lpc_percent = StringUtil.double2appCurrencyString(lpc_percent);
  }

  public void setLpc_percent_formatted(String lpc_percent)
  {
    this.lpc_percent = lpc_percent;
  }

  public String getLpc_summ()
  {
    return lpc_summ;
  }

  public void setLpc_summ(String lpc_summ)
  {
    this.lpc_summ = lpc_summ;
  }

  public String getLpc_summ_allocation()
  {
    return lpc_summ_allocation;
  }

  public void setLpc_summ_allocation(String lpc_summ_allocation)
  {
    this.lpc_summ_allocation = lpc_summ_allocation;
  }
}
