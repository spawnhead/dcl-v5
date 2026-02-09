package net.sam.dcl.controller.actions;

import net.sam.dcl.message.EMessage;
import net.sam.dcl.util.StrutsUtil;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.tiles.ComponentContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: DG
 * Date: Mar 27, 2005
 * Time: 4:08:49 PM
 */
public class ErrorAction extends BaseAction{
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    Throwable t = (Throwable)request.getAttribute("javax.servlet.error.exception");
    if (t != null){
      if (t instanceof ServletException && ((ServletException)t).getRootCause() != null){
        t = ((ServletException)t).getRootCause();
      }
      ComponentContext.setContext(null, request);
      StrutsUtil.addError(request,new EMessage(t));
    }
    return mapping.getInputForward();
  }
}
