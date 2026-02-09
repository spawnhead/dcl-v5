package net.sam.dcl.action;

import net.sam.dcl.controller.actions.DBAction;
import net.sam.dcl.controller.*;
import net.sam.dcl.controller.processor.ActionHandler;
import net.sam.dcl.report.excel.Grid2Excel;
import net.sam.dcl.util.*;
import net.sam.dcl.form.CommercialProposalsForm;
import net.sam.dcl.beans.*;
import net.sam.dcl.taglib.table.IReadOnlyChecker;
import net.sam.dcl.taglib.table.ReadOnlyCheckerContext;
import net.sam.dcl.taglib.table.model.PageableDataHolder;
import net.sam.dcl.taglib.table.impl.AlwaysReadonlyChecker;
import net.sam.dcl.dao.CommercialProposalDAO;
import net.sam.dcl.dao.MessageDAO;
import net.sam.dcl.config.Config;
import org.apache.struts.action.ActionForward;

import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
import java.util.List;

public class CommercialProposalsAction extends DBAction implements IDispatchable, IFormAutoSave, IProcessBefore
{
	public ActionForward processBefore(IActionContext context) throws Exception
	{
		context.getActionProcessor().addActionHandler("grid", PageableDataHolder.NEXT_PAGE, new ActionHandler()
		{
			public ActionForward process(IActionContext context) throws Exception
			{
				CommercialProposalsForm form = (CommercialProposalsForm) context.getForm();
				form.getGrid().incPage();
				return internalFilter(context);
			}
		});
		context.getActionProcessor().addActionHandler("grid", PageableDataHolder.PREV_PAGE, new ActionHandler()
		{
			public ActionForward process(IActionContext context) throws Exception
			{
				CommercialProposalsForm form = (CommercialProposalsForm) context.getForm();
				form.getGrid().decPage();
				return internalFilter(context);
			}
		});
		return null;
	}

	public ActionForward filter(IActionContext context) throws Exception
	{
		CommercialProposalsForm form = (CommercialProposalsForm) context.getForm();
		form.setShowCloneMsg(false);
		form.getGrid().setPage(1);
		return internalFilter(context);
	}

	public ActionForward internalFilter(IActionContext context) throws Exception
	{
		final CommercialProposalsForm form = (CommercialProposalsForm) context.getForm();

		DAOUtils.fillGrid(context, form.getGrid(), "select-commercial_proposals", CommercialProposalsForm.CommercialProposal.class, null, null);

		context.getRequest().setAttribute("alwaysReadonly", new AlwaysReadonlyChecker());

		final User currentUser = UserUtil.getCurrentUser(context.getRequest());
		context.getRequest().setAttribute("blockChecker", new IReadOnlyChecker()
		{
			public boolean check(ReadOnlyCheckerContext ctx) throws Exception
			{
				CommercialProposalsForm.CommercialProposal record = (CommercialProposalsForm.CommercialProposal) ctx.getBean();
				return !(currentUser.isAdmin() ||
								(StringUtil.isEmpty(record.getCpr_block()) && (currentUser.isEconomist() || currentUser.getUsr_id().equals(record.getUsr_id_create()))));
			}
		});

		context.getRequest().setAttribute("blockCheckerPrice", new IReadOnlyChecker()
		{
			public boolean check(ReadOnlyCheckerContext ctx) throws Exception
			{
				CommercialProposalsForm.CommercialProposal record = (CommercialProposalsForm.CommercialProposal) ctx.getBean();
				return !(!"1".equals(record.getCpr_check_price()) && (currentUser.isAdmin() || currentUser.isEconomist()));
			}
		});

		// менеджер - тока свой отдел
		context.getRequest().setAttribute("editCloneChecker", new IReadOnlyChecker()
		{
			public boolean check(ReadOnlyCheckerContext ctx) throws Exception
			{
				CommercialProposalsForm.CommercialProposal record = (CommercialProposalsForm.CommercialProposal) ctx.getBean();
				return currentUser.isOnlyManager() &&
								!currentUser.getDepartment().getId().equals(record.getDep_id());
			}
		});

		return context.getMapping().getInputForward();
	}

	public ActionForward block(IActionContext context) throws Exception
	{
		CommercialProposalsForm form = (CommercialProposalsForm) context.getForm();
		CommercialProposal commercialProposal = new CommercialProposal();
		commercialProposal.setCpr_id(form.getCpr_id());
		if ("1".equals(form.getBlock()))
		{
			commercialProposal.setCpr_block("");
		}
		else
		{
			commercialProposal.setCpr_block("1");
		}
		CommercialProposalDAO.saveBlock(context, commercialProposal);

		return internalFilter(context);
	}

	public ActionForward checkPrice(IActionContext context) throws Exception
	{
		CommercialProposalsForm form = (CommercialProposalsForm) context.getForm();
		CommercialProposal commercialProposal = new CommercialProposal();
		commercialProposal.setCpr_id(form.getCpr_id());
		commercialProposal.setCpr_check_price("1");
		commercialProposal.setCpr_block("1");
		commercialProposal.setCpr_check_price_date(StringUtil.date2dbDateTimeString(StringUtil.getCurrentDateTime()));
		commercialProposal.setUsr_id_check_price(UserUtil.getCurrentUser(context.getRequest()).getUsr_id());
		CommercialProposalDAO.saveCheckPrice(context, commercialProposal);

		CommercialProposalDAO.load(context, commercialProposal);
		UserLink userLink = new UserLink();
		userLink.setUrl("/dcl/CommercialProposalsAction.do");
		userLink.setParameters("?dispatch=filter&number=" + commercialProposal.getCpr_number());
		MessageDAO.deleteConditionForContractMessagesForEconomist(context, userLink);

		return internalFilter(context);
	}

