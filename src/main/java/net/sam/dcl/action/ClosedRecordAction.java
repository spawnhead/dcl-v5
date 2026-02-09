package net.sam.dcl.action;

import net.sam.dcl.controller.actions.DBTransactionAction;
import net.sam.dcl.controller.IDispatchable;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.StoreUtil;
import net.sam.dcl.util.StrutsUtil;
import net.sam.dcl.beans.ClosedRecord;
import net.sam.dcl.beans.ContractClosed;
import net.sam.dcl.form.ClosedRecordForm;
import net.sam.dcl.dao.*;
import net.sam.dcl.taglib.table.IStyleClassChecker;
import net.sam.dcl.taglib.table.StyleClassCheckerContext;
import net.sam.dcl.taglib.table.IShowChecker;
import net.sam.dcl.taglib.table.ShowCheckerContext;
import org.apache.struts.action.ActionForward;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class ClosedRecordAction extends DBTransactionAction implements IDispatchable
{

  private void saveCurrentFormToBean(IActionContext context, ClosedRecord closedRecord)
  {
    ClosedRecordForm form = (ClosedRecordForm) context.getForm();

    closedRecord.setNumber(form.getNumber());
    closedRecord.setCtc_id(form.getCtc_id());
    closedRecord.setLcc_charges(StringUtil.appCurrencyString2double(form.getLcc_charges()));
    closedRecord.setLcc_montage(StringUtil.appCurrencyString2double(form.getLcc_montage()));
    closedRecord.setLcc_transport(StringUtil.appCurrencyString2double(form.getLcc_transport()));
    closedRecord.setLcc_update_sum(StringUtil.appCurrencyString2double(form.getLcc_update_sum()));
    closedRecord.setSum_out_nds_eur(StringUtil.appCurrencyString2double(form.getSum_out_nds_eur()));
    closedRecord.setContractor(form.getContractor());
    closedRecord.setContract(form.getContract());
    closedRecord.setSpecification(form.getSpecification());
    closedRecord.setPay_summ(StringUtil.appCurrencyString2double(form.getPay_summ()));
    closedRecord.setShp_summ(StringUtil.appCurrencyString2double(form.getShp_summ()));
    closedRecord.setManagers(form.getManagers());
    closedRecord.setProducts(form.getProducts());

    try
    {
      if (!StringUtil.isEmpty(form.getContractor().getId()))
      {
        closedRecord.setContractor(ContractorDAO.load(context, form.getContractor().getId()));
      }
      if (!StringUtil.isEmpty(form.getContract().getCon_id()))
      {
        closedRecord.setContract(ContractDAO.load(context, form.getContract().getCon_id(), false));
      }
      if (!StringUtil.isEmpty(form.getSpecification().getSpc_id()))
      {
        closedRecord.setSpecification(SpecificationDAO.load(context, form.getSpecification().getSpc_id()));
      }
    }
    catch (Exception e)
    {
      StrutsUtil.addError(context, e);
    }

  }

  private void getCurrentFormFromBean(IActionContext context, ClosedRecord closedRecord)
  {
    ClosedRecordForm form = (ClosedRecordForm) context.getForm();

    form.setNumber(closedRecord.getNumber());
    form.setCtc_id(closedRecord.getCtc_id());
    form.setLcc_charges(closedRecord.getLcc_charges_formatted());
    form.setLcc_montage(closedRecord.getLcc_montage_formatted());
    form.setLcc_transport(closedRecord.getLcc_transport_formatted());
    form.setLcc_update_sum(closedRecord.getLcc_update_sum_formatted());
    form.setSum_out_nds_eur(closedRecord.getSum_out_nds_eur_formatted());
    form.setContractor(closedRecord.getContractor());
    form.setContract(closedRecord.getContract());
    form.setSpecification(closedRecord.getSpecification());
    form.setPay_summ(StringUtil.double2appCurrencyString(closedRecord.getPay_summ()));
    form.setShp_summ(StringUtil.double2appCurrencyString(closedRecord.getShp_summ()));
    form.setManagers(closedRecord.getManagers());
    form.setProducts(closedRecord.getProducts());
  }

  public ActionForward input(IActionContext context) throws Exception
  {
    ContractClosed contractClosed = (ContractClosed) StoreUtil.getSession(context.getRequest(), ContractClosed.class);
    final ClosedRecordForm form = (ClosedRecordForm) context.getForm();
    form.setFormReadOnly("1".equals(contractClosed.getCtc_block()));

    context.getRequest().setAttribute("style-checker", new IStyleClassChecker()
    {
      int size = form.getGridResult().getDataList().size();

      public String check(StyleClassCheckerContext context)
      {
        if (context.getTable().getRecordCounter() == size)
        {
          return "bold-cell";
        }
        return "";
      }
    });

    final ClosedRecord closedRecord = (ClosedRecord) StoreUtil.getSession(context.getRequest(), ClosedRecord.class);
    context.getRequest().setAttribute("show-checker-shipping", new IShowChecker()
    {
      int size = closedRecord.getShippings().size();

      public boolean check(ShowCheckerContext context)
      {
        if (context.getTable().getRecordCounter() > size)
        {
          return false;
        }
        return true;
      }
    }
    );
    context.getRequest().setAttribute("show-checker-payment", new IShowChecker()
    {
      int size = closedRecord.getPayments().size();

      public boolean check(ShowCheckerContext context)
      {
        if (context.getTable().getRecordCounter() > size)
        {
          return false;
        }
        return true;
      }
    }
    );

    if ( !closedRecord.isCorrectLine() )
    {
      form.setShowDeleteMsg(true);
    }
    else
    {
      form.setShowDeleteMsg(false);
    }

    if ( !StringUtil.isEmpty(closedRecord.getSpecification().getSpc_group_delivery()) )
    {
      form.setShowForGroupDelivery(true);
    }
    else
    {
      form.setShowForGroupDelivery(false);
    }

    return context.getMapping().findForward("form");
  }

  public ActionForward deleteSelected(IActionContext context) throws Exception
  {
    ClosedRecord closedRecord = (ClosedRecord) StoreUtil.getSession(context.getRequest(), ClosedRecord.class);
    saveCurrentFormToBean(context, closedRecord);

    ClosedRecordForm form = (ClosedRecordForm) context.getForm();
    closedRecord.deleteSelected(form.getGridResult().getDataList());
    closedRecord.formRecList();
    form.getGridResult().setDataList(closedRecord.getListPayShip());
    getCurrentFormFromBean(context, closedRecord);

    StoreUtil.putSession(context.getRequest(), closedRecord);

    return input(context);
  }

  public ActionForward edit(IActionContext context) throws Exception
  {
    ClosedRecordForm form = (ClosedRecordForm) context.getForm();
    ContractClosed contractClosed = (ContractClosed) StoreUtil.getSession(context.getRequest(), ContractClosed.class);

    ClosedRecord closedRecord = contractClosed.findClosedRecord(form.getNumber());
    closedRecord.getDeletedPayments().clear();
    closedRecord.getDeletedShipping().clear();
    PaymentDAO.loadPaymentsBySpcId(context, closedRecord);
    ShippingDAO.loadShippingBySpcId(context, closedRecord);

    ClosedRecord.PermanentDeleted permanentDeleted = (ClosedRecord.PermanentDeleted) StoreUtil.getSession(context.getRequest(), ClosedRecord.PermanentDeleted.class);
    if ( null != permanentDeleted )
    {
      ClosedRecord.PermanentDeletedRecord deleteRecord = permanentDeleted.getDeleteRecordBySpcId(closedRecord.getSpecification().getSpc_id());
      if ( !StringUtil.isEmpty(deleteRecord.getSpc_id()) )
      {
        closedRecord.deletePermanentShipping(deleteRecord.getDeletedShipping());
        closedRecord.deletePermanentPayments(deleteRecord.getDeletedPayments());
      }
    }

	  closedRecord.setCtcDate(contractClosed.getCtcDate());
    closedRecord.formRecList();
    form.getGridResult().setDataList(closedRecord.getListPayShip());
    getCurrentFormFromBean(context, closedRecord);

    StoreUtil.putSession(context.getRequest(), closedRecord);

    return input(context);
  }

  public ActionForward back(IActionContext context) throws Exception
  {
    ClosedRecord closedRecord = (ClosedRecord) StoreUtil.getSession(context.getRequest(), ClosedRecord.class);
    closedRecord.restoreDeleted();
    closedRecord.formRecList();

    return context.getMapping().findForward("back");
  }

  public ActionForward process(IActionContext context) throws Exception
  {
    ClosedRecordForm form = (ClosedRecordForm) context.getForm();
    ContractClosed contractClosed = (ContractClosed) StoreUtil.getSession(context.getRequest(), ContractClosed.class);
    ClosedRecord closedRecord = (ClosedRecord) StoreUtil.getSession(context.getRequest(), ClosedRecord.class);

    saveCurrentFormToBean(context, closedRecord);

    contractClosed.updateClosedRecord(form.getNumber(), closedRecord);

    if ( closedRecord.getDeletedShipping().size() != 0 || closedRecord.getDeletedPayments().size() != 0 )
    {
      ClosedRecord.PermanentDeleted permanentDeleted = (ClosedRecord.PermanentDeleted) StoreUtil.getSession(context.getRequest(), ClosedRecord.PermanentDeleted.class);
      if ( null == permanentDeleted )
      {
        permanentDeleted = new ClosedRecord.PermanentDeleted();
      }
      ClosedRecord.PermanentDeletedRecord deleteRecord = permanentDeleted.getDeleteRecordBySpcId(closedRecord.getSpecification().getSpc_id());
      deleteRecord.setSpc_id(closedRecord.getSpecification().getSpc_id());
      deleteRecord.fillDeleteShipping(closedRecord.getDeletedShipping());
      deleteRecord.fillDeletePayments(closedRecord.getDeletedPayments());
      permanentDeleted.getDeletedRecords().add(permanentDeleted.getDeletedRecords().size(), deleteRecord);
      StoreUtil.putSession(context.getRequest(), permanentDeleted);
    }

    return context.getMapping().findForward("back");
  }

}
