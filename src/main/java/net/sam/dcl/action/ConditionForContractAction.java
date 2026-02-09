package net.sam.dcl.action;

import net.sam.dcl.beans.*;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.controller.IDispatchable;
import net.sam.dcl.controller.actions.DBTransactionAction;
import net.sam.dcl.controller.actions.SelectFromGridAction;
import net.sam.dcl.dao.*;
import net.sam.dcl.dbo.DboProduce;
import net.sam.dcl.form.ConditionForContractForm;
import net.sam.dcl.service.AttachmentException;
import net.sam.dcl.service.AttachmentsService;
import net.sam.dcl.service.DeferredAttachmentService;
import net.sam.dcl.taglib.table.IShowChecker;
import net.sam.dcl.taglib.table.IStyleClassChecker;
import net.sam.dcl.taglib.table.ShowCheckerContext;
import net.sam.dcl.taglib.table.StyleClassCheckerContext;
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
public class ConditionForContractAction extends DBTransactionAction implements IDispatchable
{
	protected static Log log = LogFactory.getLog(ConditionForContractAction.class);
	final static String referencedTable = "DCL_COND_FOR_CONTRACT";

	private void loadDataDependOnContract(String conId, IActionContext context, ConditionForContract conditionForContract) throws Exception
	{
		conditionForContract.setContract(ContractDAO.load(context, conId, false));
		conditionForContract.setCurrency(conditionForContract.getContract().getCurrency());
		conditionForContract.setSpc_numbers(SpecificationDAO.loadSpecificationsNumbersForContract(context, conditionForContract.getContract()));
		conditionForContract.setCfc_spc_numbers(conditionForContract.getCfcSpecificationsNumbersFormatted(ConditionForContractDAO.loadSpecificationsNumbersForContract(context, conditionForContract)));
	}

