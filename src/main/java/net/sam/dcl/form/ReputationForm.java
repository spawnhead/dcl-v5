package net.sam.dcl.form;

import net.sam.dcl.controller.BaseDispatchValidatorForm;
import net.sam.dcl.beans.NumberInList;

/**
 * @author: DG
 * Date: Aug 6, 2005
 * Time: 5:37:29 PM
 */
public class ReputationForm extends BaseDispatchValidatorForm
{
  String rpt_id;
  NumberInList number = new NumberInList();
  String rpt_description;
  String rpt_default_in_ctc;

  public String getRpt_id()
  {
    return rpt_id;
  }

  public void setRpt_id(String rpt_id)
  {
    this.rpt_id = rpt_id;
  }

  public NumberInList getNumber()
  {
    return number;
  }

  public void setNumber(NumberInList number)
  {
    this.number = number;
  }

  public String getRpt_description()
  {
    return rpt_description;
  }

  public void setRpt_description(String rpt_description)
  {
    this.rpt_description = rpt_description;
  }

  public String getRpt_default_in_ctc()
  {
    return rpt_default_in_ctc;
  }

  public void setRpt_default_in_ctc(String rpt_default_in_ctc)
  {
    this.rpt_default_in_ctc = rpt_default_in_ctc;
  }
}
