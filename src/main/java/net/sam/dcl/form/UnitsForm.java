package net.sam.dcl.form;

import net.sam.dcl.controller.BaseForm;
import net.sam.dcl.taglib.table.model.impl.HolderImplUsingList;

/**
 * @author: DG
 * Date: Aug 6, 2005
 * Time: 5:37:29 PM
 */
public class UnitsForm extends BaseForm
{
  HolderImplUsingList grid = new HolderImplUsingList();

  public HolderImplUsingList getGrid()
  {
    return grid;
  }

  public void setGrid(HolderImplUsingList grid)
  {
    this.grid = grid;
  }

  static public class Unit
  {
    String unt_id;
    String unt_name_ru;
    String unt_name_en;
    String unt_name_de;
		boolean is_acceptable_for_cpr;

    public String getUnt_id()
    {
      return unt_id;
    }

    public void setUnt_id(String unt_id)
    {
      this.unt_id = unt_id;
    }

    public String getUnt_name_ru()
    {
      return unt_name_ru;
    }

    public void setUnt_name_ru(String unt_name_ru)
    {
      this.unt_name_ru = unt_name_ru;
    }

    public String getUnt_name_en()
    {
      return unt_name_en;
    }

    public void setUnt_name_en(String unt_name_en)
    {
      this.unt_name_en = unt_name_en;
    }

    public String getUnt_name_de()
    {
      return unt_name_de;
    }

    public void setUnt_name_de(String unt_name_de)
    {
      this.unt_name_de = unt_name_de;
    }

		public boolean isIs_acceptable_for_cpr() {
			return is_acceptable_for_cpr;
		}

		public void setIs_acceptable_for_cpr(boolean is_acceptable_for_cpr) {
			this.is_acceptable_for_cpr = is_acceptable_for_cpr;
		}
	}
}
