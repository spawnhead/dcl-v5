package net.sam.dcl.form;

import net.sam.dcl.controller.BaseForm;

import java.util.ArrayList;
import java.util.List;

public class SellersListForm extends BaseForm
{
  boolean have_all = false;
	boolean forOrder = false;

	List list = new ArrayList();

  public boolean isHave_all()
  {
    return have_all;
  }

  public void setHave_all(boolean have_all)
  {
    this.have_all = have_all;
  }

	public boolean isForOrder()
	{
		return forOrder;
	}

	public void setForOrder(boolean forOrder)
	{
		this.forOrder = forOrder;
	}

	public List getList()
	{
		return list;
	}

	public void setList(List list)
	{
		this.list = list;
	}
}