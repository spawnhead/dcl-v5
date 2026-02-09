package net.sam.dcl.action;

import net.sam.dcl.controller.actions.BaseAction;
import net.sam.dcl.controller.IDispatchable;
import net.sam.dcl.controller.IActionContext;
import org.apache.struts.action.ActionForward;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class JournalsAction extends BaseAction implements IDispatchable
{
  public ActionForward input(IActionContext context) throws Exception
  {
    return context.getMapping().getInputForward();
  }
}
