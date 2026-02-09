package net.sam.dcl.dao;

import net.sam.dcl.beans.*;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.DAOUtils;

public class DeliveryRequestProduceDAO
{
  public static DeliveryRequestProduce load(IActionContext context, String id) throws Exception
  {
    DeliveryRequestProduce deliveryRequestProduce = new DeliveryRequestProduce(id);

    if (load(context, deliveryRequestProduce))
    {
      return deliveryRequestProduce;
    }
    throw new LoadException(deliveryRequestProduce, id);
  }

  public static boolean load(IActionContext context, DeliveryRequestProduce deliveryRequestProduce) throws Exception
  {
    if (DAOUtils.load(context, "delivery_request_produce-load", deliveryRequestProduce, null))
    {
      if (null != deliveryRequestProduce.getProduce().getId())
      {
        deliveryRequestProduce.setProduce(ProduceDAO.loadProduceWithUnit(deliveryRequestProduce.getProduce().getId()));
      }

      String numbers = deliveryRequestProduce.getSpecificationNumbers();
      if ( !StringUtil.isEmpty(numbers) )
      {
        deliveryRequestProduce.setSpecificationNumbers(numbers.substring(0, numbers.length() - 2));
      }
      return true;
    }
    else
    {
      return false;
    }
  }
}
