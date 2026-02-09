package net.sam.dcl.form;

import net.sam.dcl.beans.*;
import net.sam.dcl.controller.ActionContext;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.taglib.table.model.impl.PageableHolderImplUsingList;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.StrutsUtil;
import net.sam.dcl.dao.OrderDAO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Date;

/**
 * @author: DG
 * Date: Aug 6, 2005
 * Time: 5:37:29 PM
 */
public class OrdersForm extends JournalForm
{
	protected static Log log = LogFactory.getLog(OrdersForm.class);
	protected static String replacementString = "from";

	PageableHolderImplUsingList grid = new PageableHolderImplUsingList();

	String ord_id;
	Contractor contractor_for = new Contractor();
	Contract contract = new Contract();
	Specification specification = new Specification();

	Contractor previousContractorFor = new Contractor();
	Contract previousContract = new Contract();

	String executed;
	String not_executed;
	String ord_executed;
	String ord_ready_for_deliv;
	String ord_annul_not_show;
	String ord_show_movement;

	Seller sellerForWho = new Seller();

	String state_a;
	String state_3;
	String state_b;
	String ord_num_conf;
	String state_exclamation;
	String state_c;

	String order_by = "";

	public String getOrd_id()
	{
		return ord_id;
	}

	public void setOrd_id(String ord_id)
	{
		this.ord_id = ord_id;
	}

	public Contractor getContractor_for()
	{
		return contractor_for;
	}

	public void setContractor_for(Contractor contractor_for)
	{
		this.contractor_for = contractor_for;
	}

	public String getExecuted()
	{
		return executed;
	}

	public void setExecuted(String executed)
	{
		this.executed = executed;
	}

	public String getNot_executed()
	{
		return not_executed;
	}

	public void setNot_executed(String not_executed)
	{
		this.not_executed = not_executed;
	}

	public String getOrd_executed()
	{
		if (("1".equals(getExecuted()) && "1".equals(getNot_executed())) ||
						(!"1".equals(getExecuted()) && !"1".equals(getNot_executed()))
						)
		{
			return null;
		}
		else if ("1".equals(getExecuted()))
		{
			return "1";
		}
		else
		{
			return "0";
		}
	}

	public void setOrd_executed(String ord_executed)
	{
		this.ord_executed = ord_executed;
	}

	public String getOrd_ready_for_deliv()
	{
		return ord_ready_for_deliv;
	}

	public void setOrd_ready_for_deliv(String ord_ready_for_deliv)
	{
		this.ord_ready_for_deliv = ord_ready_for_deliv;
	}

	public String getOrd_annul_not_show()
	{
		return ord_annul_not_show;
	}

	public void setOrd_annul_not_show(String ord_annul_not_show)
	{
		this.ord_annul_not_show = ord_annul_not_show;
	}

	public String getOrd_show_movement()
	{
		return ord_show_movement;
	}

	public boolean isShowMovement()
	{
		return !StringUtil.isEmpty(getOrd_show_movement());
	}

	public void setOrd_show_movement(String ord_show_movement)
	{
		this.ord_show_movement = ord_show_movement;
	}

	public Seller getSellerForWho()
	{
		return sellerForWho;
	}

	public void setSellerForWho(Seller sellerForWho)
	{
		this.sellerForWho = sellerForWho;
	}

	public String getState_a()
	{
		return state_a;
	}

	public void setState_a(String state_a)
	{
		this.state_a = state_a;
	}

	public String getState_3()
	{
		return state_3;
	}

	public void setState_3(String state_3)
	{
		this.state_3 = state_3;
	}

	public String getState_b()
	{
		return state_b;
	}

	public void setState_b(String state_b)
	{
		this.state_b = state_b;
	}

	public String getOrd_num_conf()
	{
		return ord_num_conf;
	}

	public void setOrd_num_conf(String ord_num_conf)
	{
		this.ord_num_conf = ord_num_conf;
	}

	public String getState_exclamation()
	{
		return state_exclamation;
	}