	private void saveCurrentFormToBean(IActionContext context)
	{
		ConditionForContractForm form = (ConditionForContractForm) context.getForm();
		ConditionForContract conditionForContract = (ConditionForContract) StoreUtil.getSession(context.getRequest(), ConditionForContract.class);

		conditionForContract.setIs_new_doc(form.getIs_new_doc());
		conditionForContract.setCfc_id(form.getCfc_id());
		try
		{
			if (!StringUtil.isEmpty(form.getCreateUser().getUsr_id()))
			{
				conditionForContract.setCreateUser(UserDAO.load(context, form.getCreateUser().getUsr_id()));
			}
			if (!StringUtil.isEmpty(form.getEditUser().getUsr_id()))
			{
				conditionForContract.setEditUser(UserDAO.load(context, form.getEditUser().getUsr_id()));
			}
			if (!StringUtil.isEmpty(form.getPlaceUser().getUsr_id()))
			{
				conditionForContract.setPlaceUser(UserDAO.load(context, form.getPlaceUser().getUsr_id()));
			}
			if (!StringUtil.isEmpty(form.getExecuteUser().getUsr_id()))
			{
				conditionForContract.setExecuteUser(UserDAO.load(context, form.getExecuteUser().getUsr_id()));
			}
			if (!StringUtil.isEmpty(form.getAnnulUser().getUsr_id()))
			{
				conditionForContract.setAnnulUser(UserDAO.load(context, form.getAnnulUser().getUsr_id()));
			}

			conditionForContract.setUsr_date_create(form.getUsr_date_create());
			conditionForContract.setUsr_date_edit(form.getUsr_date_edit());
			conditionForContract.setUsr_date_place(form.getUsr_date_edit());
			conditionForContract.setUsr_date_execute(form.getUsr_date_edit());
			conditionForContract.setUsr_date_annul(form.getUsr_date_annul());

			conditionForContract.setCfc_place(form.getCfc_place());
			conditionForContract.setCfc_execute(form.getCfc_execute());
			conditionForContract.setCfc_check_price(form.getCfc_check_price());
			conditionForContract.setCfc_check_price_date(form.getCfc_check_price_date());
			conditionForContract.setUsr_id_check_price(form.getUsr_id_check_price());

			if (!StringUtil.isEmpty(form.getSeller().getId()))
			{
				conditionForContract.setSeller(SellerDAO.load(context, form.getSeller().getId()));
			}

			if (!StringUtil.isEmpty(form.getContractor().getId()))
			{
				conditionForContract.setContractor(ContractorDAO.load(context, form.getContractor().getId()));
			}

			// 0 - новый договор, 1 - спецификация к уже имеющемуся договору
			if (!StringUtil.isEmpty(form.getCfc_doc_type1()))
			{
				conditionForContract.setCfc_doc_type("0");
				conditionForContract.setContract(new Contract());
				if (!StringUtil.isEmpty(form.getCurrency().getId()))
				{
					conditionForContract.setCurrency(CurrencyDAO.load(context, form.getCurrency().getId()));
				}
			}
			if (!StringUtil.isEmpty(form.getCfc_doc_type2()))
			{
				conditionForContract.setCfc_doc_type("1");
				if (!StringUtil.isEmpty(form.getContract().getCon_id()))
				{
					loadDataDependOnContract(form.getContract().getCon_id(), context, conditionForContract);
				}
				else
				{
					conditionForContract.setContract(new Contract());
					conditionForContract.setCurrency(new Currency());
					conditionForContract.setSpc_numbers("");
					conditionForContract.setCfc_spc_numbers("");
				}
			}

			if (!StringUtil.isEmpty(form.getContactPersonSign().getCps_id()))
			{
				conditionForContract.setContactPersonSign(ContactPersonDAO.load(context, form.getContactPersonSign().getCps_id()));
			}
			if (!StringUtil.isEmpty(form.getContactPerson().getCps_id()))
			{
				conditionForContract.setContactPerson(ContactPersonDAO.load(context, form.getContactPerson().getCps_id()));
			}
			conditionForContract.setCfc_con_number_txt(form.getCfc_con_number_txt());
			conditionForContract.setCfc_con_date(form.getCfc_con_date());
			conditionForContract.setCfc_spc_number_txt(form.getCfc_spc_number_txt());
			conditionForContract.setCfc_spc_date(form.getCfc_spc_date());
			conditionForContract.setCfc_pay_cond(form.getCfc_pay_cond());
			conditionForContract.setCfc_custom_point(form.getCfc_custom_point());
			conditionForContract.setCfc_delivery_cond(form.getCfc_delivery_cond());
			conditionForContract.setCfc_guarantee_cond(form.getCfc_guarantee_cond());
			conditionForContract.setCfc_montage_cond(form.getCfc_montage_cond());
			conditionForContract.setCfc_date_con_to(form.getCfc_date_con_to());
			conditionForContract.setCfc_count_delivery(form.getCfc_count_delivery());
			// 0 - Количество поставок по Контракту: =1, 1 - >1
			if (!StringUtil.isEmpty(form.getCfc_delivery_count1()))
			{
				conditionForContract.setCfc_delivery_count("0");
			}
			if (!StringUtil.isEmpty(form.getCfc_delivery_count2()))
			{
				conditionForContract.setCfc_delivery_count("1");
			}

			if (!StringUtil.isEmpty(form.getPurchasePurpose().getId()))
			{
				conditionForContract.setPurchasePurpose(PurchasePurposeDAO.load(context, form.getPurchasePurpose().getId()));
			}
			conditionForContract.setCfc_need_invoice(form.getCfc_need_invoice());
			conditionForContract.setCfc_comment(form.getCfc_comment());
			conditionForContract.setCfc_annul(form.getCfc_annul());
			conditionForContract.setCfc_annul_date(form.getCfc_annul_date());

			conditionForContract.setPrintScale(form.getPrintScale());
		}
		catch (Exception e)
		{
			StrutsUtil.addError(context, e);
		}

		StoreUtil.putSession(context.getRequest(), conditionForContract);
	}

