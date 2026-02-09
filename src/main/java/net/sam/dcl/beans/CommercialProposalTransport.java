package net.sam.dcl.beans;

import net.sam.dcl.util.StringUtil;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * Date: 14.09.2005
 * Time: 17:17:20
 * To change this template use File | Settings | File Templates.
 */
public class CommercialProposalTransport implements Serializable
{
  String trn_id;
  String cpr_id;
  String number;
  StuffCategory stuffCategory = new StuffCategory();
  double trn_sum;

  public CommercialProposalTransport()
  {
  }

  public CommercialProposalTransport(CommercialProposalTransport commercialProposalTransport)
  {
    trn_id = commercialProposalTransport.getTrn_id();
    cpr_id = commercialProposalTransport.getCpr_id();
    number = commercialProposalTransport.getNumber();
    stuffCategory = new StuffCategory(commercialProposalTransport.getStuffCategory());
    trn_sum = commercialProposalTransport.getTrn_sum();
  }

  public String getTrn_id()
  {
    return trn_id;
  }

  public void setTrn_id(String trn_id)
  {
    this.trn_id = trn_id;
  }

  public String getCpr_id()
  {
    return cpr_id;
  }

  public void setCpr_id(String cpr_id)
  {
    this.cpr_id = cpr_id;
  }

  public String getNumber()
  {
    return number;
  }

  public void setNumber(String number)
  {
    this.number = number;
  }

  public StuffCategory getStuffCategory()
  {
    return stuffCategory;
  }

  public void setStuffCategory(StuffCategory stuffCategory)
  {
    this.stuffCategory = stuffCategory;
  }

  public double getTrn_sum()
  {
    return trn_sum;
  }

  public String getTrn_sum_formatted()
  {
    return StringUtil.double2appCurrencyString(trn_sum);
  }

  public void setTrn_sum(double trn_sum)
  {
    this.trn_sum = trn_sum;
  }

  public void setTrn_sum_formatted(String trn_sum)
  {
    this.trn_sum = StringUtil.appCurrencyString2double(trn_sum);
  }
}