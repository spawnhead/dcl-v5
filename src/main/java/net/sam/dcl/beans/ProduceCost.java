package net.sam.dcl.beans;

import net.sam.dcl.service.helper.NomenclatureProduceCustomCodeHistoryHelper;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.StrutsUtil;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.controller.ActionContext;
import net.sam.dcl.dbo.DboCustomCode;

import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;
import java.lang.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by IntelliJ IDEA.
 * Date: 31.08.2005
 * Time: 18:53:07
 * To change this template use File | Settings | File Templates.
 */
public class ProduceCost implements Serializable
{
  protected static Log log = LogFactory.getLog(ProduceCost.class);

  String is_new_doc;

  String prc_id;

  User createUser = new User();
  User editUser = new User();
  String usr_date_create;
  String usr_date_edit;

  String prc_number;
  String prc_date;
  Route route = new Route();
  double prc_sum_transport;
  double prc_weight;
  double prc_course_ltl_eur;
  String prc_block;

  double prc_produce_count;
  double prc_produce_count_rest;
  String prc_1c_numbers_list;

  List<ProduceCostProduce> producesCost = new ArrayList<ProduceCostProduce>();
  int countItogRecord;

  List<ProduceCostCustom> customCodes = new ArrayList<ProduceCostCustom>();

  boolean processed;

  public ProduceCost()
  {
  }

  public ProduceCost(ProduceCost produceCost)
  {
    prc_id = produceCost.getPrc_id();
    createUser = produceCost.getCreateUser();
    editUser = produceCost.getEditUser();
    usr_date_create = produceCost.getUsr_date_create();
    usr_date_edit = produceCost.getUsr_date_edit();
    prc_number = produceCost.getPrc_number();
    prc_date = produceCost.getPrc_date();
    route = produceCost.getRoute();
    prc_sum_transport = produceCost.getPrc_sum_transport();
    prc_weight = produceCost.getPrc_weight();
    prc_course_ltl_eur = produceCost.getPrc_course_ltl_eur();
    prc_block = produceCost.getPrc_block();
    prc_produce_count = produceCost.getPrc_produce_count();
    prc_produce_count_rest = produceCost.getPrc_produce_count_rest();
    prc_1c_numbers_list = produceCost.getPrc_1c_numbers_list();
  }

