package net.sam.dcl.form;

import net.sam.dcl.controller.BaseDispatchValidatorForm;

/**
 * @author: DG
 * Date: Aug 6, 2005
 * Time: 5:37:29 PM
 */
public class CurrencyForm extends BaseDispatchValidatorForm
{
  String cur_id;
  String cur_name;
  String cur_no_round;

  public String getCur_id()
  {
    return cur_id;
  }

  public void setCur_id(String cur_id)
  {
    this.cur_id = cur_id;
  }

  public String getCur_name()
  {
    return cur_name;
  }

  public void setCur_name(String cur_name)
  {
    this.cur_name = cur_name;
  }

  public String getCur_no_round()
  {
    return cur_no_round;
  }

  public void setCur_no_round(String cur_no_round)
  {
    this.cur_no_round = cur_no_round;
  }
}
