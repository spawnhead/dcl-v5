package net.sam.dcl.form;

import net.sam.dcl.controller.BaseForm;

/**
 * @author: DG
 * Date: Sep 28, 2005
 * Time: 1:22:06 PM
 */
public class UnitsListForm extends BaseForm
{
  boolean have_all = false;

  public boolean isHave_all()
  {
    return have_all;
  }

  public void setHave_all(boolean have_all)
  {
    this.have_all = have_all;
  }
}
