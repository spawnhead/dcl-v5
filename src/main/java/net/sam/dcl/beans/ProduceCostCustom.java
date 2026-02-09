package net.sam.dcl.beans;

import net.sam.dcl.util.StringUtil;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * Date: 14.09.2005
 * Time: 17:17:20
 * To change this template use File | Settings | File Templates.
 */
public class ProduceCostCustom implements Serializable
{
  String prc_id;
  double lpc_percent;
  double lpc_summ;
  double lpc_summ_allocation;

  public ProduceCostCustom()
  {
  }

  public ProduceCostCustom(ProduceCostCustom pcc)
  {
    prc_id = pcc.getPrc_id();
    lpc_percent = pcc.getLpc_percent();
    lpc_summ = pcc.getLpc_summ();
    lpc_summ_allocation = pcc.getLpc_summ_allocation();
  }

  public String getPrc_id()
  {
    return prc_id;
  }

  public void setPrc_id(String prc_id)
  {
    this.prc_id = prc_id;
  }

  public double getLpc_percent()
  {
    return lpc_percent;
  }

  public String getLpc_percent_formatted()
  {
    return StringUtil.double2appCurrencyString(lpc_percent);
  }

  public void setLpc_percent(double lpc_percent)
  {
    this.lpc_percent = lpc_percent;
  }

  public double getLpc_summ()
  {
    return lpc_summ;
  }

  public String getLpc_summ_formatted()
  {
    return StringUtil.double2appCurrencyString(lpc_summ);
  }

  public void setLpc_summ(double lpc_summ)
  {
    this.lpc_summ = lpc_summ;
  }

  public double getLpc_summ_allocation()
  {
    return lpc_summ_allocation;
  }

  public String getLpc_summ_allocation_formatted()
  {
    return StringUtil.double2appCurrencyString(lpc_summ_allocation);
  }

  public void setLpc_summ_allocation(double lpc_summ_allocation)
  {
    this.lpc_summ_allocation = lpc_summ_allocation;
  }
}
