package net.sam.dcl.dao;

import net.sam.dcl.beans.ConditionForContract;
import net.sam.dcl.beans.ConditionForContractProduce;
import net.sam.dcl.beans.Constants;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.service.helper.Number1CHistoryHelper;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.db.VDbConnection;

import java.util.List;

/**
 * @author: DG
 * Date: Sep 11, 2005
 * Time: 2:10:04 PM
 */
public class ConditionForContractDAO
{

  public static ConditionForContract load(IActionContext context, String id) throws Exception
  {
    ConditionForContract conditionForContract = new ConditionForContract(id);

    if (load(context, conditionForContract))
    {
      conditionForContract.setUsr_date_create(StringUtil.dbDateTimeString2appDateTimeString(conditionForContract.getUsr_date_create()));
      conditionForContract.setUsr_date_edit(StringUtil.dbDateTimeString2appDateTimeString(conditionForContract.getUsr_date_edit()));
      conditionForContract.setUsr_date_place(StringUtil.dbDateTimeString2appDateTimeString(conditionForContract.getUsr_date_place()));
      conditionForContract.setUsr_date_execute(StringUtil.dbDateString2appDateString(conditionForContract.getUsr_date_execute()));
      conditionForContract.setUsr_date_annul(StringUtil.dbDateString2appDateString(conditionForContract.getUsr_date_annul()));
      conditionForContract.setCfc_con_date(StringUtil.dbDateString2appDateString(conditionForContract.getCfc_con_date()));
      conditionForContract.setCfc_spc_date(StringUtil.dbDateString2appDateString(conditionForContract.getCfc_spc_date()));
      conditionForContract.setCfc_date_con_to(StringUtil.dbDateString2appDateString(conditionForContract.getCfc_date_con_to()));

      loadProduce(context, conditionForContract);
      return conditionForContract;
    }
    throw new LoadException(conditionForContract, id);
  }

  public static boolean load(IActionContext context, ConditionForContract conditionForContract) throws Exception
  {
    if (DAOUtils.load(context, "condition_for_contract-load", conditionForContract, null))
    {
      if (!StringUtil.isEmpty(conditionForContract.getCreateUser().getUsr_id()))
      {
        UserDAO.load(context, conditionForContract.getCreateUser());
      }

      if (!StringUtil.isEmpty(conditionForContract.getEditUser().getUsr_id()))
      {
        UserDAO.load(context, conditionForContract.getEditUser());
      }

      if (!StringUtil.isEmpty(conditionForContract.getPlaceUser().getUsr_id()))
      {
        UserDAO.load(context, conditionForContract.getPlaceUser());
      }

      if (!StringUtil.isEmpty(conditionForContract.getExecuteUser().getUsr_id()))
      {
        UserDAO.load(context, conditionForContract.getExecuteUser());
      }

      if (!StringUtil.isEmpty(conditionForContract.getAnnulUser().getUsr_id()))
      {
        UserDAO.load(context, conditionForContract.getAnnulUser());
      }

      if (!StringUtil.isEmpty(conditionForContract.getContractor().getId()))
      {
        ContractorDAO.load(context, conditionForContract.getContractor());
      }

      if (!StringUtil.isEmpty(conditionForContract.getSeller().getId()))
      {
	      SellerDAO.load(context, conditionForContract.getSeller());
      }

      if (!StringUtil.isEmpty(conditionForContract.getContract().getCon_id()))
      {
        ContractDAO.load(context, conditionForContract.getContract());
      }

      if (!StringUtil.isEmpty(conditionForContract.getCurrency().getId()))
      {
        CurrencyDAO.load(context, conditionForContract.getCurrency());
      }

      if (!StringUtil.isEmpty(conditionForContract.getContactPersonSign().getCps_id()))
      {
        ContactPersonDAO.load(context, conditionForContract.getContactPersonSign());
      }

      if (!StringUtil.isEmpty(conditionForContract.getContactPerson().getCps_id()))
      {
        ContactPersonDAO.load(context, conditionForContract.getContactPerson());
      }

      if (!StringUtil.isEmpty(conditionForContract.getPurchasePurpose().getId()))
      {
        PurchasePurposeDAO.load(context, conditionForContract.getPurchasePurpose());
      }

      return true;
    }
    else
    {
      return false;
    }
  }

  public static void loadProduce(IActionContext context, ConditionForContract conditionForContract) throws Exception
  {
    List<ConditionForContractProduce> lst = DAOUtils.fillList(context, "select-condition_for_contract_produces", conditionForContract, ConditionForContractProduce.class, null, null);
    for ( ConditionForContractProduce conditionForContractProduce : lst )
    {
      if ( null != conditionForContractProduce.getProduce().getId() )
      {
        conditionForContractProduce.setProduce(ProduceDAO.loadProduce(conditionForContractProduce.getProduce().getId()));
        if (Constants.techServiceSellerId.equals(conditionForContract.getSeller().getId())) {
          String number1C = Number1CHistoryHelper.getLastActual1CNumberFromHistory(context, String.valueOf(conditionForContractProduce.getProduce().getId()), conditionForContract.getUsr_date_create());
          conditionForContractProduce.setNumber1C(number1C);
        }
      }
    }
    conditionForContract.setProduces(lst);
  }

  public static void insert(IActionContext context, ConditionForContract conditionForContract) throws Exception
  {
    DAOUtils.load(context, "condition_for_contract-insert", conditionForContract, null);
    conditionForContract.setListParentIds();
    conditionForContract.setListIdsToNull();
    saveProduce(context, conditionForContract);
  }

  public static void save(IActionContext context, ConditionForContract conditionForContract) throws Exception
  {
    DAOUtils.update(context.getConnection(), context.getSqlResource().get("condition_for_contract-update"), conditionForContract, null);
    conditionForContract.setListParentIds();
    saveProduce(context, conditionForContract);
  }

	public static void saveCheckPrice(IActionContext context, ConditionForContract conditionForContract) throws Exception
 {
   DAOUtils.update(context.getConnection(), context.getSqlResource().get("condition_for_contract-update-checkPrice"), conditionForContract, null);
 }

  public static void saveProduce(IActionContext context, ConditionForContract conditionForContract) throws Exception
  {
    VDbConnection conn = context.getConnection();
    DAOUtils.update(conn, context.getSqlResource().get("delete_condition_for_contract_produces"), conditionForContract, null);
    for (int i = 0; i < conditionForContract.getProduces().size() - 1; i++)
    {
      ConditionForContractProduce conditionForContractProduce = conditionForContract.getProduces().get(i);
      DAOUtils.update(conn, context.getSqlResource().get("insert_condition_for_contract_produce"), conditionForContractProduce, null);
    }
  }

  public static void saveExecute(IActionContext context, ConditionForContract conditionForContract) throws Exception
  {
    DAOUtils.update(context.getConnection(), context.getSqlResource().get("execute_condition_for_contract"), conditionForContract, null);
  }

  public static List<ConditionForContract> loadSpecificationsNumbersForContract(IActionContext context, ConditionForContract conditionForContract) throws Exception
  {
    return DAOUtils.fillList(context, "select-cfc_specifications_numbers-for-contract-id", conditionForContract, ConditionForContract.class, null, null);
  }

}
