package net.sam.dcl.action;

import net.sam.dcl.controller.actions.DBAction;
import net.sam.dcl.controller.IDispatchable;
import net.sam.dcl.controller.IFormAutoSave;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.form.CalculationStateForm;
import net.sam.dcl.beans.*;
import net.sam.dcl.locking.SyncObject;
import net.sam.dcl.util.*;
import net.sam.dcl.taglib.table.*;
import net.sam.dcl.taglib.table.model.impl.HolderImplUsingList;
import net.sam.dcl.report.excel.Grid2Excel;
import net.sam.dcl.dao.ContractorDAO;
import net.sam.dcl.dao.ContractDAO;
import net.sam.dcl.locking.LockedRecords;
import org.apache.struts.action.ActionForward;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class CalculationStateAction extends DBAction implements IDispatchable, IFormAutoSave
{
	public static final String CONTRACT_LOCK_NAME = "Contract";
	public static final String SHIPPING_LOCK_NAME = "Shipping";
	public static final String PAYMENT_LOCK_NAME = "Payment";

	private void saveCurrentFormToBean(IActionContext context)
	{
		CalculationStateForm form = (CalculationStateForm) context.getForm();

		CalculationState calculationState = (CalculationState) StoreUtil.getSession(context.getRequest(), CalculationState.class);

		calculationState.setContractor(form.getContractorCalcState());
		calculationState.setReportType(form.getReportType());
		calculationState.setCurrency(form.getCurrencyCalcState());
		calculationState.setUser(form.getUserCalcState());
		calculationState.setDepartment(form.getDepartmentCalcState());
		try
		{
			if (!StringUtil.isEmpty(form.getContract().getCon_id()))
			{
				if ("-1".equals(form.getContract().getCon_id()))
				{
					calculationState.setContract(new Contract(StrutsUtil.getMessage(context, "list.all_id"), StrutsUtil.getMessage(context, "list.all")));
				}
				else
				{
					calculationState.setContract(ContractDAO.load(context, form.getContract().getCon_id(), false));
				}
			}
			else
			{
				calculationState.setContract(new Contract());
			}
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
		}
		calculationState.setView_pay_cond(form.getView_pay_cond());
		calculationState.setView_delivery_cond(form.getView_delivery_cond());
		calculationState.setView_expiration(form.getView_expiration());
		calculationState.setView_complaint(form.getView_complaint());
		calculationState.setView_comment(form.getView_comment());
		calculationState.setView_manager(form.getView_manager());
		calculationState.setView_stuff_category(form.getView_stuff_category());

		calculationState.setInclude_all_specs(form.getInclude_all_specs());
		calculationState.setEarliest_doc_date(form.getEarliest_doc_date());
		calculationState.setNot_include_if_earliest(form.getNot_include_if_earliest());
		calculationState.setNot_include_zero(form.getNot_include_zero());
		calculationState.setNot_show_annul(form.getNot_show_annul());
		calculationState.setNotShowExpiredContractZeroBalance(form.getNotShowExpiredContractZeroBalance());
		calculationState.setSeller(form.getSellerCalcState());

		calculationState.setCalculationStateLines(form.getGridCalcState().getDataList());

		StoreUtil.putSession(context.getRequest(), calculationState);
	}

	private void getCurrentFormFromBean(IActionContext context)
	{
		CalculationStateForm form = (CalculationStateForm) context.getForm();

		CalculationState calculationState = (CalculationState) StoreUtil.getSession(context.getRequest(), CalculationState.class);

		form.setContractorCalcState(calculationState.getContractor());
		form.setReportType(calculationState.getReportType());
		form.setCurrencyCalcState(calculationState.getCurrency());
		form.setContract(calculationState.getContract());
		form.setUserCalcState(calculationState.getUser());
		form.setDepartmentCalcState(calculationState.getDepartment());

		form.setView_pay_cond(calculationState.getView_pay_cond());
		form.setView_delivery_cond(calculationState.getView_delivery_cond());
		form.setView_expiration(calculationState.getView_expiration());
		form.setView_complaint(calculationState.getView_complaint());
		form.setView_comment(calculationState.getView_comment());
		form.setView_manager(calculationState.getView_manager());
		form.setView_stuff_category(calculationState.getView_stuff_category());

		form.setInclude_all_specs(calculationState.getInclude_all_specs());
		form.setEarliest_doc_date(calculationState.getEarliest_doc_date());
		form.setNot_include_if_earliest(calculationState.getNot_include_if_earliest());
		form.setNot_include_zero(calculationState.getNot_include_zero());
		form.setNot_show_annul(calculationState.getNot_show_annul());
		form.setSellerCalcState(calculationState.getSeller());

		form.getGridCalcState().setDataList(calculationState.getCalculationStateLines());
	}

	public ActionForward filter(IActionContext context) throws Exception
	{
		filter_internal(context);

		return show(context);
	}

	protected void filter_internal(IActionContext context) throws Exception
	{
		final CalculationStateForm form = (CalculationStateForm) context.getForm();
		if (!form.isCanForm() && "1".equals(form.getIsDebit()))
		{
			form.getContractorCalcState().setId(StrutsUtil.getMessage(context, "list.all_id"));
			form.getContractorCalcState().setName(StrutsUtil.getMessage(context, "list.all"));
		}

		User user = UserUtil.getCurrentUser(context.getRequest());
		if (user.isAdmin() || user.isEconomist() || user.isLawyer())
		{
			form.setReadonlyEditCtrButton(false);
		}
		else
		{
			form.setReadonlyEditCtrButton(true);
		}

		if (StringUtil.isEmpty(form.getContractorCalcState().getName()))
		{
			return;
		}

		if (form.isCanForm())
		{
			if (StringUtil.isEmpty(form.getCurrencyCalcState().getName()))
			{
				form.getCurrencyCalcState().setId(null);
			}
			//если не отмечен чекбокс - убираем дату
			if (StringUtil.isEmpty(form.getNot_include_if_earliest()))
			{
				form.setEarliest_doc_date("");
			}

			if ("1".equals(form.getIsDebit()))
			{
				DAOUtils.fillGrid(context, form.getGridCalcState(), "select-calculation_state_debet", CalculationStateLine.class, null, null);
			}
			else
			{
				DAOUtils.fillGrid(context, form.getGridCalcState(), "select-calculation_state", CalculationStateLine.class, null, null);
			}
			saveCurrentFormToBean(context);
			CalculationState calculationState = (CalculationState) StoreUtil.getSession(context.getRequest(), CalculationState.class);
			if (!StringUtil.isEmpty(form.getView_comment()))
			{
				for (int i = 0; i < calculationState.getCalculationStateLines().size(); i++)
				{
					CalculationStateLine calculationStateLine = (CalculationStateLine) calculationState.getCalculationStateLines().get(i);
					if (calculationStateLine.getCon_comment_flag() > 0)
					{
						DAOUtils.load(context, "contract-load-comment", calculationStateLine, null);
					}
					if (calculationStateLine.getSpc_comment_flag() > 0)
					{
						DAOUtils.load(context, "specification-load-comment", calculationStateLine, null);
					}
				}
			}
			calculationState.calculate();
			StoreUtil.putSession(context.getRequest(), calculationState);
			form.getGridCalcState().setDataList(calculationState.getCalculationStateLines());

			form.setContract(calculationState.getContract());
		}
		else
		{
			form.getGridCalcState().setDataList(new HolderImplUsingList().getDataList());
		}
	}

	public ActionForward generate(IActionContext context) throws Exception
	{
		CalculationStateForm form = (CalculationStateForm) context.getForm();
		form.setCanForm(true);

		filter_internal(context);
		return show(context);
	}

	public ActionForward generateGrid(IActionContext context) throws Exception
	{
		CalculationStateForm form = (CalculationStateForm) context.getForm();
		form.setCanForm(true);

		filter_internal(context);
		return context.getMapping().findForward("showGrid");
	}

	public ActionForward show(IActionContext context) throws Exception
	{
		prepareForShow(context, false);
		return context.getMapping().getInputForward();
	}

	public ActionForward showGrid(IActionContext context) throws Exception
	{
		prepareForShow(context, true);
		return context.getMapping().findForward("calculationStateGrid");
	}

	private void prepareForShow(final IActionContext context, final boolean justGrid)
	{
		final CalculationStateForm form = (CalculationStateForm) context.getForm();
		context.getRequest().setAttribute("style-checker", new IStyleClassChecker()
		{
			public String check(StyleClassCheckerContext context)
			{
				CalculationStateLine calculationStateLine =
								(CalculationStateLine) form.getGridCalcState().getDataList().get(context.getTable().getRecordCounter() - 1);
				if (calculationStateLine.isItog_line())
				{
					return "bold-cell";
				}
				if (calculationStateLine.isCtr_line())
				{
					return "bold-orange-cell";
				}
				return "";
			}
		});

		context.getRequest().setAttribute("style-checker-red", new IStyleClassChecker()
		{
			public String check(StyleClassCheckerContext context)
			{
				CalculationStateLine calculationStateLine =
								(CalculationStateLine) form.getGridCalcState().getDataList().get(context.getTable().getRecordCounter() - 1);
				if (calculationStateLine.isItog_line())
				{
					return "bold-cell-red";
				}
				return "";
			}
		});

		context.getRequest().setAttribute("style-checker-blue", new IStyleClassChecker()
		{
			public String check(StyleClassCheckerContext context)
			{
				CalculationStateLine calculationStateLine =
								(CalculationStateLine) form.getGridCalcState().getDataList().get(context.getTable().getRecordCounter() - 1);
				if (calculationStateLine.isItog_line())
				{
					return "bold-cell-blue";
				}
				return "";
			}
		});

		context.getRequest().setAttribute("style-checker-shp_green", new IStyleClassChecker()
		{
			public String check(StyleClassCheckerContext context)
			{
				CalculationStateLine calculationStateLine =
								(CalculationStateLine) form.getGridCalcState().getDataList().get(context.getTable().getRecordCounter() - 1);
				if (!StringUtil.isEmpty(calculationStateLine.getShp_closed()))
				{
					return "cell-green";
				}
				return "";
			}
		});

		context.getRequest().setAttribute("style-checker-pay_green", new IStyleClassChecker()
		{
			public String check(StyleClassCheckerContext context)
			{
				CalculationStateLine calculationStateLine =
								(CalculationStateLine) form.getGridCalcState().getDataList().get(context.getTable().getRecordCounter() - 1);
				if (!StringUtil.isEmpty(calculationStateLine.getPay_closed()))
				{
					return "cell-green";
				}
				return "";
			}
		});

		context.getRequest().setAttribute("style-checker-pay_light_orange", new IStyleClassChecker()
		{
			public String check(StyleClassCheckerContext context)
			{
				CalculationStateLine calculationStateLine =
								(CalculationStateLine) form.getGridCalcState().getDataList().get(context.getTable().getRecordCounter() - 1);
				if (StringUtil.isEmpty(calculationStateLine.getPay_block()) && !StringUtil.isEmpty(calculationStateLine.getPay_date()) && StringUtil.isEmpty(form.getIsDebit()))
				{
					return "light_orange-cell";
				}
				return "";
			}
		});

		final User currentUser = UserUtil.getCurrentUser(context.getRequest());
		context.getRequest().setAttribute("linkReadonlyContract", new IReadOnlyChecker()
		{
			public boolean check(ReadOnlyCheckerContext context) throws Exception
			{
				CalculationStateLine calculationStateLine = (CalculationStateLine) form.getGridCalcState().getDataList().get(context.getTable().getRecordCounter() - 1);

				return currentUser.isOnlyManager() && !currentUser.isChiefDepartment() && !calculationStateLine.getUsr_id_list_con().contains(currentUser.getUsr_id() + ReportDelimiterConsts.html_separator) || currentUser.isOnlyManager() && currentUser.isChiefDepartment() && !calculationStateLine.getUsr_id_list_con().contains(currentUser.getUsr_id() + ReportDelimiterConsts.html_separator) && !calculationStateLine.getDep_id_list_con().contains(currentUser.getDepartment().getId() + ReportDelimiterConsts.html_separator) || justGrid || calculationStateLine.isItog_line() || calculationStateLine.isCommonBalanceLine() || calculationStateLine.isBalanceLine() || calculationStateLine.isCtr_line() || calculationStateLine.isDeviateLine();

			}
		});

		context.getRequest().setAttribute("linkReadonlyShipping", new IReadOnlyChecker()
		{
			public boolean check(ReadOnlyCheckerContext context) throws Exception
			{
				if (currentUser.isOnlyUserInLithuania())
				{
					return true;
				}

				CalculationStateLine calculationStateLine = (CalculationStateLine) form.getGridCalcState().getDataList().get(context.getTable().getRecordCounter() - 1);

				return currentUser.isOnlyManager() && !calculationStateLine.getUsr_id_list_shp().contains(currentUser.getUsr_id() + ReportDelimiterConsts.html_separator) || justGrid || calculationStateLine.isItog_line() || calculationStateLine.isCommonBalanceLine() || calculationStateLine.isBalanceLine() || calculationStateLine.isCtr_line() || calculationStateLine.isDeviateLine();

			}
		});

		context.getRequest().setAttribute("row-parser", new IRowParser()
		{
			boolean processed = false;

			public int parse(RowParserContext rpContext) throws Exception
			{
				TableTag table = (TableTag) rpContext.getRowTag().getParent();
				CalculationStateLine calculationStateLine = (CalculationStateLine) rpContext.getBean();
				if (!processed &&
								(
												StrutsUtil.getMessage(context, "CalculationState.balance_all_expand").equals(calculationStateLine.getCon_number()) ||
																StrutsUtil.getMessage(context, "CalculationState.balance_all").equals(calculationStateLine.getCon_number())
								)
								)
				{
					processed = true;
					String str = "<td colspan=100 class='line-separator'></td>";
					rpContext.getExtraRowBefore().append(str);
					table.setRecordCounter(table.getRecordCounter() - 1);
				}
				return IRowParser.EVAL_ROW_INCLUDE;
			}
		});

		CalculationStateForm formMod = (CalculationStateForm) context.getForm();
		User user = UserUtil.getCurrentUser(context.getRequest());
		if (user.isOnlyUserInLithuania())
		{
			formMod.setUserInLithuania(true);
		}
		else
		{
			formMod.setUserInLithuania(false);
		}
	}

	public ActionForward generateExcel(IActionContext context) throws Exception
	{
		CalculationState calculationState = (CalculationState) StoreUtil.getSession(context.getRequest(), CalculationState.class);
		Grid2Excel grid2Excel = new Grid2Excel("CalculationState Report");
		grid2Excel.doGrid2Excel(context, calculationState.getExcelTable(grid2Excel.getWb()));
		return null;
	}

	public ActionForward cleanAll(IActionContext context) throws Exception
	{
		CalculationStateForm form = (CalculationStateForm) context.getForm();
		CalculationState calculationState = (CalculationState) StoreUtil.getSession(context.getRequest(), CalculationState.class);
		if (calculationState == null)
		{
			calculationState = new CalculationState();
			StoreUtil.putSession(context.getRequest(), calculationState);
		}
		else
		{
			calculationState.cleanList();
		}
		form.setCanForm(false);
		form.setUserCalcState(new User());
		form.setDepartmentCalcState(new Department());

		return filter(context);
	}

	public ActionForward input(IActionContext context) throws Exception
	{
		CalculationStateForm form = (CalculationStateForm) context.getForm();
		CalculationState calculationState = new CalculationState();
		StoreUtil.putSession(context.getRequest(), calculationState);

		//обнуляем поля формы
		getCurrentFormFromBean(context);

		form.setCanForm(false);
		form.setInclude_all_specs("1");
		form.setNot_include_zero("1");
		form.setNot_show_annul("1");
		form.setNotShowExpiredContractZeroBalance("1");
		form.setNot_include_if_earliest("");
		form.setEarliest_doc_date("");
		form.setReportType(new CalcStateReportType(StrutsUtil.getMessage(context, "report_type_list.calc_in_common_id"), StrutsUtil.getMessage(context, "report_type_list.calc_in_common")));

		StoreUtil.putSession(context.getRequest(), calculationState);
		saveCurrentFormToBean(context);

		return filter(context);
	}

	public ActionForward editContractor(IActionContext context) throws Exception
	{
		saveCurrentFormToBean(context);
		return context.getMapping().findForward("editContractor");
	}

	public ActionForward shipping(IActionContext context) throws Exception
	{
		SyncObject syncObjectForLock = new SyncObject(SHIPPING_LOCK_NAME, context.getRequest().getParameter("shp_id"), context.getRequest().getSession().getId());
		SyncObject syncObjectCurrent = LockedRecords.getLockedRecords().lock(syncObjectForLock);
		if (!syncObjectForLock.equals(syncObjectCurrent))
		{
			StrutsUtil.FormatLockResult res = StrutsUtil.formatLockError(syncObjectCurrent, context);
			StrutsUtil.addError(context, "error.record_shipping.locked", res.userName, res.creationTime, res.creationElapsedTime, null);
			return show(context);
		}

		saveCurrentFormToBean(context);
		return context.getMapping().findForward("shipping");
	}

	public ActionForward payment(IActionContext context) throws Exception
	{
		SyncObject syncObjectForLock = new SyncObject(PAYMENT_LOCK_NAME, context.getRequest().getParameter("pay_id"), context.getRequest().getSession().getId());
		SyncObject syncObjectCurrent = LockedRecords.getLockedRecords().lock(syncObjectForLock);
		if (!syncObjectForLock.equals(syncObjectCurrent))
		{
			StrutsUtil.FormatLockResult res = StrutsUtil.formatLockError(syncObjectCurrent, context);
			StrutsUtil.addError(context, "error.record_payment.locked", res.userName, res.creationTime, res.creationElapsedTime, null);
			return show(context);
		}

		StoreUtil.putSession(context.getRequest(), true, "paymentFromCalculationReport");
		saveCurrentFormToBean(context);
		return context.getMapping().findForward("payment");
	}

	public ActionForward contract(IActionContext context) throws Exception
	{
		SyncObject syncObjectForLock = new SyncObject(CONTRACT_LOCK_NAME, context.getRequest().getParameter("con_id"), context.getRequest().getSession().getId());
		SyncObject syncObjectCurrent = LockedRecords.getLockedRecords().lock(syncObjectForLock);
		if (!syncObjectForLock.equals(syncObjectCurrent))
		{
			StrutsUtil.FormatLockResult res = StrutsUtil.formatLockError(syncObjectCurrent, context);
			StrutsUtil.addError(context, "error.record_contract.locked", res.userName, res.creationTime, res.creationElapsedTime, null);
			return show(context);
		}

		saveCurrentFormToBean(context);
		return context.getMapping().findForward("contract");
	}

	public ActionForward retFromContractor(IActionContext context) throws Exception
	{
		getCurrentFormFromBean(context);
		return show(context);
	}

	public ActionForward backToCalcStateFromContract(IActionContext context) throws Exception
	{
		LockedRecords.getLockedRecords().unlockWithTheSame(CONTRACT_LOCK_NAME, context.getRequest().getSession().getId());

		getCurrentFormFromBean(context);
		return show(context);
	}

	public ActionForward backToCalcStateFromShipping(IActionContext context) throws Exception
	{
		LockedRecords.getLockedRecords().unlockWithTheSame(SHIPPING_LOCK_NAME, context.getRequest().getSession().getId());

		getCurrentFormFromBean(context);
		return show(context);
	}

	public ActionForward backToCalcStateFromPayment(IActionContext context) throws Exception
	{
		LockedRecords.getLockedRecords().unlockWithTheSame(PAYMENT_LOCK_NAME, context.getRequest().getSession().getId());

		getCurrentFormFromBean(context);
		StoreUtil.putSession(context.getRequest(), false, "paymentFromCalculationReport");

		return show(context);
	}

	public ActionForward ajaxGetReputation(IActionContext context) throws Exception
	{
		String contractorId = context.getRequest().getParameter("contractor-id");
		if (!StringUtil.isEmpty(contractorId) && !"-1".equals(contractorId))
		{
			Contractor contractor = ContractorDAO.load(context, context.getRequest().getParameter("contractor-id"));
			String reputation = StrutsUtil.getMessage(context, "CalculationState.reputation");
			reputation += " ";
			reputation += contractor.getReputation().getDescription();
			StrutsUtil.setAjaxResponse(context, reputation, false);
		}
		return context.getMapping().findForward("ajax");
	}
}
