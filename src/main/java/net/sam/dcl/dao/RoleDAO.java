package net.sam.dcl.dao;

import net.sam.dcl.beans.Role;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.controller.IActionContext;

/**
 * Date: Sep 11, 2005
 * Time: 2:10:04 PM
 */
public class RoleDAO
{

  public static Role load(IActionContext context, String id) throws Exception
  {
    Role role = new Role(id);
    if (load(context, role))
    {
      return role;
    }
    throw new LoadException(role, id);
  }

  public static boolean load(IActionContext context, Role Role) throws Exception
  {
    return (DAOUtils.load(context, "dao-load-role", Role, null));
  }
}
