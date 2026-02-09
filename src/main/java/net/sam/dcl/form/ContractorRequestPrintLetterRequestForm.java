package net.sam.dcl.form;

import net.sam.dcl.controller.BaseDispatchValidatorForm;
import net.sam.dcl.beans.*;

/**
 * @author: DG
 * Date: Aug 6, 2005
 * Time: 5:37:29 PM
 */
public class ContractorRequestPrintLetterRequestForm extends BaseDispatchValidatorForm
{
  Blank blank;
  Contractor contractor = new Contractor();
  Contract contract = new Contract();
  String crq_equipment;
  String ctn_number;
  String stf_name;
  User chief = new User();

  float printScale;

  public Blank getBlank()
  {
    return blank;
  }

  public void setBlank(Blank blank)
  {
    this.blank = blank;
  }

  public Contractor getContractor()
  {
    return contractor;
  }

  public void setContractor(Contractor contractor)
  {
    this.contractor = contractor;
  }

  public Contract getContract()
  {
    return contract;
  }

  public void setContract(Contract contract)
  {
    this.contract = contract;
  }

  public String getCrq_equipment()
  {
    return crq_equipment;
  }

  public void setCrq_equipment(String crq_equipment)
  {
    this.crq_equipment = crq_equipment;
  }

  public String getCtn_number()
  {
    return ctn_number;
  }

  public void setCtn_number(String ctn_number)
  {
    this.ctn_number = ctn_number;
  }

  public String getStf_name()
  {
    return stf_name;
  }

  public void setStf_name(String stf_name)
  {
    this.stf_name = stf_name;
  }

  public User getChief()
  {
    return chief;
  }

  public void setChief(User chief)
  {
    this.chief = chief;
  }

  public float getPrintScale()
  {
    return printScale;
  }

  public void setPrintScale(float printScale)
  {
    this.printScale = printScale;
  }
}