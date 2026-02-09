package net.sam.dcl.form;

import net.sam.dcl.beans.Country;
import net.sam.dcl.beans.Reputation;
import net.sam.dcl.beans.User;
import net.sam.dcl.beans.ContractorUser;
import net.sam.dcl.controller.BaseDispatchValidatorForm;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.controller.ActionContext;
import net.sam.dcl.taglib.table.model.impl.HolderImplUsingList;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.UserUtil;

/**
 * @author: DG
 * Date: Aug 6, 2005
 * Time: 5:37:29 PM
 */
public class ContractorForm extends BaseDispatchValidatorForm
{
  public static String usersContractorPanel = "usersContractor";
  public static String accountsContractorPanel = "accountsContractor";
  public static String contactPersonContractorPanel = "contactPersonsContractor";
  public static String commentContractorPanel = "commentContractor";

  String is_new_doc = "true";

  String ctr_id;

  //первая панель
  User createUser = new User();
  User editUser = new User();
  String usr_date_create;
  String usr_date_edit;

  String ctr_name;
  String ctr_full_name;
  Country country = new Country();
  String ctr_index;
  String ctr_region;
  String ctr_place;
  String ctr_street;
  String ctr_building;
  String ctr_add_info;
  String ctr_phone;
  String ctr_fax;
  String ctr_email;
  String ctr_bank_props;
  String ctr_unp;
  String ctr_okpo;
  String ctr_block;
  Reputation reputation = new Reputation();

  //вторая панель - пользователи
  String number;
  String lastNumber;
  HolderImplUsingList gridUsers = new HolderImplUsingList();

  //третья панель - счета
  String numberAcc;
  String lastNumberAcc;
  HolderImplUsingList gridAccounts = new HolderImplUsingList();

  //четвертая панель - контактные лица
  String cps_block;
  String cps_fire;
  HolderImplUsingList gridContactPersons = new HolderImplUsingList();

	//пятая панель - комментарии
	String ctr_comment;

  boolean readOnlyReputation = false;
  boolean readOnlyComment = false;
  boolean readOnlyIfNotAdmin = true;
  boolean formReadOnly = false;
  boolean adminRole = false;

  public String getIs_new_doc()
  {
    return is_new_doc;
  }

  public void setIs_new_doc(String is_new_doc)
  {
    this.is_new_doc = is_new_doc;
  }

  public String getCtr_id()
  {
    return ctr_id;
  }

  public void setCtr_id(String ctr_id)
  {
    this.ctr_id = ctr_id;
  }

  public User getCreateUser()
  {
    return createUser;
  }

  public void setCreateUser(User createUser)
  {
    this.createUser = createUser;
  }

  public User getEditUser()
  {
    return editUser;
  }

  public void setEditUser(User editUser)
  {
    this.editUser = editUser;
  }

  public String getUsr_date_create()
  {
    return usr_date_create;
  }

  public void setUsr_date_create(String usr_date_create)
  {
    this.usr_date_create = usr_date_create;
  }

  public String getUsr_date_edit()
  {
    return usr_date_edit;
  }

  public void setUsr_date_edit(String usr_date_edit)
  {
    this.usr_date_edit = usr_date_edit;
  }

  public String getCtr_name()
  {
    return ctr_name;
  }

  public void setCtr_name(String ctr_name)
  {
    this.ctr_name = ctr_name;
  }

  public String getCtr_full_name()
  {
    return ctr_full_name;
  }

  public void setCtr_full_name(String ctr_full_name)
  {
    this.ctr_full_name = ctr_full_name;
  }

  public Country getCountry()
  {
    return country;
  }

  public void setCountry(Country country)
  {
    this.country = country;
  }

  public String getCtr_address()
  {
    String retStr = "";
    retStr += StringUtil.isEmpty(getCtr_index()) ? "" : getCtr_index() + ", ";
    retStr += StringUtil.isEmpty(getCtr_region()) ? "" : getCtr_region() + ", ";
    retStr += StringUtil.isEmpty(getCtr_place()) ? "" : getCtr_place() + ", ";
    retStr += StringUtil.isEmpty(getCtr_street()) ? "" : getCtr_street() + ", ";
    retStr += StringUtil.isEmpty(getCtr_building()) ? "" : getCtr_building() + ", ";
    retStr += StringUtil.isEmpty(getCtr_add_info()) ? "" : getCtr_add_info();
    if (StringUtil.isEmpty(getCtr_add_info()) && !StringUtil.isEmpty(retStr))
    {
      retStr = retStr.substring(0, retStr.length() - 2);
    }
    return retStr;
  }

  public String getCtr_index()
  {
    return ctr_index;
  }

  public void setCtr_index(String ctr_index)
  {
    this.ctr_index = ctr_index;
  }

  public String getCtr_region()
  {
    return ctr_region;
  }

  public void setCtr_region(String ctr_region)
  {
    this.ctr_region = ctr_region;
  }

  public String getCtr_place()
  {
    return ctr_place;
  }

  public void setCtr_place(String ctr_place)
  {
    this.ctr_place = ctr_place;
  }

