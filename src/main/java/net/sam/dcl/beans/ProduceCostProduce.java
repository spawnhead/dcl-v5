package net.sam.dcl.beans;

import net.sam.dcl.dbo.DboCatalogNumber;
import net.sam.dcl.dbo.DboProduce;
import net.sam.dcl.util.HibernateUtil;
import net.sam.dcl.util.StringUtil;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * Date: 14.09.2005
 * Time: 17:17:20
 * To change this template use File | Settings | File Templates.
 */
public class ProduceCostProduce implements Serializable
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
  double lpc_count;
  double lpc_cost_one_ltl;
  double lpc_cost_one_by;
  double lpc_price_list_by;
  double lpc_cost_one;
  double lpc_weight;
  double lpc_percent;
  boolean manualPercent;
  double lpc_percent_dcl_1_4;
  double lpc_summ;

  double lpc_sum_transport;
  double lpc_custom;
  String lpc_1c_number;
  String lpc_occupied;
  Purpose purpose = new Purpose();
  String lpc_comment;
  String noRound;

  String opr_id;
  String asm_id;
  String apr_id;
  String drp_id;
  String sip_id;

  String ord_id;

  boolean itogString;

  public ProduceCostProduce()
  {
  }

  public ProduceCostProduce(String lpc_id)
  {
    this.lpc_id = lpc_id;
  }

  public ProduceCostProduce(ProduceCostProduce produceCostProduce)
  {
    number = produceCostProduce.getNumber();
    lpc_id = produceCostProduce.getLpc_id();
    prc_id = produceCostProduce.getPrc_id();
    prc_date = produceCostProduce.getPrc_date();
    produce = produceCostProduce.getProduce();
    lpc_produce_name = produceCostProduce.getLpc_produce_name();
    stuffCategory = new StuffCategory(produceCostProduce.getStuffCategory());
    manager = new User(produceCostProduce.getManager());
    department = new Department(produceCostProduce.getDepartment());
    lpc_count = produceCostProduce.getLpc_count();
    lpc_cost_one_ltl = produceCostProduce.getLpc_cost_one_ltl();
    lpc_cost_one_by = produceCostProduce.getLpc_cost_one_by();
    lpc_price_list_by = produceCostProduce.getLpc_price_list_by();
    lpc_cost_one = produceCostProduce.getLpc_cost_one();
    lpc_weight = produceCostProduce.getLpc_weight();
    lpc_percent = produceCostProduce.getLpc_percent();
    manualPercent = produceCostProduce.isManualPercent();
    lpc_percent_dcl_1_4 = produceCostProduce.getLpc_percent_dcl_1_4();
    lpc_summ = produceCostProduce.getLpc_summ();

    lpc_sum_transport = produceCostProduce.getLpc_sum_transport();
    lpc_custom = produceCostProduce.getLpc_custom();
    lpc_1c_number = produceCostProduce.getLpc_1c_number();
    lpc_occupied = produceCostProduce.getLpc_occupied();
    purpose = produceCostProduce.getPurpose();
    lpc_comment = produceCostProduce.getLpc_comment();

    noRound = produceCostProduce.getNoRound();

    opr_id = produceCostProduce.getOpr_id();
    asm_id = produceCostProduce.getAsm_id();
    apr_id = produceCostProduce.getApr_id();
    drp_id = produceCostProduce.getDrp_id();
    sip_id = produceCostProduce.getSip_id();

    ord_id = produceCostProduce.getOrd_id();

    itogString = produceCostProduce.isItogString();
  }

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

  public String getProduce_name()
  {
    if ( isItogString() )
    {
      return lpc_produce_name;
    }
    else
    {
      return produce.getFullName();
    }
  }

  public void setLpc_produce_name(String lpc_produce_name)
  {
    this.lpc_produce_name = lpc_produce_name;
  }

  public StuffCategory getStuffCategory()
  {
    return stuffCategory;
  }

  public void setStuffCategory(StuffCategory stuffCategory)
  {
    this.stuffCategory = stuffCategory;
  }

  public String getCatalogNumberForStuffCategory()
  {
    if ( null == produce.getId() || null == stuffCategory || stuffCategory.getId().isEmpty() )
    {
      return "";
    }

    produce =(DboProduce) HibernateUtil.associateWithCurentSession(produce);
		DboCatalogNumber catalogNumber = produce.getCatalogNumbers().get(new Integer(stuffCategory.getId()));
		return catalogNumber == null ? null : catalogNumber.getNumber();
	}

  public DboCatalogNumber getDboCatalogNumberForStuffCategory()
  {
    if ( null == produce.getId() || null == stuffCategory || stuffCategory.getId().isEmpty() )
    {
      return null;
    }

    produce =(DboProduce) HibernateUtil.associateWithCurentSession(produce);
		return produce.getCatalogNumbers().get(new Integer(stuffCategory.getId()));
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

  public double getLpc_count()
  {
    return lpc_count;
  }

  public String getLpc_count_formatted()
  {
    return StringUtil.double2appCurrencyString(lpc_count);
  }

  public double getFieldForCheckCP(double rate, double coefficient)
  {
    double n = getLpc_count();
    double s = getLpc_cost_one() * n + getLpc_sum_transport() + getLpc_custom();
    return s / n * rate * coefficient;
  }

  public void setLpc_count(double lpc_count)
  {
    this.lpc_count = lpc_count;
  }

  public double getLpc_cost_one_ltl()
  {
    return lpc_cost_one_ltl;
  }

  public String getLpc_cost_one_ltl_formatted()
  {
    return StringUtil.double2appCurrencyString(lpc_cost_one_ltl);
  }

  public void setLpc_cost_one_ltl(double lpc_cost_one_ltl)
  {
    this.lpc_cost_one_ltl = lpc_cost_one_ltl;
  }

  public void setLpc_cost_one_ltl_formatted(String lpc_cost_one_ltl)
  {
    this.lpc_cost_one_ltl = StringUtil.appCurrencyString2double(lpc_cost_one_ltl);
  }

  public double getLpc_cost_one_by()
  {
    return lpc_cost_one_by;
  }

  public String getLpc_cost_one_by_formatted()
  {
    if ( StringUtil.isEmpty(noRound) )
    {
      return StringUtil.double2appCurrencyStringWithoutDec(lpc_cost_one_by);
    }

    return StringUtil.double2appCurrencyString(lpc_cost_one_by);
  }

  public void setLpc_cost_one_by(double lpc_cost_one_by)
  {
    this.lpc_cost_one_by = lpc_cost_one_by;
  }

  public void setLpc_cost_one_by_formatted(String lpc_cost_one_by)
  {
    this.lpc_cost_one_by = StringUtil.appCurrencyString2doubleSpecial(lpc_cost_one_by);
  }

  public double getLpc_price_list_by()
  {
    return lpc_price_list_by;
  }

  public String getLpc_price_list_by_formatted()
  {
    if ( StringUtil.isEmpty(noRound) )
    {
      return StringUtil.double2appCurrencyStringWithoutDec(lpc_price_list_by);
    }

    return StringUtil.double2appCurrencyString(lpc_price_list_by);
  }

  public void setLpc_price_list_by(double lpc_price_list_by)
  {
    this.lpc_price_list_by = lpc_price_list_by;
  }

  public void setLpc_price_list_by_formatted(String lpc_price_list_by)
  {
    this.lpc_price_list_by = StringUtil.appCurrencyString2doubleSpecial(lpc_price_list_by);
  }

  public double getLpc_cost_one()
  {
    return lpc_cost_one;
  }

  public String getLpc_cost_one_formatted()
  {
    return StringUtil.double2appCurrencyString(lpc_cost_one);
  }

  public void setLpc_cost_one(double lpc_cost_one)
  {
    this.lpc_cost_one = lpc_cost_one;
  }

  public double getLpc_weight()
  {
    return lpc_weight;
  }

  public String getLpc_weight_formatted()
  {
    return StringUtil.double2appCurrencyStringByMask(lpc_weight, "#,##0.000");
  }

  public void setLpc_weight(double lpc_weight)
  {
    this.lpc_weight = lpc_weight;
  }

  public void setLpc_weight_formatted(String lpc_weight)
  {
    this.lpc_weight = StringUtil.appCurrencyString2double(lpc_weight);
  }

  public double getLpc_percent()
  {
    return lpc_percent;
  }

  public String getLpc_percent_formatted()
  {
    if ( manualPercent )
    {
      return "<b>" + StringUtil.double2appCurrencyStringByMask(lpc_percent, "#,##0.000") + "</b>";
    }
    return StringUtil.double2appCurrencyStringByMask(lpc_percent, "#,##0.000");
  }

  public void setLpc_percent(double lpc_percent)
  {
    this.lpc_percent = lpc_percent;
  }

  public boolean isManualPercent()
  {
    return manualPercent;
  }

  public void setManualPercent(boolean manualPercent)
  {
    this.manualPercent = manualPercent;
  }

  public double getLpc_percent_dcl_1_4()
  {
    return lpc_percent_dcl_1_4;
  }

  public String getLpc_percent_dcl_1_4_formatted() {
      return StringUtil.double2appCurrencyStringByMask(lpc_percent_dcl_1_4, "#,##0.000");
  }
  public void setLpc_percent_dcl_1_4(double lpc_percent_dcl_1_4)
  {
    this.lpc_percent_dcl_1_4 = lpc_percent_dcl_1_4;
  }

  public void setLpc_percent_dcl_1_4_formatted(String lpc_percent_dcl_1_4)
    {
        this.lpc_percent_dcl_1_4 = StringUtil.appCurrencyString2double(lpc_percent_dcl_1_4);
    }
  public double getLpc_summ()
  {
    return lpc_summ;
  }

  public String getLpc_summ_formatted()
  {
    return StringUtil.double2appCurrencyString(lpc_summ);
  }

  public void setLpc_summ(double lpc_summ)
  {
    this.lpc_summ = lpc_summ;
  }

  public double getLpc_sum_transport()
  {
    return lpc_sum_transport;
  }

  public String getLpc_sum_transport_formatted()
  {
    return StringUtil.double2appCurrencyString(lpc_sum_transport);
  }

  public void setLpc_sum_transport(double lpc_sum_transport)
  {
    this.lpc_sum_transport = lpc_sum_transport;
  }

  public double getLpc_custom()
  {
    return lpc_custom;
  }

  public String getLpc_custom_formatted()
  {
    return StringUtil.double2appCurrencyString(lpc_custom);
  }

  public void setLpc_custom(double lpc_custom)
  {
    this.lpc_custom = lpc_custom;
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

  public String getNoRound()
  {
    return noRound;
  }

  public void setNoRound(String noRound)
  {
    this.noRound = noRound;
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

  public String getOrd_id()
  {
    return ord_id;
  }

  public void setOrd_id(String ord_id)
  {
    this.ord_id = ord_id;
  }

  public boolean isItogString()
  {
    return itogString;
  }

  public void setItogString(boolean itogString)
  {
    this.itogString = itogString;
  }

}
