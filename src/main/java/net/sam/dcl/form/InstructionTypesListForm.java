package net.sam.dcl.form;

import net.sam.dcl.controller.BaseForm;

public class InstructionTypesListForm extends BaseForm
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