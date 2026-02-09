package net.sam.dcl.dao;

import net.sam.dcl.beans.ClosedRecord;
import net.sam.dcl.beans.ContractClosed;
import net.sam.dcl.beans.Shipping;
import net.sam.dcl.beans.Payment;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.db.VDbConnection;
import net.sam.dcl.controller.IActionContext;

import java.util.List;

/**
 * @author: DG
 * Date: Sep 11, 2005
 * Time: 2:10:04 PM
 */
public class ContractClosedDAO
{
  public static ContractClosed newContractClosed(IActionContext context, String ctcDate) throws Exception
  {
    ContractClosed contractClosed = new ContractClosed();
	  contractClosed.setCtc_date(ctcDate);
    loadClosedRecordNew(context, contractClosed);
    return contractClosed;
  }

  public static ContractClosed load(IActionContext context, String id, ClosedRecord.PermanentDeleted permanentDeleted) throws Exception
  {
    ContractClosed contractClosed = new ContractClosed(id);

    if (load(context, contractClosed))
    {
      contractClosed.setUsr_date_create(StringUtil.dbDateString2appDateString(contractClosed.getUsr_date_create()));
      contractClosed.setUsr_date_edit(StringUtil.dbDateString2appDateString(contractClosed.getUsr_date_edit()));

      contractClosed.setCtc_date(StringUtil.dbDateString2appDateString(contractClosed.getCtc_date()));

      loadClosedRecord(context, contractClosed, permanentDeleted);
      return contractClosed;
    }
    throw new LoadException(contractClosed, id);
  }

  public static boolean load(IActionContext context, ContractClosed contractClosed) throws Exception
  {
    if (DAOUtils.load(context, "contract_closed-load", contractClosed, null))
    {
      if (!StringUtil.isEmpty(contractClosed.getCreateUser().getUsr_id()))
      {
        UserDAO.load(context, contractClosed.getCreateUser());
      }

      if (!StringUtil.isEmpty(contractClosed.getEditUser().getUsr_id()))
      {
        UserDAO.load(context, contractClosed.getEditUser());
      }

      return true;
    }
    else
    {
      return false;
    }
  }

  public static void loadClosedRecordNew(IActionContext context, ContractClosed contractClosed) throws Exception
  {
    List<ClosedRecord> lst = DAOUtils.fillList(context, "select-contracts_closed_new-dao", contractClosed, ClosedRecord.class, null, null);
    int i = 0;
    while ( i < lst.size() )
    {
      ClosedRecord closedRecord = lst.get(i);

      closedRecord.getContract().setCon_date(closedRecord.getContract().getCon_date());
      closedRecord.getSpecification().setSpc_date(StringUtil.dbDateString2appDateString(closedRecord.getSpecification().getSpc_date()));

      ShippingDAO.loadShippingBySpcId(context, closedRecord);
      if ( closedRecord.getShippings().size() == 0 )
      {
        lst.remove(i);
        continue;
      }
      PaymentDAO.loadPaymentsBySpcId(context, closedRecord);

      /**
       * Бывают ситуации, когда в закрытия тянутся спеки, у которых:
       * одна отгрузка И одна и более оплат И сумма отгрузки больше суммы этих оплат.
       * В этом случае очевидно, что закрыть мы её не сможем. Поэтому логично будет в закрытие такие спеки не тянуть.
      **/
      if (
           closedRecord.getShippings().size() == 1 &&
           closedRecord.getPayments().size() == 1 &&
           !StringUtil.isEmpty(closedRecord.getSpecification().getSpc_group_delivery())
         )
      {
        double shpSum = closedRecord.getShippings().get(0).getShp_summ_plus_nds();
        double paySum = closedRecord.getPayments().get(0).getPay_summ();
        if ( shpSum != paySum )
        {
          lst.remove(i);
          continue;
        }
      }
      if (
           closedRecord.getShippings().size() == 1 &&
           closedRecord.getPayments().size() > 1 &&
           !StringUtil.isEmpty(closedRecord.getSpecification().getSpc_group_delivery())
         )
      {
        double shpSum = closedRecord.getShippings().get(0).getShp_summ_plus_nds();
        double paySum = 0;
        for ( Payment payment : closedRecord.getPayments() )
        {
          payment.setPay_summ(StringUtil.roundN(payment.getPay_summ(), 2));
          paySum += payment.getPay_summ();
        }
        paySum = StringUtil.roundN(paySum, 2);
        if ( shpSum > paySum )
        {
          lst.remove(i);
          continue;
        }
      }

	    closedRecord.setCtcDate(contractClosed.getCtcDate());
      closedRecord.formRecList();
      i++;
    }
    contractClosed.setClosedRecords(lst);
  }

