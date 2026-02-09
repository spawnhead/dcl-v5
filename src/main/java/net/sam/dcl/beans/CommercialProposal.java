package net.sam.dcl.beans;

import net.sam.dcl.controller.ActionContext;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.util.NumberToWordsRussian;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.StrutsUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Date: 31.08.2005
 * Time: 18:53:07
 * To change this template use File | Settings | File Templates.
 */
public class CommercialProposal implements Serializable
{
	protected static Log log = LogFactory.getLog(CommercialProposal.class);
	public static String includeYes = "yes";
	public static String includeNo = "no";

	private int type;

	int countItogRecord;

	String include_exps;
	String is_new_doc;

	String cpr_id;

	User createUser = new User();
	User editUser = new User();
	String usr_date_create;
	String usr_date_edit;

	String cpr_number;
	String cpr_date;
	Blank blank = new Blank();
	String cpr_img_name;
	Contractor contractor = new Contractor();
	ContactPerson contactPerson = new ContactPerson();
	String cpr_concerning;
	String cpr_concerning_invoice;
	String cpr_preamble;
	Currency currency = new Currency();
	Currency currencyTable = new Currency();
	double cpr_course;
	double cpr_nds;
	IncoTerm priceCondition = new IncoTerm();
	String cpr_country;
	String cpr_pay_condition;
	String cpr_pay_condition_invoice;
	IncoTerm deliveryCondition = new IncoTerm();
	String cpr_nds_by_string;
	String cpr_delivery_address;
	double cpr_sum_transport;
	String cpr_all_transport;
	double cpr_sum_assembling;
	String cpr_delivery_term;
	String cpr_delivery_term_invoice;
	String cpr_add_info;
	String cpr_final_date;
	String cpr_final_date_invoice;
	User user = new User();
	User executor = new User();
	String cpr_executor_flag;
	String facsimile_flag;
	String cpr_date_accept;
	String cpr_proposal_received_flag = "0";
	String cpr_proposal_declined = "0";
	String cpr_block;
	double cpr_summ;
	String cpr_old_version;
	String cpr_assemble_minsk_store;
	String cpr_free_prices;
	String cpr_reverse_calc;
	String cpr_can_edit_invoice;
	String cpr_comment;
	String cpr_check_price;
	String cpr_check_price_date;
	String usr_id_check_price;
	PurchasePurpose purchasePurpose = new PurchasePurpose();
	ContactPerson contactPersonSeller = new ContactPerson();
	ContactPerson contactPersonCustomer = new ContactPerson();
	int cpr_guaranty_in_month;
	double cpr_prepay_percent;
	double cpr_prepay_sum;
	String cpr_delay_days;
	Contractor consignee = new Contractor();
	String cpr_no_reservation;
	String cpr_provider_delivery;
	String cpr_provider_delivery_address;
	String cpr_delivery_count_day;
	String cpr_donot_calculate_netto;
	String cpr_print_scale;
	String cpr_contract_scale;
	String cpr_invoice_scale;
	String cpr_final_date_above;
	String cpr_tender_number;
	String cpr_tender_number_editable = "0";
	double total;
	double totalPrint;
	double ndsPrint;

	String show_unit;
	boolean printPriceListBy;

	List<CommercialProposalProduce> produces = new ArrayList<CommercialProposalProduce>();
	List<CommercialProposalTransport> transportLines = new ArrayList<CommercialProposalTransport>();

	public CommercialProposal()
	{
	}

	public CommercialProposal(String cpr_id)
	{
		this.cpr_id = cpr_id;
	}

	public int getCountItogRecord()
	{
		return countItogRecord;
	}

	public User getCreateUser()
	{
		return createUser;
	}

	public void setCreateUser(User createUser)
	{
		this.createUser = createUser;
	}

	public User getEditUser()
	{
		return editUser;
	}

	public void setEditUser(User editUser)
	{
		this.editUser = editUser;
	}

	public String getInclude_exps()
	{
		return include_exps;
	}

	public void setInclude_exps(String include_exps)
	{
		this.include_exps = include_exps;
	}

	public String getIs_new_doc()
	{
		return is_new_doc;
	}

	public void setIs_new_doc(String is_new_doc)
	{
		this.is_new_doc = is_new_doc;
	}

	public String getCpr_id()
	{
		return cpr_id;
	}

	public void setCpr_id(String cpr_id)
	{
		this.cpr_id = cpr_id;
	}

	public String getUsr_date_create()
	{
		return usr_date_create;
	}

	public void setUsr_date_create(String usr_date_create)
	{
		this.usr_date_create = usr_date_create;
	}

	public String getUsr_date_edit()
	{
		return usr_date_edit;
	}

