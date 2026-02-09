package net.sam.dcl.beans;

import net.sam.dcl.controller.ActionContext;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.StrutsUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * Date: 31.08.2005
 * Time: 18:53:07
 * To change this template use File | Settings | File Templates.
 */
public class ShippingReport implements Serializable
{
  protected static Log log = LogFactory.getLog(ShippingReport.class);
  final static public String ecxel_separator = ", ";

  List<ShippingReportLine> shippingReportLines = new ArrayList<ShippingReportLine>();

  User user = new User();
  Department department = new Department();
  Contractor contractor = new Contractor();

  String onlyTotal;
  String itog_by_shp;
  String itog_by_user;
  String itog_by_product;
  String itog_by_produce;
  String not_include_zero;
  String include_all;
  String include_closed;
  String include_opened;

  String view_contractor;
  String view_country;
  String view_user_left;
  String view_department_left;
  String view_contract;
  String view_stuff_category;
  String view_produce;
  String view_summ;
  String view_summ_without_nds;
  String view_summ_eur;
  String view_user;
  String view_department;

  boolean showShpNumDate = true;
  boolean showLegend;
  Map<Integer, Boolean> users = new HashMap<Integer, Boolean>();
  Map<Integer, Boolean> products = new HashMap<Integer, Boolean>();
  Map<Integer, Boolean> produces = new HashMap<Integer, Boolean>();

  int count_itog;

  public ShippingReport()
  {
  }

  public List<ShippingReportLine> getShippingReportLines()
  {
    return shippingReportLines;
  }

  public void setShippingReportLines(List<ShippingReportLine> shippingReportLines)
  {
    this.shippingReportLines = shippingReportLines;
  }

  public User getUser()
  {
    return user;
  }

  public void setUser(User user)
  {
    this.user = user;
  }

  public Department getDepartment()
  {
    return department;
  }

  public void setDepartment(Department department)
  {
    this.department = department;
  }

  public Contractor getContractor()
  {
    return contractor;
  }

  public void setContractor(Contractor contractor)
  {
    this.contractor = contractor;
  }

  public String getOnlyTotal()
  {
    return onlyTotal;
  }

  public void setOnlyTotal(String onlyTotal)
  {
    this.onlyTotal = onlyTotal;
  }

  public String getItog_by_shp()
  {
    return itog_by_shp;
  }

  public void setItog_by_shp(String itog_by_shp)
  {
    this.itog_by_shp = itog_by_shp;
  }

  public String getItog_by_user()
  {
    return itog_by_user;
  }

  public void setItog_by_user(String itog_by_user)
  {
    this.itog_by_user = itog_by_user;
  }

  public String getItog_by_product()
  {
    return itog_by_product;
  }

  public void setItog_by_product(String itog_by_product)
  {
    this.itog_by_product = itog_by_product;
  }

  public String getItog_by_produce()
  {
    return itog_by_produce;
  }

  public void setItog_by_produce(String itog_by_produce)
  {
    this.itog_by_produce = itog_by_produce;
  }

  public String getNot_include_zero()
  {
    return not_include_zero;
  }

  public void setNot_include_zero(String not_include_zero)
  {
    this.not_include_zero = not_include_zero;
  }

  public String getInclude_all()
  {
    return include_all;
  }

  public void setInclude_all(String include_all)
  {
    this.include_all = include_all;
  }

  public String getInclude_closed()
  {
    return include_closed;
  }

  public void setInclude_closed(String include_closed)
  {
    this.include_closed = include_closed;
  }

  public String getInclude_opened()
  {
    return include_opened;
  }

  public void setInclude_opened(String include_opened)
  {
    this.include_opened = include_opened;
  }

  public String getView_contractor()
  {
    return view_contractor;
  }

  public void setView_contractor(String view_contractor)
  {
    this.view_contractor = view_contractor;
  }

  public String getView_country()
  {
    return view_country;
  }

  public void setView_country(String view_country)
  {
    this.view_country = view_country;
  }

  public String getView_user_left()
  {
    return view_user_left;
  }

  public void setView_user_left(String view_user_left)
  {
    this.view_user_left = view_user_left;
  }

  public String getView_department_left()
  {
    return view_department_left;
  }

  public void setView_department_left(String view_department_left)
  {
    this.view_department_left = view_department_left;
  }

  public String getView_contract()
  {
    return view_contract;
  }

  public void setView_contract(String view_contract)
  {
    this.view_contract = view_contract;
  }

  public String getView_stuff_category()
  {
    return view_stuff_category;
  }

  public void setView_stuff_category(String view_stuff_category)
  {
    this.view_stuff_category = view_stuff_category;
  }

  public String getView_produce()
  {
    return view_produce;
  }

  public void setView_produce(String view_produce)
  {
    this.view_produce = view_produce;
  }

  public String getView_summ()
  {
    return view_summ;
  }

  public void setView_summ(String view_summ)
  {
    this.view_summ = view_summ;
  }

