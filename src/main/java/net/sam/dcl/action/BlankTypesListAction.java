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
public class BlankTypesListAction extends ListAction
{
  public Object listExecute(IActionContext context) throws Exception
  {
    Map types = new LinkedHashMap();
    types.put(StrutsUtil.getMessage(context, "blank_types_list.commercial_proposal_blank_id"), StrutsUtil.getMessage(context, "blank_types_list.commercial_proposal_blank_name"));
    types.put(StrutsUtil.getMessage(context, "blank_types_list.order_blank_id"), StrutsUtil.getMessage(context, "blank_types_list.order_blank_name"));
    types.put(StrutsUtil.getMessage(context, "blank_types_list.common_blank_id"), StrutsUtil.getMessage(context, "blank_types_list.common_blank_name"));
    types.put(StrutsUtil.getMessage(context, "blank_types_list.common_light_blank_id"), StrutsUtil.getMessage(context, "blank_types_list.common_light_blank_name"));
    types.put(StrutsUtil.getMessage(context, "blank_types_list.letter_request_blank_id"), StrutsUtil.getMessage(context, "blank_types_list.letter_request_blank_name"));
    return types;
  }
}