	private void getCurrentFormFromBean(IActionContext context)
	{
		ConditionForContractForm form = (ConditionForContractForm) context.getForm();
		ConditionForContract conditionForContract = (ConditionForContract) StoreUtil.getSession(context.getRequest(), ConditionForContract.class);

		if (null != conditionForContract)
		{
			form.setIs_new_doc(conditionForContract.getIs_new_doc());

			form.setCfc_id(conditionForContract.getCfc_id());
			form.setCreateUser(conditionForContract.getCreateUser());
			form.setEditUser(conditionForContract.getEditUser());
			form.setPlaceUser(conditionForContract.getPlaceUser());
			form.setExecuteUser(conditionForContract.getExecuteUser());
			form.setAnnulUser(conditionForContract.getAnnulUser());
			form.setUsr_date_create(conditionForContract.getUsr_date_create());
			form.setUsr_date_edit(conditionForContract.getUsr_date_edit());
			form.setUsr_date_place(conditionForContract.getUsr_date_place());
			form.setUsr_date_execute(conditionForContract.getUsr_date_execute());
			form.setUsr_date_annul(conditionForContract.getUsr_date_annul());
			form.setCfc_place(conditionForContract.getCfc_place());
			form.setCfc_execute(conditionForContract.getCfc_execute());
			form.setCfc_check_price(conditionForContract.getCfc_check_price());
			form.setCfc_check_price_date(conditionForContract.getCfc_check_price_date());
			form.setUsr_id_check_price(conditionForContract.getUsr_id_check_price());

			form.setSeller(conditionForContract.getSeller());

			// 0 - новый договор, 1 - спецификация к уже имеющемуся договору
			if ("0".equals(conditionForContract.getCfc_doc_type()))
			{
				form.setCfc_doc_type1("1");
				form.setCfc_doc_type2("");
			}
			if ("1".equals(conditionForContract.getCfc_doc_type()))
			{
				form.setCfc_doc_type1("");
				form.setCfc_doc_type2("1");
			}

			form.setContractor(conditionForContract.getContractor());
			form.setContract(conditionForContract.getContract());
			form.setCurrency(conditionForContract.getCurrency());
			form.setContactPersonSign(conditionForContract.getContactPersonSign());
			form.setContactPerson(conditionForContract.getContactPerson());
			form.setCfc_con_number_txt(conditionForContract.getCfc_con_number_txt());
			form.setCfc_con_date(conditionForContract.getCfc_con_date());
			form.setSpc_numbers(conditionForContract.getSpc_numbers());
			form.setCfc_spc_numbers(conditionForContract.getCfc_spc_numbers());
			form.setCfc_spc_number_txt(conditionForContract.getCfc_spc_number_txt());
			form.setCfc_spc_date(conditionForContract.getCfc_spc_date());
			form.setCfc_pay_cond(conditionForContract.getCfc_pay_cond());
			form.setCfc_custom_point(conditionForContract.getCfc_custom_point());
			form.setCfc_delivery_cond(conditionForContract.getCfc_delivery_cond());
			form.setCfc_guarantee_cond(conditionForContract.getCfc_guarantee_cond());
			form.setCfc_montage_cond(conditionForContract.getCfc_montage_cond());
			form.setCfc_date_con_to(conditionForContract.getCfc_date_con_to());
			form.setCfc_count_delivery(conditionForContract.getCfc_count_delivery());

			// 0 - Количество поставок по Контракту: =1, 1 - >1
			if ("0".equals(conditionForContract.getCfc_delivery_count()))
			{
				form.setCfc_delivery_count1("1");
				form.setCfc_delivery_count2("");
			}
			if ("1".equals(conditionForContract.getCfc_delivery_count()))
			{
				form.setCfc_delivery_count1("");
				form.setCfc_delivery_count2("1");
			}

			form.setPurchasePurpose(conditionForContract.getPurchasePurpose());
			form.setCfc_need_invoice(conditionForContract.getCfc_need_invoice());
			form.setCfc_comment(conditionForContract.getCfc_comment());
			form.setCfc_annul(conditionForContract.getCfc_annul());
			form.setCfc_annul_date(conditionForContract.getCfc_annul_date());

			form.getGrid().setDataList(conditionForContract.getProduces());

			form.setPrintScale(conditionForContract.getPrintScale());
		}
	}

	public ActionForward newContractor(IActionContext context) throws Exception
	{
		saveCurrentFormToBean(context);

		ConditionForContractForm form = (ConditionForContractForm) context.getForm();
		context.getRequest().getSession().setAttribute(Contractor.currentContractorId, form.getContractor().getId());
		return context.getMapping().findForward("newContractor");
	}

	public ActionForward editContractor(IActionContext context) throws Exception
	{
		saveCurrentFormToBean(context);

		ConditionForContractForm form = (ConditionForContractForm) context.getForm();
		context.getRequest().getSession().setAttribute(Contractor.currentContractorId, form.getContractor().getId());
		return context.getMapping().findForward("editContractor");
	}

	public ActionForward newContactPersonSign(IActionContext context) throws Exception
	{
		saveCurrentFormToBean(context);

		ConditionForContractForm form = (ConditionForContractForm) context.getForm();
		form.setAddSignPerson(true);
		context.getRequest().getSession().setAttribute(Contractor.currentContractorId, form.getContractor().getId());
		context.getRequest().getSession().setAttribute(ContactPerson.current_contact_person_id, form.getContactPersonSign().getCps_id());
		return context.getMapping().findForward("newContactPerson");
	}

	public ActionForward editContactPersonSign(IActionContext context) throws Exception
	{
		saveCurrentFormToBean(context);

		ConditionForContractForm form = (ConditionForContractForm) context.getForm();
		form.setAddSignPerson(true);
		context.getRequest().getSession().setAttribute(Contractor.currentContractorId, form.getContractor().getId());
		context.getRequest().getSession().setAttribute(ContactPerson.current_contact_person_id, form.getContactPersonSign().getCps_id());
		return context.getMapping().findForward("editContactPerson");
	}

	public ActionForward newContactPerson(IActionContext context) throws Exception
	{
		saveCurrentFormToBean(context);

		ConditionForContractForm form = (ConditionForContractForm) context.getForm();
		form.setAddSignPerson(false);
		context.getRequest().getSession().setAttribute(Contractor.currentContractorId, form.getContractor().getId());
		context.getRequest().getSession().setAttribute(ContactPerson.current_contact_person_id, form.getContactPerson().getCps_id());
		return context.getMapping().findForward("newContactPerson");
	}

