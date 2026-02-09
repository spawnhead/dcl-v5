package net.sam.dcl.beans;

import net.sam.dcl.util.StringUtil;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * Date: 14.09.2005
 * Time: 17:17:20
 * To change this template use File | Settings | File Templates.
 */
public class PaySum implements Serializable
{
  String number;

  String lps_id;
  String pay_id;
  double lps_summ;
  double lps_summ_eur;
  double lps_summ_out_nds;
  Contract contract = new Contract();
  Specification specification = new Specification();
  String lps_occupied; 

  public PaySum()
  {
  }

  public PaySum(String lps_id)
  {
    this.lps_id = lps_id;
  }

  public PaySum(PaySum paySum)
  {
    lps_id = paySum.getLps_id();
    pay_id = paySum.getPay_id();
    number = paySum.getNumber();
    lps_summ = paySum.getLps_summ();
    lps_summ_eur = paySum.getLps_summ_eur();
    lps_summ_out_nds = paySum.getLps_summ_out_nds();
    contract = paySum.getContract();
    specification = new Specification(paySum.getSpecification());
    lps_occupied = paySum.getLps_occupied();
  }

  public String getLps_id()
  {
    return lps_id;
  }

  public void setLps_id(String lps_id)
  {
    this.lps_id = lps_id;
  }

  public String getPay_id()
  {
    return pay_id;
  }

  public void setPay_id(String pay_id)
  {
    this.pay_id = pay_id;
  }

  public String getNumber()
  {
    return number;
  }

  public void setNumber(String number)
  {
    this.number = number;
  }

  public double getLps_summ()
  {
    return lps_summ;
  }

  public String getLps_summ_formatted()
  {
    return StringUtil.double2appCurrencyString(lps_summ);
  }

  public void setLps_summ(double lps_summ)
  {
    this.lps_summ = lps_summ;
  }

  public double getLps_summ_eur()
  {
    return lps_summ_eur;
  }

  public String getLps_summ_eur_formatted()
  {
    return StringUtil.double2appCurrencyString(lps_summ_eur);
  }

  public void setLps_summ_eur(double lps_summ_eur)
  {
    this.lps_summ_eur = lps_summ_eur;
  }

  public double getLps_summ_out_nds()
  {
    return lps_summ_out_nds;
  }

  public void setLps_summ_out_nds(double lps_summ_out_nds)
  {
    this.lps_summ_out_nds = lps_summ_out_nds;
  }

  public Contract getContract()
  {
    return contract;
  }

  public void setContract(Contract contract)
  {
    this.contract = contract;
  }

  public Specification getSpecification()
  {
    return specification;
  }

  public void setSpecification(Specification specification)
  {
    this.specification = specification;
  }

  public String getLps_occupied()
  {
    return lps_occupied;
  }

  public void setLps_occupied(String lps_occupied)
  {
    this.lps_occupied = lps_occupied;
  }
}
