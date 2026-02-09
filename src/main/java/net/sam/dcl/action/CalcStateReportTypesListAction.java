package net.sam.dcl.action;

import net.sam.dcl.controller.actions.ListAction;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.util.StrutsUtil;

import java.util.Map;
import java.util.LinkedHashMap;

/**
 * @author: DG
 * Date: Aug 18, 2005
 * Time: 2:12:46 PM
 */
public class CalcStateReportTypesListAction extends ListAction
{
  public Object listExecute(IActionContext context) throws Exception
  {
    Map types = new LinkedHashMap();
    types.put(StrutsUtil.getMessage(context, "report_type_list.calc_in_common_id"), StrutsUtil.getMessage(context, "report_type_list.calc_in_common"));
    types.put(StrutsUtil.getMessage(context, "report_type_list.calc_debit_id"), StrutsUtil.getMessage(context, "report_type_list.calc_debit"));
    return types;
  }
}
