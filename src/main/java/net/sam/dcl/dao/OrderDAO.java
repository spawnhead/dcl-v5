package net.sam.dcl.dao;

import net.sam.dcl.beans.*;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.db.VDbConnection;
import net.sam.dcl.db.VDbConnectionManager;
import net.sam.dcl.form.OrdersForm;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.StoreUtil;
import net.sam.dcl.util.StrutsUtil;

import java.util.List;
import java.util.Random;

public class OrderDAO
{
	private static void changeDates(Order order)
	{
		order.setUsr_date_create(StringUtil.dbDateString2appDateString(order.getUsr_date_create()));
		order.setUsr_date_edit(StringUtil.dbDateString2appDateString(order.getUsr_date_edit()));

		order.setOrd_date(StringUtil.dbDateString2appDateString(order.getOrd_date()));

		order.setOrd_sent_to_prod_date(StringUtil.dbDateString2appDateString(order.getOrd_sent_to_prod_date()));
		order.setOrd_received_conf_date(StringUtil.dbDateString2appDateString(order.getOrd_received_conf_date()));
		order.setOrd_date_conf(StringUtil.dbDateString2appDateString(order.getOrd_date_conf()));
		order.setOrd_conf_sent_date(StringUtil.dbDateString2appDateString(order.getOrd_conf_sent_date()));
		order.setOrd_ready_for_deliv_date(StringUtil.dbDateString2appDateString(order.getOrd_ready_for_deliv_date()));
		order.setOrd_executed_date(StringUtil.dbDateString2appDateString(order.getOrd_executed_date()));
		order.setOrd_ship_from_stock(StringUtil.dbDateString2appDateString(order.getOrd_ship_from_stock()));
		order.setOrd_arrive_in_lithuania(StringUtil.dbDateString2appDateString(order.getOrd_arrive_in_lithuania()));
	}

	//Для клонирования не нужно грузить информацию для отдела логистики (немного ускорим загрузку)
	//Для клонирования не должны копироваться позиции, которые заблокированы в справочнике ""Номенклатура"
	//Для клонирования не должны копироваться оплаты по заказу и фактические
	public static Order load(IActionContext context, String id, boolean withoutDecInfo, boolean forClone) throws Exception
	{
		Order order = new Order(id);

		if (load(context, order))
		{
			changeDates(order);

			loadProduce(context, order, withoutDecInfo, forClone);
			if (!forClone)
			{
				loadPayments(context, order);
				loadPaySums(context, order);
				loadExecuted(context, order);
			}

			for (OrderExecutedAndDate orderExecutedAndDate : order.getOrderExecutedByDates())
			{
				orderExecutedAndDate.setOpr_date_executed(StringUtil.dbDateString2appDateString(orderExecutedAndDate.getOpr_date_executed()));
			}
			if (!StringUtil.isEmpty(order.getOrd_id())) //берем из базы
			{
				order.calculateOrderExecutedAndDateLines(context, order.getOrderExecutedByDates());
			}
			else
			{
				order.calculateOrderExecutedAndDateLines(); //формируем на основе списка продуктов
			}
			order.checkAndFillLastColumn();

			return order;
		}
		throw new LoadException(order, id);
	}

	public static Order loadWithoutProduces(IActionContext context, String id) throws Exception
	{
		Order order = new Order(id);

		if (load(context, order))
		{
			changeDates(order);

			return order;
		}
		throw new LoadException(order, id);
	}

