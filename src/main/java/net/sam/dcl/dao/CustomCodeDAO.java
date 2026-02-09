package net.sam.dcl.dao;

import net.sam.dcl.beans.CustomCode;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.controller.IActionContext;

/**
 * @author: DG
 * Date: Sep 11, 2005
 * Time: 2:10:04 PM
 */
public class CustomCodeDAO
{

  public static CustomCode load(IActionContext context, String id, String date) throws Exception
  {
    CustomCode code = new CustomCode(id, date);
    if (load(context, code))
    {
      return code;
    }
    throw new LoadException(code, id);
  }

  public static boolean load(IActionContext context, CustomCode code) throws Exception
  {
    if (DAOUtils.load(context, "dao-load-custom_code", code, null))
    {
      return true;
    }
    return false;
  }

  public static boolean loadByCode(IActionContext context, CustomCode code) throws Exception
  {
    if (DAOUtils.load(context, "dao-load-custom_code-by-code", code, null))
    {
      return true;
    }
    return false;
  }

  public static boolean checkByCode(IActionContext context, CustomCode code) throws Exception
  {
    if (DAOUtils.load(context, "dao-check-custom_code-by-code", code, null))
    {
      return true;
    }
    return false;
  }

  public static boolean checkByCodeInstant(IActionContext context, CustomCode code) throws Exception
  {
    if (DAOUtils.load(context, "dao-check-custom_code-by-code_instant", code, null))
    {
      return true;
    }
    return false;
  }

  public static void saveBlock(IActionContext context, CustomCode code) throws Exception
  {
    DAOUtils.update(context.getConnection(), context.getSqlResource().get("custom_code-update-block"), code, null);
  }

  public static void saveLaw240Flag(IActionContext context, CustomCode code) throws Exception
  {
    DAOUtils.update(context.getConnection(), context.getSqlResource().get("custom_code-update-law240"), code, null);
  }
}