	public ActionForward clone(IActionContext context) throws Exception
	{
		CommercialProposalsForm form = (CommercialProposalsForm) context.getForm();
		form.setShowCloneMsg(true);
		return internalFilter(context);
	}

	public ActionForward cloneLikeOldVersion(IActionContext context) throws Exception
	{
		return context.getMapping().findForward("cloneLikeOldVersion");
	}

	public ActionForward cloneLikeNewVersion(IActionContext context) throws Exception
	{
		return context.getMapping().findForward("cloneLikeNewVersion");
	}

	public ActionForward input(IActionContext context) throws Exception
	{
		CommercialProposalsForm form = (CommercialProposalsForm) context.getForm();
		form.setContractor(new Contractor());
		form.setNumber("");
		form.setSum_max_formatted("");
		form.setSum_min_formatted("");
		form.setDepartment(new Department());
		form.setUser(new User());
		form.setStuffCategory(new StuffCategory());
		form.setCpr_proposal_declined_in("");
		form.setCpr_proposal_received_flag_in("");
		form.setShowCloneMsg(false);

		if (Config.haveNumber(Constants.dayCountDeductCommercialProposals))
		{
			int dayCount = Config.getNumber(Constants.dayCountDeductCommercialProposals, 10);
			if (dayCount > 0)
			{
				Calendar calendarBegin = Calendar.getInstance();
				calendarBegin.add(Calendar.DATE, -dayCount);
				form.setDate_begin(StringUtil.date2appDateString(calendarBegin.getTime()));
				Calendar calendarEnd = Calendar.getInstance();
				calendarEnd.add(Calendar.DATE, 1);
				form.setDate_end(StringUtil.date2appDateString(calendarEnd.getTime()));
			}
			else
			{
				form.setDate_begin("");
				form.setDate_end("");
			}
		}
		else
		{
			form.setDate_begin("");
			form.setDate_end("");
		}

		return internalFilter(context);
	}

	public ActionForward restore(IActionContext context) throws Exception
	{
		restoreForm(context);
		CommercialProposalsForm form = (CommercialProposalsForm) context.getForm();
		form.setShowCloneMsg(false);
		return internalFilter(context);
	}

	public ActionForward generateExcel(IActionContext context) throws Exception
	{
		CommercialProposalsForm formContext = (CommercialProposalsForm) context.getForm();
		CommercialProposalsForm form = new CommercialProposalsForm();

		form.setNumber(formContext.getNumber());
		form.setContractor(formContext.getContractor());
		form.setDate_begin(formContext.getDate_begin());
		form.setDate_end(formContext.getDate_end());
		form.setSum_min_formatted(formContext.getSum_min_formatted());
		form.setSum_max_formatted(formContext.getSum_max_formatted());
		form.setUser(formContext.getUser());
		form.setDepartment(formContext.getDepartment());
		form.setStuffCategory(formContext.getStuffCategory());

		List<CommercialProposalsForm.CommercialProposal> lst = DAOUtils.fillList(context, "select-commercial_proposals", form, CommercialProposalsForm.CommercialProposal.class, null, null);

		Grid2Excel grid2Excel = new Grid2Excel("Commercial Proposals list");
		grid2Excel.doGrid2Excel(context, toExcelGrid(context, lst));
		return null;
	}

	List toExcelGrid(IActionContext context, List proposals)
	{
		List<Object> rows = new ArrayList<Object>();

		List<Object> header = new ArrayList<Object>();
		try
		{
			header.add(StrutsUtil.getMessage(context, "CommercialProposals.number"));
			header.add(StrutsUtil.getMessage(context, "CommercialProposals.date"));
			header.add(StrutsUtil.getMessage(context, "CommercialProposals.contractor"));
			header.add(StrutsUtil.getMessage(context, "CommercialProposals.sum"));
			header.add(StrutsUtil.getMessage(context, "CommercialProposals.currency"));
			header.add(StrutsUtil.getMessage(context, "CommercialProposals.stuffCategory"));
			header.add(StrutsUtil.getMessage(context, "CommercialProposals.reservedState"));
			header.add(StrutsUtil.getMessage(context, "CommercialProposals.currentStatus"));
			header.add(StrutsUtil.getMessage(context, "CommercialProposals.user"));
			header.add(StrutsUtil.getMessage(context, "CommercialProposals.department"));

			rows.add(header);

			for (Object proposal : proposals)
			{
				CommercialProposalsForm.CommercialProposal commercialProposal = (CommercialProposalsForm.CommercialProposal) proposal;
				List<Object> record = new ArrayList<Object>();

				record.add(commercialProposal.getCpr_number());
				record.add(commercialProposal.getCpr_date_date());
				record.add(commercialProposal.getCpr_contractor());
				record.add(commercialProposal.getCprSumFormatted());
				record.add(commercialProposal.getCpr_currency());
				record.add(commercialProposal.getCpr_stf_name());
				record.add(commercialProposal.getReservedState());

				if ("1".equals(commercialProposal.getCpr_proposal_declined()))
					record.add(StrutsUtil.getMessage(context, "CommercialProposals.declined"));
				else if ("1".equals(commercialProposal.getCpr_proposal_received_flag()))
					record.add(StrutsUtil.getMessage(context, "CommercialProposals.accepted"));
				else
					record.add("");

				record.add(commercialProposal.getCpr_user());
				record.add(commercialProposal.getCpr_department());

				rows.add(record);
			}
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}

		return rows;
	}
}
