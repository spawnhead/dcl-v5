package net.sam.dcl.action;

import net.sam.dcl.beans.*;
import net.sam.dcl.controller.BaseDispatchValidatorForm;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.controller.IDispatchable;
import net.sam.dcl.controller.actions.DBTransactionAction;
import net.sam.dcl.dao.ContactPersonDAO;
import net.sam.dcl.form.ContactPersonForm;
import net.sam.dcl.util.*;
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
public class ContactPersonAction extends DBTransactionAction implements IDispatchable
{
  private void saveCurrentFormToBean(IActionContext context)
  {
    ContactPersonForm form = (ContactPersonForm) context.getForm();

    ContactPerson contactPerson = (ContactPerson) StoreUtil.getSession(context.getRequest(), ContactPerson.class);
    if (null == contactPerson)
    {
      contactPerson = new ContactPerson();
    }

    contactPerson.setCps_id(form.getCps_id());
    contactPerson.setCtr_id(form.getCtr_id());
    contactPerson.setCps_name(form.getCps_name());
    contactPerson.setCps_position(form.getCps_position());
    contactPerson.setCps_on_reason(form.getCps_on_reason());
    contactPerson.setCps_phone(form.getCps_phone());
    contactPerson.setCps_mob_phone(form.getCps_mob_phone());
    contactPerson.setCps_fax(form.getCps_fax());
    contactPerson.setCps_email(form.getCps_email());
    contactPerson.setCps_block(form.getCps_block());
    contactPerson.setCps_contract_comment(form.getCps_contract_comment());
    contactPerson.setCps_fire(form.getCps_fire());

    StoreUtil.putSession(context.getRequest(), contactPerson);
  }

  private void getCurrentFormFromBean(IActionContext context)
  {
    ContactPersonForm form = (ContactPersonForm) context.getForm();

    ContactPerson contactPerson = (ContactPerson) StoreUtil.getSession(context.getRequest(), ContactPerson.class);

    form.setCps_id(contactPerson.getCps_id());
    form.setCtr_id(contactPerson.getCtr_id());
    form.setCps_name(contactPerson.getCps_name());
    form.setCps_position(contactPerson.getCps_position());
    form.setCps_on_reason(contactPerson.getCps_on_reason());
    form.setCps_phone(contactPerson.getCps_phone());
    form.setCps_mob_phone(contactPerson.getCps_mob_phone());
    form.setCps_fax(contactPerson.getCps_fax());
    form.setCps_email(contactPerson.getCps_email());
    form.setCps_block(contactPerson.getCps_block());
    form.setCps_contract_comment(contactPerson.getCps_contract_comment());
    form.setCps_fire(contactPerson.getCps_fire());
  }

  public ActionForward input(IActionContext context) throws Exception
  {
    ContactPersonForm form = (ContactPersonForm) context.getForm();
    String contractorId = (String) context.getRequest().getSession().getAttribute(Contractor.currentContractorId);
    if (!StringUtil.isEmpty(contractorId))
      form.setCtr_id(contractorId);

    final User currentUser = UserUtil.getCurrentUser(context.getRequest());
    if ( currentUser.isAdmin() )
    {
      form.setAdminRole(true);
    }
    else
    {
      form.setAdminRole(false);
    }

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

    return context.getMapping().getInputForward();
  }

  public ActionForward create(IActionContext context) throws Exception
  {
    ContactPerson contactPerson = new ContactPerson();
    StoreUtil.putSession(context.getRequest(), contactPerson);
    getCurrentFormFromBean(context);

    ContactPersonForm form = (ContactPersonForm) context.getForm();
    form.getGridContactPersonUsers().getDataList().clear();
    User user = UserUtil.getCurrentUser(context.getRequest());
    form.getGridContactPersonUsers().getDataList().add(new ContactPersonUser("1", user));
    form.setCtr_id("");
    form.setCps_id("");
    form.setCps_block("");
    form.setFromContractor("");
    form.setNumber("");
    form.setLastNumberUser("1000");
    form.setActivePanelName(BaseDispatchValidatorForm.mainPanel);
    return input(context);
  }

  public ActionForward createFromContractor(IActionContext context) throws Exception
  {
    ContactPerson contactPerson = new ContactPerson();
    StoreUtil.putSession(context.getRequest(), contactPerson);
    getCurrentFormFromBean(context);

    ContactPersonForm form = (ContactPersonForm) context.getForm();
    form.getGridContactPersonUsers().getDataList().clear();
    User user = UserUtil.getCurrentUser(context.getRequest());
    form.getGridContactPersonUsers().getDataList().add(new ContactPersonUser("1", user));
    form.setCtr_id("");
    form.setCps_id("");
    form.setCps_block("");
    form.setFromContractor("1");
    form.setNumber("");
    form.setLastNumberUser("1000");
    form.setActivePanelName(BaseDispatchValidatorForm.mainPanel);
    return input(context);
  }

  private void editCommon(IActionContext context) throws Exception
  {
    ContactPersonForm form = (ContactPersonForm) context.getForm();
    ContactPerson contactPerson = (ContactPerson) StoreUtil.getSession(context.getRequest(), ContactPerson.class);

    form.getGridContactPersonUsers().getDataList().clear();
    for (int i = 0; i < contactPerson.getUsers().size(); i++)
    {
      form.getGridContactPersonUsers().getDataList().add(new ContactPersonUser(contactPerson.getUsers().get(i)));
    }
    for (int i = 0; i < form.getGridContactPersonUsers().getDataList().size(); i++)
    {
      ContactPersonUser user = (ContactPersonUser) form.getGridContactPersonUsers().getDataList().get(i);
      user.setNumber(Integer.toString(i + 1));
    }

    form.setActivePanelName(BaseDispatchValidatorForm.mainPanel);
    form.setLastNumberUser("1000");
  }

