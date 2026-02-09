package net.sam.dcl.action;

import net.sam.dcl.controller.actions.ListAction;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.form.UnitsListForm;
import net.sam.dcl.db.VResultSet;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.util.StrutsUtil;

import java.util.Map;
import java.util.LinkedHashMap;

/**
 * @author: DG
 * Date: Aug 18, 2005
 * Time: 2:12:46 PM
 */
public class UnitsListAction extends ListAction
{
  public Object listExecute(IActionContext context) throws Exception
  {
    UnitsListForm form = (UnitsListForm) context.getForm();
    Map types = new LinkedHashMap();
    VResultSet resultSet = DAOUtils.executeQuery(context, "select-units", null);
    if ( form.isHave_all() )
    {
      types.put(StrutsUtil.getMessage(context, "list.all_id"), StrutsUtil.getMessage(context, "list.all"));
    }
    while (resultSet.next())
    {
      types.put(resultSet.getData("unt_id"), resultSet.getData("unt_name_ru"));
    }
    return types;
  }
}
