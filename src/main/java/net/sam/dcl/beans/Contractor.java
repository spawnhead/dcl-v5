package net.sam.dcl.beans;

import net.sam.dcl.controller.ActionContext;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.StrutsUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: DG
 * Date: Aug 23, 2005
 * Time: 8:40:39 PM
 */
public class Contractor implements Serializable
{
  protected static Log log = LogFactory.getLog(Contractor.class);

  public static String currentContractorId = "currentContractorId";
  public static String oldContractorId = "oldContractorId";
  public static String newContractorId = "newContractorId";

  String id;
  String name;
  String fullname;
  Country country = new Country();
  String index;
  String region;
  String place;
  String street;
  String building;
  String addInfo;
  String phone;
  String fax;
  String email;
  String bank_props;
  String account1;
  String account2;
  String accountVal;
  String unp;
  String okpo;
  String block;
  String doubleAccount;
  String accountsBYN;
  String accountsOther;
  List<ContactPerson> contactPersons = new ArrayList<ContactPerson>();
  List<Account> accounts = new ArrayList<Account>();
  List<ContractorUser> users = new ArrayList<ContractorUser>();
  Reputation reputation = new Reputation();

  String ctrNames;

  String is_new_doc;
  User createUser = new User();
  User editUser = new User();
  String usr_date_create;
  String usr_date_edit;
  String account;

  String comment;

  public Contractor()
  {
    this.id = "";
    this.name = "";
  }

  public Contractor(String id)
  {
    this.id = id;
  }

  public Contractor(String id, String unp)
  {
    this.id = id;
    this.unp = unp;
  }

  public Contractor(String id, String unp, String account)
  {
    this.id = id;
    this.unp = unp;
    this.account = account;
  }

  public Contractor(Contractor contractor)
  {
    id = contractor.getId();
    name = contractor.getName();
    fullname = contractor.getFullname();
    country = new Country(contractor.getCountry());

    index = contractor.getIndex(); 
    region = contractor.getRegion();
    place = contractor.getPlace();
    street = contractor.getStreet();
    building = contractor.getBuilding();
    addInfo = contractor.getAddInfo(); 
    phone = contractor.getPhone();
    fax = contractor.getFax();
    email = contractor.getEmail();
    bank_props = contractor.getBank_props();
    account1 = contractor.getAccount1();
    account2 = contractor.getAccount2();
    accountVal = contractor.getAccountVal();
    unp = contractor.getUnp();
    okpo = contractor.getOkpo();
    block = contractor.getBlock();
    doubleAccount = contractor.getDoubleAccount();
    accountsBYN = contractor.getAccountsBYN();
    accountsOther = contractor.getAccountsOther();
    contactPersons = contractor.getContactPersons();
    users = contractor.getUsers();
    accounts = contractor.getAccounts();
    reputation = new Reputation(contractor.getReputation());

    ctrNames = contractor.getCtrNames();
    account = contractor.getAccount();

    comment = contractor.getComment();
  }

  public String getId()
  {
    return id;
  }