  public ActionForward edit(IActionContext context) throws Exception
  {
    ContactPersonForm form = (ContactPersonForm) context.getForm();
    String contractorId = (String) context.getRequest().getSession().getAttribute(Contractor.currentContractorId);
    if (!StringUtil.isEmpty(contractorId))
      form.setCtr_id(contractorId);
    String contactPersonId = (String) context.getRequest().getSession().getAttribute(ContactPerson.current_contact_person_id);
    if (!StringUtil.isEmpty(contactPersonId))
      form.setCps_id(contactPersonId);

    DAOUtils.load(context, "contact-person-load", null);

    if ("1".equals(form.getCps_block()))
    {
      StrutsUtil.addError(context, "msg.contact_person.block", null);
      return context.getMapping().findForward("back");
    }
    else
    {
      ContactPerson contactPerson = (ContactPerson) StoreUtil.getSession(context.getRequest(), ContactPerson.class);
      if (contactPerson == null)
      {
        contactPerson = ContactPersonDAO.load(context, contactPersonId);
        StoreUtil.putSession(context.getRequest(), contactPerson);
      }

      List<ContactPersonUser> lstUsers = DAOUtils.fillList(context, "select-contact_person_users", contactPerson, ContactPersonUser.class, null, null);
      form.getGridContactPersonUsers().setDataList(lstUsers);
      form.setFromContractor("");

      editCommon(context);
      return input(context);
    }
  }

  public ActionForward editInContractor(IActionContext context) throws Exception
  {
    ContactPersonForm form = (ContactPersonForm) context.getForm();
    form.setFromContractor("1");
    Contractor contractor = (Contractor) StoreUtil.getSession(context.getRequest(), Contractor.class);
    ContactPerson contactPerson = contractor.findContactPerson(form.getNumber());
    StoreUtil.putSession(context.getRequest(), contactPerson);

    getCurrentFormFromBean(context);

    editCommon(context);

    return input(context);
  }

  public ActionForward process(IActionContext context) throws Exception
  {
    ContactPersonForm form = (ContactPersonForm) context.getForm();
    if (StringUtil.isEmpty(form.getFromContractor()))
    {
      ContactPerson contactPerson = (ContactPerson) StoreUtil.getSession(context.getRequest(), ContactPerson.class);
      contactPerson.getUsers().clear();
      for (int i = 0; i < form.getGridContactPersonUsers().getDataList().size(); i++)
      {
        contactPerson.getUsers().add(new ContactPersonUser((ContactPersonUser)form.getGridContactPersonUsers().getDataList().get(i)));
      }
      if (StringUtil.isEmpty(form.getCps_id()))
      {
        DAOUtils.load(context, "contact-person-insert", null);
        context.getRequest().getSession().setAttribute(ContactPerson.current_contact_person_id, form.getCps_id());
      }
      else
      {
        DAOUtils.update(context, "contact-person-update", null);
      }
      contactPerson.setCps_id(form.getCps_id());
      ContactPersonDAO.saveUsers(context, contactPerson);
    }
    else
    {
      Contractor contractor = (Contractor) StoreUtil.getSession(context.getRequest(), Contractor.class);
      saveCurrentFormToBean(context);
      ContactPerson contactPerson = (ContactPerson) StoreUtil.getSession(context.getRequest(), ContactPerson.class);
      contactPerson.getUsers().clear();
      for (int i = 0; i < form.getGridContactPersonUsers().getDataList().size(); i++)
      {
        contactPerson.getUsers().add(new ContactPersonUser((ContactPersonUser)form.getGridContactPersonUsers().getDataList().get(i)));
      }
      if (StringUtil.isEmpty(form.getNumber())) //новый
      {
        contractor.insertContactPerson(contactPerson);
      }
    }
    return context.getMapping().findForward("back");
  }

  public ActionForward addRowInUserGrid(IActionContext context) throws Exception
  {
    ContactPersonForm form = (ContactPersonForm) context.getForm();
    ContactPersonUser user = new ContactPersonUser();
    User currentUser = UserUtil.getCurrentUser(context.getRequest());
    if (!currentUser.isAdmin()) //не админ может добавить только себя, и все
      user.setUser(new User(currentUser));
    Integer lastNumber = new Integer(form.getLastNumberUser());
    lastNumber++;
    user.setNumber(lastNumber.toString());
    form.setLastNumberUser(lastNumber.toString());
    form.getGridContactPersonUsers().getDataList().add(form.getGridContactPersonUsers().getDataList().size(), user);

    form.setActivePanelName(ContactPersonForm.usersContactPersonPanel);
    return input(context);
  }

  public ActionForward deleteRowFromUserGrid(IActionContext context) throws Exception
  {
    ContactPersonForm form = (ContactPersonForm) context.getForm();
    for (int i = 0; i < form.getGridContactPersonUsers().getDataList().size(); i++)
    {
      ContactPersonUser user = (ContactPersonUser) form.getGridContactPersonUsers().getDataList().get(i);
      if (user.getNumber().equals(form.getNumber()))
      {
        form.getGridContactPersonUsers().getDataList().remove(i);
        break;
      }
    }
    form.setActivePanelName(ContactPersonForm.usersContactPersonPanel);
    return input(context);
  }

}