	public void setState_exclamation(String state_exclamation)
	{
		this.state_exclamation = state_exclamation;
	}

	public String getState_c()
	{
		return state_c;
	}

	public void setState_c(String state_c)
	{
		this.state_c = state_c;
	}

	public String getOrder_by()
	{
		return order_by;
	}

	public void setOrder_by(String order_by)
	{
		this.order_by = order_by;
	}

	public PageableHolderImplUsingList getGrid()
	{
		return grid;
	}

	public void setGrid(PageableHolderImplUsingList grid)
	{
		this.grid = grid;
	}

	static public class Order
	{
		String ord_id;
		String ord_number;
		String ord_date;
		String ord_contractor;
		String ord_contractor_for;
		double ord_summ;
		String ord_sent_to_prod_date;
		String ord_received_conf_date;
		String ord_date_conf;
		String have_empty_date_conf;
		String ord_conf_sent_date;
		String ord_ready_for_deliv_date;
		String ord_ship_from_stock;
		String ord_arrive_in_lithuania;
		String ord_executed_date;
		String ord_user;
		String ord_department;
		String ord_block;
		String is_warn;
		String usr_id_create;
		String dep_id;
		int count_day_curr_minus_sent;
		String ord_annul;
		String ord_num_conf;
		int ord_comment_flag;
		String ord_link_to_spec;
		String assemblies;
		String specifications_import;
		String cost_produces;

		boolean showMovement;

		public String getOrd_id()
		{
			return ord_id;
		}

		public void setOrd_id(String ord_id)
		{
			this.ord_id = ord_id;
		}

		public String getOrd_number()
		{
			return ord_number;
		}

		public void setOrd_number(String ord_number)
		{
			this.ord_number = ord_number;
		}

		public String getOrd_date()
		{
			return ord_date;
		}

		public void setOrd_date(String ord_date)
		{
			this.ord_date = ord_date;
		}

		public String getOrdDateFormatted()
		{
			return StringUtil.dbDateString2appDateString(getOrd_date());
		}

		public double getOrd_summ()
		{
			return ord_summ;
		}

		public String getOrdSumFormatted()
		{
			return StringUtil.double2appCurrencyString(getOrd_summ());
		}

		public void setOrd_summ(double ord_summ)
		{
			this.ord_summ = ord_summ;
		}

		public void setOrd_summ_formatted(String ord_summ)
		{
			this.ord_summ = StringUtil.appCurrencyString2double(ord_summ);
		}

		public String getOrd_sent_to_prod_date()
		{
			return ord_sent_to_prod_date;
		}

		public String getOrd_sent_to_prod_date_formatted()
		{
			return StringUtil.dbDateString2appDateString(getOrd_sent_to_prod_date());
		}

		public void setOrd_sent_to_prod_date(String ord_sent_to_prod_date)
		{
			this.ord_sent_to_prod_date = ord_sent_to_prod_date;
		}

		public String getOrd_received_conf_date()
		{
			return ord_received_conf_date;
		}

		public String getOrd_received_conf_date_formatted()
		{
			return StringUtil.dbDateString2appDateString(getOrd_received_conf_date());
		}

		public void setOrd_received_conf_date(String ord_received_conf_date)
		{
			this.ord_received_conf_date = ord_received_conf_date;
		}

		public String getOrd_date_conf()
		{
			return ord_date_conf;
		}

		public String getOrd_date_conf_formatted()
		{
			return StringUtil.dbDateString2appDateString(getOrd_date_conf());
		}

		public void setOrd_date_conf(String ord_date_conf)
		{
			this.ord_date_conf = ord_date_conf;
		}

		public String getHave_empty_date_conf()
		{
			return have_empty_date_conf;
		}

		public void setHave_empty_date_conf(String have_empty_date_conf)
		{
			this.have_empty_date_conf = have_empty_date_conf;
		}

		public String getOrd_conf_sent_date()
		{
			return ord_conf_sent_date;
		}

		public String getOrd_conf_sent_date_formatted()
		{
			return StringUtil.dbDateString2appDateString(getOrd_conf_sent_date());
		}

