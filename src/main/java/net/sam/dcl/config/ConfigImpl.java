package net.sam.dcl.config;

import net.sam.dcl.db.VDbConnection;
import net.sam.dcl.db.VResultSet;
import net.sam.dcl.db.VDbConnectionManager;
import net.sam.dcl.db.VParameter;
import net.sam.dcl.util.TypeConverter;
import net.sam.dcl.util.DAOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;
import java.sql.Types;

/**
 * User: Dima
 * Date: Feb 17, 2005
 * Time: 10:14:02 AM
 */
public class ConfigImpl implements IConfig
{
  protected static Log log = LogFactory.getLog(ConfigImpl.class);

  final protected Object configMutex = new Object();
  protected Properties values = new Properties();
  protected ArrayList<String> cfgFiles = new ArrayList<String>();

  String strPathBegin = "";
  static final String HOME = "%HOME%";
  static protected ConfigImpl config = new ConfigImpl();

  /**
   * The protected constructor.
   */
  protected ConfigImpl()
  {
  }

  static public ConfigImpl getConfigImpl()
  {
    return config;
  }

  /* IConfig interface implementation */

  /**
   * The method returns the values of the paroperty of the section.
   */
  public String getString(String section, String name)
  {
    synchronized (configMutex)
    {
      return replaceHome(values.getProperty(section + "." + name, ""));
    }
  }

  // ************************************************************************ //
  /**
   * The method returns the values of the paroperty of the section.
   */
  public String getString(String name)
  {
    synchronized (configMutex)
    {
      return replaceHome(values.getProperty(name, ""));
    }
  }

  // ************************************************************************ //
  /**
   * The method set the value of the paroperty of the section.
   */
  public void setString(String name, String value, boolean toDB)
  {
    synchronized (configMutex)
    {
      values.setProperty(name, value);
    }
    if ( toDB )
    {
      if ( isExistInDB(name) )
      {
        update(name, value);
      }
      else
      {
        insert(name, value);
      }
    }
  }

  //
  /**
   * getNumber
   */
  public int getNumber(String section, String name, int defaultValue)
  {
    int iResult = defaultValue;
    String sNum = getString(section, name);
    if (sNum.length() > 0)
    {
      try
      {
        iResult = Integer.parseInt(sNum);
      }
      catch (NumberFormatException e)
      {
        log.error(e);  
      }
    }
    return iResult;
  }

  /**
   * getNumber
   */
  public int getNumber(String name, int defaultValue)
  {
    int iResult = defaultValue;
    String sNum = getString(name);
    if (sNum.length() > 0)
    {
      try
      {
        iResult = Integer.parseInt(sNum);
      }
      catch (NumberFormatException e)
      {
        log.error(e);
      }
    }
    return iResult;
  }

  public boolean haveNumber(String name)
  {
    String sNum = getString(name);
    return (sNum.length() > 0);
  }

  /**
   * getNumber
   */
  public float getFloat(String name, float defaultValue)
  {
    float iResult = defaultValue;
    String sNum = getString(name);
    if (sNum.length() > 0)
    {
      try
      {
        iResult = Float.parseFloat(sNum);
      }
      catch (NumberFormatException e)
      {
        log.error(e);
      }
    }
    return iResult;
  }

  /**
   * getBoolean
   */
  public boolean getBoolean(String section, String name, boolean defaultValue)
  {
    String sBoolean = getString(name);
    if (sBoolean.length() > 0)
    {
      return "1".equals(sBoolean) || TypeConverter.toBoolean(sBoolean);
    }
    else
    {
      return defaultValue;
    }
  }

  public boolean getBoolean(String name, boolean defaultValue)
  {
    boolean iResult = defaultValue;
    String sBoolean = getString(name);
    if (sBoolean.length() > 0)
    {
      iResult = TypeConverter.toBoolean(sBoolean);
    }
    return iResult;
  }

  public void addProperties(Properties properties)
  {
    synchronized (configMutex)
    {
      Enumeration keys = properties.keys();
      while (keys.hasMoreElements())
      {
        String key = (String) keys.nextElement();
        values.setProperty(key, properties.getProperty(key));
      }
    }
  }

  public void reload()
  {
    Properties properties = new Properties();
    for (String cfgFile : cfgFiles)
    {
      try
      {
        readFile(cfgFile, properties);
      }
      catch (Exception e)
      {
        log.error(e.getMessage(), e);
      }
    }
    Properties dbProperties = new Properties();
    try
    {
      dbProperties = readPersistConfig();
    }
    catch (Exception e)
    {
      log.error(e.getMessage(), e);
    }
    synchronized (configMutex)
    {
      values = new Properties();
      addProperties(properties);
      addProperties(dbProperties);
    }
    log.info("reloaded");
  }


