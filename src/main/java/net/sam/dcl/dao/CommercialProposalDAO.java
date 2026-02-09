package net.sam.dcl.dao;

import net.sam.dcl.beans.*;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.db.VDbConnection;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.config.Config;
import net.sam.dcl.dbo.DboProduce;

import java.util.List;
import java.util.Random;

public class CommercialProposalDAO
{

  public static CommercialProposal load(IActionContext context, String id) throws Exception
  {
    CommercialProposal commercialProposal = new CommercialProposal(id);

    if (load(context, commercialProposal))
    {
      commercialProposal.setUsr_date_create(StringUtil.dbDateString2appDateString(commercialProposal.getUsr_date_create()));
      commercialProposal.setUsr_date_edit(StringUtil.dbDateString2appDateString(commercialProposal.getUsr_date_edit()));

      commercialProposal.setCpr_date(StringUtil.dbDateString2appDateString(commercialProposal.getCpr_date()));
      commercialProposal.setCpr_final_date(StringUtil.dbDateString2appDateString(commercialProposal.getCpr_final_date()));
      commercialProposal.setCpr_date_accept(StringUtil.dbDateString2appDateString(commercialProposal.getCpr_date_accept()));

      loadProduces(context, commercialProposal);
      loadTransport(context, commercialProposal);
      return commercialProposal;
    }
    throw new LoadException(commercialProposal, id);
  }

  public static boolean load(IActionContext context, CommercialProposal commercialProposal) throws Exception
  {
    if (DAOUtils.load(context, "commercial_proposal-load", commercialProposal, null))
    {
      if (!StringUtil.isEmpty(commercialProposal.getCreateUser().getUsr_id()))
      {
        UserDAO.load(context, commercialProposal.getCreateUser());
      }

      if (!StringUtil.isEmpty(commercialProposal.getEditUser().getUsr_id()))
      {
        UserDAO.load(context, commercialProposal.getEditUser());
      }

      if (!StringUtil.isEmpty(commercialProposal.getBlank().getBln_id()))
      {
        commercialProposal.setBlank(BlankDAO.load(context, commercialProposal.getBlank().getBln_id()));
      }

      if (!StringUtil.isEmpty(commercialProposal.getContractor().getId()))
      {
        ContractorDAO.load(context, commercialProposal.getContractor());
      }

      if (!StringUtil.isEmpty(commercialProposal.getContactPerson().getCps_id()))
      {
        ContactPersonDAO.load(context, commercialProposal.getContactPerson());
      }

      if (!StringUtil.isEmpty(commercialProposal.getCurrency().getId()))
      {
        CurrencyDAO.load(context, commercialProposal.getCurrency());
      }

      if (!StringUtil.isEmpty(commercialProposal.getCurrencyTable().getId()))
      {
        CurrencyDAO.load(context, commercialProposal.getCurrencyTable());
      }

      if (!StringUtil.isEmpty(commercialProposal.getPriceCondition().getId()))
      {
        IncoTermDAO.load(context, commercialProposal.getPriceCondition());
      }

      if (!StringUtil.isEmpty(commercialProposal.getDeliveryCondition().getId()))
      {
        IncoTermDAO.load(context, commercialProposal.getDeliveryCondition());
      }

      if (!StringUtil.isEmpty(commercialProposal.getUser().getUsr_id()))
      {
        UserDAO.load(context, commercialProposal.getUser());
      }

      if (!StringUtil.isEmpty(commercialProposal.getExecutor().getUsr_id()))
      {
        UserDAO.load(context, commercialProposal.getExecutor());
      }

      if (!StringUtil.isEmpty(commercialProposal.getPurchasePurpose().getId()))
      {
        PurchasePurposeDAO.load(context, commercialProposal.getPurchasePurpose());
      }

      if (!StringUtil.isEmpty(commercialProposal.getContactPersonSeller().getCps_id()))
      {
        ContactPersonDAO.load(context, commercialProposal.getContactPersonSeller());
      }

      if (!StringUtil.isEmpty(commercialProposal.getContactPersonCustomer().getCps_id()))
      {
        ContactPersonDAO.load(context, commercialProposal.getContactPersonCustomer());
      }

      if (!StringUtil.isEmpty(commercialProposal.getConsignee().getId()))
      {
        ContractorDAO.load(context, commercialProposal.getConsignee());
      }

      return true;
    }
    else
    {
      return false;
    }
  }