		public void setOrd_conf_sent_date(String ord_conf_sent_date)
		{
			this.ord_conf_sent_date = ord_conf_sent_date;
		}

		public String getOrd_ready_for_deliv_date()
		{
			return ord_ready_for_deliv_date;
		}

		public String getOrd_ready_for_deliv_date_formatted()
		{
			return StringUtil.dbDateString2appDateString(getOrd_ready_for_deliv_date());
		}

		public void setOrd_ready_for_deliv_date(String ord_ready_for_deliv_date)
		{
			this.ord_ready_for_deliv_date = ord_ready_for_deliv_date;
		}

		public String getOrd_ship_from_stock()
		{
			return ord_ship_from_stock;
		}

		public String getOrd_ship_from_stock_formatted()
		{
			return StringUtil.dbDateString2appDateString(getOrd_ship_from_stock());
		}

		public void setOrd_ship_from_stock(String ord_ship_from_stock)
		{
			this.ord_ship_from_stock = ord_ship_from_stock;
		}

		public String getOrd_arrive_in_lithuania()
		{
			return ord_arrive_in_lithuania;
		}

		public String getOrd_arrive_in_lithuania_formatted()
		{
			return StringUtil.dbDateString2appDateString(getOrd_arrive_in_lithuania());
		}

		public void setOrd_arrive_in_lithuania(String ord_arrive_in_lithuania)
		{
			this.ord_arrive_in_lithuania = ord_arrive_in_lithuania;
		}

		public String getOrd_executed_date()
		{
			return ord_executed_date;
		}

		public String getOrd_executed_date_formatted()
		{
			return StringUtil.dbDateString2appDateString(getOrd_executed_date());
		}

		public void setOrd_executed_date(String ord_executed_date)
		{
			this.ord_executed_date = ord_executed_date;
		}

		public String getOrd_contractor()
		{
			return ord_contractor;
		}

		public void setOrd_contractor(String ord_contractor)
		{
			this.ord_contractor = ord_contractor;
		}

		public String getOrd_contractor_for()
		{
			return ord_contractor_for;
		}

		public void setOrd_contractor_for(String ord_contractor_for)
		{
			this.ord_contractor_for = ord_contractor_for;
		}

		public String getOrd_block()
		{
			return ord_block;
		}

		public void setOrd_block(String ord_block)
		{
			this.ord_block = ord_block;
		}

		public String getIs_warn()
		{
			return is_warn;
		}

		public void setIs_warn(String is_warn)
		{
			this.is_warn = is_warn;
		}

		public String getUsr_id_create()
		{
			return usr_id_create;
		}

		public void setUsr_id_create(String usr_id_create)
		{
			this.usr_id_create = usr_id_create;
		}

		public String getDep_id()
		{
			return dep_id;
		}

		public void setDep_id(String dep_id)
		{
			this.dep_id = dep_id;
		}

		private String getAdditionalInfo()
		{
			if (!isShowMovement())
			{
				return "";
			}

			String retStr = "";
			IActionContext context = ActionContext.threadInstance();
			try
			{
				OrderDAO.loadAdditionalInfo(context, this);

				if (!StringUtil.isEmpty(getAssemblies()))
				{
					retStr += ReportDelimiterConsts.html_separator + StrutsUtil.getMessage(context, "Orders.assemblies", getAssembliesFormatted());
				}
				if (!StringUtil.isEmpty(getSpecifications_import()))
				{
					retStr += ReportDelimiterConsts.html_separator + StrutsUtil.getMessage(context, "Orders.specifications_import", getSpecificationsImportFormatted());
				}
				if (!StringUtil.isEmpty(getCost_produces()))
				{
					retStr += ReportDelimiterConsts.html_separator + StrutsUtil.getMessage(context, "Orders.cost_produces", getCostProducesFormatted());
				}
			}
			catch (Exception e)
			{
				log.error(e);
			}

			return retStr;
		}

