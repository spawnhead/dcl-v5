package net.sam.dcl.action;

import net.sam.dcl.navigation.ControlComment;
import net.sam.dcl.controller.ActionBean;
import net.sam.dcl.dao.ControlCommentDAO;
import org.apache.struts.action.ActionForward;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class ControlCommentActionBean extends ActionBean
{
  ControlComment controlComment = new ControlComment();

  public ActionForward saveControlComment() throws Exception
  {
    ControlCommentDAO.save(this, controlComment);
    return getMapping().findForward("ajax");
  }

  public ActionForward insertControlComment() throws Exception
  {
    ControlCommentDAO.insert(this, controlComment);
    return getMapping().findForward("ajax");
  }

  public ControlComment getControlComment()
  {
    return controlComment;
  }
}