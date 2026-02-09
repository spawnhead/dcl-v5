package net.sam.dcl.dao;

import net.sam.dcl.beans.InstructionType;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.controller.IActionContext;

public class InstructionTypeDAO
{

  public static InstructionType load(IActionContext context, String id) throws Exception
  {
    InstructionType instructionType = new InstructionType(id);

    if (load(context, instructionType))
    {
      return instructionType;
    }
    throw new LoadException(instructionType, id);
  }

  public static boolean load(IActionContext context, InstructionType instructionType) throws Exception
  {
    return DAOUtils.load(context, "instruction_type-load", instructionType, null);
  }
}