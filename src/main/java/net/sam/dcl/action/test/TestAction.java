/**
 * @author: DG
 * Date: Jul 30, 2005
 * Time: 5:48:06 PM
 */
package net.sam.dcl.action.test;

import net.sam.dcl.controller.IDispatchable;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.controller.actions.BaseAction;
import org.apache.struts.action.ActionForward;


public class TestAction extends BaseAction implements IDispatchable {
	public String test = "10";
	public ActionForward input(IActionContext context) throws Exception {
		context.getResponse().getWriter().print("test"+test);
		return null;
  }
  public ActionForward test1(IActionContext context) throws Exception {
    return context.getMapping().getInputForward();
  }
  public ActionForward test2(IActionContext context) throws Exception {
    return context.getMapping().findForward("test");
  }

}