	public static boolean load(IActionContext context, Order order) throws Exception
	{
		if (DAOUtils.load(context, "order-load", order, null))
		{
			if (!StringUtil.isEmpty(order.getCreateUser().getUsr_id()))
			{
				UserDAO.load(context, order.getCreateUser());
			}

			if (!StringUtil.isEmpty(order.getEditUser().getUsr_id()))
			{
				UserDAO.load(context, order.getEditUser());
			}

			if (!StringUtil.isEmpty(order.getBlank().getBln_id()))
			{
				order.setBlank(BlankDAO.load(context, order.getBlank().getBln_id()));
			}

			if (!StringUtil.isEmpty(order.getContractor().getId()))
			{
				ContractorDAO.load(context, order.getContractor());
			}

			if (!StringUtil.isEmpty(order.getContact_person().getCps_id()))
			{
				ContactPersonDAO.load(context, order.getContact_person());
			}

			if (!StringUtil.isEmpty(order.getDeliveryCondition().getId()))
			{
				IncoTermDAO.load(context, order.getDeliveryCondition());
			}

			if (!StringUtil.isEmpty(order.getDirector().getUsr_id()))
			{
				UserDAO.load(context, order.getDirector());
			}

			if (!StringUtil.isEmpty(order.getLogist().getUsr_id()))
			{
				UserDAO.load(context, order.getLogist());
			}

			if (!StringUtil.isEmpty(order.getDirector_rb().getUsr_id()))
			{
				UserDAO.load(context, order.getDirector_rb());
			}

			if (!StringUtil.isEmpty(order.getChief_dep().getUsr_id()))
			{
				UserDAO.load(context, order.getChief_dep());
			}

			if (!StringUtil.isEmpty(order.getManager().getUsr_id()))
			{
				UserDAO.load(context, order.getManager());
			}

			if (!StringUtil.isEmpty(order.getContractor_for().getId()))
			{
				ContractorDAO.load(context, order.getContractor_for());
			}

			if (!StringUtil.isEmpty(order.getSpecification().getSpc_id()))
			{
				SpecificationDAO.load(context, order.getSpecification());
			}

			if (!StringUtil.isEmpty(order.getStuffCategory().getId()))
			{
				StuffCategoryDAO.load(context, order.getStuffCategory());
			}

			if (!StringUtil.isEmpty(order.getCurrency().getId()))
			{
				CurrencyDAO.load(context, order.getCurrency());
			}

			if (!StringUtil.isEmpty(order.getShippingDocType().getId()))
			{
				ShippingDocTypeDAO.load(context, order.getShippingDocType());
			}

			if (!StringUtil.isEmpty(order.getSellerForWho().getId()))
			{
				SellerDAO.load(context, order.getSellerForWho());
			}

			if (!StringUtil.isEmpty(order.getSeller().getId()))
			{
				SellerDAO.load(context, order.getSeller());
			}

			return true;
		}
		else
		{
			return false;
		}
	}

