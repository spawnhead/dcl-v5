package net.sam.dcl.form;

import net.sam.dcl.controller.BaseDispatchValidatorForm;
import net.sam.dcl.dbo.DboProduce;
import net.sam.dcl.beans.StuffCategory;

/**
 * @author: DG
 * Date: Aug 6, 2005
 * Time: 5:37:29 PM
 */
public class ConditionForContractProduceForm extends BaseDispatchValidatorForm
{
  String ccp_id;
  String cfc_id;

  String number;
  StuffCategory stuffCategory = new StuffCategory();
  DboProduce produce = new DboProduce();
  String ccp_price;
  String ccp_count;
  String ccp_nds_rate;
  String cpr_id;
  String cpr_number;
  String cpr_date;

  String sellerId;

  public String getNumber()
  {
    return number;
  }

  public void setNumber(String number)
  {
    this.number = number;
  }

  public String getCcp_id()
  {
    return ccp_id;
  }

  public void setCcp_id(String ccp_id)
  {
    this.ccp_id = ccp_id;
  }

  public String getCfc_id()
  {
    return cfc_id;
  }

  public void setCfc_id(String cfc_id)
  {
    this.cfc_id = cfc_id;
  }

  public DboProduce getProduce()
  {
    return produce;
  }

  public void setProduce(DboProduce produce)
  {
    this.produce = produce;
  }

  public String getCcp_price()
  {
    return ccp_price;
  }

  public void setCcp_price(String ccp_price)
  {
    this.ccp_price = ccp_price;
  }

  public String getCcp_count()
  {
    return ccp_count;
  }

  public void setCcp_count(String ccp_count)
  {
    this.ccp_count = ccp_count;
  }

  public String getCcp_nds_rate()
  {
    return ccp_nds_rate;
  }

  public void setCcp_nds_rate(String ccp_nds_rate)
  {
    this.ccp_nds_rate = ccp_nds_rate;
  }

  public String getCpr_id()
  {
    return cpr_id;
  }

  public void setCpr_id(String cpr_id)
  {
    this.cpr_id = cpr_id;
  }

  public String getCpr_number()
  {
    return cpr_number;
  }

  public void setCpr_number(String cpr_number)
  {
    this.cpr_number = cpr_number;
  }

  public String getCpr_date()
  {
    return cpr_date;
  }

  public void setCpr_date(String cpr_date)
  {
    this.cpr_date = cpr_date;
  }

  public StuffCategory getStuffCategory()
  {
    return stuffCategory;
  }

  public void setStuffCategory(StuffCategory stuffCategory)
  {
    this.stuffCategory = stuffCategory;
  }

  public String getSellerId()
  {
    return sellerId;
  }

  public void setSellerId(String sellerId)
  {
    this.sellerId = sellerId;
  }
}
