package net.sam.dcl.dao;

import net.sam.dcl.beans.Contract;
import net.sam.dcl.beans.Specification;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.db.VResultSet;

import java.util.List;

/**
 * @author: DG
 * Date: Sep 11, 2005
 * Time: 2:10:04 PM
 */
public class SpecificationDAO
{
  public static Specification load(IActionContext context, String id) throws Exception
  {
    Specification specification = new Specification(id);

    if (load(context, specification))
    {
      specification.setSpc_date(StringUtil.dbDateString2appDateString(specification.getSpc_date()));

      return specification;
    }
    throw new LoadException(specification, id);
  }

  public static boolean load(IActionContext context, Specification specification) throws Exception
  {
    return DAOUtils.load(context, "specification-load", specification, null);
  }

  public static Specification loadForPayment(IActionContext context, String id, String pay_id) throws Exception
  {
    Specification specification = new Specification(id);
    if ( StringUtil.isEmpty(pay_id) ) //для нового документа оплаты, чтобы нормально работал остаток по спецификации
    {
      pay_id = "-1";
    }
    specification.setPay_id(pay_id);

    if (loadForPayment(context, specification))
    {
      return specification;
    }
    throw new LoadException(specification, id);
  }

  public static boolean loadForPayment(IActionContext context, Specification specification) throws Exception
  {
    if (DAOUtils.load(context, "specification-load-for-payment", specification, null))
    {
      return true;
    }
    else
    {
      return false;
    }
  }

  public static List<Specification> loadSpecificationsDepFromContract(IActionContext context, Contract contract) throws Exception
  {
    return DAOUtils.fillList(context, "select-specifications-for-contract-id", contract, Specification.class, null, null);
  }

  public static String loadSpecificationsNumbersForContract(IActionContext context, Contract contract) throws Exception
  {
    VResultSet resultSet = DAOUtils.executeQuery(context, "select-specifications_numbers-for-contract-id", contract, null);
    List<String> specificationNumbers = DAOUtils.resultSet2StringList(resultSet);
    StringBuffer result = new StringBuffer();
    for (int i = 0; i < specificationNumbers.size(); i++)
    {
      result.append(specificationNumbers.get(i));
      if (i != specificationNumbers.size() - 1)
      {
        result.append(", ");
      }
    }
    return result.toString();
  }

  public static void loadNotClosedPayments(IActionContext context, Specification specification) throws Exception
  {
    List<Specification.Payment> lst = DAOUtils.fillList(context, "select-not_closed_payments-for-spec", specification, Specification.Payment.class, null, null);
    specification.setNotClosedPayments(lst);
  }

  public static void loadPayedSum(IActionContext context, Specification specification) throws Exception
  {
    DAOUtils.load(context, "select-payed_sum-for-spec", specification, null);
	  specification.setPayed_summ(StringUtil.roundN(specification.getPayed_summ(), 2));
  }

  public static void loadShippedSum(IActionContext context, Specification specification) throws Exception
  {
    DAOUtils.load(context, "select-shipped_sum-for-spec", specification, null);
	  specification.setShipped_summ(StringUtil.roundN(specification.getShipped_summ(), 2));
  }

  public static void loadPayedDate(IActionContext context, Specification specification) throws Exception
  {
    DAOUtils.load(context, "select-payed_date-for-spec", specification, null);
  }
}
