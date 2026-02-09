package net.sam.dcl.beans;

import net.sam.dcl.util.StringUtil;

import java.io.Serializable;

public class ContractorRequestPrint implements Serializable
{
  String crp_id;
  String crq_id;
  String crp_reclamation_date;
  String crp_lintera_request_date;
  String crp_no_defect_act_checkbox;
  String crp_no_defect_act;
  String crp_no_reclamation_act_checkbox;
  String crp_no_reclamation_act;
  String crp_lintera_agreement_date;
  String crp_deliver_checkbox;
  String crp_deliver;

  public ContractorRequestPrint()
  {
  }

  public ContractorRequestPrint(ContractorRequestPrint specificationPayment)
  {
    this.crp_id = specificationPayment.getCrp_id();
    this.crq_id = specificationPayment.getCrq_id();
    this.crp_reclamation_date = specificationPayment.getCrp_reclamation_date();
    this.crp_lintera_request_date = specificationPayment.getCrp_lintera_request_date();
    this.crp_no_defect_act = specificationPayment.getCrp_no_defect_act();
    this.crp_no_defect_act_checkbox = specificationPayment.getCrp_no_defect_act_checkbox();
    this.crp_no_reclamation_act = specificationPayment.getCrp_no_reclamation_act();
    this.crp_no_reclamation_act_checkbox = specificationPayment.getCrp_no_reclamation_act_checkbox();
    this.crp_lintera_agreement_date = specificationPayment.getCrp_lintera_agreement_date();
    this.crp_deliver = specificationPayment.getCrp_deliver();
    this.crp_deliver_checkbox = specificationPayment.getCrp_deliver_checkbox();
  }

  public String getCrp_id()
  {
    return crp_id;
  }

  public void setCrp_id(String crp_id)
  {
    this.crp_id = crp_id;
  }

  public String getCrq_id()
  {
    return crq_id;
  }

  public void setCrq_id(String crq_id)
  {
    this.crq_id = crq_id;
  }

  public String getCrp_reclamation_date()
  {
    return crp_reclamation_date;
  }

  public String getCrp_reclamation_date_formatted()
  {
    return StringUtil.dbDateString2appDateString(crp_reclamation_date);
  }

  public void setCrp_reclamation_date(String crp_reclamation_date)
  {
    this.crp_reclamation_date = crp_reclamation_date;
  }

  public void setCrp_reclamation_date_formatted(String crp_reclamation_date)
  {
    this.crp_reclamation_date = StringUtil.appDateString2dbDateString(crp_reclamation_date);
  }

  public String getCrp_lintera_request_date()
  {
    return crp_lintera_request_date;
  }

  public String getCrp_lintera_request_date_formatted()
  {
    return StringUtil.dbDateString2appDateString(crp_lintera_request_date);
  }

  public void setCrp_lintera_request_date(String crp_lintera_request_date)
  {
    this.crp_lintera_request_date = crp_lintera_request_date;
  }

  public void setCrp_lintera_request_date_formatted(String crp_lintera_request_date)
  {
    this.crp_lintera_request_date = StringUtil.appDateString2dbDateString(crp_lintera_request_date);
  }

  public String getCrp_no_defect_act_checkbox()
  {
    return crp_no_defect_act_checkbox;
  }

  public void setCrp_no_defect_act_checkbox(String crp_no_defect_act_checkbox)
  {
    this.crp_no_defect_act_checkbox = crp_no_defect_act_checkbox;
  }

  public String getCrp_no_defect_act()
  {
    return crp_no_defect_act;
  }

  public void setCrp_no_defect_act(String crp_no_defect_act)
  {
    this.crp_no_defect_act = crp_no_defect_act;
  }

  public String getCrp_no_reclamation_act_checkbox()
  {
    return crp_no_reclamation_act_checkbox;
  }

  public void setCrp_no_reclamation_act_checkbox(String crp_no_reclamation_act_checkbox)
  {
    this.crp_no_reclamation_act_checkbox = crp_no_reclamation_act_checkbox;
  }

  public String getCrp_no_reclamation_act()
  {
    return crp_no_reclamation_act;
  }

  public void setCrp_no_reclamation_act(String crp_no_reclamation_act)
  {
    this.crp_no_reclamation_act = crp_no_reclamation_act;
  }

  public String getCrp_lintera_agreement_date()
  {
    return crp_lintera_agreement_date;
  }

  public String getCrp_lintera_agreement_date_formatted()
  {
    return StringUtil.dbDateString2appDateString(crp_lintera_agreement_date);
  }

  public void setCrp_lintera_agreement_date(String crp_lintera_agreement_date)
  {
    this.crp_lintera_agreement_date = crp_lintera_agreement_date;
  }

  public void setCrp_lintera_agreement_date_formatted(String crp_lintera_agreement_date)
  {
    this.crp_lintera_agreement_date = StringUtil.appDateString2dbDateString(crp_lintera_agreement_date);
  }

  public String getCrp_deliver_checkbox()
  {
    return crp_deliver_checkbox;
  }

  public void setCrp_deliver_checkbox(String crp_deliver_checkbox)
  {
    this.crp_deliver_checkbox = crp_deliver_checkbox;
  }

  public String getCrp_deliver()
  {
    return crp_deliver;
  }

  public void setCrp_deliver(String crp_deliver)
  {
    this.crp_deliver = crp_deliver;
  }
}