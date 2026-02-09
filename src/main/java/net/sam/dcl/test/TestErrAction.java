/**
 * @author: DG
 * Date: Jul 30, 2005
 * Time: 5:48:06 PM
 */
package net.sam.dcl.test;

import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.controller.actions.BaseAction;
import net.sam.dcl.util.StrutsUtil;
import org.apache.struts.action.ActionForward;


public class TestErrAction extends BaseAction  {
  public ActionForward execute(IActionContext context) throws Exception {
    StrutsUtil.addError(context,new Exception("test"));
    return context.getMapping().getInputForward();
  }

}
