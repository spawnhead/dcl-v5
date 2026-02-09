package net.sam.dcl.form;

import net.sam.dcl.beans.Contractor;
import net.sam.dcl.controller.BaseDispatchValidatorForm;

import java.util.ArrayList;
import java.util.List;

public class ContractorsPrintForm extends BaseDispatchValidatorForm
{
  float printScale;
  List<Contractor> contractors = new ArrayList<Contractor>();

  public float getPrintScale()
  {
    return printScale;
  }

  public void setPrintScale(float printScale)
  {
    this.printScale = printScale;
  }

  public List<Contractor> getContractors()
  {
    return contractors;
  }

  public void setContractors(List<Contractor> contractors)
  {
    this.contractors = contractors;
  }
}