package net.sam.dcl.dao;

import net.sam.dcl.beans.OrderProduce;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.util.DAOUtils;

public class OrderProduceDAO
{
  public static OrderProduce load(IActionContext context, String id) throws Exception
  {
    OrderProduce orderProduce = new OrderProduce(id);
    if (load(context, orderProduce))
    {
      return orderProduce;
    }
    throw new LoadException(orderProduce, id);
  }

  public static boolean load(IActionContext context, OrderProduce orderProduce) throws Exception
  {
    return DAOUtils.load(context, "order_produce-load", orderProduce, null);
  }

  public static boolean loadCirculationAndRest(IActionContext context, OrderProduce orderProduce) throws Exception
  {
    return DAOUtils.load(context, "order_produce-load_circulation_and_rest", orderProduce, null);
  }
}
