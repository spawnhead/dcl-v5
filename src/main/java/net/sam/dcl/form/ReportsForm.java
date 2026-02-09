package net.sam.dcl.form;

import net.sam.dcl.controller.BaseDispatchValidatorForm;

/**
 * @author: DG
 * Date: Aug 6, 2005
 * Time: 5:37:29 PM
 */
public class ReportsForm extends BaseDispatchValidatorForm
{
  String showOkCancel;
  String msg;

  public String getShowOkCancel()
  {
    return showOkCancel;
  }

  public void setShowOkCancel(String showOkCancel)
  {
    this.showOkCancel = showOkCancel;
  }

  public String getMsg()
  {
    return msg;
  }

  public void setMsg(String msg)
  {
    this.msg = msg;
  }
}
