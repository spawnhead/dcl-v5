package net.sam.dcl.action;

import net.sam.dcl.controller.ActionBean;
import net.sam.dcl.service.DeferredAttachmentService;
import net.sam.dcl.util.UserUtil;
import org.apache.struts.action.ActionForward;
import org.apache.struts.upload.FormFile;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class DeferredUploadFileActionBean extends ActionBean
{
  FormFile file;
  Integer id;

  public ActionForward input() throws Exception
  {
    return mapping.findForward("form");
  }

  public ActionForward back() throws Exception
  {
		DeferredAttachmentService attachmentService = DeferredAttachmentService.get(request.getSession());
		return attachmentService.getBackFromUploadForward();
  }

  public ActionForward process() throws Exception
  {
		DeferredAttachmentService attachmentService = DeferredAttachmentService.get(request.getSession());
		String localFileName = UploadFileActionBean.generateNewName(UserUtil.getCurrentUser(request),
		attachmentService.getRenamer().rename(file.getFileName()));
		if (null == id)
    {
      attachmentService.add(file, localFileName);
    }
    else
    {
      attachmentService.update(id, file, localFileName);
    }
    return back();
  }

  public Integer getId()
  {
    return id;
  }

  public void setId(Integer id)
  {
    this.id = id;
  }

  public FormFile getFile()
  {
    return file;
  }

  public void setFile(FormFile file)
  {
    this.file = file;
  }

}