  public String getCtr_street()
  {
    return ctr_street;
  }

  public void setCtr_street(String ctr_street)
  {
    this.ctr_street = ctr_street;
  }

  public String getCtr_building()
  {
    return ctr_building;
  }

  public void setCtr_building(String ctr_building)
  {
    this.ctr_building = ctr_building;
  }

  public String getCtr_add_info()
  {
    return ctr_add_info;
  }

  public void setCtr_add_info(String ctr_add_info)
  {
    this.ctr_add_info = ctr_add_info;
  }

  public String getCtr_phone()
  {
    return ctr_phone;
  }

  public void setCtr_phone(String ctr_phone)
  {
    this.ctr_phone = ctr_phone;
  }

  public String getCtr_fax()
  {
    return ctr_fax;
  }

  public void setCtr_fax(String ctr_fax)
  {
    this.ctr_fax = ctr_fax;
  }

  public String getCtr_email()
  {
    return ctr_email;
  }

  public void setCtr_email(String ctr_email)
  {
    this.ctr_email = ctr_email;
  }

  public String getCtr_bank_props()
  {
    return ctr_bank_props;
  }

  public void setCtr_bank_props(String ctr_bank_props)
  {
    this.ctr_bank_props = ctr_bank_props;
  }

  public String getCtr_unp()
  {
    return ctr_unp;
  }

  public void setCtr_unp(String ctr_unp)
  {
    this.ctr_unp = ctr_unp;
  }

  public String getCtr_okpo()
  {
    return ctr_okpo;
  }

  public void setCtr_okpo(String ctr_okpo)
  {
    this.ctr_okpo = ctr_okpo;
  }

  public String getCtr_block()
  {
    return ctr_block;
  }

  public void setCtr_block(String ctr_block)
  {
    this.ctr_block = ctr_block;
  }

  public Reputation getReputation()
  {
    return reputation;
  }

  public void setReputation(Reputation reputation)
  {
    this.reputation = reputation;
  }

  public String getNumber()
  {
    return number;
  }

  public void setNumber(String number)
  {
    this.number = number;
  }

  public String getLastNumber()
  {
    return lastNumber;
  }

  public void setLastNumber(String lastNumber)
  {
    this.lastNumber = lastNumber;
  }

  public HolderImplUsingList getGridUsers()
  {
    return gridUsers;
  }

  public void setGridUsers(HolderImplUsingList gridUsers)
  {
    this.gridUsers = gridUsers;
  }

  public String getNumberAcc()
  {
    return numberAcc;
  }

  public void setNumberAcc(String numberAcc)
  {
    this.numberAcc = numberAcc;
  }

  public String getLastNumberAcc()
  {
    return lastNumberAcc;
  }

  public void setLastNumberAcc(String lastNumberAcc)
  {
    this.lastNumberAcc = lastNumberAcc;
  }

  public HolderImplUsingList getGridAccounts()
  {
    return gridAccounts;
  }

  public void setGridAccounts(HolderImplUsingList gridAccounts)
  {
    this.gridAccounts = gridAccounts;
  }

  public String getCps_block()
  {
    return cps_block;
  }

  public void setCps_block(String cps_block)
  {
    this.cps_block = cps_block;
  }

  public String getCps_fire()
  {
    return cps_fire;
  }

  public void setCps_fire(String cps_fire)
  {
    this.cps_fire = cps_fire;
  }

  public HolderImplUsingList getGridContactPersons()
  {
    return gridContactPersons;
  }

  public void setGridContactPersons(HolderImplUsingList gridContactPersons)
  {
    this.gridContactPersons = gridContactPersons;
  }

	public String getCtr_comment()
	{
		return ctr_comment;
	}

	public void setCtr_comment(String ctr_comment)
	{
		this.ctr_comment = ctr_comment;
	}

	public boolean isReadOnlyReputation()
  {
    return readOnlyReputation | isFormReadOnly();
  }

  public void setReadOnlyReputation(boolean readOnlyReputation)
  {
    this.readOnlyReputation = readOnlyReputation;
  }

	public boolean isReadOnlyComment()
	{
		return readOnlyComment | isFormReadOnly();
	}

	public void setReadOnlyComment(boolean readOnlyComment)
	{
		this.readOnlyComment = readOnlyComment;
	}

	public boolean isReadOnlyIfNotAdmin()
  {
    return readOnlyIfNotAdmin | isFormReadOnly();
  }

  public void setReadOnlyIfNotAdmin(boolean readOnlyIfNotAdmin)
  {
    this.readOnlyIfNotAdmin = readOnlyIfNotAdmin;
  }

  public boolean isFormReadOnly()
  {
    return formReadOnly;
  }

  public void setFormReadOnly(boolean formReadOnly)
  {
    this.formReadOnly = formReadOnly;
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
    
    for ( int i = 0; i < getGridUsers().getDataList().size(); i++ )
    {
      ContractorUser user = (ContractorUser)getGridUsers().getDataList().get(i);
      if (user.getUser().getUsr_id().equals(currentUser.getUsr_id()))
      {
        return true;
      }
    }

    return false;
  }
}
