package net.sam.dcl.action;

import net.sam.dcl.beans.*;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.controller.IDispatchable;
import net.sam.dcl.controller.actions.DBTransactionAction;
import net.sam.dcl.dao.CurrencyDAO;
import net.sam.dcl.dao.RateNDSDAO;
import net.sam.dcl.dao.SpecificationDAO;
import net.sam.dcl.dao.UserDAO;
import net.sam.dcl.form.SpecificationForm;
import net.sam.dcl.service.DeferredAttachmentService;
import net.sam.dcl.service.helper.SpecificationFileRenamer;
import net.sam.dcl.service.AttachmentException;
import net.sam.dcl.util.*;
import org.apache.struts.action.ActionForward;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class SpecificationAction extends DBTransactionAction implements IDispatchable
{
	final static public String referencedTable = "DCL_CON_LIST_SPEC";

	private void saveCurrentFormToBean(IActionContext context, Specification specification)
	{
		SpecificationForm form = (SpecificationForm) context.getForm();

		specification.setSpc_id(form.getSpc_id());
		specification.setSpc_number(form.getSpc_number());
		specification.setSpc_date(StringUtil.appDateString2dbDateString(form.getSpc_date()));
		specification.setSpc_summ(StringUtil.appCurrencyString2double(form.getSpc_summ()));
		if (StringUtil.isEmpty(form.getSpc_summ_nds()))
		{
			specification.setSpc_summ_nds(0.0);
		}
		else
		{
			specification.setSpc_summ_nds(StringUtil.appCurrencyString2double(form.getSpc_summ_nds()));
		}
		specification.setSpc_executed(form.getSpc_executed());
		specification.setSpc_occupied(form.getSpc_occupied());
		specification.setSpc_occupied_in_pay_shp(form.getSpc_occupied_in_pay_shp());
		specification.setDeliveryTerm(form.getDeliveryTerm());
		// 0 - Процент, 1 - Сумма
		if (!StringUtil.isEmpty(form.getSpc_percent_or_sum_percent()))
		{
			specification.setSpc_percent_or_sum("0");
		}
		if (!StringUtil.isEmpty(form.getSpc_percent_or_sum_sum()))
		{
			specification.setSpc_percent_or_sum("1");
		}
		if (StringUtil.isEmpty(form.getSpc_delivery_percent()))
		{
			specification.setSpc_delivery_percent(0.0);
		}
		else
		{
			specification.setSpc_delivery_percent(StringUtil.appCurrencyString2double(form.getSpc_delivery_percent()));
		}
		if (StringUtil.isEmpty(form.getSpc_delivery_sum()))
		{
			specification.setSpc_delivery_sum(0.0);
		}
		else
		{
			specification.setSpc_delivery_sum(StringUtil.appCurrencyString2double(form.getSpc_delivery_sum()));
		}
		specification.setSpc_delivery_date(StringUtil.appDateString2dbDateString(form.getSpc_delivery_date()));
		specification.setSpc_delivery_cond(form.getSpc_delivery_cond());
		specification.setSpc_add_pay_cond(form.getSpc_add_pay_cond());
		// 0 - "Факсовая и т.п. копия", 1 - "Оригинал"
		specification.setSpc_original("");
		if (!StringUtil.isEmpty(form.getSpc_fax_copy()))
		{
			specification.setSpc_original("0");
		}
		if (!StringUtil.isEmpty(form.getSpc_original()))
		{
			specification.setSpc_original("1");
		}
		specification.setSpc_montage(form.getSpc_montage());
		specification.setSpc_pay_after_montage(form.getSpc_pay_after_montage());
		specification.setSpc_group_delivery(form.getSpc_group_delivery());
		specification.setSpc_annul(form.getSpc_annul());
		specification.setSpc_annul_date(StringUtil.appDateString2dbDateString(form.getSpc_annul_date()));
		specification.setSpc_in_ctc(form.getSpc_in_ctc());
		specification.setSpc_comment(form.getSpc_comment());
		try
		{
			if (!StringUtil.isEmpty(form.getUser().getUsr_id()))
			{
				specification.setUser(UserDAO.load(context, form.getUser().getUsr_id()));
			}
		}
		catch (Exception e)
		{
			StrutsUtil.addError(context, e);
		}
		specification.setSpc_letter1_date(StringUtil.appDateString2dbDateString(form.getSpc_letter1_date()));
		specification.setSpc_letter2_date(StringUtil.appDateString2dbDateString(form.getSpc_letter2_date()));
		specification.setSpc_letter3_date(StringUtil.appDateString2dbDateString(form.getSpc_letter3_date()));
		specification.setSpc_complaint_in_court_date(StringUtil.appDateString2dbDateString(form.getSpc_complaint_in_court_date()));
		specification.setSpc_additional_days_count(form.getSpc_additional_days_count());

		specification.setPayed_summ(form.getPayed_summ());
		specification.setPayed_date(StringUtil.appDateString2dbDateString(form.getPayed_date()));
		specification.setCurrencyName(form.getCurrencyName());

		StoreUtil.putSession(context.getRequest(), specification);
	}

	private void getCurrentFormFromBean(IActionContext context, Specification specification)
	{
		SpecificationForm form = (SpecificationForm) context.getForm();

		form.setSpc_id(specification.getSpc_id());
		form.setSpc_number(specification.getSpc_number());
		form.setSpc_date(specification.getSpc_date_formatted());
		form.setSpc_summ(StringUtil.double2appCurrencyString(specification.getSpc_summ()));
		form.setSpc_summ_nds(StringUtil.double2appCurrencyString(specification.getSpc_summ_nds()));
		form.setSpc_executed(specification.getSpc_executed());
		form.setSpc_occupied(specification.getSpc_occupied());
		form.setSpc_occupied_in_pay_shp(specification.getSpc_occupied_in_pay_shp());
		form.setDeliveryTerm(specification.getDeliveryTerm());
		if (specification.isPercentSelected())
		{
			form.setSpc_percent_or_sum_percent("1");
		}
		if (specification.isSumSelected())
		{
			form.setSpc_percent_or_sum_sum("1");
		}
		form.setSpc_delivery_percent(StringUtil.double2appCurrencyString(specification.getSpc_delivery_percent()));
		form.setSpc_delivery_sum(StringUtil.double2appCurrencyString(specification.getSpc_delivery_sum()));
		form.setSpc_delivery_date(specification.getSpc_delivery_date_formatted());
		form.setSpc_delivery_cond(specification.getSpc_delivery_cond());
		form.setSpc_add_pay_cond(specification.getSpc_add_pay_cond());
		if (specification.isCopy())
		{
			form.setSpc_fax_copy("1");
		}
		if (specification.isOriginal())
		{
			form.setSpc_original("1");
		}
		form.setSpc_montage(specification.getSpc_montage());
		form.setSpc_pay_after_montage(specification.getSpc_pay_after_montage());
		form.setSpc_group_delivery(specification.getSpc_group_delivery());
		form.setSpc_annul(specification.getSpc_annul());
		form.setSpc_annul_date(specification.getSpc_annul_date_formatted());
		form.setSpc_in_ctc(specification.getSpc_in_ctc());
		form.setSpc_comment(specification.getSpc_comment());
		form.setUser(specification.getUser());
		form.setSpc_letter1_date(specification.getSpc_letter1_date_formatted());
		form.setSpc_letter2_date(specification.getSpc_letter2_date_formatted());
		form.setSpc_letter3_date(specification.getSpc_letter3_date_formatted());
		form.setSpc_complaint_in_court_date(specification.getSpc_complaint_in_court_date_formatted());
		form.setSpc_additional_days_count(specification.getSpc_additional_days_count());

		form.setPayed_summ(specification.getPayed_summ());
		form.setPayed_date(specification.getPayed_date_formatted());
		form.setCurrencyName(specification.getCurrencyName());
	}

	public ActionForward beforeSave(IActionContext context) throws Exception
	{
		SpecificationForm form = (SpecificationForm) context.getForm();
		Contract contract = (Contract) StoreUtil.getSession(context.getRequest(), Contract.class);
		Specification specification = contract.getEmptySpecification();
		saveCurrentFormToBean(context, specification);
		specification.setAttachmentService(form.getAttachmentService());
		specification.calculateNDSRate();

		String errMsg = "";

		if (!StringUtil.isEmpty(specification.getSpc_occupied()))
		{
			SpecificationDAO.loadPayedSum(context, specification);
			specification.setShp_id("-1");
			SpecificationDAO.loadShippedSum(context, specification);
			if (specification.getSpc_summ() < specification.getPayed_summ())
			{
				errMsg = StrutsUtil.addDelimiter(errMsg);
				errMsg += StrutsUtil.getMessage(context, "error.specification.incorrect_summ_pay");
			}
			if (specification.getSpc_summ() < specification.getShipped_summ())
			{
				errMsg = StrutsUtil.addDelimiter(errMsg);
				errMsg += StrutsUtil.getMessage(context, "error.specification.incorrect_summ_shipping");
			}
		}

		if (StringUtil.isEmpty(form.getSpc_executed()) && StringUtil.isEmpty(form.getUser().getUsr_id()))
		{
			errMsg = StrutsUtil.addDelimiter(errMsg);
			errMsg += StrutsUtil.getMessage(context, "error.specification.empty_user");
		}

		if (!StringUtil.isEmpty(errMsg))
		{
			StrutsUtil.addError(context, "errors.msg", errMsg, null);
			return input(context);
		}

		return process(context);
	}

	public ActionForward input(IActionContext context) throws Exception
	{
		final SpecificationForm form = (SpecificationForm) context.getForm();

		form.getAttachmentsGrid().setDataList(form.getAttachmentService().list());

		User currentUser = UserUtil.getCurrentUser(context.getRequest());
		if (
						((currentUser.isAdmin() || currentUser.isEconomist() || currentUser.isLawyer()) && !"1".equals(form.getSpc_executed())) ||
										(currentUser.isAdmin() && "1".equals(form.getSpc_executed()))
						)
		{
			form.setReadOnlyIfNotAdminForExecuted(false);
		}
		else
		{
			form.setReadOnlyIfNotAdminForExecuted(true);
		}
		if (currentUser.isAdmin() || currentUser.isEconomist() || currentUser.isLawyer())
		{
			form.setReadOnlyIfNotAdminEconomistLawyerForExecuted(false);
			form.setShowAttach(true);
		}
		else
		{
			form.setReadOnlyIfNotAdminEconomistLawyerForExecuted(true);
			form.setShowAttach(false);
		}
		Contract contract = (Contract) StoreUtil.getSession(context.getRequest(), Contract.class);
		String sellerId = contract.getSeller().getId();
		form.setShowAttachFiles(!currentUser.isOnlyUserInLithuania() || "0".equals(sellerId) || "4".equals(sellerId));

		//Для Литовских сотрудников права доступа: read-only.
		if ((currentUser.isManager() && !currentUser.isAdmin() && !currentUser.isEconomist() && !currentUser.isLawyer()) || currentUser.isOnlyUserInLithuania() || currentUser.isOnlyLogistic())
		{
			form.setFormReadOnly(true);
		}
		else
		{
			form.setFormReadOnly(false);
		}
		if ("1".equals(form.getSpc_executed()))
		{
			form.setFormReadOnly(true);
		}

		form.setNoRoundSum(CurrencyDAO.loadByName(context, form.getCurrencyName()).getNo_round());
		if (!StringUtil.isEmpty(form.getSpc_date()))
		{
			double ndsRate = RateNDSDAO.loadForDate(context, form.getSpc_date()).getPercent();
			form.setNdsRate(StringUtil.double2appCurrencyString(ndsRate));
		}

		return context.getMapping().getInputForward();
	}

	public ActionForward insert(IActionContext context) throws Exception
	{
		Contract contract = (Contract) StoreUtil.getSession(context.getRequest(), Contract.class);
		SpecificationForm form = (SpecificationForm) context.getForm();
		Specification specification = contract.getEmptySpecification();
		specification.setCurrencyName(form.getCurrencyName());
		specification.setAttachmentService(
						DeferredAttachmentService.create(context.getRequest().getSession(),
										referencedTable, context.getMapping().findForward("backFromAttach"), null));
		specification.getAttachmentService().setRenamer(new SpecificationFileRenamer(contract, specification));
		getCurrentFormFromBean(context, specification);
		StoreUtil.putSession(context.getRequest(), specification);
		form.setAttachmentService(specification.getAttachmentService());

		DeferredAttachmentService contractAttachmentService = (DeferredAttachmentService) StoreUtil.getSession(context.getRequest(), ContractAction.CONTRACT_ATTACHMENT_SERVICE);
		List<DeferredAttachmentService.DeferredAttachment> attachmentList = contractAttachmentService.listDeleted();
		for (DeferredAttachmentService.DeferredAttachment deleted : attachmentList)
		{
			if (null != specification.getAttachmentService())
			{
				specification.getAttachmentService().fixLinksOnDeleted(deleted);
			}
		}

		form.getSpecificationPayments().clear();
		form.getSpecificationPayments().add(new SpecificationPayment(100, 0, form.getCurrencyName()));
		form.setIs_new_doc(null);
		form.setSpc_executed("");

		return input(context);
	}

	private boolean checkMandatoryForAttachmentFields(IActionContext context, Contract contract, Specification specification)
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
		if (StringUtil.isEmpty(specification.getSpc_number()))
		{
			StrutsUtil.addError(context, "error.contract.spec_num", null);
			return true;
		}
		return false;
	}

	public ActionForward edit(IActionContext context) throws Exception
	{
		SpecificationForm form = (SpecificationForm) context.getForm();
		Contract contract = (Contract) StoreUtil.getSession(context.getRequest(), Contract.class);
		form.setIs_new_doc("no");
		form.setOld_number(form.getSpc_number());

		Specification specificationFind = contract.findSpecification(form.getSpc_number());
		Specification specification = new Specification(specificationFind);
		if (specification.isSpcFromCpr())
		{
			specification.setAttachmentService(
							DeferredAttachmentService.create(context.getRequest().getSession(),
											referencedTable, context.getMapping().findForward("backFromAttach"), null));
			specification.getAttachmentService().setRenamer(new SpecificationFileRenamer(contract, specification));
			form.setAttachmentService(specification.getAttachmentService());

			DeferredAttachmentService contractAttachmentService = (DeferredAttachmentService) StoreUtil.getSession(context.getRequest(), ContractAction.CONTRACT_ATTACHMENT_SERVICE);
			List<DeferredAttachmentService.DeferredAttachment> attachmentList = contractAttachmentService.listDeleted();
			for (DeferredAttachmentService.DeferredAttachment deleted : attachmentList)
			{
				if (null != specification.getAttachmentService())
				{
					specification.getAttachmentService().fixLinksOnDeleted(deleted);
				}
			}
			specification.setSpcFromCpr(false);
		}
		else
		{
			if (specification.getAttachmentService() == null)
			{
				specification.setAttachmentService(
								DeferredAttachmentService.create(context.getRequest().getSession(),
												referencedTable, Integer.parseInt(specification.getSpc_id()),
												context.getMapping().findForward("backFromAttach"), context.getMapping().findForward("backFromAttachList")));
				specification.getAttachmentService().setRenamer(new SpecificationFileRenamer(contract, specification));
			}
			DeferredAttachmentService contractAttachmentService = (DeferredAttachmentService) StoreUtil.getSession(context.getRequest(), ContractAction.CONTRACT_ATTACHMENT_SERVICE);
			List<DeferredAttachmentService.DeferredAttachment> attachmentList = contractAttachmentService.listDeleted();
			for (DeferredAttachmentService.DeferredAttachment deleted : attachmentList)
			{
				if (null != specification.getAttachmentService())
				{
					specification.getAttachmentService().fixLinksOnDeleted(deleted);
				}
			}
			form.setAttachmentService(specification.getAttachmentService().clone());
			DeferredAttachmentService.set(context.getRequest().getSession(), form.getAttachmentService());
		}

		SpecificationDAO.loadPayedDate(context, specification);
		List<Specification.Payment> payments = DAOUtils.fillList(context, "select-payments-for-spec", specification, Specification.Payment.class, null, null);
		specification.setPayments(payments);
		specification.setCurrencyName(form.getCurrencyName());
		specification.setDeliveryTermNameById(specification.getDeliveryTerm().getId());
		specification.calculatePaymentsDescription(specification.getSpecificationPayments());
		form.getSpecificationPayments().clear();
		for (SpecificationPayment specificationPayment : specification.getSpecificationPayments())
		{
			specificationPayment.setCurrencyName(form.getCurrencyName());
			form.getSpecificationPayments().add(new SpecificationPayment(specificationPayment));
		}

		getCurrentFormFromBean(context, specification);
		StoreUtil.putSession(context.getRequest(), specification);

		return input(context);
	}

	public ActionForward attach(IActionContext context) throws Exception
	{
		SpecificationForm form = (SpecificationForm) context.getForm();
		Contract contract = (Contract) StoreUtil.getSession(context.getRequest(), Contract.class);

		Specification specification = contract.findSpecification(form.getSpc_number());
		if (checkMandatoryForAttachmentFields(context, contract, specification))
		{
			return context.getMapping().findForward("backFromAttachList");
		}

		if (specification.getAttachmentService() == null)
		{
			specification.setAttachmentService(
							DeferredAttachmentService.create(context.getRequest().getSession(),
											referencedTable, specification.isSpcFromCpr() ? null : Integer.parseInt(specification.getSpc_id()),
											context.getMapping().findForward("backFromAttach"), context.getMapping().findForward("backFromAttachList")));
			specification.getAttachmentService().setRenamer(new SpecificationFileRenamer(contract, specification));
		}
		DeferredAttachmentService.set(context.getRequest().getSession(), specification.getAttachmentService());

		return context.getMapping().findForward("attach");
	}

	public ActionForward back(IActionContext context) throws Exception
	{
		SpecificationForm form = (SpecificationForm) context.getForm();
		form.getAttachmentService().rollback();
		form.setAttachmentService(null);

		//TODO: разобраться, нужно ли здесь
		//DeferredAttachmentService.removeLast(context.getRequest().getSession());
		return context.getMapping().findForward("back");
	}

	public ActionForward reload(IActionContext context) throws Exception
	{
		Specification specification = (Specification) StoreUtil.getSession(context.getRequest(), Specification.class);
		saveCurrentFormToBean(context, specification);

		return input(context);
	}

	public ActionForward ajaxReloadPrices(IActionContext context) throws Exception
	{
		Specification specification = (Specification) StoreUtil.getSession(context.getRequest(), Specification.class);
		Specification specificationCheck = new Specification(specification);
		String spcSum = context.getRequest().getParameter("spcSum");
		if (!StringUtil.isEmpty(spcSum))
		{
			specificationCheck.setSpc_summ(StringUtil.appCurrencyString2double(spcSum));
			SpecificationDAO.loadPayedDate(context, specificationCheck);
			SpecificationForm form = (SpecificationForm) context.getForm();
			form.setPayed_date(specificationCheck.getPayed_date_formatted());
			StrutsUtil.setAjaxResponse(context, form.getPayed_date_formatted(), false);
		}

		return context.getMapping().findForward("ajax");
	}

	public ActionForward ajaxReloadDate(IActionContext context) throws Exception
	{
		String spcDate = context.getRequest().getParameter("spcDate");
		if (!StringUtil.isEmpty(spcDate))
		{
			double ndsRate = RateNDSDAO.loadForDate(context, spcDate).getPercent();
			SpecificationForm form = (SpecificationForm) context.getForm();
			form.setNdsRate(StringUtil.double2appCurrencyString(ndsRate));
			StrutsUtil.setAjaxResponse(context, form.getNdsRate(), false);
		}

		return context.getMapping().findForward("ajax");
	}

	public ActionForward ajaxReloadReminder(IActionContext context) throws Exception
	{
		String percentOrSum = context.getRequest().getParameter("percentOrSum");
		String deliveryTermId = context.getRequest().getParameter("deliveryTermId");
		String percent = context.getRequest().getParameter("percent");
		String sum = context.getRequest().getParameter("sum");
		String deliveryDate = context.getRequest().getParameter("deliveryDate");

		SpecificationForm form = (SpecificationForm) context.getForm();
		if ("0".equals(percentOrSum))
		{
			form.setSpc_percent_or_sum_percent("1");
		}
		if ("1".equals(percentOrSum))
		{
			form.setSpc_percent_or_sum_sum("1");
		}
		form.getDeliveryTerm().setId(deliveryTermId);
		form.setSpc_delivery_percent(percent);
		form.setSpc_delivery_sum(sum);
		form.setSpc_delivery_date(deliveryDate);
		String reminder;
		if (form.isShowRemainder())
		{
			reminder = StrutsUtil.getMessage(context, "Specification.spc_remainder_txt");
		}
		else
		{
			reminder = "&nbsp;";
		}
		StrutsUtil.setAjaxResponse(context, reminder, false);

		return context.getMapping().findForward("ajax");
	}

	public ActionForward ajaxCalculateDeliveryDate(IActionContext context) throws Exception
	{
		SpecificationForm form = (SpecificationForm) context.getForm();
		Specification specification = (Specification) StoreUtil.getSession(context.getRequest(), Specification.class);

		String additionalDaysCount = context.getRequest().getParameter("additionalDaysCount");
		if (!StringUtil.isEmpty(additionalDaysCount))
		{
			form.setSpc_additional_days_count(additionalDaysCount);
			specification.setSpc_additional_days_count(additionalDaysCount);

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(StringUtil.appDateString2Date(form.getPayed_date()));
			calendar.add(Calendar.DATE, Integer.parseInt(additionalDaysCount));

			StrutsUtil.setAjaxResponse(context, StringUtil.date2appDateString(calendar.getTime()), false);
		}

		return context.getMapping().findForward("ajax");
	}

	public ActionForward process(IActionContext context) throws Exception
	{
		SpecificationForm form = (SpecificationForm) context.getForm();
		Contract contract = (Contract) StoreUtil.getSession(context.getRequest(), Contract.class);
		Specification specification = contract.getEmptySpecification();
		saveCurrentFormToBean(context, specification);
		specification.setAttachmentService(form.getAttachmentService());
		if (specification.getAttachmentService() != null)
		{
			specification.setAttachmentsCount(Integer.toString(form.getAttachmentsGrid().getDataList().size()));
		}

		specification.getSpecificationPayments().clear();
		int i = 0;
		while (i < form.getSpecificationPayments().size())
		{
			SpecificationPayment specificationPayment = form.getSpecificationPayments().get(i);
			if (specificationPayment.getSpp_percent() == 0 && specificationPayment.getSpp_sum() == 0 && StringUtil.isEmpty(specificationPayment.getSpp_date()))
			{
				form.getSpecificationPayments().remove(i);
			}
			else
			{
				specification.getSpecificationPayments().add(new SpecificationPayment(specificationPayment));
				i++;
			}
		}
		if (StringUtil.isEmpty(form.getIs_new_doc()))
		{
			contract.insertSpecification(specification);
		}
		else
		{
			contract.updateSpecification(form.getOld_number(), specification);
		}
		return context.getMapping().findForward("back");
	}

	public ActionForward deferredAttach(IActionContext context) throws Exception
	{
		Contract contract = (Contract) StoreUtil.getSession(context.getRequest(), Contract.class);
		Specification specification = (Specification) StoreUtil.getSession(context.getRequest(), Specification.class);
		saveCurrentFormToBean(context, specification);
		if (checkMandatoryForAttachmentFields(context, contract, specification))
		{
			return input(context);
		}

		return context.getMapping().findForward("multipleAttach");
	}

	public ActionForward deferredAttachCopy(IActionContext context) throws Exception
	{
		SpecificationForm form = (SpecificationForm) context.getForm();
		Contract contract = (Contract) StoreUtil.getSession(context.getRequest(), Contract.class);
		Specification specification = (Specification) StoreUtil.getSession(context.getRequest(), Specification.class);
		saveCurrentFormToBean(context, specification);
		if (checkMandatoryForAttachmentFields(context, contract, specification))
		{
			return input(context);
		}

		AtomicReference<DeferredAttachmentService> contractAttachmentService = new AtomicReference<DeferredAttachmentService>();
		contractAttachmentService.set((DeferredAttachmentService) StoreUtil.getSession(context.getRequest(), ContractAction.CONTRACT_ATTACHMENT_SERVICE));
		form.getAttachmentService().addCopy(contractAttachmentService.get().find(form.getCopy_attachment_id()));
		form.setCopy_attachment_id(null);
		form.setCopy_attachment_name("");
		return input(context);
	}

	public ActionForward deleteAttachment(IActionContext context) throws Exception
	{
		Specification specification = (Specification) StoreUtil.getSession(context.getRequest(), Specification.class);
		saveCurrentFormToBean(context, specification);
		SpecificationForm form = (SpecificationForm) context.getForm();
		form.getAttachmentService().delete(form.getAttachmentId());
		return input(context);
	}

	public ActionForward downloadAttachment(IActionContext context) throws Exception
	{
		Specification specification = (Specification) StoreUtil.getSession(context.getRequest(), Specification.class);
		saveCurrentFormToBean(context, specification);
		SpecificationForm form = (SpecificationForm) context.getForm();
		try
		{
			form.getAttachmentService().download(form.getAttachmentId(), context.getRequest(), context.getResponse());
			return null;
		}
		catch (AttachmentException e)
		{
			StrutsUtil.addError(context, e.getMessage(), e);
			return input(context);
		}
	}

	public ActionForward backFromAttach(IActionContext context) throws Exception
	{
		Specification specification = (Specification) StoreUtil.getSession(context.getRequest(), Specification.class);
		getCurrentFormFromBean(context, specification);
		return input(context);
	}

	public ActionForward ajaxSpecificationPaymentsGrid(IActionContext context) throws Exception
	{
		return context.getMapping().findForward("ajaxSpecificationPaymentsGrid");
	}

	public ActionForward ajaxAddToPaymentGrid(IActionContext context) throws Exception
	{
		SpecificationForm form = (SpecificationForm) context.getForm();
		form.getSpecificationPayments().add(new SpecificationPayment(0, 0, form.getCurrencyName()));
		Specification specification = (Specification) StoreUtil.getSession(context.getRequest(), Specification.class);
		specification.calculatePaymentsDescription(form.getSpecificationPayments());

		return ajaxSpecificationPaymentsGrid(context);
	}

	public ActionForward ajaxRemoveFromPaymentGrid(IActionContext context) throws Exception
	{
		SpecificationForm form = (SpecificationForm) context.getForm();

		int id = Integer.parseInt(context.getRequest().getParameter("id"));
		form.getSpecificationPayments().remove(id);
		if (form.getSpecificationPayments().size() == 1)
		{
			form.getSpecificationPayments().get(0).setSpp_percent(100);
			form.getSpecificationPayments().get(0).setSpp_sum(StringUtil.appCurrencyString2double(form.getSpc_summ()));
		}
		Specification specification = (Specification) StoreUtil.getSession(context.getRequest(), Specification.class);
		specification.calculatePaymentsDescription(form.getSpecificationPayments());

		return ajaxSpecificationPaymentsGrid(context);
	}

	public ActionForward ajaxRecalculatePaymentGrid(IActionContext context) throws Exception
	{
		SpecificationForm form = (SpecificationForm) context.getForm();
		Specification specification = (Specification) StoreUtil.getSession(context.getRequest(), Specification.class);
		specification.calculatePaymentsDescription(form.getSpecificationPayments());

		return ajaxSpecificationPaymentsGrid(context);
	}

}
