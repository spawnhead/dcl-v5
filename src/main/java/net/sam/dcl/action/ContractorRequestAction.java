package net.sam.dcl.action;

import net.sam.dcl.beans.*;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.controller.IDispatchable;
import net.sam.dcl.controller.actions.DBTransactionAction;
import net.sam.dcl.controller.actions.SelectFromGridAction;
import net.sam.dcl.dao.*;
import net.sam.dcl.dbo.DboCatalogNumber;
import net.sam.dcl.dbo.DboProduce;
import net.sam.dcl.form.ContractorRequestForm;
import net.sam.dcl.service.AttachmentException;
import net.sam.dcl.service.DeferredAttachmentService;
import net.sam.dcl.util.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForward;
import org.hibernate.Session;

import java.util.List;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class ContractorRequestAction extends DBTransactionAction implements IDispatchable
{
	protected static Log log = LogFactory.getLog(ContractorRequestAction.class);
	final static String referencedTable = "DCL_CONTRACTOR_REQUEST";

	private void saveCurrentFormToBean(IActionContext context)
	{
		ContractorRequestForm form = (ContractorRequestForm) context.getForm();
		ContractorRequest contractorRequest = (ContractorRequest) StoreUtil.getSession(context.getRequest(), ContractorRequest.class);

		contractorRequest.setIs_new_doc(form.getIs_new_doc());
		contractorRequest.setCrq_id(form.getCrq_id());
		try
		{
			if (!StringUtil.isEmpty(form.getCreateUser().getUsr_id()))
			{
				contractorRequest.setCreateUser(UserDAO.load(context, form.getCreateUser().getUsr_id()));
			}
			if (!StringUtil.isEmpty(form.getEditUser().getUsr_id()))
			{
				contractorRequest.setEditUser(UserDAO.load(context, form.getEditUser().getUsr_id()));
			}

			contractorRequest.setUsr_date_create(form.getUsr_date_create());
			contractorRequest.setUsr_date_edit(form.getUsr_date_edit());
			contractorRequest.setCrq_number(form.getCrq_number());
			contractorRequest.setCrq_ticket_number(form.getCrq_ticket_number());
			contractorRequest.setCrq_comment(form.getCrq_comment());
			contractorRequest.setCrq_receive_date(form.getCrq_receive_date());

			if (!StringUtil.isEmpty(form.getContractor().getId()))
			{
				contractorRequest.setContractor(ContractorDAO.load(context, form.getContractor().getId()));
			}
			if (!StringUtil.isEmpty(form.getContactPerson().getCps_id()))
			{
				contractorRequest.setContactPerson(ContactPersonDAO.load(context, form.getContactPerson().getCps_id()));
			}
			contractorRequest.setRequestType(form.getRequestType());
			if (!StringUtil.isEmpty(form.getContract().getCon_id()))
			{
				contractorRequest.setContract(ContractDAO.load(context, form.getContract().getCon_id(), false));
			}
			else
			{
				contractorRequest.setContract(new Contract());
			}
			if (!StringUtil.isEmpty(form.getContractForWork().getCon_id()))
			{
				contractorRequest.setContractForWork(ContractDAO.load(context, form.getContractForWork().getCon_id(), false));
			}
			else
			{
				contractorRequest.setContractForWork(new Contract());
			}
			contractorRequest.setLps_id(form.getLps_id());
			contractorRequest.setCrq_equipment(form.getCrq_equipment());
			contractorRequest.setCtn_number(form.getCtn_number());
			contractorRequest.setStf_name(form.getStf_name());
			contractorRequest.setLps_serial_num(form.getLps_serial_num());
			contractorRequest.setLps_year_out(form.getLps_year_out());
			contractorRequest.setStuffCategory(form.getStuffCategory());
			contractorRequest.setProduce(form.getProduce());
			contractorRequest.setLps_enter_in_use_date(form.getLps_enter_in_use_date());
			contractorRequest.setMad_complexity(form.getMad_complexity());
			contractorRequest.setCrq_deliver(form.getCrq_deliver());
			contractorRequest.setCrq_annul(form.getCrq_annul());
			contractorRequest.setCrq_serial_num(form.getCrq_serial_num());
			contractorRequest.setCrq_year_out(form.getCrq_year_out());
			contractorRequest.setCrq_enter_in_use_date(form.getCrq_enter_in_use_date());
			contractorRequest.setCrq_operating_time(StringUtil.stringToDouble(form.getCrq_operating_time()));
			// 0 - ЗАО "Линтера", 1 - ИП "ЛинтераТехСервис", 2 - Прочие
			if (!StringUtil.isEmpty(form.getSeller().getId()))
			{
				contractorRequest.setSeller(SellerDAO.load(context, form.getSeller().getId()));
			}

			if (!StringUtil.isEmpty(form.getManager().getUsr_id()))
			{
				contractorRequest.setManager(UserDAO.load(context, form.getManager().getUsr_id()));
			}
			if (!StringUtil.isEmpty(form.getChief().getUsr_id()))
			{
				contractorRequest.setChief(UserDAO.load(context, form.getChief().getUsr_id()));
			}
			if (!StringUtil.isEmpty(form.getSpecialist().getUsr_id()))
			{
				contractorRequest.setSpecialist(UserDAO.load(context, form.getSpecialist().getUsr_id()));
			}
			contractorRequest.setCrq_city(form.getCrq_city());
			if (!StringUtil.isEmpty(form.getContractorOther().getId()))
			{
				contractorRequest.setContractorOther(ContractorDAO.load(context, form.getContractorOther().getId()));
			}

			contractorRequest.setCrq_no_contract(form.getCrq_no_contract());

			//используются для печати акта
			contractorRequest.setShp_date(form.getShp_date());
			contractorRequest.setCon_number(form.getCon_number());
			contractorRequest.setCon_date(form.getCon_date());
			contractorRequest.setSpc_number(form.getSpc_number());
			contractorRequest.setSpc_date(form.getSpc_date());
			contractorRequest.setCon_seller_id(form.getCon_seller_id());
			contractorRequest.setCon_seller(form.getCon_seller());
			contractorRequest.setCrq_reclamation_date(form.getCrq_reclamation_date());
			contractorRequest.setCrq_lintera_request_date(form.getCrq_lintera_request_date());
			contractorRequest.setCrq_defect_act(form.getCrq_defect_act());
			contractorRequest.setCrq_reclamation_act(form.getCrq_reclamation_act());
			contractorRequest.setCrq_lintera_agreement_date(form.getCrq_lintera_agreement_date());

			List<ContractorRequestStage> stages = new ArrayList<ContractorRequestStage>();
			for (int i = 0; i < form.getGridStages().getDataList().size(); i++)
			{
				ContractorRequestStage stage = (ContractorRequestStage) form.getGridStages().getDataList().get(i);
				stages.add(new ContractorRequestStage(stage));
			}
			contractorRequest.setStages(stages);
			contractorRequest.setVisitId(form.getVisitId());

			contractorRequest.setLetterScale(form.getLetterScale());
			contractorRequest.setActScale(form.getActScale());
		}
		catch (Exception e)
		{
			StrutsUtil.addError(context, e);
		}

		StoreUtil.putSession(context.getRequest(), contractorRequest);
	}

	private void getCurrentFormFromBean(IActionContext context, ContractorRequest contractorRequestIn)
	{
		ContractorRequestForm form = (ContractorRequestForm) context.getForm();
		ContractorRequest contractorRequest;
		if (null != contractorRequestIn)
		{
			contractorRequest = contractorRequestIn;
		}
		else
		{
			contractorRequest = (ContractorRequest) StoreUtil.getSession(context.getRequest(), ContractorRequest.class);
		}

		if (null != contractorRequest)
		{
			form.setIs_new_doc(contractorRequest.getIs_new_doc());

			form.setCrq_id(contractorRequest.getCrq_id());
			form.setCreateUser(contractorRequest.getCreateUser());
			form.setEditUser(contractorRequest.getEditUser());
			form.setUsr_date_create(contractorRequest.getUsr_date_create());
			form.setUsr_date_edit(contractorRequest.getUsr_date_edit());
			form.setCrq_number(contractorRequest.getCrq_number());
			form.setCrq_ticket_number(contractorRequest.getCrq_ticket_number());
			form.setCrq_comment(contractorRequest.getCrq_comment());
			form.setCrq_receive_date(contractorRequest.getCrq_receive_date());
			form.setContractor(contractorRequest.getContractor());
			form.setContactPerson(contractorRequest.getContactPerson());
			ContractorRequestType requestType = contractorRequest.getRequestType();
			form.setRequestType(requestType);
			List<ContractorRequestPrint> contractorRequestPrints = contractorRequest.getContractorRequestPrints();
			if (!StringUtil.isEmpty(requestType.getId()))
			{
				List<ContractorRequestPrint> formContractorRequestPrints = form.getContractorRequestPrints();
				if (!Boolean.parseBoolean(contractorRequest.getIs_new_doc()))
				{
					formContractorRequestPrints.clear();
				}

				if (contractorRequestPrints.isEmpty() && formContractorRequestPrints.isEmpty())
				{
					formContractorRequestPrints.add(new ContractorRequestPrint());
				}
				else
				{
					formContractorRequestPrints.addAll(contractorRequestPrints);
				}
			}
			form.setContract(contractorRequest.getContract());
			form.setContractForWork(contractorRequest.getContractForWork());
			form.setLps_id(contractorRequest.getLps_id());
			form.setCrq_equipment(contractorRequest.getCrq_equipment());
			form.setCtn_number(contractorRequest.getCtn_number());
			form.setStf_name(contractorRequest.getStf_name());
			form.setLps_serial_num(contractorRequest.getLps_serial_num());
			form.setLps_year_out(contractorRequest.getLps_year_out());
			form.setStuffCategory(contractorRequest.getStuffCategory());
			form.setProduce(contractorRequest.getProduce());
			form.setLps_enter_in_use_date(contractorRequest.getLps_enter_in_use_date());
			form.setMad_complexity(contractorRequest.getMad_complexity());
			form.setCrq_deliver(contractorRequest.getCrq_deliver());
			form.setCrq_annul(contractorRequest.getCrq_annul());
			form.setCrq_serial_num(contractorRequest.getCrq_serial_num());
			form.setCrq_year_out(contractorRequest.getCrq_year_out());
			form.setCrq_enter_in_use_date(contractorRequest.getCrq_enter_in_use_date());
			form.setCrq_operating_time(StringUtil.doubleToString(contractorRequest.getCrq_operating_time()));
			// 0 - ЗАО "Линтера", 1 - ИП "ЛинтераТехСервис", 2 - Прочие
			form.setSeller(contractorRequest.getSeller());
			form.setManager(contractorRequest.getManager());
			form.setChief(contractorRequest.getChief());
			form.setSpecialist(contractorRequest.getSpecialist());
			form.setCrq_city(contractorRequest.getCrq_city());
			form.setContractorOther(contractorRequest.getContractorOther());
			form.setCrq_no_contract(contractorRequest.getCrq_no_contract());

			//используются для печати акта
			form.setShp_date(contractorRequest.getShp_date());
			form.setCon_number(contractorRequest.getCon_number());
			form.setCon_date(contractorRequest.getCon_date());
			form.setSpc_number(contractorRequest.getSpc_number());
			form.setSpc_date(contractorRequest.getSpc_date());
			form.setCon_seller_id(contractorRequest.getCon_seller_id());
			form.setCon_seller(contractorRequest.getCon_seller());

			form.setCrq_reclamation_date(contractorRequest.getCrq_reclamation_date());
			form.setCrq_lintera_request_date(contractorRequest.getCrq_lintera_request_date());
			form.setCrq_defect_act(contractorRequest.getCrq_defect_act());
			form.setCrq_reclamation_act(contractorRequest.getCrq_reclamation_act());
			form.setCrq_lintera_agreement_date(contractorRequest.getCrq_lintera_agreement_date());

			List<ContractorRequestStage> stages = new ArrayList<ContractorRequestStage>();
			for (ContractorRequestStage stage : contractorRequest.getStages())
			{
				stages.add(new ContractorRequestStage(stage));
			}
			form.getGridStages().setDataList(stages);

			form.setLetterScale(contractorRequest.getLetterScale());
			form.setActScale(contractorRequest.getActScale());
		}
	}

	public ActionForward newContractor(IActionContext context) throws Exception
	{
		saveCurrentFormToBean(context);

		ContractorRequestForm form = (ContractorRequestForm) context.getForm();
		form.setClearTables(false);
		context.getRequest().getSession().setAttribute(Contractor.currentContractorId, form.getContractor().getId());
		return context.getMapping().findForward("newContractor");
	}

	public ActionForward newContactPerson(IActionContext context) throws Exception
	{
		saveCurrentFormToBean(context);

		ContractorRequestForm form = (ContractorRequestForm) context.getForm();
		form.setClearTables(false);
		context.getRequest().getSession().setAttribute(Contractor.currentContractorId, form.getContractor().getId());
		context.getRequest().getSession().setAttribute(ContactPerson.current_contact_person_id, form.getContactPerson().getCps_id());
		return context.getMapping().findForward("newContactPerson");
	}

	private void setPrintFalse(IActionContext context)
	{
		ContractorRequestForm form = (ContractorRequestForm) context.getForm();
		form.setNeedPrintLetterRequest(false);
		form.setNeedPrintAct(false);
		form.setNeedPrintEnumerationWork(false);
		form.setNeedPrintReclamationAct(false);
		form.setNeedPrintSellerRequest(false);
		form.setNeedPrintSellerAgreement(false);
	}

	private void setPrintStringFalse(IActionContext context)
	{
		ContractorRequestForm form = (ContractorRequestForm) context.getForm();
		form.setPrintLetterRequest("false");
		form.setPrintAct("false");
		form.setPrintEnumerationWork("false");
		form.setPrintReclamationAct("false");
		form.setPrintSellerRequest("false");
		form.setPrintSellerAgreement("false");
	}

	public ActionForward retFromProduceOperation(IActionContext context) throws Exception
	{
		ContractorRequestForm form = (ContractorRequestForm) context.getForm();
		form.setClearTables(true);
		getCurrentFormFromBean(context, null);

		String nomenclatureId = SelectFromGridAction.getSelectedId(context);
		if (!StringUtil.isEmpty(nomenclatureId))
		{
			form.setProduce(ProduceDAO.loadProduce(new Integer(nomenclatureId)));

			//обнуляем зависимые поля
			form.setCrq_equipment(form.getProduce().getFullName());
			DboProduce produce = (DboProduce) HibernateUtil.associateWithCurentSession(form.getProduce());
			DboCatalogNumber catalogNumber = produce.getCatalogNumbers().get(new Integer(form.getStuffCategory().getId()));
			if (null != catalogNumber)
			{
				form.setCtn_number(catalogNumber.getNumber());
			}
			else
			{
				form.setCtn_number("");
			}
			form.setStf_name(form.getStuffCategory().getName());
			if (null != catalogNumber && null != catalogNumber.getMontageAdjustment())
			{
				form.setMad_complexity(catalogNumber.getMontageAdjustment().getComplexity());
			}
			else
			{
				form.setMad_complexity("");
			}
		}

		SelectFromGridAction.setSelectedId(context, null);

		setPrintFalse(context);
		return show(context);
	}

	public ActionForward retFromContractor(IActionContext context) throws Exception
	{
		ContractorRequestForm form = (ContractorRequestForm) context.getForm();
		getCurrentFormFromBean(context, null);
		form.setClearTables(true);

		String contractorId = (String) context.getRequest().getSession().getAttribute(Contractor.currentContractorId);
		if (!StringUtil.isEmpty(contractorId))
		{
			form.setContractor(ContractorDAO.load(context, contractorId));
		}
		context.getRequest().getSession().setAttribute(Contractor.currentContractorId, null);

		String contactPersonId = (String) context.getRequest().getSession().getAttribute(ContactPerson.current_contact_person_id);
		if (!StringUtil.isEmpty(contactPersonId))
		{
			form.setContactPerson(ContactPersonDAO.load(context, contactPersonId));
		}
		context.getRequest().getSession().setAttribute(ContactPerson.current_contact_person_id, null);

		setPrintFalse(context);
		return show(context);
	}

	public ActionForward selectProduce(IActionContext context) throws Exception
	{
		ContractorRequestForm form = (ContractorRequestForm) context.getForm();
		form.setClearTables(false);
		saveCurrentFormToBean(context);
		return context.getMapping().findForward("selectProduce");
	}

	public ActionForward reload(IActionContext context) throws Exception
	{
		saveCurrentFormToBean(context);
		getCurrentFormFromBean(context, null);

		setPrintFalse(context);
		return show(context);
	}

	public ActionForward reloadNoContract(IActionContext context) throws Exception
	{
		saveCurrentFormToBean(context);
		getCurrentFormFromBean(context, null);

		ContractorRequestForm form = (ContractorRequestForm) context.getForm();
		form.setCrq_equipment("");
		form.setCtn_number("");
		form.setStf_name("");
		form.setLps_serial_num("");
		form.setLps_year_out("");
		form.setLps_enter_in_use_date("");
		form.setMad_complexity("");
		setPrintFalse(context);
		form.setContract(new Contract());
		form.setProduce(new DboProduce());
		if (!StringUtil.isEmpty(form.getCrq_no_contract()))
		{
			form.setContractReadOnly(true);
			form.getSeller().setId("2"); //если отмечен этот флаг, то считаем что Продавец = прочее
		}
		else
		{
			form.setContractReadOnly(false);
		}
		return show(context);
	}

	public ActionForward retFromAttach(IActionContext context) throws Exception
	{
		getCurrentFormFromBean(context, null);

		ContractorRequestForm form = (ContractorRequestForm) context.getForm();
		form.setClearTables(true);

		return show(context);
	}

	private boolean saveCommon(IActionContext context) throws Exception
	{
		ContractorRequestForm form = (ContractorRequestForm) context.getForm();
		saveCurrentFormToBean(context);

		ContractorRequest contractorRequest = (ContractorRequest) StoreUtil.getSession(context.getRequest(), ContractorRequest.class);
		contractorRequest.getContractorRequestPrints().clear();
		for (ContractorRequestPrint contractorRequestPrint : form.getContractorRequestPrints())
		{
			contractorRequest.getContractorRequestPrints().add(new ContractorRequestPrint(contractorRequestPrint));
		}

		if (StringUtil.isEmpty(form.getCrq_id()))
		{
			//form crq_number
			String date = StringUtil.date2appDateString(StringUtil.getCurrentDateTime());
			String year = date.substring(8);
			String month = date.substring(3, 5);
			form.setGen_num(CommonDAO.GetNumber(context, "get-num_contractor_request"));
			contractorRequest.setCrq_number("BYM" + year + month + "/" + StringUtil.padWithLeadingZeros(form.getGen_num(), 4));
			form.setCrq_number(contractorRequest.getCrq_number());

			ContractorRequestDAO.insert(context, contractorRequest);
			form.setCrq_id(contractorRequest.getCrq_id());
		}
		else
		{
			ContractorRequestDAO.save(context, contractorRequest);
		}

		Session hibSession = HibernateUtil.getSessionFactory().getCurrentSession();
		hibSession.beginTransaction();
		form.getAttachmentService().commit(Integer.parseInt(contractorRequest.getCrq_id()));
		hibSession.getTransaction().commit();

		return true;
	}

	public ActionForward printLetterRequest(IActionContext context) throws Exception
	{
		ContractorRequestForm form = (ContractorRequestForm) context.getForm();
		setPrintFalse(context); //раньше следующей строки
		form.setNeedPrintLetterRequest(true);

		return show(context);
	}

	public ActionForward printPNPAct(IActionContext context) throws Exception
	{
		ContractorRequestForm form = (ContractorRequestForm) context.getForm();
		String errMsg = "";
		if (StringUtil.isEmpty(form.getSeller().getId()))
		{
			errMsg = StrutsUtil.addDelimiter(errMsg);
			errMsg += StrutsUtil.getMessage(context, "error.contractor_request.seller_empty");
		}
		if ("2".equals(form.getSeller().getId()) && StringUtil.isEmpty(form.getContractorOther().getId()))
		{
			errMsg = StrutsUtil.addDelimiter(errMsg);
			errMsg += StrutsUtil.getMessage(context, "error.contractor_request.seller3_empty");
		}
		if (!StringUtil.isEmpty(errMsg))
		{
			StrutsUtil.addError(context, "errors.msg", errMsg, null);
			return show(context);
		}

		if ("true".equals(form.getIs_new_doc()))
		{
			boolean retFromSave = saveCommon(context);

			if (!retFromSave)
			{
				return show(context);
			}
			form.setIs_new_doc("");

			ContractorRequest contractorRequest = ContractorRequestDAO.load(context, form.getCrq_id());
			contractorRequest.setNumbers();
			StoreUtil.putSession(context.getRequest(), contractorRequest);
			getCurrentFormFromBean(context, contractorRequest);
		}

		setPrintFalse(context); //раньше следующей строки
		form.setNeedPrintAct(true);
		saveCurrentFormToBean(context);

		return show(context);
	}

	private boolean commonSavePrint(IActionContext context) throws Exception
	{
		ContractorRequestForm form = (ContractorRequestForm) context.getForm();
		if ("true".equals(form.getIs_new_doc()))
		{
			boolean retFromSave = saveCommon(context);

			if (!retFromSave)
			{
				return false;
			}
			form.setIs_new_doc("");

			ContractorRequest contractorRequest = ContractorRequestDAO.load(context, form.getCrq_id());
			contractorRequest.setNumbers();
			StoreUtil.putSession(context.getRequest(), contractorRequest);
			getCurrentFormFromBean(context, contractorRequest);
		}

		return true;
	}

	public ActionForward printGSAct(IActionContext context) throws Exception
	{
		ContractorRequestForm form = (ContractorRequestForm) context.getForm();

		if (!commonSavePrint(context))
		{
			return show(context);
		}

		if (form.isShowService())
		{
			String errMsg = "";
			if (StringUtil.isEmpty(form.getContractForWork().getCon_id()))
			{
				errMsg = StrutsUtil.addDelimiter(errMsg);
				errMsg += StrutsUtil.getMessage(context, "error.contractor_request.contractForWorkEmpty");
			}
			if (!StringUtil.isEmpty(errMsg))
			{
				StrutsUtil.addError(context, "errors.msg", errMsg, null);
				return show(context);
			}
		}

		setPrintFalse(context); //раньше следующей строки
		form.setNeedPrintAct(true);
		saveCurrentFormToBean(context);

		return show(context);
	}

	public ActionForward printEnumerationWork(IActionContext context) throws Exception
	{
		ContractorRequestForm form = (ContractorRequestForm) context.getForm();

		if (!commonSavePrint(context))
		{
			return show(context);
		}

		setPrintFalse(context); //раньше следующей строки
		form.setNeedPrintEnumerationWork(true);
		saveCurrentFormToBean(context);

		return show(context);
	}

	public ActionForward printReclamationAct(IActionContext context) throws Exception
	{
		ContractorRequestForm form = (ContractorRequestForm) context.getForm();

		if (!commonSavePrint(context))
		{
			return show(context);
		}

		setPrintFalse(context); //раньше следующей строки
		form.setNeedPrintReclamationAct(true);
		saveCurrentFormToBean(context);

		return show(context);
	}

	public ActionForward printSellerRequest(IActionContext context) throws Exception
	{
		ContractorRequestForm form = (ContractorRequestForm) context.getForm();

		if (!commonSavePrint(context))
		{
			return show(context);
		}

		setPrintFalse(context); //раньше следующей строки
		form.setNeedPrintSellerRequest(true);
		saveCurrentFormToBean(context);

		return show(context);
	}

	public ActionForward printPNPTimeSheet(IActionContext context) throws Exception
	{
		ContractorRequestForm form = (ContractorRequestForm) context.getForm();

		if (!commonSavePrint(context))
		{
			return show(context);
		}

		setPrintFalse(context);
		form.setNeedPrintPNPTimeSheet(true);
		saveCurrentFormToBean(context);

		return show(context);
	}

	public ActionForward printSellerAgreement(IActionContext context) throws Exception
	{
		ContractorRequestForm form = (ContractorRequestForm) context.getForm();

		if (!commonSavePrint(context))
		{
			return show(context);
		}

		setPrintFalse(context); //раньше следующей строки
		form.setNeedPrintSellerAgreement(true);
		saveCurrentFormToBean(context);

		return show(context);
	}

	public ActionForward show(IActionContext context) throws Exception
	{
		ContractorRequestForm form = (ContractorRequestForm) context.getForm();
		form.getRequestType().setName(ContractorRequest.getRequestTypeName(form.getRequestType().getId(), false));
		saveCurrentFormToBean(context);

		if (!StringUtil.isEmpty(form.getCrq_deliver()))
		{
			form.setFormReadOnly(true);
		}
		else
		{
			form.setFormReadOnly(false);
		}

		User currentUser = UserUtil.getCurrentUser(context.getRequest());
		if (currentUser.isOnlyStaffOfService() && !currentUser.isChiefDepartment())
		{
			form.setFormReadOnly(true);
			form.setAddAttachReadOnly(true);
		}
		else
		{
			form.setAddAttachReadOnly(false);
		}
		if (currentUser.isAdmin() || (currentUser.isEconomist() && !form.isFormReadOnly()))
		{
			form.setDeliverReadOnly(false);
		}
		else
		{
			form.setDeliverReadOnly(true);
		}
		if (currentUser.isAdmin())
		{
			form.setShowForAdmin(true);
		}
		else
		{
			form.setShowForAdmin(false);
		}
		if (currentUser.isOnlyManager())
		{
			form.setShowForManager(true);
		}
		else
		{
			form.setShowForManager(false);
		}

		if (form.isNeedPrintLetterRequest())
		{
			form.setPrintLetterRequest("true");
		}
		else
		{
			form.setPrintLetterRequest("false");
		}
		form.setNeedPrintLetterRequest(false);

		if (form.isNeedPrintPNPTimeSheet())
		{
			form.setPrintPNPTimeSheet("true");
		}
		else
		{
			form.setPrintPNPTimeSheet("false");
		}
		form.setNeedPrintPNPTimeSheet(false);

		if (form.isNeedPrintAct())
		{
			form.setPrintAct("true");
		}
		else
		{
			form.setPrintAct("false");
		}
		form.setNeedPrintAct(false);

		if (form.isNeedPrintEnumerationWork())
		{
			form.setPrintEnumerationWork("true");
		}
		else
		{
			form.setPrintEnumerationWork("false");
		}
		form.setNeedPrintEnumerationWork(false);
		if (form.isNeedPrintReclamationAct())
		{
			form.setPrintReclamationAct("true");
		}
		else
		{
			form.setPrintReclamationAct("false");
		}
		form.setNeedPrintReclamationAct(false);
		if (form.isNeedPrintSellerRequest())
		{
			form.setPrintSellerRequest("true");
		}
		else
		{
			form.setPrintSellerRequest("false");
		}
		form.setNeedPrintSellerRequest(false);
		if (form.isNeedPrintSellerAgreement())
		{
			form.setPrintSellerAgreement("true");
		}
		else
		{
			form.setPrintSellerAgreement("false");
		}
		form.setNeedPrintSellerAgreement(false);

		getCurrentFormFromBean(context, null);

		form.getAttachmentsGrid().setDataList(form.getAttachmentService().list());

		for (ContractorRequestPrint contractorRequestPrint : form.getContractorRequestPrints())
		{
			contractorRequestPrint.setCrp_no_defect_act_checkbox(contractorRequestPrint.getCrp_no_defect_act());
			contractorRequestPrint.setCrp_no_reclamation_act_checkbox(contractorRequestPrint.getCrp_no_reclamation_act());
			contractorRequestPrint.setCrp_deliver_checkbox(contractorRequestPrint.getCrp_deliver());
		}

		return context.getMapping().findForward("form");
	}

	public ActionForward input(IActionContext context) throws Exception
	{
		User currentUser = UserUtil.getCurrentUser(context.getRequest());
		if (currentUser.isOnlyStaffOfService() && !currentUser.isChiefDepartment())
		{
			StrutsUtil.addError(context, "error.access-denied", null);
			return context.getMapping().findForward("back");
		}

		ContractorRequestForm form = (ContractorRequestForm) context.getForm();
		form.setClearTables(true);

		ContractorRequest contractorRequest = new ContractorRequest();
		contractorRequest.getStages().add(new ContractorRequestStage("1",
						StrutsUtil.getMessage(context, "ContractorRequestStages.nameLine1"),
						StrutsUtil.getMessage(context, "ContractorRequestStages.commentDefaultLine1"),
						"1"
		));
		contractorRequest.getStages().add(new ContractorRequestStage("2",
						StrutsUtil.getMessage(context, "ContractorRequestStages.nameLine2"),
						StrutsUtil.getMessage(context, "ContractorRequestStages.commentDefaultLine2"),
						"1"
		));
		contractorRequest.getStages().add(new ContractorRequestStage("3",
						StrutsUtil.getMessage(context, "ContractorRequestStages.nameLine3"),
						StrutsUtil.getMessage(context, "ContractorRequestStages.commentDefaultLine3"),
						"1"
		));
		contractorRequest.getStages().add(new ContractorRequestStage("4",
						StrutsUtil.getMessage(context, "ContractorRequestStages.nameLine4"),
						StrutsUtil.getMessage(context, "ContractorRequestStages.commentDefaultLine4"),
						"1"
		));
		contractorRequest.getStages().add(new ContractorRequestStage("5",
						StrutsUtil.getMessage(context, "ContractorRequestStages.nameLine5"),
						StrutsUtil.getMessage(context, "ContractorRequestStages.commentDefaultLine5"),
						"1"
		));
		contractorRequest.getStages().add(new ContractorRequestStage("6",
						StrutsUtil.getMessage(context, "ContractorRequestStages.nameLine6"),
						StrutsUtil.getMessage(context, "ContractorRequestStages.commentDefaultLine6"),
						"1"
		));

		StoreUtil.putSession(context.getRequest(), contractorRequest);
		//обнуляем поля формы
		getCurrentFormFromBean(context, contractorRequest);

		form.setCrq_no_contract("");
		form.setIs_new_doc("true");
		form.setFormReadOnly(false);
		String department;
		department = StrutsUtil.getMessage(context, "ContractorRequest.crq_chief_dep_default");
		form.setChief(UserDAO.loadUserByDepartNameChief(context, DepartmentDAO.loadByName(context, "", department)));

		setPrintStringFalse(context);

		form.setAttachmentService(
						DeferredAttachmentService.create(context.getRequest().getSession(),
										referencedTable, context.getMapping().findForward("backFromAttach"), null));

		form.getContractorRequestPrints().clear();
		return show(context);
	}

	public ActionForward clone(IActionContext context) throws Exception
	{
		User currentUser = UserUtil.getCurrentUser(context.getRequest());
		if (currentUser.isOnlyStaffOfService() && !currentUser.isChiefDepartment())
		{
			StrutsUtil.addError(context, "error.access-denied", null);
			return context.getMapping().findForward("back");
		}

		ContractorRequestForm form = (ContractorRequestForm) context.getForm();
		form.setClearTables(true);

		ContractorRequest contractorRequest = ContractorRequestDAO.load(context, form.getCrq_id());
		contractorRequest.setListIdsToNull();

    /*
    * При копировании копировать значения полей:
    *   Дата получения заявки
    *   Контрагент
    *   Контактное лицо
    *   Вид работ
    *   Договор в списке отсутствует
    *   Договор
    *   Оборудование
    *  Остальное затираем
    * */
		contractorRequest.setCrq_id("");
		contractorRequest.setCrq_number("");
		contractorRequest.setCrq_deliver("");
		contractorRequest.setManager(new User());
		contractorRequest.setChief(new User());
		contractorRequest.setSpecialist(new User());
		contractorRequest.setCrq_city("");
		contractorRequest.setCrq_annul("");

		contractorRequest.setNumbers();
		StoreUtil.putSession(context.getRequest(), contractorRequest);
		getCurrentFormFromBean(context, contractorRequest);

		form.setIs_new_doc("true");
		setPrintStringFalse(context);

		form.setAttachmentService(
						DeferredAttachmentService.create(context.getRequest().getSession(),
										referencedTable, context.getMapping().findForward("backFromAttach"), null));

		form.getContractorRequestPrints().clear();
		form.getContractorRequestPrints().add(new ContractorRequestPrint());
		form.setSeller(contractorRequest.getContract().getSeller());

		return show(context);
	}

	public ActionForward edit(IActionContext context) throws Exception
	{
		ContractorRequestForm form = (ContractorRequestForm) context.getForm();
		form.setClearTables(true);

		ContractorRequest contractorRequest = ContractorRequestDAO.load(context, form.getCrq_id());
		contractorRequest.setNumbers();
		StoreUtil.putSession(context.getRequest(), contractorRequest);
		getCurrentFormFromBean(context, contractorRequest);

		form.setCrq_equipment(contractorRequest.getCrq_equipment());
		form.setCtn_number(contractorRequest.getCtn_number());
		form.setStf_name(contractorRequest.getStf_name());
		form.setLps_serial_num(contractorRequest.getLps_serial_num());
		form.setLps_year_out(contractorRequest.getLps_year_out());
		form.getOrders().setDataList(contractorRequest.getOrders());

		if (null != contractorRequest.getProduce().getId())
		{
			form.setProduce(ProduceDAO.loadProduce(contractorRequest.getProduce().getId()));
		}

		setPrintStringFalse(context);

		Session hibSession = HibernateUtil.getSessionFactory().getCurrentSession();
		hibSession.beginTransaction();
		form.setAttachmentService(
						DeferredAttachmentService.create(context.getRequest().getSession(),
										referencedTable, Integer.parseInt(form.getCrq_id()),
										context.getMapping().findForward("backFromAttach"), null));
		hibSession.getTransaction().commit();

		form.getContractorRequestPrints().clear();
		for (ContractorRequestPrint contractorRequestPrint : contractorRequest.getContractorRequestPrints())
		{
			form.getContractorRequestPrints().add(new ContractorRequestPrint(contractorRequestPrint));
		}
		form.setSeller(contractorRequest.getContract().getSeller());

		return show(context);
	}

	public ActionForward back(IActionContext context) throws Exception
	{
		ContractorRequestForm form = (ContractorRequestForm) context.getForm();
		form.getAttachmentService().rollback();
		form.setAttachmentService(null);

		DeferredAttachmentService.removeLast(context.getRequest().getSession());
		return context.getMapping().findForward("back");
	}

	public ActionForward process(IActionContext context) throws Exception
	{
		boolean retFromSave = saveCommon(context);

		if (retFromSave)
		{
			DeferredAttachmentService.removeLast(context.getRequest().getSession());
			return context.getMapping().findForward("back");
		}
		else
		{
			setPrintFalse(context); //раньше следующей строки
			return show(context);
		}
	}

	public ActionForward saveComment(IActionContext context) throws Exception
	{
		ContractorRequestForm form = (ContractorRequestForm) context.getForm();
		ContractorRequest contractorRequest = (ContractorRequest) StoreUtil.getSession(context.getRequest(), ContractorRequest.class);
		contractorRequest.updateComment(form.getNumber(), form.getComment());
		getCurrentFormFromBean(context, contractorRequest);

		return context.getMapping().findForward("ajax");
	}

	public ActionForward deferredAttach(IActionContext context) throws Exception
	{
		saveCurrentFormToBean(context);
		ContractorRequestForm form = (ContractorRequestForm) context.getForm();
		form.setClearTables(false);

		DeferredAttachmentService.set(context.getRequest().getSession(), form.getAttachmentService());
		return context.getMapping().findForward("deferredAttach");
	}

	public ActionForward deleteAttachment(IActionContext context) throws Exception
	{
		saveCurrentFormToBean(context);
		ContractorRequestForm form = (ContractorRequestForm) context.getForm();
		DeferredAttachmentService.DeferredAttachment attachment = form.getAttachmentService().find(Integer.parseInt(form.getAttachmentId()));
		form.getAttachmentService().delete(attachment);

		return show(context);
	}

	public ActionForward downloadAttachment(IActionContext context) throws Exception
	{
		saveCurrentFormToBean(context);
		ContractorRequestForm form = (ContractorRequestForm) context.getForm();
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

	public ActionForward ajaxContractorRequestPrintGrid(IActionContext context) throws Exception
	{
		ContractorRequestForm form = (ContractorRequestForm) context.getForm();
		List<ContractorRequestPrint> contractorRequestPrints = form.getContractorRequestPrints();
		for (ContractorRequestPrint contractorRequestPrint : contractorRequestPrints)
		{
			contractorRequestPrint.setCrp_no_defect_act_checkbox(contractorRequestPrint.getCrp_no_defect_act());
			contractorRequestPrint.setCrp_no_reclamation_act_checkbox(contractorRequestPrint.getCrp_no_reclamation_act());
			contractorRequestPrint.setCrp_deliver_checkbox(contractorRequestPrint.getCrp_deliver());
		}

		return context.getMapping().findForward("ajaxContractorRequestPrintGrid");
	}

	public ActionForward ajaxAddToGrid(IActionContext context) throws Exception
	{
		ContractorRequestForm form = (ContractorRequestForm) context.getForm();
		form.getContractorRequestPrints().add(new ContractorRequestPrint());

		return ajaxContractorRequestPrintGrid(context);
	}

	public ActionForward ajaxRemoveFromGrid(IActionContext context) throws Exception
	{
		ContractorRequestForm form = (ContractorRequestForm) context.getForm();

		int id = Integer.parseInt(context.getRequest().getParameter("id"));
		form.getContractorRequestPrints().remove(id);

		return ajaxContractorRequestPrintGrid(context);
	}

	public ActionForward ajaxChangeRequestTypeId(IActionContext context) throws Exception
	{
		ContractorRequestForm form = (ContractorRequestForm) context.getForm();

		String requestTypeId = context.getRequest().getParameter("requestTypeId");
		if (!StringUtil.isEmpty(requestTypeId))
		{
			form.getRequestType().setId(requestTypeId);
		}

		return ajaxContractorRequestPrintGrid(context);
	}

	public ActionForward ajaxChangeCrqSeller(IActionContext context) throws Exception
	{
		ContractorRequestForm form = (ContractorRequestForm) context.getForm();

		String sellerId = context.getRequest().getParameter("sellerId");
		if (!StringUtil.isEmpty(sellerId))
		{
			form.setSeller(SellerDAO.load(context, sellerId));
		}

		return ajaxContractorRequestPrintGrid(context);
	}

	public ActionForward ajaxLinkedOrdersGrid(IActionContext context) throws Exception
	{
		return context.getMapping().findForward("ajaxLinkedOrdersGrid");
	}

	public ActionForward selectOrder(IActionContext context) throws Exception
	{
		saveCurrentFormToBean(context);

		return context.getMapping().findForward("selectOrder");
	}

	public ActionForward returnFromSelectOrder(IActionContext context) throws Exception
	{
		boolean haveOrder = false;
		String ordId = SelectFromGridAction.getSelectedId(context);
		ContractorRequest contractorRequest = (ContractorRequest) StoreUtil.getSession(context.getRequest(), ContractorRequest.class);
		if (!StringUtil.isEmpty(ordId))
		{
			Order order = OrderDAO.load(context, ordId, true, false);
			if (!contractorRequest.addOrder(order))
			{
				haveOrder = true;
			}
		}

		if (haveOrder)
		{
			StrutsUtil.addError(context, "error.contractor_request.haveOrder", null);
		}

		getCurrentFormFromBean(context, contractorRequest);
		return show(context);
	}

	public ActionForward ajaxRemoveFromOrderGrid(IActionContext context) throws Exception
	{
		ContractorRequest contractorRequest = (ContractorRequest) StoreUtil.getSession(context.getRequest(), ContractorRequest.class);
		String ordId = context.getRequest().getParameter("ordId");
		if (!StringUtil.isEmpty(ordId))
		{
			contractorRequest.deleteOrder(ordId);
		}

		ContractorRequestForm form = (ContractorRequestForm) context.getForm();
		form.getOrders().setDataList(contractorRequest.getOrders());
		return ajaxLinkedOrdersGrid(context);
	}
}