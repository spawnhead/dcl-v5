package net.sam.dcl.dao;

import net.sam.dcl.beans.*;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.db.VResultSet;

import java.util.List;

/**
 * @author: DG
 * Date: Sep 11, 2005
 * Time: 2:10:04 PM
 */
public class ShippingDAO
{

  public static Shipping load(IActionContext context, String id) throws Exception
  {

    Shipping shipping = new Shipping(id);

    if (load(context, shipping))
    {
      shipping.setShp_create_date(StringUtil.dbDateString2appDateString(shipping.getShp_create_date()));
      shipping.setShp_edit_date(StringUtil.dbDateString2appDateString(shipping.getShp_edit_date()));

      shipping.setShp_letter1_date(StringUtil.dbDateString2appDateString(shipping.getShp_letter1_date()));
      shipping.setShp_letter2_date(StringUtil.dbDateString2appDateString(shipping.getShp_letter2_date()));
      shipping.setShp_letter3_date(StringUtil.dbDateString2appDateString(shipping.getShp_letter3_date()));
      shipping.setShp_complaint_in_court_date(StringUtil.dbDateString2appDateString(shipping.getShp_complaint_in_court_date()));
      shipping.setShp_notice_date(StringUtil.dbDateString2appDateString(shipping.getShp_notice_date()));

      loadShippingPositions(context, shipping);

      return shipping;
    }
    throw new LoadException(shipping, id);
  }

  public static boolean load(IActionContext context, Shipping shipping) throws Exception
  {
    if (DAOUtils.load(context, "shipping-load", shipping, null))
    {
      if (!StringUtil.isEmpty(shipping.getCreateUser().getUsr_id()))
      {
        UserDAO.load(context, shipping.getCreateUser());
      }

      if (!StringUtil.isEmpty(shipping.getEditUser().getUsr_id()))
      {
        UserDAO.load(context, shipping.getEditUser());
      }

      if (!StringUtil.isEmpty(shipping.getContractor().getId()))
      {
        ContractorDAO.load(context, shipping.getContractor());
      }

      if (!StringUtil.isEmpty(shipping.getCurrency().getId()))
      {
        CurrencyDAO.load(context, shipping.getCurrency());
      }

      if (!StringUtil.isEmpty(shipping.getSpecification().getSpc_id()))
      {
        SpecificationDAO.load(context, shipping.getSpecification());
      }

      if (!StringUtil.isEmpty(shipping.getContractorWhere().getId()))
      {
        ContractorDAO.load(context, shipping.getContractorWhere());
      }

      if (!StringUtil.isEmpty(shipping.getContractWhere().getCon_id()))
      {
        ContractDAO.load(context, shipping.getContractWhere());
      }

      return true;
    }
    else
    {
      return false;
    }
  }

  public static void loadShippingPositions(IActionContext context, Shipping shipping) throws Exception
  {
    List<ShippingPosition> lst = DAOUtils.fillList(context, "select-shipping-positions", shipping, ShippingPosition.class, null, null);
    for ( ShippingPosition shippingPosition : lst )
    {
      ShippingPositionDAO.load(context, shippingPosition);
    }
    shipping.setShippingPositions(lst);
  }

  public static void loadShippingBySpcId(IActionContext context, ClosedRecord closedRecord) throws Exception
  {
    List<Shipping> lst = DAOUtils.fillList(context, "select-shipping_by_spc_id", closedRecord, Shipping.class, null, null);
    for (Shipping shipping : lst)
    {
      VResultSet resultSet = DAOUtils.executeQuery(context, "select-shipping_managers", shipping, null);
      List<String> managers = DAOUtils.resultSet2StringList(resultSet);
      shipping.setManagers(managers);
      
      resultSet = DAOUtils.executeQuery(context, "select-shipping_products", shipping, null);
      List<String> products = DAOUtils.resultSet2StringList(resultSet);
      shipping.setProducts(products);
    }
    closedRecord.setShippings(lst);
  }

  public static void loadPositionsInContractorRequest(IActionContext context, Shipping shipping) throws Exception
  {
    DAOUtils.load(context, "shipping_position_occupied-load", shipping, null);
  }

  public static void insert(IActionContext context, Shipping shipping) throws Exception
  {
    DAOUtils.load(context.getConnection(), context.getSqlResource().get("shipping-insert"), shipping, null);
  }

  public static void save(IActionContext context, Shipping shipping) throws Exception
  {
    DAOUtils.update(context.getConnection(), context.getSqlResource().get("shipping-update"), shipping, null);
  }

  public static void saveBlock(IActionContext context, Shipping shipping) throws Exception
  {
    DAOUtils.update(context.getConnection(), context.getSqlResource().get("shipping-update-block"), shipping, null);
  }

  public static void setBlock(IActionContext context, Shipping shipping) throws Exception
  {
    DAOUtils.update(context.getConnection(), context.getSqlResource().get("shipping-set-block"), shipping, null);
  }
}
