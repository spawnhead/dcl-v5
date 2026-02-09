package net.sam.dcl.beans;

import net.sam.dcl.controller.ActionContext;
import net.sam.dcl.controller.IActionContext;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.util.StrutsUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Date: 31.08.2005
 * Time: 18:53:07
 * To change this template use File | Settings | File Templates.
 */
public class Margin implements Serializable
{
  protected static Log log = LogFactory.getLog(Margin.class);

  List<MarginLine> marginLines = new ArrayList<MarginLine>();

  User user = new User();
  Department department = new Department();
  Contractor contractor = new Contractor();
  StuffCategory stuffCategory = new StuffCategory();
  Route route = new Route();

  String user_aspect;
  String department_aspect;
  String contractor_aspect;
  String stuff_category_aspect;
  String route_aspect;
  String onlyTotal;
  String itog_by_spec;
  String itog_by_user;
  String itog_by_product;
  String get_not_block;

  String view_contractor;
  String view_country;
  String view_contract;
  String view_stuff_category;
  String view_shipping;
  String view_payment;
  String view_transport;
  String view_transport_sum;
  String view_custom;
  String view_other_sum;
  String view_montage_sum;
  String view_montage_time;
  String view_montage_cost;
  String view_update_sum;
  String view_summ_zak;
  String view_koeff;
  String view_user;
  String view_department;

  public Margin()
  {
  }

  public List<MarginLine> getMarginLines()
  {
    return marginLines;
  }

