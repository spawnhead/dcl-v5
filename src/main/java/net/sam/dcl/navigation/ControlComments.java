package net.sam.dcl.navigation;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

import net.sam.dcl.db.VDbConnection;
import net.sam.dcl.db.VResultSet;
import net.sam.dcl.db.VParameter;
import net.sam.dcl.db.VDbConnectionManager;
import net.sam.dcl.util.DAOUtils;

public class ControlComments
{
  protected static Log log = LogFactory.getLog(ControlComments.class);

  final private Object mutex = new Object();
  private Map<String, ControlComment> comments = new LinkedHashMap();

  public ControlComments()
  {
  }

  public void clear()
  {
    synchronized (mutex)
    {
      comments.clear();
    }
  }

  public void load()
  {
    synchronized (mutex)
    {
      VDbConnection conn = null;
      try
      {
        conn = VDbConnectionManager.getVDbConnection();
        String sql = "\nselect\n" +
                     "  fcm_id,\n" +
                     "  fcm_key,\n" +
                     "  fcm_value\n" +
                     "from dcl_field_comment\n";
        VResultSet resultSet = DAOUtils.executeQuery(conn, sql, null, new VParameter());
        while (resultSet.next())
        {
          ControlComment controlComment = new ControlComment(resultSet.getData("fcm_id"), resultSet.getData("fcm_key"), resultSet.getData("fcm_value"));
          comments.put(resultSet.getData("fcm_key"), controlComment);
        }
      }
      catch (Exception e)
      {
        log.error("Error while loading comments:", e);
      }
      finally
      {
        if (conn != null) conn.close();
      }
    }
  }

  public void reload()
  {
    synchronized (mutex)
    {
      clear();
      load();
    }
  }

  public String getCommentString(String key)
  {
    synchronized (mutex)
    {
      String comment = null;
      ControlComment controlComment = getComment(key);
      if ( null != controlComment )
      {
        comment = controlComment.getFcm_value();
      }
      return comment;
    }
  }

  public ControlComment getComment(String key)
  {
    synchronized (mutex)
    {
      ControlComment controlComment;
      controlComment = comments.get(key);
      return controlComment;
    }
  }

  public void putComment(ControlComment controlComment)
  {
    synchronized (mutex)
    {
      ControlComment controlCommentCheck = getComment(controlComment.getFcm_key());
      if ( null != controlCommentCheck )
      {
        controlCommentCheck.setFcm_value(controlComment.getFcm_value());  
      }
      else
      {
        comments.put(controlComment.getFcm_key(), controlComment);
      }
    }
  }
}