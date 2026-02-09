package net.sam.dcl.action;

import net.sam.dcl.controller.actions.DBAction;
import net.sam.dcl.controller.IDispatchable;
import net.sam.dcl.controller.IFormAutoSave;
import net.sam.dcl.controller.IProcessBefore;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.controller.processor.ActionHandler;
import net.sam.dcl.taglib.table.model.PageableDataHolder;
import net.sam.dcl.taglib.table.IReadOnlyChecker;
import net.sam.dcl.taglib.table.ReadOnlyCheckerContext;
import net.sam.dcl.form.AssemblesForm;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.beans.User;
import org.apache.struts.action.ActionForward;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class AssemblesAction extends DBAction implements IDispatchable, IFormAutoSave, IProcessBefore
{
  public ActionForward processBefore(IActionContext context) throws Exception
  {
    context.getActionProcessor().addActionHandler("grid", PageableDataHolder.NEXT_PAGE, new ActionHandler()
    {
      public ActionForward process(IActionContext context) throws Exception
      {
        AssemblesForm form = (AssemblesForm) context.getForm();
        form.getGrid().incPage();
        return internalFilter(context);
      }
    });
    context.getActionProcessor().addActionHandler("grid", PageableDataHolder.PREV_PAGE, new ActionHandler()
    {
      public ActionForward process(IActionContext context) throws Exception
      {
        AssemblesForm form = (AssemblesForm) context.getForm();
        form.getGrid().decPage();
        return internalFilter(context);
      }
    });
    return null;
  }

  public ActionForward internalFilter(IActionContext context) throws Exception
  {
    AssemblesForm form = (AssemblesForm) context.getForm();
    DAOUtils.fillGrid(context, form.getGrid(), "select-assembles", AssemblesForm.Assemble.class, null, null);

    //Разблокировать (по п1298-1299 блокировка и разблокировка автоматические)
    context.getRequest().setAttribute("blockChecker", new IReadOnlyChecker()
    {
      public boolean check(ReadOnlyCheckerContext ctx)
      {
        AssemblesForm.Assemble record = (AssemblesForm.Assemble) ctx.getBean();
        return StringUtil.isEmpty(record.getAsm_block());
      }
    });

    return context.getMapping().getInputForward();
  }

  public ActionForward filter(IActionContext context) throws Exception
  {
    AssemblesForm form = (AssemblesForm) context.getForm();
    form.getGrid().setPage(1);
    return internalFilter(context);
  }

  public ActionForward input(IActionContext context) throws Exception
  {
    AssemblesForm form = (AssemblesForm) context.getForm();
    form.setDate_begin("");
    form.setDate_end("");
    form.setAsm_id("");
    form.setNumber("");
    form.setUser(new User());
    form.setBlock("");

    return internalFilter(context);
  }

  public ActionForward restore(IActionContext context) throws Exception
  {
    restoreForm(context);
    return internalFilter(context);
  }

}