  public void setMarginLines(List<MarginLine> marginLines)
  {
    this.marginLines = marginLines;
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

  public StuffCategory getStuffCategory()
  {
    return stuffCategory;
  }

  public void setStuffCategory(StuffCategory stuffCategory)
  {
    this.stuffCategory = stuffCategory;
  }

  public Route getRoute()
  {
    return route;
  }

  public void setRoute(Route route)
  {
    this.route = route;
  }

  public String getUser_aspect()
  {
    return user_aspect;
  }

  public void setUser_aspect(String user_aspect)
  {
    this.user_aspect = user_aspect;
  }

  public String getDepartment_aspect()
  {
    return department_aspect;
  }

  public void setDepartment_aspect(String department_aspect)
  {
    this.department_aspect = department_aspect;
  }

  public String getContractor_aspect()
  {
    return contractor_aspect;
  }

  public void setContractor_aspect(String contractor_aspect)
  {
    this.contractor_aspect = contractor_aspect;
  }

  public String getStuff_category_aspect()
  {
    return stuff_category_aspect;
  }

  public void setStuff_category_aspect(String stuff_category_aspect)
  {
    this.stuff_category_aspect = stuff_category_aspect;
  }

  public String getRoute_aspect()
  {
    return route_aspect;
  }

  public void setRoute_aspect(String route_aspect)
  {
    this.route_aspect = route_aspect;
  }

  public String getOnlyTotal()
  {
    return onlyTotal;
  }

  public void setOnlyTotal(String onlyTotal)
  {
    this.onlyTotal = onlyTotal;
  }

  public String getItog_by_spec()
  {
    return itog_by_spec;
  }

  public void setItog_by_spec(String itog_by_spec)
  {
    this.itog_by_spec = itog_by_spec;
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

  public String getGet_not_block()
  {
    return get_not_block;
  }

  public void setGet_not_block(String get_not_block)
  {
    this.get_not_block = get_not_block;
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

  public String getView_shipping()
  {
    return view_shipping;
  }

  public void setView_shipping(String view_shipping)
  {
    this.view_shipping = view_shipping;
  }

  public String getView_payment()
  {
    return view_payment;
  }

  public void setView_payment(String view_payment)
  {
    this.view_payment = view_payment;
  }

  public String getView_transport()
  {
    return view_transport;
  }

  public void setView_transport(String view_transport)
  {
    this.view_transport = view_transport;
  }

  public String getView_transport_sum()
  {
    return view_transport_sum;
  }

  public void setView_transport_sum(String view_transport_sum)
  {
    this.view_transport_sum = view_transport_sum;
  }

  public String getView_custom()
  {
    return view_custom;
  }

  public void setView_custom(String view_custom)
  {
    this.view_custom = view_custom;
  }

  public String getView_other_sum()
  {
    return view_other_sum;
  }

  public void setView_other_sum(String view_other_sum)
  {
    this.view_other_sum = view_other_sum;
  }

  public String getView_montage_sum()
  {
    return view_montage_sum;
  }

  public void setView_montage_sum(String view_montage_sum)
  {
    this.view_montage_sum = view_montage_sum;
  }

  public String getView_montage_time()
  {
    return view_montage_time;
  }

  public void setView_montage_time(String view_montage_time)
  {
    this.view_montage_time = view_montage_time;
  }

  public String getView_montage_cost()
  {
    return view_montage_cost;
  }

  public void setView_montage_cost(String view_montage_cost)
  {
    this.view_montage_cost = view_montage_cost;
  }

  public String getView_update_sum()
  {
    return view_update_sum;
  }

  public void setView_update_sum(String view_update_sum)
  {
    this.view_update_sum = view_update_sum;
  }

  public String getView_summ_zak()
  {
    return view_summ_zak;
  }

  public void setView_summ_zak(String view_summ_zak)
  {
    this.view_summ_zak = view_summ_zak;
  }

  public String getView_koeff()
  {
    return view_koeff;
  }

  public void setView_koeff(String view_koeff)
  {
    this.view_koeff = view_koeff;
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

  public boolean isCheckAspect()
  {
    if (
            "1".equals(getUser_aspect()) ||
            "1".equals(getDepartment_aspect()) ||
            "1".equals(getContractor_aspect()) ||
            "1".equals(getStuff_category_aspect()) ||
            "1".equals(getRoute_aspect())
       )
    {
      return true;
    }

    return false;
  }

  public boolean isNotSelectAll()
  {
    if (
            ( !StringUtil.isEmpty(getUser().getUsr_name()) && !"-1".equals(getUser().getUsr_id()) ) ||
            ( !StringUtil.isEmpty(getDepartment().getName()) && !"-1".equals(getDepartment().getId()) ) ||
            ( !StringUtil.isEmpty(getContractor().getName()) && !"-1".equals(getContractor().getId()) ) ||
            ( !StringUtil.isEmpty(getStuffCategory().getName()) && !"-1".equals(getStuffCategory().getId()) ) ||
            ( !StringUtil.isEmpty(getRoute().getName()) && !"-1".equals(getRoute().getId()) )
       )
    {
      return true;
    }

    return false;
  }

  public boolean isManualGroup()
  {
    return isCheckAspect() && ( !isNotSelectAll() || StringUtil.isEmpty(getOnlyTotal()) );
  }

  public void calculate()
  {
    if (marginLines.size() == 0)
    {
      return;
    }

    for (MarginLine marginLine : marginLines)
    {
      marginLine.setStf_name_show(marginLine.getStf_name());
      marginLine.setShp_number_show(marginLine.getShpNumberWithCopy());
      marginLine.setShp_date_show(marginLine.getShp_date_formatted());
      marginLine.setUsr_name_show(marginLine.getUsr_name());
      marginLine.setDep_name_show(marginLine.getDep_name());
    }

    int i = 0;
    while ( i < marginLines.size() )
    {
      MarginLine marginLine = marginLines.get(i);
      if (StringUtil.isEmpty(marginLine.getPay_date_show()))
      {
        marginLine.setPay_date_show(marginLine.getPay_date_formatted());
      }

      int j = i + 1;
      while (j < marginLines.size())
      {
        MarginLine marginLineCmp = marginLines.get(j);

        if (
             !StringUtil.isEmpty(marginLine.getLps_id()) && marginLine.getLps_id().equalsIgnoreCase(marginLineCmp.getLps_id()) &&
             !StringUtil.isEmpty(marginLine.getUsr_id()) && marginLine.getUsr_id().equalsIgnoreCase(marginLineCmp.getUsr_id())
           )
        {
          if (marginLine.getPay_date_show().indexOf(marginLineCmp.getPay_date_formatted()) == -1)
          {
            marginLine.setPay_date_show(marginLine.getPay_date_show() + ReportDelimiterConsts.html_separator + marginLineCmp.getPay_date_formatted());
          }
          marginLines.remove(j);
          j--;
        }

        j++;
      }

      i++;
    }

    if ("1".equals(getItog_by_spec()))
    {
      i = 0;
      while (i <  marginLines.size())
      {
        MarginLine marginLine = marginLines.get(i);
        if (StringUtil.isEmpty(marginLine.getPay_date_show()))
        {
          marginLine.setPay_date_show(marginLine.getPay_date_formatted());
        }

        int j = i + 1;
        while (j < marginLines.size())
        {
          MarginLine marginLineCmp = marginLines.get(j);

          boolean is_same = !StringUtil.isEmpty(marginLine.getSpc_id()) && marginLine.getSpc_id().equalsIgnoreCase(marginLineCmp.getSpc_id());

          if ("1".equals(getItog_by_user()))
          {
            boolean is_same_user = !StringUtil.isEmpty(marginLine.getUsr_id()) && marginLine.getUsr_id().equalsIgnoreCase(marginLineCmp.getUsr_id());
            is_same = is_same & is_same_user;

            if ("1".equals(getItog_by_product()))
            {
              boolean is_same_product = !StringUtil.isEmpty(marginLine.getStf_id()) && marginLine.getStf_id().equalsIgnoreCase(marginLineCmp.getStf_id());
              is_same = is_same & is_same_product;
            }
          }

          if ( is_same )
          {
            if (marginLine.getPay_date_show().indexOf(marginLineCmp.getPay_date_formatted()) == -1)
            {
              marginLine.setPay_date_show(marginLine.getPay_date_show() + ReportDelimiterConsts.html_separator + marginLineCmp.getPay_date_formatted());
            }
            if (marginLine.getStf_name_show().indexOf(marginLineCmp.getStf_name()) == -1)
            {
              marginLine.setStf_name_show(marginLine.getStf_name_show() + ReportDelimiterConsts.html_separator + marginLineCmp.getStf_name());
            }
            if (marginLine.getShp_number_show().indexOf(marginLineCmp.getShp_number()) == -1)
            {
              marginLine.setShp_number_show(marginLine.getShp_number_show() + ReportDelimiterConsts.html_separator + marginLineCmp.getShpNumberWithCopy());
            }
            if (marginLine.getShp_date_show().indexOf(marginLineCmp.getShp_date_formatted()) == -1)
            {
              marginLine.setShp_date_show(marginLine.getShp_date_show() + ReportDelimiterConsts.html_separator + marginLineCmp.getShp_date_formatted());
            }
            marginLine.setLps_summ_eur(marginLine.getLps_summ_eur() + marginLineCmp.getLps_summ_eur());
            marginLine.setLps_summ(marginLine.getLps_summ() + marginLineCmp.getLps_summ());
            marginLine.setLps_sum_transport(marginLine.getLps_sum_transport() + marginLineCmp.getLps_sum_transport());
            marginLine.setLps_custom(marginLine.getLps_custom() + marginLineCmp.getLps_custom());
            marginLine.setLcc_charges(marginLine.getLcc_charges() + marginLineCmp.getLcc_charges());
            marginLine.setLcc_montage(marginLine.getLcc_montage() + marginLineCmp.getLcc_montage());
            marginLine.setLcc_transport(marginLine.getLcc_transport() + marginLineCmp.getLcc_transport());
            marginLine.setLps_montage_time(marginLine.getLps_montage_time() + marginLineCmp.getLps_montage_time());
            marginLine.setMontage_cost(marginLine.getMontage_cost() + marginLineCmp.getMontage_cost());
            marginLine.setLcc_update_sum(marginLine.getLcc_update_sum() + marginLineCmp.getLcc_update_sum());
            marginLine.setSumm(marginLine.getSumm() + marginLineCmp.getSumm());
            marginLine.setSumm_zak(marginLine.getSumm_zak() + marginLineCmp.getSumm_zak());
            marginLine.setMargin(marginLine.getMargin() + marginLineCmp.getMargin());
            if (marginLine.getUsr_name_show().indexOf(marginLineCmp.getUsr_name()) == -1)
            {
              marginLine.setUsr_name_show(marginLine.getUsr_name_show() + ReportDelimiterConsts.html_separator + marginLineCmp.getUsr_name());
            }
            if (marginLine.getDep_name_show().indexOf(marginLineCmp.getDep_name()) == -1)
            {
              marginLine.setDep_name_show(marginLine.getDep_name_show() + ReportDelimiterConsts.html_separator + marginLineCmp.getDep_name());
            }
            marginLines.remove(j);
            j--;
          }

          j++;
        }

        i++;
      }
    }

    double lpsSumEUR = 0.0;
    double lpsSum = 0.0;
    double lpsSumTransport = 0.0;
    double lpsCustom = 0.0;
    double lccCharges = 0.0;
    double lccMontage = 0.0;
    double lccTransport = 0.0;
    double lpsMontageTime = 0.0;
    double montageCost = 0.0;
    double lccUpdateSum = 0.0;
    double sum = 0.0;
    double sumZak = 0.0;
    double margin = 0.0;
    double koeff;

    double lpsSumEURGroup = 0.0;
    double lpsSumGroup = 0.0;
    double lpsSumTransportGroup = 0.0;
    double lpsCustomGroup = 0.0;
    double lccChargesGroup = 0.0;
    double lccMontageGroup = 0.0;
    double lccTransportGroup = 0.0;
    double lpsMontageTimeGroup = 0.0;
    double montageCostGroup = 0.0;
    double lccUpdateSumGroup = 0.0;
    double sumGroup = 0.0;
    double sumZakGroup = 0.0;
    double marginGroup = 0.0;
    double koeffGroup;

    String groupBy = "";

    if (isManualGroup()) //ручная группировка
    {
      if (!StringUtil.isEmpty(getUser().getUserFullName())) //group by user
      {
        groupBy += "usr_name";
      }
      if (!StringUtil.isEmpty(getDepartment().getName())) //group by department
      {
        groupBy += "dep_name";
      }
      if (!StringUtil.isEmpty(getContractor().getName())) //group by contractor
      {
        groupBy += "ctr_name";
      }
      if (!StringUtil.isEmpty(getStuffCategory().getName())) //group by stuffCategory
      {
        groupBy += "stf_name";
      }
      if (!StringUtil.isEmpty(getRoute().getName())) //group by route
      {
        groupBy += "rut_name";
      }

      if (!"1".equalsIgnoreCase(getOnlyTotal())) //итог не отмечен и групируем по - стандартная выборка всех строк с звезданутой групировкой
      {
        groupBy += ",";
      }
      else
      {
        groupBy += "+";
      }

      if ("1".equals(getUser_aspect())) //пользователь в разрезе пользователя - group by user
      {
        groupBy += "usr_name";
      }
      if ("1".equals(getDepartment_aspect())) //пользователь в разрезе отдела - group by Department
      {
        groupBy += "dep_name";
      }
      if ("1".equals(getContractor_aspect())) //пользователь в разрезе контрактора - group by Contractor
      {
        groupBy += "ctr_name";
      }
      if ("1".equals(getStuff_category_aspect())) //пользователь в разрезе категорий - group by stuffCategory
      {
        groupBy += "stf_name";
      }
      if ("1".equals(getRoute_aspect())) //пользователь в разрезе маршрутов - group by Route
      {
        groupBy += "rut_name";
      }
    }

    String compareStr = "";
    for (i = 0; i < marginLines.size(); i++)
    {
      double sumLine;
      double sumZakLine;
      double koeffLine;

      MarginLine marginLine = marginLines.get(i);

      String compareStrNew = "";
      if (isManualGroup()) //ручная группировка
      {
        if (groupBy.indexOf("+") != -1)//("param1+param2"))
        {
          compareStrNew = "";
          if (groupBy.indexOf("usr_name") != -1)
          {
            compareStrNew += marginLine.getUsr_name();
          }
          if (groupBy.indexOf("dep_name") != -1)
          {
            compareStrNew += marginLine.getDep_name();
          }
          if (groupBy.indexOf("ctr_name") != -1)
          {
            compareStrNew += marginLine.getCtr_name();
          }
          if (groupBy.indexOf("stf_name") != -1)
          {
            compareStrNew += marginLine.getStf_name();
          }
        }

        if (!compareStr.equals(compareStrNew))
        {
          if (!"".equals(compareStr))
          {
            if (sumZakGroup != 0)
            {
              koeffGroup = sumGroup / sumZakGroup;
              koeffGroup = StringUtil.roundN(koeffGroup, 2);
            }
            else
            {
              koeffGroup = Double.NaN;
            }

            MarginLine marginLineGroup = getEmptyLine();
            marginLineGroup.setLps_summ_eur(lpsSumEURGroup);
            marginLineGroup.setLps_summ(lpsSumGroup);
            marginLineGroup.setLps_sum_transport(lpsSumTransportGroup);
            marginLineGroup.setLps_custom(lpsCustomGroup);
            marginLineGroup.setLcc_charges(lccChargesGroup);
            marginLineGroup.setLcc_montage(lccMontageGroup);
            marginLineGroup.setLcc_transport(lccTransportGroup);
            marginLineGroup.setLps_montage_time(lpsMontageTimeGroup);
            marginLineGroup.setMontage_cost(montageCostGroup);
            marginLineGroup.setLcc_update_sum(lccUpdateSumGroup);
            marginLineGroup.setSumm(sumGroup);
            marginLineGroup.setSumm_zak(sumZakGroup);
            marginLineGroup.setMargin(marginGroup);
            marginLineGroup.setKoeff(koeffGroup);
            marginLineGroup.setItogLine(true);
            //для запятых еще ручная группировка не сделана и работает только с общим итогом - очень сложный алгоритм
            if (groupBy.indexOf("+") != -1)//("param1+param2"))
            {
              marginLines.add(i, marginLineGroup);
            }
            i++;
          }

          lpsSumEURGroup = 0.0;
          lpsSumGroup = 0.0;
          lpsSumTransportGroup = 0.0;
          lpsCustomGroup = 0.0;
          lccChargesGroup = 0.0;
          lccMontageGroup = 0.0;
          lccTransportGroup = 0.0;
          lpsMontageTimeGroup = 0.0;
          montageCostGroup = 0.0;
          lccUpdateSumGroup = 0.0;
          sumGroup = 0.0;
          sumZakGroup = 0.0;
          marginGroup = 0.0;

          compareStr = compareStrNew;
        }
      }

      sumLine = marginLine.getSumm();
      sumZakLine = marginLine.getSumm_zak();

      if (sumZakLine != 0)
      {
        koeffLine = sumLine / sumZakLine;
        koeffLine = StringUtil.roundN(koeffLine, 2);
      }
      else
      {
        koeffLine = Double.NaN;
      }
      marginLine.setKoeff(koeffLine);

      lpsSumEUR += marginLine.getLps_summ_eur();
      lpsSum += marginLine.getLps_summ();
      lpsSumTransport += marginLine.getLps_sum_transport();
      lpsCustom += marginLine.getLps_custom();
      lccCharges += marginLine.getLcc_charges();
      lccMontage += marginLine.getLcc_montage();
      lccTransport += marginLine.getLcc_transport();
      lpsMontageTime += marginLine.getLps_montage_time();
      montageCost += marginLine.getMontage_cost();
      lccUpdateSum += marginLine.getLcc_update_sum();
      sum += marginLine.getSumm();
      sumZak += marginLine.getSumm_zak();
      margin += marginLine.getMargin();

      if (isManualGroup()) //ручная группировка
      {
        lpsSumEURGroup += marginLine.getLps_summ_eur();
        lpsSumGroup += marginLine.getLps_summ();
        lpsSumTransportGroup += marginLine.getLps_sum_transport();
        lpsCustomGroup += marginLine.getLps_custom();
        lccChargesGroup += marginLine.getLcc_charges();
        lccMontageGroup += marginLine.getLcc_montage();
        lccTransportGroup += marginLine.getLcc_transport();
        lpsMontageTimeGroup += marginLine.getLps_montage_time();
        montageCostGroup += marginLine.getMontage_cost();
        lccUpdateSumGroup += marginLine.getLcc_update_sum();
        sumGroup += marginLine.getSumm();
        sumZakGroup += marginLine.getSumm_zak();
        marginGroup += marginLine.getMargin();
      }
    }

    if (isManualGroup()) //ручная группировка
    {
      if (sumZakGroup != 0)
      {
        koeffGroup = sumGroup / sumZakGroup;
        koeffGroup = StringUtil.roundN(koeffGroup, 2);
      }
      else
      {
        koeffGroup = Double.NaN;
      }

      MarginLine marginLineGroup = getEmptyLine();
      marginLineGroup.setLps_summ_eur(lpsSumEURGroup);
      marginLineGroup.setLps_summ(lpsSumGroup);
      marginLineGroup.setLps_sum_transport(lpsSumTransportGroup);
      marginLineGroup.setLps_custom(lpsCustomGroup);
      marginLineGroup.setLcc_charges(lccChargesGroup);
      marginLineGroup.setLcc_montage(lccMontageGroup);
      marginLineGroup.setLcc_transport(lccTransportGroup);
      marginLineGroup.setLps_montage_time(lpsMontageTimeGroup);
      marginLineGroup.setMontage_cost(montageCostGroup);
      marginLineGroup.setLcc_update_sum(lccUpdateSumGroup);
      marginLineGroup.setSumm(sumGroup);
      marginLineGroup.setSumm_zak(sumZakGroup);
      marginLineGroup.setMargin(marginGroup);
      marginLineGroup.setKoeff(koeffGroup);
      marginLineGroup.setItogLine(true);
      //для запятых еще ручная группировка не сделана и работает только с общим итогом - очень сложный алгоритм
      if (groupBy.indexOf("+") != -1)//("param1+param2"))
      {
        marginLines.add(marginLines.size(), marginLineGroup);
      }
    }

    if (sumZak != 0)
    {
      koeff = sum / sumZak;
      koeff = StringUtil.roundN(koeff, 2);
    }
    else
    {
      koeff = Double.NaN;
    }

    MarginLine marginLine = getEmptyLine();
    marginLine.setLps_summ_eur(lpsSumEUR);
    marginLine.setLps_summ(lpsSum);
    marginLine.setLps_sum_transport(lpsSumTransport);
    marginLine.setLps_custom(lpsCustom);
    marginLine.setLcc_charges(lccCharges);
    marginLine.setLcc_montage(lccMontage);
    marginLine.setLcc_transport(lccTransport);
    marginLine.setLps_montage_time(lpsMontageTime);
    marginLine.setMontage_cost(montageCost);
    marginLine.setLcc_update_sum(lccUpdateSum);
    marginLine.setSumm(sum);
    marginLine.setSumm_zak(sumZak);
    marginLine.setMargin(margin);
    marginLine.setKoeff(koeff);
    marginLine.setItogLine(true);
    marginLines.add(marginLines.size(), marginLine);
  }

  public MarginLine getEmptyLine()
  {
    MarginLine marginLine = new MarginLine();

    marginLine.setCtr_id("");
    marginLine.setCtr_name("");
    marginLine.setCon_id("");
    marginLine.setCon_number("");
    marginLine.setCon_date("");
    marginLine.setSpc_id("");
    marginLine.setSpc_number("");
    marginLine.setSpc_date("");
    marginLine.setSpc_summ(0.0);
    marginLine.setCur_id("");
    marginLine.setCur_name("");
    marginLine.setStf_id("");
    marginLine.setStf_name("");
    marginLine.setStf_name_show("");
    marginLine.setShp_id("");
    marginLine.setShp_number("");
    marginLine.setShp_number_show("");
    marginLine.setShp_date("");
    marginLine.setShp_date_show("");
    marginLine.setPay_id("");
    marginLine.setPay_date("");
    marginLine.setPay_date_show("");
    marginLine.setLps_id("");
    marginLine.setLps_summ_eur(0.0);
    marginLine.setLps_summ(0.0);
    marginLine.setLps_sum_transport(0.0);
    marginLine.setLps_custom(0.0);
    marginLine.setLcc_charges(0.0);
    marginLine.setLcc_montage(0.0);
    marginLine.setLcc_transport(0.0);
    marginLine.setLps_montage_time(0.0);
    marginLine.setMontage_cost(0.0);
    marginLine.setLcc_update_sum(0.0);
    marginLine.setSumm(0.0);
    marginLine.setSumm_zak(0.0);
    marginLine.setMargin(0.0);
    marginLine.setKoeff(0.0);
    marginLine.setUsr_id("");
    marginLine.setUsr_name("");
    marginLine.setUsr_name_show("");
    marginLine.setDep_id("");
    marginLine.setDep_name("");
    marginLine.setDep_name_show("");

    return marginLine;
  }

  public void cleanList()
  {
    marginLines.clear();
  }

  public List getExcelTable()
  {
    List<Object> rows = new ArrayList<Object>();

    IActionContext context = ActionContext.threadInstance();

    List<Object> header = new ArrayList<Object>();

    try
    {
      if ( "1".equals(view_contractor) )
      {
        header.add(StrutsUtil.getMessage(context, "Margin.ctr_name"));
      }
      if ( "1".equals(view_country) )
      {
        header.add(StrutsUtil.getMessage(context, "Margin.cut_name"));
      }
      if ( "1".equals(view_contract) )
      {
        header.add(StrutsUtil.getMessage(context, "Margin.con_number_exel"));
        header.add(StrutsUtil.getMessage(context, "Margin.con_date_exel"));
        header.add(StrutsUtil.getMessage(context, "Margin.spc_number_exel"));
        header.add(StrutsUtil.getMessage(context, "Margin.spc_date_exel"));
        header.add(StrutsUtil.getMessage(context, "Margin.spc_summ_exel"));
        header.add(StrutsUtil.getMessage(context, "Margin.cur_name"));
      }
      if ( "1".equals(view_stuff_category) )
      {
        header.add(StrutsUtil.getMessage(context, "Margin.stf_name_exel"));
      }
      if ( "1".equals(view_shipping) )
      {
        header.add(StrutsUtil.getMessage(context, "Margin.shp_number_exel"));
        header.add(StrutsUtil.getMessage(context, "Margin.shp_date_exel"));
      }
      if ( "1".equals(view_payment) )
      {
        header.add(StrutsUtil.getMessage(context, "Margin.pay_date_exel"));
      }
      header.add(StrutsUtil.getMessage(context, "Margin.lps_summ_eur"));
      header.add(StrutsUtil.getMessage(context, "Margin.lps_summ"));
      if ( "1".equals(view_transport) )
      {
        header.add(StrutsUtil.getMessage(context, "Margin.lps_sum_transport"));
      }
      if ( "1".equals(view_transport_sum) )
      {
        header.add(StrutsUtil.getMessage(context, "Margin.lcc_transport"));
      }
      if ( "1".equals(view_custom) )
      {
        header.add(StrutsUtil.getMessage(context, "Margin.lps_custom"));
      }
      if ( "1".equals(view_other_sum) )
      {
        header.add(StrutsUtil.getMessage(context, "Margin.lcc_charges"));
      }
      if ( "1".equals(view_montage_sum) )
      {
        header.add(StrutsUtil.getMessage(context, "Margin.lcc_montage"));
      }
      if ( "1".equals(view_montage_time) )
      {
        header.add(StrutsUtil.getMessage(context, "Margin.lps_montage_time"));
      }
      if ( "1".equals(view_montage_cost) )
      {
        header.add(StrutsUtil.getMessage(context, "Margin.montage_cost"));
      }
      if ( "1".equals(view_update_sum) )
      {
        header.add(StrutsUtil.getMessage(context, "Margin.lcc_update_sum"));
      }
      header.add(StrutsUtil.getMessage(context, "Margin.summ"));
      if ( "1".equals(view_summ_zak) )
      {
        header.add(StrutsUtil.getMessage(context, "Margin.summ_zak"));
      }
      header.add(StrutsUtil.getMessage(context, "Margin.margin"));
      if ( "1".equals(view_koeff) )
      {
        header.add(StrutsUtil.getMessage(context, "Margin.koeff_exel"));
      }
      if ( "1".equals(view_user) )
      {
        header.add(StrutsUtil.getMessage(context, "Margin.usr_name"));
      }
      if ( "1".equals(view_department) )
      {
        header.add(StrutsUtil.getMessage(context, "Margin.dep_name"));
      }
    }
    catch (Exception e)
    {
      log.error(e);
    }
    rows.add(header);

    for (int i = 0; i < marginLines.size(); i++)
    {
      MarginLine marginLine = marginLines.get(i);

      List<Object> record = new ArrayList<Object>();

      if ( "1".equals(view_contractor) )
      {
        record.add(marginLine.getCtr_name());
      }
      if ( "1".equals(view_country) )
      {
        record.add(marginLine.getCut_name());
      }
      if ( "1".equals(view_contract) )
      {
        record.add(marginLine.getCon_number_formatted_exel());
        record.add(marginLine.getCon_date_formatted());
        record.add(marginLine.getSpc_number_formatted_exel());
        record.add(marginLine.getSpc_date_formatted());
        if (i == marginLines.size() - 1){
          record.add("");
        } else {
          record.add(marginLine.getSpc_summ());
        }
        record.add(marginLine.getCur_name());
      }
      if ( "1".equals(view_stuff_category) )
      {
        String tmp_str = marginLine.getStf_name_show();
        tmp_str = tmp_str.replaceAll(ReportDelimiterConsts.html_separator, ReportDelimiterConsts.ecxel_separator);
        record.add(tmp_str);
      }
      if ( "1".equals(view_shipping) )
      {
        String tmpStr = marginLine.getShp_number_show();
        tmpStr = tmpStr.replaceAll(ReportDelimiterConsts.html_separator, ReportDelimiterConsts.ecxel_separator);
        tmpStr = tmpStr.replaceAll("&nbsp;<span style=\"color:red\"><b>", " ");
        tmpStr = tmpStr.replaceAll("</b></span>", "");
        record.add(tmpStr);

        tmpStr = marginLine.getShp_date_show();
        tmpStr = tmpStr.replaceAll(ReportDelimiterConsts.html_separator, ReportDelimiterConsts.ecxel_separator);
        record.add(tmpStr);
      }
      if ( "1".equals(view_payment) )
      {
        String tmpStr = marginLine.getPay_date_show();
        tmpStr = tmpStr.replaceAll(ReportDelimiterConsts.html_separator, ReportDelimiterConsts.ecxel_separator);
        record.add(tmpStr);
      }
      record.add(marginLine.getLps_summ_eur());
      record.add(marginLine.getLps_summ());
      if ( "1".equals(view_transport) )
      {
        record.add(marginLine.getLps_sum_transport());
      }
      if ( "1".equals(view_transport_sum) )
      {
        record.add(marginLine.getLcc_transport());
      }
      if ( "1".equals(view_custom) )
      {
        record.add(marginLine.getLps_custom());
      }
      if ( "1".equals(view_other_sum) )
      {
        record.add(marginLine.getLcc_charges());
      }
      if ( "1".equals(view_montage_sum) )
      {
        record.add(marginLine.getLcc_montage());
      }
      if ( "1".equals(view_montage_time) )
      {
        record.add(marginLine.getLps_montage_time());
      }
      if ( "1".equals(view_montage_cost) )
      {
        record.add(marginLine.getMontage_cost());
      }
      if ( "1".equals(view_update_sum) )
      {
        record.add(marginLine.getLcc_update_sum());
      }
      record.add(marginLine.getSumm());
      if ( "1".equals(view_summ_zak) )
      {
        record.add(marginLine.getSumm_zak());
      }
      record.add(marginLine.getMargin());
      if ( "1".equals(view_koeff) )
      {
        record.add(marginLine.getKoeff());
      }
      if ( "1".equals(view_user) )
      {
        String tmpStr = marginLine.getUsr_name_show();
        tmpStr = tmpStr.replaceAll(ReportDelimiterConsts.html_separator, ReportDelimiterConsts.ecxel_separator);
        record.add(tmpStr);
      }
      if ( "1".equals(view_department) )
      {
        String tmp_str = marginLine.getDep_name_show();
        tmp_str = tmp_str.replaceAll(ReportDelimiterConsts.html_separator, ReportDelimiterConsts.ecxel_separator);
        record.add(tmp_str);
      }

      rows.add(record);
    }

    return rows;
  }

}
