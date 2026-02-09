package net.sam.dcl.action;

import net.sam.dcl.controller.actions.ListAction;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.util.StrutsUtil;
import net.sam.dcl.db.VResultSet;
import net.sam.dcl.form.StuffCategoriesListForm;

import java.util.Map;
import java.util.LinkedHashMap;

/**
 * @author: DG
 * Date: Aug 18, 2005
 * Time: 2:12:46 PM
 */
public class StuffCategoriesListAction extends ListAction
{
  public Object listExecute(IActionContext context) throws Exception
  {
    StuffCategoriesListForm form = (StuffCategoriesListForm) context.getForm();
    Map types = new LinkedHashMap();
    VResultSet resultSet = DAOUtils.executeQuery(context, "select-stuff_categories-filter", null);
    if ( form.isHave_all() )
    {
      types.put(StrutsUtil.getMessage(context, "list.all_id"), StrutsUtil.getMessage(context, "list.all"));
    }
    while (resultSet.next())
    {
      types.put(resultSet.getData("stf_id"), resultSet.getData("stf_name"));
    }
    return types;
  }
}
