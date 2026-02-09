package net.sam.dcl.form;

import net.sam.dcl.controller.BaseDispatchValidatorForm;
import net.sam.dcl.dbo.DboProduce;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.beans.Contractor;
import net.sam.dcl.beans.Contract;
import net.sam.dcl.beans.Specification;
import net.sam.dcl.beans.Constants;
import net.sam.dcl.taglib.table.model.impl.HolderImplUsingList;
import net.sam.dcl.config.Config;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

public class OrderProduceForm extends BaseDispatchValidatorForm
{
  String number;
  String opr_use_prev_number;
  String opr_id;
  String ord_id;
  DboProduce produce = new DboProduce();
  String opr_produce_name;
  String opr_catalog_num;
  String opr_count;
  String opr_count_executed;
  String opr_count_occupied;
  String opr_price_brutto;
  String opr_discount;
  String opr_price_netto;
  String opr_occupied;
  String course;

  String donot_calculate_netto;
  String ord_discount_all;
  String ord_date_conf_all;
  String ord_ready_for_deliv_date_all;
  String ord_block;
  String stf_id;

  String opr_comment;

  String ord_all_include_in_spec;
  String ord_by_guaranty;
  String drp_price;
  String drp_max_extra;

  Contractor contractorOpr = new Contractor();
  Contract contractOpr = new Contract();
  Specification specificationOpr = new Specification();

  String numberProductionTerm;
  String numberReadyForShipping;
  HolderImplUsingList gridProductTerm = new HolderImplUsingList();
  HolderImplUsingList gridReadyForShipping = new HolderImplUsingList();

  boolean formReadOnly = false;
  boolean readOnlyIfNotLikeManager = true;
  boolean readOnlyIfNotLikeLogist = true;
  boolean readOnlyIfNotLikeLogistOrUIL = true;
  boolean showMsg = false;

