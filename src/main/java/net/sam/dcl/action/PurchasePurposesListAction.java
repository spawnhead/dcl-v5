package net.sam.dcl.action;

import net.sam.dcl.controller.actions.ListAction;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.form.PurchasePurposesListForm;
import net.sam.dcl.db.VResultSet;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.util.StrutsUtil;

import java.util.Map;
import java.util.LinkedHashMap;

public class PurchasePurposesListAction extends ListAction
{
  public Object listExecute(IActionContext context) throws Exception
  {
    PurchasePurposesListForm form = (PurchasePurposesListForm) context.getForm();
    Map types = new LinkedHashMap();
    VResultSet resultSet = DAOUtils.executeQuery(context, "select-purchase_purposes", null);
    if ( form.isHave_all() )
    {
      types.put(StrutsUtil.getMessage(context, "list.all_id"), StrutsUtil.getMessage(context, "list.all"));
    }
    while (resultSet.next())
    {
      types.put(resultSet.getData("pps_id"), resultSet.getData("pps_name"));
    }
    return types;
  }
}