		private String getPaymentInfo(boolean appendSeparator)
		{
			String retStr = "";
			IActionContext context = ActionContext.threadInstance();
			try
			{
				net.sam.dcl.beans.Order order = new net.sam.dcl.beans.Order(getOrd_id());
				OrderDAO.loadPaymentInfo(context, order);
				long countDayDelay = order.calculateDelay(order.getOrderPayments());
				double paySum = order.getPaySum();

				if (countDayDelay >= 2 && getOrd_summ() != paySum)
				{
					retStr += (appendSeparator ? ReportDelimiterConsts.html_separator : "") + StrutsUtil.getMessage(context, "Orders.delay", Long.toString(countDayDelay));
				}
				if (getOrd_summ() == paySum)
				{
					retStr += (appendSeparator ? ReportDelimiterConsts.html_separator : "") + StrutsUtil.getMessage(context, "Orders.payed");
				}

				Date notExecutedDate = null;
				order.calculatePaymentsDescription(order.getOrderPayments());
				for (OrderPayment orderPayment : order.getOrderPayments())
				{
					if (!StringUtil.isEmpty(orderPayment.getOrp_date()))
					{
						if (StringUtil.isEmpty(orderPayment.getDescription()) || orderPayment.getDescription().contains(StrutsUtil.getMessage(context, "Order.executed_part")))
						{
							notExecutedDate = StringUtil.dbDateString2Date(orderPayment.getOrp_date());
							break;
						}
					}
				}
				if (notExecutedDate != null)
				{
					countDayDelay = StringUtil.getDaysBetween(StringUtil.dbDateString2Date(StringUtil.date2dbDateString(StringUtil.getCurrentDateTime())), notExecutedDate);
					if (0 <= countDayDelay && countDayDelay <= 3)
					{
						retStr += (appendSeparator ? ReportDelimiterConsts.html_separator : "") + StrutsUtil.getMessage(context, "Orders.payWarn", Long.toString(countDayDelay));
					}
				}
			}
			catch (Exception e)
			{
				log.error(e);
			}

			return retStr;
		}

