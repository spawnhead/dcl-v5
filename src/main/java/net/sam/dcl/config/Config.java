package net.sam.dcl.config;

import net.sam.dcl.db.VDbConnection;

import java.util.Properties;

public class Config
{
  static private IConfig config = null;


  // ************************************************************************ //
  /**
   * The private constructor.
   */
  private Config()
  {
  }

  // ************************************************************************ //
  /**
   * The method initializes the needfull parameters.
   * @param iConfig - реализация
   */
  public static void init(IConfig iConfig)
  {
    config = iConfig;
  }


  /**
   * The method returns the values of the paroperty of the section.
   * @param section - секция
   * @param name - имя параметра
   * @return - values of the paroperty of the section.
   */
  public static String getString(String section, String name)
  {
    return config.getString(section, name);
  }

  /**
   * The method returns the values of the paroperty of the section.
   * @param name - имя параметра
   * @return - values of the paroperty of the section.
   */
  public static String getString(String name)
  {
    return config.getString(name);
  }

  // ************************************************************************ //
  /**
   * The method set the value of the paroperty of the section.
   * @param name - имя параметра
   * @param value - значение параметра
   * @param toDB - писать ли БД
   */
  public static void setString(String name, String value, boolean toDB)
  {
    config.setString(name, value, toDB);
  }

  public static int getNumber(String section, String name, int defaultValue)
  {
    return config.getNumber(section, name, defaultValue);
  }

  public static int getNumber(String name, int defaultValue)
  {
    return config.getNumber(name, defaultValue);
  }

  public static boolean haveNumber(String name)
  {
    return config.haveNumber(name);
  }

  public static float getFloat(String name, float defaultValue)
  {
    return config.getFloat(name, defaultValue);
  }

  public static boolean getBoolean(String section, String name, boolean defaultValue)
  {
    return config.getBoolean(section, name, defaultValue);
  }

  public static boolean getBoolean(String name, boolean defaultValue)
  {
    return config.getBoolean(name, defaultValue);
  }

  public static void addProperties(Properties properties)
  {
    config.addProperties(properties);
  }

  public static void reload()
  {
    config.reload();
  }


  // ************************************************************************ //
  /**
   * The method returns the properties of the current section and their values.
   * @param section - имя секции
   * @return - the properties of the current section and their values
   */
  public static Properties getSection(String section)
  {
    return config.getSection(section);
  }

  public static Properties getConfig()
  {
    return config.getConfig();
  }

  static public void save(VDbConnection conn, String key, String value) throws Exception
  {
    config.save(conn, key, value);
  }

  public static void clear()
  {
    config.clear();
    config = null;
  }

}