package net.sam.dcl.beans;

import net.sam.dcl.controller.ActionContext;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.dao.OrderDAO;
import net.sam.dcl.dbo.DboProduce;
import net.sam.dcl.util.LocaledPropertyMessageResources;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.StrutsUtil;
import net.sam.dcl.util.StoreUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.Serializable;
import java.util.*;
import java.text.ParseException;

public class Order implements Serializable
{
	protected static Log log = LogFactory.getLog(Order.class);
	String is_new_doc;

	String ord_id;
	String opr_id;

	User createUser = new User();
	User editUser = new User();
	String usr_date_create;
	String usr_date_edit;

	String ord_number;
	String ord_date;
	Blank blank = new Blank();
	Contractor contractor = new Contractor();
	ContactPerson contact_person = new ContactPerson();
	String ord_concerning;
	String ord_preamble;
	String ord_delivery_term;
	String ord_pay_condition;
	String ord_addr;
	IncoTerm deliveryCondition = new IncoTerm();
	String ord_add_info;

	User director = new User();
	User logist = new User();
	User director_rb = new User();
	User chief_dep = new User();
	User manager = new User();

	Contractor contractor_for = new Contractor();
	Contract contract = new Contract();
	Specification specification = new Specification();
	StuffCategory stuffCategory = new StuffCategory();

	String ord_sent_to_prod_date;
	String ord_received_conf_date;
	String ord_num_conf;
	String ord_date_conf;
	String ord_conf_sent_date;
	String ord_ready_for_deliv_date;
	String ord_executed_date;

	String ord_block;
	double ord_summ;

	String ord_delivery_cost_by;
	double ord_delivery_cost;
	String ord_donot_calculate_netto;

	String ord_discount_all;
	double ord_discount;

	Currency currency = new Currency();

	String ord_include_nds;
	double ord_nds_rate;

	Seller sellerForWho = new Seller();
	Seller seller = new Seller();

	String ord_count_itog_flag;
	String ord_add_reduction_flag;
	double ord_add_reduction;
	String ord_add_red_pre_pay_flag;
	double ord_add_red_pre_pay;
	String ord_all_include_in_spec;
	String ord_annul;
	String ord_in_one_spec;
	String ord_comment;
	String ord_comment_covering_letter;
	String ord_date_conf_all;
	String ord_ready_for_deliv_date_all;
	ShippingDocType shippingDocType = new ShippingDocType();
	String ord_shp_doc_number;
	String ord_ship_from_stock;
	String ord_arrive_in_lithuania;
	String ord_by_guaranty;
	String ord_print_scale;
	String ord_letter_scale;

	String ord_logist_signature;
	String ord_director_rb_signature;
	String ord_chief_dep_signature;
	String ord_manager_signature;

	String is_warn;
	int count_day_curr_minus_sent_to_prod;

	double ord_produce_count;
	double ord_produce_count_executed;

	String crq_id;

	List<OrderProduce> produces = new ArrayList<OrderProduce>();
	boolean haveItog = false;
	int countItog;

	List<OrderPayment> orderPayments = new ArrayList<OrderPayment>();
	List<OrderPaySum> orderPaySums = new ArrayList<OrderPaySum>();
	List<OrderExecutedAndDate> orderExecutedByDates = new ArrayList<OrderExecutedAndDate>();

	List<OrderExecutedDateLine> orderExecutedDate = new ArrayList<OrderExecutedDateLine>();
	List<OrderExecutedLine> orderExecuted = new ArrayList<OrderExecutedLine>();

	String show_unit;
	String merge_positions;

	boolean processed = false;

	public Order()
	{
	}

	public Order(String ord_id)
	{
		this.ord_id = ord_id;
	}

