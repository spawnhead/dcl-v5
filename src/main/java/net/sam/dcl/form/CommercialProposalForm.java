package net.sam.dcl.form;

import net.sam.dcl.controller.BaseDispatchValidatorForm;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.controller.ActionContext;
import net.sam.dcl.beans.*;
import net.sam.dcl.service.DeferredAttachmentService;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.ConstDefinitions;
import net.sam.dcl.util.StrutsUtil;
import net.sam.dcl.taglib.table.model.impl.HolderImplUsingList;
import net.sam.dcl.dbo.DboAttachment;
import net.sam.dcl.config.Config;
import org.apache.struts.action.ActionMapping;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * Date: Aug 6, 2005
 * Time: 5:37:29 PM
 */
public class CommercialProposalForm extends BaseDispatchValidatorForm
{
	protected static Log log = LogFactory.getLog(CommercialProposal.class);
	protected static String assemblingNumberLine = "1";
	public static String transportNumberLine = "2";

	String include_exps;
	String print;
	String printInvoice;
	String printContract;
	boolean needPrint;
	boolean needPrintInvoice;
	boolean needPrintContract;

	String is_new_doc;
	String number;
	String gen_num;

	String cpr_id;

	User createUser = new User();
	User editUser = new User();
	String usr_date_create;
	String usr_date_edit;

	String usr_code;

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
	String cpr_course;
	String cpr_nds;
	IncoTerm priceCondition = new IncoTerm();
	String cpr_country;
	String cpr_pay_condition;
	String cpr_pay_condition_invoice;
	IncoTerm deliveryCondition = new IncoTerm();
	String cpr_nds_by_string;
	String cpr_delivery_address;
	String cpr_sum_transport;
	String cpr_all_transport;
	String cpr_sum_assembling;
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
	boolean cpr_proposal_received_flag;
	boolean cpr_proposal_declined;
	String cpr_block;
	String cpr_summ;
	String cpr_old_version;
	String cpr_assemble_minsk_store;
	String cpr_free_prices;
	String cpr_reverse_calc;
	String cpr_can_edit_invoice;
	String cpr_comment;
	PurchasePurpose purchasePurpose = new PurchasePurpose();
	ContactPerson contactPersonSeller = new ContactPerson();
	ContactPerson contactPersonCustomer = new ContactPerson();
	String cpr_guaranty_in_month;
	String cpr_prepay_percent;
	String cpr_prepay_sum;
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

	DboAttachment templateExcel;
	String show_unit;

	boolean showForAdmin = false;
	boolean readOnlyForAssembleMinsk = false;
	boolean formReadOnly = false;
	boolean correctKoef = false;
	boolean showForAdminOrEconomist = false;
	boolean showPrintInvoice = false;
	boolean showAskClearTable = true;
	boolean noReservationReadOnly = false;
	boolean dateAcceptReadOnly = false;

	String style_sale_price;
	String style_sale_cost;
	String style_sale_price_parking_trans;
	String style_sale_cost_parking_trans;
	String style_sale_price_parking_trans_custom;
	String style_sale_cost_parking_trans_custom;

	String style_lpc_price_list_by;
	String style_discount_percent;

	String check_sale_cost;
	String check_sale_cost_parking_trans;
	String check_sale_cost_parking_trans_custom;

	HolderImplUsingList gridProduces = new HolderImplUsingList();
	HolderImplUsingList gridTransport = new HolderImplUsingList();
	HolderImplUsingList gridCharges = new HolderImplUsingList();

	String courseRecommendText;
	String total;

	String printMode;
	String cpr_tender_number;
	boolean cpr_tender_number_editable;

	HolderImplUsingList attachmentsGrid = new HolderImplUsingList();
	DeferredAttachmentService attachmentService = null;
	String attachmentId;

