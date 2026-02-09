package net.sam.dcl.form;

import net.sam.dcl.controller.BaseForm;

/**
 * @author: DG
 * Date: Sep 28, 2005
 * Time: 1:22:06 PM
 */
public class StuffCategoriesListForm extends BaseForm
{
  String filter;
  boolean have_all = false;
  boolean forMontage = false;

  public String getFilter()
  {
    return filter;
  }

  public void setFilter(String filter)
  {
    this.filter = filter;
  }

  public boolean isHave_all()
  {
    return have_all;
  }

  public void setHave_all(boolean have_all)
  {
    this.have_all = have_all;
  }

  public boolean isForMontage()
  {
    return forMontage;
  }

  public void setForMontage(boolean forMontage)
  {
    this.forMontage = forMontage;
  }

  public String getMontage()
  {
    if ( isForMontage() )
    {
      return "1";
    }

    return "0";
  }
}
