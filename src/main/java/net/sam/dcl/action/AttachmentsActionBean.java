package net.sam.dcl.action;

import net.sam.dcl.controller.actions.AttachmentsForwarderAction;
import net.sam.dcl.controller.ActionBean;
import net.sam.dcl.taglib.table.model.impl.HolderImplUsingList;
import net.sam.dcl.taglib.table.IReadOnlyChecker;
import net.sam.dcl.taglib.table.ReadOnlyCheckerContext;
import net.sam.dcl.util.StrutsUtil;
import net.sam.dcl.util.UserUtil;
import net.sam.dcl.service.AttachmentsService;
import net.sam.dcl.service.AttachmentException;
import net.sam.dcl.beans.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForward;

/**
 * @author DG
 *         Date: 08-Jan-2008
 *         Time: 20:51:01
 */
public class AttachmentsActionBean extends ActionBean
{
  protected static Log log = LogFactory.getLog(AttachmentsActionBean.class);

  //FormFile file;
  HolderImplUsingList grid;
  String referencedTable;
  Integer referencedID;

  String id;

  public ActionForward show() throws Exception
  {
    AttachmentsService attachmentsService = new AttachmentsService(hibSession);
    grid = new HolderImplUsingList();
    grid.setDataList(attachmentsService.list(referencedTable, referencedID));
    return internalShow();
  }

  public ActionForward delete() throws Exception
  {
    AttachmentsService attachmentsService = new AttachmentsService(hibSession);
    try
    {
      attachmentsService.delete(id);
    }
    catch (AttachmentException e)
    {
      StrutsUtil.addError(this, e);
    }
    return show();
  }

  public ActionForward download() throws Exception
  {
    log.info("start download");
    AttachmentsService attachmentsService = new AttachmentsService(hibSession);
    try
    {
      attachmentsService.download(id, request, response);
      return null;
    }
    catch (AttachmentException e)
    {
      StrutsUtil.addError(this, e);
      return show();
    }
    catch (Throwable e)
    {
      log.error("Unexpected error while downloading", e);
      StrutsUtil.addError(this, "common.msg", e.getMessage(), new RuntimeException(e));
      return show();
    }
  }

  protected ActionForward internalShow()
  {
    // Из DCL_PRODUCE удалять может только админ
    request.setAttribute("deleteChecker", new IReadOnlyChecker()
    {
      User currentUser = UserUtil.getCurrentUser(request);
      public boolean check(ReadOnlyCheckerContext ctx) throws Exception
      {
        return "DCL_PRODUCE".equals(referencedTable) && !currentUser.isAdmin();
      }
    });

    return mapping.findForward("form");
  }

  public ActionForward back() throws Exception
  {
    return AttachmentsForwarderAction.getAttachmentsBack(this);
  }


  public String getReferencedTable()
  {
    return referencedTable;
  }

  public void setReferencedTable(String referencedTable)
  {
    this.referencedTable = referencedTable;
  }

  public Integer getReferencedID()
  {
    return referencedID;
  }

  public void setReferencedID(Integer referencedID)
  {
    this.referencedID = referencedID;
  }

  public String getId()
  {
    return id;
  }

  public void setId(String id)
  {
    this.id = id;
  }

  public HolderImplUsingList getGrid()
  {
    return grid;
  }

  public void setGrid(HolderImplUsingList grid)
  {
    this.grid = grid;
  }
}
