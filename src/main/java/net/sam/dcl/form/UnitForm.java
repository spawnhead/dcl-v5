package net.sam.dcl.form;

import net.sam.dcl.controller.BaseDispatchValidatorForm;
import net.sam.dcl.taglib.table.model.impl.HolderImplUsingList;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: DG
 * Date: Aug 6, 2005
 * Time: 5:37:29 PM
 */
public class UnitForm extends BaseDispatchValidatorForm
{
  String unt_id;
  HolderImplUsingList unitgrid = new HolderImplUsingList();
	String is_acc_for_contract;

	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		this.setIs_acc_for_contract("0");
	}

	public String getIs_acc_for_contract() {
		return is_acc_for_contract;
	}

	public void setIs_acc_for_contract(String is_acc_for_contract) {
		this.is_acc_for_contract = is_acc_for_contract;
	}

	public String getUnt_id()
  {
    return unt_id;
  }

  public void setUnt_id(String unt_id)
  {
    this.unt_id = unt_id;
  }

  public HolderImplUsingList getUnitgrid()
  {
    return unitgrid;
  }

  public void setUnitgrid(HolderImplUsingList unitgrid)
  {
    this.unitgrid = unitgrid;
  }

	static public class UnitLanguages
	{
		String unt_id;
		String unt_name;
		String lng_id;
		String lng_name;

		public String getUnt_id()
		{
			return unt_id;
		}

		public void setUnt_id(String unt_id)
		{
			this.unt_id = unt_id;
		}

		public String getUnt_name()
		{
			return unt_name;
		}

		public void setUnt_name(String unt_name)
		{
			this.unt_name = unt_name;
		}

		public String getLng_id()
		{
			return lng_id;
		}

		public void setLng_id(String lng_id)
		{
			this.lng_id = lng_id;
		}

		public String getLng_name()
		{
			return lng_name;
		}

		public void setLng_name(String lng_name)
		{
			this.lng_name = lng_name;
		}
	}
}
