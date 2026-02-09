package net.sam.dcl.form;

import net.sam.dcl.beans.ContactPersonUser;
import net.sam.dcl.beans.User;
import net.sam.dcl.controller.ActionContext;
import net.sam.dcl.controller.BaseDispatchValidatorForm;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.taglib.table.model.impl.HolderImplUsingList;
import net.sam.dcl.util.UserUtil;

public class ContactPersonForm extends BaseDispatchValidatorForm
{
  public static String usersContactPersonPanel = "usersContactPerson";

  String cps_id;
  String ctr_id;
  String cps_name;
  String cps_position;
  String cps_on_reason;
  String cps_phone;
  String cps_mob_phone;
  String cps_fax;
  String cps_email;
  String cps_block;
  String cps_contract_comment;
  String cps_fire;

  String number;
  String fromContractor = "";

  //вторая панель - пользователи
  String numberUser;
  String lastNumberUser;
  HolderImplUsingList gridContactPersonUsers = new HolderImplUsingList();

  boolean adminRole = false;

  public String getCps_id()
  {
    return cps_id;
  }

  public void setCps_id(String cps_id)
  {
    this.cps_id = cps_id;
  }

  public String getCtr_id()
  {
    return ctr_id;
  }

  public void setCtr_id(String ctr_id)
  {
    this.ctr_id = ctr_id;
  }

  public String getCps_name()
  {
    return cps_name;
  }

  public void setCps_name(String cps_name)
  {
    this.cps_name = cps_name;
  }

  public String getCps_position()
  {
    return cps_position;
  }

  public void setCps_position(String cps_position)
  {
    this.cps_position = cps_position;
  }

  public String getCps_on_reason()
  {
    return cps_on_reason;
  }

  public void setCps_on_reason(String cps_on_reason)
  {
    this.cps_on_reason = cps_on_reason;
  }

  public String getCps_phone()
  {
    return cps_phone;
  }

  public void setCps_phone(String cps_phone)
  {
    this.cps_phone = cps_phone;
  }

  public String getCps_mob_phone()
  {
    return cps_mob_phone;
  }

  public void setCps_mob_phone(String cps_mob_phone)
  {
    this.cps_mob_phone = cps_mob_phone;
  }

  public String getCps_fax()
  {
    return cps_fax;
  }

  public void setCps_fax(String cps_fax)
  {
    this.cps_fax = cps_fax;
  }

  public String getCps_email()
  {
    return cps_email;
  }

  public void setCps_email(String cps_email)
  {
    this.cps_email = cps_email;
  }

  public String getCps_block()
  {
    return cps_block;
  }

  public void setCps_block(String cps_block)
  {
    this.cps_block = cps_block;
  }

  public String getCps_contract_comment()
  {
    return cps_contract_comment;
  }

  public void setCps_contract_comment(String cps_contract_comment)
  {
    this.cps_contract_comment = cps_contract_comment;
  }

  public String getCps_fire()
  {
    return cps_fire;
  }

  public void setCps_fire(String cps_fire)
  {
    this.cps_fire = cps_fire;
  }

  public String getNumber()
  {
    return number;
  }

  public void setNumber(String number)
  {
    this.number = number;
  }

  public String getFromContractor()
  {
    return fromContractor;
  }

  public void setFromContractor(String fromContractor)
  {
    this.fromContractor = fromContractor;
  }

  public String getNumberUser()
  {
    return numberUser;
  }

  public void setNumberUser(String numberUser)
  {
    this.numberUser = numberUser;
  }

  public String getLastNumberUser()
  {
    return lastNumberUser;
  }

  public void setLastNumberUser(String lastNumberUser)
  {
    this.lastNumberUser = lastNumberUser;
  }

  public HolderImplUsingList getGridContactPersonUsers()
  {
    return gridContactPersonUsers;
  }

  public void setGridContactPersonUsers(HolderImplUsingList gridContactPersonUsers)
  {
    this.gridContactPersonUsers = gridContactPersonUsers;
  }

  public boolean isAdminRole()
  {
    return adminRole;
  }

  public void setAdminRole(boolean adminRole)
  {
    this.adminRole = adminRole;
  }

  public boolean isAddUserReadOnly()
  {
    if (isAdminRole())
      return false;

    IActionContext context = ActionContext.threadInstance();
    User currentUser = UserUtil.getCurrentUser(context.getRequest());

    for ( int i = 0; i < getGridContactPersonUsers().getDataList().size(); i++ )
    {
      ContactPersonUser user = (ContactPersonUser)getGridContactPersonUsers().getDataList().get(i);
      if (user.getUser().getUsr_id().equals(currentUser.getUsr_id()))
      {
        return true;
      }
    }

    return false;
  }
}