	public ActionForward editContactPerson(IActionContext context) throws Exception
	{
		saveCurrentFormToBean(context);

		ConditionForContractForm form = (ConditionForContractForm) context.getForm();
		form.setAddSignPerson(false);
		context.getRequest().getSession().setAttribute(Contractor.currentContractorId, form.getContractor().getId());
		context.getRequest().getSession().setAttribute(ContactPerson.current_contact_person_id, form.getContactPerson().getCps_id());
		return context.getMapping().findForward("editContactPerson");
	}

	public ActionForward selectCP(IActionContext context) throws Exception
	{
		saveCurrentFormToBean(context);

		return context.getMapping().findForward("selectCP");
	}

	public ActionForward newProduce(IActionContext context) throws Exception
	{
		saveCurrentFormToBean(context);

		return context.getMapping().findForward("newProduce");
	}

	public ActionForward editPurchasePurposes(IActionContext context) throws Exception
	{
		saveCurrentFormToBean(context);

		return context.getMapping().findForward("editPurchasePurposes");
	}

	public ActionForward cloneProduce(IActionContext context) throws Exception
	{
		saveCurrentFormToBean(context);

		return context.getMapping().findForward("cloneProduce");
	}

	public ActionForward editProduce(IActionContext context) throws Exception
	{
		saveCurrentFormToBean(context);

		return context.getMapping().findForward("editProduce");
	}

	public ActionForward deleteProduce(IActionContext context) throws Exception
	{
		saveCurrentFormToBean(context);

		ConditionForContractForm form = (ConditionForContractForm) context.getForm();
		ConditionForContract conditionForContract = (ConditionForContract) StoreUtil.getSession(context.getRequest(), ConditionForContract.class);
		conditionForContract.deleteConditionForContractProduce(form.getNumber());

		return retFromProduceOperation(context);
	}

	public ActionForward retFromProduceOperation(IActionContext context) throws Exception
	{
		ConditionForContractForm form = (ConditionForContractForm) context.getForm();
		getCurrentFormFromBean(context);

		form.setPrint("false");

		return show(context);
	}

	public ActionForward retFromContractor(IActionContext context) throws Exception
	{
		ConditionForContractForm form = (ConditionForContractForm) context.getForm();
		getCurrentFormFromBean(context);

		String contractorId = (String) context.getRequest().getSession().getAttribute(Contractor.currentContractorId);
		if (!StringUtil.isEmpty(contractorId))
			form.setContractor(ContractorDAO.load(context, contractorId));
		context.getRequest().getSession().setAttribute(Contractor.currentContractorId, null);

		String contactPersonId = (String) context.getRequest().getSession().getAttribute(ContactPerson.current_contact_person_id);
		if (!StringUtil.isEmpty(contactPersonId))
		{
			if (form.isAddSignPerson())
			{
				form.setContactPersonSign(ContactPersonDAO.load(context, contactPersonId));
			}
			else
			{
				form.setContactPerson(ContactPersonDAO.load(context, contactPersonId));
			}
		}
		context.getRequest().getSession().setAttribute(ContactPerson.current_contact_person_id, null);

		form.setPrint("false");
		return show(context);
	}

	public ActionForward returnFromSelectCP(IActionContext context) throws Exception
	{
		ConditionForContractForm form = (ConditionForContractForm) context.getForm();
		String cprId = SelectFromGridAction.getSelectedId(context);
		ConditionForContract conditionForContract = (ConditionForContract) StoreUtil.getSession(context.getRequest(), ConditionForContract.class);
		if (!StringUtil.isEmpty(cprId))
		{
			CommercialProposal commercialProposal = CommercialProposalDAO.load(context, cprId);
			conditionForContract.importFromCP(commercialProposal);
		}

		getCurrentFormFromBean(context);
		form.setPrint("false");
		return show(context);
	}

	public ActionForward reload(IActionContext context) throws Exception
	{
		saveCurrentFormToBean(context);
		getCurrentFormFromBean(context);

		ConditionForContractForm form = (ConditionForContractForm) context.getForm();
		form.setNeedPrint(false);
		return show(context);
	}

	public ActionForward reloadSeller(IActionContext context) throws Exception
	{
		saveCurrentFormToBean(context);
		getCurrentFormFromBean(context);

		ConditionForContractForm form = (ConditionForContractForm) context.getForm();
		form.setNeedPrint(false);
		form.setContract(new Contract());
		return show(context);
	}

	public ActionForward importExcel(IActionContext context) throws Exception
	{
		saveCurrentFormToBean(context);

		return context.getMapping().findForward("importExcel");
	}

