package net.sam.dcl.beans;

import net.sam.dcl.navigation.Role;
import net.sam.dcl.util.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Date: Aug 18, 2005
 * Time: 2:31:03 PM
 */
public class User implements Serializable
{
  protected static Log log = LogFactory.getLog(User.class);
  String usr_id;
  String usr_code;
  String usr_name;
  String usr_surname;
  String usr_login;
  String usr_passwd;
  String usr_passwd2;
  String usr_position;
  String usr_phone;
  Department department = new Department();
  String usr_block;
  String usr_respons_person;
  String usr_no_login;
  String usr_chief_dep;
  String usr_fax;
  String usr_email;
  String usr_local_entry;
  String usr_internet_entry;
  transient String ip;
  List<UserLanguage> user_language = new ArrayList<UserLanguage>();

  List<String> roles = new ArrayList<String>();
  private Map<String, Action> actions = new LinkedHashMap<String, Action>();
  private Map<String, UserSetting> userSettings = new LinkedHashMap<String, UserSetting>();
  int userScreenWith;

  public User()
  {
  }

  public User(String usr_id)
  {
    this.usr_id = usr_id;
  }

  public User(Department department)
  {
    this.department = department;
  }

  public User(User user)
  {
    this.usr_id = user.usr_id;
    this.usr_code = user.usr_code;
    this.usr_name = user.usr_name;
    this.usr_surname = user.usr_surname;
    this.usr_login = user.usr_login;
    this.usr_passwd = user.usr_passwd;
    this.usr_passwd2 = user.usr_passwd2;
    this.usr_position = user.usr_position;
    this.usr_phone = user.usr_phone;
    this.usr_block = user.usr_block;
    this.usr_respons_person = user.usr_respons_person;
    this.usr_no_login = user.usr_no_login;
    this.usr_chief_dep = user.usr_chief_dep;
    this.usr_fax = user.usr_fax;
    this.usr_email = user.usr_email;
    this.usr_local_entry = user.usr_local_entry;
    this.usr_internet_entry = user.usr_internet_entry;

    this.ip = user.ip;
    this.department = new Department(user.department);
    this.roles = new ArrayList<String>(user.roles);
    this.actions = new LinkedHashMap<String, Action>(user.actions);
    this.userSettings = new LinkedHashMap<String, UserSetting>(user.userSettings);
    this.user_language = new ArrayList<UserLanguage>(user.user_language);

    this.userScreenWith = user.userScreenWith;
  }

  public String getUsr_id()
  {
    return usr_id;
  }

  public String getUsr_code()
  {
    return usr_code;
  }

  public void setUsr_code(String usr_code)
  {
    this.usr_code = usr_code;
  }

  public String getUsr_name()
  {
    return usr_name;
  }

  public void setUsr_name(String usr_name)
  {
    this.usr_name = usr_name;
  }

  public String getUsr_surname()
  {
    return usr_surname;
  }

  public void setUsr_surname(String usr_surname)
  {
    this.usr_surname = usr_surname;
  }

  public void setUsr_id(String usr_id)
  {
    this.usr_id = usr_id;
  }

  public List<String> getRoles()
  {
    return roles;
  }

  public void setRoles(List<String> roles)
  {
    this.roles = roles;
  }

  public Map<String, Action> getActions()
  {
    return actions;
  }

  public void setActions(Map<String, Action> actions)
  {
    this.actions = actions;
  }

  public Map<String, UserSetting> getUserSettings()
  {
    return userSettings;
  }

  public void setUserSettings(Map<String, UserSetting> userSettings)
  {
    this.userSettings = userSettings;
  }

  public Action getAction(String key)
  {
    return getActions().get(key);
  }

  public UserSetting getUserSetting(String key)
  {
    return getUserSettings().get(key);
  }

  public void setNewSettingValue(String key, String value)
  {
    UserSetting userSetting = getUserSettings().get(key);
    if (userSetting != null)
    {
      userSetting.setUst_value(value);  
    }
  }

  public boolean isHaveAccess(String key)
  {
    if ( isAdmin() ) //админ в любом случае имеет доступ
    {
      return true;
    }

    Action action = getAction(key);
    return ( null != action ); //в списке только те действия к которым пользователь имеет доступ
  }

  public String getUsr_login()
  {
    return usr_login;
  }

  public void setUsr_login(String usr_login)
  {
    this.usr_login = usr_login;
  }

  public String getUsr_passwd()
  {
    return usr_passwd;
  }

  public void setUsr_passwd(String usr_passwd)
  {
    this.usr_passwd = usr_passwd;
  }

  public String getUsr_passwd2()
  {
    return usr_passwd2;
  }

  public void setUsr_passwd2(String usr_passwd2)
  {
    this.usr_passwd2 = usr_passwd2;
  }

  public String getUsr_position()
  {
    return usr_position;
  }

  public void setUsr_position(String usr_position)
  {
    this.usr_position = usr_position;
  }

  public String getUsr_phone()
  {
    return usr_phone;
  }

  public void setUsr_phone(String usr_phone)
  {
    this.usr_phone = usr_phone;
  }

  public String getUsr_block()
  {
    return usr_block;
  }

  public void setUsr_block(String usr_block)
  {
    this.usr_block = usr_block;
  }

  public Department getDepartment()
  {
    return department;
  }

  public void setDepartment(Department department)
  {
    this.department = department;
  }

  public String getUsr_respons_person()
  {
    return usr_respons_person;
  }

  public void setUsr_respons_person(String usr_respons_person)
  {
    this.usr_respons_person = usr_respons_person;
  }

  public String getUsr_no_login()
  {
    return usr_no_login;
  }

  public void setUsr_no_login(String usr_no_login)
  {
    this.usr_no_login = usr_no_login;
  }

