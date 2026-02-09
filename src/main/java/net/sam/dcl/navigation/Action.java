package net.sam.dcl.navigation;

import java.util.List;
import java.util.ArrayList;

/**
 * User: Dima
 * Date: Mar 22, 2005
 * Time: 12:37:38 PM
 */
public class Action
{
  String name = null;
  List roles = new ArrayList();

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public void addRole(Role role)
  {
    roles.add(role);
  }

  public boolean CorrectRole(List available_roles)
  {
    for (int i = 0; i < available_roles.size(); i++)
    {
      for (int j = 0; j < roles.size(); j++)
      {
        Role role = (Role) roles.get(j);
        String available_role = (String) available_roles.get(i);
        int role_id = Integer.parseInt(available_role);
        if (role.getRole_id() == role_id)
          return true;
      }
    }
    return false;
  }
}
