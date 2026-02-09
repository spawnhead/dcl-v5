package net.sam.dcl.dao;

import net.sam.dcl.beans.*;
import net.sam.dcl.config.Config;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.db.VDbConnection;
import net.sam.dcl.db.VDbConnectionManager;
import net.sam.dcl.form.ContractorRequestForm;
import net.sam.dcl.form.CurrentWorksForm;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.util.StringUtil;

import java.util.List;
import java.util.Random;

/**
 * @author: DG
 * Date: Sep 11, 2005
 * Time: 2:10:04 PM
 */
public class ContractorRequestDAO
{

  public static ContractorRequest load(IActionContext context, String id) throws Exception
  {
    ContractorRequest contractorRequest = new ContractorRequest(id);

    if (load(context, contractorRequest))
    {
      contractorRequest.setUsr_date_create(StringUtil.dbDateTimeString2appDateTimeString(contractorRequest.getUsr_date_create()));
      contractorRequest.setUsr_date_edit(StringUtil.dbDateTimeString2appDateTimeString(contractorRequest.getUsr_date_edit()));

      contractorRequest.setCrq_receive_date(StringUtil.dbDateString2appDateString(contractorRequest.getCrq_receive_date()));
      contractorRequest.setCrq_enter_in_use_date(StringUtil.dbDateString2appDateString(contractorRequest.getCrq_enter_in_use_date()));

      loadStages(context, contractorRequest);
      loadPrints(context, contractorRequest);
      loadOrders(context, contractorRequest);

      return contractorRequest;
    }
    throw new LoadException(contractorRequest, id);
  }

  public static boolean load(IActionContext context, ContractorRequest contractorRequest) throws Exception
  {
    if (DAOUtils.load(context, "contractor_request-load", contractorRequest, null))
    {
      if (!StringUtil.isEmpty(contractorRequest.getCreateUser().getUsr_id()))
      {
        UserDAO.load(context, contractorRequest.getCreateUser());
      }

      if (!StringUtil.isEmpty(contractorRequest.getEditUser().getUsr_id()))
      {
        UserDAO.load(context, contractorRequest.getEditUser());
      }

      if (!StringUtil.isEmpty(contractorRequest.getContractor().getId()))
      {
        ContractorDAO.load(context, contractorRequest.getContractor());
      }

      if (!StringUtil.isEmpty(contractorRequest.getContactPerson().getCps_id()))
      {
        ContactPersonDAO.load(context, contractorRequest.getContactPerson());
      }

      if (!StringUtil.isEmpty(contractorRequest.getContract().getCon_id()))
      {
        contractorRequest.setContract(ContractDAO.load(context, contractorRequest.getContract().getCon_id(), false));
      }

      if (!StringUtil.isEmpty(contractorRequest.getContractForWork().getCon_id()))
      {
        contractorRequest.setContractForWork(ContractDAO.load(context, contractorRequest.getContractForWork().getCon_id(), false));
      }

      return true;
    }
    else
    {
      return false;
    }
  }

  public static void loadStages(IActionContext context, ContractorRequest contractorRequest) throws Exception
  {
    List<ContractorRequestStage> lst = DAOUtils.fillList(context, "select-contractor_request_stages", contractorRequest, ContractorRequestStage.class, null, null);
    contractorRequest.setStages(lst);
  }

  public static void loadPrints(IActionContext context, ContractorRequest contractorRequest) throws Exception
  {
    List<ContractorRequestPrint> lst = DAOUtils.fillList(context, "select-contractor_request_prints", contractorRequest, ContractorRequestPrint.class, null, null);
    contractorRequest.setContractorRequestPrints(lst);
  }

  public static void loadOrders(IActionContext context, ContractorRequest contractorRequest) throws Exception
  {
    List<Order> lst = DAOUtils.fillList(context, "select-contractor_request_orders", contractorRequest, Order.class, null, null);
    for (Order order : lst)
    {
      order.setOrd_date(StringUtil.dbDateString2appDateString(order.getOrd_date()));
    }
    contractorRequest.setOrders(lst);
  }

  public static void insert(IActionContext context, ContractorRequest contractorRequest) throws Exception
  {
    DAOUtils.load(context.getConnection(), context.getSqlResource().get("contractor_request-insert"), contractorRequest, null);
    contractorRequest.setListParentIds();
    contractorRequest.setListIdsToNull();
    //1 - Пуско-наладочные работы
    if (ContractorRequestForm.pnpId.equals(contractorRequest.getRequestType().getId()))
    {
      saveStages(context, contractorRequest);
    }

    savePrints(context, contractorRequest);
    saveOrders(context, contractorRequest);
  }

