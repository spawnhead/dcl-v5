package net.sam.dcl.action;

import net.sam.dcl.controller.actions.ListAction;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.util.StrutsUtil;
import net.sam.dcl.db.VResultSet;
import net.sam.dcl.form.InstructionTypesListForm;

import java.util.Map;
import java.util.LinkedHashMap;

public class InstructionTypesListAction extends ListAction
{
  public Object listExecute(IActionContext context) throws Exception
  {
    InstructionTypesListForm form = (InstructionTypesListForm) context.getForm();
    Map types = new LinkedHashMap();
    VResultSet resultSet = DAOUtils.executeQuery(context, "select-instruction_types", null);
    if ( form.isHave_all() )
    {
      types.put(StrutsUtil.getMessage(context, "list.all_id"), StrutsUtil.getMessage(context, "list.all"));
    }
    while (resultSet.next())
    {
      types.put(resultSet.getData("ist_id"), resultSet.getData("ist_name"));
    }
    return types;
  }
}