  public String getView_summ_without_nds()
  {
    return view_summ_without_nds;
  }

  public void setView_summ_without_nds(String view_summ_without_nds)
  {
    this.view_summ_without_nds = view_summ_without_nds;
  }

  public String getView_summ_eur()
  {
    return view_summ_eur;
  }

  public void setView_summ_eur(String view_summ_eur)
  {
    this.view_summ_eur = view_summ_eur;
  }

  public String getView_user()
  {
    return view_user;
  }

  public void setView_user(String view_user)
  {
    this.view_user = view_user;
  }

  public String getView_department()
  {
    return view_department;
  }

  public void setView_department(String view_department)
  {
    this.view_department = view_department;
  }

  public boolean isShowShpNumDate()
  {
    return showShpNumDate;
  }

  public void setShowShpNumDate(boolean showShpNumDate)
  {
    this.showShpNumDate = showShpNumDate;
  }

  public boolean isShowLegend()
  {
    return showLegend;
  }

  public void setShowLegend(boolean showLegend)
  {
    this.showLegend = showLegend;
  }

  public int getCount_itog()
  {
    return count_itog;
  }

  public void setCount_itog(int count_itog)
  {
    this.count_itog = count_itog;
  }

  public void calculate()
  {
    showLegend = false;
    if (shippingReportLines.size() == 0)
    {
      return;
    }

    boolean onlyTotal = "1".equalsIgnoreCase(getOnlyTotal());

    boolean groupByShp = false;
    boolean groupByShpUser = false;
    boolean groupByShpProduct = false;
    boolean groupByShpProduce = false;
    if ("1".equalsIgnoreCase(getItog_by_shp()))
    {
      groupByShp = true;
      if ("1".equalsIgnoreCase(getItog_by_user()))
      {
        groupByShpUser = true;
      }
      if ("1".equalsIgnoreCase(getItog_by_product()))
      {
        groupByShpProduct = true;
      }
      if ("1".equalsIgnoreCase(getItog_by_produce()))
      {
        groupByShpProduce = true;
      }
    }

    boolean deleteEmptyManager = false;
    boolean deleteEmptyDepartment = false;
    if (onlyTotal)
    {
      if ("-1".equals(getUser().getUsr_id()) && !StringUtil.isEmpty(getUser().getUsr_surname()))
      {
        deleteEmptyManager = true;
      }
      if ("-1".equals(getDepartment().getId()) && !StringUtil.isEmpty(getDepartment().getName()))
      {
        deleteEmptyDepartment = true;
      }
    }

    // если у отгрузки "Нераспределённая сумма" >0
    //   И
    // Выводить итоги по каждой отгрузке = ДА
    //   И
    // не требуется разбивать итог отгрузки,
    // то сумму брать из поля "Сумма с НДС"
    for (ShippingReportLine shippingReportLine : getShippingReportLines())
    {
      shippingReportLine.setUseSumPlusNdsFromShipping(shippingReportLine.getShp_sum_nr() > 0 && groupByShp && !groupByShpUser && !groupByShpProduct && !groupByShpProduce);
    }

    int shpId = -1;
    int usrId = -1;
    int stfId = -1;
    int prdId = -1;
    List<ItogSumKey> listItogSum = new ArrayList<ItogSumKey>();
    ShippingReportLine shippingReportLineCurr = new ShippingReportLine();
    int i = 0;
    if (groupByShp)
    {
      shippingReportLineCurr = shippingReportLines.get(i);
      if (!StringUtil.isEmpty(shippingReportLineCurr.getShp_closed()))
      {
        showLegend = true;
      }

      ItogSumKey key = new ItogSumKey(shippingReportLineCurr.getCurrency());
      int idx = listItogSum.indexOf(key);
      if (idx == -1)
      {
        listItogSum.add(key);
      }
      else
      {
        key = listItogSum.get(idx);
      }
      key.setSum_with_nds(key.getSum_with_nds() + shippingReportLineCurr.getSumPlusNds());
      key.setSum_out_nds(key.getSum_out_nds() + shippingReportLineCurr.getLps_summ_out_nds());
      key.setSum_out_nds_eur(key.getSum_out_nds_eur() + shippingReportLineCurr.getLps_summ_out_nds_eur());
      key.setSum_zak(key.getSum_zak() + shippingReportLineCurr.getLps_summ_zak());
      key.setSum_transport(key.getSum_transport() + shippingReportLineCurr.getLps_sum_transport());
      key.setSum_custom(key.getSum_custom() + shippingReportLineCurr.getLps_custom());

      if (!groupByShpUser)
      {
        shippingReportLineCurr.setManager(shippingReportLineCurr.getManager() + ecxel_separator);
        shippingReportLineCurr.setDepartment(shippingReportLineCurr.getDepartment() + ecxel_separator);
      }
      if (!groupByShpProduct)
      {
        shippingReportLineCurr.setStf_name(shippingReportLineCurr.getStf_name() + ecxel_separator);
      }
      if (!groupByShpProduce)
      {
        shippingReportLineCurr.setPrdFullName(shippingReportLineCurr.getProduce_full_name() + ecxel_separator);
        shippingReportLineCurr.setCtn_number(shippingReportLineCurr.getCtn_number() + ecxel_separator);
      }

      i = 1;
      shpId = shippingReportLineCurr.getShp_id();
      usrId = shippingReportLineCurr.getManager_id();
      stfId = shippingReportLineCurr.getStf_id();
      prdId = shippingReportLineCurr.getPrd_id();

      users.clear();
      products.clear();
      produces.clear();

      users.put(usrId, true);
      products.put(stfId, true);
      produces.put(prdId, true);
    }
    while (i < shippingReportLines.size())
    {
      ShippingReportLine shippingReportLine = shippingReportLines.get(i);
      if (!StringUtil.isEmpty(shippingReportLine.getShp_closed()))
      {
        showLegend = true;
      }

      ItogSumKey key = new ItogSumKey(shippingReportLine.getCurrency());
      int idx = listItogSum.indexOf(key);
      if (idx == -1)
      {
        listItogSum.add(key);
      }
      else
      {
        key = listItogSum.get(idx);
      }
      key.setSum_with_nds(key.getSum_with_nds() + shippingReportLine.getSumPlusNds());
      key.setSum_out_nds(key.getSum_out_nds() + shippingReportLine.getLps_summ_out_nds());
      key.setSum_out_nds_eur(key.getSum_out_nds_eur() + shippingReportLine.getLps_summ_out_nds_eur());
      key.setSum_zak(key.getSum_zak() + shippingReportLine.getLps_summ_zak());
      key.setSum_transport(key.getSum_transport() + shippingReportLine.getLps_sum_transport());
      key.setSum_custom(key.getSum_custom() + shippingReportLine.getLps_custom());

      if (groupByShp)
      {
        boolean needRemoveLine = false;
        if (groupByShpUser)
        {
          if (shpId == shippingReportLine.getShp_id() && usrId == shippingReportLine.getManager_id())
          {
            needRemoveLine = true;
            if (null == products.get(shippingReportLine.getStf_id())) //нету такого продукта, добавляем в список
            {
              products.put(shippingReportLine.getStf_id(), true);
              if (!StringUtil.isEmpty(shippingReportLine.getStf_name()))
              {
                shippingReportLineCurr.setStf_name(shippingReportLineCurr.getStf_name() + shippingReportLine.getStf_name() + ecxel_separator);
              }
            }
            if (null == produces.get(shippingReportLine.getPrd_id())) //нету такого товара, добавляем в список
            {
              produces.put(shippingReportLine.getPrd_id(), true);
              shippingReportLineCurr.setPrdFullName(shippingReportLineCurr.getPrdFullName() + shippingReportLine.getProduce_full_name() + ecxel_separator);
              shippingReportLineCurr.setCtn_number(shippingReportLineCurr.getCtn_number() + shippingReportLine.getCtn_number() + ecxel_separator);
            }
          }
          else
          {
            shippingReportLineCurr.setStf_name(shippingReportLineCurr.getStf_name().substring(0, shippingReportLineCurr.getStf_name().length() - 2));
            shippingReportLineCurr.setPrdFullName(shippingReportLineCurr.getPrdFullName().substring(0, shippingReportLineCurr.getPrdFullName().length() - 2));
            shippingReportLineCurr.setCtn_number(shippingReportLineCurr.getCtn_number().substring(0, shippingReportLineCurr.getCtn_number().length() - 2));

            shippingReportLineCurr = shippingReportLine;

            shpId = shippingReportLineCurr.getShp_id();
            usrId = shippingReportLineCurr.getManager_id();

            products.clear();
            products.put(shippingReportLineCurr.getStf_id(), true);
            produces.clear();
            produces.put(shippingReportLineCurr.getPrd_id(), true);
            shippingReportLineCurr.setStf_name(shippingReportLineCurr.getStf_name() + ecxel_separator);
            shippingReportLineCurr.setPrdFullName(shippingReportLineCurr.getProduce_full_name() + ecxel_separator);
            shippingReportLineCurr.setCtn_number(shippingReportLineCurr.getCtn_number() + ecxel_separator);
          }
        }
        else if (groupByShpProduct)
        {
          if (shpId == shippingReportLine.getShp_id() && stfId == shippingReportLine.getStf_id())
          {
            needRemoveLine = true;
            if (null == users.get(shippingReportLine.getManager_id())) //нету такого пользователя, добавляем в список
            {
              users.put(shippingReportLine.getManager_id(), true);
              if (!StringUtil.isEmpty(shippingReportLine.getManager()))
              {
                shippingReportLineCurr.setManager(shippingReportLineCurr.getManager() + shippingReportLine.getManager() + ecxel_separator);
              }
              if (!StringUtil.isEmpty(shippingReportLine.getDepartment()))
              {
                shippingReportLineCurr.setDepartment(shippingReportLineCurr.getDepartment() + shippingReportLine.getDepartment() + ecxel_separator);
              }
            }
            if (null == produces.get(shippingReportLine.getPrd_id())) //нету такого товара, добавляем в список
            {
              produces.put(shippingReportLine.getPrd_id(), true);
              shippingReportLineCurr.setPrdFullName(shippingReportLineCurr.getPrdFullName() + shippingReportLine.getProduce_full_name() + ecxel_separator);
              shippingReportLineCurr.setCtn_number(shippingReportLineCurr.getCtn_number() + shippingReportLine.getCtn_number() + ecxel_separator);
            }
          }
          else
          {
            shippingReportLineCurr.setManager(shippingReportLineCurr.getManager().substring(0, shippingReportLineCurr.getManager().length() - 2));
            shippingReportLineCurr.setDepartment(shippingReportLineCurr.getDepartment().substring(0, shippingReportLineCurr.getDepartment().length() - 2));
            shippingReportLineCurr.setPrdFullName(shippingReportLineCurr.getPrdFullName().substring(0, shippingReportLineCurr.getPrdFullName().length() - 2));
            shippingReportLineCurr.setCtn_number(shippingReportLineCurr.getCtn_number().substring(0, shippingReportLineCurr.getCtn_number().length() - 2));

            shippingReportLineCurr = shippingReportLine;

            shpId = shippingReportLineCurr.getShp_id();
            stfId = shippingReportLineCurr.getStf_id();

            users.clear();
            users.put(shippingReportLineCurr.getManager_id(), true);
            produces.clear();
            produces.put(shippingReportLineCurr.getPrd_id(), true);
            shippingReportLineCurr.setManager(shippingReportLineCurr.getManager() + ecxel_separator);
            shippingReportLineCurr.setDepartment(shippingReportLineCurr.getDepartment() + ecxel_separator);
            shippingReportLineCurr.setPrdFullName(shippingReportLineCurr.getProduce_full_name() + ecxel_separator);
            shippingReportLineCurr.setCtn_number(shippingReportLineCurr.getCtn_number() + ecxel_separator);
          }
        }
        else if (groupByShpProduce)
        {
          if (shpId == shippingReportLine.getShp_id() && prdId == shippingReportLine.getPrd_id())
          {
            needRemoveLine = true;
            if (null == users.get(shippingReportLine.getManager_id())) //нету такого пользователя, добавляем в список
            {
              users.put(shippingReportLine.getManager_id(), true);
              if (!StringUtil.isEmpty(shippingReportLine.getManager()))
              {
                shippingReportLineCurr.setManager(shippingReportLineCurr.getManager() + shippingReportLine.getManager() + ecxel_separator);
              }
              if (!StringUtil.isEmpty(shippingReportLine.getDepartment()))
              {
                shippingReportLineCurr.setDepartment(shippingReportLineCurr.getDepartment() + shippingReportLine.getDepartment() + ecxel_separator);
              }
            }
            if (null == products.get(shippingReportLine.getStf_id())) //нету такого продукта, добавляем в список
            {
              products.put(shippingReportLine.getStf_id(), true);
              if (!StringUtil.isEmpty(shippingReportLine.getStf_name()))
              {
                shippingReportLineCurr.setStf_name(shippingReportLineCurr.getStf_name() + shippingReportLine.getStf_name() + ecxel_separator);
              }
            }
          }
          else
          {
            shippingReportLineCurr.setManager(shippingReportLineCurr.getManager().substring(0, shippingReportLineCurr.getManager().length() - 2));
            shippingReportLineCurr.setDepartment(shippingReportLineCurr.getDepartment().substring(0, shippingReportLineCurr.getDepartment().length() - 2));
            shippingReportLineCurr.setStf_name(shippingReportLineCurr.getStf_name().substring(0, shippingReportLineCurr.getStf_name().length() - 2));

            shippingReportLineCurr = shippingReportLine;

            shpId = shippingReportLineCurr.getShp_id();
            prdId = shippingReportLineCurr.getPrd_id();

            users.clear();
            users.put(shippingReportLineCurr.getManager_id(), true);
            products.clear();
            products.put(shippingReportLineCurr.getStf_id(), true);
            shippingReportLineCurr.setManager(shippingReportLineCurr.getManager() + ecxel_separator);
            shippingReportLineCurr.setDepartment(shippingReportLineCurr.getDepartment() + ecxel_separator);
            shippingReportLineCurr.setStf_name(shippingReportLineCurr.getStf_name() + ecxel_separator);
          }
        }
        else
        {
          if (shpId == shippingReportLine.getShp_id())
          {
            needRemoveLine = true;
            if (null == users.get(shippingReportLine.getManager_id())) //нету такого пользователя, добавляем в список
            {
              users.put(shippingReportLine.getManager_id(), true);
              if (!StringUtil.isEmpty(shippingReportLine.getManager()))
              {
                shippingReportLineCurr.setManager(shippingReportLineCurr.getManager() + shippingReportLine.getManager() + ecxel_separator);
              }
              if (!StringUtil.isEmpty(shippingReportLine.getDepartment()))
              {
                shippingReportLineCurr.setDepartment(shippingReportLineCurr.getDepartment() + shippingReportLine.getDepartment() + ecxel_separator);
              }
            }
            if (null == products.get(shippingReportLine.getStf_id())) //нету такого продукта, добавляем в список
            {
              products.put(shippingReportLine.getStf_id(), true);
              if (!StringUtil.isEmpty(shippingReportLine.getStf_name()))
              {
                shippingReportLineCurr.setStf_name(shippingReportLineCurr.getStf_name() + shippingReportLine.getStf_name() + ecxel_separator);
              }
            }
            if (null == produces.get(shippingReportLine.getPrd_id())) //нету такого товара, добавляем в список
            {
              produces.put(shippingReportLine.getPrd_id(), true);
              shippingReportLineCurr.setPrdFullName(shippingReportLineCurr.getPrdFullName() + shippingReportLine.getProduce_full_name() + ecxel_separator);
              shippingReportLineCurr.setCtn_number(shippingReportLineCurr.getCtn_number() + shippingReportLine.getCtn_number() + ecxel_separator);
            }
          }
          else
          {
            shippingReportLineCurr.setManager(shippingReportLineCurr.getManager().substring(0, shippingReportLineCurr.getManager().length() - 2));
            shippingReportLineCurr.setDepartment(shippingReportLineCurr.getDepartment().substring(0, shippingReportLineCurr.getDepartment().length() - 2));
            shippingReportLineCurr.setStf_name(shippingReportLineCurr.getStf_name().substring(0, shippingReportLineCurr.getStf_name().length() - 2));
            shippingReportLineCurr.setPrdFullName(shippingReportLineCurr.getPrdFullName().substring(0, shippingReportLineCurr.getPrdFullName().length() - 2));
            shippingReportLineCurr.setCtn_number(shippingReportLineCurr.getCtn_number().substring(0, shippingReportLineCurr.getCtn_number().length() - 2));

            shippingReportLineCurr = shippingReportLine;

            shpId = shippingReportLineCurr.getShp_id();

            users.clear();
            users.put(shippingReportLineCurr.getManager_id(), true);
            products.clear();
            products.put(shippingReportLineCurr.getStf_id(), true);
            produces.clear();
            produces.put(shippingReportLineCurr.getPrd_id(), true);
            shippingReportLineCurr.setManager(shippingReportLineCurr.getManager() + ecxel_separator);
            shippingReportLineCurr.setDepartment(shippingReportLineCurr.getDepartment() + ecxel_separator);
            shippingReportLineCurr.setStf_name(shippingReportLineCurr.getStf_name() + ecxel_separator);
            shippingReportLineCurr.setPrdFullName(shippingReportLineCurr.getProduce_full_name() + ecxel_separator);
            shippingReportLineCurr.setCtn_number(shippingReportLineCurr.getCtn_number() + ecxel_separator);
          }
        }

        if (needRemoveLine)
        {
          shippingReportLineCurr.setLps_count(shippingReportLineCurr.getLps_count() + shippingReportLine.getLps_count());
          shippingReportLineCurr.setLps_summ_out_nds(shippingReportLineCurr.getLps_summ_out_nds() + shippingReportLine.getLps_summ_out_nds());
          shippingReportLineCurr.setLps_summ_out_nds_eur(shippingReportLineCurr.getLps_summ_out_nds_eur() + shippingReportLine.getLps_summ_out_nds_eur());
					if (!shippingReportLine.isUseSumPlusNdsFromShipping()) {
						shippingReportLineCurr.setSumPlusNds(shippingReportLineCurr.getSumPlusNds() + shippingReportLine.getSumPlusNds());
					}
					else{
						shippingReportLineCurr.setSumPlusNds(shippingReportLine.getSumPlusNds());
					}
					shippingReportLineCurr.setLps_summ_zak(shippingReportLineCurr.getLps_summ_zak() + shippingReportLine.getLps_summ_zak());
          shippingReportLineCurr.setLps_sum_transport(shippingReportLineCurr.getLps_sum_transport() + shippingReportLine.getLps_sum_transport());
          shippingReportLineCurr.setLps_custom(shippingReportLineCurr.getLps_custom() + shippingReportLine.getLps_custom());

          shippingReportLines.remove(i);
          continue;
        }
      }

      if (onlyTotal)
      {
        shippingReportLine.setShowSum(true);
        if (deleteEmptyManager && StringUtil.isEmpty(shippingReportLine.getManager()))
        {
          shippingReportLines.remove(i);
          continue;
        }
        if (deleteEmptyDepartment && StringUtil.isEmpty(shippingReportLine.getDepartment()))
        {
          shippingReportLines.remove(i);
          continue;
        }
      }
      i++;
    }

    //обработка последней строки
    if (groupByShp)
    {
      if (!groupByShpUser)
      {
        shippingReportLineCurr.setManager(shippingReportLineCurr.getManager().substring(0, shippingReportLineCurr.getManager().length() - 2));
        shippingReportLineCurr.setDepartment(shippingReportLineCurr.getDepartment().substring(0, shippingReportLineCurr.getDepartment().length() - 2));
      }
      if (!groupByShpProduct)
      {
        shippingReportLineCurr.setStf_name(shippingReportLineCurr.getStf_name().substring(0, shippingReportLineCurr.getStf_name().length() - 2));
      }
      if (!groupByShpProduce)
      {
        shippingReportLineCurr.setPrdFullName(shippingReportLineCurr.getPrdFullName().substring(0, shippingReportLineCurr.getPrdFullName().length() - 2));
        shippingReportLineCurr.setCtn_number(shippingReportLineCurr.getCtn_number().substring(0, shippingReportLineCurr.getCtn_number().length() - 2));
      }
    }

    count_itog = listItogSum.size();
    if ( count_itog != 0 )
    {
      int currencyId = 0;
      String checkCurrency = listItogSum.get(currencyId).getCurrency();
      int j = 0;
      while ( j < shippingReportLines.size() )
      {
        ShippingReportLine shippingReportLineCurrent = shippingReportLines.get(j);
        if ( !checkCurrency.equals(shippingReportLineCurrent.getCurrency()) )
        {
          ShippingReportLine shippingReportLine = getEmptyLine();
          IActionContext context = ActionContext.threadInstance();
          try
          {
            shippingReportLine.setShowSum(true);
            shippingReportLine.setItogLine(true);
            if (onlyTotal)
            {
              if ("-1".equals(getContractor().getId()) && !StringUtil.isEmpty(getContractor().getName()))
              {
                shippingReportLine.setShp_contractor(StrutsUtil.getMessage(context, "ShippingReport.itogo"));
              }
              if (deleteEmptyManager)
              {
                shippingReportLine.setManager(StrutsUtil.getMessage(context, "ShippingReport.itogo"));
              }
              if (deleteEmptyDepartment)
              {
                shippingReportLine.setDepartment(StrutsUtil.getMessage(context, "ShippingReport.itogo"));
              }
            }
            else
            {
              if ("1".equals(getView_contractor()))
              {
                shippingReportLine.setShp_contractor(StrutsUtil.getMessage(context, "ShippingReport.itogo"));
              }
              else if ("1".equals(getView_contract()))
              {
                shippingReportLine.setCon_number(StrutsUtil.getMessage(context, "ShippingReport.itogo"));
              }
              else
              {
                shippingReportLine.setShp_number(StrutsUtil.getMessage(context, "ShippingReport.itogo"));
              }
            }
            shippingReportLine.setCurrency(listItogSum.get(currencyId).getCurrency());
            shippingReportLine.setSumPlusNds(listItogSum.get(currencyId).getSum_with_nds());
            shippingReportLine.setLps_summ_out_nds(listItogSum.get(currencyId).getSum_out_nds());
            shippingReportLine.setLps_summ_out_nds_eur(listItogSum.get(currencyId).getSum_out_nds_eur());
            shippingReportLine.setLps_summ_zak(listItogSum.get(currencyId).getSum_zak());
            shippingReportLine.setLps_sum_transport(listItogSum.get(currencyId).getSum_transport());
            shippingReportLine.setLps_custom(listItogSum.get(currencyId).getSum_custom());
            shippingReportLines.add(j, shippingReportLine);

            j++;
          }
          catch (Exception e)
          {
            log.error(e);
          }

          currencyId++;
          checkCurrency = listItogSum.get(currencyId).getCurrency();
        }

        j++;
      }

      //последняя строка
      ShippingReportLine shippingReportLine = getEmptyLine();
      IActionContext context = ActionContext.threadInstance();
      try
      {
        shippingReportLine.setShowSum(true);
        shippingReportLine.setItogLine(true);
        if (onlyTotal)
        {
          if ("-1".equals(getContractor().getId()) && !StringUtil.isEmpty(getContractor().getName()))
          {
            shippingReportLine.setShp_contractor(StrutsUtil.getMessage(context, "ShippingReport.itogo"));
          }
          if (deleteEmptyManager)
          {
            shippingReportLine.setManager(StrutsUtil.getMessage(context, "ShippingReport.itogo"));
          }
          if (deleteEmptyDepartment)
          {
            shippingReportLine.setDepartment(StrutsUtil.getMessage(context, "ShippingReport.itogo"));
          }
        }
        else
        {
          if ("1".equals(getView_contractor()))
          {
            shippingReportLine.setShp_contractor(StrutsUtil.getMessage(context, "ShippingReport.itogo"));
          }
          else if ("1".equals(getView_contract()))
          {
            shippingReportLine.setCon_number(StrutsUtil.getMessage(context, "ShippingReport.itogo"));
          }
          else
          {
            shippingReportLine.setShp_number(StrutsUtil.getMessage(context, "ShippingReport.itogo"));
          }
        }
        shippingReportLine.setCurrency(listItogSum.get(currencyId).getCurrency());
        shippingReportLine.setSumPlusNds(listItogSum.get(currencyId).getSum_with_nds());
        shippingReportLine.setLps_summ_out_nds(listItogSum.get(currencyId).getSum_out_nds());
        shippingReportLine.setLps_summ_out_nds_eur(listItogSum.get(currencyId).getSum_out_nds_eur());
        shippingReportLine.setLps_summ_zak(listItogSum.get(currencyId).getSum_zak());
        shippingReportLine.setLps_sum_transport(listItogSum.get(currencyId).getSum_transport());
        shippingReportLine.setLps_custom(listItogSum.get(currencyId).getSum_custom());
        shippingReportLines.add(j, shippingReportLine);
      }
      catch (Exception e)
      {
        log.error(e);
      }
      
    }
  }

