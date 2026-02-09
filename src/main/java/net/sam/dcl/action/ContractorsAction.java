package net.sam.dcl.action;

import net.sam.dcl.beans.Contractor;
import net.sam.dcl.beans.ContractorsPrint;
import net.sam.dcl.beans.Department;
import net.sam.dcl.beans.User;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.controller.IDispatchable;
import net.sam.dcl.controller.IFormAutoSave;
import net.sam.dcl.controller.IProcessBefore;
import net.sam.dcl.controller.actions.DBAction;
import net.sam.dcl.controller.processor.ActionHandler;
import net.sam.dcl.dao.ContractorDAO;
import net.sam.dcl.form.ContractorsForm;
import net.sam.dcl.report.excel.Grid2Excel;
import net.sam.dcl.taglib.table.IReadOnlyChecker;
import net.sam.dcl.taglib.table.IShowChecker;
import net.sam.dcl.taglib.table.ReadOnlyCheckerContext;
import net.sam.dcl.taglib.table.ShowCheckerContext;
import net.sam.dcl.taglib.table.model.PageableDataHolder;
import net.sam.dcl.config.Config;
import net.sam.dcl.util.*;
import net.sam.dcl.log.Log;
import org.apache.struts.action.ActionForward;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */

public class ContractorsAction extends DBAction implements IDispatchable, IFormAutoSave, IProcessBefore
{
  public ActionForward processBefore(IActionContext context) throws Exception
  {
    context.getActionProcessor().addActionHandler("grid", PageableDataHolder.NEXT_PAGE, new ActionHandler()
    {
      public ActionForward process(IActionContext context) throws Exception
      {
        ContractorsForm form = (ContractorsForm) context.getForm();
        form.getGrid().incPage();
        form.recalculateSelectedIds();
        return internalFilter(context);
      }
    });
    context.getActionProcessor().addActionHandler("grid", PageableDataHolder.PREV_PAGE, new ActionHandler()
    {
      public ActionForward process(IActionContext context) throws Exception
      {
        ContractorsForm form = (ContractorsForm) context.getForm();
        form.getGrid().decPage();
        form.recalculateSelectedIds();
        return internalFilter(context);
      }
    });
    return null;
  }

  public ActionForward filter(IActionContext context) throws Exception
  {
    ContractorsForm form = (ContractorsForm) context.getForm();
    form.recalculateSelectedIds();
    if ( StringUtil.isEmpty(form.getUser().getUserFullName()) )
      form.setUser(new User());
    form.getGrid().setPage(1);
    form.setNeedPrint(false);
    return internalFilter(context);
  }

  public ActionForward internalFilter(IActionContext context) throws Exception
  {
    final ContractorsForm form = (ContractorsForm) context.getForm();
    int pageSize = Config.getNumber("table.pageSize", 15);
    int page = form.getGrid().getPage();
    int offset = Math.max(0, (page - 1) * pageSize);
    VParameter pagingParam = new VParameter();
    pagingParam.add("pageSizePlusOne", String.valueOf(pageSize + 1), Types.VARCHAR);
    pagingParam.add("offset", String.valueOf(offset), Types.VARCHAR);

    long t0 = System.currentTimeMillis();
    DAOUtils.fillGrid(context, form.getGrid(), "select-contractors", ContractorsForm.Contractor.class, null, pagingParam);
    long t1 = System.currentTimeMillis();
    if (Config.getNumber("log.contractors.timing", 0) == 1)
      Log.info("ContractorsAction.internalFilter: fillGrid " + (t1 - t0) + " ms, rows=" + (form.getGrid().getDataList() != null ? form.getGrid().getDataList().size() : 0), "ContractorsAction");

    final User currentUser = UserUtil.getCurrentUser(context.getRequest());

    context.getRequest().setAttribute("blockChecker", new IReadOnlyChecker()
    {
      public boolean check(ReadOnlyCheckerContext ctx) throws Exception
      {
        return !currentUser.isAdmin();
      }
    });

    context.getRequest().setAttribute("show-delete-checker", new IShowChecker()
    {
      public boolean check(ShowCheckerContext context)
      {
        ContractorsForm.Contractor contractor = (ContractorsForm.Contractor) form.getGrid().getDataList().get(context.getTable().getRecordCounter() - 1);
        return !contractor.isOccupied();
      }
    }
    );

    for (int i = 0; i < form.getGrid().getDataList().size(); i++)
    {
      ContractorsForm.Contractor contractor = (ContractorsForm.Contractor) form.getGrid().getDataList().get(i);
      if (form.isInSelectedList(contractor.getCtr_id()))
        contractor.setSelectedItem("1");
    }

    if (form.isNeedPrint())
    {
      form.setPrint("true");
    }
    else
    {
      form.setPrint("false");
    }
    form.setNeedPrint(false);

    return context.getMapping().getInputForward();
  }