  public String getUsr_chief_dep()
  {
    return usr_chief_dep;
  }

  public void setUsr_chief_dep(String usr_chief_dep)
  {
    this.usr_chief_dep = usr_chief_dep;
  }

  public String getUsr_fax()
  {
    return usr_fax;
  }

  public void setUsr_fax(String usr_fax)
  {
    this.usr_fax = usr_fax;
  }

  public String getUsr_email()
  {
    return usr_email;
  }

  public void setUsr_email(String usr_email)
  {
    this.usr_email = usr_email;
  }

  public String getUsr_local_entry()
  {
    return usr_local_entry;
  }

  public void setUsr_local_entry(String usr_local_entry)
  {
    this.usr_local_entry = usr_local_entry;
  }

  public String getUsr_internet_entry()
  {
    return usr_internet_entry;
  }

  public void setUsr_internet_entry(String usr_internet_entry)
  {
    this.usr_internet_entry = usr_internet_entry;
  }

  public List<UserLanguage> getUser_language()
  {
    return user_language;
  }

  public void setUser_language(List<UserLanguage> user_language)
  {
    this.user_language = user_language;
  }

  public String getUserFullName()
  {
    if (StringUtil.isEmpty(usr_name) && StringUtil.isEmpty(usr_surname))
      return "";

    String retStr = "";
    if (!StringUtil.isEmpty(usr_surname))
      retStr += usr_surname;
    retStr += " ";
    if (!StringUtil.isEmpty(usr_name))
      retStr += usr_name;

    return retStr;
  }

  /*
    Use with great care
  */
  public void setUserFullName(String fullName)
  {
    if (!StringUtil.isEmpty(fullName))
    {
      String arr[] = fullName.split(" ");
      if (arr.length > 0)
      {
        usr_surname = arr[0];
      }
      if (arr.length > 1)
      {
        usr_name = arr[1];
      }
      else
      {
        usr_name = "";
      }
    }
    else
    {
      usr_surname = "";
      usr_name = ""; 
    }
  }

  public boolean isUserInRole(Role role)
  {
    for (String userRole : roles)
    {
      if (Integer.parseInt(userRole) == role.getRole_id())
      {
        return true;
      }
    }
    return false;
  }

  public boolean isUserOnlyInOneRole(Role role)
  {
    if (roles.size() > 1 || roles.size() == 0)
    {
      return false;
    }

    String userRole = roles.get(0);
    return Integer.parseInt(userRole) == role.getRole_id();
  }

  public boolean isChiefDepartment()
  {
    return "1".equals(getUsr_chief_dep());
  }

  public boolean isAdmin()
  {
    return isUserInRole(new Role.ADMIN());
  }

  public boolean isEconomist()
  {
    return isUserInRole(new Role.ECONOMIST());
  }

  public boolean isManager()
  {
    return isUserInRole(new Role.MANAGER());
  }

  public boolean isDeclarant()
  {
    return isUserInRole(new Role.DECLARANT());
  }

  public boolean isLawyer()
  {
    return isUserInRole(new Role.LAWYER());
  }

  public boolean isUserInLithuania()
  {
    return isUserInRole(new Role.USER_IN_LITHUANIA());
  }

  public boolean isOtherUserInMinsk()
  {
    return isUserInRole(new Role.OTHER_USER_IN_MINSK());
  }

  public boolean isStaffOfService()
  {
    return isUserInRole(new Role.STAFF_OF_SERVICE());
  }

  public boolean isLogistic()
  {
    return isUserInRole(new Role.LOGISTIC());
  }

  public boolean isOnlyAdmin()
  {
    return isUserOnlyInOneRole(new Role.ADMIN());
  }

  public boolean isOnlyEconomist()
  {
    return isUserOnlyInOneRole(new Role.ECONOMIST());
  }

  public boolean isOnlyManager()
  {
    return isUserOnlyInOneRole(new Role.MANAGER());
  }

  public boolean isOnlyDeclarant()
  {
    return isUserOnlyInOneRole(new Role.DECLARANT());
  }

  public boolean isOnlyLawyer()
  {
    return isUserOnlyInOneRole(new Role.LAWYER());
  }

  public boolean isOnlyUserInLithuania()
  {
    return isUserOnlyInOneRole(new Role.USER_IN_LITHUANIA());
  }

  public boolean isOnlyOtherUserInMinsk()
  {
    return isUserOnlyInOneRole(new Role.OTHER_USER_IN_MINSK());
  }

  public boolean isOnlyStaffOfService()
  {
    return isUserOnlyInOneRole(new Role.STAFF_OF_SERVICE());
  }

  public boolean isOnlyLogistic()
  {
    return isUserOnlyInOneRole(new Role.LOGISTIC());
  }

  public String userRoles()
  {
    String ret = "";
    if (isAdmin())
    {
      ret += "Admin ";
    }
    if (isEconomist())
    {
      ret += "Economist ";
    }
    if (isManager())
    {
      ret += "Manager ";
    }
    if (isDeclarant())
    {
      ret += "Declarant ";
    }
    if (isLawyer())
    {
      ret += "Lawyer ";
    }
    if (isUserInLithuania())
    {
      ret += "User in Lithuania ";
    }
    if (isOtherUserInMinsk())
    {
      ret += "Other user in Minsk ";
    }
    if (isStaffOfService())
    {
      ret += "Staff of service ";
    }
    if (isLogistic())
    {
      ret += "Logistic ";
    }
    return ret;
  }

  public String getIp()
  {
    return ip;
  }

  public void setIp(String ip)
  {
    this.ip = ip;
  }

  public int getUserScreenWith()
  {
    return userScreenWith;
  }

  public void setUserScreenWith(int userScreenWith)
  {
    this.userScreenWith = userScreenWith;
  }
}