  public ShippingReportLine getEmptyLine()
  {
    ShippingReportLine shippingReportLine = new ShippingReportLine();

    shippingReportLine.setShp_id(-1);
    shippingReportLine.setShp_number("");
    shippingReportLine.setShp_date("");
    shippingReportLine.setShp_contractor("");
    shippingReportLine.setShp_prd_count(0);
    shippingReportLine.setCon_number("");
    shippingReportLine.setCon_date("");
    shippingReportLine.setSpc_number("");
    shippingReportLine.setSpc_date("");
    shippingReportLine.setSpc_summ(0.0);
    shippingReportLine.setCurrency("");
    shippingReportLine.setPrd_id(-1);
    shippingReportLine.setPrd_name("");
    shippingReportLine.setPrd_type("");
    shippingReportLine.setPrd_params("");
    shippingReportLine.setPrd_add_params("");
    shippingReportLine.setCtn_number("");
    shippingReportLine.setStf_id(-1);
    shippingReportLine.setStf_name("");
    shippingReportLine.setLps_id("");
    shippingReportLine.setLps_count(0.0);
    shippingReportLine.setSumPlusNds(0.0);
    shippingReportLine.setLps_summ_out_nds(0);
    shippingReportLine.setLps_summ_out_nds_eur(0);
    shippingReportLine.setLps_summ_zak(0);
    shippingReportLine.setLps_sum_transport(0);
    shippingReportLine.setLps_custom(0);
    shippingReportLine.setManager_id(-1);
    shippingReportLine.setManager("");
    shippingReportLine.setDepartment("");

    return shippingReportLine;
  }

