package net.sam.dcl.form;

import net.sam.dcl.controller.BaseDispatchValidatorForm;
import net.sam.dcl.beans.*;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.taglib.table.model.impl.HolderImplUsingList;

/**
 * Date: Aug 6, 2005
 * Time: 5:37:29 PM
 */
public class ProduceCostForm extends BaseDispatchValidatorForm
{
  String is_new_doc;
  String number;
  boolean formReadOnly = false;

  String prc_id;
  String target;

  User createUser = new User();
  User editUser = new User();
  String usr_date_create;
  String usr_date_edit;

  String prc_number;
  String prc_date;
  Route route = new Route();
  String prc_sum_transport;
  String prc_weight;
  String prc_course_ltl_eur;
  String prc_block;

  String needRecalc;

  HolderImplUsingList gridProduces = new HolderImplUsingList();
  HolderImplUsingList gridCustom = new HolderImplUsingList();

  public String getIs_new_doc()
  {
    return is_new_doc;
  }

  public void setIs_new_doc(String is_new_doc)
  {
    this.is_new_doc = is_new_doc;
  }

  public String getNumber()
  {
    return number;
  }

  public void setNumber(String number)
  {
    this.number = number;
  }

  public boolean isFormReadOnly()
  {
    return formReadOnly;
  }

  public void setFormReadOnly(boolean formReadOnly)
  {
    this.formReadOnly = formReadOnly;
  }

  public String getPrc_id()
  {
    return prc_id;
  }

  public void setPrc_id(String prc_id)
  {
    this.prc_id = prc_id;
  }

  public String getTarget()
  {
    return target;
  }

  public void setTarget(String target)
  {
    this.target = target;
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

  public String getPrc_number()
  {
    return prc_number;
  }

  public void setPrc_number(String prc_number)
  {
    this.prc_number = prc_number;
  }

  public String getPrc_date()
  {
    return prc_date;
  }

  public String getCon_date_ts()
  {
    return StringUtil.appDateString2dbDateString(prc_date);
  }

  public String getCon_date_tm()
  {
    String tm_date = StringUtil.appDateString2dbDateString(prc_date);
    tm_date += " 23:59:59";
    return tm_date;
  }

  public void setPrc_date(String prc_date)
  {
    this.prc_date = prc_date;
  }

  public Route getRoute()
  {
    return route;
  }

  public void setRoute(Route route)
  {
    this.route = route;
  }

  public double getPrc_sum_transport()
  {
    return StringUtil.appCurrencyString2double(prc_sum_transport);
  }

   public String getPrc_sum_transport_formatted()
  {
    return prc_sum_transport;
  }

  public void setPrc_sum_transport(double prc_sum_transport)
  {
    this.prc_sum_transport = StringUtil.double2appCurrencyString(prc_sum_transport);
  }

  public void setPrc_sum_transport_formatted(String prc_sum_transport)
  {
    this.prc_sum_transport = prc_sum_transport;
  }

  public double getPrc_weight()
  {
    return StringUtil.appCurrencyString2double(prc_weight);
  }

  public String getPrc_weight_formatted()
  {
    return prc_weight;
  }

  public void setPrc_weight(double prc_weight)
  {
    this.prc_weight = StringUtil.double2appCurrencyString(prc_weight);
  }

  public void setPrc_weight_formatted(String prc_weight)
  {
    this.prc_weight = prc_weight;
  }

  public double getPrc_course_ltl_eur()
  {
    return StringUtil.appCurrencyString2double(prc_course_ltl_eur);
  }

   public String getPrc_course_ltl_eur_formatted()
  {
    return prc_course_ltl_eur;
  }

  public void setPrc_course_ltl_eur(double prc_course_ltl_eur)
  {
    this.prc_course_ltl_eur = StringUtil.double2appCurrencyStringByMask(prc_course_ltl_eur, "#,##0.0000");
  }

  public void setPrc_course_ltl_eur_formatted(String prc_course_ltl_eur)
  {
    this.prc_course_ltl_eur = prc_course_ltl_eur;
  }

  public String getPrc_block()
  {
    return prc_block;
  }

  public void setPrc_block(String prc_block)
  {
    this.prc_block = prc_block;
  }

  public String getNeedRecalc()
  {
    return needRecalc;
  }

  public void setNeedRecalc(String needRecalc)
  {
    this.needRecalc = needRecalc;
  }

  public HolderImplUsingList getGridProduces()
  {
    return gridProduces;
  }

  public void setGridProduces(HolderImplUsingList gridProduces)
  {
    this.gridProduces = gridProduces;
  }

  public HolderImplUsingList getGridCustom()
  {
    return gridCustom;
  }

  public void setGridCustom(HolderImplUsingList gridCustom)
  {
    this.gridCustom = gridCustom;
  }
}
