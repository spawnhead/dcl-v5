package net.sam.dcl.dao;

import net.sam.dcl.beans.ManagerPercent;
import net.sam.dcl.beans.ShippingPosition;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.db.VDbConnection;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.util.StringUtil;

import java.util.List;

/**
 * @author: DG
 * Date: Sep 11, 2005
 * Time: 2:10:04 PM
 */
public class ShippingPositionDAO
{

  public static ShippingPosition load(IActionContext context, String id) throws Exception
  {
    ShippingPosition shippingPosition = new ShippingPosition(id);

    if (load(context, shippingPosition))
    {
      return shippingPosition;
    }
    throw new LoadException(shippingPosition, id);
  }

  public static boolean load(IActionContext context, ShippingPosition shippingPosition) throws Exception
  {
    if (DAOUtils.load(context, "shipping_position-load", shippingPosition, null))
    {
      if (!StringUtil.isEmpty(shippingPosition.getProduce().getLpc_id()))
      {
        ProduceCostProduceDAO.load(context, shippingPosition.getProduce());
      }

      loadManagers(context, shippingPosition);

      if (!StringUtil.isEmpty(shippingPosition.getStuffCategory().getId()))
      {
        StuffCategoryDAO.load(context, shippingPosition.getStuffCategory());
      }
      shippingPosition.setEnterInUseDate(StringUtil.dbDateString2appDateString(shippingPosition.getEnterInUseDate()));
      shippingPosition.calculate();
      return true;
    }
    else
    {
      return false;
    }
  }

  public static void insert(IActionContext context, ShippingPosition shippingPosition) throws Exception
  {
    DAOUtils.load(context, "shipping_position-insert", shippingPosition, null);
    shippingPosition.setListParentIds();
    shippingPosition.setListIdsToNull();
    saveManagers(context, shippingPosition);
  }

  public static void save(IActionContext context, ShippingPosition shippingPosition) throws Exception
  {
    DAOUtils.update(context.getConnection(), context.getSqlResource().get("shipping_position-update"), shippingPosition, null);
    shippingPosition.setListParentIds();
    saveManagers(context, shippingPosition);
  }

  public static void delete(IActionContext context, ShippingPosition shippingPosition) throws Exception
  {
    DAOUtils.update(context.getConnection(), context.getSqlResource().get("shipping_position-delete"), shippingPosition, null);
  }

  public static void loadManagers(IActionContext context, ShippingPosition shippingPosition) throws Exception
  {
    List<ManagerPercent> lst = DAOUtils.fillList(context, "select-managers_for_shipping_position", shippingPosition, ManagerPercent.class, null, null);
    if (lst.size() != 0)
    {
      shippingPosition.setManagers(lst);
    }
    else
    {
      shippingPosition.getManagers().get(0).setPercent(100);
    }
  }

  public static void saveManagers(IActionContext context, ShippingPosition shippingPosition) throws Exception
  {
    VDbConnection conn = context.getConnection();
    DAOUtils.update(conn, context.getSqlResource().get("delete-managers_for_shipping_position"), shippingPosition, null);
    if (shippingPosition.getManagers().size() == 1)
    {
      if (StringUtil.isEmpty(shippingPosition.getManagers().get(0).getManager().getUsr_id()))
      {
        return;
      }
    }
    for (ManagerPercent managerPercent : shippingPosition.getManagers())
    {
      DAOUtils.update(conn, context.getSqlResource().get("insert-managers_for_shipping_position"), managerPercent, null);
    }
  }
}