  public void cleanList()
  {
    shippingReportLines.clear();
  }

  public List getExcelTable()
  {
    List<Object> rows = new ArrayList<Object>();

    IActionContext context = ActionContext.threadInstance();

    List<Object> header = new ArrayList<Object>();

    try
    {
      if ("1".equals(view_contractor))
      {
        header.add(StrutsUtil.getMessage(context, "ShippingReport.contractor"));
      }
      if ("1".equals(view_country))
      {
        header.add(StrutsUtil.getMessage(context, "ShippingReport.country"));
      }
      if ("1".equals(view_user_left))
      {
        header.add(StrutsUtil.getMessage(context, "ShippingReport.manager"));
      }
      if ("1".equals(view_department_left))
      {
        header.add(StrutsUtil.getMessage(context, "ShippingReport.department"));
      }
      if ("1".equals(view_contract))
      {
        header.add(StrutsUtil.getMessage(context, "ShippingReport.con_number"));
        header.add(StrutsUtil.getMessage(context, "ShippingReport.con_date"));
        header.add(StrutsUtil.getMessage(context, "ShippingReport.spc_number"));
        header.add(StrutsUtil.getMessage(context, "ShippingReport.spc_date"));
        header.add(StrutsUtil.getMessage(context, "ShippingReport.spc_summ"));
        header.add(StrutsUtil.getMessage(context, "ShippingReport.currency"));
      }
      if (isShowShpNumDate())
      {
        header.add(StrutsUtil.getMessage(context, "ShippingReport.shp_number"));
        header.add(StrutsUtil.getMessage(context, "ShippingReport.shp_date"));
      }
      if ("1".equals(view_stuff_category))
      {
        header.add(StrutsUtil.getMessage(context, "ShippingReport.stf_name"));
      }
      if ("1".equals(view_produce))
      {
        header.add(StrutsUtil.getMessage(context, "ShippingReport.produce_full_name"));
        header.add(StrutsUtil.getMessage(context, "ShippingReport.ctn_number"));
        header.add(StrutsUtil.getMessage(context, "ShippingReport.lps_count"));
      }
      if ("1".equals(view_summ))
      {
        header.add(StrutsUtil.getMessage(context, "ShippingReport.sumPlusNds"));
      }
      if ("1".equals(view_summ_without_nds))
      {
        header.add(StrutsUtil.getMessage(context, "ShippingReport.lps_summ_out_nds"));
        header.add(StrutsUtil.getMessage(context, "ShippingReport.lps_summ_out_nds_eur"));
      }
      if ("1".equals(view_summ_eur))
      {
        header.add(StrutsUtil.getMessage(context, "ShippingReport.lps_summ_zak"));
        header.add(StrutsUtil.getMessage(context, "ShippingReport.lps_sum_transport"));
        header.add(StrutsUtil.getMessage(context, "ShippingReport.lps_custom"));
      }
      if ("1".equals(view_user))
      {
        header.add(StrutsUtil.getMessage(context, "ShippingReport.manager"));
      }
      if ("1".equals(view_department))
      {
        header.add(StrutsUtil.getMessage(context, "ShippingReport.department"));
      }
    }
    catch (Exception e)
    {
      log.error(e);
    }
    rows.add(header);

    for (ShippingReportLine shippingReportLine : shippingReportLines)
    {
      List<Object> record = new ArrayList<Object>();

      if ("1".equals(view_contractor))
      {
        record.add(shippingReportLine.getShp_contractor());
      }
      if ("1".equals(view_country))
      {
        record.add(shippingReportLine.getCountry());
      }
      if ("1".equals(view_user_left))
      {
        record.add(shippingReportLine.getManager());
      }
      if ("1".equals(view_department_left))
      {
        record.add(shippingReportLine.getDepartment());
      }
      if ("1".equals(view_contract))
      {
        record.add(shippingReportLine.getCon_number());
        record.add(shippingReportLine.getCon_date_formatted());
        record.add(shippingReportLine.getSpc_number());
        record.add(shippingReportLine.getSpc_date_formatted());
        record.add(shippingReportLine.getSpc_summ());
        record.add(shippingReportLine.getCurrency());
      }
      if (isShowShpNumDate())
      {
        record.add(shippingReportLine.getShp_number());
        record.add(shippingReportLine.getShp_date_formatted());
      }
      if ("1".equals(view_stuff_category))
      {
        record.add(shippingReportLine.getStf_name());
      }
      if ("1".equals(view_produce))
      {
        record.add(shippingReportLine.getProduce_full_name());
        record.add(shippingReportLine.getCtn_number());
        record.add(shippingReportLine.getLps_count());
      }
      if ("1".equals(view_summ))
      {
        record.add(shippingReportLine.getSumPlusNds());
      }
      if ("1".equals(view_summ_without_nds))
      {
        record.add(shippingReportLine.getLps_summ_out_nds());
        record.add(shippingReportLine.getLps_summ_out_nds_eur());
      }
      if ("1".equals(view_summ_eur))
      {
        record.add(shippingReportLine.getLps_summ_zak());
        record.add(shippingReportLine.getLps_sum_transport());
        record.add(shippingReportLine.getLps_custom());
      }
      if ("1".equals(view_user))
      {
        record.add(shippingReportLine.getManager());
      }
      if ("1".equals(view_department))
      {
        record.add(shippingReportLine.getDepartment());
      }

      rows.add(record);
    }

    return rows;
  }

