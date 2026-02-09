package net.sam.dcl.beans;

import net.sam.dcl.controller.ActionContext;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.dbo.DboCatalogNumber;
import net.sam.dcl.dbo.DboProduce;
import net.sam.dcl.util.HibernateUtil;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.StrutsUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * Date: 14.09.2005
 * Time: 17:17:20
 * To change this template use File | Settings | File Templates.
 */
public class DeliveryRequestProduce implements Serializable
{
  public static String errorDateFormat = "Error Date Format";
  protected static String replacementString = "from";

  protected static Log log = LogFactory.getLog(DeliveryRequestProduce.class);
  String id;

  String drp_id;
  String dlr_id;
  String number;
  DboProduce produce = new DboProduce();
  double drp_price;
  double drp_count;
  StuffCategory stuffCategory = new StuffCategory();
  String opr_id;
  String asm_id;
  String apr_id;
  String ord_number;
  String ord_date;
  String ordInfoAssemble;
  String customerAssemble;
  String sip_id;
  String spi_number;
  String spi_date;
  Contractor customer = new Contractor();
  Contract contract = new Contract();
  Specification specification = new Specification();
  Purpose purpose = new Purpose();
  String drp_purpose;
  String drp_occupied;
  User receiveManager = new User();
  String receive_date;
  String drp_max_extra;

  double opr_count_executed;
  double apr_count;
  double sip_count;

  String dlr_fair_trade;
  String dlr_need_deliver;
  String dlr_include_in_spec;
  String specificationNumbers;
  String dlr_ord_not_form;
  String drp_have_depend;

  public DeliveryRequestProduce()
  {
  }

  public DeliveryRequestProduce(String drp_id)
  {
    this.drp_id = drp_id;
  }

  public DeliveryRequestProduce(String drp_id, String opr_id)
  {
    this.drp_id = drp_id;
    this.opr_id = opr_id;
  }

  public DeliveryRequestProduce(String drp_id, String opr_id, String sip_id)
  {
    this.drp_id = drp_id;
    this.opr_id = opr_id;
    this.sip_id = sip_id;
  }

  public DeliveryRequestProduce(String drp_id, String opr_id, String apr_id, String sip_id)
  {
    this.drp_id = drp_id;
    this.opr_id = opr_id;
    this.apr_id = apr_id;
    this.sip_id = sip_id;
  }

  public DeliveryRequestProduce(String drp_id, String opr_id, String asm_id, String apr_id, String sip_id)
  {
    this.drp_id = drp_id;
    this.opr_id = opr_id;
    this.asm_id = asm_id;
    this.apr_id = apr_id;
    this.sip_id = sip_id;
  }

  public DeliveryRequestProduce(DeliveryRequestProduce deliveryRequestProduce)
  {
    id = deliveryRequestProduce.getId();

    drp_id = deliveryRequestProduce.getDrp_id();
    dlr_id = deliveryRequestProduce.getDlr_id();
    number = deliveryRequestProduce.getNumber();
    produce = deliveryRequestProduce.getProduce();
    drp_price = deliveryRequestProduce.getDrp_price();
    drp_count = deliveryRequestProduce.getDrp_count();
    stuffCategory = new StuffCategory(deliveryRequestProduce.getStuffCategory());
    opr_id = deliveryRequestProduce.getOpr_id();
    asm_id = deliveryRequestProduce.getAsm_id();
    apr_id = deliveryRequestProduce.getApr_id();
    ord_number = deliveryRequestProduce.getOrd_number();
    ord_date = deliveryRequestProduce.getOrd_date();
    ordInfoAssemble = deliveryRequestProduce.getOrdInfoAssemble();
    customerAssemble = deliveryRequestProduce.getCustomerAssemble();
    sip_id = deliveryRequestProduce.getSip_id();
    spi_number = deliveryRequestProduce.getSpi_number();
    spi_date = deliveryRequestProduce.getSpi_date();
    customer = new Contractor(deliveryRequestProduce.getCustomer());
    contract = new Contract(deliveryRequestProduce.getContract());
    specification = new Specification(deliveryRequestProduce.getSpecification());
    purpose = new Purpose(deliveryRequestProduce.getPurpose());
    drp_purpose = deliveryRequestProduce.getDrp_purpose();
    drp_occupied = deliveryRequestProduce.getDrp_occupied();
    receiveManager = new User(deliveryRequestProduce.getReceiveManager());
    receive_date = deliveryRequestProduce.getReceive_date();
    drp_max_extra = deliveryRequestProduce.getDrp_max_extra();
    dlr_fair_trade = deliveryRequestProduce.getDlr_fair_trade();
    dlr_need_deliver = deliveryRequestProduce.getDlr_need_deliver();
    dlr_include_in_spec = deliveryRequestProduce.getDlr_include_in_spec();
    specificationNumbers = deliveryRequestProduce.getSpecificationNumbers();
    dlr_ord_not_form = deliveryRequestProduce.getDlr_ord_not_form();
    drp_have_depend = deliveryRequestProduce.getDrp_have_depend();

    opr_count_executed = deliveryRequestProduce.getOpr_count_executed();
    apr_count = deliveryRequestProduce.getApr_count();
    sip_count = deliveryRequestProduce.getSip_count();
  }

