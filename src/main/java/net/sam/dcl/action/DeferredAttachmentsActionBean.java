package net.sam.dcl.action;

import net.sam.dcl.controller.ActionBean;
import net.sam.dcl.service.DeferredAttachmentService;
import net.sam.dcl.taglib.table.model.impl.HolderImplUsingList;
import net.sam.dcl.util.StrutsUtil;
import net.sam.dcl.service.AttachmentException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForward;

/**
 * @author DG
 *         Date: 08-Jan-2008
 *         Time: 20:51:01
 */
public class DeferredAttachmentsActionBean extends ActionBean
{
  protected static Log log = LogFactory.getLog(DeferredAttachmentsActionBean.class);
	ActionForward savedUploadForward;
  //FormFile file;
  HolderImplUsingList grid;
	DeferredAttachmentService attachmentsService;

  String id;

  public ActionForward init() throws Exception
  {
		attachmentsService = DeferredAttachmentService.get(request.getSession());
		savedUploadForward = attachmentsService.getBackFromUploadForward();
		attachmentsService.setBackFromUploadForward(mapping.findForward("back"));
		
    return show();
  }
	public ActionForward show() throws Exception
  {

		grid = new HolderImplUsingList();
    grid.setDataList(attachmentsService.list());
    return internalShow();
  }

  public ActionForward delete() throws Exception
  {
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

  private ActionForward internalShow()
  {
    return mapping.findForward("form");
  }

  public ActionForward back() throws Exception
  {
		attachmentsService.setBackFromUploadForward(savedUploadForward);
		return attachmentsService.getBackFromListForward();
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
//	public FormFile getFile() {
//		return file;
//	}
//	public void setFile(FormFile file) {
//		this.file = file;
//	}


}