  static public class ItogSumKey
  {
    String currency;
    double sum_with_nds;
    double sum_out_nds;
    double sum_out_nds_eur;
    double sum_zak;
    double sum_transport;
    double sum_custom;

    public ItogSumKey(String currency)
    {
      this.currency = currency;
    }

    public String getCurrency()
    {
      return currency;
    }

    public void setCurrency(String currency)
    {
      this.currency = currency;
    }

    public double getSum_with_nds()
    {
      return sum_with_nds;
    }

    public void setSum_with_nds(double sum_with_nds)
    {
      this.sum_with_nds = sum_with_nds;
    }

    public double getSum_out_nds()
    {
      return sum_out_nds;
    }

    public void setSum_out_nds(double sum_out_nds)
    {
      this.sum_out_nds = sum_out_nds;
    }

    public double getSum_out_nds_eur()
    {
      return sum_out_nds_eur;
    }

    public void setSum_out_nds_eur(double sum_out_nds_eur)
    {
      this.sum_out_nds_eur = sum_out_nds_eur;
    }

    public double getSum_zak()
    {
      return sum_zak;
    }

    public void setSum_zak(double sum_zak)
    {
      this.sum_zak = sum_zak;
    }

    public double getSum_transport()
    {
      return sum_transport;
    }

    public void setSum_transport(double sum_transport)
    {
      this.sum_transport = sum_transport;
    }

    public double getSum_custom()
    {
      return sum_custom;
    }

    public void setSum_custom(double sum_custom)
    {
      this.sum_custom = sum_custom;
    }

    public boolean equals(Object o)
    {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      ItogSumKey that = (ItogSumKey) o;

      if (currency != null ? !currency.equals(that.currency) : that.currency != null)
        return false;

      return true;
    }

    public int hashCode()
    {
      int result;
      result = (currency != null ? currency.hashCode() : 0);
      return result;
    }
  }
}