  public void setId(String id)
  {
    this.id = id;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public String getFullname()
  {
    return fullname;
  }

  public void setFullname(String fullname)
  {
    this.fullname = fullname;
  }

  public Country getCountry()
  {
    return country;
  }

  public void setCountry(Country country)
  {
    this.country = country;
  }

  public String getAddress()
  {
    String retStr = "";
    retStr += StringUtil.isEmpty(getIndex()) ? "" : getIndex() + ", ";
    retStr += StringUtil.isEmpty(getRegion()) ? "" : getRegion() + ", ";
    retStr += StringUtil.isEmpty(getPlace()) ? "" : getPlace() + ", ";
    retStr += StringUtil.isEmpty(getStreet()) ? "" : getStreet() + ", ";
    retStr += StringUtil.isEmpty(getBuilding()) ? "" : getBuilding() + ", ";
    retStr += StringUtil.isEmpty(getAddInfo()) ? "" : getAddInfo();
    if (StringUtil.isEmpty(getAddInfo()) && !StringUtil.isEmpty(retStr))
    {
      retStr = retStr.substring(0, retStr.length() - 2);
    }
    return retStr;
  }

  public String getIndex()
  {
    return index;
  }

  public void setIndex(String index)
  {
    this.index = index;
  }

  public String getRegion()
  {
    return region;
  }

  public void setRegion(String region)
  {
    this.region = region;
  }

  public String getPlace()
  {
    return place;
  }

  public void setPlace(String place)
  {
    this.place = place;
  }

  public String getStreet()
  {
    return street;
  }

  public void setStreet(String street)
  {
    this.street = street;
  }

  public String getBuilding()
  {
    return building;
  }

  public void setBuilding(String building)
  {
    this.building = building;
  }

  public String getAddInfo()
  {
    return addInfo;
  }

  public void setAddInfo(String addInfo)
  {
    this.addInfo = addInfo;
  }

  public String getPhone()
  {
    return phone;
  }

  public void setPhone(String phone)
  {
    this.phone = phone;
  }

  public String getFax()
  {
    return fax;
  }

  public void setFax(String fax)
  {
    this.fax = fax;
  }

  public String getEmail()
  {
    return email;
  }

  public void setEmail(String email)
  {
    this.email = email;
  }

  public String getBank_props()
  {
    return bank_props;
  }

  public void setBank_props(String bank_props)
  {
    this.bank_props = bank_props;
  }

  public String getAccount1()
  {
    return account1;
  }

  public void setAccount1(String account1)
  {
    this.account1 = account1;
  }

  public String getAccount2()
  {
    return account2;
  }

  public void setAccount2(String account2)
  {
    this.account2 = account2;
  }

  public String getAccountVal()
  {
    return accountVal;
  }

  public void setAccountVal(String accountVal)
  {
    this.accountVal = accountVal;
  }

  public String getUnp()
  {
    return unp;
  }

  public String getUnpFormatted()
  {
    if ( StringUtil.isEmpty(getUnp()) )
    {
      IActionContext context = ActionContext.threadInstance();
      try
      {
        return StrutsUtil.getMessage(context, "Contractor.noContractorData");
      }
      catch (Exception e)
      {
        log.error(e);
      }
    }

    return getUnp();
  }

  public void setUnp(String unp)
  {
    this.unp = unp;
  }

  public String getOkpo()
  {
    return okpo;
  }

  public String getOkpoFormatted()
  {
    if ( StringUtil.isEmpty(getOkpo()) )
    {
      IActionContext context = ActionContext.threadInstance();
      try
      {
        return StrutsUtil.getMessage(context, "Contractor.noContractorData");
      }
      catch (Exception e)
      {
        log.error(e);
      }
    }

    return getOkpo();
  }

  public void setOkpo(String okpo)
  {
    this.okpo = okpo;
  }

  public String getBlock()
  {
    return block;
  }

  public void setBlock(String block)
  {
    this.block = block;
  }

  public String getDoubleAccount()
  {
    return doubleAccount;
  }

  public void setDoubleAccount(String doubleAccount)
  {
    this.doubleAccount = doubleAccount;
  }

  public String getAccountsBYN()
  {
    return accountsBYN;
  }

  public String getAccountsBYNFormatted()
  {
    String retStr = accountsBYN;
    if ( !StringUtil.isEmpty(retStr) )
    {
      retStr = retStr.substring(0, retStr.length() - 2);
    }
    else
    {
      IActionContext context = ActionContext.threadInstance();
      try
      {
        retStr = StrutsUtil.getMessage(context, "Contractor.noContractorData");
      }
      catch (Exception e)
      {
        log.error(e);
      }
    }

    return retStr;
  }

  public void setAccountsBYN(String accountsBYN)
  {
    this.accountsBYN = accountsBYN;
  }

  public String getAccountsOther()
  {
    return accountsOther;
  }

  public String getAccountsOtherFormatted()
  {
    String retStr = accountsOther;
    if ( !StringUtil.isEmpty(retStr) )
    {
      retStr = retStr.substring(0, retStr.length() - 2);
    }
    else
    {
      IActionContext context = ActionContext.threadInstance();
      try
      {
        retStr = StrutsUtil.getMessage(context, "Contractor.noContractorData");
      }
      catch (Exception e)
      {
        log.error(e);
      }
    }

    return retStr;
  }

  public void setAccountsOther(String accountsOther)
  {
    this.accountsOther = accountsOther;
  }

  public String getAccountsFormatted()
  {
    String retStr = accountsBYN;
    if ( !StringUtil.isEmpty(retStr) )
    {
      retStr = retStr.substring(0, retStr.length() - 2);
    }
    String otherAcc = getAccountsOther();
    if ( !StringUtil.isEmpty(otherAcc) )
    {
      otherAcc = otherAcc.substring(0, otherAcc.length() - 2);
      retStr += StringUtil.isEmpty(retStr) ? otherAcc : ", " + otherAcc;
    }

    return retStr;
  }

  public List<ContactPerson> getContactPersons()
  {
    return contactPersons;
  }

  public void setContactPersons(List<ContactPerson> contactPersons)
  {
    this.contactPersons = contactPersons;
  }

  public List<Account> getAccounts()
  {
    return accounts;
  }

  public void setAccounts(List<Account> accounts)
  {
    this.accounts = accounts;
  }

  public List<ContractorUser> getUsers()
  {
    return users;
  }

  public void setUsers(List<ContractorUser> users)
  {
    this.users = users;
  }

  public Reputation getReputation()
  {
    return reputation;
  }

  public void setReputation(Reputation reputation)
  {
    this.reputation = reputation;
  }

  public String getCtrNames()
  {
    return ctrNames;
  }

  public String getCtrNamesFormatted()
  {
    if ( StringUtil.isEmpty(getCtrNames()) )
    {
      return "";
    }

    IActionContext context = ActionContext.threadInstance();
    try
    {
      return StrutsUtil.getMessage(context, "Payment.pay_double_account", getCtrNames());
    }
    catch (Exception e)
    {
      log.error(e);
    }

    return getCtrNames();
  }

  public void setCtrNames(String ctrNames)
  {
    this.ctrNames = ctrNames;
  }

  public String getIs_new_doc()
  {
    return is_new_doc;
  }

  public void setIs_new_doc(String is_new_doc)
  {
    this.is_new_doc = is_new_doc;
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

  public String getAccount()
  {
    return account;
  }

  public void setAccount(String account)
  {
    this.account = account;
  }

	public String getComment()
	{
		return comment;
	}

	public void setComment(String comment)
	{
		this.comment = comment;
	}

	public static ContactPerson getEmptyContactPerson()
  {
    ContactPerson contactPerson = new ContactPerson();
    contactPerson.setNumber("");
    contactPerson.setCps_id("");
    contactPerson.setCtr_id("");
    contactPerson.setCps_name("");
    contactPerson.setCps_position("");
    contactPerson.setCps_on_reason("");
    contactPerson.setCps_phone("");
    contactPerson.setCps_mob_phone("");
    contactPerson.setCps_fax("");
    contactPerson.setCps_email("");
    contactPerson.setCps_block("");
    contactPerson.setCps_contract_comment("");

    return contactPerson;
  }

  public void setListParentIds()
  {
    for ( Account account : accounts )
    {
      account.setCtr_id(getId());
    }

    for ( ContractorUser user : users )
    {
      user.setCtr_id(getId());
    }

    for ( ContactPerson contactPerson : contactPersons )
    {
      contactPerson.setCtr_id(getId());
    }
  }

  public ContactPerson findContactPerson(String number)
  {
    for (int i = 0; i < getContactPersons().size(); i++)
    {
      ContactPerson contactPerson = getContactPersons().get(i);

      if (contactPerson.getNumber().equalsIgnoreCase(number))
        return contactPerson;
    }

    return null;
  }

  public void insertContactPerson(ContactPerson contactPerson)
  {
    getContactPersons().add(getContactPersons().size(), contactPerson);
  }
}
