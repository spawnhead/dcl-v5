package net.sam.dcl.test;

import net.sam.dcl.controller.actions.BaseAction;
import net.sam.dcl.controller.actions.SelectFromGridAction;
import net.sam.dcl.controller.IDispatchable;
import net.sam.dcl.controller.IActionContext;
import org.apache.struts.action.ActionForward;

public class TestSelectAction extends BaseAction implements IDispatchable {
  public ActionForward input(IActionContext context) throws Exception {
    return context.getMapping().getInputForward();
  }
  public ActionForward selectCP(IActionContext context) throws Exception {
    return context.getMapping().findForward("selectCP");
  }
  public ActionForward return_select_kp(IActionContext context) throws Exception {
		TestSelectForm form = (TestSelectForm) context.getForm();
		form.setKp_id(SelectFromGridAction.getSelectedId(context));
		return context.getMapping().getInputForward();
  }
  public ActionForward select_nomenclature(IActionContext context) throws Exception {
    return context.getMapping().findForward("select_nomenclature");
  }
  public ActionForward returnFromSelectNomenclature(IActionContext context) throws Exception {
		TestSelectForm form = (TestSelectForm) context.getForm();
		form.setNomenclature_id(SelectFromGridAction.getSelectedId(context));
		return context.getMapping().getInputForward();
  }

}
