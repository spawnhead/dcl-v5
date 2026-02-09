package net.sam.dcl.form;

import net.sam.dcl.controller.BaseDispatchValidatorForm;
import net.sam.dcl.beans.*;
import net.sam.dcl.service.DeferredAttachmentService;
import net.sam.dcl.taglib.table.model.impl.HolderImplUsingList;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.ConstDefinitions;
import net.sam.dcl.dbo.DboAttachment;
import org.apache.struts.action.ActionMapping;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * Date: Aug 6, 2005
 * Time: 5:37:29 PM
 */
public class ConditionForContractForm extends BaseDispatchValidatorForm
{
	protected static Log log = LogFactory.getLog(ConditionForContractForm.class);
	String is_new_doc;
	String number;

	String print;
	boolean needPrint;

	String cfc_id;
	User createUser = new User();
	User editUser = new User();
	User placeUser = new User();
	User executeUser = new User();
	User annulUser = new User();
	String usr_date_create;
	String usr_date_edit;
	String usr_date_place;
	String usr_date_execute;
	String usr_date_annul;
	String cfc_place;
	String cfc_place_save;
	String cfc_execute;
	Contractor contractor = new Contractor();
	String cfc_con_number_txt;
	String cfc_con_date;
	Currency currency = new Currency();
	Contract contract = new Contract();
	String spc_numbers;
	String cfc_spc_numbers;
	String cfc_spc_number_txt;
	String cfc_spc_date;
	String cfc_pay_cond;
	String cfc_custom_point;
	String cfc_delivery_cond;
	String cfc_guarantee_cond;
	String cfc_montage_cond;
	String cfc_date_con_to;
	String cfc_count_delivery;
	ContactPerson contactPersonSign = new ContactPerson();
	ContactPerson contactPerson = new ContactPerson();
	PurchasePurpose purchasePurpose = new PurchasePurpose();
	String cfc_need_invoice;
	String cfc_comment;
	String cfc_annul;
	String cfc_annul_date;
	String cfc_check_price;
	String cfc_check_price_date;
	String usr_id_check_price;

	String cfc_doc_type1;
	String cfc_doc_type2;
	Seller seller = new Seller();
	String cfc_delivery_count1;
	String cfc_delivery_count2;

	String conFinalDate;

	DboAttachment templateExcel;

	boolean addSignPerson;

	boolean formReadOnly = false;
	boolean readOnlyForPlace = false;
	boolean readOnlyForAnnul = false;
	boolean readOnlyForSave = false;
	boolean unitsPermittedForUsageInContracts;
	boolean showMsg = false;
	boolean showForAdmin = false;

	HolderImplUsingList grid = new HolderImplUsingList();

	HolderImplUsingList attachmentsGrid = new HolderImplUsingList();
	DeferredAttachmentService attachmentService = null;
	String attachmentId;

	String printScale = "100";

	public void reset(ActionMapping mapping, HttpServletRequest request)
	{
		if (!isFormReadOnly())
		{
			setCfc_doc_type1("");
			setCfc_doc_type2("");
			setCfc_delivery_count1("");
			setCfc_delivery_count2("");
			setCfc_place("");
		}
		if (!isReadOnlyForPlace())
		{
			setCfc_place("");
		}
		if (!isReadOnlyForAnnul())
		{
			setCfc_annul("");
		}

		super.reset(mapping, request);
	}

	public String getTemplateIdCFC()
	{
		return String.valueOf(ConstDefinitions.templateIdCFC);
	}

	public String getIs_new_doc()
	{
		return is_new_doc;
	}

	public void setIs_new_doc(String is_new_doc)
	{
		this.is_new_doc = is_new_doc;
	}

	public String getNumber()
	{
		return number;
	}

	public void setNumber(String number)
	{
		this.number = number;
	}

	public String getPrint()
	{
		return print;
	}

	public void setPrint(String print)
	{
		this.print = print;
	}

	public boolean isNeedPrint()
	{
		return needPrint;
	}

	public void setNeedPrint(boolean needPrint)
	{
		this.needPrint = needPrint;
	}

	public String getCfc_id()
	{
		return cfc_id;
	}