  public ActionForward block(IActionContext context) throws Exception
  {
    ContractorsForm form = (ContractorsForm) context.getForm();
    Contractor contractor = new Contractor();
    contractor.setId(form.getCtr_id_journal());
    if ("1".equals(form.getBlock()))
    {
      contractor.setBlock("");
    }
    else
    {
      contractor.setBlock("1");
    }
    ContractorDAO.saveBlock(context, contractor);
    form.setNeedPrint(false);

    return internalFilter(context);
  }

  public ActionForward print(IActionContext context) throws Exception
  {
    ContractorsForm form = (ContractorsForm) context.getForm();
    form.recalculateSelectedIds();
    ContractorsPrint contractorsPrint = new ContractorsPrint();
    contractorsPrint.setPrintScale(form.getPrintScale());
    contractorsPrint.setSelectedIds(form.getSelectedIds());
    StoreUtil.putSession(context.getRequest(), contractorsPrint);
    form.setNeedPrint(true);
    form.setSelectedIds(new ArrayList<String>());
    return internalFilter(context);
  }

  public ActionForward input(IActionContext context) throws Exception
  {
    ContractorsForm form = (ContractorsForm) context.getForm();
    form.setCtr_account_journal("");
    form.setCtr_address_journal("");
    form.setCtr_full_name_journal("");
    form.setCtr_id_journal("");
    form.setCtr_name_journal("");
    form.setCtr_full_name_journal("");
    form.setCtr_email_journal("");
    form.setCtr_unp_journal("");
    form.setBlock("");
    form.setUser(new User());
    form.setDepartment(new Department());
    User currentUser = UserUtil.getCurrentUser(context.getRequest());
    if ( currentUser.isOnlyManager() )
    {
      form.setUser(currentUser);
    }
    form.setNeedPrint(false);
    return internalFilter(context);
  }

  public ActionForward restore(IActionContext context) throws Exception
  {
    restoreForm(context);
    ContractorsForm form = (ContractorsForm) context.getForm();
    form.resetSelectedIds();
    form.setNeedPrint(false);
    return internalFilter(context);
  }

  public ActionForward mergeContractors(IActionContext context) throws Exception
  {
    ContractorsForm form = (ContractorsForm) context.getForm();
    List<String> selectedIds = form.getContractorsSelectedIds();
    if (selectedIds.size() == 0)
    {
      StrutsUtil.addError(context, "error.contractors.only_one_contractor", null);
      return internalFilter(context);
    }
    if (selectedIds.size() > 1)
    {
      StrutsUtil.addError(context, "error.contractors.too_many_contractors", null);
      return internalFilter(context);
    }

    String oldId = selectedIds.get(0);
    String newId = form.getMergeTargetId().toString();
    context.getRequest().getSession().setAttribute(Contractor.oldContractorId, oldId);
    context.getRequest().getSession().setAttribute(Contractor.newContractorId, newId);
    return context.getMapping().findForward("mergeContractors");
  }

  public ActionForward generateExcel(IActionContext context) throws Exception
  {
    ContractorsForm formContext = (ContractorsForm) context.getForm();
    ContractorsForm form = new ContractorsForm();
    form.setCtr_name_journal(formContext.getCtr_name_journal());
    form.setCtr_address_journal(formContext.getCtr_address_journal());
    form.setCtr_account_journal(formContext.getCtr_account_journal());
    form.setCtr_email_journal(formContext.getCtr_email_journal());
    form.setCtr_unp_journal(formContext.getCtr_unp_journal());
    form.setCtr_full_name_journal(formContext.getCtr_full_name_journal());
    form.setUser(formContext.getUser());
    List<ContractorsForm.Contractor> lst = DAOUtils.fillList(context, "select-contractors-all", form, ContractorsForm.Contractor.class, null, null);

    Grid2Excel grid2Excel = new Grid2Excel("Contractors list");
    grid2Excel.doGrid2Excel(context, toExcelGrid(context, lst));
    return null;
  }

  List toExcelGrid(IActionContext context, List contractors)
  {
    List<Object> rows = new ArrayList<Object>();

    List<Object> header = new ArrayList<Object>();
    try
    {
      header.add(StrutsUtil.getMessage(context, "Contractor.ctr_full_name"));
      header.add(StrutsUtil.getMessage(context, "Contractor.ctr_address"));
      header.add(StrutsUtil.getMessage(context, "Contractor.ctr_phone"));
      header.add(StrutsUtil.getMessage(context, "Contractor.ctr_fax"));
      header.add(StrutsUtil.getMessage(context, "Contractor.ctr_email"));
      rows.add(header);

      for (Object contractor1 : contractors)
      {
        ContractorsForm.Contractor contractor = (ContractorsForm.Contractor) contractor1;
        List<Object> record = new ArrayList<Object>();
        record.add(contractor.getCtr_full_name());
        record.add(contractor.getCtr_address());
        record.add(contractor.getCtr_phone());
        record.add(contractor.getCtr_fax());
        record.add(contractor.getCtr_email());
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
