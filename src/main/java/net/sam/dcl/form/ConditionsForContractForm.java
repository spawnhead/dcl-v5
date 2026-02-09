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
public class ConditionsForContractForm extends JournalForm
{
	protected static Log log = LogFactory.getLog(ConditionsForContractForm.class);

	PageableHolderImplUsingList grid = new PageableHolderImplUsingList();

	String date_begin;
	String date_end;

	String cfc_id;
	String cfc_execute;
	Seller seller = new Seller();

	String cfc_check_price;
	String cfc_not_executed;
	String annul_exclude;
	String cfc_not_placed;

	public String getDate_begin()
	{
		return date_begin;
	}

	public String getDate_begin_date()
	{
		return StringUtil.appDateString2dbTimestampString(date_begin);
	}

	public void setDate_begin(String date_begin)
	{
		this.date_begin = date_begin;
	}

	public String getDate_end()
	{
		return date_end;
	}

	public String getDate_end_date()
	{
		return StringUtil.appDateString2dbTimestampString(date_end);
	}

	public void setDate_end(String date_end)
	{
		this.date_end = date_end;
	}

	public String getCfc_id()
	{
		return cfc_id;
	}

	public void setCfc_id(String cfc_id)
	{
		this.cfc_id = cfc_id;
	}

	public String getCfc_execute()
	{
		return cfc_execute;
	}

	public void setCfc_execute(String cfc_execute)
	{
		this.cfc_execute = cfc_execute;
	}

	public Seller getSeller()
	{
		return seller;
	}

	public void setSeller(Seller seller)
	{
		this.seller = seller;
	}

	public String getCfc_check_price()
	{
		return cfc_check_price;
	}

	public void setCfc_check_price(String cfc_check_price)
	{
		this.cfc_check_price = cfc_check_price;
	}

	public String getCfc_not_executed()
	{
		return cfc_not_executed;
	}

	public void setCfc_not_executed(String cfc_not_executed)
	{
		this.cfc_not_executed = cfc_not_executed;
	}

	public String getAnnul_exclude()
	{
		return annul_exclude;
	}

	public void setAnnul_exclude(String annul_exclude)
	{
		this.annul_exclude = annul_exclude;
	}

	public String getCfc_not_placed()
	{
		return cfc_not_placed;
	}

	public void setCfc_not_placed(String cfc_not_placed)
	{
		this.cfc_not_placed = cfc_not_placed;
	}

	public PageableHolderImplUsingList getGrid()
	{
		return grid;
	}

	public void setGrid(PageableHolderImplUsingList grid)
	{
		this.grid = grid;
	}

	static public class ConditionForContract
	{
		String cfc_id;
		String cfc_place_date;
		String cfc_contractor;
		String cfc_user;
		String cfc_execute;
		String cfc_seller;
		String cfc_check_price;
		String cfc_check_price_date;
		String cfc_check_price_user;
		String cfc_annul;

		String usr_id;
		String dep_id;

		public String getCfc_id()
		{
			return cfc_id;
		}

		public void setCfc_id(String cfc_id)
		{
			this.cfc_id = cfc_id;
		}

		public String getCfc_place_date()
		{
			return cfc_place_date;
		}

		public void setCfc_place_date(String cfc_place_date)
		{
			this.cfc_place_date = cfc_place_date;
		}

		public String getCfc_place_date_formatted()
		{
			return StringUtil.dbDateTimeString2appDateTimeString(cfc_place_date);
		}

		public String getCfc_contractor()
		{
			return cfc_contractor;
		}

		public void setCfc_contractor(String cfc_contractor)
		{
			this.cfc_contractor = cfc_contractor;
		}

		public String getCfc_execute()
		{
			return cfc_execute;
		}

		public void setCfc_execute(String cfc_execute)
		{
			this.cfc_execute = cfc_execute;
		}

		public String getCfc_user()
		{
			return cfc_user;
		}

		public void setCfc_user(String cfc_user)
		{
			this.cfc_user = cfc_user;
		}

		public String getCfc_seller()
		{
			return cfc_seller;
		}

		public void setCfc_seller(String cfc_seller)
		{
			this.cfc_seller = cfc_seller;
		}

		public String getMsg_check_block()
		{
			if ("1".equals(cfc_execute))
				return "ask_unblock";
			else
				return "ask_block";
		}

		public String getCfc_check_price()
		{
			return cfc_check_price;
		}

		public void setCfc_check_price(String cfc_check_price)
		{
			this.cfc_check_price = cfc_check_price;
		}

		public String getCfc_check_price_date()
		{
			return cfc_check_price_date;
		}

		public void setCfc_check_price_date(String cfc_check_price_date)
		{
			this.cfc_check_price_date = cfc_check_price_date;
		}

		public String getCfc_check_price_user()
		{
			return cfc_check_price_user;
		}

		public void setCfc_check_price_user(String cfc_check_price_user)
		{
			this.cfc_check_price_user = cfc_check_price_user;
		}

		public String getCheckPricesText()
		{
			if (!StringUtil.isEmpty(getCfc_check_price()))
			{
				return StringUtil.dbDateTimeString2appDateTimeString(getCfc_check_price_date()) + " " + getCfc_check_price_user();
			}

			return "";
		}

		public String getCfc_annul()
		{
			return cfc_annul;
		}

		public boolean isAnnul()
		{
			return "1".equals(getCfc_annul());
		}

		public void setCfc_annul(String cfc_annul)
		{
			this.cfc_annul = cfc_annul;
		}

		public String getUsr_id()
		{
			return usr_id;
		}

		public void setUsr_id(String usr_id)
		{
			this.usr_id = usr_id;
		}

		public String getDep_id()
		{
			return dep_id;
		}

		public void setDep_id(String dep_id)
		{
			this.dep_id = dep_id;
		}
	}

}
