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
public class ContractorRequestTypeListAction extends ListAction
{
  public Object listExecute(IActionContext context) throws Exception
  {
    Map types = new LinkedHashMap();
    types.put(StrutsUtil.getMessage(context, "ctr_request_type_list.pnp_id"), StrutsUtil.getMessage(context, "ctr_request_type_list.pnp"));
    types.put(StrutsUtil.getMessage(context, "ctr_request_type_list.service_id"), StrutsUtil.getMessage(context, "ctr_request_type_list.service"));
    types.put(StrutsUtil.getMessage(context, "ctr_request_type_list.guarantee_id"), StrutsUtil.getMessage(context, "ctr_request_type_list.guarantee"));
    return types;
  }
}