	public void setUsr_date_edit(String usr_date_edit)
	{
		this.usr_date_edit = usr_date_edit;
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

	public String getCpr_date_ts()
	{
		return StringUtil.appDateString2dbDateString(cpr_date);
	}

	public String getCpr_date_tm()
	{
		String tm_date = StringUtil.appDateString2dbDateString(cpr_date);
		tm_date += " 23:59:59";
		return tm_date;
	}

	public void setCpr_date(String cpr_date)
	{
		this.cpr_date = cpr_date;
	}

	public Blank getBlank()
	{
		return blank;
	}

	public void setBlank(Blank blank)
	{
		this.blank = blank;
	}

	public String getCpr_img_name()
	{
		return cpr_img_name;
	}

	public void setCpr_img_name(String cpr_img_name)
	{
		this.cpr_img_name = cpr_img_name;
	}

	public Contractor getContractor()
	{
		return contractor;
	}

	public void setContractor(Contractor contractor)
	{
		this.contractor = contractor;
	}

	public ContactPerson getContactPerson()
	{
		return contactPerson;
	}

	public void setContactPerson(ContactPerson contactPerson)
	{
		this.contactPerson = contactPerson;
	}

	public String getCpr_concerning()
	{
		return cpr_concerning;
	}

	public void setCpr_concerning(String cpr_concerning)
	{
		this.cpr_concerning = cpr_concerning;
	}

	public String getCpr_preamble()
	{
		return cpr_preamble;
	}

	public String getCpr_concerning_invoice()
	{
		return cpr_concerning_invoice;
	}

	public void setCpr_concerning_invoice(String cpr_concerning_invoice)
	{
		this.cpr_concerning_invoice = cpr_concerning_invoice;
	}

	public void setCpr_preamble(String cpr_preamble)
	{
		this.cpr_preamble = cpr_preamble;
	}

	public Currency getCurrency()
	{
		return currency;
	}

	public void setCurrency(Currency currency)
	{
		this.currency = currency;
	}

	public Currency getCurrencyTable()
	{
		return currencyTable;
	}

	public void setCurrencyTable(Currency currencyTable)
	{
		this.currencyTable = currencyTable;
	}

	public double getCpr_course()
	{
		return cpr_course;
	}

	public void setCpr_course(double cpr_course)
	{
		this.cpr_course = cpr_course;
	}

	public double getCpr_nds()
	{
		return cpr_nds;
	}

	public String getCpr_nds_formatted()
	{
		return StringUtil.double2appCurrencyString(cpr_nds);
	}

	public void setCpr_nds(double cpr_nds)
	{
		this.cpr_nds = cpr_nds;
	}

	public IncoTerm getPriceCondition()
	{
		return priceCondition;
	}

	public void setPriceCondition(IncoTerm priceCondition)
	{
		this.priceCondition = priceCondition;
	}

	public String getCpr_country()
	{
		return cpr_country;
	}

	public void setCpr_country(String cpr_country)
	{
		this.cpr_country = cpr_country;
	}

	public String getCpr_pay_condition()
	{
		return cpr_pay_condition;
	}

	public void setCpr_pay_condition(String cpr_pay_condition)
	{
		this.cpr_pay_condition = cpr_pay_condition;
	}

	public String getCpr_pay_condition_invoice()
	{
		return cpr_pay_condition_invoice;
	}

	public void setCpr_pay_condition_invoice(String cpr_pay_condition_invoice)
	{
		this.cpr_pay_condition_invoice = cpr_pay_condition_invoice;
	}

	public IncoTerm getDeliveryCondition()
	{
		return deliveryCondition;
	}

	public void setDeliveryCondition(IncoTerm deliveryCondition)
	{
		this.deliveryCondition = deliveryCondition;
	}

	public String getCpr_nds_by_string()
	{
		return cpr_nds_by_string;
	}

	public void setCpr_nds_by_string(String cpr_nds_by_string)
	{
		this.cpr_nds_by_string = cpr_nds_by_string;
	}

	public String getCpr_delivery_address()
	{
		return cpr_delivery_address;
	}

	public void setCpr_delivery_address(String cpr_delivery_address)
	{
		this.cpr_delivery_address = cpr_delivery_address;
	}

	public double getCpr_sum_transport()
	{
		return cpr_sum_transport;
	}

	public void setCpr_sum_transport(double cpr_sum_transport)
	{
		this.cpr_sum_transport = cpr_sum_transport;
	}

	public String getCpr_all_transport()
	{
		return cpr_all_transport;
	}

	public void setCpr_all_transport(String cpr_all_transport)
	{
		this.cpr_all_transport = cpr_all_transport;
	}

	public boolean isGetTransportFromTable()
	{
		return transportLines.size() > 1 && StringUtil.isEmpty(getCpr_all_transport());
	}

	public double getCpr_sum_assembling()
	{
		return cpr_sum_assembling;
	}

	public void setCpr_sum_assembling(double cpr_sum_assembling)
	{
		this.cpr_sum_assembling = cpr_sum_assembling;
	}

	public String getCpr_delivery_term()
	{
		return cpr_delivery_term;
	}

	public void setCpr_delivery_term(String cpr_delivery_term)
	{
		this.cpr_delivery_term = cpr_delivery_term;
	}

	public String getCpr_delivery_term_invoice()
	{
		return cpr_delivery_term_invoice;
	}

	public void setCpr_delivery_term_invoice(String cpr_delivery_term_invoice)
	{
		this.cpr_delivery_term_invoice = cpr_delivery_term_invoice;
	}

	public String getCpr_add_info()
	{
		return cpr_add_info;
	}

	public void setCpr_add_info(String cpr_add_info)
	{
		this.cpr_add_info = cpr_add_info;
	}

	public String getCpr_final_date()
	{
		return cpr_final_date;
	}

	public String getFinalDateFullFormat()
	{
		return StringUtil.appDateString2appFullDateFormat(getCpr_final_date());
	}

	public String getCpr_final_date_ts()
	{
		return StringUtil.appDateString2dbDateString(cpr_final_date);
	}

	public void setCpr_final_date(String cpr_final_date)
	{
		this.cpr_final_date = cpr_final_date;
	}

	public String getCpr_final_date_invoice()
	{
		return cpr_final_date_invoice;
	}

	public void setCpr_final_date_invoice(String cpr_final_date_invoice)
	{
		this.cpr_final_date_invoice = cpr_final_date_invoice;
	}

	public User getUser()
	{
		return user;
	}

	public void setUser(User user)
	{
		this.user = user;
	}

	public User getExecutor()
	{
		return executor;
	}

	public void setExecutor(User executor)
	{
		this.executor = executor;
	}

	public String getCpr_executor_flag()
	{
		return cpr_executor_flag;
	}

	public void setCpr_executor_flag(String cpr_executor_flag)
	{
		this.cpr_executor_flag = cpr_executor_flag;
	}

	public String getFacsimile_flag()
	{
		return facsimile_flag;
	}

	public void setFacsimile_flag(String facsimile_flag)
	{
		this.facsimile_flag = facsimile_flag;
	}

	public String getCpr_date_accept()
	{
		return cpr_date_accept;
	}

	public String getCpr_date_accept_ts()
	{
		return StringUtil.appDateString2dbDateString(cpr_date_accept);
	}

	public void setCpr_date_accept(String cpr_date_accept)
	{
		this.cpr_date_accept = cpr_date_accept;
	}

	public String getCpr_proposal_received_flag()
	{
		return cpr_proposal_received_flag;
	}

	public void setCpr_proposal_received_flag(String cpr_proposal_received_flag)
	{
		this.cpr_proposal_received_flag = cpr_proposal_received_flag;
	}

	public String getCpr_proposal_declined()
	{
		return cpr_proposal_declined;
	}

	public void setCpr_proposal_declined(String cpr_proposal_declined)
	{
		this.cpr_proposal_declined = cpr_proposal_declined;
	}

	public String getCpr_block()
	{
		return cpr_block;
	}

	public void setCpr_block(String cpr_block)
	{
		this.cpr_block = cpr_block;
	}

	public double getCpr_summ()
	{
		return cpr_summ;
	}

	public void setCpr_summ(double cpr_summ)
	{
		this.cpr_summ = cpr_summ;
	}

	public String getCpr_old_version()
	{
		return cpr_old_version;
	}

	public boolean isOldVersion()
	{
		return !StringUtil.isEmpty(cpr_old_version);
	}

	public void setCpr_old_version(String cpr_old_version)
	{
		this.cpr_old_version = cpr_old_version;
	}

	public String getCpr_assemble_minsk_store()
	{
		return cpr_assemble_minsk_store;
	}

	public boolean isAssembleMinskStore()
	{
		return !StringUtil.isEmpty(getCpr_assemble_minsk_store());
	}

	public void setCpr_assemble_minsk_store(String cpr_assemble_minsk_store)
	{
		this.cpr_assemble_minsk_store = cpr_assemble_minsk_store;
	}

	public String getCpr_free_prices()
	{
		return cpr_free_prices;
	}

	public boolean isFreePrices()
	{
		return !StringUtil.isEmpty(getCpr_free_prices());
	}

	public void setCpr_free_prices(String cpr_free_prices)
	{
		this.cpr_free_prices = cpr_free_prices;
	}

	public String getCpr_reverse_calc()
	{
		return cpr_reverse_calc;
	}

	public boolean isReverseCalc()
	{
		return !StringUtil.isEmpty(cpr_reverse_calc);
	}

	public void setCpr_reverse_calc(String cpr_reverse_calc)
	{
		this.cpr_reverse_calc = cpr_reverse_calc;
	}

	public String getCpr_can_edit_invoice()
	{
		return cpr_can_edit_invoice;
	}

	public void setCpr_can_edit_invoice(String cpr_can_edit_invoice)
	{
		this.cpr_can_edit_invoice = cpr_can_edit_invoice;
	}

	public String getCpr_comment()
	{
		return cpr_comment;
	}

	public void setCpr_comment(String cpr_comment)
	{
		this.cpr_comment = cpr_comment;
	}

	public String getCpr_check_price()
	{
		return cpr_check_price;
	}

	public boolean isCheckedPrices()
	{
		return !StringUtil.isEmpty(getCpr_check_price());
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

	public String getUsr_id_check_price()
	{
		return usr_id_check_price;
	}

	public void setUsr_id_check_price(String usr_id_check_price)
	{
		this.usr_id_check_price = usr_id_check_price;
	}

	public PurchasePurpose getPurchasePurpose()
	{
		return purchasePurpose;
	}

	public void setPurchasePurpose(PurchasePurpose purchasePurpose)
	{
		this.purchasePurpose = purchasePurpose;
	}

	public ContactPerson getContactPersonSeller()
	{
		return contactPersonSeller;
	}

	public void setContactPersonSeller(ContactPerson contactPersonSeller)
	{
		this.contactPersonSeller = contactPersonSeller;
	}

	public ContactPerson getContactPersonCustomer()
	{
		return contactPersonCustomer;
	}

	public void setContactPersonCustomer(ContactPerson contactPersonCustomer)
	{
		this.contactPersonCustomer = contactPersonCustomer;
	}

	public int getCpr_guaranty_in_month()
	{
		return cpr_guaranty_in_month;
	}

	public void setCpr_guaranty_in_month(int cpr_guaranty_in_month)
	{
		this.cpr_guaranty_in_month = cpr_guaranty_in_month;
	}

	public double getCpr_prepay_percent()
	{
		return cpr_prepay_percent;
	}

	public void setCpr_prepay_percent(double cpr_prepay_percent)
	{
		this.cpr_prepay_percent = cpr_prepay_percent;
	}

	public double getCpr_prepay_sum()
	{
		return cpr_prepay_sum;
	}

	public void setCpr_prepay_sum(double cpr_prepay_sum)
	{
		this.cpr_prepay_sum = cpr_prepay_sum;
	}

	public String getCpr_delay_days()
	{
		return cpr_delay_days;
	}

	public String getDelayDaysInWords()
	{
		return NumberToWordsRussian.toWords(new BigDecimal(getCpr_delay_days()));
	}

	public void setCpr_delay_days(String cpr_delay_days)
	{
		this.cpr_delay_days = cpr_delay_days;
		if (StringUtil.isEmpty(cpr_delay_days))
		{
			this.cpr_delay_days = "0";
		}
	}

	public Contractor getConsignee()
	{
		return consignee;
	}

	public void setConsignee(Contractor consignee)
	{
		this.consignee = consignee;
	}

	public String getCpr_no_reservation()
	{
		return cpr_no_reservation;
	}

	public void setCpr_no_reservation(String cpr_no_reservation)
	{
		this.cpr_no_reservation = cpr_no_reservation;
	}

	public String getCpr_provider_delivery()
	{
		return cpr_provider_delivery;
	}

	public boolean isProviderDelivery()
	{
		return !StringUtil.isEmpty(getCpr_provider_delivery());
	}

	public void setCpr_provider_delivery(String cpr_provider_delivery)
	{
		this.cpr_provider_delivery = cpr_provider_delivery;
	}

	public String getCpr_provider_delivery_address()
	{
		return cpr_provider_delivery_address;
	}

	public void setCpr_provider_delivery_address(String cpr_provider_delivery_address)
	{
		this.cpr_provider_delivery_address = cpr_provider_delivery_address;
	}

	public String getCpr_delivery_count_day()
	{
		return cpr_delivery_count_day;
	}

	public String getDeliveryCountDayInWords()
	{
		return NumberToWordsRussian.toWords(new BigDecimal(getCpr_delivery_count_day()));
	}

	public void setCpr_delivery_count_day(String cpr_delivery_count_day)
	{
		this.cpr_delivery_count_day = cpr_delivery_count_day;
		if (StringUtil.isEmpty(cpr_delivery_count_day))
		{
			this.cpr_delivery_count_day = "0";
		}
	}

	public String getCpr_donot_calculate_netto()
	{
		return cpr_donot_calculate_netto;
	}

	public void setCpr_donot_calculate_netto(String cpr_donot_calculate_netto)
	{
		this.cpr_donot_calculate_netto = cpr_donot_calculate_netto;
	}

	public boolean isCalculateNetto()
	{
		return StringUtil.isEmpty(getCpr_donot_calculate_netto());
	}

	public String getCpr_print_scale()
	{
		return cpr_print_scale;
	}

	public void setCpr_print_scale(String cpr_print_scale)
	{
		this.cpr_print_scale = cpr_print_scale;
	}

	public String getCpr_contract_scale()
	{
		return cpr_contract_scale;
	}

	public void setCpr_contract_scale(String cpr_contract_scale)
	{
		this.cpr_contract_scale = cpr_contract_scale;
	}

	public double getTotal()
	{
		return total;
	}

	public void setTotal(double total)
	{
		this.total = total;
	}

	public double getTotalPrint()
	{
		return totalPrint;
	}

	public void setTotalPrint(double totalPrint)
	{
		this.totalPrint = totalPrint;
	}

	public double getNdsPrint()
	{
		return ndsPrint;
	}

	public void setNdsPrint(double ndsPrint)
	{
		this.ndsPrint = ndsPrint;
	}

	public String getLprPriceBruttoHeader()
	{
		IActionContext context = ActionContext.threadInstance();
		try
		{
			String currencyTableName = getCurrencyTable() != null ? StringUtil.getString(getCurrencyTable().getName()) : "";
			return StringUtil.replace(StrutsUtil.getMessage(context, "CommercialProposalProduces.lpr_price_brutto"), "EUR", currencyTableName);
		}
		catch (Exception e)
		{
			log.error(e);
		}

		return "";
	}

	public String getLprPriceNettoHeader()
	{
		IActionContext context = ActionContext.threadInstance();
		try
		{
			String currencyTableName = getCurrencyTable() != null ? StringUtil.getString(getCurrencyTable().getName()) : "";
			return StringUtil.replace(StrutsUtil.getMessage(context, "CommercialProposalProduces.lpr_price_netto"), "EUR", currencyTableName);
		}
		catch (Exception e)
		{
			log.error(e);
		}

		return "";
	}

	public String getShow_unit()
	{
		return show_unit;
	}

	public void setShow_unit(String show_unit)
	{
		this.show_unit = show_unit;
	}

	public boolean isPrintPriceListBy()
	{
		return printPriceListBy;
	}

	public void setPrintPriceListBy(boolean printPriceListBy)
	{
		this.printPriceListBy = printPriceListBy;
	}

	public List<CommercialProposalProduce> getProduces()
	{
		return produces;
	}

	public void setProduces(List<CommercialProposalProduce> produces)
	{
		this.produces = produces;
	}

	public List<CommercialProposalTransport> getTransportLines()
	{
		return transportLines;
	}

	public void setTransportLines(List<CommercialProposalTransport> transportLines)
	{
		this.transportLines = transportLines;
	}

	public String getCpr_invoice_scale()
	{
		return cpr_invoice_scale;
	}

	public void setCpr_invoice_scale(String cpr_invoice_scale)
	{
		this.cpr_invoice_scale = cpr_invoice_scale;
	}

	public String getCpr_final_date_above()
	{
		return cpr_final_date_above;
	}

	public boolean isFinalDateAbove()
	{
		return !StringUtil.isEmpty(getCpr_final_date_above());
	}

	public void setCpr_final_date_above(String cpr_final_date_above)
	{
		this.cpr_final_date_above = cpr_final_date_above;
	}

	public void clear()
	{
		produces.clear();
		countItogRecord = 0;
	}

	private void formTransportList()
	{
		List<CommercialProposalTransport> transportsLocal = new ArrayList<CommercialProposalTransport>();

		if (StringUtil.isEmpty(getCpr_old_version()))
		{
			for (CommercialProposalProduce commercialProposalProduce : produces)
			{
				boolean foundStf = false;
				for (CommercialProposalTransport commercialProposalTransport : transportsLocal)
				{
					if (commercialProposalProduce.getStuffCategory() != null &&
									commercialProposalTransport.getStuffCategory() != null &&
									commercialProposalProduce.getStuffCategory().getId() != null &&
									commercialProposalProduce.getStuffCategory().getId().equals(commercialProposalTransport.getStuffCategory().getId()))
					{
						foundStf = true;
						break;
					}
				}

				if (!foundStf)
				{
					CommercialProposalTransport commercialProposalTransport = new CommercialProposalTransport();
					commercialProposalTransport.setStuffCategory(new StuffCategory(commercialProposalProduce.getStuffCategory()));
					commercialProposalTransport.setNumber(Integer.toString(transportsLocal.size() + 1));
					transportsLocal.add(commercialProposalTransport);
				}
			}
		}

		if (StringUtil.isEmpty(getCpr_old_version()) && transportsLocal.size() > 1)
		{
			for (CommercialProposalTransport commercialProposalTransport : transportsLocal)
			{
				CommercialProposalTransport commercialProposalTransportOld = findTransportByStfId(commercialProposalTransport.getStuffCategory().getId());
				if (null != commercialProposalTransportOld)
				{
					commercialProposalTransport.setTrn_sum(commercialProposalTransportOld.getTrn_sum());
				}
			}
			transportLines.clear();
			transportLines.addAll(transportsLocal);
		}
		else
		{
			transportLines.clear();
		}

		//не отмечено, что считаем по общей, но в рассчетах используем - поэтому подобьем по строкам
		double allTransport = 0.0;
		if (isGetTransportFromTable())
		{
			for (CommercialProposalTransport commercialProposalTransport : transportLines)
			{
				allTransport += commercialProposalTransport.getTrn_sum();
			}
			setCpr_sum_transport(allTransport);
		}
	}

	private double getSumTransportForLine(String stfId)
	{
		double transport = 0;
		//берем для строки
		if (isGetTransportFromTable())
		{
			CommercialProposalTransport commercialProposalTransport = findTransportByStfId(stfId);
			if (null != commercialProposalTransport)
			{
				transport = StringUtil.roundN(commercialProposalTransport.getTrn_sum(), 2);
			}
		}
		else //считаем от общей
		{
			transport = StringUtil.roundN(getCpr_sum_transport(), 2);
		}
		transport = StringUtil.roundN(transport, 2);

		return transport;
	}

	private double calcSumsInLine(CommercialProposalProduce commercialProposalProduce, boolean setData)
	{
		boolean fRoundPrint = getCurrency().isNeedRound();
		boolean fRound = getCurrencyTable().isNeedRound();

		double saleCost;
		double saleCostPrint;

		double lprCount;
		double lprPrice;
		double lprPricePrint;
		double lprCoeficient;
		double salePrice;
		double salePricePrint;

		lprCount = commercialProposalProduce.getLpr_count();
		double priceNetto;
		if (isCalculateNetto())
		{
			priceNetto = commercialProposalProduce.getLpr_price_brutto() * (1 - commercialProposalProduce.getLpr_discount() / 100);
			priceNetto = CommercialProposalProduce.getRoundSum(priceNetto, fRound);
			commercialProposalProduce.setLpr_price_netto(priceNetto);
		}
		else
		{
			priceNetto = commercialProposalProduce.getLpr_price_netto();
		}
		lprPrice = CommercialProposalProduce.getRoundSum(priceNetto, fRound);
		lprPricePrint = CommercialProposalProduce.getRoundSum(lprPrice * getCpr_course(), fRoundPrint);

		if (setData)
		{
			commercialProposalProduce.setLpr_price_netto(lprPrice);
			commercialProposalProduce.setLpr_price_netto_print(lprPricePrint);
		}
		lprCoeficient = commercialProposalProduce.getLpr_coeficient();

		salePrice = CommercialProposalProduce.getRoundSum(lprPrice * lprCoeficient, fRound);
		saleCost = CommercialProposalProduce.getRoundSum(salePrice * lprCount, fRound);

		salePricePrint = CommercialProposalProduce.getRoundSum(lprPricePrint * lprCoeficient, fRoundPrint);
		saleCostPrint = CommercialProposalProduce.getRoundSum(salePricePrint * lprCount, fRoundPrint);

		if (setData)
		{
			commercialProposalProduce.setSale_price(salePrice);
			commercialProposalProduce.setSale_cost(saleCost);

			commercialProposalProduce.setSale_price_print(salePricePrint);
			commercialProposalProduce.setSale_cost_print(saleCostPrint);
		}

		return saleCost;
	}

	private double getSumCostForLine(String stfId, double allSaleCostSum)
	{
		double saleCostSum = 0.0;
		//берем для строки
		if (isGetTransportFromTable())
		{
			if (StringUtil.isEmpty(stfId))
			{
				return 0.0;
			}
			for (CommercialProposalProduce commercialProposalProduce : produces)
			{
				if (commercialProposalProduce.getStuffCategory() != null
						&& !StringUtil.isEmpty(commercialProposalProduce.getStuffCategory().getId())
						&& commercialProposalProduce.getStuffCategory().getId().equals(stfId))
				{
					//здесь только считаем saleCostSum и данные в строках не переустанавливаем
					saleCostSum += calcSumsInLine(commercialProposalProduce, false);
				}
			}
			saleCostSum = StringUtil.roundN(saleCostSum, 2);
		}
		else //считаем от общей
		{
			saleCostSum = allSaleCostSum;
		}

		return saleCostSum;
	}

	private double getParckingTrancLine(CommercialProposalProduce commercialProposalProduce, double allSaleCostSum, double salePrice, boolean fRound)
	{
		double parckingTranc;

		String stfId = commercialProposalProduce.getStuffCategory() != null
				? commercialProposalProduce.getStuffCategory().getId()
				: null;
		double saleCostSumLine = getSumCostForLine(stfId, allSaleCostSum);
		if (saleCostSumLine == 0)
		{
			parckingTranc = 0;
		}
		else
		{
			parckingTranc = CommercialProposalProduce.getRoundSum(salePrice * getSumTransportForLine(stfId) / saleCostSumLine, fRound);
		}

		return parckingTranc;
	}

	private double getAssemblingLine(double allSaleCostSum, double salePrice, boolean fRound)
	{
		double assembling;

		if (allSaleCostSum == 0)
		{
			assembling = 0;
		}
		else
		{
			assembling = CommercialProposalProduce.getRoundSum(salePrice * StringUtil.roundN(getCpr_sum_assembling(), 2) / allSaleCostSum, fRound);
		}

		return assembling;
	}

	private double getTransportSumForLine(String stuffCategoryId)
	{
		double transport = 0;
		if (isGetTransportFromTable())
		{
			if (StringUtil.isEmpty(stuffCategoryId))
			{
				return 0.0;
			}
			for (CommercialProposalTransport commercialProposalTransport : transportLines)
			{
				if (commercialProposalTransport.getStuffCategory() != null
						&& !StringUtil.isEmpty(commercialProposalTransport.getStuffCategory().getId())
						&& commercialProposalTransport.getStuffCategory().getId().equals(stuffCategoryId))
					transport += StringUtil.roundN(commercialProposalTransport.getTrn_sum(), 2);
			}
		}
		else
		{
			transport += StringUtil.roundN(getCpr_sum_transport(), 2);
		}

		return transport;
	}

	private double getAllTransportSumForLine(String stuffCategoryId)
	{
		double saleCostParkingTransAll = 0;
		for (CommercialProposalProduce commercialProposalProduce : produces)
		{
			if (isGetTransportFromTable())
			{
				if (!StringUtil.isEmpty(stuffCategoryId)
						&& commercialProposalProduce.getStuffCategory() != null
						&& !StringUtil.isEmpty(commercialProposalProduce.getStuffCategory().getId())
						&& commercialProposalProduce.getStuffCategory().getId().equals(stuffCategoryId))
					saleCostParkingTransAll += StringUtil.roundN(commercialProposalProduce.getLpr_sale_cost_parking_trans(), 2);
			}
			else
				saleCostParkingTransAll += StringUtil.roundN(commercialProposalProduce.getLpr_sale_cost_parking_trans(), 2);
		}
		return saleCostParkingTransAll;
	}

	public void calculateInString()
	{
		String currencyId = getCurrency() == null ? null : getCurrency().getId();
		String currencyTableId = getCurrencyTable() == null ? null : getCurrencyTable().getId();
		boolean fRound = getCurrencyTable() != null && getCurrencyTable().isNeedRound();
		boolean fRoundPrint = getCurrency() != null && getCurrency().isNeedRound();

		for (int i = 0; i < produces.size(); i++) //начальная инициализация служебных полей
		{
			CommercialProposalProduce commercialProposalProduce = produces.get(i);

			commercialProposalProduce.setNumber(String.valueOf(i + 1));
			commercialProposalProduce.setDoubleSums(!StringUtil.equal(currencyId, currencyTableId));
			commercialProposalProduce.setNeedRoundPrint(fRoundPrint);
		}

		if (isReverseCalc())
		{
			double saleCostParkingTransAll = 0;
			for (CommercialProposalProduce commercialProposalProduce : produces)
			{
				saleCostParkingTransAll += StringUtil.roundN(commercialProposalProduce.getLpr_sale_cost_parking_trans(), 2);
			}

			for (CommercialProposalProduce commercialProposalProduce : produces)
			{
				double lprCount;
				double salePrice = 0;

				double parkingTrans;
				double salePriceParkingTrans;
				double saleCostParkingTrans;

				lprCount = commercialProposalProduce.getLpr_count();

				if (incoTermCaseA() || incoTermCaseC())
				{
					salePrice = CommercialProposalProduce.getRoundSum(commercialProposalProduce.getLpr_sale_price() / getCpr_course(), fRound);
				}

				if (incoTermCaseB() || incoTermCaseD() || incoTermCaseE())
				{
					saleCostParkingTrans = commercialProposalProduce.getLpr_sale_cost_parking_trans();
					parkingTrans = 0;
					if (saleCostParkingTransAll != 0 && lprCount != 0)
					{
						parkingTrans = CommercialProposalProduce.getRoundSum(
										getTransportSumForLine(commercialProposalProduce.getStuffCategory().getId()) * saleCostParkingTrans / getAllTransportSumForLine(commercialProposalProduce.getStuffCategory().getId()) / lprCount +
														StringUtil.roundN(getCpr_sum_assembling(), 2) * saleCostParkingTrans / saleCostParkingTransAll / lprCount,
										fRound);
					}
					commercialProposalProduce.setParking_trans(parkingTrans);
					if (incoTermCaseB())
					{
						salePriceParkingTrans = CommercialProposalProduce.getRoundSum(commercialProposalProduce.getLpr_sale_price() / getCpr_course(), fRound);
					}
					else
					{
						double salePriceParkingTransCustom = CommercialProposalProduce.getRoundSum(commercialProposalProduce.getLpr_sale_price() / getCpr_course(), fRound);
						double customPercent = commercialProposalProduce.getCustomPercent();
						customPercent = StringUtil.roundN(customPercent, 2);
						salePriceParkingTrans = CommercialProposalProduce.getRoundSum(salePriceParkingTransCustom / (1 + customPercent / 100 + 0.0015), fRound);
					}
					commercialProposalProduce.setSale_price_parking_trans(salePriceParkingTrans);

					salePrice = salePriceParkingTrans - parkingTrans;
				}
				commercialProposalProduce.setSale_price(salePrice);

				double priceNetto;
				if (isCalculateNetto())
				{
					priceNetto = commercialProposalProduce.getLpr_price_brutto() * (1 - commercialProposalProduce.getLpr_discount() / 100);
					priceNetto = CommercialProposalProduce.getRoundSum(priceNetto, fRound);
					commercialProposalProduce.setLpr_price_netto(priceNetto);
				}
				else
				{
					priceNetto = commercialProposalProduce.getLpr_price_netto();
				}

				double lprCoefficient = salePrice / StringUtil.roundN(priceNetto, 2);
				lprCoefficient = StringUtil.roundN(lprCoefficient, 4);
				commercialProposalProduce.setLpr_coeficient(lprCoefficient);

				//пощли считать прямым счетом
				double saleCost = CommercialProposalProduce.getRoundSum(salePrice * lprCount, fRound);
				commercialProposalProduce.setSale_cost(saleCost);

				//суммы для печати
				double salePricePrint;
				if (incoTermCaseA() || incoTermCaseC())
				{
					salePricePrint = commercialProposalProduce.getLpr_sale_price();
				}
				else
				{
					salePricePrint = CommercialProposalProduce.getRoundSum(commercialProposalProduce.getSale_price() * cpr_course, fRoundPrint);
				}
				commercialProposalProduce.setSale_price_print(salePricePrint);

				double saleCostPrint = CommercialProposalProduce.getRoundSum(commercialProposalProduce.getSale_price_print() * lprCount, fRoundPrint);
				commercialProposalProduce.setSale_cost_print(saleCostPrint);
			}

			double allSaleCostSum = 0.0; //нужна для подсчета относительных сумм
			for (CommercialProposalProduce commercialProposalProduce : produces)
			{
				allSaleCostSum += commercialProposalProduce.getSale_cost();
			}
			allSaleCostSum = StringUtil.roundN(allSaleCostSum, 2);

			for (CommercialProposalProduce commercialProposalProduce : produces)
			{
				double lprCount;
				double salePrice;
				double customPercent;

				double parckingTranc;
				double assembling;
				double salePriceParkingTrans;
				double saleCostParkingTrans;
				double customDuty;
				double salePriceParkingTransCustom;
				double saleCostParkingTransCustom;

				double salePriceParkingTransPrint;
				double saleCostParkingTransPrint;
				double salePriceParkingTransCustomPrint;
				double saleCostParkingTransCustomPrint;

				lprCount = commercialProposalProduce.getLpr_count();
				salePrice = commercialProposalProduce.getSale_price();

				parckingTranc = getParckingTrancLine(commercialProposalProduce, allSaleCostSum, salePrice, fRound);
				assembling = getAssemblingLine(allSaleCostSum, salePrice, fRound);
				parckingTranc += assembling;
				//в Parking_trans также затраты на сборку
				parckingTranc = CommercialProposalProduce.getRoundSum(parckingTranc, fRound);
				if (incoTermCaseA() || incoTermCaseC())
				{
					//в Parking_trans также затраты на сборку
					commercialProposalProduce.setParking_trans(parckingTranc);

					salePriceParkingTrans = CommercialProposalProduce.getRoundSum(salePrice + commercialProposalProduce.getParking_trans(), fRound);
					commercialProposalProduce.setSale_price_parking_trans(salePriceParkingTrans);

					saleCostParkingTrans = CommercialProposalProduce.getRoundSum(salePriceParkingTrans * lprCount, fRound);
					commercialProposalProduce.setSale_cost_parking_trans(saleCostParkingTrans);

					salePriceParkingTransPrint = CommercialProposalProduce.getRoundSum(commercialProposalProduce.getSale_price_parking_trans() * cpr_course, fRoundPrint);
				}
				else
				{
					salePriceParkingTrans = commercialProposalProduce.getSale_price_parking_trans();
					saleCostParkingTrans = commercialProposalProduce.getLpr_sale_cost_parking_trans();
					commercialProposalProduce.setSale_cost_parking_trans(saleCostParkingTrans);

					if (incoTermCaseB())
					{
						salePriceParkingTransPrint = commercialProposalProduce.getLpr_sale_price();
					}
					else
					{
						salePriceParkingTransPrint = CommercialProposalProduce.getRoundSum(salePriceParkingTrans * cpr_course, fRoundPrint);
					}
				}
				commercialProposalProduce.setSale_price_parking_trans_print(salePriceParkingTransPrint);

				saleCostParkingTransPrint = CommercialProposalProduce.getRoundSum(commercialProposalProduce.getSale_price_parking_trans_print() * lprCount, fRoundPrint);
				commercialProposalProduce.setSale_cost_parking_trans_print(saleCostParkingTransPrint);

				customPercent = commercialProposalProduce.getCustomPercent();
				customPercent = StringUtil.roundN(customPercent, 2);

				customDuty = CommercialProposalProduce.getRoundSum(saleCostParkingTrans * customPercent / 100 + saleCostParkingTrans * 0.0015, fRound);
				commercialProposalProduce.setCustom_duty(customDuty);

				if (!incoTermCaseD() && !incoTermCaseE())
				{
					salePriceParkingTransCustom = 0.0;
					if (lprCount != 0)
					{
						salePriceParkingTransCustom = CommercialProposalProduce.getRoundSum(salePriceParkingTrans + customDuty / lprCount, fRound);
					}
					salePriceParkingTransCustomPrint = CommercialProposalProduce.getRoundSum(commercialProposalProduce.getSale_price_parking_trans_custom() * cpr_course, fRoundPrint);
				}
				else
				{
					salePriceParkingTransCustom = CommercialProposalProduce.getRoundSum(commercialProposalProduce.getLpr_sale_price() / getCpr_course(), fRound);
					salePriceParkingTransCustomPrint = commercialProposalProduce.getLpr_sale_price();
				}
				commercialProposalProduce.setSale_price_parking_trans_custom(salePriceParkingTransCustom);
				commercialProposalProduce.setSale_price_parking_trans_custom_print(salePriceParkingTransCustomPrint);

				saleCostParkingTransCustom = CommercialProposalProduce.getRoundSum(salePriceParkingTransCustom * lprCount, fRound);
				commercialProposalProduce.setSale_cost_parking_trans_custom(saleCostParkingTransCustom);

				saleCostParkingTransCustomPrint = CommercialProposalProduce.getRoundSum(commercialProposalProduce.getSale_price_parking_trans_custom_print() * lprCount, fRoundPrint);
				commercialProposalProduce.setSale_cost_parking_trans_custom_print(saleCostParkingTransCustomPrint);
			}
		}
		else
		{
			printPriceListBy = false;

			double allSaleCostSum = 0.0;
			for (CommercialProposalProduce commercialProposalProduce : produces)
			{
				//здесь меняем данные в троках
				allSaleCostSum += calcSumsInLine(commercialProposalProduce, true);
			}
			allSaleCostSum = StringUtil.roundN(allSaleCostSum, 2);

			for (CommercialProposalProduce commercialProposalProduce : produces)
			{
				double lprCount;
				double salePrice;
				double customPercent;

				double parckingTranc;
				double assembling;
				double salePriceParkingTrans;
				double saleCostParkingTrans;
				double customDuty;
				double salePriceParkingTransCustom;
				double saleCostParkingTransCustom;

				double salePricePrint;
				double saleCostPrint;
				double salePriceParkingTransPrint;
				double saleCostParkingTransPrint;
				double salePriceParkingTransCustomPrint;
				double saleCostParkingTransCustomPrint;

				customPercent = commercialProposalProduce.getCustomPercent();
				customPercent = StringUtil.roundN(customPercent, 2);

				lprCount = commercialProposalProduce.getLpr_count();
				lprCount = StringUtil.roundN(lprCount, 2);
				salePrice = commercialProposalProduce.getSale_price();
				salePrice = StringUtil.roundN(salePrice, 2);

				salePricePrint = CommercialProposalProduce.getRoundSum(commercialProposalProduce.getSale_price() * cpr_course, fRoundPrint);
				commercialProposalProduce.setSale_price_print(salePricePrint);
				saleCostPrint = CommercialProposalProduce.getRoundSum(commercialProposalProduce.getSale_price_print() * lprCount, fRoundPrint);
				commercialProposalProduce.setSale_cost_print(saleCostPrint);

				parckingTranc = getParckingTrancLine(commercialProposalProduce, allSaleCostSum, salePrice, fRound);
				assembling = getAssemblingLine(allSaleCostSum, salePrice, fRound);
				parckingTranc += assembling;
				//в Parking_trans также затраты на сборку
				commercialProposalProduce.setParking_trans(parckingTranc);

				salePriceParkingTrans = CommercialProposalProduce.getRoundSum(salePrice + commercialProposalProduce.getParking_trans(), fRound);
				commercialProposalProduce.setSale_price_parking_trans(salePriceParkingTrans);

				saleCostParkingTrans = CommercialProposalProduce.getRoundSum(salePriceParkingTrans * lprCount, fRound);
				commercialProposalProduce.setSale_cost_parking_trans(saleCostParkingTrans);

				salePriceParkingTransPrint = CommercialProposalProduce.getRoundSum(commercialProposalProduce.getSale_price_parking_trans() * cpr_course, fRoundPrint);
				commercialProposalProduce.setSale_price_parking_trans_print(salePriceParkingTransPrint);
				saleCostParkingTransPrint = CommercialProposalProduce.getRoundSum(commercialProposalProduce.getSale_price_parking_trans_print() * lprCount, fRoundPrint);
				commercialProposalProduce.setSale_cost_parking_trans_print(saleCostParkingTransPrint);

        /*
        * Если отмечен "КП составляется по товару, который лежит на складе в Минске", то:
        * В табличной части:
        * в колонке "Таможенная пошлина + таможенное оформление" во всех строках значение =0 (соответственно, этот ноль учитывается при расчёте следующих колонок)
        * */
				if (isAssembleMinskStore())
				{
					customDuty = 0;
				}
				else
				{
					customDuty = CommercialProposalProduce.getRoundSum(saleCostParkingTrans * customPercent / 100 + saleCostParkingTrans * 0.0015, fRound);
				}
				commercialProposalProduce.setCustom_duty(customDuty);

				salePriceParkingTransCustom = 0.0;
				if (lprCount != 0)
				{
					if (!isAssembleMinskStore()) //иначе - вводится в таблице
					{
						salePriceParkingTransCustom = CommercialProposalProduce.getRoundSum(salePriceParkingTrans + customDuty / lprCount, fRound);
					}
				}
				if (!isAssembleMinskStore())
				{
					commercialProposalProduce.setSale_price_parking_trans_custom(salePriceParkingTransCustom);
				}

				if (isAssembleMinskStore()) //если сборка в Минске, счатаем дополнительные поля
				{
					double a = commercialProposalProduce.getSale_price_parking_trans_custom();
					double b = commercialProposalProduce.getLpc_cost_one_by();
					double c = commercialProposalProduce.getLpc_price_list_by();
					commercialProposalProduce.setExtra_percent(0);
					//=a/b*100-100
					if (b != 0)
					{
						commercialProposalProduce.setExtra_percent(a / b * 100 - 100);
					}

					commercialProposalProduce.setDiscount_percent(0);
					//=(1-a/c)*100
					if (c != 0)
					{
						commercialProposalProduce.setDiscount_percent((1 - a / c) * 100);
						if (!isFreePrices() && commercialProposalProduce.getDiscount_percent() != 0)
						{
							printPriceListBy = true;
						}
					}
					salePriceParkingTransCustom = a;
				}

				saleCostParkingTransCustom = CommercialProposalProduce.getRoundSum(salePriceParkingTransCustom * lprCount, fRound);
				commercialProposalProduce.setSale_cost_parking_trans_custom(saleCostParkingTransCustom);

				salePriceParkingTransCustomPrint = CommercialProposalProduce.getRoundSum(commercialProposalProduce.getSale_price_parking_trans_custom() * cpr_course, fRoundPrint);
				commercialProposalProduce.setSale_price_parking_trans_custom_print(salePriceParkingTransCustomPrint);
				saleCostParkingTransCustomPrint = CommercialProposalProduce.getRoundSum(commercialProposalProduce.getSale_price_parking_trans_custom_print() * lprCount, fRoundPrint);
				commercialProposalProduce.setSale_cost_parking_trans_custom_print(saleCostParkingTransCustomPrint);
			}
		}

		//НДС можно в конце считать - общий кусок
		for (CommercialProposalProduce commercialProposalProduce : produces)
		{
			double saleCostParkingTransCustom;
			double nds;
			double costNDS;

			double saleCostParkingTransCustomPrint;
			double ndsPrint;
			double costNDSPrint;

			saleCostParkingTransCustom = commercialProposalProduce.getSale_cost_parking_trans_custom();

			nds = CommercialProposalProduce.getRoundSum(saleCostParkingTransCustom * getCpr_nds() / 100, fRound);
			commercialProposalProduce.setNds(nds);

			costNDS = CommercialProposalProduce.getRoundSum(saleCostParkingTransCustom + nds, fRound);
			commercialProposalProduce.setCost_nds(costNDS);

			saleCostParkingTransCustomPrint = commercialProposalProduce.getSale_cost_parking_trans_custom_print();
			ndsPrint = CommercialProposalProduce.getRoundSum(saleCostParkingTransCustomPrint * getCpr_nds() / 100, fRoundPrint);
			commercialProposalProduce.setNds_print(ndsPrint);

			costNDSPrint = saleCostParkingTransCustomPrint + ndsPrint;
			commercialProposalProduce.setCost_nds_print(costNDSPrint);
		}
	}

	public static CommercialProposalProduce getEmptyProduce()
	{
		CommercialProposalProduce commercialProposalProduce = new CommercialProposalProduce();
		commercialProposalProduce.setNumber("");
		commercialProposalProduce.setLpr_produce_name("");
		commercialProposalProduce.setLpr_catalog_num("");
		commercialProposalProduce.setLpr_price_brutto(0.0);
		commercialProposalProduce.setLpr_discount(0.0);
		commercialProposalProduce.setLpr_price_netto(0.0);
		commercialProposalProduce.setLpr_count(0.0);
		commercialProposalProduce.setCustomCode(new CustomCode());
		commercialProposalProduce.getCustomCode().setCode("");
		commercialProposalProduce.getCustomCode().setCustom_percent(Double.NaN);
		commercialProposalProduce.setLpr_coeficient(0.0);
		commercialProposalProduce.setSale_price(0.0);
		commercialProposalProduce.setSale_cost(0.0);
		commercialProposalProduce.setParking_trans(0.0);
		commercialProposalProduce.setSale_price_parking_trans(0.0);
		commercialProposalProduce.setSale_cost_parking_trans(0.0);
		commercialProposalProduce.setCustom_duty(0.0);
		commercialProposalProduce.setSale_price_parking_trans_custom(0.0);
		commercialProposalProduce.setSale_cost_parking_trans_custom(0.0);
		commercialProposalProduce.setNds(0.0);
		commercialProposalProduce.setCost_nds(0.0);

		return commercialProposalProduce;
	}

	public int calculate()
	{
		IActionContext context = ActionContext.threadInstance();
		String totalString = "";
		String titleSum = "";
		String titleSumPrint;
		String titlePackTrans = "";
		String titleDuty = "";
		String titleNDS = "";
		String titleTotalSum = "";
		String titleTotalSumPrint;
		try
		{
			totalString = StrutsUtil.getMessage(context, "CommercialProposal.total");
			titleSum = StrutsUtil.getMessage(context, "CommercialProposal.sum");
			titlePackTrans = StrutsUtil.getMessage(context, "CommercialProposal.pack_trans");
			titleDuty = StrutsUtil.getMessage(context, "CommercialProposal.duty");
			titleNDS = StrutsUtil.getMessage(context, "CommercialProposal.nds");
			if (StringUtil.isEmpty(getCpr_nds_by_string()))
			{
				titleTotalSum = StrutsUtil.getMessage(context, "CommercialProposal.total_sum");
			}
			else
			{
				titleTotalSum = StrutsUtil.getMessage(context, "CommercialProposal.total_sum_by_string");
			}
		}
		catch (Exception e)
		{
			log.error(e);
		}

		String currencyId = getCurrency() == null ? null : getCurrency().getId();
		String currencyTableId = getCurrencyTable() == null ? null : getCurrencyTable().getId();
		boolean doubleSums = !StringUtil.equal(currencyId, currencyTableId);
		boolean incoTermCaseA = incoTermCaseA();
		boolean fRoundPrint = getCurrency() != null && getCurrency().isNeedRound();
		boolean fRound = getCurrencyTable() != null && getCurrencyTable().isNeedRound();

		String currencyName = getCurrency() != null ? StringUtil.getString(getCurrency().getName()) : "";
		String currencyTableName = getCurrencyTable() != null ? StringUtil.getString(getCurrencyTable().getName()) : "";

		//сначала присваиваем для печати
		titleSumPrint = StringUtil.replace(titleSum, "EUR", currencyName);
		titleTotalSumPrint = StringUtil.replace(titleTotalSum, "EUR", currencyName);

		titleSum = StringUtil.replace(titleSum, "EUR", currencyTableName);
		titleTotalSum = StringUtil.replace(titleTotalSum, "EUR", currencyTableName);

		for (int i = 0; i < countItogRecord; i++)
		{
			produces.remove(produces.size() - 1);
		}

		formTransportList();

		calculateInString(); //сначала считаем в строках

		countItogRecord = 1;

		double sumAll;
		double packTransAll;
		double dutyAll;
		double ndsAll;
		total = 0.0;

		double sumAllPrint;
		double packTransAllPrint;
		double dutyAllPrint;
		double ndsAllPrint;
		setTotalPrint(0.0);

		double saleCost = 0.0;
		double salePriceParkingTrans = 0.0;
		double saleCostParkingTrans = 0.0;
		double customDuty = 0.0;
		double saleCostParkingTransCustom = 0.0;
		double nds = 0.0;
		double costNDS = 0.0;

		double saleCostPrint = 0.0;
		double salePriceParkingTransPrint = 0.0;
		double saleCostParkingTransPrint = 0.0;
		double customDutyPrint = 0.0;
		double saleCostParkingTransCustomPrint = 0.0;
		ndsPrint = 0.0;
		double costNDSPrint = 0.0;
		double sumLprPriceNetto = 0.0;

		for (CommercialProposalProduce commercialProposalProduce : produces)
		{
			sumLprPriceNetto += StringUtil.roundN(commercialProposalProduce.getLpr_count(), 2) * StringUtil.roundN(commercialProposalProduce.getLpr_price_netto(), 2);

			saleCost += StringUtil.roundN(commercialProposalProduce.getSale_cost(), 2);
			salePriceParkingTrans += StringUtil.roundN(commercialProposalProduce.getSale_price_parking_trans(), 2);
			saleCostParkingTrans += StringUtil.roundN(commercialProposalProduce.getSale_cost_parking_trans(), 2);
			customDuty += StringUtil.roundN(commercialProposalProduce.getCustom_duty(), 2);
			saleCostParkingTransCustom += StringUtil.roundN(commercialProposalProduce.getSale_cost_parking_trans_custom(), 2);
			nds += StringUtil.roundN(commercialProposalProduce.getNds(), 2);
			costNDS += StringUtil.roundN(commercialProposalProduce.getCost_nds(), 2);

			saleCostPrint += StringUtil.roundN(commercialProposalProduce.getSale_cost_print(), 2);
			salePriceParkingTransPrint += StringUtil.roundN(commercialProposalProduce.getSale_price_parking_trans_print(), 2);
			saleCostParkingTransPrint += StringUtil.roundN(commercialProposalProduce.getSale_cost_parking_trans_print(), 2);
			customDutyPrint += StringUtil.roundN(commercialProposalProduce.getCustom_duty_print(), 2);
			saleCostParkingTransCustomPrint += StringUtil.roundN(commercialProposalProduce.getSale_cost_parking_trans_custom_print(), 2);
			ndsPrint += StringUtil.roundN(commercialProposalProduce.getNds_print(), 2);
			costNDSPrint += StringUtil.roundN(commercialProposalProduce.getCost_nds_print(), 2);
		}

		CommercialProposalProduce commercialProposalProduce = getEmptyProduce();
		commercialProposalProduce.setLpr_produce_name(totalString);
		commercialProposalProduce.setItogLine(!isAssembleMinskStore());
		commercialProposalProduce.setSumLprPrice(sumLprPriceNetto);
		commercialProposalProduce.setTotalText(totalString);
		commercialProposalProduce.setDoubleSums(doubleSums);
		commercialProposalProduce.setIncoTermCaseA(incoTermCaseA);
		commercialProposalProduce.setNeedRoundPrint(fRoundPrint);

		if (fRound)
		{
			saleCost = Math.round(saleCost);
			salePriceParkingTrans = Math.round(salePriceParkingTrans);
			saleCostParkingTrans = Math.round(saleCostParkingTrans);
			customDuty = Math.round(customDuty);
			saleCostParkingTransCustom = Math.round(saleCostParkingTransCustom);
			nds = Math.round(nds);
			costNDS = Math.round(costNDS);
		}
		if (fRoundPrint)
		{
			saleCostPrint = Math.round(saleCostPrint);
			salePriceParkingTransPrint = Math.round(salePriceParkingTransPrint);
			saleCostParkingTransPrint = Math.round(saleCostParkingTransPrint);
			customDutyPrint = Math.round(customDutyPrint);
			saleCostParkingTransCustomPrint = Math.round(saleCostParkingTransCustomPrint);
			ndsPrint = Math.round(ndsPrint);
			costNDSPrint = Math.round(costNDSPrint);
		}
		commercialProposalProduce.setSale_cost(saleCost);
		commercialProposalProduce.setParking_trans(0.0);
		commercialProposalProduce.setSale_price_parking_trans(salePriceParkingTrans);
		commercialProposalProduce.setSale_cost_parking_trans(saleCostParkingTrans);
		commercialProposalProduce.setCustom_duty(customDuty);
		commercialProposalProduce.setSale_price_parking_trans_custom(0.0);
		commercialProposalProduce.setSale_cost_parking_trans_custom(saleCostParkingTransCustom);
		commercialProposalProduce.setNds(nds);
		commercialProposalProduce.setCost_nds(costNDS);

		commercialProposalProduce.setSale_cost_print(saleCostPrint);
		commercialProposalProduce.setParking_trans_print(0.0);
		commercialProposalProduce.setSale_price_parking_trans_print(salePriceParkingTransPrint);
		commercialProposalProduce.setSale_cost_parking_trans_print(saleCostParkingTransPrint);
		commercialProposalProduce.setCustom_duty_print(customDutyPrint);
		commercialProposalProduce.setSale_price_parking_trans_custom_print(0.0);
		commercialProposalProduce.setSale_cost_parking_trans_custom_print(saleCostParkingTransCustomPrint);
		commercialProposalProduce.setNds_print(ndsPrint);
		commercialProposalProduce.setCost_nds_print(costNDSPrint);

		produces.add(commercialProposalProduce);

		if (!StringUtil.isEmpty(getPriceCondition().getName()) &&
						!StringUtil.isEmpty(getDeliveryCondition().getName()))
		{
			if (incoTermCaseA())
			{
				setInclude_exps(includeNo);

				CommercialProposalProduce prdSum = getEmptyProduce();
				prdSum.setLpr_produce_name(titleSum);
				prdSum.setTotalText(titleSumPrint);
				prdSum.setDoubleSums(doubleSums);
				prdSum.setIncoTermCaseA(true);
				prdSum.setNeedRoundPrint(fRoundPrint);

				sumAll = saleCost;
				sumAllPrint = saleCostPrint;
				prdSum.setSale_cost(sumAll);
				prdSum.setSale_cost_print(sumAllPrint);
				produces.add(prdSum);

				CommercialProposalProduce prdPackTrans = getEmptyProduce();
				prdPackTrans.setLpr_produce_name(titlePackTrans + " " + getDeliveryCondition().getNameExtended() + " " + getCpr_delivery_address());
				prdPackTrans.setTotalText(titlePackTrans + " " + getDeliveryCondition().getNameExtended() + " " + getCpr_delivery_address());
				prdPackTrans.setDoubleSums(doubleSums);
				prdPackTrans.setIncoTermCaseA(true);
				prdPackTrans.setNeedRoundPrint(fRoundPrint);

				packTransAll = CommercialProposalProduce.getRoundSum(getCpr_sum_transport() + getCpr_sum_assembling(), fRound);
				packTransAllPrint = CommercialProposalProduce.getRoundSum(packTransAll * cpr_course, fRoundPrint);
				prdPackTrans.setSale_cost(packTransAll);
				prdPackTrans.setSale_cost_print(packTransAllPrint);
				produces.add(prdPackTrans);

				CommercialProposalProduce prdTotalSum = getEmptyProduce();
				prdTotalSum.setLpr_produce_name(titleTotalSum);
				prdTotalSum.setTotalText(titleTotalSumPrint);
				prdTotalSum.setDoubleSums(doubleSums);
				prdTotalSum.setIncoTermCaseA(true);
				prdTotalSum.setNeedRoundPrint(fRoundPrint);

				total = saleCost + packTransAll;
				setTotalPrint(saleCostPrint + packTransAllPrint);
				setCpr_summ(getTotalPrint());
				prdTotalSum.setSale_cost(total);
				prdTotalSum.setSale_cost_print(getTotalPrint());
				produces.add(prdTotalSum);

				countItogRecord = countItogRecord + 3;
				type = 1;
			}

			if (incoTermCaseB())
			{
				setInclude_exps(includeYes);

				CommercialProposalProduce prdTotalSum = getEmptyProduce();
				prdTotalSum.setLpr_produce_name(titleTotalSum);
				prdTotalSum.setTotalText(titleTotalSumPrint);
				prdTotalSum.setDoubleSums(doubleSums);
				prdTotalSum.setIncoTermCaseA(false);
				prdTotalSum.setNeedRoundPrint(fRoundPrint);

				total = saleCostParkingTrans;
				setTotalPrint(saleCostParkingTransPrint);
				setCpr_summ(getTotalPrint());
				prdTotalSum.setSale_cost_parking_trans(total);
				prdTotalSum.setSale_cost_parking_trans_print(getTotalPrint());
				produces.add(prdTotalSum);

				countItogRecord = countItogRecord + 1;
				type = 2;
			}

			if (incoTermCaseC())
			{
				setInclude_exps(includeNo);

				CommercialProposalProduce prdSum = getEmptyProduce();
				prdSum.setLpr_produce_name(titleSum);
				prdSum.setTotalText(titleSumPrint);
				prdSum.setDoubleSums(doubleSums);
				prdSum.setIncoTermCaseA(false);
				prdSum.setNeedRoundPrint(fRoundPrint);

				sumAll = saleCost;
				sumAllPrint = saleCostPrint;
				prdSum.setSale_cost(sumAll);
				prdSum.setSale_cost_print(sumAllPrint);
				produces.add(prdSum);

				CommercialProposalProduce prdPackTrans = getEmptyProduce();
				prdPackTrans.setLpr_produce_name(titlePackTrans + " " + getDeliveryCondition().getName() + " " + getCpr_delivery_address());
				prdPackTrans.setTotalText(titlePackTrans + " " + getDeliveryCondition().getName() + " " + getCpr_delivery_address());
				prdPackTrans.setDoubleSums(doubleSums);
				prdPackTrans.setIncoTermCaseA(false);
				prdPackTrans.setNeedRoundPrint(fRoundPrint);

				packTransAll = CommercialProposalProduce.getRoundSum(getCpr_sum_transport() + getCpr_sum_assembling(), fRound);
				packTransAllPrint = CommercialProposalProduce.getRoundSum(packTransAll * cpr_course, fRoundPrint);
				prdPackTrans.setSale_cost(packTransAll);
				prdPackTrans.setSale_cost_print(packTransAllPrint);
				produces.add(prdPackTrans);

				CommercialProposalProduce prdDuty = getEmptyProduce();
				prdDuty.setLpr_produce_name(titleDuty);
				prdDuty.setTotalText(titleDuty);
				prdDuty.setDoubleSums(doubleSums);
				prdDuty.setIncoTermCaseA(false);
				prdDuty.setNeedRoundPrint(fRoundPrint);

				dutyAll = customDuty;
				dutyAllPrint = CommercialProposalProduce.getRoundSum(dutyAll * cpr_course, fRoundPrint);
				prdDuty.setSale_cost(dutyAll);
				prdDuty.setSale_cost_print(dutyAllPrint);
				produces.add(prdDuty);

				CommercialProposalProduce prdNDS = getEmptyProduce();
				prdNDS.setLpr_produce_name(titleNDS);
				prdNDS.setTotalText(titleNDS);
				prdNDS.setDoubleSums(doubleSums);
				prdNDS.setIncoTermCaseA(false);
				prdNDS.setNeedRoundPrint(fRoundPrint);

				ndsAll = getCpr_nds();
				ndsAllPrint = getCpr_nds();
				ndsAll = CommercialProposalProduce.getRoundSum((sumAll + packTransAll + dutyAll) * ndsAll / 100, fRound);
				ndsAllPrint = CommercialProposalProduce.getRoundSum((sumAllPrint + packTransAllPrint + dutyAllPrint) * ndsAllPrint / 100, fRoundPrint);
				prdNDS.setSale_cost(ndsAll);
				prdNDS.setSale_cost_print(ndsAllPrint);
				produces.add(prdNDS);

				CommercialProposalProduce prdTotalSum = getEmptyProduce();
				prdTotalSum.setLpr_produce_name(titleTotalSum);
				prdTotalSum.setTotalText(titleTotalSumPrint);
				prdTotalSum.setDoubleSums(doubleSums);
				prdTotalSum.setIncoTermCaseA(false);
				prdTotalSum.setNeedRoundPrint(fRoundPrint);

				total = sumAll + packTransAll + dutyAll + ndsAll;
				setTotalPrint(sumAllPrint + packTransAllPrint + dutyAllPrint + ndsAllPrint);
				setCpr_summ(getTotalPrint());
				prdTotalSum.setSale_cost(total);
				prdTotalSum.setSale_cost_print(getTotalPrint());
				produces.add(prdTotalSum);

				countItogRecord = countItogRecord + 5;
				type = 3;
			}

			if (incoTermCaseD())
			{
				setInclude_exps(includeYes);

				CommercialProposalProduce prdSum = getEmptyProduce();
				prdSum.setLpr_produce_name(titleSum);
				prdSum.setTotalText(titleSumPrint);
				prdSum.setDoubleSums(doubleSums);
				prdSum.setIncoTermCaseA(false);
				prdSum.setNeedRoundPrint(fRoundPrint);

				sumAll = saleCostParkingTransCustom;
				sumAllPrint = saleCostParkingTransCustomPrint;
				prdSum.setSale_cost_parking_trans_custom(sumAll);
				prdSum.setSale_cost_parking_trans_custom_print(sumAllPrint);
				produces.add(prdSum);

				CommercialProposalProduce prdNDS = getEmptyProduce();
				prdNDS.setLpr_produce_name(titleNDS);
				prdNDS.setTotalText(titleNDS);
				prdNDS.setDoubleSums(doubleSums);
				prdNDS.setIncoTermCaseA(false);
				prdNDS.setNeedRoundPrint(fRoundPrint);

				ndsAll = getCpr_nds();
				ndsAllPrint = getCpr_nds();
				ndsAll = CommercialProposalProduce.getRoundSum(saleCostParkingTransCustom * ndsAll / 100, fRound);
				ndsAllPrint = CommercialProposalProduce.getRoundSum(saleCostParkingTransCustomPrint * ndsAllPrint / 100, fRoundPrint);
				prdNDS.setSale_cost_parking_trans_custom(ndsAll);
				prdNDS.setSale_cost_parking_trans_custom_print(ndsAllPrint);
				produces.add(prdNDS);

				CommercialProposalProduce prdTotalSum = getEmptyProduce();
				prdTotalSum.setLpr_produce_name(titleTotalSum);
				prdTotalSum.setTotalText(titleTotalSumPrint);
				prdTotalSum.setDoubleSums(doubleSums);
				prdTotalSum.setIncoTermCaseA(false);
				prdTotalSum.setNeedRoundPrint(fRoundPrint);

				total = saleCostParkingTransCustom + ndsAll;
				setTotalPrint(saleCostParkingTransCustomPrint + ndsAllPrint);
				setCpr_summ(getTotalPrint());
				prdTotalSum.setSale_cost_parking_trans_custom(total);
				prdTotalSum.setSale_cost_parking_trans_custom_print(getTotalPrint());
				produces.add(prdTotalSum);

				countItogRecord = countItogRecord + 3;
				type = 4;
			}

			if (incoTermCaseE())
			{
				setInclude_exps(includeYes);

				CommercialProposalProduce prdSum = getEmptyProduce();
				prdSum.setLpr_produce_name(titleTotalSum);
				prdSum.setTotalText(titleTotalSumPrint);
				prdSum.setDoubleSums(doubleSums);
				prdSum.setIncoTermCaseA(false);
				prdSum.setNeedRoundPrint(fRoundPrint);

				prdSum.setSale_cost_parking_trans_custom(saleCostParkingTransCustom);
				prdSum.setSale_cost_parking_trans_custom_print(saleCostParkingTransCustomPrint);

				prdSum.setNds(nds);
				prdSum.setNds_print(ndsPrint);

				sumAll = costNDS;
				sumAllPrint = costNDSPrint;
				prdSum.setCost_nds(sumAll);
				prdSum.setCost_nds_print(sumAllPrint);

				produces.add(prdSum);

				total = sumAll;
				setTotalPrint(sumAllPrint);
				setCpr_summ(getTotalPrint());

				countItogRecord = countItogRecord + 1;
				type = 5;
			}
		}

		setOutValue();
		return countItogRecord;
	}

	public void setOutValue()
	{
		for (CommercialProposalProduce commercialProposalProduce : produces)
		{
			if (type == 1)
				commercialProposalProduce.setOutValue(commercialProposalProduce.getSale_cost_print());
			else if (type == 2)
				commercialProposalProduce.setOutValue(commercialProposalProduce.getSale_cost_parking_trans_print());
			else if (type == 3)
				commercialProposalProduce.setOutValue(commercialProposalProduce.getSale_cost_print());
			else if (type == 4)
				commercialProposalProduce.setOutValue(commercialProposalProduce.getSale_cost_parking_trans_custom_print());
			else if (type == 5)
				commercialProposalProduce.setOutValue(commercialProposalProduce.getSale_cost_parking_trans_custom_print());

			//для того, чтобы если нажать обратный счет посчитало корректно
			if (!isReverseCalc())
			{
				if (type == 1)
					commercialProposalProduce.setLpr_sale_price(commercialProposalProduce.getSale_price_print());
				else if (type == 2)
					commercialProposalProduce.setLpr_sale_price(commercialProposalProduce.getSale_price_parking_trans_print());
				else if (type == 3)
					commercialProposalProduce.setLpr_sale_price(commercialProposalProduce.getSale_price_print());
				else if (type == 4)
					commercialProposalProduce.setLpr_sale_price(commercialProposalProduce.getSale_price_parking_trans_custom_print());
				else if (type == 5)
					commercialProposalProduce.setLpr_sale_price(commercialProposalProduce.getSale_price_parking_trans_custom_print());
			}
		}
	}

	public boolean incoTermCaseE()
	{
		if (StringUtil.isEmpty(getPriceCondition().getName()) ||
						StringUtil.isEmpty(getDeliveryCondition().getName()))
		{
			return false;
		}

		return (
						getPriceCondition().getName().equalsIgnoreCase(IncoTerm.DDP) && getDeliveryCondition().getName().equalsIgnoreCase(IncoTerm.DDP) ||
										getPriceCondition().getName().equalsIgnoreCase(IncoTerm.DDP_2010) && getDeliveryCondition().getName().equalsIgnoreCase(IncoTerm.DDP_2010)
		) &&
						"1".equals(cpr_nds_by_string);
	}

	public boolean incoTermCaseD()
	{
		if (StringUtil.isEmpty(getPriceCondition().getName()) ||
						StringUtil.isEmpty(getDeliveryCondition().getName()))
		{
			return false;
		}

		return (
						getPriceCondition().getName().equalsIgnoreCase(IncoTerm.DDP) && getDeliveryCondition().getName().equalsIgnoreCase(IncoTerm.DDP) ||
										getPriceCondition().getName().equalsIgnoreCase(IncoTerm.DDP_2010) && getDeliveryCondition().getName().equalsIgnoreCase(IncoTerm.DDP_2010)
		) &&
						!"1".equals(cpr_nds_by_string);
	}

	public boolean incoTermCaseC()
	{
		if (StringUtil.isEmpty(getPriceCondition().getName()) ||
						StringUtil.isEmpty(getDeliveryCondition().getName()))
		{
			return false;
		}

		return getPriceCondition().getName().equalsIgnoreCase(IncoTerm.EXW) && getDeliveryCondition().getName().equalsIgnoreCase(IncoTerm.DDP) ||
						getPriceCondition().getName().equalsIgnoreCase(IncoTerm.EXW_2010) && getDeliveryCondition().getName().equalsIgnoreCase(IncoTerm.DDP_2010);
	}

	public boolean incoTermCaseB()
	{
		if (StringUtil.isEmpty(getPriceCondition().getName()) ||
						StringUtil.isEmpty(getDeliveryCondition().getName()))
		{
			return false;
		}

		return (getPriceCondition().getName().equalsIgnoreCase(IncoTerm.FCA) && getDeliveryCondition().getName().equalsIgnoreCase(IncoTerm.FCA)) ||
						(getPriceCondition().getName().equalsIgnoreCase(IncoTerm.CPT) && getDeliveryCondition().getName().equalsIgnoreCase(IncoTerm.CPT)) ||
						(getPriceCondition().getName().equalsIgnoreCase(IncoTerm.CIP) && getDeliveryCondition().getName().equalsIgnoreCase(IncoTerm.CIP)) ||
						(getPriceCondition().getName().equalsIgnoreCase(IncoTerm.DDU) && getDeliveryCondition().getName().equalsIgnoreCase(IncoTerm.DDU)) ||
						(getPriceCondition().getName().equalsIgnoreCase(IncoTerm.FCA_2010) && getDeliveryCondition().getName().equalsIgnoreCase(IncoTerm.FCA_2010)) ||
						(getPriceCondition().getName().equalsIgnoreCase(IncoTerm.CPT_2010) && getDeliveryCondition().getName().equalsIgnoreCase(IncoTerm.CPT_2010)) ||
						(getPriceCondition().getName().equalsIgnoreCase(IncoTerm.CIP_2010) && getDeliveryCondition().getName().equalsIgnoreCase(IncoTerm.CIP_2010)) ||
						(getPriceCondition().getName().equalsIgnoreCase(IncoTerm.DAT_2010) && getDeliveryCondition().getName().equalsIgnoreCase(IncoTerm.DAT_2010)) ||
						(getPriceCondition().getName().equalsIgnoreCase(IncoTerm.DAP_2010) && getDeliveryCondition().getName().equalsIgnoreCase(IncoTerm.DAP_2010))
						;
	}

	public boolean incoTermCaseA()
	{
		if (StringUtil.isEmpty(getPriceCondition().getName()) ||
						StringUtil.isEmpty(getDeliveryCondition().getName()))
		{
			return false;
		}

		return getPriceCondition().getName().equalsIgnoreCase(IncoTerm.EXW) &&
						(getDeliveryCondition().getName().equalsIgnoreCase(IncoTerm.FCA) ||
										getDeliveryCondition().getName().equalsIgnoreCase(IncoTerm.CPT) ||
										getDeliveryCondition().getName().equalsIgnoreCase(IncoTerm.CIP) ||
										getDeliveryCondition().getName().equalsIgnoreCase(IncoTerm.DDU)
						)
						||
						getPriceCondition().getName().equalsIgnoreCase(IncoTerm.EXW_2010) &&
										(getDeliveryCondition().getName().equalsIgnoreCase(IncoTerm.FCA_2010) ||
														getDeliveryCondition().getName().equalsIgnoreCase(IncoTerm.CPT_2010) ||
														getDeliveryCondition().getName().equalsIgnoreCase(IncoTerm.CIP_2010) ||
														getDeliveryCondition().getName().equalsIgnoreCase(IncoTerm.DAT_2010) ||
														getDeliveryCondition().getName().equalsIgnoreCase(IncoTerm.DAP_2010)
										);
	}

	public void setListParentIds()
	{
		for (int i = 0; i < produces.size() - countItogRecord; i++)
		{
			CommercialProposalProduce commercialProposalProduce = produces.get(i);

			commercialProposalProduce.setCpr_id(getCpr_id());
		}

		for (CommercialProposalTransport commercialProposalTransport : transportLines)
		{
			commercialProposalTransport.setCpr_id(getCpr_id());
		}
	}

	//for clone produces
	public void setListIdsToNull()
	{
		for (int i = 0; i < produces.size() - countItogRecord; i++)
		{
			CommercialProposalProduce commercialProposalProduce = produces.get(i);

			commercialProposalProduce.setLpr_id(null);
		}

		for (CommercialProposalTransport commercialProposalTransport : transportLines)
		{
			commercialProposalTransport.setTrn_id(null);
		}
	}

	public CommercialProposalProduce findProduce(String number)
	{
		for (int i = 0; i < produces.size() - countItogRecord; i++)
		{
			CommercialProposalProduce commercialProposalProduce = produces.get(i);

			if (commercialProposalProduce.getNumber().equalsIgnoreCase(number))
				return commercialProposalProduce;
		}

		return null;
	}

	public CommercialProposalTransport findTransport(String number)
	{
		for (CommercialProposalTransport commercialProposalTransport : transportLines)
		{
			if (commercialProposalTransport.getNumber().equalsIgnoreCase(number))
				return commercialProposalTransport;
		}

		return null;
	}

	public CommercialProposalTransport findTransportByStfId(String stfId)
	{
		if (StringUtil.isEmpty(stfId))
		{
			return null;
		}
		for (CommercialProposalTransport commercialProposalTransport : transportLines)
		{
			if (commercialProposalTransport.getStuffCategory() != null
					&& !StringUtil.isEmpty(commercialProposalTransport.getStuffCategory().getId())
					&& commercialProposalTransport.getStuffCategory().getId().equalsIgnoreCase(stfId))
				return commercialProposalTransport;
		}

		return null;
	}

	public void updateProduce(String number, CommercialProposalProduce commercialProposalProduceIn)
	{
		if (isOldVersion())
		{
			commercialProposalProduceIn.setStuffCategory(new StuffCategory());
		}
		for (int i = 0; i < produces.size() - countItogRecord; i++)
		{
			CommercialProposalProduce commercialProposalProduce = produces.get(i);

			if (commercialProposalProduce.getNumber().equalsIgnoreCase(number))
			{
				produces.set(i, commercialProposalProduceIn);
				return;
			}
		}
	}

	public void deleteProduce(String number)
	{
		for (int i = 0; i < produces.size() - countItogRecord; i++)
		{
			CommercialProposalProduce commercialProposalProduce = produces.get(i);

			if (commercialProposalProduce.getNumber().equalsIgnoreCase(number))
			{
				produces.remove(i);
				break;
			}
		}
	}

	public void insertProduce(String numberBefore, CommercialProposalProduce commercialProposalProduceIn)
	{
		if (isOldVersion())
		{
			commercialProposalProduceIn.setStuffCategory(new StuffCategory());
		}
		getProduces().add(StringUtil.isEmpty(numberBefore) ? getProduces().size() - countItogRecord : Integer.valueOf(numberBefore) - 1, commercialProposalProduceIn);
	}

	public boolean isNaNCodePercent()
	{
		for (int i = 0; i < getProduces().size() - countItogRecord; i++)
		{
			CommercialProposalProduce commercialProposalProduce = getProduces().get(i);

			if (Double.isNaN(commercialProposalProduce.getCustomPercent()))
				return true;
		}

		return false;
	}

	public List getExcelTable()
	{
		IActionContext context = ActionContext.threadInstance();
		List<Object> rows = new ArrayList<Object>();

		List<Object> header = new ArrayList<Object>();
		try
		{
			header.add(StrutsUtil.getMessage(context, "CommercialProposalProduces.number"));
			header.add(StrutsUtil.getMessage(context, "CommercialProposalProduces.produce_name"));
			header.add(StrutsUtil.getMessage(context, "CommercialProposalProduces.catalog_num"));
			if (isCalculateNetto())
			{
				header.add(getLprPriceBruttoHeader());
				header.add(StrutsUtil.getMessage(context, "CommercialProposalProduces.lpr_discount"));
			}
			header.add(getLprPriceNettoHeader());
			header.add(StrutsUtil.getMessage(context, "CommercialProposalProduces.lpr_count"));
			header.add(StrutsUtil.getMessage(context, "CommercialProposalProduces.custom_code"));
			header.add(StrutsUtil.getMessage(context, "CommercialProposalProduces.custom_percent"));
			header.add(StrutsUtil.getMessage(context, "CommercialProposalProduces.lpr_coeficient"));

			header.add(StrutsUtil.getMessage(context, "CommercialProposalProduces.sale_price"));
			header.add(StrutsUtil.getMessage(context, "CommercialProposalProduces.sale_cost"));
			header.add(StrutsUtil.getMessage(context, "CommercialProposalProduces.parking_trans"));
			header.add(StrutsUtil.getMessage(context, "CommercialProposalProduces.sale_price_parking_trans"));
			header.add(StrutsUtil.getMessage(context, "CommercialProposalProduces.sale_cost_parking_trans"));
			header.add(StrutsUtil.getMessage(context, "CommercialProposalProduces.custom_duty"));
			header.add(StrutsUtil.getMessage(context, "CommercialProposalProduces.sale_price_parking_trans_custom"));
			header.add(StrutsUtil.getMessage(context, "CommercialProposalProduces.sale_cost_parking_trans_custom"));
			if (!StringUtil.isEmpty(getCpr_nds_by_string()))
			{
				header.add(StrutsUtil.getMessage(context, "CommercialProposalProduces.nds"));
				header.add(StrutsUtil.getMessage(context, "CommercialProposalProduces.cost_nds"));
			}
		}
		catch (Exception e)
		{
			log.error(e);
		}
		rows.add(header);

		for (int i = 0; i < produces.size(); i++)
		{
			CommercialProposalProduce commercialProposalProduce = produces.get(i);

			List<Object> record = new ArrayList<Object>();

			record.add(commercialProposalProduce.getNumber());
			record.add(commercialProposalProduce.getProduce_name());
			record.add(commercialProposalProduce.getCatalog_num());
			if (i < produces.size() - countItogRecord)
			{
				if (isCalculateNetto())
				{
					record.add(commercialProposalProduce.getLpr_price_brutto());
					record.add(commercialProposalProduce.getLpr_discount());
				}
				record.add(commercialProposalProduce.getLpr_price_netto());
				record.add(commercialProposalProduce.getLpr_count());
			}
			else
			{
				record.add("");
				record.add("");
			}
			record.add(commercialProposalProduce.getCode());
			if (i < produces.size() - countItogRecord)
			{
				record.add(commercialProposalProduce.getCustomPercent());
			}
			else
			{
				record.add("");
			}
			if (i < produces.size() - countItogRecord)
			{
				record.add(commercialProposalProduce.getLpr_coeficient());
				record.add(commercialProposalProduce.getSale_price());
			}
			else
			{
				record.add("");
				record.add("");
			}

			if ((i < produces.size() - countItogRecord) || incoTermCaseA() || incoTermCaseC())
			{
				record.add(commercialProposalProduce.getSale_cost());
			}
			else
			{
				record.add("");
			}

			if (i < produces.size() - countItogRecord)
			{
				record.add(commercialProposalProduce.getParking_trans());
				record.add(commercialProposalProduce.getSale_price_parking_trans());
			}
			else
			{
				record.add("");
				record.add("");
			}

			if ((i < produces.size() - countItogRecord) || incoTermCaseB())
			{
				record.add(commercialProposalProduce.getSale_cost_parking_trans());
			}
			else
			{
				record.add("");
			}

			if (i < produces.size() - countItogRecord)
			{
				record.add(commercialProposalProduce.getCustom_duty());
				record.add(commercialProposalProduce.getSale_price_parking_trans_custom());
			}
			else
			{
				record.add("");
				record.add("");
			}

			if ((i < produces.size() - countItogRecord) || incoTermCaseD() || incoTermCaseE())
			{
				record.add(commercialProposalProduce.getSale_cost_parking_trans_custom());
			}
			else
			{
				record.add("");
			}

			if (!StringUtil.isEmpty(getCpr_nds_by_string()))
			{
				record.add(commercialProposalProduce.getNds());
				record.add(commercialProposalProduce.getCost_nds());
			}

			rows.add(record);
		}

		return rows;
	}

	public String getCpr_tender_number()
	{
		return cpr_tender_number;
	}

	public void setCpr_tender_number(String cpr_tender_number)
	{
		this.cpr_tender_number = cpr_tender_number;
	}

	public String getCpr_tender_number_editable()
	{
		return cpr_tender_number_editable;
	}

	public void setCpr_tender_number_editable(String cpr_tender_number_editable)
	{
		this.cpr_tender_number_editable = cpr_tender_number_editable;
	}
}