	public void reset(ActionMapping mapping, HttpServletRequest request)
	{
		if (!isNdsByString())
		{
			setCpr_nds_by_string("");
		}
		setShow_unit("");
		setCpr_can_edit_invoice("");
		if (!isDateAcceptReadOnly())
		{
			setCpr_proposal_received_flag(false);
		}
		setCpr_proposal_declined(false);
		setCpr_tender_number_editable(false);
		setCpr_tender_number("");
		if (!isFormReadOnly())
		{
			setCpr_old_version("");
			setCpr_assemble_minsk_store("");
			setCpr_free_prices("");
			setCpr_reverse_calc("");
			setCpr_all_transport("");
			setCpr_executor_flag("");
			setFacsimile_flag("");
			setCpr_provider_delivery("");
		}
		if (!isNoReservationReadOnly())
		{
			setCpr_no_reservation("");
		}
		setCpr_final_date_above("");
		super.reset(mapping, request);
	}

	public String getTemplateIdCP()
	{
		return String.valueOf(ConstDefinitions.templateIdCP);
	}

	public String getInclude_exps()
	{
		return include_exps;
	}

	public void setInclude_exps(String include_exps)
	{
		this.include_exps = include_exps;
	}

	public String getPrint()
	{
		return print;
	}

	public void setPrint(String print)
	{
		this.print = print;
	}

	public String getPrintInvoice()
	{
		return printInvoice;
	}

	public void setPrintInvoice(String printInvoice)
	{
		this.printInvoice = printInvoice;
	}

	public String getPrintContract()
	{
		return printContract;
	}

	public void setPrintContract(String printContract)
	{
		this.printContract = printContract;
	}

	public boolean isNeedPrint()
	{
		return needPrint;
	}

	public void setNeedPrint(boolean needPrint)
	{
		this.needPrint = needPrint;
	}

	public boolean isNeedPrintInvoice()
	{
		return needPrintInvoice;
	}

	public void setNeedPrintInvoice(boolean needPrintInvoice)
	{
		this.needPrintInvoice = needPrintInvoice;
	}

	public boolean isNeedPrintContract()
	{
		return needPrintContract;
	}

	public void setNeedPrintContract(boolean needPrintContract)
	{
		this.needPrintContract = needPrintContract;
	}

	public String getIs_new_doc()
	{
		return is_new_doc;
	}

	public void setIs_new_doc(String is_new_doc)
	{
		this.is_new_doc = is_new_doc;
	}

	public String getNumber()
	{
		return number;
	}

	public void setNumber(String number)
	{
		this.number = number;
	}

	public String getGen_num()
	{
		return gen_num;
	}

	public void setGen_num(String gen_num)
	{
		this.gen_num = gen_num;
	}

	public String getCpr_id()
	{
		return cpr_id;
	}

