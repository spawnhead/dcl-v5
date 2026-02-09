package net.sam.dcl.service;

import com.ibm.icu.text.Transliterator;
import net.sam.dcl.action.UploadFileActionBean;
import net.sam.dcl.dbo.DboAttachment;
import net.sam.dcl.filters.ResponseCollectFilter;
import net.sam.dcl.util.FileUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

/**
 * @author DG
 *         Date: 28-Feb-2008
 *         Time: 21:55:30
 */
public class AttachmentsService
{
  protected static Log log = LogFactory.getLog(AttachmentsService.class);
  Session hibSession;

  public AttachmentsService(Session hibSession)
  {
    this.hibSession = hibSession;
  }

  public List list(String referencedTable, Integer referencedID, Order order) throws Exception
  {
    assert referencedTable != null;
    assert referencedID != null;
    Criteria criteria = hibSession.createCriteria(DboAttachment.class)
            .add(Restrictions.eq("referencedTable", referencedTable))
            .add(Restrictions.eq("referencedID", referencedID));
    if (order != null)
    {
      criteria.addOrder(order);
    }
    return criteria.list();
  }

  public List list(String referencedTable, Integer referencedID) throws Exception
  {
    return list(referencedTable, referencedID, null);
  }

  public DboAttachment firstAttach(String referencedTable, Integer referencedID) throws Exception
  {
    List list = hibSession.createCriteria(DboAttachment.class)
            .add(Restrictions.eq("referencedTable", referencedTable))
            .add(Restrictions.eq("referencedID", referencedID))
            .list();

    if (list.size() != 0)
    {
      return (DboAttachment)list.get(0);
    }
    return null;
  }

  public List list() throws Exception
  {
    return hibSession.createCriteria(DboAttachment.class).list();
  }

  public void delete(String id) throws Exception
  {
    assert id != null;
    DboAttachment attachment = find(Integer.parseInt(id));
    if (attachment == null)
    {
      throw new AttachmentException("Attachments.file.deleted");
    }
    else
    {
      delete(attachment);
    }
  }

  public void delete(DboAttachment attachment)
  {
    hibSession.delete(DboAttachment.class.getName(), attachment);
    attachment.deleteFile();
  }

  public DboAttachment find(Integer id)
  {
    return (DboAttachment) hibSession.get(DboAttachment.class, id);
  }

  public boolean isExistWithSuchLocalName(String localName)
  {
    List list = hibSession.createCriteria(DboAttachment.class)
            .add(Restrictions.eq("localFileName", localName))
            .list();
    return list != null && list.size() == 1;
  }

  public void download(String id, HttpServletRequest request, HttpServletResponse response) throws Exception
  {
    log.info("downloading file with id:" + id);
    assert id != null;
    DboAttachment attachment = find(Integer.parseInt(id));
    download(attachment, request, response);
  }

  static public void download(DboAttachment attachment, HttpServletRequest request, HttpServletResponse response) throws Exception
  {
    if (attachment == null)
    {
      log.info("find return null");
      throw new AttachmentException("Attachments.file.deleted");
    }
    else
    {
      log.info("find return " + attachment.getId() + "," + attachment.getLocalFileName() + "," + attachment.getOriginalFileName());
      File file = attachment.getFile();
      if (!file.exists())
      {
        log.info("file for download:" + file.getAbsolutePath() + " is not exist");
      }
      else
      {
        log.info("file for download:" + file.getAbsolutePath());
        InputStream is = new FileInputStream(file);
        try
        {
          downloadStream(request, response, is, attachment.getOriginalFileName());
        }
        finally
        {
          FileUtil.safeClose(is);
        }
      }
    }
  }

  public static void downloadStream(HttpServletRequest request, HttpServletResponse response, InputStream is, String fileName)
          throws IOException
  {
    ResponseCollectFilter.resetNeedResponseCollect(request);
    log.info("set response headers");
    response.setContentType("application/download");
    Transliterator transliterator = Transliterator.getInstance("Cyrillic-Latin; nfd; [\u0308\u030c\u0302\u0301\u02b9\u02ba] remove;");
    fileName = transliterator.
            transform(UploadFileActionBean.replaceIllegalSymbols(fileName));
    response.setHeader("Content-disposition", " attachment;filename=\"" + fileName + "\"");
    fromInputStreamToOutputStream(is, response.getOutputStream());
  }

  static public void fromInputStreamToOutputStream(InputStream is, OutputStream os)
  {
    log.info("copying streams");
    assert is != null;
    assert os != null;
    try
    {
      final int BUFFER_SIZE = 10000;
      byte buff[] = new byte[BUFFER_SIZE];
      for (int nRead = is.read(buff); nRead != -1; nRead = is.read(buff))
      {
        os.write(buff, 0, nRead);
      }
    }
    catch (IOException e)
    {
      throw new RuntimeException(e);
    }
    finally
    {
      FileUtil.safeClose(os);
    }
    log.info("finished copying streams");
  }

  static public void upload(InputStream is, DboAttachment attachment)
  {
    assert is != null;
    assert attachment.getOriginal() == null;
    OutputStream os = null;
    try
    {
      File file = attachment.getFile();
      os = new FileOutputStream(file);
      final int BUFFER_SIZE = 10000;
      byte buff[] = new byte[BUFFER_SIZE];
      for (int nRead = is.read(buff); nRead != -1; nRead = is.read(buff))
      {
        os.write(buff, 0, nRead);
      }
    }
    catch (IOException e)
    {
      throw new RuntimeException(e);
    }
    finally
    {
      attachment.deleteOldFileIfExist();
      FileUtil.safeClose(os);
    }
  }

  public static void main(String[] args)
  {
    Transliterator transliterator = Transliterator.getInstance("Cyrillic-Latin");
    String res = transliterator.transform("Test тест");
    System.out.println(res);
  }
}
