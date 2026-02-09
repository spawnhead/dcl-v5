package net.sam.dcl.dao;

import net.sam.dcl.beans.UserSetting;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.controller.IActionContext;

public class UserSettingDAO
{

  public static UserSetting load(IActionContext context, String id) throws Exception
  {
    UserSetting userSetting = new UserSetting(id);

    if (load(context, userSetting))
    {
      return userSetting;
    }
    throw new LoadException(userSetting, id);
  }

  public static boolean load(IActionContext context, UserSetting userSetting) throws Exception
  {
    return DAOUtils.load(context, "user_setting-load", userSetting, null);
  }

  public static void save(IActionContext context, UserSetting userSetting) throws Exception
  {
    DAOUtils.update(context.getConnection(), context.getSqlResource().get("user_setting-update"), userSetting, null);
  }
}