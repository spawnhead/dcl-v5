package net.sam.dcl.beans;

import net.sam.dcl.controller.ActionContext;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.dbo.DboCustomCode;
import net.sam.dcl.dbo.DboProduce;
import net.sam.dcl.service.helper.NomenclatureProduceCustomCodeHistoryHelper;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.StrutsUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SpecificationImport implements Serializable
{
  protected static Log log = LogFactory.getLog(SpecificationImport.class);
  String is_new_doc;

  String spi_id;
  String sip_id;

  User createUser = new User();
  User editUser = new User();
  String usr_date_create;
  String usr_date_edit;

  String spi_number;
  String spi_date;
  String spi_send_date;
  String spi_comment;
  double spi_course;
  double spi_koeff;
  String spi_arrive;
  String spi_arrive_from;
  double spi_cost;

  List<SpecificationImportProduce> produces = new ArrayList<SpecificationImportProduce>();

  boolean haveItog = false;
  boolean needRecalcPrices = false;

  String usr_id;
  String chief;

  public SpecificationImport()
  {
  }

  public SpecificationImport(String spi_id)
  {
    this.spi_id = spi_id;
  }

  public SpecificationImport(String spi_id, String sip_id)
  {
    this.spi_id = spi_id;
    this.sip_id = sip_id;
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

  public String getIs_new_doc()
  {
    return is_new_doc;
  }

  public void setIs_new_doc(String is_new_doc)
  {
    this.is_new_doc = is_new_doc;
  }

  public String getSpi_id()
  {
    return spi_id;
  }

  public void setSpi_id(String spi_id)
  {
    this.spi_id = spi_id;
  }

  public String getSip_id()
  {
    return sip_id;
  }

  public void setSip_id(String sip_id)
  {
    this.sip_id = sip_id;
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

  public String getSpi_date_ts()
  {
    return StringUtil.appDateString2dbDateString(spi_date);
  }

  public String getSpi_send_date()
  {
    return spi_send_date;
  }

  public void setSpi_send_date(String spi_send_date)
  {
    this.spi_send_date = spi_send_date;
  }

  public String getSpi_send_date_ts()
  {
    return StringUtil.appDateString2dbDateString(spi_send_date);
  }

  public void setSpi_date(String spi_date)
  {
    this.spi_date = spi_date;
  }

  public String getSpi_comment()
  {
    return spi_comment;
  }

  public void setSpi_comment(String spi_comment)
  {
    this.spi_comment = spi_comment;
  }

  public double getSpi_course()
  {
    return spi_course;
  }

  public String getSpiCourseFormatted()
  {
    return StringUtil.double2appCurrencyStringByMask(getSpi_course(), "#,##0.000000");
  }

  public void setSpi_course(double spi_course)
  {
    this.spi_course = spi_course;
  }

  public double getSpi_koeff()
  {
    return spi_koeff;
  }

  public String getSpi_koeff_formatted()
  {
    return StringUtil.double2appCurrencyStringByMask(spi_koeff, "#,##0.000");
  }

  public void setSpi_koeff(double spi_koeff)
  {
    this.spi_koeff = spi_koeff;
  }

  public String getSpi_arrive()
  {
    return spi_arrive;
  }

  public void setSpi_arrive(String spi_arrive)
  {
    this.spi_arrive = spi_arrive;
  }

  public String getSpi_arrive_from()
  {
    return spi_arrive_from;
  }

  public void setSpi_arrive_from(String spi_arrive_from)
  {
    this.spi_arrive_from = spi_arrive_from;
  }

  public double getSpi_cost()
  {
    return spi_cost;
  }

  public void setSpi_cost(double spi_cost)
  {
    this.spi_cost = spi_cost;
  }

  public List<SpecificationImportProduce> getProduces()
  {
    return produces;
  }

  public void setProduces(List<SpecificationImportProduce> produces)
  {
    this.produces = produces;
  }

  public boolean isHaveItog()
  {
    return haveItog;
  }

  public void setHaveItog(boolean haveItog)
  {
    this.haveItog = haveItog;
  }

  public boolean isNeedRecalcPrices()
  {
    return needRecalcPrices;
  }

  public void setNeedRecalcPrices(boolean needRecalcPrices)
  {
    this.needRecalcPrices = needRecalcPrices;
  }

  public String getUsr_id()
  {
    return usr_id;
  }

  public void setUsr_id(String usr_id)
  {
    this.usr_id = usr_id;
  }

  public String getChief()
  {
    return chief;
  }

  public void setChief(String chief)
  {
    this.chief = chief;
  }

  public void calculateInString()
  {
    try
    {
      for (int i = 0; i < produces.size(); i++)
      {
        double sipPrice = 0.0;
        double sipCost;

        SpecificationImportProduce importProduce = produces.get(i);

        DboCustomCode cusCode = null;
        cusCode = importProduce.getProduce().getCustomCodeForDate(StringUtil.appDateString2Date(getSpi_date()), NomenclatureProduceCustomCodeHistoryHelper.loadLastCustomCodeByDateAndProduce(getSpi_date(), importProduce.getProduce().getId()));

        if (null != cusCode)
        {
          String code = cusCode.getCode();
          IActionContext context = ActionContext.threadInstance();
          if (!StringUtil.isEmpty(cusCode.getLaw240Flag()))
          {
            code += " " + StrutsUtil.getMessage(context, "SpecificationImport.law240Flag");
          }
          if (!StringUtil.isEmpty(cusCode.getBlock()))
          {
            code += " " + StrutsUtil.getMessage(context, "SpecificationImport.block");
          }
          importProduce.setCustomCode(code);
        }
        if (null != cusCode && importProduce.getSip_percent() == 0.0)
        {
          importProduce.setCustomPercent(cusCode.getPercent());
          importProduce.setSip_percent(cusCode.getPercent().doubleValue());
        }
        importProduce.setCustomPercent(new BigDecimal(importProduce.getSip_percent()));

        if (spi_course != 0.0 && spi_koeff != 0.0)
        {
          if (needRecalcPrices || !importProduce.isSip_price_calculated())
          {
            double percent = 0;
            if (null != cusCode)
            {
              percent = cusCode.getPercent().doubleValue();
            }
            importProduce.calculateSipCost(percent, spi_course, spi_koeff);
          }

          sipPrice = importProduce.getSip_price();
        }

        sipCost = sipPrice * importProduce.getSip_count();
        sipCost = StringUtil.roundN(sipCost, 2);
        importProduce.setSip_cost(sipCost);

        importProduce.setNumber(Integer.toString(i));
      }
    }
    catch (Exception e)
    {
      log.error(e);
    }
  }

  // defines ordering based on first name
  class CustomCodeComparator implements Comparator<SpecificationImportProduce>
  {
    public int compare(SpecificationImportProduce importProduce1, SpecificationImportProduce importProduce2)
    {
      if (StringUtil.isEmpty(importProduce1.getCustomCode())) return -1;
      if (StringUtil.isEmpty(importProduce2.getCustomCode())) return 1;
      return importProduce1.getCustomCode().compareTo(importProduce2.getCustomCode());
    }
  }

  class CustomerComparator implements Comparator<SpecificationImportProduce>
  {
    public int compare(SpecificationImportProduce importProduce1, SpecificationImportProduce importProduce2)
    {
      if (StringUtil.isEmpty(importProduce1.getCustomer().getName())) return -1;
      if (StringUtil.isEmpty(importProduce2.getCustomer().getName())) return 1;
      return importProduce1.getCustomer().getName().compareTo(importProduce2.getCustomer().getName());
    }
  }

  public static SpecificationImportProduce getEmptyProduce()
  {
    SpecificationImportProduce specificationImportProduce = new SpecificationImportProduce();
    specificationImportProduce.setSpi_id("");
    specificationImportProduce.setSip_id("");
    specificationImportProduce.setNumber("");
    specificationImportProduce.setProduce(new DboProduce());
    specificationImportProduce.setSip_count(0.0);
    specificationImportProduce.setDrp_price(0.0);
    specificationImportProduce.setStuffCategory(new StuffCategory());
    specificationImportProduce.setOrd_number("");
    specificationImportProduce.setOrd_date("");
    specificationImportProduce.setCustomer(new Contractor());
    specificationImportProduce.setCount_day(0);
    specificationImportProduce.setSpc_delivery_date("");
    specificationImportProduce.setPurpose(new Purpose());

    return specificationImportProduce;
  }

  public void calculate(boolean isEconomist)
  {
    if (haveItog)
    {
      produces.remove(produces.size() - 1);
    }
    calculateInString();
    if (isEconomist)
    {
      Collections.sort(produces, new CustomerComparator());
    }
    else
    {
      Collections.sort(produces, new CustomCodeComparator());
    }

    double sipCostTotal = 0.0;
    for (SpecificationImportProduce importProduce : produces)
    {
      sipCostTotal += importProduce.getSip_cost();
    }

    SpecificationImportProduce total = getEmptyProduce();
    total.setItog(true);
    sipCostTotal = StringUtil.roundN(sipCostTotal, 2);
    total.setSip_cost(sipCostTotal);
    produces.add(total);

    haveItog = true;
    needRecalcPrices = false;
    spi_cost = sipCostTotal;
  }

  public void calculateSpiCost()
  {
    double sipCostTotal = 0.0;
    for (int i = 0; i < produces.size() - 1; i++)
    {
      SpecificationImportProduce importProduce = produces.get(i);

      sipCostTotal += importProduce.getSip_cost();
    }

    SpecificationImportProduce importProduce = produces.get(produces.size() - 1);
    sipCostTotal = StringUtil.roundN(sipCostTotal, 2);
    importProduce.setSip_cost(sipCostTotal);
    spi_cost = sipCostTotal;
  }

  public void setListParentiIds()
  {
    for (SpecificationImportProduce importProduce : produces)
    {
      importProduce.setSpi_id(getSpi_id());
    }
  }

  public void setListIdsToNull()
  {
    for (SpecificationImportProduce importProduce : produces)
    {
      importProduce.setSip_id(null);
    }
  }

  public SpecificationImportProduce findProduce(String number)
  {
    for (SpecificationImportProduce importProduce : produces)
    {
      if (importProduce.getNumber().equalsIgnoreCase(number))
        return importProduce;
    }

    return null;
  }

  public void updateProduce(String number, SpecificationImportProduce importProduceIn)
  {
    for (int i = 0; i < produces.size(); i++)
    {
      SpecificationImportProduce importProduce = produces.get(i);

      if (importProduce.getNumber().equalsIgnoreCase(number))
      {
        produces.set(i, importProduceIn);
        return;
      }
    }
  }

  public void deleteProduce(String number)
  {
    for (int i = 0; i < produces.size(); i++)
    {
      SpecificationImportProduce importProduce = produces.get(i);

      if (importProduce.getNumber().equalsIgnoreCase(number))
      {
        produces.remove(i);
        break;
      }
    }
  }

  public void insertProduce(SpecificationImportProduce importProduce)
  {
    produces.add(produces.size(), importProduce);
  }
}
