package net.sam.dcl.dao;

import net.sam.dcl.beans.DeliveryRequest;
import net.sam.dcl.beans.DeliveryRequestProduce;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.util.HibernateUtil;
import net.sam.dcl.db.VDbConnection;

import java.util.List;

import org.hibernate.Session;

/**
 * @author: DG
 * Date: Sep 11, 2005
 * Time: 2:10:04 PM
 */
public class DeliveryRequestDAO
{
  public static DeliveryRequest load(IActionContext context, String id) throws Exception
  {
    DeliveryRequest deliveryRequest = new DeliveryRequest(id);

    if (load(context, deliveryRequest))
    {
      deliveryRequest.setUsr_date_create(StringUtil.dbDateString2appDateString(deliveryRequest.getUsr_date_create()));
      deliveryRequest.setUsr_date_edit(StringUtil.dbDateString2appDateString(deliveryRequest.getUsr_date_edit()));
      deliveryRequest.setUsr_date_place(StringUtil.dbDateString2appDateString(deliveryRequest.getUsr_date_place()));

      deliveryRequest.setDlr_date(StringUtil.dbDateString2appDateString(deliveryRequest.getDlr_date()));

      loadProduce(context, deliveryRequest);
      return deliveryRequest;
    }
    throw new LoadException(deliveryRequest, id);
  }

  public static boolean load(IActionContext context, DeliveryRequest deliveryRequest) throws Exception
  {
    if (DAOUtils.load(context, "delivery_request-load", deliveryRequest, null))
    {
      if (!StringUtil.isEmpty(deliveryRequest.getCreateUser().getUsr_id()))
      {
        UserDAO.load(context, deliveryRequest.getCreateUser());
      }

      if (!StringUtil.isEmpty(deliveryRequest.getEditUser().getUsr_id()))
      {
        UserDAO.load(context, deliveryRequest.getEditUser());
      }

      if (!StringUtil.isEmpty(deliveryRequest.getPlaceUser().getUsr_id()))
      {
        UserDAO.load(context, deliveryRequest.getPlaceUser());
      }

      return true;
    }
    else
    {
      return false;
    }
  }

  public static DeliveryRequest loadByDrpId(IActionContext context, String id) throws Exception
  {
    DeliveryRequest deliveryRequest = new DeliveryRequest(null, id);

    if (loadByDrpId(context, deliveryRequest))
    {
      deliveryRequest.setUsr_date_create(StringUtil.dbDateString2appDateString(deliveryRequest.getUsr_date_create()));
      deliveryRequest.setUsr_date_edit(StringUtil.dbDateString2appDateString(deliveryRequest.getUsr_date_edit()));
      deliveryRequest.setUsr_date_place(StringUtil.dbDateString2appDateString(deliveryRequest.getUsr_date_place()));

      deliveryRequest.setDlr_date(StringUtil.dbDateString2appDateString(deliveryRequest.getDlr_date()));

      return deliveryRequest;
    }
    throw new LoadException(deliveryRequest, id);
  }

  public static boolean loadByDrpId(IActionContext context, DeliveryRequest deliveryRequest) throws Exception
  {
    if (DAOUtils.load(context, "delivery_request-load_by_drp_id", deliveryRequest, null))
    {
      if (!StringUtil.isEmpty(deliveryRequest.getCreateUser().getUsr_id()))
      {
        UserDAO.load(context, deliveryRequest.getCreateUser());
      }

      if (!StringUtil.isEmpty(deliveryRequest.getEditUser().getUsr_id()))
      {
        UserDAO.load(context, deliveryRequest.getEditUser());
      }

      if (!StringUtil.isEmpty(deliveryRequest.getPlaceUser().getUsr_id()))
      {
        UserDAO.load(context, deliveryRequest.getPlaceUser());
      }

      return true;
    }
    else
    {
      return false;
    }
  }

  public static void loadProduce(IActionContext context, DeliveryRequest deliveryRequest) throws Exception
  {
    Session hibSession = HibernateUtil.getSessionFactory().getCurrentSession();
    hibSession.beginTransaction();
    List<DeliveryRequestProduce> lst = DAOUtils.fillList(context, "select-delivery_request_produces", deliveryRequest, DeliveryRequestProduce.class, null, null);
    for ( DeliveryRequestProduce deliveryRequestProduce : lst )
    {
      if (null != deliveryRequestProduce.getProduce().getId())
      {
        deliveryRequestProduce.setProduce(ProduceDAO.loadProduceWithUnitInOneSession(deliveryRequestProduce.getProduce().getId(), hibSession));
      }
      String numbers = deliveryRequestProduce.getSpecificationNumbers();
      if ( !StringUtil.isEmpty(numbers) )
      {
        deliveryRequestProduce.setSpecificationNumbers(numbers.substring(0, numbers.length() - 2));
      }
    }
    hibSession.getTransaction().commit();
    deliveryRequest.setProduces(lst);
  }

  public static void insert(IActionContext context, DeliveryRequest deliveryRequest) throws Exception
  {
    DAOUtils.load(context, "delivery_request-insert", deliveryRequest, null);
    deliveryRequest.setListParentIds();
    deliveryRequest.setListIdsToNull();
    saveProduce(context, deliveryRequest);
  }

  public static void save(IActionContext context, DeliveryRequest deliveryRequest) throws Exception
  {
    DAOUtils.update(context.getConnection(), context.getSqlResource().get("delivery_request-update"), deliveryRequest, null);
    deliveryRequest.setListParentIds();
    saveProduce(context, deliveryRequest);
  }

  public static void saveProduce(IActionContext context, DeliveryRequest deliveryRequest) throws Exception
  {
    VDbConnection conn = context.getConnection();
    DAOUtils.update(conn, context.getSqlResource().get("delete_delivery_request_produces"), deliveryRequest, null);
    for (int i = 0; i < deliveryRequest.getProduces().size(); i++)
    {
      DeliveryRequestProduce deliveryRequestProduce = deliveryRequest.getProduces().get(i);
      DeliveryRequestProduce deliveryRequestProduceFind = new DeliveryRequestProduce();
      try
      {
        if ( !StringUtil.isEmpty(deliveryRequestProduce.getDrp_id()) )
        {
          deliveryRequestProduceFind = DeliveryRequestProduceDAO.load(context, deliveryRequestProduce.getDrp_id());
        }
      }
      catch (Exception e)
      {
        //delivery request produce deleted
      }

      if ( StringUtil.isEmpty(deliveryRequestProduceFind.getDrp_id()) )
      {
        DAOUtils.update(conn, context.getSqlResource().get("insert_delivery_request_produce"), deliveryRequestProduce, null);
      }
      else
      {
        DAOUtils.update(conn, context.getSqlResource().get("update_delivery_request_produce"), deliveryRequestProduce, null);
      }
    }
  }

  public static void savePlaceRequest(IActionContext context, DeliveryRequest deliveryRequest) throws Exception
  {
    DAOUtils.update(context.getConnection(), context.getSqlResource().get("delivery_request-update-place_request"), deliveryRequest, null);
  }
}
