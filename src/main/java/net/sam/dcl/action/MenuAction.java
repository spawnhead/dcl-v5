/**
 * @author: DG
 * Date: Jul 30, 2005
 * Time: 5:48:06 PM
 */
package net.sam.dcl.action;

import net.sam.dcl.controller.actions.BaseAction;
import net.sam.dcl.controller.IActionContext;
import org.apache.struts.action.ActionForward;

public class MenuAction extends BaseAction
{
  public ActionForward execute(IActionContext context) throws Exception
  {
    String menuId = context.getRequest().getParameter(Outline.MENU_ID_NAME);
    context.getRequest().getSession().setAttribute(Outline.MENU_ID_NAME, menuId);

    return new ActionForward(Outline.getURLById(menuId));
  }

}