  public void reset(ActionMapping mapping, HttpServletRequest request)
  {
    if ( !StringUtil.isEmpty(ord_all_include_in_spec) && StringUtil.isEmpty(ord_by_guaranty) )
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

  public String getOpr_id()
  {
    return opr_id;
  }

  public void setOpr_id(String opr_id)
  {
    this.opr_id = opr_id;
  }

  public String getOrd_id()
  {
    return ord_id;
  }

  public void setOrd_id(String ord_id)
  {
    this.ord_id = ord_id;
  }

  public DboProduce getProduce()
  {
    return produce;
  }

  public void setProduce(DboProduce produce)
  {
    this.produce = produce;
  }

  public String getOpr_produce_name()
  {
    return opr_produce_name;
  }

  public void setOpr_produce_name(String opr_produce_name)
  {
    this.opr_produce_name = opr_produce_name;
  }

  public String getOpr_catalog_num()
  {
    return opr_catalog_num;
  }

  public void setOpr_catalog_num(String opr_catalog_num)
  {
    this.opr_catalog_num = opr_catalog_num;
  }

  public String getOpr_price_brutto()
  {
    return opr_price_brutto;
  }

  public void setOpr_price_brutto(String opr_price_brutto)
  {
    this.opr_price_brutto = opr_price_brutto;
  }

  public String getOpr_count()
  {
    return opr_count;
  }

  public void setOpr_count(String opr_count)
  {
    this.opr_count = opr_count;
  }

  public String getOpr_count_executed()
  {
    return opr_count_executed;
  }

  public void setOpr_count_executed(String opr_count_executed)
  {
    this.opr_count_executed = opr_count_executed;
  }

  public String getOpr_count_occupied()
  {
    return opr_count_occupied;
  }

  public void setOpr_count_occupied(String opr_count_occupied)
  {
    this.opr_count_occupied = opr_count_occupied;
  }

  public String getOpr_discount()
  {
    return opr_discount;
  }

  public void setOpr_discount(String opr_discount)
  {
    this.opr_discount = opr_discount;
  }

  public String getOpr_price_netto()
  {
    return opr_price_netto;
  }

  public void setOpr_price_netto(String opr_price_netto)
  {
    this.opr_price_netto = opr_price_netto;
  }

  public String getOpr_occupied()
  {
    return opr_occupied;
  }

  public void setOpr_occupied(String opr_occupied)
  {
    this.opr_occupied = opr_occupied;
  }

  public String getCourse()
  {
    return course;
  }

  public void setCourse(String course)
  {
    this.course = course;
  }

  public String getDrpPriceCoefficient()
  {
    return StringUtil.double2appCurrencyString(Config.getFloat(Constants.drpPriceCoefficient, 1.5f));
  }

  public String getDonot_calculate_netto()
  {
    return donot_calculate_netto;
  }

  public void setDonot_calculate_netto(String donot_calculate_netto)
  {
    this.donot_calculate_netto = donot_calculate_netto;
  }

  public boolean isCalculateNetto()
  {
    return StringUtil.isEmpty(getDonot_calculate_netto());
  }

  public String getOrd_discount_all()
  {
    return ord_discount_all;
  }

  public void setOrd_discount_all(String ord_discount_all)
  {
    this.ord_discount_all = ord_discount_all;
  }

  public String getOrd_date_conf_all()
  {
    return ord_date_conf_all;
  }

  public void setOrd_date_conf_all(String ord_date_conf_all)
  {
    this.ord_date_conf_all = ord_date_conf_all;
  }

  public String getOrd_ready_for_deliv_date_all()
  {
    return ord_ready_for_deliv_date_all;
  }

  public void setOrd_ready_for_deliv_date_all(String ord_ready_for_deliv_date_all)
  {
    this.ord_ready_for_deliv_date_all = ord_ready_for_deliv_date_all;
  }

  public String getOrd_block()
  {
    return ord_block;
  }

  public void setOrd_block(String ord_block)
  {
    this.ord_block = ord_block;
  }

  public String getStf_id()
  {
    return stf_id;
  }

  public void setStf_id(String stf_id)
  {
    this.stf_id = stf_id;
  }

  public String getOpr_comment()
  {
    return opr_comment;
  }

  public void setOpr_comment(String opr_comment)
  {
    this.opr_comment = opr_comment;
  }

  public String getOrd_all_include_in_spec()
  {
    return ord_all_include_in_spec;
  }

  public void setOrd_all_include_in_spec(String ord_all_include_in_spec)
  {
    this.ord_all_include_in_spec = ord_all_include_in_spec;
  }

  public String getOrd_by_guaranty()
  {
    return ord_by_guaranty;
  }

  public void setOrd_by_guaranty(String ord_by_guaranty)
  {
    this.ord_by_guaranty = ord_by_guaranty;
  }

  public String getDrp_price()
  {
    return drp_price;
  }

  public void setDrp_price(String drp_price)
  {
    this.drp_price = drp_price;
  }

  public String getDrp_max_extra()
  {
    return drp_max_extra;
  }

  public void setDrp_max_extra(String drp_max_extra)
  {
    this.drp_max_extra = drp_max_extra;
  }

  public String getOpr_use_prev_number()
  {
    return opr_use_prev_number;
  }

  public void setOpr_use_prev_number(String opr_use_prev_number)
  {
    this.opr_use_prev_number = opr_use_prev_number;
  }

  public Contractor getContractorOpr()
  {
    return contractorOpr;
  }

  public void setContractorOpr(Contractor contractorOpr)
  {
    this.contractorOpr = contractorOpr;
  }

  public Contract getContractOpr()
  {
    return contractOpr;
  }

  public void setContractOpr(Contract contractOpr)
  {
    this.contractOpr = contractOpr;
  }

  public Specification getSpecificationOpr()
  {
    return specificationOpr;
  }

  public void setSpecificationOpr(Specification specificationOpr)
  {
    this.specificationOpr = specificationOpr;
  }

  public String getNumberProductionTerm()
  {
    return numberProductionTerm;
  }

  public void setNumberProductionTerm(String numberProductionTerm)
  {
    this.numberProductionTerm = numberProductionTerm;
  }

  public String getNumberReadyForShipping()
  {
    return numberReadyForShipping;
  }

  public void setNumberReadyForShipping(String numberReadyForShipping)
  {
    this.numberReadyForShipping = numberReadyForShipping;
  }

  public HolderImplUsingList getGridProductTerm()
  {
    return gridProductTerm;
  }

  public void setGridProductTerm(HolderImplUsingList gridProductTerm)
  {
    this.gridProductTerm = gridProductTerm;
  }

  public HolderImplUsingList getGridReadyForShipping()
  {
    return gridReadyForShipping;
  }

  public void setGridReadyForShipping(HolderImplUsingList gridReadyForShipping)
  {
    this.gridReadyForShipping = gridReadyForShipping;
  }

  public boolean isFormReadOnly()
  {
    return formReadOnly;
  }

  public void setFormReadOnly(boolean formReadOnly)
  {
    this.formReadOnly = formReadOnly;
  }

  public boolean isReadOnlyIfNotLikeManager()
  {
    return readOnlyIfNotLikeManager;
  }

  public void setReadOnlyIfNotLikeManager(boolean readOnlyIfNotLikeManager)
  {
    this.readOnlyIfNotLikeManager = readOnlyIfNotLikeManager;
  }

  public boolean isReadOnlyIfNotLikeLogist()
  {
    return readOnlyIfNotLikeLogist;
  }

  public void setReadOnlyIfNotLikeLogist(boolean readOnlyIfNotLikeLogist)
  {
    this.readOnlyIfNotLikeLogist = readOnlyIfNotLikeLogist;
  }

  public boolean isReadOnlyIfNotLikeLogistOrUIL()
  {
    return readOnlyIfNotLikeLogistOrUIL;
  }

  public void setReadOnlyIfNotLikeLogistOrUIL(boolean readOnlyIfNotLikeLogistOrUIL)
  {
    this.readOnlyIfNotLikeLogistOrUIL = readOnlyIfNotLikeLogistOrUIL;
  }

  public boolean isReadOnlyNewProductTerm()
  {
    return !StringUtil.isEmpty(getOrd_date_conf_all()) || isReadOnlyIfNotLikeLogist();
  }

  public boolean isReadOnlyNewReadyForShipping()
  {
    return !StringUtil.isEmpty(getOrd_ready_for_deliv_date_all()) || isReadOnlyIfNotLikeLogist();
  }

  public boolean isReadOnlySave()
  {
    return readOnlyIfNotLikeLogistOrUIL && formReadOnly;
  }

  public boolean isShowMsg()
  {
    return showMsg;
  }

  public void setShowMsg(boolean showMsg)
  {
    this.showMsg = showMsg;
  }

  public boolean isReadOnlyDrpPrice()
  {
    if ( !StringUtil.isEmpty(ord_all_include_in_spec) && !StringUtil.isEmpty(opr_use_prev_number) )
    {
      return true;
    }

    return false | readOnlyIfNotLikeManager;
  }
}
