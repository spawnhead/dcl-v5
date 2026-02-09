package net.sam.dcl.action;

import net.sam.dcl.controller.actions.DBTransactionAction;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.controller.IDispatchable;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.form.RoutesForm;
import net.sam.dcl.taglib.table.IShowChecker;
import net.sam.dcl.taglib.table.ShowCheckerContext;
import org.apache.struts.action.ActionForward;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class RoutesAction extends DBTransactionAction implements IDispatchable
{

  public ActionForward execute(IActionContext context) throws Exception
  {
    final RoutesForm form = (RoutesForm) context.getForm();
    DAOUtils.fillGrid(context, form.getGrid(), "select-routes", RoutesForm.Route.class, null, null);

    context.getRequest().setAttribute("show-delete-checker", new IShowChecker()
    {
      public boolean check(ShowCheckerContext context)
      {
        RoutesForm.Route route = (RoutesForm.Route) form.getGrid().getDataList().get(context.getTable().getRecordCounter() - 1);
        if (route.isOccupied())
        {
          return false;
        }
        return true;
      }
    }
    );
    
    return context.getMapping().getInputForward();
  }

  public ActionForward delete(IActionContext context) throws Exception
  {
    DAOUtils.update(context, "route-delete", null);
    return execute(context);
  }
}
