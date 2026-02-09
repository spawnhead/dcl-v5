package net.sam.dcl.form;

import net.sam.dcl.controller.BaseDispatchValidatorForm;
import net.sam.dcl.beans.*;
import net.sam.dcl.taglib.table.model.impl.HolderImplUsingList;

/**
 * @author: DG
 * Date: Aug 6, 2005
 * Time: 5:37:29 PM
 */
public class ClosedRecordForm extends BaseDispatchValidatorForm
{
  String number;

  String ctc_id;

  String lcc_id;
  String lcc_charges;
  String lcc_montage;
  String lcc_transport;
  String lcc_update_sum;
  String sum_out_nds_eur;
  Contractor contractor = new Contractor();
  Contract contract = new Contract();
  Specification specification = new Specification();
  String pay_summ;
  String shp_summ;
  String managers;
  String products;

  boolean showDeleteMsg;
  boolean showForGroupDelivery;
  boolean formReadOnly = false;

  HolderImplUsingList gridResult = new HolderImplUsingList();

  public String getNumber()
  {
    return number;
  }

  public void setNumber(String number)
  {
    this.number = number;
  }

  public String getCtc_id()
  {
    return ctc_id;
  }

  public void setCtc_id(String ctc_id)
  {
    this.ctc_id = ctc_id;
  }

  public String getLcc_id()
  {
    return lcc_id;
  }

  public void setLcc_id(String lcc_id)
  {
    this.lcc_id = lcc_id;
  }

  public String getLcc_charges()
  {
    return lcc_charges;
  }

  public void setLcc_charges(String lcc_charges)
  {
    this.lcc_charges = lcc_charges;
  }

  public String getLcc_montage()
  {
    return lcc_montage;
  }

  public void setLcc_montage(String lcc_montage)
  {
    this.lcc_montage = lcc_montage;
  }

  public String getLcc_transport()
  {
    return lcc_transport;
  }

  public void setLcc_transport(String lcc_transport)
  {
    this.lcc_transport = lcc_transport;
  }

  public String getLcc_update_sum()
  {
    return lcc_update_sum;
  }

  public void setLcc_update_sum(String lcc_update_sum)
  {
    this.lcc_update_sum = lcc_update_sum;
  }

  public String getSum_out_nds_eur()
  {
    return sum_out_nds_eur;
  }

  public void setSum_out_nds_eur(String sum_out_nds_eur)
  {
    this.sum_out_nds_eur = sum_out_nds_eur;
  }

  public Contractor getContractor()
  {
    return contractor;
  }

  public void setContractor(Contractor contractor)
  {
    this.contractor = contractor;
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

  public String getPay_summ()
  {
    return pay_summ;
  }

  public void setPay_summ(String pay_summ)
  {
    this.pay_summ = pay_summ;
  }

  public String getShp_summ()
  {
    return shp_summ;
  }

  public void setShp_summ(String shp_summ)
  {
    this.shp_summ = shp_summ;
  }

  public String getManagers()
  {
    return managers;
  }

  public void setManagers(String managers)
  {
    this.managers = managers;
  }

  public String getProducts()
  {
    return products;
  }

  public void setProducts(String products)
  {
    this.products = products;
  }

  public HolderImplUsingList getGridResult()
  {
    return gridResult;
  }

  public void setGridResult(HolderImplUsingList gridResult)
  {
    this.gridResult = gridResult;
  }

  public boolean isShowDeleteMsg()
  {
    return showDeleteMsg;
  }

  public void setShowDeleteMsg(boolean showDeleteMsg)
  {
    this.showDeleteMsg = showDeleteMsg;
  }

  public boolean isShowForGroupDelivery()
  {
    return showForGroupDelivery;
  }

  public void setShowForGroupDelivery(boolean showForGroupDelivery)
  {
    this.showForGroupDelivery = showForGroupDelivery;
  }

  public boolean isFormReadOnly()
  {
    return formReadOnly;
  }

  public void setFormReadOnly(boolean formReadOnly)
  {
    this.formReadOnly = formReadOnly;
  }
}
