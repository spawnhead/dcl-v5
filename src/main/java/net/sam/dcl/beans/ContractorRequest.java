package net.sam.dcl.beans;

import net.sam.dcl.controller.ActionContext;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.dbo.DboProduce;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.StrutsUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Date: 31.08.2005
 * Time: 18:53:07
 * To change this template use File | Settings | File Templates.
 */
public class ContractorRequest implements Serializable
{
  protected static Log log = LogFactory.getLog(ContractorRequest.class);

  String is_new_doc;

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
  List<ContractorRequestStage> stages = new ArrayList<ContractorRequestStage>();
  List<Order> orders = new ArrayList<Order>();

  //гарантийных работ и сервисов поля (дополнительные)
  String crq_no_contract;

  StuffCategory stuffCategory = new StuffCategory();
  DboProduce produce = new DboProduce();
  String lps_enter_in_use_date;
  String mad_complexity;
  String crq_serial_num;
  String crq_year_out;
  String crq_enter_in_use_date;
  Double crq_operating_time;

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
  String visitId;

  List<ContractorRequestPrint> contractorRequestPrints = new ArrayList<ContractorRequestPrint>();

  String letterScale = "100";
  String actScale = "100";

  public ContractorRequest()
  {
  }

  public ContractorRequest(ContractorRequest contractorRequest)
  {
    this.crq_id = contractorRequest.getCrq_id();
    this.createUser = new User(contractorRequest.getCreateUser());
    this.editUser = new User(contractorRequest.getEditUser());
    this.usr_date_create = contractorRequest.getUsr_date_create();
    this.usr_date_edit = contractorRequest.getUsr_date_edit();
    this.crq_number = contractorRequest.getCrq_number();
    this.crq_ticket_number = contractorRequest.getCrq_ticket_number();
    this.crq_comment = contractorRequest.getCrq_comment();
    this.crq_receive_date = contractorRequest.getCrq_receive_date();
    this.contractor = new Contractor(contractorRequest.getContractor());
    this.contactPerson = new ContactPerson(contractorRequest.getContactPerson());
    this.requestType = new ContractorRequestType(contractorRequest.getRequestType());
    this.crq_deliver = contractorRequest.getCrq_deliver();
    this.crq_annul = contractorRequest.getCrq_annul();
    this.contract = new Contract(contractorRequest.getContract());
    this.contractForWork = new Contract(contractorRequest.getContractForWork());

    //для пуско-наладочных работ поля
    this.lps_id = contractorRequest.getLps_id();
    this.crq_equipment = contractorRequest.getCrq_equipment();
    this.ctn_number = contractorRequest.getCtn_number();
    this.stf_name = contractorRequest.getStf_name();
    this.lps_serial_num = contractorRequest.getLps_serial_num();
    this.lps_year_out = contractorRequest.getLps_year_out();

    this.seller = new Seller(contractorRequest.getSeller());
    this.contractorOther = new Contractor(contractorRequest.getContractorOther());
    this.manager = new User(contractorRequest.getManager());
    this.chief = new User(contractorRequest.getChief());
    this.specialist = new User(contractorRequest.getSpecialist());
    this.crq_city = contractorRequest.getCrq_city();
    this.stages = contractorRequest.getStages();
    this.orders = contractorRequest.getOrders();

    //гарантийных работ и сервисов поля (дополнительные)
    this.crq_no_contract = contractorRequest.getCrq_no_contract();

    this.stuffCategory = new StuffCategory(contractorRequest.getStuffCategory());
    this.produce = contractorRequest.getProduce();
    this.lps_enter_in_use_date = contractorRequest.getLps_enter_in_use_date();
    this.mad_complexity = contractorRequest.getMad_complexity();
    this.crq_serial_num = contractorRequest.getCrq_serial_num();
    this.crq_year_out = contractorRequest.getCrq_year_out();
    this.crq_enter_in_use_date = contractorRequest.getCrq_enter_in_use_date();
    this.crq_operating_time = contractorRequest.getCrq_operating_time();

    //используются для печати акта
    this.shp_date = contractorRequest.getShp_date();
    this.con_number = contractorRequest.getCon_number();
    this.con_date = contractorRequest.getCon_date();
    this.spc_number = contractorRequest.getSpc_number();
    this.spc_date = contractorRequest.getSpc_date();
    this.con_seller_id = contractorRequest.getCon_seller_id();
    this.con_seller = contractorRequest.getCon_seller();

    //даты для различных печатей
    this.crq_reclamation_date = contractorRequest.getCrq_reclamation_date();
    this.crq_lintera_request_date = contractorRequest.getCrq_lintera_request_date();
    this.crq_defect_act = contractorRequest.getCrq_defect_act();
    this.crq_reclamation_act = contractorRequest.getCrq_reclamation_act();
    this.crq_lintera_agreement_date = contractorRequest.getCrq_lintera_agreement_date();
    this.visitId = contractorRequest.getVisitId();
  }

  public ContractorRequest(String cfc_id)
  {
    this.crq_id = cfc_id;
  }

  public String getIs_new_doc()
  {
    return is_new_doc;
  }

