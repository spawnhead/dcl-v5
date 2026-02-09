package net.sam.dcl.form;

import net.sam.dcl.controller.BaseForm;
import net.sam.dcl.taglib.table.model.impl.HolderImplUsingList;

/**
 * @author: DG
 * Date: Aug 6, 2005
 * Time: 5:37:29 PM
 */
public class LanguagesForm extends BaseForm
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

  static public class Language
  {
    String lng_id;
    String lng_name;
		String lng_letter_id;
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

		public String getLng_letter_id() {
			return lng_letter_id;
		}

		public void setLng_letter_id(String lng_letter_id) {
			this.lng_letter_id = lng_letter_id;
		}
	}


}
