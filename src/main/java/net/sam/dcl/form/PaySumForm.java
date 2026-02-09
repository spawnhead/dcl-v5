package net.sam.dcl.form;

import net.sam.dcl.beans.Contract;
import net.sam.dcl.beans.Specification;
import net.sam.dcl.controller.BaseDispatchValidatorForm;
import net.sam.dcl.util.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author: DG
 * Date: Aug 6, 2005
 * Time: 5:37:29 PM
 */
public class PaySumForm extends BaseDispatchValidatorForm
{
  protected static Log log = LogFactory.getLog(PaySumForm.class);
  String number;

  String lps_summ;

  String ctr_id;
  String ctr_name;
  String cur_id;
  String pay_summ_nr;
  Contract contract = new Contract();
  Specification specification = new Specification();
  String lps_occupied;

  public String getNumber()
  {
    return number;
  }

  public void setNumber(String number)
  {
    this.number = number;
  }

  public String getLps_summ()
  {
    return lps_summ;
  }

  public void setLps_summ(String lps_summ)
  {
    this.lps_summ = lps_summ;
  }

  public String getCtr_id()
  {
    return ctr_id;
  }

  public void setCtr_id(String ctr_id)
  {
    this.ctr_id = ctr_id;
  }

  public String getCtr_name()
  {
    return ctr_name;
  }

  public void setCtr_name(String ctr_name)
  {
    this.ctr_name = ctr_name;
  }

  public String getCur_id()
  {
    return cur_id;
  }

  public void setCur_id(String cur_id)
  {
    this.cur_id = cur_id;
  }

  public String getPay_summ_nr()
  {
    return pay_summ_nr;
  }

  public void setPay_summ_nr(String pay_summ_nr)
  {
    this.pay_summ_nr = pay_summ_nr;
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

  public String getContractNumberWithDate()
  {
    if ( StringUtil.isEmpty(getContract().getCon_number()) )
    {
      return "";
    }

    return getContract().getNumberWithDate();
  }

  public String getSpecificationNumberWithDate()
  {
    if ( StringUtil.isEmpty(getSpecification().getSpc_number()) )
    {
      return "";
    }

    return getSpecification().getNumberWithDate();
  }
}
