package net.sam.dcl.form;

import net.sam.dcl.controller.BaseDispatchValidatorForm;
import net.sam.dcl.dbo.DboProduce;
import net.sam.dcl.beans.*;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.config.Config;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

public class DeliveryRequestProduceForm extends BaseDispatchValidatorForm
{
  String drp_id;
  String dlr_id;

  String opr_id;
  String asm_id;
  String apr_id;
  String ord_date;
  String ord_number;
  String ordInfoAssemble;
  String customerAssemble;
  String sip_id;
  String spi_number;
  String spi_date;
  String number;
  DboProduce produce = new DboProduce();
  String drp_price;
  String drp_count;
  StuffCategory stuffCategory = new StuffCategory();
  Contractor customer = new Contractor();
  Contract contract = new Contract();
  Specification specification = new Specification();
  Purpose purpose = new Purpose();
  String drp_purpose;
  String drp_occupied;
  User receiveManager = new User();
  String receive_date;
  String drp_max_extra;

  String dlr_fair_trade;
  String dlr_need_deliver;
  String dlr_include_in_spec;
  String dlr_guarantee_repair;

  String course;
  String opr_price_netto;

  boolean showMaxExtra = false;

  public void reset(ActionMapping mapping, HttpServletRequest request)
  {
    if ( isShowMaxExtra() )
    {
      setDrp_max_extra("");
    }
    super.reset(mapping, request);
  }

  public String getNumber()
  {
    return number;
  }

  public void setNumber(String number)
  {
    this.number = number;
  }

  public String getDrp_id()
  {
    return drp_id;
  }

  public void setDrp_id(String drp_id)
  {
    this.drp_id = drp_id;
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
    return ordInfoAssemble;
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

  public void setSpi_date(String spi_date)
  {
    this.spi_date = spi_date;
  }

  public String getDlr_id()
  {
    return dlr_id;
  }

  public void setDlr_id(String dlr_id)
  {
    this.dlr_id = dlr_id;
  }

  public DboProduce getProduce()
  {
    return produce;
  }

  public void setProduce(DboProduce produce)
  {
    this.produce = produce;
  }

  public String getDrp_price()
  {
    return drp_price;
  }

  public void setDrp_price(String drp_price)
  {
    this.drp_price = drp_price;
  }

  public String getDrp_count()
  {
    return drp_count;
  }

  public void setDrp_count(String drp_count)
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

  public Contractor getCustomer()
  {
    return customer;
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

  public void setReceive_date(String receive_date)
  {
    this.receive_date = receive_date;
  }

  public String getDrp_max_extra()
  {
    return drp_max_extra;
  }

  public void setDrp_max_extra(String drp_max_extra)
  {
    this.drp_max_extra = drp_max_extra;
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

  public String getDlr_guarantee_repair()
  {
    return dlr_guarantee_repair;
  }

  public void setDlr_guarantee_repair(String dlr_guarantee_repair)
  {
    this.dlr_guarantee_repair = dlr_guarantee_repair;
  }

  public String getCourse()
  {
    return course;
  }

  public void setCourse(String course)
  {
    this.course = course;
  }

  public String getOpr_price_netto()
  {
    return opr_price_netto;
  }

  public void setOpr_price_netto(String opr_price_netto)
  {
    this.opr_price_netto = opr_price_netto;
  }

  public String getDrpPriceCoefficient()
  {
    return StringUtil.double2appCurrencyString(Config.getFloat(Constants.drpPriceCoefficient, 1.5f));
  }

  public boolean isShowDrpPrice()
  {
    return (StringUtil.isEmpty(getDlr_fair_trade()) && StringUtil.isEmpty(getDlr_guarantee_repair()) || !StringUtil.isEmpty(getDlr_include_in_spec()));
  }

  public boolean isShowMaxExtra()
  {
    return showMaxExtra;
  }

  public void setShowMaxExtra(boolean showMaxExtra)
  {
    this.showMaxExtra = showMaxExtra;
  }

  public boolean isReadonliLikeImported()
  {
    return !StringUtil.isEmpty(getOpr_id()) || !StringUtil.isEmpty(getSip_id()) || !StringUtil.isEmpty(getAsm_id()) || !StringUtil.isEmpty(getApr_id());
  }

  public boolean isReadonliLikeGuarantee()
  {
    return !StringUtil.isEmpty(getDlr_guarantee_repair());
  }

  public String getContractNumberWithDate()
  {
    if ( StringUtil.isEmpty(getContract().getCon_number()) )
    {
      return "";
    }

    return getContract().getNumberWithDate();
  }

  public String getSpecificationNumberWithDate()
  {
    if ( StringUtil.isEmpty(getSpecification().getSpc_number()) )
    {
      return "";
    }

    return getSpecification().getNumberWithDate();
  }
}
