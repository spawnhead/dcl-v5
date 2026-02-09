package net.sam.dcl.dao;

import net.sam.dcl.beans.*;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.db.VDbConnection;
import net.sam.dcl.service.helper.Number1CHistoryHelper;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.util.HibernateUtil;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.StrutsUtil;
import org.hibernate.Session;

import java.util.*;

public class ProduceCostDAO
{

	public static ProduceCost load(IActionContext context, String id) throws Exception
	{
		ProduceCost produceCost = new ProduceCost(id);

		if (load(context, produceCost))
		{
			produceCost.setUsr_date_create(StringUtil.dbDateString2appDateString(produceCost.getUsr_date_create()));
			produceCost.setUsr_date_edit(StringUtil.dbDateString2appDateString(produceCost.getUsr_date_edit()));

			produceCost.setPrc_date(StringUtil.dbDateString2appDateString(produceCost.getPrc_date()));

			loadProduceCostProduce(context, produceCost);
			loadProduceCostCustom(context, produceCost);
			return produceCost;
		}
		throw new LoadException(produceCost, id);
	}

	public static boolean load(IActionContext context, ProduceCost produceCost) throws Exception
	{
		if (DAOUtils.load(context, "produce_cost-load", produceCost, null))
		{
			if (!StringUtil.isEmpty(produceCost.getCreateUser().getUsr_id()))
			{
				UserDAO.load(context, produceCost.getCreateUser());
			}

			if (!StringUtil.isEmpty(produceCost.getEditUser().getUsr_id()))
			{
				UserDAO.load(context, produceCost.getEditUser());
			}

			if (!StringUtil.isEmpty(produceCost.getRoute().getId()))
			{
				RouteDAO.load(context, produceCost.getRoute());
			}

			return true;
		}
		else
		{
			return false;
		}
	}

	public static void loadProduceCostProduce(IActionContext context, ProduceCost produceCost) throws Exception
	{
		Session hibSession = HibernateUtil.getSessionFactory().getCurrentSession();
		hibSession.beginTransaction();
		List<ProduceCostProduce> lst = DAOUtils.fillList(context, "select-produce_cost_produces", produceCost, ProduceCostProduce.class, null, null);
		for (ProduceCostProduce produceCostProduce : lst)
		{
			if (null != produceCostProduce.getProduce().getId())
			{
				produceCostProduce.setProduce(ProduceDAO.loadProduceWithUnitInOneSession(produceCostProduce.getProduce().getId(), hibSession));
			}
		}
		hibSession.getTransaction().commit();
		produceCost.setProducesCost(lst);
	}

	public static void loadProduceCostCustom(IActionContext context, ProduceCost produceCost) throws Exception
	{
		List<ProduceCostCustom> lst = DAOUtils.fillList(context, "select-produce_cost_custom", produceCost, ProduceCostCustom.class, null, null);
		produceCost.setCustomCodes(lst);
	}

	public static void insert(IActionContext context, ProduceCost produceCost) throws Exception
	{
		try
		{
			DAOUtils.load(context, "produce_cost-insert", produceCost, null);
			produceCost.setListParentIds();
			produceCost.setListIdsToNull();
			saveProduceCostProduce(context, produceCost);
			saveProduceCostCustom(context, produceCost);
		}
		catch (Exception e)
		{
			produceCost.setPrc_id(null);
			produceCost.setListParentIds();
			throw e;
		}
	}

	public static void save(IActionContext context, ProduceCost produceCost) throws Exception
	{
		DAOUtils.update(context.getConnection(), context.getSqlResource().get("produce_cost-update"), produceCost, null);
		produceCost.setListParentIds();
		saveProduceCostProduce(context, produceCost);
		saveProduceCostCustom(context, produceCost);
	}

	public static void saveBlock(IActionContext context, ProduceCost produceCost) throws Exception
	{
		DAOUtils.update(context.getConnection(), context.getSqlResource().get("produce_cost-update-block"), produceCost, null);
	}

