package net.sam.dcl.form;

import net.sam.dcl.taglib.table.model.impl.HolderImplUsingList;
import net.sam.dcl.util.StringUtil;
import net.sam.dcl.beans.*;

/**
 * Date: Aug 6, 2005
 * Time: 5:37:29 PM
 */
public class MarginForm extends ReportBaseForm
{
  HolderImplUsingList grid = new HolderImplUsingList();

  String common1 = " * ";
  String common2 = " sum(spc_summ) as spc_summ,\n" +
          "      sum(lps_summ_eur) as lps_summ_eur,\n" +
          "      sum(lps_summ) as lps_summ,\n" +
          "      sum(lps_sum_transport) as lps_sum_transport,\n" +
          "      sum(lps_custom) as lps_custom,\n" +
          "      sum(lcc_charges) as lcc_charges,\n" +
          "      sum(lcc_montage) as lcc_montage,\n" +
          "      sum(lcc_transport) as lcc_transport,\n" +
          "      sum(lps_montage_time) as lps_montage_time,\n" +
          "      sum(montage_cost) as montage_cost,\n" +
          "      sum(lcc_update_sum) as lcc_update_sum,\n" +
          "      sum(summ) as summ,\n" +
          "      sum(summ_zak) as summ_zak,\n" +
          "      sum(margin) as margin, ";

  String select_list = "";
  String group_by = "";
  String order_by = "";

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
  String ctc_block;
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
  boolean logisticsReadOnly = false;
  String view_montage_sum;
  String view_montage_time;
  String view_montage_cost;
  String view_update_sum;
  String view_summ_zak;
  String view_koeff;
  String view_user;
  String view_department;

  boolean readOnlyForManager;
  boolean showForChiefDep;

  public HolderImplUsingList getGrid()
  {
    return grid;
  }

  public void setGrid(HolderImplUsingList grid)
  {
    this.grid = grid;
  }

  public boolean isCheckAspect()
  {
    return "1".equals(getUser_aspect()) ||
            "1".equals(getDepartment_aspect()) ||
            "1".equals(getContractor_aspect()) ||
            "1".equals(getStuff_category_aspect()) ||
            "1".equals(getRoute_aspect());

  }

  public boolean isSelectAll()
  {
    return showForChiefDep && "-1".equals(getUser().getUsr_id()) || ("-1".equals(getUser().getUsr_id()) && !StringUtil.isEmpty(getUser().getUserFullName())) ||
            ("-1".equals(getDepartment().getId()) && !StringUtil.isEmpty(getDepartment().getName())) ||
            ("-1".equals(getContractor().getId()) && !StringUtil.isEmpty(getContractor().getName())) ||
            ("-1".equals(getStuffCategory().getId()) && !StringUtil.isEmpty(getStuffCategory().getName())) ||
            ("-1".equals(getRoute().getId()) && !StringUtil.isEmpty(getRoute().getName()));

  }

  public boolean getSelect()
  {
    return !(StringUtil.isEmpty(getUser().getUsr_id()) &&
            StringUtil.isEmpty(getDepartment().getId()) &&
            StringUtil.isEmpty(getContractor().getId()) &&
            StringUtil.isEmpty(getStuffCategory().getId()) &&
            StringUtil.isEmpty(getRoute().getId()));

  }

  public boolean getUserDisabled()
  {
    return !showForChiefDep && (!StringUtil.isEmpty(getUser().getUserFullName()) || readOnlyForManager);

  }

  public boolean getDepartmentDisabled()
  {
    return !StringUtil.isEmpty(getDepartment().getName()) || readOnlyForManager;
  }

  public boolean getContractorDisabled()
  {
    return !StringUtil.isEmpty(getContractor().getName()) || readOnlyForManager;
  }

  public boolean getStuff_categoryDisabled()
  {
    return !StringUtil.isEmpty(getStuffCategory().getName()) || readOnlyForManager;
  }

  public boolean getRouteDisabled()
  {
    return !StringUtil.isEmpty(getRoute().getName()) || readOnlyForManager;
  }

  public boolean isCanForm()
  {
    return canForm && getSelect();
  }

  public String getSelect_list()
  {
    return select_list;
  }

  public void setSelect_list(String select_list)
  {
    this.select_list = select_list;
  }

  public String getGroup_by()
  {
    return group_by;
  }

  public void setGroup_by(String group_by)
  {
    this.group_by = group_by;
  }

  public String getOrder_by()
  {
    return order_by;
  }

  public void setOrder_by(String order_by)
  {
    this.order_by = order_by;
  }

  public String getCommon1()
  {
    return common1;
  }

  public String getCommon2()
  {
    return common2;
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

  public String getCtc_block()
  {
    return ctc_block;
  }

  public void setCtc_block(String ctc_block)
  {
    this.ctc_block = ctc_block;
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

  public boolean isLogisticsReadOnly()
  {
    return logisticsReadOnly;
  }

  public void setLogisticsReadOnly(boolean logisticsReadOnly)
  {
    this.logisticsReadOnly = logisticsReadOnly;
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

  public String getTop_view_charges_count()
  {
    int base_count = 0;
    if ( !StringUtil.isEmpty(view_transport) )
    {
      base_count++;
    }
    if ( !StringUtil.isEmpty(view_transport_sum) )
    {
      base_count++;
    }
    if ( !StringUtil.isEmpty(view_custom) )
    {
      base_count++;
    }
    if ( !StringUtil.isEmpty(view_other_sum) )
    {
      base_count++;
    }
    if ( !StringUtil.isEmpty(view_montage_sum) )
    {
      base_count++;
    }
    if ( !StringUtil.isEmpty(view_montage_time) )
    {
      base_count++;
    }
    if ( !StringUtil.isEmpty(view_montage_cost) )
    {
      base_count++;
    }
    if ( !StringUtil.isEmpty(view_update_sum) )
    {
      base_count++;
    }
    return Integer.toString(base_count);
  }
  public boolean isReadOnlyForManager()
  {
    return readOnlyForManager;
  }

  public void setReadOnlyForManager(boolean readOnlyForManager)
  {
    this.readOnlyForManager = readOnlyForManager;
  }

  public boolean isShowForChiefDep()
  {
    return showForChiefDep;
  }

  public void setShowForChiefDep(boolean showForChiefDep)
  {
    this.showForChiefDep = showForChiefDep;
  }
}
