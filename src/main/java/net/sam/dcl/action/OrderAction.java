package net.sam.dcl.action;

import net.sam.dcl.beans.*;
import net.sam.dcl.config.Config;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.controller.IDispatchable;
import net.sam.dcl.controller.actions.DBTransactionAction;
import net.sam.dcl.controller.actions.SelectFromGridAction;
import net.sam.dcl.dao.*;
import net.sam.dcl.form.OrderForm;
import net.sam.dcl.service.AttachmentException;
import net.sam.dcl.service.AttachmentsService;
import net.sam.dcl.service.DeferredAttachmentService;
import net.sam.dcl.taglib.table.*;
import net.sam.dcl.util.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForward;
import org.hibernate.Session;

import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class OrderAction extends DBTransactionAction implements IDispatchable
{
	protected static Log log = LogFactory.getLog(OrderAction.class);
	final static String referencedTable = "DCL_ORDER";

	private void saveCurrentFormToBean(IActionContext context)
	{
		OrderForm form = (OrderForm) context.getForm();

		Order order = (Order) StoreUtil.getSession(context.getRequest(), Order.class);

		order.setIs_new_doc(form.getIs_new_doc());

		order.setOrd_id(form.getOrd_id());
		try
		{
			if (!StringUtil.isEmpty(form.getCreateUser().getUsr_id()))
			{
				order.setCreateUser(UserDAO.load(context, form.getCreateUser().getUsr_id()));
			}
			if (!StringUtil.isEmpty(form.getEditUser().getUsr_id()))
			{
				order.setEditUser(UserDAO.load(context, form.getEditUser().getUsr_id()));
			}
			order.setUsr_date_create(form.getUsr_date_create());
			order.setUsr_date_edit(form.getUsr_date_edit());
			order.setOrd_number(form.getOrd_number());
			order.setOrd_date(form.getOrd_date());

			if (!StringUtil.isEmpty(form.getBlank().getBln_id()))
			{
				order.setBlank(BlankDAO.load(context, form.getBlank().getBln_id()));
			}

			if (!StringUtil.isEmpty(form.getContractor().getId()))
			{
				order.setContractor(ContractorDAO.load(context, form.getContractor().getId()));
			}

			if (!StringUtil.isEmpty(form.getContact_person().getCps_id()))
			{
				order.setContact_person(ContactPersonDAO.load(context, form.getContact_person().getCps_id()));
			}
			else
			{
				order.setContact_person(new ContactPerson());
			}

			order.setOrd_concerning(form.getOrd_concerning());
			order.setOrd_preamble(form.getOrd_preamble());
			order.setOrd_pay_condition(form.getOrd_pay_condition());
			order.setDeliveryCondition(form.getDeliveryCondition());
			order.setOrd_addr(form.getOrd_addr());
			order.setOrd_delivery_term(form.getOrd_delivery_term());
			order.setOrd_add_info(form.getOrd_add_info());

			if (!StringUtil.isEmpty(form.getDirector().getUsr_id()))
			{
				order.setDirector(UserDAO.load(context, form.getDirector().getUsr_id()));
			}

			if (!StringUtil.isEmpty(form.getLogist().getUsr_id()))
			{
				order.setLogist(UserDAO.load(context, form.getLogist().getUsr_id()));
			}

			if (!StringUtil.isEmpty(form.getDirector_rb().getUsr_id()))
			{
				order.setDirector_rb(UserDAO.load(context, form.getDirector_rb().getUsr_id()));
			}

			if (!StringUtil.isEmpty(form.getChief_dep().getUsr_id()))
			{
				order.setChief_dep(UserDAO.load(context, form.getChief_dep().getUsr_id()));
			}

			if (!StringUtil.isEmpty(form.getManager().getUsr_id()))
			{
				order.setManager(UserDAO.load(context, form.getManager().getUsr_id()));
			}

			if (!StringUtil.isEmpty(form.getContractor_for().getId()))
			{
				order.setContractor_for(ContractorDAO.load(context, form.getContractor_for().getId()));
			}
			else
			{
				order.setContractor_for(new Contractor());
				form.setContract(new Contract());
				form.setSpecification(new Specification());
			}

			if (!StringUtil.isEmpty(form.getContract().getCon_id()))
			{
				order.setContract(ContractDAO.load(context, form.getContract().getCon_id(), false));
			}
			else
			{
				order.setContract(new Contract());
				form.setSpecification(new Specification());
			}

			if (!StringUtil.isEmpty(form.getSpecification().getSpc_id()))
			{
				order.setSpecification(SpecificationDAO.load(context, form.getSpecification().getSpc_id()));
			}
			else
			{
				order.setSpecification(new Specification());
			}

			if (!StringUtil.isEmpty(form.getStuffCategory().getId()))
			{
				order.setStuffCategory(StuffCategoryDAO.load(context, form.getStuffCategory().getId()));
			}
			else
			{
				order.setStuffCategory(new StuffCategory());
			}

			if (!StringUtil.isEmpty(form.getCurrency().getId()))
			{
				order.setCurrency(CurrencyDAO.load(context, form.getCurrency().getId()));
			}

			if (!StringUtil.isEmpty(form.getShippingDocType().getId()))
			{
				order.setShippingDocType(ShippingDocTypeDAO.load(context, form.getShippingDocType().getId()));
			}
			else
			{
				order.setShippingDocType(new ShippingDocType());
			}

			if (!StringUtil.isEmpty(form.getSeller().getId()))
			{
				order.setSeller(SellerDAO.load(context, form.getSeller().getId()));
			}
			else
			{
				order.setSeller(new Seller());
			}

			if (!StringUtil.isEmpty(form.getSellerForWho().getId()))
			{
				order.setSellerForWho(SellerDAO.load(context, form.getSellerForWho().getId()));
			}
			else
			{
				order.setSellerForWho(new Seller());
			}

			if (!StringUtil.isEmpty(form.getDeliveryCondition().getId()))
			{
				IncoTermDAO.load(context, order.getDeliveryCondition());
			}
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
		}

		order.setOrd_sent_to_prod_date(form.getOrd_sent_to_prod_date());
		order.setOrd_received_conf_date(form.getOrd_received_conf_date());
		order.setOrd_num_conf(form.getOrd_num_conf());
		order.setOrd_date_conf(form.getOrd_date_conf());
		order.setOrd_conf_sent_date(form.getOrd_conf_sent_date());
		order.setOrd_ready_for_deliv_date(form.getOrd_ready_for_deliv_date());
		order.setOrd_executed_date(form.getOrd_executed_date());
		order.setOrd_block(form.getOrd_block());
		order.setOrd_delivery_cost_by(form.getOrd_delivery_cost_by());
		if (StringUtil.isEmpty(form.getOrd_delivery_cost()))
		{
			form.setOrd_delivery_cost("0,0");
		}
		order.setOrd_delivery_cost(StringUtil.appCurrencyString2double(form.getOrd_delivery_cost()));
		order.setOrd_donot_calculate_netto(form.getOrd_donot_calculate_netto());
		order.setOrd_discount_all(form.getOrd_discount_all());
		order.setOrd_discount(StringUtil.appCurrencyString2double(form.getOrd_discount()));
		order.setOrd_include_nds(form.getOrd_include_nds());
		order.setOrd_nds_rate(StringUtil.appCurrencyString2double(form.getOrd_nds_rate()));
		order.setOrd_count_itog_flag(form.getOrd_count_itog_flag());
		order.setOrd_add_reduction_flag(form.getOrd_add_reduction_flag());
		order.setOrd_add_reduction(StringUtil.appCurrencyString2double(form.getOrd_add_reduction()));
		order.setOrd_add_red_pre_pay_flag(form.getOrd_add_red_pre_pay_flag());
		order.setOrd_add_red_pre_pay(StringUtil.appCurrencyString2double(form.getOrd_add_red_pre_pay()));
		order.setOrd_all_include_in_spec(form.getOrd_all_include_in_spec());
		order.setOrd_annul(form.getOrd_annul());
		order.setOrd_in_one_spec(form.getOrd_in_one_spec());
		order.setOrd_comment(form.getOrd_comment());
		order.setOrd_comment_covering_letter(form.getOrd_comment_covering_letter());
		order.setOrd_date_conf_all(form.getOrd_date_conf_all());
		order.setOrd_ready_for_deliv_date_all(form.getOrd_ready_for_deliv_date_all());
		order.setOrd_shp_doc_number(form.getOrd_shp_doc_number());
		order.setOrd_ship_from_stock(form.getOrd_ship_from_stock());
		order.setOrd_arrive_in_lithuania(form.getOrd_arrive_in_lithuania());
		order.setOrd_by_guaranty(form.getOrd_by_guaranty());
		order.setOrd_print_scale(form.getOrd_print_scale());
		order.setOrd_letter_scale(form.getOrd_letter_scale());
		order.setCount_day_curr_minus_sent_to_prod(form.getCount_day_curr_minus_sent_to_prod());
		order.setOrd_logist_signature(form.getOrd_logist_signature());
		order.setOrd_director_rb_signature(form.getOrd_director_rb_signature());
		order.setOrd_chief_dep_signature(form.getOrd_chief_dep_signature());
		order.setOrd_manager_signature(form.getOrd_manager_signature());

		//только из формы взять можем
		order.setShow_unit(form.getShow_unit());
		order.setMerge_positions(form.getMerge_positions());

		StoreUtil.putSession(context.getRequest(), order);
	}

	private void getCurrentFormFromBean(IActionContext context, Order orderIn) throws Exception
	{
		OrderForm form = (OrderForm) context.getForm();
		Order order;
		if (null != orderIn)
		{
			order = orderIn;
		}
		else
		{
			order = (Order) StoreUtil.getSession(context.getRequest(), Order.class);
		}

		if (null != order)
		{
			form.setIs_new_doc(order.getIs_new_doc());

			form.setOrd_id(order.getOrd_id());
			form.setCreateUser(order.getCreateUser());
			form.setEditUser(order.getEditUser());
			form.setUsr_date_create(order.getUsr_date_create());
			form.setUsr_date_edit(order.getUsr_date_edit());
			form.setOrd_number(order.getOrd_number());
			form.setOrd_date(order.getOrd_date());
			form.setBlank(order.getBlank());
			form.setContractor(order.getContractor());
			form.setContact_person(order.getContact_person());
			form.setOrd_concerning(order.getOrd_concerning());
			form.setOrd_preamble(order.getOrd_preamble());
			form.setOrd_pay_condition(order.getOrd_pay_condition());
			form.setDeliveryCondition(order.getDeliveryCondition());
			form.setOrd_addr(order.getOrd_addr());
			form.setOrd_delivery_term(order.getOrd_delivery_term());
			form.setOrd_add_info(order.getOrd_add_info());
			form.setDirector(order.getDirector());
			form.setLogist(order.getLogist());
			form.setDirector_rb(order.getDirector_rb());
			form.setChief_dep(order.getChief_dep());
			form.setManager(order.getManager());
			form.setContractor_for(order.getContractor_for());
			form.setSpecification(order.getSpecification());
			if (!StringUtil.isEmpty(order.getSpecification().getSpc_id()))
			{
				SpecificationDAO.load(context, order.getSpecification());
				form.setContract(ContractDAO.load(context, order.getSpecification().getCon_id(), false));
			}
			else
			{
				form.setSpecification(new Specification());
				form.setContract(new Contract());
			}
			form.setStuffCategory(order.getStuffCategory());
			form.setCurrency(order.getCurrency());
			form.setOrd_sent_to_prod_date(order.getOrd_sent_to_prod_date());
			form.setOrd_received_conf_date(order.getOrd_received_conf_date());
			form.setOrd_num_conf(order.getOrd_num_conf());
			form.setOrd_date_conf(order.getOrd_date_conf());
			form.setOrd_conf_sent_date(order.getOrd_conf_sent_date());
			form.setOrd_ready_for_deliv_date(order.getOrd_ready_for_deliv_date());
			form.setOrd_executed_date(order.getOrd_executed_date());
			form.setOrd_block(order.getOrd_block());
			form.setOrd_delivery_cost_by(order.getOrd_delivery_cost_by());
			form.setOrd_delivery_cost(StringUtil.double2appCurrencyString(order.getOrd_delivery_cost()));
			form.setOrd_donot_calculate_netto(order.getOrd_donot_calculate_netto());
			form.setOrd_discount_all(order.getOrd_discount_all());
			form.setOrd_discount(StringUtil.double2appCurrencyString(order.getOrd_discount()));
			form.setOrd_include_nds(order.getOrd_include_nds());
			form.setOrd_nds_rate(StringUtil.double2appCurrencyString(order.getOrd_nds_rate()));
			form.setSellerForWho(order.getSellerForWho());
			form.setSeller(order.getSeller());
			form.setOrd_count_itog_flag(order.getOrd_count_itog_flag());
			form.setOrd_add_reduction_flag(order.getOrd_add_reduction_flag());
			form.setOrd_add_reduction(StringUtil.double2appCurrencyString(order.getOrd_add_reduction()));
			form.setOrd_add_red_pre_pay_flag(order.getOrd_add_red_pre_pay_flag());
			form.setOrd_add_red_pre_pay(StringUtil.double2appCurrencyString(order.getOrd_add_red_pre_pay()));
			form.setOrd_all_include_in_spec(order.getOrd_all_include_in_spec());
			form.setOrd_annul(order.getOrd_annul());
			form.setOrd_in_one_spec(order.getOrd_in_one_spec());
			form.setOrd_comment(order.getOrd_comment());
			form.setOrd_comment_covering_letter(order.getOrd_comment_covering_letter());
			form.setOrd_date_conf_all(order.getOrd_date_conf_all());
			form.setOrd_ready_for_deliv_date_all(order.getOrd_ready_for_deliv_date_all());
			form.setOrd_shp_doc_number(order.getOrd_shp_doc_number());
			form.setOrd_ship_from_stock(order.getOrd_ship_from_stock());
			form.setOrd_arrive_in_lithuania(order.getOrd_arrive_in_lithuania());
			form.setOrd_by_guaranty(order.getOrd_by_guaranty());
			form.setOrd_print_scale(order.getOrd_print_scale());
			form.setOrd_letter_scale(order.getOrd_letter_scale());
			form.setShippingDocType(order.getShippingDocType());
			form.setCount_day_curr_minus_sent_to_prod(order.getCount_day_curr_minus_sent_to_prod());
			form.setOrd_logist_signature(order.getOrd_logist_signature());
			form.setOrd_director_rb_signature(order.getOrd_director_rb_signature());
			form.setOrd_chief_dep_signature(order.getOrd_chief_dep_signature());
			form.setOrd_manager_signature(order.getOrd_manager_signature());

			form.setDataToGrid(order.getProduces());
		}
	}

	public ActionForward newContractor(IActionContext context) throws Exception
	{
		saveCurrentFormToBean(context);

		return context.getMapping().findForward("newContractor");
	}

	public ActionForward selectCP(IActionContext context) throws Exception
	{
		saveCurrentFormToBean(context);

		return context.getMapping().findForward("selectCP");
	}

	public ActionForward newContractorFor(IActionContext context) throws Exception
	{
		saveCurrentFormToBean(context);
		context.getRequest().getSession().setAttribute("newContractorFor", "true");

		return context.getMapping().findForward("newContractor");
	}

	public ActionForward newProduce(IActionContext context) throws Exception
	{
		saveCurrentFormToBean(context);

		return context.getMapping().findForward("newProduce");
	}

	public ActionForward cloneProduce(IActionContext context) throws Exception
	{
		saveCurrentFormToBean(context);

		return context.getMapping().findForward("cloneProduce");
	}

	public ActionForward newContactPerson(IActionContext context) throws Exception
	{
		saveCurrentFormToBean(context);

		OrderForm form = (OrderForm) context.getForm();
		context.getRequest().getSession().setAttribute(Contractor.currentContractorId, form.getContractor().getId());

		return context.getMapping().findForward("newContactPerson");
	}

	public ActionForward editProduce(IActionContext context) throws Exception
	{
		saveCurrentFormToBean(context);

		return context.getMapping().findForward("editProduce");
	}

	public ActionForward deleteProduce(IActionContext context) throws Exception
	{
		saveCurrentFormToBean(context);

		OrderForm form = (OrderForm) context.getForm();
		Order order = (Order) StoreUtil.getSession(context.getRequest(), Order.class);
		order.deleteProduce(form.getNumber());

		return retFromProduceOperation(context);
	}

	public ActionForward editExecuted(IActionContext context) throws Exception
	{
		saveCurrentFormToBean(context);

		return context.getMapping().findForward("editExecuted");
	}


	public ActionForward retFromProduceOperation(IActionContext context) throws Exception
	{
		Order order = (Order) StoreUtil.getSession(context.getRequest(), Order.class);
		getCurrentFormFromBean(context, order);

		OrderForm form = (OrderForm) context.getForm();
		form.setNeedPrint(false);
		form.setNeedPrintLetter(false);

		return show(context);
	}

	public ActionForward retFromContractor(IActionContext context) throws Exception
	{
		Order order = (Order) StoreUtil.getSession(context.getRequest(), Order.class);
		getCurrentFormFromBean(context, order);

		OrderForm form = (OrderForm) context.getForm();
		String contractorFor = (String) context.getRequest().getSession().getAttribute("newContractorFor");

		String contractorId = (String) context.getRequest().getSession().getAttribute(Contractor.currentContractorId);
		if (!StringUtil.isEmpty(contractorId))
		{
			if (!StringUtil.isEmpty(contractorFor))
			{
				form.setContractor_for(ContractorDAO.load(context, contractorId));
				context.getRequest().getSession().setAttribute("newContractorFor", null);
			}
			else
			{
				form.setContractor(ContractorDAO.load(context, contractorId));
				form.setContact_person(new ContactPerson());
			}
		}
		context.getRequest().getSession().setAttribute(Contractor.currentContractorId, null);

		String contact_personId = (String) context.getRequest().getSession().getAttribute(ContactPerson.current_contact_person_id);
		if (!StringUtil.isEmpty(contact_personId))
			form.setContact_person(ContactPersonDAO.load(context, contact_personId));
		context.getRequest().getSession().setAttribute(ContactPerson.current_contact_person_id, null);

		form.setNeedPrint(false);
		form.setNeedPrintLetter(false);

		return show(context);
	}

	public ActionForward retFromAttach(IActionContext context) throws Exception
	{
		getCurrentFormFromBean(context, null);

		return show(context);
	}

	public ActionForward returnFromSelectCP(IActionContext context) throws Exception
	{
		boolean haveBlockPosition = false;
		String cprId = SelectFromGridAction.getSelectedId(context);
		Order order = (Order) StoreUtil.getSession(context.getRequest(), Order.class);
		if (!StringUtil.isEmpty(cprId))
		{
			CommercialProposal commercialProposal = CommercialProposalDAO.load(context, cprId);
			if (order.importFromCP(commercialProposal))
			{
				haveBlockPosition = true;
			}
		}

		if (haveBlockPosition)
		{
			StrutsUtil.addError(context, "error.order.haveBlockPosition", null);
		}

		getCurrentFormFromBean(context, order);
		return show(context);
	}

	public ActionForward loadIsWarn(IActionContext context) throws Exception
	{
		saveCurrentFormToBean(context);
		return show(context);
	}

	public ActionForward reload(IActionContext context) throws Exception
	{
		saveCurrentFormToBean(context);
		return show(context);
	}

	public ActionForward changeViewNumber(IActionContext context) throws Exception
	{
		OrderForm form = (OrderForm) context.getForm();

		Order order = (Order) StoreUtil.getSession(context.getRequest(), Order.class);
		OrderProduce orderProduce = order.findProduce(form.getNumber());
		orderProduce.setOpr_use_prev_number(form.isSameNumberAsPrevious() ? "" : "1");
		if (!StringUtil.isEmpty(orderProduce.getOpr_use_prev_number()))
		{
			orderProduce.setDrp_price(0);
		}
		saveCurrentFormToBean(context);
		return show(context);
	}

	public ActionForward reloadDontCalculate(IActionContext context) throws Exception
	{
		saveCurrentFormToBean(context);
		return show(context);
	}

	public ActionForward reloadIncludeNDS(IActionContext context) throws Exception
	{
		saveCurrentFormToBean(context);
		return show(context);
	}

	public ActionForward reloadCalculate(IActionContext context) throws Exception
	{
		saveCurrentFormToBean(context);
		return show(context);
	}

	public ActionForward reloadDeleteAllRows(IActionContext context) throws Exception
	{
		Order order = (Order) StoreUtil.getSession(context.getRequest(), Order.class);
		order.getProduces().clear();
		order.setHaveItog(false);
		return show(context);
	}

	public ActionForward reloadInOneSpec(IActionContext context) throws Exception
	{
		Order order = (Order) StoreUtil.getSession(context.getRequest(), Order.class);
		OrderForm form = (OrderForm) context.getForm();
		for (int i = 0; i < form.getOrderProducesGrid().getDataList().size() - order.getCountItog(); i++)
		{
			OrderForm.OrderProduceForForm orderProduce = (OrderForm.OrderProduceForForm) form.getOrderProducesGrid().getDataList().get(i);
			orderProduce.setContractor(new Contractor(form.getContractor_for()));
			orderProduce.setContract(new Contract(form.getContract()));
			orderProduce.setSpecification(new Specification(form.getSpecification()));
		}
		form.setContractor_for(new Contractor());
		form.setContract(new Contract());
		form.setSpecification(new Specification());
		saveCurrentFormToBean(context);

		return show(context);
	}

	public ActionForward reloadOrdReadyForDelivDateAll(IActionContext context) throws Exception
	{
		OrderForm form = (OrderForm) context.getForm();

		if (StringUtil.isEmpty(form.getOrd_ready_for_deliv_date_all()))
		{
			form.setOrd_ready_for_deliv_date("");
			form.setShippingDocType(new ShippingDocType());
			form.setOrd_shp_doc_number("");
			form.setOrd_ship_from_stock("");
			form.setOrd_arrive_in_lithuania("");
			Order order = (Order) StoreUtil.getSession(context.getRequest(), Order.class);
			for (int i = 0; i < form.getOrderProducesGrid().getDataList().size() - order.getCountItog(); i++)
			{
				OrderForm.OrderProduceForForm orderProduce = (OrderForm.OrderProduceForForm) form.getOrderProducesGrid().getDataList().get(i);
				orderProduce.getOrderProduce().getReadyForShippings().clear();
			}
		}
		saveCurrentFormToBean(context);

		return show(context);
	}

	public ActionForward reloadOrdDateConfAll(IActionContext context) throws Exception
	{
		OrderForm form = (OrderForm) context.getForm();

		if (StringUtil.isEmpty(form.getOrd_date_conf_all()))
		{
			form.setOrd_date_conf("");
		}
		form.setOrd_ready_for_deliv_date_all("");
		form.setOrd_ready_for_deliv_date("");
		form.setShippingDocType(new ShippingDocType());
		form.setOrd_shp_doc_number("");
		form.setOrd_ship_from_stock("");
		form.setOrd_arrive_in_lithuania("");
		saveCurrentFormToBean(context);

		return show(context);
	}

	private void savePaySumsToOrder(IActionContext context, List<OrderPaySum> paySums)
	{
		Order order = (Order) StoreUtil.getSession(context.getRequest(), Order.class);
		order.getOrderPaySums().clear();
		for (OrderPaySum orderPaySum : paySums)
		{
			order.getOrderPaySums().add(new OrderPaySum(orderPaySum));
		}
	}

	private boolean saveCommon(IActionContext context) throws Exception
	{
		OrderForm form = (OrderForm) context.getForm();
		String errMsg = "";

		if (!StringUtil.isEmpty(form.getOrd_in_one_spec()))
		{
			if (StringUtil.isEmpty(form.getContractor_for().getId()) || StringUtil.isEmpty(form.getContract().getCon_id()) || StringUtil.isEmpty(form.getSpecification().getSpc_id()))
			{
				errMsg = StrutsUtil.addDelimiter(errMsg);
				errMsg += StrutsUtil.getMessage(context, "error.order.ord_in_one_spec");
			}
		}

		if ((!StringUtil.isEmpty(form.getOrd_executed_date()) &&
						(StringUtil.isEmpty(form.getOrd_received_conf_date()) || StringUtil.isEmpty(form.getOrd_sent_to_prod_date())))

						||

						(!StringUtil.isEmpty(form.getOrd_ready_for_deliv_date()) &&
										(StringUtil.isEmpty(form.getOrd_received_conf_date()) || StringUtil.isEmpty(form.getOrd_sent_to_prod_date())))

						||

						(!StringUtil.isEmpty(form.getOrd_conf_sent_date()) &&
										(StringUtil.isEmpty(form.getOrd_received_conf_date()) || StringUtil.isEmpty(form.getOrd_sent_to_prod_date())))

						||

						(!StringUtil.isEmpty(form.getOrd_received_conf_date()) &&
										(StringUtil.isEmpty(form.getOrd_sent_to_prod_date())))
						)
		{
			errMsg = StrutsUtil.addDelimiter(errMsg);
			errMsg += StrutsUtil.getMessage(context, "error.order.queue_date");
		}

		if (!StringUtil.isEmpty(errMsg))
		{
			StrutsUtil.addError(context, "errors.msg", errMsg, null);
			return false;
		}

		Date dateSentToProd = StringUtil.getCurrentDateTime();
		Date dateReceivedConf = StringUtil.getCurrentDateTime();
		Date dateConfSent = StringUtil.getCurrentDateTime();
		Date readyForDeliveryDate = StringUtil.getCurrentDateTime();
		Date dateExecuted = StringUtil.getCurrentDateTime();
		try
		{
			dateSentToProd = StringUtil.appDateString2Date(form.getOrd_sent_to_prod_date());
			dateReceivedConf = StringUtil.appDateString2Date(form.getOrd_received_conf_date());
			if (!StringUtil.isEmpty(form.getOrd_conf_sent_date()))
			{
				dateConfSent = StringUtil.appDateString2Date(form.getOrd_conf_sent_date());
			}
			readyForDeliveryDate = StringUtil.appDateString2Date(form.getOrd_ready_for_deliv_date());
			dateExecuted = StringUtil.appDateString2Date(form.getOrd_executed_date());
		}
		catch (Exception e)
		{
			log.error(e);
		}

		if (!StringUtil.isEmpty(form.getOrd_received_conf_date()))
		{
			if (dateReceivedConf.before(dateSentToProd))
			{
				errMsg = StrutsUtil.addDelimiter(errMsg);
				errMsg += StrutsUtil.getMessage(context, "error.order.ord_received_conf_date");
			}
		}
		if (!StringUtil.isEmpty(form.getOrd_conf_sent_date()))
		{
			if (dateConfSent.before(dateReceivedConf))
			{
				errMsg = StrutsUtil.addDelimiter(errMsg);
				errMsg += StrutsUtil.getMessage(context, "error.order.ord_conf_sent_date");
			}
		}
		if (!StringUtil.isEmpty(form.getOrd_ready_for_deliv_date()) && !StringUtil.isEmpty(form.getOrd_received_conf_date()))
		{
			if (readyForDeliveryDate.before(dateReceivedConf))
			{
				errMsg = StrutsUtil.addDelimiter(errMsg);
				errMsg += StrutsUtil.getMessage(context, "error.order.ord_ready_for_deliv_date");
			}
		}
		if (!StringUtil.isEmpty(form.getOrd_executed_date()) && !StringUtil.isEmpty(form.getOrd_ready_for_deliv_date()))
		{
			if (dateExecuted.before(readyForDeliveryDate))
			{
				errMsg = StrutsUtil.addDelimiter(errMsg);
				errMsg += StrutsUtil.getMessage(context, "error.order.ord_executed_date");
			}
		}

		saveCurrentFormToBean(context);

		User currentUser = UserUtil.getCurrentUser(context.getRequest());
		boolean saveOnlyParentDoc = false;

		Order order = (Order) StoreUtil.getSession(context.getRequest(), Order.class);
		boolean allCountCorrect = true;
		for (int i = 0; i < form.getOrderProducesGrid().getDataList().size() - order.getCountItog(); i++)
		{
			OrderForm.OrderProduceForForm orderProduce = (OrderForm.OrderProduceForForm) form.getOrderProducesGrid().getDataList().get(i);
			if (null == orderProduce.getProduce() || null == orderProduce.getProduce().getId())
			{
				if (!currentUser.isAdmin())
				{
					errMsg = StrutsUtil.addDelimiter(errMsg);
					errMsg += StrutsUtil.getMessage(context, "error.order.ord_null_produce");
					break;
				}
				else
				{
					saveOnlyParentDoc = true;
				}
			}

			if ("1".equals(form.getOrd_all_include_in_spec()) && StringUtil.isEmpty(form.getOrd_by_guaranty()))
			{
				if (orderProduce.getDrp_price() <= 0 && StringUtil.isEmpty(orderProduce.getOpr_use_prev_number()))
				{
					errMsg = StrutsUtil.addDelimiter(errMsg);
					errMsg += StrutsUtil.getMessage(context, "error.order.null_drp_price");
					break;
				}
			}

			//у "подчинённых" позиций равенство не обязательно к соблюдению
			if (!StringUtil.isEmpty(orderProduce.getOpr_use_prev_number()))
			{
				continue;
			}
			if (orderProduce.getOpr_count() != orderProduce.getOpr_count_executed())
			{
				allCountCorrect = false;
			}
		}

		if (!StringUtil.isEmpty(form.getOrd_executed_date()) && order.getOrderExecutedDate().size() > 0)
		{
			for (int i = 0; i < order.getOrderExecutedDate().size() - 1; i++)
			{
				String date = order.getOrderExecutedDate().get(i).getDate();
				Date executedLineDate = StringUtil.appDateString2Date(date);
				if (dateExecuted.before(executedLineDate))
				{
					errMsg = StrutsUtil.addDelimiter(errMsg);
					errMsg += StrutsUtil.getMessage(context, "error.order.ordExecutedDateInLine");
					break;
				}
			}
		}

		if (allCountCorrect && (form.getOrderProducesGrid().getDataList().size() - order.getCountItog() > 0) && StringUtil.isEmpty(order.getOrd_annul()) && StringUtil.isEmpty(order.getOrd_executed_date()))
		{
			errMsg = StrutsUtil.addDelimiter(errMsg);
			errMsg += StrutsUtil.getMessage(context, "error.order.not_entered_executed_date");
		}

		if (!StringUtil.isEmpty(order.getOrd_date_conf_all()) && StringUtil.isEmpty(order.getOrd_date_conf()))
		{
			errMsg = StrutsUtil.addDelimiter(errMsg);
			errMsg += StrutsUtil.getMessage(context, "error.order.not_entered_date_conf");
		}

		if (!StringUtil.isEmpty(order.getOrd_ready_for_deliv_date_all()) &&
						(StringUtil.isEmpty(order.getOrd_ready_for_deliv_date()) || StringUtil.isEmpty(order.getShippingDocType().getId()) || StringUtil.isEmpty(order.getOrd_shp_doc_number()))
						)
		{
			errMsg = StrutsUtil.addDelimiter(errMsg);
			errMsg += StrutsUtil.getMessage(context, "error.order.not_entered_ready_for_deliv_fields");
		}

		if (!StringUtil.isEmpty(errMsg))
		{
			StrutsUtil.addError(context, "errors.msg", errMsg, null);
			return false;
		}

		order.getOrderPayments().clear();
		int i = 0;
		while (i < form.getOrderPayments().size())
		{
			OrderPayment orderPayment = form.getOrderPayments().get(i);
			if (orderPayment.getOrp_percent() == 0 && orderPayment.getOrp_sum() == 0 && StringUtil.isEmpty(orderPayment.getOrp_date()))
			{
				form.getOrderPayments().remove(i);
			}
			else
			{
				order.getOrderPayments().add(new OrderPayment(orderPayment));
				i++;
			}
		}

		savePaySumsToOrder(context, form.getOrderPaySums());

		if (StringUtil.isEmpty(form.getOrd_id()))
		{
			form.setCreateUser(currentUser);
			//form ord_number
			String year = form.getOrd_date().substring(8);
			String month = form.getOrd_date().substring(3, 5);
			if (!StringUtil.isEmpty(form.getCreateUser().getUsr_id()))
			{
				DAOUtils.load(context, "user-code-load", null);
			}
			form.setGen_num(CommonDAO.GetNumber(context, "get-num_order"));
			order.setOrd_number(order.getSellerForWho().getPrefixForOrder() + "-" + year + month + "/" + StringUtil.padWithLeadingZeros(form.getGen_num(), 4) + "-" + form.getUsr_code().toUpperCase());
			OrderDAO.insert(context, order);
			form.setOrd_id(order.getOrd_id());
		}
		else
		{
			OrderDAO.save(context, order, saveOnlyParentDoc);
		}

		if ((allCountCorrect && !StringUtil.isEmpty(form.getOrd_executed_date())) || "1".equals(form.getOrd_annul()))
		{
			order.setOrd_block("1");
		}
		else
		{
			order.setOrd_block(null);
		}
		OrderDAO.saveBlock(context, order);
		Session hibSession = HibernateUtil.getSessionFactory().getCurrentSession();
		hibSession.beginTransaction();
		form.getAttachmentService().commit(Integer.parseInt(order.getOrd_id()));
		hibSession.getTransaction().commit();

		return true;
	}

	private void saveScale(IActionContext context) throws Exception
	{
		OrderForm form = (OrderForm) context.getForm();
		Order order = (Order) StoreUtil.getSession(context.getRequest(), Order.class);
		if (!StringUtil.isEmpty(form.getOrd_id()))
		{
			OrderDAO.saveScale(context, order);
		}
	}

	private boolean commonSavePrint(IActionContext context) throws Exception
	{
		OrderForm form = (OrderForm) context.getForm();
		if ("true".equals(form.getIs_new_doc()))
		{
			boolean retFromSave = saveCommon(context);

			if (!retFromSave)
			{
				return false;
			}
			form.setIs_new_doc("");

			Order order = OrderDAO.load(context, form.getOrd_id(), false, false);
			loadAdditionalData(context, form, order);
		}

		return true;
	}

	public ActionForward print(IActionContext context) throws Exception
	{
		if (!commonSavePrint(context))
		{
			return show(context);
		}

		OrderForm form = (OrderForm) context.getForm();
		saveCurrentFormToBean(context);
		saveScale(context);
		form.setNeedPrint(true);
		return show(context);
	}

	public ActionForward printLetter(IActionContext context) throws Exception
	{
		if (!commonSavePrint(context))
		{
			return show(context);
		}

		OrderForm form = (OrderForm) context.getForm();
		saveCurrentFormToBean(context);
		saveScale(context);
		form.setNeedPrintLetter(true);
		return show(context);
	}

	public ActionForward show(IActionContext context) throws Exception
	{
		final OrderForm form = (OrderForm) context.getForm();

		Order order = (Order) StoreUtil.getSession(context.getRequest(), Order.class);
		order.calculate(order.getProduces(), new LocaledPropertyMessageResources("resources/report", new Locale("RU")));
		form.setOrd_summ(order.getOrd_summ_formatted());
		getCurrentFormFromBean(context, order);

		final int countItog = order.getCountItog();
		form.setCountItog(countItog);
		if (!StringUtil.isEmpty(form.getDirector().getUsr_id()))
		{
			form.setDirector(UserDAO.load(context, form.getDirector().getUsr_id()));
		}

		Session hibSession = HibernateUtil.getSessionFactory().getCurrentSession();
		hibSession.beginTransaction();
		AttachmentsService attachmentsService = new AttachmentsService(hibSession);
		form.setTemplateExcel(attachmentsService.firstAttach("DCL_TEMPLATE", ConstDefinitions.templateIdOrder));
		hibSession.getTransaction().commit();

		if (form.isNeedPrint())
		{
			form.setPrint("true");
		}
		else
		{
			form.setPrint("false");
		}
		form.setNeedPrint(false);

		if (form.isNeedPrintLetter())
		{
			form.setPrintLetter("true");
		}
		else
		{
			form.setPrintLetter("false");
		}
		form.setNeedPrintLetter(false);

		if (StringUtil.isEmpty(form.getCreateUser().getUsr_id()) || //новый документ
						(!"1".equals(form.getOrd_block())))
		{
			form.setFormReadOnly(false);
		}
		else
		{
			form.setFormReadOnly(true);
		}

		User currentUser = UserUtil.getCurrentUser(context.getRequest());

		/**
		 * Read-only для "Сотрудника в литве".
		 * При открытии (просмотре) доступны только "Печать", "Напечатать сопр письмо",
		 * "Отмена" и "Указывать единицы измерения"
		 */
		if (currentUser.isOnlyUserInLithuania())
		{
			form.setFormReadOnly(true);
		}

		/**
		 * Доступ к редактированию секции НАД секцией "Заполняется отделом логистики": админ, экономист, логистик, менеджер
		 */
		if ((currentUser.isAdmin() || currentUser.isEconomist() || currentUser.isLogistic() || currentUser.isManager()) && !form.isFormReadOnly())
		{
			form.setReadOnlyIfNotLikeManager(false);
		}
		else
		{
			form.setReadOnlyIfNotLikeManager(true);
		}

		/**
		 * Доступ к редактированию секции "Заполняется отделом логистики": админ, экономист, логистик
		 */
		if ((currentUser.isAdmin() || currentUser.isEconomist() || currentUser.isLogistic()) && !form.isFormReadOnly())
		{
			form.setReadOnlyIfNotLikeLogist(false);
		}
		else
		{
			form.setReadOnlyIfNotLikeLogist(true);
		}

		/**
		 * Доступ к редактированию поля "Заказ передан на производство" : админ, экономист, логистик, менеджер
		 */
		if ((currentUser.isAdmin() || currentUser.isEconomist() || currentUser.isLogistic() || currentUser.isManager()) && !form.isFormReadOnly())
		{
			form.setReadOnlySentToProdDate(false);
		}
		else
		{
			form.setReadOnlySentToProdDate(true);
		}

		/**
		 * Дать пользователям "менеджер" возможность прикреплять файлы.
		 */
		if ((currentUser.isAdmin() || currentUser.isEconomist() || currentUser.isManager() || currentUser.isLogistic()) && !"1".equals(form.getOrd_block()))
		{
			form.setReadOnlyIfNotLikeLogistOrManager(false);
		}
		else
		{
			form.setReadOnlyIfNotLikeLogistOrManager(true);
		}

		/**
		 * поле "Планируемая дата прибытия в Литву": админ, экономист, логистик, сотрудники в Литве
		 */
		if ((currentUser.isAdmin() || currentUser.isEconomist() || currentUser.isUserInLithuania() || currentUser.isLogistic()) && !"1".equals(form.getOrd_block()))
		{
			form.setReadOnlyIfNotLikeLogistOrUIL(false);
		}
		else
		{
			form.setReadOnlyIfNotLikeLogistOrUIL(true);
		}

		if (!currentUser.isAdmin())
		{
			form.setShowForAdmin(false);
		}
		else
		{
			form.setShowForAdmin(true);
		}

		/**
		 * Доступ к кнопке "Кол-во исполнено": админ, экономист, логистик, менеджер и когда документ не блокирован.
		 */
		if ((currentUser.isAdmin() || currentUser.isEconomist() || currentUser.isLogistic() || currentUser.isManager()) || !form.isFormReadOnly())
		{
			form.setReadOnlyForExecuted(false);
		}
		else
		{
			form.setReadOnlyForExecuted(true);
		}

		if (currentUser.isOnlyUserInLithuania())
		{
			form.setReadOnlyForCoveringLetter(true);
		}

		context.getRequest().setAttribute("show-checker", new IShowChecker()
		{
			int size = form.getOrderProducesGrid().getDataList().size();

			public boolean check(ShowCheckerContext context)
			{
				return context.getTable().getRecordCounter() < (size - countItog + 1);
			}
		}
		);

		context.getRequest().setAttribute("style-checker", new IStyleClassChecker()
		{
			public String check(StyleClassCheckerContext context)
			{
				OrderForm.OrderProduceForForm orderProduce = (OrderForm.OrderProduceForForm) form.getOrderProducesGrid().getDataList().get(context.getTable().getRecordCounter() - 1);
				if (orderProduce.isIs_itog())
				{
					return "bold-cell";
				}
				if (null == orderProduce.getProduce() || null == orderProduce.getProduce().getId())
				{
					return "red-font-cell";
				}
				return "";
			}
		});

		context.getRequest().setAttribute("styleCheckerExecuted", new IStyleClassChecker()
		{
			public String check(StyleClassCheckerContext context)
			{
				OrderForm.OrderProduceForForm orderProduce = (OrderForm.OrderProduceForForm) form.getOrderProducesGrid().getDataList().get(context.getTable().getRecordCounter() - 1);
				if (orderProduce.isIs_itog())
				{
					return "bold-cell";
				}
				if (null == orderProduce.getProduce() || null == orderProduce.getProduce().getId())
				{
					if (orderProduce.isPinkBackgroundForExecuted())
					{
						return "red-font-cell-pink";
					}

					return "red-font-cell";
				}

				if (orderProduce.isPinkBackgroundForExecuted())
				{
					return "cell-pink";
				}
				return "";
			}
		});

		context.getRequest().setAttribute("style-checker-image", new IStyleClassChecker()
		{
			public String check(StyleClassCheckerContext context)
			{
				OrderForm.OrderProduceForForm orderProduce = (OrderForm.OrderProduceForForm) form.getOrderProducesGrid().getDataList().get(context.getTable().getRecordCounter() - 1);
				if (orderProduce.isIs_itog())
				{
					return "bold-cell";
				}
				return "grid-image-without-border";
			}
		});

		final boolean isReadOnlyIfNotLikeManager = form.isReadOnlyIfNotLikeManager();
		context.getRequest().setAttribute("checkbox-readonly-checker", new IReadOnlyChecker()
		{
			public boolean check(ReadOnlyCheckerContext context)
			{
				return ((context.getTable().getRecordCounter() == 1) | isReadOnlyIfNotLikeManager);
			}
		});

		context.getRequest().setAttribute("editByManager", new IReadOnlyChecker()
		{
			public boolean check(ReadOnlyCheckerContext context) throws Exception
			{
				return isReadOnlyIfNotLikeManager;
			}
		});

		context.getRequest().setAttribute("cloneReadonly", new IReadOnlyChecker()
		{
			public boolean check(ReadOnlyCheckerContext context) throws Exception
			{
				return isReadOnlyIfNotLikeManager;
			}
		});

		/**
		 * Незаблокированный Заказ - кнопка "…" доступна.
		 * Заблокированный Заказ
		 * Если "срок изготовления один у всех позиций" [ ] или
		 * "Сразу весь товар готов к отгрузке со склада производителя" [ ],
		 * то в таблице должны быть доступны кнопки "…". Для всех просмотр в режиме read-only.
		 */
		context.getRequest().setAttribute("editReadonly", new IReadOnlyChecker()
		{
			public boolean check(ReadOnlyCheckerContext context) throws Exception
			{
				if (StringUtil.isEmpty(form.getOrd_block()) ||
								(!StringUtil.isEmpty(form.getOrd_block()) && (StringUtil.isEmpty(form.getOrd_date_conf_all()) || StringUtil.isEmpty(form.getOrd_ready_for_deliv_date_all())))
								)
				{
					return false;
				}

				return true;
			}
		});

		context.getRequest().setAttribute("linkReadonly", new IReadOnlyChecker()
		{
			public boolean check(ReadOnlyCheckerContext context) throws Exception
			{
				OrderForm.OrderProduceForForm orderProduce = (OrderForm.OrderProduceForForm) form.getOrderProducesGrid().getDataList().get(context.getTable().getRecordCounter() - 1);
				return orderProduce.isIs_itog();
			}
		});

		context.getRequest().setAttribute("deleteReadonly", new IReadOnlyChecker()
		{
			public boolean check(ReadOnlyCheckerContext context) throws Exception
			{
				OrderForm.OrderProduceForForm orderProduce = (OrderForm.OrderProduceForForm) form.getOrderProducesGrid().getDataList().get(context.getTable().getRecordCounter() - 1);
	      /*
        Кнопка удаления строки в табличной части не должна быть доступна,
        если "Кол-во исполнено" этой строки > 0
         */
				return orderProduce.getOpr_count_executed() > 0 || !(StringUtil.isEmpty(orderProduce.getOpr_occupied()) && !form.isFormReadOnly()) || isReadOnlyIfNotLikeManager;
			}
		});

		form.setShowAskClearTable((order.getProduces().size() - countItog) > 0);

		order.setWarn(context);
		StoreUtil.putSession(context.getRequest(), order);
		form.setIs_warn(order.getIs_warn());

		form.getAttachmentsGrid().setDataList(form.getAttachmentService().list());

		form.setNoRoundSum(CurrencyDAO.load(context, form.getCurrency().getId()).getNo_round());

		if (StringUtil.isEmpty(form.getOrd_print_scale()))
		{
			form.setOrd_print_scale("100");
		}
		if (StringUtil.isEmpty(form.getOrd_letter_scale()))
		{
			form.setOrd_letter_scale("100");
		}

		return context.getMapping().findForward("form");
	}

	public ActionForward reloadBlank(IActionContext context) throws Exception
	{
		OrderForm form = (OrderForm) context.getForm();
		form.setNeedPrint(false);
		form.setNeedPrintLetter(false);

		try
		{
			if (!StringUtil.isEmpty(form.getBlank().getBln_id()))
			{
				form.setBlank(BlankDAO.load(context, form.getBlank().getBln_id()));
			}
		}
		catch (Exception e)
		{
			log.error(e);
		}

		saveCurrentFormToBean(context);

		return show(context);
	}

	public ActionForward input(IActionContext context) throws Exception
	{
		OrderForm form = (OrderForm) context.getForm();

		Order order = new Order();
		StoreUtil.putSession(context.getRequest(), order);
		//обнуляем поля формы
		getCurrentFormFromBean(context, order);

		form.setOrd_date(StringUtil.date2appDateString(StringUtil.getCurrentDateTime()));
		form.setIs_new_doc("true");
		form.setNeedPrint(false);
		form.setNeedPrintLetter(false);

		String directorCode = Config.getString("order.director");
		String logistCode = Config.getString("order.logist");
		String directorRBCode = Config.getString("order.directorRB");

		User director = new User();
		director.setUsr_code(directorCode);
		UserDAO.loadUserByCode(context, director);
		form.setDirector(director);

		User logist = new User();
		logist.setUsr_code(logistCode);
		UserDAO.loadUserByCode(context, logist);
		form.setLogist(logist);

		User directorRB = new User();
		directorRB.setUsr_code(directorRBCode);
		UserDAO.loadUserByCode(context, directorRB);
		form.setDirector_rb(directorRB);

		User manager = UserUtil.getCurrentUser(context.getRequest());
		form.setManager(manager);

		form.setOrd_donot_calculate_netto("1");
		form.setOrd_in_one_spec("1");
		form.setShow_unit("");
		form.setMerge_positions("1");

		form.setOrd_logist_signature("1");
		form.setOrd_director_rb_signature("1");
		form.setOrd_chief_dep_signature("1");
		form.setOrd_manager_signature("1");

		form.setCurrency(CurrencyDAO.loadByName(context, StrutsUtil.getMessage(context, "Order.default_currency")));

		saveCurrentFormToBean(context);

		form.setAttachmentService(
						DeferredAttachmentService.create(context.getRequest().getSession(),
										referencedTable, context.getMapping().findForward("backFromAttach"), null));

		form.getOrderPayments().clear();
		form.getOrderPayments().add(new OrderPayment(100, 0, form.getCurrency().getName()));

		form.getOrderPaySums().clear();
		form.getOrderPaySums().add(new OrderPaySum(0, ""));

		return show(context);
	}

	public ActionForward clone(IActionContext context) throws Exception
	{
		OrderForm form = (OrderForm) context.getForm();

		Order order = OrderDAO.load(context, form.getOrd_id(), true, true);
		order.setListIdsToNull();
		order.setOrd_block("");
		order.setOrd_id("");
		order.setUsr_date_create("");
		order.setUsr_date_edit("");
		User currentUser = UserUtil.getCurrentUser(context.getRequest());
		order.setCreateUser(currentUser);
		order.setEditUser(currentUser);
		order.setOrd_number("");
		order.setOrd_date(StringUtil.date2appDateString(StringUtil.getCurrentDateTime()));
		order.setOrd_date_conf_all("");
		order.setOrd_date_conf("");
		order.setOrd_sent_to_prod_date("");
		order.setOrd_received_conf_date("");
		order.setOrd_num_conf("");
		order.setOrd_conf_sent_date("");
		order.setOrd_executed_date("");
		order.setOrd_ready_for_deliv_date_all("");
		order.setOrd_ready_for_deliv_date("");
		order.setOrd_shp_doc_number("");
		order.setOrd_ship_from_stock("");
		order.setOrd_arrive_in_lithuania("");
		order.setOrd_comment("");
		order.setOrd_comment_covering_letter("");
		order.setOrd_annul("");
		order.setCount_day_curr_minus_sent_to_prod(0);
		order.setContractor_for(new Contractor());
		order.setSpecification(new Specification());
		order.setShippingDocType(new ShippingDocType());
		order.setSeller(new Seller());
		order.setSetCorrectFieldsForOrderClone();

		form.setOrd_logist_signature("1");
		form.setOrd_director_rb_signature("1");
		form.setOrd_chief_dep_signature("1");
		form.setOrd_manager_signature("1");

		StoreUtil.putSession(context.getRequest(), order);
		getCurrentFormFromBean(context, order);

		form.setIs_new_doc("true");
		form.setNeedPrint(false);
		form.setNeedPrintLetter(false);

		form.setShow_unit("");
		form.setMerge_positions("1");

		saveCurrentFormToBean(context);

		form.setAttachmentService(
						DeferredAttachmentService.create(context.getRequest().getSession(),
										referencedTable, context.getMapping().findForward("backFromAttach"), null));

		form.getOrderPayments().clear();
		form.getOrderPayments().add(new OrderPayment(100, 0, form.getCurrency().getName()));

		form.getOrderPaySums().clear();
		form.getOrderPaySums().add(new OrderPaySum(0, ""));

		return show(context);
	}

	private void loadAdditionalData(IActionContext context, OrderForm form, Order order) throws Exception
	{
		order.calculatePaymentsDescription(order.getOrderPayments());
		StoreUtil.putSession(context.getRequest(), order);
		getCurrentFormFromBean(context, order);

		form.getOrderPayments().clear();
		for (OrderPayment orderPayment : order.getOrderPayments())
		{
			orderPayment.setCurrencyName(form.getCurrency().getName());
			form.getOrderPayments().add(new OrderPayment(orderPayment));
		}

		form.getOrderPaySums().clear();
		for (OrderPaySum orderPaySum : order.getOrderPaySums())
		{
			form.getOrderPaySums().add(new OrderPaySum(orderPaySum));
		}
	}

	public ActionForward edit(IActionContext context) throws Exception
	{
		OrderForm form = (OrderForm) context.getForm();

		Order order = OrderDAO.load(context, form.getOrd_id(), false, false);
		loadAdditionalData(context, form, order);

		form.setNeedPrint(false);
		form.setNeedPrintLetter(false);
		form.setShow_unit("");
		form.setMerge_positions("1");

		Session hibSession = HibernateUtil.getSessionFactory().getCurrentSession();
		hibSession.beginTransaction();
		form.setAttachmentService(
						DeferredAttachmentService.create(context.getRequest().getSession(),
										referencedTable, Integer.parseInt(form.getOrd_id()),
										context.getMapping().findForward("backFromAttach"), null));
		hibSession.getTransaction().commit();

		return show(context);
	}

	public ActionForward back(IActionContext context) throws Exception
	{
		OrderForm form = (OrderForm) context.getForm();
		form.getAttachmentService().rollback();
		form.setAttachmentService(null);

		DeferredAttachmentService.removeLast(context.getRequest().getSession());
		return context.getMapping().findForward("back");
	}

	public ActionForward process(IActionContext context) throws Exception
	{
		saveCurrentFormToBean(context);
		boolean retFromSave = saveCommon(context);

		if (retFromSave)
		{
			DeferredAttachmentService.removeLast(context.getRequest().getSession());
			return context.getMapping().findForward("back");
		}
		else
		{
			OrderForm form = (OrderForm) context.getForm();
			form.setNeedPrint(false);
			form.setNeedPrintLetter(false);
			return show(context);
		}
	}

	public ActionForward importExcel(IActionContext context) throws Exception
	{
		saveCurrentFormToBean(context);

		return context.getMapping().findForward("importExcel");
	}

	public ActionForward produceMovement(IActionContext context) throws Exception
	{
		saveCurrentFormToBean(context);
		return context.getMapping().findForward("produceMovement");
	}

	public ActionForward fromProduceMovement(IActionContext context) throws Exception
	{
		getCurrentFormFromBean(context, null);
		return show(context);
	}

	public ActionForward ajaxIsContractCopy(IActionContext context) throws Exception
	{
		String conId = context.getRequest().getParameter("contract-id");
		if (!StringUtil.isEmpty(conId))
		{
			try
			{
				Contract contract = ContractDAO.load(context, conId, false);
				if (contract.isProject())
				{
					StrutsUtil.setAjaxResponse(context, "Order.project", true);
				}
				if (contract.isCopy())
				{
					StrutsUtil.setAjaxResponse(context, "Order.copy", true);
				}
			}
			catch (LoadException loadException)
			{
				log.error("Ошибка загрузки контракта с id = " + conId);
			}
		}
		return context.getMapping().findForward("ajax");
	}

	public ActionForward ajaxIsSpecificationCopy(IActionContext context) throws Exception
	{
		String spcId = context.getRequest().getParameter("specification-id");
		if (!StringUtil.isEmpty(spcId))
		{
			try
			{
				Specification specification = SpecificationDAO.load(context, spcId);
				if (specification.isProject())
				{
					StrutsUtil.setAjaxResponse(context, "Order.project", true);
				}
				if (specification.isCopy())
				{
					StrutsUtil.setAjaxResponse(context, "Order.copy", true);
				}
			}
			catch (LoadException loadException)
			{
				log.error("Ошибка загрузки спецификации с id = " + spcId);
			}
		}
		return context.getMapping().findForward("ajax");
	}

	public ActionForward uploadTemplate(IActionContext context) throws Exception
	{
		saveCurrentFormToBean(context);

		return context.getMapping().findForward("uploadTemplate");
	}

	public ActionForward deferredAttach(IActionContext context) throws Exception
	{
		saveCurrentFormToBean(context);
		OrderForm form = (OrderForm) context.getForm();

		DeferredAttachmentService.set(context.getRequest().getSession(), form.getAttachmentService());
		return context.getMapping().findForward("deferredAttach");
	}

	public ActionForward deleteAttachment(IActionContext context) throws Exception
	{
		saveCurrentFormToBean(context);
		OrderForm form = (OrderForm) context.getForm();
		DeferredAttachmentService.DeferredAttachment attachment = form.getAttachmentService().find(Integer.parseInt(form.getAttachmentId()));
		form.getAttachmentService().delete(attachment);

		return show(context);
	}

	public ActionForward downloadAttachment(IActionContext context) throws Exception
	{
		saveCurrentFormToBean(context);
		OrderForm form = (OrderForm) context.getForm();
		try
		{
			form.getAttachmentService().download(form.getAttachmentId(), context.getRequest(), context.getResponse());
			return null;
		}
		catch (AttachmentException e)
		{
			StrutsUtil.addError(context, e.getMessage(), e);
			return show(context);
		}
	}

	public ActionForward ajaxCheckSave(IActionContext context) throws Exception
	{
		OrderForm form = (OrderForm) context.getForm();
		Order order = (Order) StoreUtil.getSession(context.getRequest(), Order.class);
		String resultMsg;

		//в случае, если в табличной части ничего нет, выводить сообщение при сохранении
		if (form.getOrderProducesGrid().getDataList().size() - order.getCountItog() == 0)
		{
			resultMsg = StrutsUtil.getMessage(context, "msg.order.empty_table");
			StrutsUtil.setAjaxResponse(context, resultMsg, false);
			return context.getMapping().findForward("ajax");
		}

		// Когда заказ будет исполнен, весь товар сразу включить в спецификацию (импорт)= ДА
		// осуществлять проверку по следующей формуле:
		// если a/b/c>1,5, где
		// a - Продажная цена за единицу без НДС, бел.руб.
		// b - курс из справочника "валюты" валюты, указанной в графе "Валюта" на дату заказа
		// с - Цена нетто,
		// то выдавать сообщение
		if (order.isAllIncludeInSpec())
		{
			CurrencyRate currencyRate = CurrencyRateDAO.loadRateForDate(context, order.getCurrency().getId(), StringUtil.appDateString2dbDateString(order.getOrd_date()));
			double checkCourse = currencyRate.getCrt_rate();

			double checkCoefficient = Config.getFloat(Constants.drpPriceCoefficient, 1.5f);

			for (int i = 0; i < form.getOrderProducesGrid().getDataList().size() - order.getCountItog(); i++)
			{
				OrderForm.OrderProduceForForm orderProduce = (OrderForm.OrderProduceForForm) form.getOrderProducesGrid().getDataList().get(i);
				double drpPrice = orderProduce.getDrp_price();
				double oprPriceNetto = orderProduce.getOpr_price_netto();
				if (checkCourse != 0 && oprPriceNetto != 0)
				{
					if (drpPrice / checkCourse / oprPriceNetto > checkCoefficient)
					{
						resultMsg = StrutsUtil.getMessage(context, "msg.order_produce.check_dlr_price");
						StrutsUtil.setAjaxResponse(context, resultMsg, false);
						return context.getMapping().findForward("ajax");
					}
				}
			}
		}

		return context.getMapping().findForward("ajax");
	}

	public ActionForward ajaxOrderPaymentsGrid(IActionContext context) throws Exception
	{
		return context.getMapping().findForward("ajaxOrderPaymentsGrid");
	}

	public ActionForward ajaxAddToPaymentGrid(IActionContext context) throws Exception
	{
		OrderForm form = (OrderForm) context.getForm();
		form.getOrderPayments().add(new OrderPayment(0, 0, form.getCurrency().getName()));
		Order order = (Order) StoreUtil.getSession(context.getRequest(), Order.class);
		order.calculatePaymentsDescription(form.getOrderPayments());

		return ajaxOrderPaymentsGrid(context);
	}

	public ActionForward ajaxRemoveFromPaymentGrid(IActionContext context) throws Exception
	{
		OrderForm form = (OrderForm) context.getForm();

		int id = Integer.parseInt(context.getRequest().getParameter("id"));
		form.getOrderPayments().remove(id);
		if (form.getOrderPayments().size() == 1)
		{
			form.getOrderPayments().get(0).setOrp_percent(100);
			form.getOrderPayments().get(0).setOrp_sum(StringUtil.appCurrencyString2double(form.getOrd_summ()));
		}
		Order order = (Order) StoreUtil.getSession(context.getRequest(), Order.class);
		order.calculatePaymentsDescription(form.getOrderPayments());

		return ajaxOrderPaymentsGrid(context);
	}

	public ActionForward ajaxRecalculatePaymentGrid(IActionContext context) throws Exception
	{
		OrderForm form = (OrderForm) context.getForm();
		Order order = (Order) StoreUtil.getSession(context.getRequest(), Order.class);
		order.calculatePaymentsDescription(form.getOrderPayments());

		return ajaxOrderPaymentsGrid(context);
	}

	public ActionForward ajaxOrderPaySumsGrid(IActionContext context) throws Exception
	{
		return context.getMapping().findForward("ajaxOrderPaySumsGrid");
	}

	public ActionForward ajaxAddToPaySumGrid(IActionContext context) throws Exception
	{
		OrderForm form = (OrderForm) context.getForm();
		form.getOrderPaySums().add(new OrderPaySum(0, ""));

		return ajaxOrderPaySumsGrid(context);
	}

	public ActionForward ajaxRemoveFromPaySumsGrid(IActionContext context) throws Exception
	{
		OrderForm form = (OrderForm) context.getForm();

		int id = Integer.parseInt(context.getRequest().getParameter("id"));
		form.getOrderPaySums().remove(id);
		if (form.getOrderPaySums().size() == 1)
		{
			form.getOrderPaySums().get(0).setOps_sum(StringUtil.appCurrencyString2double(form.getOrd_summ()));
		}

		return ajaxOrderPaySumsGrid(context);
	}

	public ActionForward ajaxRecalcPaySumGrid(IActionContext context) throws Exception
	{
		OrderForm form = (OrderForm) context.getForm();
		savePaySumsToOrder(context, form.getOrderPaySums());

		return ajaxOrderPaySumsGrid(context);
	}

}