	private boolean saveCommon(IActionContext context) throws Exception
	{
		ConditionForContractForm form = (ConditionForContractForm) context.getForm();
		String errMsg = "";

		//не выбран продовец
		if (StringUtil.isEmpty(form.getSeller().getId()))
		{
			StrutsUtil.addError(context, "error.condition_for_contract.seller", null);
			return false;
		}

		//не выбран тип документа
		if (StringUtil.isEmpty(form.getCfc_doc_type1()) && StringUtil.isEmpty(form.getCfc_doc_type2()))
		{
			StrutsUtil.addError(context, "error.condition_for_contract.cfc_doc_type", null);
			return false;
		}

		if (!form.isUnitsPermittedForUsageInContracts())
		{
			StrutsUtil.addError(context, "msg.ConditionForContract.unitIsNotPermittedForContracts", null);
		}

		boolean foundError = false;
		for (int i = 0; i < form.getGrid().getDataList().size() - 1; i++)
		{
			ConditionForContractProduce conditionForContractProduce = (ConditionForContractProduce) form.getGrid().getDataList().get(i);
			if (null == conditionForContractProduce.getProduce() || null == conditionForContractProduce.getProduce().getId())
			{
				foundError = true;
				break;
			}
		}
		if (foundError)
		{
			errMsg = StrutsUtil.addDelimiter(errMsg);
			errMsg += StrutsUtil.getMessage(context, "error.condition_for_contract.null_produce");
		}

		saveCurrentFormToBean(context);
		ConditionForContract conditionForContract = (ConditionForContract) StoreUtil.getSession(context.getRequest(), ConditionForContract.class);

		//Поле "Покупатель" - не указано поле "Тел.:"
		if (!StringUtil.isEmpty(conditionForContract.getContractor().getId()) && StringUtil.isEmpty(conditionForContract.getContractor().getPhone()))
		{
			errMsg = StrutsUtil.addDelimiter(errMsg);
			errMsg += StrutsUtil.getMessage(context, "error.condition_for_contract.contractorPhone");
		}

		//Поле "Покупатель" - не указано поле "Факс.:"
		if (!StringUtil.isEmpty(conditionForContract.getContractor().getId()) && StringUtil.isEmpty(conditionForContract.getContractor().getFax()))
		{
			errMsg = StrutsUtil.addDelimiter(errMsg);
			errMsg += StrutsUtil.getMessage(context, "error.condition_for_contract.contractorFax");
		}

		//не указана должность
		if (!StringUtil.isEmpty(conditionForContract.getContactPersonSign().getCps_id()) && StringUtil.isEmpty(conditionForContract.getContactPersonSign().getCps_position()))
		{
			errMsg = StrutsUtil.addDelimiter(errMsg);
			errMsg += StrutsUtil.getMessage(context, "error.condition_for_contract.cps_position1");
		}

		//не указано действует на основании
		if (!StringUtil.isEmpty(conditionForContract.getContactPersonSign().getCps_id()) && StringUtil.isEmpty(conditionForContract.getContactPersonSign().getCps_on_reason()))
		{
			errMsg = StrutsUtil.addDelimiter(errMsg);
			errMsg += StrutsUtil.getMessage(context, "error.condition_for_contract.сps_on_reason1");
		}

		//не указано "Тел.:" и "Моб."
		if (
						!StringUtil.isEmpty(conditionForContract.getContactPerson().getCps_id()) &&
										StringUtil.isEmpty(conditionForContract.getContactPerson().getCps_phone()) &&
										StringUtil.isEmpty(conditionForContract.getContactPerson().getCps_mob_phone())
						)
		{
			errMsg = StrutsUtil.addDelimiter(errMsg);
			errMsg += StrutsUtil.getMessage(context, "error.condition_for_contract.сps_phone");
		}

		if (!StringUtil.isEmpty(errMsg))
		{
			StrutsUtil.addError(context, "errors.msg", errMsg, null);
			return false;
		}

		//Если убираем флаг "Разместить заявку", то автоматически аттрибуты "цены проверил" и / или "заяка исполнена" должны сбрасываться.
		if (!conditionForContract.isCfcPlace())
		{
			conditionForContract.setCfc_check_price("");
			conditionForContract.setCfc_check_price_date("");
			conditionForContract.setUsr_id_check_price("");
			conditionForContract.setCfc_execute("");
		}
		if (StringUtil.isEmpty(form.getCfc_id()))
		{
			ConditionForContractDAO.insert(context, conditionForContract);
		}
		else
		{
			ConditionForContractDAO.save(context, conditionForContract);
		}

		if (conditionForContract.isCfcPlace())
		{
			List<UserLink> userLinkList = new ArrayList<UserLink>();
			for (int i = 0; i < conditionForContract.getProduces().size() - 1; i++)
			{
				ConditionForContractProduce conditionForContractProduce = conditionForContract.getProduces().get(i);
				if (!StringUtil.isEmpty(conditionForContractProduce.getCpr_id()))
				{
					CommercialProposal commercialProposal = CommercialProposalDAO.load(context, conditionForContractProduce.getCpr_id());
					if (!commercialProposal.isCheckedPrices())
					{
						String parametersStr = "?dispatch=filter&number=" + commercialProposal.getCpr_number();

						boolean linkFound = false;
						for (UserLink userLink : userLinkList)
						{
							if (userLink.getParameters().equals(parametersStr))
							{
								linkFound = true;
								break;
							}
						}
						if (linkFound)
							continue;

						UserLink userLink = new UserLink();
						userLink.setUrl("/dcl/CommercialProposalsAction.do");
						userLink.setParameters(parametersStr);
						userLink.setMenuId("id.commercial_proposal");
						userLink.setText(StrutsUtil.getMessage(
										context,
										"messages.ConditionForContract.pricesNotChecked",
										commercialProposal.getCpr_number(),
										conditionForContract.isNeedInvoice() ? StrutsUtil.getMessage(context, "messages.ConditionForContract.needInvoice") : "")
						);

						userLinkList.add(userLink);

						MessageDAO.saveConditionForContractMessagesForEconomist(context, userLink);
					}
				}
			}
		}

		Session hibSession = HibernateUtil.getSessionFactory().getCurrentSession();
		hibSession.beginTransaction();
		form.getAttachmentService().commit(Integer.parseInt(conditionForContract.getCfc_id()));
		hibSession.getTransaction().commit();
		return true;
	}