  public String getId()
  {
    return id;
  }

  public void setId(String id)
  {
    this.id = id;
  }

  public String getDrp_id()
  {
    return drp_id;
  }

  public void setDrp_id(String drp_id)
  {
    this.drp_id = drp_id;
  }

  public String getDlr_id()
  {
    return dlr_id;
  }

  public void setDlr_id(String dlr_id)
  {
    this.dlr_id = dlr_id;
  }

  public String getNumber()
  {
    return number;
  }

  public void setNumber(String number)
  {
    this.number = number;
  }

  public DboProduce getProduce()
  {
    return produce;
  }

  public void setProduce(DboProduce produce)
  {
    this.produce = produce;
  }

  public double getDrp_price()
  {
    return drp_price;
  }

  public String getDrp_price_formatted()
  {
    return StringUtil.double2appCurrencyString(drp_price);
  }

  public void setDrp_price(double drp_price)
  {
    this.drp_price = drp_price;
  }

  public double getDrp_count()
  {
    return drp_count;
  }

  public String getDrp_count_formatted()
  {
    return StringUtil.double2appCurrencyString(drp_count);
  }

  public void setDrp_count(double drp_count)
  {
    this.drp_count = drp_count;
  }

  public StuffCategory getStuffCategory()
  {
    return stuffCategory;
  }

  public void setStuffCategory(StuffCategory stuffCategory)
  {
    this.stuffCategory = stuffCategory;
  }

  public String getOpr_id()
  {
    return opr_id;
  }

  public void setOpr_id(String opr_id)
  {
    this.opr_id = opr_id;
  }

  public String getAsm_id()
  {
    return asm_id;
  }

  public void setAsm_id(String asm_id)
  {
    this.asm_id = asm_id;
  }

  public String getApr_id()
  {
    return apr_id;
  }

  public void setApr_id(String apr_id)
  {
    this.apr_id = apr_id;
  }

  public String getOrd_number()
  {
    return ord_number;
  }

  public void setOrd_number(String ord_number)
  {
    this.ord_number = ord_number;
  }

  public String getOrd_date()
  {
    return ord_date;
  }

  public void setOrd_date(String ord_date)
  {
    this.ord_date = ord_date;
  }

  public String getOrdInfoAssemble()
  {
    String resStr = ordInfoAssemble;
    IActionContext context = ActionContext.threadInstance();
    try
    {
      String from = " " + StrutsUtil.getMessage(context, "msg.common.from_only") + " ";
      resStr = resStr.replaceAll(replacementString, from);
    }
    catch (Exception e)
    {
      log.error(e);
    }
    return resStr;
  }

  public void setOrdInfoAssemble(String ordInfoAssemble)
  {
    this.ordInfoAssemble = ordInfoAssemble;
  }

  public String getCustomerAssemble()
  {
    return customerAssemble;
  }

  public void setCustomerAssemble(String customerAssemble)
  {
    this.customerAssemble = customerAssemble;
  }

  public String getOrd_number_date()
  {
    String retStr = "";

    if (!StringUtil.isEmpty(getAsm_id()))
    {
      return getOrdInfoAssemble();
    }

    if (StringUtil.isEmpty(ord_number) && StringUtil.isEmpty(ord_date))
    {
      retStr = "";
    }
    else
    {
      IActionContext context = ActionContext.threadInstance();
      try
      {
        retStr = StrutsUtil.getMessage(context, "msg.common.from", ord_number, StringUtil.dbDateString2appDateString(ord_date));
      }
      catch (Exception e)
      {
        log.error(e);
      }
    }

    return retStr;
  }

  public String getSip_id()
  {
    return sip_id;
  }

  public void setSip_id(String sip_id)
  {
    this.sip_id = sip_id;
  }

  public String getSpi_number()
  {
    return spi_number;
  }

  public void setSpi_number(String spi_number)
  {
    this.spi_number = spi_number;
  }

  public String getSpi_date()
  {
    return spi_date;
  }

  public String getSpi_date_formatted()
  {
    return StringUtil.dbDateString2appDateString(spi_date);
  }

  public void setSpi_date(String spi_date)
  {
    this.spi_date = spi_date;
  }

  public String getSpi_number_date()
  {
    String ret_str = "";

    if (StringUtil.isEmpty(spi_number) && StringUtil.isEmpty(spi_date))
    {
      ret_str = "";
    }
    else
    {
      IActionContext context = ActionContext.threadInstance();
      try
      {
        ret_str = StrutsUtil.getMessage(context, "msg.common.from", spi_number, getSpi_date_formatted());
      }
      catch (Exception e)
      {
        log.error(e);
      }
    }

    return ret_str;
  }

  public Contractor getCustomer()
  {
    return customer;
  }

