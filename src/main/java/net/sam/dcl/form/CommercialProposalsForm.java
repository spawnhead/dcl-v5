package net.sam.dcl.form;

import net.sam.dcl.controller.ActionContext;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.taglib.table.model.impl.PageableHolderImplUsingList;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.StrutsUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.Date;

/**
 * @author: DG
 * Date: Aug 6, 2005
 * Time: 5:37:29 PM
 */
public class CommercialProposalsForm extends JournalForm
{
	protected static Log log = LogFactory.getLog(CommercialProposalsForm.class);

	PageableHolderImplUsingList grid = new PageableHolderImplUsingList();

	String cpr_id;
	String cpr_proposal_declined_in;
	String cpr_proposal_received_flag_in;

	boolean showCloneMsg;

	public String getCpr_id()
	{
		return cpr_id;
	}

	public void setCpr_id(String cpr_id)
	{
		this.cpr_id = cpr_id;
	}

	public boolean isShowCloneMsg()
	{
		return showCloneMsg;
	}

	public void setShowCloneMsg(boolean showCloneMsg)
	{
		this.showCloneMsg = showCloneMsg;
	}

	public PageableHolderImplUsingList getGrid()
	{
		return grid;
	}

	public void setGrid(PageableHolderImplUsingList grid)
	{
		this.grid = grid;
	}

	public String getCpr_proposal_declined_in()
	{
		return cpr_proposal_declined_in;
	}

	public void setCpr_proposal_declined_in(String cpr_proposal_declined_in)
	{
		this.cpr_proposal_declined_in = cpr_proposal_declined_in;
	}

	public String getCpr_proposal_received_flag_in()
	{
		return cpr_proposal_received_flag_in;
	}

	public void setCpr_proposal_received_flag_in(String cpr_proposal_received_flag_in)
	{
		this.cpr_proposal_received_flag_in = cpr_proposal_received_flag_in;
	}

	static public class CommercialProposal
	{
		String cpr_id;
		String cpr_number;
		String cpr_date;
		double cpr_summ;
		String cpr_contractor;
		String cpr_currency;
		String cpr_proposal_received_flag;
		String cpr_block;
		String cpr_user;
		String cpr_department;
		String dep_id;
		String usr_id_create;
		String cpr_check_price;
		String cpr_check_price_date;
		String cpr_check_price_user;
		String cpr_final_date;
		String cpr_assemble_minsk_store;
		String cpr_no_reservation;
		String cpr_stf_name;
		String cpr_proposal_declined;


		public String getCpr_id()
		{
			return cpr_id;
		}

		public void setCpr_id(String cpr_id)
		{
			this.cpr_id = cpr_id;
		}

		public String getCpr_number()
		{
			return cpr_number;
		}

		public void setCpr_number(String cpr_number)
		{
			this.cpr_number = cpr_number;
		}

		public String getCpr_date()
		{
			return cpr_date;
		}

		public void setCpr_date(String cpr_date)
		{
			this.cpr_date = cpr_date;
		}

		public String getCpr_date_date()
		{
			return StringUtil.dbDateString2appDateString(cpr_date);
		}

		public double getCpr_summ()
		{
			return cpr_summ;
		}

		public String getCprSumFormatted()
		{
			return StringUtil.double2appCurrencyString(getCpr_summ());
		}

		public void setCpr_summ(double cpr_summ)
		{
			this.cpr_summ = cpr_summ;
		}

		public void setCprSumFormatted(String cprSum)
		{
			this.cpr_summ = StringUtil.appCurrencyString2double(cprSum);
		}

		public String getCpr_contractor()
		{
			return cpr_contractor;
		}

		public void setCpr_contractor(String cpr_contractor)
		{
			this.cpr_contractor = cpr_contractor;
		}

		public String getCpr_currency()
		{
			return cpr_currency;
		}

		public void setCpr_currency(String cpr_currency)
		{
			this.cpr_currency = cpr_currency;
		}

		public String getCpr_proposal_received_flag()
		{
			return cpr_proposal_received_flag;
		}

		public void setCpr_proposal_received_flag(String cpr_proposal_received_flag)
		{
			this.cpr_proposal_received_flag = cpr_proposal_received_flag;
		}

		public String getCpr_block()
		{
			return cpr_block;
		}

		public void setCpr_block(String cpr_block)
		{
			this.cpr_block = cpr_block;
		}

		public String getMsg_check_block()
		{
			if ("1".equals(getCpr_block()))
				return "ask_unblock";
			else
				return "ask_block";
		}

		public String getCpr_user()
		{
			return cpr_user;
		}

		public void setCpr_user(String cpr_user)
		{
			this.cpr_user = cpr_user;
		}

		public String getCpr_department()
		{
			return cpr_department;
		}

		public void setCpr_department(String cpr_department)
		{
			this.cpr_department = cpr_department;
		}

		public String getDep_id()
		{
			return dep_id;
		}