	public ActionForward print(IActionContext context) throws Exception
	{
		ConditionForContractForm form = (ConditionForContractForm) context.getForm();
		saveCurrentFormToBean(context);
		form.setNeedPrint(true);
		return show(context);
	}

	public ActionForward show(IActionContext context) throws Exception
	{
		final ConditionForContractForm form = (ConditionForContractForm) context.getForm();

		ConditionForContract conditionForContract = (ConditionForContract) StoreUtil.getSession(context.getRequest(), ConditionForContract.class);
		conditionForContract.calculate();
		form.getGrid().setDataList(conditionForContract.getProduces());

		Session hibSession = HibernateUtil.getSessionFactory().getCurrentSession();
		hibSession.beginTransaction();
		AttachmentsService attachmentsService = new AttachmentsService(hibSession);
		form.setTemplateExcel(attachmentsService.firstAttach("DCL_TEMPLATE", ConstDefinitions.templateIdCFC));
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

		User currentUser = UserUtil.getCurrentUser(context.getRequest());
		if ((currentUser.isManager() || currentUser.isAdmin()) && !form.isFormReadOnly())
		{
			form.setFormReadOnly(false);
		}
		else
		{
			form.setFormReadOnly(true);
		}
		form.setReadOnlyForSave(form.isFormReadOnly() && !currentUser.isAdmin());
		form.setUnitsPermittedForUsageInContracts(isUnitsPermittedForUsageInContracts(form));
		form.setReadOnlyForPlace(form.isReadOnlyForSave() || !form.isUnitsPermittedForUsageInContracts());
		if (!currentUser.isAdmin())
		{
			form.setShowForAdmin(false);
		}
		else
		{
			form.setShowForAdmin(true);
		}

		form.setReadOnlyForAnnul(form.isFormReadOnly());
		if (!form.isFormReadOnly())
		{
			//Право аннулировать Заявки на составление договоров должен иметь:
			// - тот, кто создал эту заявку;
			// - руководитель отдела, к которому относится тот, кто создал эту заявку;
			// - администратор.
			if (!currentUser.isAdmin() && !currentUser.getUsr_id().equals(form.getCreateUser().getUsr_id()) && !currentUser.isChiefDepartment())
			{
				form.setReadOnlyForAnnul(true);
			}
		}

		form.getAttachmentsGrid().setDataList(form.getAttachmentService().list());
		context.getRequest().setAttribute("show-checker", new IShowChecker()
		{
			int size = form.getGrid().getDataList().size();

			public boolean check(ShowCheckerContext context)
			{
				return context.getTable().getRecordCounter() < size;
			}
		}
		);

		context.getRequest().setAttribute("style-checker", new IStyleClassChecker()
		{
			public String check(StyleClassCheckerContext context)
			{
				ConditionForContractProduce conditionForContractProduce = (ConditionForContractProduce) form.getGrid().getDataList().get(context.getTable().getRecordCounter() - 1);
				if (conditionForContractProduce.isItogLine())
				{
					return "bold-cell";
				}
				if (null == conditionForContractProduce.getProduce() || null == conditionForContractProduce.getProduce().getId())
				{
					return "red-font-cell";
				}
				return "";
			}
		});

		context.getRequest().setAttribute("style-checker-image", new IStyleClassChecker()
		{
			public String check(StyleClassCheckerContext context)
			{
				ConditionForContractProduce conditionForContractProduce = (ConditionForContractProduce) form.getGrid().getDataList().get(context.getTable().getRecordCounter() - 1);
				if (conditionForContractProduce.isItogLine())
				{
					return "bold-cell";
				}
				return "grid-image-without-border";
			}
		});

		context.getRequest().setAttribute("unit-style-checker", new IStyleClassChecker()
		{
			public String check(StyleClassCheckerContext context)
			{
				ConditionForContractProduce conditionForContractProduce = (ConditionForContractProduce) form.getGrid().getDataList().get(context.getTable().getRecordCounter() - 1);
				if (conditionForContractProduce.isItogLine())
				{
					return "bold-cell";
				}
				if (null == conditionForContractProduce.getProduce() || null == conditionForContractProduce.getProduce().getId())
				{
					return "red-font-cell";
				}
				if (!conditionForContractProduce.getProduce().getUnit().isAcceptableForContract())
				{
					return "cell-pink";
				}

				return "";
			}
		});

		if (!StringUtil.isEmpty(form.getContactPersonSign().getCps_id()))
		{
			form.setContactPersonSign(ContactPersonDAO.load(context, form.getContactPersonSign().getCps_id()));
		}
		if (!StringUtil.isEmpty(form.getContactPerson().getCps_id()))
		{
			form.setContactPerson(ContactPersonDAO.load(context, form.getContactPerson().getCps_id()));
		}
		return context.getMapping().findForward("form");
	}

