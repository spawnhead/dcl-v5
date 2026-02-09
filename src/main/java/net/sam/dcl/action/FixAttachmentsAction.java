package net.sam.dcl.action;

import net.sam.dcl.beans.Constants;
import net.sam.dcl.config.Config;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.controller.actions.HibernateAction;
import net.sam.dcl.dbo.DboAttachment;
import net.sam.dcl.form.FilesPathsForm;
import net.sam.dcl.service.AttachmentsService;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.util.HibernateUtil;
import net.sam.dcl.util.StringUtil;
import org.apache.struts.action.ActionForward;
import org.hibernate.Session;

import java.io.CharArrayWriter;
import java.io.File;
import java.io.PrintWriter;
import java.util.List;


/**
 * @author DG
 *         Date: 22-Apr-2008
 *         Time: 22:53:03
 */
public class FixAttachmentsAction extends HibernateAction
{
  private void CheckPath(AttachmentsService attachmentsService, String path, PrintWriter out)
  {
    File attachDir = new File(path);
    File dirToMove = new File(path + "/lost_in_base");
    dirToMove.mkdir();
    File[] attachmentFiles = attachDir.listFiles();
    for (File file : attachmentFiles)
    {
      if (file.isFile() && !attachmentsService.isExistWithSuchLocalName(file.getName()))
      {
        file.renameTo(new File(dirToMove, file.getName()));
        out.printf("Mooving %s because no info about it in DB\n", file.getName());
      }
    }  }

  public ActionForward execute(IActionContext context) throws Exception
  {
    Session hibSession = HibernateUtil.getSessionFactory().getCurrentSession();
    AttachmentsService attachmentsService = new AttachmentsService(hibSession);
    List<DboAttachment> attachments = attachmentsService.list();
    CharArrayWriter writer = new CharArrayWriter();
    PrintWriter out = new PrintWriter(writer);

    /*
    String basePath = Config.getString(Constants.attachmentsDir);
    for (DboAttachment attachment : attachments)
    {
      if (attachment.getFileFixPath(basePath).exists())
      {
        File dirToMove = new File(basePath + "/" + attachment.getReferencedTable());
        File file = new File(basePath + "/" + attachment.getLocalFileName());
        if (file.isFile())
        {
          file.renameTo(new File(dirToMove, file.getName()));
        }
      }
    }
    hibSession.flush();
    */

    for (DboAttachment attachment : attachments)
    {
      if (attachment.getOriginal() != null)
      {
        continue;
      }
      if (StringUtil.isEmpty(attachment.getOriginalFileName()) && attachment.getFile().length() == 0)
      {
        out.printf("Deleting %s because OriginalFileName is empty and size = 0\n", attachment.getLocalFileName());
        attachmentsService.delete(attachment);
        continue;
      }
      if (!attachment.getFile().exists())
      {
        out.printf("Deleting attachment record  from DB %s because file doesn't exist\n", attachment.getLocalFileName());
        attachmentsService.delete(attachment);
      }
    }
    hibSession.flush();
    CheckPath(attachmentsService, Config.getString(Constants.attachmentsDir), out);
    context.getRequest().setAttribute("message", writer.toString());

    FilesPathsForm form = (FilesPathsForm) context.getForm();
    DAOUtils.fillGrid(context, form.getGridFilesPaths(), "select-files_paths", FilesPathsForm.FilesPath.class, null, null);
    for (int i = 0; i < form.getGridFilesPaths().getDataList().size(); i++)
    {
      FilesPathsForm.FilesPath filePath = (FilesPathsForm.FilesPath)form.getGridFilesPaths().getDataList().get(i);
      CheckPath(attachmentsService, filePath.getFlp_path(), out);
    }

    return context.getMapping().getInputForward();
  }
}