  public ProduceCost(String con_id)
  {
    this.prc_id = con_id;
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

  public String getPrc_id()
  {
    return prc_id;
  }

  public void setPrc_id(String prc_id)
  {
    this.prc_id = prc_id;
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

  public String getPrc_date_ts()
  {
    return StringUtil.appDateString2dbDateString(prc_date);
  }

  public String getPrc_date_tm()
  {
    String tm_date = StringUtil.appDateString2dbDateString(prc_date);
    tm_date += " 23:59:59";
    return tm_date;
  }

  public void setPrc_date(String prc_date)
  {
    this.prc_date = prc_date;
  }

  public void setPrc_date_ts(String dt)
  {
    prc_date = StringUtil.dbDateString2appDateString(dt);
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
    return prc_sum_transport;
  }

  public void setPrc_sum_transport(double prc_sum_transport)
  {
    this.prc_sum_transport = prc_sum_transport;
  }

  public double getPrc_weight()
  {
    return prc_weight;
  }

  public void setPrc_weight(double prc_weight)
  {
    this.prc_weight = prc_weight;
  }

  public double getPrc_course_ltl_eur()
  {
    return prc_course_ltl_eur;
  }

  public void setPrc_course_ltl_eur(double prc_course_ltl_eur)
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

  public double getPrc_produce_count()
  {
    return prc_produce_count;
  }

  public void setPrc_produce_count(double prc_produce_count)
  {
    this.prc_produce_count = prc_produce_count;
  }

  public double getPrc_produce_count_rest()
  {
    return prc_produce_count_rest;
  }

  public void setPrc_produce_count_rest(double prc_produce_count_rest)
  {
    this.prc_produce_count_rest = prc_produce_count_rest;
  }

  public String getPrc_1c_numbers_list()
  {
    return prc_1c_numbers_list;
  }

  public void setPrc_1c_numbers_list(String prc_1c_numbers_list)
  {
    this.prc_1c_numbers_list = prc_1c_numbers_list;
  }

  public List<ProduceCostProduce> getProducesCost()
  {
    return producesCost;
  }

  public void setProducesCost(List<ProduceCostProduce> producesCost)
  {
    this.producesCost = producesCost;
  }

  public void calculateInString(List<ProduceCostProduce> lstProduceCostProduces)
  {
    List<ProduceCostProduce> lstProd = producesCost;
    if (null != lstProduceCostProduces)
    {
      lstProd = lstProduceCostProduces;
    }
    List<ProduceCostCustom> customCodesLocal = new ArrayList<ProduceCostCustom>();

    double transport;
    transport = getPrc_sum_transport();

    double weight = 0.0;
    for (ProduceCostProduce produceCostProduce : lstProd)
    {
      if ( prc_course_ltl_eur != 0.0 )
      {
        produceCostProduce.setLpc_cost_one(produceCostProduce.getLpc_cost_one_ltl() / prc_course_ltl_eur);
        produceCostProduce.setLpc_cost_one(StringUtil.roundN(produceCostProduce.getLpc_cost_one(), 2));
      }
      else
      {
        produceCostProduce.setLpc_cost_one(0.0);  
      }

      if ( produceCostProduce.getLpc_percent_dcl_1_4() != 0.0 )
      {
        //Double percent = new Double(produceCostProduce.getLpc_percent_dcl_1_4());
        produceCostProduce.setLpc_percent(produceCostProduce.getLpc_percent_dcl_1_4());
        produceCostProduce.setManualPercent(true);
      }
      else
      {
        DboCustomCode cusCode = null;
        try
        {
          cusCode = produceCostProduce.getProduce().getCustomCodeForDate(StringUtil.appDateString2Date(prc_date), NomenclatureProduceCustomCodeHistoryHelper.loadLastCustomCodeByDateAndProduce(prc_date, produceCostProduce.getProduce().getId()));
        }
        catch (Exception e)
        {
          log.error(e);
        }
        if ( null != cusCode )
        {
          produceCostProduce.setLpc_percent(cusCode.getPercent().doubleValue());
          produceCostProduce.setManualPercent(false);
        }
      }

      weight += produceCostProduce.getLpc_weight();
    }
    weight = StringUtil.roundN(weight, 3);

    for (int i = 0; i < lstProd.size(); i++)
    {
      ProduceCostProduce produceCostProduce = lstProd.get(i);

      produceCostProduce.setNumber(String.valueOf(i + 1));
      if ( weight != 0.0 )
      {
        produceCostProduce.setLpc_sum_transport(StringUtil.roundN(transport / weight * produceCostProduce.getLpc_weight(), 2));
      }
      else
      {
        produceCostProduce.setLpc_sum_transport(0.0);
      }

      produceCostProduce.setLpc_custom(StringUtil.roundN(produceCostProduce.getLpc_custom(), 2));

      ProduceCostCustom produceCostCustom = findProduceCostCustom(produceCostProduce.getLpc_percent(), customCodesLocal);
      if (null != produceCostCustom)
      {
        produceCostCustom.setLpc_summ(produceCostCustom.getLpc_summ() + produceCostProduce.getLpc_summ());
      }
      else
      {
        ProduceCostCustom produceCostCustomNew = getEmptyProduceCostCustom();
        produceCostCustomNew.setLpc_percent(produceCostProduce.getLpc_percent());
        produceCostCustomNew.setLpc_summ(produceCostProduce.getLpc_summ());

        //вставляем с сортировкой методом вставок
        boolean inserted = false;
        for (int j = 0; j < customCodesLocal.size(); j++)
        {
          ProduceCostCustom produceCostCustomForInsert = customCodesLocal.get(j);

          if (produceCostCustomForInsert.getLpc_percent() > produceCostCustomNew.getLpc_percent())
          {
            customCodesLocal.add(j, produceCostCustomNew);
            inserted = true;
            break;
          }
        }

        //не вставили раньше
        if ( !inserted )
        {
          customCodesLocal.add(customCodesLocal.size(), produceCostCustomNew);
        }
      }

      lstProd.set(i, produceCostProduce);
    }

    for (ProduceCostCustom produceCostCustom : customCodesLocal)
    {
      ProduceCostCustom produceCostCustomOld = findProduceCostCustom(produceCostCustom.getLpc_percent(), null);
      if (null != produceCostCustomOld)
      {
        produceCostCustom.setLpc_summ_allocation(produceCostCustomOld.getLpc_summ_allocation());
      }
    }

    customCodes.clear();
    customCodes.addAll(customCodesLocal);

    for (int i = 0; i < lstProd.size(); i++)
    {
      ProduceCostProduce produceCostProduce = lstProd.get(i);
      ProduceCostCustom produceCostCustom = findProduceCostCustom(produceCostProduce.getLpc_percent(), null);

      if (produceCostCustom != null)
      {
        if ( produceCostCustom.getLpc_summ() != 0.0 )
        {
          produceCostProduce.setLpc_custom(StringUtil.roundN(produceCostCustom.getLpc_summ_allocation() / produceCostCustom.getLpc_summ() * produceCostProduce.getLpc_summ() ,2));
        }
        else
        {
          produceCostProduce.setLpc_custom(0.0);
        }
      }

      lstProd.set(i, produceCostProduce);
    }
  }

  static public ProduceCostProduce getEmptyProduceCostProduce()
  {
    ProduceCostProduce produceCostProduce = new ProduceCostProduce();
    produceCostProduce.setNumber("");
    produceCostProduce.setLpc_id("");
    produceCostProduce.setPrc_id("");
    produceCostProduce.setLpc_produce_name("");
    produceCostProduce.setStuffCategory(new StuffCategory("", ""));
    produceCostProduce.setManager(new User());
    produceCostProduce.setDepartment(new Department());
    produceCostProduce.setLpc_count(0.0);
    produceCostProduce.setLpc_cost_one_ltl(0.0);
    produceCostProduce.setLpc_cost_one_by(0.0);
    produceCostProduce.setLpc_price_list_by(0.0);
    produceCostProduce.setLpc_cost_one(0.0);
    produceCostProduce.setLpc_weight(0.0);
    produceCostProduce.setLpc_percent(0.0);
    produceCostProduce.setManualPercent(true);
    produceCostProduce.setLpc_summ(0.0);
    produceCostProduce.setLpc_1c_number("");

    produceCostProduce.setLpc_sum_transport(0.0);
    produceCostProduce.setLpc_custom(0.0);
    produceCostProduce.setPurpose(new Purpose());

    return produceCostProduce;
  }

  public void calculate(List<ProduceCostProduce> lstProduceCostProduces)
  {
    List<ProduceCostProduce> lstProd = producesCost;
    if (null != lstProduceCostProduces)
    {
      lstProd = lstProduceCostProduces;
    }

    for (int i = 0; i < countItogRecord; i++)
    {
      lstProd.remove(lstProd.size() - 1);
    }

    calculateInString(lstProd);

    countItogRecord = 1;

    double weight = 0.0;
    double transport = 0.0;
    double summ = 0.0;
    double custom = 0.0;
    //double lpc_percent = 0.0;

    for (ProduceCostProduce produceCostProduce : producesCost)
    {
      weight += StringUtil.roundN(produceCostProduce.getLpc_weight(), 3);
      transport += StringUtil.roundN(produceCostProduce.getLpc_sum_transport(), 2);
      summ += StringUtil.roundN(produceCostProduce.getLpc_summ(), 2);
      custom += StringUtil.roundN(produceCostProduce.getLpc_custom(), 2);
      //lpc_percent += StringUtil.roundN(produceCostProduce.getLpc_percent_dcl_1_4(), 3);
    }

    ProduceCostProduce produceCostProduce = getEmptyProduceCostProduce();
    IActionContext context = ActionContext.threadInstance();
    try
    {
      produceCostProduce.setLpc_produce_name(StrutsUtil.getMessage(context, "ProduceCost.total"));
    }
    catch (Exception e)
    {
      log.error(e);
    }
    produceCostProduce.setLpc_weight(weight);
    produceCostProduce.setLpc_sum_transport(transport);
    produceCostProduce.setLpc_summ(summ);
    produceCostProduce.setLpc_custom(custom);
    produceCostProduce.setItogString(true);

    lstProd.add(produceCostProduce);
  }

  public void setListParentIds()
  {
    for (int i = 0; i < producesCost.size() - countItogRecord; i++)
    {
      ProduceCostProduce produceCostProduce = producesCost.get(i);
      produceCostProduce.setPrc_id(getPrc_id());
    }

    for (ProduceCostCustom produceCostCustom : customCodes)
    {
      produceCostCustom.setPrc_id(getPrc_id());
    }
  }

  public void setListIdsToNull()
  {
    for (int i = 0; i < producesCost.size() - countItogRecord; i++)
    {
      ProduceCostProduce produceCostProduce = producesCost.get(i);
      produceCostProduce.setLpc_id(null);
    }
  }

  public ProduceCostProduce findProduceCostProduce(String number)
  {
    for (int i = 0; i < producesCost.size() - countItogRecord; i++)
    {
      ProduceCostProduce produceCostProduce = producesCost.get(i);

      if (produceCostProduce.getNumber().equalsIgnoreCase(number))
      {
        return produceCostProduce;
      }
    }

    return null;
  }

  public boolean findEmptyUserCode()
  {
    for (int i = 0; i < producesCost.size() - countItogRecord; i++)
    {
      ProduceCostProduce produceCostProduce = producesCost.get(i);

      if ( StringUtil.isEmpty(produceCostProduce.getManager().getUsr_code()) )
      {
        return true;
      }
    }

    return false;
  }

  public void updateProduceCostProduce(String number, ProduceCostProduce produceCostProduceIn)
  {
    for (int i = 0; i < producesCost.size() - countItogRecord; i++)
    {
      ProduceCostProduce produceCostProduce = producesCost.get(i);

      if (produceCostProduce.getNumber().equalsIgnoreCase(number))
      {
        producesCost.set(i, produceCostProduceIn);
        return;
      }
    }
  }

  public void deleteProduceCostProduce(String number)
  {
    for (int i = 0; i < producesCost.size() - countItogRecord; i++)
    {
      ProduceCostProduce produceCostProduce = producesCost.get(i);

      if (produceCostProduce.getNumber().equalsIgnoreCase(number))
        producesCost.remove(i);
    }
  }

  public void insertProduceCostProduce(ProduceCostProduce lpc_in)
  {
    producesCost.add(producesCost.size() - countItogRecord, lpc_in);
  }

  public int getCountItogRecord()
  {
    return countItogRecord;
  }

  public List<ProduceCostCustom> getCustomCodes()
  {
    return customCodes;
  }

  public void setCustomCodes(List<ProduceCostCustom> customCodes)
  {
    this.customCodes = customCodes;
  }

  public boolean isProcessed()
  {
    return processed;
  }

  public void setProcessed(boolean processed)
  {
    this.processed = processed;
  }

  public ProduceCostCustom getEmptyProduceCostCustom()
  {
    ProduceCostCustom produceCostCustom = new ProduceCostCustom();
    produceCostCustom.setLpc_percent(0.0);
    produceCostCustom.setLpc_summ(0.0);
    produceCostCustom.setLpc_summ_allocation(0.0);

    return produceCostCustom;
  }

  public ProduceCostCustom findProduceCostCustom(double percent, List<ProduceCostCustom> cusIn)
  {
    List<ProduceCostCustom> customCodesLocal;
    if (null == cusIn)
    {
      customCodesLocal = getCustomCodes();
    }
    else
    {
      customCodesLocal = cusIn;
    }

    for (ProduceCostCustom produceCostCustom : customCodesLocal)
    {
      if (produceCostCustom.getLpc_percent() == percent)
        return produceCostCustom;
    }

    return null;
  }

  public void updateProduceCostCustom(double percent, ProduceCostCustom pcc_in)
  {
    for (int i = 0; i < customCodes.size(); i++)
    {
      ProduceCostCustom produceCostCustom = customCodes.get(i);

      if ( percent == produceCostCustom.getLpc_percent() )
      {
        customCodes.set(i, pcc_in);
        return;
      }
    }
  }

  public void insertProduceCostCustom(ProduceCostCustom produceCostCustom)
  {
    customCodes.add(customCodes.size(), produceCostCustom);
  }
}
