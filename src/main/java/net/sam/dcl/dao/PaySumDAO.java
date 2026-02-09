package net.sam.dcl.dao;

import net.sam.dcl.beans.PaySum;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.util.DAOUtils;

/**
 * @author: DG
 * Date: 18.03.2006
 * Time: 14:53:24
 */
public class PaySumDAO
{
  public static PaySum load(IActionContext context, String id) throws Exception
  {
    PaySum produceCostProduce = new PaySum(id);
    if (load(context, produceCostProduce))
    {
      return produceCostProduce;
    }
    throw new LoadException(produceCostProduce, id);
  }

  public static boolean load(IActionContext context, PaySum produceCostProduce) throws Exception
  {
	  return DAOUtils.load(context, "pay_sum-load", produceCostProduce, null);
  }
}