  public static void loadClosedRecord(IActionContext context, ContractClosed contractClosed, ClosedRecord.PermanentDeleted permanentDeleted) throws Exception
  {
    List<ClosedRecord> lst = DAOUtils.fillList(context, "select-contracts_closed-dao", contractClosed, ClosedRecord.class, null, null);
    for (ClosedRecord closedRecord : lst)
    {
      closedRecord.getContract().setCon_date(closedRecord.getContract().getCon_date());
      closedRecord.getSpecification().setSpc_date(StringUtil.dbDateString2appDateString(closedRecord.getSpecification().getSpc_date()));

      if ( !StringUtil.isEmpty(closedRecord.getSpecification().getSpc_group_delivery()) )
      {
        ClosedRecord.PermanentDeletedRecord deleteRecord = new ClosedRecord.PermanentDeletedRecord();
        deleteRecord.setSpc_id(closedRecord.getSpecification().getSpc_id());

        List<Shipping> lstShipping = DAOUtils.fillList(context, "select-deleted_closed_record_shipping", closedRecord, Shipping.class, null, null);
        deleteRecord.setDeletedShipping(lstShipping);

        List<Payment> lstPayment = DAOUtils.fillList(context, "select-deleted_closed_record_payments", closedRecord, Payment.class, null, null);
        deleteRecord.setDeletedPayments(lstPayment);

        permanentDeleted.getDeletedRecords().add(permanentDeleted.getDeletedRecords().size(), deleteRecord);
      }

      PaymentDAO.loadPaymentsBySpcId(context, closedRecord);
      ShippingDAO.loadShippingBySpcId(context, closedRecord);

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
    }
    contractClosed.setClosedRecords(lst);
  }

  public static void loadSumEUROutNDSPart(IActionContext context, ClosedRecord closedRecord) throws Exception
  {
    DAOUtils.load(context, "sum_out_nds_eur_part-load", closedRecord, null);
  }

  public static void insert(IActionContext context, ContractClosed contractClosed) throws Exception
  {
    DAOUtils.load(context, "contract_closed-insert", contractClosed, null);
    contractClosed.setListParentIds();
    contractClosed.setListIdsToNull();
    saveClosedRecord(context, contractClosed);
  }

  public static void save(IActionContext context, ContractClosed contractClosed) throws Exception
  {
    DAOUtils.update(context.getConnection(), context.getSqlResource().get("contract_closed-update"), contractClosed, null);
    contractClosed.setListParentIds();
    contractClosed.setListIdsToNull();
    saveClosedRecord(context, contractClosed);
  }

  public static void saveClosedRecord(IActionContext context, ContractClosed contractClosed) throws Exception
  {
    VDbConnection conn = context.getConnection();

    DAOUtils.update(conn, context.getSqlResource().get("inactive_triger_contract"), null, null);

    DAOUtils.update(conn, context.getSqlResource().get("delete_closed_records"), contractClosed, null);
    for (int i = 0; i < contractClosed.getClosedRecords().size(); i++)
    {
      ClosedRecord closedRecord = contractClosed.getClosedRecords().get(i);
      DAOUtils.load(conn, context.getSqlResource().get("insert_closed_record"), closedRecord, null);

      for ( int j = 0; j < closedRecord.getShippings().size(); j++ )
      {
        Shipping shipping = closedRecord.getShippings().get(j);
        ClosedRecordShipping closedRecordShipping = new ClosedRecordShipping(closedRecord.getLcc_id(), shipping.getShp_id());
        DAOUtils.update(conn, context.getSqlResource().get("insert_closed_record_shipping"), closedRecordShipping, null);
      }
      for ( int j = 0; j < closedRecord.getPayments().size(); j++ )
      {
        Payment payment = closedRecord.getPayments().get(j);
        ClosedRecordPayment closedRecordPayment = new ClosedRecordPayment(closedRecord.getLcc_id(), payment.getPay_id(), closedRecord.getSpecification().getSpc_id());
        DAOUtils.update(conn, context.getSqlResource().get("insert_closed_record_payment"), closedRecordPayment, null);
      }

      DAOUtils.update(conn, context.getSqlResource().get("close_contract"), closedRecord, null);
    }

    DAOUtils.update(conn, context.getSqlResource().get("active_triger_contract"), null, null);
  }

  public static void saveUnblock(IActionContext context, ContractClosed contractClosed) throws Exception
  {
    DAOUtils.update(context.getConnection(), context.getSqlResource().get("contract_closed-unblock"), contractClosed, null);
  }

  public static void saveBlockSelected(IActionContext context, ContractClosed contractClosed) throws Exception
  {
    DAOUtils.update(context.getConnection(), context.getSqlResource().get("contract_closed-block-selected"), contractClosed, null);
  }

  public static void delete(IActionContext context, ContractClosed contractClosed) throws Exception
  {
    DAOUtils.update(context.getConnection(), context.getSqlResource().get("contract_closed-delete"), contractClosed, null);
  }

  static public class ClosedRecordShipping
  {
    String lcc_id;
    String shp_id;

    public ClosedRecordShipping(String lcc_id, String shp_id)
    {
      this.lcc_id = lcc_id;
      this.shp_id = shp_id;
    }

    public String getLcc_id()
    {
      return lcc_id;
    }

    public void setLcc_id(String lcc_id)
    {
      this.lcc_id = lcc_id;
    }

    public String getShp_id()
    {
      return shp_id;
    }

    public void setShp_id(String shp_id)
    {
      this.shp_id = shp_id;
    }
  }

  static public class ClosedRecordPayment
  {
    String lcc_id;
    String pay_id;
    String spc_id;

    public ClosedRecordPayment(String lcc_id, String pay_id, String spc_id)
    {
      this.lcc_id = lcc_id;
      this.pay_id = pay_id;
      this.spc_id = spc_id;
    }

    public String getLcc_id()
    {
      return lcc_id;
    }

    public void setLcc_id(String lcc_id)
    {
      this.lcc_id = lcc_id;
    }

    public String getPay_id()
    {
      return pay_id;
    }

    public void setPay_id(String pay_id)
    {
      this.pay_id = pay_id;
    }

    public String getSpc_id()
    {
      return spc_id;
    }

    public void setSpc_id(String spc_id)
    {
      this.spc_id = spc_id;
    }
  }
}
