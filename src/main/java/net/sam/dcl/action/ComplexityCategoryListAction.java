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
public class ComplexityCategoryListAction extends ListAction
{
  public Object listExecute(IActionContext context) throws Exception
  {
    Map types = new LinkedHashMap();
    types.put(StrutsUtil.getMessage(context, "complexity_category_list.first_id"), StrutsUtil.getMessage(context, "complexity_category_list.first"));
    types.put(StrutsUtil.getMessage(context, "complexity_category_list.second_id"), StrutsUtil.getMessage(context, "complexity_category_list.second"));
    types.put(StrutsUtil.getMessage(context, "complexity_category_list.third_id"), StrutsUtil.getMessage(context, "complexity_category_list.third"));
    return types;
  }
}