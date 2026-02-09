package net.sam.dcl.action;

import net.sam.dcl.controller.actions.ListAction;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.config.Config;
import net.sam.dcl.beans.Constants;

import java.util.Map;
import java.util.LinkedHashMap;

/**
 * @author: DG
 * Date: Aug 18, 2005
 * Time: 2:12:46 PM
 */
public class NumbersListAction extends ListAction
{
  public Object listExecute(IActionContext context) throws Exception
  {
    Map types = new LinkedHashMap();
    int maxLevel = Config.getNumber(Constants.maxReputationLevel, 5);
    for ( int i = 0; i <= maxLevel; i++ )
    {
      String intStr = Integer.toString(i);
      types.put(intStr, intStr);
    }
    return types;
  }
}