	public static void loadAdditionalInfo(IActionContext context, OrdersForm.Order order) throws Exception
	{
		VDbConnection conn = null;
		try
		{
			conn = VDbConnectionManager.getVDbConnection();
			loadAssemblies(conn, context, order);
			loadSpecificationsImport(conn, context, order);
			loadCostProduces(conn, context, order);
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
		finally
		{
			if (conn != null) conn.close();
		}
	}

	public static void loadPaymentInfo(IActionContext context, Order order) throws Exception
	{
		List<OrderPayment> orderPayments = DAOUtils.resultSet2List(DAOUtils.executeQuery(context.getConnection(), context.getSqlResource().get("select-order_payments"), order, null), OrderPayment.class, null);
		order.setOrderPayments(orderPayments);
		List<OrderPaySum> orderPaySums = DAOUtils.resultSet2List(DAOUtils.executeQuery(context.getConnection(), context.getSqlResource().get("select-order_pay_sums"), order, null), OrderPaySum.class, null);
		order.setOrderPaySums(orderPaySums);
	}

	public static boolean loadAssemblies(VDbConnection conn, IActionContext context, OrdersForm.Order order) throws Exception
	{
		return (DAOUtils.load(conn, context, "order-load-assemblies", order, null));
	}

	public static boolean loadSpecificationsImport(VDbConnection conn, IActionContext context, OrdersForm.Order order) throws Exception
	{
		return (DAOUtils.load(conn, context, "order-load-specifications_import", order, null));
	}

	public static boolean loadCostProduces(VDbConnection conn, IActionContext context, OrdersForm.Order order) throws Exception
	{
		return (DAOUtils.load(conn, context, "order-load-cost_produces", order, null));
	}

	public static boolean loadContract(IActionContext context, Order order) throws Exception
	{
		return (DAOUtils.load(context, "order-load-contract", order, null));
	}

	public static Order loadByOprId(IActionContext context, String id) throws Exception
	{
		Order order = new Order(null, id);

		if (loadByOprId(context, order))
		{
			order.setUsr_date_create(StringUtil.dbDateString2appDateString(order.getUsr_date_create()));
			order.setUsr_date_edit(StringUtil.dbDateString2appDateString(order.getUsr_date_edit()));

			order.setOrd_date(StringUtil.dbDateString2appDateString(order.getOrd_date()));

			return order;
		}
		throw new LoadException(order, id);
	}

	public static boolean loadByOprId(IActionContext context, Order order) throws Exception
	{
		if (DAOUtils.load(context, "order-load_by_opr_id", order, null))
		{
			if (!StringUtil.isEmpty(order.getCreateUser().getUsr_id()))
			{
				UserDAO.load(context, order.getCreateUser());
			}

			if (!StringUtil.isEmpty(order.getEditUser().getUsr_id()))
			{
				UserDAO.load(context, order.getEditUser());
			}

			if (!StringUtil.isEmpty(order.getContractor().getId()))
			{
				ContractorDAO.load(context, order.getContractor());
			}

			if (!StringUtil.isEmpty(order.getContractor_for().getId()))
			{
				ContractorDAO.load(context, order.getContractor_for());
			}

			if (!StringUtil.isEmpty(order.getStuffCategory().getId()))
			{
				StuffCategoryDAO.load(context, order.getStuffCategory());
			}

			if (!StringUtil.isEmpty(order.getSpecification().getSpc_id()))
			{
				SpecificationDAO.load(context, order.getSpecification());
			}

			return true;
		}
		else
		{
			return false;
		}
	}

	public static String getOrderIdByProduceCostProduce(IActionContext context, ProduceCostProduce produceCostProduce) throws Exception
	{
		DAOUtils.load(context, "order-load_id_by_produce_cost_produce", produceCostProduce, null);
		return produceCostProduce.getOrd_id();
	}

	public static void loadProduce(IActionContext context, Order order, boolean withoutDecInfo, boolean forClone) throws Exception
	{
		List<OrderProduce> lst;
		if (forClone)
		{
			lst = DAOUtils.fillList(context, "select-order_produces_for_clone", order, OrderProduce.class, null, null);
		}
		else
		{
			lst = DAOUtils.fillList(context, "select-order_produces", order, OrderProduce.class, null, null);
		}
		for (OrderProduce orderProduce : lst)
		{
			if (null != orderProduce.getProduce().getId())
			{
				orderProduce.setProduce(ProduceDAO.loadProduce(orderProduce.getProduce().getId()));
			}
			if (!StringUtil.isEmpty(orderProduce.getSpecificationNumbers()))
			{
				orderProduce.setSpecificationNumbers(orderProduce.getSpecificationNumbers().substring(0, orderProduce.getSpecificationNumbers().length() - 2));
			}

			if (!withoutDecInfo)
			{
				List<ProductionTerm> lstProductionTerm = DAOUtils.fillList(context, "select-production_terms_for_opr_id", orderProduce, ProductionTerm.class, null, null);
				orderProduce.setProductTerms(lstProductionTerm);

				List<ReadyForShipping> lstReadyForShipping = DAOUtils.fillList(context, "select-ready_for_shipping_for_opr_id", orderProduce, ReadyForShipping.class, null, null);
				orderProduce.setReadyForShippings(lstReadyForShipping);
			}
		}
		order.setProduces(lst);
	}

	public static void loadPayments(IActionContext context, Order order) throws Exception
	{
		List<OrderPayment> orderPayments = DAOUtils.fillList(context, "select-order_payments", order, OrderPayment.class, null, null);
		order.setOrderPayments(orderPayments);
	}

	public static void loadPaySums(IActionContext context, Order order) throws Exception
	{
		List<OrderPaySum> orderPaySums = DAOUtils.fillList(context, "select-order_pay_sums", order, OrderPaySum.class, null, null);
		order.setOrderPaySums(orderPaySums);
	}

	public static void loadExecuted(IActionContext context, Order order) throws Exception
	{
		List<OrderExecutedAndDate> orderExecutedByDates = DAOUtils.fillList(context, "select-order_produces_executed", order, OrderExecutedAndDate.class, null, null);
		order.setOrderExecutedByDates(orderExecutedByDates);
	}

	public static void insert(IActionContext context, Order order) throws Exception
	{
		VDbConnection conn = context.getConnection();
		DAOUtils.load(conn, context, "order-insert", order, null);
		order.setListParentIds();
		order.setListIdsToNull();
		saveProduce(conn, context, order);
		savePayments(conn, context, order);
		savePaySums(conn, context, order);
	}

	public static void save(IActionContext context, Order order, boolean saveOnlyParentDoc) throws Exception
	{
		VDbConnection conn = context.getConnection();
		DAOUtils.update(conn, context.getSqlResource().get("order-update"), order, null);
		if (!saveOnlyParentDoc)
		{
			order.setListParentIds();
			saveProduce(conn, context, order);
			savePayments(conn, context, order);
			savePaySums(conn, context, order);
		}
	}

	public static void saveProduce(VDbConnection conn, IActionContext context, Order order) throws Exception
	{
		List<OrderProduce> lst = DAOUtils.fillList(context, "select-order_produces", order, OrderProduce.class, null, null);
		for (OrderProduce oldOrderProduce : lst)
		{
			boolean foundOldProduce = false;
			for (int i = 0; i < order.getProduces().size() - order.getCountItog(); i++)
			{
				OrderProduce orderProduce = order.getProduces().get(i);
				if (oldOrderProduce.getOpr_id().equals(orderProduce.getOpr_id()))
				{
					foundOldProduce = true;
				}
			}

			if (!foundOldProduce)
			{
				DAOUtils.update(conn, context.getSqlResource().get("delete_order_produce"), oldOrderProduce, null);
			}
		}

		String parentId = "";
		for (int i = 0; i < order.getProduces().size() - order.getCountItog(); i++)
		{
			OrderProduce orderProduce = order.getProduces().get(i);

			//удаляем все исполненные по продукту, но только если вообще входили в их редактирование (иначе удалятся и не добавятся).
			if (order.getOrderExecutedDate().size() > 0 && !StringUtil.isEmpty(orderProduce.getOpr_id()))
			{
				DAOUtils.update(conn, context.getSqlResource().get("delete_order_produces_executed"), orderProduce, null);
			}

			if (i + 1 < order.getProduces().size() - order.getCountItog())
			{
				OrderProduce orderProduceNext = order.getProduces().get(i + 1);
				if (!StringUtil.isEmpty(orderProduceNext.getOpr_use_prev_number()) && StringUtil.isEmpty(orderProduce.getOpr_use_prev_number()))
				{
					//признак что есть зависимые позиции
					orderProduce.setOpr_have_depend("1");
				}
			}

			OrderProduce orderProduceFind = null;
			if (!StringUtil.isEmpty(orderProduce.getOpr_id()))
			{
				for (OrderProduce oldOrderProduce : lst)
				{
					if (oldOrderProduce.getOpr_id().equals(orderProduce.getOpr_id()))
					{
						orderProduceFind = oldOrderProduce;
					}
				}
			}

			if (!StringUtil.isEmpty(orderProduce.getOpr_use_prev_number()))
			{
				orderProduce.setOpr_parent_id(parentId);
				orderProduce.setOpr_have_depend("");
			}
			else
			{
				orderProduce.setOpr_parent_id("");
			}
			if (orderProduceFind == null)
			{
				DAOUtils.load(conn, context.getSqlResource().get("insert_order_produce"), orderProduce, null);
			}
			else
			{
				DAOUtils.update(conn, context.getSqlResource().get("update_order_produce"), orderProduce, null);
			}
			if (StringUtil.isEmpty(orderProduce.getOpr_use_prev_number()))
			{
				parentId = orderProduce.getOpr_id();
			}
			saveProductionTermAndReadyForShipping(conn, context, order, orderProduce);
			saveOrderProducesExecuted(conn, context, order, orderProduce);
		}

		OrderExecutedDateLines orderExecutedDateLines = (OrderExecutedDateLines) StoreUtil.getSession(context.getRequest(), OrderExecutedDateLines.class);

		int j = 0;
		for (OrderExecutedDateLine dateLine : order.getOrderExecutedDate())
		{
			double sumForDate = 0;
			for (OrderExecutedLine line : order.getOrderExecuted())
			{
				sumForDate += StringUtil.appCurrencyString2double(line.getOrderExecutedByDate().get(j));
			}
			dateLine.setCountInDate(sumForDate);
			j++;
		}

		for (int i = 0; i < order.getOrderExecutedDate().size() - 1; i++)
		{
			String date = order.getOrderExecutedDate().get(i).getDate();
			double countInDate = order.getOrderExecutedDate().get(i).getCountInDate();
			boolean foundDate = false;
			for (OrderExecutedDateLine orderExecutedDateLine : orderExecutedDateLines.getOrderExecutedDate())
			{
				// >= т.к. шлем сообщение, только если количество за дату увеличилось (но не уменьшилось).
				if (orderExecutedDateLine.getDate().equals(date) && orderExecutedDateLine.getCountInDate() >= countInDate)
				{
					foundDate = true;
					break;
				}
			}

			if (!foundDate) //новая партия
			{
				OrderMessage orderMessage = new OrderMessage();
				orderMessage.setOrd_id(order.getOrd_id());
				String msg = "";
				if (order.isExecuted()) //если "Заказ исполнен (ВЕСЬ товар поступил на склад в Литве)" = ДА
				{
					if (order.getOrderExecutedDate().size() - 1 > 1) //количество партий прихода > 1
					{
						msg = StrutsUtil.getMessage(context, "OrderMessage.executedAndGreaterOne", date, order.getOrd_number(), order.getContractor().getName());
					}
					else //количество партий прихода = 1
					{
						msg = StrutsUtil.getMessage(context, "OrderMessage.executedAndOne", date, order.getOrd_number(), order.getContractor().getName());
					}
				}
				else //если "Заказ исполнен (ВЕСЬ товар поступил на склад в Литве)"=НЕТ
				{
					msg = StrutsUtil.getMessage(context, "OrderMessage.notExecuted", date, order.getOrd_number(), order.getContractor().getName());
				}
				if (order.isAllIncludeInSpec()) //Если у заказа отмечен чекбокс "Когда заказ будет исполнен, весь товар сразу включить в спецификацию (импорт)"
				{
					msg += " " + StrutsUtil.getMessage(context, "OrderMessage.includeToImport");
				}
				orderMessage.setMsg(msg);
				MessageDAO.saveOrderMessages(context, orderMessage);
			}
		}
	}

	public static void savePayments(VDbConnection conn, IActionContext context, Order order) throws Exception
	{
		DAOUtils.update(conn, context.getSqlResource().get("delete_order_payments"), order, null);
		for (OrderPayment orderPayment : order.getOrderPayments())
		{
			if (orderPayment.getOrp_percent() != 0 || orderPayment.getOrp_sum() != 0 || !StringUtil.isEmpty(orderPayment.getOrp_date()))
			{
				DAOUtils.update(conn, context.getSqlResource().get("insert_order_payment"), orderPayment, null);
			}
		}
	}

	public static void savePaySums(VDbConnection conn, IActionContext context, Order order) throws Exception
	{
		DAOUtils.update(conn, context.getSqlResource().get("delete_order_pay_sums"), order, null);
		for (OrderPaySum orderPaySum : order.getOrderPaySums())
		{
			DAOUtils.update(conn, context.getSqlResource().get("insert_order_pay_sums"), orderPaySum, null);
		}
	}

	public static void saveProductionTermAndReadyForShipping(VDbConnection conn, IActionContext context, Order order, OrderProduce orderProduce) throws Exception
	{
		OrderProduce.loadGridData(orderProduce, order);
		DAOUtils.update(conn, context.getSqlResource().get("delete_production_term"), orderProduce, null);
		DAOUtils.update(conn, context.getSqlResource().get("delete_ready_for_shipping"), orderProduce, null);
		for (int i = 0; i < orderProduce.getProductTerms().size(); i++)
		{
			ProductionTerm productionTerm = orderProduce.getProductTerms().get(i);
			productionTerm.setOpr_id(orderProduce.getOpr_id());
			DAOUtils.update(conn, context.getSqlResource().get("insert_production_term"), productionTerm, null);
		}
		for (int i = 0; i < orderProduce.getReadyForShippings().size(); i++)
		{
			ReadyForShipping readyForShipping = orderProduce.getReadyForShippings().get(i);
			readyForShipping.setOpr_id(orderProduce.getOpr_id());
			DAOUtils.update(conn, context.getSqlResource().get("insert_ready_for_shipping"), readyForShipping, null);
		}
	}

	public static void saveOrderProducesExecuted(VDbConnection conn, IActionContext context, Order order, OrderProduce orderProduce) throws Exception
	{
		for (OrderExecutedLine orderExecutedLine : order.getOrderExecuted())
		{
			if (orderProduce.getNumber().equals(orderExecutedLine.getNumber()))
			{
				int i = 0;
				for (String executedOnDate : orderExecutedLine.getOrderExecutedByDate())
				{
					double executed = StringUtil.appCurrencyString2double(executedOnDate);
					if (executed != 0) //может быть меньше нуля
					{
						OrderExecutedAndDate orderExecutedAndDate = new OrderExecutedAndDate();
						orderExecutedAndDate.setOpr_id(orderProduce.getOpr_id());
						orderExecutedAndDate.setOpr_count_executed(executed);
						orderExecutedAndDate.setOpr_date_executed(StringUtil.appDateString2dbDateString(order.getOrderExecutedDate().get(i).getDate()));

						DAOUtils.update(conn, context.getSqlResource().get("insert_order_produces_executed"), orderExecutedAndDate, null);
					}
					i++;
				}
			}
		}
	}

	public static void saveBlock(IActionContext context, Order order) throws Exception
	{
		DAOUtils.update(context.getConnection(), context.getSqlResource().get("order-update-block"), order, null);
		if (!StringUtil.isEmpty(order.getOrd_block())) //если блокируем, то нужно выставлять не исполненные в фиктивно исполненные.
		{
			DAOUtils.update(context.getConnection(), context.getSqlResource().get("process-order_produces_unexecuted"), order, null);
		}
	}

	public static void saveScale(IActionContext context, Order order) throws Exception
	{
		DAOUtils.update(context.getConnection(), context.getSqlResource().get("order-update_scale"), order, null);
	}

	public static boolean loadWarn(IActionContext context, Order order) throws Exception
	{
		return (DAOUtils.load(context, "order-load-warn", order, null));
	}

	public static String getRandomId(IActionContext context, String blankId) throws Exception
	{
		String id = "1";
		Order order = new Order();
		order.getBlank().setBln_id(blankId);
		if (!DAOUtils.load(context, "order-load-count", order, null))
		{
			return id;
		}
		int random = new Random().nextInt(new Integer(order.getOrd_id()));
		order.setOrd_id(Integer.toString(random));
		if (!DAOUtils.load(context, "order-load-random", order, null))
		{
			return id;
		}

		return order.getOrd_id();
	}
}