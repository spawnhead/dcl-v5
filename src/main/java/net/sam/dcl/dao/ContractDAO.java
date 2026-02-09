package net.sam.dcl.dao;

import net.sam.dcl.beans.Contract;
import net.sam.dcl.beans.Specification;
import net.sam.dcl.beans.SpecificationPayment;
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
public class ContractDAO
{

  public static Contract load(IActionContext context, String id, boolean withSpecification) throws Exception
  {
    Contract contract = new Contract(id);

    if (load(context, contract))
    {
      contract.setUsr_date_create(StringUtil.dbDateString2appDateString(contract.getUsr_date_create()));
      contract.setUsr_date_edit(StringUtil.dbDateString2appDateString(contract.getUsr_date_edit()));

      if ( withSpecification )
      {
        loadSpecification(context, contract);
      }
      return contract;
    }
    throw new LoadException(contract, id);
  }

  public static boolean load(IActionContext context, Contract contract) throws Exception
  {
    if (DAOUtils.load(context, "contract-load", contract, null))
    {
      if (!StringUtil.isEmpty(contract.getCreateUser().getUsr_id()))
      {
        UserDAO.load(context, contract.getCreateUser());
      }

      if (!StringUtil.isEmpty(contract.getEditUser().getUsr_id()))
      {
        UserDAO.load(context, contract.getEditUser());
      }

      if (!StringUtil.isEmpty(contract.getContractor().getId()))
      {
        ContractorDAO.load(context, contract.getContractor());
      }

      if (!StringUtil.isEmpty(contract.getCurrency().getId()))
      {
        CurrencyDAO.load(context, contract.getCurrency());
      }

      if (!StringUtil.isEmpty(contract.getSeller().getId()))
      {
        SellerDAO.load(context, contract.getSeller());
      }

      return true;
    }
    else
    {
      return false;
    }
  }

  public static void loadSpecification(IActionContext context, Contract contract) throws Exception
  {
    List<Specification> lst = DAOUtils.fillList(context, "select-specifications", contract, Specification.class, null, null);
    for ( Specification specification : lst )
    {
      List<SpecificationPayment> lstPayments = DAOUtils.fillList(context, "select-specification_payments", specification, SpecificationPayment.class, null, null);
      specification.setSpecificationPayments(lstPayments);
    }
    contract.setSpecifications(lst);
  }

  public static void insert(IActionContext context, Contract contract) throws Exception
  {
    DAOUtils.load(context, "contract-insert", contract, null);
    contract.setListParentIds();
    contract.setListIdsToNull();
    saveSpecification(context, contract);
  }

  public static void save(IActionContext context, Contract contract) throws Exception
  {
    DAOUtils.update(context.getConnection(), context.getSqlResource().get("contract-update"), contract, null);
    contract.setListParentIds();
    saveSpecification(context, contract);
  }

  public static void saveSpecification(IActionContext context, final Contract contract) throws Exception
  {
    VDbConnection conn = context.getConnection();
    final StringBuffer res = new StringBuffer();
    res.append("-1001,"); //фиктивный id, чтобы корректно работало при создании нового Договора.
    for (Specification specification : contract.getSpecifications())
    {
      if (!StringUtil.isEmpty(specification.getSpc_id()))
      {
        res.append(specification.getSpc_id());
        res.append(",");
      }
    }
    if (res.length() > 0)
    {
      res.deleteCharAt(res.length() - 1);
    }
    DAOUtils.update(conn, context.getSqlResource().get("delete_specifications"), new DeleteSpecificationsHelper(contract.getCon_id(), res.toString()), null);
    for (int i = contract.getSpecifications().size() - 1; i >= 0; i--)
    {
      Specification specification = contract.getSpecifications().get(i);
      Specification spcFind = new Specification();
      try
      {
        if (!StringUtil.isEmpty(specification.getSpc_id()))
        {
          spcFind = SpecificationDAO.load(context, specification.getSpc_id());
        }
      }
      catch (Exception e)
      {
        //specification deleted
      }
      if (StringUtil.isEmpty(spcFind.getSpc_id()))
      {
        DAOUtils.load(context, "specification-insert", specification, null);
      }
      else
      {
        DAOUtils.update(conn, context.getSqlResource().get("specification-update"), specification, null);
      }

      DAOUtils.update(conn, context.getSqlResource().get("delete_specification_payments"), specification, null);
      specification.setListParentIds();
      for ( SpecificationPayment specificationPayment : specification.getSpecificationPayments() )
      {
        if ( specificationPayment.getSpp_percent() != 0 || specificationPayment.getSpp_sum() != 0 || !StringUtil.isEmpty(specificationPayment.getSpp_date()) )
        {
          DAOUtils.update(conn, context.getSqlResource().get("insert_specification_payment"), specificationPayment, null);
        }
      }
    }

    // Нужно комитить здесь, а не в DBTransactionAction.processAfter, потому что дальше идет обработка аттачей
    // там выполняется попытка блокировать таблицу DCL_ATTACHMENT,
    // а при удалении спецификаций удаляется и аттачи из этой таблицы по тригеру и если не закомитим - дедлок потом возникает
    context.getConnection().commit();
  }

  static public class DeleteSpecificationsHelper
  {
    String con_id;
    String exist_ids;

    DeleteSpecificationsHelper(String con_id, String exist_ids)
    {
      this.con_id = con_id;
      this.exist_ids = exist_ids;
    }

    public String getCon_id()
    {
      return con_id;
    }

    public void setCon_id(String con_id)
    {
      this.con_id = con_id;
    }

    public String getExist_ids()
    {
      return exist_ids;
    }

    public void setExist_ids(String exist_ids)
    {
      this.exist_ids = exist_ids;
    }
  }

}
