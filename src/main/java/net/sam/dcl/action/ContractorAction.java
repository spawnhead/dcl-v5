package net.sam.dcl.action;

import net.sam.dcl.controller.actions.DBTransactionAction;
import net.sam.dcl.controller.IDispatchable;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.controller.BaseDispatchValidatorForm;
import net.sam.dcl.util.*;
import net.sam.dcl.form.ContractorForm;
import net.sam.dcl.beans.*;
import net.sam.dcl.dao.ContractorDAO;
import net.sam.dcl.dao.UserDAO;
import net.sam.dcl.dao.ReputationDAO;
import net.sam.dcl.dao.CountryDAO;
import net.sam.dcl.taglib.table.model.impl.HolderImplUsingList;
import net.sam.dcl.taglib.table.IShowChecker;
import net.sam.dcl.taglib.table.ShowCheckerContext;
import net.sam.dcl.taglib.table.IReadOnlyChecker;
import net.sam.dcl.taglib.table.ReadOnlyCheckerContext;
import org.apache.struts.action.ActionForward;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Date: 09.08.2005
 * Time: 9:39:19
 * To change this template use File | Settings | File Templates.
 */
public class ContractorAction extends DBTransactionAction implements IDispatchable
{
  private void saveCurrentFormToBean(IActionContext context)
  {
    ContractorForm form = (ContractorForm) context.getForm();

    Contractor contractor = (Contractor) StoreUtil.getSession(context.getRequest(), Contractor.class);
    if ( null == contractor )
    {
      contractor = new Contractor();  
    }

    contractor.setId(form.getCtr_id());
    contractor.setName(form.getCtr_name());
    contractor.setFullname(form.getCtr_full_name());
    contractor.setCountry(form.getCountry());
    contractor.setIndex(form.getCtr_index());
    contractor.setRegion(form.getCtr_region());
    contractor.setPlace(form.getCtr_place());
    contractor.setStreet(form.getCtr_street());
    contractor.setBuilding(form.getCtr_building());
    contractor.setAddInfo(form.getCtr_add_info());
    contractor.setPhone(form.getCtr_phone());
    contractor.setFax(form.getCtr_fax());
    contractor.setEmail(form.getCtr_email());
    contractor.setBank_props(form.getCtr_bank_props());
    contractor.setUnp(form.getCtr_unp());
    contractor.setOkpo(form.getCtr_okpo());
    contractor.setReputation(form.getReputation());

    contractor.setIs_new_doc(form.getIs_new_doc());
    contractor.setCreateUser(form.getCreateUser());
    contractor.setEditUser(form.getEditUser());
    contractor.setUsr_date_create(form.getUsr_date_create());
    contractor.setUsr_date_edit(form.getUsr_date_edit());

    contractor.setUsers(form.getGridUsers().getDataList());
    contractor.setAccounts(form.getGridAccounts().getDataList());
    contractor.setContactPersons(form.getGridContactPersons().getDataList());

    StoreUtil.putSession(context.getRequest(), contractor);
  }

  private void getCurrentFormFromBean(IActionContext context)
  {
    ContractorForm form = (ContractorForm) context.getForm();

    Contractor contractor = (Contractor) StoreUtil.getSession(context.getRequest(), Contractor.class);

    form.setCtr_id(contractor.getId());
    form.setCtr_name(contractor.getName());
    form.setCtr_full_name(contractor.getFullname());
    form.setCountry(contractor.getCountry());
    form.setCtr_index(contractor.getIndex());
    form.setCtr_region(contractor.getRegion());
    form.setCtr_place(contractor.getPlace());
    form.setCtr_street(contractor.getStreet());
    form.setCtr_building(contractor.getBuilding());
    form.setCtr_add_info(contractor.getAddInfo());
    form.setCtr_phone(contractor.getPhone());
    form.setCtr_fax(contractor.getFax());
    form.setCtr_email(contractor.getEmail());
    form.setCtr_bank_props(contractor.getBank_props());
    form.setCtr_unp(contractor.getUnp());
    form.setCtr_okpo(contractor.getOkpo());
    form.setReputation(contractor.getReputation());

    form.setIs_new_doc(contractor.getIs_new_doc());
    form.setCreateUser(contractor.getCreateUser());
    form.setEditUser(contractor.getEditUser());
    form.setUsr_date_create(contractor.getUsr_date_create());
    form.setUsr_date_edit(contractor.getUsr_date_edit());
  }