		private String getOrdCurrentState()
		{
			IActionContext context = ActionContext.threadInstance();
			try
			{
				if (!StringUtil.isEmpty(getOrd_annul()))
				{
					return StrutsUtil.getMessage(context, "Orders.ord_annul");
				}

				if (!StringUtil.isEmpty(getOrd_executed_date()))
				{
					return StrutsUtil.getMessage(context, "Orders.ord_current_executed") + " " + getOrd_executed_date_formatted();
				}
				if (!StringUtil.isEmpty(getOrd_ready_for_deliv_date()))
				{
					String retStr = StrutsUtil.getMessage(context, "Orders.ord_current_ready_for_deliv") + " ";
					Date now = StringUtil.getCurrentDateTime();
					Date readyForDeliveryDate = StringUtil.appDateString2Date(getOrd_ready_for_deliv_date_formatted());
					double days = StringUtil.getDaysBetween(readyForDeliveryDate, now);
					if (days > 6)
					{
						retStr += StrutsUtil.getMessage(context, "Common.redBold", getOrd_ready_for_deliv_date_formatted());
					}
					else
					{
						retStr += getOrd_ready_for_deliv_date_formatted();
					}

					if (!StringUtil.isEmpty(getOrd_ship_from_stock()))
					{
						retStr += " " + StrutsUtil.getMessage(context, "Orders.ord_ship_from_stock") + " ";
						Date shipFromStock = StringUtil.appDateString2Date(getOrd_ship_from_stock_formatted());
						days = StringUtil.getDaysBetween(shipFromStock, now);
						if (days > 1)
						{
							retStr += StrutsUtil.getMessage(context, "Common.redBold", getOrd_ship_from_stock_formatted());
						}
						else
						{
							retStr += getOrd_ship_from_stock_formatted();
						}
					}

					if (!StringUtil.isEmpty(getOrd_arrive_in_lithuania()))
					{
						retStr += " " + StrutsUtil.getMessage(context, "Orders.ord_arrive_in_lithuania") + " " + getOrd_arrive_in_lithuania_formatted();
					}

					return retStr;
				}
				if (!StringUtil.isEmpty(getOrd_conf_sent_date()))
				{
					return StrutsUtil.getMessage(context, "Orders.ord_current_conf_sent") + " " + getOrd_conf_sent_date_formatted();
				}
				if (!StringUtil.isEmpty(getOrd_received_conf_date()))
				{
					String retStr = StrutsUtil.getMessage(context, "Orders.ord_current_received_conf") + getOrd_num_conf() + " " + getOrd_received_conf_date_formatted();
					if (!StringUtil.isEmpty(getOrd_date_conf()))
					{
						Date today = StringUtil.getCurrentDateTime();
						Date dateCheck;
						dateCheck = StringUtil.appDateString2Date(getOrd_date_conf_formatted());
						if (dateCheck.before(today))
						{
							retStr = retStr + " " + StrutsUtil.getMessage(context, "Orders.ord_current_date_conf") + " " + StrutsUtil.getMessage(context, "Common.redBold", getOrd_date_conf_formatted());
						}
						else
						{
							retStr = retStr + " " + StrutsUtil.getMessage(context, "Orders.ord_current_date_conf") + " " + getOrd_date_conf_formatted();
						}
					}
					if (!StringUtil.isEmpty(getHave_empty_date_conf()))
					{
						Date today = StringUtil.getCurrentDateTime();
						Date dateCheck = StringUtil.appDateString2Date(getOrd_received_conf_date_formatted());
						long days = StringUtil.getDaysBetween(dateCheck, today);
						retStr += " " + StrutsUtil.getMessage(context, "Orders.have_empty_date_conf", Long.toString(days));
					}
					return retStr;
				}
				if (!StringUtil.isEmpty(getOrd_sent_to_prod_date()))
				{
					String retStr = StrutsUtil.getMessage(context, "Orders.ord_current_sent_to_prod") + " " + getOrd_sent_to_prod_date_formatted();
					if (StringUtil.isEmpty(getOrd_received_conf_date()) && getCount_day_curr_minus_sent() > 3)
					{
						retStr += " " + StrutsUtil.getMessage(context, "Orders.ord_current_sent_to_prod_add", Integer.toString(getCount_day_curr_minus_sent()));
					}
					return retStr;
				}
			}
			catch (Exception e)
			{
				log.error(e);
			}

			return "";
		}

		public String getOrdCurrentStateFormatted()
		{
			String retStr = getOrdCurrentState();
			if (getOrd_comment_flag() > 0)
			{
				IActionContext context = ActionContext.threadInstance();
				try
				{
					retStr += (!StringUtil.isEmpty(retStr) ? ReportDelimiterConsts.html_separator : "") + StrutsUtil.getMessage(context, "Orders.ord_comment");
				}
				catch (Exception e)
				{
					log.error(e);
				}
			}

			retStr += getAdditionalInfo();
			retStr += getPaymentInfo(!StringUtil.isEmpty(retStr));

			return retStr;
		}

		public int getCount_day_curr_minus_sent()
		{
			return count_day_curr_minus_sent;
		}

		public void setCount_day_curr_minus_sent(int count_day_curr_minus_sent)
		{
			this.count_day_curr_minus_sent = count_day_curr_minus_sent;
		}

		public String getOrd_annul()
		{
			return ord_annul;
		}

		public void setOrd_annul(String ord_annul)
		{
			this.ord_annul = ord_annul;
		}

		public String getThreeDayMsg()
		{
			if (StringUtil.isEmpty(getOrd_received_conf_date()) && getCount_day_curr_minus_sent() > 3)
			{
				IActionContext context = ActionContext.threadInstance();
				try
				{
					return StrutsUtil.getMessage(context, "Orders.three_day_msg");
				}
				catch (Exception e)
				{
					log.error(e);
				}

				return "";
			}

			return "";
		}

		public String getOrd_num_conf()
		{
			return ord_num_conf;
		}

		public void setOrd_num_conf(String ord_num_conf)
		{
			this.ord_num_conf = ord_num_conf;
		}