  public static void save(IActionContext context, ContractorRequest contractorRequest) throws Exception
  {
    DAOUtils.update(context.getConnection(), context.getSqlResource().get("contractor_request-update"), contractorRequest, null);
    contractorRequest.setListParentIds();
    contractorRequest.setListIdsToNull();
    //1 - Пуско-наладочные работы
    if (ContractorRequestForm.pnpId.equals(contractorRequest.getRequestType().getId())) {
      saveStages(context, contractorRequest);
    }

    savePrints(context, contractorRequest);
    saveOrders(context, contractorRequest);
  }

  public static void saveStages(IActionContext context, ContractorRequest contractorRequest) throws Exception
  {
    VDbConnection conn = context.getConnection();
    DAOUtils.update(conn, context.getSqlResource().get("delete_contractor_request_stages"), contractorRequest, null);
    for (ContractorRequestStage contractorRequestStage : contractorRequest.getStages())
    {
      DAOUtils.update(conn, context.getSqlResource().get("insert_contractor_request_stage"), contractorRequestStage, null);
    }
  }

  public static void savePrints(IActionContext context, ContractorRequest contractorRequest) throws Exception
  {
    VDbConnection conn = context.getConnection();
    DAOUtils.update(conn, context.getSqlResource().get("delete_contractor_request_prints"), contractorRequest, null);
    for (ContractorRequestPrint contractorRequestPrint : contractorRequest.getContractorRequestPrints())
    {
      DAOUtils.update(conn, context.getSqlResource().get("insert_contractor_request_print"), contractorRequestPrint, null);
    }
  }

  public static void saveOrders(IActionContext context, ContractorRequest contractorRequest) throws Exception
  {
    VDbConnection conn = context.getConnection();
    DAOUtils.update(conn, context.getSqlResource().get("delete_contractor_request_orders"), contractorRequest, null);
    for (Order order : contractorRequest.getOrders())
    {
      order.setCrq_id(contractorRequest.getCrq_id());
      DAOUtils.update(conn, context.getSqlResource().get("insert_contractor_request_order"), order, null);
    }
  }

  public static String getRandomIdForAct(IActionContext context, String blankId) throws Exception
  {
    String id = "1";
    ContractorRequest contractorRequest = new ContractorRequest();
    Blank blank1 = BlankDAO.load(context, Config.getString(Constants.commonBlankLight1));
    Blank blank2 = BlankDAO.load(context, Config.getString(Constants.commonBlankLight2));
    //0 - ЗАО "Линтера", 1 - ИП "ЛинтераТехСервис", 2 - Прочие
    if (blank1.getBln_id().equals(blankId))
    {
      contractorRequest.getSeller().setId("0");
    }
    else if (blank2.getBln_id().equals(blankId))
    {
      contractorRequest.getSeller().setId("1");
    }
    else
    {
      contractorRequest.getSeller().setId("2");
    }

    if (!DAOUtils.load(context, "contractor_request-load-count-for_act", contractorRequest, null))
    {
      return id;
    }

    if (Integer.parseInt(contractorRequest.getCrq_id()) <= 0)
    {
      throw new DAOException("Нет данных");
    }

    int random = new Random().nextInt(Integer.parseInt(contractorRequest.getCrq_id()));
    contractorRequest.setCrq_id(Integer.toString(random));
    if (!DAOUtils.load(context, "contractor_request-load-random-for_act", contractorRequest, null))
    {
      return id;
    }

    return contractorRequest.getCrq_id();
  }

  public static String getRandomIdForLetter(IActionContext context) throws Exception
  {
    String id = "1";
    ContractorRequest contractorRequest = new ContractorRequest();
    if (!DAOUtils.load(context, "contractor_request-load-count-for_letter", contractorRequest, null))
    {
      return id;
    }

    if (Integer.parseInt(contractorRequest.getCrq_id()) <= 0)
    {
      throw new DAOException("Нет данных");
    }

    int random = new Random().nextInt(Integer.parseInt(contractorRequest.getCrq_id()));
    contractorRequest.setCrq_id(Integer.toString(random));
    if (!DAOUtils.load(context, "contractor_request-load-random-for_letter", contractorRequest, null))
    {
      return id;
    }

    return contractorRequest.getCrq_id();
  }

  public static boolean loadAdditionalInformationForCurrentWork(IActionContext context, CurrentWorksForm.ContractorRequest contractorRequest) throws Exception
  {
    boolean ret = false;

    VDbConnection conn = null;
    try
    {
      conn = VDbConnectionManager.getVDbConnection();
      ret = DAOUtils.load(conn, context, "current_work-load_additional_info", contractorRequest, null);
    }
    catch (Exception e)
    {
      throw new RuntimeException(e);
    }
    finally
    {
      if (conn != null) conn.close();
    }

    return ret;
  }

}