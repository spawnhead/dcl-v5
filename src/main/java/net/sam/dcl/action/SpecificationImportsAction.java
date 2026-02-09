package net.sam.dcl.action;

import net.sam.dcl.beans.SpecificationImport;
import net.sam.dcl.beans.User;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.controller.IDispatchable;
import net.sam.dcl.controller.IFormAutoSave;
import net.sam.dcl.controller.IProcessBefore;
import net.sam.dcl.controller.actions.DBAction;
import net.sam.dcl.controller.processor.ActionHandler;
import net.sam.dcl.dao.SpecificationImportDAO;
import net.sam.dcl.form.SpecificationImportsForm;
import net.sam.dcl.taglib.table.IReadOnlyChecker;
import net.sam.dcl.taglib.table.ReadOnlyCheckerContext;
import net.sam.dcl.taglib.table.model.PageableDataHolder;
import net.sam.dcl.util.*;
import org.apache.struts.action.ActionForward;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class SpecificationImportsAction extends DBAction implements IDispatchable, IFormAutoSave, IProcessBefore
{
	private class SaveList
	{
		List<SpecificationImportsForm.SpecificationImport> imports;

		public List<SpecificationImportsForm.SpecificationImport> getImports()
		{
			return imports;
		}

		public void setImports(List<SpecificationImportsForm.SpecificationImport> imports)
		{
			this.imports = imports;
		}
	}

	public ActionForward processBefore(IActionContext context) throws Exception
	{
		setCurrentUser(context);
		context.getActionProcessor().addActionHandler("grid", PageableDataHolder.NEXT_PAGE, new ActionHandler()
		{
			public ActionForward process(IActionContext context) throws Exception
			{
				SpecificationImportsForm form = (SpecificationImportsForm) context.getForm();
				form.getGrid().incPage();
				return internalFilter(context);
			}
		});
		context.getActionProcessor().addActionHandler("grid", PageableDataHolder.PREV_PAGE, new ActionHandler()
		{
			public ActionForward process(IActionContext context) throws Exception
			{
				SpecificationImportsForm form = (SpecificationImportsForm) context.getForm();
				form.getGrid().decPage();
				return internalFilter(context);
			}
		});
		return null;
	}

	public ActionForward filter(IActionContext context) throws Exception
	{
		SpecificationImportsForm form = (SpecificationImportsForm) context.getForm();
		form.getGrid().setPage(1);
		return internalFilter(context);
	}

	public ActionForward internalFilter(IActionContext context) throws Exception
	{
		final SpecificationImportsForm form = (SpecificationImportsForm) context.getForm();
		DAOUtils.fillGrid(context, form.getGrid(), "select-specification_imports", SpecificationImportsForm.SpecificationImport.class, null, null);

		List<SpecificationImportsForm.SpecificationImport> imports = new ArrayList<SpecificationImportsForm.SpecificationImport>();
		for (Object object : form.getGrid().getDataList())
		{
			SpecificationImportsForm.SpecificationImport spImport = (SpecificationImportsForm.SpecificationImport) object;
			imports.add(spImport);
		}
		SaveList saveList = new SaveList();
		saveList.setImports(imports);
		StoreUtil.putSession(context.getRequest(), saveList);

		final User currentUser = UserUtil.getCurrentUser(context.getRequest());
		context.getRequest().setAttribute("blockChecker", new IReadOnlyChecker()
		{
			public boolean check(ReadOnlyCheckerContext ctx)
			{
				return !currentUser.isAdmin();
			}
		});

		context.getRequest().setAttribute("sendDateChecker", new IReadOnlyChecker()
		{
			public boolean check(ReadOnlyCheckerContext ctx)
			{
				SpecificationImportsForm.SpecificationImport specificationImport = (SpecificationImportsForm.SpecificationImport) form.getGrid().getDataList().get(ctx.getTable().getRecordCounter() - 1);
				return !((currentUser.isAdmin() || currentUser.isDeclarant()) && StringUtil.isEmpty(specificationImport.getSpi_arrive()));
			}
		});

		return context.getMapping().getInputForward();
	}

	public ActionForward block(IActionContext context) throws Exception
	{
		SpecificationImportsForm form = (SpecificationImportsForm) context.getForm();
		SpecificationImport specificationImport = new SpecificationImport();
		specificationImport.setSpi_id(form.getSpi_id());
		if ("1".equals(form.getSpi_arrive()))
		{
			specificationImport.setSpi_arrive("");
		}
		else
		{
			specificationImport.setSpi_arrive("1");
		}
		SpecificationImportDAO.saveArrive(context, specificationImport);

		return internalFilter(context);
	}

	public ActionForward input(IActionContext context) throws Exception
	{
		SpecificationImportsForm form = (SpecificationImportsForm) context.getForm();
		form.setDate_begin("");
		form.setDate_end("");
		form.setSpi_id("");
		form.setNumber("");
		setCurrentUser(context);
		form.setSpi_arrive("");

		return internalFilter(context);
	}

	public ActionForward restore(IActionContext context) throws Exception
	{
		restoreForm(context);
		return internalFilter(context);
	}

	public ActionForward ajaxSetSentDate(IActionContext context) throws Exception
	{
		SpecificationImportsForm form = (SpecificationImportsForm) context.getForm();
		SpecificationImport specificationImport = new SpecificationImport();
		specificationImport.setSpi_id(form.getSpi_id());
		specificationImport.setSpi_send_date(form.getSpi_send_date());
		SpecificationImportDAO.saveSentDate(context, specificationImport);

		SaveList saveList = (SaveList) StoreUtil.getSession(context.getRequest(), SaveList.class);
		for (SpecificationImportsForm.SpecificationImport spImport : saveList.getImports())
		{
			if (spImport.getSpi_id().equals(specificationImport.getSpi_id()))
			{
				spImport.setSpi_send_date(StringUtil.appDateString2dbDateString(specificationImport.getSpi_send_date()));
				break;
			}
		}
		StoreUtil.putSession(context.getRequest(), saveList);

		return context.getMapping().findForward("ajax");
	}

	public ActionForward ajaxGetLaterDates(IActionContext context) throws Exception
	{
		SpecificationImportsForm form = (SpecificationImportsForm) context.getForm();
		String laterDates = "";
		List<Date> dates = new ArrayList<Date>();
		Date now = StringUtil.getCurrentDateTime();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(now);
		calendar.add(Calendar.DATE, -1);

		SaveList saveList = (SaveList) StoreUtil.getSession(context.getRequest(), SaveList.class);
		for (SpecificationImportsForm.SpecificationImport spImport : saveList.getImports())
		{
			if (!StringUtil.isEmpty(spImport.getSpi_send_date()))
			{
				Date sendDate = spImport.getSpiSendDate();
				if (sendDate.after(calendar.getTime()))
					dates.add(sendDate);
			}
		}

		if (dates.size() > 0)
		{
			Collections.sort(dates);
			for (Date date : dates)
			{
				String currDate = StringUtil.date2appDateString(date) + ", ";
				if (!laterDates.contains(currDate))
					laterDates += currDate;
			}

			laterDates = laterDates.substring(0, laterDates.length() - 2);
			laterDates = StrutsUtil.getMessage(context, "SpecificationImports.sendDates", laterDates);
		}

		StrutsUtil.setAjaxResponse(context, laterDates, false);

		return context.getMapping().findForward("ajax");
	}

	private void setCurrentUser(IActionContext context)
	{
		User currentUser = UserUtil.getCurrentUser(context.getRequest());
		SpecificationImportsForm form = (SpecificationImportsForm) context.getForm();
		if (form.getUser() == null)
		{
			form.setUser(new User());
			return;
		}

		if (currentUser.isManager() && StringUtil.isEmpty(form.getUser().getUsr_id()))
		{
			form.setUser(currentUser);
		}
	}
}