		public int getOrd_comment_flag()
		{
			return ord_comment_flag;
		}

		public void setOrd_comment_flag(int ord_comment_flag)
		{
			this.ord_comment_flag = ord_comment_flag;
		}

		public String getCommonFormattedPart(String source)
		{
			String resStr = source;
			IActionContext context = ActionContext.threadInstance();
			try
			{
				String from = " " + StrutsUtil.getMessage(context, "msg.common.from_only") + " ";
				resStr = resStr.replaceAll(replacementString, from);
				if (!StringUtil.isEmpty(resStr))
				{
					resStr = resStr.substring(0, resStr.length() - 2);
				}
			}
			catch (Exception e)
			{
				log.error(e);
			}
			return resStr;
		}

		public String getOrd_link_to_spec()
		{
			return ord_link_to_spec;
		}

		public void setOrd_link_to_spec(String ord_link_to_spec)
		{
			this.ord_link_to_spec = ord_link_to_spec;
		}

		public String getLinkToSpecText()
		{
			String resStr = "";
			IActionContext context = ActionContext.threadInstance();
			try
			{
				resStr = StrutsUtil.getMessage(context, "tooltip.Orders.linkToSpec" + getOrd_link_to_spec());
			}
			catch (Exception e)
			{
				log.error(e);
			}
			return resStr;
		}

		public String getAssemblies()
		{
			return assemblies;
		}

		public String getAssembliesFormatted()
		{
			return getCommonFormattedPart(getAssemblies());
		}

		public void setAssemblies(String assemblies)
		{
			this.assemblies = assemblies;
		}

		public String getSpecifications_import()
		{
			return specifications_import;
		}

		public String getSpecificationsImportFormatted()
		{
			return getCommonFormattedPart(getSpecifications_import());
		}

		public void setSpecifications_import(String specifications_import)
		{
			this.specifications_import = specifications_import;
		}

		public String getCost_produces()
		{
			return cost_produces;
		}

		public String getCostProducesFormatted()
		{
			return getCommonFormattedPart(getCost_produces());
		}

		public void setCost_produces(String cost_produces)
		{
			this.cost_produces = cost_produces;
		}

		public boolean isShowMovement()
		{
			return showMovement;
		}

		public void setShowMovement(boolean showMovement)
		{
			this.showMovement = showMovement;
		}

		public String getOrd_user()
		{
			return ord_user;
		}

		public void setOrd_user(String ord_user)
		{
			this.ord_user = ord_user;
		}

		public String getOrd_department()
		{
			return ord_department;
		}

		public void setOrd_department(String ord_department)
		{
			this.ord_department = ord_department;
		}

		public String getMsg_check_block()
		{
			if ("1".equals(ord_block))
				return "ask_unblock";
			else
				return "ask_block";
		}
	}

	public Contract getContract()
	{
		return contract;
	}

	public String getContractNumber()
	{
		if (getContract() == null || StringUtil.isEmpty(getContract().getCon_number()))
			return null;

		String retStr = getContract().getCon_number();
		retStr = retStr.split(" ")[0];
		return retStr;
	}

	public void setContract(Contract contract)
	{
		this.contract = contract;
	}

	public Specification getSpecification()
	{
		return specification;
	}

	public String getSpecificationNumber()
	{
		if (getSpecification() == null || StringUtil.isEmpty(getSpecification().getSpc_number()))
			return null;

		String retStr = getSpecification().getSpc_number();
		retStr = retStr.split(" ")[0];
		return retStr;
	}

	public void setSpecification(Specification specification)
	{
		this.specification = specification;
	}

	public Contractor getPreviousContractorFor()
	{
		return previousContractorFor;
	}

	public void setPreviousContractorFor(Contractor previousContractorFor)
	{
		this.previousContractorFor = previousContractorFor;
	}

	public Contract getPreviousContract()
	{
		return previousContract;
	}

	public void setPreviousContract(Contract previousContract)
	{
		this.previousContract = previousContract;
	}
}
