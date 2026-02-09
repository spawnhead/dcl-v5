package net.sam.dcl.dao;

import net.sam.dcl.beans.Country;
import net.sam.dcl.beans.ForUpdateDependedDocuments;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.util.DAOUtils;

public class CountryDAO
{

  public static Country load(IActionContext context, String id) throws Exception
  {
    Country country = new Country(id);
    if (load(context, country))
    {
      return country;
    }
    throw new LoadException(country, id);
  }

  public static boolean load(IActionContext context, Country country) throws Exception
  {
    return (DAOUtils.load(context, "dao-load-country", country, null));
  }

  public static void updateDependedDocs(IActionContext context, ForUpdateDependedDocuments updateDependedDocuments) throws Exception
  {
    DAOUtils.update(context.getConnection(), context.getSqlResource().get("country-update_depended_docs"), updateDependedDocuments, null);
  }
}