  // ************************************************************************ //
  /**
   * The method returns the properties of the current section and their values.
   */
  public Properties getSection(String section)
  {
    synchronized (configMutex)
    {
      Properties hashResult = new Properties();
      Enumeration keys = values.keys();
      while (keys.hasMoreElements())
      {
        String key = (String) keys.nextElement();
        if (key.startsWith(section + "."))
        {
          hashResult.put(key.substring(section.length() + 1),
                  getString(section, key.substring(section.length() + 1)));
        }//values.get(key)
      }
      return hashResult;
    }
  }

  public Properties getConfig()//????
  {
    return values;
  }

  public void save(VDbConnection conn, String key, String value) throws Exception
  {
    throw new Exception("not implemeted");
  }


  // ************************************************************************ //
  /**
   * The method initializes the needfull parameters.
   * @param strBasePath - базовый путь
   */
  public void init(String strBasePath)
  {
    if (strBasePath == null || strBasePath.trim().length() <= 0)
    {
      log.error("base path is empty");
    }
    else
      synchronized (configMutex)
      {
        strPathBegin = strBasePath;
      }
  }

  public void addCfgFile(String sCfgFileName)
  {
    try
    {
      synchronized (configMutex)
      {
        readFile(sCfgFileName, values);
        cfgFiles.add(sCfgFileName);
      }
      log.info("Read config file: " + sCfgFileName);
    }
    catch (Exception e)
    {
      log.error("Error while reading " + sCfgFileName + " : " + e.getMessage(), e);
    }
  }

  private void readFile(String sCfgFileName, Properties properties) throws Exception
  {
    FileInputStream inStream = new FileInputStream(sCfgFileName);
    properties.load(inStream);
    inStream.close();
  }

  public String replaceHome(String str)
  {
    StringBuffer strWork = new StringBuffer(str);
    int indexPar = str.indexOf(HOME);
    if (indexPar >= 0)
    {
      strWork.replace(indexPar, indexPar + HOME.length(), strPathBegin);
      str = strWork.toString();
    }
    return str;
  }

  public Properties readPersistConfig()
  {
    Properties dbProperties = new Properties();
    VDbConnection conn = null;
    try
    {
      conn = VDbConnectionManager.getVDbConnection();
      String sql = "\nselect\n" +
              "  stn_id as \"id\",\n" +
              "  stn_name as \"name\",\n" +
              "  stn_description as \"description\",\n" +
              "  stn_value as \"value\"\n" +
              "from dcl_setting\n";
      VResultSet resultSet = DAOUtils.executeQuery(conn, sql, null, new VParameter());
      while (resultSet.next())
      {
        dbProperties.setProperty(resultSet.getData("name"), resultSet.getData("value"));
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
    return dbProperties;
  }

  private boolean isExistInDB(String name)
  {
    VDbConnection conn = null;
    try
    {
      conn = VDbConnectionManager.getVDbConnection();
      String sql = "select stn_id\n" +
              "    from dcl_setting\n" +
              "    where stn_name = :name";
      VParameter parans = new VParameter();
      parans.add("name", name, Types.VARCHAR);
      VResultSet resultSet = DAOUtils.executeQuery(conn, sql, null, parans);
      if ( resultSet.next() )
      {
        return true;
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
    
    return false;
  }

  private void update(String name, String value)
  {
    VDbConnection conn = null;
    try
    {
      conn = VDbConnectionManager.getVDbConnection();
      String sql = "update dcl_setting set\n" +
              "       stn_value = :value\n" +
              "     where stn_name = :name";

      VParameter parans = new VParameter();
      parans.add("value", value, Types.VARCHAR);
      parans.add("name", name, Types.VARCHAR);
      DAOUtils.update(conn, sql, null, parans);
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
  }

  private void insert(String name, String value)
  {
    VDbConnection conn = null;
    try
    {
      conn = VDbConnectionManager.getVDbConnection();
      String sql = "insert\n" +
              "     into dcl_setting (\n" +
              "       stn_name,\n" +
              "       stn_value\n" +
              "     ) values ( \n" +
              "       :name, \n" +
              "       :value \n" +
              "     )";

      VParameter parameters = new VParameter();
      parameters.add("name", name, Types.VARCHAR);
      parameters.add("value", value, Types.VARCHAR);
      DAOUtils.update(conn, sql, null, parameters);
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
  }

  public void clear()
  {
    values.clear();
    cfgFiles.clear();
    config = null;
  }

}