	public void setCfc_id(String cfc_id)
	{
		this.cfc_id = cfc_id;
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

	public User getAnnulUser()
	{
		return annulUser;
	}

	public void setAnnulUser(User annulUser)
	{
		this.annulUser = annulUser;
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

	public String getUsr_date_annul()
	{
		return usr_date_annul;
	}

	public void setUsr_date_annul(String usr_date_annul)
	{
		this.usr_date_annul = usr_date_annul;
	}

	public String getCfc_place()
	{
		return cfc_place;
	}

	public void setCfc_place(String cfc_place)
	{
		this.cfc_place = cfc_place;
	}

	public String getCfc_place_save()
	{
		return cfc_place_save;
	}

	public void setCfc_place_save(String cfc_place_save)
	{
		this.cfc_place_save = cfc_place_save;
	}

	public String getCfc_execute()
	{
		return cfc_execute;
	}

	public void setCfc_execute(String cfc_execute)
	{
		this.cfc_execute = cfc_execute;
	}

	public Contractor getContractor()
	{
		return contractor;
	}

	public String getContractNumberWithDate()
	{
		if (StringUtil.isEmpty(getContract().getCon_number()))
		{
			return "";
		}

		return getContract().getFullContractInfo();
	}

	public void setContractor(Contractor contractor)
	{
		this.contractor = contractor;
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

	public String getCfc_spc_numbers()
	{
		return cfc_spc_numbers;
	}

	public void setCfc_spc_numbers(String cfc_spc_numbers)
	{
		this.cfc_spc_numbers = cfc_spc_numbers;
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

	public String getCfc_custom_point()
	{
		return cfc_custom_point;
	}

	public void setCfc_custom_point(String cfc_custom_point)
	{
		this.cfc_custom_point = cfc_custom_point;
	}

	public String getCfc_delivery_cond()
	{
		return cfc_delivery_cond;
	}

	public void setCfc_delivery_cond(String cfc_delivery_cond)
	{
		this.cfc_delivery_cond = cfc_delivery_cond;
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

	public String getCfc_need_invoice()
	{
		return cfc_need_invoice;
	}

	public void setCfc_need_invoice(String cfc_need_invoice)
	{
		this.cfc_need_invoice = cfc_need_invoice;
	}

	public String getCfc_comment()
	{
		return cfc_comment;
	}

	public void setCfc_comment(String cfc_comment)
	{
		this.cfc_comment = cfc_comment;
	}

	public String getCfc_annul()
	{
		return cfc_annul;
	}

	public void setCfc_annul(String cfc_annul)
	{
		this.cfc_annul = cfc_annul;
	}

	public String getCfc_annul_date()
	{
		return cfc_annul_date;
	}

	public String getCfcAnnulDateFormatted()
	{
		return StringUtil.dbDateString2appDateString(getCfc_annul_date());
	}

	public void setCfc_annul_date(String cfc_annul_date)
	{
		this.cfc_annul_date = cfc_annul_date;
	}

	public void setCfcAnnulDateFormatted(String cfc_annul_date)
	{
		this.cfc_annul_date = StringUtil.appDateString2dbDateString(cfc_annul_date);
	}

	public String getCfc_check_price()
	{
		return cfc_check_price;
	}

	public void setCfc_check_price(String cfc_check_price)
	{
		this.cfc_check_price = cfc_check_price;
	}

	public String getCfc_check_price_date()
	{
		return cfc_check_price_date;
	}

	public void setCfc_check_price_date(String cfc_check_price_date)
	{
		this.cfc_check_price_date = cfc_check_price_date;
	}

	public String getUsr_id_check_price()
	{
		return usr_id_check_price;
	}

	public void setUsr_id_check_price(String usr_id_check_price)
	{
		this.usr_id_check_price = usr_id_check_price;
	}

	public String getCfc_doc_type1()
	{
		return cfc_doc_type1;
	}

	public void setCfc_doc_type1(String cfc_doc_type1)
	{
		this.cfc_doc_type1 = cfc_doc_type1;
	}

	public String getCfc_doc_type2()
	{
		return cfc_doc_type2;
	}

	public void setCfc_doc_type2(String cfc_doc_type2)
	{
		this.cfc_doc_type2 = cfc_doc_type2;
	}

	public Seller getSeller()
	{
		return seller;
	}

	public void setSeller(Seller seller)
	{
		this.seller = seller;
	}

	public String getCfc_delivery_count1()
	{
		return cfc_delivery_count1;
	}

	public void setCfc_delivery_count1(String cfc_delivery_count1)
	{
		this.cfc_delivery_count1 = cfc_delivery_count1;
	}

	public String getCfc_delivery_count2()
	{
		return cfc_delivery_count2;
	}

	public void setCfc_delivery_count2(String cfc_delivery_count2)
	{
		this.cfc_delivery_count2 = cfc_delivery_count2;
	}

	public String getConFinalDate()
	{
		return conFinalDate;
	}

	public void setConFinalDate(String conFinalDate)
	{
		this.conFinalDate = conFinalDate;
	}

	public DboAttachment getTemplateExcel()
	{
		return templateExcel;
	}

	public String getTemplateId()
	{
		if (null == templateExcel || null == templateExcel.getId())
		{
			return "";
		}
		return templateExcel.getId().toString();
	}

	public void setTemplateExcel(DboAttachment templateExcel)
	{
		this.templateExcel = templateExcel;
	}

	public boolean isAddSignPerson()
	{
		return addSignPerson;
	}

	public void setAddSignPerson(boolean addSignPerson)
	{
		this.addSignPerson = addSignPerson;
	}

	public boolean isFormReadOnly()
	{
		return formReadOnly;
	}

	public void setFormReadOnly(boolean formReadOnly)
	{
		this.formReadOnly = formReadOnly;
	}

	public boolean isReadOnlyForPlace()
	{
		return readOnlyForPlace;
	}

	public void setReadOnlyForPlace(boolean readOnlyForPlace)
	{
		this.readOnlyForPlace = readOnlyForPlace;
	}

	public boolean isReadOnlyForAnnul()
	{
		return readOnlyForAnnul;
	}

	public void setReadOnlyForAnnul(boolean readOnlyForAnnul)
	{
		this.readOnlyForAnnul = readOnlyForAnnul;
	}

	public boolean isShowMsg()
	{
		return showMsg;
	}

	public void setShowMsg(boolean showMsg)
	{
		this.showMsg = showMsg;
	}

	public boolean isReadOnlyAddPerson()
	{
		return formReadOnly || StringUtil.isEmpty(contractor.getId());
	}

	public boolean isShowFields()
	{
		return !StringUtil.isEmpty(cfc_doc_type1) || !StringUtil.isEmpty(cfc_doc_type2);
	}

	public boolean isShowTable()
	{
		return !StringUtil.isEmpty(getSeller().getId());
	}

	public boolean isShowForAdmin()
	{
		return showForAdmin;
	}

	public void setShowForAdmin(boolean showForAdmin)
	{
		this.showForAdmin = showForAdmin;
	}

	public boolean isShowDownload()
	{
		return !StringUtil.isEmpty(getTemplateId());
	}

	public HolderImplUsingList getGrid()
	{
		return grid;
	}

	public void setGrid(HolderImplUsingList grid)
	{
		this.grid = grid;
	}

	public HolderImplUsingList getAttachmentsGrid()
	{
		return attachmentsGrid;
	}

	public void setAttachmentsGrid(HolderImplUsingList attachmentsGrid)
	{
		this.attachmentsGrid = attachmentsGrid;
	}

	public DeferredAttachmentService getAttachmentService()
	{
		return attachmentService;
	}

	public void setAttachmentService(DeferredAttachmentService attachmentService)
	{
		this.attachmentService = attachmentService;
	}

	public String getAttachmentId()
	{
		return attachmentId;
	}

	public void setAttachmentId(String attachmentId)
	{
		this.attachmentId = attachmentId;
	}

	public String getPrintScale()
	{
		return printScale;
	}

	public void setPrintScale(String printScale)
	{
		this.printScale = printScale;
	}

	public boolean isReadOnlyForSave()
	{
		return readOnlyForSave;
	}

	public void setReadOnlyForSave(boolean readOnlyForSave)
	{
		this.readOnlyForSave = readOnlyForSave;
	}

	public boolean isUnitsPermittedForUsageInContracts()
	{
		return unitsPermittedForUsageInContracts;
	}

	public void setUnitsPermittedForUsageInContracts(boolean unitsPermittedForUsageInContracts)
	{
		this.unitsPermittedForUsageInContracts = unitsPermittedForUsageInContracts;
	}
}
