package net.sam.dcl.action;

import net.sam.dcl.controller.actions.DBAction;
import net.sam.dcl.controller.IDispatchable;
import net.sam.dcl.controller.IFormAutoSave;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.locking.SyncObject;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.StrutsUtil;
import net.sam.dcl.util.UserUtil;
import net.sam.dcl.beans.Contractor;
import net.sam.dcl.beans.Contract;
import net.sam.dcl.beans.User;
import net.sam.dcl.beans.ContractClosed;
import net.sam.dcl.taglib.table.IReadOnlyChecker;
import net.sam.dcl.taglib.table.ReadOnlyCheckerContext;
import net.sam.dcl.taglib.table.IShowChecker;
import net.sam.dcl.taglib.table.ShowCheckerContext;
import net.sam.dcl.taglib.table.impl.AlwaysReadonlyChecker;
import net.sam.dcl.form.ContractsClosedForm;
import net.sam.dcl.dao.ContractorDAO;
import net.sam.dcl.dao.ContractDAO;
import net.sam.dcl.dao.ContractClosedDAO;
import net.sam.dcl.locking.LockedRecords;
import org.apache.struts.action.ActionForward;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class ContractsClosedAction extends DBAction implements IDispatchable, IFormAutoSave
{
	public static final String CONTRACT_CLOSED_LOCK_NAME = "ContractClosed";
	public static final String CONTRACT_CLOSED_LOCK_ID = "FULL TABLE";

	public ActionForward filter(IActionContext context) throws Exception
	{
		final ContractsClosedForm form = (ContractsClosedForm) context.getForm();
		DAOUtils.fillGrid(context, form.getGrid(), "select-contracts_closed", ContractsClosedForm.ContractClosed.class, null, null);

		context.getRequest().setAttribute("alwaysReadonly", new AlwaysReadonlyChecker());

		final User currentUser = UserUtil.getCurrentUser(context.getRequest());
		context.getRequest().setAttribute("blockChecker", new IReadOnlyChecker()
		{
			public boolean check(ReadOnlyCheckerContext ctx) throws Exception
			{
				ContractsClosedForm.ContractClosed record = (ContractsClosedForm.ContractClosed) ctx.getBean();
				return !(currentUser.isAdmin() && "1".equals(record.getCtc_block()));
			}
		});

		context.getRequest().setAttribute("show-delete-checker", new IShowChecker()
		{
			public boolean check(ShowCheckerContext context)
			{
				ContractsClosedForm.ContractClosed record = (ContractsClosedForm.ContractClosed) form.getGrid().getDataList().get(context.getTable().getRecordCounter() - 1);
				return !("1".equals(record.getCtc_block()));
			}
		}
		);

		context.getRequest().setAttribute("showWarnChecker", new IShowChecker()
		{
			public boolean check(ShowCheckerContext ctx)
			{
				ContractsClosedForm.ContractClosed record = (ContractsClosedForm.ContractClosed) ctx.getBean();
				return record.showWarn();
			}
		});


		return context.getMapping().getInputForward();
	}

	public ActionForward reload(IActionContext context) throws Exception
	{
		ContractsClosedForm form = (ContractsClosedForm) context.getForm();
		form.setCleanBlock(true);
		try
		{
			if (!StringUtil.isEmpty(form.getContractor().getId()))
			{
				form.setContractor(ContractorDAO.load(context, form.getContractor().getId()));
			}
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
		}

		return filter(context);
	}

	public ActionForward create(IActionContext context) throws Exception
	{
		ContractsClosedForm form = (ContractsClosedForm) context.getForm();
		SyncObject syncObjectForLock = new SyncObject(CONTRACT_CLOSED_LOCK_NAME, CONTRACT_CLOSED_LOCK_ID, context.getRequest().getSession().getId());
		SyncObject syncObjectCurrent = LockedRecords.getLockedRecords().lock(syncObjectForLock);
		if (!syncObjectForLock.equals(syncObjectCurrent))
		{
			StrutsUtil.FormatLockResult res = StrutsUtil.formatLockError(syncObjectCurrent, context);
			StrutsUtil.addError(context, "error.record_contract_closed_create.locked", res.userName, res.creationTime, res.creationElapsedTime, null);
			return filter(context);
		}

		form.setCleanBlock(false);
		return context.getMapping().findForward("create");
	}

	public ActionForward edit(IActionContext context) throws Exception
	{
		ContractsClosedForm form = (ContractsClosedForm) context.getForm();
		form.setCleanBlock(false);
		return context.getMapping().findForward("edit");
	}

	public ActionForward reloadContract(IActionContext context) throws Exception
	{
		ContractsClosedForm form = (ContractsClosedForm) context.getForm();
		form.setCleanBlock(true);
		try
		{
			if (!StringUtil.isEmpty(form.getContract().getCon_id()))
			{
				form.setContract(ContractDAO.load(context, form.getContract().getCon_id(), false));
			}
		}
		catch (Exception e)
		{
			StrutsUtil.addError(context, e);
		}

		return filter(context);
	}

	public ActionForward unblock(IActionContext context) throws Exception
	{
		ContractsClosedForm form = (ContractsClosedForm) context.getForm();
		form.setCleanBlock(true);
		ContractClosed contractClosed = new ContractClosed();
		contractClosed.setCtc_id(form.getCtc_id());
		ContractClosedDAO.saveUnblock(context, contractClosed);

		return filter(context);
	}

	public ActionForward delete(IActionContext context) throws Exception
	{
		ContractsClosedForm form = (ContractsClosedForm) context.getForm();
		form.setCleanBlock(true);
		ContractClosed contractClosed = new ContractClosed();
		contractClosed.setCtc_id(form.getCtc_id());
		ContractClosedDAO.delete(context, contractClosed);

		return filter(context);
	}

	public ActionForward blockSelected(IActionContext context) throws Exception
	{
		boolean seveBlock = false;
		String ids = "";
		ContractsClosedForm form = (ContractsClosedForm) context.getForm();
		form.setCleanBlock(true);
		for (int i = 0; i < form.getGrid().getDataList().size(); i++)
		{
			ContractsClosedForm.ContractClosed contractClosed = (ContractsClosedForm.ContractClosed) form.getGrid().getDataList().get(i);
			if (!StringUtil.isEmpty(contractClosed.getSelectedContractId()))
			{
				seveBlock = true;
				ids += contractClosed.getCtc_id() + ", ";
			}
		}
		if (seveBlock)
		{
			ids = ids.substring(0, ids.length() - 2);
			ContractClosed contractClosed = new ContractClosed();
			contractClosed.setCtc_id_list(ids);
			ContractClosedDAO.saveBlockSelected(context, contractClosed);
		}

		return filter(context);
	}

	public ActionForward input(IActionContext context) throws Exception
	{
		ContractsClosedForm form = (ContractsClosedForm) context.getForm();
		form.setCleanBlock(true);
		form.setContractor(new Contractor());
		form.setContract(new Contract());
		form.setDate_begin("");
		form.setDate_end("");
		form.setNot_block("1");

		return filter(context);
	}

	public ActionForward restore(IActionContext context) throws Exception
	{
		ContractsClosedForm form = (ContractsClosedForm) context.getForm();
		form.setCleanBlock(true);
		restoreForm(context);
		return filter(context);
	}

}
