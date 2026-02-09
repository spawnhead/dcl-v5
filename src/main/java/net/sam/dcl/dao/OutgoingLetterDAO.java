package net.sam.dcl.dao;

import net.sam.dcl.beans.OutgoingLetter;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.controller.IActionContext;

public class OutgoingLetterDAO
{

  public static OutgoingLetter load(IActionContext context, String id) throws Exception
  {
    OutgoingLetter outgoingLetter = new OutgoingLetter(id);

    if (load(context, outgoingLetter))
    {
      outgoingLetter.setUsr_date_create(StringUtil.dbDateString2appDateString(outgoingLetter.getUsr_date_create()));
      outgoingLetter.setUsr_date_edit(StringUtil.dbDateString2appDateString(outgoingLetter.getUsr_date_edit()));

      outgoingLetter.setOtl_date(StringUtil.dbDateString2appDateString(outgoingLetter.getOtl_date()));

      return outgoingLetter;
    }
    throw new LoadException(outgoingLetter, id);
  }

  public static boolean load(IActionContext context, OutgoingLetter outgoingLetter) throws Exception
  {
    if (DAOUtils.load(context, "outgoing_letter-load", outgoingLetter, null))
    {
      if (!StringUtil.isEmpty(outgoingLetter.getCreateUser().getUsr_id()))
      {
        UserDAO.load(context, outgoingLetter.getCreateUser());
      }

      if (!StringUtil.isEmpty(outgoingLetter.getEditUser().getUsr_id()))
      {
        UserDAO.load(context, outgoingLetter.getEditUser());
      }

      return true;
    }
    else
    {
      return false;
    }
  }

  public static void insert(IActionContext context, OutgoingLetter outgoingLetter) throws Exception
  {
    DAOUtils.load(context, "outgoing_letter-insert", outgoingLetter, null);
  }

  public static void save(IActionContext context, OutgoingLetter outgoingLetter) throws Exception
  {
    DAOUtils.update(context.getConnection(), context.getSqlResource().get("outgoing_letter-update"), outgoingLetter, null);
  }
}