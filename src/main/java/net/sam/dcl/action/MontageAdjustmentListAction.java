package net.sam.dcl.action;

import net.sam.dcl.controller.actions.ListAction;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.beans.StuffCategory;
import net.sam.dcl.beans.MontageAdjustment;
import net.sam.dcl.form.MontageAdjustmentListForm;
import net.sam.dcl.dao.StuffCategoryDAO;

import java.util.Map;
import java.util.LinkedHashMap;
import java.util.List;

public class MontageAdjustmentListAction extends ListAction
{
  public Object listExecute(IActionContext context) throws Exception
  {
    MontageAdjustmentListForm form = (MontageAdjustmentListForm) context.getForm();
    StuffCategory stuffCategory = new StuffCategory(form.getStf_id());
    List<MontageAdjustment> res = StuffCategoryDAO.loadMontageAdjustments(context, stuffCategory);
    Map types = new LinkedHashMap();
    for (MontageAdjustment montageAdjustment : res)
    {
      types.put(montageAdjustment.getMad_id(), montageAdjustment.getMad_machine_type_for_list());
    }
    return types;
  }
}