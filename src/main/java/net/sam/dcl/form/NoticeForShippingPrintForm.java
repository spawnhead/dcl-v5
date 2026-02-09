package net.sam.dcl.form;

import net.sam.dcl.controller.BaseDispatchValidatorForm;
import net.sam.dcl.beans.*;

import java.util.List;
import java.util.ArrayList;

/**
 * @author: DG
 * Date: Aug 6, 2005
 * Time: 5:37:29 PM
 */
public class NoticeForShippingPrintForm extends BaseDispatchValidatorForm
{
  String shp_id;
  String shp_number;
  String shp_date;
  Contractor contractor = new Contractor();
  Contractor contractorWhere = new Contractor();
  Contract contractWhere = new Contract();
  String shp_notice_date;
  List produces = new ArrayList();

  float printScale;

  public String getShp_id()
  {
    return shp_id;
  }

  public void setShp_id(String shp_id)
  {
    this.shp_id = shp_id;
  }

  public String getShp_number()
  {
    return shp_number;
  }

  public void setShp_number(String shp_number)
  {
    this.shp_number = shp_number;
  }

  public String getShp_date()
  {
    return shp_date;
  }

  public void setShp_date(String shp_date)
  {
    this.shp_date = shp_date;
  }

  public Contractor getContractor()
  {
    return contractor;
  }

  public void setContractor(Contractor contractor)
  {
    this.contractor = contractor;
  }

  public Contractor getContractorWhere()
  {
    return contractorWhere;
  }

  public void setContractorWhere(Contractor contractorWhere)
  {
    this.contractorWhere = contractorWhere;
  }

  public Contract getContractWhere()
  {
    return contractWhere;
  }

  public void setContractWhere(Contract contractWhere)
  {
    this.contractWhere = contractWhere;
  }

  public String getShp_notice_date()
  {
    return shp_notice_date;
  }

  public void setShp_notice_date(String shp_notice_date)
  {
    this.shp_notice_date = shp_notice_date;
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
