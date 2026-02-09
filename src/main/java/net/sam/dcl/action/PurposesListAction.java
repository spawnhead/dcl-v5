package net.sam.dcl.action;

import net.sam.dcl.controller.actions.ListAction;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.form.PurposesListForm;
import net.sam.dcl.db.VResultSet;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.util.StrutsUtil;

import java.util.Map;
import java.util.LinkedHashMap;

public class PurposesListAction extends ListAction
{
  public Object listExecute(IActionContext context) throws Exception
  {
    PurposesListForm form = (PurposesListForm) context.getForm();
    Map types = new LinkedHashMap();
    VResultSet resultSet = DAOUtils.executeQuery(context, "select-purposes", null);
    if ( form.isHave_all() )
    {
      types.put(StrutsUtil.getMessage(context, "list.all_id"), StrutsUtil.getMessage(context, "list.all"));
    }
    while (resultSet.next())
    {
      types.put(resultSet.getData("prs_id"), resultSet.getData("prs_name"));
    }
    return types;
  }
}