  public ActionForward input(IActionContext context) throws Exception
  {
    context.getRequest().getSession().setAttribute(Contractor.currentContractorId, null);

    ContractorForm form = (ContractorForm) context.getForm();

    if ( StringUtil.isEmpty(form.getReputation().getId()) )
    {
      form.setReputation(ReputationDAO.loadDefaultForCtc(context));
    }
    final User currentUser = UserUtil.getCurrentUser(context.getRequest());
    if ( currentUser.isOnlyManager() || currentUser.isOnlyOtherUserInMinsk() )
    {
      form.setReadOnlyReputation(true);
      form.setReadOnlyComment(true);
    }
    if ( currentUser.isAdmin() )
    {
      form.setReadOnlyIfNotAdmin(false);
      form.setAdminRole(true);
    }
    else
    {
      form.setReadOnlyIfNotAdmin(true);
      form.setAdminRole(false);
    }

    context.getRequest().setAttribute("show-delete-checker", new IShowChecker()
    {
      public boolean check(ShowCheckerContext context)
      {
        return context.getTable().getRecordCounter() > 3;
      }
    }
    );

    for ( int i = 0; i < form.getGridContactPersons().getDataList().size(); i++ )
    {
      ContactPerson contactPerson = (ContactPerson)form.getGridContactPersons().getDataList().get(i);
      contactPerson.setNumber(Integer.toString(i + 1));
    }

    final ContractorForm formFinal = (ContractorForm) context.getForm();
    context.getRequest().setAttribute("blockChecker", new IReadOnlyChecker()
    {
      public boolean check(ReadOnlyCheckerContext ctx) throws Exception
      {
        return !(currentUser.isAdmin() && StringUtil.isEmpty(formFinal.getCtr_block()));
      }
    });

    context.getRequest().setAttribute("showDeleteUserForAdmin", new IShowChecker()
    {
      public boolean check(ShowCheckerContext ctx)
      {
        return currentUser.isAdmin();
      }
    });

    context.getRequest().setAttribute("readonlyChangeUserForNotAdmin", new IReadOnlyChecker()
    {
      public boolean check(ReadOnlyCheckerContext ctx) throws Exception
      {
        return !currentUser.isAdmin();
      }
    });

    saveCurrentFormToBean(context);
    return context.getMapping().getInputForward();
  }

  public ActionForward create(IActionContext context) throws Exception
  {
    Contractor contractor = new Contractor();
    StoreUtil.putSession(context.getRequest(), contractor);
    getCurrentFormFromBean(context);

    ContractorForm form = (ContractorForm) context.getForm();
    form.setGridUsers(new HolderImplUsingList());
    User user = UserUtil.getCurrentUser(context.getRequest());
    form.getGridUsers().getDataList().add(new ContractorUser("1", user));

    form.setGridAccounts(new HolderImplUsingList());
    Account account = new Account();
    account.setNumber("1");
    account.setAcc_name(StrutsUtil.getMessage(context, "Contractors.ctr_account1"));
    form.getGridAccounts().getDataList().add(0, account);
    account = new Account();
    account.setNumber("2");
    account.setAcc_name(StrutsUtil.getMessage(context, "Contractors.ctr_account2"));
    form.getGridAccounts().getDataList().add(1, account);
    account = new Account();
    account.setNumber("3");
    account.setAcc_name(StrutsUtil.getMessage(context, "Contractors.ctr_account_val"));
    form.getGridAccounts().getDataList().add(2, account);

    form.setGridContactPersons(new HolderImplUsingList());

    form.setActivePanelName(BaseDispatchValidatorForm.mainPanel);
    form.setLastNumber("1000");
    form.setLastNumberAcc("1000");
    form.setIs_new_doc("true");
    return input(context);
  }