	public ActionForward input(IActionContext context) throws Exception
	{
		ConditionForContractForm form = (ConditionForContractForm) context.getForm();

		ConditionForContract conditionForContract = new ConditionForContract();
		StoreUtil.putSession(context.getRequest(), conditionForContract);
		//обнуляем поля формы
		getCurrentFormFromBean(context);

		form.setIs_new_doc("true");
		form.setCfc_doc_type1("");
		form.setCfc_doc_type2("");
		form.setCfc_place_save("");
		form.setShowMsg(false);
		form.setFormReadOnly(false);
		form.setNeedPrint(false);

		form.setAttachmentService(
						DeferredAttachmentService.create(context.getRequest().getSession(),
										referencedTable, context.getMapping().findForward("backFromAttach"), null));
		return show(context);
	}

	public ActionForward clone(IActionContext context) throws Exception
	{
		ConditionForContractForm form = (ConditionForContractForm) context.getForm();

		ConditionForContract conditionForContract = ConditionForContractDAO.load(context, form.getCfc_id());
		conditionForContract.setListIdsToNull();
		conditionForContract.setCfc_id("");
		conditionForContract.setUsr_date_create("");
		conditionForContract.setUsr_date_edit("");
		conditionForContract.setUsr_date_place("");
		conditionForContract.setUsr_date_execute("");
		conditionForContract.setUsr_date_annul("");
		conditionForContract.setCreateUser(new User());
		conditionForContract.setEditUser(new User());
		conditionForContract.setPlaceUser(new User());
		conditionForContract.setExecuteUser(new User());
		conditionForContract.setAnnulUser(new User());
		conditionForContract.setCfc_place("");
		conditionForContract.setCfc_execute("");
		// 0 - новый договор, 1 - спецификация к уже имеющемуся договору
		if ("0".equals(conditionForContract.getCfc_doc_type())) //сбросить "№ Договора (Контракта)", " Дата Контракта"
		{
			conditionForContract.setCfc_con_number_txt("");
			conditionForContract.setCfc_con_date("");
		}
		if ("1".equals(conditionForContract.getCfc_doc_type())) //сбросить "№ новой спецификации", "Дата спецификации"
		{
			conditionForContract.setCfc_spc_number_txt("");
			conditionForContract.setCfc_spc_date("");
		}
		conditionForContract.setCfc_annul("");
		conditionForContract.setCfc_annul_date("");

		StoreUtil.putSession(context.getRequest(), conditionForContract);
		getCurrentFormFromBean(context);

		form.setIs_new_doc("true");
		form.setShowMsg(false);
		form.setFormReadOnly(false);
		form.setNeedPrint(false);

		form.setAttachmentService(
						DeferredAttachmentService.create(context.getRequest().getSession(),
										referencedTable, context.getMapping().findForward("backFromAttach"), null));

		return show(context);
	}

	public ActionForward edit(IActionContext context) throws Exception
	{
		ConditionForContractForm form = (ConditionForContractForm) context.getForm();

		ConditionForContract conditionForContract = ConditionForContractDAO.load(context, form.getCfc_id());
		form.setCfc_place_save(conditionForContract.getCfc_place());
		StoreUtil.putSession(context.getRequest(), conditionForContract);
		getCurrentFormFromBean(context);

		form.setShowMsg(false);
		if (StringUtil.isEmpty(conditionForContract.getCfc_place()) && StringUtil.isEmpty(conditionForContract.getCfc_execute()))
		{
			form.setFormReadOnly(false);
		}
		else
		{
			form.setFormReadOnly(true);
		}

		form.setNeedPrint(false);
		Session hibSession = HibernateUtil.getSessionFactory().getCurrentSession();
		hibSession.beginTransaction();
		form.setAttachmentService(
						DeferredAttachmentService.create(context.getRequest().getSession(),
										referencedTable, Integer.parseInt(form.getCfc_id()),
										context.getMapping().findForward("backFromAttach"), null));
		hibSession.getTransaction().commit();
		return show(context);
	}

