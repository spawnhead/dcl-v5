package net.sam.dcl.form;

import net.sam.dcl.beans.Seller;
import net.sam.dcl.taglib.table.model.impl.PageableHolderImplUsingList;
import net.sam.dcl.util.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author: DG
 * Date: Aug 6, 2005
 * Time: 5:37:29 PM
 */
public class OutgoingLettersForm extends JournalForm
{
  protected static Log log = LogFactory.getLog(OutgoingLettersForm.class);
	Seller seller = new Seller();

  PageableHolderImplUsingList gridOutgoingLetters = new PageableHolderImplUsingList();

	public Seller getSeller()
	{
		return seller;
	}

	public void setSeller(Seller seller)
	{
		this.seller = seller;
	}

	public PageableHolderImplUsingList getGridOutgoingLetters()
  {
    return gridOutgoingLetters;
  }

  public void setGridOutgoingLetters(PageableHolderImplUsingList gridOutgoingLetters)
  {
    this.gridOutgoingLetters = gridOutgoingLetters;
  }

  static public class OutgoingLetter
  {
    String otl_id;
    String otl_number;
    String otl_date;
    String otl_contractor;
    int attach_idx;

    public String getOtl_id()
    {
      return otl_id;
    }

    public void setOtl_id(String otl_id)
    {
      this.otl_id = otl_id;
    }

    public String getOtl_number()
    {
      return otl_number;
    }

    public void setOtl_number(String otl_number)
    {
      this.otl_number = otl_number;
    }

    public String getOtl_date()
    {
      return otl_date;
    }

    public void setOtl_date(String otl_date)
    {
      this.otl_date = otl_date;
    }

    public String getOtl_date_formatted()
    {
      return StringUtil.dbDateString2appDateString(otl_date);
    }

    public String getOtl_contractor()
    {
      return otl_contractor;
    }

    public void setOtl_contractor(String otl_contractor)
    {
      this.otl_contractor = otl_contractor;
    }

    public int getAttach_idx()
    {
      return attach_idx;
    }

    public void setAttach_idx(int attach_idx)
    {
      this.attach_idx = attach_idx;
    }
  }

}