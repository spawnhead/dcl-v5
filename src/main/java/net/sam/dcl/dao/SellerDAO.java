package net.sam.dcl.dao;

import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.beans.Seller;

public class SellerDAO
{

  public static Seller load(IActionContext context, String id) throws Exception
  {
    Seller seller = new Seller(id);
    if (load(context, seller))
    {
      return seller;
    }
    throw new LoadException(seller, id);
  }

  public static boolean load(IActionContext context, Seller seller) throws Exception
  {
    return (DAOUtils.load(context, "dao-load-seller", seller, null));
  }

}