	public void setCpr_id(String cpr_id)
	{
		this.cpr_id = cpr_id;
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

	public String getUsr_code()
	{
		return usr_code;
	}

	public void setUsr_code(String usr_code)
	{
		this.usr_code = usr_code;
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

	public String getCpr_concerning_invoice()
	{
		return cpr_concerning_invoice;
	}

	public void setCpr_concerning_invoice(String cpr_concerning_invoice)
	{
		this.cpr_concerning_invoice = cpr_concerning_invoice;
	}

	public String getCpr_preamble()
	{
		return cpr_preamble;
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
		return StringUtil.appCurrencyString2double(cpr_course);
	}

	public String getCpr_course_formatted()
	{
		return cpr_course;
	}

	public void setCpr_course(double cpr_course)
	{
		this.cpr_course = StringUtil.double2appCurrencyStringByMask(cpr_course, "#,##0.00000000");
	}

	public void setCpr_course_formatted(String cpr_course)
	{
		this.cpr_course = cpr_course;
	}

	public double getCpr_nds()
	{
		return StringUtil.appCurrencyString2double(cpr_nds);
	}

	public String getCpr_nds_formatted()
	{
		return cpr_nds;
	}

	public void setCpr_nds(double cpr_nds)
	{
		this.cpr_nds = StringUtil.double2appCurrencyString(cpr_nds);
	}

	public void setCpr_nds_formatted(String cpr_nds)
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
		return StringUtil.appCurrencyString2double(cpr_sum_transport);
	}

	public String getCpr_sum_transport_formatted()
	{
		return cpr_sum_transport;
	}

	public void setCpr_sum_transport(double cpr_sum_transport)
	{
		this.cpr_sum_transport = StringUtil.double2appCurrencyString(cpr_sum_transport);
	}

	public void setCpr_sum_transport_formatted(String cpr_sum_transport)
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

	public double getCpr_sum_assembling()
	{
		return StringUtil.appCurrencyString2double(cpr_sum_assembling);
	}

	public String getCpr_sum_assembling_formatted()
	{
		return cpr_sum_assembling;
	}

	public void setCpr_sum_assembling(double cpr_sum_assembling)
	{
		this.cpr_sum_assembling = StringUtil.double2appCurrencyString(cpr_sum_assembling);
	}

	public void setCpr_sum_assembling_formatted(String cpr_sum_assembling)
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

	public User getExecutor()
	{
		return executor;
	}

	public void setExecutor(User executor)
	{
		this.executor = executor;
	}

	public String getCpr_date_accept()
	{
		return cpr_date_accept;
	}

	public String getCpr_date_accept_ts()
	{
		return StringUtil.appDateString2dbDateString(getCpr_date_accept());
	}

	public void setCpr_date_accept(String cpr_date_accept)
	{
		this.cpr_date_accept = cpr_date_accept;
	}

	public boolean isCpr_proposal_received_flag()
	{
		return cpr_proposal_received_flag;
	}

	public void setCpr_proposal_received_flag(boolean cpr_proposal_received_flag)
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

	public String getCpr_summ()
	{
		return cpr_summ;
	}

	public void setCpr_summ(String cpr_summ)
	{
		this.cpr_summ = cpr_summ;
	}

	public String getCpr_old_version()
	{
		return cpr_old_version;
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

	public void setCpr_free_prices(String cpr_free_prices)
	{
		this.cpr_free_prices = cpr_free_prices;
	}

	public String getCpr_reverse_calc()
	{
		return cpr_reverse_calc;
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

	public String getCpr_guaranty_in_month()
	{
		return cpr_guaranty_in_month;
	}

	public void setCpr_guaranty_in_month(String cpr_guaranty_in_month)
	{
		this.cpr_guaranty_in_month = cpr_guaranty_in_month;
	}

	public String getCpr_prepay_percent()
	{
		return cpr_prepay_percent;
	}

	public void setCpr_prepay_percent(String cpr_prepay_percent)
	{
		this.cpr_prepay_percent = cpr_prepay_percent;
	}

	public String getCpr_prepay_sum()
	{
		return cpr_prepay_sum;
	}

	public void setCpr_prepay_sum(String cpr_prepay_sum)
	{
		this.cpr_prepay_sum = cpr_prepay_sum;
	}

	public String getCpr_delay_days()
	{
		return cpr_delay_days;
	}

	public void setCpr_delay_days(String cpr_delay_days)
	{
		this.cpr_delay_days = cpr_delay_days;
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

	public void setCpr_delivery_count_day(String cpr_delivery_count_day)
	{
		this.cpr_delivery_count_day = cpr_delivery_count_day;
	}

	public String getCpr_donot_calculate_netto()
	{
		return cpr_donot_calculate_netto;
	}

	public void setCpr_donot_calculate_netto(String cpr_donot_calculate_netto)
	{
		this.cpr_donot_calculate_netto = cpr_donot_calculate_netto;
	}

	public String getDonot_calculate_netto()
	{
		return getCpr_donot_calculate_netto();
	}

	public String getCalculate_netto()
	{
		return StringUtil.isEmpty(getCpr_donot_calculate_netto()) ? "1" : "";
	}

	public DboAttachment getTemplateExcel()
	{
		return templateExcel;
	}

	public String getTemplateId()
	{
		if (null == templateExcel || null == templateExcel.getId())
		{
			return "";
		}
		return templateExcel.getId().toString();
	}

	public void setTemplateExcel(DboAttachment templateExcel)
	{
		this.templateExcel = templateExcel;
	}

	public String getShow_unit()
	{
		return show_unit;
	}

	public void setShow_unit(String show_unit)
	{
		this.show_unit = show_unit;
	}

	public HolderImplUsingList getGridProduces()
	{
		return gridProduces;
	}

	public void setGridProduces(HolderImplUsingList gridProduces)
	{
		this.gridProduces = gridProduces;
	}

	public HolderImplUsingList getGridTransport()
	{
		return gridTransport;
	}

	public void setGridTransport(HolderImplUsingList gridTransport)
	{
		this.gridTransport = gridTransport;
	}

	private String getIncludeExp()
	{
		IActionContext context = ActionContext.threadInstance();
		try
		{
			if (CommercialProposal.includeYes.equals(getInclude_exps()))
			{
				return StrutsUtil.getMessage(context, "CommercialProposal.include_charge_yes");
			}
			if (CommercialProposal.includeNo.equals(getInclude_exps()))
			{
				return StrutsUtil.getMessage(context, "CommercialProposal.include_charge_no");
			}
		}
		catch (Exception e)
		{
			log.error(e);
		}

		return "";
	}

	public void formGridCharges()
	{
		IActionContext context = ActionContext.threadInstance();
		try
		{
			gridCharges.getDataList().clear();

			//Вариант для случая, когда "Заполнять табличную часть без привязки к справочнику "Номенклатура" = [ V ]
			//или
			//когда "Заполнять табличную часть без привязки к справочнику "Номенклатура" = [  ]
			// и   у всех позиций одинаковый производитель
			gridCharges.getDataList().add(new CommercialProposalCharge(this,
							assemblingNumberLine,
							StrutsUtil.getMessage(context, "CommercialProposalCharges.charge_assembling"),
							getCpr_sum_assembling(),
							getIncludeExp()
			)
			);
			if (!isShowAllTransport())
			{
				gridCharges.getDataList().add(new CommercialProposalCharge(this,
								transportNumberLine,
								StrutsUtil.getMessage(context, "CommercialProposalCharges.charge_transport"),
								getCpr_sum_transport(),
								getIncludeExp()
				)
				);
			}
			else
			{
				gridCharges.getDataList().add(new CommercialProposalCharge(this,
								transportNumberLine,
								StrutsUtil.getMessage(context, "CommercialProposalCharges.charge_transport_all"),
								getCpr_sum_transport(),
								getIncludeExp()
				)
				);
			}

			if (isShowTransportTable())
			{
				for (int i = 0; i < gridTransport.getDataList().size(); i++)
				{
					CommercialProposalTransport commercialProposalTransport = (CommercialProposalTransport) gridTransport.getDataList().get(i);

					gridCharges.getDataList().add(new CommercialProposalCharge(this,
									Integer.toString(i + 1 + 2),
									"&nbsp;&nbsp;&nbsp;&nbsp;" + commercialProposalTransport.getStuffCategory().getName(),
									commercialProposalTransport.getTrn_sum(),
									""
					)
					);
				}
			}
		}
		catch (Exception e)
		{
			log.error(e);
		}
	}

	public void setGridCharges(HolderImplUsingList gridCharges)
	{
		this.gridCharges = gridCharges;
	}

	public HolderImplUsingList getGridCharges()
	{
		return gridCharges;
	}

	public boolean isShowForAdmin()
	{
		return showForAdmin;
	}

	public void setShowForAdmin(boolean showForAdmin)
	{
		this.showForAdmin = showForAdmin;
	}

	public boolean isShowAllTransport()
	{
		return getGridTransport().getDataList().size() > 1;
	}

	public boolean isShowTransportTable()
	{
		return getGridTransport().getDataList().size() > 1 && StringUtil.isEmpty(getCpr_all_transport());
	}

	public boolean isShowDownload()
	{
		return !StringUtil.isEmpty(getTemplateId());
	}

	public boolean isReadOnlyForAssembleMinsk()
	{
		return readOnlyForAssembleMinsk || formReadOnly;
	}

	public void setReadOnlyForAssembleMinsk(boolean readOnlyForAssembleMinsk)
	{
		this.readOnlyForAssembleMinsk = readOnlyForAssembleMinsk;
	}

	public boolean isFormReadOnly()
	{
		return formReadOnly;
	}

	public boolean isNdsByString()
	{
		if (StringUtil.isEmpty(getPriceCondition().getName()) ||
						StringUtil.isEmpty(getDeliveryCondition().getName()))
		{
			return formReadOnly;
		}

		return !(
						(getPriceCondition().getName().equalsIgnoreCase(IncoTerm.DDP) && getDeliveryCondition().getName().equalsIgnoreCase(IncoTerm.DDP)) ||
										(getPriceCondition().getName().equalsIgnoreCase(IncoTerm.DDP_2010) && getDeliveryCondition().getName().equalsIgnoreCase(IncoTerm.DDP_2010))
		) ||
						formReadOnly ||
						isAssembleMinskStore();
	}

	public void setFormReadOnly(boolean formReadOnly)
	{
		this.formReadOnly = formReadOnly;
	}

	public boolean isCorrectKoef()
	{
		return correctKoef;
	}

	public void setCorrectKoef(boolean correctKoef)
	{
		this.correctKoef = correctKoef;
	}

	public boolean isShowForAdminOrEconomist()
	{
		return showForAdminOrEconomist;
	}

	public void setShowForAdminOrEconomist(boolean showForAdminOrEconomist)
	{
		this.showForAdminOrEconomist = showForAdminOrEconomist;
	}

	public boolean isShowDoubleFields()
	{
		return showForAdminOrEconomist && !StringUtil.isEmpty(getCpr_can_edit_invoice());
	}

	public boolean isShowPrintInvoice()
	{
		return showPrintInvoice;
	}

	public void setShowPrintInvoice(boolean showPrintInvoice)
	{
		this.showPrintInvoice = showPrintInvoice;
	}

	public boolean isShowAskClearTable()
	{
		return showAskClearTable;
	}

	public void setShowAskClearTable(boolean showAskClearTable)
	{
		this.showAskClearTable = showAskClearTable;
	}

	public boolean isNoReservationReadOnly()
	{
		return noReservationReadOnly || isFormReadOnly();
	}

	public void setNoReservationReadOnly(boolean noReservationReadOnly)
	{
		this.noReservationReadOnly = noReservationReadOnly;
	}

	public boolean isDateAcceptReadOnly()
	{
		return dateAcceptReadOnly || isFormReadOnly();
	}

	public void setDateAcceptReadOnly(boolean dateAcceptReadOnly)
	{
		this.dateAcceptReadOnly = dateAcceptReadOnly;
	}

	public boolean isShowPrintUnit()
	{
		return StringUtil.isEmpty(getCpr_old_version());
	}

	public String getStyle_sale_price()
	{
		return style_sale_price;
	}

	public void setStyle_sale_price(String style_sale_price)
	{
		this.style_sale_price = style_sale_price;
	}

	public String getStyle_sale_cost()
	{
		return style_sale_cost;
	}

	public void setStyle_sale_cost(String style_sale_cost)
	{
		this.style_sale_cost = style_sale_cost;
	}

	public String getStyle_sale_price_parking_trans()
	{
		return style_sale_price_parking_trans;
	}

	public void setStyle_sale_price_parking_trans(String style_sale_price_parking_trans)
	{
		this.style_sale_price_parking_trans = style_sale_price_parking_trans;
	}

	public String getStyle_sale_cost_parking_trans()
	{
		return style_sale_cost_parking_trans;
	}

	public void setStyle_sale_cost_parking_trans(String style_sale_cost_parking_trans)
	{
		this.style_sale_cost_parking_trans = style_sale_cost_parking_trans;
	}

	public String getStyle_sale_price_parking_trans_custom()
	{
		return style_sale_price_parking_trans_custom;
	}

	public void setStyle_sale_price_parking_trans_custom(String style_sale_price_parking_trans_custom)
	{
		this.style_sale_price_parking_trans_custom = style_sale_price_parking_trans_custom;
	}

	public String getStyle_sale_cost_parking_trans_custom()
	{
		return style_sale_cost_parking_trans_custom;
	}

	public void setStyle_sale_cost_parking_trans_custom(String style_sale_cost_parking_trans_custom)
	{
		this.style_sale_cost_parking_trans_custom = style_sale_cost_parking_trans_custom;
	}

	public String getStyle_lpc_price_list_by()
	{
		return style_lpc_price_list_by;
	}

	public void setStyle_lpc_price_list_by(String style_lpc_price_list_by)
	{
		this.style_lpc_price_list_by = style_lpc_price_list_by;
	}

	public String getStyle_discount_percent()
	{
		return style_discount_percent;
	}

	public void setStyle_discount_percent(String style_discount_percent)
	{
		this.style_discount_percent = style_discount_percent;
	}

	public String getCheck_sale_cost()
	{
		return check_sale_cost;
	}

	public void setCheck_sale_cost(String check_sale_cost)
	{
		this.check_sale_cost = check_sale_cost;
	}

	public String getCheck_sale_cost_parking_trans()
	{
		return check_sale_cost_parking_trans;
	}

	public void setCheck_sale_cost_parking_trans(String check_sale_cost_parking_trans)
	{
		this.check_sale_cost_parking_trans = check_sale_cost_parking_trans;
	}

	public String getCheck_sale_cost_parking_trans_custom()
	{
		return check_sale_cost_parking_trans_custom;
	}

	public void setCheck_sale_cost_parking_trans_custom(String check_sale_cost_parking_trans_custom)
	{
		this.check_sale_cost_parking_trans_custom = check_sale_cost_parking_trans_custom;
	}

	public String getLprPriceBruttoHeader()
	{
		IActionContext context = ActionContext.threadInstance();
		try
		{
			return StringUtil.replace(StrutsUtil.getMessage(context, "CommercialProposalProduces.lpr_price_brutto"), "EUR", getCurrencyTable().getName());
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
			return StringUtil.replace(StrutsUtil.getMessage(context, "CommercialProposalProduces.lpr_price_netto"), "EUR", getCurrencyTable().getName());
		}
		catch (Exception e)
		{
			log.error(e);
		}

		return "";
	}

	public String getChargeSumHeader()
	{
		IActionContext context = ActionContext.threadInstance();
		try
		{
			return StrutsUtil.getMessage(context, "CommercialProposalCharges.sum") + " " + getCurrencyTable().getName();
		}
		catch (Exception e)
		{
			log.error(e);
		}

		return "";
	}

	public String getCourseRecommendText()
	{
		return courseRecommendText;
	}

	public void setCourseRecommendText(String courseRecommendText)
	{
		this.courseRecommendText = courseRecommendText;
	}

	public String getTotal()
	{
		return total;
	}

	public void setTotal(String total)
	{
		this.total = total;
	}

	public String getPrintMode()
	{
		return printMode;
	}

	public void setPrintMode(String printMode)
	{
		this.printMode = printMode;
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

	public void setCpr_final_date_above(String cpr_final_date_above)
	{
		this.cpr_final_date_above = cpr_final_date_above;
	}

	public boolean isIncorrectWhenNotEqualCurrencies()
	{
		String currencyId = getCurrency() == null ? null : getCurrency().getId();
		String currencyTableId = getCurrencyTable() == null ? null : getCurrencyTable().getId();
		if (StringUtil.isEmpty(currencyId) || StringUtil.isEmpty(currencyTableId))
		{
			return false;
		}
		return (!StringUtil.equal(currencyId, currencyTableId) && getCpr_course() == 1);
	}

	public boolean isIncorrectWhenEqualCurrencies()
	{
		String currencyId = getCurrency() == null ? null : getCurrency().getId();
		String currencyTableId = getCurrencyTable() == null ? null : getCurrencyTable().getId();
		if (StringUtil.isEmpty(currencyId) || StringUtil.isEmpty(currencyTableId))
		{
			return false;
		}
		return (StringUtil.equal(currencyId, currencyTableId) && getCpr_course() != 1);
	}

	public boolean isIncorrectCurrency()
	{
		return isIncorrectWhenNotEqualCurrencies() || isIncorrectWhenEqualCurrencies();
	}

	public String getContractorLTS()
	{
		return Config.getString(Constants.contractorLTS);
	}

	public String getDayCountWaitReservedForShipping()
	{
		return Config.getString(Constants.dayCountWaitReservedForShipping);
	}

	static public class CommercialProposalCharge
	{
		String number;
		String charge;
		double sum;
		String includeInPrice;

		CommercialProposalForm parentForm;

		public CommercialProposalCharge()
		{
		}

		public CommercialProposalCharge(CommercialProposalForm parentForm, String number, String charge, double sum, String includeInPrice)
		{
			this.number = number;
			this.charge = charge;
			this.sum = sum;
			this.includeInPrice = includeInPrice;
			this.parentForm = parentForm;
		}

		public String getNumber()
		{
			return number;
		}

		public void setNumber(String number)
		{
			this.number = number;
		}

		public String getCharge()
		{
			return charge;
		}

		public void setCharge(String charge)
		{
			this.charge = charge;
		}

		public double getSum()
		{
			return sum;
		}

		public String getSumFormatted()
		{
			return StringUtil.double2appCurrencyString(sum);
		}

		public void setSum(double sum)
		{
			this.sum = sum;
		}

		public CommercialProposalTransport findTransport(String number)
		{
			for (int i = 0; i < parentForm.getGridTransport().getDataList().size(); i++)
			{
				CommercialProposalTransport commercialProposalTransport = (CommercialProposalTransport) parentForm.getGridTransport().getDataList().get(i);

				if (commercialProposalTransport.getNumber().equalsIgnoreCase(number))
					return commercialProposalTransport;
			}

			return null;
		}

		public void setSumFormatted(String sum)
		{
			this.sum = StringUtil.appCurrencyString2double(sum);
			if (assemblingNumberLine.equals(number))
			{
				parentForm.setCpr_sum_assembling(this.sum);
			}

			if (!parentForm.isShowTransportTable())
			{
				if (transportNumberLine.equals(number))
				{
					parentForm.setCpr_sum_transport(this.sum);
				}
			}

			if (parentForm.isShowTransportTable())
			{
				CommercialProposalTransport commercialProposalTransport = findTransport(Integer.toString(new Integer(number) - 2));
				if (null != commercialProposalTransport)
				{
					commercialProposalTransport.setTrn_sum(this.sum);
				}
			}
		}

		public String getIncludeInPrice()
		{
			return includeInPrice;
		}

		public void setIncludeInPrice(String includeInPrice)
		{
			this.includeInPrice = includeInPrice;
		}

		public boolean isShowAllTransport()
		{
			return parentForm.isShowAllTransport() && transportNumberLine.equals(number);
		}
	}

	public String getCpr_tender_number()
	{
		return cpr_tender_number;
	}

	public void setCpr_tender_number(String cpr_tender_number)
	{
		this.cpr_tender_number = cpr_tender_number;
	}

	public void setCpr_tender_number_editable(boolean cpr_tender_number_editable)
	{
		this.cpr_tender_number_editable = cpr_tender_number_editable;
	}

	public boolean getCpr_tender_number_editable()
	{
		return this.cpr_tender_number_editable;
	}

	public boolean isCpr_tender_name_readonly()
	{
		return !cpr_tender_number_editable;
	}

	public boolean isCpr_proposal_declined()
	{
		return cpr_proposal_declined;
	}

	public void setCpr_proposal_declined(boolean cpr_proposal_declined)
	{
		this.cpr_proposal_declined = cpr_proposal_declined;
	}

	public boolean isCprReceivedFlagReadonly()
	{
		return isCpr_proposal_declined() || isDateAcceptReadOnly();
	}

	public HolderImplUsingList getAttachmentsGrid()
	{
		return attachmentsGrid;
	}

	public void setAttachmentsGrid(HolderImplUsingList attachmentsGrid)
	{
		this.attachmentsGrid = attachmentsGrid;
	}

	public DeferredAttachmentService getAttachmentService()
	{
		return attachmentService;
	}

	public void setAttachmentService(DeferredAttachmentService attachmentService)
	{
		this.attachmentService = attachmentService;
	}

	public String getAttachmentId()
	{
		return attachmentId;
	}

	public void setAttachmentId(String attachmentId)
	{
		this.attachmentId = attachmentId;
	}
}
