package net.sam.dcl.form;

import net.sam.dcl.beans.*;
import net.sam.dcl.controller.ActionContext;
import net.sam.dcl.controller.BaseDispatchValidatorForm;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.StrutsUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;

public class ContractorRequestPrintActForm extends BaseDispatchValidatorForm
{
  protected static Log log = LogFactory.getLog(CalculationStateLine.class);

  Blank blank;
  String crq_number;
  String crq_ticket_number;
  String crq_receive_date;
  Contractor contractor = new Contractor();
  ContactPerson contactPerson = new ContactPerson();
  Contract contract = new Contract();
  Contract contractForWork = new Contract();
  String crq_equipment;
  String ctn_number;
  String stf_name;
  String lps_serial_num;
  String lps_year_out;
  String crq_seller;
  User manager = new User();
  User chief = new User();
  User specialist = new User();
  String crq_city;
  List stages = new ArrayList();

  //гарантийных работ и сервисов поля (дополнительные)
  String crq_operating_time;
  
  String lps_enter_in_use_date;
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
  String crqSellerAgreementDateDialog;
  boolean needDetailReturn;
  String visitId;
  Contractor contractorWhere = new Contractor();

  boolean isPNP = false;
  boolean isService = false;
  boolean isPNPTimeSheet = false;
  boolean printExecutedWorkAct = false;
  boolean printEnumerationWork = false;
  boolean printReclamationAct = false;
  boolean printSellerRequest = false;
  boolean printSellerAgreement = false;

  int visitNumber = 0;
  float printScale;

  public Blank getBlank()
  {
    return blank;
  }

  public void setBlank(Blank blank)
  {
    this.blank = blank;
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

  public String getCrq_seller()
  {
    return crq_seller;
  }

  public void setCrq_seller(String crq_seller)
  {
    this.crq_seller = crq_seller;
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

  public List getStages()
  {
    return stages;
  }

  public void setStages(List stages)
  {
    this.stages = stages;
  }

  public String getCrq_operating_time()
  {
    return crq_operating_time;
  }

  public void setCrq_operating_time(String crq_operating_time)
  {
    this.crq_operating_time = crq_operating_time;
  }

  public String getLps_enter_in_use_date()
  {
    return lps_enter_in_use_date;
  }

  public void setLps_enter_in_use_date(String lps_enter_in_use_date)
  {
    this.lps_enter_in_use_date = lps_enter_in_use_date;
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

  public String getCon_spc_number_date()
  {
    String retStr = "";
    IActionContext context = ActionContext.threadInstance();

    try
    {
      if ( !StringUtil.isEmpty(getCon_number()) )
      {
        retStr += StrutsUtil.getMessage(context, "msg.common.from", getCon_number(), getCon_date());
      }
      if ( !StringUtil.isEmpty(getCon_number()) && !StringUtil.isEmpty(getSpc_number()) )
      {
        retStr += ", ";
      }
      if ( !StringUtil.isEmpty(getSpc_number()) )
      {
        retStr += StrutsUtil.getMessage(context, "msg.common.from", getSpc_number(), getSpc_date());
      }
    }
    catch (Exception e)
    {
      log.error(e);
    }
    
    return retStr;
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

  public boolean isNeedDefectAct()
  {
    return !StringUtil.isEmpty(crq_defect_act);
  }

  public boolean isNeedReclamationAct()
  {
    return !StringUtil.isEmpty(crq_reclamation_act);
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

  public Contractor getContractorWhere()
  {
    return contractorWhere;
  }

  public void setContractorWhere(Contractor contractorWhere)
  {
    this.contractorWhere = contractorWhere;
  }

  public boolean isPNP()
  {
    return isPNP;
  }

  public void setPNP(boolean PNP)
  {
    isPNP = PNP;
  }

  public boolean isService()
  {
    return isService;
  }

  public void setService(boolean service)
  {
    isService = service;
  }

  public boolean isPrintExecutedWorkAct()
  {
    return printExecutedWorkAct;
  }

  public void setPrintExecutedWorkAct(boolean printExecutedWorkAct)
  {
    this.printExecutedWorkAct = printExecutedWorkAct;
  }

  public boolean isPrintEnumerationWork()
  {
    return printEnumerationWork;
  }

  public void setPrintEnumerationWork(boolean printEnumerationWork)
  {
    this.printEnumerationWork = printEnumerationWork;
  }

  public boolean isPrintReclamationAct()
  {
    return printReclamationAct;
  }

  public void setPrintReclamationAct(boolean printReclamationAct)
  {
    this.printReclamationAct = printReclamationAct;
  }

  public boolean isPrintSellerRequest()
  {
    return printSellerRequest;
  }

  public void setPrintSellerRequest(boolean printSellerRequest)
  {
    this.printSellerRequest = printSellerRequest;
  }

  public boolean isPrintSellerAgreement()
  {
    return printSellerAgreement;
  }

  public void setPrintSellerAgreement(boolean printSellerAgreement)
  {
    this.printSellerAgreement = printSellerAgreement;
  }

  public float getPrintScale()
  {
    return printScale;
  }

  public void setPrintScale(float printScale)
  {
    this.printScale = printScale;
  }

  public boolean isPNPTimeSheet() {
    return isPNPTimeSheet;
  }

  public void setPNPTimeSheet(boolean PNPTimeSheet) {
    isPNPTimeSheet = PNPTimeSheet;
  }

  public int getVisitNumber() {
    return visitNumber;
  }

  public void setVisitNumber(int visitNumber) {
    this.visitNumber = visitNumber;
  }
}