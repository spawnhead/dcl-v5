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
import net.sam.dcl.dbo.DboProduce;
import net.sam.dcl.dbo.DboAttachment;
import org.apache.struts.action.ActionMapping;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.ArrayList;

/**
 * Date: Aug 6, 2005
 * Time: 5:37:29 PM
 */
public class OrderForm extends BaseDispatchValidatorForm
{
  protected static Log log = LogFactory.getLog(OrderForm.class);

  String print;
  String printLetter;
  boolean needPrint;
  boolean needPrintLetter;

  String is_new_doc;
  String number;
  String gen_num;

  String ord_id;

  User createUser = new User();
  User editUser = new User();
  String usr_date_create;
  String usr_date_edit;

  String usr_code;

  String ord_number;
  String ord_date;
  Blank blank = new Blank();
  Contractor contractor = new Contractor();
  ContactPerson contact_person = new ContactPerson();
  String ord_concerning;
  String ord_preamble;
  String ord_pay_condition;
  IncoTerm deliveryCondition = new IncoTerm();
  String ord_addr;
  String ord_delivery_term;
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
  String ord_summ;
  String noRoundSum;

  String ord_delivery_cost_by;
  String ord_delivery_cost;
  String ord_donot_calculate_netto;

  String ord_discount_all;
  String ord_discount;

  Currency currency;
  String ord_delivery_cost_currency;

  String ord_include_nds;
  String ord_nds_rate;

	Seller sellerForWho = new Seller();
	Seller seller = new Seller();

  String ord_count_itog_flag;
  String ord_add_reduction_flag;
  String ord_add_reduction;
  String ord_add_red_pre_pay_flag;
  String ord_add_red_pre_pay;
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

  boolean formReadOnly = false;
  boolean readOnlyIfNotLikeManager = true;
  boolean readOnlyIfNotLikeLogist = true;
  boolean readOnlyIfNotLikeLogistOrManager = true;
  boolean readOnlyIfNotLikeLogistOrUIL = true;
  boolean readOnlySentToProdDate = true;
  boolean readOnlyForExecuted = true;
  boolean readOnlyForCoveringLetter = false;

  String show_unit;
  String merge_positions;

  DboAttachment templateExcel;

  HolderImplUsingList orderProducesGrid = new HolderImplUsingList();
  int countItog;
  boolean sameNumberAsPrevious = false;
  boolean showForAdmin = false;

  boolean showAskClearTable = true;

  List<OrderPayment> orderPayments = new ArrayList<OrderPayment>();
  List<OrderPaySum> orderPaySums = new ArrayList<OrderPaySum>();

  HolderImplUsingList attachmentsGrid = new HolderImplUsingList();
  DeferredAttachmentService attachmentService = null;
  String attachmentId;

  public void reset(ActionMapping mapping, HttpServletRequest request)
  {
    if (!isReadOnlyIfNotLikeManager())
    {
      setOrd_discount_all("");
      setOrd_include_nds("");
      setOrd_count_itog_flag("");
      setOrd_add_reduction_flag("");
      setOrd_add_red_pre_pay_flag("");
      setOrd_all_include_in_spec("");
    }
    if (!isReadOnlyIfNotLikeLogist())
    {
      setOrd_date_conf_all("");
      setOrd_ready_for_deliv_date_all("");
      setOrd_annul("");
    }
    if (!isReadOnlyIfNotLikeLogistOrManager())
    {
      setOrd_logist_signature("");
      setOrd_director_rb_signature("");
      setOrd_chief_dep_signature("");
      setOrd_manager_signature("");
    }
    if (!isFormReadOnly())
    {
      setOrd_in_one_spec("");
    }
    setShow_unit("");
    setMerge_positions("");
    setOrd_by_guaranty("");
    super.reset(mapping, request);
  }

  public String getTemplateIdOrder()
  {
    return String.valueOf(ConstDefinitions.templateIdOrder);
  }

  public String getPrint()
  {
    return print;
  }