  private void editCommon(IActionContext context) throws Exception
  {
    ContractorForm form = (ContractorForm) context.getForm();
    form.setIs_new_doc("false");

    DAOUtils.load(context, "contractor-load", null);

    if ("1".equals(form.getCtr_block()))
    {
      form.setFormReadOnly(true);
    }
    else
    {
      form.setFormReadOnly(false);
    }

    if (!StringUtil.isEmpty(form.getCreateUser().getUsr_id()))
    {
      UserDAO.load(context, form.getCreateUser());
    }
    if (!StringUtil.isEmpty(form.getEditUser().getUsr_id()))
    {
      UserDAO.load(context, form.getEditUser());
    }
    form.setUsr_date_create(StringUtil.dbDateString2appDateString(form.getUsr_date_create()));
    form.setUsr_date_edit(StringUtil.dbDateString2appDateString(form.getUsr_date_edit()));
    if (!StringUtil.isEmpty(form.getReputation().getId()))
    {
      ReputationDAO.load(context, form.getReputation());
    }

    saveCurrentFormToBean(context);
    Contractor contractor = (Contractor) StoreUtil.getSession(context.getRequest(), Contractor.class);

    List<ContractorUser> lstUsers = DAOUtils.fillList(context, "select-contractor_users", contractor, ContractorUser.class, null, null);
    contractor.setUsers(lstUsers);
    form.getGridUsers().setDataList(contractor.getUsers());
    for ( int i = 0; i < form.getGridUsers().getDataList().size(); i++ )
    {
      ContractorUser user = (ContractorUser)form.getGridUsers().getDataList().get(i);
      user.setNumber(Integer.toString(i + 1));
    }

    List<Account> lstAccounts = DAOUtils.fillList(context, "select-accounts", contractor, Account.class, null, null);
    contractor.setAccounts(lstAccounts);
    form.getGridAccounts().setDataList(contractor.getAccounts());
    for ( int i = 0; i < form.getGridAccounts().getDataList().size(); i++ )
    {
      Account account = (Account)form.getGridAccounts().getDataList().get(i);
      account.setNumber(Integer.toString(i + 1));
    }

    List<ContactPerson> lstContactPersons = DAOUtils.fillList(context, "select-contact-persons", contractor, ContactPerson.class, null, null);
    for ( ContactPerson contactPerson : lstContactPersons)
    {
      List<ContactPersonUser> lstContactPersonUsers = DAOUtils.fillList(context, "select-contact_person_users", contactPerson, ContactPersonUser.class, null, null);
      contactPerson.setUsers(lstContactPersonUsers);
    }
    contractor.setContactPersons(lstContactPersons);
    form.getGridContactPersons().setDataList(contractor.getContactPersons());

    StoreUtil.putSession(context.getRequest(), contractor);

    form.setLastNumber("1000");
    form.setLastNumberAcc("1000");
  }

  public ActionForward editContactPersons(IActionContext context) throws Exception
  {
    editCommon(context);

    ContractorForm form = (ContractorForm) context.getForm();
    form.setActivePanelName(ContractorForm.contactPersonContractorPanel);

    return input(context);
  }

  public ActionForward edit(IActionContext context) throws Exception
  {
    editCommon(context);

    ContractorForm form = (ContractorForm) context.getForm();
    form.setActivePanelName(BaseDispatchValidatorForm.mainPanel);
    return input(context);
  }

  public ActionForward delete(IActionContext context) throws Exception
  {
    DAOUtils.update(context, "contractor-delete", null);
    return context.getMapping().findForward("back");
  }

