package net.sam.dcl.dao;

import net.sam.dcl.beans.Department;
import net.sam.dcl.util.DAOUtils;
import net.sam.dcl.controller.IActionContext;

/**
 * @author: DG
 * Date: Sep 11, 2005
 * Time: 2:10:04 PM
 */
public class DepartmentDAO
{
  public static Department load(IActionContext context, String id) throws Exception
  {
    Department department = new Department(id);
    if (load(context, department))
    {
      return department;
    }
    throw new LoadException(department, id);
  }

  public static boolean load(IActionContext context, Department department) throws Exception
  {
    return (DAOUtils.load(context, "dao-load-department", department, null));
  }

  public static Department loadByName(IActionContext context, String id, String name) throws Exception
  {
    Department department = new Department(id, name);
    if (loadByName(context, department))
    {
      return department;
    }
    throw new LoadException(department, id);
  }

  public static boolean loadByName(IActionContext context, Department department) throws Exception
  {
    return (DAOUtils.load(context, "dao-load-department_by_name", department, null));
  }
}
