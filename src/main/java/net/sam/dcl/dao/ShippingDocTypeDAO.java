package net.sam.dcl.dao;

import net.sam.dcl.beans.ShippingDocType;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.util.DAOUtils;

public class ShippingDocTypeDAO
{

  public static ShippingDocType load(IActionContext context, String id) throws Exception
  {
    ShippingDocType shippingDocType = new ShippingDocType(id);
    if (load(context, shippingDocType))
    {
      return shippingDocType;
    }
    throw new LoadException(shippingDocType, id);
  }

  public static boolean load(IActionContext context, ShippingDocType shippingDocType) throws Exception
  {
    return (DAOUtils.load(context, "dao-load-shipping_doc_type", shippingDocType, null));
  }
}
