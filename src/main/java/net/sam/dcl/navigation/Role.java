package net.sam.dcl.navigation;

/**
 * User: Dima
 * Date: Mar 22, 2005
 * Time: 12:38:08 PM
 */
public class Role
{

  int role_id = -1;

  public Role(int role)
  {
    this.role_id = role;
  }

  public int getRole_id()
  {
    return role_id;
  }

  public void setRole_id(int role_id)
  {
    this.role_id = role_id;
  }

  public static class ADMIN extends Role
  {
    public ADMIN()
    {
      super(UserRoles.ROLE_ADMIN);
    }
  }

  public static class MANAGER extends Role
  {
    public MANAGER()
    {
      super(UserRoles.ROLE_MANAGER);
    }
  }

  public static class DECLARANT extends Role
  {
    public DECLARANT()
    {
      super(UserRoles.ROLE_DECLARANT);
    }
  }

  public static class ECONOMIST extends Role
  {
    public ECONOMIST()
    {
      super(UserRoles.ROLE_ECONOMIST);
    }
  }

  public static class LAWYER extends Role
  {
    public LAWYER()
    {
      super(UserRoles.ROLE_LAWYER);
    }
  }

  public static class USER_IN_LITHUANIA extends Role
  {
    public USER_IN_LITHUANIA()
    {
      super(UserRoles.ROLE_USER_IN_LITHUANIA);
    }
  }

  public static class OTHER_USER_IN_MINSK extends Role
  {
    public OTHER_USER_IN_MINSK()
    {
      super(UserRoles.OTHER_USER_IN_MINSK);
    }
  }

  public static class STAFF_OF_SERVICE extends Role
  {
    public STAFF_OF_SERVICE()
    {
      super(UserRoles.STAFF_OF_SERVICE);
    }
  }

  public static class LOGISTIC extends Role
  {
    public LOGISTIC()
    {
      super(UserRoles.LOGISTIC);
    }
  }
}
