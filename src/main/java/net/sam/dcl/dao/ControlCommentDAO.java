package net.sam.dcl.dao;

import net.sam.dcl.navigation.ControlComment;
import net.sam.dcl.navigation.ControlComments;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.util.StoreUtil;
import net.sam.dcl.controller.IActionContext;

/**
 * @author: DG
 * Date: Sep 11, 2005
 * Time: 2:10:04 PM
 */
public class ControlCommentDAO
{
  public static void insert(IActionContext context, ControlComment controlComment) throws Exception
  {
    ControlComments comments = (ControlComments) StoreUtil.getApplication(context.getServletContext(), ControlComments.class);
    comments.putComment(controlComment);
    DAOUtils.update(context.getConnection(), context.getSqlResource().get("controlComment-insert"), controlComment, null);
  }

  public static void save(IActionContext context, ControlComment controlComment) throws Exception
  {
    ControlComments comments = (ControlComments) StoreUtil.getApplication(context.getServletContext(), ControlComments.class);
    comments.putComment(controlComment);
    DAOUtils.update(context.getConnection(), context.getSqlResource().get("controlComment-update"), controlComment, null);
  }
}