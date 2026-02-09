package net.sam.dcl.action;

import net.sam.dcl.controller.actions.DBTransactionAction;
import net.sam.dcl.controller.IDispatchable;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.StoreUtil;
import net.sam.dcl.util.StrutsUtil;
import net.sam.dcl.util.UserUtil;
import net.sam.dcl.beans.*;
import net.sam.dcl.dao.*;
import net.sam.dcl.form.ContractClosedForm;
import net.sam.dcl.taglib.table.impl.AlwaysNotReadonlyChecker;
import net.sam.dcl.taglib.table.IStyleClassChecker;
import net.sam.dcl.taglib.table.StyleClassCheckerContext;
import net.sam.dcl.locking.LockedRecords;
import org.apache.struts.action.ActionForward;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class ContractClosedAction extends DBTransactionAction implements IDispatchable
{
	protected static Log log = LogFactory.getLog(ContractClosedAction.class);

	private void saveCurrentFormToBean(IActionContext context)
	{
		ContractClosedForm form = (ContractClosedForm) context.getForm();
		ContractClosed contractClosed = (ContractClosed) StoreUtil.getSession(context.getRequest(), ContractClosed.class);

		contractClosed.setIs_new_doc(form.getIs_new_doc());

		contractClosed.setCtc_id(form.getCtc_id());
		try
		{
			if (!StringUtil.isEmpty(form.getCreateUser().getUsr_id()))
			{
				contractClosed.setCreateUser(UserDAO.load(context, form.getCreateUser().getUsr_id()));
			}
			if (!StringUtil.isEmpty(form.getEditUser().getUsr_id()))
			{
				contractClosed.setEditUser(UserDAO.load(context, form.getEditUser().getUsr_id()));
			}
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
		}
		contractClosed.setUsr_date_create(form.getUsr_date_create());
		contractClosed.setUsr_date_edit(form.getUsr_date_edit());
		contractClosed.setCtc_date(form.getCtc_date());
		contractClosed.setCtc_number(form.getCtc_number());
		contractClosed.setCtc_block(form.getCtc_block());

		StoreUtil.putSession(context.getRequest(), contractClosed);
	}

	private void getCurrentFormFromBean(IActionContext context, ContractClosed contract_closed_in)
	{
		ContractClosedForm form = (ContractClosedForm) context.getForm();
		ContractClosed contractClosed;
		if (null != contract_closed_in)
		{
			contractClosed = contract_closed_in;
		}
		else
		{
			contractClosed = (ContractClosed) StoreUtil.getSession(context.getRequest(), ContractClosed.class);
		}

		if (null != contractClosed)
		{
			form.setIs_new_doc(contractClosed.getIs_new_doc());

			form.setCtc_id(contractClosed.getCtc_id());
			form.setCreateUser(contractClosed.getCreateUser());
			form.setEditUser(contractClosed.getEditUser());
			form.setUsr_date_create(contractClosed.getUsr_date_create());
			form.setUsr_date_edit(contractClosed.getUsr_date_edit());
			form.setCtc_date(contractClosed.getCtc_date());
			form.setCtc_number(contractClosed.getCtc_number());
			form.setCtc_block(contractClosed.getCtc_block());

			form.getGrid().setDataList(contractClosed.getClosedRecords());
		}
	}

	public ActionForward newContractor(IActionContext context) throws Exception
	{
		saveCurrentFormToBean(context);

		return context.getMapping().findForward("newContractor");
	}

	public ActionForward editClosedRecord(IActionContext context) throws Exception
	{
		saveCurrentFormToBean(context);

		return context.getMapping().findForward("editClosedRecord");
	}

	public ActionForward retClosedRecord(IActionContext context) throws Exception
	{
		return show(context);
	}

	private boolean saveCommon(IActionContext context, boolean blockClosed) throws Exception
	{
		ContractClosedForm form = (ContractClosedForm) context.getForm();
		String errMsg = "";

		saveCurrentFormToBean(context);

		if (form.getGrid().getDataList().size() == 0)
		{
			errMsg = StrutsUtil.addDelimiter(errMsg);
			errMsg += StrutsUtil.getMessage(context, "error.contract_closed.no_data");
		}

		for (int i = 0; i < form.getGrid().getDataList().size(); i++)
		{
			ClosedRecord closedRecord = (ClosedRecord) form.getGrid().getDataList().get(i);
			if (!closedRecord.isCorrectLine())
			{
				errMsg = StrutsUtil.addDelimiter(errMsg);
				errMsg += StrutsUtil.getMessage(context, "error.contract_closed.incorrect_line");
				break;
			}
		}

		if (!StringUtil.isEmpty(errMsg))
		{
			StrutsUtil.addError(context, "errors.msg", errMsg, null);
			return false;
		}

		ContractClosed contractClosed = (ContractClosed) StoreUtil.getSession(context.getRequest(), ContractClosed.class);
		User user = UserUtil.getCurrentUser(context.getRequest());
		contractClosed.setEditUser(user);
		if (blockClosed)
		{
			contractClosed.setCtc_block("1");
		}

		if (StringUtil.isEmpty(form.getCtc_id()))
		{
			contractClosed.setCreateUser(user);
			form.setCreateUser(user);
			ContractClosedDAO.insert(context, contractClosed);
		}
		else
		{
			ContractClosedDAO.save(context, contractClosed);
		}

		return true;
	}

	private void setVisuallyAttributes(IActionContext context)
	{
		final ContractClosedForm form = (ContractClosedForm) context.getForm();

		context.getRequest().setAttribute("alwaysNotReadonly", new AlwaysNotReadonlyChecker());

		if (StringUtil.isEmpty(form.getCreateUser().getUsr_id()) || //новый документ
						!"1".equals(form.getCtc_block())
						)
		{
			form.setFormReadOnly(false);
		}
		else
		{
			form.setFormReadOnly(true);
		}

		context.getRequest().setAttribute("style-checker", new IStyleClassChecker()
		{
			public String check(StyleClassCheckerContext context)
			{
				ClosedRecord closedRecord = (ClosedRecord) form.getGrid().getDataList().get(context.getTable().getRecordCounter() - 1);
				if (!closedRecord.isCorrectLine())
				{
					if (closedRecord.haveCopyOrProject() || closedRecord.haveUnblockedPrc())
					{
						return "red-font-cell-pink";
					}
					return "red-font-cell";
				}

				if (closedRecord.haveCopyOrProject() || closedRecord.haveUnblockedPrc() || closedRecord.haveCopyOfShipping())
				{
					return "cell-pink";
				}
				return "";
			}
		});
	}

	public ActionForward show(IActionContext context) throws Exception
	{
		final ContractClosedForm form = (ContractClosedForm) context.getForm();

		ContractClosed contractClosed = (ContractClosed) StoreUtil.getSession(context.getRequest(), ContractClosed.class);
		contractClosed.calculate(null);
		form.getGrid().setDataList(contractClosed.getClosedRecords());

		setVisuallyAttributes(context);

		return context.getMapping().findForward("form");
	}

	public ActionForward input(IActionContext context) throws Exception
	{
		ContractClosedForm form = (ContractClosedForm) context.getForm();
		//Дату нужно установить до загрузки.
		form.setCtc_date(StringUtil.date2appDateString(StringUtil.getCurrentDateTime()));

		ContractClosed contractClosed = ContractClosedDAO.newContractClosed(context, form.getCtc_date());
		StoreUtil.putSession(context.getRequest(), contractClosed);
		getCurrentFormFromBean(context, contractClosed);

		//обнуляем удаленные строки
		ClosedRecord.PermanentDeleted permanentDeleted = new ClosedRecord.PermanentDeleted();
		StoreUtil.putSession(context.getRequest(), permanentDeleted);

		form.setIs_new_doc("true");
		form.setCtc_block("");

		return show(context);
	}

	public ActionForward edit(IActionContext context) throws Exception
	{
		ContractClosedForm form = (ContractClosedForm) context.getForm();

		ClosedRecord.PermanentDeleted permanentDeleted = new ClosedRecord.PermanentDeleted();
		ContractClosed contractClosed = ContractClosedDAO.load(context, form.getCtc_id(), permanentDeleted);
		StoreUtil.putSession(context.getRequest(), permanentDeleted);
		StoreUtil.putSession(context.getRequest(), contractClosed);
		getCurrentFormFromBean(context, contractClosed);

		return show(context);
	}

	private void unblockDoc(IActionContext context)
	{
		LockedRecords.getLockedRecords().unlockWithTheSame(ContractsClosedAction.CONTRACT_CLOSED_LOCK_NAME, context.getRequest().getSession().getId());
	}

	public ActionForward back(IActionContext context) throws Exception
	{
		unblockDoc(context);
		return context.getMapping().findForward("back");
	}

	public ActionForward process(IActionContext context) throws Exception
	{
		boolean retFromSave = saveCommon(context, false);

		if (retFromSave)
		{
			unblockDoc(context);
			return context.getMapping().findForward("back");
		}
		else
		{
			return show(context);
		}
	}

	public ActionForward processBlock(IActionContext context) throws Exception
	{
		boolean retFromSave = saveCommon(context, true);

		if (retFromSave)
		{
			unblockDoc(context);
			return context.getMapping().findForward("back");
		}
		else
		{
			return show(context);
		}
	}

	public ActionForward ajaxClosedContractRecordsGrid(IActionContext context) throws Exception
	{
		setVisuallyAttributes(context);

		return context.getMapping().findForward("ajaxClosedContractRecordsGrid");
	}

	public ActionForward ajaxChangeContractClosedDate(IActionContext context) throws Exception
	{
		ContractClosedForm form = (ContractClosedForm) context.getForm();
		ContractClosed contractClosed = (ContractClosed) StoreUtil.getSession(context.getRequest(), ContractClosed.class);

		String ctcDate = context.getRequest().getParameter("ctcDate");
		if (!StringUtil.isEmpty(ctcDate))
		{
			contractClosed.setCtc_date(ctcDate);
			for (ClosedRecord closedRecord : contractClosed.getClosedRecords())
			{
				closedRecord.setCtcDate(contractClosed.getCtcDate());
				closedRecord.formRecList();
			}

			form.getGrid().setDataList(contractClosed.getClosedRecords());
		}

		return ajaxClosedContractRecordsGrid(context);
	}

	public ActionForward ajaxDeleteClosedRecord(IActionContext context) throws Exception
	{
		ContractClosedForm form = (ContractClosedForm) context.getForm();
		ContractClosed contractClosed = (ContractClosed) StoreUtil.getSession(context.getRequest(), ContractClosed.class);

		String number = context.getRequest().getParameter("number");
		if (!StringUtil.isEmpty(number))
		{
			form.setNumber(number);
			contractClosed.deleteClosedRecord(form.getNumber());
			form.getGrid().setDataList(contractClosed.getClosedRecords());
		}

		return ajaxClosedContractRecordsGrid(context);
	}

	public ActionForward ajaxDeleteSelected(IActionContext context) throws Exception
	{
		ContractClosedForm form = (ContractClosedForm) context.getForm();
		ContractClosed contractClosed = (ContractClosed) StoreUtil.getSession(context.getRequest(), ContractClosed.class);

		ClosedRecord foundForDelete = contractClosed.findForDelete();
		while (foundForDelete != null)
		{
			contractClosed.deleteClosedRecord(foundForDelete.getNumber());
			foundForDelete = contractClosed.findForDelete();
		}
		form.getGrid().setDataList(contractClosed.getClosedRecords());

		return ajaxClosedContractRecordsGrid(context);
	}
}
