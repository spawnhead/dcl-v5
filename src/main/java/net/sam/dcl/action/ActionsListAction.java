package net.sam.dcl.action;

import net.sam.dcl.controller.actions.ListAction;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.db.VResultSet;

import java.util.Map;
import java.util.LinkedHashMap;

public class ActionsListAction extends ListAction
{
  public Object listExecute(IActionContext context) throws Exception
  {
    Map types = new LinkedHashMap();
    VResultSet resultSet = DAOUtils.executeQuery(context, "select-actions_in_list", null);
    while (resultSet.next())
    {
      types.put(resultSet.getData("act_id"), resultSet.getData("act_name"));
    }
    return types;
  }
}