package net.sam.dcl.action;

import net.sam.dcl.controller.actions.ListAction;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.db.VResultSet;
import net.sam.dcl.util.DAOUtils;

import java.util.Map;
import java.util.LinkedHashMap;

/**
 * @author: DG
 * Date: Aug 18, 2005
 * Time: 2:12:46 PM
 */
public class ReputationsListAction extends ListAction
{
  public Object listExecute(IActionContext context) throws Exception
  {
    Map types = new LinkedHashMap();
    VResultSet resultSet = DAOUtils.executeQuery(context, "select-reputations", null);
    while (resultSet.next())
    {
      types.put(resultSet.getData("rpt_id"), resultSet.getData("rpt_description"));
    }
    return types;
  }
}
