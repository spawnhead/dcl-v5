package net.sam.dcl.form;

import net.sam.dcl.taglib.table.model.impl.PageableHolderImplUsingList;
import net.sam.dcl.util.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.ParseException;
import java.util.Date;

/**
 * @author: DG
 * Date: Aug 6, 2005
 * Time: 5:37:29 PM
 */
public class SpecificationImportsForm extends JournalForm
{
	protected static Log log = LogFactory.getLog(SpecificationImportsForm.class);
	PageableHolderImplUsingList grid = new PageableHolderImplUsingList();

	String spi_id;
	String spi_arrive;
	String spi_send_date;

	public String getSpi_id()
	{
		return spi_id;
	}

	public void setSpi_id(String spi_id)
	{
		this.spi_id = spi_id;
	}

	public String getSpi_arrive()
	{
		return spi_arrive;
	}

	public void setSpi_arrive(String spi_arrive)
	{
		this.spi_arrive = spi_arrive;
	}

	public PageableHolderImplUsingList getGrid()
	{
		return grid;
	}

	public void setGrid(PageableHolderImplUsingList grid)
	{
		this.grid = grid;
	}

	public String getSpi_send_date()
	{
		return spi_send_date;
	}

	public void setSpi_send_date(String spi_send_date)
	{
		this.spi_send_date = spi_send_date;
	}

	static public class SpecificationImport
	{
		String spi_id;
		String spi_number;
		String spi_date;
		String spi_arrive;
		String spi_users;
		double spi_cost;
		String spi_send_date;

		public String getSpi_id()
		{
			return spi_id;
		}

		public void setSpi_id(String spi_id)
		{
			this.spi_id = spi_id;
		}

		public String getSpi_number()
		{
			return spi_number;
		}

		public void setSpi_number(String spi_number)
		{
			this.spi_number = spi_number;
		}

		public String getSpi_date()
		{
			return spi_date;
		}

		public String getSpiDateFormatted()
		{
			return StringUtil.dbDateString2appDateString(getSpi_date());
		}

		public void setSpi_date(String spi_date)
		{
			this.spi_date = spi_date;
		}

		public String getSpi_arrive()
		{
			return spi_arrive;
		}

		public void setSpi_arrive(String spi_arrive)
		{
			this.spi_arrive = spi_arrive;
		}

		public String getSpi_users()
		{
			return spi_users;
		}

		public void setSpi_users(String spi_users)
		{
			this.spi_users = spi_users;
		}

		public double getSpi_cost()
		{
			return spi_cost;
		}

		public String getSpiCostFormatted()
		{
			return StringUtil.double2appCurrencyString(getSpi_cost());
		}

		public void setSpi_cost(double spi_cost)
		{
			this.spi_cost = spi_cost;
		}

		public String getMsg_check_block()
		{
			if ("1".equals(spi_arrive))
				return "ask_unblock";
			else
				return "ask_block";
		}

		public String getSpi_send_date()
		{
			return spi_send_date;
		}

		public void setSpi_send_date(String spi_send_date)
		{
			this.spi_send_date = spi_send_date;
		}

		public String getSpiSendDateFormatted()
		{
			return StringUtil.dbDateString2appDateString(getSpi_send_date());
		}

		public Date getSpiSendDate() throws ParseException
		{
			return StringUtil.dbDateString2Date(getSpi_send_date());
		}
	}
}
