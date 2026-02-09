package net.sam.dcl.action;

import net.sam.dcl.controller.actions.DBAction;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.form.RatesNDSForm;
import net.sam.dcl.util.DAOUtils;
import org.apache.struts.action.ActionForward;

public class RatesNDSAction extends DBAction
{

  public ActionForward execute(IActionContext context) throws Exception
  {
    RatesNDSForm form = (RatesNDSForm) context.getForm();
    DAOUtils.fillGrid(context, form.getGrid(), "select-rates_nds", RatesNDSForm.RateNDS.class, null, null);

    return context.getMapping().getInputForward();
  }

}