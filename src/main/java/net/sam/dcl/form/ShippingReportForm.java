package net.sam.dcl.form;

import net.sam.dcl.taglib.table.model.impl.HolderImplUsingList;
import net.sam.dcl.beans.*;
import net.sam.dcl.util.StringUtil;

/**
 * Date: Aug 6, 2005
 * Time: 5:37:29 PM
 */
public class ShippingReportForm extends ReportBaseForm
{
  HolderImplUsingList grid = new HolderImplUsingList();
  public static String selectAll = " * ";

  public static String selectOnlyItod = " sum(spc_summ) as spc_summ,\n" +
          "    sum(lps_summ_plus_nds) as lps_summ_plus_nds,\n" +
          "    sum(lps_summ_out_nds) as lps_summ_out_nds,\n" +
          "    sum(lps_summ_out_nds_eur) as lps_summ_out_nds_eur,\n" +
          "    sum(lps_summ_zak) as lps_summ_zak,\n" +
          "    sum(lps_sum_transport) as lps_sum_transport,\n" +
          "    sum(lps_custom) as lps_custom";
  public static String selectOnlyItodCtr = ",\n" +
          "    shp_contractor, currency";
  public static String selectOnlyItodUsr = ",\n" +
          "    manager, currency";
  public static String selectOnlyItodDep = ",\n" +
          "    department, currency";
  public static String selectOnlyItodDepForChief = ",\n" +
          "    department";
  public static String groupByOnlyItodCtr = " group by \n" +
          "    shp_contractor, currency";
  public static String groupByOnlyItodUsr = " group by \n" +
          "    manager, currency";
  public static String groupByOnlyItodDep = " group by \n" +
          "    department, currency";
  public static String groupByOnlyItodForChief = " group by \n" +
          "    manager, department, currency";

  public static String orderByDefault = " order by \n" +
          "    currency";
  public static String orderByShp = " order by \n" +
          "    currency, shp_id";
  public static String orderByShpUser = ",\n" +
          "    manager_id";
  public static String orderByShpStuff = ",\n" +
          "    stf_id";
  public static String orderByShpProduce = ",\n" +
          "    prd_id";

  String select_list = "";
  String group_by = "";
  String order_by = "";

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

  boolean readOnlyForManager;
  boolean showForChiefDep;

  boolean showShpNumDate = true;
  boolean showLegend;

  public HolderImplUsingList getGrid()
  {
    return grid;
  }

  public void setGrid(HolderImplUsingList grid)
  {
    this.grid = grid;
  }

  public boolean isSelectAll()
  {
    if ( showForChiefDep && "-1".equals(getUser().getUsr_id())
       )
    {
      return true;
    }

    if (
            ("-1".equals(getUser().getUsr_id()) && !StringUtil.isEmpty(getUser().getUsr_name())) ||
            ("-1".equals(getDepartment().getId()) && !StringUtil.isEmpty(getDepartment().getName())) ||
            ("-1".equals(getContractor().getId()) && !StringUtil.isEmpty(getContractor().getName()))
       )
    {
      return true;
    }

    return false;
  }

  public boolean getSelect()
  {
    if (
            StringUtil.isEmpty(getUser().getUsr_id()) &&
            StringUtil.isEmpty(getDepartment().getId()) &&
            StringUtil.isEmpty(getContractor().getId())
       )
    {
      return false;
    }

    return true;
  }

  public boolean getUserDisabled()
  {
    if (showForChiefDep)
    {
      return false;
    }

    if (StringUtil.isEmpty(getUser().getUsr_name()))
    {
      return readOnlyForManager;
    }
    else
    {
      return true;
    }
  }

  public boolean getDepartmentDisabled()
  {
    if (StringUtil.isEmpty(getDepartment().getName()))
    {
      return readOnlyForManager;
    }
    else
    {
      return true;
    }
  }

  public boolean getContractorDisabled()
  {
    if (StringUtil.isEmpty(getContractor().getName()))
    {
      return readOnlyForManager;
    }
    else
    {
      return true;
    }
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

  public String getIncludeType()
  {
    if ( "1".equals(include_all) )
    {
      return "0";
    }
    else if ( "1".equals(include_closed) )
    {
      return "1";
    }
    else if ( "1".equals(include_opened) )
    {
      return "2";
    }
    else
    {
      return "";
    }
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
}
