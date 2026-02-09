package net.sam.dcl.dao;

import net.sam.dcl.beans.ProduceCostProduce;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.DAOUtils;

public class ProduceCostProduceDAO
{
  public static ProduceCostProduce load(IActionContext context, String id) throws Exception
  {
    ProduceCostProduce produceCostProduce = new ProduceCostProduce(id);
    if (load(context, produceCostProduce))
    {
      return produceCostProduce;
    }
    throw new LoadException(produceCostProduce, id);
  }

  public static boolean load(IActionContext context, ProduceCostProduce produceCostProduce) throws Exception
  {
    if (DAOUtils.load(context, "produce_cost_produce-load", produceCostProduce, null))
    {
      if (!StringUtil.isEmpty(produceCostProduce.getManager().getUsr_id()))
      {
        UserDAO.load(context, produceCostProduce.getManager());
      }
      if (null != produceCostProduce.getProduce() && null != produceCostProduce.getProduce().getId())
      {
        produceCostProduce.setProduce(ProduceDAO.loadProduce(produceCostProduce.getProduce().getId()));
      }
      return true;
    }
    else
    {
      return false;
    }
  }
}
