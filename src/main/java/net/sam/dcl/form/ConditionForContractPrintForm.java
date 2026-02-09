package net.sam.dcl.form;

import net.sam.dcl.controller.BaseDispatchValidatorForm;
import net.sam.dcl.beans.*;
import net.sam.dcl.util.StringUtil;

import java.util.List;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author: DG
 * Date: Aug 6, 2005
 * Time: 5:37:29 PM
 */
public class ConditionForContractPrintForm extends BaseDispatchValidatorForm
{
  protected static Log log = LogFactory.getLog(ConditionForContractPrintForm.class);

  User createUser = new User();
  User editUser = new User();
  User placeUser = new User();
  User executeUser = new User();
  String usr_date_create;
  String usr_date_edit;
  String usr_date_place;
  String usr_date_execute;
  String cfc_place;
  String cfc_execute;
	Seller seller = new Seller();
  Contractor contractor = new Contractor();
  String cfc_doc_type;
  String cfc_con_number_txt;
  String cfc_con_date;
  Currency currency = new Currency();
  Contract contract = new Contract();
  String spc_numbers;
  String cfc_spc_number_txt;
  String cfc_spc_date;
  String cfc_pay_cond;
  String cfc_delivery_cond;
  String cfc_custom_point;
  String cfc_guarantee_cond;
  String cfc_montage_cond;
  String cfc_date_con_to;
  String cfc_count_delivery;
  ContactPerson contactPersonSign = new ContactPerson();
  ContactPerson contactPerson = new ContactPerson();
  PurchasePurpose purchasePurpose = new PurchasePurpose();

  List produces = new ArrayList();

  float printScale;

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

  public User getPlaceUser()
  {
    return placeUser;
  }

  public void setPlaceUser(User placeUser)
  {
    this.placeUser = placeUser;
  }

  public User getExecuteUser()
  {
    return executeUser;
  }

  public void setExecuteUser(User executeUser)
  {
    this.executeUser = executeUser;
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

  public String getUsr_date_place()
  {
    return usr_date_place;
  }

  public void setUsr_date_place(String usr_date_place)
  {
    this.usr_date_place = usr_date_place;
  }

  public String getUsr_date_execute()
  {
    return usr_date_execute;
  }

  public void setUsr_date_execute(String usr_date_execute)
  {
    this.usr_date_execute = usr_date_execute;
  }

  public String getCfc_place()
  {
    return cfc_place;
  }

  public void setCfc_place(String cfc_place)
  {
    this.cfc_place = cfc_place;
  }

  public String getCfc_execute()
  {
    return cfc_execute;
  }

  public void setCfc_execute(String cfc_execute)
  {
    this.cfc_execute = cfc_execute;
  }

  public Seller getSeller()
  {
    return seller;
  }

  public void setSeller(Seller seller)
  {
    this.seller = seller;
  }

  public Contractor getContractor()
  {
    return contractor;
  }

  public void setContractor(Contractor contractor)
  {
    this.contractor = contractor;
  }

  public String getContractNumberWithDate()
  {
    if ( StringUtil.isEmpty(getContract().getCon_number()) )
    {
      return "";
    }

    return getContract().getNumberWithDate();
  }

  public String getCfc_doc_type()
  {
    return cfc_doc_type;
  }

  public void setCfc_doc_type(String cfc_doc_type)
  {
    this.cfc_doc_type = cfc_doc_type;
  }

  public String getCfc_con_number_txt()
  {
    return cfc_con_number_txt;
  }

  public void setCfc_con_number_txt(String cfc_con_number_txt)
  {
    this.cfc_con_number_txt = cfc_con_number_txt;
  }

  public String getCfc_con_date()
  {
    return cfc_con_date;
  }

  public void setCfc_con_date(String cfc_con_date)
  {
    this.cfc_con_date = cfc_con_date;
  }

  public Currency getCurrency()
  {
    return currency;
  }

  public void setCurrency(Currency currency)
  {
    this.currency = currency;
  }

  public Contract getContract()
  {
    return contract;
  }

  public void setContract(Contract contract)
  {
    this.contract = contract;
  }

  public String getSpc_numbers()
  {
    return spc_numbers;
  }

  public void setSpc_numbers(String spc_numbers)
  {
    this.spc_numbers = spc_numbers;
  }

  public String getCfc_spc_number_txt()
  {
    return cfc_spc_number_txt;
  }

  public void setCfc_spc_number_txt(String cfc_spc_number_txt)
  {
    this.cfc_spc_number_txt = cfc_spc_number_txt;
  }

  public String getCfc_spc_date()
  {
    return cfc_spc_date;
  }

  public void setCfc_spc_date(String cfc_spc_date)
  {
    this.cfc_spc_date = cfc_spc_date;
  }

  public String getCfc_pay_cond()
  {
    return cfc_pay_cond;
  }

  public void setCfc_pay_cond(String cfc_pay_cond)
  {
    this.cfc_pay_cond = cfc_pay_cond;
  }

  public String getCfc_delivery_cond()
  {
    return cfc_delivery_cond;
  }

  public void setCfc_delivery_cond(String cfc_delivery_cond)
  {
    this.cfc_delivery_cond = cfc_delivery_cond;
  }

  public String getCfc_custom_point()
  {
    return cfc_custom_point;
  }

  public void setCfc_custom_point(String cfc_custom_point)
  {
    this.cfc_custom_point = cfc_custom_point;
  }

  public String getCfc_guarantee_cond()
  {
    return cfc_guarantee_cond;
  }

  public void setCfc_guarantee_cond(String cfc_guarantee_cond)
  {
    this.cfc_guarantee_cond = cfc_guarantee_cond;
  }

  public String getCfc_montage_cond()
  {
    return cfc_montage_cond;
  }

  public void setCfc_montage_cond(String cfc_montage_cond)
  {
    this.cfc_montage_cond = cfc_montage_cond;
  }

  public String getCfc_date_con_to()
  {
    return cfc_date_con_to;
  }

  public void setCfc_date_con_to(String cfc_date_con_to)
  {
    this.cfc_date_con_to = cfc_date_con_to;
  }

  public String getCfc_count_delivery()
  {
    return cfc_count_delivery;
  }

  public void setCfc_count_delivery(String cfc_count_delivery)
  {
    this.cfc_count_delivery = cfc_count_delivery;
  }

  public ContactPerson getContactPersonSign()
  {
    return contactPersonSign;
  }

  public void setContactPersonSign(ContactPerson contactPersonSign)
  {
    this.contactPersonSign = contactPersonSign;
  }

  public ContactPerson getContactPerson()
  {
    return contactPerson;
  }

  public void setContactPerson(ContactPerson contactPerson)
  {
    this.contactPerson = contactPerson;
  }

  public PurchasePurpose getPurchasePurpose()
  {
    return purchasePurpose;
  }

  public void setPurchasePurpose(PurchasePurpose purchasePurpose)
  {
    this.purchasePurpose = purchasePurpose;
  }

  public List getProduces()
  {
    return produces;
  }

  public void setProduces(List produces)
  {
    this.produces = produces;
  }

  public float getPrintScale()
  {
    return printScale;
  }

  public void setPrintScale(float printScale)
  {
    this.printScale = printScale;
  }
}
