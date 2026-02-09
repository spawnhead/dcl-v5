package net.sam.dcl.dbo;

import net.sam.dcl.dao.FilesPathDAO;
import net.sam.dcl.service.AttachmentsService;
import net.sam.dcl.util.FileUtil;
import net.sam.dcl.util.StringUtil;

import javax.persistence.*;
import java.io.*;
import java.util.Date;

/**
 * @author DG
 *         Date: 08-Jan-2008
 *         Time: 20:20:36
 */
@Entity
@Table(name = "DCL_ATTACHMENT")
@SequenceGenerator(name = "GEN_DCL_ATTACHMENT_ID", sequenceName = "GEN_DCL_ATTACHMENT_ID", allocationSize = 1)
public class DboAttachment
{
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_DCL_ATTACHMENT_ID")
  @Column(name = "ATT_ID", unique = true, nullable = false)
  private Integer id;

  @Column(name = "ATT_PARENT_TABLE", nullable = false)
  private String referencedTable;

  @Column(name = "ATT_PARENT_ID", nullable = false)
  private Integer referencedID;

  @Column(name = "ATT_NAME")
  private String originalFileName;

  @Column(name = "ATT_FILE_NAME")
  private String localFileName;

  @Column(name = "ATT_CREATE_DATE")
  private Date createDate;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "USR_ID", nullable = true, insertable = false, updatable = false)
  private DboUser user;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "ATT_LINK_ID", nullable = true)
  private DboAttachment original;

  @Transient
  private String oldLocalFileName = null;

  public DboAttachment()
  {
  }

  public DboAttachment(Integer id)
  {
    this.id = id;
  }

  public DboAttachment(String referencedTable, Integer referencedID)
  {
    this.referencedTable = referencedTable;
    this.referencedID = referencedID;
  }

  public DboAttachment(Integer id, String referencedTable, Integer referencedID)
  {
    this.id = id;
    this.referencedTable = referencedTable;
    this.referencedID = referencedID;
  }

  public DboAttachment(String referencedTable, Integer referencedID, String originalFileName, String localFileName)
  {
    this.referencedTable = referencedTable;
    this.referencedID = referencedID;
    this.originalFileName = originalFileName;
    this.localFileName = localFileName;
  }

  public DboAttachment(Integer id, String referencedTable, Integer referencedID, String originalFileName, String localFileName)
  {
    this.id = id;
    this.referencedTable = referencedTable;
    this.referencedID = referencedID;
    this.originalFileName = originalFileName;
    this.localFileName = localFileName;
  }

  public Integer getId()
  {
    return id;
  }

  public void setId(Integer id)
  {
    this.id = id;
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

  public String getOriginalFileName()
  {
    return getOriginal() == null ? originalFileName : getOriginal().getOriginalFileName();
  }

  public String getOriginalFileExtention()
  {
    return getOriginalFileName().substring(getOriginalFileName().length() - 4, getOriginalFileName().length()).toUpperCase();
  }

  public void setOriginalFileName(String originalFileName)
  {
    assert getOriginal() == null;
    this.originalFileName = originalFileName;
  }

  public String getLocalFileName()
  {
    return getOriginal() == null ? localFileName : getOriginal().getLocalFileName();
  }

  public void setLocalFileName(String localFileName)
  {
    assert getOriginal() == null;
    assert localFileName != null;
    if (this.oldLocalFileName == null)
    {
      this.oldLocalFileName = this.localFileName;
    }
    this.localFileName = localFileName;
  }

  public DboAttachment getOriginal()
  {
    return original;
  }

  public void setOriginal(DboAttachment original)
  {
    this.original = original;
  }

  public String getCreateDate_formatted()
  {
    return StringUtil.date2appDateString(createDate);
  }

  public Date getCreateDate()
  {
    return createDate;
  }

  public void setCreateDate(Date createDate)
  {
    this.createDate = createDate;
  }

  public DboUser getUser()
  {
    return user;
  }

  public void setUser(DboUser user)
  {
    this.user = user;
  }

  public void parseOriginalFileName(String fileName)
  {
    assert getOriginal() == null;
    File file = new File(fileName);
    this.originalFileName = file.getName();
  }

  public void deleteFile()
  {
    if (getOriginal() == null)
    {
      File file = getFile();
      file.delete();
    }
  }

  public void deleteOldFileIfExist()
  {
    if (getOriginal() == null)
    {
      if (oldLocalFileName != null)
      {
        File file = getOldFile();
        file.delete();
      }
    }
  }

  private File getOldFile()
  {
    assert getOriginal() == null;
    return new File(FilesPathDAO.GetFileAttachPath(getReferencedTable()), oldLocalFileName);
  }

  public File getFile()
  {
    return new File(FilesPathDAO.GetFileAttachPath(getOriginal() == null ? getReferencedTable() : getOriginal().getReferencedTable()), getLocalFileName());
  }

  public File getFileFixPath(String path)
  {
    return new File(path, getLocalFileName());
  }

  public byte[] getContent()
  {
    File file = getFile();
    ByteArrayOutputStream os = null;
    InputStream is = null;
    try
    {
      os = new ByteArrayOutputStream((int) file.length());
      is = new BufferedInputStream(new FileInputStream(file));
      AttachmentsService.fromInputStreamToOutputStream(is, os);
      return os.toByteArray();
    }
    catch (FileNotFoundException e)
    {
      throw new RuntimeException(e);
    }
    finally
    {
      FileUtil.safeClose(os);
      FileUtil.safeClose(is);
    }
  }

}
