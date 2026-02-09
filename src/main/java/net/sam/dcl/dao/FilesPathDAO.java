package net.sam.dcl.dao;

import net.sam.dcl.beans.Constants;
import net.sam.dcl.beans.FilesPath;
import net.sam.dcl.config.Config;
import net.sam.dcl.db.VDbConnection;
import net.sam.dcl.db.VDbConnectionManager;
import net.sam.dcl.db.VParameter;
import net.sam.dcl.db.VResultSet;
import net.sam.dcl.util.DAOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.Types;

public class FilesPathDAO
{
  protected static Log log = LogFactory.getLog(FilesPathDAO.class);

  public static FilesPath load(String tableName)
  {
    VDbConnection conn = null;
    try
    {
      conn = VDbConnectionManager.getVDbConnection();
      String sql = "select flp_path as \"path\"\n" +
              "    from dcl_files_path\n" +
              "    where flp_table_name = :tableName";
      VParameter parans = new VParameter();
      parans.add("tableName", tableName, Types.VARCHAR);
      VResultSet resultSet = DAOUtils.executeQuery(conn, sql, null, parans);
      if (resultSet.next())
      {
        return new FilesPath(tableName, resultSet.getData("path"));
      }
    }
    catch (Exception e)
    {
      log.error(e);
      throw new RuntimeException(e);
    }
    finally
    {
      if (conn != null) conn.close();
    }

    return null;
  }

  public static String GetFileAttachPath(String tableName)
  {
    FilesPath filesPath = load(tableName);
    if (null != filesPath)
      return filesPath.getPath();
    
    return Config.getString(Constants.attachmentsDir);
  }
}