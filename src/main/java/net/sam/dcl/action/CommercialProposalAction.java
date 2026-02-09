package net.sam.dcl.action;

import net.sam.dcl.controller.actions.DBTransactionAction;
import net.sam.dcl.controller.IDispatchable;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.service.AttachmentException;
import net.sam.dcl.service.DeferredAttachmentService;
import net.sam.dcl.util.*;
import net.sam.dcl.form.CommercialProposalForm;
import net.sam.dcl.form.ImportData;
import net.sam.dcl.beans.*;
import net.sam.dcl.taglib.table.*;
import net.sam.dcl.dao.*;
import net.sam.dcl.report.excel.Grid2Excel;
import net.sam.dcl.service.AttachmentsService;
import net.sam.dcl.config.Config;
import net.sam.dcl.dbo.DboCatalogNumber;
import net.sam.dcl.dbo.DboProduce;
import org.apache.struts.action.ActionForward;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Calendar;
import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class CommercialProposalAction extends DBTransactionAction implements IDispatchable
{
	protected static Log log = LogFactory.getLog(CommercialProposalAction.class);
	private final static String REFERENCED_TABLE = "DCL_COMMERCIAL_PROPOSAL";

	private void saveCurrentFormToBean(IActionContext context)
	{
		CommercialProposalForm form = (CommercialProposalForm) context.getForm();

		CommercialProposal commercialProposal = (CommercialProposal) StoreUtil.getSession(context.getRequest(), CommercialProposal.class);
		if (commercialProposal == null)
		{
			String cprId = form.getCpr_id();
			if (StringUtil.isEmpty(cprId))
			{
				cprId = context.getRequest().getParameter("cpr_id");
			}
			if (!StringUtil.isEmpty(cprId))
			{
				try
				{
					commercialProposal = CommercialProposalDAO.load(context, cprId);
					StoreUtil.putSession(context.getRequest(), commercialProposal);
				}
				catch (Exception e)
				{
					log.error("Failed to reload CommercialProposal in saveCurrentFormToBean, cpr_id=" + cprId, e);
				}
			}
			if (commercialProposal == null)
			{
				log.warn("CommercialProposal is null in session during saveCurrentFormToBean");
				return;
			}
		}

		commercialProposal.setInclude_exps(form.getInclude_exps());
		commercialProposal.setShow_unit(form.getShow_unit());
		commercialProposal.setIs_new_doc(form.getIs_new_doc());
		commercialProposal.setCpr_id(form.getCpr_id());
		try
		{
			if (!StringUtil.isEmpty(form.getCreateUser().getUsr_id()))
			{
				commercialProposal.setCreateUser(UserDAO.load(context, form.getCreateUser().getUsr_id()));
			}
			if (!StringUtil.isEmpty(form.getEditUser().getUsr_id()))
			{
				commercialProposal.setEditUser(UserDAO.load(context, form.getEditUser().getUsr_id()));
			}

			commercialProposal.setUsr_date_create(form.getUsr_date_create());
			commercialProposal.setUsr_date_edit(form.getUsr_date_edit());
			commercialProposal.setCpr_number(form.getCpr_number());
			commercialProposal.setCpr_date(form.getCpr_date());

			if (!StringUtil.isEmpty(form.getBlank().getBln_id()))
			{
				commercialProposal.setBlank(BlankDAO.load(context, form.getBlank().getBln_id()));
			}

			commercialProposal.setCpr_img_name(form.getCpr_img_name());

			if (!StringUtil.isEmpty(form.getContractor().getId()))
			{
				commercialProposal.setContractor(ContractorDAO.load(context, form.getContractor().getId()));
			}

			if (!StringUtil.isEmpty(form.getContactPerson().getCps_id()))
			{
				commercialProposal.setContactPerson(ContactPersonDAO.load(context, form.getContactPerson().getCps_id()));
			}
			else
			{
				commercialProposal.setContactPerson(new ContactPerson());
			}

			commercialProposal.setCpr_concerning(form.getCpr_concerning());
			commercialProposal.setCpr_concerning_invoice(form.getCpr_concerning_invoice());
			commercialProposal.setCpr_preamble(form.getCpr_preamble());

			if (!StringUtil.isEmpty(form.getCurrency().getId()))
			{
				commercialProposal.setCurrency(CurrencyDAO.load(context, form.getCurrency().getId()));
			}
			if (!StringUtil.isEmpty(form.getCurrencyTable().getId()))
			{
				commercialProposal.setCurrencyTable(CurrencyDAO.load(context, form.getCurrencyTable().getId()));
			}

			commercialProposal.setCpr_course(form.getCpr_course());
			commercialProposal.setCpr_nds(form.getCpr_nds());
			commercialProposal.setPriceCondition(form.getPriceCondition());
			commercialProposal.setCpr_country(form.getCpr_country());
			commercialProposal.setCpr_pay_condition(form.getCpr_pay_condition());
			commercialProposal.setCpr_pay_condition_invoice(form.getCpr_pay_condition_invoice());
			commercialProposal.setDeliveryCondition(form.getDeliveryCondition());
			commercialProposal.setCpr_nds_by_string(form.getCpr_nds_by_string());
			commercialProposal.setCpr_delivery_address(form.getCpr_delivery_address());
			commercialProposal.setCpr_sum_transport(form.getCpr_sum_transport());
			commercialProposal.setCpr_all_transport(form.getCpr_all_transport());
			commercialProposal.setCpr_sum_assembling(form.getCpr_sum_assembling());
			commercialProposal.setCpr_delivery_term(form.getCpr_delivery_term());
			commercialProposal.setCpr_delivery_term_invoice(form.getCpr_delivery_term_invoice());
			commercialProposal.setCpr_add_info(form.getCpr_add_info());
			commercialProposal.setCpr_final_date(form.getCpr_final_date());
			commercialProposal.setCpr_final_date_invoice(form.getCpr_final_date_invoice());

			if (!StringUtil.isEmpty(form.getUser().getUsr_id()))
			{
				commercialProposal.setUser(UserDAO.load(context, form.getUser().getUsr_id()));
			}
			if (!StringUtil.isEmpty(form.getExecutor().getUsr_id()))
			{
				commercialProposal.setExecutor(UserDAO.load(context, form.getExecutor().getUsr_id()));
			}

			commercialProposal.setCpr_executor_flag(form.getCpr_executor_flag());
			commercialProposal.setFacsimile_flag(form.getFacsimile_flag());
			commercialProposal.setCpr_date_accept(form.getCpr_date_accept());
			commercialProposal.setCpr_proposal_received_flag(StringUtil.javaBooleanToDBBoolean(form.isCpr_proposal_received_flag()));
			commercialProposal.setCpr_proposal_declined(StringUtil.javaBooleanToDBBoolean(form.isCpr_proposal_declined()));
			commercialProposal.setCpr_tender_number(form.getCpr_tender_number());
			commercialProposal.setCpr_tender_number_editable(StringUtil.javaBooleanToDBBoolean(form.getCpr_tender_number_editable()));
			commercialProposal.setCpr_old_version(form.getCpr_old_version());
			commercialProposal.setCpr_assemble_minsk_store(form.getCpr_assemble_minsk_store());
			commercialProposal.setCpr_free_prices(form.getCpr_free_prices());
			commercialProposal.setCpr_reverse_calc(form.getCpr_reverse_calc());
			commercialProposal.setCpr_can_edit_invoice(form.getCpr_can_edit_invoice());
			commercialProposal.setCpr_comment(form.getCpr_comment());
			if (!StringUtil.isEmpty(form.getPurchasePurpose().getId()))
			{
				commercialProposal.setPurchasePurpose(PurchasePurposeDAO.load(context, form.getPurchasePurpose().getId()));
			}
			if (!StringUtil.isEmpty(form.getContactPersonSeller().getCps_id()))
			{
				commercialProposal.setContactPersonSeller(ContactPersonDAO.load(context, form.getContactPersonSeller().getCps_id()));
			}
			else
			{
				commercialProposal.setContactPersonSeller(new ContactPerson());
			}
			if (!StringUtil.isEmpty(form.getContactPersonCustomer().getCps_id()))
			{
				commercialProposal.setContactPersonCustomer(ContactPersonDAO.load(context, form.getContactPersonCustomer().getCps_id()));
			}
			else
			{
				commercialProposal.setContactPersonCustomer(new ContactPerson());
			}
			commercialProposal.setCpr_guaranty_in_month(Integer.parseInt(form.getCpr_guaranty_in_month()));
			commercialProposal.setCpr_prepay_percent(StringUtil.appCurrencyString2double(form.getCpr_prepay_percent()));
			commercialProposal.setCpr_prepay_sum(StringUtil.appCurrencyString2double(form.getCpr_prepay_sum()));
			commercialProposal.setCpr_delay_days(form.getCpr_delay_days());
			if (!StringUtil.isEmpty(form.getConsignee().getId()))
			{
				commercialProposal.setConsignee(ContractorDAO.load(context, form.getConsignee().getId()));
			}
			commercialProposal.setCpr_no_reservation(form.getCpr_no_reservation());
			commercialProposal.setCpr_provider_delivery(form.getCpr_provider_delivery());
			commercialProposal.setCpr_provider_delivery_address(form.getCpr_provider_delivery_address());
			commercialProposal.setCpr_delivery_count_day(form.getCpr_delivery_count_day());
			commercialProposal.setCpr_donot_calculate_netto(form.getCpr_donot_calculate_netto());
			commercialProposal.setCpr_print_scale(form.getCpr_print_scale());
			commercialProposal.setCpr_contract_scale(form.getCpr_contract_scale());
			commercialProposal.setCpr_invoice_scale(form.getCpr_invoice_scale());
			commercialProposal.setCpr_final_date_above(form.getCpr_final_date_above());

			// внимание, меняем поля !!!
			if (commercialProposal.isAssembleMinskStore())
			{
				commercialProposal.setCpr_nds_by_string("1");
			}
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
		}

		StoreUtil.putSession(context.getRequest(), commercialProposal);
	}

	private void getCurrentFormFromBean(IActionContext context, CommercialProposal commercialProposalIn)
	{
		CommercialProposalForm form = (CommercialProposalForm) context.getForm();
		CommercialProposal commercialProposal;
		if (null != commercialProposalIn)
		{
			commercialProposal = commercialProposalIn;
		}
		else
		{
			commercialProposal = (CommercialProposal) StoreUtil.getSession(context.getRequest(), CommercialProposal.class);
		}

		if (null != commercialProposal)
		{
			form.setIs_new_doc(commercialProposal.getIs_new_doc());

			form.setCpr_id(commercialProposal.getCpr_id());
			form.setCreateUser(commercialProposal.getCreateUser());
			form.setEditUser(commercialProposal.getEditUser());
			form.setUsr_date_create(commercialProposal.getUsr_date_create());
			form.setUsr_date_edit(commercialProposal.getUsr_date_edit());
			form.setCpr_number(commercialProposal.getCpr_number());
			form.setCpr_date(commercialProposal.getCpr_date());
			form.setBlank(commercialProposal.getBlank());
			form.setCpr_img_name(commercialProposal.getCpr_img_name());
			form.setContractor(commercialProposal.getContractor());
			form.setContactPerson(commercialProposal.getContactPerson());
			form.setCpr_concerning(commercialProposal.getCpr_concerning());
			form.setCpr_concerning_invoice(commercialProposal.getCpr_concerning_invoice());
			form.setCpr_preamble(commercialProposal.getCpr_preamble());
			form.setCurrency(commercialProposal.getCurrency());
			form.setCurrencyTable(commercialProposal.getCurrencyTable());
			form.setCpr_course(commercialProposal.getCpr_course());
			form.setCpr_nds(commercialProposal.getCpr_nds());
			form.setPriceCondition(commercialProposal.getPriceCondition());
			form.setCpr_country(commercialProposal.getCpr_country());
			form.setCpr_pay_condition(commercialProposal.getCpr_pay_condition());
			form.setCpr_pay_condition_invoice(commercialProposal.getCpr_pay_condition_invoice());
			form.setDeliveryCondition(commercialProposal.getDeliveryCondition());
			form.setCpr_nds_by_string(commercialProposal.getCpr_nds_by_string());
			form.setCpr_delivery_address(commercialProposal.getCpr_delivery_address());
			form.setCpr_sum_transport(commercialProposal.getCpr_sum_transport());
			form.setCpr_all_transport(commercialProposal.getCpr_all_transport());
			form.setCpr_sum_assembling(commercialProposal.getCpr_sum_assembling());
			form.setCpr_delivery_term(commercialProposal.getCpr_delivery_term());
			form.setCpr_delivery_term_invoice(commercialProposal.getCpr_delivery_term_invoice());
			form.setCpr_add_info(commercialProposal.getCpr_add_info());
			form.setCpr_final_date(commercialProposal.getCpr_final_date());
			form.setCpr_final_date_invoice(commercialProposal.getCpr_final_date_invoice());
			form.setUser(commercialProposal.getUser());
			form.setExecutor(commercialProposal.getExecutor());
			form.setCpr_executor_flag(commercialProposal.getCpr_executor_flag());
			form.setFacsimile_flag(commercialProposal.getFacsimile_flag());
			form.setCpr_date_accept(commercialProposal.getCpr_date_accept());
			form.setCpr_proposal_received_flag(StringUtil.dbBooleanToJavaBoolean(commercialProposal.getCpr_proposal_received_flag()));
			form.setCpr_proposal_declined(StringUtil.dbBooleanToJavaBoolean(commercialProposal.getCpr_proposal_declined()));
			form.setCpr_tender_number(commercialProposal.getCpr_tender_number());
			form.setCpr_tender_number_editable(StringUtil.dbBooleanToJavaBoolean(commercialProposal.getCpr_tender_number_editable()));
			form.setCpr_block(commercialProposal.getCpr_block());
			form.setCpr_old_version(commercialProposal.getCpr_old_version());
			form.setCpr_assemble_minsk_store(commercialProposal.getCpr_assemble_minsk_store());
			form.setCpr_free_prices(commercialProposal.getCpr_free_prices());
			form.setCpr_reverse_calc(commercialProposal.getCpr_reverse_calc());
			form.setCpr_can_edit_invoice(commercialProposal.getCpr_can_edit_invoice());
			form.setCpr_comment(commercialProposal.getCpr_comment());
			form.setPurchasePurpose(commercialProposal.getPurchasePurpose());
			form.setContactPersonSeller(commercialProposal.getContactPersonSeller());
			form.setContactPersonCustomer(commercialProposal.getContactPersonCustomer());
			form.setCpr_guaranty_in_month(String.valueOf(commercialProposal.getCpr_guaranty_in_month()));
			form.setCpr_prepay_percent(StringUtil.double2appCurrencyString(commercialProposal.getCpr_prepay_percent()));
			form.setCpr_prepay_sum(StringUtil.double2appCurrencyString(commercialProposal.getCpr_prepay_sum()));
			form.setCpr_delay_days(commercialProposal.getCpr_delay_days());
			form.setConsignee(commercialProposal.getConsignee());
			form.setCpr_no_reservation(commercialProposal.getCpr_no_reservation());
			form.setCpr_provider_delivery(commercialProposal.getCpr_provider_delivery());
			form.setCpr_provider_delivery_address(commercialProposal.getCpr_provider_delivery_address());
			form.setCpr_delivery_count_day(commercialProposal.getCpr_delivery_count_day());
			form.setCpr_donot_calculate_netto(commercialProposal.getCpr_donot_calculate_netto());
			form.setCpr_print_scale(commercialProposal.getCpr_print_scale());
			form.setCpr_contract_scale(commercialProposal.getCpr_contract_scale());
			form.setCpr_invoice_scale(commercialProposal.getCpr_invoice_scale());
			form.setCpr_final_date_above(commercialProposal.getCpr_final_date_above());
			form.setCpr_tender_number(commercialProposal.getCpr_tender_number());
			form.setCpr_tender_number_editable(StringUtil.dbBooleanToJavaBoolean(commercialProposal.getCpr_tender_number_editable()));
			form.getGridProduces().setDataList(commercialProposal.getProduces());
			form.getGridTransport().setDataList(commercialProposal.getTransportLines());
		}
	}

	public ActionForward newContractor(IActionContext context) throws Exception
	{
		saveCurrentFormToBean(context);

		return context.getMapping().findForward("newContractor");
	}

	public ActionForward newProduce(IActionContext context) throws Exception
	{
		saveCurrentFormToBean(context);

		CommercialProposalForm form = (CommercialProposalForm) context.getForm();
		if (!form.isAssembleMinskStore())
		{
			return context.getMapping().findForward("newProduce");
		}
		else
		{
			ImportData importData = new ImportData();
			CommercialProposal commercialProposal = (CommercialProposal) StoreUtil.getSession(context.getRequest(), CommercialProposal.class);
			for (int i = 0; i < commercialProposal.getProduces().size() - commercialProposal.getCountItogRecord(); i++)
			{
				CommercialProposalProduce commercialProposalProduce = commercialProposal.getProduces().get(i);
				ImportPosition position = new ImportPosition();
				position.setDoc_id(commercialProposalProduce.getLpc_id());
				position.setCount(commercialProposalProduce.getLpr_count());
				position.setProduce(commercialProposalProduce.getProduce());
				position.setCtn_number(commercialProposalProduce.getCatalogNumberForStuffCategory());
				ImportData.RightRecord rightRecord = new ImportData.RightRecord();
				rightRecord.setId(i);
				rightRecord.setPosition(new ImportPosition(position));
				importData.getRightIntermediate().add(rightRecord);
			}
			StoreUtil.putSession(context.getRequest(), importData);

			return context.getMapping().findForward("newProduceForAssembleMinsk");
		}
	}

	public ActionForward newContactPerson(IActionContext context) throws Exception
	{
		saveCurrentFormToBean(context);

		CommercialProposalForm form = (CommercialProposalForm) context.getForm();
		context.getRequest().getSession().setAttribute(Contractor.currentContractorId, form.getContractor().getId());

		return context.getMapping().findForward("newContactPerson");
	}

	public ActionForward editProduce(IActionContext context) throws Exception
	{
		saveCurrentFormToBean(context);

		return context.getMapping().findForward("editProduce");
	}

	public ActionForward editPurchasePurposes(IActionContext context) throws Exception
	{
		saveCurrentFormToBean(context);

		return context.getMapping().findForward("editPurchasePurposes");
	}

	public ActionForward retFromProduceOperation(IActionContext context) throws Exception
	{
		CommercialProposal commercialProposal = (CommercialProposal) StoreUtil.getSession(context.getRequest(), CommercialProposal.class);
		getCurrentFormFromBean(context, commercialProposal);

		CommercialProposalForm form = (CommercialProposalForm) context.getForm();
		form.setPrint("false");

		return show(context);
	}

	public ActionForward backFromSelect(IActionContext context) throws Exception
	{
		ImportData data = (ImportData) StoreUtil.getSession(context.getRequest(), ImportData.class);
		CommercialProposal commercialProposal = (CommercialProposal) StoreUtil.getSession(context.getRequest(), CommercialProposal.class);

		for (int i = 0; i < commercialProposal.getProduces().size() - commercialProposal.getCountItogRecord(); i++)
		{
			CommercialProposalProduce commercialProposalProduce = commercialProposal.getProduces().get(i);
			ImportData.RightRecord rightRecord = data.findRightRecordByDocIdIdx(commercialProposalProduce.getLpc_id(), i);
			if (null != rightRecord) //нашли - значит эту запись отредактировали
			{
				rightRecord.setModified(true);
				commercialProposalProduce.setLpr_count(rightRecord.getPosition().getCount());
			}
			else //не нашли - значит эту запись удалили - удаляем из списка
			{
				commercialProposal.getProduces().remove(i);
				i--;
			}
		}

		CurrencyRate currencyRate = CurrencyRateDAO.loadRateForDate(context, CurrencyDAO.loadByName(context, "EUR").getId(), commercialProposal.getCpr_date_ts());
		for (int i = 0; i < data.getRightIntermediate().size(); i++)
		{
			ImportData.RightRecord record = data.getRightIntermediate().get(i);
			if (!record.isModified()) //не измененная, а новая запись - добавляем в список товаров
			{
				CommercialProposalProduce commercialProposalProduce = new CommercialProposalProduce();
				commercialProposalProduce.setCpr_id(commercialProposal.getCpr_id());
				commercialProposalProduce.setCpr_date(commercialProposal.getCpr_date());
				commercialProposalProduce.setLpr_count(record.getPosition().getCount());

				commercialProposalProduce.setProduce(ProduceDAO.loadProduceWithUnit(record.getPosition().getProduce().getId()));

				commercialProposalProduce.setLpc_id(record.getPosition().getDoc_id());
				ProduceCostProduce produceCostProduce = ProduceCostProduceDAO.load(context, record.getPosition().getDoc_id());
				commercialProposalProduce.setStuffCategory(produceCostProduce.getStuffCategory());
				commercialProposalProduce.setLpc_cost_one_by(produceCostProduce.getLpc_cost_one_by());
				commercialProposalProduce.setLpc_price_list_by(produceCostProduce.getLpc_price_list_by());
				commercialProposalProduce.setLpc_1c_number(produceCostProduce.getLpc_1c_number());
				commercialProposalProduce.setSale_price_parking_trans(produceCostProduce.getLpc_price_list_by());

				ProduceCost produceCost = ProduceCostDAO.load(context, produceCostProduce.getPrc_id());
				double days = StringUtil.getDaysBetween(StringUtil.appDateString2Date(produceCost.getPrc_date()), StringUtil.appDateString2Date(commercialProposal.getCpr_date()));
				if (StringUtil.appDateString2Date(produceCost.getPrc_date()).before(StringUtil.appDateString2Date("01.07.2016")))
				{
					commercialProposalProduce.setLpc_cost_one_by(StringUtil.roundN(commercialProposalProduce.getLpc_cost_one_by() / 10000, 2));
					commercialProposalProduce.setLpc_price_list_by(StringUtil.roundN(commercialProposalProduce.getLpc_price_list_by() / 10000, 2));
				}

				commercialProposalProduce.setCalculatedField(
								StringUtil.double2appCurrencyString(
												produceCostProduce.getFieldForCheckCP(
																currencyRate.getCrt_rate(),
																Config.getFloat(Constants.minCourseCoefficient, 1.05f)
												)
								)
												+
												"|"
												+
												(days <= 360 ? "1" : "0")
				);

				commercialProposalProduce.setNumber(Integer.toString(commercialProposal.getProduces().size()));
				commercialProposal.insertProduce("", commercialProposalProduce);
			}
		}

		CommercialProposalForm form = (CommercialProposalForm) context.getForm();
		form.setPrint("false");

		getCurrentFormFromBean(context, commercialProposal);
		return show(context);
	}

	public ActionForward retFromContractor(IActionContext context) throws Exception
	{
		CommercialProposal commercialProposal = (CommercialProposal) StoreUtil.getSession(context.getRequest(), CommercialProposal.class);
		getCurrentFormFromBean(context, commercialProposal);

		CommercialProposalForm form = (CommercialProposalForm) context.getForm();

		String contractorId = (String) context.getRequest().getSession().getAttribute(Contractor.currentContractorId);
		if (!StringUtil.isEmpty(contractorId))
		{
			form.setContractor(ContractorDAO.load(context, contractorId));
			form.setContactPerson(new ContactPerson());
		}
		context.getRequest().getSession().setAttribute(Contractor.currentContractorId, null);

		String contact_personId = (String) context.getRequest().getSession().getAttribute(ContactPerson.current_contact_person_id);
		if (!StringUtil.isEmpty(contact_personId))
			form.setContactPerson(ContactPersonDAO.load(context, contact_personId));
		context.getRequest().getSession().setAttribute(ContactPerson.current_contact_person_id, null);

		form.setPrint("false");
		return show(context);
	}

	public ActionForward generateExcel(IActionContext context) throws Exception
	{
		CommercialProposal commercialProposal = (CommercialProposal) StoreUtil.getSession(context.getRequest(), CommercialProposal.class);
		Grid2Excel grid2Excel = new Grid2Excel("Commercial Proposal Table Part");
		grid2Excel.doGrid2Excel(context, commercialProposal.getExcelTable());
		return null;
	}

	private boolean isCorrectFields(IActionContext context) throws Exception
	{
		CommercialProposalForm form = (CommercialProposalForm) context.getForm();

		if (StringUtil.isEmpty(form.getCpr_course_formatted()) || form.getCpr_course() == 0.0)
		{
			StrutsUtil.addError(context, "error.commercial.course", null);
			return false;
		}

		return true;
	}

	private boolean isCorrectDDP(IActionContext context) throws Exception
	{
		CommercialProposal commercialProposal = (CommercialProposal) StoreUtil.getSession(context.getRequest(), CommercialProposal.class);

    /*
    * Если отмечен "КП составляется по товару, который лежит на складе в Минске", то:
    * В табличной части:
    * указание таможенных кодов необязательно
    * */
		if (!commercialProposal.isAssembleMinskStore()) //не отмечен - проверяем дальше
		{
			//Если DDP и не задан таможенный код
			if ((commercialProposal.incoTermCaseC() || commercialProposal.incoTermCaseD() || commercialProposal.incoTermCaseE()) && commercialProposal.isNaNCodePercent())
			{
				StrutsUtil.addError(context, "error.commercial.DDP", null);
				return false;
			}
		}

		return true;
	}

	private boolean saveCommon(IActionContext context) throws Exception
	{
		CommercialProposalForm form = (CommercialProposalForm) context.getForm();
		String errMsg = "";

		if (!isCorrectFields(context))
		{
			return false;
		}

		Date date = StringUtil.appDateString2Date(form.getCpr_date());
		Date dateFinish = StringUtil.appDateString2Date(form.getCpr_final_date());
		if (dateFinish.before(date))
		{
			errMsg = StrutsUtil.addDelimiter(errMsg);
			errMsg += StrutsUtil.getMessage(context, "error.commercial.final_date");
		}

		if (StringUtil.isEmpty(form.getCpr_date_accept()) && form.isCpr_proposal_received_flag())
		{
			errMsg = StrutsUtil.addDelimiter(errMsg);
			errMsg += StrutsUtil.getMessage(context, "error.commercial.accepted");
		}

		if (form.isCpr_proposal_received_flag())
		{
			try
			{
				Date dateAccept = StringUtil.appDateString2Date(form.getCpr_date_accept());
				if (dateAccept.before(date))
				{
					errMsg = StrutsUtil.addDelimiter(errMsg);
					errMsg += StrutsUtil.getMessage(context, "error.commercial.date_accept");
				}
			}
			catch (Exception e)
			{
				log.error(e.toString());
			}
		}

		if (form.getCpr_tender_number_editable() && StringUtil.isEmpty(form.getCpr_tender_number()))
		{
			errMsg = StrutsUtil.addDelimiter(errMsg);
			errMsg += StrutsUtil.getMessage(context, "error.commercial.tender");
		}

		if (form.isIncorrectWhenEqualCurrencies())
		{
			errMsg = StrutsUtil.addDelimiter(errMsg);
			errMsg += StrutsUtil.getMessage(context, "error.commercial.equalCurrencies");
		}

		saveCurrentFormToBean(context);

		CommercialProposal commercialProposal = (CommercialProposal) StoreUtil.getSession(context.getRequest(), CommercialProposal.class);

		if (StringUtil.isEmpty(commercialProposal.getCpr_old_version()))
		{
			for (int i = 0; i < commercialProposal.getProduces().size() - commercialProposal.getCountItogRecord(); i++)
			{
				CommercialProposalProduce commercialProposalProduce = commercialProposal.getProduces().get(i);
				if (commercialProposalProduce.isEmptyProduce())
				{
					errMsg = StrutsUtil.addDelimiter(errMsg);
					errMsg += StrutsUtil.getMessage(context, "error.commercial.cpr_null_produce");
				}
			}
		}

		//проверяем чтобы бронь не правышала возможное количество
		if (form.isAssembleMinskStore() && StringUtil.isEmpty(form.getCpr_no_reservation()))
		{
			for (int i = 0; i < commercialProposal.getProduces().size() - commercialProposal.getCountItogRecord(); i++)
			{
				CommercialProposalProduce commercialProposalProduce = commercialProposal.getProduces().get(i);
				CommercialProposalProduce commercialProposalProduceCheck = CommercialProposalProduceDAO.loadLpcRestCount(context, commercialProposal.getCpr_id(), commercialProposal.getCpr_date(), commercialProposalProduce.getLpc_id());
				if (commercialProposalProduce.getLpr_count() > commercialProposalProduceCheck.getRest_lpc_count())
				{
					errMsg = StrutsUtil.addDelimiter(errMsg);
					errMsg += StrutsUtil.getMessage(context, "error.commercial.cpr_reserved_error", commercialProposalProduce.getProduce().getFullName());
				}
			}
		}

		if (!StringUtil.isEmpty(errMsg))
		{
			StrutsUtil.addError(context, "errors.msg", errMsg, null);
			return false;
		}

		if (!isCorrectDDP(context))
		{
			return false;
		}

		commercialProposal.setCpr_summ(commercialProposal.getTotalPrint());

		if (StringUtil.isEmpty(form.getCpr_id()))
		{
			User user = UserUtil.getCurrentUser(context.getRequest());
			form.setCreateUser(user);
			//form cpr_number
			String year = form.getCpr_date().substring(8);
			String month = form.getCpr_date().substring(3, 5);
			if (!StringUtil.isEmpty(form.getCreateUser().getUsr_id()))
			{
				DAOUtils.load(context, "user-code-load", null);
			}
			form.setGen_num(CommonDAO.GetNumber(context, "get-num_commercial_proposal"));
			commercialProposal.setCpr_number("BYM" + year + month + "/" + StringUtil.padWithLeadingZeros(form.getGen_num(), 4) + "-" + form.getUsr_code().toUpperCase());
			CommercialProposalDAO.insert(context, commercialProposal);
		}
		else
		{
			CommercialProposalDAO.save(context, commercialProposal);
		}

		form.setCpr_id(commercialProposal.getCpr_id());
		form.getAttachmentService().commit(Integer.parseInt(commercialProposal.getCpr_id()));
		return true;
	}

	private boolean canPrint(IActionContext context) throws Exception
	{
		CommercialProposalForm form = (CommercialProposalForm) context.getForm();
		if (form.isIncorrectWhenNotEqualCurrencies())
		{
			StrutsUtil.addError(context, "error.commercial.notEqualCurrencies", null);
			return false;
		}
		if (form.isIncorrectWhenEqualCurrencies())
		{
			StrutsUtil.addError(context, "error.commercial.equalCurrencies", null);
			return false;
		}

		return isCorrectFields(context) && isCorrectDDP(context);
	}

	private void saveScale(IActionContext context) throws Exception
	{
		CommercialProposalForm form = (CommercialProposalForm) context.getForm();
		CommercialProposal commercialProposal = (CommercialProposal) StoreUtil.getSession(context.getRequest(), CommercialProposal.class);
		if (!StringUtil.isEmpty(form.getCpr_id()))
		{
			CommercialProposalDAO.saveScale(context, commercialProposal);
		}
	}

	public ActionForward print(IActionContext context) throws Exception
	{
		CommercialProposalForm form = (CommercialProposalForm) context.getForm();
		saveCurrentFormToBean(context);
		saveScale(context);

		if (canPrint(context))
		{
			form.setNeedPrint(true);
		}
		else
		{
			form.setNeedPrint(false);
		}

		return show(context);
	}

	public ActionForward printInvoice(IActionContext context) throws Exception
	{
		CommercialProposalForm form = (CommercialProposalForm) context.getForm();
		String errMsg = "";
		if (StringUtil.isEmpty(form.getPurchasePurpose().getId()))
		{
			errMsg = StrutsUtil.addDelimiter(errMsg);
			errMsg += StrutsUtil.getMessage(context, "error.commercial.emptyPurchasePurpose");
		}
		if (!StringUtil.isEmpty(errMsg))
		{
			StrutsUtil.addError(context, "errors.msg", errMsg, null);
			return show(context);
		}

		saveCurrentFormToBean(context);
		saveScale(context);

		if (canPrint(context))
		{
			form.setNeedPrintInvoice(true);
		}
		else
		{
			form.setNeedPrintInvoice(false);
		}

		return show(context);
	}

	public ActionForward printContract(IActionContext context) throws Exception
	{
		CommercialProposalForm form = (CommercialProposalForm) context.getForm();
		String errMsg = "";
		if (StringUtil.isEmpty(form.getPurchasePurpose().getId()))
		{
			errMsg = StrutsUtil.addDelimiter(errMsg);
			errMsg += StrutsUtil.getMessage(context, "error.commercial.emptyPurchasePurpose");
		}
		if (!StringUtil.isEmpty(errMsg))
		{
			StrutsUtil.addError(context, "errors.msg", errMsg, null);
			return show(context);
		}

		saveCurrentFormToBean(context);
		saveScale(context);

		if (canPrint(context))
		{
			form.setNeedPrintContract(true);
		}
		else
		{
			form.setNeedPrintContract(false);
		}

		return show(context);
	}

	private boolean isNeedWarnByCustomCode(CommercialProposalForm form)
	{
	  /*
	  * Если отмечен "КП составляется по товару, который лежит на складе в Минске", то:
    * В табличной части:
    * указание таможенных кодов необязательно
    * */
		if (form.isAssembleMinskStore())
		{
			return false; //не нужны варнинги по таможенному коду
		}

		return
						(IncoTerm.EXW.equals(form.getDeliveryCondition().getName()) && IncoTerm.DDP.equals(form.getDeliveryCondition().getName())) ||
										(IncoTerm.EXW_2010.equals(form.getDeliveryCondition().getName()) && IncoTerm.DDP_2010.equals(form.getDeliveryCondition().getName())) ||
										(IncoTerm.DDP.equals(form.getDeliveryCondition().getName()) && IncoTerm.DDP.equals(form.getDeliveryCondition().getName())) ||
										(IncoTerm.DDP_2010.equals(form.getDeliveryCondition().getName()) && IncoTerm.DDP_2010.equals(form.getDeliveryCondition().getName()));
	}

	private void setVisuallyAttributes(IActionContext context)
	{
		final CommercialProposal commercialProposal = (CommercialProposal) StoreUtil.getSession(context.getRequest(), CommercialProposal.class);
		final CommercialProposalForm form = (CommercialProposalForm) context.getForm();
		final int countTotalRecord = commercialProposal.getCountItogRecord();
		context.getRequest().setAttribute("show-checker", new IShowChecker()
		{
			int size = form.getGridProduces().getDataList().size();

			public boolean check(ShowCheckerContext context)
			{
				return !(context.getTable().getRecordCounter() >= size - countTotalRecord + 1);
			}
		}
		);

		context.getRequest().setAttribute("show-checker-long", new IShowChecker()
		{
			int size = form.getGridProduces().getDataList().size();

			public boolean check(ShowCheckerContext context)
			{
				return !(context.getTable().getRecordCounter() >= size - countTotalRecord + 1 + 1);
			}
		}
		);

		context.getRequest().setAttribute("show-checker-all", new IShowChecker()
		{
			public boolean check(ShowCheckerContext context)
			{
				return true;
			}
		}
		);

		context.getRequest().setAttribute("style-checker-long", new IStyleClassChecker()
		{
			int size = form.getGridProduces().getDataList().size();

			public String check(StyleClassCheckerContext context)
			{
				CommercialProposalProduce commercialProposalProduce = (CommercialProposalProduce) form.getGridProduces().getDataList().get(context.getTable().getRecordCounter() - 1);
				if (context.getTable().getRecordCounter() < size - countTotalRecord + 1)
				{
					if (commercialProposalProduce.isEmptyProduce() && StringUtil.isEmpty(commercialProposal.getCpr_old_version()))
					{
						return "selected-cell-red";
					}
					else
					{
						return "selected-cell";
					}
				}
				if (context.getTable().getRecordCounter() != size - countTotalRecord + 1)
				{
					return "selected-cell";
				}
				return "";
			}
		});

		context.getRequest().setAttribute("style-checker-short", new IStyleClassChecker()
		{
			int size = form.getGridProduces().getDataList().size();

			public String check(StyleClassCheckerContext context)
			{
				CommercialProposalProduce commercialProposalProduce = (CommercialProposalProduce) form.getGridProduces().getDataList().get(context.getTable().getRecordCounter() - 1);
				if (context.getTable().getRecordCounter() < size - countTotalRecord + 1)
				{
					if (commercialProposalProduce.isEmptyProduce() && StringUtil.isEmpty(commercialProposal.getCpr_old_version()))
					{
						return "selected-cell-red";
					}
					else
					{
						return "selected-cell";
					}
				}
				return "";
			}
		});

		context.getRequest().setAttribute("style-checker", new IStyleClassChecker()
		{
			int size = form.getGridProduces().getDataList().size();

			public String check(StyleClassCheckerContext context)
			{
				CommercialProposalProduce commercialProposalProduce = (CommercialProposalProduce) form.getGridProduces().getDataList().get(context.getTable().getRecordCounter() - 1);
				if (context.getTable().getRecordCounter() < size - countTotalRecord + 1)
				{
					if (commercialProposalProduce.isEmptyProduce() && StringUtil.isEmpty(commercialProposal.getCpr_old_version()))
					{
						return "red-font-cell";
					}
				}
				return "";
			}
		});

		context.getRequest().setAttribute("deleteChecker", new IReadOnlyChecker()
		{
			public boolean check(ReadOnlyCheckerContext ctx) throws Exception
			{
				return form.isFormReadOnly();
			}
		});

		/***
		 * При вариантах "Цены указаны на условиях" и "Условие поставки":
		 * EXW - DDP
		 * DDP - DDP
		 * в случае незаполненного поля "Таможенный код" в табличной части соответствующие клетки
		 * (пересечние строки с колонками "Тамож. код" и "% тамож. пошлин") подсвечивать розовым цветом.
		 */
		final boolean condForPing = isNeedWarnByCustomCode(form);
		context.getRequest().setAttribute("style-checker-custom", new IStyleClassChecker()
		{
			int size = form.getGridProduces().getDataList().size();

			public String check(StyleClassCheckerContext context)
			{
				CommercialProposalProduce commercialProposalProduce = (CommercialProposalProduce) form.getGridProduces().getDataList().get(context.getTable().getRecordCounter() - 1);
				boolean isPink = false;
				if (condForPing)
				{
					if (StringUtil.isEmpty(commercialProposalProduce.getCode()))
					{
						isPink = true;
					}
				}
				if (context.getTable().getRecordCounter() < size - countTotalRecord + 1)
				{
					if (commercialProposalProduce.isEmptyProduce() && StringUtil.isEmpty(commercialProposal.getCpr_old_version()))
					{
						if (isPink)
						{
							return "red-font-cell-pink";
						}
						return "red-font-cell";
					}
					else
					{
						if (isPink)
						{
							return "cell-pink";
						}
					}
				}
				return "";
			}
		});
	}

	public ActionForward show(IActionContext context) throws Exception
	{
		CommercialProposalForm formCalc = (CommercialProposalForm) context.getForm();
		CommercialProposal commercialProposalCalc = (CommercialProposal) StoreUtil.getSession(context.getRequest(), CommercialProposal.class);
	  /*
    * Если отмечен "КП составляется по товару, который лежит на складе в Минске", то:
    * Валюта таблицы = BYN и неактивно
    * Валюта печати = BYN и неактивно
    * Курс пересчёта = 1 и неактивно
    * Сумма транспортных расходов и ст-ти упаковки в соответствии с условиями поставки, BYN = 0 и неактивно
    * Сумма ст-ти монтажа и обучения, BYN = 0 и неактивно
    * Цены указаны на условиях = DDP и неактивно
    * Условие поставки = DDP и неактивно
    * "НДС построчно"=ДА и недоступно
    * Заполнять табличную часть без привязки к справочнику "Номенклатура" = НЕТ и неактивно
    * Поле "Дата" при создании равна текущей и в дальнейшем недоступна для редактирования.
    * Поле "Срок действия" = дата КП+10дней и недоступно.
    * В табличной части:
    * а) в колонке "Таможенная пошлина + таможенное оформление" во всех строках значение =0 (соответственно, этот ноль учитывается при расчёте следующих колонок)
    * б) указание таможенных кодов необязательно
    * в) в колонках "НДС", "Стои- мость с НДС" округление до целых
    * */
		if (formCalc.isAssembleMinskStore())
		{
			formCalc.setCurrencyTable(CurrencyDAO.loadByName(context, "BYN"));
			formCalc.setCurrency(CurrencyDAO.loadByName(context, "BYN"));
			formCalc.setCpr_course(1);
			formCalc.setCpr_sum_transport(0);
			for (int i = 0; i < commercialProposalCalc.getTransportLines().size(); i++)
			{
				CommercialProposalTransport commercialProposalTransport = commercialProposalCalc.getTransportLines().get(i);
				commercialProposalTransport.setTrn_sum(0);
			}
			formCalc.setCpr_sum_assembling(0);
			formCalc.setPriceCondition(IncoTermDAO.loadByName(context, IncoTerm.DDP_2010));
			formCalc.setDeliveryCondition(IncoTermDAO.loadByName(context, IncoTerm.DDP_2010));
			formCalc.setCpr_old_version("");
			formCalc.setCpr_nds_by_string("1");
			if ("true".equals(formCalc.getIs_new_doc()))
			{
				formCalc.setCpr_date(StringUtil.date2appDateString(StringUtil.getCurrentDateTime()));
			}
			try
			{
				Calendar calendar = Calendar.getInstance();
				Date cprDate = StringUtil.appDateString2Date(formCalc.getCpr_date());
				calendar.setTime(cprDate);
				calendar.add(Calendar.DATE, 10);
				formCalc.setCpr_final_date(StringUtil.date2appDateString(calendar.getTime()));
				Date finalDate = StringUtil.appDateString2Date(formCalc.getCpr_final_date());
				Date now = StringUtil.getCurrentDateTime();
				formCalc.setDateAcceptReadOnly(finalDate.before(now));
			}
			catch (Exception e)
			{
				log.error(e);
			}
			for (int i = 0; i < commercialProposalCalc.getProduces().size() - commercialProposalCalc.getCountItogRecord(); i++)
			{
				CommercialProposalProduce commercialProposalProduce = commercialProposalCalc.getProduces().get(i);
				commercialProposalProduce.setCustom_duty(0);
			}
			formCalc.setReadOnlyForAssembleMinsk(true);
		}
		else
		{
			formCalc.setReadOnlyForAssembleMinsk(false);
		}
		saveCurrentFormToBean(context);

		final CommercialProposalForm form = (CommercialProposalForm) context.getForm();
		final CommercialProposal commercialProposal = (CommercialProposal) StoreUtil.getSession(context.getRequest(), CommercialProposal.class);
		final int countTotalRecord = commercialProposal.calculate();
		form.getGridProduces().setDataList(commercialProposal.getProduces());
		form.getGridTransport().setDataList(commercialProposal.getTransportLines());
		form.getAttachmentsGrid().setDataList(form.getAttachmentService().list());
		form.setInclude_exps(commercialProposal.getInclude_exps());
		form.setCpr_sum_transport(commercialProposal.getCpr_sum_transport());
		form.formGridCharges();
		form.setShow_unit(commercialProposal.getShow_unit());
		form.setShowAskClearTable((commercialProposal.getProduces().size() - countTotalRecord) > 0);
		if (!StringUtil.isEmpty(form.getUser().getUsr_id()))
		{
			form.setUser(UserDAO.load(context, form.getUser().getUsr_id()));
		}
		if (!StringUtil.isEmpty(form.getExecutor().getUsr_id()))
		{
			form.setExecutor(UserDAO.load(context, form.getExecutor().getUsr_id()));
		}
		form.setTotal(StringUtil.double2appCurrencyString(commercialProposal.getTotal()));

		if (form.isNeedPrint())
		{
			form.setPrint("true");
		}
		else
		{
			form.setPrint("false");
		}
		form.setNeedPrint(false);

		if (form.isNeedPrintInvoice())
		{
			form.setPrintInvoice("true");
		}
		else
		{
			form.setPrintInvoice("false");
		}
		form.setNeedPrintInvoice(false);

		if (form.isNeedPrintContract())
		{
			form.setPrintContract("true");
		}
		else
		{
			form.setPrintContract("false");
		}
		form.setNeedPrintContract(false);

		form.setPrintMode("");

		setVisuallyAttributes(context);

		form.setStyle_sale_cost(null);
		form.setStyle_sale_price(null);
		form.setStyle_sale_cost_parking_trans(null);
		form.setStyle_sale_price_parking_trans(null);
		form.setStyle_sale_cost_parking_trans_custom(null);
		form.setStyle_sale_price_parking_trans_custom(null);

		form.setCheck_sale_cost("show-checker-long");
		form.setCheck_sale_cost_parking_trans("show-checker");
		form.setCheck_sale_cost_parking_trans_custom("show-checker");

		if (commercialProposal.incoTermCaseA())
		{
			form.setStyle_sale_cost("style-checker-long");
			form.setStyle_sale_price("style-checker-short");
			form.setCheck_sale_cost("show-checker-all");
		}
		if (commercialProposal.incoTermCaseB())
		{
			form.setStyle_sale_cost_parking_trans("style-checker-long");
			form.setStyle_sale_price_parking_trans("style-checker-short");
			form.setCheck_sale_cost_parking_trans("show-checker-all");
		}
		if (commercialProposal.incoTermCaseC())
		{
			form.setStyle_sale_cost("style-checker-long");
			form.setStyle_sale_price("style-checker-short");
			form.setCheck_sale_cost("show-checker-all");
		}
		if (commercialProposal.incoTermCaseD())
		{
			form.setStyle_sale_cost_parking_trans_custom("style-checker-long");
			form.setStyle_sale_price_parking_trans_custom("style-checker-short");
			form.setCheck_sale_cost_parking_trans_custom("show-checker-all");
		}
		if (commercialProposal.incoTermCaseE())
		{
			form.setStyle_sale_cost_parking_trans_custom("style-checker-long");
			form.setStyle_sale_price_parking_trans_custom("style-checker-short");
			form.setCheck_sale_cost_parking_trans_custom("show-checker-all");
		}

		if (StringUtil.isEmpty(form.getStyle_sale_cost()))
		{
			form.setStyle_sale_cost("style-checker");
		}
		if (StringUtil.isEmpty(form.getStyle_sale_price()))
		{
			form.setStyle_sale_price("style-checker");
		}
		if (StringUtil.isEmpty(form.getStyle_sale_cost_parking_trans()))
		{
			form.setStyle_sale_cost_parking_trans("style-checker");
		}
		if (StringUtil.isEmpty(form.getStyle_sale_price_parking_trans()))
		{
			form.setStyle_sale_price_parking_trans("style-checker");
		}
		if (StringUtil.isEmpty(form.getStyle_sale_cost_parking_trans_custom()))
		{
			form.setStyle_sale_cost_parking_trans_custom("style-checker");
		}
		if (StringUtil.isEmpty(form.getStyle_sale_price_parking_trans_custom()))
		{
			form.setStyle_sale_price_parking_trans_custom("style-checker");
		}

		context.getRequest().setAttribute("transportChecker", new IReadOnlyChecker()
		{
			public boolean check(ReadOnlyCheckerContext ctx) throws Exception
			{
				CommercialProposalForm.CommercialProposalCharge commercialProposalCharge = (CommercialProposalForm.CommercialProposalCharge) form.getGridCharges().getDataList().get(ctx.getTable().getRecordCounter() - 1);
				return form.isReadOnlyForAssembleMinsk() ||
								(form.isShowTransportTable() && commercialProposalCharge.getNumber().equals(CommercialProposalForm.transportNumberLine));
			}
		});

		if (StringUtil.isEmpty(form.getCreateUser().getUsr_id()) || //новый документ
						(!"1".equals(form.getCpr_block())))
		{
			form.setFormReadOnly(false);
		}
		else
		{
			form.setFormReadOnly(true);
		}

		Session hibSession = HibernateUtil.getSessionFactory().getCurrentSession();
		hibSession.beginTransaction();
		AttachmentsService attachmentsService = new AttachmentsService(hibSession);
		form.setTemplateExcel(attachmentsService.firstAttach("DCL_TEMPLATE", ConstDefinitions.templateIdCP));
		hibSession.getTransaction().commit();

		User user = UserUtil.getCurrentUser(context.getRequest());
		if (!user.isAdmin())
		{
			form.setShowForAdmin(false);
		}
		else
		{
			form.setShowForAdmin(true);
		}
		if (user.isAdmin() || user.isEconomist())
		{
			form.setShowForAdminOrEconomist(true);
		}
		else
		{
			form.setShowForAdminOrEconomist(false);
		}
		form.setShowPrintInvoice(false);
		if (!form.isAssembleMinskStore()) //не Минск
		{
			form.setShowPrintInvoice(form.isShowForAdminOrEconomist());
		}
		else // для товара на складе в Минске
		{
			//если админ, менеджер, экономист
			if (user.isAdmin() || user.isManager() || user.isEconomist())
			{
				form.setShowPrintInvoice(true);
			}
		}

		form.setNoReservationReadOnly(true);
		//Снимать с брони может: а) пользователь, создавший КП, б) менеджер того же отдела, в) админ
		if (
						user.isAdmin() ||
										"true".equals(form.getIs_new_doc()) ||
										user.getUsr_id().equals(form.getCreateUser().getUsr_id()) ||
										(
														!"true".equals(form.getIs_new_doc()) &&
																		user.getDepartment().getId().equals(form.getCreateUser().getDepartment().getId())
										)
						)
		{
			form.setNoReservationReadOnly(false);
		}

		/** Кнопка "Печать с-ф" доступна если выполняются сразу все эти условия:
		 1.
		 Кнопка доступна если выполняются сразу все эти условия:
		 Валюта таблицы <> BYN
		 Валюта печати = BYN
		 Все значения в колонке "Коэф-т наценки" >=1,15
		 Значение поля "Курс пересчёта" >= k*1,05, где  k - курс НБРБ валюты, указанной в поле "Валюта таблицы", на дату, указанную в поле "дата".
		 Если в справочнике отсутствует курс на ту дату, которая указана в поле "Дата", то брать курс за предыдущую дату.

		 2.
		 Кнопка доступна если выполняются сразу все эти условия:
		 Валюта таблицы = BYN
		 Валюта печати = BYN
		 Все значения в колонке "Коэф-т наценки" >=1,25
		 Значение поля "Курс пересчёта" = 1

		 НДС построчно = ДА для обеих вариантов

		 *  Часть из условий проверяем в java-скрипте
		 */
		boolean correctKoef = true;
		boolean caseOne = false;
		boolean caseTwo = false;
		//случай 1
		if (!"BYN".equalsIgnoreCase(form.getCurrencyTable().getName()) && "BYN".equalsIgnoreCase(form.getCurrency().getName()))
		{
			caseOne = true;
		}
		//случай 2
		if ("BYN".equalsIgnoreCase(form.getCurrencyTable().getName()) && "BYN".equalsIgnoreCase(form.getCurrency().getName()))
		{
			caseTwo = true;
		}

		for (int i = 0; i < commercialProposalCalc.getProduces().size() - countTotalRecord; i++)
		{
			CommercialProposalProduce commercialProposalProduce = commercialProposalCalc.getProduces().get(i);
			//случай 1
			if (caseOne)
			{
				if (commercialProposalProduce.getLpr_coeficient() < 1.15)
				{
					correctKoef = false;
					break;
				}
			}

			//случай 2
			if (caseTwo)
			{
				if (commercialProposalProduce.getLpr_coeficient() < 1.25)
				{
					correctKoef = false;
					break;
				}
			}
		}

		form.setCourseRecommendText("");
		CurrencyRate currencyRate = CurrencyRateDAO.loadRateForDate(context, form.getCurrencyTable().getId(), form.getCpr_date_ts());

		/***
		 * Если:
		 * Валюта таблицы <> BYN
		 * Валюта печати = BYN
		 * То:
		 * Справа от поля "Курс пересчёта" добавить текст "Рекомендуемый min курс: m"

		 * где
		 * m = k*1,05 (с точностью до сотых)
		 * k - курс НБРБ валюты, указанной в поле "Валюта таблицы", на дату, указанную в поле "дата".
		 * Если в справочнике отсутствует курс на ту дату, которая указана в поле "Дата", то брать курс за предыдущую дату.
		 */

		form.setCourseRecommendText(StrutsUtil.getMessage(context, "CommercialProposal.cpr_course_recommended",
						StringUtil.double2appCurrencyStringByMask(currencyRate.getCrt_rate() * Config.getFloat(Constants.minCourseCoefficient, 1.05f), "#,##0.00000000")));

		//случай 1
		if (caseOne)
		{
			try
			{
				if (!CurrencyRateDAO.haveRateForDate(context, form.getCurrencyTable().getId(), form.getCpr_date_ts()))
				{
					correctKoef = false;
				}
			}
			catch (LoadException lEx)
			{
				log.error(lEx.getMessage(), lEx);
			}
 			//if ((form.getCpr_course() + 0.01f) < currencyRate.getCrt_rate() * Config.getFloat(Constants.minCourseCoefficient, 1.05f))
			if (form.getCpr_course() < currencyRate.getCrt_rate() * Config.getFloat(Constants.minCourseCoefficient, 1.05f))
			{
				correctKoef = false;
			}
		}
		//случай 2
		if (caseTwo)
		{
			if (form.getCpr_course() != 1)
			{
				correctKoef = false;
			}
		}
		form.setCorrectKoef(correctKoef);

		if (StringUtil.isEmpty(form.getCpr_print_scale()))
		{
			form.setCpr_print_scale("100");
		}
		if (StringUtil.isEmpty(form.getCpr_contract_scale()))
		{
			form.setCpr_contract_scale("100");
		}
		if (StringUtil.isEmpty(form.getCpr_invoice_scale()))
		{
			form.setCpr_invoice_scale("100");
		}

		return context.getMapping().findForward("form");
	}

	private void setNeedPrint(CommercialProposalForm form, boolean needPrint)
	{
		form.setNeedPrint(needPrint);
		form.setNeedPrintInvoice(needPrint);
		form.setNeedPrintContract(needPrint);
	}

	public ActionForward reload(IActionContext context) throws Exception
	{
		CommercialProposalForm form = (CommercialProposalForm) context.getForm();
		setNeedPrint(form, false);

		saveCurrentFormToBean(context);

		CommercialProposal commercialProposal = (CommercialProposal) StoreUtil.getSession(context.getRequest(), CommercialProposal.class);
		for (CommercialProposalProduce commercialProposalProduce : commercialProposal.getProduces())
		{
			commercialProposalProduce.setCpr_date(commercialProposal.getCpr_date());
		}
		StoreUtil.putSession(context.getRequest(), commercialProposal);
		commonRecalc(context);

		return show(context);
	}

	public ActionForward reloadWithClean(IActionContext context) throws Exception
	{
		CommercialProposalForm form = (CommercialProposalForm) context.getForm();
		setNeedPrint(form, false);
		CommercialProposal commercialProposal = (CommercialProposal) StoreUtil.getSession(context.getRequest(), CommercialProposal.class);
		commercialProposal.clear();
		StoreUtil.putSession(context.getRequest(), commercialProposal);

		saveCurrentFormToBean(context);

		return show(context);
	}

	public ActionForward reloadPrice(IActionContext context) throws Exception
	{
		CommercialProposalForm form = (CommercialProposalForm) context.getForm();
		form.setPriceCondition(IncoTermDAO.load(context, form.getPriceCondition().getId()));
		List res = IncoTermDAO.loadDependentTerms(context, form.getPriceCondition().getName());
		if (res.size() != 0)
		{
			form.setDeliveryCondition((IncoTerm) res.get(0));
		}
		else
		{
			form.setDeliveryCondition(new IncoTerm());
		}
		setNeedPrint(form, false);

		saveCurrentFormToBean(context);

		return show(context);
	}

	public ActionForward input(IActionContext context) throws Exception
	{
		CommercialProposalForm form = (CommercialProposalForm) context.getForm();
		form.setAttachmentService(DeferredAttachmentService.create(context.getRequest().getSession(), REFERENCED_TABLE, context.getMapping().findForward("backFromAttach"), null));
		CommercialProposal commercialProposal = new CommercialProposal();
		StoreUtil.putSession(context.getRequest(), commercialProposal);
		//обнуляем поля формы
		getCurrentFormFromBean(context, commercialProposal);

		form.setCpr_date(StringUtil.date2appDateString(StringUtil.getCurrentDateTime()));
		form.setCpr_final_date(StringUtil.date2appDateString(StringUtil.getCurrentDateTime()));
		form.setCpr_final_date_invoice(StringUtil.date2appDateString(StringUtil.getCurrentDateTime()));
		form.setCpr_date_accept("");
		form.setCpr_nds_formatted(StringUtil.double2appCurrencyStringWithoutDec(RateNDSDAO.loadForDate(context, form.getCpr_date()).getPercent()));
		form.setIs_new_doc("true");
		form.setCurrency(CurrencyDAO.loadByName(context, Config.getString(Constants.defaultCPCurrency)));
		form.setCurrencyTable(CurrencyDAO.loadByName(context, Config.getString(Constants.defaultCPTableCurrency)));
		form.setCpr_course(1);
		setNeedPrint(form, false);
		form.setCpr_old_version("");
		form.setCpr_assemble_minsk_store("");
		form.setCpr_reverse_calc("");
		form.setShow_unit("1");
		form.setCpr_all_transport("1");
		form.setCpr_can_edit_invoice("");
		form.setUser(UserUtil.getCurrentUser(context.getRequest()));
		form.setCpr_donot_calculate_netto("1");
		form.setCpr_final_date_above("1");
		form.setCpr_prepay_percent("100");

		saveCurrentFormToBean(context);

		return show(context);
	}

	public void commonClone(IActionContext context, String oldVersion) throws Exception
	{
		CommercialProposalForm form = (CommercialProposalForm) context.getForm();
		form.setAttachmentService(DeferredAttachmentService.create(context.getRequest().getSession(), REFERENCED_TABLE, context.getMapping().findForward("backFromAttach"), null));
		CommercialProposal commercialProposal = CommercialProposalDAO.load(context, form.getCpr_id());
		if (StringUtil.isEmpty(oldVersion))
		{
			for (CommercialProposalProduce commercialProposalProduce : commercialProposal.getProduces())
			{
				if (null == commercialProposalProduce.getProduce())
				{
					commercialProposalProduce.setProduce(new DboProduce());
				}
				commercialProposalProduce.setLpr_produce_name("");
				commercialProposalProduce.setLpr_catalog_num("");
			}
		}
		//обнуляем, чтобы случайно не затянуть в форму
		commercialProposal.setCpr_id("");
		commercialProposal.setCpr_number("");
		commercialProposal.setCpr_date_accept("");
		String oldFlag = commercialProposal.getCpr_old_version();
		commercialProposal.setCpr_old_version(oldVersion);

		//Если исходный документ в старом формате, то обнуляем таможенный код.
		if (!StringUtil.isEmpty(oldFlag))
		{
			for (CommercialProposalProduce commercialProposalProduce : commercialProposal.getProduces())
			{
				commercialProposalProduce.setCustomCode(new CustomCode());
			}
		}

		if (StringUtil.isEmpty(oldFlag) && !StringUtil.isEmpty(oldVersion))
		{
			//был заполнен по новому, а копируется как старый - нужно перенести данные из полей DBOProduce в
			//поле старого наименования товара?
			for (CommercialProposalProduce commercialProposalProduce : commercialProposal.getProduces())
			{
				if (null != commercialProposalProduce.getProduce() && null != commercialProposalProduce.getProduce().getId())
				{
					commercialProposalProduce.setLpr_produce_name(commercialProposalProduce.getProduce().getFullName());
					if (commercialProposalProduce.getProduce().getCatalogNumbers().size() > 0)
					{
						Iterator<DboCatalogNumber> iterator = commercialProposalProduce.getProduce().getCatalogNumbers().values().iterator();
						if (iterator.hasNext())
						{
							DboCatalogNumber catalogNumber = iterator.next();
							commercialProposalProduce.setLpr_catalog_num(catalogNumber.getNumber());
						}
					}
					commercialProposalProduce.setProduce(new DboProduce());
					commercialProposalProduce.setStuffCategory(new StuffCategory());
				}
			}
		}
		commercialProposal.setListIdsToNull();
		commercialProposal.setCpr_proposal_received_flag("0");
		commercialProposal.setCpr_proposal_declined("0");
		commercialProposal.setCpr_tender_number_editable("0");
		commercialProposal.setCpr_tender_number("");
		commercialProposal.setCpr_block("");
		StoreUtil.putSession(context.getRequest(), commercialProposal);
		getCurrentFormFromBean(context, commercialProposal);

		form.setUsr_date_create(null);
		form.setUsr_date_edit(null);
		User user = UserUtil.getCurrentUser(context.getRequest());
		form.setCreateUser(user);
		form.setEditUser(user);

		form.setCpr_date(StringUtil.date2appDateString(StringUtil.getCurrentDateTime()));
		form.setShow_unit("1");
		form.setCpr_all_transport("1");
		form.setCpr_can_edit_invoice("");
		form.setCpr_prepay_percent("100");

		form.setIs_new_doc("true");
		setNeedPrint(form, false);
	}

	public ActionForward cloneLikeOldVersion(IActionContext context) throws Exception
	{
		commonClone(context, "1");

		return show(context);
	}

	public ActionForward cloneLikeNewVersion(IActionContext context) throws Exception
	{
		commonClone(context, "");

		return show(context);
	}

	public ActionForward edit(IActionContext context) throws Exception
	{
		CommercialProposalForm form = (CommercialProposalForm) context.getForm();
		form.setAttachmentService(DeferredAttachmentService.create(context.getRequest().getSession(), REFERENCED_TABLE, Integer.parseInt(form.getCpr_id()), context.getMapping().findForward("backFromAttach"), null));
		CommercialProposal commercialProposal = CommercialProposalDAO.load(context, form.getCpr_id());
		StoreUtil.putSession(context.getRequest(), commercialProposal);
		getCurrentFormFromBean(context, commercialProposal);

		form.setShow_unit("1");
		setNeedPrint(form, false);

		return show(context);
	}

	public ActionForward back(IActionContext context) throws Exception
	{
		CommercialProposalForm form = (CommercialProposalForm) context.getForm();
		form.getAttachmentService().rollback();
		form.setAttachmentService(null);
		DeferredAttachmentService.removeLast(context.getRequest().getSession());

		return context.getMapping().findForward("back");
	}

	public ActionForward deferredAttach(IActionContext context) throws Exception
	{
		saveCurrentFormToBean(context);
		CommercialProposalForm form = (CommercialProposalForm) context.getForm();

		DeferredAttachmentService.set(context.getRequest().getSession(), form.getAttachmentService());
		return context.getMapping().findForward("deferredAttach");
	}

	public ActionForward deleteAttachment(IActionContext context) throws Exception
	{
		saveCurrentFormToBean(context);
		CommercialProposalForm form = (CommercialProposalForm) context.getForm();
		DeferredAttachmentService.DeferredAttachment attachment = form.getAttachmentService().find(Integer.parseInt(form.getAttachmentId()));
		form.getAttachmentService().delete(attachment);

		return show(context);
	}

	public ActionForward downloadAttachment(IActionContext context) throws Exception
	{
		saveCurrentFormToBean(context);
		CommercialProposalForm form = (CommercialProposalForm) context.getForm();
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

	public ActionForward process(IActionContext context) throws Exception
	{
		CommercialProposalForm form = (CommercialProposalForm) context.getForm();
		boolean retFromSave = saveCommon(context);

		if (retFromSave && !StringUtil.isEmpty(form.getPrintMode()))
		{
			CommercialProposal commercialProposal = CommercialProposalDAO.load(context, form.getCpr_id());
			StoreUtil.putSession(context.getRequest(), commercialProposal);
			getCurrentFormFromBean(context, commercialProposal);
			form.setIs_new_doc("");
		}

		if (retFromSave && "print".equals(form.getPrintMode()))
		{
			return print(context);
		}
		else if (retFromSave && "printInvoice".equals(form.getPrintMode()))
		{
			return printInvoice(context);
		}
		else if (retFromSave && "printContract".equals(form.getPrintMode()))
		{
			return printContract(context);
		}
		else if (retFromSave)
		{
			return back(context);
		}
		else
		{
			setNeedPrint(form, false);
			form.setPrintMode("");
			return show(context);
		}
	}

	public ActionForward importExcel(IActionContext context) throws Exception
	{
		saveCurrentFormToBean(context);

		return context.getMapping().findForward("importExcel");
	}

	public ActionForward uploadTemplate(IActionContext context) throws Exception
	{
		saveCurrentFormToBean(context);

		return context.getMapping().findForward("uploadTemplate");
	}

	public ActionForward ajaxProducesForAssembleMinskGrid(IActionContext context) throws Exception
	{
		CommercialProposalForm form = (CommercialProposalForm) context.getForm();
		CommercialProposal commercialProposal = (CommercialProposal) StoreUtil.getSession(context.getRequest(), CommercialProposal.class);
		form.setStyle_lpc_price_list_by("");
		form.setStyle_discount_percent("");
		if (commercialProposal.isPrintPriceListBy())
		{
			form.setStyle_lpc_price_list_by("style-checker-short");
			form.setStyle_discount_percent("style-checker-short");
		}
		form.setCpr_nds_by_string(commercialProposal.getCpr_nds_by_string());
		form.setCpr_free_prices(commercialProposal.getCpr_free_prices());

		setVisuallyAttributes(context);

		return context.getMapping().findForward("ajaxProducesForAssembleMinskGrid");
	}

	public ActionForward ajaxDeleteAllProducesForAssembleMinskGrid(IActionContext context) throws Exception
	{
		CommercialProposal commercialProposal = (CommercialProposal) StoreUtil.getSession(context.getRequest(), CommercialProposal.class);
		while (commercialProposal.getProduces().size() > commercialProposal.getCountItogRecord())
		{
			commercialProposal.getProduces().remove(0);
		}

		return ajaxProducesForAssembleMinskGrid(context);
	}

	public ActionForward ajaxRemoveFromProducesForAssembleMinskGrid(IActionContext context) throws Exception
	{
		CommercialProposal commercialProposal = (CommercialProposal) StoreUtil.getSession(context.getRequest(), CommercialProposal.class);
		String number = context.getRequest().getParameter("number");
		if (!StringUtil.isEmpty(number))
		{
			commercialProposal.deleteProduce(number);
		}

		return ajaxProducesForAssembleMinskGrid(context);
	}

	public ActionForward ajaxProducesCommercialProposalGrid(IActionContext context) throws Exception
	{
		CommercialProposalForm form = (CommercialProposalForm) context.getForm();
		CommercialProposal commercialProposal = (CommercialProposal) StoreUtil.getSession(context.getRequest(), CommercialProposal.class);

		// Null-guard to prevent NPEs when CommercialProposal is not present in session.
		if (commercialProposal == null)
		{
			String cprId = form.getCpr_id();
			if (StringUtil.isEmpty(cprId))
			{
				cprId = context.getRequest().getParameter("cpr_id");
			}
			if (!StringUtil.isEmpty(cprId))
			{
				try
				{
					commercialProposal = CommercialProposalDAO.load(context, cprId);
					StoreUtil.putSession(context.getRequest(), commercialProposal);
				}
				catch (Exception e)
				{
					log.error("Failed to reload CommercialProposal for ajaxProducesCommercialProposalGrid, cpr_id=" + cprId, e);
				}
			}
		}

		if (commercialProposal == null)
		{
			log.warn("CommercialProposal is null in session during ajaxProducesCommercialProposalGrid");
			// Keep UI responsive: empty data list and default flags
			form.setCpr_nds_by_string("");
			form.getGridProduces().setDataList(new java.util.ArrayList());
			return context.getMapping().findForward("ajaxProducesCommercialProposalGrid");
		}

		form.setCpr_nds_by_string(commercialProposal.getCpr_nds_by_string());

		setVisuallyAttributes(context);

		return context.getMapping().findForward("ajaxProducesCommercialProposalGrid");
	}

	public ActionForward ajaxDeleteAllProducesCommercialProposalGrid(IActionContext context) throws Exception
	{
		CommercialProposal commercialProposal = (CommercialProposal) StoreUtil.getSession(context.getRequest(), CommercialProposal.class);
		while (commercialProposal.getProduces().size() > commercialProposal.getCountItogRecord())
		{
			commercialProposal.getProduces().remove(0);
		}

		return ajaxProducesCommercialProposalGrid(context);
	}

	public ActionForward ajaxRemoveFromCommercialProposalGrid(IActionContext context) throws Exception
	{
		CommercialProposal commercialProposal = (CommercialProposal) StoreUtil.getSession(context.getRequest(), CommercialProposal.class);
		String number = context.getRequest().getParameter("number");
		if (!StringUtil.isEmpty(number))
		{
			commercialProposal.deleteProduce(number);
		}

		return ajaxProducesCommercialProposalGrid(context);
	}

	public ActionForward ajaxChangeSalePriceForAssembleMinskGrid(IActionContext context) throws Exception
	{
		CommercialProposal commercialProposal = (CommercialProposal) StoreUtil.getSession(context.getRequest(), CommercialProposal.class);
		String salePrice = context.getRequest().getParameter("salePrice");
		String priceId = context.getRequest().getParameter("priceId");
		if (!StringUtil.isEmpty(salePrice) && !StringUtil.isEmpty(priceId))
		{
			CommercialProposalProduce commercialProposalProduce = commercialProposal.getProduces().get(new Integer(priceId));
			commercialProposalProduce.setSale_price_parking_trans_custom_formatted(salePrice);
		}

		return ajaxProducesForAssembleMinskGrid(context);
	}

	public ActionForward ajaxChangeNDSByString(IActionContext context) throws Exception
	{
		CommercialProposalForm form = (CommercialProposalForm) context.getForm();
		CommercialProposal commercialProposal = (CommercialProposal) StoreUtil.getSession(context.getRequest(), CommercialProposal.class);
		String ndsByString = context.getRequest().getParameter("ndsByString");
		if ("true".equalsIgnoreCase(ndsByString))
		{
			form.setCpr_nds_by_string("1");
			commercialProposal.setCpr_nds_by_string("1");
		}
		else
		{
			form.setCpr_nds_by_string("");
			commercialProposal.setCpr_nds_by_string("");
		}

		return context.getMapping().findForward("ajax");
	}

	public ActionForward ajaxChangeFreePrices(IActionContext context) throws Exception
	{
		CommercialProposalForm form = (CommercialProposalForm) context.getForm();
		CommercialProposal commercialProposal = (CommercialProposal) StoreUtil.getSession(context.getRequest(), CommercialProposal.class);
		String freePrices = context.getRequest().getParameter("freePrices");
		if ("true".equalsIgnoreCase(freePrices))
		{
			form.setCpr_free_prices("1");
			commercialProposal.setCpr_free_prices("1");
		}
		else
		{
			form.setCpr_free_prices("");
			commercialProposal.setCpr_free_prices("");
		}

		return context.getMapping().findForward("ajax");
	}

	public ActionForward ajaxChangeReverseCalc(IActionContext context) throws Exception
	{
		CommercialProposalForm form = (CommercialProposalForm) context.getForm();
		CommercialProposal commercialProposal = (CommercialProposal) StoreUtil.getSession(context.getRequest(), CommercialProposal.class);
		String reverseCalc = context.getRequest().getParameter("reverseCalc");
		if ("true".equalsIgnoreCase(reverseCalc))
		{
			form.setCpr_reverse_calc("1");
			commercialProposal.setCpr_reverse_calc("1");
		}
		else
		{
			form.setCpr_reverse_calc("");
			commercialProposal.setCpr_reverse_calc("");
		}

		return context.getMapping().findForward("ajax");
	}

	public ActionForward ajaxChangeCalculate(IActionContext context) throws Exception
	{
		CommercialProposalForm form = (CommercialProposalForm) context.getForm();
		CommercialProposal commercialProposal = (CommercialProposal) StoreUtil.getSession(context.getRequest(), CommercialProposal.class);
		String calculateNtto = context.getRequest().getParameter("calculateNtto");
		if ("true".equalsIgnoreCase(calculateNtto))
		{
			form.setCpr_donot_calculate_netto("");
			commercialProposal.setCpr_donot_calculate_netto("");
		}
		else
		{
			form.setCpr_donot_calculate_netto("1");
			commercialProposal.setCpr_donot_calculate_netto("1");
		}

		return context.getMapping().findForward("ajax");
	}

	public ActionForward ajaxChangeCourse(IActionContext context) throws Exception
	{
		CommercialProposalForm form = (CommercialProposalForm) context.getForm();
		CommercialProposal commercialProposal = (CommercialProposal) StoreUtil.getSession(context.getRequest(), CommercialProposal.class);
		String course = context.getRequest().getParameter("course");
		if (!StringUtil.isEmpty(course))
		{
			form.setCpr_course_formatted(course);
			commercialProposal.setCpr_course(form.getCpr_course());
		}

		return context.getMapping().findForward("ajax");
	}

	public ActionForward ajaxChangeNDS(IActionContext context) throws Exception
	{
		CommercialProposalForm form = (CommercialProposalForm) context.getForm();
		CommercialProposal commercialProposal = (CommercialProposal) StoreUtil.getSession(context.getRequest(), CommercialProposal.class);
		String nds = context.getRequest().getParameter("nds");
		if (!StringUtil.isEmpty(nds))
		{
			form.setCpr_nds_formatted(nds);
			commercialProposal.setCpr_nds(form.getCpr_nds());
		}

		return context.getMapping().findForward("ajax");
	}

	public ActionForward ajaxChangeCurrency(IActionContext context) throws Exception
	{
		CommercialProposal commercialProposal = (CommercialProposal) StoreUtil.getSession(context.getRequest(), CommercialProposal.class);
		String currencyId = context.getRequest().getParameter("currencyId");
		if (!StringUtil.isEmpty(currencyId))
		{
			commercialProposal.getCurrency().setId(currencyId);
		}

		return context.getMapping().findForward("ajax");
	}

	private void commonRecalc(IActionContext context) throws Exception
	{
		CommercialProposalForm form = (CommercialProposalForm) context.getForm();
		CommercialProposal commercialProposal = (CommercialProposal) StoreUtil.getSession(context.getRequest(), CommercialProposal.class);
		commercialProposal.calculate();
		form.getGridProduces().setDataList(commercialProposal.getProduces());
	}

	public ActionForward ajaxRecalcForAssembleMinskGrid(IActionContext context) throws Exception
	{
		commonRecalc(context);

		return ajaxProducesForAssembleMinskGrid(context);
	}

	public ActionForward ajaxGetTotal(IActionContext context) throws Exception
	{
		CommercialProposal commercialProposal = (CommercialProposal) StoreUtil.getSession(context.getRequest(), CommercialProposal.class);
		String total = StringUtil.double2appCurrencyString(commercialProposal.getTotal());
		StrutsUtil.setAjaxResponse(context, total, false);
		return context.getMapping().findForward("ajax");
	}

	public ActionForward ajaxRecalcCommercialProposalGrid(IActionContext context) throws Exception
	{
		commonRecalc(context);

		return ajaxProducesCommercialProposalGrid(context);
	}

	public ActionForward retFromAttach(IActionContext context) throws Exception
	{
		getCurrentFormFromBean(context, null);

		return show(context);
	}

	public ActionForward ajaxGetReputation(IActionContext context) throws Exception
	{
		String contractorId = context.getRequest().getParameter("contractor-id");
		if (!StringUtil.isEmpty(contractorId) && !"-1".equals(contractorId))
		{
			Contractor contractor = ContractorDAO.load(context, context.getRequest().getParameter("contractor-id"));
			String reputation = StrutsUtil.getMessage(context, "CommercialProposal.reputation");
			reputation += " ";
			reputation += contractor.getReputation().getDescription();
			StrutsUtil.setAjaxResponse(context, reputation, false);
		}
		return context.getMapping().findForward("ajax");
	}
}
