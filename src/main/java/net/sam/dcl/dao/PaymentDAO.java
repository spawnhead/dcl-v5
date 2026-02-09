package net.sam.dcl.dao;

import net.sam.dcl.beans.PaySum;
import net.sam.dcl.beans.Payment;
import net.sam.dcl.beans.ClosedRecord;
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
public class PaymentDAO
{

  public static Payment load(IActionContext context, String id) throws Exception
  {

    Payment payment = new Payment(id);

    if (load(context, payment))
    {
      payment.setUsr_date_create(StringUtil.dbDateString2appDateString(payment.getUsr_date_create()));
      payment.setUsr_date_edit(StringUtil.dbDateString2appDateString(payment.getUsr_date_edit()));

      payment.setPay_date(StringUtil.dbDateString2appDateString(payment.getPay_date()));
      payment.setPay_course_nbrb_date(StringUtil.dbDateString2appDateString(payment.getPay_course_nbrb_date()));

      loadPaySum(context, payment);
      return payment;
    }
    throw new LoadException(payment, id);
  }

  public static Payment loadClone(IActionContext context, String id) throws Exception
  {

    Payment payment = new Payment(id);

    if (load(context, payment))
    {
      payment.setUsr_date_create(StringUtil.dbDateString2appDateString(payment.getUsr_date_create()));
      payment.setUsr_date_edit(StringUtil.dbDateString2appDateString(payment.getUsr_date_edit()));

      payment.setPay_date(StringUtil.dbDateString2appDateString(payment.getPay_date()));

      return payment;
    }
    throw new LoadException(payment, id);
  }

  public static boolean load(IActionContext context, Payment payment) throws Exception
  {
    if (DAOUtils.load(context, "payment-load", payment, null))
    {
      if (!StringUtil.isEmpty(payment.getCreateUser().getUsr_id()))
      {
        UserDAO.load(context, payment.getCreateUser());
      }

      if (!StringUtil.isEmpty(payment.getEditUser().getUsr_id()))
      {
        UserDAO.load(context, payment.getEditUser());
      }

      if (!StringUtil.isEmpty(payment.getContractor().getId()))
      {
        ContractorDAO.load(context, payment.getContractor());
        payment.getContractor().setAccount(payment.getPay_account());
        ContractorDAO.loadDoubleAccount(context, payment.getContractor());
      }

      if (!StringUtil.isEmpty(payment.getCurrency().getId()))
      {
        CurrencyDAO.load(context, payment.getCurrency());
      }

      return true;
    }
    else
    {
      return false;
    }
  }

  public static void loadPaySum(IActionContext context, Payment payment) throws Exception
  {
    List<PaySum> lst = DAOUtils.fillList(context, "select-pay_summs-dao", payment, PaySum.class, null, null);
    payment.setPaySums(lst);
  }

  public static void loadPaymentsBySpcId(IActionContext context, ClosedRecord closedRecord) throws Exception
  {
    List<Payment> lst = DAOUtils.fillList(context, "select-payments_by_spc_id", closedRecord, Payment.class, null, null);
    closedRecord.setPayments(lst);
  }

  public static void insert(IActionContext context, Payment payment) throws Exception
  {
    DAOUtils.load(context, "payment-insert", payment, null);
    payment.setListParentIds();
    payment.setListIdsToNull();
    savePaySum(context, payment);
    context.getConnection().commit(); //комитим, чтобы видили при апдейте.
    updateDeliveryDate(context, payment);
  }

  public static void save(IActionContext context, Payment payment) throws Exception
  {
    DAOUtils.update(context.getConnection(), context.getSqlResource().get("payment-update"), payment, null);
    payment.setListParentIds();
    savePaySum(context, payment);
    context.getConnection().commit(); //комитим, чтобы видили при апдейте.
    updateDeliveryDate(context, payment);
  }

  public static void savePaySum(IActionContext context, Payment payment) throws Exception
  {
    VDbConnection conn = context.getConnection();
    DAOUtils.update(conn, context.getSqlResource().get("delete_pay_summs"), payment, null);
    for (int i = 0; i < payment.getPaySums().size(); i++)
    {
      PaySum paySum = payment.getPaySums().get(i);
      PaySum paySumFind = new PaySum();
      try
      {
        if (!StringUtil.isEmpty(paySum.getLps_id()))
        {
          paySumFind = PaySumDAO.load(context, paySum.getLps_id());
        }
      }
      catch (Exception e)
      {
        //pay sum deleted
      }
      if (StringUtil.isEmpty(paySumFind.getLps_id()))
      {
        DAOUtils.update(conn, context.getSqlResource().get("insert_pay_summ"), paySum, null);
      }
      else
      {
        DAOUtils.update(conn, context.getSqlResource().get("update_pay_summ"), paySum, null);
      }
    }
  }

  public static void updateDeliveryDate(IActionContext context, Payment payment) throws Exception
  {
    VDbConnection conn = context.getConnection();
    for (PaySum paySum : payment.getPaySums())
    {
      DAOUtils.update(conn, context.getSqlResource().get("update_delivery_date"), paySum, null);
    }
    if (payment.getPaySumsOld() != null)
    {
      //Удаляем те спецификации, которые не были удалены из списка - мы их уже обработали.
      for (PaySum paySum : payment.getPaySums())
      {
        for (PaySum paySumOld : payment.getPaySumsOld())
        {
          if (paySumOld.getSpecification().getSpc_id().equals(paySum.getSpecification().getSpc_id()))
          {
            payment.getPaySumsOld().remove(paySumOld);
            break;
          }
        }
      }
      for (PaySum paySumOld : payment.getPaySumsOld())
    {
      DAOUtils.update(conn, context.getSqlResource().get("update_delivery_date"), paySumOld, null);
    }
    }
  }
}
