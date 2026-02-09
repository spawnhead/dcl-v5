package net.sam.dcl.action;

import net.sam.dcl.controller.actions.ListAction;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.form.SpecificationForm;
import net.sam.dcl.util.StrutsUtil;

import java.util.Map;
import java.util.LinkedHashMap;

public class DeliveryTermTypesListAction extends ListAction
{
  public Object listExecute(IActionContext context) throws Exception
  {
    Map types = new LinkedHashMap();
    types.put(SpecificationForm.EnterImmediatelyId, StrutsUtil.getMessage(context, "DeliveryTerm.EnterImmediately"));
    types.put(SpecificationForm.EnterAfterPrepayId, StrutsUtil.getMessage(context, "DeliveryTerm.EnterAfterPrepay"));
    return types;
  }
}
