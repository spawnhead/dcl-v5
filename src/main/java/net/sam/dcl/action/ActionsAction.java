package net.sam.dcl.action;

import net.sam.dcl.controller.actions.DBAction;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.form.ActionsForm;
import net.sam.dcl.taglib.table.IReadOnlyChecker;
import net.sam.dcl.taglib.table.ReadOnlyCheckerContext;
import org.apache.struts.action.ActionForward;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class ActionsAction extends DBAction
{

  public ActionForward execute(IActionContext context) throws Exception
  {
    final ActionsForm form = (ActionsForm) context.getForm();
    DAOUtils.fillGrid(context, form.getGridActions(), "select-actions", ActionsForm.Action.class, null, null);

    context.getRequest().setAttribute("alwaysReadonly", new IReadOnlyChecker()
    {
      public boolean check(ReadOnlyCheckerContext ctx) throws Exception
      {
        return true;
      }
    });

    return context.getMapping().getInputForward();
  }

}