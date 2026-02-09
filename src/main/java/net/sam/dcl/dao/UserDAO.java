package net.sam.dcl.dao;

import net.sam.dcl.beans.Department;
import net.sam.dcl.beans.User;
import net.sam.dcl.beans.UserLanguage;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.db.VResultSet;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.util.StringUtil;

import java.util.List;

/**
 * @author: DG
 * Date: Sep 11, 2005
 * Time: 2:10:04 PM
 */
public class UserDAO
{
  public static User load(IActionContext context, String id) throws Exception
  {
    User user = new User(id);
    if (load(context, user))
    {
      loadUserLanguage(context, user);
      return user;
    }
    throw new LoadException(user, id);
  }

  public static User loadWithRoles(IActionContext context, String id) throws Exception
  {
    User user = new User(id);
    if (load(context, user))
    {
      VResultSet resultSet = DAOUtils.executeQuery(context.getConnection(), context.getSqlResource().get("select-user-roles"), user, null);
      List<String> roles = DAOUtils.resultSet2StringList(resultSet);
      user.setRoles(roles);
      return user;
    }
    throw new LoadException(user, id);
  }

  public static boolean load(IActionContext context, User user) throws Exception
  {
    if (DAOUtils.load(context, "dao-load-user", user, null))
    {
      if (StringUtil.isEmpty(user.getDepartment().getId()))
      {
        return true;
      }
      else
      {
        return DepartmentDAO.load(context, user.getDepartment());
      }
    }
    return false;
  }

  public static User loadUserByDepartNameChief(IActionContext context, Department department) throws Exception
  {
    User user = new User(department);
    if (loadUserByDepartNameChief(context, user))
    {
      loadUserLanguage(context, user);
      return user;
    }
    throw new LoadException(user, department.getName());
  }

  public static boolean loadUserByDepartNameChief(IActionContext context, User user) throws Exception
  {
    if (DAOUtils.load(context, "dao-load-user-by_dep_name_chief", user, null))
    {
      if (StringUtil.isEmpty(user.getDepartment().getId()))
      {
        return true;
      }
      else
      {
        return DepartmentDAO.load(context, user.getDepartment());
      }
    }
    return false;
  }

  public static boolean loadUserByCode(IActionContext context, User user) throws Exception
  {
    if (DAOUtils.load(context, "dao-load-user-by_code", user, null))
    {
      if (StringUtil.isEmpty(user.getDepartment().getId()))
      {
        return true;
      }
      else
      {
        return DepartmentDAO.load(context, user.getDepartment());
      }
    }

    return false;
  }

  public static boolean loadUserByName(IActionContext context, User user) throws Exception
  {
    if (DAOUtils.load(context, "dao-load-user-by_name", user, null))
    {
      if (StringUtil.isEmpty(user.getDepartment().getId()))
      {
        return true;
      }
      else
      {
        return DepartmentDAO.load(context, user.getDepartment());
      }
    }

    return false;
  }

  public static void loadUserLanguage(IActionContext context, User user) throws Exception
  {
    List<UserLanguage> lst = DAOUtils.fillList(context, "select-user_language-dao", user, UserLanguage.class, null, null);
    for (UserLanguage userLanguage : lst)
    {
      if (!StringUtil.isEmpty(userLanguage.getLanguage().getId()))
      {
        LanguageDAO.load(context, userLanguage.getLanguage());
      }
    }
    user.setUser_language(lst);
  }


  public static void saveBlock(IActionContext context, User user) throws Exception
  {
    DAOUtils.update(context.getConnection(), context.getSqlResource().get("user-update-block"), user, null);
  }
}
