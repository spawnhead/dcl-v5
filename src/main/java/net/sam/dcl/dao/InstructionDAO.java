package net.sam.dcl.dao;

import net.sam.dcl.beans.Instruction;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.controller.IActionContext;

public class InstructionDAO
{

  public static Instruction load(IActionContext context, String id) throws Exception
  {
    Instruction instruction = new Instruction(id);

    if (load(context, instruction))
    {
      instruction.setUsr_date_create(StringUtil.dbDateString2appDateString(instruction.getUsr_date_create()));
      instruction.setUsr_date_edit(StringUtil.dbDateString2appDateString(instruction.getUsr_date_edit()));

      instruction.setIns_date_sign(StringUtil.dbDateString2appDateString(instruction.getIns_date_sign()));
      instruction.setIns_date_from(StringUtil.dbDateString2appDateString(instruction.getIns_date_from()));
      instruction.setIns_date_to(StringUtil.dbDateString2appDateString(instruction.getIns_date_to()));

      return instruction;
    }
    throw new LoadException(instruction, id);
  }

  public static boolean load(IActionContext context, Instruction instruction) throws Exception
  {
    if (DAOUtils.load(context, "instruction-load", instruction, null))
    {
      if (!StringUtil.isEmpty(instruction.getCreateUser().getUsr_id()))
      {
        UserDAO.load(context, instruction.getCreateUser());
      }

      if (!StringUtil.isEmpty(instruction.getEditUser().getUsr_id()))
      {
        UserDAO.load(context, instruction.getEditUser());
      }

      return true;
    }
    else
    {
      return false;
    }
  }

  public static void insert(IActionContext context, Instruction instruction) throws Exception
  {
    DAOUtils.load(context, "instruction-insert", instruction, null);
  }

  public static void save(IActionContext context, Instruction instruction) throws Exception
  {
    DAOUtils.update(context.getConnection(), context.getSqlResource().get("instruction-update"), instruction, null);
  }
}