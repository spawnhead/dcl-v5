package net.sam.dcl.action;

import net.sam.dcl.controller.actions.DBAction;
import net.sam.dcl.controller.IDispatchable;
import net.sam.dcl.controller.IFormAutoSave;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.controller.IProcessBefore;
import net.sam.dcl.controller.processor.ActionHandler;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.util.StoreUtil;
import net.sam.dcl.util.UserUtil;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.beans.*;
import net.sam.dcl.taglib.table.*;
import net.sam.dcl.taglib.table.model.PageableDataHolder;
import net.sam.dcl.form.OrdersForm;
import net.sam.dcl.dao.OrderDAO;
import org.apache.struts.action.ActionForward;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class OrdersAction extends DBAction implements IDispatchable, IFormAutoSave, IProcessBefore
{
	public ActionForward processBefore(IActionContext context) throws Exception
	{
		context.getActionProcessor().addActionHandler("grid", PageableDataHolder.NEXT_PAGE, new ActionHandler()
		{
			public ActionForward process(IActionContext context) throws Exception
			{
				OrdersForm form = (OrdersForm) context.getForm();
				form.getGrid().incPage();
				return internalFilter(context);
			}
		});
		context.getActionProcessor().addActionHandler("grid", PageableDataHolder.PREV_PAGE, new ActionHandler()
		{
			public ActionForward process(IActionContext context) throws Exception
			{
				OrdersForm form = (OrdersForm) context.getForm();
				form.getGrid().decPage();
				return internalFilter(context);
			}
		});
		return null;
	}

	public ActionForward filter(IActionContext context) throws Exception
	{
		OrdersForm form = (OrdersForm) context.getForm();
		form.setOrder_by(" ord_date descending");
		form.getGrid().setPage(1);
		return internalFilter(context);
	}

	public ActionForward internalFilter(IActionContext context) throws Exception
	{
		final OrdersForm form = (OrdersForm) context.getForm();
		DAOUtils.fillGrid(context, form.getGrid(), "select-orders", OrdersForm.Order.class, null, null);
		for (int i = 0; i < form.getGrid().getDataList().size(); i++)
		{
			OrdersForm.Order order = (OrdersForm.Order) form.getGrid().getDataList().get(i);
			order.setShowMovement(form.isShowMovement());
		}

		final User currentUser = UserUtil.getCurrentUser(context.getRequest());
		context.getRequest().setAttribute("blockChecker", new IReadOnlyChecker()
		{
			public boolean check(ReadOnlyCheckerContext ctx)
			{
				return !currentUser.isAdmin();
			}
		});

		context.getRequest().setAttribute("showWarn", new IShowChecker()
		{
			public boolean check(ShowCheckerContext ctx)
			{
				OrdersForm.Order record = (OrdersForm.Order) ctx.getBean();
				return !StringUtil.isEmpty(record.getIs_warn());
			}
		});

		context.getRequest().setAttribute("style-checker", new IStyleClassChecker()
		{
			public String check(StyleClassCheckerContext context)
			{
				OrdersForm.Order order = (OrdersForm.Order) form.getGrid().getDataList().get(context.getTable().getRecordCounter() - 1);
				if ("1".equals(order.getOrd_annul()))
				{
					return "crossed-cell";
				}
				return "";
			}
		});

		// менеджер - тока свой отдел
		context.getRequest().setAttribute("editCloneChecker", new IReadOnlyChecker()
		{
			public boolean check(ReadOnlyCheckerContext ctx) throws Exception
			{
				OrdersForm.Order record = (OrdersForm.Order) ctx.getBean();
	      /* сохранил здесь бывший вариант работы с правами на всякий случай, если что - быстрее востановлю
        if (
             currentUser.isOnlyManager() &&
             !currentUser.isChiefDepartment() &&
             !currentUser.getUsr_id().equals(record.getUsr_id_create())
           )
        {
          return true;
        }
        */
				if (
								currentUser.isOnlyManager() &&
												//currentUser.isChiefDepartment() &&
												//!currentUser.getUsr_id().equals(record.getUsr_id_create()) &&
												!currentUser.getDepartment().getId().equals(record.getDep_id())
								)
				{
					return true;
				}
				return false;
			}
		});

		StoreUtil.putSession(context.getRequest(), context.getForm());
		return context.getMapping().getInputForward();
	}

	public ActionForward block(IActionContext context) throws Exception
	{
		OrdersForm form = (OrdersForm) context.getForm();
		Order order = new Order();
		order.setOrd_id(form.getOrd_id());
		if ("1".equals(form.getBlock()))
		{
			order.setOrd_block("");
		}
		else
		{
			order.setOrd_block("1");
		}
		OrderDAO.saveBlock(context, order);

		return internalFilter(context);
	}

	public ActionForward input(IActionContext context) throws Exception
	{
		OrdersForm form = (OrdersForm) context.getForm();
		form.setContractor(new Contractor());
		form.setContractor_for(new Contractor());
		form.setPreviousContractorFor(new Contractor());
		form.setContract(new Contract());
		form.setPreviousContract(new Contract());
		form.setSpecification(new Specification());
		form.setStuffCategory(new StuffCategory());
		form.setDate_begin("");
		form.setDate_end("");
		form.setNumber("");
		form.setSum_max_formatted("");
		form.setSum_min_formatted("");
		form.setDepartment(new Department());
		form.setUser(new User());
		form.setOrd_executed(null);
		form.setExecuted("");
		form.setNot_executed("1");
		form.setOrd_ready_for_deliv("");
		form.setOrd_annul_not_show("1");
		form.setOrd_show_movement("");
		form.setSellerForWho(new Seller());

		form.setState_a("");
		form.setState_3("");
		form.setState_b("");
		form.setOrd_num_conf("");
		form.setState_exclamation("");
		form.setState_c("");

		form.setOrder_by(" ord_ready_for_deliv descending, ord_date descending, ord_number descending");

		User user = UserUtil.getCurrentUser(context.getRequest());
		if (user.isManager())
		{
			form.setUser(user);
		}
		if (user.isDeclarant() || user.isEconomist())
		{
			form.setOrd_ready_for_deliv("1");
		}

		return internalFilter(context);
	}

	public ActionForward restore(IActionContext context) throws Exception
	{
		restoreForm(context);
		return internalFilter(context);
	}

	public ActionForward reload(IActionContext context)
	{
		OrdersForm storedForm = (OrdersForm) StoreUtil.getSession(context.getRequest(), OrdersForm.class);
		OrdersForm form = (OrdersForm) context.getForm();
		storedForm.setContractor(form.getContractor());
		storedForm.setContractor_for(form.getContractor_for());
		storedForm.setStuffCategory(form.getStuffCategory());
		storedForm.setDate_begin(form.getDate_begin());
		storedForm.setDate_end(form.getDate_end());
		storedForm.setNumber(form.getNumber());
		storedForm.setSum_max_formatted(form.getSum_max_formatted());
		storedForm.setSum_min_formatted(form.getSum_min_formatted());
		storedForm.setDepartment(form.getDepartment());
		storedForm.setUser(form.getUser());
		storedForm.setOrd_executed(form.getOrd_executed());
		storedForm.setExecuted(form.getExecuted());
		storedForm.setNot_executed(form.getNot_executed());
		storedForm.setOrd_ready_for_deliv(form.getOrd_ready_for_deliv());
		storedForm.setOrd_annul_not_show(form.getOrd_annul_not_show());
		storedForm.setOrd_show_movement(form.getOrd_show_movement());
		storedForm.setSellerForWho(form.getSellerForWho());

		storedForm.setState_a(form.getState_a());
		storedForm.setState_3(form.getState_3());
		storedForm.setState_b(form.getState_b());
		storedForm.setOrd_num_conf(form.getOrd_num_conf());
		storedForm.setState_exclamation(form.getState_exclamation());
		storedForm.setState_c(form.getState_c());
		storedForm.setContract(form.getContract());
		storedForm.setSpecification(form.getSpecification());

		storedForm.setPreviousContractorFor(form.getPreviousContractorFor());
		storedForm.setPreviousContract(form.getPreviousContract());

		context.getRequest().setAttribute("Orders", storedForm);
		return context.getMapping().findForward("form");
	}
}
