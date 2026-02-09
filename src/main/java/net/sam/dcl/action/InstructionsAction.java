package net.sam.dcl.action;

import net.sam.dcl.controller.actions.DBAction;
import net.sam.dcl.controller.*;
import net.sam.dcl.controller.processor.ActionHandler;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.beans.InstructionType;
import net.sam.dcl.taglib.table.model.PageableDataHolder;
import net.sam.dcl.taglib.table.IStyleClassChecker;
import net.sam.dcl.taglib.table.StyleClassCheckerContext;
import net.sam.dcl.form.InstructionsForm;
import org.apache.struts.action.ActionForward;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class InstructionsAction extends DBAction implements IDispatchable, IFormAutoSave, IProcessBefore
{
  public ActionForward processBefore(IActionContext context) throws Exception
  {
    context.getActionProcessor().addActionHandler("gridInstructions", PageableDataHolder.NEXT_PAGE, new ActionHandler()
    {
      public ActionForward process(IActionContext context) throws Exception
      {
        InstructionsForm form = (InstructionsForm) context.getForm();
        form.getGridInstructions().incPage();
        return internalFilter(context);
      }
    });
    context.getActionProcessor().addActionHandler("gridInstructions", PageableDataHolder.PREV_PAGE, new ActionHandler()
    {
      public ActionForward process(IActionContext context) throws Exception
      {
        InstructionsForm form = (InstructionsForm) context.getForm();
        form.getGridInstructions().decPage();
        return internalFilter(context);
      }
    });
    return null;
  }

  public ActionForward filter(IActionContext context) throws Exception
  {
    InstructionsForm form = (InstructionsForm) context.getForm();
    form.getGridInstructions().setPage(1);
    return internalFilter(context);
  }

  public ActionForward internalFilter(IActionContext context) throws Exception
  {
    final InstructionsForm form = (InstructionsForm) context.getForm();
    DAOUtils.fillGrid(context, form.getGridInstructions(), "select-instructions", InstructionsForm.Instruction.class, null, null);

    context.getRequest().setAttribute("style-checker", new IStyleClassChecker()
    {
      public String check(StyleClassCheckerContext context)
      {
        InstructionsForm.Instruction instruction = (InstructionsForm.Instruction) form.getGridInstructions().getDataList().get(context.getTable().getRecordCounter() - 1);
        if (!StringUtil.isEmpty(instruction.getIns_inactive()))
        {
          return "crossed-cell";
        }
        return "";
      }
    });

    return context.getMapping().getInputForward();
  }

  public ActionForward input(IActionContext context) throws Exception
  {
    InstructionsForm form = (InstructionsForm) context.getForm();
    form.setDate_begin("");
    form.setDate_end("");
    form.setNumber("");
    form.setShowActive("1");
    form.setType(new InstructionType());
    form.setIns_concerning("");

    return internalFilter(context);
  }

  public ActionForward restore(IActionContext context) throws Exception
  {
    restoreForm(context);
    return internalFilter(context);
  }

}