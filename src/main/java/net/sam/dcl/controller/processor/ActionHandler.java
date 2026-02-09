package net.sam.dcl.controller.processor;

import net.sam.dcl.controller.IActionContext;
import org.apache.struts.action.ActionForward;

/**
 * User: Dima
 * Date: Mar 20, 2005
 * Time: 4:19:07 PM
 */
public interface ActionHandler {
  ActionForward process(IActionContext context) throws Exception;
}