  public void setIs_new_doc(String is_new_doc)
  {
    this.is_new_doc = is_new_doc;
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

  public String getCrq_receive_date_ts()
  {
    return StringUtil.appDateString2dbDateString(crq_receive_date);
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

  public String getCrq_deliver()
  {
    return crq_deliver;
  }

  public void setCrq_deliver(String crq_deliver)
  {
    this.crq_deliver = crq_deliver;
  }

  public String getCrqNumberDeliver()
  {
    if ( StringUtil.isEmpty(crq_number) )
    {
      return "";
    }

    String retStr = crq_number;
    IActionContext context = ActionContext.threadInstance();
    try
    {
      if ( StringUtil.isEmpty(crq_deliver) )
      {
        retStr += StrutsUtil.getMessage(context, "TimeboardWorks.tbw_crq_deliver");
      }
    }
    catch (Exception e)
    {
      log.error(e);
    }

    return retStr;
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

  public List<ContractorRequestStage> getStages()
  {
    return stages;
  }

  public void setStages(List<ContractorRequestStage> stages)
  {
    this.stages = stages;
  }

  public List<Order> getOrders()
  {
    return orders;
  }

  public void setOrders(List<Order> orders)
  {
    this.orders = orders;
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

  public String getCrq_enter_in_use_date_ts()
  {
    return StringUtil.appDateString2dbDateString(crq_enter_in_use_date);
  }

  public void setCrq_enter_in_use_date(String crq_enter_in_use_date)
  {
    this.crq_enter_in_use_date = crq_enter_in_use_date;
  }

  public Double getCrq_operating_time()
  {
    return crq_operating_time;
  }

  public void setCrq_operating_time(Double crq_operating_time)
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

  public String getVisitId()
  {
    return visitId;
  }

  public void setVisitId(String visitId)
  {
    this.visitId = visitId;
  }

  public List<ContractorRequestPrint> getContractorRequestPrints()
  {
    return contractorRequestPrints;
  }

  public void setContractorRequestPrints(List<ContractorRequestPrint> contractorRequestPrints)
  {
    this.contractorRequestPrints = contractorRequestPrints;
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

  /**
   * inList - true для журнала
   *          false для отображения в форме
  *
   * @param requestTypeId - иденитфикатор типа запроса.
   * @param inList - в списке или нет.
   * @return - строка для локализации.*/
  public static String getRequestTypeName(String requestTypeId, boolean inList)
  {
    String retStr = "";
    IActionContext context = ActionContext.threadInstance();
    try
    {
      if ( requestTypeId.equals(StrutsUtil.getMessage(context, "ctr_request_type_list.pnp_id")) )
      {
        if ( inList )
        {
          retStr = StrutsUtil.getMessage(context, "ctr_request_type_list.pnp_out");
        }
        else
        {
          retStr = StrutsUtil.getMessage(context, "ctr_request_type_list.pnp");
        }
      }
      if ( requestTypeId.equals(StrutsUtil.getMessage(context, "ctr_request_type_list.service_id")) )
      {
        if ( inList )
        {
          retStr = StrutsUtil.getMessage(context, "ctr_request_type_list.service_out");
        }
        else
        {
          retStr = StrutsUtil.getMessage(context, "ctr_request_type_list.service");
        }
      }
      if ( requestTypeId.equals(StrutsUtil.getMessage(context, "ctr_request_type_list.guarantee_id")) )
      {
        if ( inList )
        {
          retStr = StrutsUtil.getMessage(context, "ctr_request_type_list.guarantee_out");
        }
        else
        {
          retStr = StrutsUtil.getMessage(context, "ctr_request_type_list.guarantee");
        }
      }
    }
    catch (Exception e)
    {
      log.error(e);
    }

    return retStr;
  }

  public void setNumbers()
  {
    for (int i = 0; i < stages.size(); i++)
    {
      ContractorRequestStage contractorRequestStage = stages.get(i);
      contractorRequestStage.setNumber(Integer.toString(i + 1));
    }
  }

  public void updateComment(String number, String comment)
  {
    for (ContractorRequestStage contractorRequestStage : stages)
    {
      if (contractorRequestStage.getNumber().equalsIgnoreCase(number))
      {
        contractorRequestStage.setComment(comment);
        return;
      }
    }
  }

  public void setListParentIds()
  {
    for (ContractorRequestStage contractorRequestStage : stages)
    {
      contractorRequestStage.setParentId(getCrq_id());
    }

    for (ContractorRequestPrint contractorRequestPrint : contractorRequestPrints)
    {
      contractorRequestPrint.setCrq_id(getCrq_id());
    }
  }

  public void setListIdsToNull()
  {
    for (ContractorRequestStage contractorRequestStage : getStages())
    {
      contractorRequestStage.setId(null);
    }

    for (ContractorRequestPrint contractorRequestPrint : getContractorRequestPrints())
    {
      contractorRequestPrint.setCrp_id(null);
    }
  }

  public boolean addOrder(Order orderIn)
  {
    for (Order order : getOrders())
    {
      if (order.getOrd_id().equalsIgnoreCase(orderIn.getOrd_id()))
      {
        //заказ уже есть в списке - добавлять не нужно - выдаем сообщение.
        return false;
      }
    }

    getOrders().add(orderIn);

    return true;
  }

  public void deleteOrder(String ordId)
  {
    for (Order order : getOrders())
    {
      if (order.getOrd_id().equalsIgnoreCase(ordId))
      {
        getOrders().remove(order);
        break;
      }
    }
  }
}