		public void setDep_id(String dep_id)
		{
			this.dep_id = dep_id;
		}

		public String getUsr_id_create()
		{
			return usr_id_create;
		}

		public void setUsr_id_create(String usr_id_create)
		{
			this.usr_id_create = usr_id_create;
		}

		public String getCpr_check_price()
		{
			return cpr_check_price;
		}

		public void setCpr_check_price(String cpr_check_price)
		{
			this.cpr_check_price = cpr_check_price;
		}

		public String getCpr_check_price_date()
		{
			return cpr_check_price_date;
		}

		public void setCpr_check_price_date(String cpr_check_price_date)
		{
			this.cpr_check_price_date = cpr_check_price_date;
		}

		public String getCpr_check_price_user()
		{
			return cpr_check_price_user;
		}

		public void setCpr_check_price_user(String cpr_check_price_user)
		{
			this.cpr_check_price_user = cpr_check_price_user;
		}

		public String getCheckPricesText()
		{
			if (!StringUtil.isEmpty(getCpr_check_price()))
			{
				return StringUtil.dbDateTimeString2appDateTimeString(getCpr_check_price_date()) + " " + getCpr_check_price_user();
			}

			return "";
		}

		public String getCpr_final_date()
		{
			return cpr_final_date;
		}

		public String getCprFinalDateFormatted()
		{
			return StringUtil.dbDateString2appDateString(getCpr_final_date());
		}

		public void setCpr_final_date(String cpr_final_date)
		{
			this.cpr_final_date = cpr_final_date;
		}

		public String getCpr_assemble_minsk_store()
		{
			return cpr_assemble_minsk_store;
		}

		public String getCpr_stf_name()
		{
			return cpr_stf_name;
		}

		public void setCpr_stf_name(String cpr_stf_name)
		{
			this.cpr_stf_name = cpr_stf_name;
		}

		public void setCpr_assemble_minsk_store(String cpr_assemble_minsk_store)
		{
			this.cpr_assemble_minsk_store = cpr_assemble_minsk_store;
		}

		public String getCpr_no_reservation()
		{
			return cpr_no_reservation;
		}

		public void setCpr_no_reservation(String cpr_no_reservation)
		{
			this.cpr_no_reservation = cpr_no_reservation;
		}

		public String getCpr_proposal_declined()
		{
			return cpr_proposal_declined;
		}

		public void setCpr_proposal_declined(String cpr_proposal_declined)
		{
			this.cpr_proposal_declined = cpr_proposal_declined;
		}

		public String getAttachSqr()
		{
			if ("1".equals(getCpr_proposal_received_flag()))
				return "green";
			else if ("1".equals(getCpr_proposal_declined()))
				return "red";
			else
				return "null";
		}

		public String getReservedState()
		{
			if (StringUtil.isEmpty(getCpr_assemble_minsk_store()))
				return "";

			String retStr = "";
			try
			{
				IActionContext context = ActionContext.threadInstance();
				//Время для сравнения - конец дня.
				Date today = StringUtil.getEndOfDay(StringUtil.getCurrentDateTime());

				Date finalDate = StringUtil.dbDateString2Date(getCpr_final_date());

				// Если "Не бронировать товар (снять с брони)"=ДА,
				// то писать: "Не бронировалось (снято с брони) менеджером".
				if (!StringUtil.isEmpty(getCpr_no_reservation()))
				{
					retStr = StrutsUtil.getMessage(context, "CommercialProposals.reservedState4");
					return retStr;
				}

				// Если текущая дата =< значения поля "Срок действия" И поле "Дата акцепта коммерческого предложения" НЕзаполнено (чекбокс НЕотмечен),
				// то писать: "Бронь истекает <значение поля "Срок действия">".
				if ((today.equals(finalDate) || today.before(finalDate)) && (getCpr_proposal_received_flag().equals("0")))
				{
					retStr = StrutsUtil.getMessage(context, "CommercialProposals.reservedState1", getCprFinalDateFormatted());
					return retStr;
				}

				// Если текущая дата > значения поля "Срок действия" И поле "Дата акцепта коммерческого предложения" НЕзаполнено (чекбокс НЕотмечен),
				// то писать: "Снято с брони. Истёк срок.".
				if (finalDate.before(today) && getCpr_proposal_received_flag().equals("0"))
				{
					retStr = StrutsUtil.getMessage(context, "CommercialProposals.reservedState2");
					return retStr;
				}

				// Если текущая дата =< значения поля "Срок действия" И поле "Дата акцепта коммерческого предложения" заполнено (чекбокс отмечен),
				// то писать: "Акцептовано".
				if ((today.equals(finalDate) || today.before(finalDate)) && getCpr_proposal_received_flag().equals("1"))
				{
					retStr = StrutsUtil.getMessage(context, "CommercialProposals.reservedState3");
					return retStr;
				}

			}
			catch (Exception e)
			{
				log.error(e);
			}

			return retStr;
		}
	}

}