  public ActionForward process(IActionContext context) throws Exception
  {
    ContractorForm form = (ContractorForm) context.getForm();
    Contractor contractor = ContractorDAO.loadByUNP(context, null, form.getCtr_unp());
    String errMsg = "";

    if (StringUtil.isEmpty(form.getCtr_id()))
    {
      if (null != contractor)
      {
        errMsg = StrutsUtil.addDelimiter(errMsg);
        errMsg += StrutsUtil.getMessage(context, "error.contractorpage.duplicate_unp");
      }
    }
    else
    {
      if (null != contractor && !contractor.getId().equalsIgnoreCase(form.getCtr_id()))
      {
        errMsg = StrutsUtil.addDelimiter(errMsg);
        errMsg += StrutsUtil.getMessage(context, "error.contractorpage.duplicate_unp");
      }
    }

    for ( int i = 0; i < form.getGridAccounts().getDataList().size(); i++ )
    {
      Account account = (Account)form.getGridAccounts().getDataList().get(i);
      if (
              ( !StringUtil.isEmpty(account.getAcc_name()) && account.getAcc_name().equals(StrutsUtil.getMessage(context, "Contractors.ctr_account1")) ) ||
              ( !StringUtil.isEmpty(account.getAcc_name()) && account.getAcc_name().equals(StrutsUtil.getMessage(context, "Contractors.ctr_account2")) ) ||
              ( !StringUtil.isEmpty(account.getAcc_name()) && account.getAcc_name().equals(StrutsUtil.getMessage(context, "Contractors.ctr_account_val")) )
         )
      {
        if ( !StringUtil.isEmpty(account.getAcc_account()) && StringUtil.isEmpty(account.getCurrency().getId()) )
        {
          errMsg = StrutsUtil.addDelimiter(errMsg);
          errMsg += StrutsUtil.getMessage(context, "error.contractor.accounts.empty_currency");
          form.setActivePanelName(ContractorForm.accountsContractorPanel);
          break;
        }
      }
      else
      {
        if ( StringUtil.isEmpty(account.getAcc_account()) || StringUtil.isEmpty(account.getCurrency().getId()) )
        {
          errMsg = StrutsUtil.addDelimiter(errMsg);
          errMsg += StrutsUtil.getMessage(context, "error.contractor.accounts.empty_field");
          form.setActivePanelName(ContractorForm.accountsContractorPanel);
          break;
        }
      }

      if ( account.getAcc_account().length() > 35 )
      {
        errMsg = StrutsUtil.addDelimiter(errMsg);
        errMsg += StrutsUtil.getMessage(context, "error.contractor.accounts.incorrect_account_lenght");
        form.setActivePanelName(ContractorForm.accountsContractorPanel);
        break;
      }
    }

    if ( !StringUtil.isEmpty(errMsg) )
    {
      StrutsUtil.addError(context, "errors.msg", errMsg, null);
      return input(context);
    }

    contractor = (Contractor) StoreUtil.getSession(context.getRequest(), Contractor.class);
    contractor.setUsers(form.getGridUsers().getDataList());
    contractor.setAccounts(form.getGridAccounts().getDataList());
    contractor.setContactPersons(form.getGridContactPersons().getDataList());
    if (StringUtil.isEmpty(form.getCtr_id()))
    {
      DAOUtils.load(context, "contractor-insert", null);
      contractor.setId(form.getCtr_id());
    }
    else
    {
      DAOUtils.update(context, "contractor-update", null);
    }
    contractor.setListParentIds();
    ContractorDAO.saveUsers(context, contractor);
    ContractorDAO.saveAccounts(context, contractor);
    ContractorDAO.saveContactPersons(context, contractor);
    context.getRequest().getSession().setAttribute(Contractor.currentContractorId, form.getCtr_id());
    return context.getMapping().findForward("back");
  }

  public ActionForward editReputation(IActionContext context) throws Exception
  {
    ContractorForm form = (ContractorForm) context.getForm();
    form.setActivePanelName(BaseDispatchValidatorForm.mainPanel);
    saveCurrentFormToBean(context);
    return context.getMapping().findForward("editReputation");
  }

  public ActionForward addCountry(IActionContext context) throws Exception
  {
    saveCurrentFormToBean(context);

    ContractorForm form = (ContractorForm) context.getForm();
    form.setActivePanelName(BaseDispatchValidatorForm.mainPanel);
    context.getRequest().getSession().setAttribute(Country.currentCountryId, form.getCountry().getId());
    
    return context.getMapping().findForward("addCountry");
  }

  public ActionForward editPersonInContractor(IActionContext context) throws Exception
  {
    saveCurrentFormToBean(context);

    return context.getMapping().findForward("editPersonInContractor");
  }

  public ActionForward addPersonInContractor(IActionContext context) throws Exception
  {
    saveCurrentFormToBean(context);

    return context.getMapping().findForward("addPersonInContractor");
  }

  public ActionForward fromEditReputation(IActionContext context) throws Exception
  {
    getCurrentFormFromBean(context);

    ContractorForm form = (ContractorForm) context.getForm();
    form.setActivePanelName(BaseDispatchValidatorForm.mainPanel);
    return input(context);
  }

  public ActionForward fromContactPerson(IActionContext context) throws Exception
  {
    getCurrentFormFromBean(context);

    ContractorForm form = (ContractorForm) context.getForm();
    form.setActivePanelName(ContractorForm.contactPersonContractorPanel);
    return input(context);
  }

  public ActionForward fromAddCountry(IActionContext context) throws Exception
  {
    getCurrentFormFromBean(context);

    ContractorForm form = (ContractorForm) context.getForm();
    String countryId = (String) context.getRequest().getSession().getAttribute(Country.currentCountryId);
    if (!StringUtil.isEmpty(countryId))
    {
      form.setCountry(CountryDAO.load(context, countryId));
    }
    context.getRequest().getSession().setAttribute(Country.currentCountryId, null);

    return input(context);
  }