  public void setPrint(String print)
  {
    this.print = print;
  }

  public boolean isNeedPrint()
  {
    return needPrint;
  }

  public void setNeedPrint(boolean needPrint)
  {
    this.needPrint = needPrint;
  }

  public String getPrintLetter()
  {
    return printLetter;
  }

  public void setPrintLetter(String printLetter)
  {
    this.printLetter = printLetter;
  }

  public boolean isNeedPrintLetter()
  {
    return needPrintLetter;
  }

  public void setNeedPrintLetter(boolean needPrintLetter)
  {
    this.needPrintLetter = needPrintLetter;
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

  public String getOrd_id()
  {
    return ord_id;
  }

  public void setOrd_id(String ord_id)
  {
    this.ord_id = ord_id;
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

  public String getOrd_date_tm()
  {
    String tm_date = StringUtil.appDateString2dbDateString(ord_date);
    tm_date += " 23:59:59";
    return tm_date;
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

  public String getContractNumberWithDate()
  {
    return getContract().getNumberWithDate();
  }

  public String getSpecificationNumberWithDate()
  {
    return getSpecification().getNumberWithDate();
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

  public String getOrd_summ()
  {
    return ord_summ;
  }

  public void setOrd_summ(String ord_summ)
  {
    this.ord_summ = ord_summ;
  }

  public String getNoRoundSum()
  {
    return noRoundSum;
  }

  public void setNoRoundSum(String noRoundSum)
  {
    this.noRoundSum = noRoundSum;
  }

  public String getOrd_delivery_cost_by()
  {
    return ord_delivery_cost_by;
  }

  public void setOrd_delivery_cost_by(String ord_delivery_cost_by)
  {
    this.ord_delivery_cost_by = ord_delivery_cost_by;
  }

  public String getOrd_delivery_cost()
  {
    return ord_delivery_cost;
  }

  public void setOrd_delivery_cost(String ord_delivery_cost)
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

  public String getDonot_calculate_netto()
  {
    return getOrd_donot_calculate_netto();
  }

  public String getCalculate_netto()
  {
    return StringUtil.isEmpty(getOrd_donot_calculate_netto()) ? "1" : "";
  }

  public String getOrd_discount_all()
  {
    return ord_discount_all;
  }

  public void setOrd_discount_all(String ord_discount_all)
  {
    this.ord_discount_all = ord_discount_all;
  }

  public String getOrd_discount()
  {
    return ord_discount;
  }

  public void setOrd_discount(String ord_discount)
  {
    this.ord_discount = ord_discount;
  }

  public boolean getDiscountDisabled()
  {
    return (!"1".equals(ord_discount_all));
  }

  public boolean getNds_rateDisabled()
  {
    return (!"1".equals(ord_include_nds));
  }

  public Currency getCurrency()
  {
    return currency;
  }

  public void setCurrency(Currency currency)
  {
    this.currency = currency;
  }

  public String getOrd_delivery_cost_currency()
  {
    if (null != currency)
    {
      return currency.getName();
    }
    else
    {
      return "";
    }
  }

  public String getOrd_include_nds()
  {
    return ord_include_nds;
  }

  public void setOrd_include_nds(String ord_include_nds)
  {
    this.ord_include_nds = ord_include_nds;
  }

  public String getOrd_nds_rate()
  {
    return ord_nds_rate;
  }

  public void setOrd_nds_rate(String ord_nds_rate)
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

  public String getOrd_add_reduction()
  {
    return ord_add_reduction;
  }

  public void setOrd_add_reduction(String ord_add_reduction)
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

  public String getOrd_add_red_pre_pay()
  {
    return ord_add_red_pre_pay;
  }

  public void setOrd_add_red_pre_pay(String ord_add_red_pre_pay)
  {
    this.ord_add_red_pre_pay = ord_add_red_pre_pay;
  }

  public boolean getAddReductionDisabled()
  {
    return (!"1".equals(ord_add_reduction_flag));
  }

  public boolean getAddRedPreRayDisabled()
  {
    return (!"1".equals(ord_add_red_pre_pay_flag));
  }

  public String getOrd_all_include_in_spec()
  {
    return ord_all_include_in_spec;
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

  public String getOrd_arrive_in_lithuania()
  {
    return ord_arrive_in_lithuania;
  }

  public String getOrd_ship_from_stock()
  {
    return ord_ship_from_stock;
  }

  public void setOrd_ship_from_stock(String ord_ship_from_stock)
  {
    this.ord_ship_from_stock = ord_ship_from_stock;
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

  public boolean isShowSendMailMsg()
  {
    return (StringUtil.isEmpty(ord_received_conf_date) && getCount_day_curr_minus_sent_to_prod() > 3);
  }

  public HolderImplUsingList getOrderProducesGrid()
  {
    return orderProducesGrid;
  }

  public void setDataToGrid(List<OrderProduce> produces)
  {
    List<OrderProduceForForm> result = processProduces(produces);
    orderProducesGrid.setDataList(result);
  }

  public int getCountItog()
  {
    return countItog;
  }

  public void setCountItog(int countItog)
  {
    this.countItog = countItog;
  }

  public static List<OrderProduceForForm> processProduces(List<OrderProduce> produces)
  {
    List<OrderProduceForForm> result = new ArrayList<OrderProduceForForm>();
    OrderProduceForForm prevProduce = null;
	  int viewNumber2 = 0;
    for (OrderProduce orderProduce : produces)
    {
      OrderProduceForForm produce = new OrderProduceForForm(orderProduce);
      if (prevProduce == null)
      {
        produce.setViewNumber("1");
	      viewNumber2 = 1;
      }
      else
      {
        if (!StringUtil.isEmpty(produce.getNumber()))
        {
          produce.setViewNumber(prevProduce.getViewNumber());
          if (!"1".equals(produce.getOpr_use_prev_number()))
          {
            produce.incrementViewNumber();
	          viewNumber2 = 1;
          }
	        else
          {
	          prevProduce.setViewNumber2(String.valueOf(viewNumber2));
	          viewNumber2++;
	          produce.setViewNumber2(String.valueOf(viewNumber2));
          }
        }
      }
      result.add(produce);
      prevProduce = produce;
    }
    return result;
  }

  public boolean isFormReadOnly()
  {
    return formReadOnly;
  }

  public void setFormReadOnly(boolean formReadOnly)
  {
    this.formReadOnly = formReadOnly;
  }

  public boolean isReadOnlyIfNotLikeManager()
  {
    return readOnlyIfNotLikeManager;
  }

  public boolean isReadOnlyStuffCategory()
  {
    return readOnlyIfNotLikeManager || orderProducesGrid.getDataList().size() > 1;
  }

  public void setReadOnlyIfNotLikeManager(boolean readOnlyIfNotLikeManager)
  {
    this.readOnlyIfNotLikeManager = readOnlyIfNotLikeManager;
  }

  public boolean isReadOnlyIfNotLikeLogist()
  {
    return readOnlyIfNotLikeLogist;
  }

  public void setReadOnlyIfNotLikeLogist(boolean readOnlyIfNotLikeLogist)
  {
    this.readOnlyIfNotLikeLogist = readOnlyIfNotLikeLogist;
  }

  public boolean isReadOnlyIfNotLikeLogistOrManager()
  {
    return readOnlyIfNotLikeLogistOrManager;
  }

  public void setReadOnlyIfNotLikeLogistOrManager(boolean readOnlyIfNotLikeLogistOrManager)
  {
    this.readOnlyIfNotLikeLogistOrManager = readOnlyIfNotLikeLogistOrManager;
  }

  public boolean isReadOnlyIfNotLikeLogistOrUIL()
  {
    return readOnlyIfNotLikeLogistOrUIL;
  }

  public void setReadOnlyIfNotLikeLogistOrUIL(boolean readOnlyIfNotLikeLogistOrUIL)
  {
    this.readOnlyIfNotLikeLogistOrUIL = readOnlyIfNotLikeLogistOrUIL;
  }

  public boolean isReadOnlyDateCond()
  {
    return readOnlyIfNotLikeLogist || StringUtil.isEmpty(getOrd_date_conf_all());
  }

  public boolean isReadOnlySave()
  {
    return readOnlyIfNotLikeLogistOrUIL && readOnlyIfNotLikeLogistOrManager && formReadOnly;
  }

  public boolean isReadOnlySentToProdDate()
  {
    return readOnlySentToProdDate;
  }

  public void setReadOnlySentToProdDate(boolean readOnlySentToProdDate)
  {
    this.readOnlySentToProdDate = readOnlySentToProdDate;
  }

  public boolean isReadOnlyForExecuted()
  {
    return readOnlyForExecuted;
  }

  public void setReadOnlyForExecuted(boolean readOnlyForExecuted)
  {
    this.readOnlyForExecuted = readOnlyForExecuted;
  }

	public boolean isReadOnlyForCoveringLetter()
	{
		return readOnlyForCoveringLetter || formReadOnly;
	}

	public void setReadOnlyForCoveringLetter(boolean readOnlyForCoveringLetter)
	{
		this.readOnlyForCoveringLetter = readOnlyForCoveringLetter;
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

  public boolean isSameNumberAsPrevious()
  {
    return sameNumberAsPrevious;
  }

  public void setSameNumberAsPrevious(boolean sameNumberAsPrevious)
  {
    this.sameNumberAsPrevious = sameNumberAsPrevious;
  }

  public boolean isShowForAdmin()
  {
    return showForAdmin;
  }

  public void setShowForAdmin(boolean showForAdmin)
  {
    this.showForAdmin = showForAdmin;
  }

  public boolean isShowAskClearTable()
  {
    return showAskClearTable;
  }

  public void setShowAskClearTable(boolean showAskClearTable)
  {
    this.showAskClearTable = showAskClearTable;
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

  public boolean isShowDownload()
  {
    return !StringUtil.isEmpty(getTemplateId());
  }

  public String getPrintLegend()
  {
    IActionContext context = ActionContext.threadInstance();
    try
    {
      return StrutsUtil.getMessage(context, "Order.printLegend");
    }
    catch (Exception e)
    {
      log.error(e);
    }

    return "";
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

	public boolean isExecutedOrPartExecuted()
  {
    if(!StringUtil.isEmpty(getOrd_block()))
    {
      return true;
    }

    for (int i = 0; i < getOrderProducesGrid().getDataList().size() - getCountItog(); i++)
    {
      OrderForm.OrderProduceForForm orderProduce = (OrderForm.OrderProduceForForm) getOrderProducesGrid().getDataList().get(i);
      if ( orderProduce.getOpr_count_executed() > 0 && orderProduce.getOpr_count_executed() <= orderProduce.getOpr_count() )
      {
        return true;
      }
    }

    return false;
  }

  public int getOrderPaymentsCount()
  {
    return orderPayments.size();
  }

  public int getOrderPaySumsCount()
  {
    return orderPaySums.size();
  }

  public static class OrderProduceForForm
  {
    OrderProduce produce;
    String viewNumber;
    String viewNumber2;
    boolean group = false;

    public OrderProduceForForm(OrderProduce produce)
    {
      this.produce = produce;
    }

    public OrderProduce getOrderProduce()
    {
      return produce;
    }

    public String getNumber()
    {
      return produce.getNumber();
    }

    public String getOpr_catalog_num()
    {
      return produce.getOpr_catalog_num();
    }

    public double getOpr_count()
    {
      return produce.getOpr_count();
    }

    public String getOpr_count_formatted()
    {
      return produce.getOpr_count_formatted();
    }

    public double getOpr_count_executed()
    {
      return produce.getOpr_count_executed();
    }

    public String getOpr_count_executed_formatted()
    {
      return produce.getOpr_count_executed_formatted();
    }

    public double getOpr_discount()
    {
      return produce.getOpr_discount();
    }

    public String getOpr_discount_formatted()
    {
      return produce.getOpr_discount_formatted();
    }

    public String getOpr_id()
    {
      return produce.getOpr_id();
    }

    public double getOpr_price_brutto()
    {
      return produce.getOpr_price_brutto();
    }

    public String getOpr_price_brutto_formatted()
    {
      return produce.getOpr_price_brutto_formatted();
    }

    public double getOpr_price_netto()
    {
      return produce.getOpr_price_netto();
    }

    public String getOpr_price_netto_formatted()
    {
      return produce.getOpr_price_netto_formatted();
    }

    public String getOpr_produce_name()
    {
      return produce.getOpr_produce_name();
    }

    public DboProduce getProduce()
    {
      return produce.getProduce();
    }

    public String getProduce_name()
    {
      if (produce.isIs_itog())
      {
        return produce.getOpr_produce_name();
      }
      if (!StringUtil.isEmpty(produce.getProduce().getName()))
      {
        return produce.getProduce().getName();
      }
      return "";
    }

    public double getOpr_summ()
    {
      return produce.getOpr_summ();
    }

    public String getOpr_summ_formatted()
    {
      return produce.getOpr_summ_formatted();
    }

    public String getOpr_parent_id()
    {
      return produce.getOpr_parent_id();
    }

    public String getOpr_comment()
    {
      return produce.getOpr_comment();
    }

    public double getDrp_price()
    {
      return produce.getDrp_price();
    }

    public String getDrp_price_formatted()
    {
      return produce.getDrp_price_formatted();
    }

    public String getSpecificationNumbers()
    {
      return produce.getSpecificationNumbers();
    }

    public String getOpr_use_prev_number()
    {
      return produce.getOpr_use_prev_number();
    }

    public String getViewNumber()
    {
      return viewNumber;
    }

    public void setViewNumber(String viewNumber)
    {
      this.viewNumber = viewNumber;
    }

    public void incrementViewNumber()
    {
      if (null != viewNumber)
      {
        int num = Integer.parseInt(viewNumber);
        viewNumber = String.valueOf(num + 1);
      }
    }

	  public String getViewNumber2()
	  {
		  return viewNumber2;
	  }

	  public void setViewNumber2(String viewNumber2)
	  {
		  this.viewNumber2 = viewNumber2;
	  }

	  public String getViewNumberFormatted()
	  {
		  return getViewNumber() + (StringUtil.isEmpty(getViewNumber2()) ? "" : "/" + getViewNumber2());
	  }

	  public String getOpr_occupied()
    {
      return produce.getOpr_occupied();
    }

    public String getOrd_id()
    {
      return produce.getOrd_id();
    }

    public boolean isIs_itog()
    {
      return produce.isIs_itog();
    }

    public String getCatalogNumberForStuffCategory()
    {
      return produce.getCatalogNumberForStuffCategory();
    }

    public String getProduceNameForLanguage(String lngCode)
    {
      return produce.getProduceNameForLanguage(lngCode);
    }

    public String getUnitNameForLanguage(String lngCode)
    {
      return produce.getUnitNameForLanguage(lngCode);
    }

    public Contractor getContractor()
    {
      return produce.getContractor();
    }

    public void setContractor(Contractor contractor)
    {
      produce.setContractor(contractor);
    }

    public Contract getContract()
    {
      return produce.getContract();
    }

    public void setContract(Contract contract)
    {
      produce.setContract(contract);
    }

    public Specification getSpecification()
    {
      return produce.getSpecification();
    }

    public void setSpecification(Specification specification)
    {
      produce.setSpecification(specification);
    }

    public boolean isPinkBackgroundForExecuted()
    {
      return getOpr_count() > getOpr_count_executed() && StringUtil.isEmpty(getOpr_use_prev_number());
    }
  }
}
