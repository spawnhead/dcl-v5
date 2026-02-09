package net.sam.dcl.form;

import net.sam.dcl.controller.BaseDispatchValidatorForm;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.controller.ActionContext;
import net.sam.dcl.beans.*;
import net.sam.dcl.util.ConstDefinitions;
import net.sam.dcl.util.StrutsUtil;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.dbo.DboProduce;
import net.sam.dcl.taglib.table.model.impl.HolderImplUsingList;
import net.sam.dcl.service.DeferredAttachmentService;
import org.apache.struts.action.ActionMapping;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.ArrayList;

/**
 * Date: Aug 6, 2005
 * Time: 5:37:29 PM
 */
public class ContractorRequestForm extends BaseDispatchValidatorForm
{
  protected static Log log = LogFactory.getLog(ContractorRequestForm.class);

  public static String pnpId = "1";
  public static String serviceId = "2";
  public static String guaranteeId = "3";

  String is_new_doc;
  String gen_num;
  String printLetterRequest;
  boolean needPrintLetterRequest;
  String printAct;
  boolean needPrintAct;
  String printEnumerationWork;
  boolean needPrintEnumerationWork;
  String printReclamationAct;
  boolean needPrintReclamationAct;
  String printSellerRequest;
  boolean needPrintSellerRequest;
  String printSellerAgreement;
  boolean needPrintSellerAgreement;
  String printPNPTimeSheet;
  boolean needPrintPNPTimeSheet;

  String crq_id;
  User createUser = new User();
  User editUser = new User();
  String usr_date_create;
  String usr_date_edit;
  String crq_number;
  String crq_ticket_number;
  String crq_comment;
  String crq_receive_date;
  Contractor contractor = new Contractor();
  ContactPerson contactPerson = new ContactPerson();
  ContractorRequestType requestType = new ContractorRequestType();
  String crq_deliver;
  String crq_annul;
  Contract contract = new Contract();
  Contract contractForWork = new Contract();

  //для пуско-наладочных работ поля
  Equipment equipment = new Equipment();
  String lps_id;
  String crq_equipment;
  String ctn_number;
  String stf_name;
  String lps_serial_num;
  String lps_year_out;

  Seller seller = new Seller();
  Contractor contractorOther = new Contractor();
  User manager = new User();
  User chief = new User();
  User specialist = new User();
  String crq_city;
  HolderImplUsingList gridStages = new HolderImplUsingList();
  String number;
  String comment;

  //гарантийных работ и сервисов поля (дополнительные)
  String crq_no_contract;

  StuffCategory stuffCategory = new StuffCategory();
  DboProduce produce = new DboProduce();
  String lps_enter_in_use_date;
  String mad_complexity;
  String crq_serial_num;
  String crq_year_out;
  String crq_enter_in_use_date;
  String crq_operating_time;

  //используются для печати акта
  String shp_date;
  String con_number;
  String con_date;
  String spc_number;
  String spc_date;
  String con_seller_id;
  String con_seller;

  //даты для различных печатей
  String crq_reclamation_date;
  String crq_lintera_request_date;
  String crq_defect_act;
  String crq_reclamation_act;
  String crq_lintera_agreement_date;
  String crq_lintera_agreement_date_dialog;
  String crqSellerAgreementDateDialog;
  boolean needDetailReturn;
  String visitId;

  boolean formReadOnly = false;
  boolean deliverReadOnly = false;
  boolean addAttachReadOnly = false;
  boolean contractReadOnly = false;
  boolean showForAdmin = false;
  boolean showForManager = false;
  boolean clearTables = false;

  HolderImplUsingList attachmentsGrid = new HolderImplUsingList();
  DeferredAttachmentService attachmentService = null;
  String attachmentId;

  List<ContractorRequestPrint> contractorRequestPrints = new ArrayList<ContractorRequestPrint>();
  HolderImplUsingList orders = new HolderImplUsingList();

  String letterScale = "100";
  String actScale = "100";