	public ActionForward back(IActionContext context) throws Exception
	{
		ConditionForContractForm form = (ConditionForContractForm) context.getForm();
		form.getAttachmentService().rollback();
		form.setAttachmentService(null);
		DeferredAttachmentService.removeLast(context.getRequest().getSession());
		return context.getMapping().findForward("back");
	}

	public ActionForward process(IActionContext context) throws Exception
	{
		ConditionForContractForm form = (ConditionForContractForm) context.getForm();
		//устанавливаем только если поменялось с пустого
		if ("1".equals(form.getCfc_place()) && StringUtil.isEmpty(form.getCfc_place_save()))
		{
			form.setShowMsg(true);
			form.setNeedPrint(false);
			return show(context);
		}

		return processForce(context);
	}

	public ActionForward processForce(IActionContext context) throws Exception
	{
		boolean retFromSave = saveCommon(context);

		if (retFromSave)
		{
			DeferredAttachmentService.removeLast(context.getRequest().getSession());
			return context.getMapping().findForward("back");
		}
		else
		{
			ConditionForContractForm form = (ConditionForContractForm) context.getForm();
			form.setNeedPrint(false);
			return show(context);
		}
	}

	public ActionForward uploadTemplate(IActionContext context) throws Exception
	{
		saveCurrentFormToBean(context);

		return context.getMapping().findForward("uploadTemplate");
	}

	public ActionForward attach(IActionContext context) throws Exception
	{
		saveCurrentFormToBean(context);
		return context.getMapping().findForward("deferredAttach");
	}

	public ActionForward deleteAttachment(IActionContext context) throws Exception
	{
		saveCurrentFormToBean(context);
		ConditionForContractForm form = (ConditionForContractForm) context.getForm();
		form.getAttachmentService().delete(form.getAttachmentId());
		return show(context);
	}

	public ActionForward downloadAttachment(IActionContext context) throws Exception
	{
		saveCurrentFormToBean(context);
		ConditionForContractForm form = (ConditionForContractForm) context.getForm();

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

	public ActionForward ajaxChangeContract(IActionContext context) throws Exception
	{
		ConditionForContractForm form = (ConditionForContractForm) context.getForm();
		ConditionForContract conditionForContract = (ConditionForContract) StoreUtil.getSession(context.getRequest(), ConditionForContract.class);
		String conId = context.getRequest().getParameter("conId");
		if (!StringUtil.isEmpty(conId))
		{
			loadDataDependOnContract(conId, context, conditionForContract);
			getCurrentFormFromBean(context);

			String resultMsg = form.getCurrency().getId() + "|" +
							form.getCurrency().getName() + "|" +
							form.getContractNumberWithDate() + "|" +
							form.getSpc_numbers() + "|" +
							form.getCfc_spc_numbers() + "|" +
							form.getContract().getNumberWithDateAndReusable() + "|" +
							form.getContract().getAnnulStr() + "|" +
							form.getContract().getCon_final_date_formatted();

			StrutsUtil.setAjaxResponse(context, resultMsg, false);
		}

		return context.getMapping().findForward("ajax");
	}

	public ActionForward ajaxGetReputation(IActionContext context) throws Exception
	{
		String contractorId = context.getRequest().getParameter("contractor-id");
		if (!StringUtil.isEmpty(contractorId) && !"-1".equals(contractorId))
		{
			Contractor contractor = ContractorDAO.load(context, context.getRequest().getParameter("contractor-id"));
			String reputation = StrutsUtil.getMessage(context, "ConditionForContract.reputation");
			reputation += " ";
			reputation += contractor.getReputation().getDescription();
			StrutsUtil.setAjaxResponse(context, reputation, false);
		}
		return context.getMapping().findForward("ajax");
	}

	private boolean isUnitsPermittedForUsageInContracts(ConditionForContractForm form)
	{
		if (form.getGrid().getDataList() != null)
		{
			for (Object produce : form.getGrid().getDataList())
			{
				DboProduce dboProduce = ((ConditionForContractProduce) produce).getProduce();
				if (dboProduce == null || dboProduce.getUnit() == null)
				{
					continue;
				}

				if (!dboProduce.getUnit().isAcceptableForContract())
				{
					return false;
				}
			}
		}

		return true;
	}
}
