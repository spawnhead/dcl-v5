package net.sam.dcl.form;

import net.sam.dcl.controller.BaseDispatchValidatorForm;
import net.sam.dcl.beans.StuffCategory;
import net.sam.dcl.beans.User;
import net.sam.dcl.beans.Purpose;
import net.sam.dcl.beans.Department;
import net.sam.dcl.dbo.DboProduce;
import net.sam.dcl.util.StringUtil;

/**
 * @author: DG
 * Date: Aug 6, 2005
 * Time: 5:37:29 PM
 */
public class ProduceCostProduceForm extends BaseDispatchValidatorForm
{
  String number;

  String lpc_id;
  String prc_id;
  String prc_date;
  DboProduce produce = new DboProduce();
  String lpc_produce_name;
  StuffCategory stuffCategory = new StuffCategory();
  User manager = new User();
  Department department = new Department();
  String lpc_count;
  String lpc_cost_one_ltl;
  String lpc_cost_one_by;
  String lpc_price_list_by;
  String lpc_weight;
  String lpc_summ;
  String lpc_1c_number;
  String lpc_occupied;
  Purpose purpose = new Purpose();
  String lpc_comment;
  String opr_id;
  String asm_id;
  String apr_id;
  String drp_id;
  String sip_id;

  String lpc_percent_dcl_1_4;

  public String getNumber()
  {
    return number;
  }

  public void setNumber(String number)
  {
    this.number = number;
  }

  public String getLpc_id()
  {
    return lpc_id;
  }

  public void setLpc_id(String lpc_id)
  {
    this.lpc_id = lpc_id;
  }

  public String getPrc_id()
  {
    return prc_id;
  }

  public void setPrc_id(String prc_id)
  {
    this.prc_id = prc_id;
  }

  public String getPrc_date()
  {
    return prc_date;
  }

  public void setPrc_date(String prc_date)
  {
    this.prc_date = prc_date;
  }

  public DboProduce getProduce()
  {
    return produce;
  }

  public void setProduce(DboProduce produce)
  {
    this.produce = produce;
  }

  public String getLpc_produce_name()
  {
    return lpc_produce_name;
  }

  public StuffCategory getStuffCategory()
  {
    return stuffCategory;
  }

  public void setStuffCategory(StuffCategory stuffCategory)
  {
    this.stuffCategory = stuffCategory;
  }

  public User getManager()
  {
    return manager;
  }

  public void setManager(User manager)
  {
    this.manager = manager;
  }

  public Department getDepartment()
  {
    return department;
  }

  public void setDepartment(Department department)
  {
    this.department = department;
  }

  public void setLpc_produce_name(String lpc_produce_name)
  {
    this.lpc_produce_name = lpc_produce_name;
  }

  public String getLpc_count()
  {
    return lpc_count;
  }

  public void setLpc_count(String lpc_count)
  {
    this.lpc_count = lpc_count;
  }

  public String getLpc_cost_one_ltl()
  {
    return lpc_cost_one_ltl;
  }

  public void setLpc_cost_one_ltl(String lpc_cost_one_ltl)
  {
    this.lpc_cost_one_ltl = lpc_cost_one_ltl;
  }

  public String getLpc_weight()
  {
    return lpc_weight;
  }

  public void setLpc_weight(String lpc_weight)
  {
    this.lpc_weight = lpc_weight;
  }

  public String getLpc_cost_one_by()
  {
    return lpc_cost_one_by;
  }

  public void setLpc_cost_one_by(String lpc_cost_one_by)
  {
    this.lpc_cost_one_by = lpc_cost_one_by;
  }

  public String getLpc_price_list_by()
  {
    return lpc_price_list_by;
  }

  public void setLpc_price_list_by(String lpc_price_list_by)
  {
    this.lpc_price_list_by = lpc_price_list_by;
  }

  public String getLpc_summ()
  {
    return lpc_summ;
  }

  public void setLpc_summ(String lpc_summ)
  {
    this.lpc_summ = lpc_summ;
  }

  public String getLpc_1c_number()
  {
    return lpc_1c_number;
  }

  public void setLpc_1c_number(String lpc_1c_number)
  {
    this.lpc_1c_number = lpc_1c_number;
  }

  public String getLpc_occupied()
  {
    return lpc_occupied;
  }

  public void setLpc_occupied(String lpc_occupied)
  {
    this.lpc_occupied = lpc_occupied;
  }

  public Purpose getPurpose()
  {
    return purpose;
  }

  public void setPurpose(Purpose purpose)
  {
    this.purpose = purpose;
  }

  public String getLpc_comment()
  {
    return lpc_comment;
  }

  public void setLpc_comment(String lpc_comment)
  {
    this.lpc_comment = lpc_comment;
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

  public String getDrp_id()
  {
    return drp_id;
  }

  public void setDrp_id(String drp_id)
  {
    this.drp_id = drp_id;
  }

  public String getSip_id()
  {
    return sip_id;
  }

  public void setSip_id(String sip_id)
  {
    this.sip_id = sip_id;
  }

  public String getLpc_percent_dcl_1_4()
  {
    return lpc_percent_dcl_1_4;
  }

  public void setLpc_percent_dcl_1_4(String lpc_percent_dcl_1_4)
  {
    this.lpc_percent_dcl_1_4 = lpc_percent_dcl_1_4;
  }

  public boolean isReadonliLikeImported()
  {
    return !StringUtil.isEmpty(opr_id) || !StringUtil.isEmpty(asm_id) || !StringUtil.isEmpty(apr_id) || !StringUtil.isEmpty(drp_id) || !StringUtil.isEmpty(sip_id);
  }

  public boolean isReadonliLikeImportedExceptSome()
  {
    return !StringUtil.isEmpty(opr_id) || !StringUtil.isEmpty(sip_id) ;
  }
}
