package net.sam.dcl.action;

import net.sam.dcl.controller.actions.ListAction;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.beans.IncoTerm;
import net.sam.dcl.form.DeliveryConditionListForm;
import net.sam.dcl.dao.IncoTermDAO;

import java.util.Map;
import java.util.LinkedHashMap;
import java.util.List;

public class DeliveryConditionListAction extends ListAction
{
  public Object listExecute(IActionContext context) throws Exception
  {
    DeliveryConditionListForm form = (DeliveryConditionListForm) context.getForm();

    Map types = new LinkedHashMap();
    List<IncoTerm> res = IncoTermDAO.loadDependentTerms(context, form.getName());
    for (IncoTerm term : res)
    {
      types.put(term.getId(), term.getNameExtended());
    }

    return types;
  }
}
