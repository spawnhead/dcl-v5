package net.sam.dcl.dao;

import net.sam.dcl.beans.PurchasePurpose;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.util.DAOUtils;

public class PurchasePurposeDAO
{
  public static PurchasePurpose load(IActionContext context, String id) throws Exception
  {
    PurchasePurpose purchasePurpose = new PurchasePurpose(id);
    if (load(context, purchasePurpose))
    {
      return purchasePurpose;
    }
    throw new LoadException(purchasePurpose, id);
  }

  public static boolean load(IActionContext context, PurchasePurpose purchasePurpose) throws Exception
  {
    return (DAOUtils.load(context, "dao-load-purchase_purpose", purchasePurpose, null));
  }
}