  public void reset(ActionMapping mapping, HttpServletRequest request)
  {
    if ( !isFormReadOnly() )
    {
      if ( isShowPNP() && isClearTables() )
      {
        for (int i = 0; i < gridStages.getDataList().size(); i++)
        {
          ContractorRequestStage contractorRequestStage = (ContractorRequestStage) gridStages.getDataList().get(i);
          contractorRequestStage.setNeedPrint("");
        }
      }
      if ( isShowServiceOrGuaranty() )
      {
        setCrq_no_contract("");
      }
    }
    if ( !isDeliverReadOnly() )
    {
      setCrq_deliver("");
      setCrq_annul("");
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

  public String getGen_num()
  {
    return gen_num;
  }

  public void setGen_num(String gen_num)
  {
    this.gen_num = gen_num;
  }

  public String getPrintLetterRequest()
  {
    return printLetterRequest;
  }

  public void setPrintLetterRequest(String printLetterRequest)
  {
    this.printLetterRequest = printLetterRequest;
  }

  public boolean isNeedPrintLetterRequest()
  {
    return needPrintLetterRequest;
  }

  public void setNeedPrintLetterRequest(boolean needPrintLetterRequest)
  {
    this.needPrintLetterRequest = needPrintLetterRequest;
  }

  public String getPrintAct()
  {
    return printAct;
  }

  public void setPrintAct(String printAct)
  {
    this.printAct = printAct;
  }

  public boolean isNeedPrintAct()
  {
    return needPrintAct;
  }

  public void setNeedPrintAct(boolean needPrintAct)
  {
    this.needPrintAct = needPrintAct;
  }

  public String getPrintEnumerationWork()
  {
    return printEnumerationWork;
  }

  public void setPrintEnumerationWork(String printEnumerationWork)
  {
    this.printEnumerationWork = printEnumerationWork;
  }

  public boolean isNeedPrintEnumerationWork()
  {
    return needPrintEnumerationWork;
  }

  public void setNeedPrintEnumerationWork(boolean needPrintEnumerationWork)
  {
    this.needPrintEnumerationWork = needPrintEnumerationWork;
  }

  public String getPrintReclamationAct()
  {
    return printReclamationAct;
  }

  public void setPrintReclamationAct(String printReclamationAct)
  {
    this.printReclamationAct = printReclamationAct;
  }

  public boolean isNeedPrintReclamationAct()
  {
    return needPrintReclamationAct;
  }

  public void setNeedPrintReclamationAct(boolean needPrintReclamationAct)
  {
    this.needPrintReclamationAct = needPrintReclamationAct;
  }

  public String getPrintSellerRequest()
  {
    return printSellerRequest;
  }

  public void setPrintSellerRequest(String printSellerRequest)
  {
    this.printSellerRequest = printSellerRequest;
  }

  public boolean isNeedPrintSellerRequest()
  {
    return needPrintSellerRequest;
  }

  public void setNeedPrintSellerRequest(boolean needPrintSellerRequest)
  {
    this.needPrintSellerRequest = needPrintSellerRequest;
  }

  public String getPrintSellerAgreement()
  {
    return printSellerAgreement;
  }

  public void setPrintSellerAgreement(String printSellerAgreement)
  {
    this.printSellerAgreement = printSellerAgreement;
  }

  public boolean isNeedPrintSellerAgreement()
  {
    return needPrintSellerAgreement;
  }

  public void setNeedPrintSellerAgreement(boolean needPrintSellerAgreement)
  {
    this.needPrintSellerAgreement = needPrintSellerAgreement;
  }

  public String getCrq_id()
  {
    return crq_id;
  }

  public void setCrq_id(String crq_id)
  {
    this.crq_id = crq_id;
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

  public String getCrq_number()
  {
    return crq_number;
  }

  public void setCrq_number(String crq_number)
  {
    this.crq_number = crq_number;
  }

  public String getCrq_ticket_number()
  {
    return crq_ticket_number;
  }

  public void setCrq_ticket_number(String crq_ticket_number)
  {
    this.crq_ticket_number = crq_ticket_number;
  }

  public String getCrq_comment()
  {
    return crq_comment;
  }

  public void setCrq_comment(String crq_comment)
  {
    this.crq_comment = crq_comment;
  }

  public String getCrq_receive_date()
  {
    return crq_receive_date;
  }

  public void setCrq_receive_date(String crq_receive_date)
  {
    this.crq_receive_date = crq_receive_date;
  }

  public Contractor getContractor()
  {
    return contractor;
  }

  public void setContractor(Contractor contractor)
  {
    this.contractor = contractor;
  }

  public ContactPerson getContactPerson()
  {
    return contactPerson;
  }

  public void setContactPerson(ContactPerson contactPerson)
  {
    this.contactPerson = contactPerson;
  }

  public ContractorRequestType getRequestType()
  {
    return requestType;
  }

  public void setRequestType(ContractorRequestType requestType)
  {
    this.requestType = requestType;
  }

  public String getRequestTypeIdCheck()
  {
    return requestType.getId();
  }

  public String getCrq_deliver()
  {
    return crq_deliver;
  }

  public void setCrq_deliver(String crq_deliver)
  {
    this.crq_deliver = crq_deliver;
  }

  public String getCrq_annul()
  {
    return crq_annul;
  }

  public void setCrq_annul(String crq_annul)
  {
    this.crq_annul = crq_annul;
  }

  public Contract getContract()
  {
    return contract;
  }

  public void setContract(Contract contract)
  {
    this.contract = contract;
  }

  public Contract getContractForWork()
  {
    return contractForWork;
  }

  public void setContractForWork(Contract contractForWork)
  {
    this.contractForWork = contractForWork;
  }

  public Equipment getEquipment()
  {
    return equipment;
  }

  public void setEquipment(Equipment equipment)
  {
    this.equipment = equipment;
  }

  public String getLps_id()
  {
    return lps_id;
  }

  public void setLps_id(String lps_id)
  {
    this.lps_id = lps_id;
  }

  public String getCrq_equipment()
  {
    return crq_equipment;
  }

  public void setCrq_equipment(String crq_equipment)
  {
    this.crq_equipment = crq_equipment;
  }

  public String getCtn_number()
  {
    return ctn_number;
  }

  public void setCtn_number(String ctn_number)
  {
    this.ctn_number = ctn_number;
  }

  public String getStf_name()
  {
    return stf_name;
  }

  public void setStf_name(String stf_name)
  {
    this.stf_name = stf_name;
  }

  public String getLps_serial_num()
  {
    return lps_serial_num;
  }

  public void setLps_serial_num(String lps_serial_num)
  {
    this.lps_serial_num = lps_serial_num;
  }

  public String getLps_year_out()
  {
    return lps_year_out;
  }

  public void setLps_year_out(String lps_year_out)
  {
    this.lps_year_out = lps_year_out;
  }

  public String getUnknownSeller()
  {
    if ( !StringUtil.isEmpty(crq_no_contract) || "2".equals(getSeller().getId()) || StringUtil.isEmpty(getSeller().getId()) )
    {
      return "1";
    }

    return "";
  }

  public String getSellerType0()
  {
    if ( !"1".equals(getSeller().getId()) &&  !"2".equals(getSeller().getId()))
    {
      return "1";
    }

    return "";
  }

  public Seller getSeller()
  {
    return seller;
  }

  public void setSeller(Seller seller)
  {
    this.seller = seller;
  }

  public Contractor getContractorOther()
  {
    return contractorOther;
  }

  public void setContractorOther(Contractor contractorOther)
  {
    this.contractorOther = contractorOther;
  }

  public User getManager()
  {
    return manager;
  }

  public void setManager(User manager)
  {
    this.manager = manager;
  }

  public User getChief()
  {
    return chief;
  }

  public void setChief(User chief)
  {
    this.chief = chief;
  }

  public User getSpecialist()
  {
    return specialist;
  }

  public void setSpecialist(User specialist)
  {
    this.specialist = specialist;
  }

  public String getCrq_city()
  {
    return crq_city;
  }

  public void setCrq_city(String crq_city)
  {
    this.crq_city = crq_city;
  }

  public HolderImplUsingList getGridStages()
  {
    return gridStages;
  }

  public void setGridStages(HolderImplUsingList gridStages)
  {
    this.gridStages = gridStages;
  }

  public String getNumber()
  {
    return number;
  }

  public void setNumber(String number)
  {
    this.number = number;
  }

  public String getComment()
  {
    return comment;
  }

  public void setComment(String comment)
  {
    this.comment = comment;
  }

  public String getCrq_no_contract()
  {
    return crq_no_contract;
  }

  public void setCrq_no_contract(String crq_no_contract)
  {
    this.crq_no_contract = crq_no_contract;
  }

  public StuffCategory getStuffCategory()
  {
    return stuffCategory;
  }

  public void setStuffCategory(StuffCategory stuffCategory)
  {
    this.stuffCategory = stuffCategory;
  }

  public DboProduce getProduce()
  {
    return produce;
  }

  public void setProduce(DboProduce produce)
  {
    this.produce = produce;
  }

  public String getLps_enter_in_use_date()
  {
    return lps_enter_in_use_date;
  }

  public void setLps_enter_in_use_date(String lps_enter_in_use_date)
  {
    this.lps_enter_in_use_date = lps_enter_in_use_date;
  }

  public String getMad_complexity()
  {
    return mad_complexity;
  }

  public void setMad_complexity(String mad_complexity)
  {
    this.mad_complexity = mad_complexity;
  }

  public String getCrq_serial_num()
  {
    return crq_serial_num;
  }

  public void setCrq_serial_num(String crq_serial_num)
  {
    this.crq_serial_num = crq_serial_num;
  }

  public String getCrq_year_out()
  {
    return crq_year_out;
  }

  public void setCrq_year_out(String crq_year_out)
  {
    this.crq_year_out = crq_year_out;
  }

  public String getCrq_enter_in_use_date()
  {
    return crq_enter_in_use_date;
  }

  public void setCrq_enter_in_use_date(String crq_enter_in_use_date)
  {
    this.crq_enter_in_use_date = crq_enter_in_use_date;
  }

  public String getCrq_operating_time()
  {
    return crq_operating_time;
  }

  public void setCrq_operating_time(String crq_operating_time)
  {
    this.crq_operating_time = crq_operating_time;
  }

  public String getShp_date()
  {
    return shp_date;
  }

  public void setShp_date(String shp_date)
  {
    this.shp_date = shp_date;
  }

  public String getCon_number()
  {
    return con_number;
  }

  public void setCon_number(String con_number)
  {
    this.con_number = con_number;
  }

  public String getCon_date()
  {
    return con_date;
  }

  public void setCon_date(String con_date)
  {
    this.con_date = con_date;
  }

  public String getSpc_number()
  {
    return spc_number;
  }

  public void setSpc_number(String spc_number)
  {
    this.spc_number = spc_number;
  }

  public String getSpc_date()
  {
    return spc_date;
  }

  public void setSpc_date(String spc_date)
  {
    this.spc_date = spc_date;
  }

	public String getCon_seller_id()
	{
		return con_seller_id;
	}

	public void setCon_seller_id(String con_seller_id)
	{
		this.con_seller_id = con_seller_id;
	}

	public String getCon_seller()
  {
    return con_seller;
  }

  public void setCon_seller(String con_seller)
  {
    this.con_seller = con_seller;
  }

  public String getCrq_reclamation_date()
  {
    return crq_reclamation_date;
  }

  public void setCrq_reclamation_date(String crq_reclamation_date)
  {
    this.crq_reclamation_date = crq_reclamation_date;
  }

  public String getCrq_defect_act()
  {
    return crq_defect_act;
  }

  public void setCrq_defect_act(String crq_defect_act)
  {
    this.crq_defect_act = crq_defect_act;
  }

  public String getCrq_reclamation_act()
  {
    return crq_reclamation_act;
  }

  public void setCrq_reclamation_act(String crq_reclamation_act)
  {
    this.crq_reclamation_act = crq_reclamation_act;
  }

  public String getCrq_lintera_request_date()
  {
    return crq_lintera_request_date;
  }

  public void setCrq_lintera_request_date(String crq_lintera_request_date)
  {
    this.crq_lintera_request_date = crq_lintera_request_date;
  }

  public String getCrq_lintera_agreement_date()
  {
    return crq_lintera_agreement_date;
  }

  public void setCrq_lintera_agreement_date(String crq_lintera_agreement_date)
  {
    this.crq_lintera_agreement_date = crq_lintera_agreement_date;
  }

  public String getCrq_lintera_agreement_date_dialog()
  {
    return crq_lintera_agreement_date_dialog;
  }

  public void setCrq_lintera_agreement_date_dialog(String crq_lintera_agreement_date_dialog)
  {
    this.crq_lintera_agreement_date_dialog = crq_lintera_agreement_date_dialog;
  }

  public String getCrqSellerAgreementDateDialog()
  {
    return crqSellerAgreementDateDialog;
  }

  public void setCrqSellerAgreementDateDialog(String crqSellerAgreementDateDialog)
  {
    this.crqSellerAgreementDateDialog = crqSellerAgreementDateDialog;
  }

  public boolean isNeedDetailReturn()
  {
    return needDetailReturn;
  }

  public void setNeedDetailReturn(boolean needDetailReturn)
  {
    this.needDetailReturn = needDetailReturn;
  }

  public String getVisitId()
  {
    return visitId;
  }

  public void setVisitId(String visitId)
  {
    this.visitId = visitId;
  }

  public boolean isFormReadOnly()
  {
    return formReadOnly;
  }

  public void setFormReadOnly(boolean formReadOnly)
  {
    this.formReadOnly = formReadOnly;
  }

  public boolean isDeliverReadOnly()
  {
    return deliverReadOnly;
  }

  public void setDeliverReadOnly(boolean deliverReadOnly)
  {
    this.deliverReadOnly = deliverReadOnly;
  }

  public boolean isAddAttachReadOnly()
  {
    return addAttachReadOnly;
  }

  public void setAddAttachReadOnly(boolean addAttachReadOnly)
  {
    this.addAttachReadOnly = addAttachReadOnly;
  }

  public boolean isContractReadOnly()
  {
    return contractReadOnly || formReadOnly || !isShowContractEquipment();
  }

  public void setContractReadOnly(boolean contractReadOnly)
  {
    this.contractReadOnly = contractReadOnly;
  }

  public boolean isReadOnlySave()
  {
    return isFormReadOnly() & isDeliverReadOnly() & isAddAttachReadOnly() & isContractReadOnly(); 
  }

  public boolean isShowForAdmin()
  {
    return showForAdmin;
  }

  public void setShowForAdmin(boolean showForAdmin)
  {
    this.showForAdmin = showForAdmin;
  }

  public boolean isShowForManager()
  {
    return showForManager;
  }

  public void setShowForManager(boolean showForManager)
  {
    this.showForManager = showForManager;
  }

  public boolean isClearTables()
  {
    return clearTables;
  }

  public void setClearTables(boolean clearTables)
  {
    this.clearTables = clearTables;
  }

  public boolean isShowPNP()
  {
    boolean show = false;
    IActionContext context = ActionContext.threadInstance();
    try
    {
      show = requestType.getId().equals(StrutsUtil.getMessage(context, "ctr_request_type_list.pnp_id"));
    }
    catch (Exception e)
    {
      log.error(e);
    }
    return show;
  }

  public boolean isShowService()
  {
    boolean show = false;
    IActionContext context = ActionContext.threadInstance();
    try
    {
      show = requestType.getId().equals(StrutsUtil.getMessage(context, "ctr_request_type_list.service_id"));
    }
    catch (Exception e)
    {
      log.error(e);
    }
    return show;
  }

  public boolean isShowGuaranty()
  {
    boolean show = false;
    IActionContext context = ActionContext.threadInstance();
    try
    {
      show = requestType.getId().equals(StrutsUtil.getMessage(context, "ctr_request_type_list.guarantee_id"));
    }
    catch (Exception e)
    {
      log.error(e);
    }
    return show;
  }

  public boolean isShowServiceOrGuaranty()
  {
    return isShowService() || isShowGuaranty();
  }

  public boolean isShowContractEquipment()
  {
    return isShowPNP() || ( isShowServiceOrGuaranty() && StringUtil.isEmpty(getCrq_no_contract()) );
  }

  public boolean isShowEquipmentFromProduce()
  {
    return isShowServiceOrGuaranty() && !StringUtil.isEmpty(getCrq_no_contract());
  }

  public String getContractNumberWithDate()
  {
    if ( StringUtil.isEmpty(getContract().getCon_number()) )
    {
      return "";
    }

    return getContract().getNumberWithDate();
  }

  public String getContractForWorkNumberWithDate()
  {
    if ( StringUtil.isEmpty(getContractForWork().getCon_number()) )
    {
      return "";
    }

    return getContractForWork().getNumberWithDate();
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

  public List<ContractorRequestPrint> getContractorRequestPrints()
  {
    return contractorRequestPrints;
  }

  public void setContractorRequestPrints(List<ContractorRequestPrint> contractorRequestPrints)
  {
    this.contractorRequestPrints = contractorRequestPrints;
  }

  public HolderImplUsingList getOrders()
  {
    return orders;
  }

  public void setOrders(HolderImplUsingList orders)
  {
    this.orders = orders;
  }

  public String getLetterScale()
  {
    return letterScale;
  }

  public void setLetterScale(String letterScale)
  {
    this.letterScale = letterScale;
  }

  public String getActScale()
  {
    return actScale;
  }

  public void setActScale(String actScale)
  {
    this.actScale = actScale;
  }

  public boolean isNeedPrintPNPTimeSheet() {
    return needPrintPNPTimeSheet;
  }

  public void setNeedPrintPNPTimeSheet(boolean needPrintPNPTimeSheet) {
    this.needPrintPNPTimeSheet = needPrintPNPTimeSheet;
  }

  public String getPrintPNPTimeSheet() {
    return printPNPTimeSheet;
  }

  public void setPrintPNPTimeSheet(String printPNPTimeSheet) {
    this.printPNPTimeSheet = printPNPTimeSheet;
  }
}