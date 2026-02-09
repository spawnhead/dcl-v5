package net.sam.dcl.action;

import net.sam.dcl.controller.actions.DBTransactionAction;
import net.sam.dcl.controller.actions.SelectFromGridAction;
import net.sam.dcl.controller.IDispatchable;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.service.DeferredAttachmentService;
import net.sam.dcl.util.*;
import net.sam.dcl.beans.*;
import net.sam.dcl.dao.*;
import net.sam.dcl.form.ContractForm;
import net.sam.dcl.taglib.table.IReadOnlyChecker;
import net.sam.dcl.taglib.table.ReadOnlyCheckerContext;
import net.sam.dcl.taglib.table.IStyleClassChecker;
import net.sam.dcl.taglib.table.StyleClassCheckerContext;
import net.sam.dcl.taglib.table.impl.AlwaysReadonlyChecker;
import net.sam.dcl.service.AttachmentException;
import net.sam.dcl.service.AttachmentFileRenamer;
import org.apache.struts.action.ActionForward;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class ContractAction extends DBTransactionAction implements IDispatchable
{
	protected static Log log = LogFactory.getLog(ContractAction.class);
	final static String referencedTable = "DCL_CONTRACT";
	public static final String CONTRACT_ATTACHMENT_SERVICE = "CONTRACT_ATTACHMENT_SERVICE";

	public static String contractIdForMessage = "contractIdForMessage";

	private void saveCurrentFormToBean(IActionContext context)
	{
		ContractForm form = (ContractForm) context.getForm();

		Contract contract = (Contract) StoreUtil.getSession(context.getRequest(), Contract.class);

		contract.setIs_new_doc(form.getIs_new_doc());
		try
		{
			contract.setCon_id(form.getCon_id());

			if (!StringUtil.isEmpty(form.getCreateUser().getUsr_id()))
			{
				contract.setCreateUser(UserDAO.load(context, form.getCreateUser().getUsr_id()));
			}
			if (!StringUtil.isEmpty(form.getEditUser().getUsr_id()))
			{
				contract.setEditUser(UserDAO.load(context, form.getEditUser().getUsr_id()));
			}
			contract.setUsr_date_create(form.getUsr_date_create());
			contract.setUsr_date_edit(form.getUsr_date_edit());
			contract.setCon_number(form.getCon_number());
			contract.setCon_date(form.getCon_date());
			contract.setCon_reusable(form.getCon_reusable());
			contract.setCon_final_date(form.getCon_final_date());
			contract.setCon_executed(form.getCon_executed());
			contract.setCon_occupied(form.getCon_occupied());

			// 0 - "Факсовая и т.п. копия", 1 - "Оригинал"
			contract.setCon_original("");
			if (!StringUtil.isEmpty(form.getCon_fax_copy()))
			{
				contract.setCon_original("0");
			}
			if (!StringUtil.isEmpty(form.getCon_original()))
			{
				contract.setCon_original("1");
			}

			if (!StringUtil.isEmpty(form.getSeller().getId()))
			{
				contract.setSeller(SellerDAO.load(context, form.getSeller().getId()));
			}

			contract.setCon_annul(form.getCon_annul());
			contract.setCon_annul_date(form.getCon_annul_date());
			contract.setCon_comment(form.getCon_comment());

			if (!StringUtil.isEmpty(form.getContractor().getId()))
			{
				contract.setContractor(ContractorDAO.load(context, form.getContractor().getId()));
			}
			if (!StringUtil.isEmpty(form.getCurrency().getId()))
			{
				contract.setCurrency(CurrencyDAO.load(context, form.getCurrency().getId()));
			}
		}
		catch (Exception e)
		{
			StrutsUtil.addError(context, e);
		}

		StoreUtil.putSession(context.getRequest(), contract);
	}

	private void getCurrentFormFromBean(IActionContext context, Contract contractIn)
	{
		ContractForm form = (ContractForm) context.getForm();
		Contract contract;
		if (null != contractIn)
		{
			contract = contractIn;
		}
		else
		{
			contract = (Contract) StoreUtil.getSession(context.getRequest(), Contract.class);
		}

		if (null != contract)
		{
			form.setIs_new_doc(contract.getIs_new_doc());

			form.setCon_id(contract.getCon_id());
			form.setCreateUser(contract.getCreateUser());
			form.setEditUser(contract.getEditUser());
			form.setUsr_date_create(contract.getUsr_date_create());
			form.setUsr_date_edit(contract.getUsr_date_edit());
			form.setCon_number(contract.getCon_number());
			form.setCon_date(contract.getCon_date());
			form.setCon_reusable(contract.getCon_reusable());
			form.setCon_final_date(contract.getCon_final_date());
			form.setContractor(contract.getContractor());
			form.setCurrency(contract.getCurrency());
			form.setCon_executed(contract.getCon_executed());
			form.setCon_occupied(contract.getCon_occupied());
			if (contract.isCopy())
			{
				form.setCon_fax_copy("1");
			}
			if (contract.isOriginal())
			{
				form.setCon_original("1");
			}

			form.setSeller(contract.getSeller());
			form.setCon_annul(contract.getCon_annul());
			form.setCon_annul_date(contract.getCon_annul_date());
			form.setCon_comment(contract.getCon_comment());

			form.getGrid().setDataList(contract.getSpecifications());
		}
	}

	public ActionForward newContractor(IActionContext context) throws Exception
	{
		saveCurrentFormToBean(context);

		ContractForm form = (ContractForm) context.getForm();
		context.getRequest().getSession().setAttribute(Contractor.currentContractorId, form.getContractor().getId());

		return context.getMapping().findForward("newContractor");
	}

	public ActionForward newSpecification(IActionContext context) throws Exception
	{
		saveCurrentFormToBean(context);
		ContractForm form = (ContractForm) context.getForm();
		StoreUtil.putSession(context.getRequest(), form.getAttachmentService(), CONTRACT_ATTACHMENT_SERVICE);

		return context.getMapping().findForward("newSpecification");
	}

	public ActionForward editSpecification(IActionContext context) throws Exception
	{
		saveCurrentFormToBean(context);
		ContractForm form = (ContractForm) context.getForm();
		StoreUtil.putSession(context.getRequest(), form.getAttachmentService(), CONTRACT_ATTACHMENT_SERVICE);
		return context.getMapping().findForward("editSpecification");
	}

	public ActionForward deleteSpecification(IActionContext context) throws Exception
	{
		saveCurrentFormToBean(context);

		ContractForm form = (ContractForm) context.getForm();
		Contract contract = (Contract) StoreUtil.getSession(context.getRequest(), Contract.class);
		contract.deleteSpecification(form.getSpc_number());

		return retFromSpecificationOperation(context);
	}

	public ActionForward attach(IActionContext context) throws Exception
	{
		saveCurrentFormToBean(context);

		return context.getMapping().findForward("attach");
	}

	public ActionForward retFromSpecificationOperation(IActionContext context) throws Exception
	{
		getCurrentFormFromBean(context, null);

		return show(context);
	}

	public ActionForward retFromContractor(IActionContext context) throws Exception
	{
		ContractForm form = (ContractForm) context.getForm();
		getCurrentFormFromBean(context, null);

		String contractorId = (String) context.getRequest().getSession().getAttribute(Contractor.currentContractorId);
		if (!StringUtil.isEmpty(contractorId))
			form.setContractor(ContractorDAO.load(context, contractorId));
		context.getRequest().getSession().setAttribute(Contractor.currentContractorId, null);

		return show(context);
	}

	private boolean saveCommon(IActionContext context) throws Exception
	{
		ContractForm form = (ContractForm) context.getForm();
		String errMsg = "";

		for (int i = 0; i < form.getGrid().getDataList().size(); i++)
		{
			Specification specification = (Specification) form.getGrid().getDataList().get(i);
			if (StringUtil.isEmpty(specification.getSpc_executed()) && StringUtil.isEmpty(specification.getUser().getUsr_id()))
			{
				errMsg = StrutsUtil.addDelimiter(errMsg);
				errMsg += StrutsUtil.getMessage(context, "error.contract.spc_user_empty", specification.getSpc_number());
			}
		}

		if (!StringUtil.isEmpty(errMsg))
		{
			StrutsUtil.addError(context, "errors.msg", errMsg, null);
			return false;
		}

		saveCurrentFormToBean(context);

		Contract contract = (Contract) StoreUtil.getSession(context.getRequest(), Contract.class);
		contract.setCon_summ(contract.calculateTotalSum());
		//если нашли не закрытую спецификацию - контракт тоже не закрыт
		if (contract.findNotExecutedSpecification())
		{
			contract.setCon_executed("");
		}
		else
		{
			contract.setCon_executed("1");
		}

		if (StringUtil.isEmpty(form.getCon_id()))
		{
			ContractDAO.insert(context, contract);
		}
		else
		{
			ContractDAO.save(context, contract);
		}
		Session hibSession = HibernateUtil.getSessionFactory().getCurrentSession();
		hibSession.beginTransaction();
		form.getAttachmentService().commit(Integer.parseInt(contract.getCon_id()));
		for (int i = 0; i < form.getGrid().getDataList().size(); i++)
		{
			List<DeferredAttachmentService.DeferredAttachment> attachmentList = form.getAttachmentService().listDeleted();
			Specification specification = (Specification) form.getGrid().getDataList().get(i);
			if (null != specification.getAttachmentService())
			{
				for (DeferredAttachmentService.DeferredAttachment deleted : attachmentList)
				{
					specification.getAttachmentService().fixLinksOnDeleted(deleted);
				}
				specification.getAttachmentService().commit(Integer.parseInt(specification.getSpc_id()));
			}
		}
		hibSession.getTransaction().commit();

		String contractId = (String) context.getRequest().getSession().getAttribute(contractIdForMessage);
		if ((!StringUtil.isEmpty(contractId) || "true".equals(form.getIs_new_doc())) && contract.isOriginal())
		{
			ContractMessage contractMessage = new ContractMessage();
			contractMessage.setCon_id(contract.getCon_id());
			contractMessage.setCtr_id(contract.getContractor().getId());
			contractMessage.setMsg(StrutsUtil.getMessage(context, "ContractMessage.msgByCon", contract.getCon_number(), contract.getCon_date_formatted()));
			MessageDAO.saveContractMessages(context, contractMessage);
		}

		SpecificationsForMsgCheck specificationsForMsgCheck = (SpecificationsForMsgCheck) StoreUtil.getSession(context.getRequest(), SpecificationsForMsgCheck.class);
		for (Specification specification : contract.getSpecifications())
		{
			if (("true".equals(form.getIs_new_doc()) || specificationsForMsgCheck.isInSelectedList(specification.getSpc_id())) && specification.isOriginal())
			{
				ContractMessage contractMessage = new ContractMessage();
				contractMessage.setSpc_id(specification.getSpc_id());
				contractMessage.setCtr_id(contract.getContractor().getId());
				contractMessage.setMsg(StrutsUtil.getMessage(context, "ContractMessage.msgBySpc", specification.getSpc_number(), contract.getCon_number(), contract.getCon_date_formatted()));
				MessageDAO.saveContractMessages(context, contractMessage);
			}
		}

		return true;
	}

	public ActionForward show(IActionContext context) throws Exception
	{
		final ContractForm form = (ContractForm) context.getForm();

		Contract contract = (Contract) StoreUtil.getSession(context.getRequest(), Contract.class);
		contract.calculate(null);
		form.getGrid().setDataList(contract.getSpecifications());

		context.getRequest().setAttribute("alwaysReadonly", new AlwaysReadonlyChecker());

		User currentUser = UserUtil.getCurrentUser(context.getRequest());
		form.setReadOnlyForAnnul(true);
		if (currentUser.isAdmin() || currentUser.isEconomist() || currentUser.isLawyer())
		{
			form.setReadOnlyIfNotAdminEconomistLawyer(false);
			form.setShowAttach(true);
			if (contract.isCanAnnul())
			{
				form.setReadOnlyForAnnul(false);
			}
		}
		else
		{
			form.setReadOnlyIfNotAdminEconomistLawyer(true);
			form.setShowAttach(false);
		}
		//0 - ЗАО "Линтера" , 4 - UAB "SMERKONA", 6 - UAB "LinterР° - baldu technologijos", 7 - UAB “Lintera, automatika ir technika“
		String sellerId = contract.getSeller().getId();
		form.setShowAttachFiles(!currentUser.isOnlyUserInLithuania() || "0".equals(sellerId) || "4".equals(sellerId) || "6".equals(sellerId) || "7".equals(sellerId));

		//Для Литовских сотрудников и логистов права доступа: read-only. Возможность смотреть аттачи.
		if ((currentUser.isManager() && !currentUser.isAdmin() && !currentUser.isEconomist() && !currentUser.isLawyer()) || currentUser.isOnlyUserInLithuania() || currentUser.isOnlyLogistic())
		{
			form.setFormReadOnly(true);
		}
		else
		{
			form.setFormReadOnly(false);
		}

		form.getAttachmentsGrid().setDataList(form.getAttachmentService().list());
		context.getRequest().setAttribute("alwaysRead", new IReadOnlyChecker()
		{
			public boolean check(ReadOnlyCheckerContext ctx) throws Exception
			{
				return false;
			}
		});

		context.getRequest().setAttribute("deleteReadonly", new IReadOnlyChecker()
		{
			public boolean check(ReadOnlyCheckerContext ctx) throws Exception
			{
				Specification specification = (Specification) form.getGrid().getDataList().get(ctx.getTable().getRecordCounter() - 1);
				if (!"1".equalsIgnoreCase(specification.getSpc_executed()) &&
								"".equalsIgnoreCase(specification.getSpc_occupied()) &&
								!form.isFormReadOnly())
					return false;
				else
					return true;
			}
		});

		context.getRequest().setAttribute("style-checker", new IStyleClassChecker()
		{
			public String check(StyleClassCheckerContext context)
			{
				Specification specification = (Specification) form.getGrid().getDataList().get(context.getTable().getRecordCounter() - 1);
				if ("1".equals(specification.getSpc_annul()))
				{
					return "crossed-cell";
				}
				return "";
			}
		});

		return context.getMapping().findForward("form");
	}

	public ActionForward input(IActionContext context) throws Exception
	{
		Contract contract = new Contract();
		return inputCommon(context, contract);
	}

	public ActionForward importCP(IActionContext context) throws Exception
	{
		String cprId = SelectFromGridAction.getSelectedId(context);
		Contract contract = new Contract();
		if (!StringUtil.isEmpty(cprId))
		{
			CommercialProposal commercialProposal = CommercialProposalDAO.load(context, cprId);
			contract.importFromCP(commercialProposal);
		}

		return inputCommon(context, contract);
	}

	private ActionForward inputCommon(IActionContext context, Contract contract) throws Exception
	{
		ContractForm form = (ContractForm) context.getForm();

		StoreUtil.putSession(context.getRequest(), contract);
		//обнуляем поля формы
		getCurrentFormFromBean(context, contract);

		form.setIs_new_doc("true");
		form.setCon_executed("0"); //при всавке не исполнен контракт - возможно перенести в тригер?

		form.setAttachmentService(
						DeferredAttachmentService.create(context.getRequest().getSession(),
										referencedTable, context.getMapping().findForward("backFromAttach"), null));
		form.getAttachmentService().setRenamer(new ContractFileRenamer(contract));

		return show(context);
	}

	public ActionForward edit(IActionContext context) throws Exception
	{
		ContractForm form = (ContractForm) context.getForm();

		Contract contract = ContractDAO.load(context, form.getCon_id(), true);
		if (!contract.isOriginal())
			context.getRequest().getSession().setAttribute(contractIdForMessage, contract.getCon_id());
		else
			context.getRequest().getSession().setAttribute(contractIdForMessage, null);

		SpecificationsForMsgCheck specificationsForMsgCheck = new SpecificationsForMsgCheck();
		List<String> selectedIds = new ArrayList<String>();
		for (Specification specification : contract.getSpecifications())
		{
			if (!specification.isOriginal())
			{
				selectedIds.add(specification.getSpc_id());
			}
		}
		specificationsForMsgCheck.setSelectedIds(selectedIds);
		StoreUtil.putSession(context.getRequest(), specificationsForMsgCheck);
		StoreUtil.putSession(context.getRequest(), contract);
		getCurrentFormFromBean(context, contract);

		Session hibSession = HibernateUtil.getSessionFactory().getCurrentSession();
		hibSession.beginTransaction();
		form.setAttachmentService(
						DeferredAttachmentService.create(context.getRequest().getSession(),
										referencedTable, Integer.parseInt(form.getCon_id()),
										context.getMapping().findForward("backFromAttach"), null));
		form.getAttachmentService().setRenamer(new ContractFileRenamer(contract));
		hibSession.getTransaction().commit();

		return show(context);
	}

	public ActionForward back(IActionContext context) throws Exception
	{
		ContractForm form = (ContractForm) context.getForm();
		form.getAttachmentService().rollback();
		form.setAttachmentService(null);
		for (int i = 0; i < form.getGrid().getDataList().size(); i++)
		{
			Specification specification = (Specification) form.getGrid().getDataList().get(i);
			if (null != specification.getAttachmentService())
			{
				specification.getAttachmentService().rollback();
				specification.setAttachmentService(null);
			}
		}
		DeferredAttachmentService.removeLast(context.getRequest().getSession());
		return context.getMapping().findForward("back");
	}

	public ActionForward delete(IActionContext context) throws Exception
	{
		DAOUtils.update(context, "contract-delete", null);
		return context.getMapping().findForward("back");
	}

	public ActionForward process(IActionContext context) throws Exception
	{
		boolean retFromSave = saveCommon(context);

		if (retFromSave)
		{
			return back(context);
		}
		else
		{
			return show(context);
		}
	}

	public ActionForward deferredAttach(IActionContext context) throws Exception
	{
		saveCurrentFormToBean(context);
		ContractForm form = (ContractForm) context.getForm();
		Contract contract = (Contract) StoreUtil.getSession(context.getRequest(), Contract.class);
		if (checkMandatoryForAttachmentFields(context, contract))
		{
			return show(context);
		}

		DeferredAttachmentService.set(context.getRequest().getSession(), form.getAttachmentService());
		return context.getMapping().findForward("multipleAttach");
	}

	public ActionForward deleteAttachment(IActionContext context) throws Exception
	{
		saveCurrentFormToBean(context);
		ContractForm form = (ContractForm) context.getForm();
		DeferredAttachmentService.DeferredAttachment attachment = form.getAttachmentService().find(Integer.parseInt(form.getAttachmentId()));
		form.getAttachmentService().delete(attachment);

		for (int i = 0; i < form.getGrid().getDataList().size(); i++)
		{
			Specification specification = (Specification) form.getGrid().getDataList().get(i);
			if (null != specification.getAttachmentService())
			{
				specification.getAttachmentService().fixLinksOnDeleted(attachment);
			}
		}

		return show(context);
	}

	public ActionForward downloadAttachment(IActionContext context) throws Exception
	{
		saveCurrentFormToBean(context);
		ContractForm form = (ContractForm) context.getForm();
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

	static class ContractFileRenamer implements AttachmentFileRenamer
	{
		Contract contract;

		ContractFileRenamer(Contract contract)
		{
			this.contract = contract;
		}

		public String rename(String original)
		{
			String extention = null;
			int idx = original.lastIndexOf('.');
			if (idx != -1)
			{
				extention = original.substring(idx);
			}

			SimpleDateFormat appFormat = new SimpleDateFormat("yyyy.MM.dd");
			String date = appFormat.format(StringUtil.getCurrentDateTime());

			String renamed = contract.getContractor().getName().trim() + "_" + date + "_" + contract.getCon_number().trim();

			if (extention != null)
			{
				renamed += extention;
			}
			return renamed;
		}
	}

	private boolean checkMandatoryForAttachmentFields(IActionContext context, Contract contract)
					throws Exception
	{
		if (contract.getContractor() == null || StringUtil.isEmpty(contract.getContractor().getId()))
		{
			StrutsUtil.addError(context, "error.contract.contractor", null);
			return true;
		}
		if (StringUtil.isEmpty(contract.getCon_number()))
		{
			StrutsUtil.addError(context, "error.contract.num", null);
			return true;
		}

		return false;
	}

}
