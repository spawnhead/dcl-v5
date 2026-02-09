package net.sam.dcl.action;

import net.sam.dcl.beans.*;
import net.sam.dcl.config.Config;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.controller.IDispatchable;
import net.sam.dcl.controller.IFormAutoSave;
import net.sam.dcl.controller.IProcessBefore;
import net.sam.dcl.controller.actions.DBAction;
import net.sam.dcl.controller.processor.ActionHandler;
import net.sam.dcl.dao.ConditionForContractDAO;
import net.sam.dcl.dao.MessageDAO;
import net.sam.dcl.form.ConditionsForContractForm;
import net.sam.dcl.form.ContractsForm;
import net.sam.dcl.taglib.table.IReadOnlyChecker;
import net.sam.dcl.taglib.table.IStyleClassChecker;
import net.sam.dcl.taglib.table.ReadOnlyCheckerContext;
import net.sam.dcl.taglib.table.StyleClassCheckerContext;
import net.sam.dcl.taglib.table.model.PageableDataHolder;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.StrutsUtil;
import net.sam.dcl.util.UserUtil;
import org.apache.struts.action.ActionForward;

import java.util.Calendar;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class ConditionsForContractAction extends DBAction implements IDispatchable, IFormAutoSave, IProcessBefore
{
	public ActionForward processBefore(IActionContext context) throws Exception
	{
		context.getActionProcessor().addActionHandler("grid", PageableDataHolder.NEXT_PAGE, new ActionHandler()
		{
			public ActionForward process(IActionContext context) throws Exception
			{
				ConditionsForContractForm form = (ConditionsForContractForm) context.getForm();
				form.getGrid().incPage();
				return internalFilter(context);
			}
		});
		context.getActionProcessor().addActionHandler("grid", PageableDataHolder.PREV_PAGE, new ActionHandler()
		{
			public ActionForward process(IActionContext context) throws Exception
			{
				ConditionsForContractForm form = (ConditionsForContractForm) context.getForm();
				form.getGrid().decPage();
				return internalFilter(context);
			}
		});
		return null;
	}

	public ActionForward filter(IActionContext context) throws Exception
	{
		ConditionsForContractForm form = (ConditionsForContractForm) context.getForm();
		form.getGrid().setPage(1);
		return internalFilter(context);
	}

	public ActionForward internalFilter(IActionContext context) throws Exception
	{
		ConditionsForContractForm form = (ConditionsForContractForm) context.getForm();
		DAOUtils.fillGrid(context, form.getGrid(), "select-conditions_for_contract", ConditionsForContractForm.ConditionForContract.class, null, null);

		final User currentUser = UserUtil.getCurrentUser(context.getRequest());
		context.getRequest().setAttribute("executeChecker", new IReadOnlyChecker()
		{
			public boolean check(ReadOnlyCheckerContext ctx)
			{
				return !(currentUser.isAdmin() || currentUser.isLawyer());
			}
		});

		context.getRequest().setAttribute("checkPriceChecker", new IReadOnlyChecker()
		{
			public boolean check(ReadOnlyCheckerContext ctx)
			{
				return !(currentUser.isAdmin() || currentUser.isEconomist());
			}
		});

		// менеджер - тока свой отдел
		context.getRequest().setAttribute("cloneEditChecker", new IReadOnlyChecker()
		{
			public boolean check(ReadOnlyCheckerContext ctx) throws Exception
			{
				ConditionsForContractForm.ConditionForContract record = (ConditionsForContractForm.ConditionForContract) ctx.getBean();
				return currentUser.isOnlyManager() &&
								!currentUser.getDepartment().getId().equals(record.getDep_id());
			}
		});

		context.getRequest().setAttribute("style-checker", new IStyleClassChecker()
  {
    public String check(StyleClassCheckerContext ctx)
    {
	    ConditionsForContractForm.ConditionForContract record = (ConditionsForContractForm.ConditionForContract) ctx.getBean();
      if (record.isAnnul())
      {
        return "crossed-cell";
      }
      return "";
    }
  });

		return context.getMapping().getInputForward();
	}

	public ActionForward markExecute(IActionContext context) throws Exception
	{
		ConditionsForContractForm form = (ConditionsForContractForm) context.getForm();
		ConditionForContract conditionForContract = new ConditionForContract();
		conditionForContract.setCfc_id(form.getCfc_id());
		if ("1".equals(form.getCfc_execute()))
		{
			conditionForContract.setCfc_execute("");
		}
		else
		{
			User currentUser = UserUtil.getCurrentUser(context.getRequest());
			conditionForContract.setExecuteUser(currentUser);
			conditionForContract.setCfc_execute("1");
		}
		ConditionForContractDAO.saveExecute(context, conditionForContract);
		if (!"1".equals(form.getCfc_execute())) //после сохранения основного документа
		{
			conditionForContract = ConditionForContractDAO.load(context, conditionForContract.getCfc_id());
			ConditionForContractMessage conditionForContractMessage = new ConditionForContractMessage();
			conditionForContractMessage.setCfc_id(conditionForContract.getCfc_id());
			conditionForContractMessage.setCtr_id(conditionForContract.getContractor().getId());
			conditionForContractMessage.setMsg(StrutsUtil.getMessage(context, "ConditionForContractMessage.msg", conditionForContract.getUsr_date_place()));
			MessageDAO.saveConditionForContractMessages(context, conditionForContractMessage);
		}

		return internalFilter(context);
	}

	public ActionForward input(IActionContext context) throws Exception
	{
		ConditionsForContractForm form = (ConditionsForContractForm) context.getForm();
		form.setContractor(new Contractor());
		form.setUser(new User());
		form.setCfc_not_executed("");
		form.setSeller(new Seller());
		form.setAnnul_exclude("1");
		form.setCfc_not_placed("1");
		if (Config.haveNumber(Constants.dayCountDeductConditionsForContract))
		{
			int dayCount = Config.getNumber(Constants.dayCountDeductConditionsForContract, 10);
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
		return internalFilter(context);
	}

	public ActionForward checkPrice(IActionContext context) throws Exception
	{
		ConditionsForContractForm form = (ConditionsForContractForm) context.getForm();
		ConditionForContract conditionForContract = new ConditionForContract();
		conditionForContract.setCfc_id(form.getCfc_id());
		if ("1".equals(form.getCfc_check_price()))
		{
			conditionForContract.setCfc_check_price("");
		}
		else
		{
			conditionForContract.setCfc_check_price("1");
		}

		conditionForContract.setCfc_check_price_date(StringUtil.date2dbDateTimeString(StringUtil.getCurrentDateTime()));
		conditionForContract.setUsr_id_check_price(UserUtil.getCurrentUser(context.getRequest()).getUsr_id());
		ConditionForContractDAO.saveCheckPrice(context, conditionForContract);

		return internalFilter(context);
	}

}
