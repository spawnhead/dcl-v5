package net.sam.dcl.form;

import net.sam.dcl.controller.BaseDispatchValidatorForm;

public class SellerForm extends BaseDispatchValidatorForm
{
  String sln_id;
  String sln_name;
  String sln_used_in_order;
  String sln_prefix_for_order;
  String sln_is_resident;

  public String getSln_id()
  {
    return sln_id;
  }

  public void setSln_id(String sln_id)
  {
    this.sln_id = sln_id;
  }

  public String getSln_name()
  {
    return sln_name;
  }

  public void setSln_name(String sln_name)
  {
    this.sln_name = sln_name;
  }

	public String getSln_used_in_order()
	{
		return sln_used_in_order;
	}

	public void setSln_used_in_order(String sln_used_in_order)
	{
		this.sln_used_in_order = sln_used_in_order;
	}

	public String getSln_prefix_for_order()
	{
		return sln_prefix_for_order;
	}

	public void setSln_prefix_for_order(String sln_prefix_for_order)
	{
		this.sln_prefix_for_order = sln_prefix_for_order;
	}

	public String getSln_is_resident()
	{
		return sln_is_resident;
	}

	public void setSln_is_resident(String sln_is_resident)
	{
		this.sln_is_resident = sln_is_resident;
	}
}