package net.sam.dcl.action;

import net.sam.dcl.controller.actions.DBAction;
import net.sam.dcl.controller.IDispatchable;
import net.sam.dcl.controller.IFormAutoSave;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.controller.IProcessBefore;
import net.sam.dcl.controller.processor.ActionHandler;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.util.UserUtil;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.beans.*;
import net.sam.dcl.taglib.table.IReadOnlyChecker;
import net.sam.dcl.taglib.table.ReadOnlyCheckerContext;
import net.sam.dcl.taglib.table.model.PageableDataHolder;
import net.sam.dcl.form.ShippingsForm;
import net.sam.dcl.dao.ShippingDAO;
import net.sam.dcl.config.Config;
import org.apache.struts.action.ActionForward;

import java.util.Calendar;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class ShippingsAction extends DBAction implements IDispatchable, IFormAutoSave, IProcessBefore
{
	public ActionForward processBefore(IActionContext context) throws Exception
	{
		context.getActionProcessor().addActionHandler("grid", PageableDataHolder.NEXT_PAGE, new ActionHandler()
		{
			public ActionForward process(IActionContext context) throws Exception
			{
				ShippingsForm form = (ShippingsForm) context.getForm();
				form.getGrid().incPage();
				return internalFilter(context);
			}
		});
		context.getActionProcessor().addActionHandler("grid", PageableDataHolder.PREV_PAGE, new ActionHandler()
		{
			public ActionForward process(IActionContext context) throws Exception
			{
				ShippingsForm form = (ShippingsForm) context.getForm();
				form.getGrid().decPage();
				return internalFilter(context);
			}
		});
		return null;
	}

	public ActionForward filter(IActionContext context) throws Exception
	{
		ShippingsForm form = (ShippingsForm) context.getForm();
		form.getGrid().setPage(1);
		return internalFilter(context);
	}

	public ActionForward internalFilter(IActionContext context) throws Exception
	{
		final ShippingsForm form = (ShippingsForm) context.getForm();

		if ("1".equals(form.getClosed_open()))
		{
			form.setClosed_closed("");
			form.setClosed_all("");
		}

		DAOUtils.fillGrid(context, form.getGrid(), "select-shippings", ShippingsForm.Shipping.class, null, null);

		final User currentUser = UserUtil.getCurrentUser(context.getRequest());
		context.getRequest().setAttribute("blockChecker", new IReadOnlyChecker()
		{
			public boolean check(ReadOnlyCheckerContext ctx) throws Exception
			{
				ShippingsForm.Shipping shipping = (ShippingsForm.Shipping) form.getGrid().getDataList().get(ctx.getTable().getRecordCounter() - 1);
				return !(currentUser.isAdmin() || (currentUser.isEconomist() && StringUtil.isEmpty(shipping.getShp_closed())));
			}
		});

		// менеджер если указан в одной из спек договора в поле "Чья специф-я" ИЛИ в колонке "Менеджер" и свой отдел
		context.getRequest().setAttribute("editChecker", new IReadOnlyChecker()
		{
			public boolean check(ReadOnlyCheckerContext ctx) throws Exception
			{
				ShippingsForm.Shipping record = (ShippingsForm.Shipping) ctx.getBean();
				return currentUser.isOnlyManager() &&
								!record.getDep_id_list().contains(currentUser.getDepartment().getId() + ReportDelimiterConsts.html_separator);
			}
		});

		return context.getMapping().getInputForward();
	}

	public ActionForward block(IActionContext context) throws Exception
	{
		ShippingsForm form = (ShippingsForm) context.getForm();
		Shipping shipping = new Shipping();
		shipping.setShp_id(form.getShp_id());
		if ("1".equals(form.getShp_block()))
		{
			shipping.setShp_block("");
		}
		else
		{
			shipping.setShp_block("1");
		}
		ShippingDAO.saveBlock(context, shipping);

		return internalFilter(context);
	}

	public ActionForward input(IActionContext context) throws Exception
	{
		ShippingsForm form = (ShippingsForm) context.getForm();
		form.setContractor(new Contractor());
		form.setCurrency(new Currency());
		form.setSum_min_formatted(StringUtil.double2appCurrencyString(0.01));
		form.setSum_max_formatted("");
		form.setNumber("");
		form.setClosed_open("1");
		if (Config.haveNumber(Constants.dayCountDeductShippings))
		{
			int dayCount = Config.getNumber(Constants.dayCountDeductShippings, 10);
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
		form.setSeller(new Seller());

		return internalFilter(context);
	}

	public ActionForward restore(IActionContext context) throws Exception
	{
		restoreForm(context);
		return internalFilter(context);
	}

}
