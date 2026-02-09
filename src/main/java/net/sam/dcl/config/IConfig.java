package net.sam.dcl.config;

import net.sam.dcl.db.VDbConnection;

import java.util.Properties;

/**
 * User: Dima
 * Date: Feb 16, 2005
 * Time: 1:28:45 PM
 */
public interface IConfig {
  String getString(String section, String name);
  String getString(String name);
  void setString(String name, String value, boolean toDB);

  int getNumber(String section, String name, int defaultValue);
  int getNumber(String name, int defaultValue);
  boolean haveNumber(String name);

  boolean getBoolean(String section, String name, boolean defaultValue);
  boolean getBoolean(String name, boolean defaultValue);

  Properties getSection(String section);
  Properties getConfig();

  void addProperties(Properties properties);
  void save(VDbConnection conn, String key, String value) throws Exception;

  void reload();

	void clear();

	float getFloat(String name, float defaultValue);
}
