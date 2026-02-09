package net.sam.dcl.action;


import net.sam.dcl.service.DeferredAttachmentService;
import net.sam.dcl.util.UserUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Aleksandra Shkrobova
 * Date: 10/30/13
*/

public class MultipleFileUploadHelperAction extends DispatchAction {
  public ActionForward upload(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    ServletFileUpload upload = prepareUploader();
    FileItem file = getUploadedFileItem(upload.parseRequest(request));
    if (file != null) {
      String fileName = file.getName();
      if (!getDeferredAttachmentService(request).alreadyExists(fileName)) {
        String localFileName = UploadFileActionBean.generateNewName(UserUtil.getCurrentUser(request), getDeferredAttachmentService(request).getRenamer().rename(fileName));
        getDeferredAttachmentService(request).add(new DCLFormFile(file), localFileName);
      }
    }
    return mapping.findForward("form");
  }

  private FileItem getUploadedFileItem(List<FileItem> items) {
    FileItem file = null;
    for (FileItem fileItem : items) {
      if (fileItem.getFieldName().equals("file")) {
        file = fileItem;
      }
    }
    return file;
  }

  private ServletFileUpload prepareUploader() {
    FileItemFactory factory = new DiskFileItemFactory();
    return new ServletFileUpload(factory);
  }

  private DeferredAttachmentService getDeferredAttachmentService(HttpServletRequest request) {
    return DeferredAttachmentService.get(request.getSession());
  }

  class DCLFormFile implements FormFile {
    FileItem fileItem;

    DCLFormFile(FileItem fileItem) {
      this.fileItem = fileItem;
    }

    @Override
    public byte[] getFileData() throws IOException {
      return fileItem.get();
    }

    @Override
    public InputStream getInputStream() throws IOException {
      return fileItem.getInputStream();
    }

    @Override
    public String getContentType() {
      return fileItem.getContentType();
    }

    @Override
    public void destroy() {
      fileItem.delete();
    }

    @Override
    public String getFileName() {
      return fileItem.getName();
    }

    @Override
    public void setContentType(String s) {

    }

    @Override
    public int getFileSize() {
      return 0;
    }

    @Override
    public void setFileSize(int i) {
    }

    @Override
    public void setFileName(String s) {
    }
  }
}
