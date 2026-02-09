package net.sam.dcl.action;

import net.sam.dcl.controller.actions.DBAction;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.controller.IDispatchable;
import net.sam.dcl.form.InstructionTypesForm;
import net.sam.dcl.util.DAOUtils;
import org.apache.struts.action.ActionForward;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class InstructionTypesAction extends DBAction implements IDispatchable
{

  public ActionForward execute(IActionContext context) throws Exception
  {
    InstructionTypesForm form = (InstructionTypesForm) context.getForm();
    DAOUtils.fillGrid(context, form.getGrid(), "select-instruction_types", InstructionTypesForm.InstructionType.class, null, null);

    return context.getMapping().getInputForward();
  }

  public ActionForward edit(IActionContext context) throws Exception
  {
    return context.getMapping().findForward("edit");
  }
}