package net.sam.dcl.action;

import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.controller.IDispatchable;
import net.sam.dcl.controller.actions.DBTransactionAction;
import net.sam.dcl.util.DAOUtils;
import org.apache.struts.action.ActionForward;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class FilesPathAction extends DBTransactionAction implements IDispatchable
{
  public ActionForward input(IActionContext context) throws Exception
  {
    return context.getMapping().getInputForward();
  }

  public ActionForward edit(IActionContext context) throws Exception
  {
    DAOUtils.load(context, "files_path-load", null);
    return input(context);
  }

  public ActionForward process(IActionContext context) throws Exception
  {
    DAOUtils.update(context, "files_path-update", null);
    return context.getMapping().findForward("back");
  }

}