  public String getCustomerFormatted()
  {
    if (!StringUtil.isEmpty(getAsm_id()))
    {
      return getCustomerAssemble();
    }

    return getCustomer().getName();
  }

  public String getCustomerContractSpec()
  {
    String retStr = getCustomerFormatted();
    if (!StringUtil.isEmpty(retStr) && !StringUtil.isEmpty(getSpecification().getSpc_id()))
    {
      IActionContext context = ActionContext.threadInstance();
      try
      {
        retStr += StrutsUtil.getMessage(context, "DeliveryRequestProduces.conSpecFormat", getContract().getNumberWithDate(), getSpecification().getNumberWithDate());  
      }
      catch (Exception e)
      {
        log.error(e);
      }
    }

    return retStr;
  }

  public void setCustomer(Contractor customer)
  {
    this.customer = customer;
  }

  public Contract getContract()
  {
    return contract;
  }

  public void setContract(Contract contract)
  {
    this.contract = contract;
  }

  public Specification getSpecification()
  {
    return specification;
  }

  public void setSpecification(Specification specification)
  {
    this.specification = specification;
  }

  public Purpose getPurpose()
  {
    return purpose;
  }

  public void setPurpose(Purpose purpose)
  {
    this.purpose = purpose;
  }

  public String getDrp_purpose()
  {
    return drp_purpose;
  }

  public void setDrp_purpose(String drp_purpose)
  {
    this.drp_purpose = drp_purpose;
  }

  public String getDrp_occupied()
  {
    return drp_occupied;
  }

  public void setDrp_occupied(String drp_occupied)
  {
    this.drp_occupied = drp_occupied;
  }

  public User getReceiveManager()
  {
    return receiveManager;
  }

  public void setReceiveManager(User receiveManager)
  {
    this.receiveManager = receiveManager;
  }

  public String getReceive_date()
  {
    return receive_date;
  }

  public String getReceive_date_formatted()
  {
    return StringUtil.dbDateString2appDateString(receive_date);
  }

  public void setReceive_date(String receive_date)
  {
    this.receive_date = receive_date;
  }

  public void setReceive_date_formatted(String receive_date)
  {
    this.receive_date = StringUtil.appDateString2dbDateString(receive_date);
    //проверка на ошибку перевода даты из app в db формат
    if (StringUtil.isEmpty(this.receive_date) && !StringUtil.isEmpty(receive_date))
    {
      this.receive_date = errorDateFormat;
    }
  }

  public String getDrp_max_extra()
  {
    return drp_max_extra;
  }

  public void setDrp_max_extra(String drp_max_extra)
  {
    this.drp_max_extra = drp_max_extra;
  }

  public double getOpr_count_executed()
  {
    return opr_count_executed;
  }

  public void setOpr_count_executed(double opr_count_executed)
  {
    this.opr_count_executed = opr_count_executed;
  }

  public double getApr_count()
  {
    return apr_count;
  }

  public void setApr_count(double apr_count)
  {
    this.apr_count = apr_count;
  }

  public double getSip_count()
  {
    return sip_count;
  }

  public void setSip_count(double sip_count)
  {
    this.sip_count = sip_count;
  }

  public String getDlr_fair_trade()
  {
    return dlr_fair_trade;
  }

  public void setDlr_fair_trade(String dlr_fair_trade)
  {
    this.dlr_fair_trade = dlr_fair_trade;
  }

  public String getDlr_need_deliver()
  {
    return dlr_need_deliver;
  }

  public void setDlr_need_deliver(String dlr_need_deliver)
  {
    this.dlr_need_deliver = dlr_need_deliver;
  }

  public String getDlr_include_in_spec()
  {
    return dlr_include_in_spec;
  }

  public void setDlr_include_in_spec(String dlr_include_in_spec)
  {
    this.dlr_include_in_spec = dlr_include_in_spec;
  }

  public String getCatalogNumberForStuffCategory()
  {
    produce = (DboProduce) HibernateUtil.associateWithCurentSession(produce);
    DboCatalogNumber catalogNumber = produce.getCatalogNumbers().get(new Integer(getStuffCategory().getId()));
    return catalogNumber == null ? null : catalogNumber.getNumber();
  }

  public String getUnitName()
  {
    if (null != produce && null != produce.getUnit())
    {
      return produce.getUnit().getName();
    }
    return "";
  }

  public String getSpecificationNumbers()
  {
    return specificationNumbers;
  }

  public void setSpecificationNumbers(String specificationNumbers)
  {
    this.specificationNumbers = specificationNumbers;
  }

  public String getDlr_ord_not_form()
  {
    return dlr_ord_not_form;
  }

  public void setDlr_ord_not_form(String dlr_ord_not_form)
  {
    this.dlr_ord_not_form = dlr_ord_not_form;
  }

  public String getDrp_have_depend()
  {
    return drp_have_depend;
  }

  public void setDrp_have_depend(String drp_have_depend)
  {
    this.drp_have_depend = drp_have_depend;
  }
}