	public static void saveProduceCostProduce(IActionContext context, ProduceCost produceCost) throws Exception
	{
		Map<String, String> ordIds = new HashMap<String, String>();
		Set<OrderMessage> orderMessages = new HashSet<OrderMessage>();
		if (produceCost.getRoute().getId().equals(Constants.routeRBClient)) //"Маршрут" = "РБ-Клиент"
		{
			for (int i = 0; i < produceCost.getProducesCost().size() - produceCost.getCountItogRecord(); i++)
			{
				ProduceCostProduce produceCostProduce = produceCost.getProducesCost().get(i);
				if (StringUtil.isEmpty(produceCostProduce.getLpc_id())) //новая строка - пытаемся получить заказ, из которого она пришла.
				{
					String ordId = OrderDAO.getOrderIdByProduceCostProduce(context, produceCostProduce);
					if (!StringUtil.isEmpty(ordId))
					{
						ordIds.put(ordId, ordId);
					}
				}
			}
		}

		for (String ordId : ordIds.keySet())
		{
			OrderMessage orderMessage = new OrderMessage();
			orderMessage.setOrd_id(ordId);
			orderMessage.setPrc_id(produceCost.getPrc_id());
			Order order = OrderDAO.loadWithoutProduces(context, ordId);
			String msg = StrutsUtil.getMessage(context, "OrderMessage.produceCostReceived", produceCost.getPrc_date(), order.getOrd_number(), order.getContractor().getName());
			orderMessage.setMsg(msg);
			orderMessages.add(orderMessage);
		}

		for (OrderMessage orderMessage : orderMessages)
		{
			MessageDAO.saveOrderMessages(context, orderMessage);
		}

		VDbConnection conn = context.getConnection();
		DAOUtils.update(conn, context.getSqlResource().get("delete_produce_cost_produce"), produceCost, null);
		for (int i = 0; i < produceCost.getProducesCost().size() - produceCost.getCountItogRecord(); i++)
		{
			ProduceCostProduce produceCostProduce = produceCost.getProducesCost().get(i);
			ProduceCostProduce produceCostProduceFind = new ProduceCostProduce();
			try
			{
				if (!StringUtil.isEmpty(produceCostProduce.getLpc_id()))
				{
					produceCostProduceFind = ProduceCostProduceDAO.load(context, produceCostProduce.getLpc_id());
				}
			}
			catch (Exception e)
			{
				//produce for produce cost deleted
			}

			if (StringUtil.isEmpty(produceCostProduceFind.getLpc_id()))
			{
				DAOUtils.update(conn, context.getSqlResource().get("insert_produce_cost_produce"), produceCostProduce, null);
			}
			else
			{
				DAOUtils.update(conn, context.getSqlResource().get("update_produce_cost_produce"), produceCostProduce, null);
			}
			if ((produceCostProduce.getLpc_1c_number().length() != 0) && (!Number1CHistoryHelper.isNumber1CExist(context, produceCostProduce)))
			{
				produceCostProduce.setPrc_date(produceCost.getPrc_date());
				try
				{
					Number1CHistoryHelper.insert(context, String.valueOf(produceCostProduce.getProduce().getId()), produceCostProduce.getLpc_1c_number(), produceCostProduce.getPrc_date());
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	public static void saveProduceCostCustom(IActionContext context, ProduceCost produceCost) throws Exception
	{
		VDbConnection conn = context.getConnection();
		DAOUtils.update(conn, context.getSqlResource().get("delete_produce_cost_custom"), produceCost, null);
		for (int i = 0; i < produceCost.getCustomCodes().size(); i++)
		{
			ProduceCostCustom pcc = produceCost.getCustomCodes().get(i);
			DAOUtils.update(conn, context.getSqlResource().get("insert_produce_cost_custom"), pcc, null);
		}
	}
}
