package net.sam.dcl.action;

import net.sam.dcl.controller.ActionBean;
import net.sam.dcl.util.UserUtil;
import net.sam.dcl.dbo.DboAttachment;
import net.sam.dcl.beans.User;
import net.sam.dcl.service.AttachmentsService;
import org.apache.struts.action.ActionForward;
import org.apache.struts.upload.FormFile;

import java.util.Calendar;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class UploadFileActionBean extends ActionBean
{
  FormFile file;
  String referencedTable;
  Integer referencedID;
  Integer id;

  public ActionForward input() throws Exception
  {
    return mapping.findForward("form");
  }

  public ActionForward back() throws Exception
  {
    return mapping.findForward("back");
  }

  public ActionForward process() throws Exception
  {
    DboAttachment attachment;
    if (null == id)
    {
      attachment = new DboAttachment(referencedTable, referencedID);
    }
    else
    {
      AttachmentsService attachmentsService = new AttachmentsService(hibSession);
      attachment = attachmentsService.find(id);
    }
    attachment.setOriginalFileName(file.getFileName());
    String localFileName = generateNewName(UserUtil.getCurrentUser(request), attachment.getOriginalFileName());
    attachment.setLocalFileName(localFileName);
    AttachmentsService.upload(file.getInputStream(), attachment);
    hibSession.save(attachment);
    return back();
  }

  static public String generateNewName(User user, String name)
  {
    java.text.DecimalFormat df = new java.text.DecimalFormat("00");
    java.text.DecimalFormat dfms = new java.text.DecimalFormat("000");

    Calendar calendar = Calendar.getInstance();

    String strResult = user.getUsr_code() + "-";
    strResult += Integer.toString(calendar.get(Calendar.YEAR));
    strResult += df.format(calendar.get(Calendar.MONTH) + 1);
    strResult += df.format(calendar.get(Calendar.DAY_OF_MONTH));
    strResult += df.format(calendar.get(Calendar.HOUR_OF_DAY));
    strResult += df.format(calendar.get(Calendar.MINUTE));
    strResult += df.format(calendar.get(Calendar.SECOND));
    strResult += dfms.format(calendar.get(Calendar.MILLISECOND));

    return strResult + "-" + replaceIllegalSymbols(name);
  }

  static public String replaceIllegalSymbols(String name)
  {
    return name.replaceAll("[^ A-Za-zА-Яа-я._0-9]", "-");
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