	public Order(String ord_id, String opr_id)
	{
		this.ord_id = ord_id;
		this.opr_id = opr_id;
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

	public String getIs_new_doc()
	{
		return is_new_doc;
	}

	public void setIs_new_doc(String is_new_doc)
	{
		this.is_new_doc = is_new_doc;
	}

	public String getOrd_id()
	{
		return ord_id;
	}

	public void setOrd_id(String ord_id)
	{
		this.ord_id = ord_id;
	}

	public String getOpr_id()
	{
		return opr_id;
	}

	public void setOpr_id(String opr_id)
	{
		this.opr_id = opr_id;
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

	public String getOrd_date_ts()
	{
		return StringUtil.appDateString2dbDateString(ord_date);
	}

	public void setOrd_date(String ord_date)
	{
		this.ord_date = ord_date;
	}

	public Blank getBlank()
	{
		return blank;
	}

	public void setBlank(Blank blank)
	{
		this.blank = blank;
	}

	public Contractor getContractor()
	{
		return contractor;
	}

	public void setContractor(Contractor contractor)
	{
		this.contractor = contractor;
	}

	public ContactPerson getContact_person()
	{
		return contact_person;
	}

	public void setContact_person(ContactPerson contact_person)
	{
		this.contact_person = contact_person;
	}

	public String getOrd_concerning()
	{
		return ord_concerning;
	}

	public void setOrd_concerning(String ord_concerning)
	{
		this.ord_concerning = ord_concerning;
	}

	public String getOrd_preamble()
	{
		return ord_preamble;
	}

	public void setOrd_preamble(String ord_preamble)
	{
		this.ord_preamble = ord_preamble;
	}

	public String getOrd_pay_condition()
	{
		return ord_pay_condition;
	}

	public void setOrd_pay_condition(String ord_pay_condition)
	{
		this.ord_pay_condition = ord_pay_condition;
	}

	public IncoTerm getDeliveryCondition()
	{
		return deliveryCondition;
	}

	public void setDeliveryCondition(IncoTerm deliveryCondition)
	{
		this.deliveryCondition = deliveryCondition;
	}

	public String getOrd_addr()
	{
		return ord_addr;
	}

	public void setOrd_addr(String ord_addr)
	{
		this.ord_addr = ord_addr;
	}

	public String getOrd_delivery_term()
	{
		return ord_delivery_term;
	}

	public void setOrd_delivery_term(String ord_delivery_term)
	{
		this.ord_delivery_term = ord_delivery_term;
	}

	public String getOrd_add_info()
	{
		return ord_add_info;
	}

	public void setOrd_add_info(String ord_add_info)
	{
		this.ord_add_info = ord_add_info;
	}

	public User getDirector()
	{
		return director;
	}

	public void setDirector(User director)
	{
		this.director = director;
	}

	public User getLogist()
	{
		return logist;
	}

	public void setLogist(User logist)
	{
		this.logist = logist;
	}

	public User getDirector_rb()
	{
		return director_rb;
	}

	public void setDirector_rb(User director_rb)
	{
		this.director_rb = director_rb;
	}

	public User getChief_dep()
	{
		return chief_dep;
	}

	public void setChief_dep(User chief_dep)
	{
		this.chief_dep = chief_dep;
	}

	public User getManager()
	{
		return manager;
	}

	public void setManager(User manager)
	{
		this.manager = manager;
	}

	public Contractor getContractor_for()
	{
		return contractor_for;
	}

	public void setContractor_for(Contractor contractor_for)
	{
		this.contractor_for = contractor_for;
	}

	public Contract getContract()
	{
		return contract;
	}

	public void setContract(Contract contract)
	{
		this.contract = contract;
	}

	public Specification getSpecification()
	{
		return specification;
	}

	public void setSpecification(Specification specification)
	{
		this.specification = specification;
	}

	public StuffCategory getStuffCategory()
	{
		return stuffCategory;
	}

	public void setStuffCategory(StuffCategory stuffCategory)
	{
		this.stuffCategory = stuffCategory;
	}

	public String getOrd_sent_to_prod_date()
	{
		return ord_sent_to_prod_date;
	}

	public String getOrd_sent_to_prod_date_ts()
	{
		return StringUtil.appDateString2dbDateString(ord_sent_to_prod_date);
	}

	public void setOrd_sent_to_prod_date(String ord_sent_to_prod_date)
	{
		this.ord_sent_to_prod_date = ord_sent_to_prod_date;
	}

	public String getOrd_received_conf_date()
	{
		return ord_received_conf_date;
	}

	public String getOrd_received_conf_date_ts()
	{
		return StringUtil.appDateString2dbDateString(ord_received_conf_date);
	}

	public void setOrd_received_conf_date(String ord_received_conf_date)
	{
		this.ord_received_conf_date = ord_received_conf_date;
	}

	public String getOrd_num_conf()
	{
		return ord_num_conf;
	}

	public void setOrd_num_conf(String ord_num_conf)
	{
		this.ord_num_conf = ord_num_conf;
	}

	public String getOrd_date_conf()
	{
		return ord_date_conf;
	}

	public String getOrd_date_conf_ts()
	{
		return StringUtil.appDateString2dbDateString(ord_date_conf);
	}

	public void setOrd_date_conf(String ord_date_conf)
	{
		this.ord_date_conf = ord_date_conf;
	}

	public String getOrd_conf_sent_date()
	{
		return ord_conf_sent_date;
	}

	public String getOrd_conf_sent_date_ts()
	{
		return StringUtil.appDateString2dbDateString(ord_conf_sent_date);
	}

	public void setOrd_conf_sent_date(String ord_conf_sent_date)
	{
		this.ord_conf_sent_date = ord_conf_sent_date;
	}

	public String getOrd_ready_for_deliv_date()
	{
		return ord_ready_for_deliv_date;
	}

	public String getOrd_ready_for_deliv_date_ts()
	{
		return StringUtil.appDateString2dbDateString(ord_ready_for_deliv_date);
	}

	public void setOrd_ready_for_deliv_date(String ord_ready_for_deliv_date)
	{
		this.ord_ready_for_deliv_date = ord_ready_for_deliv_date;
	}

	public String getOrd_executed_date()
	{
		return ord_executed_date;
	}

	public boolean isExecuted()
	{
		return !StringUtil.isEmpty(getOrd_executed_date());
	}

	public String getOrd_executed_date_ts()
	{
		return StringUtil.appDateString2dbDateString(ord_executed_date);
	}

	public void setOrd_executed_date(String ord_executed_date)
	{
		this.ord_executed_date = ord_executed_date;
	}

	public String getOrd_block()
	{
		return ord_block;
	}

	public void setOrd_block(String ord_block)
	{
		this.ord_block = ord_block;
	}

	public boolean isBlock()
	{
		return !StringUtil.isEmpty(getOrd_block());
	}

	public double getOrd_summ()
	{
		return ord_summ;
	}

	public String getOrd_summ_formatted()
	{
		return StringUtil.double2appCurrencyString(ord_summ);
	}

	public void setOrd_summ(double ord_summ)
	{
		this.ord_summ = ord_summ;
	}

	public String getOrd_delivery_cost_by()
	{
		return ord_delivery_cost_by;
	}

	public void setOrd_delivery_cost_by(String ord_delivery_cost_by)
	{
		this.ord_delivery_cost_by = ord_delivery_cost_by;
	}

	public double getOrd_delivery_cost()
	{
		return ord_delivery_cost;
	}

	public void setOrd_delivery_cost(double ord_delivery_cost)
	{
		this.ord_delivery_cost = ord_delivery_cost;
	}

	public String getOrd_donot_calculate_netto()
	{
		return ord_donot_calculate_netto;
	}

	public void setOrd_donot_calculate_netto(String ord_donot_calculate_netto)
	{
		this.ord_donot_calculate_netto = ord_donot_calculate_netto;
	}

	public boolean isCalculateNetto()
	{
		return StringUtil.isEmpty(getOrd_donot_calculate_netto());
	}

	public String getOrd_discount_all()
	{
		return ord_discount_all;
	}

	public void setOrd_discount_all(String ord_discount_all)
	{
		this.ord_discount_all = ord_discount_all;
	}

	public double getOrd_discount()
	{
		return ord_discount;
	}

	public void setOrd_discount(double ord_discount)
	{
		this.ord_discount = ord_discount;
	}

	public Currency getCurrency()
	{
		return currency;
	}

	public void setCurrency(Currency currency)
	{
		this.currency = currency;
	}

	public String getOrd_include_nds()
	{
		return ord_include_nds;
	}

	public void setOrd_include_nds(String ord_include_nds)
	{
		this.ord_include_nds = ord_include_nds;
	}

	public double getOrd_nds_rate()
	{
		return ord_nds_rate;
	}

	public void setOrd_nds_rate(double ord_nds_rate)
	{
		this.ord_nds_rate = ord_nds_rate;
	}

	public Seller getSellerForWho()
	{
		return sellerForWho;
	}

	public void setSellerForWho(Seller sellerForWho)
	{
		this.sellerForWho = sellerForWho;
	}

	public Seller getSeller()
	{
		return seller;
	}

	public void setSeller(Seller seller)
	{
		this.seller = seller;
	}

	public String getOrd_count_itog_flag()
	{
		return ord_count_itog_flag;
	}

	public void setOrd_count_itog_flag(String ord_count_itog_flag)
	{
		this.ord_count_itog_flag = ord_count_itog_flag;
	}

	public String getOrd_add_reduction_flag()
	{
		return ord_add_reduction_flag;
	}

	public void setOrd_add_reduction_flag(String ord_add_reduction_flag)
	{
		this.ord_add_reduction_flag = ord_add_reduction_flag;
	}

	public double getOrd_add_reduction()
	{
		return ord_add_reduction;
	}

	public void setOrd_add_reduction(double ord_add_reduction)
	{
		this.ord_add_reduction = ord_add_reduction;
	}

	public String getOrd_add_red_pre_pay_flag()
	{
		return ord_add_red_pre_pay_flag;
	}

	public void setOrd_add_red_pre_pay_flag(String ord_add_red_pre_pay_flag)
	{
		this.ord_add_red_pre_pay_flag = ord_add_red_pre_pay_flag;
	}

	public double getOrd_add_red_pre_pay()
	{
		return ord_add_red_pre_pay;
	}

	public void setOrd_add_red_pre_pay(double ord_add_red_pre_pay)
	{
		this.ord_add_red_pre_pay = ord_add_red_pre_pay;
	}

	public String getOrd_all_include_in_spec()
	{
		return ord_all_include_in_spec;
	}

	public boolean isAllIncludeInSpec()
	{
		return !StringUtil.isEmpty(getOrd_all_include_in_spec());
	}

	public void setOrd_all_include_in_spec(String ord_all_include_in_spec)
	{
		this.ord_all_include_in_spec = ord_all_include_in_spec;
	}

	public String getOrd_annul()
	{
		return ord_annul;
	}

	public void setOrd_annul(String ord_annul)
	{
		this.ord_annul = ord_annul;
	}

	public String getOrd_in_one_spec()
	{
		return ord_in_one_spec;
	}

	public void setOrd_in_one_spec(String ord_in_one_spec)
	{
		this.ord_in_one_spec = ord_in_one_spec;
	}

	public String getOrd_comment()
	{
		return ord_comment;
	}

	public void setOrd_comment(String ord_comment)
	{
		this.ord_comment = ord_comment;
	}

	public String getOrd_comment_covering_letter()
	{
		return ord_comment_covering_letter;
	}

	public void setOrd_comment_covering_letter(String ord_comment_covering_letter)
	{
		this.ord_comment_covering_letter = ord_comment_covering_letter;
	}

	public String getOrd_date_conf_all()
	{
		return ord_date_conf_all;
	}

	public void setOrd_date_conf_all(String ord_date_conf_all)
	{
		this.ord_date_conf_all = ord_date_conf_all;
	}

	public String getOrd_ready_for_deliv_date_all()
	{
		return ord_ready_for_deliv_date_all;
	}

	public void setOrd_ready_for_deliv_date_all(String ord_ready_for_deliv_date_all)
	{
		this.ord_ready_for_deliv_date_all = ord_ready_for_deliv_date_all;
	}

	public ShippingDocType getShippingDocType()
	{
		return shippingDocType;
	}

	public void setShippingDocType(ShippingDocType shippingDocType)
	{
		this.shippingDocType = shippingDocType;
	}

	public String getOrd_shp_doc_number()
	{
		return ord_shp_doc_number;
	}

	public void setOrd_shp_doc_number(String ord_shp_doc_number)
	{
		this.ord_shp_doc_number = ord_shp_doc_number;
	}

	public String getOrd_ship_from_stock()
	{
		return ord_ship_from_stock;
	}

	public void setOrd_ship_from_stock(String ord_ship_from_stock)
	{
		this.ord_ship_from_stock = ord_ship_from_stock;
	}

	public String getOrd_arrive_in_lithuania()
	{
		return ord_arrive_in_lithuania;
	}

	public void setOrd_arrive_in_lithuania(String ord_arrive_in_lithuania)
	{
		this.ord_arrive_in_lithuania = ord_arrive_in_lithuania;
	}

	public String getOrd_by_guaranty()
	{
		return ord_by_guaranty;
	}

	public void setOrd_by_guaranty(String ord_by_guaranty)
	{
		this.ord_by_guaranty = ord_by_guaranty;
	}

	public String getIs_warn()
	{
		return is_warn;
	}

	public void setIs_warn(String is_warn)
	{
		this.is_warn = is_warn;
	}

	public int getCount_day_curr_minus_sent_to_prod()
	{
		return count_day_curr_minus_sent_to_prod;
	}

	public void setCount_day_curr_minus_sent_to_prod(int count_day_curr_minus_sent_to_prod)
	{
		this.count_day_curr_minus_sent_to_prod = count_day_curr_minus_sent_to_prod;
	}

	public double getOrd_produce_count()
	{
		return ord_produce_count;
	}

	public void setOrd_produce_count(double ord_produce_count)
	{
		this.ord_produce_count = ord_produce_count;
	}

	public double getOrd_produce_count_executed()
	{
		return ord_produce_count_executed;
	}

	public void setOrd_produce_count_executed(double ord_produce_count_executed)
	{
		this.ord_produce_count_executed = ord_produce_count_executed;
	}

	public String getCrq_id()
	{
		return crq_id;
	}

	public void setCrq_id(String crq_id)
	{
		this.crq_id = crq_id;
	}

	public int getCountItog()
	{
		return countItog;
	}

	public void setCountItog(int countItog)
	{
		this.countItog = countItog;
	}

	public List<OrderPayment> getOrderPayments()
	{
		return orderPayments;
	}

	public void setOrderPayments(List<OrderPayment> orderPayments)
	{
		this.orderPayments = orderPayments;
	}

	public List<OrderPaySum> getOrderPaySums()
	{
		return orderPaySums;
	}

	public void setOrderPaySums(List<OrderPaySum> orderPaySums)
	{
		this.orderPaySums = orderPaySums;
	}

	public List<OrderExecutedAndDate> getOrderExecutedByDates()
	{
		return orderExecutedByDates;
	}

	public void setOrderExecutedByDates(List<OrderExecutedAndDate> orderExecutedByDates)
	{
		this.orderExecutedByDates = orderExecutedByDates;
	}

	public double getPaySum()
	{
		double paySum = 0;
		for (OrderPaySum orderPaySum : getOrderPaySums())
		{
			paySum += orderPaySum.getOps_sum();
		}

		return paySum;
	}

	public List<OrderExecutedDateLine> getOrderExecutedDate()
	{
		return orderExecutedDate;
	}

	public void setOrderExecutedDate(List<OrderExecutedDateLine> orderExecutedDate)
	{
		this.orderExecutedDate = orderExecutedDate;
	}

	public List<OrderExecutedLine> getOrderExecuted()
	{
		return orderExecuted;
	}

	public void setOrderExecuted(List<OrderExecutedLine> orderExecuted)
	{
		this.orderExecuted = orderExecuted;
	}

	public String getShow_unit()
	{
		return show_unit;
	}

	public void setShow_unit(String show_unit)
	{
		this.show_unit = show_unit;
	}

	public String getMerge_positions()
	{
		return merge_positions;
	}

	public void setMerge_positions(String merge_positions)
	{
		this.merge_positions = merge_positions;
	}

	public String getOrd_print_scale()
	{
		return ord_print_scale;
	}

	public void setOrd_print_scale(String ord_print_scale)
	{
		this.ord_print_scale = ord_print_scale;
	}

	public String getOrd_letter_scale()
	{
		return ord_letter_scale;
	}

	public void setOrd_letter_scale(String ord_letter_scale)
	{
		this.ord_letter_scale = ord_letter_scale;
	}

	public String getOrd_logist_signature()
	{
		return ord_logist_signature;
	}

	public void setOrd_logist_signature(String ord_logist_signature)
	{
		this.ord_logist_signature = ord_logist_signature;
	}

	public String getOrd_director_rb_signature()
	{
		return ord_director_rb_signature;
	}

	public void setOrd_director_rb_signature(String ord_director_rb_signature)
	{
		this.ord_director_rb_signature = ord_director_rb_signature;
	}

	public String getOrd_chief_dep_signature()
	{
		return ord_chief_dep_signature;
	}

	public void setOrd_chief_dep_signature(String ord_chief_dep_signature)
	{
		this.ord_chief_dep_signature = ord_chief_dep_signature;
	}

	public String getOrd_manager_signature()
	{
		return ord_manager_signature;
	}

	public void setOrd_manager_signature(String ord_manager_signature)
	{
		this.ord_manager_signature = ord_manager_signature;
	}

	public boolean isProcessed()
	{
		return processed;
	}

	public void setProcessed(boolean processed)
	{
		this.processed = processed;
	}

	public List<OrderProduce> getProduces()
	{
		return produces;
	}

	public void setProduces(List<OrderProduce> produces)
	{
		this.produces = produces;
	}

	public boolean isHaveItog()
	{
		return haveItog;
	}//возвращает true, если есть заблокированне позиций номенклатуры. п.1525

	public void setHaveItog(boolean haveItog)
	{
		this.haveItog = haveItog;
	}

	public boolean importFromCP(CommercialProposal commercialProposal)
	{
		boolean haveBlockPosition = false;

		if (isHaveItog())
		{
			for (int i = 0; i < getCountItog(); i++)
			{
				getProduces().remove(getProduces().size() - 1);
			}
		}

		commercialProposal.calculate();
		List cprProduces = commercialProposal.getProduces();

		for (int i = 0; i < cprProduces.size() - commercialProposal.getCountItogRecord(); i++)
		{
			boolean needAdd = true;
			CommercialProposalProduce commercialProposalProduce = (CommercialProposalProduce) cprProduces.get(i);
			if (commercialProposalProduce.getProduce() != null && commercialProposalProduce.getProduce().isBBlock())
			{
				needAdd = false;
				haveBlockPosition = true;
			}
			OrderProduce orderProduce = getEmptyProduce();
			orderProduce.setOrd_id(ord_id);
			//по старому
			if (!StringUtil.isEmpty(commercialProposal.getCpr_old_version()))
			{
				//1074 - Надо, чтобы в эти поля инфа при импорте не копировалась.
			  /*
        orderProduce.setOpr_produce_name(commercialProposalProduce.getLpr_produce_name());
        orderProduce.setOpr_catalog_num(commercialProposalProduce.getLpr_catalog_num());
        */
				if (!StringUtil.isEmpty(commercialProposalProduce.getLpr_catalog_num())) // только если каталожный номер заполнен
				{
					List<DboProduce> listDboProduces = DboProduce.findByCatalogNumberAndStuffCategory(commercialProposalProduce.getLpr_catalog_num(), new Integer(getStuffCategory().getId()));
					if (listDboProduces.size() != 0)
					{
						orderProduce.setProduce(listDboProduces.get(0));
						orderProduce.setStf_id(getStuffCategory().getId());

						if (listDboProduces.get(0).isBBlock()) //блокированные позиции - "не видим"
						{
							needAdd = false;
						}
					}
				}
			}
			else
			{
				if (commercialProposalProduce.getStuffCategory().getId().equals(getStuffCategory().getId()))
				{
					orderProduce.setProduce(commercialProposalProduce.getProduce());
					orderProduce.setStf_id(getStuffCategory().getId());
				}
				else
				{
					needAdd = false;
				}
			}
			orderProduce.setOpr_count(commercialProposalProduce.getLpr_count());

			//если
			// 1) Весь товар сразу включать в спецификацию (импорт)
			// 2) в импортируемом КП "Цены указаны на условиях" = DDP
			// то в табличной части заказа заполнять колонку "Продажная цена за единицу без НДС, бел.руб." = p*V, где:
			//    p - "Прод. цена + Уп + Трансп + Монтаж + Обучен + Тамож. расходы" (КП)
			//    V - "Курс пересчёта" (КП)
			if (isAllIncludeInSpec() && (commercialProposal.getPriceCondition().getName().equalsIgnoreCase(IncoTerm.DDP) || commercialProposal.getPriceCondition().getName().equalsIgnoreCase(IncoTerm.DDP_2010)))
			{
				orderProduce.setDrp_price(commercialProposalProduce.getSale_price_parking_trans_custom_print());
			}

			if ("1".equals(ord_discount_all))
			{
				orderProduce.setOpr_discount(ord_discount);
			}

			if (!isCalculateNetto())
			{
				orderProduce.setOpr_price_netto(commercialProposalProduce.getLpr_price_netto());
			}

			if (needAdd)
			{
				getProduces().add(orderProduce);
			}
		}

		setHaveItog(false);
		return haveBlockPosition;
	}

	public void calculateInString(List produces)
	{
		if (getOrderExecuted().size() > 0)
		{
			int j = 0;
			for (OrderExecutedLine orderExecutedLine : getOrderExecuted())
			{
				orderExecutedLine.setNumber(String.valueOf(j + 1));
				j++;
			}
		}

		for (int i = 0; i < produces.size(); i++)
		{
			OrderProduce orderProduce = (OrderProduce) produces.get(i);

			double summ;
			double priceNetto;
			orderProduce.setNumber(String.valueOf(i + 1));

			if ("1".equals(ord_discount_all))
			{
				orderProduce.setOpr_discount(ord_discount);
			}
			if (isCalculateNetto())
			{
				if (!"1".equals(getOrd_count_itog_flag()))
				{
					priceNetto = orderProduce.getOpr_price_brutto() * (1 - orderProduce.getOpr_discount() / 100);
					priceNetto = StringUtil.roundN(priceNetto, 2);
					orderProduce.setOpr_price_netto(priceNetto);
				}
				else
				{
					priceNetto = orderProduce.getOpr_price_brutto();
				}
			}
			else
			{
				priceNetto = orderProduce.getOpr_price_netto();
			}
			summ = priceNetto * orderProduce.getOpr_count();
			summ = StringUtil.roundN(summ, 2);
			orderProduce.setOpr_summ(summ);

			double executed = 0;
			//Получаем исполненные из соотв. формы, могут быть скорректированы.
			if (getOrderExecuted().size() > 0)
			{
				for (OrderExecutedLine orderExecutedLine : getOrderExecuted())
				{
					if (orderProduce.getNumber().equals(orderExecutedLine.getNumber()))
					{
						executed = orderExecutedLine.getOpr_count_executed();
					}
				}
			}
			else if (!StringUtil.isEmpty(orderProduce.getOpr_id())) //получаем исполненные из данных базы, если старый. Для нового в базе ничего нет.
			{
				for (OrderExecutedAndDate orderExecutedAndDate : getOrderExecutedByDates())
				{
					//этот товар, и за эту дату - в сумму добавляем.
					if (orderExecutedAndDate.getOpr_id().equals(orderProduce.getOpr_id()))
					{
						executed += orderExecutedAndDate.getOpr_count_executed();
					}
				}
			}
			orderProduce.setOpr_count_executed(executed);
		}
	}

	public static OrderProduce getEmptyProduce()
	{
		OrderProduce orderProduce = new OrderProduce();
		orderProduce.setNumber("");
		orderProduce.setOpr_use_prev_number("");
		orderProduce.setOpr_produce_name("");
		orderProduce.setOpr_catalog_num("");
		orderProduce.setOpr_price_brutto(0.0);
		orderProduce.setOpr_count(0.0);
		orderProduce.setOpr_price_netto(0.0);
		orderProduce.setOpr_occupied("");
		orderProduce.setOpr_comment("");
		orderProduce.setDrp_price(0.0);
		orderProduce.setOpr_parent_id("");
		orderProduce.setOpr_have_depend("");

		return orderProduce;
	}

	public void calculate(List<OrderProduce> produces, LocaledPropertyMessageResources words)
	{
		if (isHaveItog())
		{
			for (int i = 0; i < getCountItog(); i++)
			{
				produces.remove(produces.size() - 1);
			}
		}

		calculateInString(produces);
		ord_summ = 0.0;

		for (OrderProduce orderProduce : produces)
		{
			ord_summ += orderProduce.getOpr_summ();
		}

		OrderProduce orderProduceTotal = getEmptyProduce();
		try
		{
			orderProduceTotal.setOpr_produce_name(words.getMessage("OrderProduces.itogo_big"));
		}
		catch (Exception e)
		{
			log.error(e);
		}
		ord_summ = StringUtil.roundN(ord_summ, 2);
		orderProduceTotal.setOpr_summ(ord_summ);
		orderProduceTotal.setIs_itog(true);
		produces.add(orderProduceTotal);

		int lItog = 1;

		if ("1".equals(ord_count_itog_flag))
		{
			orderProduceTotal = getEmptyProduce();
			try
			{
				String discount_str = words.getMessage("OrderProduces.itogo_discount");
				discount_str += " ";
				discount_str += StringUtil.double2appCurrencyString(ord_discount);
				discount_str += words.getMessage("OrderProduces.itogo_percent");
				orderProduceTotal.setOpr_produce_name(discount_str);
			}
			catch (Exception e)
			{
				log.error(e);
			}
			double discount_summ = ord_summ * ord_discount / 100 * -1;
			discount_summ = StringUtil.roundN(discount_summ, 2);
			orderProduceTotal.setOpr_summ(discount_summ);
			orderProduceTotal.setIs_itog(true);
			produces.add(orderProduceTotal);

			ord_summ += discount_summ;

			lItog++;
		}

		if ("1".equals(ord_add_reduction_flag))
		{
			orderProduceTotal = getEmptyProduce();
			try
			{
				String discount_str = words.getMessage("OrderProduces.itogo_add_reduction");
				discount_str += " ";
				discount_str += StringUtil.double2appCurrencyString(ord_add_reduction);
				discount_str += words.getMessage("OrderProduces.itogo_percent");
				orderProduceTotal.setOpr_produce_name(discount_str);
			}
			catch (Exception e)
			{
				log.error(e);
			}
			double discount_summ = ord_summ * ord_add_reduction / 100 * -1;
			discount_summ = StringUtil.roundN(discount_summ, 2);
			orderProduceTotal.setOpr_summ(discount_summ);
			orderProduceTotal.setIs_itog(true);
			produces.add(orderProduceTotal);

			ord_summ += discount_summ;

			lItog++;
		}

		if ("1".equals(ord_add_red_pre_pay_flag))
		{
			orderProduceTotal = getEmptyProduce();
			try
			{
				String discount_str = words.getMessage("OrderProduces.itogo_add_red_pre_pay");
				discount_str += " ";
				discount_str += StringUtil.double2appCurrencyString(ord_add_red_pre_pay);
				discount_str += words.getMessage("OrderProduces.itogo_percent");
				orderProduceTotal.setOpr_produce_name(discount_str);
			}
			catch (Exception e)
			{
				log.error(e);
			}
			double discount_summ = ord_summ * ord_add_red_pre_pay / 100 * -1;
			discount_summ = StringUtil.roundN(discount_summ, 2);
			orderProduceTotal.setOpr_summ(discount_summ);
			orderProduceTotal.setIs_itog(true);
			produces.add(orderProduceTotal);

			ord_summ += discount_summ;

			lItog++;
		}

		if ("1".equals(ord_count_itog_flag) || "1".equals(ord_add_reduction_flag) || "1".equals(ord_add_red_pre_pay_flag))
		{
			if ("1".equals(ord_count_itog_flag) && "1".equals(ord_add_reduction_flag) && "1".equals(ord_add_red_pre_pay_flag))
			{
				orderProduceTotal = getEmptyProduce();
				try
				{
					String discount_str;
					discount_str = words.getMessage("OrderProduces.itogo_add_reduction_include");
					discount_str += " ";
					discount_str += StringUtil.double2appCurrencyString(ord_discount);
					discount_str += words.getMessage("OrderProduces.itogo_percent");
					discount_str += ", ";
					discount_str += StringUtil.double2appCurrencyString(ord_add_reduction);
					discount_str += words.getMessage("OrderProduces.itogo_percent");
					discount_str += ", ";
					discount_str += StringUtil.double2appCurrencyString(ord_add_red_pre_pay);
					discount_str += words.getMessage("OrderProduces.itogo_percent");
					orderProduceTotal.setOpr_produce_name(discount_str);
				}
				catch (Exception e)
				{
					log.error(e);
				}
			}
			else if ("1".equals(ord_add_reduction_flag) && "1".equals(ord_add_red_pre_pay_flag))
			{
				orderProduceTotal = getEmptyProduce();
				try
				{
					String discount_str;
					discount_str = words.getMessage("OrderProduces.itogo_add_reduction_include");
					discount_str += " ";
					discount_str += StringUtil.double2appCurrencyString(ord_add_reduction);
					discount_str += words.getMessage("OrderProduces.itogo_percent");
					discount_str += ", ";
					discount_str += StringUtil.double2appCurrencyString(ord_add_red_pre_pay);
					discount_str += words.getMessage("OrderProduces.itogo_percent");
					orderProduceTotal.setOpr_produce_name(discount_str);
				}
				catch (Exception e)
				{
					log.error(e);
				}
			}
			else if ("1".equals(ord_count_itog_flag) && "1".equals(ord_add_reduction_flag))
			{
				orderProduceTotal = getEmptyProduce();
				try
				{
					String discount_str;
					discount_str = words.getMessage("OrderProduces.itogo_add_reduction_include");
					discount_str += " ";
					discount_str += StringUtil.double2appCurrencyString(ord_discount);
					discount_str += words.getMessage("OrderProduces.itogo_percent");
					discount_str += ", ";
					discount_str += StringUtil.double2appCurrencyString(ord_add_reduction);
					discount_str += words.getMessage("OrderProduces.itogo_percent");
					orderProduceTotal.setOpr_produce_name(discount_str);
				}
				catch (Exception e)
				{
					log.error(e);
				}
			}
			else if ("1".equals(ord_count_itog_flag) && "1".equals(ord_add_red_pre_pay_flag))
			{
				orderProduceTotal = getEmptyProduce();
				try
				{
					String discount_str;
					discount_str = words.getMessage("OrderProduces.itogo_add_reduction_include");
					discount_str += " ";
					discount_str += StringUtil.double2appCurrencyString(ord_discount);
					discount_str += words.getMessage("OrderProduces.itogo_percent");
					discount_str += ", ";
					discount_str += StringUtil.double2appCurrencyString(ord_add_red_pre_pay);
					discount_str += words.getMessage("OrderProduces.itogo_percent");
					orderProduceTotal.setOpr_produce_name(discount_str);
				}
				catch (Exception e)
				{
					log.error(e);
				}
			}
			else if ("1".equals(ord_add_reduction_flag))
			{
				orderProduceTotal = getEmptyProduce();
				try
				{
					String discount_str;
					discount_str = words.getMessage("OrderProduces.itogo_discount_include");
					discount_str += " ";
					discount_str += StringUtil.double2appCurrencyString(ord_add_reduction);
					discount_str += words.getMessage("OrderProduces.itogo_percent");
					orderProduceTotal.setOpr_produce_name(discount_str);
				}
				catch (Exception e)
				{
					log.error(e);
				}
			}
			else if ("1".equals(ord_add_red_pre_pay_flag))
			{
				orderProduceTotal = getEmptyProduce();
				try
				{
					String discount_str;
					discount_str = words.getMessage("OrderProduces.itogo_discount_include");
					discount_str += " ";
					discount_str += StringUtil.double2appCurrencyString(ord_add_red_pre_pay);
					discount_str += words.getMessage("OrderProduces.itogo_percent");
					orderProduceTotal.setOpr_produce_name(discount_str);
				}
				catch (Exception e)
				{
					log.error(e);
				}
			}
			else
			{
				orderProduceTotal = getEmptyProduce();
				try
				{
					String discount_str;
					discount_str = words.getMessage("OrderProduces.itogo_discount_include");
					discount_str += " ";
					discount_str += StringUtil.double2appCurrencyString(ord_discount);
					discount_str += words.getMessage("OrderProduces.itogo_percent");
					orderProduceTotal.setOpr_produce_name(discount_str);
				}
				catch (Exception e)
				{
					log.error(e);
				}
			}
			ord_summ = StringUtil.roundN(ord_summ, 2);
			orderProduceTotal.setOpr_summ(ord_summ);
			orderProduceTotal.setIs_itog(true);
			produces.add(orderProduceTotal);

			lItog++;
		}

		if ("1".equals(ord_include_nds))
		{
			orderProduceTotal = getEmptyProduce();
			try
			{
				String ndsTotal = words.getMessage("OrderProduces.itogo_nds");
				ndsTotal += " ";
				ndsTotal += StringUtil.double2appCurrencyString(ord_nds_rate);
				ndsTotal += words.getMessage("OrderProduces.itogo_percent");
				orderProduceTotal.setOpr_produce_name(ndsTotal);
			}
			catch (Exception e)
			{
				log.error(e);
			}
			double ordSumNds = ord_summ / 100 * ord_nds_rate;
			ordSumNds = StringUtil.roundN(ordSumNds, 2);
			ord_summ += ordSumNds;
			orderProduceTotal.setOpr_summ(ordSumNds);
			orderProduceTotal.setIs_itog(true);
			produces.add(orderProduceTotal);

			lItog++;
		}

		setCountItog(lItog);

		setHaveItog(true);
	}

	private String checkDatesInLists(String appListDate, String dbPayDate)
	{
		String retStr = "";

		Date dateInList;
		Date paymentDate;
		IActionContext context = ActionContext.threadInstance();
		try
		{
			dateInList = StringUtil.appDateString2Date(appListDate);
			paymentDate = StringUtil.dbDateString2Date(dbPayDate);
			if (dateInList.before(paymentDate))
			{
				long day = StringUtil.getDaysBetween(dateInList, paymentDate);
				retStr += " " + StrutsUtil.getMessage(context, "Order.delay", Long.toString(day));
			}
		}
		catch (Exception e)
		{
			log.error(e);
		}

		return retStr;
	}

	private String checkDatesInListsWithNow(String appListDate) throws Exception
	{
		Date paymentDate = StringUtil.getCurrentDateTime();
		return checkDatesInLists(appListDate, StringUtil.date2dbDateString(paymentDate));
	}

	private void calculateLastSums(List<OrderPayment> orderPayments)
	{
		if (orderPayments.size() <= 0)
			return;

		double sum = 0;
		double percent = 0;

		for (int i = 0; i < orderPayments.size() - 1; i++)
		{
			sum += orderPayments.get(i).getOrp_sum();
			percent += orderPayments.get(i).getOrp_percent();
		}

		orderPayments.get(orderPayments.size() - 1).setOrp_percent(StringUtil.roundN(100 - percent, 5));
		orderPayments.get(orderPayments.size() - 1).setOrp_sum(getOrd_summ() - sum);
	}

	public void calculatePaymentsDescription(List<OrderPayment> orderPayments)
	{
		IActionContext context = ActionContext.threadInstance();
		try
		{
			if (getOrderPaySums().size() > 0)
			{
				double sumInList = 0;
				for (OrderPayment orderPayment : orderPayments)
				{
					orderPayment.setDescription("");
					orderPayment.setProcessed(false);
					sumInList += orderPayment.getOrp_sum();

					double paymentSum = 0;
					for (OrderPaySum orderPaySum : getOrderPaySums())
					{
						if (StringUtil.isEmpty(orderPaySum.getOps_date()))
							continue;

						paymentSum += orderPaySum.getOps_sum();
						String description;
						if (paymentSum >= sumInList)
						{
							description = StrutsUtil.getMessage(context, "Order.executed", orderPaySum.getOps_date_formatted());
							if (!StringUtil.isEmpty(orderPayment.getOrp_date()))
							{
								description += checkDatesInLists(orderPayment.getOrp_date_formatted(), orderPaySum.getOps_date());
							}
							orderPayment.setDescription(description);
							orderPayment.setProcessed(true);
							break;
						}
					}
				}

				sumInList = 0;
				for (OrderPayment orderPayment : orderPayments)
				{
					if (orderPayment.isProcessed()) //считаем сумму обработанных
					{
						sumInList += orderPayment.getOrp_sum();
					}
					if (!orderPayment.isProcessed()) //первая не обработанная - и последняя
					{
						double paymentSum = 0;
						for (OrderPaySum orderPaySum : getOrderPaySums())
						{
							paymentSum += orderPaySum.getOps_sum();
						}

						String description;
						if (paymentSum > sumInList)
						{
							description = StrutsUtil.getMessage(context, "Order.executed_part");
							if (!StringUtil.isEmpty(orderPayment.getOrp_date()))
							{
								description += checkDatesInListsWithNow(orderPayment.getOrp_date_formatted());
							}
							orderPayment.setDescription(description);
						}

						break;
					}
				}
			}
			else
			{
				for (OrderPayment orderPayment : orderPayments)
				{
					if (!StringUtil.isEmpty(orderPayment.getOrp_date()))
					{
						orderPayment.setDescription(checkDatesInListsWithNow(orderPayment.getOrp_date_formatted()));
					}
				}
			}
		}
		catch (Exception e)
		{
			log.error(e);
		}

		calculateLastSums(orderPayments);
	}

	public long calculateDelay(List<OrderPayment> orderPayments)
	{
		long countDayDelay = 0;
		Date dateInList = null;
		Date paymentDate = null;

		try
		{
			if (getOrderPaySums().size() > 0)
			{
				double sumInList = 0;
				for (OrderPayment orderPayment : orderPayments)
				{
					orderPayment.setProcessed(false);
					sumInList += orderPayment.getOrp_sum();

					double paymentSum = 0;
					for (OrderPaySum orderPaySum : getOrderPaySums())
					{
						if (StringUtil.isEmpty(orderPaySum.getOps_date()))
							continue;

						paymentSum += orderPaySum.getOps_sum();
						if (paymentSum >= sumInList)
						{
							if (!StringUtil.isEmpty(orderPayment.getOrp_date()))
							{
								if (!StringUtil.isEmpty(checkDatesInLists(orderPayment.getOrp_date_formatted(), orderPaySum.getOps_date())))
								{
									dateInList = StringUtil.appDateString2Date(orderPayment.getOrp_date_formatted());
									paymentDate = StringUtil.dbDateString2Date(orderPaySum.getOps_date());
								}
							}
							orderPayment.setProcessed(true);
							break;
						}
					}
				}

				sumInList = 0;
				for (OrderPayment orderPayment : orderPayments)
				{
					if (orderPayment.isProcessed()) //считаем сумму обработанных
					{
						sumInList += orderPayment.getOrp_sum();
					}
					if (!orderPayment.isProcessed()) //первая не обработанная - и последняя
					{
						double paymentSum = 0;
						for (OrderPaySum orderPaySum : getOrderPaySums())
						{
							paymentSum += orderPaySum.getOps_sum();
						}

						if (paymentSum > sumInList)
						{
							if (!StringUtil.isEmpty(orderPayment.getOrp_date()))
							{
								if (!StringUtil.isEmpty(checkDatesInListsWithNow(orderPayment.getOrp_date_formatted())))
								{
									dateInList = StringUtil.appDateString2Date(orderPayment.getOrp_date_formatted());
									paymentDate = StringUtil.dbDateString2Date(StringUtil.date2dbDateString(StringUtil.getCurrentDateTime())); //обрезаем время, чтобы не влияло на результат
								}
							}
						}

						break;
					}
				}
			}
			else
			{
				for (OrderPayment orderPayment : orderPayments)
				{
					if (!StringUtil.isEmpty(orderPayment.getOrp_date()))
					{
						if (!StringUtil.isEmpty(checkDatesInListsWithNow(orderPayment.getOrp_date_formatted())))
						{
							dateInList = StringUtil.appDateString2Date(orderPayment.getOrp_date_formatted());
							paymentDate = StringUtil.dbDateString2Date(StringUtil.date2dbDateString(StringUtil.getCurrentDateTime())); //обрезаем время, чтобы не влияло на результат
						}
					}
				}
			}
		}
		catch (Exception e)
		{
			log.error(e);
		}

		if (dateInList != null && paymentDate != null)
		{
			countDayDelay = StringUtil.getDaysBetween(dateInList, paymentDate);
		}

		return countDayDelay;
	}

	public void setListParentIds()
	{
		for (OrderProduce orderProduce : getProduces())
		{
			orderProduce.setOrd_id(getOrd_id());
		}
		for (OrderPayment orderPayment : getOrderPayments())
		{
			orderPayment.setOrd_id(getOrd_id());
		}
		for (OrderPaySum orderPaySum : getOrderPaySums())
		{
			orderPaySum.setOrd_id(getOrd_id());
		}
	}

	public void setListIdsToNull()
	{
		for (OrderProduce orderProduce : getProduces())
		{
			orderProduce.setOpr_id(null);
		}
		for (OrderPayment orderPayment : getOrderPayments())
		{
			orderPayment.setOrp_id(null);
		}
		for (OrderPaySum orderPaySum : getOrderPaySums())
		{
			orderPaySum.setOps_id(null);
		}
	}

	public void setSetCorrectFieldsForOrderClone()
	{
		for (OrderProduce orderProduce : getProduces())
		{
			orderProduce.setOpr_produce_name("");
			orderProduce.setOpr_catalog_num("");
			orderProduce.setOpr_count_executed(0.0);
			orderProduce.setOpr_occupied("");
			orderProduce.setProductTerms(new ArrayList<ProductionTerm>());
			orderProduce.setReadyForShippings(new ArrayList<ReadyForShipping>());
		}
	}

	public OrderProduce findProduce(String number)
	{
		for (OrderProduce orderProduce : getProduces())
		{
			if (orderProduce.getNumber().equalsIgnoreCase(number))
				return orderProduce;
		}

		return null;
	}

	public boolean haveProduce(Integer prdId)
	{
		for (OrderProduce orderProduce : getProduces())
		{
			if (null != orderProduce.getProduce() && null != orderProduce.getProduce().getId() && orderProduce.getProduce().getId().intValue() == prdId.intValue())
				return true;
		}

		return false;
	}

	public void updateProduce(String number, OrderProduce orderProduceIn)
	{
		for (int i = 0; i < getProduces().size(); i++)
		{
			OrderProduce orderProduce = getProduces().get(i);

			if (orderProduce.getNumber().equalsIgnoreCase(number))
			{
				getProduces().set(i, orderProduceIn);
				return;
			}
		}
	}

	public void deleteProduce(String number)
	{
		for (int i = 0; i < getProduces().size(); i++)
		{
			OrderProduce orderProduce = getProduces().get(i);

			if (orderProduce.getNumber().equalsIgnoreCase(number))
			{
				//если последующие элементы есть и они зависимы от удаляемого, то:
				//подчинённые позиции становятся "основными"
				if (getProduces().size() > i + 1)
				{
					int j = i + 1;
					while (j < getProduces().size() && !StringUtil.isEmpty(getProduces().get(j).getOpr_use_prev_number()))
					{
						OrderProduce orderProduceDep = getProduces().get(j);
						orderProduceDep.setOpr_use_prev_number("");
						orderProduceDep.setOpr_parent_id("");
						j++;
					}
				}
				getProduces().remove(i);
				break;
			}
		}

		for (OrderExecutedLine orderExecutedLine : getOrderExecuted())
		{
			if (number.equals(orderExecutedLine.getNumber()))
			{
				getOrderExecuted().remove(orderExecutedLine);
				break;
			}
		}

		if (getProduces().size() > 0)
		{
			getProduces().get(0).setOpr_use_prev_number("");
		}
	}

	public void insertProduce(OrderProduce orderProduce)
	{
		if (getOrderExecutedDate().size() > 0)
		{
			OrderExecutedLine orderExecutedLine = new OrderExecutedLine();
			orderExecutedLine.setNumber(Integer.toString(getProduces().size() - getCountItog() + 1));
			orderExecutedLine.setOpr_id(orderProduce.getOpr_id());
			orderExecutedLine.setOpr_count(orderProduce.getOpr_count());
			orderExecutedLine.setCatalog_num(orderProduce.getCatalogNumberForStuffCategory());
			orderExecutedLine.setPrd_type(orderProduce.getProduce().getType());
			orderExecutedLine.setPrd_params(orderProduce.getProduce().getParams());
			orderExecutedLine.setPrd_add_params(orderProduce.getProduce().getAddParams());
			orderExecutedLine.setPrd_name(orderProduce.getProduce().getName());

			List<String> orderExecutedByDate = new ArrayList<String>();
			for (OrderExecutedDateLine orderDate : getOrderExecutedDate())
			{
				orderExecutedByDate.add(StringUtil.double2appCurrencyString(0));
			}
			orderExecutedLine.setOrderExecutedByDate(orderExecutedByDate);

			getOrderExecuted().add(orderExecutedLine);
		}
		getProduces().add(getProduces().size() - getCountItog(), orderProduce);
	}

	public void setWarn(IActionContext context)
	{
		try
		{
			OrderDAO.loadWarn(context, this);
		}
		catch (Exception e)
		{
			log.error(e);
		}

		try
		{
			/**
			 * Cообщение "Риск нарушить срок поставки по контракту!" выдаётся
			 * если хотя бы у одной позиции в табличке "Сроки изготовления" есть дата,
			 * удовлетворяющая условию для появления этого сообщения.
			 * Дата спецификации - дата срока изготовления < 14
			 */
			if (StringUtil.isEmpty(getOrd_date_conf()) && StringUtil.isEmpty(getOrd_date_conf_all()))
			{
				Date spcDate;
				spcDate = StringUtil.dbDateString2Date(specification.getSpc_delivery_date());
				for (OrderProduce orderProduce : getProduces())
				{
					boolean found = false;
					for (int j = 0; j < orderProduce.getProductTerms().size(); j++)
					{
						ProductionTerm productionTerm = orderProduce.getProductTerms().get(j);
						if (!StringUtil.isEmpty(productionTerm.getPtr_date()))
						{
							Date ptrDate;
							ptrDate = StringUtil.dbDateString2Date(productionTerm.getPtr_date());
							if (StringUtil.getDaysBetween(ptrDate, spcDate) < 14)
							{
								setIs_warn("1");
								found = true;
								break;
							}
						}
					}

					if (found)
					{
						break;
					}
				}
			}
		}
		catch (Exception e)
		{
			log.error(e);
		}
	}

	public double getOprCountOccupiedByNumber(String number)
	{
		for (int i = 0; i < getProduces().size(); i++)
		{
			OrderProduce orderProduce = getProduces().get(i);

			if (orderProduce.getNumber().equalsIgnoreCase(number))
			{
				return orderProduce.getOpr_count_occupied();
			}
		}

		return 0;
	}

	// defines ordering based on first name
	class DateComparator implements Comparator<OrderExecutedDateLine>
	{
		public int compare(OrderExecutedDateLine date1, OrderExecutedDateLine date2)
		{
			if (StringUtil.isEmpty(date1.getDate())) return -1;
			if (StringUtil.isEmpty(date2.getDate())) return 1;
			try
			{
				return StringUtil.appDateString2Date(date1.getDate()).compareTo(StringUtil.appDateString2Date(date2.getDate()));
			}
			catch (ParseException ex)
			{
				return -1;
			}
		}
	}

	public void calculateOrderExecutedAndDateLines(IActionContext context, List<OrderExecutedAndDate> orderExecutedAndDates)
	{
		Map<String, Boolean> dates = new HashMap<String, Boolean>();
		for (OrderExecutedAndDate orderExecutedAndDate : orderExecutedAndDates)
		{
			if (null == dates.get(orderExecutedAndDate.getOpr_date_executed()))
			{
				dates.put(orderExecutedAndDate.getOpr_date_executed(), true);
			}
		}
		getOrderExecutedDate().clear();
		for (String date : dates.keySet())
		{
			getOrderExecutedDate().add(new OrderExecutedDateLine(date));
		}
		Collections.sort(getOrderExecutedDate(), new DateComparator());

		getOrderExecuted().clear();
		for (int i = 0; i < getProduces().size() - getCountItog(); i++)
		{
			OrderProduce orderProduce = getProduces().get(i);

			OrderExecutedLine orderExecutedLine = new OrderExecutedLine();
			orderExecutedLine.setNumber(Integer.toString(i + 1));
			orderExecutedLine.setOpr_id(orderProduce.getOpr_id());
			orderExecutedLine.setOpr_count(orderProduce.getOpr_count());
			orderExecutedLine.setPrd_name(orderProduce.getProduce().getName());
			orderExecutedLine.setPrd_type(orderProduce.getProduce().getType());
			orderExecutedLine.setPrd_params(orderProduce.getProduce().getParams());
			orderExecutedLine.setPrd_add_params(orderProduce.getProduce().getAddParams());
			orderExecutedLine.setCatalog_num(orderProduce.getCatalogNumberForStuffCategory());

			List<String> orderExecutedByDate = new ArrayList<String>();
			for (OrderExecutedDateLine orderDate : getOrderExecutedDate())
			{
				double sumForDate = 0;
				for (OrderExecutedAndDate orderExecutedAndDate : orderExecutedAndDates)
				{
					//этот товар, и за эту дату - в сумму добавляем.
					if (orderExecutedAndDate.getOpr_id().equals(orderProduce.getOpr_id()) && orderExecutedAndDate.getOpr_date_executed().equals(orderDate.getDate()))
					{
						sumForDate += orderExecutedAndDate.getOpr_count_executed();
					}
				}
				orderExecutedByDate.add(StringUtil.double2appCurrencyString(sumForDate));
			}
			orderExecutedLine.setOrderExecutedByDate(orderExecutedByDate);

			getOrderExecuted().add(orderExecutedLine);
		}

		recalcAllExecuted();

		int j = 0;
		for (OrderExecutedDateLine dateLine : getOrderExecutedDate())
		{
			double sumForDate = 0;
			for (OrderExecutedLine line : getOrderExecuted())
			{
				sumForDate += StringUtil.appCurrencyString2double(line.getOrderExecutedByDate().get(j));
			}
			dateLine.setCountInDate(sumForDate);
			j++;
		}

		List<OrderExecutedDateLine> orderExecutedDate = new ArrayList<OrderExecutedDateLine>();
		for (OrderExecutedDateLine dateLine : getOrderExecutedDate())
		{
			orderExecutedDate.add(new OrderExecutedDateLine(dateLine));
		}
		OrderExecutedDateLines orderExecutedDateLines = new OrderExecutedDateLines();
		orderExecutedDateLines.setOrderExecutedDate(orderExecutedDate);
		StoreUtil.putSession(context.getRequest(), orderExecutedDateLines);
	}

	public void calculateOrderExecutedAndDateLines()
	{
		getOrderExecuted().clear();
		for (int i = 0; i < getProduces().size() - getCountItog(); i++)
		{
			OrderProduce orderProduce = getProduces().get(i);

			OrderExecutedLine orderExecutedLine = new OrderExecutedLine();
			orderExecutedLine.setNumber(Integer.toString(i + 1));
			orderExecutedLine.setOpr_id(orderProduce.getOpr_id());
			orderExecutedLine.setOpr_count(orderProduce.getOpr_count());
			orderExecutedLine.setPrd_name(orderProduce.getProduce().getName());
			orderExecutedLine.setPrd_type(orderProduce.getProduce().getType());
			orderExecutedLine.setPrd_params(orderProduce.getProduce().getParams());
			orderExecutedLine.setPrd_add_params(orderProduce.getProduce().getAddParams());
			orderExecutedLine.setCatalog_num(orderProduce.getCatalogNumberForStuffCategory());

			orderExecutedLine.setOrderExecutedByDate(new ArrayList<String>());

			getOrderExecuted().add(orderExecutedLine);
		}
	}

	//Добавляем в конец колонку с пустой строкой, при необходимости.
	public void checkAndFillLastColumn()
	{
		int size = getOrderExecutedDate().size();
		if ((size > 0 && !StringUtil.isEmpty(getOrderExecutedDate().get(size - 1).getDate())) || size == 0)
		{
			getOrderExecutedDate().add(new OrderExecutedDateLine("", true));
			for (OrderExecutedLine orderExecutedLine : getOrderExecuted())
			{
				orderExecutedLine.getOrderExecutedByDate().add(StringUtil.double2appCurrencyString(0));
			}
		}
	}

	//Пересчитывем количество исполнено по строкам.
	private void recalcAllExecuted()
	{
		for (OrderExecutedLine orderExecutedLine : getOrderExecuted())
		{
			double allExecutedInLine = 0;
			for (String executedOnDate : orderExecutedLine.getOrderExecutedByDate())
			{
				allExecutedInLine += StringUtil.appCurrencyString2double(executedOnDate);
			}
			orderExecutedLine.setOpr_count_executed(allExecutedInLine);
		}
	}

	//Объединяем позиции по элементу справочника и одинаковыми ценами нетто
	public List<OrderProduce> getMergedProduces(boolean mergeDepended, boolean checkPrice, boolean checkSpc)
	{
		List<OrderProduce> mergedProducesCopy = new ArrayList<OrderProduce>();
		for (int i = 0; i < getProduces().size(); i++)
		{
			OrderProduce produce = getProduces().get(i);

			boolean needAdd = true;
			//Чекбокс "Не объединять одинаковые позиции" не отмечен
			if (mergeDepended)
			{
		        /*
		        * При выводе на печать строки с одинаковыми позициями (т.е. выбран один и тотже элемент справочника)
		        * И одинаковыми ценами нетто у этих позиций объединять в одну.
		        * В этой колонке в соотв. строке через запятую указывать номера строк уже электронного документа.
		        * */
				for (OrderProduce produceCopy : mergedProducesCopy)
				{
					if (
									null != produceCopy.getProduce() &&
													null != produceCopy.getProduce().getId() &&
													null != produce.getProduce().getId() &&
													produceCopy.getProduce().getId().equals(produce.getProduce().getId()) &&
													(!checkPrice || produceCopy.getOpr_price_netto() == produce.getOpr_price_netto()) &&
													(
																	!checkSpc ||
																					(
																									produceCopy.getSpecification().getSpc_id().equals(produce.getSpecification().getSpc_id()) &&
																													produceCopy.getContract().getCon_id().equals(produce.getContract().getCon_id()) &&
																													produceCopy.getContractor().getId().equals(produce.getContractor().getId())
																					)
													)
									)
					{
						produceCopy.setOpr_count(produceCopy.getOpr_count() + produce.getOpr_count());
						produceCopy.setOpr_summ(produceCopy.getOpr_summ() + produce.getOpr_summ());
						//Количество изменилось, значит зависимых уже не будет.
						produceCopy.setOpr_use_prev_number("");

						needAdd = false;
						break;
					}
				}
			} //if ( mergeDepended )

			if (needAdd)
			{
				mergedProducesCopy.add(new OrderProduce(produce));
			}
		}

		return mergedProducesCopy;
	}
}
