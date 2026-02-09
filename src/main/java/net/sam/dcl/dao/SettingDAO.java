package net.sam.dcl.dao;

import net.sam.dcl.beans.Setting;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.controller.IActionContext;

public class SettingDAO
{

  public static Setting load(IActionContext context, String id) throws Exception
  {
    Setting setting = new Setting(id);

    if (load(context, setting))
    {
      return setting;
    }
    throw new LoadException(setting, id);
  }

  public static boolean load(IActionContext context, Setting setting) throws Exception
  {
    return DAOUtils.load(context, "setting-load", setting, null);
  }

  public static void save(IActionContext context, Setting setting) throws Exception
  {
    DAOUtils.update(context.getConnection(), context.getSqlResource().get("setting-update"), setting, null);
  }
}