  public static void loadProduces(IActionContext context, CommercialProposal commercialProposal) throws Exception
  {
    CurrencyRate currencyRate = CurrencyRateDAO.loadRateForDate(context, CurrencyDAO.loadByName(context, "EUR").getId(), commercialProposal.getCpr_date_ts());
    List<CommercialProposalProduce> lst = DAOUtils.fillList(context, "select-commercial_proposal_produces", commercialProposal, CommercialProposalProduce.class, null, null);
    for (CommercialProposalProduce commercialProposalProduce : lst )
    {
      commercialProposalProduce.setCpr_date(commercialProposal.getCpr_date());
      if (commercialProposal.isOldVersion())
      {
        commercialProposalProduce.setProduce(null);
      }

      if (  null != commercialProposalProduce.getProduce() && null != commercialProposalProduce.getProduce().getId() )
      {
        commercialProposalProduce.setProduce(ProduceDAO.loadProduceWithUnit(commercialProposalProduce.getProduce().getId()));
        commercialProposalProduce.setLpr_produce_name("");
        commercialProposalProduce.setLpr_catalog_num("");
      }
      if ( commercialProposal.isAssembleMinskStore() ) //здесь, потому что потом сразу начинается обработка
      {
        commercialProposalProduce.setSale_price_parking_trans_custom(commercialProposalProduce.getLpr_sale_price());
        if (!StringUtil.isEmpty(commercialProposalProduce.getLpc_id()))
        {
          ProduceCostProduce produceCostProduce = ProduceCostProduceDAO.load(context, commercialProposalProduce.getLpc_id());
          ProduceCost produceCost = ProduceCostDAO.load(context, produceCostProduce.getPrc_id());
          double days = StringUtil.getDaysBetween(StringUtil.appDateString2Date(produceCost.getPrc_date()), StringUtil.appDateString2Date(commercialProposal.getCpr_date()));
          commercialProposalProduce.setCalculatedField(
                StringUtil.double2appCurrencyString(
                        produceCostProduce.getFieldForCheckCP(
                                currencyRate.getCrt_rate(),
                                Config.getFloat(Constants.minCourseCoefficient, 1.05f)
                        )
                )
                +
                "|"
                +
                (days <= 360 ? "1" : "0")
          );
        }
      }
    }
    commercialProposal.setProduces(lst);
  }

  public static void loadTransport(IActionContext context, CommercialProposal commercialProposal) throws Exception
  {
    List<CommercialProposalTransport> lst = DAOUtils.fillList(context, "select-commercial_proposal_transports", commercialProposal, CommercialProposalTransport.class, null, null);
    commercialProposal.setTransportLines(lst);
  }

  public static void insert(IActionContext context, CommercialProposal commercialProposal) throws Exception
  {
    DAOUtils.load(context, "commercial_proposal-insert", commercialProposal, null);
    commercialProposal.setListParentIds();
    commercialProposal.setListIdsToNull();
    saveProduces(context, commercialProposal);
    saveTransports(context, commercialProposal);
  }

  public static void save(IActionContext context, CommercialProposal commercialProposal) throws Exception
  {
    DAOUtils.update(context.getConnection(), context.getSqlResource().get("commercial_proposal-update"), commercialProposal, null);
    commercialProposal.setListParentIds();
    commercialProposal.setListIdsToNull();
    saveProduces(context, commercialProposal);
    saveTransports(context, commercialProposal);
  }

  public static void saveProduces(IActionContext context, CommercialProposal commercialProposal) throws Exception
  {
    VDbConnection conn = context.getConnection();
    DAOUtils.update(conn, context.getSqlResource().get("delete_commercial_proposal_produces"), commercialProposal, null);
    for (int i = 0; i < commercialProposal.getProduces().size() - commercialProposal.getCountItogRecord(); i++)
    {
      CommercialProposalProduce commercialProposalProduce = commercialProposal.getProduces().get(i);
      if (null == commercialProposalProduce.getProduce()) //создаем, чтобы корректно сохранило
      {
        commercialProposalProduce.setProduce(new DboProduce());
      }
      DAOUtils.update(conn, context.getSqlResource().get("insert_commercial_proposal_produce"), commercialProposalProduce, null);
    }
  }

  public static void saveTransports(IActionContext context, CommercialProposal commercialProposal) throws Exception
  {
    VDbConnection conn = context.getConnection();
    DAOUtils.update(conn, context.getSqlResource().get("delete_commercial_proposal_transports"), commercialProposal, null);
    for (int i = 0; i < commercialProposal.getTransportLines().size(); i++)
    {
      CommercialProposalTransport commercialProposalTransport = commercialProposal.getTransportLines().get(i);
      DAOUtils.update(conn, context.getSqlResource().get("insert_commercial_proposal_transport"), commercialProposalTransport, null);
    }
  }

  public static void saveBlock(IActionContext context, CommercialProposal commercialProposal) throws Exception
  {
    DAOUtils.update(context.getConnection(), context.getSqlResource().get("commercial_proposal-update-block"), commercialProposal, null);
  }

  public static void saveCheckPrice(IActionContext context, CommercialProposal commercialProposal) throws Exception
  {
    DAOUtils.update(context.getConnection(), context.getSqlResource().get("commercial_proposal-update-checkPrice"), commercialProposal, null);
  }

  public static void saveScale(IActionContext context, CommercialProposal commercialProposal) throws Exception
  {
    DAOUtils.update(context.getConnection(), context.getSqlResource().get("commercial_proposal-update_scale"), commercialProposal, null);
  }

  public static String getRandomId(IActionContext context, String blankId) throws Exception
  {
    String id = "1";
    CommercialProposal commercialProposal = new CommercialProposal();
    commercialProposal.getBlank().setBln_id(blankId);
    if (!DAOUtils.load(context, "commercial_proposal-load-count", commercialProposal, null))
    {
      return id;
    }
    int random = new Random().nextInt(Integer.parseInt(commercialProposal.getCpr_id()));
    commercialProposal.setCpr_id(Integer.toString(random));
    if (!DAOUtils.load(context, "commercial_proposal-load-random", commercialProposal, null))
    {
      return id;
    }

    return commercialProposal.getCpr_id();
  }
}
