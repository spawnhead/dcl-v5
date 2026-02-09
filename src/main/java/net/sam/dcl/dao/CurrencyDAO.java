package net.sam.dcl.dao;

import net.sam.dcl.beans.Currency;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.controller.IActionContext;

/**
 * @author: DG
 * Date: Sep 11, 2005
 * Time: 2:10:04 PM
 */
public class CurrencyDAO
{
  public static Currency load(IActionContext context, String id) throws Exception
  {
    Currency currency = new Currency(id);
    if (load(context, currency))
    {
      return currency;
    }
    throw new LoadException(currency, id);
  }

  public static Currency loadByName(IActionContext context, String name) throws Exception
  {
    Currency currency = new Currency(null, name);
    if ( !loadByName(context, currency) )
    {
      currency.setName("");
    }

    return currency;
  }

  public static boolean load(IActionContext context, Currency currency) throws Exception
  {
    return (DAOUtils.load(context, "dao-load-currency", currency, null));
  }

  public static boolean loadByName(IActionContext context, Currency currency) throws Exception
  {
    return (DAOUtils.load(context, "dao-load-currency-name", currency, null));
  }

}
