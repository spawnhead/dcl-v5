package net.sam.dcl.action;

import net.sam.dcl.controller.actions.ListAction;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.util.StrutsUtil;
import net.sam.dcl.db.VResultSet;
import net.sam.dcl.form.RoutesListForm;

import java.util.Map;
import java.util.LinkedHashMap;

public class RoutesListAction extends ListAction
{
  public Object listExecute(IActionContext context) throws Exception
  {
    RoutesListForm form = (RoutesListForm) context.getForm();
    Map types = new LinkedHashMap();
    VResultSet resultSet = DAOUtils.executeQuery(context, "select-routes", null);
    if ( form.isHave_all() )
    {
      types.put(StrutsUtil.getMessage(context, "list.all_id"), StrutsUtil.getMessage(context, "list.all"));
    }
    while (resultSet.next())
    {
      types.put(resultSet.getData("rut_id"), resultSet.getData("rut_name"));
    }
    return types;
  }
}