  public ActionForward addRowInUserGrid(IActionContext context) throws Exception
  {
    ContractorForm form = (ContractorForm) context.getForm();
    ContractorUser user = new ContractorUser();
    User currentUser = UserUtil.getCurrentUser(context.getRequest());
    if (!currentUser.isAdmin()) //не админ может добавить только себя, и все
      user.setUser(new User(currentUser));
    Integer lastNumber = new Integer(form.getLastNumber());
    lastNumber++;
    user.setNumber(lastNumber.toString());
    form.setLastNumber(lastNumber.toString());
    form.getGridUsers().getDataList().add(form.getGridUsers().getDataList().size(), user);

    form.setActivePanelName(ContractorForm.usersContractorPanel);
    return input(context);
  }

  public ActionForward deleteRowFromUserGrid(IActionContext context) throws Exception
  {
    ContractorForm form = (ContractorForm) context.getForm();
    for ( int i = 0; i < form.getGridUsers().getDataList().size(); i++ )
    {
      ContractorUser user = (ContractorUser)form.getGridUsers().getDataList().get(i);
      if (user.getNumber().equals(form.getNumber()))
      {
        form.getGridUsers().getDataList().remove(i);
        break;
      }
    }
    form.setActivePanelName(ContractorForm.usersContractorPanel);
    return input(context);
  }

  public ActionForward addRowInAccountGrid(IActionContext context) throws Exception
  {
    ContractorForm form = (ContractorForm) context.getForm();
    Account account = new Account();
    if ( form.getGridAccounts().getDataList().size() == 0 )
    {
      account.setAcc_name(StrutsUtil.getMessage(context, "Contractors.ctr_account1"));
    }
    if ( form.getGridAccounts().getDataList().size() == 1 )
    {
      account.setAcc_name(StrutsUtil.getMessage(context, "Contractors.ctr_account2"));
    }
    if ( form.getGridAccounts().getDataList().size() == 2 )
    {
      account.setAcc_name(StrutsUtil.getMessage(context, "Contractors.ctr_account_val"));
    }
    Integer lastNumberAcc = new Integer(form.getLastNumberAcc());
    lastNumberAcc++;
    account.setNumber(lastNumberAcc.toString());
    form.setLastNumberAcc(lastNumberAcc.toString());
    form.getGridAccounts().getDataList().add(form.getGridAccounts().getDataList().size(), account);

    form.setActivePanelName(ContractorForm.accountsContractorPanel);
    return input(context);
  }

  public ActionForward deleteRowFromAccountGrid(IActionContext context) throws Exception
  {
    ContractorForm form = (ContractorForm) context.getForm();
    for ( int i = 0; i < form.getGridAccounts().getDataList().size(); i++ )
    {
      Account account = (Account)form.getGridAccounts().getDataList().get(i);
      if (account.getNumber().equals(form.getNumberAcc()))
      {
        form.getGridAccounts().getDataList().remove(i);
        break;
      }
    }

    form.setActivePanelName(ContractorForm.accountsContractorPanel);
    return input(context);
  }

  public ActionForward blockContactPerson(IActionContext context) throws Exception
  {
    ContractorForm form = (ContractorForm) context.getForm();
    Contractor contractor = (Contractor) StoreUtil.getSession(context.getRequest(), Contractor.class);
    ContactPerson contactPerson = contractor.findContactPerson(form.getNumber());
    if ("1".equals(form.getCps_block()))
    {
      contactPerson.setCps_block("");
    }
    else
    {
      contactPerson.setCps_block("1");
    }

    form.setActivePanelName(ContractorForm.contactPersonContractorPanel);
    return input(context);
  }

  public ActionForward fireContactPerson(IActionContext context) throws Exception
  {
    ContractorForm form = (ContractorForm) context.getForm();
    Contractor contractor = (Contractor) StoreUtil.getSession(context.getRequest(), Contractor.class);
    ContactPerson contactPerson = contractor.findContactPerson(form.getNumber());
    if ("1".equals(form.getCps_fire()))
    {
      contactPerson.setCps_fire("");
    }
    else
    {
      contactPerson.setCps_fire("1");
    }

    form.setActivePanelName(ContractorForm.contactPersonContractorPanel);
    return input(context);
  }
}
